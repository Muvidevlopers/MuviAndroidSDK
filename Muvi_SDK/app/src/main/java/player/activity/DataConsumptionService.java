package player.activity;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.LogUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import player.utils.DBHelper;
import player.utils.Util;

public class DataConsumptionService extends Service {

    String email_Id = "";
    PreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceManager = PreferenceManager.getPreferenceManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        registerReceiver(BandwidthLogReceiver, new IntentFilter("BnadwidthLog"));

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public BroadcastReceiver BandwidthLogReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            Log.v("BIBHU17", "Receiver called");


            if (preferenceManager != null) {
                    email_Id = preferenceManager.getEmailIdFromPref();
                try
                {
                    Util.timer.cancel();
                    StartNewTimer();
                }
                catch(Exception e)
                {
                    StartNewTimer();
                }
            }


        }
    };

    public void StartNewTimer(){
        Util.timer = new Timer();
        Util.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GetTotalUsedData(email_Id);
                LogUtil.showLog("BIBHU17", "*****************************************************==============Timer Called");
            }
        },0,60000);
    }

    public void GetTotalUsedData(String emailIdStr)
    {

        SQLiteDatabase DB = DataConsumptionService.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = DB.rawQuery("SELECT * FROM "+ DBHelper.DOWNLOAD_CONTENT_INFO+" WHERE email = '"+emailIdStr+"' AND  server_sending_final_status = '0'", null);
        int count = cursor.getCount();

        if (count > 0) {
            if (cursor.moveToFirst()) {
                do {

                    LogUtil.showLog("BIBHU17", "fetching content size");

                    DownloadManager downloadManager1 = (DownloadManager) DataConsumptionService.this.getSystemService(DOWNLOAD_SERVICE);
                    DownloadManager.Query download_id_query = new DownloadManager.Query();
                    download_id_query.setFilterById(Long.parseLong(cursor.getString(0).trim())); //filter by id which you have receieved when reqesting download from download manager
                    Cursor id_cursor = downloadManager1.query(download_id_query);

                    long TotalUsedData = 0;

                    if (id_cursor != null && id_cursor.getCount() > 0) {
                        if (id_cursor.moveToFirst()) {
                            int columnIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = id_cursor.getInt(columnIndex);

                            int sizeIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                            int downloadedIndex = id_cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                            long size = id_cursor.getInt(sizeIndex);
                            long downloaded = id_cursor.getInt(downloadedIndex);

                            TotalUsedData = downloaded/1024;
                            LogUtil.showLog("BIBHU17","TotalUsedData Download size============"+TotalUsedData);

                        }
                    }

                    // Fetchind data from DataBase

                    String download_contnet_id = cursor.getString(0).trim();
                    String log_id = cursor.getString(1).trim();
                    String authtoken = cursor.getString(2).trim();
                    String email = cursor.getString(3).trim();
                    String ipaddress = cursor.getString(4).trim();
                    String movie_id = cursor.getString(5);
                    String episode_id = cursor.getString(6).trim();
                    String device_type = cursor.getString(7).trim();
                    String download_status = cursor.getString(8).trim();
                    String server_sending_final_status = cursor.getString(9).trim();

                    if(download_status.trim().equals("1"))
                    {
                        String query1 = "UPDATE "+ DBHelper.DOWNLOAD_CONTENT_INFO+" SET server_sending_final_status = '1'" +
                                " WHERE email = '"+emailIdStr+"' AND download_contnet_id = '"+download_contnet_id+"'";
                        DB.execSQL(query1);
                    }

                    // Start API call for bandwith log of downloading content.

                    SendConsumedDataToServer(ipaddress+","+movie_id+","+episode_id+","+log_id+","+TotalUsedData+","+download_contnet_id);


                } while (cursor.moveToNext());
            }
        }
        else
        {
            try{
            Util.timer.cancel();
            }catch (Exception e){};
        }
    }



    public void SendConsumedDataToServer(String Data) {
        new SendData().execute(Data);
    }

    class SendData extends AsyncTask<String, String, String> {

        String responseStr;
        int statusCode = 0;
        String request_data = "";
        String log_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {

            LogUtil.showLog("BIBHU17","f_url[0]======="+f_url[0]);

            String Data[]= f_url[0].split(",");

            String urlRouteList = Util.rootUrl().trim()+ Util.bufferLogUrl.trim();
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", Util.authTokenStr.trim());
                httppost.addHeader("ip_address", Data[0]);
                httppost.addHeader("movie_id", Data[1]);
                httppost.addHeader("episode_id",Data[2]);
                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", Data[3]);
                httppost.addHeader("downloaded_bandwidth",Data[4]);
                httppost.addHeader("request_data",Data[5]);



                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                } catch (Exception e) {
                    statusCode = 0;
                    e.printStackTrace();
                }


                LogUtil.showLog("BIBHU17","f_url[0]responseStr======="+responseStr);

                JSONObject myJson = null;
                if(responseStr!=null) {
                    myJson = new JSONObject(responseStr);
                    statusCode = Integer.parseInt(myJson.optString("code"));
                    request_data = myJson.optString("request_data");
                    log_id = myJson.optString("log_id");

                    if(statusCode == 200)
                    {
                        SQLiteDatabase DB = DataConsumptionService.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                        String query1 = "UPDATE "+ DBHelper.DOWNLOAD_CONTENT_INFO+" SET log_id = '"+log_id+"'" +
                                " WHERE download_contnet_id = '"+request_data+"'";
                        DB.execSQL(query1);

                        String query = "DELETE FROM "+ DBHelper.DOWNLOAD_CONTENT_INFO+"  WHERE server_sending_final_status = '1'";
                        DB.execSQL(query);

                    }else
                    {
                        SQLiteDatabase DB = DataConsumptionService.this.openOrCreateDatabase(DBHelper.DATABASE_NAME, MODE_PRIVATE, null);
                        String query1 = "UPDATE "+ DBHelper.DOWNLOAD_CONTENT_INFO+" SET server_sending_final_status = '0'" +
                                " WHERE download_contnet_id = '"+request_data+"'";
                        DB.execSQL(query1);
                    }

                }
            }
            catch (Exception e) {
                statusCode = 0;
            }

            return null;
        }


        @Override
        protected void onPostExecute(String file_url) {


        }
    }


}
