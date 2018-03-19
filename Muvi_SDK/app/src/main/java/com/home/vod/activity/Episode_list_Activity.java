package com.home.vod.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiController.GetEpisodeDeatailsAsynTask;
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetLanguageListAsynTask;
import com.home.apisdk.apiController.GetMonitizationDetailsAsync;
import com.home.apisdk.apiController.GetTranslateLanguageAsync;
import com.home.apisdk.apiController.GetValidateUserAsynTask;
import com.home.apisdk.apiController.GetVoucherPlanAsynTask;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiController.ValidateVoucherAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiController.VoucherSubscriptionAsyntask;
import com.home.apisdk.apiModel.APVModel;
import com.home.apisdk.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.Episode_Details_input;
import com.home.apisdk.apiModel.Episode_Details_output;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.GetVoucherPlanInputModel;
import com.home.apisdk.apiModel.GetVoucherPlanOutputModel;
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
import com.home.vod.LoginRegistrationOnContentClickHandler;
import com.home.vod.MonetizationHandler;
import com.home.vod.R;
import com.home.vod.adapter.EpisodesListViewMoreAdapter;
import com.home.vod.adapter.LanguageCustomAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.DataModel;
import com.home.vod.model.EpisodesListModel;
import com.home.vod.model.LanguageModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.MyLibraryPlayer;
import player.activity.Player;
import player.activity.ResumePopupActivity;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.ALREADY_PURCHASE_THIS_CONTENT;
import static com.home.vod.preferences.LanguagePreference.APP_ON;
import static com.home.vod.preferences.LanguagePreference.APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.COMPLETE_SEASON;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ALREADY_PURCHASE_THIS_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_ON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_COMPLETE_SEASON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_EPISODE_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_IS_STREAMING_RESTRICTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LANGUAGE_POPUP_LOGIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_DOWNLOAD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NEXT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PROFILE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SEASON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECT_PURCHASE_TYPE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_YES;
import static com.home.vod.preferences.LanguagePreference.ENTER_VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.EPISODE_TITLE;

import static com.home.vod.preferences.LanguagePreference.LANGUAGE_POPUP_LOGIN;
import static com.home.vod.preferences.LanguagePreference.LOGOUT;
import static com.home.vod.preferences.LanguagePreference.LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.MY_DOWNLOAD;
import static com.home.vod.preferences.LanguagePreference.MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.NEXT;
import static com.home.vod.preferences.LanguagePreference.NO;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.PLAN_ID;
import static com.home.vod.preferences.LanguagePreference.PROFILE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.SEASON;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SELECT_PURCHASE_TYPE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.VOUCHER_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.YES;
import static com.home.vod.util.Constant.CAST_INTENT_KEY;
import static com.home.vod.util.Constant.CENSOR_RATING_INTENT_KEY;
import static com.home.vod.util.Constant.GENRE_INTENT_KEY;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.SEASON_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;
import static com.home.vod.util.Util.languageModel;


/**
 * Created by Muvi on 2/6/2017.
 */
