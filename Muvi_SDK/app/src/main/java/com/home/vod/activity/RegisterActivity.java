package com.home.vod.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.home.apisdk.apiController.AsyncGmailReg;
import com.home.apisdk.apiController.CheckDeviceAsyncTask;
import com.home.apisdk.apiController.CheckFbUserDetailsAsyn;
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetValidateUserAsynTask;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiController.SDKInitializer;
import com.home.apisdk.apiController.RegistrationAsynTask;
import com.home.apisdk.apiController.SocialAuthAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiModel.CheckDeviceInput;
import com.home.apisdk.apiModel.CheckDeviceOutput;
import com.home.apisdk.apiModel.CheckFbUserDetailsInput;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.GmailLoginInput;
import com.home.apisdk.apiModel.GmailLoginOutput;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.LogoutInput;
import com.home.apisdk.apiModel.Registration_input;
import com.home.apisdk.apiModel.Registration_output;
import com.home.apisdk.apiModel.SocialAuthInputModel;
import com.home.apisdk.apiModel.SocialAuthOutputModel;
import com.home.apisdk.apiModel.ValidateUserOutput;
import com.home.vod.BuildConfig;
import com.home.vod.CheckSubscriptionHandler;
import com.home.vod.MonetizationHandler;
import com.home.vod.R;
import com.home.vod.RegisterUIHandler;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.MyActivity;
import player.activity.Player;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.ALREADY_MEMBER;
import static com.home.vod.preferences.LanguagePreference.ANDROID_VERSION;
import static com.home.vod.preferences.LanguagePreference.BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ALREADY_MEMBER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ANDROID_VERSION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_EMAIL_EXISTS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_REGISTRATION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FAILURE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FIRST_NAME;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_GOOGLE_FCM_TOKEN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_IS_STREAMING_RESTRICTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_RESTRICT_DEVICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PASSWORDS_DO_NOT_MATCH;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SLOW_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VALID_CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.EMAIL_EXISTS;
import static com.home.vod.preferences.LanguagePreference.ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_REGISTRATION;
import static com.home.vod.preferences.LanguagePreference.FAILURE;
import static com.home.vod.preferences.LanguagePreference.FIRST_NAME;
import static com.home.vod.preferences.LanguagePreference.GOOGLE_FCM_TOKEN;
import static com.home.vod.preferences.LanguagePreference.LOGIN;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.TEXT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.VALID_CONFIRM_PASSWORD;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;

public class RegisterActivity extends AppCompatActivity implements
        RegistrationAsynTask.RegistrationDetailsListener,
        VideoDetailsAsynctask.VideoDetailsListener,
        GetValidateUserAsynTask.GetValidateUserListener,
        SocialAuthAsynTask.SocialAuthListener,
        LogoutAsynctask.LogoutListener,
        CheckDeviceAsyncTask.CheckDeviceListener,
        CheckFbUserDetailsAsyn.CheckFbUserDetailsListener, AsyncGmailReg.AsyncGmailListener,
        GoogleApiClient.OnConnectionFailedListener, GetIpAddressAsynTask.IpAddressListener {
    String UniversalErrorMessage = "";
    String UniversalIsSubscribed = "";
    String deviceName = "";
    ProgressBarHandler pDialog;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    String Authname, AuthEmail, AuthId;
    private static final String TAG = "Nihar";
    private String AuthImageUrl;
    LanguagePreference languagePreference;
    int countryPosition = 0;
    int langPosition = 0;
    /*********fb****/


    //for resume play
    String seek_status = "";
    int Played_Length = 0;
    String watch_status_String = "start";

    FeatureHandler featureHandler;
    RegisterUIHandler registerUIHandler;
    String fbUserId = "";
    String fbEmail = "";
    String fbName = "";
    private LinearLayout btnLogin;
    private ProgressBarHandler progressDialog;
    private CallbackManager callbackManager;
    CheckFbUserDetailsAsyn asynCheckFbUserDetails;
    SocialAuthAsynTask asynFbRegDetails;
    LoginButton loginWithFacebookButton;
    AlertDialog alert;
    String priceForUnsubscribedStr, priceFosubscribedStr;
    String planId = "";
    /*********fb****/
       /*subtitle-------------------------------------*/


    String ipAddressStr = "";
    String filename = "";
    static File mediaStorageDir;
    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();
    public static ProgressBarHandler progressBarHandler;
    Player playerModel;

    @Override
    public void onGmailRegPreExecuteStarted() {

        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onGmailRegPostExecuteCompleted(GmailLoginOutput gmailLoginOutput, int status, String message) {


        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            status = 0;

        }


        if (status == 200) {

            preferenceManager.setLogInStatusToPref("1");
            preferenceManager.setUserIdToPref(gmailLoginOutput.getId());
            preferenceManager.setPwdToPref("");
            preferenceManager.setEmailIdToPref(gmailLoginOutput.getEmail());
            preferenceManager.setDispNameToPref(gmailLoginOutput.getDisplay_name());
            preferenceManager.setLoginProfImgoPref(gmailLoginOutput.getProfile_image());
            preferenceManager.setIsSubscribedToPref(Integer.toString(gmailLoginOutput.getIsSubscribed()));
            preferenceManager.setLoginHistIdPref(gmailLoginOutput.getLogin_history_id());

            /*Date todayDate = new Date();
            String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
            editor.putString("date", todayStr.trim());
            editor.commit();*/


            if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {

                //load video urls according to resolution
                if (featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE)) {

                    Log.v("BIBHU", "isRestrictDevice called");
                    // Call For Check Api.
                    CheckDeviceInput checkDeviceInput = new CheckDeviceInput();
                    checkDeviceInput.setDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    checkDeviceInput.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                    checkDeviceInput.setAuthToken(authTokenStr);
                    checkDeviceInput.setUser_id(preferenceManager.getUseridFromPref());
                    checkDeviceInput.setDevice_type("1");
                    checkDeviceInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    checkDeviceInput.setDevice_info(deviceName + "," + languagePreference.getTextofLanguage(ANDROID_VERSION, DEFAULT_ANDROID_VERSION) + " " + Build.VERSION.RELEASE);
                    CheckDeviceAsyncTask asynCheckDevice = new CheckDeviceAsyncTask(checkDeviceInput, this, this);
                    asynCheckDevice.executeOnExecutor(threadPoolExecutor);
                } else {

                    if(Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (getIntent().getStringExtra("from") != null) {
                        //** review **//*
                        onBackPressed();
                    } else {
                        if (Util.check_for_subscription == 1) {
                            //go to subscription page
                            if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {

                                setResultAtFinishActivity();

                            } else {
                                Util.showToast(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                                // Toast.makeText(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this,Util.NO_INTERNET_CONNECTION,Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            if (planId.equals("1") && preferenceManager.getIsSubscribedFromPref().equals("0")) {
                                if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                                    Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    if (LoginActivity.loginA != null) {
                                        LoginActivity.loginA.finish();
                                    }

                                    onBackPressed();
                                }else {
                                    Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    if (LoginActivity.loginA != null) {
                                        LoginActivity.loginA.finish();
                                    }
                                    onBackPressed();
                                }
                            } else {

                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }
                                onBackPressed();
                            }
                        }
                    }
                }

            } else {
                Util.showToast(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
            }


        } else {

            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setMessage(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK));
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();


        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onIPAddressPreExecuteStarted() {

    }

    @Override
    public void onIPAddressPostExecuteCompleted(String message, int statusCode, String ipAddressStr) {
        this.ipAddressStr = ipAddressStr;
        return;
    }

        /*subtitle-------------------------------------*/

/*chromecast-------------------------------------*/


    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    /**
     * List of various states that we can be in
     */

    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    private LoginActivity.PlaybackLocation mLocation;
    private LoginActivity.PlaybackState mPlaybackState;
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaInfo mSelectedMedia;


    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {

        @Override
        public void onSessionEnded(CastSession session, int error) {
            if (session == mCastSession) {
                mCastSession = null;
            }
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mCastSession = session;
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mCastSession = session;
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStarting(CastSession session) {
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionEnding(CastSession session) {
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
        }
    }


    MediaInfo mediaInfo;
    /*chromecast-------------------------------------*/


    RegistrationAsynTask asyncReg;
    VideoDetailsAsynctask asynLoadVideoUrls;
    GetValidateUserAsynTask asynValidateUserDetails;
    String movieVideoUrlStr = "";
    private ImageView registerImageView;
    private EditText editEmail, editName, editPassword, editConfirmPassword, editName_first, editName_last;
    private Button registerButton;
    private TextView alreadyMemmberText, loginTextView;
    String regEmailStr, regPasswordStr, regConfirmPasswordStr;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    String registrationIdStr;
    String isSubscribedStr = "";
    int keepAliveTime = 10;
    PreferenceManager preferenceManager;
    Toolbar mActionBarToolbar;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onResume() {
        super.onResume();
        /***************chromecast**********************/
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }


        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);

        /***************chromecast**********************/


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*********fb****/
        FacebookSdk.sdkInitialize(getApplicationContext());
        /*********fb****/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_register);

        LogUtil.showLog("BKS", "packagename===" + SDKInitializer.user_Package_Name_At_Api);

        languagePreference = LanguagePreference.getLanguagePreference(RegisterActivity.this);
        featureHandler = FeatureHandler.getFeaturePreference(RegisterActivity.this);
        preferenceManager = PreferenceManager.getPreferenceManager(this);


        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        deviceName = myDevice.getName();

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(BTN_REGISTER,DEFAULT_BTN_REGISTER));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        //playerModel=new Player();

        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");

        if (playerModel != null)

            playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
            mActionBarToolbar.setNavigationIcon(null);
        } else {
            mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        }

        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        registerImageView = (ImageView) findViewById(R.id.registerImageView);
        /*editName = (EditText) findViewById(R.id.editNameStr);

        editName_first = (EditText) findViewById(R.id.editNameStr_first);
        editName_last = (EditText) findViewById(R.id.editNameStr_last);*/

        editEmail = (EditText) findViewById(R.id.editEmailStr);
        editPassword = (EditText) findViewById(R.id.editPasswordStr);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPasswordStr);
        registerButton = (Button) findViewById(R.id.registerButton);
        alreadyMemmberText = (TextView) findViewById(R.id.alreadyMemberText);
        loginTextView = (TextView) findViewById(R.id.alreadyHaveALoginButton);

        /*FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editName_first);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editName_last);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editName);*/

        /*******enter key of keyboard *************/
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (source.charAt(i) == '\n') {
                        return " ";
                    }
                }
                return null;
            }
        };

        editEmail.setFilters(new InputFilter[]{filter});
        editConfirmPassword.setFilters(new InputFilter[]{filter});
        // editName.setFilters(new InputFilter[]{filter});
        editPassword.setFilters(new InputFilter[]{filter});
        /*******enter key of keyboard *************/

        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editEmail);

        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editPassword);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), editConfirmPassword);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), registerButton);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), alreadyMemmberText);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.light_fonts), loginTextView);


        /*editName_first.setHint(languagePreference.getTextofLanguage(FIRST_NAME,DEFAULT_FIRST_NAME));
        editName_last.setHint(languagePreference.getTextofLanguage(LAST_NAME,DEFAULT_LAST_NAME));
        editName.setHint(languagePreference.getTextofLanguage(NAME_HINT, DEFAULT_NAME_HINT));*/


        editEmail.setHint(languagePreference.getTextofLanguage(TEXT_EMIAL, DEFAULT_TEXT_EMIAL));
        editPassword.setHint(languagePreference.getTextofLanguage(TEXT_PASSWORD, DEFAULT_TEXT_PASSWORD));
        editConfirmPassword.setHint(languagePreference.getTextofLanguage(CONFIRM_PASSWORD, DEFAULT_CONFIRM_PASSWORD));
        registerButton.setText(languagePreference.getTextofLanguage(BTN_REGISTER, DEFAULT_BTN_REGISTER));
        alreadyMemmberText.setText(languagePreference.getTextofLanguage(ALREADY_MEMBER, DEFAULT_ALREADY_MEMBER));
        loginTextView.setText(languagePreference.getTextofLanguage(LOGIN, DEFAULT_LOGIN));

       /* *//**********fb*********//*
        loginWithFacebookButton = (LoginButton) findViewById(R.id.loginWithFacebookButton);
        loginWithFacebookButton.setVisibility(View.GONE);
        TextView fbLoginTextView = (TextView) findViewById(R.id.fbLoginTextView);
        FontUtls.loadFont(RegisterActivity.this, getResources().getString(R.string.regular_fonts),fbLoginTextView);

        *//**********fb*********//*
*/

       /* Toolbar mActionBarToolbar= (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/


        planId = (languagePreference.getTextofLanguage(PLAN_ID, DEFAULT_PLAN_ID)).trim();
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCastSession != null && mCastSession.isConnected()) {
                    Log.v("pratik", "reg to log chrome con");
                    Intent castLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    castLoginIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    castLoginIntent.putExtra("PlayerModel", playerModel);
                    startActivity(castLoginIntent);
                    onBackPressed();
                } else {
                    final Intent detailsIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    if (getIntent().getStringExtra("from") != null) {
                        detailsIntent.putExtra("from", getIntent().getStringExtra("from"));
                    }
                    if (Util.check_for_subscription == 1) {
                        Util.check_for_subscription = 1;
                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    }

                    detailsIntent.putExtra("PlayerModel", playerModel);
                    detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(detailsIntent);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

        alreadyMemmberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "There's some error. Please try again !", Toast.LENGTH_SHORT).show();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                registerUIHandler.getRegisterName();
                // registerButtonClicked();
            }
        });


        /************fb************/
        callbackManager = CallbackManager.Factory.create();

        registerUIHandler = new RegisterUIHandler(this);
        registerUIHandler.setCountryList(preferenceManager);
        registerUIHandler.setTermsTextView(languagePreference);
        //registerUIHandler.setEmailText(languagePreference);
        registerUIHandler.callFblogin(callbackManager, registerButton, languagePreference);
        registerUIHandler.callSignin(languagePreference);
        //-----------------------google signin--------------//

        // loginHandler.callFblogin(callbackManager,mCallBack);
      /*  RelativeLayout GoogleSignView = (RelativeLayout) findViewById(R.id.sign_in_button);
        GoogleSignView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });*/

        // Configure Google Sign In

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_ids))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //-----------------------end--------------------------//

