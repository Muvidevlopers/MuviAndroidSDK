package com.home.vod.FCM_Support;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.home.apisdk.apiController.FcmNotificationcountAsynTask;
import com.home.apisdk.apiModel.FcmNotificationcountInputModel;
import com.home.apisdk.apiModel.FcmNotificationcountOutputModel;
import com.home.vod.BuildConfig;
import com.home.vod.R;
import com.home.vod.activity.MainActivity;
import com.home.vod.activity.SplashScreen;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements FcmNotificationcountAsynTask.FcmNotificationcountListener{

    private static final String TAG = "BIBHU2";
    String MESSAGE = "";
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    PreferenceManager preferenceManager;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0){
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        init();
    }

    private void handleNotification(String message) {
        Log.e(TAG, "Notification message : " + message);

        MESSAGE = message;

        Handler handler = new Handler(Looper.getMainLooper());

       /* handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"notification="+MESSAGE,Toast.LENGTH_LONG).show();
            }
        });*/
    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");

            String user_id = data.optString("user_id");
            String message = data.optString("message");
            MESSAGE = message;

            Log.e(TAG, "user_id: =======" + user_id);
            Log.e(TAG, "message: ==========" + message);

          /*  Handler handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"data="+MESSAGE,Toast.LENGTH_LONG).show();
                }
            });*/


            Intent intent1 = new Intent(MyFirebaseMessagingService.this, SplashScreen.class);
            PendingIntent pIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent1, 0);

            NotificationCompat.Builder mBuilder;
            NotificationManager mNotifyManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);

            mBuilder.setContentTitle(user_id)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .addAction(0, "Tap to View Notification", pIntent)
                    .setSmallIcon(R.drawable.logo);


            mNotifyManager.notify(100, mBuilder.build());



            PreferenceManager preferenceManager = PreferenceManager.getPreferenceManager(this);
            String loggedInStr =  preferenceManager.getUseridFromPref();

            if (preferenceManager != null) {
                if(loggedInStr.trim().equals(user_id.trim())){

                    preferenceManager.clearLoginPref();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    public void init() {

        FcmNotificationcountInputModel fcmNotificationcountInputModel = new FcmNotificationcountInputModel();
        fcmNotificationcountInputModel.setAuthToken(BuildConfig.AUTH_TOKEN);
        fcmNotificationcountInputModel.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        FcmNotificationcountAsynTask fcmNotificationcountAsynTask = new FcmNotificationcountAsynTask(fcmNotificationcountInputModel,this,this);
        fcmNotificationcountAsynTask.executeOnExecutor(threadPoolExecutor);

    }

    @Override
    public void onFcmNotificationcountPreExecuteStarted() {

    }

    @Override
    public void onFcmNotificationcountPostExecuteCompleted(FcmNotificationcountOutputModel fcmNotificationcountOutputModel, int count, String sucessMsg) {

        preferenceManager.setNOTI_COUNT(count);
    }

}