public class Episode_list_Activity extends AppCompatActivity implements VideoDetailsAsynctask.VideoDetailsListener, GetValidateUserAsynTask.GetValidateUserListener,
        GetEpisodeDeatailsAsynTask.GetEpisodeDetailsListener, GetLanguageListAsynTask.GetLanguageListListener, LogoutAsynctask.LogoutListener,
        GetTranslateLanguageAsync.GetTranslateLanguageInfoListener, GetIpAddressAsynTask.IpAddressListener,
        GetMonitizationDetailsAsync.GetMonitizationDetailsListner
        , VoucherSubscriptionAsyntask.VoucherSubscriptionListener, ValidateVoucherAsynTask.ValidateVoucherListener,
        GetVoucherPlanAsynTask.GetVoucherPlanListener {

    String filename = "";
    static File mediaStorageDir;
    ArrayList<Episode_Details_output.Episode> episodeArray;
    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();
    public static final int VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE = 8888;
    public static final int PAYMENT_REQUESTCODE = 8889;
    // ProgressBarHandler loadEpisodedetailspDialog;
    SharedPreferences pref;
    int previousTotal = 0;
    VideoDetailsAsynctask asynLoadVideoUrls;
    GetEpisodeDeatailsAsynTask asynEpisodeDetails;
    GetValidateUserAsynTask asynValidateUserDetails;
    EpisodesListModel itemToPlay;
    String permalinkStr;
    ArrayList<EpisodesListModel> itemData = new ArrayList<EpisodesListModel>();
    LinearLayoutManager mLayoutManager;
    boolean firstTime = false;
    private EpisodesListViewMoreAdapter customListAdapter;
    public static boolean isLoading = false;
    private String movieVideoUrlStr = "";
    private String videoResolution = "";

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    RecyclerView episodelist;
    private RelativeLayout noInternetConnectionLayout;
    RelativeLayout noDataLayout;
    TextView noDataTextView;
    TextView noInternetTextView;
    SharedPreferences isLoginPref;
    Player playerModel;
    int isVoucher = 0;
    String PurchageType = "";
    String VoucherCode = "";
    TextView content_label, content_name, voucher_success;
    EditText voucher_code;
    Button apply, watch_now;
    boolean watch_status = false;
    String ContentName = "";
    AlertDialog voucher_alert;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    //for resume play
    String seek_status = "";
    int Played_Length = 0;
    String watch_status_String = "start";
    String resume_time = "0";
    int offset = 1;
    int limit = 10;
    int listSize = 0;
    int itemsInServer = 0;
    int isLogin = 0;

    int isPPV = 0;
    int isAPV = 0;
    int isConverted = 0;
    int isFreeContent = 0;

    ProgressBarHandler pDialog;
    RelativeLayout footerView;
    private PreferenceManager preferenceManager;
    String PlanId = "";
    String videoUrlStr = "";
    String priceForUnsubscribedStr, priceFosubscribedStr;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    MonetizationHandler monetizationHandler;
    CheckVoucherOrPpvPaymentHandler checkVoucherOrPpvPaymentHandler;
    GetMonitizationDetailsAsync getMonitizationDetailsAsync;

    PPVModel ppvmodel;
    APVModel advmodel;
    CurrencyModel currencymodel;
    String movieNameStr = "";

    String email, id;
    LanguageCustomAdapter languageCustomAdapter;
    AlertDialog alert;
    String default_Language = "";
    String Previous_Selected_Language = "";
    int prevPosition = 0;
    ProgressBarHandler progressBarHandler;
    LanguagePreference languagePreference;
    FeatureHandler featureHandler;
    private String ipAddressStr = "";
    private EpisodeListOptionMenuHandler episodeListOptionMenuHandler;
// This method is to reset all item data

    public void resetData() {
        if (itemData != null && itemData.size() > 0) {
            itemData.clear();
        }
        firstTime = true;

        offset = 1;
        isLoading = false;
        listSize = 0;
        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            limit = 20;
        } else {
            limit = 15;
        }
        itemsInServer = 0;
    }


    private static final String TAG = "ShowWithEpisodesActivity";


    @Override
    public void onVideoDetailsPreExecuteStarted() {

        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();

    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int statusCode, String stus, String message) {
        // _video_details_output.setThirdparty_url("https://www.youtube.com/watch?v=fqU2FzATTPY&spfreload=10");
        // _video_details_output.setThirdparty_url("https://player.vimeo.com/video/192417650?color=00ff00&badge=0");

     /*check if status code 200 then set the video url before this it check it is thirdparty url or normal if third party

        then set thirdpartyurl true here and assign the url to videourl*/

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
            }
        } catch (IllegalArgumentException ex) {
            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            // movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
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



            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
            playerModel.setSubTitleLanguage(_video_details_output.getSubTitleLanguage());
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

                Util.showNoDataAlert(Episode_list_Activity.this);
            } else {



                // condition for checking if the response has third party url or not.
                if (_video_details_output.getThirdparty_url() == null ||
                        _video_details_output.getThirdparty_url().matches("")
                        ) {

                    if (mCastSession != null && mCastSession.isConnected()) {
                        ///Added for resume cast watch
                        if ((Util.dataModel.getPlayPos() * 1000) > 0) {
                            Util.dataModel.setPlayPos(Util.dataModel.getPlayPos());
                            Intent resumeIntent = new Intent(Episode_list_Activity.this, ResumePopupActivity.class);
                            startActivityForResult(resumeIntent, 1001);

                        } else {
                            Played_Length = 0;
                            watch_status_String = "start";

                            PlayThroughChromeCast();
                        }
                    } else {

                        playerModel.setThirdPartyPlayer(false);
                        final Intent playVideoIntent;
                        //edit by bishal for player

                        if (Util.goToLibraryplayer) {
                            playVideoIntent = new Intent(Episode_list_Activity.this, MyLibraryPlayer.class);

                        } else {
                            if (Util.dataModel.getAdNetworkId() == 3) {
                                LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                                playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                            } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                                if (Util.dataModel.getPlayPos() <= 0) {
                                    playVideoIntent = new Intent(Episode_list_Activity.this, AdPlayerActivity.class);
                                } else {
                                    playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                                }

                            } else {
                                playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                            }
                        }
                        //final Intent playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);
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

                                    Download_SubTitle(FakeSubTitlePath.get(0).trim());
                                } else {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel", playerModel);
                                    startActivity(playVideoIntent);
                                }

                            }
                        });
                    }
                } else {
                    final Intent playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);

                }
            }

        } else {

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            Util.showNoDataAlert(Episode_list_Activity.this);
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
                } else {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "0");
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
                } else {
                    jsonObj.put("restrict_stream_id", "0");
                    jsonObj.put("is_streaming_restriction", "0");
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

       /* if (preferenceManager.getUseridFromPref()!=null){
            Intent intent = new Intent(Episode_list_Activity.this, ExpandedControlsActivity.class);
            startActivity(intent);
        }*/

    }

    @Override
    public void onGetValidateUserPreExecuteStarted() {
        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();
    }

    @Override
    public void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }
        String Subscription_Str = preferenceManager.getIsSubscribedFromPref();
        String validUserStr = validateUserOutput.getValiduser_str();
        if (validateUserOutput == null) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(Episode_list_Activity.this, MainActivity.class);
                            startActivity(in);

                        }
                    });
            dlgAlert.create().show();
        } else if (status <= 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent in = new Intent(Episode_list_Activity.this, MainActivity.class);
                            startActivity(in);

                        }
                    });
            dlgAlert.create().show();
        }

        if (status > 0) {

            if (status == 425) {

                if (isVoucher == 1) {
                    // API call for get Voucher Plan
                    GetVoucherPlan();
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
                    // API call for get Voucher Plan
                    GetVoucherPlan();
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
            if (status == 427) {


                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this);
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

                            }
                        });
                dlgAlert.create().show();
            } else if (status == 429 || status == 430) {

                new MonetizationHandler(Episode_list_Activity.this).handle429OR430statusCod(validUserStr, message, Subscription_Str);


            } else if (status == 428) {

                monetizationHandler.handle428Error(Subscription_Str);

            } else if (Util.dataModel.getIsAPV() == 1 && status == 431) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
                ShowPpvPopUp();
            } else if (PlanId.equals("1") && Subscription_Str.equals("0")) {
                Intent intent = new Intent(Episode_list_Activity.this, SubscriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            } else if (Util.dataModel.getIsConverted() == 0) {
                Util.showNoDataAlert(Episode_list_Activity.this);
               /* AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this);
                dlgAlert.setMessage(languagePreference.getTextofLanguage( Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage( SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                dlgAlert.create().show();*/
            } else {
                if (NetworkStatus.getInstance().isConnected(this)) {
                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    @Override
    public void onGetEpisodeDetailsPreExecuteStarted() {
        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();
    }

    @Override
    public void onGetEpisodeDetailsPostExecuteCompleted(Episode_Details_output episode_details_output, int status, int i, String message, String movieUniqueId) {

        LogUtil.showLog("BKS", "onget eisode details ca;l==");
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            noInternetConnectionLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }

        /*try{
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        }
        catch(IllegalArgumentException ex)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    footerView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                    episodelist.setVisibility(View.GONE);
                    noInternetConnectionLayout.setVisibility(View.GONE);
                }

            });
        }*/
        isAPV = episode_details_output.getIsAPV();
        isPPV = episode_details_output.getIs_ppv();
        movieNameStr = episode_details_output.getName();
        for (int a = 0; a < episode_details_output.getEpisodeArray().size(); a++) {

            String episodeNoStr = episode_details_output.getEpisodeArray().get(a).getEpisode_number();
            String episodeStoryStr = episode_details_output.getEpisodeArray().get(a).getEpisode_story();
            String episodeDateStr = episode_details_output.getEpisodeArray().get(a).getEpisode_date();
            String episodeImageStr = episode_details_output.getEpisodeArray().get(a).getPoster_url();
            String episodeTitleStr = episode_details_output.getEpisodeArray().get(a).getEpisode_title();
            String episodeSeriesNoStr = episode_details_output.getEpisodeArray().get(a).getSeries_number();
            String episodeMovieStreamUniqueIdStr = episode_details_output.getEpisodeArray().get(a).getMovie_stream_uniq_id();
            String episodeThirdParty = episode_details_output.getEpisodeArray().get(a).getThirdparty_url();
            int episodeContenTTypesId = episode_details_output.getEpisodeArray().get(a).getContent_types_id();
            String videodurationStr = episode_details_output.getEpisodeArray().get(a).getVideo_duration();
            String episodeVideoUrlStr = episode_details_output.getEpisodeArray().get(a).getVideo_url();

            itemData.add(new EpisodesListModel(episodeNoStr, episodeStoryStr, episodeDateStr,
                    episodeImageStr, episodeTitleStr, episodeVideoUrlStr, episodeSeriesNoStr,
                    movieUniqueId, episodeMovieStreamUniqueIdStr, episodeThirdParty, videodurationStr, episodeContenTTypesId));

        }


        Util.currencyModel = episode_details_output.getCurrencyDetails();
        Util.apvModel = episode_details_output.getApvDetails();
        Util.ppvModel = episode_details_output.getPpvDetails();
        LogUtil.showLog("BKS", "onget eisode details itemdata==" + itemData);

        if (itemData.size() <= 0) {
            footerView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.GONE);
            episodelist.setVisibility(View.VISIBLE);
            noInternetConnectionLayout.setVisibility(View.GONE);
            AsynLOADUI loadui = new AsynLOADUI();
            loadui.executeOnExecutor(threadPoolExecutor);

            //Toast.makeText(ShowWithEpisodesListActivity.this, getResources().getString(R.string.there_no_data_str), Toast.LENGTH_LONG).show();
        } else {
            footerView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.GONE);
            noInternetConnectionLayout.setVisibility(View.GONE);
            episodelist.setVisibility(View.VISIBLE);
            AsynLOADUI loadui = new AsynLOADUI();
            loadui.executeOnExecutor(threadPoolExecutor);
        }

    }


    @Override
    public void onGetLanguageListPreExecuteStarted() {
        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();
    }

    @Override
    public void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage) {
        if (pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

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
    public void onLogoutPreExecuteStarted() {
        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();
    }

    @Override
    public void onLogoutPostExecuteCompleted(int code, String status, String message) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;
        }

        if (status == null) {
            Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code > 0) {
            if (code == 200) {
                preferenceManager.clearLoginPref();
                if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
                    final Intent startIntent = new Intent(Episode_list_Activity.this, SplashScreen.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                } else {
                    final Intent startIntent = new Intent(Episode_list_Activity.this, MainActivity.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                }

            } else {
                Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }
        }
    }

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
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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

            VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

        } else {
            Toast.makeText(getApplicationContext(), voucherSubscriptionOutputModel.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetVoucherPlanPreExecuteStarted() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onGetVoucherPlanPostExecuteCompleted(GetVoucherPlanOutputModel getVoucherPlanOutputModel, int status, String message) {

        String isShow = "0", isSeason = "0", isEpisode = "0";
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();

            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }
        if (status == 200) {
            isEpisode = getVoucherPlanOutputModel.getIs_episode();
            isSeason = getVoucherPlanOutputModel.getIs_season();
            isShow = getVoucherPlanOutputModel.getIs_show();

            if (isShow.equals("0") && isSeason.equals("0")) {
                PurchageType = "episode";
                ShowVoucherPopUp("  " + movieNameStr.trim() + " S" + Util.dataModel.getSeason_id().trim() + " E" + Util.dataModel.getEpisode_no() + " ");
            } else {
                ShowVoucherPurchaseTypePopUp(isShow, isSeason, isEpisode);
            }
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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

    /*chromecast-------------------------------------*/
    private VideoView mVideoView;
    private TextView mTitleView;
    private TextView mDescriptionView;
    private TextView mStartText;
    private TextView mEndText;
    private SeekBar mSeekbar;
    private ImageView mPlayPause;
    private ProgressBar mLoading;
    private View mControllers;
    private View mContainer;
    private ImageView mCoverArt;
    private Timer mSeekbarTimer;
    private Timer mControllersTimer;
    private PlaybackLocation mLocation;
    private PlaybackState mPlaybackState;
    private final Handler mHandler = new Handler();
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaInfo mSelectedMedia;
    private boolean mControllersVisible;
    private int mDuration;
    private TextView mAuthorView;
    private ImageButton mPlayCircle;
 /*chromecast-------------------------------------*/

    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;


    private IntroductoryOverlay mIntroductoryOverlay;
    private CastStateListener mCastStateListener;
     /*chromecast-------------------------------------*/

    /*chromecast-------------------------------------*/
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_listing);

        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        featureHandler = FeatureHandler.getFeaturePreference(Episode_list_Activity.this);
        isLogin = preferenceManager.getLoginFeatureFromPref();
        playerModel = new Player();
        playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        episodeListOptionMenuHandler = new EpisodeListOptionMenuHandler(this);
        monetizationHandler = new MonetizationHandler(this);
       /* mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState != CastState.NO_DEVICES_AVAILABLE) {

                    showIntroductoryOverlay();
                }
            }
        };
        mCastContext = CastContext.getSharedInstance(Episode_list_Activity.this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(Episode_list_Activity.this, savedInstanceState);



        // int startPosition = getInt("startPosition", 0);
        // mVideoView.setVideoURI(Uri.parse(item.getContentId()));

        setupCastListener();
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();*/


        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(SEASON, DEFAULT_SEASON).toString() + " " + getIntent().getStringExtra(SEASON_INTENT_KEY));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView sectionTitle = (TextView) findViewById(R.id.sectionTitle);
        FontUtls.loadFont(Episode_list_Activity.this, getResources().getString(R.string.regular_fonts), sectionTitle);
        sectionTitle.setText(languagePreference.getTextofLanguage(EPISODE_TITLE, DEFAULT_EPISODE_TITLE));

        episodelist = (RecyclerView) findViewById(R.id.episodelist);
        noInternetConnectionLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));


        mLayoutManager = new LinearLayoutManager(Episode_list_Activity.this, LinearLayoutManager.VERTICAL, false);
        permalinkStr = getIntent().getStringExtra(PERMALINK_INTENT_KEY);
        LogUtil.showLog("BKS", "permslink  najhjh == " + permalinkStr);

        footerView = (RelativeLayout) findViewById(R.id.loadingPanel);
        footerView.setVisibility(View.GONE);

        ppvmodel = new PPVModel();
        advmodel = new APVModel();
        currencymodel = new CurrencyModel();
        PlanId = (languagePreference.getTextofLanguage(PLAN_ID, DEFAULT_PLAN_ID)).trim();

        resetData();

        boolean isNetwork = NetworkStatus.getInstance().isConnected(Episode_list_Activity.this);
        if (isNetwork) {

            if (ContextCompat.checkSelfPermission(Episode_list_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Episode_list_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(Episode_list_Activity.this,
                            new String[]{Manifest.permission
                                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                            111);
                } else {
                    ActivityCompat.requestPermissions(Episode_list_Activity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            111);
                }
            } else {
                //Call whatever you want
                if (isNetwork) {
                    Episode_Details_input episodeDetailsInput = new Episode_Details_input();
                    episodeDetailsInput.setAuthtoken(authTokenStr);
                    episodeDetailsInput.setPermalink(permalinkStr);
                    episodeDetailsInput.setSeries_number(getIntent().getStringExtra(SEASON_INTENT_KEY));
                    episodeDetailsInput.setLimit(String.valueOf(limit));
                    episodeDetailsInput.setOffset(String.valueOf(offset));
                    episodeDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                    episodeDetailsInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));


                    asynEpisodeDetails = new GetEpisodeDeatailsAsynTask(episodeDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynEpisodeDetails.executeOnExecutor(threadPoolExecutor);
                } else {
                    noInternetConnectionLayout.setVisibility(View.VISIBLE);
                }
            }
        } else {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
        }

        episodelist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (isLoading) {
                    if (totalItemCount > previousTotal) {
                        isLoading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!isLoading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem)) {
                    // End has been reached
                    listSize = itemData.size();
                    if (mLayoutManager.findLastVisibleItemPosition() >= itemsInServer - 1) {
                        isLoading = true;
                        footerView.setVisibility(View.GONE);
                        return;

                    }
                    offset += 1;
                    if (NetworkStatus.getInstance().isConnected(Episode_list_Activity.this)) {
                        // default data
                        Episode_Details_input episodeDetailsInput = new Episode_Details_input();
                        episodeDetailsInput.setAuthtoken(authTokenStr);
                        episodeDetailsInput.setPermalink(permalinkStr);
                        episodeDetailsInput.setSeries_number(getIntent().getStringExtra(SEASON_INTENT_KEY));
                        episodeDetailsInput.setLimit(String.valueOf(limit));
                        episodeDetailsInput.setOffset(String.valueOf(offset));
                        episodeDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                        episodeDetailsInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));


                        asynEpisodeDetails = new GetEpisodeDeatailsAsynTask(episodeDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                        asynEpisodeDetails.executeOnExecutor(threadPoolExecutor);
                    }

                }

            }
        });

