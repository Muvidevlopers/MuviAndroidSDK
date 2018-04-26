package com.home.vod.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.home.apisdk.apiController.FcmNotificationlistsAsynTask;
import com.home.apisdk.apiController.FcmNotificationreadAsynTask;
import com.home.apisdk.apiModel.FcmNotificationlistsInputModel;
import com.home.apisdk.apiModel.FcmNotificationlistsOutputModel;
import com.home.apisdk.apiModel.FcmNotificationreadInputModel;
import com.home.apisdk.apiModel.FcmNotificationreadOutputModel;
import com.home.vod.R;
import com.home.vod.adapter.NotificatonAdapter;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.AppThreadPoolExecuter;
import com.home.vod.util.ProgressBarHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_NOTIFICATION_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_NOTIFICATION;
import static com.home.vod.preferences.LanguagePreference.NOTIFICATION_TITLE;
import static com.home.vod.preferences.LanguagePreference.NO_NOTIFICATION;
import static com.home.vod.util.Constant.authTokenStr;

public class Notification extends AppCompatActivity implements FcmNotificationlistsAsynTask.GetFcmNotificationListListener,FcmNotificationreadAsynTask.FcmNotificationreadListener {


    private RecyclerView obj;
    //DBHelper mydb;
    Toolbar mActionBarToolbar;
    JSONObject jsonObject;
    ArrayList<FcmNotificationlistsOutputModel> msg;
    TextView nonotificationdata;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    private Executor threadPoolExecutor;
    PreferenceManager preferenceManager;
    LanguagePreference languagePreference;
    NotificatonAdapter notificationAdapter;

    public static ProgressBarHandler internetSpeedDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        threadPoolExecutor = new AppThreadPoolExecuter().getThreadPoolExecutor();
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(this);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(NOTIFICATION_TITLE,DEFAULT_NOTIFICATION_TITLE));
        mActionBarToolbar.setTitleTextColor(Color.WHITE);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        obj = (RecyclerView)findViewById(R.id.listView1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        obj.setLayoutManager(mLayoutManager);
        nonotificationdata= (TextView) findViewById(R.id.no_notification);
        nonotificationdata.setText(languagePreference.getTextofLanguage(NO_NOTIFICATION,DEFAULT_NO_NOTIFICATION));

        msg=new ArrayList<FcmNotificationlistsOutputModel>();

        FcmNotificationlistsInputModel fcmNotificationlistsInputModel = new FcmNotificationlistsInputModel();
        fcmNotificationlistsInputModel.setAuthToken(authTokenStr);
        fcmNotificationlistsInputModel.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        FcmNotificationlistsAsynTask fcmNotificationlistsAsynTask = new FcmNotificationlistsAsynTask(fcmNotificationlistsInputModel,this,this);
        fcmNotificationlistsAsynTask.executeOnExecutor(threadPoolExecutor);

    }





    @Override
    public void onFcmNotificationreadPreExecuteStarted() {

        internetSpeedDialog = new ProgressBarHandler(Notification.this);
        internetSpeedDialog.show();

    }

    @Override
    public void onFcmNotificationreadPostExecuteCompleted(FcmNotificationreadOutputModel fcmNotificationreadOutputModel, String responseMsg) {


        if (internetSpeedDialog != null && internetSpeedDialog.isShowing()) {
            internetSpeedDialog.hide();
            internetSpeedDialog = null;

        }


        if (responseMsg.equals("Success")) {

            preferenceManager.setNOTI_COUNT(0);

        }


//        sideMenuHandler.sendBroadCast();

    }

    @Override
    public void onGetFcmNotificationListPreExecuteStarted() {
        internetSpeedDialog = new ProgressBarHandler(Notification.this);
        internetSpeedDialog.show();
    }

    @Override
    public void onGetFcmNotificationListPostExecuteCompleted(ArrayList<FcmNotificationlistsOutputModel> fcmNotificationlistsOutputArray, int status, String message) {
        if (internetSpeedDialog != null && internetSpeedDialog.isShowing()) {
            internetSpeedDialog.hide();
            internetSpeedDialog = null;

        }
        Log.v("pratikNoti","fcmNotificationlistsOutputArray.size()1===="+fcmNotificationlistsOutputArray.size());


        if (fcmNotificationlistsOutputArray != null && fcmNotificationlistsOutputArray.size() > 0) {
            Log.v("pratikNoti","fcmNotificationlistsOutputArray.size()===="+fcmNotificationlistsOutputArray.size());
            Collections.reverse(fcmNotificationlistsOutputArray);
            notificationAdapter = new NotificatonAdapter(Notification.this,fcmNotificationlistsOutputArray);
            obj.setAdapter(notificationAdapter);

            FcmNotificationreadInputModel fcmNotificationreadInputModel = new FcmNotificationreadInputModel();
            fcmNotificationreadInputModel.setAuthToken(authTokenStr);
            fcmNotificationreadInputModel.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            FcmNotificationreadAsynTask fcmNotificationreadAsynTask = new FcmNotificationreadAsynTask(fcmNotificationreadInputModel,this,this);
            fcmNotificationreadAsynTask.executeOnExecutor(threadPoolExecutor);
            nonotificationdata.setVisibility(View.GONE);
        }

        else {

            nonotificationdata.setVisibility(View.VISIBLE);

        }

    }
}
