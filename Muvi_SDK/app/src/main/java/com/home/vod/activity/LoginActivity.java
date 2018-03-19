package com.home.vod.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.images.WebImage;
import com.home.apisdk.apiController.AsyncGmailReg;
import com.home.apisdk.apiController.CheckDeviceAsyncTask;
import com.home.apisdk.apiController.CheckFbUserDetailsAsyn;
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetSimultaneousLogoutAsync;
import com.home.apisdk.apiController.GetValidateUserAsynTask;
import com.home.apisdk.apiController.LoginAsynTask;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiController.SocialAuthAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiModel.CheckDeviceInput;
import com.home.apisdk.apiModel.CheckDeviceOutput;
import com.home.apisdk.apiModel.CheckFbUserDetailsInput;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.GmailLoginInput;
import com.home.apisdk.apiModel.GmailLoginOutput;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.Login_input;
import com.home.apisdk.apiModel.Login_output;
import com.home.apisdk.apiModel.LogoutInput;
import com.home.apisdk.apiModel.SimultaneousLogoutInput;
import com.home.apisdk.apiModel.SocialAuthInputModel;
import com.home.apisdk.apiModel.SocialAuthOutputModel;
import com.home.apisdk.apiModel.ValidateUserOutput;
import com.home.vod.BuildConfig;
import com.home.vod.CheckSubscriptionHandler;
import com.home.vod.LoginHandler;
import com.home.vod.MonetizationHandler;

import com.home.vod.R;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.MyLibraryPlayer;
import player.activity.Player;

import static com.home.vod.preferences.LanguagePreference.ANDROID_VERSION;
import static com.home.vod.preferences.LanguagePreference.BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEAFULT_CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ANDROID_VERSION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_EMAIL_PASSWORD_INVALID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FORGOT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_GMAIL_SIGNIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_GOOGLE_FCM_TOKEN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_IS_STREAMING_RESTRICTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_ONE_STEP_REGISTRATION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_RESTRICT_DEVICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGIN_FACEBOOK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NEW_HERE_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_UP_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRY_AGAIN;
import static com.home.vod.preferences.LanguagePreference.DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.EMAIL_PASSWORD_INVALID;
import static com.home.vod.preferences.LanguagePreference.ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.FORGOT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.GMAIL_SIGNIN;
import static com.home.vod.preferences.LanguagePreference.GOOGLE_FCM_TOKEN;
import static com.home.vod.preferences.LanguagePreference.LOGIN;
import static com.home.vod.preferences.LanguagePreference.LOGIN_FACEBOOK;
import static com.home.vod.preferences.LanguagePreference.NEW_HERE_TITLE;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIGN_UP_TITLE;
import static com.home.vod.preferences.LanguagePreference.SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.TEXT_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.TRY_AGAIN;
import static com.home.vod.util.Constant.authTokenStr;

