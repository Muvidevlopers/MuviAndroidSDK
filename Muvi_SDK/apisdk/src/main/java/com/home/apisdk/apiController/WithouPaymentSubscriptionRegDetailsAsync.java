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
import com.home.apisdk.apiModel.WithouPaymentSubscriptionRegDetailsInput;

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
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * This Class allow to register user without paying any subscription charges.
 *
 * @author MUVI
 */

public class WithouPaymentSubscriptionRegDetailsAsync extends AsyncTask<WithouPaymentSubscriptionRegDetailsInput, Void, Void> {

    private WithouPaymentSubscriptionRegDetailsInput withouPaymentSubscriptionRegDetailsInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private WithouPaymentSubscriptionRegDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a WithouPaymentSubscriptionRegDetailsAsync to run some code when get
     * responses.
     */

    public interface WithouPaymentSubscriptionRegDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetWithouPaymentSubscriptionRegDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param status   Response Code from the server
         * @param Response For Getting The Response
         */

        void onGetWithouPaymentSubscriptionRegDetailsPostExecuteCompleted(int status, String Response);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param withouPaymentSubscriptionRegDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                                 For Example: to use this API we have to set following attributes:
     *                                                 setAuthToken(),setIs_advance() etc.
     * @param listener                                 WithouPaymentSubscriptionRegDetails Listener
     * @param context                                  android.content.Context
     */

    public WithouPaymentSubscriptionRegDetailsAsync(WithouPaymentSubscriptionRegDetailsInput withouPaymentSubscriptionRegDetailsInput, WithouPaymentSubscriptionRegDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.withouPaymentSubscriptionRegDetailsInput = withouPaymentSubscriptionRegDetailsInput;
        PACKAGE_NAME = context.getPackageName();

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(WithouPaymentSubscriptionRegDetailsInput... params) {


        try {

            URL url = new URL(APIUrlConstant.getAddSubscriptionUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.withouPaymentSubscriptionRegDetailsInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.IS_ADVANCE, this.withouPaymentSubscriptionRegDetailsInput.getIs_advance())
                    .appendQueryParameter(HeaderConstants.CARD_NAME, this.withouPaymentSubscriptionRegDetailsInput.getCard_name())
                    .appendQueryParameter(HeaderConstants.EXP_MONTH, this.withouPaymentSubscriptionRegDetailsInput.getExp_month())
                    .appendQueryParameter(HeaderConstants.CARD_NUMBER, this.withouPaymentSubscriptionRegDetailsInput.getCard_number())
                    .appendQueryParameter(HeaderConstants.EXP_YEAR, this.withouPaymentSubscriptionRegDetailsInput.getExp_year())
                    .appendQueryParameter(HeaderConstants.EMAIL, this.withouPaymentSubscriptionRegDetailsInput.getEmail())
                    .appendQueryParameter(HeaderConstants.MOVIE_ID, this.withouPaymentSubscriptionRegDetailsInput.getMovie_id())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.withouPaymentSubscriptionRegDetailsInput.getUser_id())
                    .appendQueryParameter(HeaderConstants.COUPON_CODE_WITHOUT_PAYMENT, this.withouPaymentSubscriptionRegDetailsInput.getCoupon_code())
                    .appendQueryParameter(HeaderConstants.CARD_TYPE, this.withouPaymentSubscriptionRegDetailsInput.getCard_type())
                    .appendQueryParameter(HeaderConstants.CARD_LAST_FOUR_DIGIT, this.withouPaymentSubscriptionRegDetailsInput.getCard_last_fourdigit())
                    .appendQueryParameter(HeaderConstants.PROFILE_ID, this.withouPaymentSubscriptionRegDetailsInput.getProfile_id())
                    .appendQueryParameter(HeaderConstants.TOKEN, this.withouPaymentSubscriptionRegDetailsInput.getToken())
                    .appendQueryParameter(HeaderConstants.CVV, this.withouPaymentSubscriptionRegDetailsInput.getCvv())
                    .appendQueryParameter(HeaderConstants.COUNTRY, this.withouPaymentSubscriptionRegDetailsInput.getCountry())
                    .appendQueryParameter(HeaderConstants.SEASON_ID, this.withouPaymentSubscriptionRegDetailsInput.getSeason_id())
                    .appendQueryParameter(HeaderConstants.EPISODE_ID, this.withouPaymentSubscriptionRegDetailsInput.getEpisode_id())
                    .appendQueryParameter(HeaderConstants.CURRENCY_ID, this.withouPaymentSubscriptionRegDetailsInput.getCurrency_id())
                    .appendQueryParameter(HeaderConstants.IS_SAVE_THIS_CARD, this.withouPaymentSubscriptionRegDetailsInput.getIs_save_this_card())
                    .appendQueryParameter(HeaderConstants.EXISTING_CARD_ID, this.withouPaymentSubscriptionRegDetailsInput.getExisting_card_id());
            String query = (builder.build().getEncodedQuery()).replaceAll("%40","@");
            responseStr = Utils.handleHttpAndHttpsRequest(url,query,status,message);


            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetWithouPaymentSubscriptionRegDetailsPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetWithouPaymentSubscriptionRegDetailsPostExecuteCompleted(status, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetWithouPaymentSubscriptionRegDetailsPostExecuteCompleted(status, responseStr);
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onGetWithouPaymentSubscriptionRegDetailsPostExecuteCompleted(status, responseStr);
    }
}
