package com.android.internal.telephony;

public class PreferredNetworkListInfo
{
    public int mGsmAct;
    public int mGsmCompactAct;
    public int mIndex;
    public int mMode;
    public String mOperator;
    public String mPlmn;
    public int mUtranAct;
    
    public PreferredNetworkListInfo() {
        this.mIndex = 0;
        this.mOperator = "";
        this.mPlmn = "";
        this.mGsmAct = 0;
        this.mGsmCompactAct = 0;
        this.mUtranAct = 0;
        this.mMode = 0;
    }
    
    public PreferredNetworkListInfo(final int mIndex, final String mOperator, final String mPlmn, final int mGsmAct, final int mGsmCompactAct, final int mUtranAct, final int mMode) {
        this.mIndex = mIndex;
        this.mOperator = mOperator;
        this.mPlmn = mPlmn;
        this.mGsmAct = mGsmAct;
        this.mGsmCompactAct = mGsmCompactAct;
        this.mUtranAct = mUtranAct;
        this.mMode = mMode;
    }
    
    public PreferredNetworkListInfo(final PreferredNetworkListInfo preferredNetworkListInfo) {
        this.copyFrom(preferredNetworkListInfo);
    }
    
    protected void copyFrom(final PreferredNetworkListInfo preferredNetworkListInfo) {
        this.mIndex = preferredNetworkListInfo.mIndex;
        this.mOperator = preferredNetworkListInfo.mOperator;
        this.mPlmn = preferredNetworkListInfo.mPlmn;
        this.mGsmAct = preferredNetworkListInfo.mGsmAct;
        this.mGsmCompactAct = preferredNetworkListInfo.mGsmCompactAct;
        this.mUtranAct = preferredNetworkListInfo.mUtranAct;
        this.mMode = preferredNetworkListInfo.mMode;
    }
    
    @Override
    public String toString() {
        return "PreferredNetworkListInfo: { index: " + this.mIndex + ", operator: " + this.mOperator + ", plmn: " + this.mPlmn + ", gsmAct: " + this.mGsmAct + ", gsmCompactAct: " + this.mGsmCompactAct + ", utranAct: " + this.mUtranAct + ", mode: " + this.mMode + " }";
    }
}

