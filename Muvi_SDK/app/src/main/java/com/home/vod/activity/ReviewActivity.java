package com.home.vod.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.home.apisdk.apiController.AddContentRatingAsynTask;
import com.home.apisdk.apiController.ViewContentRatingAsynTask;
import com.home.apisdk.apiModel.AddContentRatingInputModel;
import com.home.apisdk.apiModel.AddContentRatingOutputModel;
import com.home.apisdk.apiModel.ViewContentRatingInputModel;
import com.home.apisdk.apiModel.ViewContentRatingOutputModel;

import com.home.vod.R;
import com.home.vod.adapter.ReviewsAdapter;
import com.home.vod.model.ReviewsItem;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.BTN_POST_REVIEW;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CLICK_HERE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_POST_REVIEW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CLICK_HERE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_REVIEW_HERE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FAILURE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NEED_LOGIN_TO_REVIEW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_REVIEWS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SLOW_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SUBMIT_YOUR_RATING_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TO_LOGIN;
import static com.home.vod.preferences.LanguagePreference.ENTER_REVIEW_HERE;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.FAILURE;
import static com.home.vod.preferences.LanguagePreference.NEED_LOGIN_TO_REVIEW;
import static com.home.vod.preferences.LanguagePreference.REVIEWS;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SUBMIT_YOUR_RATING_TITLE;
import static com.home.vod.preferences.LanguagePreference.TO_LOGIN;
import static com.home.vod.util.Constant.authTokenStr;



