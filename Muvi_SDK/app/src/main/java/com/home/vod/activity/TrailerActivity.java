package com.home.vod.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.home.apisdk.APIUrlConstant;
import com.home.vod.R;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ResizableCustomView;
import com.home.vod.util.SensorOrientationChangeNotifier;
import com.home.vod.util.Util;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import player.utils.DBHelper;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.util.Constant.authTokenStr;
import static player.utils.Util.DEFAULT_NO_INTERNET_CONNECTION;
import static player.utils.Util.VIEW_MORE;


/*enum ContentTypes1 {
    DASH("application/dash+xml"), HLS("application/vnd.apple.mpegurl"), PDCF(
            "video/mp4"), M4F("video/mp4"), DCF("application/vnd.oma.drm.dcf"), BBTS(
            "video/mp2t");
    String mediaSourceParamsContentType = null;

    private ContentTypes1(String mediaSourceParamsContentType) {
        this.mediaSourceParamsContentType = mediaSourceParamsContentType;
    }

    public String getMediaSourceParamsContentType() {
        return mediaSourceParamsContentType;
    }
}*/
public class TrailerActivity extends AppCompatActivity implements SensorOrientationChangeNotifier.Listener  {

    int playerStartPosition = 0;
    private static final int MAX_LINES = 3;


    // Added For Buffer Log
    long PreviousUsedData = 0;
    long CurrentUsedData = 0;

    Timer timer;
    private Handler threadHandler = new Handler();
    String videoLogId = "0";
    String watchStatus = "start";
    int playerPosition = 0;
    public  boolean isFastForward = false;
    public  int playerPreviousPosition = 0;
    TimerTask timerTask;
    String emailIdStr = "";
    String userIdStr = "";
    String movieId = "";
    String episodeId = "0";
    AsyncVideoLogDetails asyncVideoLogDetails;
    AsyncFFVideoLogDetails asyncFFVideoLogDetails;

    AsynGetIpAddress asynGetIpAddress;

    ImageButton  back, center_play_pause;
    LinearLayout back_layout;
    ImageView compress_expand;
    SeekBar seekBar;
    private Handler mHandler = new Handler();
    Timer  center_pause_paly_timer;
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
    int player_layout_height,player_layout_width;
    int screenWidth,screenHeight;
    ImageButton latest_center_play_pause;
    String videoBufferLogId = "0";
    String videoBufferLogUniqueId = "0";
    String Location = "0";

    String resolution = "BEST";

    String ipAddressStr = "";
    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    SharedPreferences pref;
    //Toolbar mActionBarToolbar;
    LinearLayout linearLayout1;

    TextView videoTitle,GenreTextView,videoDurationTextView,videoCensorRatingTextView,videoCensorRatingTextView1,videoReleaseDateTextView,
            videoStoryTextView,videoCastCrewTitleTextView;

    private EMVideoView emVideoView;
    int seek_label_pos = 0;
    int content_types_id = 0;
    boolean censor_layout = true;
    PreferenceManager preferenceManager;
    LanguagePreference languagePreference;

    int player_start_time = 0;
    int player_end_time = 0;
    String log_temp_id = "0";

    Timer navigation_key_timer;

