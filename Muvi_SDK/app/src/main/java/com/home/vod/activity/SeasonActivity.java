package com.home.vod.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.home.apisdk.apiController.GetContentDetailsAsynTask;
import com.home.apisdk.apiModel.ContentDetailsInput;
import com.home.apisdk.apiModel.ContentDetailsOutput;
import com.home.vod.R;
import com.home.vod.adapter.SeasonAdapter;
import com.home.vod.model.SeasonModel;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.ProgressBarHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SEASON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SEASON;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.util.Constant.PERMALINK_INTENT_KEY;
import static com.home.vod.util.Constant.authTokenStr;

public class SeasonActivity extends AppCompatActivity implements GetContentDetailsAsynTask.GetContentDetailsListener {

    RecyclerView seasonGridView;
    ArrayList<SeasonModel> season;
    SeasonAdapter adapter;
    GetContentDetailsAsynTask asynLoadSeason;
    ProgressBarHandler pDialog;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    LanguagePreference languagePreference;
    String useridStr;
    String permalinkStr;
    Toolbar mActionBarToolbar;
    PreferenceManager preferenceManager;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    int[] imageArr = {R.drawable.man_doing_pushups,
            R.drawable.man_flexing_knees_with_arms_up,
            R.drawable.man_flexing_waist,
            R.drawable.man_flexing_waist_down,
            R.drawable.man_flexing_waist_to_feet,
            R.drawable.man_laying_on_his_back_with_his_knees_on_his_chest,
            R.drawable.man_on_floor_flexing_backwards,
            R.drawable.man_on_his_knees,
            R.drawable.man_on_his_knees_stretching_arms_on_floor,
            R.drawable.man_on_his_knees_stretching_back,
            R.drawable.man_on_his_knees_stretching_side_view,
            R.drawable.man_on_his_knees_with_head_on_floor,
            R.drawable.man_on_squat_position_turning_waist,
            R.drawable.man_on_the_floor_flexing_body,
            R.drawable.man_sitting_on_the_floor_stretching_leg_and_waist,
            R.drawable.man_squatting,
            R.drawable.man_standing_on_his_right_leg_stretching_left_leg_and_right_arm,
            R.drawable.man_stretching_back,
            R.drawable.man_stretching_both_arms_and_legs,
            R.drawable.man_stretching_left_leg_and_bending_waist,
            R.drawable.man_stretching_leg_and_arms,
            R.drawable.man_stretching_legs_and_flexing_body,
            R.drawable.man_stretching_legs_and_waist,
            R.drawable.man_supporting_himself_on_one_arm_and_stretching_right_arm,
            R.drawable.man_up_flexing_to_right,
            R.drawable.woman_bending_waist_to_feet_with_stretching_leg,
            R.drawable.woman_flexing_body,
            R.drawable.woman_flexing_waist_to_feet,
            R.drawable.woman_honding_her_body_with_arms_and_legs
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        //  seasonGridView = (GridView) findViewById(R.id.seasonGridView);

        seasonGridView = (RecyclerView) findViewById(R.id.seasonGridView);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);