// =======End ===========================//

        /************fb************/
/*
            /*chromecast-------------------------------------*/

        GoogleApiAvailability googAvail = GoogleApiAvailability.getInstance();

        int status = googAvail.isGooglePlayServicesAvailable(this);
        Log.i(TAG, "onCreate: Status= " + googAvail.getErrorString(status));


        boolean shouldStartPlayback = false;
        int startPosition = 0;
        try {
            mAquery = new AQuery(this);

            // setupControlsCallbacks();
            setupCastListener();
            mCastContext = CastContext.getSharedInstance(this);
            mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
            mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

            shouldStartPlayback = false;
            startPosition = 0;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

         /*   MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, movieName.getText().toString());
            movieMetadata.putString(MediaMetadata.KEY_TITLE,  movieName.getText().toString());
            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put("description", movieName.getText().toString());
            } catch (JSONException e) {
                Log.e(TAG, "Failed to add description to the json object", e);
            }

            mediaInfo = new MediaInfo.Builder(castVideoUrl.trim())
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType("videos/mp4")
                    .setMetadata(movieMetadata)
                    .setStreamDuration(15 * 1000)
                    .setCustomData(jsonObj)
                    .build();
            mSelectedMedia = mediaInfo;*/

        // see what we need to play and where
           /* Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mSelectedMedia = getIntent().getParcelableExtra("media");
                //setupActionBar();
                boolean shouldStartPlayback = bundle.getBoolean("shouldStart");
                int startPosition = bundle.getInt("startPosition", 0);
                // mVideoView.setVideoURI(Uri.parse(mSelectedMedia.getContentId()));
               // Log.d(TAG, "Setting url of the VideoView to: " + mSelectedMedia.getContentId());
                if (shouldStartPlayback) {
                    // this will be the case only if we are coming from the
                    // CastControllerActivity by disconnecting from a device
                    mPlaybackState = PlaybackState.PLAYING;
                    updatePlaybackLocation(PlaybackLocation.LOCAL);
                    updatePlayButton(mPlaybackState);
                    if (startPosition > 0) {
                        // mVideoView.seekTo(startPosition);
                    }
                    // mVideoView.start();
                    //startControllersTimer();
                } else {
                    // we should load the video but pause it
                    // and show the album art.
                    if (mCastSession != null && mCastSession.isConnected()) {
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    } else {
                        updatePlaybackLocation(PlaybackLocation.LOCAL);
                    }
                    mPlaybackState = PlaybackState.IDLE;
                    updatePlayButton(mPlaybackState);
                }
            }*/


        if (shouldStartPlayback) {
            // this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            mPlaybackState = LoginActivity.PlaybackState.PLAYING;
            updatePlaybackLocation(LoginActivity.PlaybackLocation.LOCAL);
            updatePlayButton(mPlaybackState);
            if (startPosition > 0) {
                // mVideoView.seekTo(startPosition);
            }
            // mVideoView.start();
            //startControllersTimer();
        } else {
            // we should load the video but pause it
            // and show the album art.
            if (mCastSession != null && mCastSession.isConnected()) {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                updatePlaybackLocation(LoginActivity.PlaybackLocation.REMOTE);
            } else {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));

                updatePlaybackLocation(LoginActivity.PlaybackLocation.LOCAL);
            }
            mPlaybackState = LoginActivity.PlaybackState.IDLE;
            updatePlayButton(mPlaybackState);
        }
/***************chromecast**********************/
    }


    public void registerButtonClicked(String first_name, String last_name, String phone) {

     /*   regNameStr_first = editName_first.getText().toString().trim();
        regNameStr_last = editName_last.getText().toString().trim();
        regNameStr = editName.getText().toString().trim();*/


        regEmailStr = editEmail.getText().toString().trim();
        regPasswordStr = editPassword.getText().toString().trim();
        regConfirmPasswordStr = editConfirmPassword.getText().toString().trim();






        if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {

            if(first_name.trim().equals("")){
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(FIRST_NAME, DEFAULT_FIRST_NAME), Toast.LENGTH_LONG).show();
                return;
            }

            if(regEmailStr.trim().equals("")){
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(TEXT_EMIAL, DEFAULT_TEXT_EMIAL), Toast.LENGTH_LONG).show();
                return;
            }
            boolean isValidEmail = Util.isValidMail(regEmailStr);
            if(!isValidEmail){
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(OOPS_INVALID_EMAIL, DEFAULT_OOPS_INVALID_EMAIL), Toast.LENGTH_LONG).show();
                return;
            }

            if(regPasswordStr.trim().equals("")){
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(TEXT_PASSWORD, DEFAULT_TEXT_PASSWORD), Toast.LENGTH_LONG).show();

                return;
            }

            if(regConfirmPasswordStr.trim().equals("")){
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(VALID_CONFIRM_PASSWORD, DEFAULT_VALID_CONFIRM_PASSWORD), Toast.LENGTH_LONG).show();
                return;
            }



            Registration_input registration_input = new Registration_input();
            registration_input.setAuthToken(authTokenStr);
            registration_input.setName(first_name);
            registration_input.setCustom_last_name(last_name);
            registration_input.setEmail(regEmailStr);
            registration_input.setPhone(phone);
            registration_input.setPassword(regPasswordStr);
            registration_input.setCustom_country(registerUIHandler.selected_Country_Id);
            registration_input.setCustom_languages(registerUIHandler.selected_Language_Id);
            registration_input.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            registration_input.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
            registration_input.setDevice_type("1");
            registration_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

            asyncReg = new RegistrationAsynTask(registration_input, this, this);
            asyncReg.executeOnExecutor(threadPoolExecutor);


