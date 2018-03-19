/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.UpdateGoogleIdInputModel;
import com.home.apisdk.apiModel.UpdateGoogleIdOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class allow to update the Google Id of the users.
 *
 * @author MUVI
 */


public class UpdateGoogleIdAsynTask extends AsyncTask<UpdateGoogleIdInputModel, Void, Void> {

    private UpdateGoogleIdInputModel updateGoogleIdInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private UpdateGoogleIdListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a UpdateGoogleIdAsynTask to run some code when get
     * responses.
     */

    public interface UpdateGoogleIdListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onUpdateGoogleIdPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param updateGoogleIdOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                    Response Code from the server
         * @param message                   On Success Message
         */

        void onUpdateGoogleIdPostExecuteCompleted(UpdateGoogleIdOutputModel updateGoogleIdOutputModel, int status, String message);
    }


    UpdateGoogleIdOutputModel updateGoogleIdOutputModel = new UpdateGoogleIdOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param updateGoogleIdInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                 For Example: to use this API we have to set following attributes:
     *                                 setAuthToken(),setUser_id() etc.
     * @param listener                 UpdateGoogleIdListener
     * @param context                  android.content.Context
     */

    public UpdateGoogleIdAsynTask(UpdateGoogleIdInputModel updateGoogleIdInputModel, UpdateGoogleIdListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.updateGoogleIdInputModel = updateGoogleIdInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");


    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(UpdateGoogleIdInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getUpdateGoogleid());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.updateGoogleIdInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.updateGoogleIdInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.updateGoogleIdInputModel.getDevice_id());
            httppost.addHeader(HeaderConstants.GOOGLE_ID, this.updateGoogleIdInputModel.getGoogle_id());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                status = 0;
                message = "Error";


            } catch (IOException e) {
                status = 0;
                message = "Error";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("status");
            }


            if (status == 200) {

                if ((myJson.has("msg")) && myJson.optString("msg").trim() != null && !myJson.optString("msg").trim().isEmpty() && !myJson.optString("msg").trim().equals("null") && !myJson.optString("msg").trim().matches("")) {
                    updateGoogleIdOutputModel.setMsg(myJson.optString("msg"));
                }


            } else {

                responseStr = "0";
                status = 0;
                message = "Error";
            }
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
        listener.onUpdateGoogleIdPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onUpdateGoogleIdPostExecuteCompleted(updateGoogleIdOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onUpdateGoogleIdPostExecuteCompleted(updateGoogleIdOutputModel, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onUpdateGoogleIdPostExecuteCompleted(updateGoogleIdOutputModel, status, message);

    }

    //}
}
