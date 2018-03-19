/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.Utils;
import com.home.apisdk.apiModel.VideoBufferLogsInputModel;
import com.home.apisdk.apiModel.VideoBufferLogsOutputModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This Class is used  for the buffering of video while playing online
 *
 * @author MUVI
 */

public class GetFFVideoBufferLogDetailsAsync extends AsyncTask<VideoBufferLogsInputModel, Void, Void> {
    VideoBufferLogsInputModel videoBufferLogsInputModel;

    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetFFVideoBufferLogsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetFFVideoBufferLogDetailsAsync to run some code when get
     * responses.
     */

    public interface GetFFVideoBufferLogsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetFFVideoBufferLogsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param videoBufferLogsOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                     Response From The Server
         * @param message                    On Success Message
         */

        void onGetFFVideoBufferLogsPostExecuteCompleted(VideoBufferLogsOutputModel videoBufferLogsOutputModel, int status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param videoBufferLogsInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                  For Example: to use this API we have to set following attributes:
     *                                  setAuthToken(),setUserId() etc.
     * @param listener                  GetFFVideoBufferLogsListener
     * @param context                   android.content.Context
     */

    public GetFFVideoBufferLogDetailsAsync(VideoBufferLogsInputModel videoBufferLogsInputModel, GetFFVideoBufferLogsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.videoBufferLogsInputModel = videoBufferLogsInputModel;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }


    VideoBufferLogsOutputModel videoBufferLogsOutputModel = new VideoBufferLogsOutputModel();

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(VideoBufferLogsInputModel... params) {

        try {

            // Execute HTTP Post Request
            URL url = new URL(APIUrlConstant.getVideoBufferLogsUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.videoBufferLogsInputModel.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.videoBufferLogsInputModel.getUserId())
                    .appendQueryParameter(HeaderConstants.IP_ADDRESS, this.videoBufferLogsInputModel.getIpAddress())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.videoBufferLogsInputModel.getMuviUniqueId())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.videoBufferLogsInputModel.getEpisodeStreamUniqueId())
                    .appendQueryParameter(HeaderConstants.LOG_ID, this.videoBufferLogsInputModel.getBufferLogId())
                    .appendQueryParameter(HeaderConstants.RESOLUTION, this.videoBufferLogsInputModel.getVideoResolution())
                    .appendQueryParameter(HeaderConstants.DEVICE_TYPE, this.videoBufferLogsInputModel.getDeviceType())
                    .appendQueryParameter(HeaderConstants.START_TIME, this.videoBufferLogsInputModel.getBufferStartTime())
                    .appendQueryParameter(HeaderConstants.END_TIME, this.videoBufferLogsInputModel.getBufferEndTime())
                    .appendQueryParameter(HeaderConstants.LOG_UNIQUE_ID, this.videoBufferLogsInputModel.getBufferLogUniqueId())
                    .appendQueryParameter(HeaderConstants.LOCATION, this.videoBufferLogsInputModel.getLocation());

            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));

                if (status == 200) {


                    if ((mainJson.has("log_id")) && mainJson.optString("log_id").trim() != null && !mainJson.optString("log_id").trim().isEmpty() && !mainJson.optString("log_id").trim().equals("null") && !mainJson.optString("log_id").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLogId(mainJson.optString("log_id"));

                    }
                    if ((mainJson.has("log_unique_id")) && mainJson.optString("log_unique_id").trim() != null && !mainJson.optString("log_unique_id").trim().isEmpty() && !mainJson.optString("log_unique_id").trim().equals("null") && !mainJson.optString("log_unique_id").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLogUniqueId(mainJson.optString("log_unique_id"));

                    }
                    if ((mainJson.has("location")) && mainJson.optString("location").trim() != null && !mainJson.optString("location").trim().isEmpty() && !mainJson.optString("location").trim().equals("null") && !mainJson.optString("location").trim().matches("")) {
                        videoBufferLogsOutputModel.setBufferLocation(mainJson.optString("location"));

                    }

                } else {
                    videoBufferLogsInputModel.setBufferLogUniqueId("0");
                    videoBufferLogsOutputModel.setBufferLogId("0");
                    videoBufferLogsOutputModel.setBufferLocation("0");
                }
            }
        } catch (Exception e) {
            videoBufferLogsInputModel.setBufferLogUniqueId("0");
            videoBufferLogsOutputModel.setBufferLogId("0");
            videoBufferLogsOutputModel.setBufferLocation("0");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetFFVideoBufferLogsPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetFFVideoBufferLogsPostExecuteCompleted(videoBufferLogsOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetFFVideoBufferLogsPostExecuteCompleted(videoBufferLogsOutputModel, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onGetFFVideoBufferLogsPostExecuteCompleted(videoBufferLogsOutputModel, status, message);
    }
}