/*
            // if (!regNameStr.matches("") && (!regEmailStr.matches("")) && (!regPasswordStr.matches("")) && !regNameStr.equals("")) {
            if ((!regEmailStr.matches("")) && (!regPasswordStr.matches(""))) {
                boolean isValidEmail = Util.isValidMail(regEmailStr);
                if (isValidEmail) {
                    if (regPasswordStr.equals(regConfirmPasswordStr)) {


                        Registration_input registration_input = new Registration_input();
                        registration_input.setAuthToken(authTokenStr);
                        registration_input.setName(first_name);
                        registration_input.setCustom_last_name(last_name);
                        registration_input.setEmail(regEmailStr);
                        registration_input.setPhone(phone);
                        registration_input.setPassword(regPasswordStr);
                        registration_input.setCustom_country(registerUIHandler.selected_Country_Id);
                        registration_input.setCustom_languages(registerUIHandler.selected_Language_Id);
                        registration_input.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                        registration_input.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                        registration_input.setDevice_type("1");
                        registration_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                        asyncReg = new RegistrationAsynTask(registration_input, this, this);
                        asyncReg.executeOnExecutor(threadPoolExecutor);
                    } else {
                        Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(PASSWORDS_DO_NOT_MATCH, DEFAULT_PASSWORDS_DO_NOT_MATCH), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(OOPS_INVALID_EMAIL, DEFAULT_OOPS_INVALID_EMAIL), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(ENTER_REGISTER_FIELDS_DATA, DEFAULT_ENTER_REGISTER_FIELDS_DATA), Toast.LENGTH_LONG).show();

            }
            */



        } else {
            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
        }

















    }

    @Override
    public void onRegistrationDetailsPreExecuteStarted() {

        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();

    }

    @Override
    public void onRegistrationDetailsPostExecuteCompleted(Registration_output registration_output, int status, String message) {

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            status = 0;

        }

        if (status == 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_REGISTRATION, DEFAULT_ERROR_IN_REGISTRATION));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, DEFAULT_FAILURE));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        }
        if (status > 0) {

            if (status == 422) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(EMAIL_EXISTS, DEFAULT_EMAIL_EXISTS));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();

            } else if (status == 200) {

                // Take appropiate step here
                // playerModel.setEmailId(registration_output.getEmail());

                isSubscribedStr = registration_output.getIsSubscribed();
                preferenceManager.setLogInStatusToPref("1");
                preferenceManager.setIsSubscribedToPref(registration_output.getIsSubscribed());
                preferenceManager.setUserIdToPref(registration_output.getId());
                preferenceManager.setPwdToPref(editPassword.getText().toString().trim());
                preferenceManager.setEmailIdToPref(registration_output.getEmail());
                preferenceManager.setDispNameToPref(registration_output.getDisplay_name());
                preferenceManager.setLoginProfImgoPref(registration_output.getProfile_image());
                preferenceManager.setIsSubscribedToPref(isSubscribedStr);
                preferenceManager.setLoginHistIdPref(registration_output.getLogin_history_id());

                Date todayDate = new Date();
                String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
                preferenceManager.setLoginDatePref(todayStr);

                if (featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE)) {
                    // Call For Check Api.
                    CheckDeviceInput checkDeviceInput = new CheckDeviceInput();
                    checkDeviceInput.setAuthToken(authTokenStr);
                    String userIdStr = preferenceManager.getUseridFromPref();
                    checkDeviceInput.setUser_id(userIdStr);
                    checkDeviceInput.setDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    checkDeviceInput.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                    checkDeviceInput.setDevice_type("1");
                    checkDeviceInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    checkDeviceInput.setDevice_info(deviceName + ",Android " + Build.VERSION.RELEASE);
                    CheckDeviceAsyncTask asynCheckDevice = new CheckDeviceAsyncTask(checkDeviceInput, this, this);
                    asynCheckDevice.executeOnExecutor(threadPoolExecutor);
                } else {

                    if(Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (getIntent().getStringExtra("from") != null) {
                        /** review **/
                        onBackPressed();
                    } else {
                        if (Util.check_for_subscription == 1) {
                            // Go for subscription

                            if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {

                                setResultAtFinishActivity();

                            } else {
                                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING), Toast.LENGTH_LONG).show();
                            }

                        } else {

                            if (planId.equals("1") && isSubscribedStr.equals("0")) {
                                if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                                    Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    if (LoginActivity.loginA != null) {
                                        LoginActivity.loginA.finish();
                                    }
                                    finish();
                                }else {
                                    Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    if (LoginActivity.loginA != null) {
                                        LoginActivity.loginA.finish();
                                    }
                                    finish();
                                }
                            } else {
                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }
                                finish();
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public void onVideoDetailsPreExecuteStarted() {
        SubTitleName.clear();
        SubTitlePath.clear();
        ResolutionUrl.clear();
        ResolutionFormat.clear();
        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int statusCode, String stus, String message) {
        // _video_details_output.setThirdparty_url("https://www.youtube.com/watch?v=fqU2FzATTPY&spfreload=10");
        // _video_details_output.setThirdparty_url("https://player.vimeo.com/video/192417650?color=00ff00&badge=0");

     /*check if status code 200 then set the video url before this it check it is thirdparty url or normal if third party
        then set thirdpartyurl true here and assign the url to videourl*/

        boolean play_video = true;

        if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {

            if (_video_details_output.getStreaming_restriction().trim().equals("0")) {

                play_video = false;
            } else {
                play_video = true;
            }
        } else {
            play_video = true;
        }
        if (!play_video) {

            try {
                if (pDialog.isShowing())
                    pDialog.hide();
            } catch (IllegalArgumentException ex) {
            }

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(message);
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            dlgAlert.create().show();

            return;
        }


        if (statusCode == 200) {

            if (_video_details_output.getThirdparty_url() == null || _video_details_output.getThirdparty_url().matches("")) {
                playerModel.setIsOffline(_video_details_output.getIs_offline());
                playerModel.setDownloadStatus(_video_details_output.getDownload_status());

                /**@bishal
                 * for drm player below condition added
                 * if studio_approved_url is there in api then set the videourl from this other wise goto 2nd one
                 */

                if (_video_details_output.getStudio_approved_url() != null &&
                        !_video_details_output.getStudio_approved_url().isEmpty() &&
                        !_video_details_output.getStudio_approved_url().equals("null") &&
                        !_video_details_output.getStudio_approved_url().matches("")) {
                    LogUtil.showLog("BISHAL", "if called means  studioapproved");
                    playerModel.setVideoUrl(_video_details_output.getStudio_approved_url());
                    LogUtil.showLog("BS", "studipapprovedurl====" + playerModel.getVideoUrl());


                    if (_video_details_output.getLicenseUrl().trim() != null && !_video_details_output.getLicenseUrl().trim().isEmpty() && !_video_details_output.getLicenseUrl().trim().equals("null") && !_video_details_output.getLicenseUrl().trim().matches("")) {
                        playerModel.setLicenseUrl(_video_details_output.getLicenseUrl());
                    }
                    if (_video_details_output.getVideoUrl().trim() != null && !_video_details_output.getVideoUrl().isEmpty() && !_video_details_output.getVideoUrl().equals("null") && !_video_details_output.getVideoUrl().trim().matches("")) {
                        playerModel.setMpdVideoUrl(_video_details_output.getVideoUrl());

                    } else {
                        playerModel.setMpdVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                    }
                } else {
                    if (_video_details_output.getVideoUrl() != null || !_video_details_output.getVideoUrl().matches("")) {
                        playerModel.setVideoUrl(_video_details_output.getVideoUrl());
                        LogUtil.showLog("BISHAL", "videourl===" + playerModel.getVideoUrl());
                        playerModel.setThirdPartyPlayer(false);
                    } else {
                        //  Util.dataModel.setVideoUrl(translatedLanuage.getNoData());
                        playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

                    }
                }
            } else {
                if (_video_details_output.getThirdparty_url() != null || !_video_details_output.getThirdparty_url().matches("")) {
                    playerModel.setVideoUrl(_video_details_output.getThirdparty_url());
                    playerModel.setThirdPartyPlayer(true);

                } else {
                    //  Util.dataModel.setVideoUrl(translatedLanuage.getNoData());
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

                }
            }

            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());

            playerModel.setVideoResolution(_video_details_output.getVideoResolution());
            if (_video_details_output.getPlayed_length() != null && !_video_details_output.getPlayed_length().equals(""))
                playerModel.setPlayPos((Util.isDouble(_video_details_output.getPlayed_length())));

            SubTitleName = _video_details_output.getSubTitleName();
            SubTitleLanguage = _video_details_output.getSubTitleLanguage();

            //dependency for datamodel
            Util.dataModel.setVideoUrl(_video_details_output.getVideoUrl());
            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());
            Util.dataModel.setThirdPartyUrl(_video_details_output.getThirdparty_url());
            Util.dataModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            Util.dataModel.setChannel_id(_video_details_output.getChannel_id());
            Util.dataModel.setPreRoll(_video_details_output.getPreRoll());
            Util.dataModel.setPostRoll(_video_details_output.getPostRoll());
            Util.dataModel.setMidRoll(_video_details_output.getMidRoll());
            Util.dataModel.setAdDetails(_video_details_output.getAdDetails());
            Util.dataModel.setPlayPos(Util.isDouble(_video_details_output.getPlayed_length()));

            //player model set
            playerModel.setAdDetails(_video_details_output.getAdDetails());
            playerModel.setMidRoll(_video_details_output.getMidRoll());
            playerModel.setPostRoll(_video_details_output.getPostRoll());
            playerModel.setChannel_id(_video_details_output.getChannel_id());
            playerModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            playerModel.setPreRoll(_video_details_output.getPreRoll());
            playerModel.setSubTitleName(_video_details_output.getSubTitleName());
            playerModel.setSubTitlePath(_video_details_output.getSubTitlePath());
            playerModel.setResolutionFormat(_video_details_output.getResolutionFormat());
            playerModel.setResolutionUrl(_video_details_output.getResolutionUrl());
            playerModel.setFakeSubTitlePath(_video_details_output.getFakeSubTitlePath());
            playerModel.setVideoResolution(_video_details_output.getVideoResolution());
            FakeSubTitlePath = _video_details_output.getFakeSubTitlePath();
            playerModel.setPlayPos(Util.isDouble(_video_details_output.getPlayed_length()));
            playerModel.setSubTitleLanguage(_video_details_output.getSubTitleLanguage());


            if (playerModel.getVideoUrl() == null ||
                    playerModel.getVideoUrl().matches("")) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                }
                Util.showNoDataAlert(RegisterActivity.this);
              /*  AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();*/
            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                }


                // condition for checking if the response has third party url or not.
                if (_video_details_output.getThirdparty_url() == null ||
                        _video_details_output.getThirdparty_url().matches("")
                        ) {

                    if (mCastSession != null && mCastSession.isConnected()) {

                        ///Added for resume cast watch
                        if ((Util.dataModel.getPlayPos() * 1000) > 0) {
                            Util.dataModel.setPlayPos(Util.dataModel.getPlayPos());
                           /* Intent resumeIntent = new Intent(RegisterActivity.this, ResumePopupActivity.class);
                            startActivityForResult(resumeIntent, 1001);*/
                            Intent resumeLogin = new Intent();

                            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

                            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, playerModel.getVideoStory());
                            movieMetadata.putString(MediaMetadata.KEY_TITLE, playerModel.getVideoTitle());
                            movieMetadata.addImage(new WebImage(Uri.parse(playerModel.getPosterImageId())));
                            movieMetadata.addImage(new WebImage(Uri.parse(playerModel.getPosterImageId())));


                            String mediaContentType = "videos/mp4";

                            if (playerModel.getVideoUrl().contains(".mpd")) {
                                mediaContentType = "application/dash+xml";
                                JSONObject jsonObj = null;
                                try {
                                    jsonObj = new JSONObject();
                                    jsonObj.put("description", playerModel.getVideoTitle());
                                    jsonObj.put("licenseUrl", playerModel.getLicenseUrl());

                                    //  This Code Is Added For Video Log By Bibhu..

                                    jsonObj.put("authToken", authTokenStr);
                                    jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                                    jsonObj.put("ip_address", ipAddressStr.trim());
                                    jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                                    jsonObj.put("episode_id", playerModel.getEpisode_id());
                                    jsonObj.put("watch_status", watch_status_String);
                                    jsonObj.put("device_type", "2");
                                    jsonObj.put("log_id", "0");
                                    jsonObj.put("active_track_index", "0");

                                    if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                                        jsonObj.put("restrict_stream_id", "0");
                                        jsonObj.put("is_streaming_restriction", "1");
                                        Log.v("BIBHU4", "restrict_stream_id============1");
                                    } else {
                                        jsonObj.put("restrict_stream_id", "0");
                                        jsonObj.put("is_streaming_restriction", "0");
                                        Log.v("BIBHU4", "restrict_stream_id============0");
                                    }

                                    jsonObj.put("domain_name", BuildConfig.SERVICE_BASE_PATH.trim().substring(0, BuildConfig.SERVICE_BASE_PATH.trim().length() - 6));
                                    jsonObj.put("is_log", "1");

                                    //=====================End===================//

                                    // This code is changed according to new Video log //

                                    jsonObj.put("played_length", "0");
                                    jsonObj.put("log_temp_id", "0");
                                    jsonObj.put("resume_time", "0");
                                    jsonObj.put("seek_status", seek_status);
                                    // This  Code Is Added For Drm BufferLog By Bibhu ...

                                    jsonObj.put("resolution", "BEST");
                                    jsonObj.put("start_time", "0");
                                    jsonObj.put("end_time", "0");
                                    jsonObj.put("log_unique_id", "0");
                                    jsonObj.put("location", "0");
                                    jsonObj.put("bandwidth_log_id", "0");
                                    jsonObj.put("video_type", "mped_dash");
                                    jsonObj.put("drm_bandwidth_by_sender", "0");

                                    //====================End=====================//

                                } catch (JSONException e) {
                                }
                                List tracks = new ArrayList();
                                for (int i = 0; i < FakeSubTitlePath.size(); i++) {
                                    MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                                            MediaTrack.TYPE_TEXT)
                                            .setName(SubTitleName.get(0))
                                            .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                            .setContentId(FakeSubTitlePath.get(0))
                                            .setLanguage(SubTitleLanguage.get(0))
                                            .setContentType("text/vtt")
                                            .build();
                                    tracks.add(englishSubtitle);
                                }

                                mediaInfo = new MediaInfo.Builder(playerModel.getMpdVideoUrl().trim())
                                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                                        .setContentType(mediaContentType)
                                        .setMetadata(movieMetadata)
                                        .setCustomData(jsonObj)
                                        .setMediaTracks(tracks)
                                        .build();
                                Util.mSendingMedia = mediaInfo;


                            } else {
                                JSONObject jsonObj = null;
                                try {
                                    jsonObj = new JSONObject();
                                    jsonObj.put("description", playerModel.getVideoTitle());

                                    //  This Code Is Added For Video Log By Bibhu..

                                    jsonObj.put("authToken", authTokenStr);
                                    jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                                    jsonObj.put("ip_address", ipAddressStr.trim());
                                    jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                                    jsonObj.put("episode_id", playerModel.getEpisode_id());
                                    jsonObj.put("watch_status", watch_status_String);
                                    jsonObj.put("device_type", "2");
                                    jsonObj.put("log_id", "0");
                                    jsonObj.put("active_track_index", "0");
                                    jsonObj.put("seek_status", seek_status);

                                    jsonObj.put("played_length", "0");
                                    jsonObj.put("log_temp_id", "0");
                                    jsonObj.put("resume_time", "0");


                                    if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                                        jsonObj.put("restrict_stream_id", "0");
                                        jsonObj.put("is_streaming_restriction", "1");
                                        Log.v("BIBHU4", "restrict_stream_id============1");
                                    } else {
                                        jsonObj.put("restrict_stream_id", "0");
                                        jsonObj.put("is_streaming_restriction", "0");
                                        Log.v("BIBHU4", "restrict_stream_id============0");
                                    }

                                    jsonObj.put("domain_name", BuildConfig.SERVICE_BASE_PATH.trim().substring(0, BuildConfig.SERVICE_BASE_PATH.trim().length() - 6));
                                    jsonObj.put("is_log", "1");

                                    //=====================End===================//


                                    // This  Code Is Added For Drm BufferLog By Bibhu ...

                                    jsonObj.put("resolution", "BEST");
                                    jsonObj.put("start_time", "0");
                                    jsonObj.put("end_time", "0");
                                    jsonObj.put("log_unique_id", "0");
                                    jsonObj.put("location", "0");
                                    jsonObj.put("video_type", "");
                                    jsonObj.put("totalBandwidth", "0");

                                    //====================End=====================//

                                } catch (JSONException e) {
                                }

                                List tracks = new ArrayList();
                                for (int i = 0; i < FakeSubTitlePath.size(); i++) {
                                    MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                                            MediaTrack.TYPE_TEXT)
                                            .setName(SubTitleName.get(0))
                                            .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                            .setContentId(FakeSubTitlePath.get(0))
                                            .setLanguage(SubTitleLanguage.get(0))
                                            .setContentType("text/vtt")
                                            .build();
                                    tracks.add(englishSubtitle);
                                }

                                mediaInfo = new MediaInfo.Builder(Util.dataModel.getVideoUrl().trim())
                                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                                        .setContentType(mediaContentType)
                                        .setMetadata(movieMetadata)
                                        .setStreamDuration(15 * 1000)
                                        .setCustomData(jsonObj)
                                        .setMediaTracks(tracks)
                                        .build();
                                Util.mSendingMedia = mediaInfo;

                            }
                            resumeLogin.putExtra("yes", "2002");
                            setResult(RESULT_OK, resumeLogin);
                            finish();
                        } else {
                            Played_Length = 0;
                            watch_status_String = "start";

                            PlayThroughChromeCast();
                        }
                    } else {
                        playerModel.setThirdPartyPlayer(false);

                        final Intent playVideoIntent;
                        if (Util.dataModel.getAdNetworkId() == 3) {
                            LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                            playVideoIntent = new Intent(RegisterActivity.this, MyActivity.class);

                        } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                            if (Util.dataModel.getPlayPos() <= 0) {
                                playVideoIntent = new Intent(RegisterActivity.this, AdPlayerActivity.class);
                            } else {
                                playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);

                            }
                        } else {
                            playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);

                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (FakeSubTitlePath.size() > 0) {
                                    // This Portion Will Be changed Later.

                                    File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
                                    if (dir.isDirectory()) {
                                        String[] children = dir.list();
                                        for (int i = 0; i < children.length; i++) {
                                            new File(dir, children[i]).delete();
                                        }
                                    }

                                    progressBarHandler = new ProgressBarHandler(RegisterActivity.this);
                                    progressBarHandler.show();
                                    Download_SubTitle(FakeSubTitlePath.get(0).trim());
                                } else {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                                    playVideoIntent.putExtra("PlayerModel", playerModel);
                                    startActivity(playVideoIntent);
                                    if (LoginActivity.loginA != null) {
                                        LoginActivity.loginA.finish();
                                    }
                                    finish();
                                }

                            }
                        });
                    }
                } else {
                    final Intent playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);
                    if (LoginActivity.loginA != null) {
                        LoginActivity.loginA.finish();
                    }

                    //below part  checked at exoplayer thats why no need of checking here

                   /* playerModel.setThirdPartyPlayer(true);
                    if (playerModel.getVideoUrl().contains("://www.youtube") ||
                            playerModel.getVideoUrl().contains("://www.youtu.be")) {
                        if (playerModel.getVideoUrl().contains("live_stream?channel")) {
                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel",playerModel);
                                    startActivity(playVideoIntent);

                                }
                            });
                        } else {

                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, YouTubeAPIActivity.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel",playerModel);
                                    startActivity(playVideoIntent);


                                }
                            });

                        }
                    } else {
                        final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                playVideoIntent.putExtra("PlayerModel",playerModel);
                                startActivity(playVideoIntent);

                            }
                        });
                    }*/
                }
            }

        } else {

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                // movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            }
            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();*/
            Util.showNoDataAlert(RegisterActivity.this);
        }


    }

    private void PlayThroughChromeCast() {
        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, playerModel.getVideoStory());
        movieMetadata.putString(MediaMetadata.KEY_TITLE, playerModel.getVideoTitle());
        movieMetadata.addImage(new WebImage(Uri.parse(playerModel.getPosterImageId())));
        movieMetadata.addImage(new WebImage(Uri.parse(playerModel.getPosterImageId())));


        String mediaContentType = "videos/mp4";
        if (playerModel.getVideoUrl().contains(".mpd")) {
            mediaContentType = "application/dash+xml";
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put("description", playerModel.getVideoTitle());
                jsonObj.put("licenseUrl", playerModel.getLicenseUrl());

                //  This Code Is Added For Video Log By Bibhu..

                jsonObj.put("authToken", authTokenStr);
                jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                jsonObj.put("ip_address", ipAddressStr.trim());
                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                jsonObj.put("episode_id", playerModel.getEpisode_id());
                jsonObj.put("watch_status", watch_status_String);
                jsonObj.put("device_type", "2");
                jsonObj.put("log_id", "0");
                jsonObj.put("active_track_index", "0");

                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "1");
                    Log.v("BIBHU4", "restrict_stream_id============1");
                } else {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "0");
                    Log.v("BIBHU4", "restrict_stream_id============0");
                }

                jsonObj.put("domain_name", BuildConfig.SERVICE_BASE_PATH.trim().substring(0, BuildConfig.SERVICE_BASE_PATH.trim().length() - 6));
                jsonObj.put("is_log", "1");

                //=====================End===================//

                // This code is changed according to new Video log //

                jsonObj.put("played_length", "0");
                jsonObj.put("log_temp_id", "0");
                jsonObj.put("resume_time", "0");
                jsonObj.put("seek_status", seek_status);
                // This  Code Is Added For Drm BufferLog By Bibhu ...

                jsonObj.put("resolution", "BEST");
                jsonObj.put("start_time", "0");
                jsonObj.put("end_time", "0");
                jsonObj.put("log_unique_id", "0");
                jsonObj.put("location", "0");
                jsonObj.put("bandwidth_log_id", "0");
                jsonObj.put("video_type", "mped_dash");
                jsonObj.put("drm_bandwidth_by_sender", "0");

                //====================End=====================//

            } catch (JSONException e) {
            }
            List tracks = new ArrayList();
            for (int i = 0; i < FakeSubTitlePath.size(); i++) {
                MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                        MediaTrack.TYPE_TEXT)
                        .setName(SubTitleName.get(0))
                        .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                        .setContentId(FakeSubTitlePath.get(0))
                        .setLanguage(SubTitleLanguage.get(0))
                        .setContentType("text/vtt")
                        .build();
                tracks.add(englishSubtitle);
            }

            mediaInfo = new MediaInfo.Builder(playerModel.getMpdVideoUrl().trim())
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType(mediaContentType)
                    .setMetadata(movieMetadata)
                    .setCustomData(jsonObj)
                    .setMediaTracks(tracks)
                    .build();
            mSelectedMedia = mediaInfo;


            togglePlayback();
        } else {
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put("description", playerModel.getVideoTitle());

                //  This Code Is Added For Video Log By Bibhu..

                jsonObj.put("authToken", authTokenStr);
                jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                jsonObj.put("ip_address", ipAddressStr.trim());
                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                jsonObj.put("episode_id", playerModel.getEpisode_id());
                jsonObj.put("watch_status", watch_status_String);
                jsonObj.put("device_type", "2");
                jsonObj.put("log_id", "0");
                jsonObj.put("active_track_index", "0");
                jsonObj.put("seek_status", seek_status);

                jsonObj.put("played_length", "0");
                jsonObj.put("log_temp_id", "0");
                jsonObj.put("resume_time", "0");


                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "1");
                    Log.v("BIBHU4", "restrict_stream_id============1");
                } else {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "0");
                    Log.v("BIBHU4", "restrict_stream_id============0");
                }

                jsonObj.put("domain_name", BuildConfig.SERVICE_BASE_PATH.trim().substring(0, BuildConfig.SERVICE_BASE_PATH.trim().length() - 6));
                jsonObj.put("is_log", "1");

                //=====================End===================//


                // This  Code Is Added For Drm BufferLog By Bibhu ...

                jsonObj.put("resolution", "BEST");
                jsonObj.put("start_time", "0");
                jsonObj.put("end_time", "0");
                jsonObj.put("log_unique_id", "0");
                jsonObj.put("location", "0");
                jsonObj.put("video_type", "");
                jsonObj.put("totalBandwidth", "0");

                //====================End=====================//

            } catch (JSONException e) {
            }

            List tracks = new ArrayList();
            for (int i = 0; i < FakeSubTitlePath.size(); i++) {
                MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                        MediaTrack.TYPE_TEXT)
                        .setName(SubTitleName.get(0))
                        .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                        .setContentId(FakeSubTitlePath.get(0))
                        .setLanguage(SubTitleLanguage.get(0))
                        .setContentType("text/vtt")
                        .build();
                tracks.add(englishSubtitle);
            }

            mediaInfo = new MediaInfo.Builder(Util.dataModel.getVideoUrl().trim())
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType(mediaContentType)
                    .setMetadata(movieMetadata)
                    .setStreamDuration(15 * 1000)
                    .setCustomData(jsonObj)
                    .setMediaTracks(tracks)
                    .build();
            mSelectedMedia = mediaInfo;


            togglePlayback();
        }
    }


    @Override
    public void onGetValidateUserPreExecuteStarted() {
        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message) {
        {


            String Subscription_Str = preferenceManager.getIsSubscribedFromPref();
            String validUserStr = validateUserOutput.getValiduser_str();

            if (validateUserOutput == null) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }
                                finish();
                            }
                        });
                dlgAlert.create().show();
            } else if (status <= 0) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }
                                finish();
                            }
                        });
                dlgAlert.create().show();
            }

            if (status > 0) {
                if (status == 427) {

                    try {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }
                    } catch (IllegalArgumentException ex) {
                        status = 0;
                    }
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                    if (message != null && message.equalsIgnoreCase("")) {
                        dlgAlert.setMessage(message);
                    } else {
                        dlgAlert.setMessage(languagePreference.getTextofLanguage(CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY, DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY));

                    }
                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    onBackPressed();
                                }
                            });
                    dlgAlert.create().show();
                } else if (status == 429 || status == 430) {

                    new MonetizationHandler(RegisterActivity.this).handle429OR430statusCod(validUserStr, message, Subscription_Str);


                } else if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                    if (Util.dataModel.getContentTypesId() == 3) {
                        // Show Popup
                        ShowPpvPopUp();
                    } else {
                        // Go to ppv Payment
                        payment_for_single_part();
                    }
                } else if (planId.equals("1") && Subscription_Str.equals("0")) {
                    if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                        Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        if (LoginActivity.loginA != null) {
                            LoginActivity.loginA.finish();
                        }
                        finish();
                        overridePendingTransition(0, 0);
                    }else if (Util.dataModel.getIsConverted() == 0){
                        Util.showNoDataAlert(RegisterActivity.this);
                    }else {
                        if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                            GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                            getVideoDetailsInput.setAuthToken(authTokenStr);
                            getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                            getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                            getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                            getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                            getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                            asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
                            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                        } else {
                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }
                } else if (Util.dataModel.getIsConverted() == 0) {
                    Util.showNoDataAlert(RegisterActivity.this);
                  /*  AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    onBackPressed();
                                }
                            });
                    dlgAlert.create().show();*/
                } else {
                    if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                    } else {
                        Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }

            }

        }
    }