public class LoginActivity extends AppCompatActivity implements LoginAsynTask.LoinDetailsListener, GoogleApiClient.OnConnectionFailedListener,
        GetValidateUserAsynTask.GetValidateUserListener,
        VideoDetailsAsynctask.VideoDetailsListener,
        LogoutAsynctask.LogoutListener, CheckDeviceAsyncTask.CheckDeviceListener,
        GetSimultaneousLogoutAsync.SimultaneousLogoutAsyncListener,
        CheckFbUserDetailsAsyn.CheckFbUserDetailsListener, SocialAuthAsynTask.SocialAuthListener
        , AsyncGmailReg.AsyncGmailListener, GetIpAddressAsynTask.IpAddressListener {




    /***************************************************************************/

    TextView gmailTest;
    private RelativeLayout googleSignView;
    private Button registerButton;
    TextView fbLoginTextView;


    /***************************************************************************/


    /*subtitle-------------------------------------*/
    public static Activity loginA;
    LoginHandler loginHandler;
    String filename = "";
    static File mediaStorageDir;
    String UniversalErrorMessage = "";
    String UniversalIsSubscribed = "";
    String loggedInIdStr;
    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();
    ProgressDialog progressDialog1;
    public static ProgressBarHandler progressBarHandler;
    ProgressBarHandler pDialog;
    Player playerModel;
    LanguagePreference languagePreference;

    //for resume play
    String seek_status = "";
    int Played_Length = 0;
    String watch_status_String = "start";
    String ipAddressStr = "";
    ////////Google sign in ////start/////
    TextView name, email, id;
    String Authname, AuthEmail, AuthId;
    private static final String TAG = "Nihar";
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    Button signout;
    private String AuthImageUrl;
    String deviceRestrictionMessage = "";
    /////////////////////end//////////////////

    @Override
    public void onLoginPreExecuteStarted() {
        pDialog = new ProgressBarHandler(LoginActivity.this);
        pDialog.show();
    }

    @Override
    public void onLoginPostExecuteCompleted(Login_output login_output, int status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {

        }

        if (status > 0) {

            // SharedPreferences.Editor editor = pref.edit();

            if (status == 200) {
                //playerModel.setEmailId(login_output.getEmail());

                preferenceManager.setLogInStatusToPref("1");
                preferenceManager.setUserIdToPref(login_output.getId());
                preferenceManager.setPwdToPref("");
                preferenceManager.setEmailIdToPref(login_output.getEmail());
                preferenceManager.setDispNameToPref(login_output.getDisplay_name());
                preferenceManager.setLoginProfImgoPref(login_output.getProfile_image());
                preferenceManager.setIsSubscribedToPref(login_output.getIsSubscribed());
                preferenceManager.setLoginHistIdPref(login_output.getLogin_history_id());


                if (NetworkStatus.getInstance().isConnected(this)) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }

                    if ((featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE))) {

                        LogUtil.showLog("MUVI", "isRestrictDevice called");
                        // Call For Check Api.
                        CheckDeviceInput checkDeviceInput = new CheckDeviceInput();
                        if (preferenceManager != null) {
                            String userIdStr = preferenceManager.getUseridFromPref();
                            checkDeviceInput.setUser_id(userIdStr.trim());
                        }
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
                            /** review **/
                            onBackPressed();
                        } else {
                            if (Util.check_for_subscription == 1) {
                                //go to subscription page
                                if (NetworkStatus.getInstance().isConnected(this)) {

                                    setResultAtFinishActivity();


                                } else {
                                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                                }

                            } else {
                                if (PlanId.equals("1") && login_output.getIsSubscribed().equals("0")) {
                                    if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                                        Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(0, 0);
                                    }else {
                                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();
                                    }
                                } else {
                                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else if (status == 300) {
                // Show Popup For the Simultaneous Logout

                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;

                }
                show_logout_popup(login_output.getMsg());
            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(EMAIL_PASSWORD_INVALID, DEFAULT_EMAIL_PASSWORD_INVALID));
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
            }
        } else {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {

            }

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT));
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
        }

    }

    @Override
    public void onGetValidateUserPreExecuteStarted() {

        pDialog = new ProgressBarHandler(LoginActivity.this);
        pDialog.show();

    }

    @Override
    public void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message) {
        String validUserStr = validateUserOutput.getValiduser_str();
        String subscription_Str = preferenceManager.getIsSubscribedFromPref();

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }

        if (validateUserOutput == null) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }
                    });
            dlgAlert.create().show();
        } else if (status <= 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }
                    });
            dlgAlert.create().show();
        }

        if (status > 0) {
            if (status == 427) {


                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
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

                new MonetizationHandler(LoginActivity.this).handle429OR430statusCod(validUserStr, message, subscription_Str);

            }
           else if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                if (Util.dataModel.getContentTypesId() == 3) {
                    // Show Popup
                    ShowPpvPopUp();
                } else {
                    // Go to ppv Payment
                    payment_for_single_part();
                }
            } else if (PlanId.equals("1") && validateUserOutput.getIsMemberSubscribed().equals("0")) {
                if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                    Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }else if (Util.dataModel.getIsConverted() == 0) {
                    Util.showNoDataAlert(LoginActivity.this);
                }else {
                    if (NetworkStatus.getInstance().isConnected(this)) {

                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                        getVideoDetailsInput.setInternetSpeed(getVideoDetailsInput.getInternetSpeed());
                        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, this, this);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                    } else {
                        Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }
            } else if (Util.dataModel.getIsConverted() == 0) {
                Util.showNoDataAlert(LoginActivity.this);
            } else {
                if (NetworkStatus.getInstance().isConnected(this)) {

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(getVideoDetailsInput.getInternetSpeed());
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, this, this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        // finish();
    }

    @Override
    public void onVideoDetailsPreExecuteStarted() {

        SubTitleName.clear();
        SubTitlePath.clear();
        ResolutionUrl.clear();
        ResolutionFormat.clear();
        pDialog = new ProgressBarHandler(LoginActivity.this);
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

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
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
            if (_video_details_output.getIs_offline() != null
                    && !_video_details_output.getIs_offline().trim().isEmpty() &&
                    !_video_details_output.getIs_offline().trim().equals("null") &&
                    !_video_details_output.getIs_offline().trim().matches("")) {
                playerModel.setIsOffline(_video_details_output.getIs_offline());
            }
            if (_video_details_output.getDownload_status() != null
                    && !_video_details_output.getDownload_status().trim().isEmpty() &&
                    !_video_details_output.getDownload_status().trim().equals("null") &&
                    !_video_details_output.getDownload_status().trim().matches("")
                    ) {
                playerModel.setDownloadStatus(_video_details_output.getDownload_status());
            }
            if (_video_details_output.getThirdparty_url() == null || _video_details_output.getThirdparty_url().matches("")) {

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
                    //  dataModel.setVideoUrl(translatedLanuage.getNoData());
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
                Util.showNoDataAlert(LoginActivity.this);
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
                        if (Util.goToLibraryplayer) {
                            playVideoIntent = new Intent(LoginActivity.this, MyLibraryPlayer.class);
                        } else {
                            if (Util.dataModel.getAdNetworkId() == 3) {
                                LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                                playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                            } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                                if (Util.dataModel.getPlayPos() <= 0) {
                                    playVideoIntent = new Intent(LoginActivity.this, AdPlayerActivity.class);
                                } else {
                                    playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                                }
                            } else {
                                playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                            }
                            // playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);
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

                                    progressBarHandler = new ProgressBarHandler(LoginActivity.this);
                                    progressBarHandler.show();
                                    Download_SubTitle(FakeSubTitlePath.get(0).trim());
                                } else {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel", playerModel);
                                    startActivity(playVideoIntent);
                                    finish();
                                }

                            }
                        });
                    }
                } else {
                    final Intent playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);
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
            }
            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            Util.showNoDataAlert(LoginActivity.this);
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
    public void onLogoutPreExecuteStarted() {
        pDialog = new ProgressBarHandler(LoginActivity.this);
        pDialog.show();
    }

    @Override
    public void onLogoutPostExecuteCompleted(int code, String status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;

            }
        } catch (IllegalArgumentException ex) {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (status == null) {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code > 0) {
            if (code == 200) {
                preferenceManager.clearLoginPref();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(deviceRestrictionMessage);
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
                Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }
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

    private PlaybackLocation mLocation;
    private PlaybackState mPlaybackState;
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

    /*********fb****/

    String fbUserId = "";
    String fbEmail = "";
    String fbName = "";
    private LinearLayout btnLogin;
    private ProgressBarHandler progressDialog;
    private CallbackManager callbackManager;
    CheckFbUserDetailsAsyn asynCheckFbUserDetails;
    GetValidateUserAsynTask asynValidateUserDetails;
    SocialAuthAsynTask asynFbRegDetails;

    /*********fb****/
    //AsynLogInDetails asyncReg;
    VideoDetailsAsynctask asynLoadVideoUrls;
    EditText editEmailStr, editPasswordStr;
    TextView forgotPassword, loginNewUser, signUpTextView;
    Button loginButton;
    LoginButton loginWithFacebookButton;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    //SharedPreferences pref;
    String regEmailStr, regPasswordStr;
    Toolbar mActionBarToolbar;

    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    // Added For FCM

    TextView logout_text;
    Button ok, cancel;
    AlertDialog logout_alert;
    String PlanId = "";
    AlertDialog alert;
    String priceForUnsubscribedStr, priceFosubscribedStr;
    String deviceName = "";

    PreferenceManager preferenceManager;
    FeatureHandler featureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*********fb****/
        FacebookSdk.sdkInitialize(getApplicationContext());
        /*********fb****/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_login);
        loginA = this;
        languagePreference = LanguagePreference.getLanguagePreference((this));
        featureHandler = FeatureHandler.getFeaturePreference(LoginActivity.this);
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        deviceName = myDevice.getName();


        LogUtil.showLog("MUVI", "Device_Name=" + deviceName);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(LOGIN,DEFAULT_LOGIN));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        //playerModel = new Player();
        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");
        if (playerModel != null) {
            playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        }
        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
            mActionBarToolbar.setNavigationIcon(null);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
            LogUtil.showLog("MUVI", "Called");
        } else {
            mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        }

        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editEmailStr = (EditText) findViewById(R.id.editEmailStr);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.light_fonts), editEmailStr);

        editEmailStr.setHint(languagePreference.getTextofLanguage(TEXT_EMIAL, DEFAULT_TEXT_EMIAL));

        editPasswordStr = (EditText) findViewById(R.id.editPasswordStr);


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

        editEmailStr.setFilters(new InputFilter[]{filter});
        editPasswordStr.setFilters(new InputFilter[]{filter});

        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.light_fonts), editPasswordStr);


        editPasswordStr.setHint(languagePreference.getTextofLanguage(TEXT_PASSWORD, DEFAULT_TEXT_PASSWORD));
        forgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.light_fonts), forgotPassword);
        forgotPassword.setText(languagePreference.getTextofLanguage(FORGOT_PASSWORD, DEFAULT_FORGOT_PASSWORD));
        loginNewUser = (TextView) findViewById(R.id.loginNewUser);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.light_fonts), loginNewUser);

        loginNewUser.setText(languagePreference.getTextofLanguage(NEW_HERE_TITLE, DEFAULT_NEW_HERE_TITLE));

        signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.light_fonts), signUpTextView);


        /*
         Because Of request of Sony we used BTN_REGISTER instead of SIGN_UP_TITLE
         *Abhishek
         */
        //signUpTextView.setText(languagePreference.getTextofLanguage(SIGN_UP_TITLE, DEFAULT_SIGN_UP_TITLE));


        signUpTextView.setText(languagePreference.getTextofLanguage(BTN_REGISTER, DEFAULT_BTN_REGISTER));

        loginButton = (Button) findViewById(R.id.loginButton);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.regular_fonts), loginButton);

        loginButton.setText(languagePreference.getTextofLanguage(LOGIN, DEFAULT_LOGIN));




        preferenceManager = PreferenceManager.getPreferenceManager(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.hideKeyboard(LoginActivity.this);

                loginButtonClicked();

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent detailsIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                detailsIntent.putExtra("PlayerModel", playerModel);
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(detailsIntent);
                finish();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Util.favorite_clicked) {
                    Util.favorite_clicked = true;
                }

                final Intent detailsIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                /****rating ******/

                if (getIntent().getStringExtra("from") != null) {
                    detailsIntent.putExtra("from", getIntent().getStringExtra("from"));
                }
                detailsIntent.putExtra("PlayerModel", playerModel);
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(detailsIntent);
                onBackPressed();
            }
        });

        PlanId = (languagePreference.getTextofLanguage(PLAN_ID, DEFAULT_PLAN_ID)).trim();


        callbackManager = CallbackManager.Factory.create();

        //-----------------------google signin--------------//


        /***************************************************************************/

        gmailTest=(TextView) findViewById(R.id.textView);
        googleSignView = (RelativeLayout) findViewById(R.id.sign_in_button);
        loginWithFacebookButton = (LoginButton) findViewById(R.id.loginWithFacebookButton);
        loginWithFacebookButton.setVisibility(View.GONE);
        loginWithFacebookButton.setReadPermissions("public_profile", "email", "user_friends");
        fbLoginTextView = (TextView) findViewById(R.id.fbLoginTextView);
        FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.regular_fonts), fbLoginTextView);


        btnLogin = (LinearLayout) findViewById(R.id.btnLogin);

        if(featureHandler.getFeatureStatus(FeatureHandler.FACEBOOK,FeatureHandler.DEFAULT_FACEBOOK)) {
            btnLogin.setVisibility(View.VISIBLE);
        }else {
            btnLogin.setVisibility(View.GONE);
        }

        if(featureHandler.getFeatureStatus(FeatureHandler.GOOGLE,FeatureHandler.DEFAULT_GOOGLE)) {
            googleSignView.setVisibility(View.VISIBLE);
        }else {
            googleSignView.setVisibility(View.GONE);
        }



        /***************************************************************************/


       // loginHandler = new LoginHandler(this);
        callSignin(languagePreference);
        callFblogin(callbackManager, loginButton, languagePreference);




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


            /*chromecast-------------------------------------*/

        mAquery = new AQuery(this);

        // setupControlsCallbacks();
        setupCastListener();
        mCastContext = CastContext.getSharedInstance(this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        boolean shouldStartPlayback = false;
        int startPosition = 0;

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
/***************chromecast**********************/
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        GetIpAddressAsynTask asynGetIpAddress = new GetIpAddressAsynTask(this, this);
        asynGetIpAddress.executeOnExecutor(threadPoolExecutor);
        /***************chromecast**********************/
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }


        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);


        /***************chromecast**********************/


    }

    public void loginButtonClicked() {

        regEmailStr = editEmailStr.getText().toString().trim();
        regPasswordStr = editPasswordStr.getText().toString().trim();


        if (NetworkStatus.getInstance().isConnected(this)) {
            if ((!regEmailStr.equals("")) && (!regPasswordStr.equals(""))) {
                boolean isValidEmail = Util.isValidMail(regEmailStr);
                if (isValidEmail == true) {

                    LogUtil.showLog("MUVI", "activity_login valid");
                    Login_input login_input = new Login_input();
                    login_input.setAuthToken(authTokenStr);
                    login_input.setEmail(regEmailStr);
                    login_input.setPassword(regPasswordStr);
                    login_input.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    login_input.setGoogle_id(languagePreference.getTextofLanguage(GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
                    login_input.setDevice_type("1");
                    login_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    LoginAsynTask asyncReg = new LoginAsynTask(login_input, this, this);
                    asyncReg.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(OOPS_INVALID_EMAIL, DEFAULT_OOPS_INVALID_EMAIL), Toast.LENGTH_LONG).show();
                }
            } else {
                if(regEmailStr.equals("")) {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(TEXT_EMIAL, DEFAULT_TEXT_EMIAL), Toast.LENGTH_LONG).show();
                }else if(regPasswordStr.equals("")) {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(TEXT_PASSWORD, DEFAULT_TEXT_PASSWORD), Toast.LENGTH_LONG).show();
                }
                }
        } else {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        if (asynValidateUserDetails != null) {
            asynValidateUserDetails.cancel(true);
        }

        if (asynCheckFbUserDetails != null) {
            asynCheckFbUserDetails.cancel(true);
        }
        if (asynFbRegDetails != null) {
            asynFbRegDetails.cancel(true);
        }
        if (asynLoadVideoUrls != null) {
            asynLoadVideoUrls.cancel(true);
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

    /*****************chromecvast*-------------------------------------*/

    private void updateMetadata(boolean visible) {
        Point displaySize;
        if (!visible) {
            displaySize = Util.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(displaySize.x,
                    displaySize.y + getSupportActionBar().getHeight());
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            displaySize = Util.getDisplaySize(this);
            RelativeLayout.LayoutParams lp = new
                    RelativeLayout.LayoutParams(displaySize.x,
                    (int) (displaySize.x * mAspectRatio));
            lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
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
                mLocation = PlaybackLocation.REMOTE;
                if (null != mSelectedMedia) {

                    if (mPlaybackState == PlaybackState.PLAYING) {
                       /* mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);*/
                        return;
                    } else {
                        mPlaybackState = PlaybackState.IDLE;
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    }
                }
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {

                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }
        };
    }

    private void updatePlayButton(PlaybackState state) {

        switch (state) {
            case PLAYING:

                break;
            case IDLE:
                if (mLocation == PlaybackLocation.LOCAL) {

                } else {
                }

                break;
            case PAUSED:
                break;
            case BUFFERING:
                break;
            default:
                break;
        }
    }

    private void updatePlaybackLocation(PlaybackLocation location) {
        mLocation = location;
        if (location == PlaybackLocation.LOCAL) {
            if (mPlaybackState == PlaybackState.PLAYING
                    || mPlaybackState == PlaybackState.BUFFERING) {

            } else {

            }
        } else {

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
                mPlaybackState = PlaybackState.PAUSED;

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

                Intent intent = new Intent(LoginActivity.this, ExpandedControlsActivity.class);
                startActivity(intent);
                remoteMediaClient.removeListener(this);
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
        remoteMediaClient.load(mSelectedMedia, autoPlay, position);
        finish();
    }

    /***************chromecast**********************/


    /***********Subtitle********/

    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL().execute(Url);
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
                        LogUtil.showLog("App", "failed to create directory");
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
                LogUtil.showLog("Error: ", e.getMessage());
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

                if (Util.goToLibraryplayer) {
                    playVideoIntent = new Intent(LoginActivity.this, MyLibraryPlayer.class);
                } else {
                    if (Util.dataModel.getAdNetworkId() == 3) {
                        LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                        playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                    } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                        if (Util.dataModel.getPlayPos() <= 0) {
                            playVideoIntent = new Intent(LoginActivity.this, AdPlayerActivity.class);
                        } else {
                            playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                        }
                    } else {
                        playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);

                    }
                    // playVideoIntent = new Intent(LoginActivity.this, ExoPlayerActivity.class);
                }
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               /* playVideoIntent.putExtra("SubTitleName", SubTitleName);
                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
                removeFocusFromViews();
                finish();
                overridePendingTransition(0, 0);
            }
        }
    }



  /*  ************facebook*******--------------/
            *
            *
            */

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
            Log.v(TAG, "" + result.toString());

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

    }



    @Override
    public void onCheckFbUserDetailsAsynPreExecuteStarted() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }
        pDialog = new ProgressBarHandler(LoginActivity.this);
        pDialog.show();
    }

    @Override
    public void onCheckFbUserDetailsAsynPostExecuteCompleted(int code) {

        if (code == 0) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
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
            asynFbRegDetails = new SocialAuthAsynTask(socialAuthInputModel, this, this);
            asynFbRegDetails.executeOnExecutor(threadPoolExecutor);


        } else {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
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
    public void onSocialAuthPreExecuteStarted() {

        pDialog = new ProgressBarHandler(LoginActivity.this);
        pDialog.show();
    }

    @Override
    public void onSocialAuthPostExecuteCompleted(SocialAuthOutputModel socialAuthOutputModel, int status, String message) {

        if (socialAuthOutputModel == null) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                status = 0;

            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
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

        if (status > 0) {

            //    SharedPreferences.Editor editor = pref.edit();

            if (status == 200) {
                String displayNameStr = socialAuthOutputModel.getDisplay_name();
                String emailFromApiStr = socialAuthOutputModel.getEmail();
                String profileImageStr = socialAuthOutputModel.getProfile_image();
                String isSubscribedStr = socialAuthOutputModel.getIsSubscribed();
                String loginHistoryIdStr = socialAuthOutputModel.getLogin_history_id();

                preferenceManager.setLogInStatusToPref("1");
                preferenceManager.setUserIdToPref(socialAuthOutputModel.getId());
                preferenceManager.setPwdToPref("");
                preferenceManager.setEmailIdToPref(emailFromApiStr);
                preferenceManager.setDispNameToPref(displayNameStr);
                preferenceManager.setLoginProfImgoPref(profileImageStr);
                preferenceManager.setIsSubscribedToPref(isSubscribedStr);
                preferenceManager.setLoginHistIdPref(loginHistoryIdStr);


                if (NetworkStatus.getInstance().isConnected(this)) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }


                    if ((featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE))) {

                        LogUtil.showLog("MUVI", "isRestrictDevice called");
                        // Call For Check Api.
                        CheckDeviceInput checkDeviceInput = new CheckDeviceInput();
                        if (preferenceManager != null) {
                            String userIdStr = preferenceManager.getUseridFromPref();
                            checkDeviceInput.setUser_id(userIdStr.trim());
                        }
                        checkDeviceInput.setDevice(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                        checkDeviceInput.setGoogle_id(languagePreference.getTextofLanguage(Util.GOOGLE_FCM_TOKEN, Util.DEFAULT_GOOGLE_FCM_TOKEN));
                        checkDeviceInput.setAuthToken(authTokenStr);
                        checkDeviceInput.setUser_id(preferenceManager.getUseridFromPref());
                        checkDeviceInput.setDevice_type("1");
                        checkDeviceInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        checkDeviceInput.setDevice_info(deviceName + "," + languagePreference.getTextofLanguage(ANDROID_VERSION, DEFAULT_ANDROID_VERSION) + " " + Build.VERSION.RELEASE);
                        CheckDeviceAsyncTask asynCheckDevice = new CheckDeviceAsyncTask(checkDeviceInput, this, this);
                        asynCheckDevice.executeOnExecutor(threadPoolExecutor);
                    } else {


                        if (Util.check_for_subscription == 1) {
                            //go to subscription page
                            if (NetworkStatus.getInstance().isConnected(this)) {
                                if (Util.dataModel.getIsFreeContent() == 1) {
                                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                                    getVideoDetailsInput.setAuthToken(authTokenStr);
                                    getVideoDetailsInput.setContent_uniq_id(getVideoDetailsInput.getContent_uniq_id());
                                    getVideoDetailsInput.setStream_uniq_id(getVideoDetailsInput.getStream_uniq_id());
                                    getVideoDetailsInput.setInternetSpeed(getVideoDetailsInput.getInternetSpeed());
                                    getVideoDetailsInput.setUser_id(getVideoDetailsInput.getUser_id());
                                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                                    VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, LoginActivity.this, LoginActivity.this);
                                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                                } else {

                                    setResultAtFinishActivity();


                                }
                            } else {
                                Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            }

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
                                    //go to subscription page
                                    if (NetworkStatus.getInstance().isConnected(this)) {

                                        setResultAtFinishActivity();


                                    } else {
                                        Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    if (PlanId.equals("1") && socialAuthOutputModel.getIsSubscribed().equals("0")) {

                                        if (CheckSubscriptionHandler.isSubscriptionAllowed){
                                            Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);
                                            finish();
                                            overridePendingTransition(0, 0);
                                        }else {
                                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(in);
                                            finish();
                                        }

                                    } else {
                                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                            }
                        }
                    }


                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }


            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;

                }
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(EMAIL_PASSWORD_INVALID, DEFAULT_EMAIL_PASSWORD_INVALID));
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
            }
        } else {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {

            }
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT));
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
        }

    }