        permalinkStr = getIntent().getStringExtra(PERMALINK_INTENT_KEY);
        useridStr = preferenceManager.getUseridFromPref();
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // seasonGridView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        seasonGridView.setItemAnimator(new DefaultItemAnimator());
        if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
            // seasonGridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            seasonGridView.setLayoutManager(mLayoutManager);

        } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
            // seasonGridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            seasonGridView.setLayoutManager(mLayoutManager);


        } else if ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {

            //seasonGridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            seasonGridView.setLayoutManager(mLayoutManager);

        } else {
            //seasonGridView.setNumColumns(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 4);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            seasonGridView.setLayoutManager(mLayoutManager);


        }

        ContentDetailsInput contentDetailsInput = new ContentDetailsInput();

        contentDetailsInput.setAuthToken(authTokenStr);
        if (preferenceManager != null) {
            String countryPref = preferenceManager.getCountryCodeFromPref();
            contentDetailsInput.setCountry(countryPref);
        } else {
            contentDetailsInput.setCountry("IN");
        }
        contentDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        contentDetailsInput.setPermalink(permalinkStr);
        contentDetailsInput.setUser_id(useridStr);
        asynLoadSeason = new GetContentDetailsAsynTask(contentDetailsInput, this, this);
        asynLoadSeason.executeOnExecutor(threadPoolExecutor);

    }

    @Override
    public void onGetContentDetailsPreExecuteStarted() {

        pDialog = new ProgressBarHandler(SeasonActivity.this);
        pDialog.show();

    }

    @Override
    public void onGetContentDetailsPostExecuteCompleted(ContentDetailsOutput contentDetailsOutput, int status, String message) {


        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {

            status = 0;
        }
        if (status == 200) {
            if (season != null && season.size() > 0) {
                season.clear();
            }
            season = new ArrayList<SeasonModel>();

            for (int j = 0; j < contentDetailsOutput.getSeason().length; j++) {
                season.add(new SeasonModel(String.valueOf(contentDetailsOutput.getSeason()[j]), imageArr[j], languagePreference.getTextofLanguage(SEASON, DEFAULT_SEASON) + " " + contentDetailsOutput.getSeason()[j]));
            }
            adapter = new SeasonAdapter(SeasonActivity.this, R.layout.season_card_row, season);
            seasonGridView.setVisibility(View.VISIBLE);
            seasonGridView.setAdapter(adapter);

        } else {
            seasonGridView.setVisibility(View.GONE);
        }
    }

    /*private class AsynLoadSeason extends AsyncTask<Void, Void, Void> {
        ProgressBarHandler pDialog;
        String responseStr;
        int status;


        @Override
        protected Void doInBackground(Void... params) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.detailsUrl.trim());
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("permalink", getIntent().getStringExtra(Util.PERMALINK_INTENT_KEY));
                SharedPreferences countryPref = getSharedPreferences(Util.COUNTRY_PREF, 0); // 0 - for private mode
                if (countryPref != null) {
                    String countryCodeStr = countryPref.getString("countryCode", null);
                    httppost.addHeader("country", countryCodeStr);
                } else {
                    httppost.addHeader("country", "IN");

                }
                httppost.addHeader("lang_code", Util.getTextofLanguage(SeasonActivity.this, Util.SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());


                } catch (org.apache.http.conn.ConnectTimeoutException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SeasonActivity.this, Util.getTextofLanguage(SeasonActivity.this, Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();


                        }

                    });

                } catch (IOException e) {
                    status = 0;

                    e.printStackTrace();
                }

                JSONObject myJson = null;
                if (responseStr != null) {
                    myJson = new JSONObject(responseStr);
                    status = Integer.parseInt(myJson.optString("code"));
                }

                if (status > 0) {

                    if (status == 200) {

                        JSONObject epDetailsJson =myJson.optJSONObject("epDetails");
                        if ((epDetailsJson.has("series_number")) && epDetailsJson.getString("series_number").trim() != null && !epDetailsJson.getString("series_number").trim().isEmpty() && !epDetailsJson.getString("series_number").trim().equals("null") && !epDetailsJson.getString("series_number").trim().matches("") && !epDetailsJson.getString("series_number").trim().matches("0")) {
                            String s[] = epDetailsJson.optString("series_number").split(",");
                            Arrays.sort(s);
                            if (season!=null && season.size() > 0){
                                season.clear();
                            }
                            season=new ArrayList<SeasonModel>();

                            for(int j=0;j<s.length;j++){
                                 .add(new SeasonModel(String.valueOf(s[j]),imageArr[j],Util.getTextofLanguage(SeasonActivity.this, Util.SEASON, Util.DEFAULT_SEASON)+" "+s[j]));
                            }

                        }
                    }


                } else {

                    status = 0;


                }
            } catch (final JSONException e1) {
                status = 0;

                e1.printStackTrace();
            } catch (Exception e) {
                status = 0;

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
            } catch (IllegalArgumentException ex) {

                status = 0;
            }
            if (status == 200 && season !=null && season.size() > 0){
                adapter = new SeasonAdapter(SeasonActivity.this,R.layout.season_card_row,season);
                seasonGridView.setVisibility(View.VISIBLE);
                seasonGridView.setAdapter(adapter);

            }else{
                seasonGridView.setVisibility(View.GONE);
            }


        }

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressBarHandler(SeasonActivity.this);
            pDialog.show();

        }


    }
*/

}