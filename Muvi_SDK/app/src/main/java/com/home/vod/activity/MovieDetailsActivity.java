package com.home.vod.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.ResultCallback;
import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiController.AddToFavAsync;
import com.home.apisdk.apiController.DeleteFavAsync;
import com.home.apisdk.apiController.GetContentDetailsAsynTask;
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetLanguageListAsynTask;
import com.home.apisdk.apiController.GetMonitizationDetailsAsync;
import com.home.apisdk.apiController.GetTranslateLanguageAsync;
import com.home.apisdk.apiController.GetValidateUserAsynTask;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiController.ValidateVoucherAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiController.ViewContentRatingAsynTask;
import com.home.apisdk.apiController.VoucherSubscriptionAsyntask;
import com.home.apisdk.apiModel.APVModel;
import com.home.apisdk.apiModel.AddToFavInputModel;
import com.home.apisdk.apiModel.AddToFavOutputModel;
import com.home.apisdk.apiModel.ContentDetailsInput;
import com.home.apisdk.apiModel.ContentDetailsOutput;
import com.home.apisdk.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.DeleteFavInputModel;
import com.home.apisdk.apiModel.DeleteFavOutputModel;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.MonitizationDetailsInput;
import com.home.apisdk.apiModel.MonitizationDetailsOutput;
import com.home.apisdk.apiModel.ValidateVoucherInputModel;
import com.home.apisdk.apiModel.ValidateVoucherOutputModel;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.LanguageListInputModel;
import com.home.apisdk.apiModel.LanguageListOutputModel;
import com.home.apisdk.apiModel.LogoutInput;
import com.home.apisdk.apiModel.PPVModel;
import com.home.apisdk.apiModel.ValidateUserInput;
import com.home.apisdk.apiModel.ValidateUserOutput;
import com.home.apisdk.apiModel.VoucherSubscriptionInputModel;
import com.home.apisdk.apiModel.VoucherSubscriptionOutputModel;
import com.home.vod.BuildConfig;
import com.home.vod.CheckVoucherOrPpvPaymentHandler;
import com.home.vod.EpisodeListOptionMenuHandler;
import com.home.vod.HandleRatingbar;
import com.home.vod.LoginRegistrationOnContentClickHandler;
import com.home.vod.MonetizationHandler;
import com.home.vod.R;
import com.home.apisdk.apiModel.ViewContentRatingInputModel;
import com.home.apisdk.apiModel.ViewContentRatingOutputModel;
import com.home.vod.adapter.LanguageCustomAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.DataModel;
import com.home.vod.model.LanguageModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.common.images.WebImage;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.ResizableCustomView;
import com.home.vod.util.Util;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.Player;
import player.activity.ResumePopupActivity;
import player.activity.ThirdPartyPlayer;
import player.activity.YouTubeAPIActivity;

import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import static com.home.vod.preferences.LanguagePreference.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.ADD_A_REVIEW;
import static com.home.vod.preferences.LanguagePreference.ADVANCE_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.ALREADY_PURCHASE_THIS_CONTENT;
import static com.home.vod.preferences.LanguagePreference.APP_ON;
import static com.home.vod.preferences.LanguagePreference.APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ADD_A_REVIEW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ADVANCE_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ALREADY_PURCHASE_THIS_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_ON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_HAS_FAVORITE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_IS_STREAMING_RESTRICTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LANGUAGE_POPUP_LOGIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_DOWNLOAD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PROFILE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_REVIEWS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_TRAILER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_YES;
import static com.home.vod.preferences.LanguagePreference.ENTER_VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.NO;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.PROFILE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.REVIEWS;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.VIEW_TRAILER;
import static com.home.vod.preferences.LanguagePreference.VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.VOUCHER_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.YES;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;
import static com.home.vod.util.Util.languageModel;
import static player.utils.Util.DEFAULT_IS_CHROMECAST;
import static player.utils.Util.DEFAULT_IS_OFFLINE;


