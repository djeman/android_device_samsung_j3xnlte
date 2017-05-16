/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony;

import static com.android.internal.telephony.RILConstants.*;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.SystemProperties;
import android.telephony.gsm.CbConfig;
import android.telephony.Rlog;
import android.telephony.PhoneNumberUtils;

import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.SimLockInfoResult;
import com.android.internal.telephony.uicc.SimPBEntryResult;
import com.android.internal.telephony.uicc.SpnOverride;
import com.android.internal.telephony.RILConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Custom RIL to handle unique behavior of SPRD RIL
 *
 * {@hide}
 */
public class SamsungSPRDRIL extends RIL implements CommandsInterface {

    private static final int RIL_REQUEST_DIAL_EMERGENCY_CALL = 10001;
    private static final int RIL_REQUEST_CALL_DEFLECTION = 10002;
    private static final int RIL_REQUEST_MODIFY_CALL_INITIATE = 10003;
    private static final int RIL_REQUEST_MODIFY_CALL_CONFIRM = 10004;
    private static final int RIL_REQUEST_SET_VOICE_DOMAIN_PREF = 10005;
    private static final int RIL_REQUEST_SAFE_MODE = 10006;
    private static final int RIL_REQUEST_SET_TRANSMIT_POWER = 10007;
    private static final int RIL_REQUEST_GET_CELL_BROADCAST_CONFIG = 10008;
    private static final int RIL_REQUEST_GET_PHONEBOOK_STORAGE_INFO = 10009;
    private static final int RIL_REQUEST_GET_PHONEBOOK_ENTRY = 10010;
    private static final int RIL_REQUEST_ACCESS_PHONEBOOK_ENTRY = 10011;
    private static final int RIL_REQUEST_USIM_PB_CAPA = 10012;
    private static final int RIL_REQUEST_LOCK_INFO = 10013;
    private static final int RIL_REQUEST_STK_SIM_INIT_EVENT = 10014;
    private static final int RIL_REQUEST_SET_PREFERRED_NETWORK_LIST = 10015;
    private static final int RIL_REQUEST_GET_PREFERRED_NETWORK_LIST = 10016;
    private static final int RIL_REQUEST_CHANGE_SIM_PERSO = 10017;
    private static final int RIL_REQUEST_ENTER_SIM_PERSO = 10018;
    private static final int RIL_REQUEST_SEND_ENCODED_USSD = 10019;
    private static final int RIL_REQUEST_CDMA_SEND_SMS_EXPECT_MORE = 10020;
    private static final int RIL_REQUEST_HANGUP_VT = 10021;
    private static final int RIL_REQUEST_HOLD = 10022;
    private static final int RIL_REQUEST_SET_SIM_POWER = 10023;
    private static final int RIL_REQUEST_SET_LTE_BAND_MODE = 10024;
    private static final int RIL_REQUEST_UICC_GBA_AUTHENTICATE_BOOTSTRAP = 10025;
    private static final int RIL_REQUEST_UICC_GBA_AUTHENTICATE_NAF = 10026;
    private static final int RIL_REQUEST_GET_INCOMING_COMMUNICATION_BARRING = 10027;
    private static final int RIL_REQUEST_SET_INCOMING_COMMUNICATION_BARRING = 10028;
    private static final int RIL_REQUEST_QUERY_CNAP = 10029;

    static final boolean SHIP_BUILD = "true".equals(SystemProperties.get("ro.product_ship", "false"));

    public SamsungSPRDRIL(Context context, int networkMode, int cdmaSubscription) {
        this(context, networkMode, cdmaSubscription, null);
    }

    public SamsungSPRDRIL(Context context, int networkMode,
            int cdmaSubscription, Integer instanceId) {
        super(context, networkMode, cdmaSubscription, instanceId);
        mQANElements = SystemProperties.getInt("ro.telephony.ril_qanelements", 6);
    }

