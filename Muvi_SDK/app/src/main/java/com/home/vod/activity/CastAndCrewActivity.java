package com.home.vod.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.home.apisdk.apiController.GetCelibrityAsyntask;
import com.home.apisdk.apiModel.CelibrityInputModel;
import com.home.apisdk.apiModel.CelibrityOutputModel;
import com.home.vod.CastAndCrewDetailsIntentHandler;
import com.home.vod.R;
import com.home.vod.adapter.CastCrewAdapter;
import com.home.vod.model.GetCastCrewItem;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
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

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CAST_CREW_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FAILURE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.FAILURE;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.util.Constant.authTokenStr;

public class CastAndCrewActivity extends AppCompatActivity implements GetCelibrityAsyntask.GetCelibrityListener {


    Toolbar mActionBarToolbar;
   //TextView castCrewTitleTextView;
    RecyclerView castCrewListRecyclerView;

    ArrayList<GetCastCrewItem> castCrewItems = new ArrayList<GetCastCrewItem>();
    CastCrewAdapter castCrewAdapter;
    GridView cast_crew_crid;

    RelativeLayout noInternetLayout;
    RelativeLayout noDataLayout;
    TextView noDataTextView;
    ProgressBarHandler pDialog;
    TextView noInternetTextView;

    LinearLayout primary_layout;
    boolean isNetwork;

    String movie_id, movie_uniq_id;
    LanguagePreference languagePreference;
    CastAndCrewDetailsIntentHandler castAndCrewDetailsIntentHandler;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_and_crew);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(CAST_CREW_BUTTON_TITLE,DEFAULT_CAST_CREW_BUTTON_TITLE));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noInternetLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(NO_CONTENT, DEFAULT_NO_CONTENT));
        castAndCrewDetailsIntentHandler = new CastAndCrewDetailsIntentHandler(this);

        primary_layout = (LinearLayout) findViewById(R.id.primary_layout);
       // castCrewTitleTextView = (TextView) findViewById(R.id.castCrewTitleTextView);
       // FontUtls.loadFont(CastAndCrewActivity.this, getResources().getString(R.string.regular_fonts), castCrewTitleTextView);
       // castCrewTitleTextView.setText(languagePreference.getTextofLanguage(CAST_CREW_BUTTON_TITLE, DEFAULT_CAST_CREW_BUTTON_TITLE));
        cast_crew_crid = (GridView) findViewById(R.id.cast_crew_crid);
        isNetwork = NetworkStatus.getInstance().isConnected(this);


        if (((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) || ((getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            cast_crew_crid.setNumColumns(2);

        } else {
            //"Mobile";
            cast_crew_crid.setNumColumns(1);
        }
        castCrewAdapter = new CastCrewAdapter(CastAndCrewActivity.this, castCrewItems);
        cast_crew_crid.setAdapter(castCrewAdapter);
        GetCsatCrewDetails();
        cast_crew_crid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetCastCrewItem item = castCrewItems.get(position);
                Intent castCrewIntent = new Intent(CastAndCrewActivity.this, CastCrewDetailsActivity.class);
                castCrewIntent.putExtra("castPermalink", item.getCastPermalink());
                LogUtil.showLog("SUBHA", "PERMALINK_INTENT_KEY" + item.getCastPermalink());

                castCrewIntent.putExtra("castName", item.getCastPermalink());
                castCrewIntent.putExtra("castSummary", item.getCelebritySummary());
                castCrewIntent.putExtra("castImage", item.getCastImage());

                castAndCrewDetailsIntentHandler.castandcrewdetailsIntentShowORHide(castCrewIntent);
            }
        });


    }


    public void GetCsatCrewDetails() {
        noInternetLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        primary_layout.setVisibility(View.VISIBLE);

        CelibrityInputModel celibrityInputModel = new CelibrityInputModel();
        celibrityInputModel.setAuthToken(authTokenStr);
        celibrityInputModel.setMovie_id(getIntent().getStringExtra("cast_movie_id"));
        celibrityInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        GetCelibrityAsyntask asynGetCsatDetails = new GetCelibrityAsyntask(celibrityInputModel, this, this);
        asynGetCsatDetails.executeOnExecutor(threadPoolExecutor);

    }

    @Override
    public void onGetCelibrityPreExecuteStarted() {

        pDialog = new ProgressBarHandler(CastAndCrewActivity.this);
        pDialog.show();

    }

    @Override
    public void onGetCelibrityPostExecuteCompleted(ArrayList<CelibrityOutputModel> celibrityOutputModel, int status, String msg) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }

        } catch (IllegalArgumentException ex) {
        }
        if (status == 200) {
            //castCrewItems = new ArrayList<GetCastCrewItem>();
            for (int i = 0; i < celibrityOutputModel.size(); i++) {
                GetCastCrewItem movie = new GetCastCrewItem(celibrityOutputModel.get(i).getName(), celibrityOutputModel.get(i).getCast_type()
                        , celibrityOutputModel.get(i).getCelebrity_image()
                        , celibrityOutputModel.get(i).getPermalink(), celibrityOutputModel.get(i).getSummary());

                castCrewItems.add(movie);
            }

            noDataLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.GONE);
            primary_layout.setVisibility(View.VISIBLE);
            // Set the grid adapter here.
            castCrewAdapter.notifyDataSetChanged();
        } else if (status == 448) {
            ShowDialog(msg);
        } else if (status == 0) {
            primary_layout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }

    }

    public void ShowDialog(String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(CastAndCrewActivity.this, R.style.MyAlertDialogStyle);
        dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, DEFAULT_FAILURE));
        dlgAlert.setMessage(msg);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        dlgAlert.create().show();
    }

   /* @Override
    public void onBackPressed()
    {
        finish();
//        overridePendingTransition(0, 0);
        super.onBackPressed();
    }*/


}