public class MovieDetailsActivity extends AppCompatActivity implements LogoutAsynctask.LogoutListener,
        GetValidateUserAsynTask.GetValidateUserListener, VideoDetailsAsynctask.VideoDetailsListener,
        GetLanguageListAsynTask.GetLanguageListListener,
        GetContentDetailsAsynTask.GetContentDetailsListener,
        AddToFavAsync.AddToFavListener, ViewContentRatingAsynTask.ViewContentRatingListener
        , DeleteFavAsync.DeleteFavListener, GetTranslateLanguageAsync.GetTranslateLanguageInfoListener,
        GetIpAddressAsynTask.IpAddressListener, GetMonitizationDetailsAsync.GetMonitizationDetailsListner
        , VoucherSubscriptionAsyntask.VoucherSubscriptionListener, ValidateVoucherAsynTask.ValidateVoucherListener {
    int prevPosition = 0;
    PreferenceManager preferenceManager;
    private static final int MAX_LINES = 3;
    ProgressBarHandler pDialog;
    int ratingAddedByUser = 1;
    String ipAddressStr = "";
    String filename = "";
    static File mediaStorageDir;
    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();
    MonetizationHandler monetizationHandler;
    CheckVoucherOrPpvPaymentHandler checkVoucherOrPpvPaymentHandler;
    GetMonitizationDetailsAsync getMonitizationDetailsAsync;


    VideoDetailsAsynctask asynLoadVideoUrls;
    GetValidateUserAsynTask asynValidateUserDetails;
    GetContentDetailsAsynTask asynLoadMovieDetails;
    ViewContentRatingAsynTask asynGetReviewDetails;
    String loggedInIdStr;
    AddToFavAsync asynFavoriteAdd;
    Toolbar mActionBarToolbar;
    ImageView moviePoster;
    PPVModel ppvmodel;
    APVModel advmodel;
    CurrencyModel currencymodel;
    ImageView playButton, favorite_view;
    String PlanId = "";
    ImageButton offlineImageButton;
    Button watchTrailerButton;
    Button preorderButton;
    int loginresultcode = 0;
    //for resume play
    String seek_status = "";
    String resume_time = "";
    int Played_Length = 0;
    String watch_status_String = "start";

    String default_Language = "";
    private boolean isThirdPartyTrailer = false;
    String Default_Language = "";
    String Previous_Selected_Language = "";

    RelativeLayout viewStoryLayout;

    //Add By Bibhu Later.
    TextView videoStoryTextView;
    Button storyViewMoreButton;

    boolean isExpanded = false;

    TextView videoTitle, videoGenreTextView, videoDurationTextView, videoCensorRatingTextView, videoCensorRatingTextView1,
            videoReleaseDateTextView, videoCastCrewTitleTextView;
    String movieNameStr;
    String movieTypeStr = "";
    boolean castStr = false;
    String censorRatingStr = "";
    String videoduration = "";
    String movieDetailsStr = "";
    String Video_Url = "";
    String movieThirdPartyUrl = "";


//     ///****rating****///

    RatingBar ratingBar;
    TextView viewRatingTextView;
    String movieIdStr;

    /*** rating***///
    String rating;
    String reviews;


    Intent DataIntent;
    String permalinkStr;
    String movieTrailerUrlStr, movieReleaseDateStr = "";
    String movieStreamUniqueId, bannerImageId, posterImageId, priceForUnsubscribedStr, priceFosubscribedStr, currencyIdStr, currencyCountryCodeStr,
            currencySymbolStr;
    String movieUniqueId = "", isEpisode = "";
    int isFreeContent, isPPV, isConverted, contentTypesId, isAPV;

    RelativeLayout noInternetConnectionLayout, noDataLayout, iconImageRelativeLayout, bannerImageRelativeLayout;
    LinearLayout story_layout;
    String sucessMsg;
    int isFavorite;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    int isLogin = 0;
    TextView noDataTextView;
    TextView noInternetTextView;
    String email, id;
    LanguageCustomAdapter languageCustomAdapter;
    AlertDialog alert;
    String isMemberSubscribed, loggedInStr;
    HandleRatingbar handleRatingbar;

    // Added For The Voucher

    int isVoucher = 0;
    String VoucherCode = "";

    TextView content_label, content_name, voucher_success;
    EditText voucher_code;
    Button apply, watch_now;
    boolean watch_status = false;
    String ContentName = "";
    AlertDialog voucher_alert;
    Player playerModel;
    // Video_Details_Output _video_details_output;
    LanguagePreference languagePreference;
    FeatureHandler featureHandler;
    private EpisodeListOptionMenuHandler episodeListOptionMenuHandler;
    public static final int VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE = 8888;
    public static final int PAYMENT_REQUESTCODE = 8889;


    // voucher ends here //

    @Override
    protected void onResume() {

        super.onResume();

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

        /**FAVORITE*/
        if (Util.favorite_clicked == true) {
            ContentDetailsInput contentDetailsInput = new ContentDetailsInput();
            contentDetailsInput.setAuthToken(authTokenStr);
            contentDetailsInput.setPermalink(permalinkStr);
            contentDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
            contentDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
            contentDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            asynLoadMovieDetails = new GetContentDetailsAsynTask(contentDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
            asynLoadMovieDetails.executeOnExecutor(threadPoolExecutor);
        }

        invalidateOptionsMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        id = preferenceManager.getUseridFromPref();
        email = preferenceManager.getEmailIdFromPref();
        episodeListOptionMenuHandler.createOptionMenu(menu, preferenceManager, languagePreference,featureHandler);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                final Intent searchIntent = new Intent(MovieDetailsActivity.this, SearchActivity.class);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(searchIntent);
                // Not implemented here
                return false;
            case R.id.action_filter:

                // Not implemented here
                return false;
            case R.id.action_login:

                Intent loginIntent = new Intent(MovieDetailsActivity.this, LoginActivity.class);
                Util.check_for_subscription = 0;
                startActivity(loginIntent);
                // Not implemented here
                return false;
            case R.id.action_register:

                Intent registerIntent = new Intent(MovieDetailsActivity.this, RegisterActivity.class);
                Util.check_for_subscription = 0;
                startActivity(registerIntent);
                // Not implemented here
                return false;
            case R.id.menu_item_favorite:

                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                favoriteIntent.putExtra("sectionName",languagePreference.getTextofLanguage(MY_FAVOURITE, DEFAULT_MY_FAVOURITE));
                favoriteIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(favoriteIntent);
                // Not implemented here
                return false;
            case R.id.action_mydownload:

                Intent mydownload = new Intent(MovieDetailsActivity.this, MyDownloads.class);
                startActivity(mydownload);
                // Not implemented here
                return false;
            case R.id.menu_item_language:

                // Not implemented here
                default_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);
                Previous_Selected_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);

                if (Util.languageModel != null && Util.languageModel.size() > 0) {


                    ShowLanguagePopup();

                } else {
                    LanguageListInputModel languageListInputModel = new LanguageListInputModel();
                    languageListInputModel.setAuthToken(authTokenStr);
                    GetLanguageListAsynTask asynGetLanguageList = new GetLanguageListAsynTask(languageListInputModel, this, this);
                    asynGetLanguageList.executeOnExecutor(threadPoolExecutor);
                }
                return false;

            case R.id.menu_item_profile:

                Intent profileIntent = new Intent(MovieDetailsActivity.this, ProfileActivity.class);
                profileIntent.putExtra("EMAIL", email);
                profileIntent.putExtra("LOGID", id);
                startActivity(profileIntent);
                // Not implemented here
                return false;
            case R.id.action_purchage:

                Intent purchaseintent = new Intent(MovieDetailsActivity.this, PurchaseHistoryActivity.class);
                startActivity(purchaseintent);
                // Not implemented here
                return false;

            case R.id.action_logout:

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(SIGN_OUT_WARNING, DEFAULT_SIGN_OUT_WARNING));
                dlgAlert.setTitle("");

                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(YES, DEFAULT_YES), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if(NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                        // dialog.cancel();
                        LogoutInput logoutInput = new LogoutInput();
                        logoutInput.setAuthToken(authTokenStr);
                        logoutInput.setLogin_history_id(preferenceManager.getLoginHistIdFromPref());
                        logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                        asynLogoutDetails.executeOnExecutor(threadPoolExecutor);


                        dialog.dismiss();
                        }else {
                            Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dlgAlert.setNegativeButton(languagePreference.getTextofLanguage(NO, DEFAULT_NO), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });
                // dlgAlert.setPositiveButton(getResources().getString(R.string.yes_str), null);
                dlgAlert.setCancelable(false);

                dlgAlert.create().show();

                return false;
            default:
                break;
        }

        return false;
    }


    @Override
    public void onLogoutPostExecuteCompleted(int code, String status, String message) {

        if (status == null) {
            Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code > 0) {
            if (code == 200) {
                preferenceManager.clearLoginPref();
                if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
                    final Intent startIntent = new Intent(MovieDetailsActivity.this, SplashScreen.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                } else {
                    final Intent startIntent = new Intent(MovieDetailsActivity.this, MainActivity.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                }

            } else {
                Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }
        }

    }
    /*chromecast-------------------------------------*/

    View view;

    @Override
    public void onGetMonitizationDetailsPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onGetMonitizationDetailsPostExecuteStarted(MonitizationDetailsOutput monitizationDetailsOutput, int code, String status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            code = 0;
        }

        if (code == 200) {
            if (monitizationDetailsOutput.getVoucher() != null) {
                isVoucher = Integer.parseInt(monitizationDetailsOutput.getVoucher());
            } else {
                isVoucher = 0;
            }
            callValidateUserAPI();
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
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
    public void onVoucherSubscriptionPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onVoucherSubscriptionPostExecuteCompleted(VoucherSubscriptionOutputModel voucherSubscriptionOutputModel, int status) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }
        if (status == 200) {

            GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
            getVideoDetailsInput.setAuthToken(authTokenStr);
            getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
            getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
            getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
            getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
            getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

            VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

        } else {
            Toast.makeText(getApplicationContext(), voucherSubscriptionOutputModel.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValidateVoucherPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onValidateVoucherPostExecuteCompleted(ValidateVoucherOutputModel validateVoucherOutputModel, int status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }

        if (status == 200) {


            voucher_success.setVisibility(View.VISIBLE);
            watch_now.setBackgroundResource(R.drawable.button_radious);
            watch_now.setTextColor(Color.parseColor("#ffffff"));
            watch_status = true;

            apply.setEnabled(false);
            apply.setBackgroundResource(R.drawable.voucher_inactive_button);
            apply.setTextColor(Color.parseColor("#7f7f7f"));

        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }


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

    private MenuItem mediaRouteMenuItem;
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

    RelativeLayout relativeOverlayLayout;
    private BroadcastReceiver DELETE_ACTION = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String movieUniqId = intent.getStringExtra("movie_uniq_id").trim();
            if (movieUniqId.equals(movieUniqueId.trim())) {
                isFavorite = 0;
                favorite_view.setImageResource(R.drawable.favorite_unselected);
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.details_layout);
        playerModel = new Player();
        pDialog = new ProgressBarHandler(MovieDetailsActivity.this);
        handleRatingbar = new HandleRatingbar(this);
        monetizationHandler = new MonetizationHandler(this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(DELETE_ACTION, new IntentFilter("ITEM_STATUS"));
        // _video_details_output = new Video_Details_Output();
        languagePreference = LanguagePreference.getLanguagePreference(this);
        featureHandler = FeatureHandler.getFeaturePreference(MovieDetailsActivity.this);
        playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        episodeListOptionMenuHandler = new EpisodeListOptionMenuHandler(this);

        Util.goToLibraryplayer = false;

        moviePoster = (ImageView) findViewById(R.id.bannerImageView);
        playButton = (ImageView) findViewById(R.id.playButton);
        watchTrailerButton = (Button) findViewById(R.id.viewTrailerButton);
        preorderButton = (Button) findViewById(R.id.preOrderButton);
        favorite_view = (ImageView) findViewById(R.id.favorite_view);
        Typeface submitButtonTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.regular_fonts));
        watchTrailerButton.setTypeface(submitButtonTypeface);
        Typeface preorderButtonTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.regular_fonts));
        preorderButton.setTypeface(preorderButtonTypeface);
        preorderButton.setVisibility(View.GONE);

        offlineImageButton = (ImageButton) findViewById(R.id.offlineImageButton);
        videoTitle = (TextView) findViewById(R.id.videoTitle);
        videoGenreTextView = (TextView) findViewById(R.id.videoGenreTextView);
        videoDurationTextView = (TextView) findViewById(R.id.videoDurationTextView);
        videoCensorRatingTextView = (TextView) findViewById(R.id.videoCensorRatingTextView);
        videoCensorRatingTextView1 = (TextView) findViewById(R.id.videoCensorRatingTextView1);
        videoReleaseDateTextView = (TextView) findViewById(R.id.videoReleaseDateTextView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        viewStoryLayout = (RelativeLayout) findViewById(R.id.viewStoryLayout);

        videoStoryTextView = (TextView) findViewById(R.id.videoStoryTextView);
        storyViewMoreButton = (Button) findViewById(R.id.storyViewMoreButton);


        videoCastCrewTitleTextView = (TextView) findViewById(R.id.videoCastCrewTitleTextView);
        videoCastCrewTitleTextView.setVisibility(View.GONE);


        // *** rating***////
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setFocusable(false);
        ratingBar.setVisibility(View.GONE);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        viewRatingTextView = (TextView) findViewById(R.id.viewRatingTextView);
        // loggedInStr = preferenceManager.getLoginStatusFromPref();
        //pref = getSharedPreferences(Util.LOGIN_PREF, 0);

        relativeOverlayLayout = (RelativeLayout) findViewById(R.id.relativeOverlayLayout);

        noInternetConnectionLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));

        iconImageRelativeLayout = (RelativeLayout) findViewById(R.id.iconImageRelativeLayout);
        bannerImageRelativeLayout = (RelativeLayout) findViewById(R.id.bannerImageRelativeLayout);
        story_layout = (LinearLayout) findViewById(R.id.story_layout);

        preferenceManager = PreferenceManager.getPreferenceManager(this);

        permalinkStr = getIntent().getStringExtra(PERMALINK_INTENT_KEY);
        // isLogin = ((Global) getApplicationContext()).getIsLogin();

        isLogin = preferenceManager.getLoginFeatureFromPref();

        ppvmodel = new PPVModel();
        advmodel = new APVModel();
        currencymodel = new CurrencyModel();
        PlanId = (languagePreference.getTextofLanguage(PLAN_ID, DEFAULT_PLAN_ID)).trim();


        // *****rating********///
        viewRatingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(MovieDetailsActivity.this, ReviewActivity.class);
                reviewIntent.putExtra("muviId", movieIdStr);
                startActivityForResult(reviewIntent, 30060);
            }
        });
        /***favorite *****/

        favorite_view.setVisibility(View.GONE);

        favorite_view.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String loggedInStr = "";
                if (preferenceManager.getLoginFeatureFromPref() == 1) {
                    loggedInStr = preferenceManager.getUseridFromPref();
                }

                if (loggedInStr != null) {
                    if (isFavorite == 1) {
                        Log.v("goofy", "Item deleted");

                        DeleteFavInputModel deleteFavInputModel = new DeleteFavInputModel();
                        deleteFavInputModel.setAuthTokenStr(authTokenStr);
                        deleteFavInputModel.setLoggedInStr(preferenceManager.getUseridFromPref());
                        deleteFavInputModel.setMovieUniqueId(movieUniqueId);
                        deleteFavInputModel.setIsEpisode(isEpisode);

                        DeleteFavAsync deleteFavAsync = new DeleteFavAsync(deleteFavInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
                        deleteFavAsync.executeOnExecutor(threadPoolExecutor);


                    } else {

                        AddToFavInputModel addToFavInputModel = new AddToFavInputModel();
                        addToFavInputModel.setAuthToken(authTokenStr);
                        addToFavInputModel.setMovie_uniq_id(movieUniqueId);
                        addToFavInputModel.setLoggedInStr(preferenceManager.getUseridFromPref());
                        addToFavInputModel.setIsEpisodeStr(isEpisode);

                        asynFavoriteAdd = new AddToFavAsync(addToFavInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
                        asynFavoriteAdd.executeOnExecutor(threadPoolExecutor);

                    }
                } else {
                    Util.favorite_clicked = true;
                    final Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            registerActivity.putExtra("from", this.getClass().getName());
                            startActivity(registerActivity);

                        }
                    });

                }

            }
        });
        /***favorite *****/

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //playermodel set data
// *****************set data into playermdel for play in exoplayer************

                playerModel.setStreamUniqueId(movieStreamUniqueId);
                playerModel.setMovieUniqueId(movieUniqueId);
                playerModel.setUserId(preferenceManager.getUseridFromPref());
                playerModel.setEmailId(preferenceManager.getEmailIdFromPref());
                playerModel.setAuthTokenStr(authTokenStr.trim());
                playerModel.setRootUrl(BuildConfig.SERVICE_BASE_PATH);
                playerModel.setEpisode_id("0");
                playerModel.setIsFreeContent(isFreeContent);
                playerModel.setVideoTitle(movieNameStr);
                playerModel.setVideoStory(movieDetailsStr);
                playerModel.setVideoGenre(videoGenreTextView.getText().toString());
                playerModel.setVideoDuration(videoDurationTextView.getText().toString());
                playerModel.setVideoReleaseDate(videoReleaseDateTextView.getText().toString());
                playerModel.setCensorRating(censorRatingStr);
                playerModel.setContentTypesId(contentTypesId);
                playerModel.setPosterImageId(posterImageId);
                playerModel.setCastCrew(castStr);

                Log.v("BKS", "stramid=" + playerModel.getStreamUniqueId());
                Log.v("BKS", "movieID=" + playerModel.getMovieUniqueId());
                Log.v("BKS", "userid=" + preferenceManager.getUseridFromPref());
                Log.v("BKS", "emailid=" + playerModel.getEmailId());


                DataModel dbModel = new DataModel();
                dbModel.setIsFreeContent(isFreeContent);
                dbModel.setIsAPV(isAPV);
                dbModel.setIsPPV(isPPV);
                dbModel.setIsConverted(isConverted);
                dbModel.setMovieUniqueId(movieUniqueId);
                dbModel.setStreamUniqueId(movieStreamUniqueId);
                dbModel.setThirdPartyUrl(movieThirdPartyUrl);
                dbModel.setVideoTitle(movieNameStr);
                dbModel.setVideoStory(movieDetailsStr);
                dbModel.setVideoGenre(videoGenreTextView.getText().toString());
                dbModel.setVideoDuration(videoDurationTextView.getText().toString());
                dbModel.setVideoReleaseDate(videoReleaseDateTextView.getText().toString());
                dbModel.setCensorRating(censorRatingStr);
                dbModel.setCastCrew(castStr);
                dbModel.setEpisode_id("0");
                dbModel.setSeason_id("0");
                dbModel.setPurchase_type("show");
                dbModel.setPosterImageId(posterImageId);
                dbModel.setContentTypesId(contentTypesId);

                Util.dataModel = dbModel;
                SubTitleName.clear();
                SubTitlePath.clear();
                ResolutionUrl.clear();
                ResolutionFormat.clear();

                if (preferenceManager.getLoginFeatureFromPref() == 1) {
                    if (preferenceManager != null) {
                        String loggedInStr = preferenceManager.getUseridFromPref();

                        if (loggedInStr == null) {
                            Util.check_for_subscription = 1;
                            Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                            registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            registerActivity.putExtra("PlayerModel", playerModel);
                            startActivityForResult(registerActivity, VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE);
                        } else {

                            if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {


                                if (playerModel.getIsFreeContent() == 1) {

                                    Log.v("MUVI", "video details");
                                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                                    getVideoDetailsInput.setAuthToken(authTokenStr);
                                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                                    Log.v("BKS", "contentid" + getVideoDetailsInput.getContent_uniq_id());
                                } else {
                                    //for check this flabour should voucher payment or normal payment like card and ppv payment

                                    new CheckVoucherOrPpvPaymentHandler(MovieDetailsActivity.this).handleVoucherPaymentOrPpvPayment();
                                    //callValidateUserAPI();
                                }
                            } else {
                                Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            }

                        }
                    } else {
                        Util.check_for_subscription = 1;
                        Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        registerActivity.putExtra("PlayerModel", playerModel);
//                        startActivity(registerActivity);
                        startActivityForResult(registerActivity, VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE);

                    }
                } else {
                    if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                        // MUVIlaxmi

                        Log.v("MUVI", "VV");
                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

                    } else {
                        Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        preorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataModel dbModel = new DataModel();
                dbModel.setIsFreeContent(isFreeContent);
                dbModel.setIsAPV(isAPV);
                dbModel.setIsPPV(isPPV);
                dbModel.setIsConverted(isConverted);
                dbModel.setMovieUniqueId(movieUniqueId);
                dbModel.setStreamUniqueId(movieStreamUniqueId);
                dbModel.setThirdPartyUrl(movieThirdPartyUrl);
                dbModel.setVideoTitle(movieNameStr);
                dbModel.setVideoStory(movieDetailsStr);
                dbModel.setVideoGenre(videoGenreTextView.getText().toString());
                dbModel.setVideoDuration(videoDurationTextView.getText().toString());
                dbModel.setVideoReleaseDate(videoReleaseDateTextView.getText().toString());
                dbModel.setCensorRating(censorRatingStr);
                dbModel.setCastCrew(castStr);
                dbModel.setEpisode_id("0");
                dbModel.setSeason_id("0");
                dbModel.setPurchase_type("show");
                dbModel.setPosterImageId(posterImageId);
                dbModel.setContentTypesId(contentTypesId);

                Util.dataModel = dbModel;

                if (isLogin == 1) {
                    if (preferenceManager.getLoginFeatureFromPref() == 1) {
                        String loggedInStr = preferenceManager.getUseridFromPref();

                        if (loggedInStr == null) {


                            final Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    Util.check_for_subscription = 1;
                                    registerActivity.putExtra("PlayerModel", playerModel);
                                    startActivity(registerActivity);

                                }
                            });
                            //showLoginDialog();
                        } else {
                            //String loggedinDateStr = pref.getString("date", null);
                            String loggedinDateStr = preferenceManager.getLoginDateFromPref();
                            if (loggedinDateStr != null) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                Date loggedInDate = null;
                                try {
                                    loggedInDate = formatter.parse(loggedinDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date today = new Date();
                                long differenceInDays = (int) Util.calculateDays(loggedInDate, today) + 1;
                                if (differenceInDays >= 7) {
                                   /* SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.commit();*/

                                    final Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            Util.check_for_subscription = 1;

                                            startActivity(registerActivity);

                                        }
                                    });
                                } else {

                                    if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {

                                        ValidateUserInput validateUserInput = new ValidateUserInput();
                                        validateUserInput.setAuthToken(authTokenStr);
                                        if (preferenceManager != null) {
                                            loggedInIdStr = preferenceManager.getUseridFromPref();
                                        }
                                        validateUserInput.setUserId(loggedInIdStr.trim());
                                        validateUserInput.setMuviUniqueId(Util.dataModel.getMovieUniqueId().trim());
                                        validateUserInput.setPurchaseType(Util.dataModel.getPurchase_type());
                                        validateUserInput.setSeasonId(Util.dataModel.getSeason_id());
                                        validateUserInput.setEpisodeStreamUniqueId(Util.dataModel.getEpisode_id());
                                        validateUserInput.setLanguageCode(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                                        asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                                        asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
                                    } else {
                                        Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                                        // Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                                    }


                                }
                            }
                        }
                    } else {

                        final Intent registerActivity = new LoginRegistrationOnContentClickHandler(MovieDetailsActivity.this).handleClickOnContent();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                Util.check_for_subscription = 1;

                                startActivity(registerActivity);

                            }
                        });


                    }
                } else {
                    if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                        // MUVIlaxmi

                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

                    } else {
                        Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                        //Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        watchTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel dbModel = new DataModel();
                dbModel.setIsFreeContent(isFreeContent);
                dbModel.setIsAPV(isAPV);
                dbModel.setIsPPV(isPPV);
                dbModel.setIsConverted(isConverted);
                dbModel.setMovieUniqueId(movieUniqueId);
                dbModel.setStreamUniqueId(movieStreamUniqueId);
                dbModel.setThirdPartyUrl(movieThirdPartyUrl);
                dbModel.setVideoTitle(movieNameStr);
                dbModel.setVideoStory(movieDetailsStr);
                dbModel.setVideoGenre(videoGenreTextView.getText().toString());
                dbModel.setVideoDuration(videoDurationTextView.getText().toString());
                dbModel.setVideoReleaseDate(videoReleaseDateTextView.getText().toString());
                dbModel.setCensorRating(censorRatingStr);
                dbModel.setCastCrew(castStr);
                dbModel.setVideoUrl(movieTrailerUrlStr);
                dbModel.setVideoResolution("BEST");
                dbModel.setContentTypesId(contentTypesId);

                Util.dataModel = dbModel;

                if (movieTrailerUrlStr == null) {

                    Util.showNoDataAlert(MovieDetailsActivity.this);
                    /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                    dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();*/
                    return;

                } else if ((movieTrailerUrlStr.matches("")) || (movieTrailerUrlStr.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA)))) {
                    Util.showNoDataAlert(MovieDetailsActivity.this);
                   /* AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                    dlgAlert.setTitle(languagePreference.getTextofLanguage( Util.SORRY, Util.DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();*/
                    return;

                } else {
                    /*chromecast-------------------------------------*/
                    if (isThirdPartyTrailer == false) {

                        if (mCastSession != null && mCastSession.isConnected()) {


                            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

                            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, movieReleaseDateStr);
                            movieMetadata.putString(MediaMetadata.KEY_TITLE, movieNameStr + " - Trailer");
                            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
                            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject();
                                jsonObj.put("description", movieNameStr);


                                //  This Code Is Added For Video Log By Bibhu..

                                jsonObj.put("authToken", authTokenStr.trim());
                                jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                                jsonObj.put("ip_address", ipAddressStr.trim());
                                jsonObj.put("movie_id", movieUniqueId);
                                jsonObj.put("episode_id", "");


                                jsonObj.put("played_length", "0");


                                jsonObj.put("watch_status", "start");
                                jsonObj.put("device_type", "2");
                                jsonObj.put("log_id", "0");

                                // restrict_stream_id is always set to be "0" , bcoz it's a triler.
                                jsonObj.put("restrict_stream_id", "0");

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
                                // This is added only to identify, the videolog is called for trailer
                                jsonObj.put("content_type", "2");

                                //====================End=====================//

                            } catch (JSONException e) {
                            }

                            mediaInfo = new MediaInfo.Builder(movieTrailerUrlStr.trim())
                                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                                    .setContentType("videos/mp4")
                                    .setMetadata(movieMetadata)
                                    .setStreamDuration(15 * 1000)
                                    .setCustomData(jsonObj)
                                    .build();
                            mSelectedMedia = mediaInfo;
                            // Util.showQueuePopup(ShowWithEpisodesActivity.this, view, mediaInfo);

                            togglePlayback();
                        } else {
                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, TrailerActivity.class);

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(playVideoIntent);

                                }
                            });
                        }
                    } else {
                        if (movieTrailerUrlStr.contains("://www.youtube") || movieTrailerUrlStr.contains("://www.youtu.be")) {
                            if (movieTrailerUrlStr.contains("live_stream?channel")) {
                                final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(playVideoIntent);

                                    }
                                });
                            } else {

                                final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, YouTubeAPIActivity.class);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(playVideoIntent);


                                    }
                                });

                            }
                        } else {
                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(playVideoIntent);

                                }
                            });
                        }

                    }
                }
            }
        });

        videoCastCrewTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Will Add Some Data to send
                final Intent registerActivity = new Intent(MovieDetailsActivity.this, CastAndCrewActivity.class);
                runOnUiThread(new Runnable() {
                    public void run() {


                        registerActivity.putExtra("cast_movie_id", movieUniqueId);
                        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(registerActivity);
                    }
                });

            }
        });

        /// Subtitle/////

        if (ContextCompat.checkSelfPermission(MovieDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MovieDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MovieDetailsActivity.this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                        111);
            } else {
                ActivityCompat.requestPermissions(MovieDetailsActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        111);

            }
        } else {
            //Call whatever you want
            if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {

                ContentDetailsInput contentDetailsInput = new ContentDetailsInput();
                contentDetailsInput.setAuthToken(authTokenStr);
                contentDetailsInput.setPermalink(permalinkStr);
                contentDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                contentDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                contentDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                asynLoadMovieDetails = new GetContentDetailsAsynTask(contentDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                asynLoadMovieDetails.executeOnExecutor(threadPoolExecutor);

            } else {
                Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                // Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                finish();
            }
        }


     /*chromecast-------------------------------------*/

        mAquery = new AQuery(this);

        // setupControlsCallbacks();
        setupCastListener();
        mCastContext = CastContext.getSharedInstance(this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        boolean shouldStartPlayback = false;
        int startPosition = 0;

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
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                updatePlaybackLocation(PlaybackLocation.REMOTE);
            } else {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));

                updatePlaybackLocation(PlaybackLocation.LOCAL);
            }
            mPlaybackState = PlaybackState.IDLE;
            updatePlayButton(mPlaybackState);
        }
