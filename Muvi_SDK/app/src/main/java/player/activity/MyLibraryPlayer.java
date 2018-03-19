package player.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.images.WebImage;
import com.home.vod.HandleOfflineInExoplayer;
import com.home.vod.R;
import com.home.vod.activity.CastAndCrewActivity;
import com.home.vod.activity.MovieDetailsActivity;
import com.home.vod.activity.SupportActivity1;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.ResizableCustomView;
import com.home.vod.util.StrokedTextView;
import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.Runtime;
import com.intertrust.wasabi.media.PlaylistProxy;
import com.intertrust.wasabi.media.PlaylistProxyListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import player.adapter.DownloadOptionAdapter;
import player.model.ContactModel1;
import player.model.SubtitleModel;
import player.service.PopUpService;
import player.subtitle_support.Caption;
import player.subtitle_support.FormatSRT;
import player.subtitle_support.FormatSRT_WithoutCaption;
import player.subtitle_support.TimedTextObject;
import player.utils.DBHelper;
import player.utils.SensorOrientationChangeNotifier;
import player.utils.Util;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_IS_STREAMING_RESTRICTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SAVE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SAVE_OFFLINE_VIDEO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;

import static com.home.vod.preferences.LanguagePreference.SAVE;
import static com.home.vod.preferences.LanguagePreference.SAVE_OFFLINE_VIDEO;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;


enum ContentTypes3 {
    DASH("application/dash+xml"), HLS("application/vnd.apple.mpegurl"), PDCF(
            "video/mp4"), M4F("video/mp4"), DCF("application/vnd.oma.drm.dcf"), BBTS(
            "video/mp2t");
    String mediaSourceParamsContentType = null;

    private ContentTypes3(String mediaSourceParamsContentType) {
        this.mediaSourceParamsContentType = mediaSourceParamsContentType;
    }

    public String getMediaSourceParamsContentType() {
        return mediaSourceParamsContentType;
    }
}

public class MyLibraryPlayer extends AppCompatActivity implements SensorOrientationChangeNotifier.Listener, PlaylistProxyListener, AdEvent.AdEventListener, AdErrorEvent.AdErrorListener {

    PlaylistProxy playerProxy;
    int played_length = 0;
    String adDetails[];
    int playerStartPosition = 0;
    boolean censor_layout = true;
    boolean stopOrientationLiastener = false;

    // MyLibraryPlayer.this is added for the new video log API;

    int player_start_time = 0;
    int player_end_time = 0;
    String log_temp_id = "0";
    LanguagePreference languagePreference;
    // ===========End============================//

    /***** offline *****/
    DownloadManager downloadManager;
    RelativeLayout download_layout;
    public boolean downloading;
    //Handler mHandler;
    static String filename, path;
    ArrayList<ContactModel1> dmanager;
    AsynWithdrm asynWithdrm;
    ContactModel1 audio, audio_1;
    DBHelper dbHelper;
    public Handler exoplayerdownloadhandler;
    public long enqueue;
    ImageView download;
    ProgressBar Progress;
    TextView percentg;
    private static final int REQUEST_STORAGE = 1;
    File mediaStorageDir, mediaStorageDir1;

    String mlvfile = "";
    String token = "";
    String fname;
    String fileExtenstion;
    int lenghtOfFile;
    int lengthfile;
    Timer CheckAvailabilityOfChromecast;
    boolean video_prepared = false;
    /***** offline *****/

    Timer timer;
    private Handler threadHandler = new Handler();
    String videoLogId = "0";
    String restrict_stream_id = "0";
    String watchStatus = "start";
    int playerPosition = 0;
    public boolean isFastForward = false;
    public int playerPreviousPosition = 0;
    TimerTask timerTask;
    String emailIdStr = "";
    String userIdStr = "";
    String movieId = "";
    String episodeId = "0";
    AsyncVideoLogDetails asyncVideoLogDetails;
    AsyncFFVideoLogDetails asyncFFVideoLogDetails;

    AsynGetIpAddress asynGetIpAddress;

    ImageButton back, center_play_pause;
    ImageView compress_expand;
    SeekBar seekBar;
    private Handler mHandler = new Handler();
    Timer center_pause_paly_timer;
    String Current_Time, TotalTime;
    TextView current_time, total_time;
    ProgressBar progressView;
    LinearLayout primary_ll, last_ll;
    boolean video_completed = false;
    // TextView detais_text;
    TextView ipAddressTextView;
    TextView emailAddressTextView;
    TextView dateTextView;
    long previous_matching_time = 0, current_matching_time = 0;
    boolean center_pause_paly_timer_is_running = false;
    RelativeLayout player_layout;

    boolean compressed = true;
    int player_layout_height, player_layout_width;
    int screenWidth, screenHeight;
    ImageButton latest_center_play_pause;
    String licensetoken;

    String resolution = "BEST";

    String ipAddressStr = "";
    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    //Toolbar mActionBarToolbar;
    LinearLayout linearLayout1;

    TextView videoTitle, GenreTextView, videoDurationTextView, videoCensorRatingTextView, videoCensorRatingTextView1, videoReleaseDateTextView,
            videoCastCrewTitleTextView;
    TextView story;
    private static final int MAX_LINES = 2;

    private EMVideoView emVideoView;
    int seek_label_pos = 0;
    int content_types_id = 0;

    // Added For Buffer Log
    long PreviousUsedData = 0;
    long CurrentUsedData = 0;

    long PreviousUsedData_By_DownloadContent = 0;


    long DataUsedByChrmoeCast = 0;
    long Current_Sesion_DataUsedByChrmoeCast = 0;

    String videoBufferLogId = "0";
    String videoBufferLogUniqueId = "0";
    String Location = "0";

    // Adder Later // By Bibhu

    private SubtitleProcessingTask subsFetchTask;
    public TimedTextObject srt;
    StrokedTextView subtitleText;
    public Handler subtitleDisplayHandler;
    ImageView subtitle_change_btn;

    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    boolean callWithoutCaption = true;
    boolean video_completed_at_chromecast = false;

    // MyLibraryPlayer.this is only applicable for multiple download feature

    ArrayList<String> List_Of_FileSize = new ArrayList<>();
    ArrayList<String> List_Of_Resolution_Format = new ArrayList<>();
    ArrayList<String> List_Of_Resolution_Url = new ArrayList<>();
    ArrayList<String> List_Of_Resolution_Url_Used_For_Download = new ArrayList<>();

    ProgressBarHandler pDialog_for_gettig_filesize;
    AlertDialog alert;
    int selected_download_format = 0;
    String Dwonload_Complete_Msg = "";

    boolean isDrm = false;

    Context castContext;
    Drawable drawable = null;
    TypedArray a;

    // =====================End==============================//


    /*----------------chromecast-------------------------------------*/
    View view;

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
    private SessionManagerListener<CastSession> mSessionManagerListener = null;
    public CastSession mCastSession = null;
    MediaInfo mediaInfo;
    MediaRouteButton mediaRouteButton;
    FeatureHandler featureHandler;

    RemoteMediaClient remoteMediaClient;
    long[] tracksArray;
    int current_played_length = 0;
    long cast_disconnected_position = 0;
    String active_track_index = "0";

    Player playerModel;
    LinearLayout new_detailsLayout;


    @Override
    protected void onResume() {

        super.onResume();

        stopOrientationLiastener = false;
        CheckAvailabilityOfChromecast = new Timer();
        CheckAvailabilityOfChromecast.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.v("PINTU", "CheckAvailabilityOfChromecast called");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        try {

                            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                            int orientation = display.getRotation();

                            Log.v("PINTU", "CheckAvailabilityOfChromecast called orientation=" + orientation);

                            if (orientation == 1 || orientation == 3) {
                                hideSystemUI();
                            }
                        } catch (Exception e) {
                        }

                        if (featureHandler.getFeatureStatus(FeatureHandler.CHROMECAST, FeatureHandler.DEFAULT_CHROMECAST)) {
                            if (video_prepared) {
                                if (mediaRouteButton.isEnabled()) {
                                    //  mediaRouteButton.setVisibility(View.VISIBLE);
                                    handleOfflineInExoplayer.handleVisibleUnvisibleChromcast(mediaRouteButton);
                                } else {
                                    mediaRouteButton.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            mediaRouteButton.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }, 1000, 1000);


        if (mAdsManager != null && mIsAdDisplayed) {
            mAdsManager.resume();
        } else {
            Util.app_is_in_player_context = true;
            Log.v("PINTU", "onresume called");

            if (Util.call_finish_at_onUserLeaveHint) {

                SensorOrientationChangeNotifier.getInstance(MyLibraryPlayer.this).addListener(MyLibraryPlayer.this);
                AsynGetIpAddress asynGetIpAddress = new AsynGetIpAddress();
                asynGetIpAddress.executeOnExecutor(threadPoolExecutor);
            }
        }
    }

    ProgressBarHandler mDialog;
    // private SampleVideoPlayer mVideoPlayer;

    // The container for the ad's UI.
    private ViewGroup mAdUiContainer;

    // Factory class for creating SDK objects.
    private ImaSdkFactory mSdkFactory;

    // The AdsLoader instance exposes the requestAds method.
    private AdsLoader mAdsLoader;

    // AdsManager exposes methods to control ad playback and listen to ad events.
    private AdsManager mAdsManager;

    // Whether an ad is displayed.
    private boolean mIsAdDisplayed;
    HandleOfflineInExoplayer handleOfflineInExoplayer;
    LinearLayout back_layout, cc_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_exoplayer);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");

        mAdUiContainer = (ViewGroup) findViewById(R.id.videoPlayerWithAdPlayback);
        back_layout = (LinearLayout) findViewById(R.id.back_layout);
        handleOfflineInExoplayer = new HandleOfflineInExoplayer(this);
        featureHandler = FeatureHandler.getFeaturePreference(MyLibraryPlayer.this);
        // setContentView(layout);
        mSdkFactory = ImaSdkFactory.getInstance();
        mAdsLoader = mSdkFactory.createAdsLoader(this);
        // Add listeners for when ads are loaded and for errors.
        mAdsLoader.addAdErrorListener(this);
        mAdsLoader.addAdsLoadedListener(new AdsLoader.AdsLoadedListener() {
            @Override
            public void onAdsManagerLoaded(AdsManagerLoadedEvent adsManagerLoadedEvent) {
                // Ads were successfully loaded, so get the AdsManager instance. AdsManager has
                // events for ad playback and errors.
                mAdsManager = adsManagerLoadedEvent.getAdsManager();

                // Attach event and error event listeners.
                mAdsManager.addAdErrorListener(MyLibraryPlayer.this);
                mAdsManager.addAdEventListener(MyLibraryPlayer.this);
                mAdsManager.init();
            }
        });

        if (playerModel.getVideoUrl().contains(".mpd")) {
            isDrm = true;
        } else {
            isDrm = false;
        }

        if (!playerModel.getVideoUrl().trim().equals("")) {
            if (playerModel.isThirdPartyPlayer()) {

                if (playerModel.getVideoUrl().contains("://www.youtube") || playerModel.getVideoUrl().contains("://www.youtu.be")) {
                    if (playerModel.getVideoUrl().contains("live_stream?channel")) {
                        final Intent playVideoIntent = new Intent(MyLibraryPlayer.this, ThirdPartyPlayer.class);
                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        playVideoIntent.putExtra("PlayerModel", playerModel);
                        startActivity(playVideoIntent);
                        finish();
                        return;
                    } else {
                        final Intent playVideoIntent = new Intent(MyLibraryPlayer.this, YouTubeAPIActivity.class);
                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        playVideoIntent.putExtra("PlayerModel", playerModel);
                        startActivity(playVideoIntent);
                        finish();
                        return;
                    }
                } else {
                    final Intent playVideoIntent = new Intent(MyLibraryPlayer.this, ThirdPartyPlayer.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);
                    finish();
                    return;

                }
            }
        } else
            backCalled();

        content_types_id = playerModel.getContentTypesId();
        played_length = playerModel.getPlayPos() * 1000;
        current_played_length = played_length;
        PreviousUsedDataByApp(true);
        PreviousUsedData_By_DownloadContent = DataUsedByDownloadContent();

        /********* Offline********/


        if (playerModel != null && playerModel.getUserId() != null && !playerModel.getUserId().trim().matches("")) {
            userIdStr = playerModel.getUserId();
        }
        if (playerModel != null && playerModel.getEmailId() != null && !playerModel.getEmailId().trim().matches("")) {
            emailIdStr = playerModel.getEmailId();
        }

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);


        exoplayerdownloadhandler = new Handler();
        dbHelper = new DBHelper(MyLibraryPlayer.this);
        dbHelper.getWritableDatabase();
        audio_1 = dbHelper.getContact(playerModel.getStreamUniqueId() + emailIdStr);

        if (audio_1 != null) {
            if (audio_1.getUSERNAME().trim().equals(emailIdStr.trim())) {
                checkDownLoadStatusFromDownloadManager1(audio_1, false);
            }
        }

        download = (ImageView) findViewById(R.id.downloadImageView);
        Progress = (ProgressBar) findViewById(R.id.progressBar);
        percentg = (TextView) findViewById(R.id.percentage);
        new_detailsLayout = (LinearLayout) findViewById(R.id.new_detailsLayout);
        new_detailsLayout.setVisibility(View.GONE);


        //Check for offline content // Added By sanjay
        mediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        download_layout = (RelativeLayout) findViewById(R.id.downloadRelativeLayout);
        if (content_types_id != 4 && playerModel.getIsOffline().equals("1") && playerModel.getDownloadStatus().equals("1")) {
            download_layout.setVisibility(View.VISIBLE);
        }

       /* if (content_types_id!=4 && playerModel.getIsOffline().equals("1")) {
            download_layout.setVisibility(View.VISIBLE);
        }*/


        /********* Offline ********/
