package android.telephony.gsm;

public class CbConfig
{
    public boolean bCBEnabled;
    public short[] msgIDs;
    public int msgIdCount;
    public char msgIdMaxCount;
    public char selectedId;
    
    @Override
    public String toString() {
        return super.toString() + "CB ENABLED: " + this.bCBEnabled + "selectedId" + (int)this.selectedId + " msgIdMaxCount:" + (int)this.msgIdMaxCount + "msgIdCount" + this.msgIdCount;
    }
}

