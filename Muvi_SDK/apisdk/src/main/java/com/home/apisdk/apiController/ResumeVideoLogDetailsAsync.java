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
import com.home.apisdk.apiModel.ResumeVideoLogDetailsInput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MUVI on 7/6/2017.
 * Class to get Resume Video Logs details.
 */

public class ResumeVideoLogDetailsAsync extends AsyncTask<ResumeVideoLogDetailsInput, Void, Void> {

    private ResumeVideoLogDetailsInput resumeVideoLogDetailsInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String videoLogId = "";
    private ResumeVideoLogDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a ResumeVideoLogDetailsAsync to run some code when get
     * responses.
     */


    public interface ResumeVideoLogDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetResumeVideoLogDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param status     Response Code from the server
         * @param message    On Success Message
         * @param videoLogId For Getting The Video Log Id
         */

        void onGetResumeVideoLogDetailsPostExecuteCompleted(int status, String message, String videoLogId);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param resumeVideoLogDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                   For Example: to use this API we have to set following attributes:
     *                                   setAuthToken(),setUser_id() etc.
     * @param listener                   ResumeVideoLogDetailsListener
     * @param context                    android.content.Context
     */

    public ResumeVideoLogDetailsAsync(ResumeVideoLogDetailsInput resumeVideoLogDetailsInput, ResumeVideoLogDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.resumeVideoLogDetailsInput = resumeVideoLogDetailsInput;
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
    protected Void doInBackground(ResumeVideoLogDetailsInput... params) {

        try {

            // Execute HTTP Post Request

            URL url = new URL(APIUrlConstant.getVideoLogsUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.resumeVideoLogDetailsInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.resumeVideoLogDetailsInput.getUser_id())
                    .appendQueryParameter(HeaderConstants.IP_ADDRESS, this.resumeVideoLogDetailsInput.getIp_address())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.resumeVideoLogDetailsInput.getMovie_id())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.resumeVideoLogDetailsInput.getEpisode_id())
                    .appendQueryParameter(HeaderConstants.PLAYED_LENGTH, this.resumeVideoLogDetailsInput.getPlayed_length())
                    .appendQueryParameter(HeaderConstants.WATCH_STATUS, this.resumeVideoLogDetailsInput.getWatch_status());

            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));
                if (status == 200) {
                    videoLogId = mainJson.optString("log_id");
                } else {
                    videoLogId = "0";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetResumeVideoLogDetailsPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetResumeVideoLogDetailsPostExecuteCompleted(status, responseStr, videoLogId);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetResumeVideoLogDetailsPostExecuteCompleted(status, responseStr, videoLogId);
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onGetResumeVideoLogDetailsPostExecuteCompleted(status, responseStr, videoLogId);
    }
}