public class ReviewActivity extends AppCompatActivity implements
        ViewContentRatingAsynTask.ViewContentRatingListener, AddContentRatingAsynTask.AddContentRatingListener{

    Toolbar mActionBarToolbar;
    ProgressBarHandler pDialog;
    ArrayList<ReviewsItem> reviewsItem = new ArrayList<ReviewsItem>();
    ReviewsAdapter reviewsAdapter;
    GridView reviewsGridView;



    /* RelativeLayout noInternetLayout;
     RelativeLayout noDataLayout;
     TextView noDataTextView;
     TextView noInternetTextView;*/
    int isLogin = 0;
    PreferenceManager preferenceManager;
    LanguagePreference languagePreference;
    //  LinearLayout primary_layout;
    boolean isNetwork;
    int showRating = 0;
    String movie_id;
    TextView submitTitleTextView;
    EditText submitReviewTextView;
    RelativeLayout submitRatingLayout;
    Button submitButton;
    RatingBar addRatingBar;
    TextView clickHereToLogin;
    String reviewMessage = "";
    String ratingStr = "";
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        isLogin = preferenceManager.getLoginFeatureFromPref();

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(REVIEWS,DEFAULT_REVIEWS));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitRatingLayout = (RelativeLayout)findViewById(R.id.submitRatingLayout);
        clickHereToLogin = (TextView) findViewById(R.id.clickHereToLogin);
        submitTitleTextView = (TextView) findViewById(R.id.sectionTitle);
        submitReviewTextView = (EditText) findViewById(R.id.reviewEditText);
        submitButton = (Button) findViewById(R.id.submitReviewButton);
        addRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        FontUtls.loadFont(ReviewActivity.this, getResources().getString(R.string.light_fonts),submitButton);
        FontUtls.loadFont(ReviewActivity.this, getResources().getString(R.string.light_fonts),submitTitleTextView);
        submitTitleTextView.setText(languagePreference.getTextofLanguage(SUBMIT_YOUR_RATING_TITLE,DEFAULT_SUBMIT_YOUR_RATING_TITLE));
        submitButton.setText(languagePreference.getTextofLanguage(BTN_POST_REVIEW,DEFAULT_BTN_POST_REVIEW));

        submitReviewTextView.setHint(languagePreference.getTextofLanguage(ENTER_REVIEW_HERE,DEFAULT_ENTER_REVIEW_HERE));
        String clickHereStr = languagePreference.getTextofLanguage(NEED_LOGIN_TO_REVIEW,DEFAULT_NEED_LOGIN_TO_REVIEW) + " " + languagePreference.getTextofLanguage(CLICK_HERE,DEFAULT_CLICK_HERE) + " "+ languagePreference.getTextofLanguage(TO_LOGIN,DEFAULT_TO_LOGIN);

        /*******enter key of keyboard *************/

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (source.charAt(i) == '\n') {
                        return " ";
                    }
                }
                return null;
            }
        };

        submitReviewTextView.setFilters(new InputFilter[]{filter});

        SpannableString mySpannableString = new SpannableString(clickHereStr);
        mySpannableString.setSpan(new UnderlineSpan(), 0, mySpannableString.length(), 0);
        clickHereToLogin.setText(mySpannableString);
        clickHereToLogin.setVisibility(View.GONE);
        clickHereToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent registerActivity = new Intent(ReviewActivity.this, LoginActivity.class);
                runOnUiThread(new Runnable() {
                    public void run() {
                        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        registerActivity.putExtra("from", this.getClass().getName());
                        startActivity(registerActivity);

                    }
                });

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewMessage = submitReviewTextView.getText().toString().trim();
                ratingStr = Float.toString(addRatingBar.getRating());


                AddContentRatingInputModel addContentRatingInputModel = new AddContentRatingInputModel();
                addContentRatingInputModel.setUser_id(preferenceManager.getUseridFromPref());
                addContentRatingInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,DEFAULT_SELECTED_LANGUAGE_CODE));
                addContentRatingInputModel.setContent_id(getIntent().getStringExtra("muviId"));
                addContentRatingInputModel.setAuthToken(authTokenStr.trim());
                addContentRatingInputModel.setRating(ratingStr);
                addContentRatingInputModel.setReview(reviewMessage);

                AddContentRatingAsynTask addContentRatingAsynTask = new AddContentRatingAsynTask(addContentRatingInputModel, ReviewActivity.this, ReviewActivity.this);
                addContentRatingAsynTask.executeOnExecutor(threadPoolExecutor);


            }
        });


        //  primary_layout = (LinearLayout)findViewById(R.id.primary_layout);
        reviewsGridView = (GridView) findViewById(R.id.reviewsList);
        isNetwork = NetworkStatus.getInstance().isConnected(this);
        reviewsGridView.setNumColumns(1);




       /* if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            reviewsGridView.setNumColumns(2);

        }else {
            //"Mobile";
            reviewsGridView.setNumColumns(1);
        }*/

        // GetReviewDetails();




    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if(isLogin == 1) {
            if (pref != null) {
                LogUtil.showLog("MUVI","FHFH");
                String loggedInStr = pref.getString("PREFS_LOGGEDIN_KEY", null);
                if (loggedInStr == null) {
                    LogUtil.showLog("MUVI","loggedInStr");

                    clickHereToLogin.setVisibility(View.VISIBLE);
                    submitRatingLayout.setVisibility(View.GONE);
                }else{
                    LogUtil.showLog("MUVI","loggedInStr1");

                    clickHereToLogin.setVisibility(View.GONE);
                    submitRatingLayout.setVisibility(View.VISIBLE);
                }
            }else{
                LogUtil.showLog("MUVI","loggedInStr2");

                clickHereToLogin.setVisibility(View.VISIBLE);
                submitRatingLayout.setVisibility(View.GONE);
            }
        }else{
            LogUtil.showLog("MUVI","loggedInStr3");

            clickHereToLogin.setVisibility(View.GONE);
            submitRatingLayout.setVisibility(View.GONE);

        }*/
        GetReviewDetails();
    }

    public void GetReviewDetails()
    {
        if(isNetwork) {
            String muviid = getIntent().getStringExtra("muviId");
            ViewContentRatingInputModel viewContentRatingInputModel = new ViewContentRatingInputModel();
            viewContentRatingInputModel.setAuthToken(authTokenStr);
            viewContentRatingInputModel.setContent_id(muviid);
            viewContentRatingInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            viewContentRatingInputModel.setUser_id(preferenceManager.getUseridFromPref());

            ViewContentRatingAsynTask viewContentRatingAsynTask = new ViewContentRatingAsynTask(viewContentRatingInputModel, ReviewActivity.this, ReviewActivity.this);
            viewContentRatingAsynTask.executeOnExecutor(threadPoolExecutor);
        }else{
            Util.showToast(ReviewActivity.this, languagePreference.getTextofLanguage(ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING));
        }




    }

    @Override
    public void onViewContentRatingPreExecuteStarted() {
        pDialog = new ProgressBarHandler(ReviewActivity.this);
        pDialog.show();
    }

    @Override
    public void onViewContentRatingPostExecuteCompleted(ViewContentRatingOutputModel viewContentRatingOutputModel, int status, String message) {

        try{
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }

        }
        catch(IllegalArgumentException ex) {
        }

        if (status>0){
            if (status==200){

               /* for (int a = 0; a < viewContentRatingOutputModel.getRatingArray().size(); a++) {

                    if(viewContentRatingOutputModel.getRatingArray().get(a).getStatus().equals("1")){
                        ReviewsItem reviewItem = new ReviewsItem(viewContentRatingOutputModel.getRatingArray().get(a).getReview()
                                , viewContentRatingOutputModel.getRatingArray().get(a).getDisplay_name(),
                                viewContentRatingOutputModel.getRatingArray().get(a).getRating());
                        reviewsItem.add(reviewItem);
                    }
                }*/
                //LogUtil.showLog("MUVI", "Review activity activity_login featrure ::"+preferenceManager.getLoginFeatureFromPref());
                    if (preferenceManager.getLoginFeatureFromPref() == 1) {

                        String loggedInStr = preferenceManager.getLoginStatusFromPref();
                        if (loggedInStr == null) {
                            LogUtil.showLog("MUVI","loggedInStr");

                            clickHereToLogin.setVisibility(View.VISIBLE);
                            submitRatingLayout.setVisibility(View.GONE);
                        }else{
                            if (viewContentRatingOutputModel.getShowrating() == 0){
                                submitRatingLayout.setVisibility(View.GONE);
                            }else{
                                submitRatingLayout.setVisibility(View.VISIBLE);

                            }
                            clickHereToLogin.setVisibility(View.GONE);
                            // submitRatingLayout.setVisibility(View.VISIBLE);
                        }
                    }else{
                        LogUtil.showLog("MUVI","loggedInStr2");

                        clickHereToLogin.setVisibility(View.VISIBLE);
                        submitRatingLayout.setVisibility(View.GONE);
                    }
                }
                reviewsAdapter = new ReviewsAdapter(ReviewActivity.this,viewContentRatingOutputModel.getRatingArray());
                reviewsGridView.setAdapter(reviewsAdapter);

        }else {

        }
        /*if(preferenceManager.getLoginFeatureFromPref() == 1) {
            if (preferenceManager.getLoginFeatureFromPref() == 1) {

                String loggedInStr = preferenceManager.getUseridFromPref();
                if (loggedInStr == null) {
                    LogUtil.showLog("MUVI","loggedInStr");

                    clickHereToLogin.setVisibility(View.VISIBLE);
                    submitRatingLayout.setVisibility(View.GONE);
                }else{


                    if (viewContentRatingOutputModel.getShowrating() == 0){
                        submitRatingLayout.setVisibility(View.GONE);
                    }else{
                        submitRatingLayout.setVisibility(View.VISIBLE);

                    }
                    clickHereToLogin.setVisibility(View.GONE);
                    // submitRatingLayout.setVisibility(View.VISIBLE);
                }
            }else{
                LogUtil.showLog("MUVI","loggedInStr2");

                clickHereToLogin.setVisibility(View.VISIBLE);
                submitRatingLayout.setVisibility(View.GONE);
            }
        }else{
            LogUtil.showLog("MUVI","loggedInStr3");

            clickHereToLogin.setVisibility(View.GONE);
            submitRatingLayout.setVisibility(View.GONE);

        }

        ;
        reviewsAdapter = new ReviewsAdapter(ReviewActivity.this,viewContentRatingOutputModel.getRatingArray());
        reviewsGridView.setAdapter(reviewsAdapter);*/

    }
    //Asyntask for getDetails of the csat and crew members.

  /*  private class AsynGetReviewDetails extends AsyncTask<Void, Void, Void> {
        ProgressBarHandler pDialog;
        String responseStr = "";
        int status;
        String msg;
        int reviewDisabled = 1;

        @Override
        protected Void doInBackground(Void... params) {

            try {


                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Util.rootUrl().trim()+Util.ViewContentRating.trim());
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("content_id",getIntent().getStringExtra("muviId"));
                if (pref != null) {
                    String loggedInStr = pref.getString("PREFS_LOGGEDIN_KEY", null);
                    if (loggedInStr == null) {

                    }else{
                        httppost.addHeader("user_id",pref.getString("PREFS_LOGGEDIN_ID_KEY", null));

                    }
                }
                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());
                    LogUtil.showLog("MUVI","RESPO"+responseStr);
                    LogUtil.showLog("MUVI","RESPO"+getIntent().getStringExtra("muviId"));


                } catch (Exception e){

                }

                JSONObject myJson =null;
                JSONArray jsonArray =null;
                if(responseStr!=null){
                    myJson = new JSONObject(responseStr);
                    status = Integer.parseInt(myJson.optString("code"));
                    msg = myJson.optString("msg");
                    if ((myJson.has("showrating")) && myJson.optString("showrating").trim() != null && !myJson.optString("showrating").trim().isEmpty() && !myJson.optString("showrating").trim().equals("null") && !myJson.optString("showrating").trim().matches("")) {
                        showRating = Integer.parseInt(myJson.optString("showrating"));
                        LogUtil.showLog("MUVI","HFFH"+showRating);
                    }

                }

                if (status == 200) {
                    jsonArray = myJson.getJSONArray("rating");
                    if (reviewsItem!=null && reviewsItem.size() > 0){
                        reviewsItem.clear();
                    }
                    for (int i=0 ;i<jsonArray.length();i++)
                    {
                        String userName = "";
                        String rating = "0";
                        String review = "";
                        if ((jsonArray.getJSONObject(i).has("display_name")) && jsonArray.getJSONObject(i).optString("display_name").trim() != null && !jsonArray.getJSONObject(i).optString("display_name").trim().isEmpty() && !jsonArray.getJSONObject(i).optString("display_name").trim().equals("null") && !jsonArray.getJSONObject(i).optString("display_name").trim().matches("")) {
                            userName =jsonArray.getJSONObject(i).optString("display_name");

                        }
                        if ((jsonArray.getJSONObject(i).has("rating")) && jsonArray.getJSONObject(i).optString("rating").trim() != null && !jsonArray.getJSONObject(i).optString("rating").trim().isEmpty() && !jsonArray.getJSONObject(i).optString("rating").trim().equals("null") && !jsonArray.getJSONObject(i).optString("rating").trim().matches("")) {
                            rating =jsonArray.getJSONObject(i).optString("rating");

                        }
                        if ((jsonArray.getJSONObject(i).has("review")) && jsonArray.getJSONObject(i).optString("review").trim() != null && !jsonArray.getJSONObject(i).optString("display_name").trim().isEmpty() && !jsonArray.getJSONObject(i).optString("review").trim().equals("null") && !jsonArray.getJSONObject(i).optString("review").trim().matches("")) {
                            review =jsonArray.getJSONObject(i).optString("review");

                        }

                        if ((jsonArray.getJSONObject(i).has("status")) && jsonArray.getJSONObject(i).optString("status").trim() != null && !jsonArray.getJSONObject(i).optString("status").trim().isEmpty() && !jsonArray.getJSONObject(i).optString("status").trim().equals("null") && !jsonArray.getJSONObject(i).optString("status").trim().matches("")) {
                            reviewDisabled = Integer.parseInt(jsonArray.getJSONObject(i).optString("status"));

                        }
                        if (reviewDisabled == 1) {
                            ReviewsItem reviewItem = new ReviewsItem(review, userName, rating);
                            reviewsItem.add(reviewItem);
                        }

                        ReviewsItem reviewItem = new ReviewsItem(review,userName,rating);
                        reviewsItem.add(reviewItem);
                    }
                    if ((myJson.has("showrating")) && myJson.optString("showrating").trim() != null && !myJson.optString("showrating").trim().isEmpty() && !myJson.optString("showrating").trim().equals("null") && !myJson.optString("showrating").trim().matches("")) {
                        showRating = Integer.parseInt(myJson.optString("showrating"));
                        LogUtil.showLog("MUVI","HFFH"+showRating);
                    }

                }else{
                    responseStr = "0";
                   *//* if(status == 448)
                    {

                        // show dialog
                        responseStr = "1";
                    }
                    else
                    {
                        responseStr = "0";
                    }*//*
                }

            } catch (final JSONException e1) {
                responseStr = "0";
            }
            catch (Exception e)
            {
                responseStr = "0";

            }
            return null;

        }

        protected void onPostExecute(Void result) {

            try{
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


                    }

                });
                responseStr = "0";
            }
            if(responseStr == null) {
                responseStr = "0";
            }

            if((responseStr.trim().equals("0"))){

            }else{
                if(isLogin == 1) {
                    if (pref != null) {
                        LogUtil.showLog("MUVI","FHFH");
                        String loggedInStr = pref.getString("PREFS_LOGGEDIN_KEY", null);
                        if (loggedInStr == null) {
                            LogUtil.showLog("MUVI","loggedInStr");

                            clickHereToLogin.setVisibility(View.VISIBLE);
                            submitRatingLayout.setVisibility(View.GONE);
                        }else{
                            if (showRating == 0){
                                submitRatingLayout.setVisibility(View.GONE);
                            }else{
                                submitRatingLayout.setVisibility(View.VISIBLE);

                            }
                            clickHereToLogin.setVisibility(View.GONE);
                            // submitRatingLayout.setVisibility(View.VISIBLE);
                        }
                    }else{
                        LogUtil.showLog("MUVI","loggedInStr2");

                        clickHereToLogin.setVisibility(View.VISIBLE);
                        submitRatingLayout.setVisibility(View.GONE);
                    }
                }else{
                    LogUtil.showLog("MUVI","loggedInStr3");

                    clickHereToLogin.setVisibility(View.GONE);
                    submitRatingLayout.setVisibility(View.GONE);

                }

                reviewsAdapter = new ReviewsAdapter(ReviewActivity.this,reviewsItem);
                reviewsGridView.setAdapter(reviewsAdapter);

            }
        }

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressBarHandler(ReviewActivity.this);
            pDialog.show();


        }
    }*/

    public void ShowDialog(String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ReviewActivity.this);
        dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE,DEFAULT_FAILURE));
        dlgAlert.setMessage(msg);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK,DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK,DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void onAddContentRatingPreExecuteStarted() {
        pDialog = new ProgressBarHandler(ReviewActivity.this);
        pDialog.show();
    }

    @Override
    public void onAddContentRatingPostExecuteCompleted(AddContentRatingOutputModel addContentRatingOutputModel, int status, String message) {


        if((status != 200)){
            ShowDialog(message);
        }else{
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();

        }


    }

   /* private class AsynAddReviewDetails extends AsyncTask<Void, Void, Void> {
        ProgressBarHandler pDialog;
        String responseStr = "";
        int status;
        String msg;
        @Override
        protected Void doInBackground(Void... params) {

            try {


                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Util.rootUrl().trim()+Util.AddContentRating.trim());
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("content_id",getIntent().getStringExtra("muviId"));
                httppost.addHeader("lang_code",languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
                httppost.addHeader("user_id",pref.getString("PREFS_LOGGEDIN_ID_KEY", null));

                httppost.addHeader("rating",ratingStr);
                httppost.addHeader("review",reviewMessage);


                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());
                    LogUtil.showLog("MUVI","RESPO"+responseStr);
                    LogUtil.showLog("MUVI","RESPO"+getIntent().getStringExtra("muviId"));


                } catch (Exception e){
                    responseStr = "0";

                }

                JSONObject myJson =null;
                if(responseStr!=null){
                    myJson = new JSONObject(responseStr);
                    status = Integer.parseInt(myJson.optString("code"));
                    msg = myJson.optString("msg");


                }

                if (status == 200) {


                }else{
                    responseStr = "0";
                }

            } catch (final JSONException e1) {
                responseStr = "0";
            }
            catch (Exception e)
            {
                responseStr = "0";

            }
            return null;

        }

        protected void onPostExecute(Void result) {

            try{
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
                        responseStr = "0";


                    }

                });
                responseStr = "0";
            }
            if(responseStr == null) {
                responseStr = "0";
            }

            if((responseStr.trim().equals("0"))){
                ShowDialog(msg);
            }else{
                Intent intent=new Intent();
                setResult(30060,intent);
                finish();
                overridePendingTransition(0,0);


            }
        }

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressBarHandler(ReviewActivity.this);
            pDialog.show();


        }
    }
*/



}
