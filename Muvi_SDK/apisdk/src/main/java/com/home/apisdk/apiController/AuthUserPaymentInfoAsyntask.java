/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.AuthUserPaymentInfoInputModel;
import com.home.apisdk.apiModel.AuthUserPaymentInfoOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class shows the payment information of the users who logs in using Social Media platforms such as Facebook.
 *
 * @author MUVI
 */

public class AuthUserPaymentInfoAsyntask extends AsyncTask<AuthUserPaymentInfoInputModel, Void, Void> {

    private AuthUserPaymentInfoInputModel authUserPaymentInfoInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String responseMessageStr;
    private int code;
    private int status;
    private AuthUserPaymentInfoListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a AuthUserPaymentInfoAsyntask to run some code when get
     * responses.
     */

    public interface AuthUserPaymentInfoListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onAuthUserPaymentInfoPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param authUserPaymentInfoOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                         Response Code from the server
         * @param message                        Message Response
         */

        void onAuthUserPaymentInfoPostExecuteCompleted(AuthUserPaymentInfoOutputModel authUserPaymentInfoOutputModel, int status, String message);
    }


    AuthUserPaymentInfoOutputModel authUserPaymentInfoOutputModel = new AuthUserPaymentInfoOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param authUserPaymentInfoInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                      For Example: to use this API we have to set following attributes:
     *                                      setAuthToken(),setName_on_card() etc.
     * @param listener                      AuthUserPaymentInfo Listener
     * @param context                       android.content.Context
     */

    public AuthUserPaymentInfoAsyntask(AuthUserPaymentInfoInputModel authUserPaymentInfoInputModel, AuthUserPaymentInfoListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.authUserPaymentInfoInputModel = authUserPaymentInfoInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "register user payment");
    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(AuthUserPaymentInfoInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getAuthUserPaymentInfoUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.authUserPaymentInfoInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.NAME_ON_CARD, this.authUserPaymentInfoInputModel.getName_on_card());
            httppost.addHeader(HeaderConstants.EXPIRY_MONTH, this.authUserPaymentInfoInputModel.getExpiryMonth());
            httppost.addHeader(HeaderConstants.EXPIRY_YEAR, this.authUserPaymentInfoInputModel.getExpiryYear());
            httppost.addHeader(HeaderConstants.CARD_NUMBER, this.authUserPaymentInfoInputModel.getCardNumber());
            httppost.addHeader(HeaderConstants.CVV, this.authUserPaymentInfoInputModel.getCvv().trim());
            httppost.addHeader(HeaderConstants.EMAIL, this.authUserPaymentInfoInputModel.getEmail().trim());


            // ************************ Added Later *************************** //

            httppost.addHeader(HeaderConstants.PLAN_ID, this.authUserPaymentInfoInputModel.getPlan_id());

            // ************************ Added Later *************************** //


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
                code = Integer.parseInt(myJson.optString("isSuccess"));

            }

            if (code == 1) {


                if (myJson.has("card")) {
                    JSONObject mainJson = null;
                    mainJson = myJson.getJSONObject("card");
                    if (mainJson.has("status") && mainJson.optString("status").trim() != null && !mainJson.optString("status").trim().isEmpty() && !mainJson.optString("status").trim().equals("null") && !mainJson.optString("status").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setStatus(mainJson.optString("status"));
                    } else {
                        authUserPaymentInfoOutputModel.setStatus("");
                    }

                    if (mainJson.has("token") && mainJson.optString("token").trim() != null && !mainJson.optString("token").trim().isEmpty() && !mainJson.optString("token").trim().equals("null") && !mainJson.optString("token").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setToken(mainJson.optString("token"));
                    } else {
                        authUserPaymentInfoOutputModel.setToken("");
                    }

                    if (mainJson.has("response_text") && mainJson.optString("response_text").trim() != null && !mainJson.optString("response_text").trim().isEmpty() && !mainJson.optString("response_text").trim().equals("null") && !mainJson.optString("response_text").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setResponse_text(mainJson.optString("response_text"));
                    } else {
                        authUserPaymentInfoOutputModel.setResponse_text("");
                    }

                    if (mainJson.has("profile_id") && mainJson.optString("profile_id").trim() != null && !mainJson.optString("profile_id").trim().isEmpty() && !mainJson.optString("profile_id").trim().equals("null") && !mainJson.optString("profile_id").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setProfile_id(mainJson.optString("profile_id"));
                    } else {
                        authUserPaymentInfoOutputModel.setProfile_id("");
                    }
                    if (mainJson.has("card_last_fourdigit") && mainJson.optString("card_last_fourdigit").trim() != null && !mainJson.optString("card_last_fourdigit").trim().isEmpty() && !mainJson.optString("card_last_fourdigit").trim().equals("null") && !mainJson.optString("card_last_fourdigit").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setCard_last_fourdigit(mainJson.optString("card_last_fourdigit"));
                    } else {
                        authUserPaymentInfoOutputModel.setCard_last_fourdigit("");
                    }

                    if (mainJson.has("card_type") && mainJson.optString("card_type").trim() != null && !mainJson.optString("card_type").trim().isEmpty() && !mainJson.optString("card_type").trim().equals("null") && !mainJson.optString("card_type").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setCard_type(mainJson.optString("card_type"));
                    } else {
                        authUserPaymentInfoOutputModel.setCard_type("");
                    }

                }
                    // ************************ Added Later *************************** //


                    if (myJson.has("transaction_invoice_id") && myJson.optString("transaction_invoice_id").trim() != null && !myJson.optString("transaction_invoice_id").trim().isEmpty() && !myJson.optString("transaction_invoice_id").trim().equals("null") && !myJson.optString("transaction_invoice_id").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_invoice_id(myJson.optString("transaction_invoice_id"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_invoice_id("");
                    }
                    if (myJson.has("transaction_order_number") && myJson.optString("transaction_order_number").trim() != null && !myJson.optString("transaction_order_number").trim().isEmpty() && !myJson.optString("transaction_order_number").trim().equals("null") && !myJson.optString("transaction_order_number").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_order_number(myJson.optString("transaction_order_number"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_order_number("");
                    }
                    if (myJson.has("transaction_dollar_amount") && myJson.optString("transaction_dollar_amount").trim() != null && !myJson.optString("transaction_dollar_amount").trim().isEmpty() && !myJson.optString("transaction_dollar_amount").trim().equals("null") && !myJson.optString("transaction_dollar_amount").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_dollar_amount(myJson.optString("transaction_dollar_amount"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_dollar_amount("");
                    }
                    if (myJson.has("transaction_amount") && myJson.optString("transaction_amount").trim() != null && !myJson.optString("transaction_amount").trim().isEmpty() && !myJson.optString("transaction_amount").trim().equals("null") && !myJson.optString("transaction_amount").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_amount(myJson.optString("transaction_amount"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_amount("");
                    }
                    if (myJson.has("transaction_response_text") && myJson.optString("transaction_response_text").trim() != null && !myJson.optString("transaction_response_text").trim().isEmpty() && !myJson.optString("transaction_response_text").trim().equals("null") && !myJson.optString("transaction_response_text").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_response_text(myJson.optString("transaction_response_text"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_response_text("");
                    }
                    if (myJson.has("transaction_is_success") && myJson.optString("transaction_is_success").trim() != null && !myJson.optString("transaction_is_success").trim().isEmpty() && !myJson.optString("transaction_is_success").trim().equals("null") && !myJson.optString("transaction_is_success").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_is_success(myJson.optString("transaction_is_success"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_is_success("");
                    }
                    if (myJson.has("transaction_status") && myJson.optString("transaction_status").trim() != null && !myJson.optString("transaction_status").trim().isEmpty() && !myJson.optString("transaction_status").trim().equals("null") && !myJson.optString("transaction_status").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setTransaction_status(myJson.optString("transaction_status"));
                    }else {
                        authUserPaymentInfoOutputModel.setTransaction_status("");
                    }
                    if (myJson.has("isSuccess") && myJson.optString("isSuccess").trim() != null && !myJson.optString("isSuccess").trim().isEmpty() && !myJson.optString("isSuccess").trim().equals("null") && !myJson.optString("isSuccess").trim().matches("")) {
                        authUserPaymentInfoOutputModel.setIsSuccess(myJson.optString("isSuccess"));
                    }else {
                        authUserPaymentInfoOutputModel.setIsSuccess("");
                    }


                    // ************************ Added Later *************************** //



            }
            if (code == 0) {
                if (myJson.has("Message")) {
                    responseMessageStr = myJson.optString("Message");
                }
                if (((responseMessageStr.equalsIgnoreCase("null")) || (responseMessageStr.length() <= 0))) {
                    responseMessageStr = "No Details found";

                }
            }


        } catch (
                Exception e)


        {
            code = 0;
            message = "";
            responseMessageStr = message;

        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onAuthUserPaymentInfoPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            listener.onAuthUserPaymentInfoPostExecuteCompleted(authUserPaymentInfoOutputModel, code, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            listener.onAuthUserPaymentInfoPostExecuteCompleted(authUserPaymentInfoOutputModel, code, message);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onAuthUserPaymentInfoPostExecuteCompleted(authUserPaymentInfoOutputModel, code, responseMessageStr);
    }
}
