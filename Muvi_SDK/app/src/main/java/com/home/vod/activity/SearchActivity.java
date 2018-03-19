package com.home.vod.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.home.apisdk.apiController.SearchDataAsynTask;
import com.home.apisdk.apiModel.Search_Data_input;
import com.home.apisdk.apiModel.Search_Data_otput;
import com.home.vod.R;
import com.home.vod.adapter.VideoFilterAdapter;
import com.home.vod.model.GridItem;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.SearchProgressHandler;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_RESULT_FOUND_REFINE_YOUR_SEARCH;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SEARCH_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SEARCH_PLACEHOLDER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_SEARCH_PLACEHOLDER;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.NO_RESULT_FOUND_REFINE_YOUR_SEARCH;
import static com.home.vod.preferences.LanguagePreference.SEARCH_ALERT;
import static com.home.vod.preferences.LanguagePreference.SEARCH_PLACEHOLDER;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.TEXT_SEARCH_PLACEHOLDER;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;

/*
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyHelper;
*/

/**
 * Created by user on 28-06-2015.
 */

public class SearchActivity extends AppCompatActivity implements SearchDataAsynTask.SearchDataListener {
    ProgressBarHandler videoPDialog;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    int previousTotal = 0;
    ProgressBarHandler pDialog;
    private boolean mIsScrollingUp;
    private int mLastFirstVisibleItem;
    int scrolledPosition = 0;
    boolean scrolling;
    Toast toast;
    //    ProgressBarHandler searchProgressBarHandler;
    SearchProgressHandler searchProgressBarHandler;

    String videoImageStrToHeight;
    int videoHeight = 185;
    int videoWidth = 256;
    // SharedPreferences pref;
    GridItem itemToPlay;
    LanguagePreference languagePreference;
    SearchDataAsynTask searchDataAsynTask;

    private static int firstVisibleInListview;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    //for no internet

    private RelativeLayout noInternetConnectionLayout;

    //firsttime load
    boolean firstTime = false;

    //data to load videourl
    private String movieUniqueId;
    private String movieStreamUniqueId;
    // String videoUrlStr;
    String videoResolution = "BEST";


    //search
    String searchTextStr;
    boolean isSearched = false;
    private SearchView.SearchAutoComplete theTextArea;



    /* Handling GridView Scrolling*/


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

    //Set Context

    //Adapter for GridView
    private VideoFilterAdapter customGridAdapter;
    Toolbar mActionBarToolbar;

    //Model for GridView
    ArrayList<GridItem> itemData = new ArrayList<GridItem>();
    GridLayoutManager mLayoutManager;
    String posterUrl;

    // UI
    private GridView gridView;
    RelativeLayout footerView;
    private String movieVideoUrlStr = "";
    //private String movieThirdPartyUrl = "";
    TextView noDataTextView;
    TextView noInternetTextView;
    PreferenceManager preferenceManager;

    public SearchActivity() {
        // Required empty public constructor

    }

    View header;
    private boolean isLoading = false;
    private int lastVisibleItem, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        languagePreference = LanguagePreference.getLanguagePreference(SearchActivity.this);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        searchProgressBarHandler = new SearchProgressHandler(SearchActivity.this);