//    private class AsynFbRegDetails extends AsyncTask<Void, Void, Void> {
//        // ProgressDialog pDialog;
//        ProgressBarHandler pDialog;
//        int statusCode;
//        String loggedInIdStr;
//        String responseStr;
//        String isSubscribedStr;
//        String loginHistoryIdStr;
//
//        JSONObject myJson = null;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String urlRouteList = Util.rootUrl().trim() + Util.fbRegUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("name", fbName.trim());
//                httppost.addHeader("email", fbEmail.trim());
//                httppost.addHeader("password", "");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("fb_userid", fbUserId.trim());
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage( Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            statusCode = 0;
//                            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage( Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
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
//                    loggedInIdStr = myJson.optString("id");
//                    isSubscribedStr = myJson.optString("isSubscribed");
//                    UniversalIsSubscribed = isSubscribedStr;
//                    if (myJson.has("login_history_id")) {
//                        loginHistoryIdStr = myJson.optString("login_history_id");
//                    }
//
//
//                }
//
//            } catch (Exception e) {
//                statusCode = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//            if (responseStr == null) {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//                    statusCode = 0;
//
//                }
//                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.DETAILS_NOT_FOUND_ALERT, Util.DEFAULT_DETAILS_NOT_FOUND_ALERT));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK));
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//
//            if (statusCode > 0) {
//
//                //    SharedPreferences.Editor editor = pref.edit();
//
//                if (statusCode == 200) {
//                    String displayNameStr = myJson.optString("display_name");
//                    String emailFromApiStr = myJson.optString("email");
//                    String profileImageStr = myJson.optString("profile_image");
//
//                    preferenceManager.setLogInStatusToPref("1");
//                    preferenceManager.setUserIdToPref(loggedInIdStr);
//                    preferenceManager.setPwdToPref("");
//                    preferenceManager.setEmailIdToPref(emailFromApiStr);
//                    preferenceManager.setDispNameToPref(displayNameStr);
//                    preferenceManager.setLoginProfImgoPref(profileImageStr);
//                    preferenceManager.setIsSubscribedToPref(isSubscribedStr);
//                    preferenceManager.setLoginHistIdPref(loginHistoryIdStr);
//
//
//                    if (Util.checkNetwork(LoginActivity.this) == true) {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                        //load video urls according to resolution
//
//
//                        if (Util.check_for_subscription == 1) {
//                            //go to subscription page
//                            if (Util.checkNetwork(LoginActivity.this) == true) {
//                                if (Util.dataModel.getIsFreeContent() == 1) {
//                                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                    getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                    getVideoDetailsInput.setContent_uniq_id(getVideoDetailsInput.getContent_uniq_id());
//                                    getVideoDetailsInput.setStream_uniq_id(getVideoDetailsInput.getStream_uniq_id());
//                                    getVideoDetailsInput.setInternetSpeed(getVideoDetailsInput.getInternetSpeed());
//                                    getVideoDetailsInput.setUser_id(getVideoDetailsInput.getUser_id());
//                                    VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, LoginActivity.this, LoginActivity.this);
//                                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                                } else {
//                                    ValidateUserInput validateUserInput = new ValidateUserInput();
//                                    validateUserInput.setAuthToken(Util.authTokenStr);
//                                    validateUserInput.setUserId(validateUserInput.getUserId());
//                                    validateUserInput.setMuviUniqueId(validateUserInput.getMuviUniqueId());
//                                    validateUserInput.setEpisodeStreamUniqueId(validateUserInput.getEpisodeStreamUniqueId());
//                                    validateUserInput.setSeasonId(validateUserInput.getSeasonId());
//                                    validateUserInput.setLanguageCode(validateUserInput.getLanguageCode());
//                                    validateUserInput.setPurchaseType(validateUserInput.getPurchaseType());
//                                    GetValidateUserAsynTask asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, LoginActivity.this, LoginActivity.this);
//                                    asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
//                                }
//                            } else {
//                                Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage( Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                            }
//
//                        } else {
//
//                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
//                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(in);
//
//                            onBackPressed();
//                        }
//
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage( Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                    }
//
//
//                } else {
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        statusCode = 0;
//
//                    }
//                    android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.EMAIL_PASSWORD_INVALID, Util.DEFAULT_EMAIL_PASSWORD_INVALID));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    dlgAlert.create().show();
//                }
//            } else {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//
//                }
//                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.DETAILS_NOT_FOUND_ALERT, Util.DEFAULT_DETAILS_NOT_FOUND_ALERT));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(LoginActivity.this);
//            pDialog.show();
//
//        }
//    }


    // Added For  FCM

    public void show_logout_popup(String msg) {
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
            LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View convertView = inflater.inflate(R.layout.logout_popup, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("");

            logout_text = (TextView) convertView.findViewById(R.id.logout_text);
            ok = (Button) convertView.findViewById(R.id.ok);
            cancel = (Button) convertView.findViewById(R.id.cancel);


            // Font implemented Here//
            FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.regular_fonts), logout_text);
            FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.regular_fonts), ok);
            FontUtls.loadFont(LoginActivity.this, getResources().getString(R.string.regular_fonts), cancel);

            //==============end===============//

            // Language Implemented Here //

            logout_text.setText(msg);
            ok.setText(" " + languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK));
            cancel.setText(" " + languagePreference.getTextofLanguage(CANCEL_BUTTON, DEAFULT_CANCEL_BUTTON));

            //==============End===============//


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkStatus.getInstance().isConnected(LoginActivity.this)) {

                        // Call Api For Simultaneous Logout
                        SimultaneousLogoutInput simultaneousLogoutInput = new SimultaneousLogoutInput();
                        simultaneousLogoutInput.setAuthToken(authTokenStr);
                        simultaneousLogoutInput.setDevice_type("1");
                        simultaneousLogoutInput.setEmail_id(regEmailStr);
                        GetSimultaneousLogoutAsync asynSimultaneousLogout = new GetSimultaneousLogoutAsync(simultaneousLogoutInput, LoginActivity.this, LoginActivity.this);
                        asynSimultaneousLogout.executeOnExecutor(threadPoolExecutor);
                    } else {
                        Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    logout_alert.dismiss();
                }
            });
            logout_alert = alertDialog.show();
            logout_alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }

    @Override
    public void onSimultaneousLogoutPreExecuteStarted() {
        progressDialog1 = new ProgressDialog(LoginActivity.this, R.style.MyTheme);
        progressDialog1.setCancelable(false);
        progressDialog1.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
        progressDialog1.setIndeterminate(false);
        progressDialog1.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dialog_progress_rawable));
        progressDialog1.show();
    }

    @Override
    public void onSimultaneousLogoutPostExecuteCompleted(int code) {
        if (progressDialog1 != null && progressDialog1.isShowing()) {
            progressDialog1.hide();
            progressDialog1 = null;
        }
        if (code == 200) {
            // Allow the user to activity_login
            if (logout_alert.isShowing()) {
                logout_alert.dismiss();
            }

            Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE, DEFAULT_SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(TRY_AGAIN, DEFAULT_TRY_AGAIN), Toast.LENGTH_LONG).show();
        }
    }

    // This API is called for simultaneous logout
