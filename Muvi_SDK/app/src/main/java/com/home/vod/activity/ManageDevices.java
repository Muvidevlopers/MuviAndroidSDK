package com.home.vod.activity;



import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.home.apisdk.apiController.LoadRegisteredDevicesAsync;
import com.home.apisdk.apiModel.LoadRegisteredDevicesInput;
import com.home.apisdk.apiModel.LoadRegisteredDevicesOutput;
import com.home.vod.R;
import com.home.vod.adapter.DeviceListAdapter;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FontUtls;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_MANAGE_DEVICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DEVICE_AVAILABE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_YOUR_DEVICE;
import static com.home.vod.preferences.LanguagePreference.MANAGE_DEVICE;
import static com.home.vod.preferences.LanguagePreference.NO_DEVICE_AVAILABE;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.YOUR_DEVICE;
import static com.home.vod.util.Constant.authTokenStr;

public class ManageDevices extends AppCompatActivity implements LoadRegisteredDevicesAsync.LoadRegisteredDevicesListener {
    String userId = "";
    String emailId = "";
    TextView name_of_user;
    ProgressBarHandler pDialog;

    ArrayList<String> DeviceName = new ArrayList<>();
    ArrayList<String> DeviceFalg = new ArrayList<>();
    ArrayList<String> DeviceInfo = new ArrayList<>();
    ListView device_list;
    TextView manage_device_text;


    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    private PreferenceManager preferenceManager;
    LanguagePreference languagePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_devices);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(ManageDevices.this);
        device_list = (ListView) findViewById(R.id.device_list);
        manage_device_text = (TextView) findViewById(R.id.manage_device_text);

        manage_device_text.setText("  " + languagePreference.getTextofLanguage( YOUR_DEVICE, DEFAULT_YOUR_DEVICE));
        FontUtls.loadFont(ManageDevices.this, getResources().getString(R.string.regular_fonts),manage_device_text);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(MANAGE_DEVICE, DEFAULT_MANAGE_DEVICE));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userId = preferenceManager.getUseridFromPref();
        emailId = preferenceManager.getEmailIdFromPref();
        LoadRegisteredDevicesInput loadRegisteredDevicesInput=new LoadRegisteredDevicesInput();
        loadRegisteredDevicesInput.setAuthToken(authTokenStr.trim());
        loadRegisteredDevicesInput.setDevice(Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID));
        loadRegisteredDevicesInput.setUser_id(userId);
        loadRegisteredDevicesInput.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        LoadRegisteredDevicesAsync asynLoadRegisteredDevices = new LoadRegisteredDevicesAsync(loadRegisteredDevicesInput, this, this);
        asynLoadRegisteredDevices.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onLoadRegisteredDevicesPreExecuteStarted() {
        pDialog = new ProgressBarHandler(ManageDevices.this);
        pDialog.show();
    }

    @Override
    public void onLoadRegisteredDevicesPostExecuteCompleted(ArrayList<LoadRegisteredDevicesOutput> loadRegisteredDevicesOutputs, int status, String message) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {
        }
        if (status == 200) {
            // Start parsing Here
            for (int i=0;i<loadRegisteredDevicesOutputs.size();i++){

                String devicename=loadRegisteredDevicesOutputs.get(i).getDevice();
                String deviceinfo=loadRegisteredDevicesOutputs.get(i).getDevice_info();
                String flag=loadRegisteredDevicesOutputs.get(i).getFlag();

                DeviceName.add(devicename);
                DeviceInfo.add(deviceinfo);
                DeviceFalg.add(flag);
            }

            DeviceListAdapter adapter = new DeviceListAdapter(ManageDevices.this, DeviceName, DeviceInfo, DeviceFalg);
            device_list.setAdapter(adapter);

        } else {
            // Show The Error Message Here
            Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_DEVICE_AVAILABE,DEFAULT_NO_DEVICE_AVAILABE), Toast.LENGTH_LONG).show();
            finish();
        }
    }
//    private class AsynLoadRegisteredDevices extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//        String responseStr="0";
//        int statusCode;
//        String message;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.ManageDevices.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("user_id", userId);
//                httppost.addHeader("device", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//                httppost.addHeader("lang_code",Util.getTextofLanguage(ManageDevices.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//                // Execute HTTP Post Request
//                try {
//
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//                    LogUtil.showLog("BIBHU","responseStr of device list ="+responseStr);
//
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    ManageDevices.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ManageDevices.this, Util.getTextofLanguage(ManageDevices.this, Util.SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//                    message = myJson.optString("msg");
//                }
//
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
//
//            try {
//                if (pDialog != null && pDialog.isShowing()) {
//                    pDialog.hide();
//                    pDialog = null;
//                }
//            } catch (IllegalArgumentException ex) {
//            }
//            if (responseStr != null) {
//                if (statusCode==200) {
//                    // Start parsing Here
//
//                    try {
//                        JSONObject myJson = new JSONObject(responseStr);
//                        JSONArray jsonArray = myJson.optJSONArray("device_list");
//
//                        for(int i=0 ;i<jsonArray.length();i++)
//                        {
//                            DeviceName.add(jsonArray.optJSONObject(i).optString("device").trim());
//                            DeviceInfo.add(jsonArray.optJSONObject(i).optString("device_info").trim());
//                            DeviceFalg.add(jsonArray.optJSONObject(i).optString("flag").trim());
//                        }
//
//                        DeviceListAdapter adapter = new DeviceListAdapter(ManageDevices.this,DeviceName,DeviceInfo,DeviceFalg);
//                        device_list.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                {
//                    // Show The Error Message Here
//                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            }
//            else{
//                // Show Try Again Msg and finish here.
//                Toast.makeText(getApplicationContext(),  Util.getTextofLanguage(ManageDevices.this,Util.TRY_AGAIN,Util.DEFAULT_TRY_AGAIN), Toast.LENGTH_LONG).show();
//                finish();
//            }
//
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(ManageDevices.this);
//            pDialog.show();
//        }
//    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }
}
