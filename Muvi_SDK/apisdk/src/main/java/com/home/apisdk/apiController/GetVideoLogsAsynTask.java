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
import com.home.apisdk.apiModel.VideoLogsInputModel;
import com.home.apisdk.apiModel.Video_Log_Output_Model;

import org.json.JSONException;
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
 * Created by Muvi on 12/16/2016.
 * Class to get Video Logs details.
 */
public class GetVideoLogsAsynTask extends AsyncTask<VideoLogsInputModel, Void, Void> {

    private VideoLogsInputModel videoLogsInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String videoLogId = "";
    private GetVideoLogsListener listener;
    private Video_Log_Output_Model video_log_output_model;
    private Context context;

    /**
     * Interface used to allow the caller of a GetVideoLogsAsynTask to run some code when get
     * responses.
     */

    public interface GetVideoLogsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetVideoLogsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param video_log_output_model A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                 Response Code From The Server
         * @param message                On Success Message
         */

        void onGetVideoLogsPostExecuteCompleted(Video_Log_Output_Model video_log_output_model, int status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param videoLogsInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setUserId() etc.
     * @param listener            GetVideoLogs Listener
     * @param context             android.content.Context
     */

    public GetVideoLogsAsynTask(VideoLogsInputModel videoLogsInputModel, GetVideoLogsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.videoLogsInputModel = videoLogsInputModel;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(VideoLogsInputModel... params) {


        try {

            // Execute HTTP Post Request
            URL url = new URL(APIUrlConstant.getVideoLogsUrl());

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.videoLogsInputModel.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.videoLogsInputModel.getUserId())
                    .appendQueryParameter(HeaderConstants.IP_ADDRESS, this.videoLogsInputModel.getIpAddress())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.videoLogsInputModel.getMuviUniqueId())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.videoLogsInputModel.getEpisodeStreamUniqueId())
                    .appendQueryParameter(HeaderConstants.PLAYED_LENGTH, this.videoLogsInputModel.getPlayedLength())
                    .appendQueryParameter(HeaderConstants.WATCH_STATUS, this.videoLogsInputModel.getWatchStatus())
                    .appendQueryParameter(HeaderConstants.DEVICE_TYPE, this.videoLogsInputModel.getDeviceType())
                    .appendQueryParameter(HeaderConstants.LOG_TEMP_ID, this.videoLogsInputModel.getLogTemId())
                    .appendQueryParameter(HeaderConstants.RESUME_TIME, this.videoLogsInputModel.getResumeTime())
                    .appendQueryParameter(HeaderConstants.CONTENT_TYPE_ID, this.videoLogsInputModel.getContentTypeId())
                    .appendQueryParameter(HeaderConstants.LOG_ID, this.videoLogsInputModel.getVideoLogId())
                    .appendQueryParameter(HeaderConstants.IS_STREAMING_RESTRICTION, this.videoLogsInputModel.getIs_streaming_restriction())
                    .appendQueryParameter(HeaderConstants.RESTRICT_STREAM_ID, this.videoLogsInputModel.getRestrict_stream_id());

            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);
            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));

                if (status == 200) {
                    String log_temp_id = mainJson.optString("log_temp_id");
                    video_log_output_model.setLogTempId(log_temp_id);
                    video_log_output_model.setRestrict_stream_id(mainJson.optString("restrict_stream_id"));
                    video_log_output_model.setVideoLogId(mainJson.optString("log_id"));

                } else {
                    video_log_output_model.setLogTempId("0");
                    video_log_output_model.setRestrict_stream_id("0");
                    video_log_output_model.setVideoLogId("0");
                }

            } else {

                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {

            responseStr = "0";
            status = 0;
            message = "Error";
        } catch (Exception e) {

            responseStr = "0";
            status = 0;
            message = "Error";
        }
        return null;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetVideoLogsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetVideoLogsPostExecuteCompleted(video_log_output_model, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetVideoLogsPostExecuteCompleted(video_log_output_model, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetVideoLogsPostExecuteCompleted(video_log_output_model, status, message);

    }

}
