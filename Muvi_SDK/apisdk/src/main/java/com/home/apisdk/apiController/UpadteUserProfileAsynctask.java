/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.Update_UserProfile_Input;
import com.home.apisdk.apiModel.Update_UserProfile_Output;

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
 * This Class allow users to update their profile details whenever needed.
 *
 * @author MUVI
 */

public class UpadteUserProfileAsynctask extends AsyncTask<Update_UserProfile_Input, Void, Void> {

    private Update_UserProfile_Input update_userProfile_input;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private int code;
    private Update_UserProfile_Output update_userProfile_output;
    private Update_UserProfileListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a UpadteUserProfileAsynctask to run some code when get
     * responses.
     */

    public interface Update_UserProfileListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onUpdateUserProfilePreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param update_userProfile_output A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code                      Response Code from the server
         * @param message                   On Success Message
         */

        void onUpdateUserProfilePostExecuteCompleted(Update_UserProfile_Output update_userProfile_output, int code, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param update_userProfile_input A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                 For Example: to use this API we have to set following attributes:
     *                                 setAuthToken(),setPassword() etc.
     * @param listener                 Update_UserProfile Listenerv
     * @param context                  android.content.Context
     */

    public UpadteUserProfileAsynctask(Update_UserProfile_Input update_userProfile_input, Update_UserProfileListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.update_userProfile_input = update_userProfile_input;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "UpadteUserProfileAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(Update_UserProfile_Input... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getUpdateProfileUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.update_userProfile_input.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.update_userProfile_input.getUser_id());
            httppost.addHeader(HeaderConstants.NAME, this.update_userProfile_input.getName());
            httppost.addHeader(HeaderConstants.PASSWORD, this.update_userProfile_input.getPassword());
            httppost.addHeader(HeaderConstants.CUSTOM_LANGUAGES, this.update_userProfile_input.getCustom_languages());
            httppost.addHeader(HeaderConstants.CUSTOM_COUNTRY, this.update_userProfile_input.getCustom_country());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.update_userProfile_input.getLang_code());
            httppost.addHeader(HeaderConstants.MOBILE_NO, this.update_userProfile_input.getPhone_no());
            httppost.addHeader(HeaderConstants.Custom_last_Name, this.update_userProfile_input.getCustom_last_name());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                message = "";

            } catch (IOException e) {
                code = 0;
                message = "";
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
            }

            if (code == 200) {


                try {
                    update_userProfile_output = new Update_UserProfile_Output();
                    update_userProfile_output.setName(myJson.optString("name"));
                    update_userProfile_output.setEmail(myJson.optString("email"));
                    update_userProfile_output.setNick_name(myJson.optString("nick_name"));
                    update_userProfile_output.setProfile_image(myJson.optString("profile_image"));
                    if (myJson.has("mobile_number")) {
                        update_userProfile_output.setPhone_no(myJson.optString("mobile_number"));
                    }
                    else {
                        update_userProfile_output.setPhone_no("");

                    }
                    Log.v("MUVISDK", "user_name====== " + myJson.optString("name"));

                } catch (Exception e) {
                    code = 0;
                    message = "";
                }

            }
        } catch (Exception e) {
            code = 0;
            message = "";
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onUpdateUserProfilePreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onUpdateUserProfilePostExecuteCompleted(update_userProfile_output, code, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onUpdateUserProfilePostExecuteCompleted(update_userProfile_output, code, message);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onUpdateUserProfilePostExecuteCompleted(update_userProfile_output, code, message);
    }
}
