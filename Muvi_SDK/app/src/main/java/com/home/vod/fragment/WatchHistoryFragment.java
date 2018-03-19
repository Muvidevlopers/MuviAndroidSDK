package com.home.vod.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.androidquery.AQuery;
import com.daimajia.slider.library.SliderLayout;
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
import com.home.apisdk.apiController.GetIpAddressAsynTask;
import com.home.apisdk.apiController.GetValidateUserAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiController.WatchHistoryAsynTask;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.ValidateUserInput;
import com.home.apisdk.apiModel.ValidateUserOutput;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.WatchHistoryInputModel;
import com.home.apisdk.apiModel.WatchHistoryOutputModel;
import com.home.vod.R;
import com.home.vod.activity.Episode_list_Activity;
import com.home.vod.activity.MainActivity;
import com.home.vod.activity.MovieDetailsActivity;
import com.home.vod.activity.ShowWithEpisodesActivity;
import com.home.vod.adapter.GenreFilterAdapter;
import com.home.vod.adapter.VideoFilterAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.DataModel;
import com.home.vod.model.GridItem;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.Constant;
import com.home.vod.util.FeatureHandler;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.MyLibraryPlayer;
import player.activity.Player;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.home.vod.preferences.LanguagePreference.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.APP_ON;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_ON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.util.Constant.CAST_INTENT_KEY;
import static com.home.vod.util.Constant.CENSOR_RATING_INTENT_KEY;
import static com.home.vod.util.Constant.GENRE_INTENT_KEY;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.SEASON_INTENT_KEY;
import static com.home.vod.util.Constant.STORY_INTENT_KEY;
import static com.home.vod.util.Constant.VIDEO_TITLE_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;

/**
 * Created by user on 28-06-2015.
 */
