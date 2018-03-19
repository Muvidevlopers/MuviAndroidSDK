/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.ValidateCouponCodeInputModel;
import com.home.apisdk.apiModel.ValidateCouponCodeOutputModel;

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
 * This Class checks whether coupon code entered by the user is valid or invalid.
 *
 * @author MUVI
 */


public class ValidateCouponCodeAsynTask extends AsyncTask<ValidateCouponCodeInputModel, Void, Void> {

    private ValidateCouponCodeInputModel validateCouponCodeInputModel;
    private String responseStr;
    private int status;
    private float planPrice = 0.0f;
    private float chargedPrice = 0.0f;
    private float previousChargedPrice = 0.0f;
    private String message;
    private String PACKAGE_NAME;
    private ValidateCouponCodeLIstener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a ValidateCouponCodeAsynTask to run some code when get
     * responses.
     */

    public interface ValidateCouponCodeLIstener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onValidateCouponCodePreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param validateCouponCodeOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                        Response Code from the server
         * @param message                       On Success Message
         */

        void onValidateCouponCodePostExecuteCompleted(ValidateCouponCodeOutputModel validateCouponCodeOutputModel, int status, String message);
    }

    ValidateCouponCodeOutputModel validateCouponCodeOutputModel = new ValidateCouponCodeOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param validateCouponCodeInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                     For Example: to use this API we have to set following attributes:
     *                                     setAuthToken(),setCouponCode() etc.
     * @param listener                     ValidateCouponCode LIstener
     * @param context                      android.content.Context
     */

    public ValidateCouponCodeAsynTask(ValidateCouponCodeInputModel validateCouponCodeInputModel, ValidateCouponCodeLIstener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.validateCouponCodeInputModel = validateCouponCodeInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "validate voucher");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(ValidateCouponCodeInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getValidateCouponCodeUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.validateCouponCodeInputModel.getAuthToken());

            httppost.addHeader(HeaderConstants.USER_ID, this.validateCouponCodeInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.COUPAN_CODE, this.validateCouponCodeInputModel.getCouponCode());
            httppost.addHeader(HeaderConstants.CURRENCY_ID, this.validateCouponCodeInputModel.getCurrencyId());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                message = "";

            } catch (IOException e) {
                status = 0;
                message = "";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
            }


            if ((myJson.has("discount_type")) &&
                    myJson.optString("discount_type").trim() != null &&
                    !myJson.optString("discount_type").trim().isEmpty() &&
                    !myJson.optString("discount_type").trim().equals("null") &&
                    !myJson.optString("discount_type").trim().matches("")) {

                String discountTypeStr = myJson.optString("discount_type").trim();
                validateCouponCodeOutputModel.setDiscount_type(myJson.optString("discount_type").trim());

                if ((myJson.has("discount")) && myJson.optString("discount").trim() != null && !myJson.optString("discount").trim().isEmpty() && !myJson.optString("discount").trim().equals("null") && !myJson.optString("discount").trim().matches("")) {

                    validateCouponCodeOutputModel.setDiscount(myJson.optString("discount").trim());
                }
            }


        } catch (Exception e) {
            status = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onValidateCouponCodePreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onValidateCouponCodePostExecuteCompleted(validateCouponCodeOutputModel, status, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onValidateCouponCodePostExecuteCompleted(validateCouponCodeOutputModel, status, responseStr);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onValidateCouponCodePostExecuteCompleted(validateCouponCodeOutputModel, status, message);

    }

    //}
}
