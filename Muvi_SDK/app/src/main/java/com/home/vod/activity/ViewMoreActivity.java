
package com.home.vod.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.home.apisdk.apiController.GetFeatureContentAsynTask;
import com.home.apisdk.apiController.GetLanguageListAsynTask;
import com.home.apisdk.apiController.GetTranslateLanguageAsync;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiModel.FeatureContentInputModel;
import com.home.apisdk.apiModel.FeatureContentOutputModel;
import com.home.apisdk.apiModel.LanguageListInputModel;
import com.home.apisdk.apiModel.LanguageListOutputModel;
import com.home.apisdk.apiModel.LogoutInput;
import com.home.vod.EpisodeListOptionMenuHandler;
import com.home.vod.R;
import com.home.vod.adapter.LanguageCustomAdapter;
import com.home.vod.adapter.VideoFilterAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.GridItem;
import com.home.vod.model.LanguageModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.R.attr.bitmap;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_REGISTER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
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
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PROFILE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_YES;

import static com.home.vod.preferences.LanguagePreference.LANGUAGE_POPUP_LOGIN;
import static com.home.vod.preferences.LanguagePreference.LOGOUT;
import static com.home.vod.preferences.LanguagePreference.LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.MY_DOWNLOAD;
import static com.home.vod.preferences.LanguagePreference.MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.NO;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.PROFILE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.YES;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.languageModel;