/***************chromecast**********************/

        mAquery = new AQuery(MyLibraryPlayer.this);
        setupCastListener();
        mCastContext = CastContext.getSharedInstance(MyLibraryPlayer.this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(MyLibraryPlayer.this, savedInstanceState);
        mCastSession = CastContext.getSharedInstance(MyLibraryPlayer.this).getSessionManager().getCurrentCastSession();
        mCastContext.getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);


        boolean shouldStartPlayback = false;
        int startPosition = 0;

        if (shouldStartPlayback) {
            // MyLibraryPlayer.this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            mPlaybackState = PlaybackState.PLAYING;
            updatePlaybackLocation(PlaybackLocation.LOCAL);
            updatePlayButton(mPlaybackState);
            if (startPosition > 0) {
            }
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

        // Added For Chromecast By BIBHU//


        castContext = new ContextThemeWrapper(MyLibraryPlayer.this, android.support.v7.mediarouter.R.style.Theme_MediaRouter);
        a = castContext.obtainStyledAttributes(null, android.support.v7.mediarouter.R.styleable.MediaRouteButton, android.support.v7.mediarouter.R.attr.mediaRouteButtonStyle, 0);
        drawable = a.getDrawable(android.support.v7.mediarouter.R.styleable.MediaRouteButton_externalRouteEnabledDrawable);
        a.recycle();
        DrawableCompat.setTint(drawable, getResources().getColor(R.color.resumeTitleTextColor));


        CastButtonFactory.setUpMediaRouteButton(MyLibraryPlayer.this, mediaRouteButton);
        mediaRouteButton.setRemoteIndicatorDrawable(drawable);

        /***************chromecast**********************/


        if (playerModel.getVideoUrl().matches("")) {
            backCalled();
            //onBackPressed();
        }
        movieId = playerModel.getMovieUniqueId();
        episodeId = playerModel.getEpisode_id();

        if (playerModel != null && playerModel.getUserId() != null && !playerModel.getUserId().trim().matches("")) {
            userIdStr = playerModel.getUserId();
        }
        if (playerModel != null && playerModel.getEmailId() != null && !playerModel.getEmailId().trim().matches("")) {
            emailIdStr = playerModel.getEmailId();
        }

        emVideoView = (EMVideoView) findViewById(R.id.emVideoView);
        cc_layout = (LinearLayout) findViewById(R.id.cc_layout);
        subtitleText = (StrokedTextView) findViewById(R.id.offLine_subtitleText);
        subtitle_change_btn = (ImageView) findViewById(R.id.subtitle_change_btn);

        latest_center_play_pause = (ImageButton) findViewById(R.id.latest_center_play_pause);
        videoTitle = (TextView) findViewById(R.id.videoTitle);
        Typeface videoTitleface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_regular));
        videoTitle.setTypeface(videoTitleface);
        GenreTextView = (TextView) findViewById(R.id.GenreTextView);
        Typeface GenreTextViewface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        GenreTextView.setTypeface(GenreTextViewface);
        videoDurationTextView = (TextView) findViewById(R.id.videoDurationTextView);
        Typeface videoDurationTextViewface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        videoDurationTextView.setTypeface(videoDurationTextViewface);
        videoCensorRatingTextView = (TextView) findViewById(R.id.videoCensorRatingTextView);
        Typeface videoCensorRatingTextViewface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        videoCensorRatingTextView.setTypeface(videoCensorRatingTextViewface);
        videoCensorRatingTextView1 = (TextView) findViewById(R.id.videoCensorRatingTextView1);
        Typeface videoCensorRatingTextView1face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        videoCensorRatingTextView1.setTypeface(videoCensorRatingTextView1face);
        videoReleaseDateTextView = (TextView) findViewById(R.id.videoReleaseDateTextView);
        Typeface videoReleaseDateTextViewface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        videoReleaseDateTextView.setTypeface(videoReleaseDateTextViewface);
        story = (TextView) findViewById(R.id.story);
        Typeface storyTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        story.setTypeface(storyTypeface);
        videoCastCrewTitleTextView = (TextView) findViewById(R.id.videoCastCrewTitleTextView);
        Typeface watchTrailerButtonTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts));
        videoCastCrewTitleTextView.setTypeface(watchTrailerButtonTypeface);
        videoCastCrewTitleTextView.setText(Util.getTextofLanguage(MyLibraryPlayer.this, Util.CAST_CREW_BUTTON_TITLE, Util.DEFAULT_CAST_CREW_BUTTON_TITLE));

        // MyLibraryPlayer.this is changed for the new requirement of Offline Viewing.
        startService(new Intent(MyLibraryPlayer.this, DataConsumptionService.class));
        registerReceiver(SelectedUrl, new IntentFilter("UrlPosition"));

        //Call For Subtitle Loading // Added By Bibhu


        if (playerModel.getSubTitleName() != null) {
            SubTitleName = playerModel.getSubTitleName();

            Log.v("BIBHU1", "SubTitleName = " + SubTitleName.size());

        } else {
            SubTitleName.clear();
        }

        if (playerModel.getSubTitlePath() != null) {
            SubTitlePath = playerModel.getSubTitlePath();
            Log.v("BIBHU1", "SubTitlePath = " + SubTitlePath.size());
        } else {
            SubTitlePath.clear();
        }


        //===============================This is used for Resolution Change ===================================//

        if (!isDrm) {
            Util.VideoResolution = "Auto";

            if (playerModel.getMidRoll() == 1) {

                adDetails = playerModel.getAdDetails().split(",");

            }

            if (playerModel.getResolutionFormat() != null) {
                ResolutionFormat = playerModel.getResolutionFormat();
            } else {
                ResolutionFormat.clear();

            }

            if (playerModel.getResolutionUrl() != null) {
                ResolutionUrl = playerModel.getResolutionUrl();

            } else {
                ResolutionUrl.clear();
            }

            Log.v("BIBHU1", "ResolutionUrl = " + ResolutionUrl.size());

            if (ResolutionUrl.size() < 1) {
                Log.v("SUBHA", "resolution image Invisible called");
            } else {
                ResolutionUrl.add(playerModel.getVideoUrl().trim());
                ResolutionFormat.add("Auto");
            }

            if (ResolutionFormat.size() > 0) {
                Collections.reverse(ResolutionFormat);
                for (int m = 0; m < ResolutionFormat.size(); m++) {
                    Log.v("BIBHU", "RESOLUTION FORMAT======" + ResolutionFormat.get(m));
                }
            }
            if (ResolutionUrl.size() > 0) {
                Collections.reverse(ResolutionUrl);
                for (int n = 0; n < ResolutionUrl.size(); n++) {
                    Log.v("BIBHU", "RESOLUTION URL======" + ResolutionUrl.get(n));
                }
            }


        }



        if(!featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)){
            SubTitlePath.clear();
            playerModel.offline_url.clear();
        }

        if(!featureHandler.getFeatureStatus(FeatureHandler.IS_RESOLUTION,FeatureHandler.DEFAULT_IS_RESOLUTION)){
            ResolutionUrl.clear();
        }


        //=========================End=================================//

        if (isDrm) {
            if (SubTitlePath.size() < 1) {
                subtitle_change_btn.setVisibility(View.INVISIBLE);
            } else {
                subtitle_change_btn.setBackgroundResource(R.drawable.cc_button_radious);
                subtitle_change_btn.setImageResource(R.drawable.subtitle_image_drm);
                subtitle_change_btn.setVisibility(View.VISIBLE);
                Log.v("BIBHU1", "subtitle_image button visible called");
            }
        } else {
            if ((SubTitlePath.size() < 1) && (ResolutionUrl.size() < 1)) {
                subtitle_change_btn.setVisibility(View.INVISIBLE);
                Log.v("BIBHU1", "subtitle_image button Invisible called");
            } else {
                subtitle_change_btn.setBackgroundResource(0);
                subtitle_change_btn.setImageResource(R.drawable.subtitle_image);
                subtitle_change_btn.setVisibility(View.VISIBLE);
                Log.v("BIBHU1", "subtitle_image button visible called");

                try {
                    if(SubTitleName.size()>0)
                        Util.DefaultSubtitle = SubTitleName.get(0);
                }catch (Exception e){}
            }
        }


        subtitle_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    if(!changeSubtitle_Resolution()){
                        return;
                    }

                    Util.call_finish_at_onUserLeaveHint = false;

                    if (isDrm) {
                        Intent intent = new Intent(MyLibraryPlayer.this, SubtitleList.class);
                        intent.putExtra("SubTitleName", SubTitleName);
                        intent.putExtra("SubTitlePath", SubTitlePath);
                        startActivityForResult(intent, 222);
                    } else {
                        Intent intent = new Intent(MyLibraryPlayer.this, Subtitle_Resolution.class);
                        intent.putExtra("ResolutionFormat", ResolutionFormat);
                        intent.putExtra("ResolutionUrl", ResolutionUrl);
                        intent.putExtra("SubTitleName", SubTitleName);
                        intent.putExtra("SubTitlePath", SubTitlePath);
                        startActivityForResult(intent, 222);
                    }


                } catch (Exception e) {
                }

            }
        });

        cc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    if(!changeSubtitle_Resolution()){
                        return;
                    }
                    Util.call_finish_at_onUserLeaveHint = false;

                    if (isDrm) {
                        Intent intent = new Intent(MyLibraryPlayer.this, SubtitleList.class);
                        intent.putExtra("SubTitleName", SubTitleName);
                        intent.putExtra("SubTitlePath", SubTitlePath);
                        startActivityForResult(intent, 222);
                    } else {
                        Intent intent = new Intent(MyLibraryPlayer.this, Subtitle_Resolution.class);
                        intent.putExtra("ResolutionFormat", ResolutionFormat);
                        intent.putExtra("ResolutionUrl", ResolutionUrl);
                        intent.putExtra("SubTitleName", SubTitleName);
                        intent.putExtra("SubTitlePath", SubTitlePath);
                        startActivityForResult(intent, 222);
                    }


                } catch (Exception e) {
                }

            }
        });


        if (playerModel.getVideoTitle().trim() != null && !playerModel.getVideoTitle().trim().matches(""))

        {
            videoTitle.setText(playerModel.getVideoTitle().trim());
            videoTitle.setVisibility(View.VISIBLE);
        } else {
            videoTitle.setVisibility(View.GONE);
        }


        if (playerModel.getVideoGenre().trim() != null && !playerModel.getVideoGenre().trim().matches(""))

        {
            GenreTextView.setText(playerModel.getVideoGenre().trim());
            GenreTextView.setVisibility(View.VISIBLE);
        } else {
            GenreTextView.setVisibility(View.GONE);
        }


        if (playerModel.getVideoDuration().trim() != null && !playerModel.getVideoDuration().trim().matches(""))

        {
            videoDurationTextView.setText(playerModel.getVideoDuration().trim());
            videoDurationTextView.setVisibility(View.VISIBLE);
            censor_layout = false;
        } else {
            videoDurationTextView.setVisibility(View.GONE);
        }
        if (playerModel.getCensorRating().trim() != null && !playerModel.getCensorRating().trim().matches("")) {
            if ((playerModel.getCensorRating().trim()).contains("_")) {
                String Data[] = (playerModel.getCensorRating().trim()).split("-");
                videoCensorRatingTextView.setVisibility(View.VISIBLE);
                videoCensorRatingTextView1.setVisibility(View.VISIBLE);
                videoCensorRatingTextView.setText(Data[0]);
                videoCensorRatingTextView1.setText(Data[1]);
                censor_layout = false;
            } else {
                censor_layout = false;
                videoCensorRatingTextView.setVisibility(View.VISIBLE);
                videoCensorRatingTextView1.setVisibility(View.GONE);
                videoCensorRatingTextView.setText(playerModel.getCensorRating().trim());
            }
        } else {
            videoCensorRatingTextView.setVisibility(View.GONE);
            videoCensorRatingTextView1.setVisibility(View.GONE);
        }
        if (playerModel.getCensorRating().trim() != null && playerModel.getCensorRating().trim().equalsIgnoreCase(Util.getTextofLanguage(MyLibraryPlayer.this, Util.NO_DATA, Util.DEFAULT_NO_DATA))) {
            videoCensorRatingTextView.setVisibility(View.GONE);
            videoCensorRatingTextView1.setVisibility(View.GONE);
        }

        if (playerModel.getVideoReleaseDate().trim() != null && !playerModel.getVideoReleaseDate().trim().matches(""))

        {
            videoReleaseDateTextView.setText(playerModel.getVideoReleaseDate().trim());
            videoReleaseDateTextView.setVisibility(View.VISIBLE);
            censor_layout = false;
        } else {
            videoReleaseDateTextView.setVisibility(View.GONE);
        }

        if (censor_layout) {

            ((LinearLayout) findViewById(R.id.durationratingLiearLayout)).setVisibility(View.GONE);
        }
        if (playerModel.getVideoStory().trim() != null && !playerModel.getVideoStory().trim().matches(""))

        {
            story.setText(playerModel.getVideoStory());
            story.setVisibility(View.VISIBLE);
            ResizableCustomView.doResizeTextView(MyLibraryPlayer.this, story, MAX_LINES, languagePreference.getTextofLanguage(VIEW_MORE, DEFAULT_VIEW_MORE), true);

        } else {
            story.setVisibility(View.GONE);
        }

        if (playerModel.isCastCrew() == true)

        {
            videoCastCrewTitleTextView.setVisibility(View.VISIBLE);
        } else {
            videoCastCrewTitleTextView.setVisibility(View.GONE);
        }


        videoCastCrewTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkNetwork(MyLibraryPlayer.this)) {
                    //Will Add Some Data to send
                    Util.call_finish_at_onUserLeaveHint = false;
                    Util.hide_pause = true;
                    ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.VISIBLE);

                    if (emVideoView.isPlaying()) {
                        emVideoView.pause();
                        latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
                        center_play_pause.setImageResource(R.drawable.ic_media_play);
                        mHandler.removeCallbacks(updateTimeTask);
                    }


                    if (center_pause_paly_timer_is_running) {
                        center_pause_paly_timer.cancel();
                        center_pause_paly_timer_is_running = false;
                        Log.v("BIBHU11", "CastAndCrewActivity End_Timer cancel called");


                        subtitle_change_btn.setVisibility(View.INVISIBLE);
                        primary_ll.setVisibility(View.GONE);
                        last_ll.setVisibility(View.GONE);
                        center_play_pause.setVisibility(View.GONE);
                        current_time.setVisibility(View.GONE);
                    }


                    final Intent detailsIntent = new Intent(MyLibraryPlayer.this, CastAndCrewActivity.class);
                    detailsIntent.putExtra("cast_movie_id", movieId.trim());
                    detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(detailsIntent);
                } else {
                    Toast.makeText(getApplicationContext(), Util.getTextofLanguage(MyLibraryPlayer.this, Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }
            }
        });


        player_layout = (RelativeLayout) findViewById(R.id.player_layout);
        player_layout_height = player_layout.getHeight();
        player_layout_width = player_layout.getWidth();

        primary_ll = (LinearLayout) findViewById(R.id.primary_ll);
        last_ll = (LinearLayout) findViewById(R.id.last_ll);
        last_ll = (LinearLayout) findViewById(R.id.last_ll);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);

        ipAddressTextView = (TextView) findViewById(R.id.emailAddressTextView);
        emailAddressTextView = (TextView) findViewById(R.id.ipAddressTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        ipAddressTextView.setVisibility(View.GONE);
        emailAddressTextView.setVisibility(View.GONE);
        dateTextView.setVisibility(View.GONE);

        compress_expand = (ImageView) findViewById(R.id.compress_expand);
        compress_expand.setVisibility(View.GONE);
        back = (ImageButton) findViewById(R.id.back);

        seekBar = (SeekBar) findViewById(R.id.progress);
        center_play_pause = (ImageButton) findViewById(R.id.center_play_pause);

        current_time = (TextView) findViewById(R.id.current_time);
        total_time = (TextView) findViewById(R.id.total_time);
        progressView = (ProgressBar) findViewById(R.id.progress_view);


        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        Util.player_description = false;
        Util.landscape = false;

        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        player_layout.setLayoutParams(l_params);
        compress_expand.setImageResource(R.drawable.ic_media_fullscreen_shrink);
        compress_expand.setVisibility(View.GONE);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        hideSystemUI();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
     /*   Util.player_description = true;


        LinearLayout.LayoutParams params1 = null;
        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            if (MyLibraryPlayer.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
                // showSystemUI();

            } else {
                //   showSystemUI();
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
            }
        } else {
            if (MyLibraryPlayer.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);
                // showSystemUI();
            } else {
                //showSystemUI();
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);
            }
        }
        player_layout.setLayoutParams(params1);*/

        if (content_types_id == 4) {
            seekBar.setEnabled(false);
            seekBar.setProgress(0);
        } else {
            seekBar.setEnabled(true);
            seekBar.setProgress(0);
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Toast.makeText(getApplicationContext(),""+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateTimeTask);
                playerStartPosition = emVideoView.getCurrentPosition();

                // Call New Video Log Api.

                asyncVideoLogDetails = new AsyncVideoLogDetails();
                asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                subtitleText.setText("");
                mHandler.removeCallbacks(updateTimeTask);
                emVideoView.seekTo(seekBar.getProgress());
                current_time.setVisibility(View.VISIBLE);
                current_time.setVisibility(View.GONE);
                showCurrentTime();
                current_time.setVisibility(View.VISIBLE);
                updateProgressBar();

                Log.v("BIBHU11", "stop tracking called");

                // Changed due to New VoideoLogApi

                isFastForward = true;
                playerPreviousPosition = playerStartPosition;

                log_temp_id = "0";
                player_start_time = millisecondsToString(emVideoView.getCurrentPosition());
                playerPosition = player_start_time;

                // ============End=====================//

            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Instant_End_Timer();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Start_Timer();
                }
                return false;
            }
        });

        emVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                    int orientation = display.getRotation();

                    Log.v("PINTU", "CheckAvailabilityOfChromecast called orientation=" + orientation);

                    if (orientation == 1 || orientation == 3) {
                        hideSystemUI();
                    }
                } catch (Exception e) {
                }


                if (Util.hide_pause) {
                    Util.hide_pause = false;
                }

                if (((ProgressBar) findViewById(R.id.progress_view)).getVisibility() == View.VISIBLE) {
                    primary_ll.setVisibility(View.VISIBLE);
                    center_play_pause.setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.GONE);
                    current_time.setVisibility(View.GONE);
                    subtitle_change_btn.setVisibility(View.INVISIBLE);
                    mediaRouteButton.setVisibility(View.INVISIBLE);


                } else {
                    if (primary_ll.getVisibility() == View.VISIBLE) {
                        primary_ll.setVisibility(View.GONE);
                        last_ll.setVisibility(View.GONE);
                        center_play_pause.setVisibility(View.GONE);
                        latest_center_play_pause.setVisibility(View.GONE);
                        current_time.setVisibility(View.GONE);
                        subtitle_change_btn.setVisibility(View.INVISIBLE);
                        mediaRouteButton.setVisibility(View.INVISIBLE);

                        End_Timer();
                    } else {
                        primary_ll.setVisibility(View.VISIBLE);


                        mediaRouteButton.setVisibility(View.VISIBLE);


                        if (isDrm) {
                            if (SubTitlePath.size() > 0) {
                                subtitle_change_btn.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (SubTitlePath.size() > 0 || ResolutionUrl.size() > 0) {
                                subtitle_change_btn.setVisibility(View.VISIBLE);
                            }
                        }



                        last_ll.setVisibility(View.VISIBLE);
                        center_play_pause.setVisibility(View.VISIBLE);
                        latest_center_play_pause.setVisibility(View.VISIBLE);
                        current_time.setVisibility(View.VISIBLE);
                        current_time.setVisibility(View.GONE);
                        showCurrentTime();
                        current_time.setVisibility(View.VISIBLE);
                        Start_Timer();
                    }

                }


            }
        });

        compress_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Write code here

                if (compressed) {
                    compressed = false;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    player_layout.setLayoutParams(params);
                    compress_expand.setImageResource(R.drawable.ic_media_fullscreen_shrink);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        }
                    });
                    Util.player_description = false;
                    Util.landscape = true;
                    hideSystemUI();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {

                    Util.player_description = true;
                    LinearLayout.LayoutParams params1 = null;
                    if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);

                        } else {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
                        }
                    } else {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);

                        } else {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);
                        }
                    }
                    player_layout.setLayoutParams(params1);
                    compressed = true;
                    compress_expand.setImageResource(R.drawable.ic_media_fullscreen_stretch);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        }
                    });
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    showSystemUI();


                }


            }
        });


        center_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Execute_Pause_Play();
            }
        });
        latest_center_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCastSession != null && mCastSession.isConnected()) {
                    if (Util.hide_pause) {
                        Util.hide_pause = false;
                        latest_center_play_pause.setVisibility(View.GONE);
                    }
                    Execute_Pause_Play();

                } else {
                    Execute_Pause_Play();

                }

            }
        });

       /* back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    back.setImageResource(R.drawable.ic_back);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                }
                return false;
            }
        });*/

        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {


                video_prepared = true;

               /* Log.v("SUBHA","played_length"+played_length);
                Log.v("SUBHA","emVideoView.getDuration()"+emVideoView.getDuration());
                int duration=emVideoView.getDuration()/1000;
                int hours = duration / 3600;
                int minutes = (duration / 60) - (hours * 60);
                int seconds = duration - (hours * 3600) - (minutes * 60) ;
                String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                Toast.makeText(getApplicationContext(), "duration is " + duration ,  Toast.LENGTH_LONG).show();*/

                if (playerModel.getPlayPos() >= emVideoView.getDuration() / 1000) {
                    played_length = 0;
                }

                video_completed = false;
                if (progressView != null) {
                    ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.VISIBLE);
                    center_play_pause.setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.GONE);
                }


                try {
                  /*  if (emailIdStr != null && !emailIdStr.equalsIgnoreCase("")) {
                        emailAddressTextView.setVisibility(View.VISIBLE);
                        emailAddressTextView.setText(emailIdStr);
                    } else {
                        emailAddressTextView.setVisibility(View.GONE);
                    }
                    if (ipAddressStr!=null){
                        ipAddressTextView.setVisibility(View.VISIBLE);
                        ipAddressTextView.setText(ipAddressStr);
                    }else{
                        ipAddressTextView.setVisibility(View.GONE);
                    }
                    String date = new SimpleDateFormat("MMMM dd , yyyy").format(new Date());
                    if (date != null && !date.equalsIgnoreCase("")) {
                        dateTextView.setVisibility(View.VISIBLE);
                        dateTextView.setText(date);
                    } else {
                        dateTextView.setVisibility(View.GONE);
                    }*/

                    //video log
                    if (content_types_id == 4) {

                        if (SubTitlePath.size() > 0) {
                            CheckSubTitleParsingType("1");
                            subtitleDisplayHandler = new Handler();
                            subsFetchTask = new SubtitleProcessingTask("1");
                            subsFetchTask.execute();
                        } else {
                            asyncVideoLogDetails = new AsyncVideoLogDetails();
                            asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                        }

                        PreviousUsedDataByApp(false);
                        requestAds(playerModel.getChannel_id());
                        emVideoView.start();
                        updateProgressBar();
                    } else {
                        startTimer();

                        if (played_length > 0) {
                            ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
                            Util.call_finish_at_onUserLeaveHint = false;

                            Intent resumeIntent = new Intent(MyLibraryPlayer.this, ResumePopupActivity.class);
                            resumeIntent.putExtra("activity","MyLibraryPlayer");
                            startActivityForResult(resumeIntent, 1001);
                        } else {

                            PreviousUsedDataByApp(false);
                            requestAds(playerModel.getChannel_id());
                            emVideoView.start();
                            seekBar.setProgress(emVideoView.getCurrentPosition());
                            updateProgressBar();

                            if (SubTitlePath.size() > 0) {

                                CheckSubTitleParsingType("1");
                                subtitleDisplayHandler = new Handler();
                                subsFetchTask = new SubtitleProcessingTask("1");
                                subsFetchTask.execute();
                            } else {
                                asyncVideoLogDetails = new AsyncVideoLogDetails();
                                asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                            }

                        }

                    }
                } catch (Exception e) {
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backCalled();
                mHandler.removeCallbacks(updateTimeTask);
                emVideoView.release();
                finish();
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backCalled();
                mHandler.removeCallbacks(updateTimeTask);
                emVideoView.release();
                finish();
            }
        });
      /*
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backCalled();
               *//* Toast.makeText(MyLibraryPlayer.this, "test", Toast.LENGTH_SHORT).show();
                mHandler.removeCallbacks(updateTimeTask);
                emVideoView.release();
                finish();*//*
            }
        });*/