/***************chromecast**********************/

    }

    /******* Subtitle*****/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111: {

                if (grantResults.length > 0) {
                    if ((grantResults.length > 0) && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
                        //Call whatever you want

                        if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {

                            ContentDetailsInput contentDetailsInput = new ContentDetailsInput();
                            contentDetailsInput.setAuthToken(authTokenStr);
                            contentDetailsInput.setPermalink(permalinkStr);
                            contentDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                            contentDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                            contentDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            asynLoadMovieDetails = new GetContentDetailsAsynTask(contentDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                            asynLoadMovieDetails.executeOnExecutor(threadPoolExecutor);
                        } else {
                            Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                            // Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                            finish();
                        }

                    } else {
                        finish();
                    }
                } else {
                    finish();
                }

                return;
            }
        }
    }


    private void payment_for_single_part() {

        {

            try {
                if (Util.currencyModel.getCurrencySymbol() == null) {
                    Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));

                    // Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                Util.showToast(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));

                //  Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
                return;

            }


            Util.selected_episode_id = "0";
            Util.selected_season_id = "0";

            if (Util.dataModel.getIsAPV() == 1) {
                priceForUnsubscribedStr = Util.apvModel.getAPVPriceForUnsubscribedStr();
                priceFosubscribedStr = Util.apvModel.getAPVPriceForsubscribedStr();

            } else {
                priceForUnsubscribedStr = Util.ppvModel.getPPVPriceForUnsubscribedStr();
                priceFosubscribedStr = Util.ppvModel.getPPVPriceForsubscribedStr();
            }


            final Intent showPaymentIntent = new Intent(MovieDetailsActivity.this, PPvPaymentInfoActivity.class);
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
            startActivityForResult(showPaymentIntent, PAYMENT_REQUESTCODE);

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
        if (asynLoadMovieDetails != null) {
            asynLoadMovieDetails.cancel(true);
        }
        if (asynGetReviewDetails != null) {
            asynGetReviewDetails.cancel(true);
        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();

        }
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }

    public void ShowLanguagePopup() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.language_pop_up, null);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.languagePopupTitle);
        titleTextView.setText(languagePreference.getTextofLanguage(APP_SELECT_LANGUAGE, DEFAULT_APP_SELECT_LANGUAGE));

        alertDialog.setView(convertView);
        alertDialog.setTitle("");

        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.language_recycler_view);
        Button apply = (Button) convertView.findViewById(R.id.apply_btn);
        apply.setText(languagePreference.getTextofLanguage(BUTTON_APPLY, DEFAULT_BUTTON_APPLY));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        languageCustomAdapter = new LanguageCustomAdapter(MovieDetailsActivity.this, Util.languageModel);
        recyclerView.setAdapter(languageCustomAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener1(MovieDetailsActivity.this, recyclerView, new ClickListener1() {
            @Override
            public void onClick(View view, int position) {
                Util.itemclicked = true;

                Util.languageModel.get(position).setSelected(true);


                if (prevPosition != position) {
                    Util.languageModel.get(prevPosition).setSelected(false);
                    prevPosition = position;

                }

                default_Language = Util.languageModel.get(position).getLanguageId();


                languagePreference.setLanguageSharedPrefernce(SELECTED_LANGUAGE_CODE, Util.languageModel.get(position).getLanguageId());
                languageCustomAdapter.notifyDataSetChanged();

                // Default_Language = Util.languageModel.get(position).getLanguageId();
             /*   AsynGetTransalatedLanguage asynGetTransalatedLanguage = new AsynGetTransalatedLanguage();
                asynGetTransalatedLanguage.executeOnExecutor(threadPoolExecutor);*/


                // new LanguageAsyncTask(new Get).executeOnExecutor(threadPoolExecutor);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                LanguageListInputModel languageListInputModel = new LanguageListInputModel();
                languageListInputModel.setAuthToken(authTokenStr);
                languageListInputModel.setLangCode(default_Language);

                if (!Previous_Selected_Language.equals(default_Language)) {
                    GetTranslateLanguageAsync getTranslateLanguageAsync = new GetTranslateLanguageAsync(languageListInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    getTranslateLanguageAsync.executeOnExecutor(threadPoolExecutor);

                /*AsynGetTransalatedLanguage asynGetTransalatedLanguage = new AsynGetTransalatedLanguage();
                asynGetTransalatedLanguage.executeOnExecutor(threadPoolExecutor);*/
                }

            }
        });


        alert = alertDialog.show();


        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                languagePreference.setLanguageSharedPrefernce(SELECTED_LANGUAGE_CODE, Previous_Selected_Language);
            }
        });

    }

    public static class RecyclerTouchListener1 implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener1 clickListener;

        public RecyclerTouchListener1(Context context, final RecyclerView recyclerView, final ClickListener1 clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener1 {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    /*****************
     * chromecast*-------------------------------------
     */

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

             /*   if (preferenceManager.getUseridFromPref()!=null){
                    Intent intent = new Intent(MovieDetailsActivity.this, ExpandedControlsActivity.class);
                    startActivity(intent);
                }*/
            }

            private void onApplicationDisconnected() {
/*
                    mPlayCircle.setVisibility(View.GONE);
*/

                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }
        };
    }

    private void updatePlayButton(PlaybackState state) {
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
                if (mLocation == PlaybackLocation.LOCAL) {
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

    private void updatePlaybackLocation(PlaybackLocation location) {
        mLocation = location;
        if (location == PlaybackLocation.LOCAL) {
            if (mPlaybackState == PlaybackState.PLAYING
                    || mPlaybackState == PlaybackState.BUFFERING) {
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
/*
                Intent intent = new Intent(MovieDetailsActivity.this, ExpandedControlsActivity.class);
                startActivity(intent);
                remoteMediaClient.removeListener(this);*/

            Log.v("BIBHU123","status upadted = "+remoteMediaClient.getMediaStatus());

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
                    Log.v("SUBHA", "Failed with status code:" +
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


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pDialog != null && !pDialog.isShowing())
                pDialog.show();
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
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
            }
            FakeSubTitlePath.remove(0);
            if (FakeSubTitlePath.size() > 0) {
                Download_SubTitle(FakeSubTitlePath.get(0).trim());
            } else {
                playerModel.setSubTitlePath(SubTitlePath);

                /***ad **/
                final Intent playVideoIntent;
                if (Util.dataModel.getAdNetworkId() == 3) {
                    playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                    if (Util.dataModel.getPlayPos() <= 0) {
                        playVideoIntent = new Intent(MovieDetailsActivity.this, AdPlayerActivity.class);
                    } else {
                        playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                    }
                } else {
                    playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                }
                /***ad **/
                //playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               /* playVideoIntent.putExtra("SubTitleName", SubTitleName);
                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                playVideoIntent.putExtra("ResolutionFormat",ResolutionFormat);
                playVideoIntent.putExtra("ResolutionUrl",ResolutionUrl);*/
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
            }
        }
    }


    public void showToast() {

        Context context = getApplicationContext();
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.custom_toast, null);
        TextView customToastMsg = (TextView) toastRoot.findViewById(R.id.toastMsg);
        customToastMsg.setText(sucessMsg);
        Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
//        toast.setText("Added to Favorites");
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("BKS", "elseclickedddddd");

        if (resultCode == RESULT_OK && requestCode == 1001) {
            if (data.getStringExtra("yes").equals("1002")) {
                watch_status_String = "halfplay";
                seek_status = "first_time";
                Played_Length = Util.dataModel.getPlayPos() * 1000;
                resume_time = "" + Util.dataModel.getPlayPos();
                PlayThroughChromeCast();

            } else {
                watch_status_String = "start";
                Played_Length = 0;
                PlayThroughChromeCast();
            }
        } else if (resultCode == RESULT_OK && requestCode == 1007) {

            if (data.getStringExtra("yes").equals("1002")) {

                Log.v("pratik", "resumed...");
                watch_status_String = "halfplay";
                seek_status = "first_time";
                Played_Length = Util.dataModel.getPlayPos() * 1000;
                togglePlayback();

            } else {
                watch_status_String = "start";
                Played_Length = 0;
                togglePlayback();
            }
        } else if (resultCode == RESULT_OK && requestCode == 30060) {
            if (NetworkStatus.getInstance().isConnected(this)) {
                ContentDetailsInput contentDetailsInput = new ContentDetailsInput();
                contentDetailsInput.setAuthToken(authTokenStr);
                contentDetailsInput.setPermalink(permalinkStr);
                contentDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                contentDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                contentDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

                asynLoadMovieDetails = new GetContentDetailsAsynTask(contentDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                asynLoadMovieDetails.executeOnExecutor(threadPoolExecutor);

            } else {
                Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                finish();
            }
        } else if (requestCode == VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE && resultCode == RESULT_OK) {
            new CheckVoucherOrPpvPaymentHandler(MovieDetailsActivity.this).handleVoucherPaymentOrPpvPayment();

        } else if (requestCode == PAYMENT_REQUESTCODE && resultCode == RESULT_OK) {
            getVideoInfo();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //aunregisterReceiver(DELETE_ACTION);
    }


    public void GetReviewDetails() {


        ViewContentRatingInputModel viewContentRatingInputModel = new ViewContentRatingInputModel();
        viewContentRatingInputModel.setAuthToken(authTokenStr);
        viewContentRatingInputModel.setUser_id(preferenceManager.getUseridFromPref());
//        viewContentRatingInputModel.setUser_id("142026");
        viewContentRatingInputModel.setContent_id(movieIdStr.trim());
        viewContentRatingInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        asynGetReviewDetails = new ViewContentRatingAsynTask(viewContentRatingInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
        asynGetReviewDetails.executeOnExecutor(threadPoolExecutor);


        Log.v("MUVI2", "user id" + preferenceManager.getUseridFromPref());
        Log.v("MUVI2", "Movie  id" + movieIdStr.trim());
        Log.v("MUVI2", "View Content Rating Call");

    }

    @Override
    public void onIPAddressPreExecuteStarted() {

    }

    @Override
    public void onIPAddressPostExecuteCompleted(String message, int statusCode, String ipAddressStr) {

        this.ipAddressStr = ipAddressStr;
        return;
    }


    @Override
    public void onLogoutPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }


    @Override
    public void onGetValidateUserPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }


    @Override
    public void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }
        String subscription_Str = validateUserOutput.getIsMemberSubscribed();
        preferenceManager.setIsSubscribedToPref(subscription_Str);
        String validUserStr = validateUserOutput.getValiduser_str();


        if (validateUserOutput == null) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(MovieDetailsActivity.this, MainActivity.class);
                            startActivity(in);
                            onBackPressed();
                        }
                    });
            dlgAlert.create().show();
        } else if (status <= 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(MovieDetailsActivity.this, MainActivity.class);
                            startActivity(in);
                            onBackPressed();
                        }
                    });
            dlgAlert.create().show();
        }

        if (status > 0) {

            if (status == 425) {

                if (isVoucher == 1) {
                    // Don't need for API call to get Voucher Plan
                    // Directly show the voucher popup
                    ShowVoucherPopUp();
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage(ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO));
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


            } else if (status == 426) {


                if (isVoucher == 1) {
                    // Don't need for API call to get Voucher Plan
                    // Directly show the voucher popup
                    ShowVoucherPopUp();
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage(ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO) + " " + languagePreference.getTextofLanguage(APP_ON, DEFAULT_APP_ON) + " " + getResources().getString(R.string.studio_site));
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


            } else if (status == 427) {
                Log.v("MUVI", "validate post execute 11" + status);

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
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

                new MonetizationHandler(MovieDetailsActivity.this).handle429OR430statusCod(validUserStr, message, subscription_Str);

            } else if (status == 428) {

                monetizationHandler.handle428Error(subscription_Str);

            } else if (Util.dataModel.getIsAPV() == 1 && status == 431) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                if (message != null && !message.equalsIgnoreCase("")) {
                    dlgAlert.setMessage(message);
                } else {
                    dlgAlert.setMessage(languagePreference.getTextofLanguage(ALREADY_PURCHASE_THIS_CONTENT, DEFAULT_ALREADY_PURCHASE_THIS_CONTENT));

                }
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                                overridePendingTransition(0, 0);
                            }
                        });
                dlgAlert.create().show();

            } else if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                // Go to ppv Payment
                payment_for_single_part();
            } else if (PlanId.equals("1") && subscription_Str.equals("0")) {
                Intent intent = new Intent(MovieDetailsActivity.this, SubscriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            } else if (Util.dataModel.getIsConverted() == 0) {
                Util.showNoDataAlert(MovieDetailsActivity.this);
               /* AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this);
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
                if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {

                    Log.v("MUVI", "VV vv vv vvvv");
                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    public void onGetLanguageListPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage) {
        if (pDialog.isShowing()) {
            pDialog.hide();


        }
        ArrayList<LanguageModel> languageModels = new ArrayList<LanguageModel>();

        for (int i = 0; i < languageListOutputArray.size(); i++) {
            String language_id = languageListOutputArray.get(i).getLanguageCode();
            String language_name = languageListOutputArray.get(i).getLanguageName();


            LanguageModel languageModel = new LanguageModel();
            languageModel.setLanguageId(language_id);
            languageModel.setLanguageName(language_name);

            if (default_Language.equalsIgnoreCase(language_id)) {
                languageModel.setIsSelected(true);
            } else {
                languageModel.setIsSelected(false);
            }
            languageModels.add(languageModel);
        }

        languageModel = languageModels;
        ShowLanguagePopup();
    }
    @Override
    public void onVideoDetailsPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int code, String status, String message) {
        // _video_details_output.setThirdparty_url("https://www.youtube.com/watch?v=iWcnxTZMXS4");
        // _video_details_output.setThirdparty_url("https://player.vimeo.com/video/192417650?color=00ff00&badge=0");

     /*check if status code 200 then set the video url before this it check it is thirdparty url or normal if third party
        then set thirdpartyurl true here and assign the url to videourl*/
     try {
            if (pDialog != null && pDialog.isShowing())
                pDialog.hide();
        } catch (IllegalArgumentException ex) {
        }
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


            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
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

        if (code == 200) {
            playerModel.setIsOffline(_video_details_output.getIs_offline());
            playerModel.setDownloadStatus(_video_details_output.getDownload_status());
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
                        Log.v("BISHAL", "videourl===" + playerModel.getVideoUrl());
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
            Util.dataModel.setVideoUrl(playerModel.getVideoUrl());
            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());
            Util.dataModel.setThirdPartyUrl(_video_details_output.getThirdparty_url());
            Util.dataModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            Util.dataModel.setChannel_id(_video_details_output.getChannel_id());
            Util.dataModel.setPreRoll(_video_details_output.getPreRoll());
            Util.dataModel.setPostRoll(_video_details_output.getPostRoll());
            Util.dataModel.setMidRoll(_video_details_output.getMidRoll());
            Util.dataModel.setPlayPos(Util.isDouble(_video_details_output.getPlayed_length()));
            Util.dataModel.setAdDetails(_video_details_output.getAdDetails());


            //player model set
            playerModel.setMidRoll(_video_details_output.getMidRoll());
            playerModel.setAdDetails(_video_details_output.getAdDetails());
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
            playerModel.setSubTitleLanguage(_video_details_output.getSubTitleLanguage());
            playerModel.setOfflineUrl(_video_details_output.getOfflineUrl());
            playerModel.setOfflineLanguage(_video_details_output.getOfflineLanguage());
            playerModel.setPlayPos(Util.isDouble(_video_details_output.getPlayed_length()));


            if (playerModel.getVideoUrl() == null ||
                    playerModel.getVideoUrl().matches("")) {
                Util.showNoDataAlert(MovieDetailsActivity.this);

                /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
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
               /* try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                       
                    }
                } catch (IllegalArgumentException ex) {
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                }*/


                // condition for checking if the response has third party url or not.
                if (_video_details_output.getThirdparty_url() == null ||
                        _video_details_output.getThirdparty_url().matches("")
                        ) {

                    if (mCastSession != null && mCastSession.isConnected()) {

                        ///Added for resume cast watch
                        if ((Util.dataModel.getPlayPos() * 1000) > 0) {
                            Util.dataModel.setPlayPos(Util.dataModel.getPlayPos());
                            Intent resumeIntent = new Intent(MovieDetailsActivity.this, ResumePopupActivity.class);
                            startActivityForResult(resumeIntent, 1001);

                        } else {
                            Played_Length = 0;
                            watch_status_String = "start";

                            PlayThroughChromeCast();
                        }


                    } else {


                        playerModel.setThirdPartyPlayer(false);


                        /***ad **/
                        Log.v("responseStr", "Util.dataModel.getAdNetworkId()" + Util.dataModel.getAdNetworkId());
                          /*  Util.dataModel.setAdNetworkId(3);
                            Util.dataModel.setChannelId("http://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=xml_vmap1&unviewed_position_start=1&cust_params=sample_ar%3Dpremidpostpod%26deployment%3Dgmf-js&cmsid=496&vid=short_onecue&correlator=%22");
                            Util.dataModel.setPreRoll(1);*/

                        final Intent playVideoIntent;
                        if (Util.dataModel.getAdNetworkId() == 3) {
                            Log.v("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                            playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                        } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                            if (Util.dataModel.getPlayPos() <= 0) {
                                playVideoIntent = new Intent(MovieDetailsActivity.this, AdPlayerActivity.class);
                            } else {
                                playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                            }

                        } else {
                            playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);

                        }

                        /***ad **/

                        if (FakeSubTitlePath.size() > 0) {
                            // This Portion Will Be changed Later.

                            File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++) {
                                    new File(dir, children[i]).delete();
                                }
                            }

                                   /* pDialog = new ProgressBarHandler(MovieDetailsActivity.this);
                                    pDialog.show();*/
                            Download_SubTitle(FakeSubTitlePath.get(0).trim());
                        } else {
                            playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                            playVideoIntent.putExtra("PlayerModel", playerModel);
                            startActivity(playVideoIntent);
                        }


                    }
                } else {
                    final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ExoPlayerActivity.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);

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
            //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            Util.showNoDataAlert(MovieDetailsActivity.this);
           /* AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
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
                jsonObj.put("resume_time", resume_time);
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

            if(featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)){
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
                jsonObj.put("resume_time", resume_time);


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
            if(featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)) {

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
            }

            if (Util.dataModel != null) {
                mediaInfo = new MediaInfo.Builder(Util.dataModel.getVideoUrl().trim())
                        .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                        .setContentType(mediaContentType)
                        .setMetadata(movieMetadata)
                        .setStreamDuration(15 * 1000)
                        .setCustomData(jsonObj)
                        .setMediaTracks(tracks)
                        .build();
            }
            mSelectedMedia = mediaInfo;


            togglePlayback();
        }


          /* if (preferenceManager.getUseridFromPref()!=null){
                    Intent intent = new Intent(MovieDetailsActivity.this, ExpandedControlsActivity.class);
                    startActivity(intent);
                }*/
    }

    @Override
    public void onAddToFavPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onAddToFavPostExecuteCompleted(AddToFavOutputModel addToFavOutputModel, int status, String sucessMsg) {
        favorite_view.setImageResource(R.drawable.favorite_red);
        isFavorite = 1;
        MovieDetailsActivity.this.sucessMsg = sucessMsg;
        showToast();
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    @Override
    public void onGetContentDetailsPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onGetContentDetailsPostExecuteCompleted(ContentDetailsOutput contentDetailsOutput, int status, String message) {

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }


        if (status == 200) {
            castStr = contentDetailsOutput.getCastStr();
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.GONE);
            isPPV = contentDetailsOutput.getIsPpv();
            isAPV = contentDetailsOutput.getIsApv();
            movieUniqueId = contentDetailsOutput.getMuviUniqId();
            isEpisode = contentDetailsOutput.getIsEpisode();
            movieStreamUniqueId = contentDetailsOutput.getMovieStreamUniqId();
            movieNameStr = contentDetailsOutput.getName();
            movieTrailerUrlStr = contentDetailsOutput.getTrailerUrl();
            videoduration = contentDetailsOutput.getVideoDuration();
            censorRatingStr = contentDetailsOutput.getCensorRating();
            movieDetailsStr = contentDetailsOutput.getStory();
            movieTypeStr = contentDetailsOutput.getGenre();
            isFavorite = contentDetailsOutput.getIs_favorite();
            reviews = contentDetailsOutput.getReview();
            rating = contentDetailsOutput.getRating();
            movieIdStr = contentDetailsOutput.getId();
            posterImageId = contentDetailsOutput.getPoster();
            contentTypesId = contentDetailsOutput.getContentTypesId();
            Util.currencyModel = contentDetailsOutput.getCurrencyDetails();
            Util.apvModel = contentDetailsOutput.getApvDetails();
            Util.ppvModel = contentDetailsOutput.getPpvDetails();

            Log.v("MUVI", "rattting === " + rating);
            Log.v("MUVI", "reviewwww === " + reviews);


            //  castValue = contentDetailsOutput.getCastStr();

//        Log.v("MUVI2","cast value" +castValue);

            Log.v("MUVI", "movieUniqueId====== " + movieUniqueId);

            /***favorite *****/

            if ((featureHandler.getFeatureStatus(FeatureHandler.HAS_FAVOURITE, FeatureHandler.DEFAULT_HAS_FAVOURITE))) {
                //  favorite_view.setVisibility(View.VISIBLE);
                handleRatingbar.handleVisibleUnvisibleFavicon(favorite_view);
            }

            /***play button visibility condition *****/

            if (contentDetailsOutput.getIsApv() == 1) {
                playButton.setVisibility(View.INVISIBLE);
                preorderButton.setText(languagePreference.getTextofLanguage(ADVANCE_PURCHASE, DEFAULT_ADVANCE_PURCHASE));
                preorderButton.setVisibility(View.VISIBLE);
            } else if (contentDetailsOutput.getContentTypesId() == 4) {
                playButton.setVisibility(View.VISIBLE);
                preorderButton.setVisibility(View.GONE);

            } else if ((contentDetailsOutput.getIsFreeContent().equals("1") || contentDetailsOutput.getIsPpv() == 1)
                    && contentDetailsOutput.getIsConverted() == 1) {

                playButton.setVisibility(View.VISIBLE);
                preorderButton.setVisibility(View.GONE);

            } else if (contentDetailsOutput.getIsApv() == 0 && contentDetailsOutput.getIsPpv() == 0 &&
                    contentDetailsOutput.getIsConverted() == 1) {
                playButton.setVisibility(View.VISIBLE);
                preorderButton.setVisibility(View.GONE);

            }


            videoTitle.setVisibility(View.VISIBLE);
            Typeface castDescriptionTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.regular_fonts));
            videoTitle.setTypeface(castDescriptionTypeface);
            videoTitle.setText(contentDetailsOutput.getName());

            if (contentDetailsOutput.getTrailerUrl().matches("") || contentDetailsOutput.getTrailerUrl().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                watchTrailerButton.setVisibility(View.INVISIBLE);
            } else {
                watchTrailerButton.setText(languagePreference.getTextofLanguage(VIEW_TRAILER, DEFAULT_VIEW_TRAILER));
                watchTrailerButton.setVisibility(View.VISIBLE);
            }

            if (contentDetailsOutput.getGenre() != null && contentDetailsOutput.getGenre().matches("") || contentDetailsOutput.getGenre().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                videoGenreTextView.setVisibility(View.GONE);

            } else {
                videoGenreTextView.setVisibility(View.VISIBLE);
                Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                videoGenreTextView.setTypeface(videoGenreTextViewTypeface);
                videoGenreTextView.setText(contentDetailsOutput.getGenre());

            }
            if (contentDetailsOutput.getVideoDuration().matches("") || contentDetailsOutput.getVideoDuration().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                videoDurationTextView.setVisibility(View.GONE);

            } else {

                videoDurationTextView.setVisibility(View.VISIBLE);
                Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                videoDurationTextView.setTypeface(videoGenreTextViewTypeface);
                videoDurationTextView.setText(contentDetailsOutput.getVideoDuration());
            }


            if (contentDetailsOutput.getReleaseDate().matches("") || contentDetailsOutput.getReleaseDate().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                videoReleaseDateTextView.setVisibility(View.GONE);
            } else {
                videoReleaseDateTextView.setVisibility(View.VISIBLE);
                Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                videoReleaseDateTextView.setTypeface(videoGenreTextViewTypeface);
                movieReleaseDateStr = Util.formateDateFromstring("yyyy-mm-dd", "yyyy", contentDetailsOutput.getReleaseDate());
                videoReleaseDateTextView.setText(movieReleaseDateStr);

            }

            if (contentDetailsOutput.getStory().matches("") || contentDetailsOutput.getStory().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                videoStoryTextView.setVisibility(View.GONE);

            } else {
                videoStoryTextView.setVisibility(View.VISIBLE);
                Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                videoStoryTextView.setTypeface(videoGenreTextViewTypeface);
                videoStoryTextView.setText(Util.getTextViewTextFromApi(contentDetailsOutput.getStory()));
                ResizableCustomView.doResizeTextView(MovieDetailsActivity.this, videoStoryTextView, MAX_LINES, languagePreference.getTextofLanguage(VIEW_MORE, DEFAULT_VIEW_MORE), true);
            }

            if (contentDetailsOutput.getCensorRating().matches("") || contentDetailsOutput.getCensorRating().matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                videoCensorRatingTextView.setVisibility(View.GONE);
                videoCensorRatingTextView1.setVisibility(View.GONE);

            } else {

                if (contentDetailsOutput.getCensorRating().contains("-")) {
                    String Data[] = contentDetailsOutput.getCensorRating().split("-");
                    videoCensorRatingTextView.setVisibility(View.VISIBLE);
                    videoCensorRatingTextView1.setVisibility(View.VISIBLE);
                    Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                    videoCensorRatingTextView.setTypeface(videoGenreTextViewTypeface);
                    videoCensorRatingTextView1.setTypeface(videoGenreTextViewTypeface);

                    videoCensorRatingTextView.setText(Data[0]);
                    videoCensorRatingTextView1.setText(Data[1]);

                } else {
                    videoCensorRatingTextView.setVisibility(View.VISIBLE);
                    videoCensorRatingTextView1.setVisibility(View.GONE);
                    Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                    videoCensorRatingTextView.setTypeface(videoGenreTextViewTypeface);
                    videoCensorRatingTextView.setText(contentDetailsOutput.getCensorRating());
                }


            }

            if (castStr) {
                videoCastCrewTitleTextView.setText(languagePreference.getTextofLanguage(CAST_CREW_BUTTON_TITLE, DEFAULT_CAST_CREW_BUTTON_TITLE));
                FontUtls.loadFont(MovieDetailsActivity.this, getResources().getString(R.string.regular_fonts), videoCastCrewTitleTextView);
                videoCastCrewTitleTextView.setVisibility(View.VISIBLE);
            }


            String bannerUrl = contentDetailsOutput.getBanner().trim().equals("")?contentDetailsOutput.getPoster().trim():contentDetailsOutput.getBanner().trim();
            Picasso.with(MovieDetailsActivity.this)
                    .load(bannerUrl)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .into(moviePoster);

            GetReviewDetails();


        }else if (status==414){
            noDataTextView.setText(languagePreference.getTextofLanguage(CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY,DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY));
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);

            story_layout.setVisibility(View.GONE);
            bannerImageRelativeLayout.setVisibility(View.GONE);
            iconImageRelativeLayout.setVisibility(View.GONE);
            return;
        }

        else {
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.bringToFront();
            noDataLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onViewContentRatingPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onViewContentRatingPostExecuteCompleted(ViewContentRatingOutputModel viewContentRatingOutputModel,
                                                        int status, String message) {

        String loggedInStr = preferenceManager.getUseridFromPref();


        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }

        if (status == 200) {

            Log.v("MUVI", "review data" + reviews);
            Log.v("MUVI", "rating data" + rating);

            if (reviews.equalsIgnoreCase("")) {
                viewRatingTextView.setVisibility(View.GONE);

            } else {
                //here handler is calling for visible and gone for sony app it should be gone thats why we create handler
               /* ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(Float.parseFloat(rating));*/
                handleRatingbar.handleVisibleUnvisibleRating(ratingBar);
                ratingBar.setRating(Float.parseFloat(rating));
            }
            if (rating.equalsIgnoreCase("")) {
                ratingBar.setVisibility(View.GONE);
            } else {
                Log.v("MUVI", "rating ==== " + rating);
                //here handler is calling for visible and gone for sony app it should be gone thats why we create handler

                handleRatingbar.handleVisibleUnvisibleRatingTextView(viewRatingTextView);
                //viewRatingTextView.setVisibility(View.VISIBLE);


                if (preferenceManager.getLoginFeatureFromPref() == 1) {

                    if (loggedInStr == null) {
                        viewRatingTextView.setText(languagePreference.getTextofLanguage(ADD_A_REVIEW, DEFAULT_ADD_A_REVIEW));
                        Log.v("MUVI", "rating 0 ==== " + viewContentRatingOutputModel.getShowrating());
                    } else {
                        if (viewContentRatingOutputModel.getShowrating() == 1) {
                            viewRatingTextView.setText(languagePreference.getTextofLanguage(ADD_A_REVIEW, DEFAULT_ADD_A_REVIEW));
                            Log.v("MUVI", "rating 1 ==== " + viewContentRatingOutputModel.getShowrating());
                        } else {
                            Log.v("MUVI", "rating 2 ==== " + viewContentRatingOutputModel.getShowrating());
                            viewRatingTextView.setText("reviews (" + reviews + ")");
                            viewRatingTextView.setText(languagePreference.getTextofLanguage(REVIEWS, DEFAULT_REVIEWS) + " (" + reviews + ") ");

                        }

                    }
                } else {
                    viewRatingTextView.setText(languagePreference.getTextofLanguage(ADD_A_REVIEW, DEFAULT_ADD_A_REVIEW));
                }
            }

            /***favorite *****/

        } else {

        }
        try {
            if (loggedInStr != null && isFavorite == 0 && Util.favorite_clicked == true) {

                Util.favorite_clicked = false;
                AddToFavInputModel addToFavInputModel = new AddToFavInputModel();
                addToFavInputModel.setAuthToken(authTokenStr);
                addToFavInputModel.setMovie_uniq_id(movieUniqueId);
                addToFavInputModel.setLoggedInStr(preferenceManager.getUseridFromPref());
                addToFavInputModel.setIsEpisodeStr(isEpisode);

                asynFavoriteAdd = new AddToFavAsync(addToFavInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
                asynFavoriteAdd.executeOnExecutor(threadPoolExecutor);
            } else if (loggedInStr != null && isFavorite == 1) {

                favorite_view.setImageResource(R.drawable.favorite_red);
            }
            /***favorite *****/
        }catch (Exception e) {

        }
    }

    @Override
    public void onDeleteFavPreExecuteStarted() {
      /*  pDialog = new ProgressBarHandler(MovieDetailsActivity.this);
        pDialog.show();*/
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();

    }

    @Override
    public void onDeleteFavPostExecuteCompleted(DeleteFavOutputModel deleteFavOutputModel, int status, String sucessMsg) {
        favorite_view.setImageResource(R.drawable.favorite_unselected);
        MovieDetailsActivity.this.sucessMsg = sucessMsg;
        showToast();
        isFavorite = 0;
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    @Override
    public void onGetTranslateLanguagePreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();


        }

        if (jsonResponse == null) {
        } else {
            if (status > 0 && status == 200) {

                try {

                    Util.parseLanguage(languagePreference, jsonResponse, default_Language);
                    //Call For Language PopUp Dialog

                    languageCustomAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(MovieDetailsActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Call For Other Methods.


            } else {
            }
        }
    }

    public void handleActionForValidateUserForVoucherPayment(String validUserStr, String message, String subscription_Str) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                    Log.v("MUVI", "VV VV VV");

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    if (isVoucher == 1) {
                        // Don't need for API call to get Voucher Plan
                        // Directly show the voucher popup
                        ShowVoucherPopUp();
                    } else {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                        dlgAlert.setMessage(languagePreference.getTextofLanguage(ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO) + " " + languagePreference.getTextofLanguage(APP_ON, DEFAULT_APP_ON) + " " + getResources().getString(R.string.studio_site));

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

            }
        }
    }

    public void handleActionForValidateUserPayment(String validUserStr, String message, String subscription_Str) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                    Log.v("MUVI", "VV VV VV");

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                        // Go to ppv Payment

                        Log.v("MUVI", "unpaid msg");
                        payment_for_single_part();
                    } else if (PlanId.equals("1") && subscription_Str.equals("0")) {
                        Intent intent = new Intent(MovieDetailsActivity.this, SubscriptionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else {
                        // Go to ppv Payment
                        Log.v("MUVI", "unpaid msg");
                        payment_for_single_part();
                    }
                }

            }
        }
    }

    public void handleActionForValidateSonyUserPayment(String validUserStr, String message, String subscription_Str, String alertShowMsg) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(MovieDetailsActivity.this)) {
                    Log.v("MUVI", "VV VV VV");

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    Util.showActivateSubscriptionWatchVideoAleart(this, alertShowMsg);
                }

            }
        }
    }

    public void ShowVoucherPopUp() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) MovieDetailsActivity.this.getSystemService(MovieDetailsActivity.this.LAYOUT_INFLATER_SERVICE);

        View convertView = (View) inflater.inflate(R.layout.voucher_popup, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("");

        content_label = (TextView) convertView.findViewById(R.id.content_label);
        content_name = (TextView) convertView.findViewById(R.id.content_name);
        voucher_success = (TextView) convertView.findViewById(R.id.voucher_success);
        voucher_code = (EditText) convertView.findViewById(R.id.voucher_code);
        apply = (Button) convertView.findViewById(R.id.apply);
        watch_now = (Button) convertView.findViewById(R.id.watch_now);

        apply.setEnabled(true);
        apply.setBackgroundResource(R.drawable.button_radious);
        apply.setTextColor(getResources().getColor(R.color.listTitleContentColor));


        // Font implemented Here//

        Typeface typeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        content_label.setTypeface(typeface);
        content_name.setTypeface(typeface);
        voucher_success.setTypeface(typeface);
        apply.setTypeface(typeface);
        watch_now.setTypeface(typeface);
        voucher_code.setTypeface(typeface);

        //==============end===============//

        // Language Implemented Here //

        content_label.setText(" " + languagePreference.getTextofLanguage(PURCHASE, DEFAULT_PURCHASE) + " :");
        voucher_success.setText(" " + languagePreference.getTextofLanguage(VOUCHER_SUCCESS, DEFAULT_VOUCHER_SUCCESS) + " ");
        apply.setText(" " + languagePreference.getTextofLanguage(BUTTON_APPLY, DEFAULT_BUTTON_APPLY) + " ");
        watch_now.setText(" " + languagePreference.getTextofLanguage(WATCH_NOW, DEFAULT_WATCH_NOW) + " ");
        voucher_code.setHint(" " + languagePreference.getTextofLanguage(ENTER_VOUCHER_CODE, DEFAULT_ENTER_VOUCHER_CODE) + " ");


        //==============End===============//


        voucher_code.setText("");
        watch_now.setBackgroundResource(R.drawable.voucher_inactive_button);
        watch_now.setTextColor(Color.parseColor("#7f7f7f"));

        voucher_success.setVisibility(View.INVISIBLE);

        content_name.setText(" " + movieNameStr);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VoucherCode = voucher_code.getText().toString().trim();
                if (!VoucherCode.equals("")) {
                    voucher_alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    ValidateVoucher_And_VoucherSubscription();
                } else {
                    Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(VOUCHER_BLANK_MESSAGE, DEFAULT_VOUCHER_BLANK_MESSAGE), Toast.LENGTH_SHORT).show();

                }
            }
        });

        watch_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (watch_status) {
                    voucher_alert.dismiss();

                    // Calling Voucher Subscription Api

                    VoucherSubscriptionInputModel voucherSubscriptionInputModel = new VoucherSubscriptionInputModel();
                    voucherSubscriptionInputModel.setAuthToken(authTokenStr);
                    voucherSubscriptionInputModel.setUser_id(preferenceManager.getUseridFromPref());
                    voucherSubscriptionInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
                    voucherSubscriptionInputModel.setStream_id(Util.dataModel.getStreamUniqueId().trim());
                    voucherSubscriptionInputModel.setVoucher_code(VoucherCode);
                    voucherSubscriptionInputModel.setIs_preorder("" + Util.dataModel.getIsAPV());
                    voucherSubscriptionInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    voucherSubscriptionInputModel.setPurchase_type("show");
                    VoucherSubscriptionAsyntask asynVoucherSubscription = new VoucherSubscriptionAsyntask(voucherSubscriptionInputModel, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    asynVoucherSubscription.executeOnExecutor(threadPoolExecutor);

                }
            }
        });
        voucher_alert = alertDialog.show();
        voucher_alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void ValidateVoucher_And_VoucherSubscription() {
        ValidateVoucherInputModel validateVoucherInputModel = new ValidateVoucherInputModel();
        validateVoucherInputModel.setAuthToken(authTokenStr);
        validateVoucherInputModel.setUser_id(preferenceManager.getUseridFromPref());
        validateVoucherInputModel.setVoucher_code(VoucherCode);
        validateVoucherInputModel.setPurchase_type("show");
        validateVoucherInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        validateVoucherInputModel.setStream_id(Util.dataModel.getStreamUniqueId().trim());
        validateVoucherInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        ValidateVoucherAsynTask asynValidateVoucher = new ValidateVoucherAsynTask(validateVoucherInputModel, this, this);
        asynValidateVoucher.executeOnExecutor(threadPoolExecutor);
    }

    /**
     * callValidateUserAPI
     */

    public void callValidateUserAPI() {
        Log.v("MUVI", "validate user details");

        ValidateUserInput validateUserInput = new ValidateUserInput();
        validateUserInput.setAuthToken(authTokenStr);
        if (preferenceManager != null) {
            loggedInIdStr = preferenceManager.getUseridFromPref();
        }
        validateUserInput.setUserId(loggedInIdStr.trim());
        validateUserInput.setMuviUniqueId(Util.dataModel.getMovieUniqueId().trim());
        validateUserInput.setPurchaseType(Util.dataModel.getPurchase_type());
        validateUserInput.setSeasonId(Util.dataModel.getSeason_id());
        validateUserInput.setEpisodeStreamUniqueId(Util.dataModel.getEpisode_id());
        validateUserInput.setLanguageCode(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
        asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
    }

    public void getMonitizationDetailsApi() {

        MonitizationDetailsInput monitizationDetailsInput = new MonitizationDetailsInput();
        monitizationDetailsInput.setAuthToken(authTokenStr);
        monitizationDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
        monitizationDetailsInput.setMovie_id(movieUniqueId);
        monitizationDetailsInput.setStream_id(movieStreamUniqueId);
        monitizationDetailsInput.setPurchase_type("show");
        getMonitizationDetailsAsync = new GetMonitizationDetailsAsync(monitizationDetailsInput, this, this);
        getMonitizationDetailsAsync.executeOnExecutor(threadPoolExecutor);
    }

    public void handleFor428Status(String subscription_Str) {

        if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
            // Go to ppv Payment

            Log.v("MUVI", "unpaid msg");
            payment_for_single_part();
        } else if (PlanId.equals("1") && subscription_Str.equals("0")) {
            Intent intent = new Intent(MovieDetailsActivity.this, SubscriptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else {
            // Go to ppv Payment
            Log.v("MUVI", "unpaid msg");
            payment_for_single_part();
        }
    }

    public void handleFor428StatusVoucher(String subscription_Str) {


        if (isVoucher == 1) {
            // API call for get Voucher Plan
            ShowVoucherPopUp();
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(CROSSED_MAXIMUM_LIMIT, DEFAULT_CROSSED_MAXIMUM_LIMIT) + " " + languagePreference.getTextofLanguage(ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO) + " " + languagePreference.getTextofLanguage(APP_ON, DEFAULT_APP_ON) + " " + getResources().getString(R.string.studio_site));
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


    public void getVideoInfo() {
        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
        getVideoDetailsInput.setAuthToken(authTokenStr);
        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, MovieDetailsActivity.this, MovieDetailsActivity.this);
        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
    }


}
