package com.home.vod.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.home.apisdk.apiController.GetAppMenuAsync;
import com.home.apisdk.apiController.GetImageForDownloadAsynTask;
import com.home.apisdk.apiController.GetLanguageListAsynTask;
import com.home.apisdk.apiController.GetTranslateLanguageAsync;
import com.home.apisdk.apiController.LogoutAsynctask;
import com.home.apisdk.apiController.SDKInitializer;
import com.home.apisdk.apiModel.GetMenusInputModel;
import com.home.apisdk.apiModel.LanguageListInputModel;
import com.home.apisdk.apiModel.LanguageListOutputModel;
import com.home.apisdk.apiModel.LogoutInput;
import com.home.apisdk.apiModel.MenusOutputModel;
import com.home.vod.EpisodeListOptionMenuHandler;
import com.home.vod.FooterMenuHandler;
import com.home.vod.R;
import com.home.vod.ToolbarTitleHandler;
import com.home.vod.adapter.LanguageCustomAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.fragment.AboutUsFragment;
import com.home.vod.fragment.ContactUsFragment;
import com.home.vod.fragment.FragmentDrawer;
import com.home.vod.fragment.HomeFragment;
import com.home.vod.fragment.MyLibraryFragment;
import com.home.vod.fragment.VideosListFragment;
import com.home.vod.model.LanguageModel;
import com.home.vod.model.NavDrawerItem;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.LogUtil;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.CONTACT_US;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_APP_SELECT_LANGUAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTACT_US;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_HOME;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_ONE_STEP_REGISTRATION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_LIBRARY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_YES;
import static com.home.vod.preferences.LanguagePreference.HOME;

