/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.Registration_input;
import com.home.apisdk.apiModel.Registration_output;

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
 * This Class allow users to register themselves to the application if they are not member.
 *
 * @author MUVI
 */
public class RegistrationAsynTask extends AsyncTask<Registration_input, Void, Void> {
    private Registration_input registration_input;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private RegistrationDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a RegistrationAsynTask to run some code when get
     * responses.
     */

    public interface RegistrationDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onRegistrationDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param registration_output A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status              Response Code from the server
         * @param message             On Success Message
         */

        void onRegistrationDetailsPostExecuteCompleted(Registration_output registration_output, int status, String message);
    }

    Registration_output registration_output = new Registration_output();

    /**
     * Constructor to initialise the private data members.
     *
     * @param registration_input A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                           For Example: to use this API we have to set following attributes:
     *                           setAuthToken(),setPassword() etc.
     * @param listener           RegistrationDetails Listener
     * @param context            android.content.Context
     */

    public RegistrationAsynTask(Registration_input registration_input, RegistrationDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.registration_input = registration_input;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "ResistrationAsynTask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(Registration_input... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getRegisterUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.registration_input.getAuthToken());
            httppost.addHeader(HeaderConstants.EMAIL, this.registration_input.getEmail());
            httppost.addHeader(HeaderConstants.MOBILE_NO, this.registration_input.getPhone());
            httppost.addHeader(HeaderConstants.PASSWORD, this.registration_input.getPassword());
            httppost.addHeader(HeaderConstants.NAME, this.registration_input.getName());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.registration_input.getLang_code());
            httppost.addHeader(HeaderConstants.CUSTOM_COUNTRY, this.registration_input.getCustom_country());
            httppost.addHeader(HeaderConstants.CUSTOM_LANGUAGES, this.registration_input.getCustom_languages());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.registration_input.getDevice_id());
            httppost.addHeader(HeaderConstants.GOOGLE_ID, this.registration_input.getGoogle_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.registration_input.getDevice_type());
            httppost.addHeader(HeaderConstants.Custom_last_Name, this.registration_input.getCustom_last_name());

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

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));


                if ((mainJson.has("email")) && mainJson.optString("email").trim() != null && !mainJson.optString("email").trim().isEmpty() && !mainJson.optString("email").trim().equals("null") && !mainJson.optString("email").trim().matches("")) {
                    registration_output.setEmail(mainJson.optString("email"));
                } else {
                    registration_output.setEmail("");

                }

                if ((mainJson.has("id")) && mainJson.optString("id").trim() != null && !mainJson.optString("id").trim().isEmpty() && !mainJson.optString("id").trim().equals("null") && !mainJson.optString("id").trim().matches("")) {
                    registration_output.setId(mainJson.optString("id"));
                } else {
                    registration_output.setId("");

                }
                if ((mainJson.has("display_name")) && mainJson.optString("display_name").trim() != null && !mainJson.optString("display_name").trim().isEmpty() && !mainJson.optString("display_name").trim().equals("null") && !mainJson.optString("display_name").trim().matches("")) {
                    String hh = mainJson.optString("display_name");
                    registration_output.setDisplay_name(mainJson.optString("display_name"));


                } else {
                    registration_output.setDisplay_name("");

                }
                if ((mainJson.has("profile_image")) && mainJson.optString("profile_image").trim() != null && !mainJson.optString("profile_image").trim().isEmpty() && !mainJson.optString("profile_image").trim().equals("null") && !mainJson.optString("profile_image").trim().matches("")) {
                    registration_output.setProfile_image(mainJson.optString("profile_image"));


                } else {
                    registration_output.setProfile_image("");

                }
                if ((mainJson.has("isSubscribed")) && mainJson.optString("isSubscribed").trim() != null && !mainJson.optString("isSubscribed").trim().isEmpty() && !mainJson.optString("isSubscribed").trim().equals("null") && !mainJson.optString("isSubscribed").trim().matches("")) {
                    registration_output.setIsSubscribed(mainJson.optString("isSubscribed"));
                } else {
                    registration_output.setIsSubscribed("");

                }
                if ((mainJson.has("nick_name")) && mainJson.optString("nick_name").trim() != null && !mainJson.optString("nick_name").trim().isEmpty() && !mainJson.optString("nick_name").trim().equals("null") && !mainJson.optString("nick_name").trim().matches("")) {
                    registration_output.setNick_name(mainJson.optString("nick_name"));
                } else {
                    registration_output.setNick_name("");

                }
                if ((mainJson.has("login_history_id")) && mainJson.optString("login_history_id").trim() != null && !mainJson.optString("login_history_id").trim().isEmpty() && !mainJson.optString("login_history_id").trim().equals("null") && !mainJson.optString("login_history_id").trim().matches("")) {
                    registration_output.setLogin_history_id(mainJson.optString("login_history_id"));
                }

                if ((mainJson.has("studio_id")) && mainJson.optString("studio_id").trim() != null && !mainJson.optString("studio_id").trim().isEmpty() && !mainJson.optString("studio_id").trim().equals("null") && !mainJson.optString("studio_id").trim().matches("")) {
                    registration_output.setStudio_id(mainJson.optString("studio_id"));

                } else {
                    registration_output.setStudio_id("");

                }

                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    registration_output.setMsg(mainJson.optString("msg"));
                } else {
                    registration_output.setMsg("");

                }
                if ((mainJson.has("custom_last_name")) && mainJson.optString("custom_last_name").trim() != null && !mainJson.optString("custom_last_name").trim().isEmpty() && !mainJson.optString("custom_last_name").trim().equals("null") && !mainJson.optString("custom_last_name").trim().matches("")) {
                    registration_output.setCustom_last_name(mainJson.optString("custom_last_name"));
                } else {
                    registration_output.setCustom_last_name("");

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
        listener.onRegistrationDetailsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onRegistrationDetailsPostExecuteCompleted(registration_output, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onRegistrationDetailsPostExecuteCompleted(registration_output, status, message);

        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onRegistrationDetailsPostExecuteCompleted(registration_output, status, message);
    }
}