/*----------------------------------chromecast-------------------------------------*/

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
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                updatePlaybackLocation(PlaybackLocation.REMOTE);
            } else {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));

                updatePlaybackLocation(PlaybackLocation.LOCAL);
            }
            mPlaybackState = PlaybackState.IDLE;
            updatePlayButton(mPlaybackState);
        }

    }
/*chromecast-------------------------------------*/

    public void clickItem(EpisodesListModel item) {

        try {
            Util.check_for_subscription = 1;

            itemToPlay = item;
            DataModel dbModel = new DataModel();
            dbModel.setIsFreeContent(isFreeContent);
            dbModel.setIsAPV(isAPV);
            dbModel.setIsPPV(isPPV);
            dbModel.setIsConverted(1);
            dbModel.setMovieUniqueId(item.getEpisodeMuviUniqueId());
            dbModel.setStreamUniqueId(item.getEpisodeStreamUniqueId());
            dbModel.setThirdPartyUrl(item.getEpisodeThirdPartyUrl());
            dbModel.setVideoTitle(movieNameStr);
            // dbModel.setVideoStory(getIntent().getStringExtra(Util.STORY_INTENT_KEY));
            dbModel.setVideoStory(item.getEpisodeDescription());

            dbModel.setVideoGenre(getIntent().getStringExtra(GENRE_INTENT_KEY));
            dbModel.setVideoDuration(item.getEpisodeDuration());
            // dbModel.setVideoReleaseDate(item.getEpisodeTelecastOn());
            dbModel.setVideoReleaseDate("");

            dbModel.setCensorRating(getIntent().getStringExtra(CENSOR_RATING_INTENT_KEY));
            dbModel.setCastCrew(getIntent().getBooleanExtra(CAST_INTENT_KEY, false));
            dbModel.setEpisode_id(item.getEpisodeStreamUniqueId());
            dbModel.setPosterImageId(item.getEpisodeThumbnailImageView());

            dbModel.setContentTypesId(Integer.parseInt(getIntent().getStringExtra("content_types_id")));


            dbModel.setEpisode_series_no(item.getEpisodeSeriesNo());
            dbModel.setEpisode_no(item.getEpisodeNumber());
            dbModel.setEpisode_title(item.getEpisodeTitle());


//edit by bishal
            //set the required data in playermodel

            playerModel.setStreamUniqueId(item.getEpisodeStreamUniqueId());
            playerModel.setMovieUniqueId(item.getEpisodeMuviUniqueId());
            playerModel.setUserId(preferenceManager.getUseridFromPref());
            playerModel.setEmailId(preferenceManager.getEmailIdFromPref());
            playerModel.setAuthTokenStr(authTokenStr.trim());
            playerModel.setRootUrl(BuildConfig.SERVICE_BASE_PATH);
            playerModel.setIsFreeContent(isFreeContent);
            playerModel.setEpisode_id(item.getEpisodeStreamUniqueId());
            playerModel.setVideoTitle(item.getEpisodeTitle());
            playerModel.setVideoStory(item.getEpisodeDescription());
            playerModel.setVideoGenre(getIntent().getStringExtra(GENRE_INTENT_KEY));
            playerModel.setVideoDuration(item.getEpisodeDuration());
            playerModel.setVideoReleaseDate("");
            playerModel.setCensorRating(getIntent().getStringExtra(CENSOR_RATING_INTENT_KEY));
            playerModel.setCastCrew(getIntent().getBooleanExtra(CAST_INTENT_KEY, false));


            if (!getIntent().getStringExtra(SEASON_INTENT_KEY).equals("")) {
                dbModel.setSeason_id(getIntent().getStringExtra(SEASON_INTENT_KEY));

            } else {
                dbModel.setSeason_id("0");

            }
            dbModel.setPurchase_type("episode");
            Util.dataModel = dbModel;


            SubTitleName.clear();
            SubTitlePath.clear();
            ResolutionUrl.clear();
            ResolutionFormat.clear();
            String loggedInStr = preferenceManager.getLoginStatusFromPref();
            if (isLogin == 1) {
                if (loggedInStr != null) {
                    if (NetworkStatus.getInstance().isConnected(this)) {
                        if (playerModel.getIsFreeContent() == 1) {

                            GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                            getVideoDetailsInput.setAuthToken(authTokenStr);
                            getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                            getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                            getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                            getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                            getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                            asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                        } else {
                            new CheckVoucherOrPpvPaymentHandler(Episode_list_Activity.this).handleVoucherPaymentOrPpvPayment();
                            //callValidateUserAPI();
                        }

                    } else {
                        Util.showToast(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                        //  Toast.makeText(ShowWithEpisodesActivity.this,Util.getTextofLanguage(ShowWithEpisodesActivity.this,Util.NO_INTERNET_CONNECTION,Util.DEFAULT_NO_INTERNET_CONNECTION),Toast.LENGTH_LONG).show();
                    }

                } else {

                        Util.check_for_subscription = 1;
                        Intent registerActivity = new LoginRegistrationOnContentClickHandler(this).handleClickOnContent();
                        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        registerActivity.putExtra("PlayerModel", playerModel);
                        startActivityForResult(registerActivity, VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE);

                }
            } else {
                if (NetworkStatus.getInstance().isConnected(this)) {
                    // MUVIlaxmi

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

                } else {
                    Util.showToast(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));

                    //Toast.makeText(ShowWithEpisodesActivity.this,Util.getTextofLanguage(ShowWithEpisodesActivity.this,Util.NO_INTERNET_CONNECTION,Util.DEFAULT_NO_INTERNET_CONNECTION),Toast.LENGTH_LONG).show();
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class AsynLOADUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        protected void onPostExecute(Void result) {


            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {

                noDataLayout.setVisibility(View.VISIBLE);
                noInternetConnectionLayout.setVisibility(View.GONE);
                episodelist.setVisibility(View.GONE);
                footerView.setVisibility(View.GONE);
            }

            if (footerView != null && listSize >= itemsInServer - 1) {
                footerView.setVisibility(View.GONE);
            }
            if (firstTime == true) {


                episodelist.smoothScrollToPosition(0);
                firstTime = false;
                ViewGroup.LayoutParams layoutParams = episodelist.getLayoutParams();
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
                episodelist.setLayoutParams(layoutParams);
                if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                    mLayoutManager = new GridLayoutManager(Episode_list_Activity.this, 1);
                    episodelist.setLayoutManager(mLayoutManager);

                } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                    mLayoutManager = new GridLayoutManager(Episode_list_Activity.this, 1);
                    episodelist.setLayoutManager(mLayoutManager);

                } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

                    mLayoutManager = new GridLayoutManager(Episode_list_Activity.this, 1);
                    episodelist.setLayoutManager(mLayoutManager);

                } else {
                    mLayoutManager = new GridLayoutManager(Episode_list_Activity.this, 1);
                    episodelist.setLayoutManager(mLayoutManager);
                }
                customListAdapter = new EpisodesListViewMoreAdapter(Episode_list_Activity.this, R.layout.episode_list_item, itemData, new EpisodesListViewMoreAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(EpisodesListModel item) {
                        clickItem(item);
                    }
                });
                episodelist.setAdapter(customListAdapter);

            } else {
                // save RecyclerView state
              /*  mBundleRecyclerViewState = new Bundle();
                Parcelable listState = episodelist.getLayoutManager().onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);*/


                customListAdapter = new EpisodesListViewMoreAdapter(Episode_list_Activity.this, R.layout.episode_list_item, itemData, new EpisodesListViewMoreAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(EpisodesListModel item) {
                        clickItem(item);
                    }
                });
                episodelist.setAdapter(customListAdapter);


                /*if (mBundleRecyclerViewState != null) {
                    episodelist.getLayoutManager().onRestoreInstanceState(listState);
                }*/
            }

        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
      /*  mBundleRecyclerViewState = new Bundle();
        if (mBundleRecyclerViewState != null ) {
            Parcelable listState = episodelist.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        }*/
    }


    @Override
    public void onBackPressed() {
        if (asynLoadVideoUrls != null) {
            asynLoadVideoUrls.cancel(true);
        }
        if (asynEpisodeDetails != null) {
            asynEpisodeDetails.cancel(true);
        }
        if (asynValidateUserDetails != null) {
            asynValidateUserDetails.cancel(true);
        }
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }


    public void ShowPpvPopUp() {


        try {
            if (Util.currencyModel.getCurrencySymbol() == null) {
                Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE), Toast.LENGTH_LONG).show();
            return;
        }


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) Episode_list_Activity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

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


        completeRadioButton.setText("  " + Util.dataModel.getVideoTitle().trim() + " Complete Season ");
        seasonRadioButton.setText("  " + Util.dataModel.getVideoTitle().trim() + " Season " + Util.dataModel.getEpisode_series_no().trim() + " ");
        episodeRadioButton.setText("  " + Util.dataModel.getVideoTitle().trim() + " S" + Util.dataModel.getEpisode_series_no().trim() + " E " + Util.dataModel.getEpisode_no().trim() + " ");


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
                    Util.selected_season_id = Util.dataModel.getEpisode_series_no();

                } else {
                    Util.selected_episode_id = Util.dataModel.getStreamUniqueId();
                    Util.selected_season_id = Util.dataModel.getEpisode_series_no();
                }

                alert.dismiss();
                final Intent showPaymentIntent = new Intent(Episode_list_Activity.this, PPvPaymentInfoActivity.class);
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
                startActivityForResult(showPaymentIntent, PAYMENT_REQUESTCODE);

            }
        });
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
            case R.id.media_route_menu_item:
                // Not implemented here
                return false;
            case R.id.action_search:
                final Intent searchIntent = new Intent(Episode_list_Activity.this, SearchActivity.class);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(searchIntent);
                // Not implemented here
                return false;
            case R.id.action_filter:

                // Not implemented here
                return false;
            case R.id.action_login:

                final Intent loginIntent = new Intent(Episode_list_Activity.this, LoginActivity.class);
                Util.check_for_subscription = 0;
                startActivity(loginIntent);
                // Not implemented here
                return false;
            case R.id.action_register:

                Intent registerIntent = new Intent(Episode_list_Activity.this, RegisterActivity.class);
                Util.check_for_subscription = 0;
                startActivity(registerIntent);
                // Not implemented here
                return false;
            case R.id.menu_item_favorite:

                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                favoriteIntent.putExtra("sectionName",languagePreference.getTextofLanguage(MY_FAVOURITE, DEFAULT_MY_FAVOURITE));
