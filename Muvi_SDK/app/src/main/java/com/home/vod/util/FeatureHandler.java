package com.home.vod.util;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;

/**
 * Created by MUVI on 08-Jan-18.
 */

public class FeatureHandler {

    private static SharedPreferences fetureSharedPref;
    private static FeatureHandler featureHandler;
    private SharedPreferences.Editor editor;
    Context context;

    public static final String PrefName = "FEATURE_HANDLER";

    public static final String IS_MYLIBRARY = "isMylibrary";
    public static final String IS_RATING = "isRating";
    public static final String IS_LOGIN = "is_login";
    public static final String HAS_FAVOURITE = "has_favourite";
    public static final String SIGNUP_STEP = "signup_step";
    public static final String IS_RESTRICTIVE_DEVICE = "isRestrictDevice";
    public static final String IS_STREAMING_RESTRICTION = "is_streaming_restriction";
    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";
    public static final String CHROMECAST = "chromecast";
    public static final String IS_OFFLINE = "is_offline";
    public static final String IS_PLAYLIST = "isPlayList";
    public static final String IS_QUEUE = "isQueue";

    public static final String IS_SUBTITLE = "isSubtitle";
    public static final String IS_RESOLUTION = "isResolution";
    public static final String IS_FORGOTPASSWORD = "isForgotPassword";
    public static final String IS_PURCHASEHISTORY = "isPurchaseHistory";
    public static final String IS_FILTER = "isFilter";
    public static final String IS_SEARCH = "isSearch";
    public static final String IS_DEVICEMANGEMENT = "isDeviceMangenment";
    public static final String IS_APPNOTIFICATION = "isAppNotification";
    public static final String WATCH_HISTORY = "watch_history";


    public static final String DEFAULT_IS_WATCH_HISTORY = "0";
    public static final String DEFAULT_IS_MYLIBRARY = "0";
    public static final String DEFAULT_IS_RATING = "0";
    public static final String DEFAULT_IS_LOGIN = "1";
    public static final String DEFAULT_HAS_FAVOURITE = "0";
    public static final String DEFAULT_SIGNUP_STEP = "0";
    public static final String DEFAULT_IS_RESTRICTIVE_DEVICE = "0";
    public static final String DEFAULT_IS_STREAMING_RESTRICTION = "0";
    public static final String DEFAULT_FACEBOOK = "0";
    public static final String DEFAULT_GOOGLE = "0";
    public static final String DEFAULT_CHROMECAST = "0";
    public static final String DEFAULT_IS_OFFLINE = "0";
    public static final String DEFAULT_IS_PLAYLIST = "0";
    public static final String DEFAULT_IS_QUEUE = "0";

    public static final String DEFAULT_IS_SUBTITLE = "1";
    public static final String DEFAULT_IS_RESOLUTION = "1";
    public static final String DEFAULT_IS_FORGOTPASSWORD = "1";
    public static final String DEFAULT_IS_PURCHASEHISTORY = "1";
    public static final String DEFAULT_IS_FILTER = "1";
    public static final String DEFAULT_IS_SEARCH = "1";
    public static final String DEFAULT_IS_DEVICEMANGEMENT = "1";
    public static final String DEFAULT_IS_APPNOTIFICATION = "1";

    public FeatureHandler(Context context) {
        fetureSharedPref = context.getSharedPreferences(PrefName,Context.MODE_PRIVATE);
        editor = fetureSharedPref.edit();
    }
    public static FeatureHandler getFeaturePreference(Context mContext) {
        if (featureHandler == null) {
            return new FeatureHandler(mContext);
        }
        return featureHandler;
    }


    public void setFeatureFlag(String key , String value){
        editor.putString(key,value);
        editor.commit();

    }

