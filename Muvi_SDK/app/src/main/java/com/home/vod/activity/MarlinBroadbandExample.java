
package com.home.vod.activity;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.app.NotificationCompat;
import android.support.v7.media.MediaRouter;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import player.activity.ExoPlayerActivity;
import player.subtitle_support.Caption;
import player.subtitle_support.FormatSRT;
import player.subtitle_support.FormatSRT_WithoutCaption;
import player.subtitle_support.TimedTextObject;

import com.androidquery.AQuery;
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
import com.home.apisdk.APIUrlConstant;
import com.home.vod.util.FeatureHandler;
import com.intertrust.wasabi.Runtime;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.home.vod.R;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.SensorOrientationChangeNotifier;
import com.home.vod.util.Util;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.media.PlaylistProxy;
import com.intertrust.wasabi.media.PlaylistProxyListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import javax.net.ssl.HttpsURLConnection;

import player.activity.ResumePopupActivity;
import player.activity.SubtitleList;
import player.utils.DBHelper;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;




import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import static player.utils.Util.authTokenStr;


enum ContentTypes {
	DASH("application/dash+xml"), HLS("application/vnd.apple.mpegurl"), PDCF(
			"video/mp4"), M4F("video/mp4"), DCF("application/vnd.oma.drm.dcf"), BBTS(
			"video/mp2t");
	String mediaSourceParamsContentType = null;

	private ContentTypes(String mediaSourceParamsContentType) {
		this.mediaSourceParamsContentType = mediaSourceParamsContentType;
	}

	public String getMediaSourceParamsContentType() {
		return mediaSourceParamsContentType;
	}
}

public class MarlinBroadbandExample extends AppCompatActivity implements SensorOrientationChangeNotifier.Listener, PlaylistProxyListener {
	private static long tot = 0;
	PlaylistProxy playerProxy;
	int played_length = 0;
	int playerStartPosition = 0;
	boolean censor_layout = true;
	private static final int REQUEST_STORAGE = 1;
	Timer timer;
	static final String TAG = "SampleBBPlayer";
	private Handler threadHandler = new Handler();
	String videoLogId = "0";
	String watchStatus = "start";
	int playerPosition = 0;
	public boolean isFastForward = false;
	public int playerPreviousPosition = 0;
	TimerTask timerTask;
	String fname;
	int lenghtOfFile;
	int lengthfile;
	int seekBarProgress = 0;
	int id = 188;
	ImageView download;
	private static ProgressBar Progress;
	TextView percentg;
	Timer timerr;
	String download_content_type="";
	FeatureHandler featureHandler;
	boolean resume_orientation = false;




	int player_start_time = 0;
	String log_temp_id = "0";

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
	static String encrypt_file, genre;
	String resolution = "BEST";
	String genreTextView;
	String ipAddressStr = "";

	String files, filename, path, movieid, duration, rdate;
	// load asynctask

	SharedPreferences pref;
	//Toolbar mActionBarToolbar;
	LinearLayout linearLayout1;

	TextView videoTitle, GenreTextView, videoDurationTextView, videoCensorRatingTextView, videoCensorRatingTextView1, videoReleaseDateTextView,
			videoCastCrewTitleTextView;
	TextView story;

	private EMVideoView emVideoView;
	int seek_label_pos = 0;
	int content_types_id = 0;

	String emailIdStr = "";
	String title = "";
	String userIdStr = "";
	String movieId = "";
	String episodeId = "0";
	String mlvfile = "";
	String token = "";

	private long enqueue;
	private DownloadManager downloadManager;
	BroadcastReceiver receiver;
	private boolean downloading;
	AsyncVideoLogDetails asyncVideoLogDetails;

	String contid, muviid, gen, vidduration;

	private SubtitleProcessingTask subsFetchTask;
	public TimedTextObject srt;
	TextView subtitleText;
	public Handler subtitleDisplayHandler;
	ImageView subtitle_change_btn;

	ArrayList<String> SubTitleName = new ArrayList<>();
	ArrayList<String> SubTitlePath = new ArrayList<>();
	boolean callWithoutCaption = true;
	String download_id_from_watch_access_table = "";
	String PlayedLength = "0";
	int current_played_length = 0;
	String UniqueId = "";
	String streamId = "";
	String poster = "";
	String storydes = "";
	boolean stopcalling_onpause = false;
	NetworkStateReceiver networkStateReceiver;

	ArrayList<String> Chromecast_Subtitle_Url = new ArrayList<String>();
	ArrayList<String> Chromecast_Subtitle_Language_Name = new ArrayList<String>();
	ArrayList<String> Chromecast_Subtitle_Code = new ArrayList<String>();

	int corePoolSize = 60;
	int maximumPoolSize = 80;
	int keepAliveTime = 10;
	BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
	Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

	String MpdVideoUrl = "";
	String licenseUrl = "";

	Timer CheckAvailabilityOfChromecast;
	boolean video_prepared = false;
	MediaRouteButton mediaRouteButton;

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

	public PlaybackLocation mLocation;
	public PlaybackState mPlaybackState;
	private final float mAspectRatio = 72f / 128;
	private AQuery mAquery;
	private MediaInfo mSelectedMedia;


	private CastContext mCastContext;
	private SessionManagerListener<CastSession> mSessionManagerListener = null;
	public CastSession mCastSession = null;
	MediaInfo mediaInfo;


	RemoteMediaClient remoteMediaClient;
	long[] tracksArray;

	long cast_disconnected_position = 0;
	String active_track_index = "0";
	MediaRouter mMediaRouter = null;

	long DataUsedByChrmoeCast = 0;
	long Current_Sesion_DataUsedByChrmoeCast = 0;


	Animation myAnim;
	LinearLayout volume_brightness_control_layout,back_layout,cc_layout;
	ImageButton volume_brightness_control;
	TextView volume_bright_value;
	Window mWindow;
	AudioManager am;
	LanguagePreference languagePreference;
	PreferenceManager preferenceManager;
	String flag = "";

	Context castContext;
	Drawable drawable = null;
	TypedArray a;