//    private class AsynRegistrationDetails extends AsyncTask<Void, Void, Void> {
//
//        ProgressBarHandler pDialog;
//
//        int status;
//        String responseStr;
//        String registrationIdStr;
//        String isSubscribedStr;
//        String loginHistoryIdStr;
//        JSONObject myJson = null;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            String urlRouteList = Util.rootUrl().trim()+Util.registrationUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("name", regNameStr);
//                httppost.addHeader("email", regEmailStr);
//                httppost.addHeader("password", regPasswordStr);
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("lang_code",Util.getTextofLanguage(RegisterActivity.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//                httppost.addHeader("custom_country", Selected_Country_Id);
//                httppost.addHeader("custom_languages",Selected_Language_Id);
//
//                // Added For FCM
//
//                httppost.addHeader("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//                httppost.addHeader("google_id", Util.getTextofLanguage(RegisterActivity.this,Util.GOOGLE_FCM_TOKEN,Util.DEFAULT_GOOGLE_FCM_TOKEN));
//                httppost.addHeader("device_type","1");
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            status = 0;
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                    status = 0;
//                    e.printStackTrace();
//                }
//                if(responseStr!=null){
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                    registrationIdStr = myJson.optString("id");
//                    isSubscribedStr = myJson.optString("isSubscribed");
//                    UniversalIsSubscribed = isSubscribedStr;
//                    loginHistoryIdStr = myJson.optString("login_history_id");
//
//                }
//
//            }
//            catch (Exception e) {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//                status = 0;
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//            try {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//            } catch (IllegalArgumentException ex) {
//                status = 0;
//
//            }
//            if (responseStr == null) {
//                status = 0;
//
//            }
//            if (status == 0) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_REGISTRATION, Util.DEFAULT_ERROR_IN_REGISTRATION));
//                dlgAlert.setTitle(Util.getTextofLanguage(RegisterActivity.this,Util.FAILURE,Util.DEFAULT_FAILURE));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(Util.getTextofLanguage(RegisterActivity.this,Util.BUTTON_OK,Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status > 0) {
//
//                if (status == 422) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(EMAIL_EXISTS, Util.DEFAULT_EMAIL_EXISTS));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    dlgAlert.create().show();
//
//                } else if (status == 200) {
//
//                    String displayNameStr = myJson.optString("display_name");
//                    String emailFromApiStr = myJson.optString("email");
//                    String profileImageStr = myJson.optString("profile_image");
//
//
//                    // Take appropiate step here.
//
//
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("PREFS_LOGGEDIN_KEY", "1");
//                    editor.putString("PREFS_LOGGEDIN_ID_KEY", registrationIdStr);
//                    editor.putString("PREFS_LOGGEDIN_PASSWORD_KEY", editPassword.getText().toString().trim());
//                    editor.putString("PREFS_LOGIN_EMAIL_ID_KEY", emailFromApiStr);
//                    editor.putString("PREFS_LOGIN_DISPLAY_NAME_KEY", displayNameStr);
//                    editor.putString("PREFS_LOGIN_PROFILE_IMAGE_KEY", profileImageStr);
//                    editor.putString("PREFS_LOGIN_ISSUBSCRIBED_KEY", isSubscribedStr);
//                    editor.putString("PREFS_LOGIN_HISTORYID_KEY", loginHistoryIdStr);
//
//                    Date todayDate = new Date();
//                    String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
//                    editor.putString("date", todayStr.trim());
//                    editor.commit();
//
//
//                    if (Util.checkNetwork(RegisterActivity.this) == true) {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } else {
//                        Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                    }
//
//                    if (Util.checkNetwork(RegisterActivity.this) == true) {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//
//                    }
//
//
//                    if(Util.getTextofLanguage(RegisterActivity.this,Util.IS_RESTRICT_DEVICE,Util.DEFAULT_IS_RESTRICT_DEVICE).trim().equals("1"))
//                    {
//                        // Call For Check Api.
//                        AsynCheckDevice asynCheckDevice = new AsynCheckDevice();
//                        asynCheckDevice.executeOnExecutor(threadPoolExecutor);
//                    }
//                    else {
//                        if(Util.check_for_subscription == 1)
//                        {
//                            // Go for subscription
//
//                            if (Util.checkNetwork(RegisterActivity.this) == true) {
//                                if (Util.dataModel.getIsFreeContent() == 1) {
//                                    asynLoadVideoUrls = new AsynLoadVideoUrls();
//                                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                                } else {
//                                    asynValidateUserDetails = new AsynValidateUserDetails();
//                                    asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
//                                }
//                            } else {
//                                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                        else {
//
//                            if (planId.equals("1") && isSubscribedStr.equals("0")) {
//                                Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(in);
//                                finish();
//                            }
//                        }
//                    }
//
//                }
// }

//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(RegisterActivity.this);
//            pDialog.show();
//        }
//
//
//    }


//    private class AsynLoadVideoUrls extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//        String responseStr;
//        int statusCode;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.loadVideoUrl.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("content_uniq_id", Util.dataModel.getMovieUniqueId().trim());
//                httppost.addHeader("stream_uniq_id", Util.dataModel.getStreamUniqueId().trim());
//                httppost.addHeader("internet_speed", MainActivity.internetSpeed.trim());
//                httppost.addHeader("user_id", pref.getString("PREFS_LOGGEDIN_ID_KEY", null));
//
//                // Execute HTTP Post Request
//                try {
//
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            responseStr = "0";
//                            Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                    responseStr = "0";
//                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                    e.printStackTrace();
//                }
//
//                /**** subtitles************/
//
//                JSONArray SubtitleJosnArray = null;
//                JSONArray ResolutionJosnArray = null;
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    SubtitleJosnArray = myJson.optJSONArray("subTitle");
//                    ResolutionJosnArray = myJson.optJSONArray("videoDetails");
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//                }
//             /*   JSONObject myJson =null;
//                if(responseStr!=null){
//                    myJson = new JSONObject(responseStr);
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//                }*/
//
//                if (statusCode >= 0) {
//                    if (statusCode == 200) {
//                        if (Util.dataModel.getThirdPartyUrl().matches("") || Util.dataModel.getThirdPartyUrl().equalsIgnoreCase(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA))) {
//                            if ((myJson.has("videoUrl")) && myJson.getString("videoUrl").trim() != null && !myJson.getString("videoUrl").trim().isEmpty() && !myJson.getString("videoUrl").trim().equals("null") && !myJson.getString("videoUrl").trim().matches("")) {
//                                Util.dataModel.setVideoUrl(myJson.getString("videoUrl"));
//
//
//                            } else {
//                                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//
//                            }
//                        } else {
//                            if ((myJson.has("thirdparty_url")) && myJson.getString("thirdparty_url").trim() != null && !myJson.getString("thirdparty_url").trim().isEmpty() && !myJson.getString("thirdparty_url").trim().equals("null") && !myJson.getString("thirdparty_url").trim().matches("")) {
//                                Util.dataModel.setVideoUrl(myJson.getString("thirdparty_url"));
//
//                            } else {
//                                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//
//                            }
//                        }
//                        if ((myJson.has("videoResolution")) && myJson.getString("videoResolution").trim() != null && !myJson.getString("videoResolution").trim().isEmpty() && !myJson.getString("videoResolution").trim().equals("null") && !myJson.getString("videoResolution").trim().matches("")) {
//                            Util.dataModel.setVideoResolution(myJson.getString("videoResolution"));
//
//                        }
//                        if ((myJson.has("played_length")) && myJson.getString("played_length").trim() != null && !myJson.getString("played_length").trim().isEmpty() && !myJson.getString("played_length").trim().equals("null") && !myJson.getString("played_length").trim().matches("")) {
//                            Util.dataModel.setPlayPos(Util.isDouble(myJson.getString("played_length")));
//
//
//                        }
//                        /**** subtitles************/
//                        if (SubtitleJosnArray != null) {
//                            if (SubtitleJosnArray.length() > 0) {
//                                for (int i = 0; i < SubtitleJosnArray.length(); i++) {
//                                    SubTitleName.add(SubtitleJosnArray.getJSONObject(i).optString("language").trim());
//                                    FakeSubTitlePath.add(SubtitleJosnArray.getJSONObject(i).optString("url").trim());
//
//
//                                }
//                            }
//                        }
//                        /**** subtitles************/
//
//                        if (ResolutionJosnArray != null) {
//                            if (ResolutionJosnArray.length() > 0) {
//                                for (int i = 0; i < ResolutionJosnArray.length(); i++) {
//                                    if ((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()).equals("BEST")) {
//                                        ResolutionFormat.add(ResolutionJosnArray.getJSONObject(i).optString("resolution").trim());
//                                    } else {
//                                        ResolutionFormat.add((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()) + "p");
//                                    }
//
//                                    ResolutionUrl.add(ResolutionJosnArray.getJSONObject(i).optString("url").trim());
//
//                                }
//                            }
//                        }
//
//                    }
//
//                } else {
//
//                    responseStr = "0";
//                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                }
//            } catch (JSONException e1) {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//                responseStr = "0";
//                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                e1.printStackTrace();
//            } catch (Exception e) {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//                responseStr = "0";
//                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//
//                e.printStackTrace();
//
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
//
//      /*  try{
//            if(pDialog.isShowing())
//                pDialog.dismiss();
//        }
//        catch(IllegalArgumentException ex)
//        {
//            responseStr = "0";
//            movieVideoUrlStr = getResources().getString(R.string.no_data_str);
//        }*/
//            if (responseStr == null) {
//                responseStr = "0";
//                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//
//                //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
//            }
//
//            if ((responseStr.trim().equalsIgnoreCase("0"))) {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                    // movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
//                }
//                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                finish();
//                                overridePendingTransition(0, 0);
//                            }
//                        });
//                dlgAlert.create().show();
//            } else {
//
//                if (Util.dataModel.getVideoUrl() == null) {
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    finish();
//                                    overridePendingTransition(0, 0);
//                                }
//                            });
//                    dlgAlert.create().show();
//                } else if (Util.dataModel.getVideoUrl().matches("") || Util.dataModel.getVideoUrl().equalsIgnoreCase(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA))) {
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    finish();
//                                    overridePendingTransition(0, 0);
//                                }
//                            });
//                    dlgAlert.create().show();
//                } else {
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA));
//                    }
//                    if (Util.dataModel.getThirdPartyUrl().matches("") || Util.dataModel.getThirdPartyUrl().equalsIgnoreCase(languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA))) {
//                        /*if (mCastSession != null && mCastSession.isConnected()) {
//
//
//                            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
//
//                            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, Util.dataModel.getVideoStory());
//                            movieMetadata.putString(MediaMetadata.KEY_TITLE, Util.dataModel.getVideoTitle());
//                            movieMetadata.addImage(new WebImage(Uri.parse(Util.dataModel.getPosterImageId())));
//                            movieMetadata.addImage(new WebImage(Uri.parse(Util.dataModel.getPosterImageId())));
//                            JSONObject jsonObj = null;
//                            try {
//                                jsonObj = new JSONObject();
//                                jsonObj.put("description", Util.dataModel.getVideoTitle());
//                            } catch (JSONException e) {
//                            }
//
//                            mediaInfo = new MediaInfo.Builder(Util.dataModel.getVideoUrl().trim())
//                                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
//                                    .setContentType("videos/mp4")
//                                    .setMetadata(movieMetadata)
//                                    .setStreamDuration(15 * 1000)
//                                    .setCustomData(jsonObj)
//                                    .build();
//                            mSelectedMedia = mediaInfo;
//
//
//                            togglePlayback();
//                            removeFocusFromViews();
//                            finish();
//                            overridePendingTransition(0, 0);
//                        }*/
//                        {
//                            final Intent playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);
//                           /* runOnUiThread(new Runnable() {
//                                public void run() {
//                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                    removeFocusFromViews();
//
//                                    startActivity(playVideoIntent);
//                                    finish();
//                                    overridePendingTransition(0, 0);
//
//
//                                }
//                            });*/
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    if (FakeSubTitlePath.size() > 0) {
//                                        // This Portion Will Be changed Later.
//
//                                        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
//                                        if (dir.isDirectory()) {
//                                            String[] children = dir.list();
//                                            for (int i = 0; i < children.length; i++) {
//                                                new File(dir, children[i]).delete();
//                                            }
//                                        }
//
//                                        progressBarHandler = new ProgressBarHandler(RegisterActivity.this);
//                                        progressBarHandler.show();
//                                        Download_SubTitle(FakeSubTitlePath.get(0).trim());
//                                    } else {
//                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        playVideoIntent.putExtra("SubTitleName", SubTitleName);
//                                        playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
//                                        playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
//                                        playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);
//                                        startActivity(playVideoIntent);
//                                        removeFocusFromViews();
//                                        finish();
//                                        overridePendingTransition(0, 0);
//                                    }
//
//                                }
//                            });
//                        }
//                    } else {
//                        if (Util.dataModel.getVideoUrl().contains("://www.youtube") || Util.dataModel.getVideoUrl().contains("://www.youtu.be")) {
//                            if (Util.dataModel.getVideoUrl().contains("live_stream?channel")) {
//                                final Intent playVideoIntent = new Intent(RegisterActivity.this, ThirdPartyPlayer.class);
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        removeFocusFromViews();
//
//                                        startActivity(playVideoIntent);
//                                        finish();
//                                        overridePendingTransition(0, 0);
//
//                                    }
//                                });
//                            } else {
//
//                                final Intent playVideoIntent = new Intent(RegisterActivity.this, YouTubeAPIActivity.class);
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        removeFocusFromViews();
//
//                                        startActivity(playVideoIntent);
//                                        finish();
//                                        overridePendingTransition(0, 0);
//
//
//                                    }
//                                });
//
//                            }
//                        } else {
//                            final Intent playVideoIntent = new Intent(RegisterActivity.this, ThirdPartyPlayer.class);
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                    removeFocusFromViews();
//
//                                    startActivity(playVideoIntent);
//                                    finish();
//                                    overridePendingTransition(0, 0);
//
//                                }
//                            });
//                        }
//                    }
//                }
//
//
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            SubTitleName.clear();
//            SubTitlePath.clear();
//            ResolutionUrl.clear();
//            ResolutionFormat.clear();
//            pDialog = new ProgressBarHandler(RegisterActivity.this);
//            pDialog.show();
//        }
//
//
//    }

//
//    private class AsynValidateUserDetails extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//
//        int status;
//        String validUserStr;
//        String userMessage;
//        String responseStr;
//        String loggedInIdStr;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            if (pref != null) {
//                loggedInIdStr = pref.getString("PREFS_LOGGEDIN_ID_KEY", null);
//            }
//
//
//            String urlRouteList = Util.rootUrl().trim() + Util.userValidationUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("user_id", loggedInIdStr.trim());
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("movie_id", Util.dataModel.getMovieUniqueId().trim());
//                httppost.addHeader("purchase_type", Util.dataModel.getPurchase_type());
//                httppost.addHeader("season_id", Util.dataModel.getSeason_id());
//                httppost.addHeader("episode_id", Util.dataModel.getEpisode_id());
//                SharedPreferences countryPref = getSharedPreferences(Util.COUNTRY_PREF, 0); // 0 - for private mode
//             /*   if (countryPref != null) {
//                    String countryCodeStr = countryPref.getString("countryCode", null);
//                    httppost.addHeader("country", countryCodeStr);
//                }else{
//                    httppost.addHeader("country", "IN");
//
//                }    */
//
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    StringBuilder sb = new StringBuilder();
//
//                    BufferedReader reader =
//                            new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
//                    String line = null;
//
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line);
//                    }
//
//                    responseStr = sb.toString();
//
//
//                } catch (final org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            status = 0;
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                    status = 0;
//
//                    e.printStackTrace();
//                }
//                if (responseStr != null) {
//                    JSONObject myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                    validUserStr = myJson.optString("status");
//                    userMessage = myJson.optString("msg");
//
//                }
//
//            } catch (Exception e) {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//                status = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//
//            String Subscription_Str = pref.getString("PREFS_LOGIN_ISSUBSCRIBED_KEY", "0");
//
//
//            try {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//            } catch (IllegalArgumentException ex) {
//                status = 0;
//            }
//
//            if (responseStr == null) {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, Util.DEFAULT_NO_DETAILS_AVAILABLE));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(in);
//                                finish();
//                            }
//                        });
//                dlgAlert.create().show();
//            } else if (status <= 0) {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, Util.DEFAULT_NO_DETAILS_AVAILABLE));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(in);
//                                finish();
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//
//            if (status > 0) {
//                if (status == 427) {
//
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        status = 0;
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
//                    if (userMessage != null && userMessage.equalsIgnoreCase("")) {
//                        dlgAlert.setMessage(userMessage);
//                    } else {
//                        dlgAlert.setMessage(languagePreference.getTextofLanguage(CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY, Util.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY));
//
//                    }
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    onBackPressed();
//                                }
//                            });
//                    dlgAlert.create().show();
//                } else if (status == 429) {
//
//                    if (validUserStr != null) {
//                        try {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                        } catch (IllegalArgumentException ex) {
//                            status = 0;
//                        }
//
//                        if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
//                            if (Util.checkNetwork(RegisterActivity.this) == true) {
//                                GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
//                                getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
//                                getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
//                                getVideoDetailsInput.setUser_id(pref.getString("PREFS_LOGGEDIN_ID_KEY", null));
//                                asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput,RegisterActivity.this,RegisterActivity.this);
//                                asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                            } else {
//                                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                                onBackPressed();
//                            }
//                        } else {
//
//                            if ((userMessage.trim().equalsIgnoreCase("Unpaid")) || (userMessage.trim().matches("Unpaid")) || (userMessage.trim().equals("Unpaid"))) {
//                                if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
//                                    if (Util.dataModel.getContentTypesId() == 3) {
//                                        // Show Popup
//                                        ShowPpvPopUp();
//                                    } else {
//                                        // Go to ppv Payment
//                                        payment_for_single_part();
//                                    }
//                                } else if (planId.equals("1") && Subscription_Str.equals("0")) {
//                                    Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    if (Util.dataModel.getContentTypesId() == 3) {
//                                        // Show Popup
//                                        ShowPpvPopUp();
//                                    } else {
//                                        // Go to ppv Payment
//                                        payment_for_single_part();
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//
//                } else if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
//                    if (Util.dataModel.getContentTypesId() == 3) {
//                        // Show Popup
//                        ShowPpvPopUp();
//                    } else {
//                        // Go to ppv Payment
//                        payment_for_single_part();
//                    }
//                } else if (planId.equals("1") && Subscription_Str.equals("0")) {
//                    Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(0, 0);
//
//                } else if (Util.dataModel.getIsConverted() == 0) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    onBackPressed();
//                                }
//                            });
//                    dlgAlert.create().show();
//                } else {
//                    if (Util.checkNetwork(RegisterActivity.this) == true) {
//                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                        getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
//                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
//                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
//                        getVideoDetailsInput.setUser_id(pref.getString("PREFS_LOGGEDIN_ID_KEY", null));
//                        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput,RegisterActivity.this,RegisterActivity.this);
//                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                    } else {
//                        Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                        onBackPressed();
//                    }
//                }
//
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(RegisterActivity.this);
//            pDialog.show();
//
//        }
//
//
//    }

    private void payment_for_single_part() {
        {

            try {
                if (Util.currencyModel.getCurrencySymbol() == null) {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
                finish();
            }

            if (Util.dataModel.getIsAPV() == 1) {
                priceForUnsubscribedStr = Util.apvModel.getAPVPriceForUnsubscribedStr();
                priceFosubscribedStr = Util.apvModel.getAPVPriceForsubscribedStr();

            } else {
                priceForUnsubscribedStr = Util.ppvModel.getPPVPriceForUnsubscribedStr();
                priceFosubscribedStr = Util.ppvModel.getPPVPriceForsubscribedStr();
            }

            Util.selected_episode_id = "0";
            Util.selected_season_id = "0";

            LogUtil.showLog("MUVI", "priceFosubscribedStr=" + priceFosubscribedStr);
            LogUtil.showLog("MUVI", "priceForUnsubscribedStr=" + priceForUnsubscribedStr);

            final Intent showPaymentIntent = new Intent(RegisterActivity.this, PPvPaymentInfoActivity.class);
            showPaymentIntent.putExtra("muviuniqueid", Util.dataModel.getMovieUniqueId().trim());
            showPaymentIntent.putExtra("episodeStreamId", Util.dataModel.getStreamUniqueId().trim());
            showPaymentIntent.putExtra("content_types_id", Util.dataModel.getContentTypesId());
            showPaymentIntent.putExtra("movieThirdPartyUrl", Util.dataModel.getThirdPartyUrl());
            showPaymentIntent.putExtra("planUnSubscribedPrice", priceForUnsubscribedStr);
            showPaymentIntent.putExtra("planSubscribedPrice", priceFosubscribedStr);
            showPaymentIntent.putExtra("currencyId", Util.currencyModel.getCurrencyId());
            showPaymentIntent.putExtra("currencyCountryCode", Util.currencyModel.getCurrencyCode());
            showPaymentIntent.putExtra("currencySymbol", Util.currencyModel.getCurrencySymbol());
            showPaymentIntent.putExtra("showName", Util.dataModel.getVideoTitle());
            showPaymentIntent.putExtra("isPPV", Util.dataModel.getIsPPV());
            showPaymentIntent.putExtra("isAPV", Util.dataModel.getIsAPV());
            showPaymentIntent.putExtra("PlayerModel", playerModel);
            if (Util.dataModel.getIsAPV() == 1) {
                showPaymentIntent.putExtra("isConverted", 0);
            } else {
                showPaymentIntent.putExtra("isConverted", 1);
            }

            showPaymentIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(showPaymentIntent);
            if (LoginActivity.loginA != null) {
                LoginActivity.loginA.finish();
            }
            finish();
        }
    }

    private void ShowPpvPopUp() {
        {

            try {
                if (Util.currencyModel.getCurrencySymbol() == null) {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
                finish();
            }


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            LayoutInflater inflater = (LayoutInflater) RegisterActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View convertView = inflater.inflate(R.layout.activity_ppv_popup, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("");

            final RadioButton completeRadioButton = (RadioButton) convertView.findViewById(R.id.completeRadioButton);
            final RadioButton seasonRadioButton = (RadioButton) convertView.findViewById(R.id.seasonRadioButton);
            final RadioButton episodeRadioButton = (RadioButton) convertView.findViewById(R.id.episodeRadioButton);
            TextView episodePriceTextView = (TextView) convertView.findViewById(R.id.episodePriceTextView);
            TextView seasonPriceTextView = (TextView) convertView.findViewById(R.id.seasonPriceTextView);
            TextView completePriceTextView = (TextView) convertView.findViewById(R.id.completePriceTextView);
            Button payNowButton = (Button) convertView.findViewById(R.id.payNowButton);


            if (Util.dataModel.getIsAPV() == 1) {
                if (Util.apvModel.getIsEpisode() == 1) {
                    episodeRadioButton.setVisibility(View.VISIBLE);
                    episodePriceTextView.setVisibility(View.VISIBLE);
                } else {
                    episodeRadioButton.setVisibility(View.GONE);
                    episodePriceTextView.setVisibility(View.GONE);
                }
                if (Util.apvModel.getIsSeason() == 1) {
                    seasonRadioButton.setVisibility(View.VISIBLE);
                    seasonPriceTextView.setVisibility(View.VISIBLE);
                } else {
                    seasonRadioButton.setVisibility(View.GONE);
                    seasonPriceTextView.setVisibility(View.GONE);
                }
                if (Util.apvModel.getIsShow() == 1) {
                    completeRadioButton.setVisibility(View.VISIBLE);
                    completePriceTextView.setVisibility(View.VISIBLE);
                } else {
                    completeRadioButton.setVisibility(View.GONE);
                    completePriceTextView.setVisibility(View.GONE);
                }
            } else {
                if (Util.ppvModel.getIsEpisode() == 1) {
                    episodeRadioButton.setVisibility(View.VISIBLE);
                    episodePriceTextView.setVisibility(View.VISIBLE);
                } else {
                    episodeRadioButton.setVisibility(View.GONE);
                    episodePriceTextView.setVisibility(View.GONE);
                }
                if (Util.ppvModel.getIsSeason() == 1) {
                    seasonRadioButton.setVisibility(View.VISIBLE);
                    seasonPriceTextView.setVisibility(View.VISIBLE);
                } else {
                    seasonRadioButton.setVisibility(View.GONE);
                    seasonPriceTextView.setVisibility(View.GONE);
                }
                if (Util.ppvModel.getIsShow() == 1) {
                    completeRadioButton.setVisibility(View.VISIBLE);
                    completePriceTextView.setVisibility(View.VISIBLE);
                } else {
                    completeRadioButton.setVisibility(View.GONE);
                    completePriceTextView.setVisibility(View.GONE);
                }
            }


            completeRadioButton.setText("  " + Util.dataModel.getEpisode_title().trim() + " Complete Season ");
            seasonRadioButton.setText("  " + Util.dataModel.getEpisode_title().trim() + " Season " + Util.dataModel.getEpisode_series_no().trim() + " ");
            episodeRadioButton.setText("  " + Util.dataModel.getEpisode_title().trim() + " S" + Util.dataModel.getEpisode_series_no().trim() + " E " + Util.dataModel.getEpisode_no().trim() + " ");


            if (Util.dataModel.getIsAPV() == 1) {
                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvEpisodeUnsubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvSeasonUnsubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvShowUnsubscribedStr());
            } else {
                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvEpisodeUnsubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvSeasonUnsubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvShowUnsubscribedStr());
            }


            alert = alertDialog.show();
            completeRadioButton.setChecked(true);


            if (completeRadioButton.isChecked() == true) {
                if (Util.dataModel.getIsAPV() == 1) {
                    priceForUnsubscribedStr = Util.apvModel.getApvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.apvModel.getApvShowSubscribedStr();

                } else {
                    priceForUnsubscribedStr = Util.ppvModel.getPpvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.ppvModel.getPpvShowSubscribedStr();
                }
            }


       /*
        completeRadioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                episodeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(false);
                if (Util.dataModel.getIsAPV() == 1) {
                    priceForUnsubscribedStr = Util.apvModel.getApvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.apvModel.getApvShowSubscribedStr();

                } else {
                    priceForUnsubscribedStr = Util.ppvModel.getPpvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.ppvModel.getPpvShowSubscribedStr();
                }

            }

        });*/


            // Changed later
            if (completeRadioButton.getVisibility() == View.VISIBLE) {

                completeRadioButton.setChecked(true);
                episodeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(false);

                if (Util.dataModel.getIsAPV() == 1) {
                    priceForUnsubscribedStr = Util.apvModel.getApvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.apvModel.getApvShowSubscribedStr();

                } else {
                    priceForUnsubscribedStr = Util.ppvModel.getPpvShowUnsubscribedStr();
                    priceFosubscribedStr = Util.ppvModel.getPpvShowSubscribedStr();
                }

            } else {
                if (seasonRadioButton.getVisibility() == View.VISIBLE) {

                    completeRadioButton.setChecked(false);
                    seasonRadioButton.setChecked(true);
                    episodeRadioButton.setChecked(false);

                    if (Util.dataModel.getIsAPV() == 1) {
                        priceForUnsubscribedStr = Util.apvModel.getApvSeasonUnsubscribedStr();
                        priceFosubscribedStr = Util.apvModel.getApvSeasonSubscribedStr();

                    } else {
                        priceForUnsubscribedStr = Util.ppvModel.getPpvSeasonUnsubscribedStr();
                        priceFosubscribedStr = Util.ppvModel.getPpvSeasonSubscribedStr();
                    }

                } else {
                    completeRadioButton.setChecked(false);
                    seasonRadioButton.setChecked(false);
                    episodeRadioButton.setChecked(true);

                    if (Util.dataModel.getIsAPV() == 1) {
                        priceForUnsubscribedStr = Util.apvModel.getApvEpisodeUnsubscribedStr();
                        priceFosubscribedStr = Util.apvModel.getApvEpisodeSubscribedStr();

                    } else {
                        priceForUnsubscribedStr = Util.ppvModel.getPpvEpisodeUnsubscribedStr();
                        priceFosubscribedStr = Util.ppvModel.getPpvEpisodeSubscribedStr();
                    }


                }
            }

            ///////////////////////=====================////////////////////////

            completeRadioButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    episodeRadioButton.setChecked(false);
                    seasonRadioButton.setChecked(false);
                    if (Util.dataModel.getIsAPV() == 1) {
                        priceForUnsubscribedStr = Util.apvModel.getApvShowUnsubscribedStr();
                        priceFosubscribedStr = Util.apvModel.getApvShowSubscribedStr();

                    } else {
                        priceForUnsubscribedStr = Util.ppvModel.getPpvShowUnsubscribedStr();
                        priceFosubscribedStr = Util.ppvModel.getPpvShowSubscribedStr();
                    }


                }

            });


            episodeRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    completeRadioButton.setChecked(false);
                    seasonRadioButton.setChecked(false);

                    if (Util.dataModel.getIsAPV() == 1) {
                        priceForUnsubscribedStr = Util.apvModel.getApvEpisodeUnsubscribedStr();
                        priceFosubscribedStr = Util.apvModel.getApvEpisodeSubscribedStr();

                    } else {
                        priceForUnsubscribedStr = Util.ppvModel.getPpvEpisodeUnsubscribedStr();
                        priceFosubscribedStr = Util.ppvModel.getPpvEpisodeSubscribedStr();
                    }

                }
            });
            seasonRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    episodeRadioButton.setChecked(false);
                    completeRadioButton.setChecked(false);
                    if (Util.dataModel.getIsAPV() == 1) {
                        priceForUnsubscribedStr = Util.apvModel.getApvSeasonUnsubscribedStr();
                        priceFosubscribedStr = Util.apvModel.getApvSeasonSubscribedStr();

                    } else {
                        priceForUnsubscribedStr = Util.ppvModel.getPpvSeasonUnsubscribedStr();
                        priceFosubscribedStr = Util.ppvModel.getPpvSeasonSubscribedStr();
                    }

                }
            });

            payNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (completeRadioButton.isChecked()) {
                        Util.selected_episode_id = "0";
                        Util.selected_season_id = "0";
                    } else if (seasonRadioButton.isChecked()) {
                        Util.selected_episode_id = "0";

                    } else {
                        Util.selected_episode_id = Util.dataModel.getStreamUniqueId();
                        Util.selected_season_id = Util.dataModel.getEpisode_series_no();
                    }


                    alert.dismiss();
                    final Intent showPaymentIntent = new Intent(RegisterActivity.this, PPvPaymentInfoActivity.class);
                    showPaymentIntent.putExtra("muviuniqueid", Util.dataModel.getMovieUniqueId().trim());
                    showPaymentIntent.putExtra("episodeStreamId", Util.dataModel.getStreamUniqueId().trim());
                    showPaymentIntent.putExtra("content_types_id", Util.dataModel.getContentTypesId());
                    showPaymentIntent.putExtra("movieThirdPartyUrl", Util.dataModel.getThirdPartyUrl());
                    showPaymentIntent.putExtra("planUnSubscribedPrice", priceForUnsubscribedStr);
                    showPaymentIntent.putExtra("planSubscribedPrice", priceFosubscribedStr);
                    showPaymentIntent.putExtra("currencyId", Util.currencyModel.getCurrencyId());
                    showPaymentIntent.putExtra("currencyCountryCode", Util.currencyModel.getCurrencyCode());
                    showPaymentIntent.putExtra("currencySymbol", Util.currencyModel.getCurrencySymbol());
                    showPaymentIntent.putExtra("showName", Util.dataModel.getEpisode_title());
                    showPaymentIntent.putExtra("seriesNumber", Util.dataModel.getEpisode_series_no());
                    showPaymentIntent.putExtra("isPPV", Util.dataModel.getIsPPV());
                    showPaymentIntent.putExtra("isAPV", Util.dataModel.getIsAPV());
                    showPaymentIntent.putExtra("PlayerModel", playerModel);
                    if (Util.dataModel.getIsAPV() == 1) {
                        showPaymentIntent.putExtra("isConverted", 0);
                    } else {
                        showPaymentIntent.putExtra("isConverted", 1);

                    }

                    showPaymentIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(showPaymentIntent);
                    if (LoginActivity.loginA != null) {
                        LoginActivity.loginA.finish();
                    }
                    finish();

                }
            });
        }
    }


    @Override
    public void onBackPressed() {

        if (asynValidateUserDetails != null) {
            asynValidateUserDetails.cancel(true);
        }
        if (asynLoadVideoUrls != null) {
            asynLoadVideoUrls.cancel(true);
        }
        if (asyncReg != null) {
            asyncReg.cancel(true);
        }
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }

    public void removeFocusFromViews() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*****************chromecast*-------------------------------------*/

    private void updateMetadata(boolean visible) {
        Point displaySize;
        if (!visible) {
            /*mDescriptionView.setVisibility(View.GONE);
            mTitleView.setVisibility(View.GONE);
            mAuthorView.setVisibility(View.GONE);*/
            displaySize = Util.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(displaySize.x,
                    displaySize.y + getSupportActionBar().getHeight());
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            // mVideoView.setLayoutParams(lp);
            //mVideoView.invalidate();
        } else {
            //MediaMetadata mm = mSelectedMedia.getMetadata();
          /*  mDescriptionView.setText(mSelectedMedia.getCustomData().optString(
                    VideoProvider.KEY_DESCRIPTION));
            //mTitleView.setText(mm.getString(MediaMetadata.KEY_TITLE));
            //mAuthorView.setText(mm.getString(MediaMetadata.KEY_SUBTITLE));
            mDescriptionView.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.VISIBLE);
            mAuthorView.setVisibility(View.VISIBLE);*/
            displaySize = Util.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(displaySize.x,
                    (int) (displaySize.x * mAspectRatio));
            lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
            // mVideoView.setLayoutParams(lp);
            //mVideoView.invalidate();
        }
    }


    private void setupCastListener() {
        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionResumed(CastSession session, boolean wasSuspended) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionResumeFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarted(CastSession session, String sessionId) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionStartFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarting(CastSession session) {
            }

            @Override
            public void onSessionEnding(CastSession session) {
            }

            @Override
            public void onSessionResuming(CastSession session, String sessionId) {
            }

            @Override
            public void onSessionSuspended(CastSession session, int reason) {
            }

            private void onApplicationConnected(CastSession castSession) {
                mCastSession = castSession;
                mLocation = LoginActivity.PlaybackLocation.REMOTE;
                if (null != mSelectedMedia) {

                    if (mPlaybackState == LoginActivity.PlaybackState.PLAYING) {
                       /* mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);*/
                        return;
                    } else {
                        mPlaybackState = LoginActivity.PlaybackState.IDLE;
                        updatePlaybackLocation(LoginActivity.PlaybackLocation.REMOTE);
                    }
                }
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {
/*
                    mPlayCircle.setVisibility(View.GONE);
*/

                updatePlaybackLocation(LoginActivity.PlaybackLocation.LOCAL);
                mPlaybackState = LoginActivity.PlaybackState.IDLE;
                mLocation = LoginActivity.PlaybackLocation.LOCAL;
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }
        };
    }

    private void updatePlayButton(LoginActivity.PlaybackState state) {
           /* boolean isConnected = (mCastSession != null)
                    && (mCastSession.isConnected() || mCastSession.isConnecting());*/
        //mControllers.setVisibility(isConnected ? View.GONE : View.VISIBLE);

        switch (state) {
            case PLAYING:

                //mLoading.setVisibility(View.INVISIBLE);
                // mPlayPause.setVisibility(View.VISIBLE);
                //mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_av_pause_dark));

                break;
            case IDLE:
                if (mLocation == LoginActivity.PlaybackLocation.LOCAL) {
                   /* if (isAPV == 1) {
                        watchMovieButton.setText(getResources().getString(R.string.advance_purchase_str));
                    }else {
                        watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                    }*/

                } else {
                   /* if (isAPV == 1) {
                        watchMovieButton.setText(getResources().getString(R.string.advance_purchase_str));
                    }else {
                        watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                    }*/
                }
                //mCon
                // trollers.setVisibility(View.GONE);
                // mCoverArt.setVisibility(View.VISIBLE);
                // mVideoView.setVisibility(View.INVISIBLE);
                break;
            case PAUSED:
                //mLoading.setVisibility(View.INVISIBLE);
              /*  mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_av_play_dark));*/

                break;
            case BUFFERING:
                //mPlayPause.setVisibility(View.INVISIBLE);
                //mLoading.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void updatePlaybackLocation(LoginActivity.PlaybackLocation location) {
        mLocation = location;
        if (location == LoginActivity.PlaybackLocation.LOCAL) {
            if (mPlaybackState == LoginActivity.PlaybackState.PLAYING
                    || mPlaybackState == LoginActivity.PlaybackState.BUFFERING) {
                //setCoverArtStatus(null);
                //startControllersTimer();
            } else {
                //stopControllersTimer();

                //setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            }
        } else {
            //stopControllersTimer();
            // setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            //updateControllersVisibility(false);
        }
    }


    private void togglePlayback() {
        //stopControllersTimer();
        switch (mPlaybackState) {
            case PAUSED:
                switch (mLocation) {
                    case LOCAL:



                      /* mVideoView.start();
                        Log.d(TAG, "Playing locally...");
                        mPlaybackState = PlaybackState.PLAYING;
                        startControllersTimer();
                        restartTrickplayTimer();
                        updatePlaybackLocation(PlaybackLocation.LOCAL);*/
                        break;

                    case REMOTE:

                        loadRemoteMedia(Played_Length, true);

                        break;
                    default:
                        break;
                }
                break;

            case PLAYING:
                mPlaybackState = LoginActivity.PlaybackState.PAUSED;

                //  mVideoView.pause();
                break;

            case IDLE:
                switch (mLocation) {
                    case LOCAL:
                        //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));

                        // mPlayCircle.setVisibility(View.GONE);
                       /* mVideoView.setVideoURI(Uri.parse(mSelectedMedia.getContentId()));
                        mVideoView.seekTo(0);
                        mVideoView.start();
                        mPlaybackState = PlaybackState.PLAYING;
                        restartTrickplayTimer();
                        updatePlaybackLocation(PlaybackLocation.LOCAL);*/
                        break;
                    case REMOTE:
                        // mPlayCircle.setVisibility(View.VISIBLE);
                        if (mCastSession != null && mCastSession.isConnected()) {
                            // watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                            loadRemoteMedia(Played_Length, true);


                            // Utils.showQueuePopup(this, mPlayCircle, mSelectedMedia);
                        } else {
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        updatePlayButton(mPlaybackState);
    }

    private void loadRemoteMedia(int position, boolean autoPlay) {

        if (mCastSession == null) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }
        remoteMediaClient.addListener(new RemoteMediaClient.Listener() {

            @Override
            public void onStatusUpdated() {

                Intent intent = new Intent(RegisterActivity.this, ExpandedControlsActivity.class);
                startActivity(intent);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                remoteMediaClient.removeListener(this);
                if (LoginActivity.loginA != null) {
                    LoginActivity.loginA.finish();
                }
                finish();
            }

            @Override
            public void onMetadataUpdated() {
            }

            @Override
            public void onQueueStatusUpdated() {
            }

            @Override
            public void onPreloadStatusUpdated() {
            }

            @Override
            public void onSendingRemoteMediaRequest() {
            }
        });
        remoteMediaClient.setActiveMediaTracks(new long[1]).setResultCallback(new ResultCallback<RemoteMediaClient.MediaChannelResult>() {
            @Override
            public void onResult(@NonNull RemoteMediaClient.MediaChannelResult mediaChannelResult) {
                if (!mediaChannelResult.getStatus().isSuccess()) {
                    LogUtil.showLog("SUBHA", "Failed with status code:" +
                            mediaChannelResult.getStatus().getStatusCode());
                }
            }
        });
        remoteMediaClient.load(mSelectedMedia, autoPlay, position);
    }

    /***************chromecast**********************/


    /***********Subtitle********/

    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL().execute(Url);
    }

    @Override
    public void onSocialAuthPreExecuteStarted() {
        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();

    }

    @Override
    public void onSocialAuthPostExecuteCompleted(SocialAuthOutputModel socialAuthOutputModel, int status, String message) {
        if (status == 0) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_REGISTRATION, DEFAULT_ERROR_IN_REGISTRATION));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, DEFAULT_FAILURE));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        }
        if (status > 0) {

            if (status == 422) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(EMAIL_EXISTS, DEFAULT_EMAIL_EXISTS));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();

            } else if (status == 200) {


                preferenceManager.setLogInStatusToPref("1");
                preferenceManager.setUserIdToPref(socialAuthOutputModel.getId());
                preferenceManager.setPwdToPref("");
                preferenceManager.setEmailIdToPref(socialAuthOutputModel.getEmail());
                preferenceManager.setDispNameToPref(socialAuthOutputModel.getDisplay_name());
                preferenceManager.setLoginProfImgoPref(socialAuthOutputModel.getProfile_image());
                preferenceManager.setIsSubscribedToPref(socialAuthOutputModel.getIsSubscribed());
                preferenceManager.setLoginHistIdPref(socialAuthOutputModel.getLogin_history_id());


                if (featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE)) {

                    Log.v("BIBHU", "isRestrictDevice called");
                    // Call For Check Api.
                    CheckDeviceInput checkDeviceInput = new CheckDeviceInput();
                    checkDeviceInput.setDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    checkDeviceInput.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                    checkDeviceInput.setAuthToken(authTokenStr);
                    checkDeviceInput.setUser_id(preferenceManager.getUseridFromPref());
                    checkDeviceInput.setDevice_type("1");
                    checkDeviceInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    checkDeviceInput.setDevice_info(deviceName + "," + languagePreference.getTextofLanguage(ANDROID_VERSION, DEFAULT_ANDROID_VERSION) + " " + Build.VERSION.RELEASE);
                    CheckDeviceAsyncTask asynCheckDevice = new CheckDeviceAsyncTask(checkDeviceInput, this, this);
                    asynCheckDevice.executeOnExecutor(threadPoolExecutor);
                } else {

                    if(Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (Util.check_for_subscription == 1) {
                        // Go for subscription

                        if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {

                            setResultAtFinishActivity();

                        } else {
                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        if (planId.equals("1") && preferenceManager.getIsSubscribedFromPref().equals("0")) {
                            if (CheckSubscriptionHandler.isSubscriptionAllowed){
                                Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }

                                onBackPressed();
                            }else {
                                Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                if (LoginActivity.loginA != null) {
                                    LoginActivity.loginA.finish();
                                }
                                onBackPressed();
                            }

                        } else {

                            Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            if (LoginActivity.loginA != null) {
                                LoginActivity.loginA.finish();
                            }
                            onBackPressed();
                        }
                    }
                }


                if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }

                if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                }
            }

        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {
            int count;


            try {
                URL url = new URL(f_url[0]);
                String str = f_url[0];
                filename = str.substring(str.lastIndexOf("/") + 1);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File root = Environment.getExternalStorageDirectory();
                mediaStorageDir = new File(root + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/", "");

                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                }

                SubTitlePath.add(mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".vtt");
                OutputStream output = new FileOutputStream(mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".vtt");

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String file_url) {

            FakeSubTitlePath.remove(0);
            if (FakeSubTitlePath.size() > 0) {
                Download_SubTitle(FakeSubTitlePath.get(0).trim());
            } else {
                if (progressBarHandler != null && progressBarHandler.isShowing()) {
                    progressBarHandler.hide();
                }
                playerModel.setSubTitlePath(SubTitlePath);
                final Intent playVideoIntent;
                if (Util.dataModel.getAdNetworkId() == 3) {
                    LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                    playVideoIntent = new Intent(RegisterActivity.this, MyActivity.class);

                } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                    if (Util.dataModel.getPlayPos() <= 0) {
                        playVideoIntent = new Intent(RegisterActivity.this, AdPlayerActivity.class);
                    } else {
                        playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);

                    }
                } else {
                    playVideoIntent = new Intent(RegisterActivity.this, ExoPlayerActivity.class);

                }
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
              /*  playVideoIntent.putExtra("SubTitleName", SubTitleName);
                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
                if (LoginActivity.loginA != null) {
                    LoginActivity.loginA.finish();
                }
                removeFocusFromViews();
                finish();
                overridePendingTransition(0, 0);
            }
        }
    }


    @Override
    public void onCheckFbUserDetailsAsynPreExecuteStarted() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }
        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onCheckFbUserDetailsAsynPostExecuteCompleted(int code) {

        if (code == 0) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setMessage(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK));
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        }

        if (code == 200) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }

            SocialAuthInputModel socialAuthInputModel = new SocialAuthInputModel();
            socialAuthInputModel.setAuthToken(authTokenStr);
            socialAuthInputModel.setName(fbName.trim());
            socialAuthInputModel.setEmail(fbEmail.trim());
            socialAuthInputModel.setPassword("");
            socialAuthInputModel.setFb_userid(fbUserId.trim());
            socialAuthInputModel.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            socialAuthInputModel.setDevice_type("1");
            socialAuthInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            asynFbRegDetails = new SocialAuthAsynTask(socialAuthInputModel, RegisterActivity.this, RegisterActivity.this);
            asynFbRegDetails.executeOnExecutor(threadPoolExecutor);


        } else {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setMessage(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK));
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1001) {
            if (data.getStringExtra("yes").equals("1002")) {
                watch_status_String = "halfplay";
                seek_status = "first_time";
                Played_Length = Util.dataModel.getPlayPos() * 1000;
                PlayThroughChromeCast();

            } else {
                watch_status_String = "strat";
                Played_Length = 0;
                PlayThroughChromeCast();
            }
        } else {
            if (requestCode == 1001) {
                watch_status_String = "strat";
                Played_Length = 0;
                PlayThroughChromeCast();
            }
        }

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            //Log.v(TAG,""+result.toString());

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCheckDevicePreExecuteStarted() {

        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onCheckDevicePostExecuteCompleted(CheckDeviceOutput checkDeviceOutput, int code, String message) {

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            code = 0;

        }

        UniversalErrorMessage = message;

        if (code > 0) {
            if (code == 200) {

                // Allow The User To Login

                if (Util.check_for_subscription == 1) {
                    // Go for subscription

                    if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                        if (Util.dataModel.getIsFreeContent() == 1) {
                            GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                            getVideoDetailsInput.setAuthToken(authTokenStr);
                            getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                            getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                            getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                            getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                            getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                            asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
                            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                        } else {
                            setResultAtFinishActivity();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING), Toast.LENGTH_LONG).show();
                    }

                } else {

                    if(Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (planId.equals("1") && preferenceManager.getIsSubscribedFromPref().equals("0")) {
                        if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                            Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            if (LoginActivity.loginA != null) {
                                LoginActivity.loginA.finish();
                            }
                            finish();
                        }else {
                            Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            if (LoginActivity.loginA != null) {
                                LoginActivity.loginA.finish();
                            }
                            finish();
                        }
                    } else {
                        Intent in = new Intent(RegisterActivity.this, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        if (LoginActivity.loginA != null) {
                            LoginActivity.loginA.finish();
                        }
                        finish();
                    }
                }
            } else {
                // Call For Logout

                LogOut();
            }
        } else {
            // Call For Logout
            LogOut();
        }

    }

    // Following Code Has Been Added For The Device Management
//    private class AsynCheckDevice extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//
//        int statusCode;
//        String responseStr;
//        JSONObject myJson = null;
//        String userIdStr;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            if (preferenceManager != null) {
//                userIdStr = preferenceManager.getUseridFromPref();
//            }
//
//            String urlRouteList = Util.rootUrl().trim() + Util.CheckDevice.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("user_id", userIdStr.trim());
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("device", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//                httppost.addHeader("google_id", languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, Util.DEFAULT_GOOGLE_FCM_TOKEN));
//                httppost.addHeader("device_type", "1");
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//                httppost.addHeader("device_info", deviceName + "," + languagePreference.getTextofLanguage(ANDROID_VERSION, Util.DEFAULT_ANDROID_VERSION) + " " + Build.VERSION.RELEASE);
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            statusCode = 0;
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    statusCode = 0;
//
//                    e.printStackTrace();
//                }
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//                    UniversalErrorMessage = myJson.optString("msg");
//                }
//            } catch (Exception e) {
//                statusCode = 0;
//            }
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//            try {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//            } catch (IllegalArgumentException ex) {
//                statusCode = 0;
//            }
//
//            if (responseStr == null) {
//                // Call For Logout
//                LogOut();
//            }
//
//            if (statusCode > 0) {
//                if (statusCode == 200) {
//
//                    // Allow The User To Login
//
//                    if (Util.check_for_subscription == 1) {
//                        // Go for subscription
//
//                        if (Util.checkNetwork(RegisterActivity.this) == true) {
//                            if (Util.dataModel.getIsFreeContent() == 1) {
//                                GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
//                                getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
//                                getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
//                                getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
//                                asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
//                                asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                            } else {
//                                ValidateUserInput validateUserInput = new ValidateUserInput();
//                                validateUserInput.setAuthToken(Util.authTokenStr);
//                                validateUserInput.setUserId(preferenceManager.getUseridFromPref());
//                                validateUserInput.setMuviUniqueId(Util.dataModel.getMovieUniqueId().trim());
//                                validateUserInput.setPurchaseType(Util.dataModel.getPurchase_type());
//                                validateUserInput.setSeasonId(Util.dataModel.getSeason_id());
//                                validateUserInput.setEpisodeStreamUniqueId( Util.dataModel.getEpisode_id());
//                                validateUserInput.setLanguageCode(Util.getTextofLanguage(RegisterActivity.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//                                asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput,RegisterActivity.this,RegisterActivity.this);
//                                asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
//                            }
//                        } else {
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                        }
//
//                    } else {
//
//                        if (planId.equals("1") && UniversalIsSubscribed.equals("0")) {
//                            Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Intent in = new Intent(RegisterActivity.this, MainActivity.class);
//                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(in);
//                            finish();
//                        }
//                    }
//                } else {
//                    // Call For Logout
//
//                    LogOut();
//                }
//            } else {
//                // Call For Logout
//                LogOut();
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(RegisterActivity.this);
//            pDialog.show();
//        }
//    }

    public void LogOut() {

        LogoutInput logoutInput = new LogoutInput();
        logoutInput.setAuthToken(authTokenStr);
        logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        logoutInput.setLogin_history_id(preferenceManager.getLoginHistIdFromPref());
        LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, this, this);
        asynLogoutDetails.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onLogoutPreExecuteStarted() {
        pDialog = new ProgressBarHandler(RegisterActivity.this);
        pDialog.show();
    }

    @Override
    public void onLogoutPostExecuteCompleted(int code, String status, String message) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;
        }

        if (status == null) {
            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code > 0) {
            if (code == 200) {
                preferenceManager.clearLoginPref();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(UniversalErrorMessage);
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();

            } else {
                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }
        }

    }

//    private class AsynLogoutDetails extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//        int responseCode;
//        String loginHistoryIdStr = pref.getString("PREFS_LOGIN_HISTORYID_KEY", null);
//        String responseStr;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//
//            String urlRouteList = Util.rootUrl().trim() + Util.logoutUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("login_history_id", loginHistoryIdStr);
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            responseCode = 0;
//                            Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                    responseCode = 0;
//                    e.printStackTrace();
//                }
//                if (responseStr != null) {
//                    JSONObject myJson = new JSONObject(responseStr);
//                    responseCode = Integer.parseInt(myJson.optString("code"));
//                }
//
//            } catch (Exception e) {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//                responseCode = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//            try {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//
//                }
//            } catch (IllegalArgumentException ex) {
//                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseStr == null) {
//                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseCode == 0) {
//                Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseCode > 0) {
//                if (responseCode == 200) {
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.clear();
//                    editor.commit();
//                    SharedPreferences loginPref = getSharedPreferences(Util.LOGIN_PREF, 0); // 0 - for private mode
//                    if (loginPref != null) {
//                        SharedPreferences.Editor countryEditor = loginPref.edit();
//                        countryEditor.clear();
//                        countryEditor.commit();
//                    }
//
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(UniversalErrorMessage);
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    dlgAlert.create().show();
//
//                } else {
//                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            pDialog = new ProgressBarHandler(RegisterActivity.this);
//            pDialog.show();
//        }
//    }


    public void handleFbUserDetails(String fbUserId, String fbEmail, String fbName) {
        this.fbUserId = fbUserId;
        this.fbEmail = fbEmail;
        this.fbName = fbName;
        CheckFbUserDetailsInput checkFbUserDetailsInput = new CheckFbUserDetailsInput();
        checkFbUserDetailsInput.setAuthToken(authTokenStr);
        checkFbUserDetailsInput.setFb_userid(fbUserId.trim());
        asynCheckFbUserDetails = new CheckFbUserDetailsAsyn(checkFbUserDetailsInput, RegisterActivity.this, RegisterActivity.this);
        asynCheckFbUserDetails.executeOnExecutor(threadPoolExecutor);

    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        // Log.d(TAG, "handleSignInResult:" + result.isSuccess()+result.getSignInAccount());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Authname = acct.getDisplayName();
            AuthEmail = acct.getEmail();
            AuthId = acct.getId();
            AuthImageUrl = String.valueOf(acct.getPhotoUrl());

            GmailLoginInput gmailLoginInput = new GmailLoginInput();
            gmailLoginInput.setEmail(AuthEmail);
            gmailLoginInput.setName(Authname);
            gmailLoginInput.setGmail_userid(AuthId);
            gmailLoginInput.setProfile_image(AuthImageUrl);
            gmailLoginInput.setPassword("");
            gmailLoginInput.setAuthToken(authTokenStr);
            AsyncGmailReg asyncGmailReg = new AsyncGmailReg(gmailLoginInput, this, this);
            asyncGmailReg.executeOnExecutor(threadPoolExecutor);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // finish();
    }

    public void handleActionForValidateUserPayment(String validUserStr, String message, String Subscription_Str) {

        if (validUserStr != null) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {

            }

            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                        if (Util.dataModel.getContentTypesId() == 3) {
                            // Show Popup
                            ShowPpvPopUp();
                        } else {
                            // Go to ppv Payment
                            payment_for_single_part();
                        }
                    } else if (planId.equals("1") && Subscription_Str.equals("0")) {
                        if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                            Intent intent = new Intent(RegisterActivity.this, SubscriptionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            if (LoginActivity.loginA != null) {
                                LoginActivity.loginA.finish();
                            }
                            finish();
                        }else {
                            if (Util.dataModel.getContentTypesId() == 3) {
                                // Show Popup
                                ShowPpvPopUp();
                            } else {
                                // Go to ppv Payment
                                payment_for_single_part();
                            }
                        }
                    } else {
                        if (Util.dataModel.getContentTypesId() == 3) {
                            // Show Popup
                            ShowPpvPopUp();
                        } else {
                            // Go to ppv Payment
                            payment_for_single_part();
                        }
                    }
                }

            }
        }

    }

    public void handleActionForValidateSonyUserPayment(String validUserStr, String message, String subscription_Str, String alertShowMsg) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(RegisterActivity.this)) {
                    Log.v("MUVI", "VV VV VV");

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, RegisterActivity.this, RegisterActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(RegisterActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    Util.showActivateSubscriptionWatchVideoAleart(this, alertShowMsg);
                }

            }
        }
    }

    public void setResultAtFinishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}

