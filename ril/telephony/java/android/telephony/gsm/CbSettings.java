package android.telephony.gsm;

public class CbSettings
{
    public enum CB_SETTINGS_LANG_TYPE
    {
        MSGSMS_CB_LANGUAGE_ARABIC(34), 
        MSGSMS_CB_LANGUAGE_CZECH(32), 
        MSGSMS_CB_LANGUAGE_DANISH(7), 
        MSGSMS_CB_LANGUAGE_DUMMY(255), 
        MSGSMS_CB_LANGUAGE_DUTCH(5), 
        MSGSMS_CB_LANGUAGE_ENGLISH(1), 
        MSGSMS_CB_LANGUAGE_FINNISH(9), 
        MSGSMS_CB_LANGUAGE_FRENCH(3), 
        MSGSMS_CB_LANGUAGE_GREEK(11), 
        MSGSMS_CB_LANGUAGE_HEBREW(33), 
        MSGSMS_CB_LANGUAGE_HUNGARIAN(13), 
        MSGSMS_CB_LANGUAGE_ICELANDIC(36), 
        MSGSMS_CB_LANGUAGE_ITALIAN(2), 
        MSGSMS_CB_LANGUAGE_NORWEGIAN(10), 
        MSGSMS_CB_LANGUAGE_POLISH(14), 
        MSGSMS_CB_LANGUAGE_PORTUGUESE(8), 
        MSGSMS_CB_LANGUAGE_RESERVED_22(37), 
        MSGSMS_CB_LANGUAGE_RESERVED_23(38), 
        MSGSMS_CB_LANGUAGE_RESERVED_24(39), 
        MSGSMS_CB_LANGUAGE_RUSSIAN(35), 
        MSGSMS_CB_LANGUAGE_SPANISH(4), 
        MSGSMS_CB_LANGUAGE_SWEDISH(6), 
        MSGSMS_CB_LANGUAGE_TURKISH(12), 
        MSGSMS_CB_LANGUAGE_UNSPECIFIED(15), 
        SGSMS_CB_LANGUAGE_GERMAN(0);
        
        private int Value;
        
        private CB_SETTINGS_LANG_TYPE(final int value) {
            this.Value = value;
        }
        
        public int getLanguage() {
            return this.Value;
        }
        
        public void setLanguage(final int value) {
            this.Value = value;
        }
    }
}