    @Override
    public void dial(String address, int clirMode, UUSInfo uusInfo, Message result) {
        RILRequest rr = RILRequest.obtain(RIL_REQUEST_DIAL, result);

        rr.mParcel.writeString(address);
        rr.mParcel.writeInt(clirMode);
        rr.mParcel.writeInt(0);     // CallDetails.call_type
        rr.mParcel.writeInt(1);     // CallDetails.call_domain
        rr.mParcel.writeString(""); // CallDetails.getCsvFromExtras

        if (uusInfo == null) {
            rr.mParcel.writeInt(0); // UUS information is absent
        } else {
            rr.mParcel.writeInt(1); // UUS information is present
            rr.mParcel.writeInt(uusInfo.getType());
            rr.mParcel.writeInt(uusInfo.getDcs());
            rr.mParcel.writeByteArray(uusInfo.getUserData());
        }

        if (RILJ_LOGD) riljLog(rr.serialString() + "> " + requestToString(rr.mRequest));

        send(rr);
    }

    @Override
    public void acceptCall(Message result) {
        RILRequest rr = RILRequest.obtain(RIL_REQUEST_ANSWER, result);

        if (RILJ_LOGD) riljLog(rr.serialString() + "> " + requestToString(rr.mRequest));

        rr.mParcel.writeInt(1);
        rr.mParcel.writeInt(0);

        send(rr);
    }

    @Override
    protected Object
    responseIccCardStatus(Parcel p) {
        IccCardApplicationStatus appStatus;

        IccCardStatus cardStatus = new IccCardStatus();
        cardStatus.setCardState(p.readInt());
        cardStatus.setUniversalPinState(p.readInt());
        cardStatus.mGsmUmtsSubscriptionAppIndex = p.readInt();
        cardStatus.mCdmaSubscriptionAppIndex = p.readInt();
        cardStatus.mImsSubscriptionAppIndex = p.readInt();

        int numApplications = p.readInt();

        // limit to maximum allowed applications
        if (numApplications > IccCardStatus.CARD_MAX_APPS) {
            numApplications = IccCardStatus.CARD_MAX_APPS;
        }
        cardStatus.mApplications = new IccCardApplicationStatus[numApplications];

        for (int i = 0 ; i < numApplications ; i++) {
            appStatus = new IccCardApplicationStatus();
            appStatus.app_type       = appStatus.AppTypeFromRILInt(p.readInt());
            appStatus.app_state      = appStatus.AppStateFromRILInt(p.readInt());
            appStatus.perso_substate = appStatus.PersoSubstateFromRILInt(p.readInt());
            appStatus.aid            = p.readString();
            appStatus.app_label      = p.readString();
            appStatus.pin1_replaced  = p.readInt();
            appStatus.pin1           = appStatus.PinStateFromRILInt(p.readInt());
            appStatus.pin2           = appStatus.PinStateFromRILInt(p.readInt());
            // All subsequent readInt()s added for our device
            p.readInt(); // pin1_num_retries
            p.readInt(); // puk1_num_retries
            p.readInt(); // pin2_num_retries
            p.readInt(); // puk2_num_retries
            p.readInt(); // perso_unblock_retries

            cardStatus.mApplications[i] = appStatus;
        }
        return cardStatus;
    }

