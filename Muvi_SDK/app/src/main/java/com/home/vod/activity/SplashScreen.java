package com.home.vod.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.home.apisdk.apiController.CheckGeoBlockCountryAsynTask;
import com.home.apisdk.apiController.GetGenreListAsynctask;
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetLanguageListAsynTask;
import com.home.apisdk.apiController.GetPlanListAsynctask;
import com.home.apisdk.apiController.GetTranslateLanguageAsync;
import com.home.apisdk.apiController.GetUserProfileAsynctask;
import com.home.apisdk.apiController.IsRegistrationEnabledAsynTask;
import com.home.apisdk.apiController.SDKInitializer;
import com.home.apisdk.apiController.SDKInitializer;
import com.home.apisdk.apiModel.CheckGeoBlockInputModel;
import com.home.apisdk.apiModel.CheckGeoBlockOutputModel;
import com.home.apisdk.apiModel.GenreListInput;
import com.home.apisdk.apiModel.GenreListOutput;
import com.home.apisdk.apiModel.Get_UserProfile_Input;
import com.home.apisdk.apiModel.Get_UserProfile_Output;
import com.home.apisdk.apiModel.IsRegistrationEnabledInputModel;
import com.home.apisdk.apiModel.IsRegistrationEnabledOutputModel;
import com.home.apisdk.apiModel.LanguageListInputModel;
import com.home.apisdk.apiModel.LanguageListOutputModel;
import com.home.apisdk.apiModel.SubscriptionPlanInputModel;
import com.home.apisdk.apiModel.SubscriptionPlanOutputModel;
import com.home.vod.R;
import com.home.vod.SplashScreenHandler;
import com.home.vod.model.LanguageModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.AppThreadPoolExecuter;
import com.home.vod.util.Constant;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.LogUtil;
import com.home.vod.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import player.model.ContactModel1;
import player.utils.DBHelper;

import static com.home.apisdk.apiController.HeaderConstants.RATING;
import static com.home.vod.preferences.LanguagePreference.*;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_GOOGLE_FCM_TOKEN;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;
import static com.home.vod.util.Util.GOOGLE_FCM_TOKEN;

import static com.home.vod.util.Util.decodeSampledBitmapFromResource;
import static player.utils.Util.IS_CHROMECAST;
import static player.utils.Util.IS_OFFLINE;