    @Override
    protected void onPause() {
        super.onPause();

        try{
            if (navigation_key_timer != null)
                navigation_key_timer.cancel();
        }catch (Exception e){}

    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorOrientationChangeNotifier.getInstance(TrailerActivity.this).addListener(this);
        AsynGetIpAddress asynGetIpAddress = new AsynGetIpAddress();
        asynGetIpAddress.executeOnExecutor(threadPoolExecutor);

        navigation_key_timer = new Timer();
        navigation_key_timer.schedule(new TimerTask() {
            @Override
            public void run() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                       int orientation = display.getRotation();

                       if (orientation == 1|| orientation == 3) {
                           hideSystemUI();
                       }
                   }
               });
            }
        },0,1000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        content_types_id = Util.dataModel.getContentTypesId();

        PreviousUsedDataByApp(true);

        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);

        //commented by me

        if (Util.dataModel.getVideoUrl().matches("")){
            // onBackPressed();
            backCalled();
        }
        movieId = Util.dataModel.getMovieUniqueId();
        episodeId = Util.dataModel.getEpisode_id();

        if (preferenceManager != null) {
            emailIdStr = preferenceManager.getEmailIdFromPref();
            userIdStr = preferenceManager.getUseridFromPref();
            LogUtil.showLog("BKS","userid trailer=="+userIdStr);
            if (emailIdStr==null){
                emailIdStr = "";
            }
            if (userIdStr==null){
                userIdStr = "";
            }

        } else {
            emailIdStr = "";
            userIdStr = "";
        }

        ((ImageView) findViewById(R.id.subtitle_change_btn)).setVisibility(View.INVISIBLE);

        emVideoView = (EMVideoView) findViewById(R.id.emVideoView);
        latest_center_play_pause = (ImageButton) findViewById(R.id.latest_center_play_pause);
        videoTitle = (TextView) findViewById(R.id.videoTitle);
        Typeface videoTitleface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.regular_fonts));
        videoTitle.setTypeface(videoTitleface);
        GenreTextView = (TextView) findViewById(R.id.GenreTextView);
        Typeface GenreTextViewface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.light_fonts));
        GenreTextView.setTypeface(GenreTextViewface);
        videoDurationTextView = (TextView) findViewById(R.id.videoDurationTextView);
        Typeface videoDurationTextViewface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.light_fonts));
        videoDurationTextView.setTypeface(videoDurationTextViewface);
        videoCensorRatingTextView = (TextView) findViewById(R.id.videoCensorRatingTextView);
        Typeface videoCensorRatingTextViewface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.light_fonts));
        videoCensorRatingTextView.setTypeface(videoCensorRatingTextViewface);
        videoCensorRatingTextView1 = (TextView) findViewById(R.id.videoCensorRatingTextView1);
        Typeface videoCensorRatingTextView1face = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.light_fonts));
        videoCensorRatingTextView1.setTypeface(videoCensorRatingTextView1face);
        videoReleaseDateTextView = (TextView) findViewById(R.id.videoReleaseDateTextView);
        Typeface videoReleaseDateTextViewface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.light_fonts));
        videoReleaseDateTextView.setTypeface(videoReleaseDateTextViewface);

        videoStoryTextView = (TextView) findViewById(R.id.videoStoryTextView);
        Typeface videoStoryTextViewTypeface = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
        videoStoryTextView.setTypeface(videoStoryTextViewTypeface);
        //storyViewMoreButton = (Button) findViewById(R.id.storyViewMoreButton);

        videoCastCrewTitleTextView = (TextView) findViewById(R.id.videoCastCrewTitleTextView);
        Typeface watchTrailerButtonTypeface = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.regular_fonts));
        videoCastCrewTitleTextView.setTypeface(watchTrailerButtonTypeface);
        videoCastCrewTitleTextView.setText(languagePreference.getTextofLanguage(CAST_CREW_BUTTON_TITLE,DEFAULT_CAST_CREW_BUTTON_TITLE));




        if (Util.dataModel.getVideoTitle().trim() != null)
        {
            videoTitle.setText(Util.dataModel.getVideoTitle().trim());
            videoTitle.setVisibility(View.VISIBLE);
        } else {
            videoTitle.setVisibility(View.GONE);
        }


        if (Util.dataModel.getVideoGenre().trim() != null && !Util.dataModel.getVideoGenre().trim().matches(""))

        {
            GenreTextView.setText(Util.dataModel.getVideoGenre().trim());
            GenreTextView.setVisibility(View.VISIBLE);
        } else {
            GenreTextView.setVisibility(View.GONE);
        }


        if (Util.dataModel.getVideoDuration().trim() != null && !Util.dataModel.getVideoDuration().trim().matches(""))
        {
            videoDurationTextView.setText(Util.dataModel.getVideoDuration().trim());
            videoDurationTextView.setVisibility(View.VISIBLE);
            censor_layout = false;
        } else {

            videoDurationTextView.setVisibility(View.GONE);
        }

        if (Util.dataModel.getCensorRating().trim() != null && !Util.dataModel.getCensorRating().trim().matches("")) {
            if ((Util.dataModel.getCensorRating().trim()).contains("_")) {
                String Data[] = (Util.dataModel.getCensorRating().trim()).split("-");
                videoCensorRatingTextView.setVisibility(View.VISIBLE);
                videoCensorRatingTextView1.setVisibility(View.VISIBLE);
                videoCensorRatingTextView.setText(Data[0]);
                videoCensorRatingTextView1.setText(Data[-1]);
                censor_layout = false;
            } else {
                censor_layout = false;

                videoCensorRatingTextView.setVisibility(View.VISIBLE);
                videoCensorRatingTextView1.setVisibility(View.GONE);
                videoCensorRatingTextView.setText(Util.dataModel.getCensorRating().trim());
            }
        } else {

            videoCensorRatingTextView.setVisibility(View.GONE);
            videoCensorRatingTextView1.setVisibility(View.GONE);
        }
        if (Util.dataModel.getCensorRating().trim() != null && Util.dataModel.getCensorRating().trim()
                .equalsIgnoreCase(languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))) {
            videoCensorRatingTextView.setVisibility(View.GONE);
            videoCensorRatingTextView1.setVisibility(View.GONE);
        }

        if (Util.dataModel.getVideoReleaseDate().trim() != null && !Util.dataModel.getVideoReleaseDate().trim().matches(""))

        {
            videoReleaseDateTextView.setText(Util.dataModel.getVideoReleaseDate().trim());
            videoReleaseDateTextView.setVisibility(View.VISIBLE);
            censor_layout = false;
        } else {
            videoReleaseDateTextView.setVisibility(View.GONE);
        }

        if(censor_layout) {

            findViewById(R.id.durationratingLiearLayout).setVisibility(View.GONE);
        }

        if (Util.dataModel.getVideoStory().trim() != null && !Util.dataModel.getVideoStory().trim().matches(""))

        {
            videoStoryTextView.setText(Util.dataModel.getVideoStory());
            videoStoryTextView.setVisibility(View.VISIBLE);
            ResizableCustomView.doResizeTextView(TrailerActivity.this,videoStoryTextView,MAX_LINES, languagePreference.getTextofLanguage(VIEW_MORE,DEFAULT_VIEW_MORE), true);

        } else {
            videoStoryTextView.setVisibility(View.GONE);
        }

        if (Util.dataModel.isCastCrew() == true)

        {
            videoCastCrewTitleTextView.setVisibility(View.VISIBLE);
        } else {
            videoCastCrewTitleTextView.setVisibility(View.GONE);
        }


        videoCastCrewTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.getInstance().isConnected(TrailerActivity.this)) {
                    //Will Add Some Data to send
                    Util.call_finish_at_onUserLeaveHint = false;
                    Util.hide_pause = true;
                    findViewById(R.id.progress_view).setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.VISIBLE);

                    if (emVideoView.isPlaying()) {
                        emVideoView.pause();
                        latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
                        center_play_pause.setImageResource(R.drawable.ic_media_play);
                        mHandler.removeCallbacks(updateTimeTask);
                    }


                    final Intent detailsIntent = new Intent(TrailerActivity.this, CastAndCrewActivity.class);
                    detailsIntent.putExtra("cast_movie_id", movieId.trim());
                    detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(detailsIntent);
                } else {
                    Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION,DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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
        back = (ImageButton) findViewById(R.id.back);

        try{
            back_layout = (LinearLayout) findViewById(R.id.back_layout);
        }catch (Exception e){}

        seekBar = (SeekBar) findViewById(R.id.progress);
        center_play_pause = (ImageButton) findViewById(R.id.center_play_pause);

        current_time = (TextView) findViewById(R.id.current_time);
        total_time = (TextView) findViewById(R.id.total_time);
        progressView = (ProgressBar) findViewById(R.id.progress_view);


        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();


        LinearLayout.LayoutParams params1 = null;
        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)){
            if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);

            }
            else
            {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);
            }
        }
        else
        {
            if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);
            }
            else
            {
                params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);
            }
        }
        player_layout.setLayoutParams(params1);

        if (content_types_id== 4){
            seekBar.setEnabled(false);
            seekBar.setProgress(0);
        }else{
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
                if (progressView.getVisibility() == View.VISIBLE) {
                    primary_ll.setVisibility(View.VISIBLE);
                    center_play_pause.setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.GONE);
                    current_time.setVisibility(View.GONE);

                } else {
                    if (primary_ll.getVisibility() == View.VISIBLE) {
                        primary_ll.setVisibility(View.GONE);
                        last_ll.setVisibility(View.GONE);
                        center_play_pause.setVisibility(View.GONE);
                        latest_center_play_pause.setVisibility(View.GONE);
                        current_time.setVisibility(View.GONE);
                        End_Timer();
                    } else {
                        primary_ll.setVisibility(View.VISIBLE);
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
                    hideSystemUI();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {

                    LinearLayout.LayoutParams params1 = null;
                    if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)){
                        if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);

                        }
                        else
                        {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);
                        }
                    }
                    else
                    {
                        if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);

                        }
                        else
                        {
                            params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);
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
                    showSystemUI();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                Execute_Pause_Play();
            }
        });



        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                video_completed = false;
                progressView.setVisibility(View.VISIBLE);
                center_play_pause.setVisibility(View.GONE);
                latest_center_play_pause.setVisibility(View.GONE);
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
                    if (content_types_id== 4){
                        asyncVideoLogDetails = new AsyncVideoLogDetails();
                        asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);


                        emVideoView.start();
                        updateProgressBar();
                    }else{
                        startTimer();

                        asyncVideoLogDetails = new AsyncVideoLogDetails();
                        asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
                        emVideoView.start();
                        updateProgressBar();
                    }
                } catch (Exception e) {
                }
            }
        });



     try{
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
     }catch (Exception e){}