import static com.home.vod.preferences.LanguagePreference.LOGOUT;
import static com.home.vod.preferences.LanguagePreference.LOGOUT_SUCCESS;
import static com.home.vod.preferences.LanguagePreference.MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.MY_LIBRARY;
import static com.home.vod.preferences.LanguagePreference.NO;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_ERROR;
import static com.home.vod.preferences.LanguagePreference.SIGN_OUT_WARNING;
import static com.home.vod.preferences.LanguagePreference.YES;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.languageModel;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener, NavigationDrawerFragment.NavigationDrawerCallbacks,
        LogoutAsynctask.LogoutListener,
        GetLanguageListAsynTask.GetLanguageListListener,
        GetTranslateLanguageAsync.GetTranslateLanguageInfoListener {


    public MainActivity() {

    }

    LanguagePreference languagePreference;
    FooterMenuHandler fooerMenuHandler;


    //*** chromecast**************//*

    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }


    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    private NavigationDrawerFragment mNavigationDrawerFragment;
    public CharSequence mTitle;
    public DrawerLayout mDrawerLayout;
    TextView text;

    private Timer mControllersTimer;
    private PlaybackLocation mLocation;
    private PlaybackState mPlaybackState;
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaInfo mSelectedMedia;


    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;
    private MenuItem mediaRouteMenuItem;
    private IntroductoryOverlay mIntroductoryOverlay;
    private CastStateListener mCastStateListener;
    private EpisodeListOptionMenuHandler episodeListOptionMenuHandler;
    private ToolbarTitleHandler toolbarTitleHandler;

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

    /*** chromecast**************/


    public static int vertical = 0;
    private String lang_code = "";
    int check = 0;
    public static int isNavigated = 0;
    String Default_Language = "";
    public ArrayList<NavDrawerItem> menuList = new ArrayList<>();
    public ArrayList<LanguageModel> languageModels = new ArrayList<>();
    private String imageUrlStr;
    // public static SharedPreferences dataPref;
    int state = 0;
    LanguageCustomAdapter languageCustomAdapter;
    public static ProgressBarHandler internetSpeedDialog;
    //Load on background thread
    /*Asynctask on background thread*/
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    //Toolbar
    public Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private RelativeLayout noInternetLayout;
    public static String internetSpeed = "0";
    Fragment fragment = null;
    private ProgressBarHandler pDialog = null;
    String loggedInStr, loginHistoryIdStr, email, id;

    GetImageForDownloadAsynTask as = null;
    GetAppMenuAsync asynLoadMenuItems = null;
    int isLogin = 0;

    public static int planIdOfStudios = 3;
    int prevPosition = 0;


    AlertDialog alert;
    String Previous_Selected_Language = "";
    TextView noInternetTextView;
    // SharedPreferences isLoginPref;
    public static ProgressBarHandler progressBarHandler;
    PreferenceManager preferenceManager;
    FeatureHandler featureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.showLog("BKS", "packagenameMAINactivity1===" + SDKInitializer.user_Package_Name_At_Api);

        Util.drawer_collapse_expand_imageview.clear();

        if (menuList != null && menuList.size() > 0) {
            menuList.clear();
        }
        fooerMenuHandler = new FooterMenuHandler(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        episodeListOptionMenuHandler = new EpisodeListOptionMenuHandler(this);
        featureHandler = FeatureHandler.getFeaturePreference(this);

        /*Set Toolbar*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        toolbarTitleHandler=new ToolbarTitleHandler(this);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        LogUtil.showLog("Abhishek", "Toolbar");

        //**** chromecast*************//*

        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState != CastState.NO_DEVICES_AVAILABLE) {

                    showIntroductoryOverlay();
                }
            }
        };

        mCastContext = CastContext.getSharedInstance(this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);


        // int startPosition = getInt("startPosition", 0);
        // mVideoView.setVideoURI(Uri.parse(item.getContentId()));

        setupCastListener();
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();


        //**** chromecast*************//*

        preferenceManager = PreferenceManager.getPreferenceManager(this);
        isLogin = preferenceManager.getLoginFeatureFromPref();
        // dataPref = getApplicationContext().getSharedPreferences("DrawerState", 0);


    /*    SharedPreferences countryPref = getSharedPreferences(Util.COUNTRY_PREF, 0); // 0 - for private mode
        if (countryPref != null) {
            String countryCodeStr = countryPref.getString("countryCode", null);
            if (countryCodeStr != null) {
                Global globalVariable = (Global) getApplicationContext();
                globalVariable.setCountryCode(countryCodeStr);
            }
        }*/

        noInternetLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_NO_DATA, DEFAULT_NO_INTERNET_NO_DATA));
        noInternetLayout.setVisibility(View.GONE);


        if (NetworkStatus.getInstance().isConnected(MainActivity.this)) {
           /* if (asynLoadMenuItems != null) {
                asynLoadMenuItems = null;
            }
            GetMenusInputModel menuListInput = new GetMenusInputModel();
            menuListInput.setAuthToken(authTokenStr);
            menuListInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            asynLoadMenuItems = new GetAppMenuAsync(menuListInput, this, this);
            asynLoadMenuItems.executeOnExecutor(threadPoolExecutor);*/
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
            dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

       /* if (getIntent().getIntExtra("isLogin",0) != 0){

            Global globalVariable = (Global) getApplicationContext();
            globalVariable.setIsLogin(getIntent().getIntExtra("isLogin",0));

        }*/


        //MUVIshree genre
       /* Bundle b=this.getIntent().getExtras();

        String[] genreArray = new String[0];
        String[] genreValuesArray = new String[0];
        try {
            genreArray = b.getStringArray("genreArray");
            genreValuesArray = b.getStringArray("genreValueArray");


        Global globalVariable = (Global) getApplicationContext();
        // isLogin = ((Global) getApplicationContext()).getIsLogin();

        if (genreArray.length > 0){
            globalVariable.setGenreArray(genreArray);

        }
        if (genreValuesArray.length > 0){
            globalVariable.setGenreValueArray(genreValuesArray);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        id = preferenceManager.getUseridFromPref();
        email = preferenceManager.getEmailIdFromPref();

        episodeListOptionMenuHandler.createOptionMenu(menu, preferenceManager, languagePreference,featureHandler);
/************chromecast***********/
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);

       /* MenuItemCompat.setActionProvider(mediaRouteMenuItem, new MediaRouteActionProvider(new ContextThemeWrapper(this, R.style.Theme_CastVideosDark)));
        mediaRouteMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
        showIntroductoryOverlay();

        /************chromecast***********/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                item.getTitle();
                final Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                searchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(searchIntent);
                // Not implemented here
                return false;
            case R.id.action_filter:

                // Not implemented here
                return false;
            case R.id.action_login:

                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                Util.check_for_subscription = 0;
                startActivity(loginIntent);
                // Not implemented here
                return false;
            case R.id.action_register:

                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
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
            case R.id.action_mydownload:

                Intent mydownload = new Intent(MainActivity.this, MyDownloads.class);
                startActivity(mydownload);
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

                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileIntent.putExtra("EMAIL", email);
                profileIntent.putExtra("LOGID", id);
                startActivity(profileIntent);
                // Not implemented here
                return false;
            case R.id.action_purchage:

                Intent purchaseintent = new Intent(MainActivity.this, PurchaseHistoryActivity.class);
                startActivity(purchaseintent);
                // Not implemented here
                return false;
            case R.id.action_logout:

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(SIGN_OUT_WARNING, DEFAULT_SIGN_OUT_WARNING));
                dlgAlert.setTitle("");

                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(YES, DEFAULT_YES), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        // dialog.cancel();
                        if(NetworkStatus.getInstance().isConnected(MainActivity.this)) {
                            LogoutInput logoutInput = new LogoutInput();
                            logoutInput.setAuthToken(authTokenStr);
                            LogUtil.showLog("Abhi", authTokenStr);
                            String loginHistoryIdStr = preferenceManager.getLoginHistIdFromPref();
                            logoutInput.setLogin_history_id(loginHistoryIdStr);
                            logoutInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            LogUtil.showLog("Abhi", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            LogoutAsynctask asynLogoutDetails = new LogoutAsynctask(logoutInput, MainActivity.this, MainActivity.this);
                            asynLogoutDetails.executeOnExecutor(threadPoolExecutor);



                            dialog.dismiss();
                        }else {
                            Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
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
           /* dlgAlert.setNegativeButton(getResources().getString(R.string.no_str),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no_str),
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });*/
                dlgAlert.create().show();

               /* Intent lanuageIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);*/
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    //*************chromecast*****************//*
    @Override
    public void onResume() {
        super.onResume();
        mCastContext.addCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }
//        if (mCastSession == null) {
//            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
//                    .getCurrentCastSession();
//        }
//        removeFocusFromViews();

        invalidateOptionsMenu();
    }


    /*************chromecast*****************/


   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }*/

    //Selection of menu Item
    @Override
    public void onDrawerItemSelected(View view, int position) {
        check = position;
        //  displayView(position);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        check = position;
        Toast.makeText(MainActivity.this, "ckjw", Toast.LENGTH_SHORT).show();
        // displayView(position);

    }

    private void displayView(int position) {

        isNavigated = 1;

        String title = getString(R.string.app_name);
     /*   SharedPreferences.Editor dataEditor = dataPref.edit();*/
        Bundle bundle = new Bundle();
        String str = menuList.get(position).getPermalink();
        String titleStr = menuList.get(position).getTitle();
        // state = position;

        if (internetSpeedDialog != null && internetSpeedDialog.isShowing()) {
            internetSpeedDialog.hide();
            internetSpeedDialog = null;

        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

        }
        if (str != null && !str.equalsIgnoreCase("") && !str.isEmpty() && menuList.get(position).getLinkType().equalsIgnoreCase("-101")) {

            fragment = new HomeFragment();
            bundle.putString("item", str);


        } else if (menuList.get(position).getLinkType().equalsIgnoreCase("102")) {

            fragment = new MyLibraryFragment();
            bundle.putString("title", titleStr);

        } else if (menuList.get(position).getIsEnabled() == false) {

            if (str.equals("contactus")) {

                fragment = new ContactUsFragment();
                bundle.putString("title", titleStr);


            } else {


              /*  if (menuList.get(position).getLinkType().trim().equalsIgnoreCase("external")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuList.get(position).getUrl().trim()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(browserIntent);
                    return;
                }else {
                    fragment = new AboutUs();
                    bundle.putString("item", str);
                    bundle.putString("title", titleStr);
                }*/


                fragment = new AboutUsFragment();
                bundle.putString("item", str);
                bundle.putString("title", titleStr);

                Log.v("ANU", "item======" + str);
                Log.v("ANU", "title======" + titleStr);

            }


        } else if (menuList.get(position).getIsEnabled() == true) {

            fragment = new VideosListFragment();
            bundle.putString("item", str);
            bundle.putString("title", titleStr);


        }
       /* else if (menuList.get(position).getIsEnabled() == false) {

            fragment = new WebViewFragment();
            bundle.putString("item", getResources().getString(R.string.studio_site)+str);

        }*/
        fragment.setArguments(bundle);

      /*  dataEditor.putString("state", String.valueOf(state));
                dataEditor.commit();*/



      /*  if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }*/
    }

    //Display View based on selection of menu item

   /* private void displayView(int position) {

        isNavigated = 1;

        String title = getString(R.string.app_name);
     *//*   SharedPreferences.Editor dataEditor = dataPref.edit();*//*
        Bundle bundle = new Bundle();
        String str = menuList.get(position).getPermalink();
        String titleStr = menuList.get(position).getTitle();
        // state = position;

        if (internetSpeedDialog != null && internetSpeedDialog.isShowing()) {
            internetSpeedDialog.hide();
            internetSpeedDialog = null;

        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

        }
        if (str != null && !str.equalsIgnoreCase("") && !str.isEmpty() && menuList.get(position).getLinkType().equalsIgnoreCase("-101")) {

            fragment = new HomeFragment();
            bundle.putString("item", str);


        } else if (menuList.get(position).getLinkType().equalsIgnoreCase("102")) {

            fragment = new MyLibraryFragment();
            bundle.putString("title", titleStr);

        } else if (menuList.get(position).getIsEnabled() == false) {

            if (str.equals("contactus")) {

                fragment = new ContactUsFragment();
                bundle.putString("title", titleStr);


            } else {


                if (menuList.get(position).getLinkType().trim().equalsIgnoreCase("2")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(menuList.get(position).getPermalink().trim()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(browserIntent);
                    return;
                } else {


                    fragment = new AboutUsFragment();
                    bundle.putString("item", str);
                    bundle.putString("title", titleStr);

                }


            }
        } else if (menuList.get(position).getIsEnabled() == true) {

            fragment = new VideosListFragment();
            bundle.putString("item", str);
            bundle.putString("title", titleStr);


        }
       *//* else if (menuList.get(position).getIsEnabled() == false) {

            fragment = new WebViewFragment();
            bundle.putString("item", getResources().getString(R.string.studio_site)+str);

        }*//*
        fragment.setArguments(bundle);

      *//*  dataEditor.putString("state", String.valueOf(state));
                dataEditor.commit();*//*


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (asynLoadMenuItems != null) {
            asynLoadMenuItems.cancel(true);
        }


        if (as != null) {
            as.cancel(true);
        }

        // Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_body);


        super.onBackPressed();


    }


    @Override
    public void onLogoutPreExecuteStarted() {
        pDialog = new ProgressBarHandler(MainActivity.this);
        pDialog.show();
    }

    @Override
    public void onLogoutPostExecuteCompleted(int code, String status, String message) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

        }
        if (code != 200) {
            Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
        if (code == 0) {
            Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

        }
            if (code > 0) {
                if (code == 200) {
                    preferenceManager.clearLoginPref();

                    if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
                        final Intent startIntent = new Intent(MainActivity.this, SplashScreen.class);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(startIntent);
                                Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();

                                finish();

                            }
                        });
                    } else {
                        final Intent startIntent = new Intent(MainActivity.this, MainActivity.class);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(startIntent);
                                Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(LOGOUT_SUCCESS, DEFAULT_LOGOUT_SUCCESS), Toast.LENGTH_LONG).show();

                                finish();

                            }
                        });
                    }


                } else {
                    Toast.makeText(MainActivity.this, languagePreference.getTextofLanguage(SIGN_OUT_ERROR, DEFAULT_SIGN_OUT_ERROR), Toast.LENGTH_LONG).show();

                }
            }

    }


    public void ShowLanguagePopup() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
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

        languageCustomAdapter = new LanguageCustomAdapter(MainActivity.this, languageModel);

        recyclerView.setAdapter(languageCustomAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener1(MainActivity.this, recyclerView, new ClickListener1() {
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
                    GetTranslateLanguageAsync asynGetTransalatedLanguage = new GetTranslateLanguageAsync(languageListInputModel, MainActivity.this, MainActivity.this);
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
    public void onGetLanguageListPreExecuteStarted() {
        pDialog = new ProgressBarHandler(MainActivity.this);
        pDialog.show();
    }

    @Override
    public void onGetLanguageListPostExecuteCompleted(ArrayList<LanguageListOutputModel> languageListOutputArray, int status, String message, String defaultLanguage) {

        if (pDialog != null && pDialog.isShowing()) {
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
    public void onGetTranslateLanguagePreExecuteStarted() {

        pDialog = new ProgressBarHandler(MainActivity.this);
        pDialog.show();
    }

    @Override
    public void onGetTranslateLanguagePostExecuteCompleted(String jsonResponse, int status) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog = null;

        }

        if (status > 0 && status == 200) {

            try {

                Util.parseLanguage(languagePreference, jsonResponse, Default_Language);

                languageCustomAdapter.notifyDataSetChanged();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);


            } catch (JSONException e) {
                e.printStackTrace();
                noInternetLayout.setVisibility(View.GONE);
            }
            // Call For Other Methods.


        } else {
            noInternetLayout.setVisibility(View.GONE);
        }

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            if (NetworkStatus.getInstance().isConnected(MainActivity.this)) {
                switch (msg.what) {
                    case MSG_UPDATE_STATUS:
                        break;
                    case MSG_UPDATE_CONNECTION_TIME:

                        break;
                    case MSG_COMPLETE_STATUS:
                        final SpeedInfo info2 = (SpeedInfo) msg.obj;
                        String downloadedSpeed = String.format("%.1f", info2.megabits);
                        internetSpeed = downloadedSpeed;

                        break;
                    default:
                        internetSpeed = "0";

                        super.handleMessage(msg);
                }
            } else {
                internetSpeed = "0";

            }
        }
    };

    /**
     * Our Slave worker that does actually all the work
     */
    private final Runnable mWorker = new Runnable() {
        @Override
        public void run() {
            InputStream stream = null;

            try {
                int bytesIn = 0;

                String downloadFileUrl = imageUrlStr;
                long startCon = System.currentTimeMillis();
                URL url = new URL(downloadFileUrl);
                URLConnection con = url.openConnection();
                con.setUseCaches(false);
                long connectionLatency = System.currentTimeMillis() - startCon;
                stream = con.getInputStream();

                Message msgUpdateConnection = Message.obtain(mHandler, MSG_UPDATE_CONNECTION_TIME);
                msgUpdateConnection.arg1 = (int) connectionLatency;
                mHandler.sendMessage(msgUpdateConnection);

                long start = System.currentTimeMillis();
                int currentByte = 0;
                long updateStart = System.currentTimeMillis();
                long updateDelta = 0;
                int bytesInThreshold = 0;

                while ((currentByte = stream.read()) != -1) {
                    bytesIn++;
                    bytesInThreshold++;

                    if (updateDelta >= UPDATE_THRESHOLD) {
                        int progress = (int) ((bytesIn / (double) EXPECTED_SIZE_IN_BYTES) * 100);

                        Message msg = Message.obtain(mHandler, MSG_UPDATE_STATUS, calculate(updateDelta, bytesInThreshold));
                        msg.arg1 = progress;
                        msg.arg2 = bytesIn;
                        mHandler.sendMessage(msg);
                        //Reset
                        updateStart = System.currentTimeMillis();
                        bytesInThreshold = 0;
                    }
                    updateDelta = System.currentTimeMillis() - updateStart;
                }

                long downloadTime = (System.currentTimeMillis() - start);
                //Prevent AritchmeticException
                if (downloadTime == 0) {
                    downloadTime = 1;
                }

                Message msg = Message.obtain(mHandler, MSG_COMPLETE_STATUS, calculate(downloadTime, bytesIn));
                msg.arg1 = bytesIn;
                mHandler.sendMessage(msg);
            } catch (MalformedURLException e) {

                internetSpeed = "0";
            } catch (IOException e) {

                internetSpeed = "0";

            } finally {
                try {
                    if (stream != null) {
                        stream.close();

                    }
                } catch (IOException e) {
                    //Suppressed
                    internetSpeed = "0";
                }
            }


        }

    };


    /**
     * Get Network type from download rate
     *
     * @return 0 for Edge and 1 for 3G
     */
    private int networkType(final double kbps) {
        int type = 1;//3G
        //Check if its EDGE
        if (kbps < EDGE_THRESHOLD) {
            type = 0;
        }
        return type;
    }

    /**
     * 1 byte = 0.0078125 kilobits
     * 1 kilobits = 0.0009765625 megabit
     *
     * @param downloadTime in miliseconds
     * @param bytesIn      number of bytes downloaded
     * @return SpeedInfo containing current speed
     */
    private SpeedInfo calculate(final long downloadTime, final long bytesIn) {
        SpeedInfo info = new SpeedInfo();
        //from mil to sec
        long bytespersecond = (bytesIn / downloadTime) * 1000;
        double kilobits = bytespersecond * BYTE_TO_KILOBIT;
        double megabits = kilobits * KILOBIT_TO_MEGABIT;
        info.downspeed = bytespersecond;
        info.kilobits = kilobits;
        info.megabits = megabits;

        return info;
    }

    /**
     * Transfer Object
     *
     * @author devil
     */
    private static class SpeedInfo {
        public double kilobits = 0;
        public double megabits = 0;
        public double downspeed = 0;
    }


    //Private fields
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int EXPECTED_SIZE_IN_BYTES = 1048576;//1MB 1024*1024

    private static final double EDGE_THRESHOLD = 176.0;
    private static final double BYTE_TO_KILOBIT = 0.0078125;
    private static final double KILOBIT_TO_MEGABIT = 0.0009765625;

    private final int MSG_UPDATE_STATUS = 0;
    private final int MSG_UPDATE_CONNECTION_TIME = 1;
    private final int MSG_COMPLETE_STATUS = 2;

    private final static int UPDATE_THRESHOLD = 300;


    private DecimalFormat mDecimalFormater;


    //**** chromecast*************//*

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        return mCastContext.onDispatchVolumeKeyEventBeforeJellyBean(event)
                || super.dispatchKeyEvent(event);
    }


    @Override
    protected void onPause() {
        mCastContext.removeCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().removeSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        super.onPause();
    }


    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                            MainActivity.this, mediaRouteMenuItem)
                            .setTitleText(getString(R.string.introducing_cast))
                            .setOverlayColor(R.color.colorPrimaryDark)
                            .setSingleTime()
                            .setOnOverlayDismissedListener(
                                    new IntroductoryOverlay.OnOverlayDismissedListener() {
                                        @Override
                                        public void onOverlayDismissed() {
                                            mIntroductoryOverlay = null;
                                        }
                                    })
                            .build();
                    mIntroductoryOverlay.show();
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

                    if (mPlaybackState == PlaybackState.PLAYING) {
                       /* mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);*/
                        return;
                    } else {

                        mPlaybackState = PlaybackState.IDLE;
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    }
                }
                //   updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {
               /* if (mCastSession != null && mCastSession.isConnected()) {
                    watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                }*/
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                updatePlaybackLocation(PlaybackLocation.LOCAL);
                mPlaybackState = PlaybackState.IDLE;
                mLocation = PlaybackLocation.LOCAL;

                invalidateOptionsMenu();
            }
        };
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
                Intent intent = new Intent(MainActivity.this, ExpandedControlsActivity.class);
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

   /* private void setCoverArtStatus(String url) {
        if (url != null) {
            mAquery.id(mCoverArt).image(url);
            mCoverArt.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.INVISIBLE);
        } else {
            mCoverArt.setVisibility(View.GONE);
            mVideoView.setVisibility(View.VISIBLE);
        }
    }*/

    private void stopTrickplayTimer() {
     /*   if (mSeekbarTimer != null) {
            mSeekbarTimer.cancel();
        }*/
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
            getSupportActionBar().show();
            // mControllers.setVisibility(View.VISIBLE);
        } else {
            if (!Util.isOrientationPortrait(this)) {
                getSupportActionBar().hide();
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
                    //  mControllersVisible = false;
                }
            });

        }
    }

    /**** chromecast*************/


    public void removeFocusFromViews() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * For Handling The Search Menu Action
     */

    public void actionSearchHendler() {
        final Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
        searchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(searchIntent);
    }

}