//                favoriteIntent.putExtra("LOGID",id);
                favoriteIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(favoriteIntent);
                // Not implemented here
                return false;
            case R.id.menu_item_language:

                // Not implemented here
                default_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);
                Previous_Selected_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);

                if (Util.languageModel != null && Util.languageModel.size() > 0) {


                    ShowLanguagePopup();

                } else {
                    LanguageListInputModel languageListInputMode = new LanguageListInputModel();
                    languageListInputMode.setAuthToken(authTokenStr);
                    GetLanguageListAsynTask asynGetLanguageList = new GetLanguageListAsynTask(languageListInputMode, this, this);
                    asynGetLanguageList.executeOnExecutor(threadPoolExecutor);
                }
                return false;
            case R.id.menu_item_profile:

                Intent profileIntent = new Intent(Episode_list_Activity.this, ProfileActivity.class);
                profileIntent.putExtra("EMAIL", email);
                profileIntent.putExtra("LOGID", id);
                startActivity(profileIntent);
                // Not implemented here
                return false;
            case R.id.action_purchage:

             /*  Intent purchaseintent = new Intent(Episode_list_Activity.this, PurchaseHistoryActivity.class);
                startActivity(purchaseintent);*/
                // Not implemented here
                return false;
            case R.id.action_logout:

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(SIGN_OUT_WARNING, DEFAULT_SIGN_OUT_WARNING));
                dlgAlert.setTitle("");

                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(YES, DEFAULT_YES), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        // dialog.cancel();
                        LogoutInput logoutInput = new LogoutInput();
                        logoutInput.setAuthToken(authTokenStr);
                        logoutInput.setLogin_history_id(preferenceManager.getLoginHistIdFromPref());
                        logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, Episode_list_Activity.this, Episode_list_Activity.this);
                        asynLogoutDetails.executeOnExecutor(threadPoolExecutor);


                        dialog.dismiss();
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


    public void ShowLanguagePopup() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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

        languageCustomAdapter = new LanguageCustomAdapter(Episode_list_Activity.this, Util.languageModel);
        recyclerView.setAdapter(languageCustomAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener1(Episode_list_Activity.this, recyclerView, new ClickListener1() {
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

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();


                if (!Previous_Selected_Language.equals(default_Language)) {

                    LanguageListInputModel languageListInputModel = new LanguageListInputModel();
                    languageListInputModel.setAuthToken(authTokenStr);
                    languageListInputModel.setLangCode(default_Language);
                    GetTranslateLanguageAsync asynGetTransalatedLanguage = new GetTranslateLanguageAsync(languageListInputModel, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynGetTransalatedLanguage.executeOnExecutor(threadPoolExecutor);
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


    @Override
    public void onGetTranslateLanguagePreExecuteStarted() {
        pDialog = new ProgressBarHandler(Episode_list_Activity.this);
        pDialog.show();
    }

    @Override
    public void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status) {


        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

        }

        if (jsonResponse == null) {
        } else {
            if (status > 0 && status == 200) {

                try {

                    Util.parseLanguage(languagePreference, jsonResponse, default_Language);
                    //Call For Language PopUp Dialog

                    languageCustomAdapter.notifyDataSetChanged();

                    Intent intent = new Intent(Episode_list_Activity.this, MainActivity.class);
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

    @Override
    public void onResume() {
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
        invalidateOptionsMenu();


    }

    /*****************
     * chromecast*-------------------------------------
     */

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
                        mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);
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

                mVideoView.pause();
                break;

            case IDLE:
                switch (mLocation) {
                    case LOCAL:
                        break;
                    case REMOTE:

                        if (mCastSession != null && mCastSession.isConnected()) {
                            // watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                            loadRemoteMedia(Played_Length, true);

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
    }

    /*****************
     * chromecast*-------------------------------------
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111: {

                if (grantResults.length > 0) {
                    if ((grantResults.length > 0) && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
                        //Call whatever you want
                        if (NetworkStatus.getInstance().isConnected(Episode_list_Activity.this)) {
                            Episode_Details_input episodeDetailsInput = new Episode_Details_input();
                            episodeDetailsInput.setAuthtoken(authTokenStr);
                            episodeDetailsInput.setPermalink(permalinkStr);
                            episodeDetailsInput.setSeries_number(getIntent().getStringExtra(SEASON_INTENT_KEY));
                            episodeDetailsInput.setLimit(String.valueOf(limit));
                            episodeDetailsInput.setOffset(String.valueOf(offset));
                            episodeDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                            episodeDetailsInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));


                            asynEpisodeDetails = new GetEpisodeDeatailsAsynTask(episodeDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                            asynEpisodeDetails.executeOnExecutor(threadPoolExecutor);
                        } else {
                            noInternetConnectionLayout.setVisibility(View.VISIBLE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1001) {
            if (data.getStringExtra("yes").equals("1002")) {
                watch_status_String = "halfplay";
                seek_status = "first_time";
                Played_Length = Util.dataModel.getPlayPos() * 1000;
                resume_time = "" + Util.dataModel.getPlayPos();
                PlayThroughChromeCast();

            } else {
                watch_status_String = "strat";
                Played_Length = 0;
                PlayThroughChromeCast();
            }
        } else if (resultCode == RESULT_OK && requestCode == 1007) {

            if (data.getStringExtra("yes").equals("1002")) {

                watch_status_String = "halfplay";
                seek_status = "first_time";
                Played_Length = Util.dataModel.getPlayPos() * 1000;
                togglePlayback();

            } else {
                watch_status_String = "strat";
                Played_Length = 0;
                togglePlayback();
            }
        } else if (requestCode == VIDEO_PLAY_BUTTON_CLICK_LOGIN_REG_REQUESTCODE && resultCode == RESULT_OK) {
            new CheckVoucherOrPpvPaymentHandler(Episode_list_Activity.this).handleVoucherPaymentOrPpvPayment();
            // callValidateUserAPI();
        } else if (requestCode == PAYMENT_REQUESTCODE && resultCode == RESULT_OK) {
            getVideoInfo();
        }
    }

    @Override
    public void onIPAddressPreExecuteStarted() {

    }

    @Override
    public void onIPAddressPostExecuteCompleted(String message, int statusCode, String ipAddressStr) {
        this.ipAddressStr = ipAddressStr;
        return;
    }


    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL().execute(Url);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBarHandler(Episode_list_Activity.this);
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
            LogUtil.showLog("MUVI", "Download Completed");
            FakeSubTitlePath.remove(0);
            if (FakeSubTitlePath.size() > 0) {
                Download_SubTitle(FakeSubTitlePath.get(0).trim());
            } else {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                }
                playerModel.setSubTitlePath(SubTitlePath);
                Intent playVideoIntent;
                if (Util.goToLibraryplayer) {
                    playVideoIntent = new Intent(Episode_list_Activity.this, MyLibraryPlayer.class);


                } else {
                    if (Util.dataModel.getAdNetworkId() == 3) {
                        LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                        playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                    } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                        if (Util.dataModel.getPlayPos() <= 0) {
                            playVideoIntent = new Intent(Episode_list_Activity.this, AdPlayerActivity.class);
                        } else {
                            playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                        }
                    } else {
                        playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                    }
                    // playVideoIntent = new Intent(Episode_list_Activity.this, ExoPlayerActivity.class);

                }
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
            }
        }


    }


    public void handleActionForValidateUserForVoucherPayment(String validUserStr, String message, String subscription_Str) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(Episode_list_Activity.this)) {

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    if (isVoucher == 1) {
                        // Don't need for API call to get Voucher Plan
                        // Directly show the voucher popup
                        GetVoucherPlan();
                    } else {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
                if (NetworkStatus.getInstance().isConnected(Episode_list_Activity.this)) {
                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();

                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
                        ShowPpvPopUp();
                    } else if (PlanId.equals("1") && subscription_Str.equals("0")) {
                        Intent intent = new Intent(Episode_list_Activity.this, SubscriptionActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else {
                        ShowPpvPopUp();
                    }
                }

            }
        }
    }

    public void handleActionForValidateSonyUserPayment(String validUserStr, String message, String subscription_Str, String alertShowMsg) {
        if (validUserStr != null) {


            if ((validUserStr.trim().equalsIgnoreCase("OK")) || (validUserStr.trim().matches("OK")) || (validUserStr.trim().equals("OK"))) {
                if (NetworkStatus.getInstance().isConnected(Episode_list_Activity.this)) {

                    GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                    getVideoDetailsInput.setAuthToken(authTokenStr);
                    getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                    getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                    getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                    getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                    getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
                    asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                } else {
                    Toast.makeText(Episode_list_Activity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            } else {

                if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                    Util.showActivateSubscriptionWatchVideoAleart(this, alertShowMsg);
                }

            }
        }
    }

    public void handleActionForValidateUserPaymentForVoucher(String validUserStr, String message, String subscription_Str) {


    }

    public void getMonitizationDetailsApi() {

        MonitizationDetailsInput monitizationDetailsInput = new MonitizationDetailsInput();
        monitizationDetailsInput.setAuthToken(authTokenStr);
        monitizationDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
        monitizationDetailsInput.setMovie_id(Util.dataModel.getMovieUniqueId());
        monitizationDetailsInput.setStream_id(Util.dataModel.getStreamUniqueId());
        monitizationDetailsInput.setPurchase_type("episode");
        getMonitizationDetailsAsync = new GetMonitizationDetailsAsync(monitizationDetailsInput, this, this);
        getMonitizationDetailsAsync.executeOnExecutor(threadPoolExecutor);
    }

    public void ShowVoucherPurchaseTypePopUp(String isShow, String isSeason, String isEpisode) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) Episode_list_Activity.this.getSystemService(Episode_list_Activity.this.LAYOUT_INFLATER_SERVICE);

        View convertView = (View) inflater.inflate(R.layout.voucher_plan_popup, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("");

        final RadioButton completeRadioButton = (RadioButton) convertView.findViewById(R.id.completeRadioButton);
        final RadioButton seasonRadioButton = (RadioButton) convertView.findViewById(R.id.seasonRadioButton);
        final RadioButton episodeRadioButton = (RadioButton) convertView.findViewById(R.id.episodeRadioButton);
        Button payNowButton = (Button) convertView.findViewById(R.id.payNowButton);
        TextView heading = (TextView) convertView.findViewById(R.id.heading);


        // Font implemented Here//

        Typeface typeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        completeRadioButton.setTypeface(typeface);
        seasonRadioButton.setTypeface(typeface);
        episodeRadioButton.setTypeface(typeface);
        payNowButton.setTypeface(typeface);
        heading.setTypeface(typeface);


        // ============end==============//

        // Language Implemented Here //

        completeRadioButton.setText("  " + movieNameStr.trim() + " " + languagePreference.getTextofLanguage(COMPLETE_SEASON, DEFAULT_COMPLETE_SEASON) + " ");
        seasonRadioButton.setText("  " + movieNameStr.trim() + " " + languagePreference.getTextofLanguage(SEASON, DEFAULT_SEASON) + " " + Util.dataModel.getSeason_id().trim() + " ");
        episodeRadioButton.setText("  " + movieNameStr.trim() + " S" + Util.dataModel.getSeason_id().trim() + " E" + Util.dataModel.getEpisode_no() + " ");
        heading.setText(languagePreference.getTextofLanguage(SELECT_PURCHASE_TYPE, DEFAULT_SELECT_PURCHASE_TYPE));
        payNowButton.setText(languagePreference.getTextofLanguage(NEXT, DEFAULT_NEXT));


        //==============End===============//


        if (isEpisode.equals("1")) {
            episodeRadioButton.setVisibility(View.VISIBLE);
        } else {
            episodeRadioButton.setVisibility(View.GONE);
        }
        if (isSeason.equals("1")) {
            seasonRadioButton.setVisibility(View.VISIBLE);
        } else {
            seasonRadioButton.setVisibility(View.GONE);
        }
        if (isShow.equals("1")) {
            completeRadioButton.setVisibility(View.VISIBLE);
        } else {
            completeRadioButton.setVisibility(View.GONE);
        }

        voucher_alert = alertDialog.show();
        completeRadioButton.setChecked(true);

        // Changed later

        if (completeRadioButton.getVisibility() == View.VISIBLE) {

            completeRadioButton.setChecked(true);
            episodeRadioButton.setChecked(false);
            seasonRadioButton.setChecked(false);

            PurchageType = "show";
            ContentName = completeRadioButton.getText().toString().trim();

        } else {
            if (seasonRadioButton.getVisibility() == View.VISIBLE) {

                completeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(true);
                episodeRadioButton.setChecked(false);

                PurchageType = "season";
                ContentName = seasonRadioButton.getText().toString().trim();

            } else {
                completeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(false);
                episodeRadioButton.setChecked(true);

                PurchageType = "episode";
                ContentName = episodeRadioButton.getText().toString().trim();

            }
        }

        ///////////////////////=====================////////////////////////


        episodeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(false);

                PurchageType = "episode";
                ContentName = episodeRadioButton.getText().toString().trim();


            }
        });
        seasonRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                episodeRadioButton.setChecked(false);
                completeRadioButton.setChecked(false);

                PurchageType = "season";
                ContentName = seasonRadioButton.getText().toString().trim();


            }
        });

        completeRadioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                episodeRadioButton.setChecked(false);
                seasonRadioButton.setChecked(false);

                PurchageType = "show";
                ContentName = completeRadioButton.getText().toString().trim();


            }

        });

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voucher_alert.dismiss();
                ShowVoucherPopUp(ContentName);
            }
        });

    }

    public void ShowVoucherPopUp(String ContentName) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) Episode_list_Activity.this.getSystemService(Episode_list_Activity.this.LAYOUT_INFLATER_SERVICE);

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

        content_label.setText(" " + languagePreference.getTextofLanguage(PURCHASE, DEFAULT_PURCHASE) + " : ");
        voucher_success.setText(" " + languagePreference.getTextofLanguage(VOUCHER_SUCCESS, DEFAULT_VOUCHER_SUCCESS) + " ");
        apply.setText(" " + languagePreference.getTextofLanguage(BUTTON_APPLY, DEFAULT_BUTTON_APPLY) + " ");
        watch_now.setText(" " + languagePreference.getTextofLanguage(WATCH_NOW, DEFAULT_WATCH_NOW) + " ");
        voucher_code.setHint(" " + languagePreference.getTextofLanguage(ENTER_VOUCHER_CODE, DEFAULT_ENTER_VOUCHER_CODE) + " ");


        //==============End===============//

        voucher_code.setText("");
        watch_now.setBackgroundResource(R.drawable.voucher_inactive_button);
        watch_now.setTextColor(Color.parseColor("#7f7f7f"));

        voucher_success.setVisibility(View.INVISIBLE);

        content_name.setText(ContentName);

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
                    voucherSubscriptionInputModel.setSeason(Util.dataModel.getSeason_id());
                    voucherSubscriptionInputModel.setIs_preorder("" + Util.dataModel.getIsAPV());
                    voucherSubscriptionInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    voucherSubscriptionInputModel.setPurchase_type("show");
                    VoucherSubscriptionAsyntask asynVoucherSubscription = new VoucherSubscriptionAsyntask(voucherSubscriptionInputModel, Episode_list_Activity.this, Episode_list_Activity.this);
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
        validateVoucherInputModel.setPurchase_type("episode");
        validateVoucherInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        validateVoucherInputModel.setStream_id(Util.dataModel.getStreamUniqueId().trim());
        validateVoucherInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        ValidateVoucherAsynTask asynValidateVoucher = new ValidateVoucherAsynTask(validateVoucherInputModel, this, this);
        asynValidateVoucher.executeOnExecutor(threadPoolExecutor);
    }

    public void GetVoucherPlan() {
        GetVoucherPlanInputModel getVoucherPlanInputModel = new GetVoucherPlanInputModel();
        getVoucherPlanInputModel.setAuthToken(authTokenStr);
        getVoucherPlanInputModel.setUser_id(preferenceManager.getUseridFromPref());
        getVoucherPlanInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        getVoucherPlanInputModel.setSeason(Util.dataModel.getSeason_id());
        getVoucherPlanInputModel.setStream_id(Util.dataModel.getStreamUniqueId());
        GetVoucherPlanAsynTask getVoucherPlan = new GetVoucherPlanAsynTask(getVoucherPlanInputModel, this, this);
        getVoucherPlan.executeOnExecutor(threadPoolExecutor);
    }


    /**
     * callValidateUserAPI
     */

    public void callValidateUserAPI() {
        ValidateUserInput validateUserInput = new ValidateUserInput();
        validateUserInput.setAuthToken(authTokenStr);
        validateUserInput.setUserId(preferenceManager.getUseridFromPref());
        validateUserInput.setMuviUniqueId(Util.dataModel.getMovieUniqueId().trim());
        validateUserInput.setPurchaseType(Util.dataModel.getPurchase_type());
        validateUserInput.setSeasonId(Util.dataModel.getSeason_id());
        validateUserInput.setEpisodeStreamUniqueId(Util.dataModel.getEpisode_id());
        validateUserInput.setLanguageCode(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, Episode_list_Activity.this, Episode_list_Activity.this);
        asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);
    }

    public void handleFor428Status(String subscription_Str) {
        if (Util.dataModel.getIsAPV() == 1 || Util.dataModel.getIsPPV() == 1) {
            ShowPpvPopUp();
        } else if (PlanId.equals("1") && subscription_Str.equals("0")) {
            Intent intent = new Intent(Episode_list_Activity.this, SubscriptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else {
            ShowPpvPopUp();
        }

    }

    public void handleFor428StatusVoucher(String subscription_Str) {

        if (isVoucher == 1) {
            // API call for get Voucher Plan
            GetVoucherPlan();
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Episode_list_Activity.this, R.style.MyAlertDialogStyle);
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
        asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, Episode_list_Activity.this, Episode_list_Activity.this);
        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
    }
}