//commented by me
        //  emVideoView.setVideoURI(Uri.parse(playerModel.getVideoUrl()));
        try {
            /*
             * Initialize the Wasabi Runtime (necessary only once for each
			 * instantiation of the application)
			 *
			 * ** Note: Set Runtime Properties as needed for your environment
			 */
            Runtime.initialize(getDir("wasabi", MODE_PRIVATE).getAbsolutePath());
            /*
             * Personalize the application (acquire DRM keys). MyLibraryPlayer.this is only
			 * necessary once each time the application is freshly installed
			 *
			 * ** Note: personalize() is a blocking call and may take long
			 * enough to complete to trigger ANR (Application Not Responding)
			 * errors. In a production application MyLibraryPlayer.this should be called in a
			 * background thread.
			 */
            if (!Runtime.isPersonalized())
                Runtime.personalize();

        } catch (NullPointerException e) {
            //onBackPressed();
            backCalled();
            return;
        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            //onBackPressed();
            backCalled();
            return;
        }

        try {
            EnumSet<PlaylistProxy.Flags> flags = EnumSet.noneOf(PlaylistProxy.Flags.class);
            playerProxy = new PlaylistProxy(flags, MyLibraryPlayer.this, new Handler());
            playerProxy.start();
        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            //  onBackPressed();
            backCalled();
            return;
        }



        	/*
         * create a playlist proxy url and pass it to the native player
		 */
        try {
            /*
             * Note that the MediaSourceType must be adapted to the stream type
			 * (DASH or HLS). Similarly,
			 * the MediaSourceParams need to be set according to the media type
			 * if MediaSourceType is SINGLE_FILE
			 */

            ContentTypes3 contentType = ContentTypes3.DASH;
            PlaylistProxy.MediaSourceParams params = new PlaylistProxy.MediaSourceParams();
            params.sourceContentType = contentType
                    .getMediaSourceParamsContentType();
            /*
             * if the content has separate audio tracks (eg languages) you may
			 * select one using MediaSourceParams, eg params.language="es";
			 */
            String contentTypeValue = contentType.toString();
            if (playerModel.getVideoUrl().contains(".mpd")) {
                String url = playerProxy.makeUrl(playerModel.getVideoUrl(), PlaylistProxy.MediaSourceType.valueOf((contentTypeValue == "MP4" || contentTypeValue == "HLS" || contentTypeValue == "DASH") ? contentTypeValue : "SINGLE_FILE"), params);
                emVideoView.setVideoURI(Uri.parse(url));

            } else {
                emVideoView.setVideoURI(Uri.parse(playerModel.getVideoUrl()));

            }


        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            //  onBackPressed();
            backCalled();
            return;
        } catch (IllegalArgumentException e) {
            // onBackPressed();
            backCalled();
            e.printStackTrace();
        } catch (SecurityException e) {
            // onBackPressed();
            backCalled();
            e.printStackTrace();
        } catch (IllegalStateException e) {
            //  onBackPressed();
            backCalled();
            e.printStackTrace();
        }


        /*****Offline*****/

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MyLibraryPlayer.this)) {
                    final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    try {
                        Util.call_finish_at_onUserLeaveHint = false;
                        startActivityForResult(intent, 22222);
                    } catch (ActivityNotFoundException e) {
                    }
                } else {

                    if (isDrm) {
                        // This is applicable for DRM content.

                        List_Of_Resolution_Format.clear();
                        List_Of_FileSize.clear();
                        List_Of_Resolution_Url.clear();
                        List_Of_Resolution_Url_Used_For_Download.clear();


                        asynWithdrm = new AsynWithdrm();
                        asynWithdrm.executeOnExecutor(threadPoolExecutor);
                    } else {
                        // This is applicable for NON-DRM contnet.

                        List_Of_Resolution_Url.clear();
                        List_Of_FileSize.clear();
                        if (List_Of_Resolution_Url.size() > 0) {
                            for (int i = 1; i < List_Of_Resolution_Url.size(); i++) {
                                List_Of_Resolution_Url.add(playerModel.ResolutionUrl.get(i));
                            }

                            pDialog_for_gettig_filesize = new ProgressBarHandler(MyLibraryPlayer.this);
                            pDialog_for_gettig_filesize.show();

                            new DetectDownloadingFileSize().execute();
                        } else {
                            new DownloadFileFromURL().execute(playerModel.getVideoUrl());
                        }

                    }


                    if (playerModel.getOfflineUrl().size() > 0) {
                        Download_SubTitle(playerModel.getOfflineUrl().get(0));
                    }
                }

            }
        });


        percentg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MyLibraryPlayer.this, R.style.MyAlertDialogStyle);
                                            dlgAlert.setTitle(Util.getTextofLanguage(MyLibraryPlayer.this, Util.STOP_SAVING_THIS_VIDEO, Util.DEFAULT_STOP_SAVING_THIS_VIDEO));
                                            dlgAlert.setMessage(Util.getTextofLanguage(MyLibraryPlayer.this, Util.YOUR_VIDEO_WONT_BE_SAVED, Util.DEFAULT_YOUR_VIDEO_WONT_BE_SAVED));
                                            dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BTN_KEEP, Util.DEFAULT_BTN_KEEP), null);
                                            dlgAlert.setCancelable(false);
                                            dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BTN_KEEP, Util.DEFAULT_BTN_KEEP),
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();

                                                        }
                                                    });
                                            dlgAlert.setNegativeButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BTN_DISCARD, Util.DEFAULT_BTN_DISCARD), null);
                                            dlgAlert.setCancelable(false);
                                            dlgAlert.setNegativeButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BTN_DISCARD, Util.DEFAULT_BTN_DISCARD),
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                            downloading = false;
                                                            audio = dbHelper.getContact(playerModel.getStreamUniqueId() + emailIdStr);

                                                            if (audio != null) {


                                                                String k = String.valueOf(audio.getDOWNLOADID());

                                                                downloadManager.remove(audio.getDOWNLOADID());
                                                                dbHelper.deleteRecord(audio);

                                                                SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                                                String query = "DELETE FROM " + DBHelper.DOWNLOAD_CONTENT_INFO + " WHERE download_contnet_id = '" + enqueue + "'";
                                                                DB.execSQL(query);

                                                            }


                                                            exoplayerdownloadhandler.post(new Runnable() {
                                                                @Override
                                                                public void run() {


                                                                    Progress.setProgress((int) 0);
                                                                    //percentg.setText(0+"%");
                                                                    percentg.setVisibility(View.GONE);
                                                                    download.setVisibility(View.VISIBLE);


                                                                }
                                                            });

                                                            Toast.makeText(getApplicationContext(), Util.getTextofLanguage(MyLibraryPlayer.this, Util.DOWNLOAD_CANCELLED, Util.DEFAULT_DOWNLOAD_CANCELLED), Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                            dlgAlert.create().show();

                                        }
                                    }
        );

        /*****Offline*****/
    }


    @Override
    public void onErrorNotification(int i, String s) {

    }

    private class AsyncVideoLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = Util.rootUrl().trim() + Util.videoLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("watch_status", watchStatus);
                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoLogId);

                Log.v("BIBHU6", "authToken=" + Util.authTokenStr.trim());
                Log.v("BIBHU6", "user_id=" + userIdStr.trim());
                Log.v("BIBHU6", "ip_address=" + ipAddressStr.trim());
                Log.v("BIBHU6", "movie_id=" + movieId.trim());
                Log.v("BIBHU6", "episode_id=" + episodeId.trim());
                Log.v("BIBHU6", "played_length=" + String.valueOf(playerPosition));
                Log.v("BIBHU6", "watch_status=" + watchStatus);
                Log.v("BIBHU6", "device_type=" + "2");
                Log.v("BIBHU6", "log_id=" + videoLogId);


                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    Log.v("BIBHU", "sending restrict_stream_id============" + restrict_stream_id);
                    httppost.addHeader("is_streaming_restriction", "1");
                    httppost.addHeader("restrict_stream_id", restrict_stream_id);

                    Log.v("BIBHU6", "is_streaming_restriction=" + "1");
                    Log.v("BIBHU6", "restrict_stream_id=" + restrict_stream_id);
                }

                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", "0");
                httppost.addHeader("resume_time", "" + (playerPosition));

                Log.v("BIBHU", "player_start_time===*****************=========" + player_start_time);
                Log.v("BIBHU", "playerPosition======***************8======" + playerPosition);


                Log.v("BIBHU", "played_length============" + (playerPosition - player_start_time));
                Log.v("BIBHU", "log_temp_id============" + log_temp_id);
                Log.v("BIBHU", "resume_time============" + (playerPosition));
                Log.v("BIBHU", "playerPosition============" + playerPosition);
                Log.v("BIBHU", "log_id============" + videoLogId);

                Log.v("BIBHU", "user_id============" + userIdStr.trim());
                Log.v("BIBHU", "movieId.trim()============" + movieId.trim());
                Log.v("BIBHU", "episodeId.trim()============" + episodeId.trim());
                Log.v("BIBHU", "watchStatus============" + watchStatus);
                Log.v("BIBHU", "restrict_stream_id============" + restrict_stream_id);


                //===============End=============================//


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    Log.v("BIBHU", "responseStr of videolog============" + responseStr);


                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoLogId = "0";

                        }

                    });

                } catch (Exception e) {
                    videoLogId = "0";
                    e.printStackTrace();

                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoLogId = myJson.optString("log_id");
                        log_temp_id = myJson.optString("log_temp_id");
                        restrict_stream_id = myJson.optString("restrict_stream_id");
                        player_start_time = playerPosition ;
                        Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);


                    } else {
                        videoLogId = "0";
                        log_temp_id = "0";
                    }

                }

            } catch (Exception e) {
                videoLogId = "0";
                log_temp_id = "0";

            }

            return null;
        }


        protected void onPostExecute(Void result) {

            if (responseStr == null) {
                videoLogId = "0";
                log_temp_id = "0";
            }

            AsyncVideoBufferLogDetails asyncVideoBufferLogDetails = new AsyncVideoBufferLogDetails();
            asyncVideoBufferLogDetails.executeOnExecutor(threadPoolExecutor);
        }

        @Override
        protected void onPreExecute() {
            stoptimertask();
        }


    }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {

            Log.v("BIBHU", "=======================================stoptimertask caled=================================");

            timer.cancel();
            timer = null;
        }

    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                threadHandler.post(new Runnable() {
                    public void run() {
                        if (emVideoView != null) {

                            //  Log.v("BIBHU","===================****====================initializeTimerTask caled===============**==================");


                            int currentPositionStr = millisecondsToString(emVideoView.getCurrentPosition());
                            playerPosition = currentPositionStr;


                            if (isFastForward == true) {
                                isFastForward = false;

                                log_temp_id = "0";


                                int duration = emVideoView.getDuration() / 1000;
                                if (currentPositionStr > 0 && currentPositionStr == duration) {
                                    asyncFFVideoLogDetails = new AsyncFFVideoLogDetails();
                                    watchStatus = "complete";
                                    asyncFFVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                                } else {
                                    asyncFFVideoLogDetails = new AsyncFFVideoLogDetails();
                                    watchStatus = "halfplay";
                                    asyncFFVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                                }

                            } else if (isFastForward == false && currentPositionStr > 0) {

                                playerPreviousPosition = 0;

                                int duration = emVideoView.getDuration() / 1000;
                                if (currentPositionStr > 0 && currentPositionStr == duration) {
                                    asyncVideoLogDetails = new AsyncVideoLogDetails();
                                    watchStatus = "complete";
                                    asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                                } else if (currentPositionStr > 0 && currentPositionStr % 60 == 0) {
                                    asyncVideoLogDetails = new AsyncVideoLogDetails();
                                    watchStatus = "halfplay";
                                    asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

                                }
                            }
                        }
                        //get the current timeStamp
                    }
                });
            }
        };
    }

    private class AsyncFFVideoLogDetails extends AsyncTask<Void, Void, Void> {
        String responseStr;
        int statusCode = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = Util.rootUrl().trim() + Util.videoLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr);
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());

                httppost.addHeader("watch_status", watchStatus);

                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoLogId);

                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {

                    Log.v("BIBHU", "sending restrict_stream_id============" + restrict_stream_id);
                    httppost.addHeader("is_streaming_restriction", "1");
                    httppost.addHeader("restrict_stream_id", restrict_stream_id);
                }

                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", "0");
                httppost.addHeader("resume_time", "" + (playerPosition));


                Log.v("BIBHU11", "played_length============" + (playerPosition - player_start_time));


                Log.v("BIBHU11", "log_temp_id============" + log_temp_id);
                Log.v("BIBHU11", "resume_time============" + (playerPosition));
                Log.v("BIBHU11", "log_id============" + videoLogId);

                //===============End=============================//

                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    Log.v("BIBHU", "responseStr of responseStr============" + responseStr);

                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoLogId = "0";

                        }

                    });

                } catch (IOException e) {
                    videoLogId = "0";

                    e.printStackTrace();
                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoLogId = myJson.optString("log_id");
                        log_temp_id = myJson.optString("log_temp_id");
                        restrict_stream_id = myJson.optString("restrict_stream_id");
                        player_start_time = playerPosition ;
                        Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);
                    } else {
                        videoLogId = "0";
                        log_temp_id = "0";
                    }

                }

            } catch (Exception e) {
                videoLogId = "0";
                log_temp_id = "0";

            }

            return null;
        }


        protected void onPostExecute(Void result) {
            if (responseStr == null) {
                videoLogId = "0";
                log_temp_id = "0";
            }
            AsyncVideoBufferLogDetails asyncVideoBufferLogDetails = new AsyncVideoBufferLogDetails();
            asyncVideoBufferLogDetails.executeOnExecutor(threadPoolExecutor);

        }

        @Override
        protected void onPreExecute() {
            // updateSeekBarThread.stop();
            stoptimertask();

        }


    }

    private int millisecondsToString(int milliseconds) {
        // int seconds = (int) (milliseconds / 1000) % 60 ;
        int seconds = (int) (milliseconds / 1000);

        return seconds;
    }

    @Override
    public void onOrientationChange(int orientation) {


        if (orientation == 90) {

            Util.player_description = false;
            Util.landscape = false;
            compressed = false;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            player_layout.setLayoutParams(params);
            compress_expand.setImageResource(R.drawable.ic_media_fullscreen_shrink);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            });
            hideSystemUI();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            //current_time.setVisibility(View.GONE);
        } else if (orientation == 270) {
            Util.player_description = false;
            Util.landscape = true;

            compressed = false;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            player_layout.setLayoutParams(params);
            compress_expand.setImageResource(R.drawable.ic_media_fullscreen_shrink);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            });
            hideSystemUI();

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //current_time.setVisibility(View.GONE);

            // Do some landscape stuff
        }

        current_time_position_timer();

    }


    private class AsynGetIpAddress extends AsyncTask<Void, Void, Void> {
        String responseStr;


        @Override
        protected Void doInBackground(Void... params) {

            try {

                // Execute HTTP Post Request
                try {
                    URL myurl = new URL(Util.loadIPUrl);
                    HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
                    InputStream ins = con.getInputStream();
                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader in = new BufferedReader(isr);

                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        responseStr = inputLine;
                    }

                    in.close();


                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    ipAddressStr = "";

                } catch (UnsupportedEncodingException e) {

                    ipAddressStr = "";

                } catch (IOException e) {
                    ipAddressStr = "";

                }
                if (responseStr != null) {
                    Object json = new JSONTokener(responseStr).nextValue();
                    if (json instanceof JSONObject) {
                        ipAddressStr = ((JSONObject) json).getString("ip");

                    }
                }

            } catch (Exception e) {
                ipAddressStr = "";
            }
            return null;
        }


        protected void onPostExecute(Void result) {

            if (responseStr == null) {
                ipAddressStr = "";
            }
            return;
        }

        protected void onPreExecute() {

        }
    }


    private void updateProgressBar() {
        mHandler.postDelayed(updateTimeTask, 1000);
    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {


            if (emVideoView.getCurrentPosition() % 2 == 0)
                BufferBandWidth();

            current_played_length = emVideoView.getCurrentPosition();

          /*  if (played_length > 0) {
                emVideoView.seekTo(34000);
                seekBar.setProgress(34000);
            }else {*/
            seekBar.setProgress(emVideoView.getCurrentPosition());
//            }
            seekBar.setMax(emVideoView.getDuration());
            Calcute_Currenttime_With_TotalTime();
            mHandler.postDelayed(this, 1000);

            if (content_types_id != 4) {
                showCurrentTime();
//                seek_label_pos = (((seekBar.getRight() - seekBar.getLeft()) * seekBar.getProgress()) / seekBar.getMax()) + seekBar.getLeft();
            }

            current_matching_time = emVideoView.getCurrentPosition();


            if ((previous_matching_time == current_matching_time) && (current_matching_time < emVideoView.getDuration())) {
                ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.VISIBLE);
                // Added Later By Bibhu

                primary_ll.setVisibility(View.GONE);
                last_ll.setVisibility(View.GONE);
                center_play_pause.setVisibility(View.GONE);
                latest_center_play_pause.setVisibility(View.GONE);
                current_time.setVisibility(View.GONE);
                subtitle_change_btn.setVisibility(View.INVISIBLE);
                mediaRouteButton.setVisibility(View.INVISIBLE);
            } else {

                if (content_types_id == 4) {


                } else {
                    if (current_matching_time >= emVideoView.getDuration()) {
                        mHandler.removeCallbacks(updateTimeTask);

                        AsyncVideoBufferLogDetails asyncVideoBufferLogDetails = new AsyncVideoBufferLogDetails();
                        asyncVideoBufferLogDetails.executeOnExecutor(threadPoolExecutor);
                        seekBar.setProgress(0);
                        current_time.setText("00:00:00");
                        total_time.setText("00:00:00");
                        previous_matching_time = 0;
                        current_matching_time = 0;
                        video_completed = true;
                        backCalled();
                    }
                }


                previous_matching_time = current_matching_time;
                ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);

                if (playerModel.getMidRoll() == 1) {

                    if (adDetails != null && adDetails.length > 0) {
                        for (int i = 0; i < adDetails.length; i++) {
                            Log.v("SUBHA", "SEC" + (int) (TimeUnit.MILLISECONDS.toSeconds(emVideoView.getCurrentPosition())));
                            Log.v("SUBHA", "Integer.parseInt(adDetails[i])" + Integer.parseInt(adDetails[i]));

                            if ((int) (TimeUnit.MILLISECONDS.toSeconds(emVideoView.getCurrentPosition())) > 0 && ((int) (TimeUnit.MILLISECONDS.toSeconds(emVideoView.getCurrentPosition())) == Integer.parseInt(adDetails[i]))) {
                                if (Util.checkNetwork(MyLibraryPlayer.this)) {
                                    //Will Add Some Data to send
                                    Util.call_finish_at_onUserLeaveHint = false;
                                    Util.hide_pause = true;
                                    ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
                                    latest_center_play_pause.setVisibility(View.VISIBLE);

                                    if (emVideoView.isPlaying()) {
                                        emVideoView.pause();
                                        latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
                                        center_play_pause.setImageResource(R.drawable.ic_media_play);
                                        mHandler.removeCallbacks(updateTimeTask);
                                    }
                                    Intent adIntent = new Intent(MyLibraryPlayer.this, AdPlayerActivity.class);
                                    adIntent.putExtra("fromAd", "fromAd");
                                    adIntent.putExtra("PlayerModel", playerModel);
                                    startActivity(adIntent);

                                } else {
                                    Toast.makeText(getApplicationContext(), Util.getTextofLanguage(MyLibraryPlayer.this, Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
    };

    public void Calcute_Currenttime_With_TotalTime() {
        TotalTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(emVideoView.getDuration()),
                TimeUnit.MILLISECONDS.toMinutes(emVideoView.getDuration()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(emVideoView.getDuration())),
                TimeUnit.MILLISECONDS.toSeconds(emVideoView.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(emVideoView.getDuration())));

        Current_Time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(emVideoView.getCurrentPosition()),
                TimeUnit.MILLISECONDS.toMinutes(emVideoView.getCurrentPosition()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(emVideoView.getCurrentPosition())),
                TimeUnit.MILLISECONDS.toSeconds(emVideoView.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(emVideoView.getCurrentPosition())));

        total_time.setText(TotalTime);
        current_time.setText(Current_Time);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (asynGetIpAddress != null) {
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails != null) {
            asyncVideoLogDetails.cancel(true);
        }
        if (asyncFFVideoLogDetails != null) {
            asyncFFVideoLogDetails.cancel(true);
        }
        if (progressView != null && progressView.isShown()) {
            progressView = null;
        }
        if (timer != null) {
            stoptimertask();
            timer = null;
        }

        if (video_completed == false) {

            if (mCastSession != null && mCastSession.isConnected()) {
            } else {
                AsyncResumeVideoLogDetails asyncResumeVideoLogDetails = new AsyncResumeVideoLogDetails();
                asyncResumeVideoLogDetails.executeOnExecutor(threadPoolExecutor);
            }
            return;
        }
        mHandler.removeCallbacks(updateTimeTask);
        if (emVideoView != null) {
            emVideoView.release();
        }
        finish();
        overridePendingTransition(0, 0);
    }

    public void backCalled() {

        if (asynGetIpAddress != null) {
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails != null) {
            asyncVideoLogDetails.cancel(true);
        }
        if (asyncFFVideoLogDetails != null) {
            asyncFFVideoLogDetails.cancel(true);
        }
        if (progressView != null && progressView.isShown()) {
            progressView = null;
        }
        if (timer != null) {
            stoptimertask();
            timer = null;
        }

        if (mCastSession != null && mCastSession.isConnected()) {
        } else {
            AsyncResumeVideoLogDetails asyncResumeVideoLogDetails = new AsyncResumeVideoLogDetails();
            asyncResumeVideoLogDetails.executeOnExecutor(threadPoolExecutor);
        }


        return;

    }

    @Override
    protected void onUserLeaveHint() {

        //if (played_length <= 0) {
        if (asynGetIpAddress != null) {
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails != null) {
            asyncVideoLogDetails.cancel(true);
        }
        if (asyncFFVideoLogDetails != null) {
            asyncFFVideoLogDetails.cancel(true);
        }
        if (progressView != null && progressView.isShown()) {
            progressView = null;
        }
        if (timer != null && Util.call_finish_at_onUserLeaveHint) {
            stoptimertask();
            timer = null;
        }

        if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION) && Util.call_finish_at_onUserLeaveHint) {

            if (mCastSession != null && mCastSession.isConnected()) {
            } else {
                AsyncResumeVideoLogDetails_HomeClicked asyncResumeVideoLogDetails_homeClicked = new AsyncResumeVideoLogDetails_HomeClicked();
                asyncResumeVideoLogDetails_homeClicked.executeOnExecutor(threadPoolExecutor);
            }

        }

        if (Util.call_finish_at_onUserLeaveHint) {

            Log.v("BIBHU6", "finish activity");

            Util.call_finish_at_onUserLeaveHint = true;

            mHandler.removeCallbacks(updateTimeTask);
            if (emVideoView != null) {
                emVideoView.release();
            }

            finish();
            overridePendingTransition(0, 0);
            super.onUserLeaveHint();
        }

    }


    public void Execute_Pause_Play() {


        if (mCastSession != null && mCastSession.isConnected()) {
            if (remoteMediaClient.isPlaying()) {
                remoteMediaClient.pause();
                return;
            }
            if (remoteMediaClient.isPaused()) {
                remoteMediaClient.play();
                return;
            }
        }


        if (emVideoView.isPlaying()) {
            emVideoView.pause();
            latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
            center_play_pause.setImageResource(R.drawable.ic_media_play);
            mHandler.removeCallbacks(updateTimeTask);
        } else {
            if (video_completed) {

                if (content_types_id != 4) {
                    // onBackPressed();
                    backCalled();
                }

            } else {
                PreviousUsedDataByApp(false);
                emVideoView.start();
                latest_center_play_pause.setImageResource(R.drawable.center_ic_media_pause);
                center_play_pause.setImageResource(R.drawable.ic_media_pause);
                mHandler.removeCallbacks(updateTimeTask);
                updateProgressBar();
            }

        }
    }

    public void Start_Timer() {

        End_Timer();
        center_pause_paly_timer = new Timer();
        center_pause_paly_timer_is_running = true;
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                //perform your action here

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        current_time.setVisibility(View.GONE);
                        center_play_pause.setVisibility(View.GONE);
                        latest_center_play_pause.setVisibility(View.GONE);
                        End_Timer();
                    }
                });
            }
        };
        center_pause_paly_timer.schedule(timerTaskObj, 2000, 2000);
    }

    public void End_Timer() {
        if (center_pause_paly_timer_is_running) {
            center_pause_paly_timer.cancel();
            center_pause_paly_timer_is_running = false;

            subtitle_change_btn.setVisibility(View.INVISIBLE);
            mediaRouteButton.setVisibility(View.INVISIBLE);

            primary_ll.setVisibility(View.GONE);
            last_ll.setVisibility(View.GONE);
            center_play_pause.setVisibility(View.GONE);
            latest_center_play_pause.setVisibility(View.GONE);
            current_time.setVisibility(View.GONE);
        }

    }

    public void Instant_End_Timer() {
        if (center_pause_paly_timer_is_running) {
            center_pause_paly_timer.cancel();
            center_pause_paly_timer_is_running = false;
        }

    }

    public void showCurrentTime() {

        current_time.setText(Current_Time);
        current_time_position_timer();

       /* if(seek_label_pos == 0)
        {
            current_time_position_timer();
        }
        else
        {
            seek_label_pos = (((seekBar.getRight() - seekBar.getLeft()) * seekBar.getProgress()) / seekBar.getMax()) + seekBar.getLeft();
            current_time.setX(seek_label_pos - current_time.getWidth() / 2);
        }
       *//* if (progresss <=9)
        {
            current_time.setX(seek_label_pos -6);
        }
        else
        {
            current_time.setX(seek_label_pos - 11);
        }*/


    }

    public void current_time_position_timer() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (content_types_id != 4) {

//                            seek_label_pos = (((seekBar.getRight() - seekBar.getLeft()) * seekBar.getProgress()) / seekBar.getMax()) + seekBar.getLeft();
//                            current_time.setX(seek_label_pos - current_time.getWidth() / 2);

                            current_time.setText(Current_Time);
                            double pourcent = seekBar.getProgress() / (double) seekBar.getMax();
                            int offset = seekBar.getThumbOffset();
                            int seekWidth = seekBar.getWidth();
                            int val = (int) Math.round(pourcent * (seekWidth - 2 * offset));
                            int labelWidth = current_time.getWidth();
                            current_time.setX(offset + seekBar.getX() + val
                                    - Math.round(pourcent * offset)
                                    - Math.round(pourcent * labelWidth / 2));
                            timer.cancel();
                        }
                    }
                });
            }
        }, 0, 100);
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent objEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.v("SUBHA","FHFHFHCALLED");
            return true;
        }
        return super.onKeyUp(keyCode, objEvent);
    }