    @Override
    protected Object
    responseCallList(Parcel p) {
        int num;
        int voiceSettings;
        ArrayList<DriverCall> response;
        DriverCall dc;

        num = p.readInt();
        response = new ArrayList<DriverCall>(num);

        if (RILJ_LOGV) {
            riljLog("responseCallList: num=" + num +
                    " mEmergencyCallbackModeRegistrant=" + mEmergencyCallbackModeRegistrant +
                    " mTestingEmergencyCall=" + mTestingEmergencyCall.get());
        }
        for (int i = 0 ; i < num ; i++) {
            dc = new DriverCall();

            dc.state = DriverCall.stateFromCLCC(p.readInt());
            // & 0xff to truncate to 1 byte added for us, not in RIL.java
            dc.index = p.readInt() & 0xff;
            dc.TOA = p.readInt();
            dc.isMpty = (0 != p.readInt());
            dc.isMT = (0 != p.readInt());
            dc.als = p.readInt();
            voiceSettings = p.readInt();
            dc.isVoice = (0 != voiceSettings);
            boolean isVideo = (0 != p.readInt());
            int call_type = p.readInt();            // Samsung CallDetails
            int call_domain = p.readInt();          // Samsung CallDetails
            p.readInt();            // Samsung CallDetails
            dc.isVoicePrivacy = (0 != p.readInt());
            dc.number = p.readString();
            int np = p.readInt();
            dc.numberPresentation = DriverCall.presentationFromCLIP(np);
            dc.name = p.readString();
            dc.namePresentation = p.readInt();
            int uusInfoPresent = p.readInt();
            if (uusInfoPresent == 1) {
                dc.uusInfo = new UUSInfo();
                dc.uusInfo.setType(p.readInt());
                dc.uusInfo.setDcs(p.readInt());
                byte[] userData = p.createByteArray();
                dc.uusInfo.setUserData(userData);
                riljLogv(String.format("Incoming UUS : type=%d, dcs=%d, length=%d",
                                dc.uusInfo.getType(), dc.uusInfo.getDcs(),
                                dc.uusInfo.getUserData().length));
                riljLogv("Incoming UUS : data (string)="
                        + new String(dc.uusInfo.getUserData()));
                riljLogv("Incoming UUS : data (hex): "
                        + IccUtils.bytesToHexString(dc.uusInfo.getUserData()));
            } else {
                riljLogv("Incoming UUS : NOT present!");
            }

            // Make sure there's a leading + on addresses with a TOA of 145
            dc.number = PhoneNumberUtils.stringFromStringAndTOA(dc.number, dc.TOA);

            response.add(dc);

            if (dc.isVoicePrivacy) {
                mVoicePrivacyOnRegistrants.notifyRegistrants();
                riljLog("InCall VoicePrivacy is enabled");
            } else {
                mVoicePrivacyOffRegistrants.notifyRegistrants();
                riljLog("InCall VoicePrivacy is disabled");
            }
        }

        Collections.sort(response);

        if ((num == 0) && mTestingEmergencyCall.getAndSet(false)) {
            if (mEmergencyCallbackModeRegistrant != null) {
                riljLog("responseCallList: call ended, testing emergency call," +
                            " notify ECM Registrants");
                mEmergencyCallbackModeRegistrant.notifyRegistrant();
            }
        }

        return response;
    }

    private Object responseCbSettings(final Parcel p) {
        int n = 0;
        final CbConfig cbConfig = new CbConfig();
        Rlog.d("RILJ", "responseCbSettings");

        final int value = p.readInt();
        if (value == 1)
            cbConfig.bCBEnabled = true;
        else if (value == 2)
            cbConfig.bCBEnabled = false;
        
        cbConfig.selectedId = (char)p.readInt();
        cbConfig.msgIdMaxCount = (char)p.readInt();
        cbConfig.msgIdCount = p.readInt();

        if (cbConfig.msgIdMaxCount > 0)
            cbConfig.msgIDs = new short[2 * cbConfig.msgIdMaxCount];
        else
            cbConfig.msgIDs = new short[100];

        if (cbConfig.msgIdCount > 100)
            Rlog.d("RILJ", "No of CBMID Exceeded ");
        
        cbConfig.msgIDs = new short[cbConfig.msgIdCount];
        final String string = p.readString();
        Rlog.d("RILJ", "ENABLED:" + cbConfig.bCBEnabled + ", selectedId:" + (int)cbConfig.selectedId + ", msgIdCount:" + cbConfig.msgIdCount + ", msgIdMaxCount:" + (int)cbConfig.msgIdMaxCount);

        if (string == null) {
            Rlog.d("RILJ", "MessageIDs String is NULL");
        } else {
            Rlog.d("RILJ", ", MessageIDs:" + string);
            final byte[] hexStringToBytes = IccUtils.hexStringToBytes(string);
            for (int i = 0; i < cbConfig.msgIdCount; ++i) {
                cbConfig.msgIDs[i] = (short)((0xFF & hexStringToBytes[n + 1]) | (0xFF & hexStringToBytes[n]) << 8);
                n += 2;
            }
        }

        return cbConfig;
    }

