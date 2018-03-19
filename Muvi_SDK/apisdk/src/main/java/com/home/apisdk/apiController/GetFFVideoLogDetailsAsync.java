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
import com.home.apisdk.apiModel.FFVideoLogDetailsInput;

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
 * This Class is called firstly whenever we play any video.
 *
 * @author MUVI
 */

public class GetFFVideoLogDetailsAsync extends AsyncTask<FFVideoLogDetailsInput, Void, Void> {

    private FFVideoLogDetailsInput ffVideoLogDetailsInput;
    private String responseStr;
    private String message;
    private int code;
    private String PACKAGE_NAME;
    private String videoLogId = "";
    private String videoLogstreamId = "";
    private GetFFVideoLogsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetFFVideoLogDetailsAsync to run some code when get
     * responses.
     */

    public interface GetFFVideoLogsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetFFVideoLogsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param code       Response Code From the Server.
         * @param videoLogId For getting the Video Log Id
         */

        void onGetFFVideoLogsPostExecuteCompleted(int code, String status, String videoLogId);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param videoBufferLogsInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                  For Example: to use this API we have to set following attributes:
     *                                  setAuthToken(),setUser_id() etc.
     * @param listener                  GetFFVideoLogs Listener
     * @param context                   android.content.Context
     */

    public GetFFVideoLogDetailsAsync(FFVideoLogDetailsInput videoBufferLogsInputModel, GetFFVideoLogsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.ffVideoLogDetailsInput = ffVideoLogDetailsInput;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(FFVideoLogDetailsInput... params) {

        try {

            // Execute HTTP Post Request
            URL url = new URL(APIUrlConstant.getVideoLogsUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.ffVideoLogDetailsInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.ffVideoLogDetailsInput.getUser_id())
                    .appendQueryParameter(HeaderConstants.IP_ADDRESS, this.ffVideoLogDetailsInput.getIp_address())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.ffVideoLogDetailsInput.getMovie_id())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.ffVideoLogDetailsInput.getEpisode_id())
                    .appendQueryParameter(HeaderConstants.PLAYED_LENGTH, this.ffVideoLogDetailsInput.getPlayed_length())
                    .appendQueryParameter(HeaderConstants.WATCH_STATUS, this.ffVideoLogDetailsInput.getWatch_status())
                    .appendQueryParameter(HeaderConstants.DEVICE_TYPE, this.ffVideoLogDetailsInput.getDevice_type())
                    .appendQueryParameter(HeaderConstants.LOG_ID, this.ffVideoLogDetailsInput.getLog_id());
            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, code, message);

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                code = Integer.parseInt(mainJson.optString("code"));

                if (code == 200) {
                    if ((mainJson.has("log_id")) && mainJson.optString("log_id").trim() != null && !mainJson.optString("log_id").trim().isEmpty() && !mainJson.optString("log_id").trim().equals("null") && !mainJson.optString("log_id").trim().matches("")) {
                        videoLogId = mainJson.optString("log_id");
                    }

                    if ((mainJson.has("restrict_stream_id")) && mainJson.optString("restrict_stream_id").trim() != null && !mainJson.optString("restrict_stream_id").trim().isEmpty() && !mainJson.optString("restrict_stream_id").trim().equals("null") && !mainJson.optString("restrict_stream_id").trim().matches("")) {
                        videoLogstreamId = mainJson.optString("restrict_stream_id");
                    }

                } else {
                    videoLogId = "0";
                }
            }
        } catch (Exception e) {
            videoLogId = "0";

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetFFVideoLogsPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetFFVideoLogsPostExecuteCompleted(code, responseStr, videoLogId);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetFFVideoLogsPostExecuteCompleted(code, responseStr, videoLogId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onGetFFVideoLogsPostExecuteCompleted(code, responseStr, videoLogId);
    }
}