//commented by me
        emVideoView.setVideoURI(Uri.parse(Util.dataModel.getVideoUrl()));
       /* try {
			*//*
			 * Initialize the Wasabi Runtime (necessary only once for each
			 * instantiation of the application)
			 *
			 * ** Note: Set Runtime Properties as needed for your environment
			 *//*
            Runtime.initialize(getDir("wasabi", MODE_PRIVATE).getAbsolutePath());
			*//*
			 * Personalize the application (acquire DRM keys). This is only
			 * necessary once each time the application is freshly installed
			 *
			 * ** Note: personalize() is a blocking call and may take long
			 * enough to complete to trigger ANR (Application Not Responding)
			 * errors. In a production application this should be called in a
			 * background thread.
			 *//*
            if (!Runtime.isPersonalized())
                Runtime.personalize();

        } catch (NullPointerException e) {
            onBackPressed();

            return;
        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            onBackPressed();

            return;
        }

        try {
            EnumSet<PlaylistProxy.Flags> flags = EnumSet.noneOf(PlaylistProxy.Flags.class);
            playerProxy = new PlaylistProxy(flags, this, new Handler());
            playerProxy.start();
        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            onBackPressed();

            return;
        }



        	*//*
		 * create a playlist proxy url and pass it to the native player
		 *//*
        try {
			*//*
			 * Note that the MediaSourceType must be adapted to the stream type
			 * (DASH or HLS). Similarly,
			 * the MediaSourceParams need to be set according to the media type
			 * if MediaSourceType is SINGLE_FILE
			 *//*

            ContentTypes1 contentType = ContentTypes1.DASH;
            PlaylistProxy.MediaSourceParams params = new PlaylistProxy.MediaSourceParams();
            params.sourceContentType = contentType
                    .getMediaSourceParamsContentType();
			*//*
			 * if the content has separate audio tracks (eg languages) you may
			 * select one using MediaSourceParams, eg params.language="es";
			 *//*
            String contentTypeValue = contentType.toString();
                if (Util.dataModel.getVideoUrl().contains(".mpd")) {
                    String url = playerProxy.makeUrl(Util.dataModel.getVideoUrl(), PlaylistProxy.MediaSourceType.valueOf((contentTypeValue == "MP4" || contentTypeValue == "HLS" || contentTypeValue == "DASH") ? contentTypeValue : "SINGLE_FILE"), params);
                    emVideoView.setVideoURI(Uri.parse(url));

                } else {
                    emVideoView.setVideoURI(Uri.parse(Util.dataModel.getVideoUrl()));
                }




        } catch (ErrorCodeException e) {
            // Consult WasabiErrors.txt for resolution of the error codes
            onBackPressed();

            return;
        }

        catch (IllegalArgumentException e) {
            onBackPressed();

            e.printStackTrace();
        } catch (SecurityException e) {
            onBackPressed();

            e.printStackTrace();
        } catch (IllegalStateException e) {
            onBackPressed();

            e.printStackTrace();
        }

        emVideoView.setVideoURI(Uri.parse("https://r12---sn-p5qlsnss.googlevideo.com/videoplayback?beids=%5B9452306%5D&itag=17&ipbits=0&sparams=clen%2Cdur%2Cei%2Cgir%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Crequiressl%2Csource%2Cupn%2Cexpire&lmt=1463866613479313&ei=Pj2sWL37McaT1gL91JLABQ&source=youtube&initcwndbps=5327500&requiressl=yes&dur=393.113&clen=2692773&mime=video%2F3gpp&key=yt6&gir=yes&pl=24&expire=1487704478&mm=31&ip=159.253.144.86&mn=sn-p5qlsnss&ms=au&mt=1487682748&upn=K6r9nWryb4Q&mv=m&id=o-AOjCnfCLnuL36xAojs4SvpH3RDLZfV-BtQfQnJPSO1r3&signature=46D104B20C417DED2A65B9F155F4512B2518A56A.55B6B4AFF163BA21B5C7E6BDA06737972B7657B6&title=Jarvis+for+Windows+7%2F8%2F8.1%2F10.3gp"));

*/
    }


    public void backCalled(){

        if (asynGetIpAddress!=null){
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails!=null){
            asyncVideoLogDetails.cancel(true);
        }
        if (asyncFFVideoLogDetails!=null){
            asyncFFVideoLogDetails.cancel(true);
        }

        if (timer!=null){
            stoptimertask();
            timer = null;
        }

        if (video_completed == false){

            AsyncResumeVideoLogDetails  asyncResumeVideoLogDetails = new AsyncResumeVideoLogDetails();
            asyncResumeVideoLogDetails.executeOnExecutor(threadPoolExecutor);
//            return;
        }
        mHandler.removeCallbacks(updateTimeTask);
        if (emVideoView!=null) {
            emVideoView.release();
        }
        finish();
        overridePendingTransition(0, 0);
    }

    private class AsyncVideoLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = APIUrlConstant.getVideoLogsUrl();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("watch_status", watchStatus);
                httppost.addHeader("device_type", "2");
                httppost.addHeader("content_type", "2");
                httppost.addHeader("log_id", videoLogId);

                Log.v("BIBHU6", "authToken=" + authTokenStr.trim());
                Log.v("BIBHU6", "user_id=" + userIdStr.trim());
                Log.v("BIBHU6", "ip_address=" + ipAddressStr.trim());
                Log.v("BIBHU6", "movie_id=" + movieId.trim());
                Log.v("BIBHU6", "episode_id=" + episodeId.trim());
                Log.v("BIBHU6", "played_length=" + String.valueOf(playerPosition));
                Log.v("BIBHU6", "watch_status=" + watchStatus);
                Log.v("BIBHU6", "device_type=" + "2");
                Log.v("BIBHU6", "log_id=" + videoLogId);



                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", log_temp_id);
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


                //===============End=============================//


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    Log.v("BIBHU6", "responseStr of videolog============" + responseStr);


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

                    Log.v("BIBHU6", "responseStr of videolog Exception============" + e.toString());

                }
                if (responseStr != null) {
                    JSONObject myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    if (statusCode == 200) {
                        videoLogId = myJson.optString("log_id");
                        log_temp_id = myJson.optString("log_temp_id");


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

                            } else if (isFastForward == false && currentPositionStr >= millisecondsToString(playerPreviousPosition)) {

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

            String urlRouteList = APIUrlConstant.getVideoLogsUrl();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr);
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("content_type", "2");
                httppost.addHeader("watch_status", watchStatus);

                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", videoLogId);


                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", log_temp_id);
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

                    Log.v("BIBHU", "responseStr of responseStr ff ============" + responseStr);

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

//                        Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);
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

    private int millisecondsToString(int milliseconds)  {
        // int seconds = (int) (milliseconds / 1000) % 60 ;
        int seconds = (int) (milliseconds / 1000);

        return seconds;
    }

    @Override
    public void onOrientationChange(int orientation) {



        if (orientation == 90){


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
        }
        else if (orientation == 270){


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
        } else if (orientation == 180){

            LinearLayout.LayoutParams params1 = null;
            if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)){
                if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);

                }
                else
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);
                }
            }
            else
            {
                if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);

                }
                else
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);
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
            showSystemUI();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //current_time.setVisibility(View.GONE);

        } else if (orientation == 0) {


            LinearLayout.LayoutParams params1 = null;
            if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)){
                if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);

                }
                else
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*45)/100);
                }
            }
            else
            {
                if(TrailerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);

                }
                else
                {
                    params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(screenHeight*40)/100);
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
            showSystemUI();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //current_time.setVisibility(View.GONE);
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
                    URL myurl = new URL(APIUrlConstant.IP_ADDRESS_URL);
                    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
                    InputStream ins = con.getInputStream();
                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader in = new BufferedReader(isr);

                    String inputLine;

                    while ((inputLine = in.readLine()) != null)
                    {
                        System.out.println(inputLine);
                        responseStr = inputLine;
                    }

                    in.close();


                } catch (org.apache.http.conn.ConnectTimeoutException e){
                    ipAddressStr = "";

                } catch (UnsupportedEncodingException e) {

                    ipAddressStr = "";

                }catch (IOException e) {
                    ipAddressStr = "";

                }
                if(responseStr!=null){
                    Object json = new JSONTokener(responseStr).nextValue();
                    if (json instanceof JSONObject){
                        ipAddressStr = ((JSONObject) json).getString("ip");

                    }

                }

            }
            catch (Exception e) {
                ipAddressStr = "";

            }

            return null;
        }


        protected void onPostExecute(Void result) {

            if(responseStr == null){
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

            try{
                if (emVideoView.getCurrentPosition() % 2 == 0)
                    BufferBandWidth();


                seekBar.setProgress(emVideoView.getCurrentPosition());
                seekBar.setMax(emVideoView.getDuration());
                Calcute_Currenttime_With_TotalTime();
                mHandler.postDelayed(this, 1000);

                if (content_types_id!= 4){
                    showCurrentTime();
                }

                current_matching_time = emVideoView.getCurrentPosition();


                if ((previous_matching_time == current_matching_time) && (current_matching_time < emVideoView.getDuration())) {
                    progressView.setVisibility(View.VISIBLE);
                    center_play_pause.setVisibility(View.GONE);
                    latest_center_play_pause.setVisibility(View.GONE);
                    previous_matching_time = current_matching_time;
                } else {

                    if (content_types_id== 4){


                    }
                    else
                    {
                        if (current_matching_time >= emVideoView.getDuration()) {
                            mHandler.removeCallbacks(updateTimeTask);
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
                }
            }catch (Exception e){

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

    public void onBackPressed() {
        super.onBackPressed();

        try{
            if (asynGetIpAddress!=null){
                asynGetIpAddress.cancel(true);
            }
            if (asyncVideoLogDetails!=null){
                asyncVideoLogDetails.cancel(true);
            }
            if (asyncFFVideoLogDetails!=null){
                asyncFFVideoLogDetails.cancel(true);
            }

            if (timer!=null){
                stoptimertask();
                timer = null;
            }
            mHandler.removeCallbacks(updateTimeTask);
            if (emVideoView!=null) {
                emVideoView.release();
            }
        }catch (Exception e){}

        finish();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onUserLeaveHint()
    {
        try{
            if (asynGetIpAddress!=null){
                asynGetIpAddress.cancel(true);
            }
            if (asyncVideoLogDetails!=null){
                asyncVideoLogDetails.cancel(true);
            }
            if (asyncFFVideoLogDetails!=null){
                asyncFFVideoLogDetails.cancel(true);
            }

            if (timer!=null){
                stoptimertask();
                timer = null;
            }
            mHandler.removeCallbacks(updateTimeTask);
            if (emVideoView!=null) {
                emVideoView.release();
            }
        }catch(Exception e){}


        finish();
        overridePendingTransition(0, 0);
        super.onUserLeaveHint();
    }


    public void Execute_Pause_Play() {
        if (emVideoView.isPlaying()) {
            emVideoView.pause();
            latest_center_play_pause.setImageResource(R.drawable.center_ic_media_play);
            center_play_pause.setImageResource(R.drawable.ic_media_play);
            mHandler.removeCallbacks(updateTimeTask);
        } else {
            if (video_completed) {

                if (content_types_id!= 4){
                    backCalled();
                }

            } else {
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

            primary_ll.setVisibility(View.GONE);
            last_ll.setVisibility(View.GONE);
            center_play_pause.setVisibility(View.GONE);
            latest_center_play_pause.setVisibility(View.GONE);
        }

    }

    public void Instant_End_Timer() {
        if (center_pause_paly_timer_is_running) {
            center_pause_paly_timer.cancel();
            center_pause_paly_timer_is_running = false;
        }

    }

    public void showCurrentTime ()
    {

        current_time.setText(Current_Time);
        current_time_position_timer();

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

    private void hideSystemUI() {
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
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {

        if (Util.dataModel.getVideoStory().trim() != null && !Util.dataModel.getVideoStory().trim().matches("")){
            videoStoryTextView.setText(Util.dataModel.getVideoStory());
            videoStoryTextView.setVisibility(View.VISIBLE);
            ResizableCustomView.doResizeTextView(TrailerActivity.this,videoStoryTextView, MAX_LINES, languagePreference.getTextofLanguage( VIEW_MORE, DEFAULT_VIEW_MORE), true);

        } else {
            videoStoryTextView.setVisibility(View.GONE);
        }
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        );
    }


    private class AsyncResumeVideoLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;
        String watchSt = "halfplay";

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = APIUrlConstant.getVideoLogsUrl();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("content_type", "2");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (current_matching_time >= emVideoView.getDuration()) {
                            watchSt = "complete";
                        }

                    }

                });
                httppost.addHeader("watch_status", watchSt);


                // Following code is changed due to NewVideoLog API ;

                httppost.addHeader("played_length", "" + (playerPosition - player_start_time));
                httppost.addHeader("log_temp_id", log_temp_id);
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


    private class AsyncVideoBufferLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        int statusCode = 0;

        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = APIUrlConstant.getVideoBufferLogsUrl();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", authTokenStr.trim());
                httppost.addHeader("user_id", userIdStr);
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", movieId.trim());
                httppost.addHeader("episode_id", episodeId.trim());
                httppost.addHeader("content_type", "2");
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
                else
                    httppost.addHeader("totalBandwidth", "" + CurrentUsedData);

                Log.v("BIBHU", "Response of the bufferlog CurrentUsedData======#############=" + CurrentUsedData);

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
                    PreviousUsedData = prev_data;
                }
            }

        } catch (Exception e) {

        }
    }


    public void BufferBandWidth() {
        DataAsynTask dataAsynTask = new DataAsynTask();
        dataAsynTask.execute();
    }

    private class DataAsynTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            try {
                long total = 0;
                PackageManager pm = getPackageManager();
                List<PackageInfo> listPackages = pm.getInstalledPackages(0);
                for (PackageInfo pi : listPackages) {
                    String appName = (String) pi.applicationInfo.loadLabel(pm);
                    if (appName != null && appName.trim().equals(getResources().getString(R.string.app_name))) {
                        int uid = pi.applicationInfo.uid;
                        total = (TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidTxBytes(uid)) / 1024;

                        CurrentUsedData = total - PreviousUsedData;
                        CurrentUsedData = CurrentUsedData - (DataUsedByDownloadContent());

                        Log.v("BIBHU", "Current_total_UsedData==================" + total + " KB");
                        Log.v("BIBHU", "CurrentUsedData==================" + CurrentUsedData + " KB");

                    }
                }
            } catch (Exception e) {

            }

            return null;
        }
    }


    public long DataUsedByDownloadContent() {

        try {

            SQLiteDatabase DB = TrailerActivity.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor cursor = DB.rawQuery("SELECT " + DBHelper.COLUMN_DOWNLOADID + " FROM " + DBHelper.TABLE_NAME + " ", null);
            int count = cursor.getCount();

            long Total = 0;

            if (count > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        DownloadManager downloadManager1 = (DownloadManager) TrailerActivity.this.getSystemService(DOWNLOAD_SERVICE);
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


}