public class SplashScreen extends Activity implements GetIpAddressAsynTask.IpAddressListener,
        CheckGeoBlockCountryAsynTask.CheckGeoBlockForCountryListener,
        GetPlanListAsynctask.GetStudioPlanListsListener,
        IsRegistrationEnabledAsynTask.IsRegistrationenabledListener,
        GetLanguageListAsynTask.GetLanguageListListener,
        GetGenreListAsynctask.GenreListListener,
        GetUserProfileAsynctask.Get_UserProfileListener,
        GetTranslateLanguageAsync.GetTranslateLanguageInfoListener, SDKInitializer.SDKInitializerListner {

    private String[] genreArrToSend;
    private String[] genreValueArrayToSend;
    private RelativeLayout noInternetLayout;
    private RelativeLayout geoBlockedLayout;
    private String default_Language = "";
    private ArrayList<LanguageModel> languageModels = new ArrayList<>();
    private TextView noInternetTextView, geoTextView;
    private ArrayList<String> genreArrayList = new ArrayList<String>();
    private ArrayList<String> genreValueArrayList = new ArrayList<String>();
    private String user_Id = "", email_Id = "", isSubscribed = "0";

    Timer GoogleIdGeneraterTimer;

    /*Asynctask on background thread*/
    String ipAddressStr="";
    private Executor threadPoolExecutor;
    private PreferenceManager preferenceManager;
    private LanguagePreference languagePreference;
    private FeatureHandler featureHandler;
    DBHelper dbHelper;

    SplashScreenHandler splashScreenHandler;


    private void _init() {


        Util.getDPI(this);
        Util.printMD5Key(this);
        dbHelper=new DBHelper(SplashScreen.this);
        threadPoolExecutor = new AppThreadPoolExecuter().getThreadPoolExecutor();
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        featureHandler = FeatureHandler.getFeaturePreference(SplashScreen.this);

        noInternetLayout = (RelativeLayout) findViewById(R.id.noInternet);
        geoBlockedLayout = (RelativeLayout) findViewById(R.id.geoBlocked);

        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        geoTextView = (TextView) findViewById(R.id.geoBlockedTextView);
        ImageView imageResize = (ImageView) findViewById(R.id.splash_screen);
       /* DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;*/

        Display display = getWindowManager().getDefaultDisplay();
        float dpHeight = display.getHeight();
        float dpWidth = display.getWidth();
        imageResize.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.splash_screen, dpWidth, dpHeight));


        /*if ( Util.isTablet(SplashScreen.this)){
            imageResize.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else {
            imageResize.setScaleType(ImageView.ScaleType.FIT_XY);
            try {

            } catch (Exception e) {

            }
        }*/


        splashScreenHandler.handleSplashscreen(imageResize);


        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        geoTextView.setText(languagePreference.getTextofLanguage(GEO_BLOCKED_ALERT, DEFAULT_GEO_BLOCKED_ALERT));

        // ImageView imageResize = (ImageView) findViewById(R.id.splash_screen);

        noInternetLayout.setVisibility(View.GONE);
        geoBlockedLayout.setVisibility(View.GONE);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            noInternetTextView.setText("The app is not compatible for this OS.");
            noInternetLayout.setVisibility(View.VISIBLE);
            return;
        }

        if (NetworkStatus.getInstance().isConnected(this)) {
            SDKInitializer.getInstance().init(this, this, authTokenStr);
        } else {
            // Go to my download page , if the user is pre loggged in and the user has some download content.
            email_Id = preferenceManager.getEmailIdFromPref();
            if (email_Id != null) {
                ArrayList<ContactModel1> Size_Of_Download_Content = dbHelper.getContactt(email_Id, 1);
                if (Size_Of_Download_Content.size() > 0) {

                    Util.hideBcakIcon = true;

                    Intent intent = new Intent(SplashScreen.this, MyDownloads.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                } else {
                    noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                    noInternetLayout.setVisibility(View.VISIBLE);
                    geoBlockedLayout.setVisibility(View.GONE);
                }
            } else {
                noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                noInternetLayout.setVisibility(View.VISIBLE);
                geoBlockedLayout.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenHandler = new SplashScreenHandler(this);

        _init();
    }

    @Override
    protected void onPause() {

        // TODO Auto-generated method stub
        super.onPause();
        LogUtil.showLog("BKS", "packagenamesplash===" + SDKInitializer.user_Package_Name_At_Api);
        finish();
        overridePendingTransition(0, 0);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onIPAddressPreExecuteStarted() {

    }

    @Override
    public void onIPAddressPostExecuteCompleted(String message, int statusCode, String ipAddressStr) {

        Log.v("MUVI11","ipAddressStr====="+ipAddressStr);
        Log.v("MUVI11","getIPAddress====="+getIPAddress(true));


        if (ipAddressStr.equals("")) {
            noInternetTextView.setText("Could not detect your IP.");
            noInternetLayout.setVisibility(View.VISIBLE);
            geoBlockedLayout.setVisibility(View.GONE);
        } else {
            this.ipAddressStr = ipAddressStr;
            CheckGeoBlockInputModel checkGeoBlockInputModel = new CheckGeoBlockInputModel();
            checkGeoBlockInputModel.setAuthToken(authTokenStr);
            checkGeoBlockInputModel.setIp(ipAddressStr);
            CheckGeoBlockCountryAsynTask asynGetCountry = new CheckGeoBlockCountryAsynTask(checkGeoBlockInputModel, this, this);
            asynGetCountry.executeOnExecutor(threadPoolExecutor);
        }
    }

    @Override
    public void onCheckGeoBlockCountryPreExecuteStarted() {

    }

    @Override
    public void onCheckGeoBlockCountryPostExecuteCompleted(CheckGeoBlockOutputModel checkGeoBlockOutputModel, int status, String message) {
        if (checkGeoBlockOutputModel == null) {
            // countryCode = "";
            noInternetLayout.setVisibility(View.GONE);
            geoBlockedLayout.setVisibility(View.VISIBLE);
        } else {
            if (status > 0 && status == 200) {
                preferenceManager.setCountryCodeToPref(checkGeoBlockOutputModel.getCountrycode().trim());
                SubscriptionPlanInputModel planListInput = new SubscriptionPlanInputModel();
                planListInput.setAuthToken(authTokenStr);
                planListInput.setLang(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                GetPlanListAsynctask asynGetPlanid = new GetPlanListAsynctask(planListInput, SplashScreen.this, SplashScreen.this);
                asynGetPlanid.executeOnExecutor(threadPoolExecutor);

            }
            else if(status == 454){
                noInternetLayout.setVisibility(View.GONE);
                geoBlockedLayout.setVisibility(View.VISIBLE);
            }
            else {
                noInternetTextView.setText("Oops something went wrong.Please try again later .");
                noInternetLayout.setVisibility(View.VISIBLE);
                geoBlockedLayout.setVisibility(View.GONE);
            }
        }

       /* if (preferenceManager != null) {
            preferenceManager.setCountryCodeToPref("AU");
            SubscriptionPlanInputModel planListInput = new SubscriptionPlanInputModel();
            planListInput.setAuthToken(authTokenStr);
            planListInput.setLang(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            GetPlanListAsynctask asynGetPlanid = new GetPlanListAsynctask(planListInput, SplashScreen.this, SplashScreen.this);
            asynGetPlanid.executeOnExecutor(threadPoolExecutor);
        }*/

    }

    @Override
    public void onGetPlanListPreExecuteStarted() {

    }

    @Override
    public void onGetPlanListPostExecuteCompleted(ArrayList<SubscriptionPlanOutputModel> planListOutput, int status) {
        if (status > 0) {
            if (status == 200) {
                languagePreference.setLanguageSharedPrefernce(PLAN_ID, "1");
                LogUtil.showLog("MUVI", "responsestring of plan id = 1");
            } else {
                languagePreference.setLanguageSharedPrefernce(PLAN_ID, "0");
                LogUtil.showLog("MUVI", "responsestring of plan id = 0");
            }
        }

        IsRegistrationEnabledInputModel isRegistrationEnabledInputModel = new IsRegistrationEnabledInputModel();
        isRegistrationEnabledInputModel.setAuthToken(authTokenStr);
        IsRegistrationEnabledAsynTask asynIsRegistrationEnabled = new IsRegistrationEnabledAsynTask(isRegistrationEnabledInputModel, this, this);
        asynIsRegistrationEnabled.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onIsRegistrationenabledPreExecuteStarted() {

    }

    @Override
    public void onIsRegistrationenabledPostExecuteCompleted(IsRegistrationEnabledOutputModel isRegistrationEnabledOutputModel, int status, String message ,String response) {


        try{

            featureHandler.setDefaultFeaturePref(response);

        }catch (Exception e){}
        languagePreference.setLanguageSharedPrefernce(RATING, "" + isRegistrationEnabledOutputModel.getRating());


        languagePreference.setLanguageSharedPrefernce(HAS_FAVORITE, "" + isRegistrationEnabledOutputModel.getHas_favourite());
        languagePreference.setLanguageSharedPrefernce(RATING, "" + isRegistrationEnabledOutputModel.getRating());

        languagePreference.setLanguageSharedPrefernce(IS_RESTRICT_DEVICE, "" + isRegistrationEnabledOutputModel.getIsRestrictDevice());
        languagePreference.setLanguageSharedPrefernce(IS_ONE_STEP_REGISTRATION, "" + isRegistrationEnabledOutputModel.getSignup_step());
        languagePreference.setLanguageSharedPrefernce(IS_MYLIBRARY, "" + isRegistrationEnabledOutputModel.getIsMylibrary());

        languagePreference.setLanguageSharedPrefernce(IS_STREAMING_RESTRICTION, "" + isRegistrationEnabledOutputModel.getIs_streaming_restriction());
        languagePreference.setLanguageSharedPrefernce(IS_OFFLINE, "" + isRegistrationEnabledOutputModel.getIs_offline());
        languagePreference.setLanguageSharedPrefernce(IS_CHROMECAST, "" + isRegistrationEnabledOutputModel.getChromecast());


        preferenceManager.setLoginFeatureToPref(isRegistrationEnabledOutputModel.getIs_login());


        /**
         * Override feature properties.
         */

        splashScreenHandler.changeFeatureProperties(featureHandler);




        LogUtil.showLog("MUVI", "Splash setLoginFeatureToPref ::" + isRegistrationEnabledOutputModel.getIs_login());

        LanguageListInputModel languageListInputModel = new LanguageListInputModel();
        languageListInputModel.setAuthToken(authTokenStr);
        GetLanguageListAsynTask asynGetLanguageList = new GetLanguageListAsynTask(languageListInputModel, this, this);
        asynGetLanguageList.executeOnExecutor(threadPoolExecutor);

    }

    @Override
    public void onGetLanguageListPreExecuteStarted() {

    }

    @Override
    public void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage) {

        this.default_Language = defaultLanguage;
        for (int i = 0; i < languageListOutputArray.size(); i++) {

            LanguageModel languageModel = new LanguageModel();
            languageModel.setLanguageId(languageListOutputArray.get(i).getLanguageCode());
            languageModel.setLanguageName(languageListOutputArray.get(i).getLanguageName());
            if (defaultLanguage.equalsIgnoreCase(languageListOutputArray.get(i).getLanguageCode())) {
                languageModel.setIsSelected(true);

            } else {
                languageModel.setIsSelected(false);
            }

            languageModels.add(languageModel);
        }

        Util.languageModel = languageModels;

        if (languageModels.size() == 1) {
            preferenceManager.setLanguageListToPref("1");
        }
        if (languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, "").equalsIgnoreCase("")) {
            languagePreference.setLanguageSharedPrefernce(SELECTED_LANGUAGE_CODE, defaultLanguage);
        }

        else {
            defaultLanguage = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,DEFAULT_SELECTED_LANGUAGE_CODE);
        }

        // Call For Language Translation.

        LanguageListInputModel languageListInputModel = new LanguageListInputModel();
        languageListInputModel.setAuthToken(authTokenStr);
        languageListInputModel.setLangCode(defaultLanguage);
        GetTranslateLanguageAsync asynGetTransalatedLanguage = new GetTranslateLanguageAsync(languageListInputModel, this, this);
        asynGetTransalatedLanguage.executeOnExecutor(threadPoolExecutor);

    }

    @Override
    public void onGetGenreListPreExecuteStarted() {

    }

    @Override
    public void onGetGenreListPostExecuteCompleted(ArrayList<GenreListOutput> genreListOutput, int code, String status) {

        if (code > 0) {

            if (code == 200) {
                int lengthJsonArr = genreListOutput.size();
                if (lengthJsonArr > 0) {
                    genreArrayList.add(0, languagePreference.getTextofLanguage(FILTER_BY, DEFAULT_FILTER_BY));
                    genreValueArrayList.add(0, "");

                }
                for (int i = 0; i < lengthJsonArr; i++) {
                    genreArrayList.add(genreListOutput.get(i).getGenre_name());
                    genreValueArrayList.add(genreListOutput.get(i).getGenre_name());
                }

                if (genreArrayList.size() > 1) {

                    genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_BY, DEFAULT_SORT_BY));
                    genreValueArrayList.add(genreValueArrayList.size(), "");

                    genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_LAST_UPLOADED, DEFAULT_SORT_LAST_UPLOADED));
                    genreValueArrayList.add(genreValueArrayList.size(), "lastupload");

                    genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_RELEASE_DATE, DEFAULT_SORT_RELEASE_DATE));
                    genreValueArrayList.add(genreValueArrayList.size(), "releasedate");

                    genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_A_Z, DEFAULT_SORT_ALPHA_A_Z));
                    genreValueArrayList.add(genreValueArrayList.size(), "sortasc");

                    genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_Z_A, DEFAULT_SORT_ALPHA_Z_A));
                    genreValueArrayList.add(genreValueArrayList.size(), "sortdesc");

                }
                genreArrToSend = new String[genreArrayList.size()];
                genreArrToSend = genreArrayList.toArray(genreArrToSend);


                genreValueArrayToSend = new String[genreValueArrayList.size()];
                genreValueArrayToSend = genreValueArrayList.toArray(genreValueArrayToSend);
            } else {
                genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_BY, DEFAULT_SORT_BY));
                genreValueArrayList.add(genreValueArrayList.size(), "");


                genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_LAST_UPLOADED, DEFAULT_SORT_LAST_UPLOADED));
                genreValueArrayList.add(genreValueArrayList.size(), "lastupload");

                genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_RELEASE_DATE, DEFAULT_SORT_RELEASE_DATE));
                genreValueArrayList.add(genreValueArrayList.size(), "releasedate");

                genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_A_Z, DEFAULT_SORT_ALPHA_A_Z));
                genreValueArrayList.add(genreValueArrayList.size(), "sortasc");

                genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_Z_A, DEFAULT_SORT_ALPHA_Z_A));
                genreValueArrayList.add(genreValueArrayList.size(), "sortdesc");

                genreArrToSend = new String[genreArrayList.size()];
                genreArrToSend = genreArrayList.toArray(genreArrToSend);

                genreValueArrayToSend = new String[genreValueArrayList.size()];
                genreValueArrayToSend = genreValueArrayList.toArray(genreValueArrayToSend);

            }
        } else {
            genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_BY, DEFAULT_SORT_BY));
            genreValueArrayList.add(genreValueArrayList.size(), "");


            genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_LAST_UPLOADED, DEFAULT_SORT_LAST_UPLOADED));
            genreValueArrayList.add(genreValueArrayList.size(), "lastupload");

            genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_RELEASE_DATE, DEFAULT_SORT_RELEASE_DATE));
            genreValueArrayList.add(genreValueArrayList.size(), "releasedate");

            genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_A_Z, DEFAULT_SORT_ALPHA_A_Z));
            genreValueArrayList.add(genreValueArrayList.size(), "sortasc");

            genreArrayList.add(genreArrayList.size(), languagePreference.getTextofLanguage(SORT_ALPHA_Z_A, DEFAULT_SORT_ALPHA_Z_A));
            genreValueArrayList.add(genreValueArrayList.size(), "sortdesc");

            genreArrToSend = new String[genreArrayList.size()];
            genreArrToSend = genreArrayList.toArray(genreArrToSend);


            genreValueArrayToSend = new String[genreValueArrayList.size()];
            genreValueArrayToSend = genreValueArrayList.toArray(genreValueArrayToSend);
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genreArrToSend.length; i++) {
            sb.append(genreArrToSend[i]).append(",");
        }

        preferenceManager.setGenreArrayToPref(sb.toString());

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < genreValueArrayToSend.length; i++) {
            sb1.append(genreValueArrayToSend[i]).append(",");
        }

        preferenceManager.setGenreValuesArrayToPref(sb1.toString());

        // This Code Is Done For The One Step Registration.


        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {

            if (preferenceManager != null) {
                user_Id = preferenceManager.getUseridFromPref();
                email_Id = preferenceManager.getEmailIdFromPref();

                if (user_Id != null && email_Id != null) {

                    Get_UserProfile_Input get_userProfile_input = new Get_UserProfile_Input();
                    get_userProfile_input.setAuthToken(authTokenStr);
                    get_userProfile_input.setEmail(email_Id);
                    get_userProfile_input.setUser_id(user_Id);
                    get_userProfile_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    GetUserProfileAsynctask asynLoadProfileDetails = new GetUserProfileAsynctask(get_userProfile_input, this, this);
                    asynLoadProfileDetails.executeOnExecutor(threadPoolExecutor);

                } else {
                    Call_One_Step_Procedure();
                }
            } else {
                Call_One_Step_Procedure();
            }
        } else {
            Call_One_Step_Procedure();
        }
    }

    @Override
    public void onGet_UserProfilePreExecuteStarted() {

    }

    @Override
    public void onGet_UserProfilePostExecuteCompleted(Get_UserProfile_Output get_userProfile_output, int code, String message, String status) {
        if (status == null) {
            isSubscribed = "0";
        }
        if (code==200){
            isSubscribed=get_userProfile_output.getIsSubscribed();
        }

        Call_One_Step_Procedure();
    }

    @Override
    public void onGetTranslateLanguagePreExecuteStarted() {

    }

    @Override
    public void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status) {

        if (status > 0 && status == 200) {


            try {
                Util.parseLanguage(languagePreference, jsonResponse, default_Language);
            } catch (JSONException e) {
                e.printStackTrace();
                noInternetLayout.setVisibility(View.GONE);
            }

        } else {
            noInternetLayout.setVisibility(View.GONE);
        }

        GenreListInput genreListInput = new GenreListInput();
        genreListInput.setAuthToken(authTokenStr);

        GetGenreListAsynctask asynGetGenreList = new GetGenreListAsynctask(genreListInput, SplashScreen.this, SplashScreen.this);
        asynGetGenreList.executeOnExecutor(threadPoolExecutor);
    }

    public void Call_One_Step_Procedure() {

        if (!languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN).equals("0")) {
            LogUtil.showLog("MUVI", "google_id already created =" + languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));

            jumpToNextScreen();

        } else {
            GoogleIdGeneraterTimer = new Timer();
            GoogleIdGeneraterTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    LogUtil.showLog("MUVI", "google_id=" + languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                    if (!languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN).equals("0")) {
                        GoogleIdGeneraterTimer.cancel();
                        GoogleIdGeneraterTimer.purge();

                        LogUtil.showLog("MUVI", "google_id=" + languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                        jumpToNextScreen();

                    }
                }
            }, 0, 1000);
        }

        //============================End Added For FCM===========================//
    }


    /**
     * Jump to next screen by checking condition.
     */
    private void jumpToNextScreen() {
        Intent mIntent;
        String loggedInStr = preferenceManager.getLoginStatusFromPref();
//        if ((languagePreference.getTextofLanguage(IS_ONE_STEP_REGISTRATION, DEFAULT_IS_ONE_STEP_REGISTRATION).trim()).equals("1")) {
        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
            if (loggedInStr != null) {
                if (isSubscribed.trim().equals("1")) {
                    mIntent = new Intent(SplashScreen.this, MainActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(mIntent);
                    finish();
                    overridePendingTransition(0, 0);
                } else {
                    mIntent = new Intent(SplashScreen.this, SubscriptionActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(mIntent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            } else {
                mIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                startActivity(mIntent);
                finish();
            }

        } else {

            mIntent = new Intent(SplashScreen.this, MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(mIntent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onPreExexuteListner() {

    }

    @Override
    public void onPostExecuteListner(int status) {
        SDKInitializer.setData(this);
        if (status==200){
//            if (NetworkStatus.getInstance().isConnected(this)) {
               GetIpAddressAsynTask asynGetIpAddress = new GetIpAddressAsynTask(this, this);
                asynGetIpAddress.executeOnExecutor(threadPoolExecutor);

              //  ipAddressStr = getIPAddress(true);
              /*  if (ipAddressStr.equals("")) {
                    noInternetTextView.setText("Could not detect your IP.");
                    noInternetLayout.setVisibility(View.VISIBLE);
                    geoBlockedLayout.setVisibility(View.GONE);
                } else {
                    CheckGeoBlockInputModel checkGeoBlockInputModel = new CheckGeoBlockInputModel();
                    checkGeoBlockInputModel.setAuthToken(authTokenStr);
                    checkGeoBlockInputModel.setIp(ipAddressStr);
                    CheckGeoBlockCountryAsynTask asynGetCountry = new CheckGeoBlockCountryAsynTask(checkGeoBlockInputModel, this, this);
                    asynGetCountry.executeOnExecutor(threadPoolExecutor);
                }*/

//            }
        }
        else if (status==Util.ERROR_CODE_EXPIRED_AUTHTOKEN){
            geoTextView.setText(languagePreference.getTextofLanguage(APP_NO_LONGER_ACTIVE, DEFAULT_APP_NO_LONGER_ACTIVE));
//            geoTextView.setText("Thanks for visiting Plusnights. The trial has now ended, but keep your eyes peeled for more information coming later this year. Thanks, and we hope you enjoyed your film!");
            noInternetLayout.setVisibility(View.GONE);
            geoBlockedLayout.setVisibility(View.VISIBLE);
        }
        else {
            noInternetTextView.setText("Oops something went wrong.Please try again later .");
            noInternetLayout.setVisibility(View.VISIBLE);
            geoBlockedLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.showLog("BKS", "packagenamesplash===" + SDKInitializer.user_Package_Name_At_Api);

    }


    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

}