package com.home.vod.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.home.apisdk.apiController.GetAppHomePageAsync;
import com.home.apisdk.apiController.GetLoadVideosAsync;
import com.home.apisdk.apiModel.AppHomePageOutput;
import com.home.apisdk.apiModel.HomePageBannerModel;
import com.home.apisdk.apiModel.HomePageInputModel;
import com.home.apisdk.apiModel.HomePageSectionModel;
import com.home.apisdk.apiModel.LoadVideoInput;
import com.home.apisdk.apiModel.LoadVideoOutput;
import com.home.vod.R;
import com.home.vod.activity.MainActivity;
import com.home.vod.adapter.RecyclerViewDataAdapter;
import com.home.vod.model.GetMenuItem;
import com.home.vod.model.SectionDataModel;
import com.home.vod.model.SingleItemModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.Constant;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.util.Constant.authTokenStr;

/**
 * Created by Muvi on 11/24/2016.
 */
public class HomeFragment extends Fragment implements
        GetLoadVideosAsync.LoadVideosAsyncListener, GetAppHomePageAsync.HomePageListener {

    //    int bannerArray[] = {R.drawable.banner1};
    int videoHeight = 185;
    int videoWidth = 256;

    GetLoadVideosAsync asynLoadVideos;
    //    AsynLOADUI loadui;
    View rootView;
    int item_CountOfSections = 0;
    boolean isFirstTime = false;
    int counter = 0;
    ArrayList<GetMenuItem> menuList;
    ArrayList<String> url_maps;

    private ProgressBarHandler mProgressBarHandler = null;

    //private ProgressDialog videoPDialog = null;
    private RelativeLayout noInternetLayout;
    RelativeLayout noDataLayout;
    TextView noDataTextView;
    TextView noInternetTextView;
    LanguagePreference languagePreference;

    RecyclerView my_recycler_view;
    Context context;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;


    ArrayList<SectionDataModel> allSampleData;
    ArrayList<SingleItemModel> singleItem;

    //AsynLoadImageUrls as = null;
    GetAppHomePageAsync asynLoadMenuItems = null;
    /* int bannerArray[] = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
     int bannerL[] = {R.drawable.banner1_l,R.drawable.banner2_l,R.drawable.banner3_l};*/
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    private String imageUrlStr;
    private int listSize = 0;
    RecyclerView recycler_view_list;
    private boolean firstTime = false;
    RecyclerViewDataAdapter adapter;
    LinearLayoutManager mLayoutManager;
    RelativeLayout footerView;
    int bannerLoaded = 0;
    RelativeLayout sliderRelativeLayout;
    private SliderLayout mDemoSlider;
    String videoImageStrToHeight;
    int ui_completed = 0;
    int loading_completed = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rootView = v;
        context = getActivity();
        setHasOptionsMenu(true);
        Util.image_orentiation.clear();
        languagePreference = LanguagePreference.getLanguagePreference(getActivity());
        LogUtil.showLog("MUVI", "device_id already created =" + Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        String GOOGLE_FCM_TOKEN;
        // LogUtil.showLog("MUVI", "google_id already created =" + languagePreference.getTextofLanguage( GOOGLE_FCM_TOKEN, DEFAULT_GOOGLE_FCM_TOKEN));
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));


 /*       *//***************chromecast**********************//*

        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState != CastState.NO_DEVICES_AVAILABLE) {

                    showIntroductoryOverlay();
                }
            }
        };
        mCastContext = CastContext.getSharedInstance(getActivity());
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(getActivity(), savedInstanceState);



        // int startPosition = getInt("startPosition", 0);
        // mVideoView.setVideoURI(Uri.parse(item.getContentId()));

        setupCastListener();
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

*//***************chromecast**********************//*
*/
        allSampleData = new ArrayList<SectionDataModel>();
        // createDummyData();
        footerView = (RelativeLayout) v.findViewById(R.id.loadingPanel);
        my_recycler_view = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        sliderRelativeLayout = (RelativeLayout) v.findViewById(R.id.sliderRelativeLayout);
        mDemoSlider = (SliderLayout) v.findViewById(R.id.sliderLayout);

        sliderRelativeLayout.setVisibility(View.GONE);
        noInternetLayout = (RelativeLayout) rootView.findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) rootView.findViewById(R.id.noData);
        noInternetTextView = (TextView) rootView.findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) rootView.findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));

        footerView.setVisibility(View.GONE);

        my_recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        if (NetworkStatus.getInstance().isConnected(getActivity())) {
            // default data
            menuList = new ArrayList<GetMenuItem>();

            url_maps = new ArrayList<String>();

            HomePageInputModel homePageInputModel = new HomePageInputModel();
            homePageInputModel.setAuthToken(authTokenStr);
            homePageInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            asynLoadMenuItems = new GetAppHomePageAsync(homePageInputModel, this, context);
            asynLoadMenuItems.executeOnExecutor(threadPoolExecutor);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        return v;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here

        MenuItem item;
        item = menu.findItem(R.id.action_filter);
        item.setVisible(false);
      /*  *//***************chromecast**********************//*


        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getActivity(), menu, R.id.media_route_menu_item);
        showIntroductoryOverlay();

        *//***************chromecast**********************/

        super.onCreateOptionsMenu(menu, inflater);

    }


  /*  private void StartAsyncTaskInParallel(AsynLoadMenuItems task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }*/


    @Override
    public void onLoadVideosAsyncPreExecuteStarted() {

        if (firstTime == false) {

           mProgressBarHandler = new ProgressBarHandler(context);
            mProgressBarHandler.show();

        } else {

        }
    }

    @Override
    public void onLoadVideosAsyncPostExecuteCompleted(ArrayList<LoadVideoOutput> loadVideoOutputs, int code, String status) {

        try {
            if (mProgressBarHandler != null && mProgressBarHandler.isShowing()) {
                mProgressBarHandler.hide();
                mProgressBarHandler = null;
            }
        } catch (IllegalArgumentException ex) {

        }
        String movieImageStr = "";
         if (code==200) {
             if (loadVideoOutputs != null) {

                 singleItem = new ArrayList<SingleItemModel>();

                 for (int i = 0; i < loadVideoOutputs.size(); i++) {
                     movieImageStr = loadVideoOutputs.get(i).getPoster_url();
                     String movieName = loadVideoOutputs.get(i).getName();
                     String videoTypeIdStr = loadVideoOutputs.get(i).getContent_types_id();
                     String movieGenreStr = loadVideoOutputs.get(i).getGenre();
                     String moviePermalinkStr = loadVideoOutputs.get(i).getPermalink();
                     String isEpisodeStr = loadVideoOutputs.get(i).getIs_episode();
                     int isConverted = loadVideoOutputs.get(i).getIs_converted();
                     int isPPV = loadVideoOutputs.get(i).getIs_ppv();
                     int isAPV = loadVideoOutputs.get(i).getIs_advance();


                     singleItem.add(new SingleItemModel(movieImageStr, movieName, "", videoTypeIdStr, movieGenreStr, "", moviePermalinkStr, isEpisodeStr, "", "", isConverted, isPPV, isAPV));
                 }

                 allSampleData.add(new SectionDataModel(menuList.get(counter).getName(), menuList.get(counter).getSectionId(), singleItem));


                 if (NetworkStatus.getInstance().isConnected(getActivity())) {

                     if (getActivity() != null) {
                         new RetrieveFeedTask().execute(movieImageStr);
                     }


                 } else {
                     noInternetLayout.setVisibility(View.VISIBLE);
                 }
             }

         }
         else {
             if (counter >= 0 && counter < menuList.size() - 1) {
                 counter++;
                 LoadVideoInput loadVideoInput = new LoadVideoInput();
                 loadVideoInput.setAuthToken(authTokenStr);
                 loadVideoInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                 loadVideoInput.setSection_id(menuList.get(counter).getSectionId());
                 asynLoadVideos = new GetLoadVideosAsync(loadVideoInput, HomeFragment.this, context);
                 asynLoadVideos.executeOnExecutor(threadPoolExecutor);
             }else {
                 footerView.setVisibility(View.GONE);
             }
         }
        return;

    }

    public void loadUI() {
        //ui_completed = ui_completed + 1;

        LogUtil.showLog("MUVI1", "videoWidth =" + videoWidth + "videoHeight=" + videoHeight);

        if (videoWidth > videoHeight) {

            Util.image_orentiation.add(Constant.IMAGE_LANDSCAPE_CONST);

        } else {
            Util.image_orentiation.add(Constant.IMAGE_PORTAIT_CONST);
        }


        if (getView() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }

        if (firstTime == false) {

            if (mProgressBarHandler != null) {
                mProgressBarHandler.hide();
                mProgressBarHandler = null;
            }
            firstTime = true;

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else { // it works first time
                adapter = new RecyclerViewDataAdapter(context, allSampleData, url_maps, firstTime, MainActivity.vertical);
                //   adapter = new AdapterClass(context,list);
                my_recycler_view.setAdapter(adapter);
            }

        } else {
            if (mProgressBarHandler != null) {
                mProgressBarHandler.hide();
                mProgressBarHandler = null;
            }


            if (counter >= 0 && counter >= menuList.size() - 1) {
                footerView.setVisibility(View.GONE);
            }

            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = my_recycler_view.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
            if (mBundleRecyclerViewState != null) {
                my_recycler_view.getLayoutManager().onRestoreInstanceState(listState);
            }
        }


        if (counter >= 0 && counter < menuList.size() - 1) {
            counter = counter + 1;
            if (NetworkStatus.getInstance().isConnected(getActivity())) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            footerView.setVisibility(View.VISIBLE);

                        }
                    });
                }

                // default data
                LoadVideoInput loadVideoInput = new LoadVideoInput();
                loadVideoInput.setAuthToken(authTokenStr);
                loadVideoInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                loadVideoInput.setSection_id(menuList.get(counter).getSectionId());
                asynLoadVideos = new GetLoadVideosAsync(loadVideoInput, HomeFragment.this, context);
                asynLoadVideos.executeOnExecutor(threadPoolExecutor);

            } else {
                noInternetLayout.setVisibility(View.VISIBLE);
            }
        }


    }


    /*private class AsynLOADUI extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        protected void onPostExecute(Void result) {
            //ui_completed = ui_completed + 1;

            LogUtil.showLog("MUVI1", "videoWidth =" + videoWidth +"videoHeight="+ videoHeight);

            if (videoWidth > videoHeight) {

                Util.image_orentiation.add(Constant.IMAGE_LANDSCAPE_CONST);

            } else {
                Util.image_orentiation.add(Constant.IMAGE_PORTAIT_CONST);
            }





            if (getView() != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

            if (firstTime == false) {

                if (mProgressBarHandler != null) {
                    mProgressBarHandler.hide();
                    mProgressBarHandler = null;
                }
                firstTime = true;

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                } else { // it works first time
                    adapter = new RecyclerViewDataAdapter(context, allSampleData, url_maps, firstTime, MainActivity.vertical);
                    //   adapter = new AdapterClass(context,list);
                    my_recycler_view.setAdapter(adapter);
                }

            } else {
                if (mProgressBarHandler != null) {
                    mProgressBarHandler.hide();
                    mProgressBarHandler = null;
                }


                if (counter >= 0 && counter >= menuList.size() - 1) {
                    footerView.setVisibility(View.GONE);
                }

                mBundleRecyclerViewState = new Bundle();
                Parcelable listState = my_recycler_view.getLayoutManager().onSaveInstanceState();
                mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
                if (mBundleRecyclerViewState != null) {
                    my_recycler_view.getLayoutManager().onRestoreInstanceState(listState);
                }
            }




            if (counter >= 0 && counter < menuList.size() - 1) {
                counter = counter + 1;
                if (NetworkStatus.getInstance().isConnected(getActivity())) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                footerView.setVisibility(View.VISIBLE);

                            }
                        });
                    }

                    // default data
                    LoadVideoInput loadVideoInput = new LoadVideoInput();
                    loadVideoInput.setAuthToken(authTokenStr);
                    loadVideoInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    loadVideoInput.setSection_id(menuList.get(counter).getSectionId());
                    asynLoadVideos = new GetLoadVideosAsync(loadVideoInput, HomeFragment.this, context);
                    asynLoadVideos.executeOnExecutor(threadPoolExecutor);

                } else {
                    noInternetLayout.setVisibility(View.VISIBLE);
                }
            }


        }

        @Override
        protected void onPreExecute() {

        }

    }*/


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save RecyclerView state
        if (my_recycler_view != null) {
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = mLayoutManager.onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

        }
        super.onSaveInstanceState(outState);

       /* if (my_recycler_view!=null && my_recycler_view.getLayoutManager().onSaveInstanceState()!=nu) {
            Parcelable listState = my_recycler_view.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        }*/
    }

    @Override
    public void onHomePagePreExecuteStarted() {
        mProgressBarHandler = new ProgressBarHandler(getActivity());
        mProgressBarHandler.show();
    }

    @Override
    public void onHomePagePostExecuteCompleted(AppHomePageOutput appHomePageOutput, int status, String message) {

        if (mProgressBarHandler != null) {
            mProgressBarHandler.hide();
            mProgressBarHandler = null;
        }
        if (appHomePageOutput != null) {

            if (status == 200) {

                if (singleItem != null && singleItem.size() > 0) {
                    singleItem.clear();
                }

                if (allSampleData != null && allSampleData.size() > 0) {
                    allSampleData.clear();
                }

                if (appHomePageOutput.getHomePageBannerModels() != null) {
                    for (HomePageBannerModel model : appHomePageOutput.getHomePageBannerModels()) {
                        if (model != null) {
                            if (model.getImage_path() != null) {
                                url_maps.add(model.getImage_path());
                            }
                        }
                    }
                }
                if (appHomePageOutput.getHomePageSectionModel() != null) {
                    for (HomePageSectionModel section : appHomePageOutput.getHomePageSectionModel()) {
                        if (section != null) {
                            if (section.getTitle() != null || section.getSection_id() != null || section.getStudio_id() != null || section.getLanguage_id() != null) {
                                menuList.add(new GetMenuItem(section.getTitle(), section.getSection_id(), section.getStudio_id(), section.getLanguage_id()));

                            }
                        }
                    }
                }


                if (NetworkStatus.getInstance().isConnected(getActivity())) {

                    my_recycler_view.setLayoutManager(mLayoutManager);
                    adapter = new RecyclerViewDataAdapter(context, allSampleData, url_maps, firstTime, MainActivity.vertical);
                    my_recycler_view.setAdapter(adapter);
                    my_recycler_view.setVisibility(View.VISIBLE);

                    LoadVideoInput loadVideoInput = new LoadVideoInput();
                    loadVideoInput.setAuthToken(authTokenStr);
                    loadVideoInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    loadVideoInput.setSection_id(menuList.get(counter).getSectionId());
                    asynLoadVideos = new GetLoadVideosAsync(loadVideoInput, HomeFragment.this, context);
                    asynLoadVideos.executeOnExecutor(threadPoolExecutor);
                    // default data
                    /*asynLoadVideos = new AsynLoadVideos();
                    asynLoadVideos.executeOnExecutor(threadPoolExecutor,menuList.get(counter).getSectionId());*/

                } else {
                    noInternetLayout.setVisibility(View.VISIBLE);
                }

            } else {
//            url_maps.add("https://d2gx0xinochgze.cloudfront.net/public/no-image-a.png");

                for (HomePageBannerModel model : appHomePageOutput.getHomePageBannerModels()) {
                    url_maps.add(model.getImage_path());
                }

                if (firstTime == false) {
                    firstTime = true;

                    if (((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
                        for (int j = 0; j < url_maps.size(); j++) {
                            DefaultSliderView textSliderView = new DefaultSliderView(context);
                            textSliderView
                                    .description("")
                                    .image(url_maps.get(j))
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            // .setOnSliderClickListener(this);
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", "");

                            mDemoSlider.addSlider(textSliderView);
                        }
                    } else {
                        for (int j = 0; j < url_maps.size(); j++) {
                            DefaultSliderView textSliderView = new DefaultSliderView(context);
                            textSliderView
                                    .description("")
                                    .image(url_maps.get(j))
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            // .setOnSliderClickListener(this);
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", "");

                            mDemoSlider.addSlider(textSliderView);
                        }
                    }
                }
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider.setDuration(10000);
                //   mDemoSlider.addOnPageChangeListener(this);
                mDemoSlider.getPagerIndicator().setVisibility(View.INVISIBLE);

                sliderRelativeLayout.setVisibility(View.VISIBLE);

            }
        }
        return;
    }

    public void myOnKeyDown() {
        //do whatever you want here
        if (asynLoadMenuItems != null) {
            asynLoadMenuItems.cancel(true);
        }

        if (asynLoadVideos != null) {
            asynLoadVideos.cancel(true);
        }
       /* ActivityCompat.finishAffinity(getActivity());
        getActivity().finish();
        System.exit(0);*/

    }


    /***************chromecast**********************/

    @Override
    public void onResume() {
        super.onResume();

        if (mProgressBarHandler != null) {
            mProgressBarHandler.hide();
            mProgressBarHandler = null;
        }
     /*   *//***************chromecast**********************//*
        mCastContext.addCastStateListener(mCastStateListener);
        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(context).getSessionManager()
                    .getCurrentCastSession();
        }
        *//***************chromecast**********************//*
*/
        getActivity().invalidateOptionsMenu();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.media_route_menu_item:
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;
        private ProgressBarHandler phandler;

        protected Void doInBackground(String... urls) {
            try {


                LogUtil.showLog("MUVI1", "image url==============" + videoHeight);

                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                videoHeight = bmp.getHeight();
                videoWidth = bmp.getWidth();


                LogUtil.showLog("MUVI1", "videoHeight==============" + videoHeight);
                LogUtil.showLog("MUVI1", "videoWidth==============" + videoWidth);

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

            LogUtil.showLog("MUVI1", "==HHH");
//            loadui = new AsynLOADUI();
//            loadui.executeOnExecutor(threadPoolExecutor);

            loadUI();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  phandler = new ProgressBarHandler(getActivity());
            phandler.show();*/

        }
    }
}





