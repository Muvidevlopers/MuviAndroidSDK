/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.CheckFbUserDetailsInput;

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
 * This Class checks all the necessary details about the user when they login via Facebook.
 * Details like Name, Email, Profile picture etc.
 *
 * @author MUVI
 */

public class CheckFbUserDetailsAsyn extends AsyncTask<CheckFbUserDetailsInput, Void, Void> {

    private CheckFbUserDetailsInput checkFbUserDetailsInput;
    private String PACKAGE_NAME;
    private String responseStr;
    private String message;
    private int isNewUserStr = 1;
    private int code;
    private JSONObject myJson = null;
    private CheckFbUserDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a CheckFbUserDetailsAsyn to run some code when get
     * responses.
     */

    public interface CheckFbUserDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onCheckFbUserDetailsAsynPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param code Response Code From The Server
         */

        void onCheckFbUserDetailsAsynPostExecuteCompleted(int code);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param checkFbUserDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                For Example: to use this API we have to set following attributes:
     *                                setAuthToken(),setFb_userid() etc.
     * @param listener                CheckFbUserDetails Listener
     * @param context                 android.content.Context
     */

    public CheckFbUserDetailsAsyn(CheckFbUserDetailsInput checkFbUserDetailsInput, CheckFbUserDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.checkFbUserDetailsInput = checkFbUserDetailsInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "register user payment");
    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(CheckFbUserDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getFbUserExistsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.checkFbUserDetailsInput.getAuthToken());
            httppost.addHeader(HeaderConstants.FB_USER_ID, this.checkFbUserDetailsInput.getFb_userid());


            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);


            } catch (ClientProtocolException e) {
                code = 0;
                e.printStackTrace();
            } catch (IOException e) {
                code = 0;
                e.printStackTrace();
            }

            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                try {
                    isNewUserStr = Integer.parseInt(myJson.optString("is_newuser"));
                }catch (Exception e){
                    isNewUserStr = 0;
                }

            }

        } catch (JSONException e) {
            code = 0;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onCheckFbUserDetailsAsynPreExecuteStarted();

        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onCheckFbUserDetailsAsynPostExecuteCompleted(code);
    }
}