        posterUrl = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);

        gridView = (GridView) findViewById(R.id.imagesGridView);
       /* gridView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(SearchActivity.this,2);
        gridView.setLayoutManager(mLayoutManager);
        gridView.setItemAnimator(new DefaultItemAnimator());*/

        footerView = (RelativeLayout) findViewById(R.id.loadingPanel);

        noInternetConnectionLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_RESULT_FOUND_REFINE_YOUR_SEARCH, DEFAULT_NO_RESULT_FOUND_REFINE_YOUR_SEARCH));

        noInternetConnectionLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);



        //Detect Network Connection


        if (!NetworkStatus.getInstance().isConnected(SearchActivity.this)) {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT; //this is in pixels
        gridView.setLayoutParams(layoutParams);
       /* gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setGravity(Gravity.CENTER_HORIZONTAL);*/
        resetData();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                itemToPlay = itemData.get(position);
                String moviePermalink = itemToPlay.getPermalink();
                String movieTypeId = itemToPlay.getVideoTypeId();
                // if searched

                // for tv shows navigate to episodes
                if ((movieTypeId.equalsIgnoreCase("3"))) {
                    if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SearchActivity.this, R.style.MyAlertDialogStyle);
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

                        final Intent detailsIntent = new Intent(SearchActivity.this, ShowWithEpisodesActivity.class);
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
                    final Intent detailsIntent = new Intent(SearchActivity.this, MovieDetailsActivity.class);

                    if (moviePermalink.matches(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA))) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SearchActivity.this, R.style.MyAlertDialogStyle);
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
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

           /*     if (gridView.getLastVisiblePosition() >= itemsInServer - 1) {
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

                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                int daat = gridView.getLastVisiblePosition();

                if((gridView.getLastVisiblePosition() == totalItemCount-1)  && (itemsInServer > totalItemCount) && !scrolling  && itemsInServer>limit){
                    scrolling = true;
                    offset += 1;
                    getSearchData();
                    Log.v("SUBHASEARCH","onscroll ==== ");
                }

                /*if (scrolling == true && mIsScrollingUp == false) {

                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {

                        listSize = itemData.size();
                        if (gridView.getLastVisiblePosition() >= itemsInServer - 1) {
                            return;

                        }
                        offset += 1;

                        if (NetworkStatus.getInstance().isConnected(SearchActivity.this)) {


                            // default data
                            Search_Data_input search_data_input = new Search_Data_input();
                            search_data_input.setAuthToken(authTokenStr);
                            search_data_input.setLimit(String.valueOf(limit));
                            search_data_input.setOffset(String.valueOf(offset));
                            search_data_input.setQ(searchTextStr.trim());
                            String countryCodeStr = preferenceManager.getCountryCodeFromPref();
                            if (countryCodeStr != null) {
                                search_data_input.setCountry(countryCodeStr);
                            } else {
                                search_data_input.setCountry("IN");
                            }
                            search_data_input.setLanguage_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            SearchDataAsynTask asyncLoadVideos = new SearchDataAsynTask(search_data_input, SearchActivity.this, SearchActivity.this);
                            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);


                            scrolling = false;

                        }

                    }

                }*/

            }
        });

    }

    @Override
    public void onBackPressed() {
     /*   if (asyncViewFavorite!=null){
            asyncViewFavorite.cancel(true);
        }
        if (asynLoadVideoUrls!=null){
            asynLoadVideoUrls.cancel(true);
        }*/
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }

    @Override
    public void onSearchDataPreexecute() {

        if (searchProgressBarHandler != null && searchProgressBarHandler.isShowing()) {
        } else {
            searchProgressBarHandler = new SearchProgressHandler(SearchActivity.this);
        }
        searchProgressBarHandler.show();
        if (listSize == 0) {
            footerView.setVisibility(View.GONE);
        } else {
            footerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSearchDataPostExecuteCompleted(ArrayList<Search_Data_otput> contentListOutputArray, int status, int totalItems, String message) {

        itemsInServer=totalItems;
        String videoGenreStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String videoName = "";
        String videoImageStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String videoPermalinkStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String videoTypeStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String videoTypeIdStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String videoUrlStr = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        String isEpisodeStr = "";
        String movieUniqueIdStr = "";
        String movieStreamUniqueIdStr = "";
        int isConverted = 0;
        int isAPV = 0;
        int isPPV = 0;
        String movieThirdPartyUrl = "";

        try {
            if (searchProgressBarHandler != null && searchProgressBarHandler.isShowing()) {
                searchProgressBarHandler.hide();
            }
        }catch (IllegalArgumentException ex) {

        }

        if (status > 0) {
            if (status == 200) {
                gridView.setVisibility(View.VISIBLE);
                noInternetConnectionLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);

                if (contentListOutputArray.size() > 0) {

                    gridView.setVisibility(View.VISIBLE);
                    noInternetConnectionLayout.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.GONE);



                    for (int i = 0; i < contentListOutputArray.size(); i++) {


                        videoImageStr = contentListOutputArray.get(i).getPoster_url();
                        videoName = contentListOutputArray.get(i).getEpisode_title();
                        videoTypeIdStr = contentListOutputArray.get(i).getContent_types_id();
                        videoGenreStr = contentListOutputArray.get(i).getGenre();
                        videoPermalinkStr = contentListOutputArray.get(i).getPermalink();
                        isEpisodeStr = contentListOutputArray.get(i).getIs_episode();
                        isConverted = contentListOutputArray.get(i).getIs_converted();
                        isPPV = contentListOutputArray.get(i).getIs_ppv();
                        isAPV = contentListOutputArray.get(i).getIs_advance();
                        itemData.add(new GridItem(videoImageStr, videoName, "", videoTypeIdStr, videoGenreStr, "", videoPermalinkStr, isEpisodeStr, "", "", isConverted, isPPV, isAPV));
                    }

                    videoImageStrToHeight = videoImageStr;

                    if (firstTime == true){

                        new RetrieveFeedTask().execute(videoImageStrToHeight);

                        /*Picasso.with(SearchActivity.this).load(videoImageStrToHeight
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

                    }else {
                        AsynLOADUI loadUI = new AsynLOADUI();
                        loadUI.executeOnExecutor(threadPoolExecutor);
                    }

                } else {

                    noDataLayout.setVisibility(View.VISIBLE);
                    noInternetConnectionLayout.setVisibility(View.GONE);
                    gridView.setVisibility(View.GONE);
                    footerView.setVisibility(View.GONE);

                }

            } else {

                noDataLayout.setVisibility(View.VISIBLE);
                noInternetConnectionLayout.setVisibility(View.GONE);
                gridView.setVisibility(View.GONE);
                footerView.setVisibility(View.GONE);

            }
        } else {

            noDataLayout.setVisibility(View.VISIBLE);
            noInternetConnectionLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            footerView.setVisibility(View.GONE);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_search, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setImeOptions(0);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(languagePreference.getTextofLanguage(SEARCH_PLACEHOLDER, DEFAULT_SEARCH_PLACEHOLDER));
        searchView.requestFocus();
        int searchImgId = getResources().getIdentifier(String.valueOf(R.id.search_button), null, null);
        ImageView v = (ImageView) searchView.findViewById(searchImgId);

        searchView.setMaxWidth(10000);

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close_24dp);
        ImageView imgView = (ImageView) searchView.findViewById(R.id.search_mag_icon);
        imgView.setImageResource(R.drawable.ic_search);

        final SearchView.SearchAutoComplete theTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        theTextArea.setBackgroundResource(R.drawable.edit);
        theTextArea.setHint(languagePreference.getTextofLanguage(TEXT_SEARCH_PLACEHOLDER, DEFAULT_TEXT_SEARCH_PLACEHOLDER));
        theTextArea.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        theTextArea.setHintTextColor(getResources().getColor(R.color.search_hint_color));//or any color that you want
        theTextArea.setTextColor(getResources().getColor(R.color.search_text_color));

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(theTextArea, R.drawable.cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }



        theTextArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(theTextArea.getText().toString().trim().length()>=1){

                    if(searchDataAsynTask!=null){
                        searchDataAsynTask.cancel(true);
                    }

                    searchTextStr = theTextArea.getText().toString().trim();
                    getSearchData();
                    Log.v("MUVI","theTextArea.addTextChangedListener ==== ");

                    if(toast != null){
                        toast.cancel();
                    }
                }
                else{
                    try {
                        if (searchDataAsynTask != null) {
                            searchDataAsynTask.cancel(true);
                        }

                        if (searchProgressBarHandler != null && searchProgressBarHandler.isShowing()) {
                            searchProgressBarHandler.hide();
                        }
                        if(toast != null){
                            toast.cancel();
                        }

                    } catch (IllegalArgumentException ex) {

                    }

                    // Show message.
                 //   ShowToast(SearchActivity.this,"Please enter one letters for search .");
                }
                resetData();
                noDataLayout.setVisibility(View.GONE);
//                titleLayout.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        theTextArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stopCallingSearch = true;
            }
        });


/*

        theTextArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == event.KEYCODE_SEARCH) {
                    // Your piece of code on keyboard search click
                    String query = theTextArea.getText().toString().trim();
                    if (query.equalsIgnoreCase("") || query == null) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SearchActivity.this, R.style.MyAlertDialogStyle);
                        dlgAlert.setMessage(languagePreference.getTextofLanguage(SEARCH_ALERT, DEFAULT_SEARCH_ALERT));
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
                        resetData();
                        firstTime = true;

                        offset = 1;
                        listSize = 0;
                        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
                            limit = 20;
                        } else {
                            limit = 15;
                        }
                        itemsInServer = 0;
                        isLoading = false;
                        searchTextStr = query;
                        if (itemData != null && itemData.size() > 0) {
                            itemData.clear();
                        }
                        isSearched = true;
                        if (!NetworkStatus.getInstance().isConnected(SearchActivity.this)) {
                            noInternetConnectionLayout.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.GONE);

                        } else {
                            Search_Data_input search_data_input = new Search_Data_input();
                            search_data_input.setAuthToken(authTokenStr);
                            search_data_input.setLimit(String.valueOf(limit));
                            search_data_input.setOffset(String.valueOf(offset));
                            search_data_input.setQ(searchTextStr.trim());
                            Log.v("pratik","q===="+searchTextStr.trim());
                            String countryCodeStr = preferenceManager.getCountryCodeFromPref();
                            if (countryCodeStr != null) {
                                search_data_input.setCountry(countryCodeStr);
                            } else {
                                search_data_input.setCountry("IN");
                            }

                            search_data_input.setLanguage_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            SearchDataAsynTask asyncLoadVideos = new SearchDataAsynTask(search_data_input, SearchActivity.this, SearchActivity.this);
                            asyncLoadVideos.executeOnExecutor(threadPoolExecutor);

                        }
                    }
                    return true;
                }

                return false;
            }
        });*/

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        return true;
    }


    private class AsynLOADUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        protected void onPostExecute(Void result) {
            float density = getResources().getDisplayMetrics().density;

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
                    footerView.setVisibility(View.GONE);
                }

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
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_grid_layout, itemData);

                    }
                    // customGridAdapter = new VideoFilterAdapter(context, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }
              /*  if (videoWidth > videoHeight) {
                    customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_280_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                } else {
                    customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }
*/

            } else {
                // save RecyclerView state
                mBundleRecyclerViewState = new Bundle();
                Parcelable listState = gridView.onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);


              /*  if (videoWidth > videoHeight) {
                    customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_280_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                } else {
                    customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_grid_layout, itemData);
                    gridView.setAdapter(customGridAdapter);
                }*/
                if (videoWidth > videoHeight) {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.nexus_videos_grid_layout_land, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_280_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                } else {
                    if (density >= 3.5 && density <= 4.0) {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.nexus_videos_grid_layout, itemData);
                    } else {
                        customGridAdapter = new VideoFilterAdapter(SearchActivity.this, R.layout.videos_grid_layout, itemData);

                    }
                    gridView.setAdapter(customGridAdapter);
                }
                if (mBundleRecyclerViewState != null) {
                    gridView.onRestoreInstanceState(listState);
                }

            }
            Util.hideKeyboard(SearchActivity.this);
        }
    }

    public void onResume() {
        super.onResume();
        //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);


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
            try {
                customGridAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        isSearched = false;
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

    /*
        This method is responsible for getting searching data form server .
     */

    public void getSearchData(){
        Search_Data_input search_data_input = new Search_Data_input();
        search_data_input.setAuthToken(authTokenStr);
        search_data_input.setLimit(String.valueOf(limit));
        search_data_input.setOffset(String.valueOf(offset));
        search_data_input.setQ(searchTextStr.trim());
        String countryCodeStr = preferenceManager.getCountryCodeFromPref();
        if (countryCodeStr != null) {
            search_data_input.setCountry(countryCodeStr);
        } else {
            search_data_input.setCountry("IN");
        }
        searchDataAsynTask = new SearchDataAsynTask(search_data_input, SearchActivity.this, SearchActivity.this);
        searchDataAsynTask.executeOnExecutor(threadPoolExecutor);
    }


    public void ShowToast(Context context, String info) {
        toast = Toast.makeText(context, Html.fromHtml("<font color='"+getResources().getColor(R.color.hint_background)+"' ><b>" + info + "</b></font>"), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}

