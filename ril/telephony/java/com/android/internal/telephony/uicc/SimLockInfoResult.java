package com.android.internal.telephony.uicc;

import android.util.Log;

public class SimLockInfoResult
{
    private static final int LOCK_ACL = 11;
    private static final int LOCK_CORP_PERS = 8;
    private static final int LOCK_FD = 4;
    private static final int LOCK_NETWORK_PERS = 5;
    private static final int LOCK_NETWORK_SUBSET_PERS = 6;
    private static final int LOCK_PH_FSIM = 2;
    private static final int LOCK_PH_SIM = 1;
    private static final int LOCK_PIN2 = 9;
    private static final int LOCK_PUK2 = 10;
    private static final int LOCK_READY = 0;
    private static final int LOCK_SIM = 3;
    private static final int LOCK_SP_PERS = 7;
    static final String LOG_TAG = "SimLockInfoResult";
    private static final int NOT_NEED = 0;
    private static final int NO_SIM = 128;
    private static final int PERM_BLOCKED = 5;
    public static final int PIN = 1;
    public static final int PIN2 = 3;
    private static final int PIN2_DISABLE = 6;
    public static final int PUK = 2;
    public static final int PUK2 = 4;
    static int Pin2_Retry;
    static int Pin_Retry;
    static int Puk2_Retry;
    static int Puk_Retry;
    static int isPermBlocked;
    static int lockPin2Key;
    static int lockPinKey;
    int lockKey;
    int lockType;
    int numRetry;
    int num_lock_type;
    
    static {
        SimLockInfoResult.lockPinKey = 0;
        SimLockInfoResult.lockPin2Key = 0;
        SimLockInfoResult.Pin_Retry = 0;
        SimLockInfoResult.Pin2_Retry = 0;
        SimLockInfoResult.Puk_Retry = 0;
        SimLockInfoResult.Puk2_Retry = 0;
        SimLockInfoResult.isPermBlocked = 0;
    }
    
    public SimLockInfoResult(final int num_lock_type, final int lockType, final int n, final int puk2_Retry) {
        this.num_lock_type = 0;
        this.lockType = 0;
        this.lockKey = 0;
        this.numRetry = 0;
        this.num_lock_type = num_lock_type;
        this.lockType = lockType;
        this.lockKey = n;
        this.numRetry = puk2_Retry;
        Log.i("SimLockInfoResult", "num:" + num_lock_type + ", lockType:" + lockType + ", lock_key:" + n + ", numRetry:" + puk2_Retry);
        if (lockType == 10) {
            SimLockInfoResult.Puk2_Retry = puk2_Retry;
            return;
        }
        switch (n) {
            default: {}
            case 0: {
                SimLockInfoResult.Pin_Retry = puk2_Retry;
                SimLockInfoResult.lockPinKey = n;
                Log.i("SimLockInfoResult", "NOT_NEED numRetry: " + SimLockInfoResult.Pin_Retry);
            }
            case 1: {
                SimLockInfoResult.Pin_Retry = puk2_Retry;
                SimLockInfoResult.lockPinKey = n;
                Log.i("SimLockInfoResult", "PIN numRetry: " + SimLockInfoResult.Pin_Retry);
            }
            case 5: {
                if (lockType == 3) {
                    SimLockInfoResult.Pin_Retry = 0;
                    SimLockInfoResult.Puk_Retry = 0;
                    SimLockInfoResult.isPermBlocked = 1;
                    SimLockInfoResult.lockPinKey = n;
                }
                else if (lockType == 9) {
                    SimLockInfoResult.Pin2_Retry = 0;
                    SimLockInfoResult.Puk2_Retry = 0;
                    SimLockInfoResult.lockPin2Key = n;
                }
                Log.i("SimLockInfoResult", "Permernet blocked");
            }
            case 2: {
                SimLockInfoResult.Puk_Retry = puk2_Retry;
                SimLockInfoResult.lockPinKey = n;
                Log.i("SimLockInfoResult", "PUK numRetry: " + SimLockInfoResult.Puk_Retry);
            }
            case 3:
            case 6: {
                SimLockInfoResult.Pin2_Retry = puk2_Retry;
                SimLockInfoResult.lockPin2Key = n;
                Log.i("SimLockInfoResult", "PIN2 numRetry: " + SimLockInfoResult.Pin2_Retry);
            }
            case 4: {
                SimLockInfoResult.Puk2_Retry = puk2_Retry;
                SimLockInfoResult.lockPin2Key = n;
                Log.i("SimLockInfoResult", "PUK2 numRetry: " + SimLockInfoResult.Puk2_Retry);
            }
        }
    }
    
    public int getLockPin2Key() {
        return SimLockInfoResult.lockPin2Key;
    }
    
    public int getLockPinKey() {
        return SimLockInfoResult.lockPinKey;
    }
    
    public int getPin2Retry() {
        return SimLockInfoResult.Pin2_Retry;
    }
    
    public int getPinRetry() {
        return SimLockInfoResult.Pin_Retry;
    }
    
    public int getPuk2Retry() {
        return SimLockInfoResult.Puk2_Retry;
    }
    
