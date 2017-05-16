package com.android.internal.telephony.uicc;

import android.util.Log;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.IccUtils;
import java.io.UnsupportedEncodingException;

public class SimPBEntryResult
{
    private static final int GSM_TEXT_ENC_ASCII = 1;
    private static final int GSM_TEXT_ENC_GSM7BIT = 2;
    private static final int GSM_TEXT_ENC_HEX = 4;
    private static final int GSM_TEXT_ENC_UCS2 = 3;
    public static final int INDEX_ANR = 1;
    public static final int INDEX_ANRA = 2;
    public static final int INDEX_ANRB = 3;
    public static final int INDEX_ANRC = 4;
    public static final int INDEX_EMAIL = 2;
    public static final int INDEX_NAME = 0;
    public static final int INDEX_NUMBER = 0;
    public static final int INDEX_SNE = 1;
    static final String LOG_TAG = "GSM";
    public static final int NUM_OF_ALPHA = 3;
    public static final int NUM_OF_NUMBER = 5;
    public String[] alphaTags;
    public int[] dataTypeAlphas;
    public int[] dataTypeNumbers;
    public int[] lengthAlphas;
    public int[] lengthNumbers;
    public int nextIndex;
    public String[] numbers;
    public int recordIndex;
    
    public SimPBEntryResult(final int[] array, final int[] array2, final String[] array3, final int[] array4, final int[] array5, final String[] array6, final int recordIndex, final int nextIndex) {
        this.lengthAlphas = new int[3];
        this.dataTypeAlphas = new int[3];
        this.alphaTags = new String[3];
        this.lengthNumbers = new int[5];
        this.dataTypeNumbers = new int[5];
        this.numbers = new String[5];
        for (int i = 0; i < 3; ++i) {
            this.lengthAlphas[i] = array[i];
            this.dataTypeAlphas[i] = array2[i];
            final byte[] hexStringToBytes = IccUtils.hexStringToBytes(array3[i]);
            switch (array2[i]) {
                default: {
                    this.alphaTags[i] = "";
                    Log.i("GSM", "SimPBEntryResult: default Unknown type");
                    break;
                }
                case 1: {
                    this.alphaTags[i] = "";
                    Log.i("GSM", "Not supported encoding type");
                    break;
                }
                case 2: {
                    this.alphaTags[i] = GsmAlphabet.gsm8BitUnpackedToString(hexStringToBytes, 0, array[i]);
                    break;
                }
                case 3: {
                    try {
                        this.alphaTags[i] = new String(hexStringToBytes, 0, array[i], "UTF-16");
                    }
                    catch (UnsupportedEncodingException ex) {
                        this.alphaTags[i] = "";
                        Log.i("GSM", "SimPBEntryResult - implausible UnsupportedEncodingException");
                    }
                    break;
                }
                case 4: {
                    this.alphaTags[i] = "";
                    Log.i("GSM", "Not supported encoding type");
                    break;
                }
            }
        }
        for (int j = 0; j < 5; ++j) {
            this.lengthNumbers[j] = array4[j];
            this.dataTypeNumbers[j] = array5[j];
            if (array4[j] == 0 || array6[j] == null) {
                this.numbers[j] = "";
            }
            else {
                this.numbers[j] = array6[j];
            }
        }
        this.recordIndex = recordIndex;
        this.nextIndex = nextIndex;
    }
}

