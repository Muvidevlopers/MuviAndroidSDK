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
import com.home.apisdk.apiModel.ValidateUserInput;
import com.home.apisdk.apiModel.ValidateUserOutput;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This Class checks whether the user is validate or not.
 * If user is not validate then it won't allow that user to see any video.
 *
 * @author MUVI
 */
public class GetValidateUserAsynTask extends AsyncTask<ValidateUserInput, Void, Void> {

    private ValidateUserInput validateUserInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private String validuser_str;
    private String isSubscribed;
    private GetValidateUserListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetValidateUserAsynTask to run some code when get
     * responses.
     */

    public interface GetValidateUserListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetValidateUserPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param validateUserOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status             Response Code From The Server
         * @param message            On Success Message
         */

        void onGetValidateUserPostExecuteCompleted(ValidateUserOutput validateUserOutput, int status, String message);
    }


    ValidateUserOutput validateUserOutput = new ValidateUserOutput();

    /**
     * Constructor to initialise the private data members.
     *
     * @param validateUserInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                          For Example: to use this API we have to set following attributes:
     *                          setAuthToken(),setMuviUniqueId() etc.
     * @param listener          GetValidateUser Listener
     * @param context           android.content.Context
     */

    public GetValidateUserAsynTask(ValidateUserInput validateUserInput, GetValidateUserListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.validateUserInput = validateUserInput;
        PACKAGE_NAME = context.getPackageName();

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(ValidateUserInput... params) {


        try {

            // Execute HTTP Post Request

            URL url = new URL(APIUrlConstant.getValidateUserForContentUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.validateUserInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.validateUserInput.getUserId())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.validateUserInput.getMuviUniqueId())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.validateUserInput.getEpisodeStreamUniqueId())
                    .appendQueryParameter(HeaderConstants.SEASON_ID, this.validateUserInput.getSeasonId())
                    .appendQueryParameter(HeaderConstants.LANG_CODE, this.validateUserInput.getLanguageCode())
                    .appendQueryParameter(HeaderConstants.PURCHASE_TYPE, this.validateUserInput.getPurchaseType());
            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);

            JSONObject mainJson = null;
            if (responseStr != null) {
                mainJson = new JSONObject(responseStr);
                status = Integer.parseInt(mainJson.optString("code"));
                if ((mainJson.has("msg")) && mainJson.optString("msg").trim() != null && !mainJson.optString("msg").trim().isEmpty() && !mainJson.optString("msg").trim().equals("null") && !mainJson.optString("msg").trim().matches("")) {
                    validateUserOutput.setMessage(mainJson.optString("msg"));
                    message = mainJson.optString("msg");

                }
                if ((mainJson.has("member_subscribed")) && mainJson.optString("member_subscribed").trim() != null && !mainJson.optString("member_subscribed").trim().isEmpty() && !mainJson.optString("member_subscribed").trim().equals("null") && !mainJson.optString("member_subscribed").trim().matches("")) {
                    validateUserOutput.setIsMemberSubscribed(mainJson.optString("member_subscribed"));
                    isSubscribed = mainJson.optString("member_subscribed");

                }

                if ((mainJson.has("status")) && mainJson.optString("status").trim() != null && !mainJson.optString("status").trim().isEmpty() && !mainJson.optString("status").trim().equals("null") && !mainJson.optString("status").trim().matches("")) {
                    validateUserOutput.setValiduser_str(mainJson.optString("status"));
                    validuser_str = mainJson.optString("status");

                }
            } else {
                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {
            Log.v("MUVISDK", "JSONException" + e1);

            responseStr = "0";
            status = 0;
            message = "Error";
        } catch (Exception e) {
            Log.v("MUVISDK", "Exception" + e);

            responseStr = "0";
            status = 0;
            message = "Error";
        }
        return null;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetValidateUserPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetValidateUserPostExecuteCompleted(validateUserOutput, status, message);

    }

}
