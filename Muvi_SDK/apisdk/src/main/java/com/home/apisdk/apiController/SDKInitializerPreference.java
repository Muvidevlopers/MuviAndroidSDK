package com.home.apisdk.apiController;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MUVI on 8/31/2017.
 */

public class SDKInitializerPreference {

    private static SDKInitializerPreference sdkInitializerPreference;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String PREFERENCE_KEY = "sdkinitializer_pref";


    private static final String PACKAGE_NAME = "pkgname";
    private static final String HASH_KEY = "hashkey";
    private static final String TIME = "server_time";

    private SDKInitializerPreference(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SDKInitializerPreference getSdkInitializerPreference(Context mContext) {

        if (sdkInitializerPreference == null) {
            return new SDKInitializerPreference(mContext);
        }
        return sdkInitializerPreference;
    }

    public String getPackage_nameFromPreference() {
        return mSharedPreferences.getString(PACKAGE_NAME, "");
    }

    public String getHash_KeyFromPreference() {
        return mSharedPreferences.getString(HASH_KEY, "");
    }

    public String getTimeFromPreference() {
        return mSharedPreferences.getString(TIME, "");
    }

    public void setPackage_namePref(String package_name) {
        mEditor.putString(PACKAGE_NAME, package_name);
        mEditor.commit();
    }

    public void setHash_KeyPref(String hash_Key) {
        mEditor.putString(HASH_KEY, hash_Key);
        mEditor.commit();
    }

    public void setTimePref(String time) {
        mEditor.putString(TIME, time);
        mEditor.commit();
    }

    public void clearSDKPref() {

        mEditor.clear();
        mEditor.commit();
        mEditor.apply();

    }
}