    private Object responseSIM_PB(final Parcel p) {
        final int[] array = new int[3];
        final int[] array2 = new int[3];
        final String[] array3 = new String[3];
        final int[] array4 = new int[5];
        final int[] array5 = new int[5];
        final String[] array6 = new String[5];

        p.readIntArray(array);
        p.readIntArray(array2);
        p.readStringArray(array3);

        if (!SHIP_BUILD)
            Rlog.i("RILJ", "alphaTag is " + array3[0]);
        
        if (!SHIP_BUILD)
            Rlog.i("RILJ", "SNE is " + array3[1]);
        
        if (!SHIP_BUILD)
            Rlog.i("RILJ", "email is " + array3[2]);
        
        p.readIntArray(array4);
        Rlog.i("RILJ", "lengthNumber is " + array4[0]);
        p.readIntArray(array5);
        p.readStringArray(array6);

        if (!SHIP_BUILD)
            Rlog.i("RILJ", "number is " + array6[0]);
        
        if (!SHIP_BUILD)
            Rlog.i("RILJ", "ANR is " + array6[1]);
        
        return new SimPBEntryResult(array, array2, array3, array4, array5, array6, p.readInt(), p.readInt());
    }

    private Object responseSIM_LockInfo(final Parcel p) {
        final int num = p.readInt();
        final int type = p.readInt();
        final int key = p.readInt();
        final int retry = p.readInt();
        Rlog.i("RILJ", "num:" + num + " lock_type:" + type + " lock_key:" + key + " num_of_retry:" + retry);
        return new SimLockInfoResult(num, type, key, retry);
    }

    private Object responseSimPowerDone(final Parcel p) {
        Rlog.d("RILJ", "ResponseSimPowerDone");
        final int int1 = p.readInt();
        final int[] array = new int[int1];
        for (int i = 0; i < int1; ++i) {
            array[i] = p.readInt();
        }
        Rlog.d("RILJ", "ResponseSimPowerDone : " + array[0]);
        return array[0];
    }

    private Object responseBootstrap(final Parcel p) {
        final Bundle b = new Bundle();
        b.putByteArray("res", IccUtils.hexStringToBytes(p.readString()));
        b.putByteArray("auts", IccUtils.hexStringToBytes(p.readString()));
        return b;
    }

    private Object responseNaf(final Parcel p) {
        return IccUtils.hexStringToBytes(p.readString());
    }

    private Object responsePreferredNetworkList(final Parcel p) {
        final int int1 = p.readInt();
        Rlog.d("RILJ", "number of network list = " + int1);
        final ArrayList list = new ArrayList<PreferredNetworkListInfo>(int1);
        for (int i = 0; i < int1; ++i) {
            final PreferredNetworkListInfo preferredNetworkListInfo = new PreferredNetworkListInfo();
            preferredNetworkListInfo.mIndex = p.readInt();
            preferredNetworkListInfo.mOperator = p.readString();
            preferredNetworkListInfo.mPlmn = p.readString();
            preferredNetworkListInfo.mGsmAct = p.readInt();
            preferredNetworkListInfo.mGsmCompactAct = p.readInt();
            preferredNetworkListInfo.mUtranAct = p.readInt();
            preferredNetworkListInfo.mMode = p.readInt();
            list.add(preferredNetworkListInfo);
        }
        return list;
    }

