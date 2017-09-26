package com.sec.android.emergencymode;

import android.content.Context;

public class EmergencyManager
{
    private static EmergencyManager sInstance = null;
    
    public static EmergencyManager getInstance(final Context context) {
        return sInstance;
    }
    
    public static boolean isEmergencyMode(final Context context) {
        return false;
    }
    
    public boolean checkModeType(final int n) {
        return false;
    }
    
    public boolean isEmergencyMode() {
        return false;
    }
}

