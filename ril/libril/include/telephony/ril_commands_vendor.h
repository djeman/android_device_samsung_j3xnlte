/* //device/libs/telephony/ril_commands.h
**
** Copyright 2006, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/
    {10000, NULL, NULL},
    {RIL_REQUEST_DIAL_EMERGENCY_CALL, dispatchDial, responseVoid}, // 10001
    {RIL_REQUEST_CALL_DEFLECTION, dispatchString, responseVoid}, // 10002
    {RIL_REQUEST_MODIFY_CALL_INITIATE, dispatchModifyCall, responseInts}, // 10003
    {RIL_REQUEST_MODIFY_CALL_CONFIRM, dispatchModifyCall, responseVoid}, // 10004
    {RIL_REQUEST_SET_VOICE_DOMAIN_PREF, dispatchInts, responseVoid}, // 10005
    {RIL_REQUEST_SAFE_MODE, dispatchInts, responseVoid}, // 10006
    {RIL_REQUEST_SET_TRANSMIT_POWER, dispatchInts, responseVoid}, // 10007
    {RIL_REQUEST_GET_CELL_BROADCAST_CONFIG, dispatchVoid, responseVoid /*sub_4084*/}, // 10008
    {RIL_REQUEST_GET_PHONEBOOK_STORAGE_INFO, dispatchInts, responseInts}, // 10009
    {RIL_REQUEST_GET_PHONEBOOK_ENTRY, dispatchSIM_IO, responseInts /*responsePhoneBook*/}, // 10010
    {RIL_REQUEST_ACCESS_PHONEBOOK_ENTRY, dispatchInts /*dispatchPhoneBook*/, responseInts}, // 10011
    {RIL_REQUEST_USIM_PB_CAPA, dispatchVoid, responseInts}, // 10012
    {RIL_REQUEST_LOCK_INFO, dispatchInts /*dispatchLockInfo*/, responseInts /*responseLockInfo*/}, // 10013
    {RIL_REQUEST_STK_SIM_INIT_EVENT, dispatchVoid, responseVoid}, // 10014
    {RIL_REQUEST_SET_PREFERRED_NETWORK_LIST, dispatchInts /*dispatchPreferredNetworkList*/, responseVoid}, // 10015
    {RIL_REQUEST_GET_PREFERRED_NETWORK_LIST, dispatchVoid, responseInts /*responsePreferredNetworkList*/}, // 10016
    {RIL_REQUEST_CHANGE_SIM_PERSO, dispatchStrings, responseInts}, // 10017
    {RIL_REQUEST_ENTER_SIM_PERSO, dispatchStrings, responseInts}, // 10018
    {RIL_REQUEST_SEND_ENCODED_USSD, dispatchInts /*dispatchEncodedUSSD*/, responseVoid}, // 10019
    {RIL_REQUEST_CDMA_SEND_SMS_EXPECT_MORE, dispatchCdmaSms, responseSMS}, // 10020
    {RIL_REQUEST_HANGUP_VT, dispatchInts, responseVoid}, // 10021
    {RIL_REQUEST_REQUEST_HOLD, dispatchVoid, responseVoid}, // 10022
    {RIL_REQUEST_SET_SIM_POWER, dispatchInts, responseInts}, // 10023
    {RIL_REQUEST_SET_LTE_BAND_MODE, dispatchInts, responseVoid}, // 10024
    {RIL_REQUEST_UICC_GBA_AUTHENTICATE_BOOTSTRAP, NULL, NULL}, // 10025
    {RIL_REQUEST_UICC_GBA_AUTHENTICATE_NAF, NULL, NULL}, // 10026
    {RIL_REQUEST_GET_INCOMING_COMMUNICATION_BARRING, dispatchStrings, responseString}, // 10027
    {RIL_REQUEST_SET_INCOMING_COMMUNICATION_BARRING, dispatchStrings, responseInts}, // 10028
    {RIL_REQUEST_QUERY_CNAP, dispatchVoid, responseInts}, // 10029
