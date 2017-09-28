/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.samsung.android.telephony;

import android.content.Context;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.util.List;

public class MultiSimManager {
    private static final String TAG = "MultiSimManager";

    private static final int SIMSLOT_COUNT = TelephonyManager.getDefault().getPhoneCount();
    private static SubscriptionManager SM = new SubscriptionManager(null);

    public static int getEnabledSimCount(Context ctx) {
        List<SubscriptionInfo> activeSubList = SM.getActiveSubscriptionInfoList();
        int count = 0;

        if (activeSubList != null) {
            for (SubscriptionInfo subInfo : activeSubList) {
                TelephonyManager.getDefault();
                if (!"0".equals(TelephonyManager.getTelephonyProperty(subInfo.getSimSlotIndex(), "ril.ICC_TYPE", "0"))) {
                    ++count;
                }
            }
        }

        Rlog.i(TAG, "return getEnabledSimCount count = " + count);
        return count;
    }

    public static int getSimSlotCount() {
        return SIMSLOT_COUNT;
    }
}

