/**
 * SDK initialization, platform and device information classes.
 */

package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.DownloadContentInput;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class gives a short note about the company/organisation to its users.
 *
 * @author MUVI
 */

public class DownloadContentAsync extends AsyncTask<DownloadContentInput, Void, Void> {


    private DownloadContentInput downloadContentInput;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String filepath;
    private String responseStr;
    private DownloadContentListener listener;
    private Context context;


    /**
     * Interface used to allow the caller of a DownloadContentAsync to run some code when get
     * responses.
     */
    public interface DownloadContentListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */
        void onDownloadContentPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param filepath Holds download file of "filepath"
         */
        void onDownloadContentPostExecuteCompleted(String filepath);
    }


    /**
     * Constructor to initialise the private data members.
     *
     * @param downloadContentInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken() ,setVLink() etc.
     * @param listener            DownloadContentListener Listener
     * @param context             android.content.Context
     */
    public DownloadContentAsync(DownloadContentInput downloadContentInput, DownloadContentListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.downloadContentInput = downloadContentInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetUserProfileAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return Null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(DownloadContentInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getAboutUs());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.downloadContentInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.downloadContentInput.getvLink());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                try {
                    myJson = new JSONObject(responseStr);
                    filepath = myJson.optString("file_path");

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        } catch (Exception e) {


        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onDownloadContentPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onDownloadContentPostExecuteCompleted(filepath);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onDownloadContentPostExecuteCompleted(filepath);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onDownloadContentPostExecuteCompleted(filepath);
    }
}