    public void setDefaultFeaturePref(String response){
        try{

            /**
             * Feature status which are not coming from CMS .
             */
            setFeatureFlag( IS_SUBTITLE ,DEFAULT_IS_SUBTITLE);
            setFeatureFlag( IS_RESOLUTION ,DEFAULT_IS_RESOLUTION);
            setFeatureFlag( IS_PURCHASEHISTORY ,DEFAULT_IS_PURCHASEHISTORY);
            setFeatureFlag( IS_FILTER ,DEFAULT_IS_FILTER);
            setFeatureFlag( IS_SEARCH , DEFAULT_IS_SEARCH);
            setFeatureFlag( IS_APPNOTIFICATION ,DEFAULT_IS_APPNOTIFICATION);


            /**
             * Feature status which are coming from CMS .
             */

            JSONObject myJson1 = new JSONObject(response);

            if(myJson1.has("is_login") && myJson1.optString("is_login").trim() != null && !myJson1.optString("is_login").trim().isEmpty() && !myJson1.optString("is_login").trim().equals("null") && !myJson1.optString("is_login").trim().matches("")) {
                setFeatureFlag( IS_LOGIN , (myJson1.optString("is_login")));
            }else{
                setFeatureFlag( IS_LOGIN , DEFAULT_IS_LOGIN);
            }

            if(myJson1.has("isMylibrary") && myJson1.optString("isMylibrary").trim() != null && !myJson1.optString("isMylibrary").trim().isEmpty() && !myJson1.optString("isMylibrary").trim().equals("null") && !myJson1.optString("isMylibrary").trim().matches("")) {
                setFeatureFlag( IS_MYLIBRARY , (myJson1.optString("isMylibrary")));
            }else{
                setFeatureFlag( IS_MYLIBRARY ,DEFAULT_IS_MYLIBRARY);
            }
            if(myJson1.has("watch_history") && myJson1.optString("watch_history").trim() != null && !myJson1.optString("watch_history").trim().isEmpty() && !myJson1.optString("watch_history").trim().equals("null") && !myJson1.optString("watch_history").trim().matches("")) {
                setFeatureFlag( WATCH_HISTORY , (myJson1.optString("watch_history")));
            }else{
                setFeatureFlag( WATCH_HISTORY ,DEFAULT_IS_WATCH_HISTORY);
            }

            if(myJson1.has("signup_step") && myJson1.optString("signup_step").trim() != null && !myJson1.optString("signup_step").trim().isEmpty() && !myJson1.optString("signup_step").trim().equals("null") && !myJson1.optString("signup_step").trim().matches("")) {
                setFeatureFlag( SIGNUP_STEP , (myJson1.optString("signup_step")));
            }else {
                setFeatureFlag( SIGNUP_STEP , DEFAULT_SIGNUP_STEP);
            }

            if(myJson1.has("has_favourite") && myJson1.optString("has_favourite").trim() != null && !myJson1.optString("has_favourite").trim().isEmpty() && !myJson1.optString("has_favourite").trim().equals("null") && !myJson1.optString("has_favourite").trim().matches("")) {
                setFeatureFlag( HAS_FAVOURITE , (myJson1.optString("has_favourite")));
            }else {
                setFeatureFlag( HAS_FAVOURITE , DEFAULT_HAS_FAVOURITE);
            }

            if(myJson1.has("isRating") && myJson1.optString("isRating").trim() != null && !myJson1.optString("isRating").trim().isEmpty() && !myJson1.optString("isRating").trim().equals("null") && !myJson1.optString("isRating").trim().matches("")) {
                setFeatureFlag( IS_RATING , (myJson1.optString("isRating")));
            }else{
                setFeatureFlag( IS_RATING , DEFAULT_IS_RATING);
            }

            if(myJson1.has("isRestrictDevice") && myJson1.optString("isRestrictDevice").trim() != null && !myJson1.optString("isRestrictDevice").trim().isEmpty() && !myJson1.optString("isRestrictDevice").trim().equals("null") && !myJson1.optString("isRestrictDevice").trim().matches("")) {
                setFeatureFlag( IS_RESTRICTIVE_DEVICE , (myJson1.optString("isRestrictDevice")));
            }else{
                setFeatureFlag( IS_RESTRICTIVE_DEVICE , DEFAULT_IS_RESTRICTIVE_DEVICE);
            }

            if(myJson1.has("chromecast") && myJson1.optString("chromecast").trim() != null && !myJson1.optString("chromecast").trim().isEmpty() && !myJson1.optString("chromecast").trim().equals("null") && !myJson1.optString("chromecast").trim().matches("")) {
                setFeatureFlag( CHROMECAST , (myJson1.optString("chromecast")));
            }else {
                setFeatureFlag( CHROMECAST ,DEFAULT_CHROMECAST);
            }

            if(myJson1.has("is_streaming_restriction") && myJson1.optString("is_streaming_restriction").trim() != null && !myJson1.optString("is_streaming_restriction").trim().isEmpty() && !myJson1.optString("is_streaming_restriction").trim().equals("null") && !myJson1.optString("is_streaming_restriction").trim().matches("")) {
                setFeatureFlag( IS_STREAMING_RESTRICTION , (myJson1.optString("is_streaming_restriction")));
            }else{
                setFeatureFlag( IS_STREAMING_RESTRICTION ,DEFAULT_IS_STREAMING_RESTRICTION);
            }

            if(myJson1.has("is_offline") && myJson1.optString("is_offline").trim() != null && !myJson1.optString("is_offline").trim().isEmpty() && !myJson1.optString("is_offline").trim().equals("null") && !myJson1.optString("is_offline").trim().matches("")) {
                setFeatureFlag( IS_OFFLINE , (myJson1.optString("is_offline")));
            }else {
                setFeatureFlag( IS_OFFLINE ,DEFAULT_IS_OFFLINE);
            }

            if(myJson1.has("isPlayList") && myJson1.optString("isPlayList").trim() != null && !myJson1.optString("isPlayList").trim().isEmpty() && !myJson1.optString("isPlayList").trim().equals("null") && !myJson1.optString("isPlayList").trim().matches("")) {
                setFeatureFlag( IS_PLAYLIST , (myJson1.optString("isPlayList")));
            }else{
                setFeatureFlag( IS_PLAYLIST , DEFAULT_IS_PLAYLIST);
            }

            if(myJson1.has("isQueue") && myJson1.optString("isQueue").trim() != null && !myJson1.optString("isQueue").trim().isEmpty() && !myJson1.optString("isQueue").trim().equals("null") && !myJson1.optString("isQueue").trim().matches("")) {
                setFeatureFlag( IS_QUEUE , (myJson1.optString("isQueue")));
            }else {
                setFeatureFlag( IS_QUEUE , DEFAULT_IS_QUEUE);
            }

            if(myJson1.has("facebook") && myJson1.optString("facebook").trim() != null && !myJson1.optString("facebook").trim().isEmpty() && !myJson1.optString("facebook").trim().equals("null") && !myJson1.optString("facebook").trim().matches("")) {
                JSONObject jsonObject = myJson1.optJSONObject("facebook");

                if(jsonObject.has("status") && jsonObject.optString("status").trim() != null && !jsonObject.optString("status").trim().isEmpty() && !jsonObject.optString("status").trim().equals("null") && !jsonObject.optString("status").trim().matches("")) {
                    setFeatureFlag( FACEBOOK , (jsonObject.optString("status")));
                }else{
                    setFeatureFlag( FACEBOOK ,DEFAULT_FACEBOOK);
                }
            }else {
                setFeatureFlag( FACEBOOK ,DEFAULT_FACEBOOK);
            }

            if(myJson1.has("google") && myJson1.optString("google").trim() != null && !myJson1.optString("google").trim().isEmpty() && !myJson1.optString("google").trim().equals("null") && !myJson1.optString("google").trim().matches("")) {
                JSONObject jsonObject = myJson1.optJSONObject("google");

                if(jsonObject.has("status") && jsonObject.optString("status").trim() != null && !jsonObject.optString("status").trim().isEmpty() && !jsonObject.optString("status").trim().equals("null") && !jsonObject.optString("status").trim().matches("")) {
                    setFeatureFlag( GOOGLE , (jsonObject.optString("status")));
                }else{
                    setFeatureFlag( GOOGLE ,DEFAULT_GOOGLE);
                }
            }else {
                setFeatureFlag( GOOGLE ,DEFAULT_GOOGLE);
            }


        }catch (Exception e){}
    }


    public boolean getFeatureStatus(String key, String defaultValue) {
        return fetureSharedPref.getString(key, defaultValue).equals("1")?true:false;
    }
}
