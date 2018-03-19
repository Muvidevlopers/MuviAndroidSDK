/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.Forgotpassword_input;
import com.home.apisdk.apiModel.Forgotpassword_output;

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
 * This Class helps users to reset their password if they forgot somehow.
 * This Class send reset link to the registered Email id provided by the user.
 *
 * @author MUVI
 */
public class ForgotpassAsynTask extends AsyncTask<Forgotpassword_input, Void, Void> {

    private Forgotpassword_input forgotpassword_input;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private ForgotpassDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a ForgotpassAsynTask to run some code when get
     * responses.
     */

    public interface ForgotpassDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onForgotpassDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param forgotpassword_output A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                Response Code From The Server
         * @param message               On Success Message
         */

        void onForgotpassDetailsPostExecuteCompleted(Forgotpassword_output forgotpassword_output, int status, String message);
    }


    Forgotpassword_output forgotpassword_output = new Forgotpassword_output();

    /**
     * Constructor to initialise the private data members.
     *
     * @param forgotpassword_input A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                             For Example: to use this API we have to set following attributes:
     *                             setAuthToken(),setEmail() etc.
     * @param listener             ForgotpassDetails Listener
     * @param context              android.content.Context
     */

    public ForgotpassAsynTask(Forgotpassword_input forgotpassword_input, ForgotpassDetailsListener listener, Context context) {
        this.forgotpassword_input = forgotpassword_input;
        this.listener = listener;
        this.context = context;

        Log.v("MUVISDK", "ForgotpassAsynTask");
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
    protected Void doInBackground(Forgotpassword_input... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getForgotPasswordUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.forgotpassword_input.getAuthToken());
            httppost.addHeader(HeaderConstants.EMAIL, this.forgotpassword_input.getEmail());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.forgotpassword_input.getLang_code());

            Log.v("MUVISDK", "responseStr" + this.forgotpassword_input.getAuthToken());
            Log.v("MUVISDK", "responseStr" + this.forgotpassword_input.getEmail());
            Log.v("MUVISDK", "responseStr" + this.forgotpassword_input.getLang_code());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "responseStr" + responseStr);


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

                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    forgotpassword_output.setMsg(mainJson.optString("msg"));
                } else {
                    forgotpassword_output.setMsg("");
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
        //String pkg=context.getPackageName();
        // String s=forgotpassword_input.getPakagename();
        listener.onForgotpassDetailsPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onForgotpassDetailsPostExecuteCompleted(forgotpassword_output, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onForgotpassDetailsPostExecuteCompleted(forgotpassword_output, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onForgotpassDetailsPostExecuteCompleted(forgotpassword_output, status, message);
    }
}
