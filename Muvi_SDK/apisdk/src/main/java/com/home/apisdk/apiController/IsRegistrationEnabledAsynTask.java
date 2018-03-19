/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.IsRegistrationEnabledInputModel;
import com.home.apisdk.apiModel.IsRegistrationEnabledOutputModel;

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
 * This Class checks whether the registration is enable or not.
 *
 * @author MUVI
 */
public class IsRegistrationEnabledAsynTask extends AsyncTask<IsRegistrationEnabledInputModel, Void, Void> {

    private IsRegistrationEnabledInputModel isRegistrationEnabledInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private IsRegistrationenabledListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a IsRegistrationEnabledAsynTask to run some code when get
     * responses.
     */

    public interface IsRegistrationenabledListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onIsRegistrationenabledPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param isRegistrationEnabledOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                           Response Code From The Server
         * @param message                          On Success Message
         */

        void onIsRegistrationenabledPostExecuteCompleted(IsRegistrationEnabledOutputModel isRegistrationEnabledOutputModel, int status, String message , String response);
    }


    IsRegistrationEnabledOutputModel isRegistrationEnabledOutputModel = new IsRegistrationEnabledOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param isRegistrationEnabledInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                        For Example: to use this API we have to set following attributes:
     *                                        setAuthToken() etc.
     * @param listener                        IsRegistrationenabled Listener
     * @param context                         android.content.Context
     */

    public IsRegistrationEnabledAsynTask(IsRegistrationEnabledInputModel isRegistrationEnabledInputModel, IsRegistrationenabledListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.isRegistrationEnabledInputModel = isRegistrationEnabledInputModel;
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
    protected Void doInBackground(IsRegistrationEnabledInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getIsRegistrationenabledUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.isRegistrationEnabledInputModel.getAuthToken());


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


                try {
                    if ((myJson.has("is_login")) && myJson.optString("is_login").trim() != null && !myJson.optString("is_login").trim().isEmpty() && !myJson.optString("is_login").trim().equals("null") && !myJson.optString("is_login").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setIs_login(Integer.parseInt(myJson.optString("is_login")));
                    }else {
                        isRegistrationEnabledOutputModel.setIs_login(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setIs_login(0);
                    e.printStackTrace();
                }
                try {
                    if ((myJson.has("isMylibrary")) && myJson.optString("isMylibrary").trim() != null && !myJson.optString("isMylibrary").trim().isEmpty() && !myJson.optString("isMylibrary").trim().equals("null") && !myJson.optString("isMylibrary").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setIsMylibrary(Integer.parseInt(myJson.optString("isMylibrary")));
                    }else {
                        isRegistrationEnabledOutputModel.setIsMylibrary(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setIsMylibrary(0);
                    e.printStackTrace();
                }
                try {
                    if ((myJson.has("signup_step")) && myJson.optString("signup_step").trim() != null && !myJson.optString("signup_step").trim().isEmpty() && !myJson.optString("signup_step").trim().equals("null") && !myJson.optString("signup_step").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setSignup_step(Integer.parseInt(myJson.optString("signup_step")));
                    }else {
                        isRegistrationEnabledOutputModel.setSignup_step(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setSignup_step(0);
                    e.printStackTrace();
                }

                try {
                    if ((myJson.has("has_favourite")) && myJson.optString("has_favourite").trim() != null && !myJson.optString("has_favourite").trim().isEmpty() && !myJson.optString("has_favourite").trim().equals("null") && !myJson.optString("has_favourite").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setHas_favourite(Integer.parseInt(myJson.optString("has_favourite")));
                    }else {
                        isRegistrationEnabledOutputModel.setHas_favourite(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setHas_favourite(0);
                    e.printStackTrace();
                }

                try {
                    if ((myJson.has("isRating")) && myJson.optString("isRating").trim() != null && !myJson.optString("isRating").trim().isEmpty() && !myJson.optString("isRating").trim().equals("null") && !myJson.optString("isRating").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setRating(Integer.parseInt(myJson.optString("isRating")));
                    }else {
                        isRegistrationEnabledOutputModel.setRating(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setRating(0);
                    e.printStackTrace();
                }

                try {
                    if ((myJson.has("isRestrictDevice")) && myJson.optString("isRestrictDevice").trim() != null && !myJson.optString("isRestrictDevice").trim().isEmpty()
                            && !myJson.optString("isRestrictDevice").trim().equals("null") && !myJson.optString("isRestrictDevice").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setIsRestrictDevice(Integer.parseInt(myJson.optString("isRestrictDevice")));
                    }else {
                        isRegistrationEnabledOutputModel.setIsRestrictDevice(0);

                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setIsRestrictDevice(0);
                    e.printStackTrace();
                }

                try {
                    if ((myJson.has("chromecast")) && myJson.optString("chromecast").trim() != null && !myJson.optString("chromecast").trim().isEmpty()
                            && !myJson.optString("chromecast").trim().equals("null") && !myJson.optString("chromecast").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setChromecast(Integer.parseInt(myJson.optString("chromecast")));
                    }else {
                        isRegistrationEnabledOutputModel.setChromecast(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setChromecast(0);
                    e.printStackTrace();
                }
                try {
                    if ((myJson.has("is_streaming_restriction")) && myJson.optString("is_streaming_restriction").trim() != null && !myJson.optString("is_streaming_restriction").trim().isEmpty()
                            && !myJson.optString("is_streaming_restriction").trim().equals("null") && !myJson.optString("is_streaming_restriction").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setIs_streaming_restriction(Integer.parseInt(myJson.optString("is_streaming_restriction")));
                    }else {
                        isRegistrationEnabledOutputModel.setIs_streaming_restriction(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setIs_streaming_restriction(0);
                    e.printStackTrace();
                }
                try {
                    if ((myJson.has("is_offline")) && myJson.optString("is_offline").trim() != null && !myJson.optString("is_offline").trim().isEmpty()
                            && !myJson.optString("is_offline").trim().equals("null") && !myJson.optString("is_offline").trim().matches("")) {
                        isRegistrationEnabledOutputModel.setIs_offline(Integer.parseInt(myJson.optString("is_offline")));
                    }else {
                        isRegistrationEnabledOutputModel.setIs_offline(0);
                    }
                } catch (NumberFormatException e) {
                    isRegistrationEnabledOutputModel.setIs_offline(0);
                    e.printStackTrace();
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
        listener.onIsRegistrationenabledPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onIsRegistrationenabledPostExecuteCompleted(isRegistrationEnabledOutputModel, status, message,responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onIsRegistrationenabledPostExecuteCompleted(isRegistrationEnabledOutputModel, status, message,responseStr);
            return;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onIsRegistrationenabledPostExecuteCompleted(isRegistrationEnabledOutputModel, status, message,responseStr);

    }

    //}
}