public class ViewMoreActivity extends AppCompatActivity implements
        LogoutAsynctask.LogoutListener, GetLanguageListAsynTask.GetLanguageListListener,
        GetTranslateLanguageAsync.GetTranslateLanguageInfoListener,
        GetFeatureContentAsynTask.GetFeatureContentListener {
    public static ProgressBarHandler progressBarHandler;
    String email, id;
    LanguageCustomAdapter languageCustomAdapter;
    String Default_Language = "";
    String Previous_Selected_Language = "";
    int prevPosition = 0;
    AlertDialog alert;
    ProgressBarHandler pDialog;


    ProgressBarHandler videoPDialog;
    String videoImageStrToHeight;
    private boolean mIsScrollingUp;
    private EpisodeListOptionMenuHandler episodeListOptionMenuHandler;
    private int mLastFirstVisibleItem;

    int videoHeight = 185;
    int videoWidth = 256;
    PreferenceManager preferenceManager;
    GridItem itemToPlay;
    Toolbar mActionBarToolbar;
    GridLayoutManager mLayoutManager;

  //  private TextView sectionTitle;
    //Register Dialog


    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    //for no internet

    private RelativeLayout noInternetConnectionLayout;

    //firsttime load
    boolean firstTime = false;


    /* Handling GridView Scrolling*/

    int scrolledPosition = 0;
    boolean scrolling;
    private static final String KEY_TRANSITION_EFFECT = "transition_effect";

    private Map<String, Integer> mEffectMap;
    // private int mCurrentTransitionEffect = JazzyHelper.HELIX;

    //no data
    RelativeLayout noDataLayout;

    /*The Data to be posted*/
    int offset = 1;
    int limit = 10;
    int listSize = 0;
    int itemsInServer = 0;

    /*Asynctask on background thread*/
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    TextView noDataTextView;
    TextView noInternetTextView;
    //Set Context
    int isLogin = 0;

    //Adapter for GridView
    private VideoFilterAdapter customGridAdapter;

    //Model for GridView
    ArrayList<GridItem> itemData = new ArrayList<GridItem>();
    String posterUrl;
    String sectionName;
    String sectionId;
    // UI
    GetFeatureContentAsynTask asyncLoadVideos;
    private GridView gridView;
    // private JazzyGridView gridView;
    RelativeLayout footerView;
    LanguagePreference languagePreference;
    FeatureHandler featureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        featureHandler = FeatureHandler.getFeaturePreference(ViewMoreActivity.this);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(getIntent().getStringExtra("sectionName"));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent().getStringExtra("SectionId") != null) {
            sectionId = getIntent().getStringExtra("SectionId");

        }
        if (getIntent().getStringExtra("sectionName") != null) {
            sectionName = getIntent().getStringExtra("sectionName");
        } else {
            sectionName="";
        }

        isLogin = preferenceManager.getLoginFeatureFromPref();
        episodeListOptionMenuHandler = new EpisodeListOptionMenuHandler(this);
      //  sectionTitle = (TextView) findViewById(R.id.sectionTitle);
       // FontUtls.loadFont(ViewMoreActivity.this, getResources().getString(R.string.regular_fonts), sectionTitle);
      /*  if (getIntent().getStringExtra("sectionName") != null) {
           // mActionBarToolbar.setTitle(getIntent().getStringExtra("sectionName"));
            mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(VIEW_MORE,DEFAULT_VIEW_MORE));
            *//*sectionName = getIntent().getStringExtra("sectionName");
            sectionTitle.setText(sectionName);*//*
        } else {
            mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(VIEW_MORE,DEFAULT_VIEW_MORE));
        }
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));*/

        posterUrl = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);

        gridView = (GridView) findViewById(R.id.imagesGridView);
        footerView = (RelativeLayout) findViewById(R.id.loadingPanel);

        noInternetConnectionLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));

        noInternetConnectionLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);

        //MUVIlaxmi
        gridView.setVisibility(View.VISIBLE);
      /*  ArrayList<GridItem> tempData = new ArrayList<GridItem>();


        for (int i = 0; i <= 10; i ++){
            tempData.add(new GridItem("","Loading","","","","","","","","",0,0,0));
            float density = getResources().getDisplayMetrics().density;

            if (density >= 3.5 && density <= 4.0){
                customGridAdapter = new GridViewAdapter(ViewMoreActivity.this, R.layout.nexus_videos_grid_layout, itemData, new GridViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(GridItem item) {
                        clickItem(item);

                    }
                });
            }else{
                customGridAdapter = new GridViewAdapter(ViewMoreActivity.this, R.layout.videos_280_grid_layout, itemData, new GridViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(GridItem item) {
                        clickItem(item);

                    }
                });

            }
        }*/

        gridView.setAdapter(customGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GridItem item = itemData.get(position);
                itemToPlay = item;
                String posterUrl = item.getImage();
                String movieName = item.getTitle();
                String movieGenre = item.getMovieGenre();
                String moviePermalink = item.getPermalink();
                String movieTypeId = item.getVideoTypeId();

                if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ViewMoreActivity.this);
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
                        final Intent movieDetailsIntent = new Intent(ViewMoreActivity.this, MovieDetailsActivity.class);
                        movieDetailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                        movieDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                movieDetailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(movieDetailsIntent);
                            }
                        });


                    } else if ((movieTypeId.trim().equalsIgnoreCase("3"))) {
                        final Intent detailsIntent = new Intent(ViewMoreActivity.this, ShowWithEpisodesActivity.class);
                        detailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(detailsIntent);
                            }
                        });
                    }
                }

            }
        });
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
                        if (NetworkStatus.getInstance().isConnected(ViewMoreActivity.this)) {

                            // default data
                            FeatureContentInputModel featureContentInputModel = new FeatureContentInputModel();
                            featureContentInputModel.setAuthToken(authTokenStr);
                            featureContentInputModel.setSection_id(sectionId.trim());
                            featureContentInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            GetFeatureContentAsynTask asyncLoadVideos = new GetFeatureContentAsynTask(featureContentInputModel, ViewMoreActivity.this, ViewMoreActivity.this);
                            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);


                            scrolling = false;

                        } else {
                            noInternetConnectionLayout.setVisibility(View.VISIBLE);
                        }

                    }

                }

            }
        });


        //Detect Network Connection

        if (!NetworkStatus.getInstance().isConnected(ViewMoreActivity.this)) {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
        gridView.setLayoutParams(layoutParams);


        firstTime = true;


        //Load first 10 data items

        if (itemData != null && itemData.size() > 0) {
            itemData.clear();
        }
        offset = 1;
        scrolledPosition = 0;
        listSize = 0;
        itemsInServer = 0;
        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            limit = 20;
        } else {
            limit = 15;
        }
        scrolling = false;
        if (NetworkStatus.getInstance().isConnected(ViewMoreActivity.this)) {
            FeatureContentInputModel featureContentInputModel = new FeatureContentInputModel();
            featureContentInputModel.setAuthToken(authTokenStr);
            featureContentInputModel.setSection_id(sectionId.trim());
            featureContentInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            GetFeatureContentAsynTask asyncLoadVideos = new GetFeatureContentAsynTask(featureContentInputModel, ViewMoreActivity.this, ViewMoreActivity.this);
            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);
        } else {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
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


    public void clickItem(GridItem item) {
        String moviePermalink = item.getPermalink();
        String movieTypeId = item.getVideoTypeId();
        // if searched

        // for tv shows navigate to episodes
        if ((movieTypeId.equalsIgnoreCase("3"))) {
            if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ViewMoreActivity.this, R.style.MyAlertDialogStyle);
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

                final Intent detailsIntent = new Intent(ViewMoreActivity.this, ShowWithEpisodesActivity.class);
                detailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                runOnUiThread(new Runnable() {
                    public void run() {
                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(detailsIntent);
                    }
                });
            }

        }

        // for single clips and movies
        else if ((movieTypeId.trim().equalsIgnoreCase("1")) || (movieTypeId.trim().equalsIgnoreCase("2")) || (movieTypeId.trim().equalsIgnoreCase("4"))) {
            final Intent detailsIntent = new Intent(ViewMoreActivity.this, MovieDetailsActivity.class);

            if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ViewMoreActivity.this, R.style.MyAlertDialogStyle);
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
                detailsIntent.putExtra(PERMALINK_INTENT_KEY, moviePermalink);
                runOnUiThread(new Runnable() {
                    public void run() {
                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(detailsIntent);
                    }
                });
            }
        }

    }


    @Override
    public void onBackPressed() {
        if (asyncLoadVideos != null) {
            asyncLoadVideos.cancel(true);
        }

        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }

    @Override
    public void onLogoutPreExecuteStarted() {
        pDialog = new ProgressBarHandler(ViewMoreActivity.this);
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
            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (status == null) {
            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code > 0) {
            if (code == 200) {
                preferenceManager.clearLoginPref();
                if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {

                    final Intent startIntent = new Intent(ViewMoreActivity.this, SplashScreen.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                } else {
                    final Intent startIntent = new Intent(ViewMoreActivity.this, MainActivity.class);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startIntent);
                            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });
                }

            } else {
                Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

            }
        }

    }

    @Override
    public void onGetLanguageListPreExecuteStarted() {

        progressBarHandler = new ProgressBarHandler(ViewMoreActivity.this);
        progressBarHandler.show();

    }

    @Override
    public void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage) {
        if (progressBarHandler.isShowing()) {
            progressBarHandler.hide();
            progressBarHandler = null;

        }
        ArrayList<LanguageModel> languageModels = new ArrayList<LanguageModel>();

        for (int i = 0; i < languageListOutputArray.size(); i++) {
            String language_id = languageListOutputArray.get(i).getLanguageCode();
            String language_name = languageListOutputArray.get(i).getLanguageName();


            LanguageModel languageModel = new LanguageModel();
            languageModel.setLanguageId(language_id);
            languageModel.setLanguageName(language_name);

            if (Default_Language.equalsIgnoreCase(language_id)) {
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
    public void onGetFeatureContentPreExecuteStarted() {

        if (MainActivity.internetSpeedDialog != null && MainActivity.internetSpeedDialog.isShowing()) {
            videoPDialog = MainActivity.internetSpeedDialog;
            footerView.setVisibility(View.GONE);

        } else {
            videoPDialog = new ProgressBarHandler(ViewMoreActivity.this);

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
    public void onGetFeatureContentPostExecuteCompleted(ArrayList<FeatureContentOutputModel> featureContentOutputModelArray, int status, String message) {
        try {
            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();

            }
        } catch (IllegalArgumentException ex) {


        }
        String movieImageStr = "";

        Log.v("Muvi", "featurecontent size=" + featureContentOutputModelArray.size());

        for (int i = 0; i < featureContentOutputModelArray.size(); i++) {
            movieImageStr = featureContentOutputModelArray.get(i).getPoster_url();
            String movieName = featureContentOutputModelArray.get(i).getName();
            String videoTypeIdStr = featureContentOutputModelArray.get(i).getContent_types_id();
            String movieGenreStr = featureContentOutputModelArray.get(i).getGenre();
            String moviePermalinkStr = featureContentOutputModelArray.get(i).getPermalink();
            String isEpisodeStr = featureContentOutputModelArray.get(i).getIs_episode();
            int isConverted = featureContentOutputModelArray.get(i).getIs_converted();
            int isPPV = featureContentOutputModelArray.get(i).getIs_ppv();
            int isAPV = featureContentOutputModelArray.get(i).getIs_advance();

            itemData.add(new GridItem(movieImageStr, movieName, "", videoTypeIdStr, movieGenreStr, "", moviePermalinkStr, isEpisodeStr, "", "", isConverted, isPPV, isAPV));

        }
        if (message == null)
            message = "0";
        if ((message.trim().equals("0"))) {

            noDataLayout.setVisibility(View.VISIBLE);
            noInternetConnectionLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);
        } else {
            if (itemData.size() <= 0) {
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

                    /*
                    Picasso.with(ViewMoreActivity.this).load(videoImageStrToHeight
                    ).error(R.drawable.no_image).into(new Target() {

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            videoWidth = bitmap.getWidth();
                            videoHeight = bitmap.getHeight();
                            AsynLOADUI loadUI = new AsynLOADUI();
                            loadUI.executeOnExecutor(threadPoolExecutor);
                        }

                        @Override
                        public void onBitmapFailed(final Drawable errorDrawable) {
                            videoImageStrToHeight = "https://d2gx0xinochgze.cloudfront.net/public/no-image-a.png";
                            videoWidth = errorDrawable.getIntrinsicWidth();
                            videoHeight = errorDrawable.getIntrinsicHeight();
                            AsynLOADUI loadUI = new AsynLOADUI();
                            loadUI.executeOnExecutor(threadPoolExecutor);

                        }

                        @Override
                        public void onPrepareLoad(final Drawable placeHolderDrawable) {

                        }
                    });*/

                } else {
                    AsynLOADUI loadUI = new AsynLOADUI();
                    loadUI.executeOnExecutor(threadPoolExecutor);
                }


            }
        }
    }


//    private class AsynLoadVideos extends AsyncTask<Void, Void, Void> {
//        String responseStr;
//        int status;
//        String movieGenreStr = "";
//        String movieName = languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA);
//        String movieImageStr = languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA);
//        String moviePermalinkStr = languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA);
//        String videoTypeIdStr = languagePreference.getTextofLanguage(NO_DATA, Util.DEFAULT_NO_DATA);
//        String isEpisodeStr = "";
//        int isAPV = 0;
//        int isPPV = 0;
//        int isConverted = 0;
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
    // String urlRouteList = Util.rootUrl().trim() + Util.getContent.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("section_id", sectionId.trim());
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//           /*     httppost.addHeader("limit", String.valueOf(limit));
//                httppost.addHeader("offset", String.valueOf(offset));
//                httppost.addHeader("orderby", "lastupload");*/
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
//                            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    runOnUiThread(new Runnable() {
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
//                   /* String items = myJson.optString("item_count");
//                    itemsInServer = Integer.parseInt(items);*/
//                }
//
//                if (status > 0) {
//                    if (status == 200) {
//
//                        JSONArray jsonMainNode = myJson.getJSONArray("section");
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
//                                if ((jsonChildNode.has("is_advance")) && jsonChildNode.getString("is_advance").trim() != null && !jsonChildNode.getString("is_advance").trim().isEmpty() && !jsonChildNode.getString("is_advance").trim().equals("null") && !jsonChildNode.getString("is_advance").trim().matches("")) {
//                                    isAPV = Integer.parseInt(jsonChildNode.getString("is_advance"));
//
//                                }
//                                if ((jsonChildNode.has("is_ppv")) && jsonChildNode.getString("is_ppv").trim() != null && !jsonChildNode.getString("is_ppv").trim().isEmpty() && !jsonChildNode.getString("is_ppv").trim().equals("null") && !jsonChildNode.getString("is_ppv").trim().matches("")) {
//                                    isPPV = Integer.parseInt(jsonChildNode.getString("is_ppv"));
//
//                                }
//                                if ((jsonChildNode.has("is_episode")) && jsonChildNode.getString("is_episode").trim() != null && !jsonChildNode.getString("is_episode").trim().isEmpty() && !jsonChildNode.getString("is_episode").trim().equals("null") && !jsonChildNode.getString("is_episode").trim().matches("")) {
//                                    isEpisodeStr = jsonChildNode.getString("is_episode");
//
//                                }
//
//                                itemData.add(new GridItem(movieImageStr, movieName, "", videoTypeIdStr, movieGenreStr, "", moviePermalinkStr, isEpisodeStr, "", "", isConverted, isPPV, isAPV));
//                            } catch (Exception e) {
//                                runOnUiThread(new Runnable() {
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
//                        runOnUiThread(new Runnable() {
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
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        noDataLayout.setVisibility(View.VISIBLE);
//                        noInternetConnectionLayout.setVisibility(View.GONE);
//                        gridView.setVisibility(View.GONE);
//                        footerView.setVisibility(View.GONE);
//                    }
//                });
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
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
//
//                    if (firstTime == true) {
//                        Picasso.with(ViewMoreActivity.this).load(videoImageStrToHeight
//                        ).error(R.drawable.no_image).into(new Target() {
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
//                                videoImageStrToHeight = "https://d2gx0xinochgze.cloudfront.net/public/no-image-a.png";
//                                videoWidth = errorDrawable.getIntrinsicWidth();
//                                videoHeight = errorDrawable.getIntrinsicHeight();
//                                AsynLOADUI loadUI = new AsynLOADUI();
//                                loadUI.executeOnExecutor(threadPoolExecutor);
//
//                            }
//
//                            @Override
//                            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//
//                            }
//                        });
//
//                    } else {
//                        AsynLOADUI loadUI = new AsynLOADUI();
//                        loadUI.executeOnExecutor(threadPoolExecutor);
//                    }
//
//
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
//                videoPDialog = new ProgressBarHandler(ViewMoreActivity.this);
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

    private class AsynLOADUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        protected void onPostExecute(Void result) {
            float density = getResources().getDisplayMetrics().density;

            if (firstTime == true) {

                gridView.smoothScrollToPosition(0);
                firstTime = false;
                ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
                gridView.setLayoutParams(layoutParams);
                gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gridView.setGravity(Gravity.CENTER_HORIZONTAL);

                if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    } else {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                    }

                } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);
                    } else {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 3);
                    }

                } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

                    gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 2);


                } else {
                    if (videoWidth > videoHeight) {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
                    } else {
                        gridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 5 : 5);
                    }

                }
                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_grid_layout, itemData);

                    }
                    // customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }

              /*  if (videoWidth > videoHeight) {
                    customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_280_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                } else {
                    customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }*/


            } else {
                // save RecyclerView state
                mBundleRecyclerViewState = new Bundle();
                Parcelable listState = gridView.onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);


              /*  if (videoWidth > videoHeight) {
                    customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_280_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                } else {
                    customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }*/

                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(ViewMoreActivity.this, R.layout.videos_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                }

                if (mBundleRecyclerViewState != null) {
                    gridView.onRestoreInstanceState(listState);
                }

            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        id = preferenceManager.getUseridFromPref();
        email = preferenceManager.getEmailIdFromPref();
        episodeListOptionMenuHandler.createOptionMenu(menu, preferenceManager, languagePreference,featureHandler);
        return true;
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

    private PlaybackLocation mLocation;
    private PlaybackState mPlaybackState;
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaInfo mSelectedMedia;
    private boolean mControllersVisible;


    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;
    private MenuItem mediaRouteMenuItem;

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

    /*****************chromecvast*-------------------------------------*/

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

                Intent intent = new Intent(ViewMoreActivity.this, ExpandedControlsActivity.class);
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

    /***************chromecast**********************/
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
        invalidateOptionsMenu();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                final Intent searchIntent = new Intent(ViewMoreActivity.this, SearchActivity.class);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(searchIntent);
                // Not implemented here
                return false;
            case R.id.action_filter:

                // Not implemented here
                return false;

            case R.id.action_login:

                Intent loginIntent = new Intent(ViewMoreActivity.this, LoginActivity.class);
                Util.check_for_subscription = 0;
                startActivity(loginIntent);
                // Not implemented here
                return false;

            case R.id.menu_item_favorite:

                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                favoriteIntent.putExtra("sectionName",languagePreference.getTextofLanguage(MY_FAVOURITE, DEFAULT_MY_FAVOURITE));
                favoriteIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(favoriteIntent);
                // Not implemented here
                return false;
            case R.id.action_register:

                Intent registerIntent = new Intent(ViewMoreActivity.this, RegisterActivity.class);
                Util.check_for_subscription = 0;
                startActivity(registerIntent);
                // Not implemented here
                return false;
            case R.id.menu_item_language:

                // Not implemented here
                Default_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);
                Previous_Selected_Language = languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE);

                if (languageModel != null && languageModel.size() > 0) {

                    ShowLanguagePopup();

                } else {
                    LanguageListInputModel languageListInputModel = new LanguageListInputModel();
                    languageListInputModel.setAuthToken(authTokenStr);
                    GetLanguageListAsynTask asynGetLanguageList = new GetLanguageListAsynTask(languageListInputModel, this, this);
                    asynGetLanguageList.executeOnExecutor(threadPoolExecutor);
                }
                return false;
            case R.id.menu_item_profile:

                Intent profileIntent = new Intent(ViewMoreActivity.this, ProfileActivity.class);
                profileIntent.putExtra("EMAIL", email);
                profileIntent.putExtra("LOGID", id);
                startActivity(profileIntent);
                // Not implemented here
                return false;
            case R.id.action_purchage:

                Intent purchaseintent = new Intent(ViewMoreActivity.this, PurchaseHistoryActivity.class);
                startActivity(purchaseintent);
                // Not implemented here
                return false;
            case R.id.action_logout:

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ViewMoreActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(SIGN_OUT_WARNING, DEFAULT_SIGN_OUT_WARNING));
                dlgAlert.setTitle("");

                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(YES, DEFAULT_YES), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        // dialog.cancel();
                        if(NetworkStatus.getInstance().isConnected(ViewMoreActivity.this)) {
                        LogoutInput logoutInput = new LogoutInput();
                        logoutInput.setAuthToken(authTokenStr);
                        logoutInput.setLogin_history_id(preferenceManager.getLoginHistIdFromPref());
                        logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, ViewMoreActivity.this, ViewMoreActivity.this);
                        asynLogoutDetails.executeOnExecutor(threadPoolExecutor);


                        dialog.dismiss();
                        }else {
                            Toast.makeText(ViewMoreActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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
            case R.id.action_mydownload:

                Intent mydownload = new Intent(ViewMoreActivity.this, MyDownloads.class);
                startActivity(mydownload);
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
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
//                httppost.addHeader("lang_code",Util.getTextofLanguage(ViewMoreActivity.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//                } catch (org.apache.http.conn.ConnectTimeoutException e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (pDialog != null && pDialog.isShowing()) {
//                                pDialog.hide();
//                                pDialog = null;
//                            }
//                            responseCode = 0;
//                            Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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
//                Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if(responseStr == null){
//                Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//            }
//            if (responseCode == 0) {
//                Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
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
//                 /*   SharedPreferences countryPref = getSharedPreferences(Util.COUNTRY_PREF, 0); // 0 - for private mode
//                    if (countryPref!=null) {
//                        SharedPreferences.Editor countryEditor = countryPref.edit();
//                        countryEditor.clear();
//                        countryEditor.commit();
//                    }*/
//                    if ((languagePreference.getTextofLanguage(IS_ONE_STEP_REGISTRATION, Util.DEFAULT_IS_ONE_STEP_REGISTRATION)
//                            .trim()).equals("1")) {
//                        final Intent startIntent = new Intent(ViewMoreActivity.this, SplashScreen.class);
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(startIntent);
//                                Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(LOGOUT_SUCCESS, Util.DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
//                                finish();
//
//                            }
//                        });
//                    }
//                    else
//                    {
//                        final Intent startIntent = new Intent(ViewMoreActivity.this, MainActivity.class);
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(startIntent);
//                                Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(LOGOUT_SUCCESS, Util.DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();
//                                finish();
//
//                            }
//                        });
//                    }
//
//                }
//                else {
//                    Toast.makeText(ViewMoreActivity.this,languagePreference.getTextofLanguage(SIGN_OUT_ERROR, Util.DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            pDialog = new ProgressBarHandler(ViewMoreActivity.this);
//            pDialog.show();
//        }
//    }

    public void ShowLanguagePopup() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewMoreActivity.this, R.style.MyAlertDialogStyle);
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

        languageCustomAdapter = new LanguageCustomAdapter(ViewMoreActivity.this, languageModel);
        // Util.languageModel.get(0).setSelected(true);
      /*  if (Util.languageModel.get(i).getLanguageId().equalsIgnoreCase(Util.getTextofLanguage(MovieDetailsActivity.this, Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE))) {
            prevPosition = i;
            Util.languageModel.get(i).setSelected(true);

        }
        Util.languageModel.get(0).setSelected(true);*/

        recyclerView.setAdapter(languageCustomAdapter);



    /*    for (int i = 0 ; i < Util.languageModel.size() - 1 ; i ++){
                if (Util.languageModel.get(i).getLanguageId().equalsIgnoreCase(Util.getTextofLanguage(MovieDetailsActivity.this, Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE))) {
                    prevPosition = i;
                    Util.languageModel.get(i).setSelected(true);
                    break;

            }else {
                prevPosition = 0;

                Util.languageModel.get(0).setSelected(true);
                break;

            }
        }
*/
        recyclerView.addOnItemTouchListener(new MovieDetailsActivity.RecyclerTouchListener1(ViewMoreActivity.this, recyclerView, new MovieDetailsActivity.ClickListener1() {
            @Override
            public void onClick(View view, int position) {
                Util.itemclicked = true;

                languageModel.get(position).setSelected(true);


                if (prevPosition != position) {
                    languageModel.get(prevPosition).setSelected(false);
                    prevPosition = position;

                }

                Default_Language = languageModel.get(position).getLanguageId();


                languagePreference.setLanguageSharedPrefernce(SELECTED_LANGUAGE_CODE, languageModel.get(position).getLanguageId());
                languageCustomAdapter.notifyDataSetChanged();

                // default_Language = Util.languageModel.get(position).getLanguageId();
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


                if (!Previous_Selected_Language.equals(Default_Language)) {

                    LanguageListInputModel languageListInputModel = new LanguageListInputModel();
                    languageListInputModel.setLangCode(Default_Language);
                    languageListInputModel.setAuthToken(authTokenStr);
                    GetTranslateLanguageAsync asynGetTransalatedLanguage = new GetTranslateLanguageAsync(languageListInputModel, ViewMoreActivity.this, ViewMoreActivity.this);
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

  /*  @Override
    protected void onResume() {
        super.onResume();
        *//***************chromecast**********************//*
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }



        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);

        */

    /***************chromecast**********************//*
        invalidateOptionsMenu();

    }*/

    public interface ClickListener1 {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


//    private class AsynGetLanguageList extends AsyncTask<Void, Void, Void> {
//        String responseStr;
//        int status;
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String urlRouteList =Util.rootUrl().trim()+Util.LanguageList.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//
//
//                // Execute HTTP Post Request
//                try {
//
//
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = (EntityUtils.toString(response.getEntity())).trim();
//                } catch (Exception e) {
//                }
//                if (responseStr != null) {
//                    JSONObject json = new JSONObject(responseStr);
//                    try {
//                        status = Integer.parseInt(json.optString("code"));
//                        default_Language = json.optString("default_lang");
//                        if(!Util.getTextofLanguage(ViewMoreActivity.this,Util.SELECTED_LANGUAGE_CODE,"").equals(""))
//                        {
//                            default_Language = Util.getTextofLanguage(ViewMoreActivity.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE);
//                        }
//
//                    } catch (Exception e) {
//                        status = 0;
//                    }
//                }
//
//            } catch (Exception e) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//
//                    }
//                });
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//            if(progressBarHandler.isShowing())
//            {
//                progressBarHandler.hide();
//                progressBarHandler = null;
//
//            }
//
//            if (responseStr == null) {
//            } else {
//                if (status > 0 && status == 200) {
//
//                    try {
//                        JSONObject json = new JSONObject(responseStr);
//                        JSONArray jsonArray = json.getJSONArray("lang_list");
//                        ArrayList<LanguageModel> languageModels = new ArrayList<LanguageModel>();
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            String language_id = jsonArray.getJSONObject(i).optString("code").trim();
//                            String language_name = jsonArray.getJSONObject(i).optString("language").trim();
//
//
//                            LanguageModel languageModel = new LanguageModel();
//                            languageModel.setLanguageId(language_id);
//                            languageModel.setLanguageName(language_name);
//
//                            if(default_Language.equalsIgnoreCase(language_id))
//                            {
//                                languageModel.setIsSelected(true);
//                            }
//                            else
//                            {
//                                languageModel.setIsSelected(false);
//                            }
//                            languageModels.add(languageModel);
//                        }
//
//                        Util.languageModel = languageModels;
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                 /*   if(!default_Language.equals("en")) {
//                        //                  Call For Language Translation.
//                        AsynGetTransalatedLanguage asynGetTransalatedLanguage = new AsynGetTransalatedLanguage();
//                        asynGetTransalatedLanguage.executeOnExecutor(threadPoolExecutor);
//
//                    }else{
//
//                    }*/
//
//                } else {
//                }
//            }
//            ShowLanguagePopup();
//
//
//        }
//
//        protected void onPreExecute() {
//
//            progressBarHandler = new ProgressBarHandler(ViewMoreActivity.this);
//            progressBarHandler.show();
//
//        }
//    }

    @Override
    public void onGetTranslateLanguagePreExecuteStarted() {

        progressBarHandler = new ProgressBarHandler(ViewMoreActivity.this);
        progressBarHandler.show();
    }

    @Override
    public void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status) {

        if (status > 0 && status == 200) {

            try {
                Util.parseLanguage(languagePreference, jsonResponse, Default_Language);

                //Call For Language PopUp Dialog

                languageCustomAdapter.notifyDataSetChanged();

                Intent intent = new Intent(ViewMoreActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Call For Other Methods.


        } else {
        }
    }


// Added by Bibhu


    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;
        private ProgressBarHandler phandler;

        protected Void doInBackground(String... urls) {
            try {


                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                videoHeight = bmp.getHeight();
                videoWidth = bmp.getWidth();


                LogUtil.showLog("MUVI", "videoHeight==============" + videoHeight);
                LogUtil.showLog("MUVI", "videoWidth==============" + videoWidth);

                return null;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(Void feed) {
            // TODO: check this.exception
            // TODO: do something with the feed

           /* if (phandler != null && phandler.isShowing()) {
                phandler.hide();
            }*/

            AsynLOADUI loadUI = new AsynLOADUI();
            loadUI.executeOnExecutor(threadPoolExecutor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  phandler = new ProgressBarHandler(getActivity());
            phandler.show();*/

        }
    }

}