public class WatchHistoryFragment extends Fragment implements VideoDetailsAsynctask.VideoDetailsListener,
        GetValidateUserAsynTask.GetValidateUserListener,
        WatchHistoryAsynTask.WatchHistoryListener, GetIpAddressAsynTask.IpAddressListener {


    /***************chromecast**********************/
    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }


    private VideoView mVideoView;
    Player playerModel;
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
    String ipAddressStr = "";


    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;
    private MenuItem mediaRouteMenuItem;
    private IntroductoryOverlay mIntroductoryOverlay;
    private CastStateListener mCastStateListener;
    FeatureHandler featureHandler;

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {

        @Override
        public void onSessionEnded(CastSession session, int error) {
            if (session == mCastSession) {
                mCastSession = null;
            }
            //invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mCastSession = session;
            //invalidateOptionsMenu();
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mCastSession = session;
            //invalidateOptionsMenu();
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
    /***************chromecast**********************/

    ArrayList<String> url_maps;
    private ProgressBarHandler videoPDialog;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    int previousTotal = 0;
    private ProgressBarHandler gDialog;
    private SliderLayout mDemoSlider;
    String videoImageStrToHeight;
    int videoHeight = 185;
    int videoWidth = 256;
    public PreferenceManager preferenceManager;
    GridItem itemToPlay;
    private ProgressBarHandler pDialog;
    private static int firstVisibleInListview;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    LanguagePreference languagePreference;

    //for no internet

    private RelativeLayout noInternetConnectionLayout;
    RelativeLayout noDataLayout;
    TextView noDataTextView;
    TextView noInternetTextView;


    //firsttime load
    boolean firstTime = false;


    //no data

    /*The Data to be posted*/
    int offset = 1;
    int limit = 10;
    int listSize = 0;
    int itemsInServer = 0;


    int season_id = 0;
    int isFreeContent = 0;

    /*Asynctask on background thread*/
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    //Set Context
    Context context;

    //Adapter for GridView
    private VideoFilterAdapter customGridAdapter;

    //Model for GridView
    ArrayList<GridItem> itemData = new ArrayList<GridItem>();
    GridLayoutManager mLayoutManager;
    String posterUrl;

    // UI
    private GridView gridView;

    ImageView img;
    //data to load videourl
    private String movieUniqueId;
    private String movieStreamUniqueId;
    String videoUrlStr;
    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;

    RelativeLayout filterView;
    ArrayList<String> genreArray;
    String filterOrderByStr = "lastupload";
    GenreFilterAdapter genreAdapter;
    MenuItem filterMenuItem;
    int prevPosition = 5;
    String filterPermalink = "";
    int scrolledPosition = 0;
    boolean scrolling;
    boolean isSearched = false;
    RecyclerView genreListData;
    RelativeLayout footerView;
    TextView sectionTitle;
    String titleListName;

    public static ProgressBarHandler progressBarHandler;
    String filename = "";
    static File mediaStorageDir;

    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();

    public WatchHistoryFragment() {
        // Required empty public constructor

    }

    View header;
    public static boolean isLoading = false;


    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        if (mDemoSlider != null) {
            mDemoSlider.stopAutoCycle();
        }
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.watch_history, container, false);

        context = getActivity();
        featureHandler = FeatureHandler.getFeaturePreference(context);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        final Intent startIntent = new Intent(getActivity(), MainActivity.class);

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().startActivity(startIntent);

                                getActivity().finish();

                            }
                        });
                    }
                }
                return false;
            }
        });
        //for search for each activity
        setHasOptionsMenu(true);
        languagePreference = LanguagePreference.getLanguagePreference(getActivity());
        playerModel = new Player();
        playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        genreListData = (RecyclerView) rootView.findViewById(R.id.demoListView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        genreListData.setLayoutManager(linearLayout);
        genreListData.setItemAnimator(new DefaultItemAnimator());
        preferenceManager = PreferenceManager.getPreferenceManager(getActivity());// 0 - for private mode
       // sectionTitle = (TextView) rootView.findViewById(R.id.sectionTitle);
        posterUrl = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);

        gridView = (GridView) rootView.findViewById(R.id.imagesGridView);
       /* gridView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(context,2);
        gridView.setLayoutManager(mLayoutManager);
        gridView.setItemAnimator(new DefaultItemAnimator());*/
        footerView = (RelativeLayout) rootView.findViewById(R.id.loadingPanel);

        noInternetConnectionLayout = (RelativeLayout) rootView.findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) rootView.findViewById(R.id.noData);
        noInternetTextView = (TextView) rootView.findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) rootView.findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));

        noInternetConnectionLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("title"));
        if (getArguments().getString("title") != null) {
            titleListName = getArguments().getString("title");
           // sectionTitle.setText(titleListName);
        } else {
            //sectionTitle.setText("");

        }
        /*Typeface sectionTitleTypeface = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.regular_fonts));
        sectionTitle.setTypeface(sectionTitleTypeface);*/

        gridView.setAdapter(customGridAdapter);

        //Detect Network Connection


        if (!NetworkStatus.getInstance().isConnected(getActivity())) {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);
        }
        resetData();


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);

            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
        } else {
            //Call whatever you want
            if (NetworkStatus.getInstance().isConnected(getActivity())) {

                WatchHistoryInputModel watchHistoryInputModel = new WatchHistoryInputModel();
                watchHistoryInputModel.setAuthToken(authTokenStr);
                watchHistoryInputModel.setUser_id(preferenceManager.getUseridFromPref());
                watchHistoryInputModel.setLimit(String.valueOf(limit));
                watchHistoryInputModel.setOffset(String.valueOf(offset));
                String countryPref = preferenceManager.getCountryCodeFromPref();
                if (countryPref != null) {
                    watchHistoryInputModel.setCountry(countryPref);
                } else {
                    watchHistoryInputModel.setCountry("IN");
                }
                watchHistoryInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                WatchHistoryAsynTask asyncLoadVideos = new WatchHistoryAsynTask(watchHistoryInputModel, WatchHistoryFragment.this, context);
                asyncLoadVideos.executeOnExecutor(threadPoolExecutor);

            } else {
                noDataLayout.setVisibility(View.GONE);
                noInternetConnectionLayout.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                footerView.setVisibility(View.GONE);
            }
        }


        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (gridView.getLastVisiblePosition() >= itemsInServer - 1) {
                    footerView.setVisibility(View.GONE);
                    return;

                }

                if (view.getId() == gridView.getId()) {
                    final int currentFirstVisibleItem = gridView.getFirstVisiblePosition();

                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        mIsScrollingUp = false;

                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        mIsScrollingUp = true;

                    }

                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    scrolling = false;

                } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                    scrolling = true;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (scrolling == true && mIsScrollingUp == false) {

                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {

                        listSize = itemData.size();
                        if (gridView.getLastVisiblePosition() >= itemsInServer - 1) {
                            return;

                        }
                        offset += 1;

                        if (NetworkStatus.getInstance().isConnected(getActivity())) {

                            // default data
                            WatchHistoryInputModel watchHistoryInputModel = new WatchHistoryInputModel();
                            watchHistoryInputModel.setAuthToken(authTokenStr);
                            watchHistoryInputModel.setUser_id(preferenceManager.getUseridFromPref());
                            watchHistoryInputModel.setLimit(String.valueOf(limit));
                            watchHistoryInputModel.setOffset(String.valueOf(offset));
                            String countryPref = preferenceManager.getCountryCodeFromPref();
                            if (countryPref != null) {
                                watchHistoryInputModel.setCountry(countryPref);
                            } else {
                                watchHistoryInputModel.setCountry("IN");
                            }
                            watchHistoryInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            WatchHistoryAsynTask asyncLoadVideos = new WatchHistoryAsynTask(watchHistoryInputModel, WatchHistoryFragment.this, context);
                            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);
                            scrolling = false;

                        }

                    }
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                img = (ImageView) view.findViewById(R.id.movieImageView);

                GridItem item = itemData.get(position);
                itemToPlay = item;
                posterUrl = item.getImage();
                String movieName = item.getTitle();
                String movieGenre = item.getMovieGenre();
                String moviePermalink = item.getPermalink();
                String movieTypeId = item.getVideoTypeId();
                videoUrlStr = item.getVideoUrl();
                String isEpisode = item.getIsEpisode();
                movieUniqueId = item.getMovieUniqueId();
                movieStreamUniqueId = item.getMovieStreamUniqueId();
                season_id = item.getIsAPV();
                isFreeContent = item.getIsPPV();

                SubTitleName.clear();
                SubTitlePath.clear();

                DataModel dbModel = new DataModel();
                Util.dataModel = dbModel;


                if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
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

                } else {

                    if ((movieTypeId.trim().equalsIgnoreCase("1")) || (movieTypeId.trim().equalsIgnoreCase("2")) || (movieTypeId.trim().equalsIgnoreCase("4"))) {
                        final Intent movieDetailsIntent = new Intent(context, MovieDetailsActivity.class);
                        movieDetailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                        movieDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                movieDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(movieDetailsIntent);
                            }
                        });


                    } else if ((movieTypeId.trim().equalsIgnoreCase("3")) && isEpisode.equals("1")) {
                        // Call Load Videos Url to play the Video

                        ValidateUserInput validateUserInput = new ValidateUserInput();
                        validateUserInput.setAuthToken(authTokenStr);
                        validateUserInput.setUserId(preferenceManager.getUseridFromPref());
                        validateUserInput.setMuviUniqueId(movieUniqueId.trim());
                        validateUserInput.setPurchaseType("episode");
                        validateUserInput.setSeasonId("" + season_id);
                        validateUserInput.setEpisodeStreamUniqueId(movieStreamUniqueId);
                        validateUserInput.setLanguageCode(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        GetValidateUserAsynTask asynValidateUserDetails = new GetValidateUserAsynTask(validateUserInput, WatchHistoryFragment.this, context);
                        asynValidateUserDetails.executeOnExecutor(threadPoolExecutor);


                    } else if ((movieTypeId.trim().equalsIgnoreCase("3")) && isEpisode.equals("0") && season_id == 0) {
                        final Intent detailsIntent = new Intent(context, ShowWithEpisodesActivity.class);
                        detailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(detailsIntent);
                            }
                        });
                    } else if ((movieTypeId.trim().equalsIgnoreCase("3")) && isEpisode.equals("0") && season_id != 0) {


                        final Intent detailsIntent = new Intent(context, Episode_list_Activity.class);
                        Util.goToLibraryplayer = true;

                        detailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                        detailsIntent.putExtra(SEASON_INTENT_KEY, "" + season_id);

                        detailsIntent.putExtra(GENRE_INTENT_KEY, movieGenre);
                        detailsIntent.putExtra(STORY_INTENT_KEY, "");
                        detailsIntent.putExtra(CENSOR_RATING_INTENT_KEY, "");
                        detailsIntent.putExtra(CAST_INTENT_KEY, "");
                        detailsIntent.putExtra(VIDEO_TITLE_INTENT_KEY, movieName);
                        detailsIntent.putExtra("content_types_id", "" + "3");


                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(detailsIntent);
                            }
                        });
                    }
                }
            }
        });

         /*chromecast-------------------------------------*/

        mAquery = new AQuery(getActivity());

        // setupControlsCallbacks();
        setupCastListener();
        mCastContext = CastContext.getSharedInstance(getActivity());
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(getActivity(), savedInstanceState);
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
/***************chromecast**********************/


        return rootView;
    }


    public void onResume() {

        GetIpAddressAsynTask asynGetIpAddress = new GetIpAddressAsynTask(this, context);
        asynGetIpAddress.executeOnExecutor(threadPoolExecutor);

        /***************chromecast**********************/
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(getActivity()).getSessionManager()
                    .getCurrentCastSession();
        }


        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);

        /***************chromecast**********************/

        if (url_maps != null && url_maps.size() > 0) {
            url_maps.clear();
        }
        getActivity().invalidateOptionsMenu();
        super.onResume();
        if (getView() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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

    @Override
    public void onVideoDetailsPreExecuteStarted() {
        pDialog = new ProgressBarHandler(getActivity());
        pDialog.show();
    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int code, String status, String message) {

        boolean play_video = true;

        if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {

            if (_video_details_output.getStreaming_restriction().trim().equals("0")) {

                play_video = true;
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

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
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
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
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
                    LogUtil.showLog("BKS", "studipapprovedurl====" + playerModel.getVideoUrl());


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


            //dependency for datamodel

            SubTitleName = _video_details_output.getSubTitleName();
            SubTitleLanguage = _video_details_output.getSubTitleLanguage();

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
            playerModel.setSubTitleLanguage(_video_details_output.getSubTitleLanguage());
            playerModel.setOfflineUrl(_video_details_output.getOfflineUrl());
            playerModel.setOfflineLanguage(_video_details_output.getOfflineLanguage());
            playerModel.setPlayPos(Util.isDouble(_video_details_output.getPlayed_length()));



            if (playerModel.getVideoUrl() == null ||
                    playerModel.getVideoUrl().matches("")) {
                Util.showNoDataAlert(getActivity());


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
                if (_video_details_output.getThirdparty_url() == null ||_video_details_output.getThirdparty_url().matches("")){


                    if (mCastSession != null && mCastSession.isConnected()) {


                        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

                        movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, Util.dataModel.getVideoStory());
                        movieMetadata.putString(MediaMetadata.KEY_TITLE, Util.dataModel.getVideoTitle());
                        movieMetadata.addImage(new WebImage(Uri.parse(posterUrl.trim())));
                        movieMetadata.addImage(new WebImage(Uri.parse(posterUrl.trim())));

                        String mediaContentType = "videos/mp4";
                        if (Util.dataModel.getVideoUrl().contains(".mpd")) {
                            mediaContentType = "application/dash+xml";
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject();
                                jsonObj.put("description", playerModel.getVideoUrl());
                                jsonObj.put("licenseUrl", playerModel.getLicenseUrl());

                                //  This Code Is Added For Video Log By Bibhu..

                                jsonObj.put("authToken", Constant.authTokenStr.trim());
                                jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                                jsonObj.put("ip_address", ipAddressStr.trim());
                                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                                jsonObj.put("episode_id", playerModel.getEpisode_id());
                                jsonObj.put("played_length", "0");
                                jsonObj.put("watch_status", "start");
                                jsonObj.put("device_type", "2");
                                jsonObj.put("log_id", "0");

                                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                                    jsonObj.put("restrict_stream_id", "1");
                                } else {
                                    jsonObj.put("restrict_stream_id", "0");
                                }

                                jsonObj.put("domain_name", APIUrlConstant.BASE_URl.trim().substring(0, APIUrlConstant.BASE_URl.trim().length() - 6));
                                jsonObj.put("is_log", "1");
                                jsonObj.put("seek_status", "");

                                //=====================End===================//

                                // This  Code Is Added For Drm BufferLog By Bibhu ...

                                jsonObj.put("resolution", "BEST");
                                jsonObj.put("start_time", "0");
                                jsonObj.put("end_time", "0");
                                jsonObj.put("log_unique_id", "0");
                                jsonObj.put("location", "0");
                                jsonObj.put("video_type", "mped_dash");
                                jsonObj.put("totalBandwidth", "0");

                                //====================End=====================//

                            } catch (JSONException e) {
                            }

                            List tracks = new ArrayList();
                            if (featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE, FeatureHandler.DEFAULT_IS_SUBTITLE)) {

                                for (int i = 0; i < playerModel.getFakeSubTitlePath().size(); i++) {
                                    MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                                            MediaTrack.TYPE_TEXT)
                                            .setName(playerModel.getSubTitleName().get(0))
                                            .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                            .setContentId(playerModel.getFakeSubTitlePath().get(0))
                                            .setLanguage(playerModel.getSubTitleLanguage().get(0))
                                            .setContentType("text/vtt")
                                            .build();
                                    tracks.add(englishSubtitle);
                                }
                            }

                            mediaInfo = new MediaInfo.Builder(playerModel.getMpdVideoUrl().trim())
                                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                                    .setContentType(mediaContentType)
                                    .setMetadata(movieMetadata)
                                    .setStreamDuration(15 * 1000)
                                    .setCustomData(jsonObj)
                                    .setMediaTracks(tracks)
                                    .build();
                            mSelectedMedia = mediaInfo;


                            togglePlayback();
                        } else {
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject();
                                jsonObj.put("description", Util.dataModel.getVideoTitle());

                                //  This Code Is Added For Video Log By Bibhu..

                                jsonObj.put("authToken", Constant.authTokenStr.trim());
                                jsonObj.put("user_id", preferenceManager.getUseridFromPref());
                                jsonObj.put("ip_address", ipAddressStr.trim());
                                jsonObj.put("movie_id", playerModel.getMovieUniqueId());
                                jsonObj.put("episode_id", playerModel.getEpisode_id());
                                jsonObj.put("played_length", "0");
                                jsonObj.put("watch_status", "start");
                                jsonObj.put("device_type", "2");
                                jsonObj.put("log_id", "0");
                                jsonObj.put("seek_status", "");

                                if (featureHandler.getFeatureStatus(FeatureHandler.IS_STREAMING_RESTRICTION, FeatureHandler.DEFAULT_IS_STREAMING_RESTRICTION)) {
                                    jsonObj.put("restrict_stream_id", "1");
                                } else {
                                    jsonObj.put("restrict_stream_id", "0");
                                }

                                jsonObj.put("domain_name", APIUrlConstant.BASE_URl.trim().substring(0, APIUrlConstant.BASE_URl.trim().length() - 6));
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
                            if (featureHandler.getFeatureStatus(FeatureHandler.IS_SUBTITLE, FeatureHandler.DEFAULT_IS_SUBTITLE)) {

                                for (int i = 0; i < playerModel.getFakeSubTitlePath().size(); i++) {
                                    MediaTrack englishSubtitle = new MediaTrack.Builder(i,
                                            MediaTrack.TYPE_TEXT)
                                            .setName(playerModel.getSubTitleName().get(0))
                                            .setSubtype(MediaTrack.SUBTYPE_SUBTITLES)
                                            .setContentId(playerModel.getFakeSubTitlePath().get(0))
                                            .setLanguage(playerModel.getSubTitleLanguage().get(0))
                                            .setContentType("text/vtt")
                                            .build();
                                    tracks.add(englishSubtitle);
                                }
                            }

                            mediaInfo = new MediaInfo.Builder(playerModel.getMpdVideoUrl().trim())
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
                        return;
                    }
                    playerModel.setThirdPartyPlayer(false);

                    /***ad **/
                    Log.v("responseStr", "Util.dataModel.getAdNetworkId()" + Util.dataModel.getAdNetworkId());
                          /*  Util.dataModel.setAdNetworkId(3);
                            Util.dataModel.setChannelId("http://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=xml_vmap1&unviewed_position_start=1&cust_params=sample_ar%3Dpremidpostpod%26deployment%3Dgmf-js&cmsid=496&vid=short_onecue&correlator=%22");
                            Util.dataModel.setPreRoll(1);*/

                    final Intent playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);

                    /***ad **/

                            if (FakeSubTitlePath.size() > 0) {
                                // This Portion Will Be changed Later.

                                try{
                                    File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
                                    if (dir.isDirectory()) {
                                        String[] children = dir.list();
                                        for (int i = 0; i < children.length; i++) {
                                            new File(dir, children[i]).delete();
                                        }
                                    }

                                    progressBarHandler = new ProgressBarHandler(getActivity());
                                    progressBarHandler.show();
                                    Download_SubTitle(FakeSubTitlePath.get(0).trim());
                                }catch (Exception e){
                                    String ex = e.toString();

                                    progressBarHandler = new ProgressBarHandler(getActivity());
                                    progressBarHandler.show();
                                    Download_SubTitle(FakeSubTitlePath.get(0).trim());

                                   /* playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel", playerModel);
                                    startActivity(playVideoIntent);*/
                                }



                            } else {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                playVideoIntent.putExtra("PlayerModel", playerModel);
                                startActivity(playVideoIntent);
                            }

                } else {
                    final Intent playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);


                }
            }

        } else {

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            Util.showNoDataAlert(getActivity());


        }



 /*       boolean play_video = true;

        if (languagePreference.getTextofLanguage(IS_STREAMING_RESTRICTION, DEFAULT_IS_IS_STREAMING_RESTRICTION).equals("1")) {

            if (_video_details_output.getStreaming_restriction().trim().equals("0")) {

                play_video = false;
            }
            else
            {
                play_video = true;
            }
        }
        else
        {
            play_video = true;
        }
        if (!play_video) {

            try {
                if (pDialog.isShowing())
                    pDialog.hide();
            } catch (IllegalArgumentException ex) {
            }

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(message);
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK,DEFAULT_BUTTON_OK), null);
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

        if (status == null) {
            status = "0";
            Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
            //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
        }

        if ((status.trim().equalsIgnoreCase("0"))) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
                // movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            }
            Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
            //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage( NO_VIDEO_AVAILABLE, DEFAULT_NO_VIDEO_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage( SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        } else {

            if (Util.dataModel.getVideoUrl() == null) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage( NO_VIDEO_AVAILABLE, DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage( SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();
            } else if (Util.dataModel.getVideoUrl().matches("") || Util.dataModel.getVideoUrl().equalsIgnoreCase(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA))) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage( NO_VIDEO_AVAILABLE, DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(languagePreference.getTextofLanguage( SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage( BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();
            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    Util.dataModel.setVideoUrl(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA));
                }

                Util.dataModel.setAdNetworkId(_video_details_output.getAdNetworkId());
                Util.dataModel.setChannel_id(_video_details_output.getChannel_id());
                Util.dataModel.setPreRoll(_video_details_output.getPreRoll());
                Util.dataModel.setPostRoll(_video_details_output.getPostRoll());
                Util.dataModel.setMidRoll(_video_details_output.getMidRoll());
                Util.dataModel.setAdDetails(_video_details_output.getAdDetails());

                playerModel.setAdDetails(_video_details_output.getAdDetails());
                playerModel.setMidRoll(_video_details_output.getMidRoll());
                playerModel.setPostRoll(_video_details_output.getPostRoll());
                playerModel.setChannel_id(_video_details_output.getChannel_id());
                playerModel.setAdNetworkId(_video_details_output.getAdNetworkId());
                playerModel.setPreRoll(_video_details_output.getPreRoll());

                if (Util.dataModel.getThirdPartyUrl().matches("") || Util.dataModel.getThirdPartyUrl().equalsIgnoreCase(languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA))) {
                    final Intent playVideoIntent;
                    if (Util.dataModel.getAdNetworkId() == 3){
                        Log.v("responseStr","playVideoIntent"+Util.dataModel.getAdNetworkId());

                        playVideoIntent = new Intent(getActivity(), ExoPlayerActivity.class);

                    }
                    else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1){
                        if (Util.dataModel.getPlayPos() <= 0) {
                            playVideoIntent = new Intent(getActivity(), AdPlayerActivity.class);
                        }else{
                            playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);

                        }
                    }else{
                        playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

//                            if(FakeSubTitlePath.size()>0)
//                            {
//                                // This Portion Will Be changed Later.
//
//                                File dir = new File(Environment.getExternalStorageDirectory()+"/Android/data/" + getActivity().getApplicationContext().getPackageName().trim() + "/SubTitleList/");
//                                if (dir.isDirectory())
//                                {
//                                    String[] children = dir.list();
//                                    for (int i = 0; i < children.length; i++)
//                                    {
//                                        new File(dir, children[i]).delete();
//                                    }
//                                }
//
//                                progressBarHandler = new ProgressBarHandler(getActivity());
//                                progressBarHandler.show();
//                                Download_SubTitle(FakeSubTitlePath.get(0).trim());
//                            }
//                            else
                            {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("PlayerModel", playerModel);
                                context.startActivity(playVideoIntent);
                            }

                        }
                    });
                } else {
                    if (Util.dataModel.getVideoUrl().contains("://www.youtube") || Util.dataModel.getVideoUrl().contains("://www.youtu.be")) {
                        if (Util.dataModel.getVideoUrl().contains("live_stream?channel")) {
                            final Intent playVideoIntent = new Intent(getActivity(), ThirdPartyPlayer.class);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    context.startActivity(playVideoIntent);
                                }
                            });
                        } else {

                            final Intent playVideoIntent = new Intent(getActivity(), YouTubeAPIActivity.class);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    context.startActivity(playVideoIntent);
                                }
                            });

                        }
                    } else {
                        final Intent playVideoIntent = new Intent(getActivity(), ThirdPartyPlayer.class);
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(playVideoIntent);

                            }
                        });
                    }
                }
            }
        }*/
    }

    @Override
    public void onGetValidateUserPreExecuteStarted() {
        pDialog = new ProgressBarHandler(getActivity());
        pDialog.show();
    }

    @Override
    public void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message) {
        if (validateUserOutput == null) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                status = 0;
            }
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
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
        } else if (status <= 0) {
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                status = 0;
            }
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
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

        if (status > 0) {
            if (status == 425) {


                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

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
            } else if (status == 426) {


                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
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
            } else if (status == 428) {

                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(CROSSED_MAXIMUM_LIMIT, CROSSED_MAXIMUM_LIMIT) + " " + languagePreference.getTextofLanguage(ACTIVATE_SUBSCRIPTION_WATCH_VIDEO, DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO) + " " + languagePreference.getTextofLanguage(APP_ON, DEFAULT_APP_ON) + " " + getResources().getString(R.string.studio_site));

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
           /*     else if (Util.dataModel.getIsAPV() == 1 && status == 431){

                    try {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }

                    } catch (IllegalArgumentException ex) {
                        status = 0;
                    }
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
                    if (userMessage!=null && !userMessage.equalsIgnoreCase("")){
                        dlgAlert.setMessage(userMessage);
                    }else{
                        dlgAlert.setMessage(languagePreference.getTextofLanguage(Util.ALREADY_PURCHASE_THIS_CONTENT,Util.DEFAULT_ALREADY_PURCHASE_THIS_CONTENT));

                    }
                    //dlgAlert.setMessage(languagePreference.getTextofLanguage(Util.ALREADY_PURCHASE_THIS_CONTENT,Util.DEFAULT_ALREADY_PURCHASE_THIS_CONTENT)+ " " +getResources().getString(R.string.studio_site));

                    dlgAlert.setTitle(languagePreference.getTextofLanguage(Util.SORRY,Util.DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(Util.BUTTON_OK,Util.DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(Util.BUTTON_OK,Util.DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                    overridePendingTransition(0,0);
                                }
                            });
                    dlgAlert.create().show();

                }*/
            else if (status == 430) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }

                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

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


            } else if (status == 427) {


                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
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
            } else if (status == 429) {


                if (validateUserOutput.getValiduser_str() == null) {
                    try {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }
                    } catch (IllegalArgumentException ex) {
                        status = 0;
                    }
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

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
                if (validateUserOutput.getValiduser_str() != null) {
                    try {
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog = null;
                        }
                    } catch (IllegalArgumentException ex) {
                        status = 0;
                    }

                    if ((validateUserOutput.getValiduser_str().trim().equalsIgnoreCase("OK")) || (validateUserOutput.getValiduser_str().trim().matches("OK")) || (validateUserOutput.getValiduser_str().trim().equals("OK")))

                    {
                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setContent_uniq_id(movieUniqueId);
                        getVideoDetailsInput.setStream_uniq_id(movieStreamUniqueId);
                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                        getVideoDetailsInput.setUser_id(preferenceManager.getUseridFromPref());
                        VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, WatchHistoryFragment.this, context);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
                    } else {
                        try {
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog = null;
                            }
                        } catch (IllegalArgumentException ex) {
                            status = 0;
                        }
                        if ((message.trim().equalsIgnoreCase("Unpaid")) || (message.trim().matches("Unpaid")) || (message.trim().equals("Unpaid"))) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

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
    }

    @Override
    public void onWatchHistoryPreExecuteStarted() {

        if (MainActivity.internetSpeedDialog != null && MainActivity.internetSpeedDialog.isShowing()) {
            videoPDialog = MainActivity.internetSpeedDialog;
            footerView.setVisibility(View.GONE);

        } else {
            videoPDialog = new ProgressBarHandler(context);

            if (listSize == 0) {
                // hide loader for first time

                videoPDialog.show();
                footerView.setVisibility(View.GONE);
            } else {
                // show loader for first time
                videoPDialog.hide();
                footerView.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onWatchHistoryPostExecuteCompleted(ArrayList<WatchHistoryOutputModel> watchHistoryOutputModels, int status, String totalItems, String message) {

        try {
            try {
                if (videoPDialog != null && videoPDialog.isShowing()) {
                    videoPDialog.hide();
                }
            } catch (Exception ex) {
            }

            if (status == 200) {

                String movieImageStr = "";

                for (int i = 0; i < watchHistoryOutputModels.size(); i++) {
                    movieImageStr = watchHistoryOutputModels.get(i).getPosterUrl();
                    String movieName = watchHistoryOutputModels.get(i).getName();
                    String videoTypeIdStr = watchHistoryOutputModels.get(i).getContentTypesId();
                    String movieGenreStr = watchHistoryOutputModels.get(i).getGenre();
                    String moviePermalinkStr = watchHistoryOutputModels.get(i).getPermalink();
                    String isEpisodeStr = watchHistoryOutputModels.get(i).getIs_episode();
                    movieStreamUniqueId = watchHistoryOutputModels.get(i).getMovie_stream_uniq_id();
                    movieUniqueId = watchHistoryOutputModels.get(i).getMovieId();
                    int isConverted = watchHistoryOutputModels.get(i).getIsConverted();
                    int isFreeContent = watchHistoryOutputModels.get(i).getIsfreeContent();
                    int season_id = watchHistoryOutputModels.get(i).getSeason_id();

                    itemData.add(new GridItem(movieImageStr, movieName, "", videoTypeIdStr, movieGenreStr, "", moviePermalinkStr, isEpisodeStr, movieUniqueId, movieStreamUniqueId, isConverted, isFreeContent, season_id));
                }

                if (message == null)
                    message = "0";
                if ((message.trim().equals("0"))) {
                    try {
                        if (videoPDialog != null && videoPDialog.isShowing()) {
                            videoPDialog.hide();
                        }
                    } catch (IllegalArgumentException ex) {

                        noDataLayout.setVisibility(View.VISIBLE);
                        noInternetConnectionLayout.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                        footerView.setVisibility(View.GONE);
                    }
                    noDataLayout.setVisibility(View.VISIBLE);
                    noInternetConnectionLayout.setVisibility(View.GONE);
                    gridView.setVisibility(View.GONE);
                    footerView.setVisibility(View.GONE);
                } else {
                    if (itemData.size() <= 0) {
                        try {
                            if (videoPDialog != null && videoPDialog.isShowing()) {
                                videoPDialog.hide();
                            }
                        } catch (IllegalArgumentException ex) {

                            noDataLayout.setVisibility(View.VISIBLE);
                            noInternetConnectionLayout.setVisibility(View.GONE);
                            gridView.setVisibility(View.GONE);
                            footerView.setVisibility(View.GONE);
                        }
                        noDataLayout.setVisibility(View.VISIBLE);
                        noInternetConnectionLayout.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                        footerView.setVisibility(View.GONE);
                    } else {
                        footerView.setVisibility(View.GONE);
                        gridView.setVisibility(View.VISIBLE);
                        noInternetConnectionLayout.setVisibility(View.GONE);
                        noDataLayout.setVisibility(View.GONE);
                        videoImageStrToHeight = movieImageStr;
                        if (firstTime == true) {


                            new RetrieveFeedTask().execute(videoImageStrToHeight);


                        } else {
                            AsynLOADUI loadUI = new AsynLOADUI();
                            loadUI.executeOnExecutor(threadPoolExecutor);
                        }
                    }
                }

            } else {
                noDataLayout.setVisibility(View.VISIBLE);
                noInternetConnectionLayout.setVisibility(View.GONE);
                gridView.setVisibility(View.GONE);
                footerView.setVisibility(View.GONE);
            }

        } catch (Exception e) {
        }


    }

    //Load Films Videos
//    private class AsynLoadVideos extends AsyncTask<Void, Void, Void> {
//        String responseStr;
//        int status;
//        String movieGenreStr = "";
//        String movieName = languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA);
//        String movieImageStr = languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA);
//        String moviePermalinkStr = languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA);
//        String videoTypeIdStr = languagePreference.getTextofLanguage( NO_DATA, DEFAULT_NO_DATA);
//        String isEpisodeStr = "";
//
//
//        int isConverted = 0;
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//
//            try {
//               /* String urlRouteList ="http://www.idogic.com/rest/MyLibrary";
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken","445882348316089103b8729dcb397c51");
//                httppost.addHeader("user_id","5029");
//
//                httppost.addHeader("limit", String.valueOf(limit));
//                httppost.addHeader("offset", String.valueOf(offset));
//
//                SharedPreferences countryPref = context.getSharedPreferences(COUNTRY_PREF, 0);
//
//                if (countryPref != null) {
//                    String countryCodeStr = countryPref.getString("countryCode", null);
//                    httppost.addHeader("country", countryCodeStr);
//                } else {
//                    httppost.addHeader("country", "IN");
//                }
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage( SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
//*/
//
//                String urlRouteList = rootUrl().trim() + myLibrary.trim();
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//
//                httppost.addHeader("authToken", authTokenStr.trim());
//                httppost.addHeader("user_id", preferenceManager.getUseridFromPref());
//
//
//                httppost.addHeader("limit", String.valueOf(limit));
//                httppost.addHeader("offset", String.valueOf(offset));
//
//                String countryCodeStr = preferenceManager.getCountryCodeFromPref();
//
//                if (countryCodeStr != null) {
//                    httppost.addHeader("country", countryCodeStr);
//                } else {
//                    httppost.addHeader("country", "IN");
//                }
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage( SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (itemData != null) {
//                                noInternetConnectionLayout.setVisibility(View.GONE);
//                                gridView.setVisibility(View.VISIBLE);
//                                noDataLayout.setVisibility(View.GONE);
//                            } else {
//                                noInternetConnectionLayout.setVisibility(View.VISIBLE);
//                                noDataLayout.setVisibility(View.GONE);
//                                gridView.setVisibility(View.GONE);
//                            }
//
//                            footerView.setVisibility(View.GONE);
//                            Toast.makeText(context, languagePreference.getTextofLanguage( SLOW_INTERNET_CONNECTION, DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            noInternetConnectionLayout.setVisibility(View.GONE);
//                            noDataLayout.setVisibility(View.VISIBLE);
//                            footerView.setVisibility(View.GONE);
//                            gridView.setVisibility(View.GONE);
//                        }
//                    });
//                    e.printStackTrace();
//                }
//
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                    String items = myJson.optString("item_count");
////                    itemsInServer = Integer.parseInt(items);
//                }
//
//                if (status > 0) {
//                    if (status == 200) {
//
//                        JSONArray jsonMainNode = myJson.getJSONArray("mylibrary");
//
//                        int lengthJsonArr = jsonMainNode.length();
//                        for (int i = 0; i < lengthJsonArr; i++) {
//                            JSONObject jsonChildNode;
//                            try {
//                                jsonChildNode = jsonMainNode.getJSONObject(i);
//
//                                if ((jsonChildNode.has("genre")) && jsonChildNode.getString("genre").trim() != null && !jsonChildNode.getString("genre").trim().isEmpty() && !jsonChildNode.getString("genre").trim().equals("null") && !jsonChildNode.getString("genre").trim().matches("")) {
//                                    movieGenreStr = jsonChildNode.getString("genre");
//
//                                }
//                                if ((jsonChildNode.has("name")) && jsonChildNode.getString("name").trim() != null && !jsonChildNode.getString("name").trim().isEmpty() && !jsonChildNode.getString("name").trim().equals("null") && !jsonChildNode.getString("name").trim().matches("")) {
//                                    movieName = jsonChildNode.getString("name");
//
//                                }
//                                if ((jsonChildNode.has("poster_url")) && jsonChildNode.getString("poster_url").trim() != null && !jsonChildNode.getString("poster_url").trim().isEmpty() && !jsonChildNode.getString("poster_url").trim().equals("null") && !jsonChildNode.getString("poster_url").trim().matches("")) {
//                                    movieImageStr = jsonChildNode.getString("poster_url");
//                                    //movieImageStr = movieImageStr.replace("episode", "original");
//
//                                }
//                                if ((jsonChildNode.has("permalink")) && jsonChildNode.getString("permalink").trim() != null && !jsonChildNode.getString("permalink").trim().isEmpty() && !jsonChildNode.getString("permalink").trim().equals("null") && !jsonChildNode.getString("permalink").trim().matches("")) {
//                                    moviePermalinkStr = jsonChildNode.getString("permalink");
//
//                                }
//                                if ((jsonChildNode.has("content_types_id")) && jsonChildNode.getString("content_types_id").trim() != null && !jsonChildNode.getString("content_types_id").trim().isEmpty() && !jsonChildNode.getString("content_types_id").trim().equals("null") && !jsonChildNode.getString("content_types_id").trim().matches("")) {
//                                    videoTypeIdStr = jsonChildNode.getString("content_types_id");
//
//                                }
//                                //videoTypeIdStr = "1";
//
//                                if ((jsonChildNode.has("is_converted")) && jsonChildNode.getString("is_converted").trim() != null && !jsonChildNode.getString("is_converted").trim().isEmpty() && !jsonChildNode.getString("is_converted").trim().equals("null") && !jsonChildNode.getString("is_converted").trim().matches("")) {
//                                    isConverted = Integer.parseInt(jsonChildNode.getString("is_converted"));
//
//                                }
//                                if ((jsonChildNode.has("season_id")) && jsonChildNode.getString("season_id").trim() != null && !jsonChildNode.getString("season_id").trim().isEmpty() && !jsonChildNode.getString("season_id").trim().equals("null") && !jsonChildNode.getString("season_id").trim().matches("")) {
//                                    season_id = Integer.parseInt(jsonChildNode.getString("season_id"));
//
//
//                                }
//                                if ((jsonChildNode.has("isFreeContent")) && jsonChildNode.getString("isFreeContent").trim() != null && !jsonChildNode.getString("isFreeContent").trim().isEmpty() && !jsonChildNode.getString("isFreeContent").trim().equals("null") && !jsonChildNode.getString("isFreeContent").trim().matches("")) {
//                                    isFreeContent = Integer.parseInt(jsonChildNode.getString("isFreeContent"));
//
//                                }
//                                if ((jsonChildNode.has("is_episode")) && jsonChildNode.getString("is_episode").trim() != null && !jsonChildNode.getString("is_episode").trim().isEmpty() && !jsonChildNode.getString("is_episode").trim().equals("null") && !jsonChildNode.getString("is_episode").trim().matches("")) {
//                                    isEpisodeStr = jsonChildNode.getString("is_episode");
//
//                                }
//
//                                if ((jsonChildNode.has("muvi_uniq_id")) && jsonChildNode.getString("muvi_uniq_id").trim() != null && !jsonChildNode.getString("muvi_uniq_id").trim().isEmpty() && !jsonChildNode.getString("muvi_uniq_id").trim().equals("null") && !jsonChildNode.getString("muvi_uniq_id").trim().matches("")) {
//                                    movieUniqueId = jsonChildNode.getString("muvi_uniq_id");
//                                }
//
//                                if ((jsonChildNode.has("movie_stream_uniq_id")) && jsonChildNode.getString("movie_stream_uniq_id").trim() != null && !jsonChildNode.getString("movie_stream_uniq_id").trim().isEmpty() && !jsonChildNode.getString("movie_stream_uniq_id").trim().equals("null") && !jsonChildNode.getString("movie_stream_uniq_id").trim().matches("")) {
//                                    movieStreamUniqueId = jsonChildNode.getString("movie_stream_uniq_id");
//                                }
//
//
//                                itemData.add(new GridItem(movieImageStr, movieName, "", videoTypeIdStr, movieGenreStr, "", moviePermalinkStr, isEpisodeStr, movieUniqueId, movieStreamUniqueId, isConverted, isFreeContent, season_id));
//                            } catch (Exception e) {
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        noDataLayout.setVisibility(View.VISIBLE);
//                                        noInternetConnectionLayout.setVisibility(View.GONE);
//                                        gridView.setVisibility(View.GONE);
//                                        footerView.setVisibility(View.GONE);
//                                    }
//                                });
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                        }
//                    } else {
//                        responseStr = "0";
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                noDataLayout.setVisibility(View.VISIBLE);
//                                noInternetConnectionLayout.setVisibility(View.GONE);
//                                gridView.setVisibility(View.GONE);
//                                footerView.setVisibility(View.GONE);
//                            }
//                        });
//                    }
//                }
//            } catch (Exception e) {
//                if (getActivity() != null) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            noDataLayout.setVisibility(View.VISIBLE);
//                            noInternetConnectionLayout.setVisibility(View.GONE);
//                            gridView.setVisibility(View.GONE);
//                            footerView.setVisibility(View.GONE);
//                        }
//                    });
//                }
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
//            if (responseStr == null)
//                responseStr = "0";
//            if ((responseStr.trim().equals("0"))) {
//                try {
//                    if (videoPDialog != null && videoPDialog.isShowing()) {
//                        videoPDialog.hide();
//                        videoPDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//
//                    noDataLayout.setVisibility(View.VISIBLE);
//                    noInternetConnectionLayout.setVisibility(View.GONE);
//                    gridView.setVisibility(View.GONE);
//                    footerView.setVisibility(View.GONE);
//                }
//                noDataLayout.setVisibility(View.VISIBLE);
//                noInternetConnectionLayout.setVisibility(View.GONE);
//                gridView.setVisibility(View.GONE);
//                footerView.setVisibility(View.GONE);
//            } else {
//                if (itemData.size() <= 0) {
//                    try {
//                        if (videoPDialog != null && videoPDialog.isShowing()) {
//                            videoPDialog.hide();
//                            videoPDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//
//                        noDataLayout.setVisibility(View.VISIBLE);
//                        noInternetConnectionLayout.setVisibility(View.GONE);
//                        gridView.setVisibility(View.GONE);
//                        footerView.setVisibility(View.GONE);
//                    }
//                    noDataLayout.setVisibility(View.VISIBLE);
//                    noInternetConnectionLayout.setVisibility(View.GONE);
//                    gridView.setVisibility(View.GONE);
//                    footerView.setVisibility(View.GONE);
//                } else {
//                    footerView.setVisibility(View.GONE);
//                    gridView.setVisibility(View.VISIBLE);
//                    noInternetConnectionLayout.setVisibility(View.GONE);
//                    noDataLayout.setVisibility(View.GONE);
//                    videoImageStrToHeight = movieImageStr;
//                    if (firstTime == true) {
//
//
//                        new RetrieveFeedTask().execute(videoImageStrToHeight);
//                    /*    Picasso.with(context).load(videoImageStrToHeight
//                        ).into(new Target() {
//
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                videoWidth = bitmap.getWidth();
//                                videoHeight = bitmap.getHeight();
//                                AsynLOADUI loadUI = new AsynLOADUI();
//                                loadUI.executeOnExecutor(threadPoolExecutor);
//                            }
//
//                            @Override
//                            public void onBitmapFailed(final Drawable errorDrawable) {
//
//                            }
//
//                            @Override
//                            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//
//                            }
//                        });*/
//
//                    } else {
//                        AsynLOADUI loadUI = new AsynLOADUI();
//                        loadUI.executeOnExecutor(threadPoolExecutor);
//                    }
//                }
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            if (MainActivity.internetSpeedDialog != null && MainActivity.internetSpeedDialog.isShowing()) {
//                videoPDialog = MainActivity.internetSpeedDialog;
//                footerView.setVisibility(View.GONE);
//
//            } else {
//                videoPDialog = new ProgressBarHandler(context);
//
//                if (listSize == 0) {
//                    // hide loader for first time
//
//                    videoPDialog.show();
//                    footerView.setVisibility(View.GONE);
//                } else {
//                    // show loader for first time
//                    videoPDialog.hide();
//                    footerView.setVisibility(View.VISIBLE);
//
//                }
//            }
//        }
//
//
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

     /*   InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
*/

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
        gridView.setLayoutParams(layoutParams);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setGravity(Gravity.CENTER_HORIZONTAL);


        if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            } else {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
            }

        } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1);
            } else {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            }

        } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

            gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1);


        } else {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
            } else {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 4);
            }


        }
      /*  if ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            } else {
                gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
            }

        } else if ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1);
            } else {
                gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            }

        } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

            gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1);


        } else {
            if (videoWidth > videoHeight) {
                gridView.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
            } else {
                gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 4);
            }


        }
*/
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
/***************chromecast**********************/

        CastButtonFactory.setUpMediaRouteButton(getActivity(), menu, R.id.media_route_menu_item);
        /***************chromecast**********************/
        MenuItem item;
        item = menu.findItem(R.id.action_filter);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);

    }


    private class AsynLOADUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        protected void onPostExecute(Void result) {
            float density = context.getResources().getDisplayMetrics().density;
            if (firstTime == true) {
                try {
                    if (videoPDialog != null && videoPDialog.isShowing()) {
                        videoPDialog.hide();
                        videoPDialog = null;
                    }
                } catch (IllegalArgumentException ex) {

                    noDataLayout.setVisibility(View.VISIBLE);
                    noInternetConnectionLayout.setVisibility(View.GONE);
                    gridView.setVisibility(View.GONE);
                    if (filterMenuItem != null) {

                        filterMenuItem.setVisible(false);
                    }

                    footerView.setVisibility(View.GONE);
                }

                gridView.smoothScrollToPosition(0);
                firstTime = false;
                ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
                gridView.setLayoutParams(layoutParams);
                gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gridView.setGravity(Gravity.CENTER_HORIZONTAL);
                if (getActivity() != null && (getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    } else {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                    }

                } else if (getActivity() != null && (getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);
                    } else {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    }

                } else if (getActivity() != null && (context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

                    gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);


                } else {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                    } else {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 5);
                    }

                }

                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_videos_grid_layout, itemData);

                    }
                    // customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }


            } else {
                // save RecyclerView state
                mBundleRecyclerViewState = new Bundle();
                Parcelable listState = gridView.onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);


                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_videos_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.watch_history_videos_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                }

                if (mBundleRecyclerViewState != null) {
                    gridView.onRestoreInstanceState(listState);
                }

            }
    /*            if (getActivity() != null && (getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    } else {
                        if (density <= 1.5) {
                            gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 5);

                        } else {
                            gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                        }
                    }

                } else if (getActivity() != null && (getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);
                    } else {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    }

                } else if (getActivity() != null && (context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

                    gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);


                } else {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                    } else {
                        if (density <= 1.5) {
                            gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 6);

                        } else {
                            gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 5);
                        }
                    }

                }

                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {

                        customGridAdapter = new VideoFilterAdapter(context, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);

                    }
                    // customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }


            } else {
                // save RecyclerView state
                mBundleRecyclerViewState = new Bundle();
                Parcelable listState = gridView.onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);


                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                }

                if (mBundleRecyclerViewState != null) {
                    gridView.onRestoreInstanceState(listState);
                }

            }*/
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = gridView.onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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


    public void resetData() {
        if (itemData != null && itemData.size() > 0) {
            itemData.clear();
        }
        firstTime = true;

        offset = 1;
        isLoading = false;
        listSize = 0;
        if (((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            limit = 20;
        } else {
            limit = 15;
        }
        itemsInServer = 0;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                // add feature for search activity

                return false;

            default:
                break;
        }

        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111: {

                if (grantResults.length > 0) {
                    if ((grantResults.length > 0) && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
                        //Call whatever you want
                        if (NetworkStatus.getInstance().isConnected(getActivity())) {

                            WatchHistoryInputModel watchHistoryInputModel = new WatchHistoryInputModel();
                            watchHistoryInputModel.setAuthToken(authTokenStr);
                            watchHistoryInputModel.setUser_id(preferenceManager.getUseridFromPref());
                            watchHistoryInputModel.setLimit(String.valueOf(limit));
                            watchHistoryInputModel.setOffset(String.valueOf(offset));
                            String countryPref = preferenceManager.getCountryCodeFromPref();
                            if (countryPref != null) {
                                watchHistoryInputModel.setCountry(countryPref);
                            } else {
                                watchHistoryInputModel.setCountry("IN");
                            }
                            watchHistoryInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            WatchHistoryAsynTask asyncLoadVideos = new WatchHistoryAsynTask(watchHistoryInputModel, WatchHistoryFragment.this, context);
                            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);
                        } else {
                            noDataLayout.setVisibility(View.GONE);
                            noInternetConnectionLayout.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.GONE);
                            footerView.setVisibility(View.GONE);
                        }
                    } else {
                        getActivity().finish();
                    }
                } else {
                    getActivity().finish();
                }

                return;
            }
        }
    }


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
                mediaStorageDir = new File(root + "/Android/data/" + getActivity().getApplicationContext().getPackageName().trim() + "/SubTitleList/", "");

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


                playerModel.setSubTitlePath(SubTitlePath);

                if (progressBarHandler != null && progressBarHandler.isShowing()) {
                    progressBarHandler.hide();
                }
                Intent playVideoIntent;

                if (Util.dataModel.getAdNetworkId() == 3) {
                    Log.v("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                    playVideoIntent = new Intent(getActivity(), ExoPlayerActivity.class);

                } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                    if (Util.dataModel.getPlayPos() <= 0) {
                        playVideoIntent = new Intent(getActivity(), AdPlayerActivity.class);
                    } else {
                        playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);

                    }
                } else {
                    playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);

                }
                // Intent playVideoIntent = new Intent(getActivity(), MyLibraryPlayer.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                playVideoIntent.putExtra("PlayerModel", playerModel);
                context.startActivity(playVideoIntent);
            }
        }
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;
        private ProgressBarHandler phandler;

        protected Void doInBackground(String... urls) {
            try {


                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                videoHeight = bmp.getHeight();
                videoWidth = bmp.getWidth();

                return null;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

        protected void onPostExecute(Void feed) {
            // TODO: check this.exception
            // TODO: do something with the feed

          /*  if (phandler != null && phandler.isShowing()) {
                phandler.hide();
            }*/

            AsynLOADUI loadUI = new AsynLOADUI();
            loadUI.executeOnExecutor(threadPoolExecutor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    /***************chromecast**********************/

    private void showIntroductoryOverlay() {


        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }


        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    /*mIntroductoryOverlay =
                            new IntroductoryOverlay.Builder(
                            getActivity(), mediaRouteMenuItem)
                            .setTitleText(getActivity().getString(R.string.introducing_cast))
                            .setOverlayColor(R.color.primary)
                            .setSingleTime()
                            .setOnOverlayDismissedListener(
                                    new IntroductoryOverlay.OnOverlayDismissedListener() {
                                        @Override
                                        public void onOverlayDismissed() {
                                            mIntroductoryOverlay = null;
                                        }
                                    })
                            .build();


                    mIntroductoryOverlay.show();*/
                }
            });
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

                if (null != mSelectedMedia) {
                   /* if (mCastSession != null && mCastSession.isConnected()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                            }
                        });

                    }*/
                    if (mPlaybackState == PlaybackState.PLAYING) {
                        mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);
                        return;
                    } else {

                        mPlaybackState = PlaybackState.IDLE;
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    }
                }
                //   updatePlayButton(mPlaybackState);
                //invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {
               /* if (mCastSession != null && mCastSession.isConnected()) {
                    watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                }*/
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;

                //invalidateOptionsMenu();
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
                startControllersTimer();
            } else {
                stopControllersTimer();
                //setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            }
        } else {
            stopControllersTimer();
            //setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            updateControllersVisibility(false);
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

                        loadRemoteMedia(0, true);

                        break;
                    default:
                        break;
                }
                break;

            case PLAYING:
                mPlaybackState = PlaybackState.PAUSED;

                // mVideoView.pause();
                break;

            case IDLE:

                if (mCastSession != null && mCastSession.isConnected()) {
                    // watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                    loadRemoteMedia(0, true);


                    // Utils.showQueuePopup(this, mPlayCircle, mSelectedMedia);
                } else {
                }

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
                            loadRemoteMedia(0, true);


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
                Intent intent = new Intent(context, ExpandedControlsActivity.class);
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
    }


    private void stopControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
    }

    private void startControllersTimer() {
        if (mControllersTimer != null) {
            mControllersTimer.cancel();
        }
        if (mLocation == PlaybackLocation.REMOTE) {
            return;
        }
        mControllersTimer = new Timer();
        mControllersTimer.schedule(new HideControllersTask(), 5000);
    }

    // should be called from the main thread
    private void updateControllersVisibility(boolean show) {
        if (show) {
            //getSupportActionBar().show();
            mControllers.setVisibility(View.VISIBLE);
        } else {
            if (!Util.isOrientationPortrait(context)) {
                //getSupportActionBar().hide();
            }
            //  mControllers.setVisibility(View.INVISIBLE);
        }
    }

    private class HideControllersTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // updateControllersVisibility(false);
                    mControllersVisible = false;
                }
            });

        }
    }
/***************chromecast**********************/

}