	@Override
	protected void onResume() {
		super.onResume();
		resume_orientation = false;
		SensorOrientationChangeNotifier.getInstance(MarlinBroadbandExample.this).addListener(this);
		Util.app_is_in_player_context = true;

		CheckAvailabilityOfChromecast = new Timer();
		CheckAvailabilityOfChromecast.schedule(new TimerTask() {
			@Override
			public void run() {


				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						try{

						Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
						int orientation = display.getRotation();

						Log.v("PINTU", "CheckAvailabilityOfChromecast called orientation="+orientation);

						if (orientation == 1|| orientation == 3) {
							hideSystemUI();
						}}catch (Exception e){}

						SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
						Cursor cursor = DB.rawQuery("SELECT Flag FROM " + DBHelper.RESUME_WATCH + " WHERE UniqueId = '" + UniqueId + "'", null);
						int count = cursor.getCount();

						if (count > 0) {
							if (cursor.moveToFirst()) {
								do {
									flag = cursor.getString(0).trim();
								} while (cursor.moveToNext());
							}
						}

						Log.v("SUBHA", "Falg==" + flag);

						if(featureHandler.getFeatureStatus(FeatureHandler.CHROMECAST,FeatureHandler.DEFAULT_CHROMECAST))
						{
							if (flag.equals("1")) {
								if (video_prepared) {
									if (mediaRouteButton.isEnabled()) {
										mediaRouteButton.setVisibility(View.VISIBLE);
										Log.v("SUBHA", "called1");
									} else {
										mediaRouteButton.setVisibility(View.GONE);
										Log.v("SUBHA", "called11");
									}
								} else {
									mediaRouteButton.setVisibility(View.GONE);
									Log.v("SUBHA", "called111");
								}
							} else {
								mediaRouteButton.setVisibility(View.GONE);
								Log.v("SUBHA", "called1111");
							}
						}else{
							mediaRouteButton.setVisibility(View.GONE);
						}

					}
				});
			}
		}, 3000, 3000);

        AsynGetIpAddress asynGetIpAddress = new AsynGetIpAddress();
        asynGetIpAddress.executeOnExecutor(threadPoolExecutor);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final_exoplayer);
		languagePreference = LanguagePreference.getLanguagePreference(this);
		preferenceManager = PreferenceManager.getPreferenceManager(this);
		featureHandler = FeatureHandler.getFeaturePreference(MarlinBroadbandExample.this);

		networkStateReceiver = new NetworkStateReceiver();
		this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

		download_id_from_watch_access_table = getIntent().getStringExtra("download_id_from_watch_access_table").trim();
		PlayedLength = getIntent().getStringExtra("PlayedLength").trim();
		current_played_length = Integer.parseInt(PlayedLength);
		UniqueId = getIntent().getStringExtra("UniqueId").trim();
		streamId = getIntent().getStringExtra("streamId").trim();
		poster = getIntent().getStringExtra("poster").trim();
		storydes = getIntent().getStringExtra("story").trim();



		Chromecast_Subtitle_Url = getIntent().getStringArrayListExtra("Chromecast_Subtitle_Url");
		Chromecast_Subtitle_Language_Name = getIntent().getStringArrayListExtra("Chromecast_Subtitle_Language_Name");
		Chromecast_Subtitle_Code = getIntent().getStringArrayListExtra("Chromecast_Subtitle_Code");
		download_content_type = getIntent().getStringExtra("download_content_type");


		mediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
		//mediaRouteButton.setVisibility(View.INVISIBLE);

		contid = getIntent().getStringExtra("contid").trim();
		muviid = getIntent().getStringExtra("muvid").trim();
		gen = getIntent().getStringExtra("gen").trim();
		title = getIntent().getStringExtra("Title").trim();
		vidduration = getIntent().getStringExtra("vid").trim();


		content_types_id = Integer.parseInt(contid);
		genreTextView = gen;
		movieId = muviid;

		if (preferenceManager != null) {
			emailIdStr = preferenceManager.getEmailIdFromPref();
			userIdStr = preferenceManager.getUseridFromPref();

		} else {
			emailIdStr = "";
			userIdStr = "";
		}


		emVideoView = (EMVideoView) findViewById(R.id.emVideoView);
		cc_layout = (LinearLayout) findViewById(R.id.cc_layout);
		subtitleText = (TextView) findViewById(R.id.offLine_subtitleText);
		subtitle_change_btn = (ImageView) findViewById(R.id.subtitle_change_btn);
		back_layout = (LinearLayout) findViewById(R.id.back_layout);

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
		videoCensorRatingTextView.setVisibility(View.GONE);
		videoCensorRatingTextView1.setVisibility(View.GONE);



		download = (ImageView) findViewById(R.id.downloadImageView);
		Progress = (ProgressBar) findViewById(R.id.progressBar);
		percentg = (TextView) findViewById(R.id.percentage);
		back_layout = (LinearLayout) findViewById(R.id.back_layout);

		// Adding Chromecast in Offline Player


		/***************chromecast**********************/

		mAquery = new AQuery(this);
		setupCastListener();

		mCastContext = CastContext.getSharedInstance(this);
		mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
		mCastSession = CastContext.getSharedInstance(this).getSessionManager().getCurrentCastSession();
		mCastContext.getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);


		boolean shouldStartPlayback = false;
		int startPosition = 0;

		if (shouldStartPlayback) {
			// this will be the case only if we are coming from the
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


		castContext = new ContextThemeWrapper(MarlinBroadbandExample.this, android.support.v7.mediarouter.R.style.Theme_MediaRouter);
		a = castContext.obtainStyledAttributes(null, android.support.v7.mediarouter.R.styleable.MediaRouteButton, android.support.v7.mediarouter.R.attr.mediaRouteButtonStyle, 0);
		drawable = a.getDrawable(android.support.v7.mediarouter.R.styleable.MediaRouteButton_externalRouteEnabledDrawable);
		a.recycle();
		DrawableCompat.setTint(drawable, getResources().getColor(R.color.resumeTitleTextColor));


		CastButtonFactory.setUpMediaRouteButton(MarlinBroadbandExample.this, mediaRouteButton);
		mediaRouteButton.setRemoteIndicatorDrawable(drawable);

		/***************chromecast**********************/


		//============================================//

		// This code is responsible for change volume and brightness using swipe control .. & Player center buton animation...

		/*myAnim= AnimationUtils.loadAnimation(this, R.anim.bounce);
		am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		mWindow = getWindow();
		emVideoView.setOnTouchListener(clickFrameSwipeListener);

		volume_brightness_control_layout = (LinearLayout) findViewById(R.id.volume_brightness_control_layout);
		volume_brightness_control = (ImageButton) findViewById(R.id.volume_brightness_control);
		volume_bright_value = (TextView) findViewById(R.id.volume_bright_value);*/

		//===================End===================//

		//Call For Subtitle Loading // Added By Bibhu

		if (getIntent().getStringArrayListExtra("SubTitleName") != null) {
			SubTitleName = getIntent().getStringArrayListExtra("SubTitleName");
		} else {
			SubTitleName.clear();
		}

		if (getIntent().getStringArrayListExtra("SubTitlePath") != null) {
			SubTitlePath = getIntent().getStringArrayListExtra("SubTitlePath");
		} else {
			SubTitlePath.clear();
		}


		if(!featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE,FeatureHandler.DEFAULT_IS_SUBTITLE)){
			SubTitlePath.clear();
			Chromecast_Subtitle_Url.clear();
		}



		if (SubTitlePath.size() < 1) {
			subtitle_change_btn.setVisibility(View.INVISIBLE);
		}

		subtitle_change_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {

					if (SubTitlePath.size() < 1) {
						return;
					}

					Util.call_finish_at_onUserLeaveHint = false;
					Intent intent = new Intent(MarlinBroadbandExample.this, SubtitleList.class);
					intent.putExtra("SubTitleName", SubTitleName);
					intent.putExtra("SubTitlePath", SubTitlePath);
					startActivityForResult(intent, 222);
				} catch (Exception e) {
				}

			}
		});

		cc_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {

					if (SubTitlePath.size() < 1) {
						return;
					}

					Util.call_finish_at_onUserLeaveHint = false;
					Intent intent = new Intent(MarlinBroadbandExample.this, SubtitleList.class);
					intent.putExtra("SubTitleName", SubTitleName);
					intent.putExtra("SubTitlePath", SubTitlePath);
					startActivityForResult(intent, 222);
				} catch (Exception e) {
				}

			}
		});

		try{

			if (title != null && !title.matches("")) {
				videoTitle.setText(title);
				videoTitle.setVisibility(View.VISIBLE);
			} else {
				videoTitle.setVisibility(View.GONE);
			}

			if (gen != null && !gen.matches("")) {
				GenreTextView.setText(gen);
				GenreTextView.setVisibility(View.VISIBLE);
			} else {
				GenreTextView.setVisibility(View.GONE);
			}


			if (vidduration != null && !vidduration.matches("")) {
				videoDurationTextView.setText(vidduration);
				videoDurationTextView.setVisibility(View.VISIBLE);
				censor_layout = false;
			} else {
				videoDurationTextView.setVisibility(View.GONE);
			}

			if (storydes != null && !storydes.matches("")) {
				story.setText(storydes);
				story.setVisibility(View.VISIBLE);
			} else {
				story.setVisibility(View.GONE);
			}

		}catch (Exception e){}



		videoCastCrewTitleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (NetworkStatus.getInstance().isConnected(MarlinBroadbandExample.this)) {
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


					final Intent detailsIntent = new Intent(MarlinBroadbandExample.this, CastAndCrewActivity.class);
					detailsIntent.putExtra("cast_movie_id", movieId.trim());
					detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(detailsIntent);
				} else {
					Toast.makeText(getApplicationContext(), player.utils.Util.getTextofLanguage(MarlinBroadbandExample.this, player.utils.Util.NO_INTERNET_CONNECTION, player.utils.Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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

		seekBar = (SeekBar) findViewById(R.id.progress);
		center_play_pause = (ImageButton) findViewById(R.id.center_play_pause);

		current_time = (TextView) findViewById(R.id.current_time);
		total_time = (TextView) findViewById(R.id.total_time);
		progressView = (ProgressBar) findViewById(R.id.progress_view);


		Display display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();


		Util.player_description = true;


		LinearLayout.LayoutParams params1 = null;
		if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
			if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
				// showSystemUI();

			} else {
				//   showSystemUI();
				params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
			}
		} else {
			if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);
				// showSystemUI();
			} else {
				//showSystemUI();
				params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 40) / 100);
			}
		}
		player_layout.setLayoutParams(params1);

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

				asyncVideoLogDetails = new AsyncVideoLogDetails();
				asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				{
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

					// Call Updated Video Log Here .
					asyncVideoLogDetails = new AsyncVideoLogDetails();
					asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
				}








				mHandler.removeCallbacks(updateTimeTask);
				emVideoView.seekTo(seekBar.getProgress());
				current_time.setVisibility(View.VISIBLE);
				current_time.setVisibility(View.GONE);
				showCurrentTime();
				current_time.setVisibility(View.VISIBLE);
				updateProgressBar();
				if (playerPreviousPosition == 0) {
					if (playerStartPosition < emVideoView.getCurrentPosition()) {
						isFastForward = true;
						playerPreviousPosition = playerStartPosition;

					} else {
						playerPreviousPosition = playerStartPosition;
						isFastForward = false;
					}
				}
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


				try{

					Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
					int orientation = display.getRotation();

					Log.v("PINTU", "CheckAvailabilityOfChromecast called orientation="+orientation);

					if (orientation == 1|| orientation == 3) {
						hideSystemUI();
					}}catch (Exception e){}


				if (Util.hide_pause) {
					Util.hide_pause = false;
				}
				if (((ProgressBar) findViewById(R.id.progress_view)).getVisibility() == View.VISIBLE) {
					primary_ll.setVisibility(View.VISIBLE);
					center_play_pause.setVisibility(View.GONE);
					latest_center_play_pause.setVisibility(View.GONE);
					current_time.setVisibility(View.GONE);
					subtitle_change_btn.setVisibility(View.INVISIBLE);


				} else {
					if (primary_ll.getVisibility() == View.VISIBLE) {
						primary_ll.setVisibility(View.GONE);
						last_ll.setVisibility(View.GONE);
						center_play_pause.setVisibility(View.GONE);
						latest_center_play_pause.setVisibility(View.GONE);
						current_time.setVisibility(View.GONE);
						subtitle_change_btn.setVisibility(View.INVISIBLE);

						End_Timer();
					} else {


						primary_ll.setVisibility(View.VISIBLE);

						if (SubTitlePath.size() > 0) {
							subtitle_change_btn.setVisibility(View.VISIBLE);
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
						if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
							params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);

						} else {
							params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
						}
					} else {
						if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

//				latest_center_play_pause.startAnimation(myAnim);

				if (Util.hide_pause) {
					Util.hide_pause = false;
					latest_center_play_pause.setVisibility(View.GONE);
				}

				Execute_Pause_Play();
			}
		});


		emVideoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared() {


				video_prepared = true;
				video_completed = false;
				if (progressView != null) {
					((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.VISIBLE);
					center_play_pause.setVisibility(View.GONE);
					latest_center_play_pause.setVisibility(View.GONE);
				}


				try {
					if (content_types_id == 4) {
						if (SubTitlePath.size() > 0) {
							CheckSubTitleParsingType("1");
							subtitleDisplayHandler = new Handler();
							subsFetchTask = new SubtitleProcessingTask("1");
							subsFetchTask.execute();
						}

						emVideoView.start();
						updateProgressBar();
					} else {

						if (Long.parseLong(PlayedLength) > 0) {

							Log.v("BIBHU3", " called============");

							((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
							Util.call_finish_at_onUserLeaveHint = false;

							resume_orientation = true;
							Intent resumeIntent = new Intent(MarlinBroadbandExample.this, ResumePopupActivity.class);
							resumeIntent.putExtra("activity","MarlinBroadbandExample");
							startActivityForResult(resumeIntent, 1001);

						} else {

							Log.v("BIBHU3", " called 1============");

							emVideoView.start();
							seekBar.setProgress(emVideoView.getCurrentPosition());
							updateProgressBar();

							if (SubTitlePath.size() > 0) {
								Log.v("BIBHU3", "parsing called============");

								CheckSubTitleParsingType("1");
								subtitleDisplayHandler = new Handler();
								subsFetchTask = new SubtitleProcessingTask("1");
								subsFetchTask.execute();
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

			}
		});
		back_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				backCalled();

			}
		});

		back_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				backCalled();

			}
		});

		path = getIntent().getStringExtra("FILE").trim();
		token = getIntent().getStringExtra("TOK").trim();
		title = getIntent().getStringExtra("Title").trim();

		if(download_content_type.trim().equals("1")){
			try {
            /*
             * Initialize the Wasabi Runtime (necessary only once for each
			 * instantiation of the application)
			 *
			 * ** Note: Set Runtime Properties as needed for your environment
			 */
				Runtime.initialize(getDir("wasabi", MODE_PRIVATE).getAbsolutePath());
            /*
             * Personalize the application (acquire DRM keys). This is only
			 * necessary once each time the application is freshly installed
			 *
			 * ** Note: personalize() is a blocking call and may take long
			 * enough to complete to trigger ANR (Application Not Responding)
			 * errors. In a production application this should be called in a
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



			String licenseAcquisitionToken = getActionTokenFromStorage(token);
			if (licenseAcquisitionToken == null) {
				Log.e(TAG,"Could not find action token in the assets directory - exiting");


			}
			com.intertrust.wasabi.jni.Runtime.processServiceToken(licenseAcquisitionToken);
			try {
				EnumSet<PlaylistProxy.Flags> flags = EnumSet.noneOf(PlaylistProxy.Flags.class);
				playerProxy = new PlaylistProxy(flags, this, new Handler());
				playerProxy.start();
			} catch (ErrorCodeException e) {

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
				String dash_url = path;
				ContentTypes contentType = ContentTypes.DASH;
				PlaylistProxy.MediaSourceParams params = new PlaylistProxy.MediaSourceParams();
				params.sourceContentType = contentType
						.getMediaSourceParamsContentType();
			/*
			 * if the content has separate audio tracks (eg languages) you may
			 * select one using MediaSourceParams, eg params.language="es";
			 */


				try {
					String proxy_url = playerProxy.makeUrl(dash_url, PlaylistProxy.MediaSourceType.SINGLE_FILE, new PlaylistProxy.MediaSourceParams());
					emVideoView.setVideoURI(Uri.parse(proxy_url));

				} catch (Exception e) {
					// Consult WasabiErrors.txt for resolution of the error codes
					Log.e(TAG, "playback error: " + e.getLocalizedMessage());
					e.printStackTrace();

				}


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
		}else
		{
			emVideoView.setVideoPath(path);
		}




		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));


	}


	private String getActionTokenFromStorage(String tokenFileName) {
		String token = null;
		byte[] readBuffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		int bytesRead = 0;

		try {
			is = new FileInputStream(tokenFileName);
				/*is = getActivity().getAssets()
						.open(tokenFileName, MODE_PRIVATE);*/
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


	@Override
	public void onErrorNotification(int i, String s) {

	}


	@Override
	public void onOrientationChange(int orientation) {

		if(resume_orientation){
			Util.player_description = true;
			return;
		}

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
		} else if (orientation == 180) {

			Util.player_description = true;

			LinearLayout.LayoutParams params1 = null;
			if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
				if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);

				} else {
					params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
				}
			} else {
				if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

			//current_time.setVisibility(View.GONE);

		} else if (orientation == 0) {

			Util.player_description = true;
			LinearLayout.LayoutParams params1 = null;
			if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
				if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);

				} else {
					params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (screenHeight * 45) / 100);
				}
			} else {
				if (MarlinBroadbandExample.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

			//current_time.setVisibility(View.GONE);
		}

		current_time_position_timer();

	}


	private void updateProgressBar() {
		mHandler.postDelayed(updateTimeTask, 1000);
	}

	private Runnable updateTimeTask = new Runnable() {
		public void run() {
			seekBar.setProgress(emVideoView.getCurrentPosition());
			seekBarProgress = emVideoView.getCurrentPosition();
			current_played_length = emVideoView.getCurrentPosition();
			seekBar.setMax(emVideoView.getDuration());
			Calcute_Currenttime_With_TotalTime();
			mHandler.postDelayed(this, 1000);

			if (content_types_id != 4) {
				showCurrentTime();
			}

			current_matching_time = emVideoView.getCurrentPosition();
			playerPosition = millisecondsToString(emVideoView.getCurrentPosition());

			int duration = emVideoView.getDuration() / 1000;
			/*if (currentPositionStr > 0 && currentPositionStr == duration) {
				asyncVideoLogDetails = new AsyncVideoLogDetails();
				watchStatus = "complete";
				asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);*/


			if (player.utils.Util.checkNetwork(MarlinBroadbandExample.this)) {
				if (emVideoView.getCurrentPosition() > 0 && ((millisecondsToString(emVideoView.getCurrentPosition())) % 60) == 0) {

					watchStatus = "halfplay";
					asyncVideoLogDetails = new AsyncVideoLogDetails();
					asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);

				}
			}




			if ((previous_matching_time == current_matching_time) && (current_matching_time < emVideoView.getDuration())) {
				((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.VISIBLE);

				primary_ll.setVisibility(View.GONE);
				last_ll.setVisibility(View.GONE);
				center_play_pause.setVisibility(View.GONE);
				latest_center_play_pause.setVisibility(View.GONE);
				current_time.setVisibility(View.GONE);
				subtitle_change_btn.setVisibility(View.INVISIBLE);

				previous_matching_time = current_matching_time;
			} else {

				if (content_types_id == 4) {

				} else {
					if (current_matching_time >= emVideoView.getDuration()) {
						mHandler.removeCallbacks(updateTimeTask);
						seekBar.setProgress(0);
						current_time.setText("00:00:00");
						total_time.setText("00:00:00");
						previous_matching_time = 0;
						current_matching_time = 0;
						video_completed = true;

						// Resume watch table is updated because of resume watch feature..
						SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);

						String Qry1 = "UPDATE " + DBHelper.RESUME_WATCH + " SET Flag = '0' ,PlayedDuration = '0'  WHERE UniqueId = '" + UniqueId + "'";
						DB.execSQL(Qry1);

						stopcalling_onpause = true;

						Log.v("BIBHU1", "Update called UniqueId=" + UniqueId);

						//==========================End============================//
						if (player.utils.Util.checkNetwork(MarlinBroadbandExample.this)) {
							asyncVideoLogDetails = new AsyncVideoLogDetails();
							watchStatus = "complete";
							asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
						}

						backCalled();
					}
				}

				previous_matching_time = current_matching_time;
				((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
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

		if (progressView != null && progressView.isShown()) {
			progressView = null;
		}


		mHandler.removeCallbacks(updateTimeTask);
		if (emVideoView != null) {
			emVideoView.release();
		}
		finish();
		overridePendingTransition(0, 0);
	}

	public void backCalled() {
		finish();
	}


	@Override
	protected void onUserLeaveHint() {


		if (progressView != null && progressView.isShown()) {
			progressView = null;
		}


		if (Util.call_finish_at_onUserLeaveHint) {

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

							timer.cancel();

							current_time.setText(Current_Time);
							double pourcent = seekBar.getProgress() / (double) seekBar.getMax();
							int offset = seekBar.getThumbOffset();
							int seekWidth = seekBar.getWidth();
							int val = (int) Math.round(pourcent * (seekWidth - 2 * offset));
							int labelWidth = current_time.getWidth();
							current_time.setX(offset + seekBar.getX() + val
									- Math.round(pourcent * offset)
									- Math.round(pourcent * labelWidth / 2));


						}
					}
				});
			}
		}, 0, 100);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		stopcalling_onpause = false;

		if (resultCode == RESULT_OK) {
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
						}

					}

				}
			}

			if (requestCode == 1001) {

				Util.call_finish_at_onUserLeaveHint = true;


				if (data.getStringExtra("yes").equals("1002")) {

					watchStatus = "halfplay";
					emVideoView.start();

					playerPosition = millisecondsToString(Integer.parseInt(PlayedLength));
					player_start_time = playerPosition;

					emVideoView.seekTo(Integer.parseInt(PlayedLength));
					seekBar.setProgress(Integer.parseInt(PlayedLength));
					updateProgressBar();




				} else {
					emVideoView.start();
					seekBar.setProgress(emVideoView.getCurrentPosition());
					updateProgressBar();
				}

				if (SubTitlePath.size() > 0) {
					Log.v("BIBHU3", "parsing called============");

					CheckSubTitleParsingType("1");
					subtitleDisplayHandler = new Handler();
					subsFetchTask = new SubtitleProcessingTask("1");
					subsFetchTask.execute();
				}
			}

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		try{
			if (emVideoView.getCurrentPosition() > 0 && (emVideoView.getCurrentPosition() >= emVideoView.getDuration())) {
				watchStatus = "complete";
				asyncVideoLogDetails = new AsyncVideoLogDetails();
				asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
			} else {
				watchStatus = "halfplay";
				asyncVideoLogDetails = new AsyncVideoLogDetails();
				asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
			}

		}catch (Exception e){}



		mCastContext.getSessionManager().removeSessionManagerListener(mSessionManagerListener, CastSession.class);

		// Resume watch table is updated because of resume watch feature..

		if (!stopcalling_onpause) {
			SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
			String Qry1 = "UPDATE " + DBHelper.RESUME_WATCH + " SET Flag = '0' , PlayedDuration = '" + emVideoView.getCurrentPosition() + "'" +
					" WHERE UniqueId = '" + UniqueId + "'";
			DB.execSQL(Qry1);
		}

		//==========================End============================//


		Util.hide_pause = false;
		Util.app_is_in_player_context = false;
		this.unregisterReceiver(networkStateReceiver);

		try{

			a = castContext.obtainStyledAttributes(null, android.support.v7.mediarouter.R.styleable.MediaRouteButton, android.support.v7.mediarouter.R.attr.mediaRouteButtonStyle, 0);
			drawable = a.getDrawable(android.support.v7.mediarouter.R.styleable.MediaRouteButton_externalRouteEnabledDrawable);
			a.recycle();
			DrawableCompat.setTint(drawable, getResources().getColor(R.color.chromecast_color));

			CastButtonFactory.setUpMediaRouteButton(MarlinBroadbandExample.this, mediaRouteButton);
			mediaRouteButton.setRemoteIndicatorDrawable(drawable);

		}catch (Exception e){}


	}

	// Added Later By Bibhu For Subtitle Feature.

	public class SubtitleProcessingTask extends AsyncTask<Void, Void, Void> {
		String Subtitle_Path = "";

		public SubtitleProcessingTask(String path) {

			Subtitle_Path = SubTitlePath.get((Integer.parseInt(path) - 1));
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// int count;
			try {

				File myFile = new File(Subtitle_Path);
				InputStream fIn = new FileInputStream(String.valueOf(myFile));


				if (callWithoutCaption) {
					FormatSRT_WithoutCaption formatSRT = new FormatSRT_WithoutCaption();
					srt = formatSRT.parseFile("sample", fIn);
				} else {
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
			}

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
		super.onPause();

		if (CheckAvailabilityOfChromecast != null)
			CheckAvailabilityOfChromecast.cancel();

		// Update Data Base Here for Restriction ondownload content.
		SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);

		String Qry = "UPDATE " + DBHelper.WATCH_ACCESS_INFO + " SET updated_server_current_time = '" + System.currentTimeMillis() + "'" +
				" WHERE download_id = '" + download_id_from_watch_access_table + "' ";
		DB.execSQL(Qry);

		//==========================End============================//


		if (Util.call_finish_at_onUserLeaveHint) {
			finish();
		}
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
		Log.v("BIBHU3", "parsing called CheckSubTitleParsingType============" + Subtitle_Path);

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

        story.setText(storydes);

		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE

		);
	}


	public class NetworkStateReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// Toast.makeText(context,"Receiver Called",Toast.LENGTH_SHORT).show();

			if (NetworkStatus.getInstance().isConnected(MarlinBroadbandExample.this)) {
				{
					SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);

					Cursor cursor = DB.rawQuery("SELECT * FROM " + DBHelper.RESUME_WATCH + " WHERE UniqueId = '" + UniqueId + "'", null);
					int count = cursor.getCount();

					if (count > 0) {
						if (cursor.moveToFirst()) {
							do {
								flag = cursor.getString(3).trim();
								Log.v("SUBHA", "flag=====**=======" + flag);
								MpdVideoUrl = cursor.getString(2).trim();
								licenseUrl = cursor.getString(4).trim();

							} while (cursor.moveToNext());
						}
					}


					if (!flag.equals("1")) {
						// Call API for Ip Address Here .
						AsynGetIpAddress asynGetIpAddress = new AsynGetIpAddress();
						asynGetIpAddress.executeOnExecutor(threadPoolExecutor);
					}


					/******************************************************************************/

				}
			}
		}
	}

		private class AsynGetIpAddress extends AsyncTask<Void, Void, Void> {
			String responseStr;


			@Override
			protected Void doInBackground(Void... params) {

				try {

					// Execute HTTP Post Request
					try {
						URL myurl = new URL(APIUrlConstant.IP_ADDRESS_URL);
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

					} catch (Exception e) {
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

				if (!flag.equals("1")) {
					// Calling GetVideo Details API to get Latest Url Details.

					AsynLoadVideoUrls asynLoadVideoUrls = new AsynLoadVideoUrls();
					asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
				}
			}

			protected void onPreExecute() {
			}
		}


		private class AsynLoadVideoUrls extends AsyncTask<Void, Void, Void> {
			String responseStr;
			int statusCode;

			// This is added because of change in simultaneous login feature
			String message;
			boolean play_video = true;

			// ================================== End ====================================//
			@Override
			protected Void doInBackground(Void... params) {
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(APIUrlConstant.VIDEO_DETAILS_URL);
					httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
					httppost.addHeader("authToken", authTokenStr.trim());
					httppost.addHeader("content_uniq_id", muviid);
					httppost.addHeader("stream_uniq_id", streamId);
					httppost.addHeader("internet_speed", MainActivity.internetSpeed.trim());
					httppost.addHeader("user_id",userIdStr);
					httppost.addHeader("lang_code", player.utils.Util.getTextofLanguage(MarlinBroadbandExample.this, player.utils.Util.SELECTED_LANGUAGE_CODE, player.utils.Util.DEFAULT_SELECTED_LANGUAGE_CODE));


					Log.v("SUBHA", "authToken = " + authTokenStr.trim());
					Log.v("SUBHA", "content_uniq_id = " + muviid);
					Log.v("SUBHA", "stream_uniq_id = " + streamId);
					Log.v("SUBHA", "user_id = " +userIdStr);

					// Execute HTTP Post Request
					try {

						HttpResponse response = httpclient.execute(httppost);
						responseStr = EntityUtils.toString(response.getEntity());

					} catch (Exception e) {
						responseStr = "0";
					}

					Log.v("SUBHA", "response = " + responseStr);
					JSONObject myJson = null;
					JSONArray SubtitleJosnArray = null;
					if (responseStr != null) {
						myJson = new JSONObject(responseStr);
						SubtitleJosnArray = myJson.optJSONArray("subTitle");
						statusCode = Integer.parseInt(myJson.optString("code"));
						message = myJson.optString("msg");
						// ================================== End ====================================//
					}

					if (statusCode >= 0) {
						if (statusCode == 200) {

							MpdVideoUrl = "";
							licenseUrl = "";

							if ((myJson.has("videoUrl")) && myJson.getString("videoUrl").trim() != null && !myJson.getString("videoUrl").trim().isEmpty() && !myJson.getString("videoUrl").trim().equals("null") && !myJson.getString("videoUrl").trim().matches("")) {
								MpdVideoUrl = myJson.getString("videoUrl").trim();

								if (MpdVideoUrl.equals(""))
									responseStr = "0";


								if ((myJson.has("licenseUrl")) && myJson.getString("licenseUrl").trim() != null && !myJson.getString("licenseUrl").trim().isEmpty() && !myJson.getString("licenseUrl").trim().equals("null") && !myJson.getString("licenseUrl").trim().matches("")) {
									licenseUrl = myJson.optString("licenseUrl");
								}

								SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);

								String Qry1 = "UPDATE " + DBHelper.RESUME_WATCH + " SET LicenceUrl = '" + licenseUrl + "' , Flag = '1' ,LatestMpdUrl = '" + MpdVideoUrl + "'  WHERE UniqueId = '" + UniqueId + "'";
								DB.execSQL(Qry1);
							} else {
								responseStr = "0";
							}

							if (SubtitleJosnArray != null) {
								if (SubtitleJosnArray.length() > 0) {
									Chromecast_Subtitle_Url.clear();
									Chromecast_Subtitle_Language_Name.clear();
									Chromecast_Subtitle_Code.clear();

									for (int i = 0; i < SubtitleJosnArray.length(); i++) {
										Log.v("BIBHU", "Sutitle name = " + SubtitleJosnArray.getJSONObject(i).optString("language").trim());
										Log.v("BIBHU", "Sutitle path = " + SubtitleJosnArray.getJSONObject(i).optString("url").trim());
										Log.v("BIBHU", "Sutitle code = " + SubtitleJosnArray.getJSONObject(i).optString("code").trim());

										Chromecast_Subtitle_Url.add(SubtitleJosnArray.getJSONObject(i).optString("url").trim());
										Chromecast_Subtitle_Language_Name.add(SubtitleJosnArray.getJSONObject(i).optString("language").trim());
										Chromecast_Subtitle_Code.add(SubtitleJosnArray.getJSONObject(i).optString("code").trim());
									}
								}
							}
							// ================================== End ====================================//
						}
					} else {
						responseStr = "0";
					}
				} catch (Exception e1) {
					responseStr = "0";
				}
				return null;
			}

			protected void onPostExecute(Void result) {
			}

			@Override
			protected void onPreExecute() {
			}

		}


		public void XXXX(){}
		public void setupCastListener() {
			mSessionManagerListener = new SessionManagerListener<CastSession>() {

				@Override
				public void onSessionEnded(CastSession session, int error) {

					onApplicationDisconnected();

					if (!Util.app_is_in_player_context) {
						Log.v("BIBHU2", "context not matched");
						return;
					}
					//mSessionManagerListener  = null;
					mCastSession = null;
					Util.call_finish_at_onUserLeaveHint = true;
					latest_center_play_pause.setEnabled(true);
					emVideoView.setEnabled(true);
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
					try {

						cast_disconnected_position = session.getRemoteMediaClient().getApproximateStreamPosition();
						DataUsedByChrmoeCast = Current_Sesion_DataUsedByChrmoeCast + DataUsedByChrmoeCast;
						Current_Sesion_DataUsedByChrmoeCast = 0;

						// This is done because , during cast ending receiver already closed the streaming restriction for this user , so we have to
						// satrt a new streaming restriction at sender end.

						session.getRemoteMediaClient().getMediaInfo().getMetadata();
						Log.v("BIBHU3", "onSessionEnding===================" + cast_disconnected_position);
						Log.v("BIBHU3", "onSessionEnding DataUsedByChrmoeCast===================" + DataUsedByChrmoeCast);
					} catch (Exception e) {
					}

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


					try {
						castSession.setMessageReceivedCallbacks("urn:x-cast:muvi.mcrt.final", new Cast.MessageReceivedCallback() {
							@Override
							public void onMessageReceived(CastDevice castDevice, String s, String s1) {
								Log.v("bibhu", "onMessageReceived Message from receiver=" + s1);


								if (s1.contains("completed")) {
									Log.v("bibhu", "video completed at chromecast");
									runOnUiThread(new Runnable() {
										@Override
										public void run() {

											stopcalling_onpause = true;

											SQLiteDatabase DB = MarlinBroadbandExample.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
											String Qry1 = "UPDATE " + DBHelper.RESUME_WATCH + " SET Flag = '0' , PlayedDuration = '0' " +
													" WHERE UniqueId = '" + UniqueId + "'";
											DB.execSQL(Qry1);

											finish();

										}
									});
								} else {
									try {
										JSONObject jsonObject = new JSONObject(s1);
										videoLogId = jsonObject.optString("video_log_id");
//                                    videoBufferLogId = jsonObject.optString("bandwidth_log_id");
										Current_Sesion_DataUsedByChrmoeCast = Long.parseLong(jsonObject.optString("bandwidth"));


										Log.v("BIBHU1234", "videoLogId========" + videoLogId);
										Log.v("BIBHU1234", "Current_Sesion_DataUsedByChrmoeCast========" + Current_Sesion_DataUsedByChrmoeCast);


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


					//Will Add Some Data to send
					Util.call_finish_at_onUserLeaveHint = false;
					Util.hide_pause = true;
					((ProgressBar) findViewById(R.id.progress_view)).setVisibility(View.GONE);
					latest_center_play_pause.setVisibility(View.VISIBLE);
					subtitleText.setText("");
					emVideoView.setEnabled(false);

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

				public void onApplicationDisconnected() {
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

		public void PlayUsingCsat() {


			MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
			movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, "");
			movieMetadata.putString(MediaMetadata.KEY_TITLE, title);
			movieMetadata.addImage(new WebImage(Uri.parse(poster)));


			String mediaContentType = "videos/mp4";
			if (MpdVideoUrl.contains(".mpd")) {
				mediaContentType = "application/dash+xml";
				JSONObject jsonObj = null;
				try {
					jsonObj = new JSONObject();
					jsonObj.put("description", title);
					jsonObj.put("licenseUrl", licenseUrl);
					jsonObj.put("active_track_index", active_track_index);

					//  This Code Is Added For Video Log By Bibhu..

					jsonObj.put("authToken", authTokenStr.trim());
					jsonObj.put("user_id", userIdStr);
					jsonObj.put("ip_address", ipAddressStr.trim());
					jsonObj.put("movie_id", muviid);
					jsonObj.put("episode_id", streamId);

					jsonObj.put("watch_status", watchStatus);
					jsonObj.put("device_type", "2");
					jsonObj.put("log_id", videoLogId);

					if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
						jsonObj.put("restrict_stream_id", "0");
						jsonObj.put("is_streaming_restriction", "1");
						Log.v("BIBHU4", "restrict_stream_id============1");
					} else {
						jsonObj.put("restrict_stream_id", "0");
						jsonObj.put("is_streaming_restriction", "0");
						Log.v("BIBHU4", "restrict_stream_id============0");
					}

					jsonObj.put("domain_name", player.utils.Util.rootUrl().trim().substring(0, player.utils.Util.rootUrl().trim().length() - 6));
					jsonObj.put("is_log", "1");

					// This code is changed according to new Video log //

					jsonObj.put("played_length", "0");
					jsonObj.put("log_temp_id", "0");
					jsonObj.put("resume_time", "" + (playerPosition));
					jsonObj.put("seek_status", "first_time");


					//=====================End===================//


					// This  Code Is Added For Drm BufferLog By Bibhu ...

					jsonObj.put("resolution", "BEST");
					jsonObj.put("start_time", String.valueOf(playerPosition));
					jsonObj.put("end_time", String.valueOf(playerPosition));
					jsonObj.put("log_unique_id", "0"); // hv 2 chk
					jsonObj.put("bandwidth_log_id", "0"); // hv 2 chk
					jsonObj.put("location", "0");
					jsonObj.put("video_type", "mped_dash");

//                jsonObj.put("drm_bandwidth_by_sender",""+((DataUsedByChrmoeCast)*1024));
					jsonObj.put("drm_bandwidth_by_sender", "0");

					//====================End=====================//

				} catch (JSONException e) {
				}

				List tracks = new ArrayList();

				Log.v("BIBHU", "url size============" + Chromecast_Subtitle_Url.size());
				if (Chromecast_Subtitle_Url.size() > 0) {
					for (int i = 0; i < Chromecast_Subtitle_Url.size(); i++) {

						MediaTrack mediaTrack = new MediaTrack.Builder(i,
								MediaTrack.TYPE_TEXT)
								.setName(Chromecast_Subtitle_Language_Name.get(i))
								.setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
								.setContentId(Chromecast_Subtitle_Url.get(i))
								.setLanguage(Chromecast_Subtitle_Code.get(i))
								.setContentType("text/vtt")
								.build();

						tracks.add(mediaTrack);
					}
				}

				mediaInfo = new MediaInfo.Builder(MpdVideoUrl.trim())
						.setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
						.setContentType(mediaContentType)
						.setMetadata(movieMetadata)
						.setCustomData(jsonObj)
						.setMediaTracks(tracks)
						.build();
				mSelectedMedia = mediaInfo;
				togglePlayback();
			}
			else
			{
				mediaContentType = "videos/mp4";
				JSONObject jsonObj = null;
				try {
					jsonObj = new JSONObject();
					jsonObj.put("description", title);
					jsonObj.put("active_track_index", active_track_index);

					//  This Code Is Added For Video Log By Bibhu..

					jsonObj.put("authToken", authTokenStr.trim());
					jsonObj.put("user_id", userIdStr);
					jsonObj.put("ip_address", ipAddressStr.trim());
					jsonObj.put("movie_id", muviid);
					jsonObj.put("episode_id", "0");

					jsonObj.put("watch_status", watchStatus);
					jsonObj.put("device_type", "2");
					jsonObj.put("log_id", videoLogId);

					if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
						jsonObj.put("restrict_stream_id", "0");
						jsonObj.put("is_streaming_restriction", "1");
						Log.v("BIBHU4", "restrict_stream_id============1");
					} else {
						jsonObj.put("restrict_stream_id", "0");
						jsonObj.put("is_streaming_restriction", "0");
						Log.v("BIBHU4", "restrict_stream_id============0");
					}

					jsonObj.put("domain_name", player.utils.Util.rootUrl().trim().substring(0, player.utils.Util.rootUrl().trim().length() - 6));
					jsonObj.put("is_log", "1");

					// This code is changed according to new Video log //

					jsonObj.put("played_length", "0");
					jsonObj.put("log_temp_id", "0");
					jsonObj.put("resume_time", "" + (playerPosition));
					jsonObj.put("seek_status", "first_time");


					//=====================End===================//


					// This  Code Is Added For Drm BufferLog By Bibhu ...

					jsonObj.put("resolution", "BEST");
					jsonObj.put("start_time", String.valueOf(playerPosition));
					jsonObj.put("end_time", String.valueOf(playerPosition));
					jsonObj.put("log_unique_id", "0");
					jsonObj.put("bandwidth_log_id", "0");
					jsonObj.put("location", "0");
					jsonObj.put("video_type", "");

//                jsonObj.put("drm_bandwidth_by_sender",""+((DataUsedByChrmoeCast)*1024));
					jsonObj.put("drm_bandwidth_by_sender", "0");

					//====================End=====================//

				} catch (JSONException e) {
				}

				List tracks = new ArrayList();

				Log.v("BIBHU", "url size============" + Chromecast_Subtitle_Url.size());
				if (Chromecast_Subtitle_Url.size() > 0) {
					for (int i = 0; i < Chromecast_Subtitle_Url.size(); i++) {

						MediaTrack mediaTrack = new MediaTrack.Builder(i,
								MediaTrack.TYPE_TEXT)
								.setName(Chromecast_Subtitle_Language_Name.get(i))
								.setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
								.setContentId(Chromecast_Subtitle_Url.get(i))
								.setLanguage(Chromecast_Subtitle_Code.get(i))
								.setContentType("text/vtt")
								.build();

						tracks.add(mediaTrack);
					}
				}


				mediaInfo = new MediaInfo.Builder(MpdVideoUrl.trim())
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


		/***************
		 * chromecast
		 **********************/


		// This code is responsible for change volume and brightness using swipe control ..

	/*OnSwipeTouchListener clickFrameSwipeListener = new OnSwipeTouchListener(){

		int startVolume;
		int maxVolume;
		int startBrightness;
		int maxBrightness;

		@Override
		public void onMove(Direction dir, float diff) {


			if(dir == Direction.LEFT || dir == Direction.RIGHT) {

				// Here we have to implement seek control in player using finger swiping.

			}else
			{
				if(initialX >= emVideoView.getWidth()/2 || mWindow==null) {
					float diffVolume;
					int finalVolume;

					diffVolume = (float) maxVolume * diff / ((float) emVideoView.getHeight() / 2);
					if (dir == Direction.DOWN) {
						diffVolume = -diffVolume;
					}
					finalVolume = startVolume + (int) diffVolume;
					if (finalVolume < 0)
						finalVolume = 0;
					else if (finalVolume > maxVolume)
						finalVolume = maxVolume;

					am.setStreamVolume(AudioManager.STREAM_MUSIC, finalVolume, 0);

					volume_brightness_control.setImageResource(R.drawable.volume);
					volume_bright_value.setText(""+finalVolume);
					volume_brightness_control_layout.setVisibility(View.VISIBLE);
					latest_center_play_pause.setVisibility(View.INVISIBLE);

				}
				else if(initialX < emVideoView.getWidth()/2){
					float diffBrightness;
					int finalBrightness;

					diffBrightness = (float) maxBrightness * diff / ((float) emVideoView.getHeight() / 2);
					if (dir == Direction.DOWN) {
						diffBrightness = -diffBrightness;
					}
					finalBrightness = startBrightness + (int) diffBrightness;
					if (finalBrightness < 0)
						finalBrightness = 0;
					else if (finalBrightness > maxBrightness)
						finalBrightness = maxBrightness;

					WindowManager.LayoutParams layout = mWindow.getAttributes();
					layout.screenBrightness = (float)finalBrightness / 100;
					mWindow.setAttributes(layout);

					volume_brightness_control.setImageResource(R.drawable.brightness);
					volume_bright_value.setText(""+finalBrightness);
					volume_brightness_control_layout.setVisibility(View.VISIBLE);
					latest_center_play_pause.setVisibility(View.INVISIBLE);


                *//*PreferenceManager.getDefaultSharedPreferences(getContext()) .edit()
                        .putInt(BETTER_VIDEO_PLAYER_BRIGHTNESS, finalBrightness)
                        .apply();*//*
				}
			}

		}

		@Override
		public void onClick() {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {


					{

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
								if (SubTitlePath.size() > 0) {
									subtitle_change_btn.setVisibility(View.VISIBLE);
								}

								// This is changed Later

								if (mediaRouteButton.isEnabled()) {
									mediaRouteButton.setVisibility(View.VISIBLE);
								} else {
									mediaRouteButton.setVisibility(View.GONE);
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


				}
			});

		}

		@Override
		public void onAfterMove() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					volume_brightness_control_layout.setVisibility(View.GONE);
					if (primary_ll.getVisibility() == View.VISIBLE && ((ProgressBar) findViewById(R.id.progress_view)).getVisibility() != View.VISIBLE) {
						latest_center_play_pause.setVisibility(View.VISIBLE);
					}
				}
			});
		}

		@Override
		public void onBeforeMove(Direction dir) {

			if(dir == Direction.LEFT || dir == Direction.RIGHT) {
			}
			else{
				maxBrightness = 100;
				if(mWindow!=null) {
					startBrightness = (int) (mWindow.getAttributes().screenBrightness * 100);
				}
				maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				startVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			}
		}
	};*/

		//=========================================End========================================//

		private int millisecondsToString(int milliseconds) {
			// int seconds = (int) (milliseconds / 1000) % 60 ;
			int seconds = (int) (milliseconds / 1000);

			return seconds;
		}


	/**
	 * Following code is responsible for Synchronization of resume watch in downloaded video .
	 */


	private class AsyncVideoLogDetails extends AsyncTask<Void, Void, Void> {
		//  ProgressDialog pDialog;
		String responseStr;
		int statusCode = 0;

		@Override
		protected Void doInBackground(Void... params) {

			String urlRouteList = player.utils.Util.rootUrl().trim() + player.utils.Util.videoLogUrl.trim();
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlRouteList);
				httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
				httppost.addHeader("authToken", player.utils.Util.authTokenStr.trim());
				httppost.addHeader("user_id", userIdStr.trim());
				httppost.addHeader("ip_address", ipAddressStr.trim());
				httppost.addHeader("movie_id", movieId.trim());
				httppost.addHeader("episode_id", streamId.trim());
				httppost.addHeader("watch_status", watchStatus);
				httppost.addHeader("device_type", "2");
				httppost.addHeader("log_id", videoLogId);

				Log.v("BIBHU6", "authToken=" + player.utils.Util.authTokenStr.trim());
				Log.v("BIBHU6", "user_id=" + userIdStr.trim());
				Log.v("BIBHU6", "ip_address=" + ipAddressStr.trim());
				Log.v("BIBHU6", "movie_id=" + movieId.trim());
				Log.v("BIBHU6", "episode_id=" + episodeId.trim());
				Log.v("BIBHU6", "played_length=" + String.valueOf(playerPosition));
				Log.v("BIBHU6", "watch_status=" + watchStatus);
				Log.v("BIBHU6", "device_type=" + "2");
				Log.v("BIBHU6", "log_id=" + videoLogId);



				// This is done only because of , streaming restriction is not applicable on downloaded videos.

				httppost.addHeader("is_streaming_restriction", "0");
				httppost.addHeader("restrict_stream_id", "0");


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
//						restrict_stream_id = myJson.optString("restrict_stream_id");
//						Log.v("BIBHU", "responseStr of restrict_stream_id============" + restrict_stream_id);


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
		}

		@Override
		protected void onPreExecute() {
		}
	}


}