    public int getPukRetry() {
        return SimLockInfoResult.Puk_Retry;
    }
    
    public int isSimBlocked() {
        return SimLockInfoResult.isPermBlocked;
    }
    
    public void setLockInfoResult(final int pin_Retry, final int puk_Retry, final int pin2_Retry, final int puk2_Retry) {
        Log.i("SimLockInfoResult", "Pin_Retry:" + pin_Retry + ", Puk_Retry:" + puk_Retry + ", Pin2_Retry:" + pin2_Retry + ", Puk2_Retry:" + puk2_Retry);
        if (pin_Retry != -1) {
            SimLockInfoResult.Pin_Retry = pin_Retry;
        }
        if (puk_Retry != -1) {
            SimLockInfoResult.Puk_Retry = puk_Retry;
        }
        if (pin2_Retry != -1) {
            SimLockInfoResult.Pin2_Retry = pin2_Retry;
        }
        if (puk2_Retry != -1) {
            SimLockInfoResult.Puk2_Retry = puk2_Retry;
        }
    }
    
    public void setLockInfoResult(final int pin_Retry, final int puk_Retry, final int pin2_Retry, final int puk2_Retry, final int lockPinKey, final int lockPin2Key) {
        Log.i("SimLockInfoResult", "Pin_Retry:" + pin_Retry + ", Puk_Retry:" + puk_Retry + ", Pin2_Retry:" + pin2_Retry + ", Puk2_Retry:" + puk2_Retry + ", lockKey:" + lockPinKey + ", lockKey2:" + lockPin2Key);
        if (pin_Retry != -1) {
            SimLockInfoResult.Pin_Retry = pin_Retry;
        }
        if (puk_Retry != -1) {
            SimLockInfoResult.Puk_Retry = puk_Retry;
        }
        if (pin2_Retry != -1) {
            SimLockInfoResult.Pin2_Retry = pin2_Retry;
        }
        if (puk2_Retry != -1) {
            SimLockInfoResult.Puk2_Retry = puk2_Retry;
        }
        SimLockInfoResult.lockPinKey = lockPinKey;
        SimLockInfoResult.lockPin2Key = lockPin2Key;
    }
    
    void setLockInfoResult(final SimLockInfoResult simLockInfoResult) {
        this.num_lock_type = simLockInfoResult.num_lock_type;
        this.lockType = simLockInfoResult.lockType;
        this.lockKey = simLockInfoResult.lockKey;
        this.numRetry = simLockInfoResult.numRetry;
        Log.i("SimLockInfoResult", "num:" + this.num_lock_type + ", lockType:" + this.lockType + ", lock_key:" + this.lockKey + ", numRetry:" + this.numRetry);
        if (simLockInfoResult.lockType == 10) {
            SimLockInfoResult.Puk2_Retry = simLockInfoResult.numRetry;
            return;
        }
        switch (simLockInfoResult.lockKey) {
            default: {}
            case 0: {
                SimLockInfoResult.Pin_Retry = simLockInfoResult.numRetry;
                SimLockInfoResult.lockPinKey = simLockInfoResult.lockKey;
                Log.i("SimLockInfoResult", "NOT_NEED numRetry: " + SimLockInfoResult.Pin_Retry);
            }
            case 1: {
                SimLockInfoResult.Pin_Retry = simLockInfoResult.numRetry;
                SimLockInfoResult.lockPinKey = simLockInfoResult.lockKey;
                Log.i("SimLockInfoResult", "PIN numRetry: " + SimLockInfoResult.Pin_Retry);
            }
            case 5: {
                if (simLockInfoResult.lockType == 3) {
                    SimLockInfoResult.Pin_Retry = 0;
                    SimLockInfoResult.Puk_Retry = 0;
                    SimLockInfoResult.isPermBlocked = 1;
                    SimLockInfoResult.lockPinKey = simLockInfoResult.lockKey;
                }
                else if (simLockInfoResult.lockType == 9) {
                    SimLockInfoResult.Pin2_Retry = 0;
                    SimLockInfoResult.Puk2_Retry = 0;
                    SimLockInfoResult.lockPin2Key = simLockInfoResult.lockKey;
                }
                Log.i("SimLockInfoResult", "Permernet blocked");
            }
            case 2: {
                SimLockInfoResult.Puk_Retry = simLockInfoResult.numRetry;
                SimLockInfoResult.lockPinKey = simLockInfoResult.lockKey;
                Log.i("SimLockInfoResult", "PUK numRetry: " + SimLockInfoResult.Puk_Retry);
            }
            case 3:
            case 6: {
                SimLockInfoResult.lockPin2Key = simLockInfoResult.lockKey;
                SimLockInfoResult.Pin2_Retry = simLockInfoResult.numRetry;
                Log.i("SimLockInfoResult", "PIN2 numRetry: " + SimLockInfoResult.Pin2_Retry);
            }
            case 4: {
                SimLockInfoResult.Puk2_Retry = simLockInfoResult.numRetry;
                SimLockInfoResult.lockPin2Key = simLockInfoResult.lockKey;
                Log.i("SimLockInfoResult", "PUK2 numRetry: " + SimLockInfoResult.Puk2_Retry);
            }
        }
    }
}

