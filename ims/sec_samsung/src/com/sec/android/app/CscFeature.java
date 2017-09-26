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

package com.sec.android.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class CscFeature {
    private static final String TAG = "CscFeature";

    public static final boolean Bool_NoTag = false;
    public static final int Int_NoTag = 0;
    public static final String Str_NoTag = "";

    private static final String OMC_MPS_FEATURE_XML = "/data/omc/others.xml";
    private static final String FEATURE_XML = "/system/csc/feature.xml";
    private static final String MPS_FEATURE_XML = "/system/csc/others.xml";

    private static CscFeature sInstance = null;

    private Hashtable<String, String> mFeatureList = new Hashtable<String, String>();
    
    private CscFeature() {
        try {
            loadFeatureFile();
        } catch (Exception ex) {
            Log.w(TAG, ex.toString());
        }
    }
    
    public static CscFeature getInstance() {
        if (CscFeature.sInstance == null) {
            CscFeature.sInstance = new CscFeature();
        }
        return CscFeature.sInstance;
    }
    
    private void loadFeatureFile() throws XmlPullParserException, IOException {
    	mFeatureList.clear();

        File featureXmlFile = new File(OMC_MPS_FEATURE_XML);
        if (!featureXmlFile.exists() || featureXmlFile.length() < 1) {
            featureXmlFile = new File(FEATURE_XML);
            if (!featureXmlFile.exists() || featureXmlFile.length() < 1) {
                featureXmlFile = new File(MPS_FEATURE_XML);
                if (!featureXmlFile.exists() || featureXmlFile.length() < 1) {
                    Log.e(TAG, "Setting file not found.");
                    return;
                }
            }
        }

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        InputStream fi = new FileInputStream(featureXmlFile);
        parser.setInput(fi, null);

        String TagName = null;
        String TagValue = null;
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
            case XmlPullParser.START_DOCUMENT:
            case XmlPullParser.END_TAG:
                break;
            case XmlPullParser.START_TAG:
                TagName = parser.getName();
                break;
            case XmlPullParser.TEXT:
                TagValue = parser.getText().trim();

                if (!mFeatureList.contains(TagName) && TagValue.length() > 0)
                    mFeatureList.put(TagName, TagValue);
                
                break;
            }
            eventType = parser.next();
        }

        if (fi != null)
            fi.close();
    }
    
    public boolean getEnableStatus(final String s) {
        try {
            return mFeatureList.get(s) != null && Boolean.parseBoolean(mFeatureList.get(s));
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean getEnableStatus(final String s, boolean val) {
        try {
            if (mFeatureList.get(s) != null) {
                val = Boolean.parseBoolean(mFeatureList.get(s));
            }
            return val;
        } catch (Exception ex) {
            return val;
        }
    }
    
    public int getInteger(final String s) {
        try {
            if (mFeatureList.get(s) != null) {
                return Integer.parseInt(mFeatureList.get(s));
            }
            return -1;
        } catch (Exception ex) {
            return -1;
        }
    }
    
    public int getInteger(final String s, int val) {
        try {
            if (mFeatureList.get(s) != null) {
                val = Integer.parseInt(mFeatureList.get(s));
            }
            return val;
        } catch (Exception ex) {
            return val;
        }
    }
    
    public String getString(final String s) {
        try {
            if (mFeatureList.get(s) != null) {
                return mFeatureList.get(s);
            }
            return "";
        } catch (Exception ex) {
            return "";
        }
    }
    
    public String getString(final String s, String val) {
        try {
            if (mFeatureList.get(s) != null) {
                val = mFeatureList.get(s);
            }
            return val;
        } catch (Exception ex) {
            return val;
        }
    }
}

