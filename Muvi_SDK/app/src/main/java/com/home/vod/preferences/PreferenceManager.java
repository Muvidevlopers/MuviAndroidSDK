package com.home.vod.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muvi on 20/6/17.
 */

public class PreferenceManager {

    private static  PreferenceManager preferenceManager;
    private static final String PREFERENCE_KEY = "vod_pref";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private  final String PREFS_LOGGEDIN_ID_KEY = "pref_loggedin_id_key";
    private final String PREFS_LOGIN_EMAIL_ID_KEY = "pref_login_email_id_key";
    private final String PREF_COUNTRY_CODE_KEY = "countryCode";
    private final String IS_LOGIN_PREF_KEY = "VishwamisLogin";
    private final String GENRE_ARRAY_PREF_KEY = "genreArray";
    public  final String GENRE_VALUES_ARRAY_PREF_KEY = "genreValueArray";
    public final String LANGUAGE_LIST_PREF = "VishwamLanguageListPref";
    public  final String PREFS_LOGIN_ISSUBSCRIBED_KEY = "isSubscribed";
    public  final String PREFS_LOGGEDIN_KEY = "pref_loged_in";
    public  final String PREFS_LOGGEDIN_PASSWORD_KEY = "password";
    public  final String PREFS_LOGIN_DISPLAY_NAME_KEY = "displayName";
    public  final String PREFS_LOGIN_DISPLAY_PHONE_KEY = "mobilenumber";
    public  final String PREFS_LOGIN_PROFILE_IMAGE_KEY = "loginProfImg";
    public  final String PREFS_LOGIN_HISTORYID_KEY = "loginHistId";
    public  final String PREFS_LOGIN_DATE = "date";


    private PreferenceManager(Context mContext){
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PreferenceManager getPreferenceManager(Context mContext){
        if(preferenceManager==null){
            return new PreferenceManager(mContext);
        }
        return preferenceManager;
    }




    public String getUseridFromPref() {
        return mSharedPreferences.getString(PREFS_LOGGEDIN_ID_KEY, null);
    }

    public String getEmailIdFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_EMAIL_ID_KEY, null);
    }

    public void setUserIdToPref(String loginId) {
        mEditor.putString(PREFS_LOGGEDIN_ID_KEY,loginId);
        mEditor.commit();
    }

    public void setEmailIdToPref(String emailId) {
        mEditor.putString(PREFS_LOGIN_EMAIL_ID_KEY,emailId);
        mEditor.commit();
    }


    public String getCountryCodeFromPref() {
        return mSharedPreferences.getString(PREF_COUNTRY_CODE_KEY, null);
    }
    public void setCountryCodeToPref(String code) {
        mEditor.putString(PREF_COUNTRY_CODE_KEY,code);
        mEditor.commit();
    }


    public int getLoginFeatureFromPref() {
        return mSharedPreferences.getInt(IS_LOGIN_PREF_KEY, 0);
    }
    public void setLoginFeatureToPref(int status) {
        mEditor.putInt(IS_LOGIN_PREF_KEY,status);
        mEditor.commit();
    }



    public String getGenreArrayFromPref() {
        return mSharedPreferences.getString(GENRE_ARRAY_PREF_KEY, null);
    }
    public void setGenreArrayToPref(String status) {
        mEditor.putString(GENRE_ARRAY_PREF_KEY,status);
        mEditor.commit();
    }


    public String getGenreValuesArrayFromPref() {
        return mSharedPreferences.getString(GENRE_VALUES_ARRAY_PREF_KEY, null);
    }
    public void setGenreValuesArrayToPref(String status) {
        mEditor.putString(GENRE_VALUES_ARRAY_PREF_KEY,status);
        mEditor.commit();
    }

    public String getLanguageListFromPref() {
        return mSharedPreferences.getString(LANGUAGE_LIST_PREF, "0");
    }
    public void setLanguageListToPref(String lang) {
        mEditor.putString(LANGUAGE_LIST_PREF,lang);
        mEditor.commit();
    }


    public String getIsSubscribedFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_ISSUBSCRIBED_KEY, "0");
    }
    public void setIsSubscribedToPref(String isSubscribed) {
        mEditor.putString(PREFS_LOGIN_ISSUBSCRIBED_KEY,isSubscribed);
        mEditor.commit();
    }



    public String getLoginStatusFromPref() {
        return mSharedPreferences.getString(PREFS_LOGGEDIN_KEY, null);
    }
    public void setLogInStatusToPref(String logIn) {
        mEditor.putString(PREFS_LOGGEDIN_KEY,logIn);
        mEditor.commit();
    }

    public String getPwdFromPref() {
        return mSharedPreferences.getString(PREFS_LOGGEDIN_PASSWORD_KEY, "");
    }
    public void setPwdToPref(String pwd) {
        mEditor.putString(PREFS_LOGGEDIN_PASSWORD_KEY,pwd);
        mEditor.commit();
    }

    public String getDispNameFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_DISPLAY_NAME_KEY, "");
    }
    public void setDispNameToPref(String name) {
        mEditor.putString(PREFS_LOGIN_DISPLAY_NAME_KEY,name);
        mEditor.commit();
    }

    public String getDispPhoneFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_DISPLAY_PHONE_KEY, "");
    }
    public void setDispPhoneToPref(String phone) {
        mEditor.putString(PREFS_LOGIN_DISPLAY_PHONE_KEY,phone);
        mEditor.commit();
    }

    public String getLoginProfImgFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_PROFILE_IMAGE_KEY, "");
    }
    public void setLoginProfImgoPref(String name) {
        mEditor.putString(PREFS_LOGIN_PROFILE_IMAGE_KEY,name);
        mEditor.commit();
    }

    public String getLoginHistIdFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_HISTORYID_KEY, "");
    }
    public void setLoginHistIdPref(String name) {
        mEditor.putString(PREFS_LOGIN_HISTORYID_KEY,name);
        mEditor.commit();
    }

    public String getLoginDateFromPref() {
        return mSharedPreferences.getString(PREFS_LOGIN_DATE, null);
    }
    public void setLoginDatePref(String date) {
        mEditor.putString(PREFS_LOGIN_DATE,date);
        mEditor.commit();
    }



    public void clearLoginPref(){
        mEditor.remove(PREFS_LOGGEDIN_KEY);
        mEditor.remove(PREFS_LOGGEDIN_ID_KEY);
        mEditor.remove(PREFS_LOGGEDIN_PASSWORD_KEY);
        mEditor.remove(PREFS_LOGIN_EMAIL_ID_KEY);
        mEditor.remove(PREFS_LOGIN_DISPLAY_NAME_KEY);
        mEditor.remove(PREFS_LOGIN_PROFILE_IMAGE_KEY);
        mEditor.remove(PREFS_LOGIN_ISSUBSCRIBED_KEY);
        mEditor.remove(PREFS_LOGIN_HISTORYID_KEY);
        mEditor.apply();
        mEditor.commit();
    }

}