//    private class AsynSimultaneousLogout extends AsyncTask<Void, Void, Void> {
//        ProgressDialog progressDialog1;
//        String responseStr;
//        int statusCode = 0;
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.LogoutAll.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("device_type", "1");
//                httppost.addHeader("email_id", regEmailStr);
//
//                // Execute HTTP Post Request
//                try {
//
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//                    LogUtil.showLog("MUVI", "Response Of the Simultaneous Logout =" + responseStr);
//
//
//                } catch (Exception e) {
//                    responseStr = "0";
//                }
//
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//                }
//
//            } catch (Exception e) {
//                responseStr = "0";
//                e.printStackTrace();
//
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void result) {
//
//            if (progressDialog1 != null && progressDialog1.isShowing()) {
//                progressDialog1.hide();
//                progressDialog1 = null;
//            }
//            if (statusCode == 200) {
//                // Allow the user to activity_login
//                if (logout_alert.isShowing()) {
//                    logout_alert.dismiss();
//                }
//
//                Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage( Util.SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE, Util.DEFAULT_SIMULTANEOUS_LOGOUT_SUCCESS_MESSAGE), Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage( Util.TRY_AGAIN, Util.DEFAULT_TRY_AGAIN), Toast.LENGTH_LONG).show();
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog1 = new ProgressDialog(LoginActivity.this, R.style.MyTheme);
//            progressDialog1.setCancelable(false);
//            progressDialog1.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
//            progressDialog1.setIndeterminate(false);
//            progressDialog1.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dialog_progress_rawable));
//            progressDialog1.show();
//        }
//
//    }


    public void ShowPpvPopUp() {

        try {
            if (Util.currencyModel.getCurrencySymbol() == null) {
                Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
            finish();
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) LoginActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

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


        String subscriptionStr = preferenceManager.getIsSubscribedFromPref();

        if (subscriptionStr.trim().equals("1")) {
            if (Util.dataModel.getIsAPV() == 1) {

                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvEpisodeSubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvSeasonSubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvShowSubscribedStr());
            } else {
                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvEpisodeSubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvSeasonSubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvShowSubscribedStr());
            }
        } else {
            if (Util.dataModel.getIsAPV() == 1) {

                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvEpisodeUnsubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvSeasonUnsubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.apvModel.getApvShowUnsubscribedStr());
            } else {
                episodePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvEpisodeUnsubscribedStr());
                seasonPriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvSeasonUnsubscribedStr());
                completePriceTextView.setText(Util.currencyModel.getCurrencySymbol() + " " + Util.ppvModel.getPpvShowUnsubscribedStr());
            }
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
                final Intent showPaymentIntent = new Intent(LoginActivity.this, PPvPaymentInfoActivity.class);
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
                finish();
                overridePendingTransition(0, 0);

            }
        });
    }

    public void payment_for_single_part() {

        try {
            if (Util.currencyModel.getCurrencySymbol() == null) {
                Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
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

        final Intent showPaymentIntent = new Intent(LoginActivity.this, PPvPaymentInfoActivity.class);
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
        if (Util.dataModel.getIsAPV() == 1) {
            showPaymentIntent.putExtra("isConverted", 0);
        } else {
            showPaymentIntent.putExtra("isConverted", 1);

        }

        showPaymentIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(showPaymentIntent);
        finish();
        overridePendingTransition(0, 0);
    }


    // Following Code Has Been Added For The Device Management

    @Override
    public void onCheckDevicePreExecuteStarted() {
        pDialog = new ProgressBarHandler(LoginActivity.this);
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
        }
        deviceRestrictionMessage = message;

        if (code > 0) {
            if (code == 200) {

                // Allow The User To Login
                if (Util.check_for_subscription == 1) {
                    //go to subscription page
                    if (NetworkStatus.getInstance().isConnected(this)) {
                        if (Util.dataModel.getIsFreeContent() == 1) {
                            GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                            getVideoDetailsInput.setAuthToken(authTokenStr);
                            getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                            getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                            getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                            getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                            getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, LoginActivity.this, LoginActivity.this);
                            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                        } else {
                            setResultAtFinishActivity();

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (PlanId.equals("1") && UniversalIsSubscribed.equals("0")) {
                        if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                            Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0, 0);
                        }else {
                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }
                    } else {
                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
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
//                httppost.addHeader("google_id", languagePreference.getTextofLanguage( Util.GOOGLE_FCM_TOKEN, Util.DEFAULT_GOOGLE_FCM_TOKEN));
//                httppost.addHeader("device_type", "1");
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage( Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//                httppost.addHeader("device_info", deviceName + "," + languagePreference.getTextofLanguage( Util.ANDROID_VERSION, Util.DEFAULT_ANDROID_VERSION) + " " + Build.VERSION.RELEASE);
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                    LogUtil.showLog("MUVI3", "responseStr of check device=" + responseStr);
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            statusCode = 0;
//                            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage( Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//
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
//                    if (Util.check_for_subscription == 1) {
//                        //go to subscription page
//                        if (Util.checkNetwork(LoginActivity.this) == true) {
//                            if (Util.dataModel.getIsFreeContent() == 1) {
//                                GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                getVideoDetailsInput.setContent_uniq_id(getVideoDetailsInput.getContent_uniq_id());
//                                getVideoDetailsInput.setStream_uniq_id(getVideoDetailsInput.getStream_uniq_id());
//                                getVideoDetailsInput.setInternetSpeed(getVideoDetailsInput.getInternetSpeed());
//                                getVideoDetailsInput.setUser_id(getVideoDetailsInput.getUser_id());
//                                VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, LoginActivity.this, LoginActivity.this);
//                                asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                            } else {
//                                ValidateUserInput validateUserInput = new ValidateUserInput();
//                                validateUserInput.setAuthToken(Util.authTokenStr);
//                                validateUserInput.setUserId(validateUserInput.getUserId());
//                                validateUserInput.setMuviUniqueId(validateUserInput.getMuviUniqueId());
//                                validateUserInput.setEpisodeStreamUniqueId(validateUserInput.getEpisodeStreamUniqueId());
//                                validateUserInput.setSeasonId(validateUserInput.getSeasonId());
//                                validateUserInput.setLanguageCode(validateUserInput.getLanguageCode());
//                                validateUserInput.setPurchaseType(validateUserInput.getPurchaseType());
//                                GetValidateUserAsynTask asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, LoginActivity.this, LoginActivity.this);
//                                asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
//
//                            }
//                        } else {
//                            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage( Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        if (planId.equals("1") && UniversalIsSubscribed.equals("0")) {
//                            Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            finish();
//                            overridePendingTransition(0, 0);
//                        } else {
//                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
//                            in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(in);
//                            finish();
//                        }
//                    }
//                } else {
//                    // Call For Logout
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
//            pDialog = new ProgressBarHandler(LoginActivity.this);
//            pDialog.show();
//        }
//    }

    public void LogOut() {
        String loginHistoryIdStr = preferenceManager.getLoginHistIdFromPref();
        LogUtil.showLog("MUVI3", "logout Called");
        LogoutInput logoutInput = new LogoutInput();
        logoutInput.setAuthToken(authTokenStr);
        logoutInput.setLogin_history_id(loginHistoryIdStr);
        logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, this, this);
        asynLogoutDetails.executeOnExecutor(threadPoolExecutor);
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
//            String urlRouteList =Util.rootUrl().trim()+Util.logoutUrl.trim();
//            try {
//                HttpClient httpclient=new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("login_history_id",loginHistoryIdStr);
//                httppost.addHeader("lang_code",languagePreference.getTextofLanguage(Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                    LogUtil.showLog("MUVI3","responseStr of logout="+responseStr);
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            responseCode = 0;
//                            Toast.makeText(LoginActivity.this,languagePreference.getTextofLanguage( Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                }catch (IOException e) {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                    responseCode = 0;
//                    e.printStackTrace();
//                }
//                if(responseStr!=null){
//                    JSONObject myJson = new JSONObject(responseStr);
//                    responseCode = Integer.parseInt(myJson.optString("code"));
//                }
//
//            }
//            catch (Exception e) {
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
//                Toast.makeText(LoginActivity.this,languagePreference.getTextofLanguage( Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if(responseStr == null){
//                Toast.makeText(LoginActivity.this,languagePreference.getTextofLanguage( Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseCode == 0) {
//                Toast.makeText(LoginActivity.this,languagePreference.getTextofLanguage( Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseCode > 0) {
//                if (responseCode == 200) {
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.clear();
//                    editor.commit();
//                    SharedPreferences loginPref = getSharedPreferences(Util.LOGIN_PREF, 0); // 0 - for private mode
//                    if (loginPref!=null) {
//                        SharedPreferences.Editor countryEditor = loginPref.edit();
//                        countryEditor.clear();
//                        countryEditor.commit();
//                    }
//
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(UniversalErrorMessage);
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(Util.BUTTON_OK,Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(Util.BUTTON_OK,Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                dlgAlert.create().show();
//
//                }
//                else {
//                    Toast.makeText(LoginActivity.this,languagePreference.getTextofLanguage( Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            pDialog = new ProgressBarHandler(LoginActivity.this);
//            pDialog.show();
//        }
//    }


    /////////Google sign in and sign out by nihar/////start//////////
    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(LoginActivity.this, "Log out sucessfull", Toast.LENGTH_SHORT).show();
                    }
                });
    }
   /*
   *@onhandleSigninResult call when we recive Auth conformation from google
    * Dont remove implimentation for onDemand just use 9.4.0 not latest v because it ll affect the chromecast . by @nihar.
    */

    // [START handleSignInResult]
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
    // [END handleSignInResult]

   /* private class  AsynGmailReg extends AsyncTask<Void, Void, Void> {
        int statusCode;
        String loggedInIdStr;
        String responseStr;
        String isSubscribedStr;
        String loginHistoryIdStr;
        ProgressBarHandler pDialog;

        JSONObject myJson = null;

        @Override
        protected Void doInBackground(Void... params) {
            String urlRouteList = Util.rootUrl().trim() + Util.fbRegUrl.trim();
//            String urlRouteList = "https://www.muvi.com/rest/socialAuth";
            try {
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("name", Authname);
                httppost.addHeader("email", AuthEmail);
                httppost.addHeader("password","");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("fb_userid", AuthId);
                httppost.addHeader("profile_image", AuthImageUrl);
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    Log.v(TAG,""+responseStr);
                } catch (org.apache.http.conn.ConnectTimeoutException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusCode = 0;

                            //  Toast.makeText(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this,Util.SLOW_INTERNET_CONNECTION,Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();

                        }

                    });

                } catch (IOException e) {
                    statusCode = 0;

                    e.printStackTrace();
                }
                if(responseStr!=null){
                    myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    loggedInIdStr = myJson.optString("id");
                    isSubscribedStr = myJson.optString("isSubscribed");
                    if (myJson.has("login_history_id")) {
                        loginHistoryIdStr = myJson.optString("login_history_id");
                    }


                }

            }
            catch (Exception e) {
                statusCode = 0;

            }

            return null;
        }


        protected void onPostExecute(Void result) {
            if (responseStr == null) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    statusCode = 0;

                }
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(Util.getTextofLanguage(LoginActivity.this, Util.DETAILS_NOT_FOUND_ALERT, Util.DEFAULT_DETAILS_NOT_FOUND_ALERT));
                dlgAlert.setTitle(Util.getTextofLanguage(LoginActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
                dlgAlert.setMessage(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK));
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();
            }

            if (statusCode > 0) {

                SharedPreferences.Editor editor = pref.edit();

                if (statusCode == 200) {
                    String displayNameStr = myJson.optString("display_name");
                    String emailFromApiStr = myJson.optString("email");
                    String profileImageStr = myJson.optString("profile_image");

                    editor.putString("PREFS_LOGGEDIN_KEY", "1");
                    editor.putString("PREFS_LOGGEDIN_ID_KEY", loggedInIdStr);
                    editor.putString("PREFS_LOGGEDIN_PASSWORD_KEY", "");
                    editor.putString("PREFS_LOGIN_EMAIL_ID_KEY", emailFromApiStr);
                    editor.putString("PREFS_LOGIN_DISPLAY_NAME_KEY", displayNameStr);
                    editor.putString("PREFS_LOGIN_PROFILE_IMAGE_KEY", profileImageStr);
                    editor.putString("PREFS_LOGIN_ISSUBSCRIBED_KEY", isSubscribedStr);
                    editor.putString("PREFS_LOGIN_HISTORYID_KEY", loginHistoryIdStr);

                    preferenceManager.setLogInStatusToPref("1");
                    preferenceManager.setUserIdToPref(login_output.getId());
                    preferenceManager.setPwdToPref("");
                    preferenceManager.setEmailIdToPref(login_output.getEmail());
                    preferenceManager.setDispNameToPref(login_output.getDisplay_name());
                    preferenceManager.setLoginProfImgoPref(login_output.getProfile_image());
                    preferenceManager.setIsSubscribedToPref(login_output.getIsSubscribed());
                    preferenceManager.setLoginHistIdPref(login_output.getLogin_history_id());

                    Date todayDate = new Date();
                    String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);
                    editor.putString("date", todayStr.trim());
                    editor.commit();


                    if (Util.checkNetwork(LoginActivity.this) == true) {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }
                        //load video urls according to resolution
                        if (Util.getTextofLanguage(LoginActivity.this, Util.IS_RESTRICT_DEVICE, Util.DEFAULT_IS_RESTRICT_DEVICE).trim().equals("1")) {

                            Log.v("BIBHU", "isRestrictDevice called");
                            // Call For Check Api.
                            AsynCheckDevice asynCheckDevice = new AsynCheckDevice();
                            asynCheckDevice.executeOnExecutor(threadPoolExecutor);
                        } else {
                            if (getIntent().getStringExtra("from") != null) {
                                */

    /**
     * review
     **//*
                                onBackPressed();
                            } else {
                                if (Util.check_for_subscription == 1) {
                                    //go to subscription page
                                    if (Util.checkNetwork(LoginActivity.this) == true) {
                                        asynValidateUserDetails = new AsynValidateUserDetails();
                                        asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
                                    } else {
                                        Util.showToast(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this, Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION));
                                        // Toast.makeText(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this,Util.NO_INTERNET_CONNECTION,Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    if (PlanId.equals("1") && isSubscribedStr.equals("0")) {
                                        Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        if (RegisterActivity.fa != null) {
                                            RegisterActivity.fa.finish();
                                        }
                                        if (ForgotPasswordActivity.forgotA != null) {
                                            ForgotPasswordActivity.forgotA.finish();
                                        }
                                        onBackPressed();
                                    } else {
                                        Log.v("ABC", "FBREGISTRATIONDETAILS_POST");

                                        Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        onBackPressed();
                                    }
                                }
                            }
                        }

                    } else {
                        Util.showToast(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this, Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION));
                    }


                } else {
                    try {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }
                    } catch (IllegalArgumentException ex) {
                        statusCode = 0;

                    }
                    android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                    dlgAlert.setMessage(Util.getTextofLanguage(LoginActivity.this, Util.EMAIL_PASSWORD_INVALID, Util.DEFAULT_EMAIL_PASSWORD_INVALID));
                    dlgAlert.setTitle(Util.getTextofLanguage(LoginActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();
                }
            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {

                }
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(Util.getTextofLanguage(LoginActivity.this, Util.DETAILS_NOT_FOUND_ALERT, Util.DEFAULT_DETAILS_NOT_FOUND_ALERT));
                dlgAlert.setTitle(Util.getTextofLanguage(LoginActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
                dlgAlert.setPositiveButton(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(Util.getTextofLanguage(LoginActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressBarHandler(LoginActivity.this);
            pDialog.show();
        }
    }*/

    /////////////////////////end/////////////////////////
    @Override
    public void onGmailRegPreExecuteStarted() {
        pDialog = new ProgressBarHandler(LoginActivity.this);
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


            if (NetworkStatus.getInstance().isConnected(LoginActivity.this)) {

                //load video urls according to resolution
                if ((featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE))){

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

                    if (Util.favorite_clicked) {
                        finish();
                        return;
                    }

                    if (getIntent().getStringExtra("from") != null) {
                        //** review **//*
                        onBackPressed();
                    } else {
                        if (Util.check_for_subscription == 1) {
                            //go to subscription page
                            if (NetworkStatus.getInstance().isConnected(LoginActivity.this)) {
                                setResultAtFinishActivity();

                            } else {
                                Util.showToast(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                                // Toast.makeText(LoginActivity.this, Util.getTextofLanguage(LoginActivity.this,Util.NO_INTERNET_CONNECTION,Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            if (PlanId.equals("1") && preferenceManager.getIsSubscribedFromPref().equals("0")) {
                                if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                                    Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                               /* if (RegisterActivity.fa != null) {
                                    RegisterActivity.fa.finish();
                                }
                                if (ForgotPasswordActivity.forgotA != null) {
                                    ForgotPasswordActivity.forgotA.finish();
                                }*/
                                    onBackPressed();
                                }else {
                                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    onBackPressed();
                                }
                            } else {

                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                onBackPressed();
                            }
                        }
                    }
                }

            } else {
                Util.showToast(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
            }


        } else {

            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
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


    public void handleFbUserDetails(String fbUserId, String fbEmail, String fbName) {
        this.fbUserId = fbUserId;
        this.fbEmail = fbEmail;
        this.fbName = fbName;
        CheckFbUserDetailsInput checkFbUserDetailsInput = new CheckFbUserDetailsInput();
        checkFbUserDetailsInput.setAuthToken(authTokenStr);
        checkFbUserDetailsInput.setFb_userid(fbUserId.trim());
        asynCheckFbUserDetails = new CheckFbUserDetailsAsyn(checkFbUserDetailsInput, LoginActivity.this, LoginActivity.this);
        asynCheckFbUserDetails.executeOnExecutor(threadPoolExecutor);

    }

    public void handleActionForValidateUserPayment(String validUserStr, String message, String Subscription_Str) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(this)) {
                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, this, this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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
                    } else if (PlanId.equals("1") && Subscription_Str.equals("0")) {
                        if (CheckSubscriptionHandler.isSubscriptionAllowed) {
                            Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0, 0);
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
                if (NetworkStatus.getInstance().isConnected(LoginActivity.this)) {
                    Log.v("MUVI", "VV VV VV");

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, this, this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    Util.showActivateSubscriptionWatchVideoAleart(this, alertShowMsg);
                }

            }
        }
    }

    public void setResultAtFinishActivity(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


















    // *********************************************************************************************************//




    public void callSignin(LanguagePreference languagePreference){
        gmailTest.setText(languagePreference.getTextofLanguage(GMAIL_SIGNIN, DEFAULT_GMAIL_SIGNIN));
        googleSignView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    public void callFblogin(final CallbackManager callbackManager,Button registerButton,LanguagePreference languagePreference){

        this.registerButton=registerButton;
        this.languagePreference=languagePreference;
        fbLoginTextView.setText(languagePreference.getTextofLanguage(LOGIN_FACEBOOK,DEFAULT_LOGIN_FACEBOOK));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithFacebookButton.performClick();

                loginWithFacebookButton.setPressed(true);

                loginWithFacebookButton.invalidate();

                loginWithFacebookButton.registerCallback(callbackManager, mCallBack);

                loginWithFacebookButton.setPressed(false);

                loginWithFacebookButton.invalidate();

            }
        });
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            //progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {


                            JSONObject json = response.getJSONObject();
                            try {
                                if (json != null) {

                                    String fName = "";
                                    if ((json.has("id")) && json.getString("id").trim() != null && !json.getString("id").trim().isEmpty() && !json.getString("id").trim().equals("null") && !json.getString("id").trim().matches("")) {
                                        fbUserId = json.getString("id");
                                    }
                                    if ((json.has("first_name")) && json.getString("first_name").trim() != null && !json.getString("first_name").trim().isEmpty() && !json.getString("first_name").trim().equals("null") && !json.getString("first_name").trim().matches("")) {
                                        if ((json.has("last_name")) && json.getString("last_name").trim() != null && !json.getString("last_name").trim().isEmpty() && !json.getString("last_name").trim().equals("null") && !json.getString("last_name").trim().matches("")) {
                                            fbName = json.getString("first_name") + " " + json.getString("last_name");
                                        }
                                        fName =  json.getString("first_name");
                                    } else {

                                        if ((json.has("last_name")) && json.getString("last_name").trim() != null && !json.getString("last_name").trim().isEmpty() && !json.getString("last_name").trim().equals("null") && !json.getString("last_name").trim().matches("")) {
                                            fbName = json.getString("last_name");
                                            fName =  json.getString("last_name");
                                        }else{
                                            if ((json.has("name")) && json.getString("name").trim() != null && !json.getString("name").trim().isEmpty() && !json.getString("name").trim().equals("null") && !json.getString("name").trim().matches("")) {
                                                fbName = json.getString("name");
                                                fName = json.getString("name").replace(" ","").trim();
                                            }
                                        }

                                    }

                                    if ((json.has("email")) && json.getString("email").trim() != null && !json.getString("email").trim().isEmpty() && !json.getString("email").trim().equals("null") && !json.getString("email").trim().matches("")) {
                                        fbEmail = json.getString("email");
                                    } else {
                                        if (fbName != null && !fbName.matches("")) {
                                            fbEmail = fName + "@facebook.com";
                                        } else {
                                            fbName = fbUserId;
                                            fbEmail = fbUserId + "@facebook.com";
                                        }
                                    }


                                    registerButton.setVisibility(View.GONE);
                                    loginWithFacebookButton.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.GONE);
                                    handleFbUserDetails(fbUserId,fbEmail,fbName);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

            registerButton.setVisibility(View.VISIBLE);
            loginWithFacebookButton.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT), Toast.LENGTH_LONG).show();
            //progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {

            registerButton.setVisibility(View.VISIBLE);
            loginWithFacebookButton.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT), Toast.LENGTH_LONG).show();

            //progressDialog.dismiss();
        }
    };

    public void sendBroadCast()
    {
        Intent Sintent = new Intent("LOGIN_SUCCESS");
        sendBroadcast(Sintent);
    }

}

// *********************************************************************************************************//