*/


    private class AsyncResumeVideoLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;
        String watchSt = "halfplay";

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = Util.rootUrl().trim() + Util.videoLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (current_matching_time >= emVideoView.getDuration()) {
                            watchSt = "complete";
                        }

                    }

                });
                httppost.addHeader("watch_status", watchSt);


                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    Log.v("BIBHU", "sending restrict_stream_id============" + restrict_stream_id);

                    httppost.addHeader("is_streaming_restriction", "1");
                    httppost.addHeader("restrict_stream_id", restrict_stream_id);
                    httppost.addHeader("is_active_stream_closed", "1");
                }

                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", "0");
                httppost.addHeader("resume_time", "" + (playerPosition));

                Log.v("BIBHU11", "played_length============" + (playerPosition - player_start_time));
                Log.v("BIBHU11", "log_temp_id============" + log_temp_id);
                Log.v("BIBHU11", "resume_time============" + (playerPosition));
                Log.v("BIBHU11", "log_id============" + videoLogId);

                //===============End=============================//


                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoLogId);


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());
                    Log.v("BIBHU", "responseStr of responseStr============" + responseStr);


                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoLogId = "0";

                        }

                    });

                } catch (IOException e) {
                    videoLogId = "0";

                    e.printStackTrace();
                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoLogId = myJson.optString("log_id");
                        log_temp_id = myJson.optString("log_temp_id");
                        restrict_stream_id = myJson.optString("restrict_stream_id");
                        player_start_time = playerPosition ;
                        Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);
                    } else {
                        videoLogId = "0";
                        log_temp_id = "0";
                    }

                }

            } catch (Exception e) {
                videoLogId = "0";
                log_temp_id = "0";

            }

            return null;
        }


        protected void onPostExecute(Void result) {
         /*   try {
                if (pDialog.isShowing())
                    pDialog.dismiss();
            } catch (IllegalArgumentException ex) {
                videoLogId = "0";
            }*/
            if (responseStr == null) {
                videoLogId = "0";
                log_temp_id = "0";

            }
            mHandler.removeCallbacks(updateTimeTask);
            if (emVideoView != null) {
                emVideoView.release();
            }
            if (video_completed == true) {
                Log.v("SUBHA", "CALLED");
                if (playerModel.getPostRoll() == 1) {
                    Intent adIntent = new Intent(MyLibraryPlayer.this, AdPlayerActivity.class);
                    adIntent.putExtra("PlayerModel", playerModel);
                    adIntent.putExtra("fromAd", "fromAd");
                    startActivity(adIntent);

                    finish();
                    overridePendingTransition(0, 0);
                }
            }
            finish();
            overridePendingTransition(0, 0);
            //startTimer();
            return;


        }

        @Override
        protected void onPreExecute() {
            stoptimertask();
        }
    }

    private class AsyncResumeVideoLogDetails_HomeClicked extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;
        String watchSt = "halfplay";

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = Util.rootUrl().trim() + Util.videoLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (current_matching_time >= emVideoView.getDuration()) {
                            watchSt = "complete";
                        }

                    }

                });

                httppost.addHeader("watch_status", watchSt);


                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    Log.v("BIBHU", "sending restrict_stream_id============" + restrict_stream_id);

                    httppost.addHeader("is_streaming_restriction", "1");
                    httppost.addHeader("restrict_stream_id", restrict_stream_id);
                    httppost.addHeader("is_active_stream_closed", "1");
                }


                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", "0");
                httppost.addHeader("resume_time", "" + (playerPosition));


                Log.v("BIBHU11", "played_length============" + (playerPosition - player_start_time));
                Log.v("BIBHU11", "log_temp_id============" + log_temp_id);
                Log.v("BIBHU11", "resume_time============" + (playerPosition));
                Log.v("BIBHU11", "log_id============" + videoLogId);


                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoLogId);


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());
                    Log.v("BIBHU", "responseStr of responseStr============" + responseStr);


                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoLogId = "0";

                        }

                    });

                } catch (IOException e) {
                    videoLogId = "0";

                    e.printStackTrace();
                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoLogId = myJson.optString("log_id");
                        log_temp_id = myJson.optString("log_temp_id");
                        restrict_stream_id = myJson.optString("restrict_stream_id");
                        player_start_time = playerPosition ;
                        Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);
                    } else {
                        videoLogId = "0";
                        log_temp_id = "0";
                    }

                }

            } catch (Exception e) {
                videoLogId = "0";
                log_temp_id = "0";

            }

            return null;
        }


        protected void onPostExecute(Void result) {
        }

        @Override
        protected void onPreExecute() {

        }


    }

    public void ShowResumeDialog(String Title, String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MyLibraryPlayer.this, R.style.MyAlertDialogStyle);

        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(Title);
        dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 22222) {

            Log.v("BIBHU1111", "requestCode=" + requestCode + "==========resultCode==" + resultCode);
            Util.call_finish_at_onUserLeaveHint = true;

            if (isDrm) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(MyLibraryPlayer.this)) {
                    List_Of_Resolution_Format.clear();
                    List_Of_FileSize.clear();
                    List_Of_Resolution_Url.clear();
                    List_Of_Resolution_Url_Used_For_Download.clear();


                    asynWithdrm = new AsynWithdrm();
                    asynWithdrm.executeOnExecutor(threadPoolExecutor);

                    Log.v("BIBHU1111", "(playerModel.getOfflineUrl()=" + (playerModel.getOfflineUrl().size()));


                    if (playerModel.getOfflineUrl().size() > 0) {
                        Download_SubTitle(playerModel.getOfflineUrl().get(0));
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(MyLibraryPlayer.this)) {
                    // This is applicable for NON-DRM contnet.

                    List_Of_Resolution_Url.clear();
                    List_Of_FileSize.clear();
                    if (List_Of_Resolution_Url.size() > 0) {
                        for (int i = 1; i < List_Of_Resolution_Url.size(); i++) {
                            List_Of_Resolution_Url.add(playerModel.ResolutionUrl.get(i));
                        }

                        pDialog_for_gettig_filesize = new ProgressBarHandler(MyLibraryPlayer.this);
                        pDialog_for_gettig_filesize.show();

                        new DetectDownloadingFileSize().execute();
                    } else {
                        new DownloadFileFromURL().execute(playerModel.getVideoUrl());
                    }
                }
            }

        }


        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                Util.call_finish_at_onUserLeaveHint = true;


                if (data.getStringExtra("yes").equals("1002")) {

                    watchStatus = "halfplay";
                    playerPosition = playerModel.getPlayPos();
                    player_start_time = playerPosition;
                    PreviousUsedDataByApp(false);
                    emVideoView.start();
                    emVideoView.seekTo(played_length);
                    seekBar.setProgress(played_length);
                    updateProgressBar();

                } else {
                    PreviousUsedDataByApp(false);
                    emVideoView.start();
                    seekBar.setProgress(emVideoView.getCurrentPosition());
                    updateProgressBar();
                }


                if (SubTitlePath.size() > 0) {

                    CheckSubTitleParsingType("1");

                    subtitleDisplayHandler = new Handler();
                    subsFetchTask = new SubtitleProcessingTask("1");
                    subsFetchTask.execute();
                } else {
                    asyncVideoLogDetails = new AsyncVideoLogDetails();
                    asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                }

            }
            if (requestCode == 222) {


                if (mCastSession != null && mCastSession.isConnected()) {
                    Util.call_finish_at_onUserLeaveHint = false;
                }


                if (!data.getStringExtra("position").equals("nothing")) {

                    if (data.getStringExtra("position").equals("0")) {
                        // Stop Showing Subtitle
                        if (subtitleDisplayHandler != null)
                            subtitleDisplayHandler.removeCallbacks(subtitleProcessesor);
                        subtitleText.setText("");

                        active_track_index = "";

                        Log.v("BIBHU", "selected trackid===========**" + active_track_index);

                        // check chromecast is connected or not , if connected then remove the active track id

                        if (mCastSession != null && mCastSession.isConnected()) {

                            subtitleText.setText("");
                            remoteMediaClient.setActiveMediaTracks(new long[]{}).setResultCallback(new ResultCallback<RemoteMediaClient.MediaChannelResult>() {
                                @Override
                                public void onResult(@NonNull RemoteMediaClient.MediaChannelResult mediaChannelResult) {
                                    if (!mediaChannelResult.getStatus().isSuccess()) {
                                        Log.v("SUBHA", "Failed with status code:" +
                                                mediaChannelResult.getStatus().getStatusCode());
                                        Toast.makeText(getApplicationContext(), "failed to off subtitle", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    } else {
                        try {
                            CheckSubTitleParsingType(data.getStringExtra("position"));

                            subtitleDisplayHandler = new Handler();
                            subsFetchTask = new SubtitleProcessingTask(data.getStringExtra("position"));
                            subsFetchTask.execute();

                            active_track_index = (Integer.parseInt(data.getStringExtra("position")) - 1) + "";
                            int id = Integer.parseInt(active_track_index);

                            Log.v("BIBHU", " trackid===========" + id);

                            // check chromecast is connected or not , if connected then remove the active track id

                            if (mCastSession != null && mCastSession.isConnected()) {
                                subtitleText.setText("");
                                remoteMediaClient.setActiveMediaTracks(new long[]{id}).setResultCallback(new ResultCallback<RemoteMediaClient.MediaChannelResult>() {
                                    @Override
                                    public void onResult(@NonNull RemoteMediaClient.MediaChannelResult mediaChannelResult) {
                                        if (!mediaChannelResult.getStatus().isSuccess()) {
                                            Log.v("SUBHA", "Failed with status code:" +
                                                    mediaChannelResult.getStatus().getStatusCode());
                                            Toast.makeText(getApplicationContext(), "failed to set subtitle", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }


                        } catch (Exception e) {

                            String data1 = e.toString();
                        }

                    }

                }
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCastContext.getSessionManager().removeSessionManagerListener(mSessionManagerListener, CastSession.class);

        Util.app_is_in_player_context = false;
        Log.v("BIBHU", "***********************************************************************************Ondestory called");

        Util.hide_pause = false;


        if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION) && Util.Call_API_For_Close_Streming) {

            Util.Call_API_For_Close_Streming = false;
            Log.v("BIBHU", "==============Ondestory of Exoplyer called============");

            if (!video_completed_at_chromecast) {
                AsyncResumeVideoLogDetails asyncResumeVideoLogDetails = new AsyncResumeVideoLogDetails();
                asyncResumeVideoLogDetails.executeOnExecutor(threadPoolExecutor);
            }
        }

        try {

            a = castContext.obtainStyledAttributes(null, android.support.v7.mediarouter.R.styleable.MediaRouteButton, android.support.v7.mediarouter.R.attr.mediaRouteButtonStyle, 0);
            drawable = a.getDrawable(android.support.v7.mediarouter.R.styleable.MediaRouteButton_externalRouteEnabledDrawable);
            a.recycle();
            DrawableCompat.setTint(drawable, getResources().getColor(R.color.chromecast_color));

            CastButtonFactory.setUpMediaRouteButton(MyLibraryPlayer.this, mediaRouteButton);
            mediaRouteButton.setRemoteIndicatorDrawable(drawable);

        } catch (Exception e) {
        }
        try {
            if (SelectedUrl != null)
                unregisterReceiver(SelectedUrl);
        } catch (Exception e) {

        }

        Intent intent  = new Intent(MyLibraryPlayer.this, SupportActivity1.class);
        startActivity(intent);
        finish();


    }

    // Added Later By Bibhu For Subtitle Feature.

    public class SubtitleProcessingTask extends AsyncTask<Void, Void, Void> {


        String Subtitle_Path = "";

        public SubtitleProcessingTask(String path) {

            Subtitle_Path = SubTitlePath.get((Integer.parseInt(path) - 1));
            Log.v("BIBHU1", "SubTitlePath==============" + SubTitlePath.get((Integer.parseInt(path) - 1)));
        }

        @Override
        protected void onPreExecute() {
//            subtitleText.setText("Loading subtitles..");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // int count;
            try {

                File myFile = new File(Subtitle_Path);
                InputStream fIn = new FileInputStream(String.valueOf(myFile));


              /* InputStream stream = getResources().openRawResource(
                        R.raw.subtitle);*/

                if (callWithoutCaption) {
                    Log.v("BIBHU1", "SubTitlePath==============callWithoutCaption");
                    FormatSRT_WithoutCaption formatSRT = new FormatSRT_WithoutCaption();
                    srt = formatSRT.parseFile("sample", fIn);
                } else {
                    Log.v("BIBHU1", "SubTitlePath==============callWithCaption");

                    FormatSRT formatSRT = new FormatSRT();
                    srt = formatSRT.parseFile("sample", fIn);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (null != srt) {
                subtitleText.setText("");
                subtitleDisplayHandler.post(subtitleProcessesor);
//                Toast.makeText(getApplicationContext(), "subtitles loaded!!",Toast.LENGTH_SHORT).show();
            }

            asyncVideoLogDetails = new AsyncVideoLogDetails();
            asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

            super.onPostExecute(result);
        }
    }

    public void onTimedText(Caption text) {
        if (text == null) {
            subtitleText.setVisibility(View.INVISIBLE);
            return;
        }

        Typeface videoGenreTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_regular));
        subtitleText.setTypeface(videoGenreTextViewTypeface);
        subtitleText.setText(Html.fromHtml(text.content));
        subtitleText.setVisibility(View.VISIBLE);

    }

    @Override
    public void finish() {
        cleanUp();
        super.finish();
    }

    private void cleanUp() {
        if (subtitleDisplayHandler != null) {
            subtitleDisplayHandler.removeCallbacks(subtitleProcessesor);
        }

    }

    @Override
    protected void onPause() {

        stopOrientationLiastener = true;

        if (CheckAvailabilityOfChromecast != null)
            CheckAvailabilityOfChromecast.cancel();

      /*  if (mAdsManager != null && mIsAdDisplayed) {
            mAdsManager.pause();
        } else {
            Util.call_finish_at_onUserLeaveHint = false;
            emVideoView.pause();
        }*/
        super.onPause();
    }

    private Runnable subtitleProcessesor = new Runnable() {

        @Override
        public void run() {
            if (emVideoView != null && emVideoView.isPlaying()) {
                int currentPos = emVideoView.getCurrentPosition();
                Collection<Caption> subtitles = srt.captions.values();
                for (Caption caption : subtitles) {
                    if (currentPos >= caption.start.mseconds
                            && currentPos <= caption.end.mseconds) {
                        onTimedText(caption);
                        break;
                    } else if (currentPos > caption.end.mseconds) {
                        onTimedText(null);
                    }
                }
            }
            subtitleDisplayHandler.postDelayed(this, 100);
        }
    };

    public void CheckSubTitleParsingType(String path) {

        String Subtitle_Path = SubTitlePath.get((Integer.parseInt(path) - 1));


        callWithoutCaption = true;

        File myFile = new File(Subtitle_Path);
        BufferedReader test_br = null;
        InputStream stream = null;
        InputStreamReader in = null;
        try {
            stream = new FileInputStream(String.valueOf(myFile));
            in = new InputStreamReader(stream);
            test_br = new BufferedReader(in);

        } catch (Exception e) {
            e.printStackTrace();
        }

        int testinglinecounter = 1;
        int captionNumber = 1;


        String TestingLine = null;
        try {
            TestingLine = test_br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (testinglinecounter < 6) {
            try {

                if (Integer.parseInt(TestingLine.toString().trim()) == captionNumber) {
                    callWithoutCaption = false;
                    testinglinecounter = 6;
                }
            } catch (Exception e) {
                try {
                    TestingLine = test_br.readLine();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                testinglinecounter++;
            }
        }
    }

    private void hideSystemUI() {
        story.setText("");
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        story.setText(playerModel.getVideoStory());
        ResizableCustomView.doResizeTextView(MyLibraryPlayer.this, story, MAX_LINES, languagePreference.getTextofLanguage(VIEW_MORE, DEFAULT_VIEW_MORE), true);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        );
    }


    // Added for Bandwidth Log

    public void BufferBandWidth() {
        DataAsynTask dataAsynTask = new DataAsynTask();
        dataAsynTask.execute();
    }

    private class DataAsynTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            try {
//                Log.v("BIBHU", "BufferBandWidth==============called====");

                long total = 0;
                PackageManager pm = getPackageManager();
                List<PackageInfo> listPackages = pm.getInstalledPackages(0);
                for (PackageInfo pi : listPackages) {
                    String appName = (String) pi.applicationInfo.loadLabel(pm);
                    if (appName != null && appName.trim().equals(getResources().getString(R.string.app_name))) {
                        int uid = pi.applicationInfo.uid;
                        total = (TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidTxBytes(uid)) / 1024;

                        CurrentUsedData = total - PreviousUsedData;
                        CurrentUsedData = CurrentUsedData - (DataUsedByDownloadContent() - PreviousUsedData_By_DownloadContent);

                        Log.v("BIBHU", "Current_total_UsedData==================" + total + " KB");
                        Log.v("BIBHU", "CurrentUsedData==================" + CurrentUsedData + " KB");

                    }
                }
            } catch (Exception e) {

            }

            return null;
        }
    }

    public void PreviousUsedDataByApp(boolean status) {

        try {

            long prev_data = 0;
            PackageManager pm = getPackageManager();
            List<PackageInfo> listPackages = pm.getInstalledPackages(0);
            for (PackageInfo pi : listPackages) {
                String appName = (String) pi.applicationInfo.loadLabel(pm);
                if (appName != null && appName.trim().equals(getResources().getString(R.string.app_name))) {
                    int uid = pi.applicationInfo.uid;
                    prev_data = (TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidTxBytes(uid)) / 1024;

                    if (status) {
                        PreviousUsedData = prev_data;
                        Log.v("BIBHU", "PreviousUsedDataByApp  true===========================" + (appName + " : " + PreviousUsedData + "KB"));

                    } else {
                        Log.v("BIBHU", "*************** false===========================prev_data= " + prev_data + " KB==========CurrentUsedData= " + CurrentUsedData + " KB");


                        PreviousUsedData = ((prev_data - PreviousUsedData) - CurrentUsedData) + PreviousUsedData;
                        Log.v("BIBHU", "PreviousUsedDataByApp false===========================" + (appName + " : " + PreviousUsedData + "KB"));

                    }

                }
            }

        } catch (Exception e) {

        }

    }

    public long DataUsedByDownloadContent() {

        try {

            SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = DB.rawQuery("SELECT " + DBHelper.COLUMN_DOWNLOADID + " FROM " + DBHelper.TABLE_NAME + " ", null);
            int count = cursor.getCount();

            long Total = 0;

            if (count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        DownloadManager downloadManager1 = (DownloadManager) MyLibraryPlayer.this.getSystemService(DOWNLOAD_SERVICE);
                        DownloadManager.Query download_id_query = new DownloadManager.Query();
                        download_id_query.setFilterById(Long.parseLong(cursor.getString(0).trim())); //filter by id which you have receieved when reqesting download from download manager
                        Cursor id_cursor = downloadManager1.query(download_id_query);


                        if (id_cursor != null && id_cursor.getCount() > 0) {
                            if (id_cursor.moveToFirst()) {
                                int columnIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                                int status = id_cursor.getInt(columnIndex);

                                int sizeIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                                int downloadedIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                                long size = id_cursor.getInt(sizeIndex);
                                long downloaded = id_cursor.getInt(downloadedIndex);

                                Total = Total + downloaded / 1024;
                            }
                        }

                        Log.v("BIBHU11", "  TotalUsedData Download size============" + Total + "KB");

                    } while (cursor.moveToNext());
                }
                return Total;
            } else {
                return Total;
            }
        } catch (Exception e) {
            return 0;
        }
    }


    private class AsyncVideoBufferLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = Util.rootUrl().trim() + Util.bufferLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr);
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoBufferLogId);
                httppost.addHeader("resolution", resolution.trim());
                httppost.addHeader("start_time", String.valueOf(playerPosition));
                httppost.addHeader("end_time", String.valueOf(playerPosition));
                httppost.addHeader("log_unique_id", videoBufferLogUniqueId);
                httppost.addHeader("location", Location);
                httppost.addHeader("video_type", "mped_dash");

                if (videoBufferLogUniqueId.equals("0"))
                    httppost.addHeader("totalBandwidth", "0");
                else if (isDrm)
                    httppost.addHeader("totalBandwidth", "" + (CurrentUsedData + DataUsedByChrmoeCast));
                else
                    httppost.addHeader("totalBandwidth", "" + CurrentUsedData);


                Log.v("BIBHU", "Response of the bufferlog totalBandwidth======#############=" + (CurrentUsedData + DataUsedByChrmoeCast));
                Log.v("BIBHU", "Response of the bufferlog CurrentUsedData======#############=" + CurrentUsedData);
                Log.v("BIBHU", "Response of the bufferlog DataUsedByChrmoeCast======#############=" + DataUsedByChrmoeCast);


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    Log.v("BIBHU", "Response of the bufferlog =" + responseStr);

                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoBufferLogId = "0";
                            videoBufferLogUniqueId = "0";
                            Location = "0";
                        }
                    });

                } catch (IOException e) {
                    videoBufferLogId = "0";
                    videoBufferLogUniqueId = "0";
                    Location = "0";
                    e.printStackTrace();
                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoBufferLogId = myJson.optString("log_id");
                        videoBufferLogUniqueId = myJson.optString("log_unique_id");
                        Location = myJson.optString("location");

                    } else {
                        videoBufferLogId = "0";
                        videoBufferLogUniqueId = "0";
                        Location = "0";
                    }
                }
            } catch (Exception e) {
                videoBufferLogId = "0";
                videoBufferLogUniqueId = "0";
                Location = "0";
            }

            return null;
        }


        protected void onPostExecute(Void result) {

            if (responseStr == null) {

                videoBufferLogId = "0";
                videoBufferLogUniqueId = "0";
                Location = "0";
            }
            if (!watchStatus.equals("complete"))
                startTimer();

            return;
        }

        @Override
        protected void onPreExecute() {

        }
    }


    /*****offline  *****/

    private class AsynWithdrm extends AsyncTask<Void, Void, Void> {
        ProgressBarHandler pDialog;
        int responseCode;
        // String loginHistoryIdStr = loginPref.getString("PREFS_LOGIN_HISTORYID_KEY", null);
        String responseStr;

        @Override
        protected Void doInBackground(Void... params) {


            String urlRouteList = Util.rootUrl().trim() + Util.morlineBB.trim();
            //String urlRouteList ="https://sonydadc.muvi.com/rest/getMarlinBBOffline";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr);
                httppost.addHeader("stream_unique_id", playerModel.getStreamUniqueId());


                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());


                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject myJson = null;
                if (responseStr != null) {
                    myJson = new JSONObject(responseStr);

                    responseCode = Integer.parseInt(myJson.optString("code"));
                }
                JSONObject mainJson = null;
                if (responseCode >= 0) {
                    if (responseCode == 200) {
                        Log.v("SUBHA", "" + responseCode);
                        mainJson = myJson.getJSONObject("data");

                        if ((mainJson.has("file")) && mainJson.getString("file").trim() != null && !mainJson.getString("file").trim().isEmpty() && !mainJson.getString("file").trim().equals("null") && !mainJson.getString("file").trim().matches("")) {
                            mlvfile = mainJson.getString("file");

                            Log.v("SUBHA", mlvfile);
                        } else {
                            mlvfile = Util.getTextofLanguage(MyLibraryPlayer.this, Util.NO_DATA, Util.DEFAULT_NO_DATA);
                        }

                        if ((mainJson.has("token")) && mainJson.getString("token").trim() != null && !mainJson.getString("token").trim().isEmpty() && !mainJson.getString("token").trim().equals("null") && !mainJson.getString("token").trim().matches("")) {
                            token = mainJson.getString("token");
                            Log.v("SUBHA", "token" + token);

                        } else {
                            token = Util.getTextofLanguage(MyLibraryPlayer.this, Util.NO_DATA, Util.DEFAULT_NO_DATA);
                        }


                        if ((mainJson.has("multiple_resolution")) && mainJson.getString("multiple_resolution").trim() != null && !mainJson.getString("multiple_resolution").trim().isEmpty() && !mainJson.getString("multiple_resolution").trim().equals("null") && !mainJson.getString("multiple_resolution").trim().matches("")) {
                            JSONArray jsonArray = mainJson.optJSONArray("multiple_resolution");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                if (jsonArray.getJSONObject(i).optString("resolution").trim().contains("BEST"))
                                    List_Of_Resolution_Format.add(jsonArray.getJSONObject(i).optString("resolution"));
                                else
                                    List_Of_Resolution_Format.add(jsonArray.getJSONObject(i).optString("resolution") + "p");

                                List_Of_Resolution_Url.add(jsonArray.getJSONObject(i).optString("url"));
                                List_Of_Resolution_Url_Used_For_Download.add(jsonArray.getJSONObject(i).optString("url"));

                                Log.v("BIBHU1", "resolution = " + jsonArray.getJSONObject(i).optString("resolution"));
                                Log.v("BIBHU1", "url = " + jsonArray.getJSONObject(i).optString("url"));
                            }

                            Collections.reverse(List_Of_Resolution_Format);
                            Collections.reverse(List_Of_Resolution_Url);
                            Collections.reverse(List_Of_Resolution_Url_Used_For_Download);

                        }
                        //=======================End====================//

                    }
                } else {
                    responseStr = "0";
                }

            } catch (Exception e) {
                responseCode = 0;
            }

            int count;
            InputStream is = new ByteArrayInputStream(token.getBytes());
            InputStream inputs = new BufferedInputStream(is, 8192);
            Log.v("SUBHA", "pathh" + token);
            File root = Environment.getExternalStorageDirectory();
            mediaStorageDir = new File(root + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/TOKEN", "");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            }

            licensetoken = mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".xml";
            OutputStream output = null;
            try {
                output = new FileOutputStream(licensetoken);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.v("SUBHA", "pathh" + licensetoken);
            byte data[] = new byte[1024];

            long total = 0;

            try {
                while ((count = inputs.read(data)) != -1) {
                    total += count;
                    Log.v("SUBHA", "Lrngth" + total);

                    output.write(data, 0, count);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                output.flush();
                output.close();


                inputs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void result) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;

                }
                Log.d("sanji", playerModel.getStreamUniqueId());


                //MyLibraryPlayer.this portion is changed later because of multiple download option.

                if (List_Of_Resolution_Url.size() > 0) {
                    pDialog_for_gettig_filesize = new ProgressBarHandler(MyLibraryPlayer.this);
                    pDialog_for_gettig_filesize.show();

                    new DetectDownloadingFileSize().execute();
                } else {
                    new DownloadFileFromURL().execute(mlvfile);

                }


            } catch (IllegalArgumentException ex) {
                Toast.makeText(MyLibraryPlayer.this, Util.getTextofLanguage(MyLibraryPlayer.this, Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressBarHandler(MyLibraryPlayer.this);
            pDialog.show();


        }
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        ProgressBarHandler pDialog;
        int responseCode;
        String responseStr;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressBarHandler(MyLibraryPlayer.this);
            pDialog.show();

        }

        /**
         * Downloading file in background thread
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {
            int count;

            try {

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(f_url[0]);
                HttpResponse execute = client.execute(httpGet);
                float size = (Float.parseFloat("" + execute.getEntity().getContentLength()) / 1024) / 1024;
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                size = Float.valueOf(decimalFormat.format(size));
                lengthfile = (int) size;


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //===========

            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {

            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                }


                String lengh = String.valueOf(lengthfile);

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MyLibraryPlayer.this, R.style.MyAlertDialogStyle);
                dlgAlert.setTitle(Util.getTextofLanguage(MyLibraryPlayer.this, Util.WANT_TO_DOWNLOAD, Util.DEFAULT_WANT_TO_DOWNLOAD));
                dlgAlert.setMessage(playerModel.getVideoTitle() + " " + "(" + lengh + "MB)");
                dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.DOWNLOAD_BUTTON_TITLE, Util.DEFAULT_DOWNLOAD_BUTTON_TITLE), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.DOWNLOAD_BUTTON_TITLE, Util.DEFAULT_DOWNLOAD_BUTTON_TITLE),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                downloading = true;

                                int currentApiVersion = Build.VERSION.SDK_INT;
                                if (currentApiVersion >= Build.VERSION_CODES.M) {
                                    requestStoragePermission();
                                } else {
                                    downloadFile(true);
                                }

                            }
                        });
                dlgAlert.setNegativeButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.CANCEL_BUTTON, Util.DEFAULT_CANCEL_BUTTON), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setNegativeButton(Util.getTextofLanguage(MyLibraryPlayer.this, Util.CANCEL_BUTTON, Util.DEFAULT_CANCEL_BUTTON),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                dlgAlert.create().show();


            } catch (IllegalArgumentException ex) {
                Toast.makeText(MyLibraryPlayer.this, Util.getTextofLanguage(MyLibraryPlayer.this, Util.SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }


        }
    }

    public void checkDownLoadStatusFromDownloadManager1(final ContactModel1 model, final boolean CallAccessPeriodApi) {

        if (model.getDOWNLOADID() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    downloading = true;
                    //  Util.downloadprogress=0;
                    int bytes_downloaded = 0;
                    int bytes_total = 0;
                    downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    while (downloading) {


                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(model.getDOWNLOADID()); //filter by id which you have receieved when reqesting download from download manager
                        Cursor cursor = downloadManager.query(q);


                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                                int status = cursor.getInt(columnIndex);
                                if (status == DownloadManager.STATUS_SUCCESSFUL) {

                                    model.setDSTATUS(1);
                                    dbHelper.updateRecord(model);
                                    downloading = false;

                                    Intent intent = new Intent("NewVodeoAvailable");
                                    sendBroadcast(intent);

                                    SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                    String query1 = "UPDATE " + DBHelper.DOWNLOAD_CONTENT_INFO + " SET download_status = '1'" +
                                            " WHERE email = '" + emailIdStr + "' AND download_contnet_id = '" + model.getDOWNLOADID() + "'";
                                    DB.execSQL(query1);

                                    if (isDrm) {
                                        try {
                                            String licenseAcquisitionToken = getActionTokenFromStorage(model.getToken());
                                            com.intertrust.wasabi.jni.Runtime.processServiceToken(licenseAcquisitionToken);

                                            EnumSet<PlaylistProxy.Flags> flags = EnumSet.noneOf(PlaylistProxy.Flags.class);
                                            playerProxy = new PlaylistProxy(flags, MyLibraryPlayer.this, new Handler());
                                            playerProxy.start();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (CallAccessPeriodApi) {
                                        // Call API to get Access Period and Watch period of download content...
                                        new AsyncWatchAccessDetails().execute("" + model.getDOWNLOADID());
                                    }
                                    // Have to unComment


                                } else if (status == DownloadManager.STATUS_FAILED) {
                                    // 1. process for download fail.
                                    model.setDSTATUS(0);

                                    SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                                    String query1 = "UPDATE " + DBHelper.DOWNLOAD_CONTENT_INFO + " SET download_status = '0'" +
                                            " WHERE email = '" + emailIdStr + "' AND download_contnet_id = '" + model.getDOWNLOADID() + "'";
                                    DB.execSQL(query1);

                                } else if ((status == DownloadManager.STATUS_PAUSED) ||
                                        (status == DownloadManager.STATUS_RUNNING)) {
                                    model.setDSTATUS(2);

                                } else if (status == DownloadManager.STATUS_PENDING) {
                                    //Not handling now
                                }
                                int sizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                                int downloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                                long size = cursor.getInt(sizeIndex);
                                long downloaded = cursor.getInt(downloadedIndex);
                                double progress = 0.0;
                                if (size != -1) progress = downloaded * 100.0 / size;
                                // At MyLibraryPlayer.this point you have the progress as a percentage.
                                model.setProgress((int) progress);
                                //Util.downloadprogress=(int) progress;

                                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
//                                    downloading = false;
//                                    download_layout.setVisibility(View.GONE);
//                                    writefilepath();
//                                    String path=Environment.getExternalStorageDirectory() + "/WITHDRM/"+fname;
//                                    String fileNameWithOutExt = FilenameUtils.removeExtension(fname);
//                                    String path1 = Environment.getExternalStorageDirectory() + "/Android/data/"+getApplicationContext().getPackageName().trim()+"/WITHDRM/" + playerModel.getVideoTitle().trim() + "-1." + "mlv";
//                                    File file = new File(path1);
//                                    if (file != null && file.exists()) {
//                                        file.delete();
//                                    }

                                }


                            }
                        } else {
                            // model.setDSTATUS(3);
                        }


//

                        runOnUiThread(new Runnable() {
                            //
                            @Override
                            public void run() {


                                download.setVisibility(View.GONE);
                                percentg.setVisibility(View.VISIBLE);
                                Progress.setProgress(0);

                                Progress.setProgress((int) model.getProgress());
                                percentg.setText(model.getProgress() + "%");
//
                                if (model.getProgress() == 100) {

                                    //writefilepath();
//                                dbHelper.deleteRecord(audio);
                                    download_layout.setVisibility(View.GONE);
                                }

                            }
                        });

                        // Log.d(Constants.MAIN_VIEW_ACTIVITY, statusMessage(cursor));
                        cursor.close();
                    }


                }
            }).start();


        }

    }


    private void downloadFile(boolean singlefile) {

        DownloadManager.Request request;
        if (singlefile) {
            selected_download_format = 0;
            if (isDrm)
                request = new DownloadManager.Request(Uri.parse(mlvfile));
            else
                request = new DownloadManager.Request(Uri.parse(Util.dataModel.getVideoUrl()));
        } else {
            if (isDrm)
                request = new DownloadManager.Request(Uri.parse(List_Of_Resolution_Url_Used_For_Download.get(selected_download_format)));
            else
                request = new DownloadManager.Request(Uri.parse(ResolutionUrl.get(selected_download_format + 1)));
            selected_download_format = 0;
        }


        request.setTitle(playerModel.getVideoTitle());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String timestamp = "";
        if (isDrm) {
            //Get download file name
            fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(mlvfile);
            timestamp = System.currentTimeMillis() + ".mlv";
            //Save file to destination folder
            request.setDestinationInExternalPublicDir("Android/data/" + getApplicationContext().getPackageName().trim() + "/WITHDRM", timestamp);
        } else {
            //Get download file name
            fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(Util.dataModel.getVideoUrl());
            timestamp = System.currentTimeMillis() + ".exo";
            //Save file to destination folder
            request.setDestinationInExternalPublicDir("Android/data/" + getApplicationContext().getPackageName().trim() + "/WITHOUT_DRM", timestamp);

        }

        enqueue = downloadManager.enqueue(request);

        download.setVisibility(View.GONE);
        percentg.setVisibility(View.VISIBLE);
        Progress.setProgress(0);

        ContactModel1 contactModel1 = new ContactModel1();
        contactModel1.setMUVIID(playerModel.getVideoTitle());
        contactModel1.setDOWNLOADID((int) enqueue);
        contactModel1.setProgress(0);
        contactModel1.setUSERNAME(emailIdStr);
        contactModel1.setUniqueId(playerModel.getStreamUniqueId() + emailIdStr);
        contactModel1.setDSTATUS(2);
        contactModel1.setPoster(playerModel.getPosterImageId().trim());


        if (isDrm) {
            contactModel1.setToken(licensetoken);
            contactModel1.setPath(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/WITHDRM/" + timestamp);
        } else {
            contactModel1.setToken(fileExtenstion);
            contactModel1.setPath(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/WITHOUT_DRM/" + timestamp);
        }

        contactModel1.setContentid(String.valueOf(playerModel.getContentTypesId()));
        contactModel1.setGenere(playerModel.getVideoGenre().trim());
        contactModel1.setMuviid(playerModel.getMovieUniqueId().trim());
        contactModel1.setDuration(playerModel.getVideoDuration().trim());
        dbHelper.insertRecord(contactModel1);

        Log.d("BIBHU", emailIdStr);


        audio = dbHelper.getContact(playerModel.getStreamUniqueId() + emailIdStr);
        if (audio != null) {
            if (audio.getUSERNAME().trim().equals(emailIdStr.trim())) {
                checkDownLoadStatusFromDownloadManager1(audio, true);
            }
        }


        SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
        String query1 = "INSERT INTO " + DBHelper.DOWNLOAD_CONTENT_INFO + "(download_contnet_id,log_id,authtoken,email," +
                "ipaddress,movie_id,episode_id,device_type,download_status,server_sending_final_status) VALUES" +
                "('" + enqueue + "','0','" + Util.authTokenStr.trim() + "','" + emailIdStr.trim() + "','" + ipAddressStr + "'," +
                "'" + playerModel.getMovieUniqueId().trim() + "','" + playerModel.getStreamUniqueId().trim() + "'," +
                "'" + 2 + "','2','0')";

        DB.execSQL(query1);

        //---------------------- End ---------------------------------//

        // Send BroadCast to start sending Download content bandwidth .

        Intent intent = new Intent("BnadwidthLog");
        sendBroadcast(intent);

        //---------------------- End ---------------------------------//

        // Have to unComment

        // MyLibraryPlayer.this code is only responsible for Access period and Watch Period feature on Download Contnet
        Cursor cursor = DB.rawQuery("SELECT * FROM " + DBHelper.WATCH_ACCESS_INFO + "" +
                " WHERE email = '" + emailIdStr.trim() + "' AND stream_unique_id = '" + playerModel.getStreamUniqueId() + "'", null);

        if (cursor.getCount() > 0) {
            String query = "UPDATE " + DBHelper.WATCH_ACCESS_INFO + " SET download_id = '" + enqueue + "' , " +
                    "stream_unique_id = '" + playerModel.getStreamUniqueId() + "',initial_played_time = '0'," +
                    "updated_server_current_time = '0' WHERE email = '" + emailIdStr.trim() + "' AND stream_unique_id = '" + playerModel.getStreamUniqueId() + "'";
            DB.execSQL(query);

            Log.v("BIBHU1234", "update called");

        } else {
            String query = "INSERT INTO " + DBHelper.WATCH_ACCESS_INFO + " (download_id , stream_unique_id , initial_played_time , updated_server_current_time,email) VALUES" +
                    " ('" + enqueue + "','" + playerModel.getStreamUniqueId() + "','0','0','" + emailIdStr.trim() + "')";
            DB.execSQL(query);

            Log.v("BIBHU1234", "insert called");

        }


        //=================================End=======================================================//


    }

    private void requestStoragePermission() {
        if (ActivityCompat.checkSelfPermission(MyLibraryPlayer.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyLibraryPlayer.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } else {

            if (List_Of_Resolution_Url_Used_For_Download.size() > 0) {
                downloadFile(false);
            } else {
                downloadFile(true);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

               /* if(List_Of_Resolution_Url_Used_For_Download.size()>0)
                {
                    downloadFile(false);
                }else
                {
                    downloadFile(true);
                }*/

            } else {
                Toast.makeText(MyLibraryPlayer.this, Util.getTextofLanguage(MyLibraryPlayer.this, Util.DOWNLOAD_INTERRUPTED, Util.DEFAULT_DOWNLOAD_INTERRUPTED), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL_Offline().execute(Url);
    }

    class DownloadFileFromURL_Offline extends AsyncTask<String, String, String> {

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
                mediaStorageDir1 = new File(root + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList_Offline/", "");

                if (!mediaStorageDir1.exists()) {
                    if (!mediaStorageDir1.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                }

                SubtitleModel subtitleModel = new SubtitleModel();
                subtitleModel.setUID(playerModel.getStreamUniqueId() + emailIdStr);
                subtitleModel.setLanguage(playerModel.getOfflineLanguage().get(0));
                String filename = mediaStorageDir1.getAbsolutePath() + "/" + System.currentTimeMillis() + ".vtt";
                subtitleModel.setPath(filename);

                Log.v("BIBHU3", "SubTitleName============" + filename);

                long rowId = dbHelper.insertRecordSubtittel(subtitleModel);
                Log.v("BIBHU3", "rowId============" + rowId + "sub id ::" + subtitleModel.getUID());

                playerModel.getOfflineLanguage().remove(0);


                OutputStream output = new FileOutputStream(filename);

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
                Log.v("BIBHU3", "error===========" + e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String file_url) {

            playerModel.getOfflineUrl().remove(0);
            if (playerModel.getOfflineUrl().size() > 0) {
                Download_SubTitle(playerModel.getOfflineUrl().get(0).trim());
            }

        }
    }

    /*****offline *****/


    // MyLibraryPlayer.this added later for chromecast//
    private void setupCastListener() {
        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded(CastSession session, int error) {

                onApplicationDisconnected();
                //mSessionManagerListener  = null;
                mCastSession = null;


                Util.call_finish_at_onUserLeaveHint = true;
                latest_center_play_pause.setEnabled(true);
                emVideoView.setEnabled(true);

                startTimer();
                PreviousUsedDataByApp(false);
                emVideoView.start();

                if (cast_disconnected_position != 0) {

                    Log.v("BIBHU2", "onSessionEnded===and video log called");

                    emVideoView.seekTo((int) cast_disconnected_position);
                    log_temp_id = "0";
                    player_start_time = millisecondsToString((int) cast_disconnected_position);
                    playerPosition = player_start_time;

                    // Call video log here

                    asyncVideoLogDetails = new AsyncVideoLogDetails();
                    asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                }

                latest_center_play_pause.setImageResource(R.drawable.center_ic_media_pause);
                center_play_pause.setImageResource(R.drawable.ic_media_pause);
                latest_center_play_pause.setVisibility(View.GONE);
                mHandler.removeCallbacks(updateTimeTask);
                updateProgressBar();

            }


            @Override
            public void onSessionResumed(CastSession session, boolean wasSuspended) {
                onApplicationConnected(session);
                mCastSession = session;

                Log.v("BIBHU2", "onSessionResumed");
            }

            @Override
            public void onSessionResumeFailed(CastSession session, int error) {
                onApplicationDisconnected();

                Log.v("BIBHU2", "onSessionResumeFailed");
            }

            @Override
            public void onSessionStarted(CastSession session, String sessionId) {
                onApplicationConnected(session);
                mCastSession = session;
            }

            @Override
            public void onSessionStartFailed(CastSession session, int error) {
                onApplicationDisconnected();

                Log.v("BIBHU2", "onSessionStartFailed");
            }

            @Override
            public void onSessionStarting(CastSession session) {
                Log.v("BIBHU2", "onSessionStarting===cast connecting");
            }

            @Override
            public void onSessionEnding(CastSession session) {


                cast_disconnected_position = session.getRemoteMediaClient().getApproximateStreamPosition();

                if (isDrm) {
                    DataUsedByChrmoeCast = Current_Sesion_DataUsedByChrmoeCast + DataUsedByChrmoeCast;
                    Current_Sesion_DataUsedByChrmoeCast = 0;
                }

                // ExoPlayerActivity.this is done because , during cast ending receiver already closed the streaming restriction for ExoPlayerActivity.this user , so we have to
                // satrt a new streaming restriction at sender end.
                restrict_stream_id = "0";

                Log.v("BIBHU3", "onSessionEnding===================" + cast_disconnected_position);
                Log.v("BIBHU3", "onSessionEnding DataUsedByChrmoeCast===================" + DataUsedByChrmoeCast);
            }

            @Override
            public void onSessionResuming(CastSession session, String sessionId) {
            }

            @Override
            public void onSessionSuspended(CastSession session, int reason) {

                Log.v("BIBHU3", "onSessionSuspended===cast disconnected");
            }

            private void onApplicationConnected(final CastSession castSession) {

                //================================================================================//

              /*  String x = "{'msg':'Reply from Bibhu Prasad'}";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(x);
                }catch (Exception e){}

                castSession.sendMessage("urn:x-cast:muvi.mcrt.final",jsonObject.toString());*/

                try {
                    castSession.setMessageReceivedCallbacks("urn:x-cast:muvi.mcrt.final", new Cast.MessageReceivedCallback() {
                        @Override
                        public void onMessageReceived(CastDevice castDevice, String s, String s1) {
                            Log.v("bibhu", "onMessageReceived Message from receiver=" + s1);


                            if (s1.contains("completed")) {
                                video_completed_at_chromecast = true;
                                Log.v("bibhu", "video completed at chromecast");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                            } else {
                                try {
                                    JSONObject jsonObject = new JSONObject(s1);
                                    videoLogId = jsonObject.optString("video_log_id");

                                    if (isDrm) {
                                        videoBufferLogId = jsonObject.optString("bandwidth_log_id");
                                        Current_Sesion_DataUsedByChrmoeCast = Long.parseLong(jsonObject.optString("bandwidth"));
                                        Log.v("bibhu", "Current_Sesion_DataUsedByChrmoeCast=*****************=====" + Current_Sesion_DataUsedByChrmoeCast);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //================================================================================//

                // Call video log after caonnected with chromecast

                asyncVideoLogDetails = new AsyncVideoLogDetails();
                asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

                //====================END============================//


                //Will Add Some Data to send
                stoptimertask();
                Util.call_finish_at_onUserLeaveHint = false;
                Util.hide_pause = true;
                ((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
                latest_center_play_pause.setVisibility(View.VISIBLE);
                subtitleText.setText("");
                emVideoView.setEnabled(false);
//                latest_center_play_pause.setEnabled(true);

                if (emVideoView.isPlaying()) {
                    emVideoView.pause();
                    latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
                    center_play_pause.setImageResource(R.drawable.ic_media_play);
                    mHandler.removeCallbacks(updateTimeTask);
                }


                if (center_pause_paly_timer_is_running) {
                    center_pause_paly_timer.cancel();
                    center_pause_paly_timer_is_running = false;
                    Log.v("BIBHU11", "CastAndCrewActivity End_Timer cancel called");


                    last_ll.setVisibility(View.GONE);
                    center_play_pause.setVisibility(View.GONE);
                    current_time.setVisibility(View.GONE);
                }

                if (SubTitlePath.size() > 0)
                    subtitle_change_btn.setVisibility(View.VISIBLE);
                mediaRouteButton.setVisibility(View.VISIBLE);
                primary_ll.setVisibility(View.VISIBLE);


                //===================================================================================================//


                Log.v("BIBHU2", "cast connected");
                cast_disconnected_position = 0;

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

                PlayUsingCsat();
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
                        Log.v("BIBHU2", "=============1");
                        break;

                    case REMOTE:
                        Log.v("BIBHU2", "=============2");

                        loadRemoteMedia(0, true);

                        break;
                    default:
                        break;
                }
                break;

            case PLAYING:
                Log.v("BIBHU2", "=============3");
                mPlaybackState = PlaybackState.PAUSED;
                break;

            case IDLE:
                switch (mLocation) {
                    case LOCAL:
                        Log.v("BIBHU2", "=============4");
                        break;
                    case REMOTE:
                        if (mCastSession != null && mCastSession.isConnected()) {
                            Log.v("BIBHU2", "=============5==+current_played_length==" + current_played_length);
                            loadRemoteMedia(current_played_length, true);
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
        remoteMediaClient = mCastSession.getRemoteMediaClient();


        if (remoteMediaClient == null) {
            return;
        }


        remoteMediaClient.addListener(new RemoteMediaClient.Listener() {

            @Override
            public void onStatusUpdated() {

//                Intent intent = new Intent(MyLibraryPlayer.this, ExpandedControlsActivity.class);
//                startActivity(intent);
//                remoteMediaClient.removeListener(MyLibraryPlayer.this);

                if (mCastSession != null && mCastSession.isConnected()) {
                    Log.v("BIBHU222", "======" + remoteMediaClient.isPlaying());

                    if (remoteMediaClient.isPlaying()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                latest_center_play_pause.setImageResource(R.drawable.center_ic_media_pause);
                                latest_center_play_pause.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
                                latest_center_play_pause.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                }
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

    /***************
     * chromecast
     **********************/


    public void PlayUsingCsat() {


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

                jsonObj.put("active_track_index", active_track_index);

                //  This Code Is Added For Video Log By Bibhu..

                jsonObj.put("authToken", Util.authTokenStr.trim());
                jsonObj.put("user_id", userIdStr.trim());
                jsonObj.put("ip_address", ipAddressStr.trim());
                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                jsonObj.put("episode_id", playerModel.getEpisode_id());


                jsonObj.put("watch_status", watchStatus);
                jsonObj.put("device_type", "2");
                jsonObj.put("log_id", videoLogId);

                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    jsonObj.put("restrict_stream_id", restrict_stream_id);
                    jsonObj.put("is_streaming_restriction", "1");
                    Log.v("BIBHU4", "restrict_stream_id============1");
                } else {
                    jsonObj.put("restrict_stream_id", restrict_stream_id);
                    jsonObj.put("is_streaming_restriction", "0");
                    Log.v("BIBHU4", "restrict_stream_id============0");
                }

                jsonObj.put("domain_name", Util.rootUrl().trim().substring(0, Util.rootUrl().trim().length() - 6));
                jsonObj.put("is_log", "1");

                // MyLibraryPlayer.this code is changed according to new Video log //

                jsonObj.put("played_length", "0");
                jsonObj.put("log_temp_id", "0");
                jsonObj.put("resume_time", "" + (playerPosition));
                jsonObj.put("seek_status", "first_time");


                //=====================End===================//


                // MyLibraryPlayer.this  Code Is Added For Drm BufferLog By Bibhu ...

                jsonObj.put("resolution", "BEST");
                jsonObj.put("start_time", String.valueOf(playerPosition));
                jsonObj.put("end_time", String.valueOf(playerPosition));
                jsonObj.put("log_unique_id", videoBufferLogUniqueId);
                jsonObj.put("bandwidth_log_id", videoBufferLogId);
                jsonObj.put("location", Location);


                if (isDrm) {
                    jsonObj.put("video_type", "mped_dash");
                } else {
                    jsonObj.put("video_type", "");
                }

                Log.v("BIBHU4", "drm_bandwidth_by_sender============" + ((CurrentUsedData + DataUsedByChrmoeCast) * 1024));
                Log.v("BIBHU4", "CurrentUsedData============" + CurrentUsedData);
                Log.v("BIBHU4", "DataUsedByChrmoeCast============" + DataUsedByChrmoeCast);

                jsonObj.put("drm_bandwidth_by_sender", "" + ((CurrentUsedData + DataUsedByChrmoeCast) * 1024));

                //====================End=====================//

            } catch (JSONException e) {
            }

            List tracks = new ArrayList();

            Log.v("BIBHU", "url size============" + playerModel.offline_url.size());
            if(featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)) {
                if (playerModel.offline_url.size() > 0) {

                    for (int i = 0; i < playerModel.offline_url.size(); i++) {

                        MediaTrack mediaTrack = new MediaTrack.Builder(i,
                                MediaTrack.TYPE_TEXT)
                                .setName(playerModel.offline_language.get(i))
                                .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                .setContentId(playerModel.offline_url.get(i))
                                .setLanguage(playerModel.SubTitleLanguage.get(i))
                                .setContentType("text/vtt")
                                .build();

                        tracks.add(mediaTrack);
                    }
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
            mediaContentType = "videos/mp4";
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put("description", playerModel.getVideoTitle());
                jsonObj.put("active_track_index", active_track_index);

                //  This Code Is Added For Video Log By Bibhu..

                jsonObj.put("authToken", Util.authTokenStr.trim());
                jsonObj.put("user_id", userIdStr.trim());
                jsonObj.put("ip_address", ipAddressStr.trim());
                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                jsonObj.put("episode_id", playerModel.getEpisode_id());


                jsonObj.put("watch_status", watchStatus);
                jsonObj.put("device_type", "2");
                jsonObj.put("log_id", videoLogId);

                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                    jsonObj.put("restrict_stream_id", restrict_stream_id);
                    jsonObj.put("is_streaming_restriction", "1");
                    Log.v("BIBHU4", "restrict_stream_id============1");
                } else {
                    jsonObj.put("restrict_stream_id", restrict_stream_id);
                    jsonObj.put("is_streaming_restriction", "0");
                    Log.v("BIBHU4", "restrict_stream_id============0");
                }

                jsonObj.put("domain_name", Util.rootUrl().trim().substring(0, Util.rootUrl().trim().length() - 6));
                jsonObj.put("is_log", "1");

                // ExoPlayerActivity.this code is changed according to new Video log //

                jsonObj.put("played_length", "0");
                jsonObj.put("log_temp_id", "0");
                jsonObj.put("resume_time", "" + (playerPosition));
                jsonObj.put("seek_status", "first_time");


                //=====================End===================//

                // This  Code Is Added For Drm BufferLog By Bibhu ...

                jsonObj.put("resolution", "BEST");
                jsonObj.put("start_time", String.valueOf(playerPosition));
                jsonObj.put("end_time", String.valueOf(playerPosition));

                // This ia always "0" for Non DRM play and Cast.
                jsonObj.put("log_unique_id", "");
                jsonObj.put("bandwidth_log_id", "0");
                //====END========================//

                jsonObj.put("location", Location);
                jsonObj.put("video_type", "");


                //====================End=====================//
            } catch (JSONException e) {
            }

            List tracks = new ArrayList();

            Log.v("BIBHU", "url size============" + playerModel.offline_url.size());
            if(featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)) {
                if (playerModel.offline_url.size() > 0) {

                    for (int i = 0; i < playerModel.offline_url.size(); i++) {

                        MediaTrack mediaTrack = new MediaTrack.Builder(i,
                                MediaTrack.TYPE_TEXT)
                                .setName(playerModel.offline_language.get(i))
                                .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                .setContentId(playerModel.offline_url.get(i))
                                .setLanguage(playerModel.SubTitleLanguage.get(i))
                                .setContentType("text/vtt")
                                .build();

                        tracks.add(mediaTrack);
                    }
                }
            }

            mediaInfo = new MediaInfo.Builder(playerModel.getVideoUrl().trim())
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType(mediaContentType)
                    .setMetadata(movieMetadata)
                    .setCustomData(jsonObj)
                    .setMediaTracks(tracks)
                    .build();
            mSelectedMedia = mediaInfo;


            togglePlayback();

        }
    }

    // MyLibraryPlayer.this part is only applicable for multiple download optin feature

    class DetectDownloadingFileSize extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
        }

        /**
         * Downloading file in background thread
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {
            int count;

            try {

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(List_Of_Resolution_Url.get(0));
                HttpResponse execute = client.execute(httpGet);
//                int contentLength = (int)execute.getEntity().getContentLength();

                float size = (Float.parseFloat("" + execute.getEntity().getContentLength()) / 1024) / 1024;
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                size = Float.valueOf(decimalFormat.format(size));
                List_Of_FileSize.add("(" + size + " MB)");


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {


            List_Of_Resolution_Url.remove(0);
            if (List_Of_Resolution_Url.size() > 0) {
                new DetectDownloadingFileSize().execute();
            } else {
                try {
                    if (pDialog_for_gettig_filesize != null && pDialog_for_gettig_filesize.isShowing()) {
                        pDialog_for_gettig_filesize.hide();
                    }
                } catch (IllegalArgumentException ex) {
                }

                // Show PopUp for Multiple Options for Download .
                ShowDownloadOptionPopUp();
            }

        }
    }

    public void ShowDownloadOptionPopUp() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyLibraryPlayer.this, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) MyLibraryPlayer.this.getSystemService(MyLibraryPlayer.this.LAYOUT_INFLATER_SERVICE);

        View convertView = (View) inflater.inflate(R.layout.activity_download_popup, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("");

        TextView title_text = (TextView) convertView.findViewById(R.id.title_text);
        ListView resolution_list = (ListView) convertView.findViewById(R.id.resolution_list);
        Button save = (Button) convertView.findViewById(R.id.save);
        Button cancel = (Button) convertView.findViewById(R.id.cancel);

        save.setText(Util.getTextofLanguage(MyLibraryPlayer.this, SAVE, DEFAULT_SAVE));
        cancel.setText(Util.getTextofLanguage(MyLibraryPlayer.this, CANCEL_BUTTON, DEFAULT_CANCEL_BUTTON));
        title_text.setText(Util.getTextofLanguage(MyLibraryPlayer.this, SAVE_OFFLINE_VIDEO, DEFAULT_SAVE_OFFLINE_VIDEO));

        DownloadOptionAdapter adapter = new DownloadOptionAdapter(MyLibraryPlayer.this, List_Of_FileSize, List_Of_Resolution_Format);
        resolution_list.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //download file here
                downloadFile(false);

                alert.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_download_format = 0;
                alert.cancel();
            }
        });

        alert = alertDialog.show();
        alertDialog.setCancelable(false);
        alert.setCancelable(false);

        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                selected_download_format = 0;
                // Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private BroadcastReceiver SelectedUrl = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String position = intent.getStringExtra("position");
            Log.v("BIBHU1", "Got position: " + position);
            selected_download_format = Integer.parseInt(position);

        }
    };

    //============================End=================================//


    // MyLibraryPlayer.this AsyncTask is called to get Details of Watch Period and Access Period of download content.

    class AsyncWatchAccessDetails extends AsyncTask<String, String, String> {

        String responseStr;
        int statusCode = 0;
        String request_data = "";
        String log_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {

            Log.v("BIBHU11", "f_url[0]=======" + f_url[0]);

            SQLiteDatabase DB = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = DB.rawQuery("SELECT stream_unique_id FROM " + DBHelper.WATCH_ACCESS_INFO + " WHERE download_id = '" + f_url[0].trim() + "'", null);
            int count = cursor.getCount();
            String Stream_Id = "";

            if (count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Stream_Id = cursor.getString(0).trim();

                        Log.v("BIBHU3", "Stream_Id============" + Stream_Id);
                    } while (cursor.moveToNext());
                }
            }


            String urlRouteList = Util.rootUrl().trim() + Util.GetOfflineViewRemainingTime.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("stream_uniq_id", Stream_Id);
                httppost.addHeader("watch_remaining_time", "0");
                httppost.addHeader("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                httppost.addHeader("user_id", userIdStr);
                httppost.addHeader("device_type ", "2");
                httppost.addHeader("request_data ", "");
                httppost.addHeader("lang_code", Util.getTextofLanguage(MyLibraryPlayer.this, Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                } catch (Exception e) {
                    statusCode = 0;
                    e.printStackTrace();
                }


                Log.v("BIBHU11", "response of GetOfflineViewRemainingTime in exoplayer=======" + responseStr);

                JSONObject myJson = null;
                if (responseStr != null) {
                    myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    request_data = myJson.optString("request_data");
                    log_id = myJson.optString("log_id");

                    Log.v("BIBHU11", "response of server_current_time in exoplayer=======" + myJson.optLong("created_date"));
                    Log.v("BIBHU11", "response of server_current_time in exoplayer=======" + myJson.optLong("access_expiry_time"));
                    Dwonload_Complete_Msg = "";
                    if (statusCode == 200) {

                        Dwonload_Complete_Msg = myJson.optString("download_complete_msg");

                        if (Dwonload_Complete_Msg.trim().equals(""))
                            Dwonload_Complete_Msg = "Your video has been downloaded successfully.";

                        SQLiteDatabase DB1 = MyLibraryPlayer.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);


                        String query1 = "UPDATE " + DBHelper.WATCH_ACCESS_INFO + " SET server_current_time = '" + myJson.optLong("created_date") + "' ," +
                                "watch_period = '0',access_period = '" + myJson.optLong("access_expiry_time") + "' WHERE download_id = '" + f_url[0].trim() + "'";


//                        String query1 = "UPDATE "+DBHelper.WATCH_ACCESS_INFO+" SET server_current_time = '"+myJson.optLong("created_date")+"' ," +
//                                "watch_period = '0',access_period = '"+((myJson.optLong("created_date"))+300000)+"' WHERE download_id = '"+f_url[0].trim()+"'";


                        DB1.execSQL(query1);
                    }
                }
            } catch (Exception e) {
                statusCode = 0;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Intent intent = new Intent(MyLibraryPlayer.this, PopUpService.class);
            intent.putExtra("msg", Dwonload_Complete_Msg);
            startService(intent);
        }
    }

    private String getActionTokenFromStorage(String tokenFileName) {
        String token = null;
        byte[] readBuffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        int bytesRead = 0;

        try {
            is = new FileInputStream(tokenFileName);
            while ((bytesRead = is.read(readBuffer)) != -1) {
                baos.write(readBuffer, 0, bytesRead);
            }
            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        token = new String(baos.toByteArray());
        return token;
    }

    /**
     * Request video ads from the given VAST ad tag.
     *
     * @param adTagUrl URL of the ad's VAST XML
     */
    private void requestAds(String adTagUrl) {
        AdDisplayContainer adDisplayContainer = mSdkFactory.createAdDisplayContainer();
        adDisplayContainer.setAdContainer(mAdUiContainer);

        // Create the ads request.
        AdsRequest request = mSdkFactory.createAdsRequest();
        request.setAdTagUrl(adTagUrl);
        request.setAdDisplayContainer(adDisplayContainer);
        request.setContentProgressProvider(new ContentProgressProvider() {
            @Override
            public VideoProgressUpdate getContentProgress() {
                if (mIsAdDisplayed || emVideoView == null || emVideoView.getDuration() <= 0) {
                    return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
                }
                Log.v("SUBHA", "emVideoView.getCurrentPosition()" + emVideoView.getCurrentPosition());
                Log.v("SUBHA", "emVideoView.getDuration()" + emVideoView.getDuration());

               /* if (emVideoView.getCurrentPosition() >= emVideoView.getDuration()){
                    return new VideoProgressUpdate(emVideoView.getCurrentPosition(),
                            emVideoView.getDuration());
                }
*/
                return new VideoProgressUpdate(emVideoView.getCurrentPosition(),
                        emVideoView.getDuration());
            }
        });
       /* if (mAdsManager !=null){
            Log.v("SUBHA","ddT"+mAdsManager.getAdCuePoints());

        }*/
        Log.v("SUBHA", "ddT");

        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        mAdsLoader.requestAds(request);
    }

    @Override
    public void onAdEvent(AdEvent adEvent) {
        Log.v("SUBHA", "Event: " + adEvent.getType());

        // These are the suggested event types to handle. For full list of all ad event
        // types, see the documentation for AdEvent.AdEventType.
        switch (adEvent.getType()) {
            case LOADED:
                onClick1();
             /*   if (pDialog == null){
                    pDialog = new ProgressDialog(ExoPlayerActivity.this);
                    pDialog.setMessage("loading");
                    pDialog.show();
                }*/

                // AdEventType.LOADED will be fired when ads are ready to be played.
                // AdsManager.start() begins ad playback. This method is ignored for VMAP or
                // ad rules playlists, as the SDK will automatically start executing the
                // playlist.
                mAdsManager.start();
                break;

            case STARTED:
             /*   if (pDialog != null){
                    Log.v("SUBHA","DISMISS");
                    pDialog.dismiss();
                }*/
                //  progressView.setVisibility(View.VISIBLE);
                Util.call_finish_at_onUserLeaveHint = false;
                onClick1();
             /*   final ProgressDialog finalPDialog = pDialog;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalPDialog !=null && finalPDialog.isShowing()) {
                            Log.v("SUBHA","DISMISS");

                            finalPDialog.dismiss();

                        }
                    }
                });*/


                break;

            case CONTENT_PAUSE_REQUESTED:
                // AdEventType.CONTENT_PAUSE_REQUESTED is fired immediately before a video
                // ad is played.
                mIsAdDisplayed = true;
                Util.call_finish_at_onUserLeaveHint = false;

                emVideoView.pause();
                //  mVideoPlayer.pause();
                break;
            case CONTENT_RESUME_REQUESTED:
                // AdEventType.CONTENT_RESUME_REQUESTED is fired when the ad is completed
                // and you should start playing your content.
                mIsAdDisplayed = false;
                if (video_completed == true) {
                    backCalled();
                }

                Util.call_finish_at_onUserLeaveHint = true;
                emVideoView.start();
                break;
            case ALL_ADS_COMPLETED:
                if (mAdsManager != null) {
                    mAdsManager.destroy();
                    mAdsManager = null;
                }
                if (video_completed == true) {
                    backCalled();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAdError(AdErrorEvent adErrorEvent) {
        Log.e("subha", "Ad Error: " + adErrorEvent.getError().getMessage());
        Util.call_finish_at_onUserLeaveHint = true;
        emVideoView.start();
    }

    public void onClick1() {
        if (mDialog == null) {
            mDialog = new ProgressBarHandler(MyLibraryPlayer.this);
            mDialog.show();
        } else {
            mDialog.hide();

        }

    }


    private boolean changeSubtitle_Resolution() {
        boolean status = false;
        if (isDrm) {
            if (SubTitlePath.size() < 1) {
                status = false;
            } else {
                status = true;
            }
        } else {
            if ((SubTitlePath.size() < 1) && (ResolutionUrl.size() < 1)) {
                status = false;
            } else {
                status = true;
            }
        }
        return status;
    }
}
