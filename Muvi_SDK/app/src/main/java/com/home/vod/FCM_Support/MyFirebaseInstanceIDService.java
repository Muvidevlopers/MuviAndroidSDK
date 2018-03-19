package com.home.vod.FCM_Support;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.home.vod.BuildConfig;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.LogUtil;
import com.home.vod.util.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.GOOGLE_FCM_TOKEN;
import static com.home.vod.util.Constant.authTokenStr;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "BIBHU2";
    String device_id = "";
    String loggedInStr=null;
    PreferenceManager preferenceManager;
    String refreshedToken;

    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    LanguagePreference languagePreference;

    @Override
    public void onCreate() {
        super.onCreate();

        preferenceManager = PreferenceManager.getPreferenceManager(this);
        loggedInStr = preferenceManager.getUseridFromPref();
        languagePreference = LanguagePreference.getLanguagePreference(this);

        Log.e(TAG, "On create called="+loggedInStr);
        Log.e(TAG, "refreshedToken="+refreshedToken);
        Log.e(TAG, "device_id="+ Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));


        if(loggedInStr != null)
        {
            // This API is called for google_id updation

            AsynUpdateGoogleId asynUpdateGoogleId = new AsynUpdateGoogleId();
            asynUpdateGoogleId.executeOnExecutor(threadPoolExecutor);
        }
    }


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        languagePreference.setLanguageSharedPrefernce(GOOGLE_FCM_TOKEN, refreshedToken);
        Log.e(TAG, "sendRegistrationToServer: " + refreshedToken);

        onCreate();
    }

    private class AsynUpdateGoogleId extends AsyncTask<Void, Void, Void> {
        String responseStr;
        int statusCode = 0;
        String isLogin = "";

        @Override
        protected Void doInBackground(Void... params) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(BuildConfig.SERVICE_BASE_PATH.trim() + Util.UpdateGoogleid.trim());
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", authTokenStr.trim());
                httppost.addHeader("google_id", refreshedToken);
                httppost.addHeader("device_id", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                httppost.addHeader("user_id", loggedInStr);

                // Execute HTTP Post Request
                try {

                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());
                    LogUtil.showLog("BIBHU", "Response Of the google_id updatation =" + responseStr);

                } catch (Exception e) {
                    responseStr = "0";
                }
                JSONObject myJson = null;
                if (responseStr != null) {
                    myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                }

            } catch (Exception e) {
                responseStr = "0";
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {

        }
    }
}