    @Override
    protected RILRequest processSolicited(Parcel p) {
        int serial, error;

        serial = p.readInt();
        error = p.readInt();

        RILRequest rr;

        rr = findAndRemoveRequestFromList(serial);

        if (rr == null) {
            Rlog.w(RILJ_LOG_TAG, "Unexpected solicited response! sn: "
                            + serial + " error: " + error);
            return null;
        }

        Object ret = null;

        if (error == 0 || p.dataAvail() > 0) {
            // either command succeeds or command fails but with data payload
            try {switch (rr.mRequest) {
            /*
 cat libs/telephony/ril_commands.h \
 | egrep "^ *{RIL_" \
 | sed -re 's/\{([^,]+),[^,]+,([^}]+).+/case \1: ret = \2(p); break;/'
             */
            case RIL_REQUEST_GET_SIM_STATUS: ret =  responseIccCardStatus(p); break;
            case RIL_REQUEST_ENTER_SIM_PIN: ret =  responseInts(p); break;
            case RIL_REQUEST_ENTER_SIM_PUK: ret =  responseInts(p); break;
            case RIL_REQUEST_ENTER_SIM_PIN2: ret =  responseInts(p); break;
            case RIL_REQUEST_ENTER_SIM_PUK2: ret =  responseInts(p); break;
            case RIL_REQUEST_CHANGE_SIM_PIN: ret =  responseInts(p); break;
            case RIL_REQUEST_CHANGE_SIM_PIN2: ret =  responseInts(p); break;
            case RIL_REQUEST_ENTER_NETWORK_DEPERSONALIZATION: ret =  responseInts(p); break;
            case RIL_REQUEST_GET_CURRENT_CALLS: ret =  responseCallList(p); break;
            case RIL_REQUEST_DIAL: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_IMSI: ret =  responseString(p); break;
            case RIL_REQUEST_HANGUP: ret =  responseVoid(p); break;
            case RIL_REQUEST_HANGUP_WAITING_OR_BACKGROUND: ret =  responseVoid(p); break;
            case RIL_REQUEST_HANGUP_FOREGROUND_RESUME_BACKGROUND: {
                if (mTestingEmergencyCall.getAndSet(false)) {
                    if (mEmergencyCallbackModeRegistrant != null) {
                        riljLog("testing emergency call, notify ECM Registrants");
                        mEmergencyCallbackModeRegistrant.notifyRegistrant();
                    }
                }
                ret = responseVoid(p);
                break;
            }
            case RIL_REQUEST_SWITCH_WAITING_OR_HOLDING_AND_ACTIVE: ret =  responseVoid(p); break;
            case RIL_REQUEST_CONFERENCE: ret =  responseVoid(p); break;
            case RIL_REQUEST_UDUB: ret =  responseVoid(p); break;
            case RIL_REQUEST_LAST_CALL_FAIL_CAUSE: ret =  responseInts(p); break;
            case RIL_REQUEST_SIGNAL_STRENGTH: ret =  responseSignalStrength(p); break;
            case RIL_REQUEST_VOICE_REGISTRATION_STATE: ret =  responseString(p); break;
            case RIL_REQUEST_DATA_REGISTRATION_STATE: ret =  responseString(p); break;
            case RIL_REQUEST_OPERATOR: ret =  responseString(p); break;
            case RIL_REQUEST_RADIO_POWER: ret =  responseVoid(p); break;
            case RIL_REQUEST_DTMF: ret =  responseVoid(p); break;
            case RIL_REQUEST_SEND_SMS: ret =  responseSMS(p); break;
            case RIL_REQUEST_SEND_SMS_EXPECT_MORE: ret =  responseSMS(p); break;
            case RIL_REQUEST_SETUP_DATA_CALL: ret =  responseSetupDataCall(p); break;
            case RIL_REQUEST_SIM_IO: ret =  responseICC_IO(p); break;
            case RIL_REQUEST_SEND_USSD: ret =  responseVoid(p); break;
            case RIL_REQUEST_CANCEL_USSD: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_CLIR: ret =  responseInts(p); break;
            case RIL_REQUEST_SET_CLIR: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_CALL_FORWARD_STATUS: ret =  responseCallForward(p); break;
            case RIL_REQUEST_SET_CALL_FORWARD: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_CALL_WAITING: ret =  responseInts(p); break;
            case RIL_REQUEST_SET_CALL_WAITING: ret =  responseVoid(p); break;
            case RIL_REQUEST_SMS_ACKNOWLEDGE: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_IMEI: ret =  responseString(p); break;
            case RIL_REQUEST_GET_IMEISV: ret =  responseString(p); break;
            case RIL_REQUEST_ANSWER: ret =  responseVoid(p); break;
            case RIL_REQUEST_DEACTIVATE_DATA_CALL: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_FACILITY_LOCK: ret =  responseInts(p); break;
            case RIL_REQUEST_SET_FACILITY_LOCK: ret =  responseInts(p); break;
            case RIL_REQUEST_CHANGE_BARRING_PASSWORD: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_NETWORK_SELECTION_MODE: ret =  responseInts(p); break;
            case RIL_REQUEST_SET_NETWORK_SELECTION_AUTOMATIC: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_NETWORK_SELECTION_MANUAL: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_AVAILABLE_NETWORKS: ret =  responseOperatorInfos(p); break;
            case RIL_REQUEST_DTMF_START: ret =  responseVoid(p); break;
            case RIL_REQUEST_DTMF_STOP: ret =  responseVoid(p); break;
            case RIL_REQUEST_BASEBAND_VERSION: ret =  responseString(p); break;
            case RIL_REQUEST_SEPARATE_CONNECTION: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_MUTE: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_MUTE: ret =  responseInts(p); break;
            case RIL_REQUEST_QUERY_CLIP: ret =  responseInts(p); break;
            case RIL_REQUEST_LAST_DATA_CALL_FAIL_CAUSE: ret =  responseInts(p); break;
            case RIL_REQUEST_DATA_CALL_LIST: ret =  responseDataCallList(p); break;
            case RIL_REQUEST_RESET_RADIO: ret =  responseVoid(p); break;
            case RIL_REQUEST_OEM_HOOK_RAW: ret =  responseRaw(p); break;
            case RIL_REQUEST_OEM_HOOK_STRINGS: ret =  responseString(p); break;
            case RIL_REQUEST_SCREEN_STATE: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_SUPP_SVC_NOTIFICATION: ret =  responseVoid(p); break;
            case RIL_REQUEST_WRITE_SMS_TO_SIM: ret =  responseInts(p); break;
            case RIL_REQUEST_DELETE_SMS_ON_SIM: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_BAND_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_AVAILABLE_BAND_MODE: ret =  responseInts(p); break;
            case RIL_REQUEST_STK_GET_PROFILE: ret =  responseString(p); break;
            case RIL_REQUEST_STK_SET_PROFILE: ret =  responseVoid(p); break;
            case RIL_REQUEST_STK_SEND_ENVELOPE_COMMAND: ret =  responseString(p); break;
            case RIL_REQUEST_STK_SEND_TERMINAL_RESPONSE: ret =  responseVoid(p); break;
            case RIL_REQUEST_STK_HANDLE_CALL_SETUP_REQUESTED_FROM_SIM: ret =  responseInts(p); break;
            case RIL_REQUEST_EXPLICIT_CALL_TRANSFER: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_PREFERRED_NETWORK_TYPE: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_PREFERRED_NETWORK_TYPE: ret =  responseGetPreferredNetworkType(p); break;
            case RIL_REQUEST_GET_NEIGHBORING_CELL_IDS: ret =  responseCellList(p); break;
            case RIL_REQUEST_SET_LOCATION_UPDATES: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_SET_SUBSCRIPTION_SOURCE: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_SET_ROAMING_PREFERENCE: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_QUERY_ROAMING_PREFERENCE: ret =  responseInts(p); break;
            case RIL_REQUEST_SET_TTY_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_TTY_MODE: ret =  responseInts(p); break;
            case RIL_REQUEST_CDMA_SET_PREFERRED_VOICE_PRIVACY_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_QUERY_PREFERRED_VOICE_PRIVACY_MODE: ret =  responseInts(p); break;
            case RIL_REQUEST_CDMA_FLASH: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_BURST_DTMF: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_SEND_SMS: ret =  responseSMS(p); break;
            case RIL_REQUEST_CDMA_SMS_ACKNOWLEDGE: ret =  responseVoid(p); break;
            case RIL_REQUEST_GSM_GET_BROADCAST_CONFIG: ret =  responseGmsBroadcastConfig(p); break;
            case RIL_REQUEST_GSM_SET_BROADCAST_CONFIG: ret =  responseVoid(p); break;
            case RIL_REQUEST_GSM_BROADCAST_ACTIVATION: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_GET_BROADCAST_CONFIG: ret =  responseCdmaBroadcastConfig(p); break;
            case RIL_REQUEST_CDMA_SET_BROADCAST_CONFIG: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_BROADCAST_ACTIVATION: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_VALIDATE_AND_WRITE_AKEY: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_SUBSCRIPTION: ret =  responseString(p); break;
            case RIL_REQUEST_CDMA_WRITE_SMS_TO_RUIM: ret =  responseInts(p); break;
            case RIL_REQUEST_CDMA_DELETE_SMS_ON_RUIM: ret =  responseVoid(p); break;
            case RIL_REQUEST_DEVICE_IDENTITY: ret =  responseString(p); break;
            case RIL_REQUEST_GET_SMSC_ADDRESS: ret =  responseString(p); break;
            case RIL_REQUEST_SET_SMSC_ADDRESS: ret =  responseVoid(p); break;
            case RIL_REQUEST_EXIT_EMERGENCY_CALLBACK_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_REPORT_SMS_MEMORY_STATUS: ret =  responseVoid(p); break;
            case RIL_REQUEST_REPORT_STK_SERVICE_IS_RUNNING: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_GET_SUBSCRIPTION_SOURCE: ret =  responseInts(p); break;
            case RIL_REQUEST_ISIM_AUTHENTICATION: ret =  responseString(p); break;
            case RIL_REQUEST_ACKNOWLEDGE_INCOMING_GSM_SMS_WITH_PDU: ret =  responseVoid(p); break;
            case RIL_REQUEST_STK_SEND_ENVELOPE_WITH_STATUS: ret =  responseICC_IO(p); break;
            case RIL_REQUEST_VOICE_RADIO_TECH: ret =  responseInts(p); break;
            case RIL_REQUEST_GET_CELL_INFO_LIST: ret =  responseCellInfoList(p); break;
            case RIL_REQUEST_SET_UNSOL_CELL_INFO_LIST_RATE: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_INITIAL_ATTACH_APN: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_DATA_PROFILE: ret =  responseVoid(p); break;
            case RIL_REQUEST_IMS_REGISTRATION_STATE: ret =  responseInts(p); break;
            case RIL_REQUEST_IMS_SEND_SMS: ret =  responseSMS(p); break;
            case RIL_REQUEST_SIM_TRANSMIT_APDU_BASIC: ret =  responseICC_IO(p); break;
            case RIL_REQUEST_SIM_OPEN_CHANNEL: ret =  responseInts(p); break;
            case RIL_REQUEST_SIM_CLOSE_CHANNEL: ret =  responseVoid(p); break;
            case RIL_REQUEST_SIM_TRANSMIT_APDU_CHANNEL: ret =  responseICC_IO(p); break;
            case RIL_REQUEST_NV_READ_ITEM: ret =  responseString(p); break;
            case RIL_REQUEST_NV_WRITE_ITEM: ret =  responseVoid(p); break;
            case RIL_REQUEST_NV_WRITE_CDMA_PRL: ret =  responseVoid(p); break;
            case RIL_REQUEST_NV_RESET_CONFIG: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_UICC_SUBSCRIPTION: ret =  responseVoid(p); break;
            case RIL_REQUEST_ALLOW_DATA: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_HARDWARE_CONFIG: ret =  responseHardwareConfig(p); break;
            case RIL_REQUEST_SIM_AUTHENTICATION: ret =  responseICC_IO(p); break;
            case RIL_REQUEST_SHUTDOWN: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_RADIO_CAPABILITY: ret =  responseRadioCapability(p); break;
            case RIL_REQUEST_SET_RADIO_CAPABILITY: ret =  responseRadioCapability(p); break;

            case RIL_REQUEST_DIAL_EMERGENCY_CALL: ret =  responseVoid(p); break;
            case RIL_REQUEST_CALL_DEFLECTION: ret =  responseVoid(p); break;
            case RIL_REQUEST_MODIFY_CALL_INITIATE: ret =  responseInts(p); break;
            case RIL_REQUEST_MODIFY_CALL_CONFIRM: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_VOICE_DOMAIN_PREF: ret =  responseVoid(p); break;
            case RIL_REQUEST_SAFE_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_TRANSMIT_POWER: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_CELL_BROADCAST_CONFIG: ret =  responseCbSettings(p); break;
            case RIL_REQUEST_GET_PHONEBOOK_STORAGE_INFO: ret =  responseInts(p); break;
            case RIL_REQUEST_GET_PHONEBOOK_ENTRY: ret =  responseSIM_PB(p); break;
            case RIL_REQUEST_ACCESS_PHONEBOOK_ENTRY: ret =  responseInts(p); break;
            case RIL_REQUEST_USIM_PB_CAPA: ret =  responseInts(p); break;
            case RIL_REQUEST_LOCK_INFO: ret =  responseSIM_LockInfo(p); break;
            case RIL_REQUEST_STK_SIM_INIT_EVENT: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_PREFERRED_NETWORK_LIST: ret =  responseVoid(p); break;
            case RIL_REQUEST_GET_PREFERRED_NETWORK_LIST: ret =  responsePreferredNetworkList(p); break;
            case RIL_REQUEST_CHANGE_SIM_PERSO: ret =  responseInts(p); break;
            case RIL_REQUEST_ENTER_SIM_PERSO: ret =  responseInts(p); break;
            case RIL_REQUEST_SEND_ENCODED_USSD: ret =  responseVoid(p); break;
            case RIL_REQUEST_CDMA_SEND_SMS_EXPECT_MORE: ret =  responseSMS(p); break;
            case RIL_REQUEST_HANGUP_VT: ret =  responseVoid(p); break;
            case RIL_REQUEST_HOLD: ret =  responseVoid(p); break;
            case RIL_REQUEST_SET_SIM_POWER: ret =  responseSimPowerDone(p); break;
            case RIL_REQUEST_SET_LTE_BAND_MODE: ret =  responseVoid(p); break;
            case RIL_REQUEST_UICC_GBA_AUTHENTICATE_BOOTSTRAP: ret =  responseBootstrap(p); break;
            case RIL_REQUEST_UICC_GBA_AUTHENTICATE_NAF: ret =  responseNaf(p); break;
            case RIL_REQUEST_GET_INCOMING_COMMUNICATION_BARRING: ret =  responseString(p); break;
            case RIL_REQUEST_SET_INCOMING_COMMUNICATION_BARRING: ret =  responseVoid(p); break;
            case RIL_REQUEST_QUERY_CNAP: ret =  responseInts(p); break;
            default:
                throw new RuntimeException("Unrecognized solicited response: " + rr.mRequest);
            //break;
            }} catch (Throwable tr) {
                // Exceptions here usually mean invalid RIL responses

                Rlog.w(RILJ_LOG_TAG, rr.serialString() + "< "
                        + requestToString(rr.mRequest)
                        + " exception, possible invalid RIL response", tr);

                if (rr.mResult != null) {
                    AsyncResult.forMessage(rr.mResult, null, tr);
                    rr.mResult.sendToTarget();
                }
                return rr;
            }
        }

        if (rr.mRequest == RIL_REQUEST_SHUTDOWN) {
            this.riljLog("Response to RIL_REQUEST_SHUTDOWN received. Error is " + error + " Setting Radio State to Unavailable regardless of error.");
            this.setRadioState(RadioState.RADIO_UNAVAILABLE);
            if ("DCGS".equals("") && "1".equals(SystemProperties.get("sys.deviceOffReq", "0"))) {
                if (this.mInstanceId == 0)
                    SystemProperties.set("ril.deviceOffRes", "1");
                else if (this.mInstanceId == 1)
                    SystemProperties.set("ril.deviceOffRes2", "1");
            }
        }
        
        switch (rr.mRequest) {
            case RIL_REQUEST_ENTER_SIM_PUK:
            case RIL_REQUEST_ENTER_SIM_PUK2:
                if (mIccStatusChangedRegistrants != null) {
                    if (RILJ_LOGD) {
                        riljLog("ON enter sim puk fakeSimStatusChanged: reg count="
                                + mIccStatusChangedRegistrants.size());
                    }
                    mIccStatusChangedRegistrants.notifyRegistrants();
                }
                break;
        }

        if (error != 0) {
            switch (rr.mRequest) {
                case RIL_REQUEST_ENTER_SIM_PIN:
                case RIL_REQUEST_ENTER_SIM_PIN2:
                case RIL_REQUEST_CHANGE_SIM_PIN:
                case RIL_REQUEST_CHANGE_SIM_PIN2:
                case RIL_REQUEST_SET_FACILITY_LOCK: {
                    if (this.mIccStatusChangedRegistrants != null) {
                        this.riljLog("ON some errors fakeSimStatusChanged: reg count=" + this.mIccStatusChangedRegistrants.size());
                        this.mIccStatusChangedRegistrants.notifyRegistrants();
                    }
                    break;
                }
            }
            rr.onError(error, ret);
            return rr;
        }

        if (RILJ_LOGD) riljLog(rr.serialString() + "< " + requestToString(rr.mRequest)
            + " " + retToString(rr.mRequest, ret));

        if (rr.mResult != null) {
            AsyncResult.forMessage(rr.mResult, ret, null);
            rr.mResult.sendToTarget();
        }

        return rr;
    }

    private void invokeOemRilRequestSprd(byte key, byte value, Message response) {
        invokeOemRilRequestRaw(new byte[] { 'S', 'P', 'R', 'D', key, value }, response);
    }
}

