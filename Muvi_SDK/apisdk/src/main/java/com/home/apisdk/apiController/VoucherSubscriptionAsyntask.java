/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.VoucherSubscriptionInputModel;
import com.home.apisdk.apiModel.VoucherSubscriptionOutputModel;

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
 * This Class helps in subscription of the voucher.
 *
 * @author MUVI
 */

public class VoucherSubscriptionAsyntask extends AsyncTask<VoucherSubscriptionInputModel, Void, Void> {

    private VoucherSubscriptionInputModel voucherSubscriptionInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private int code;
    private VoucherSubscriptionListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a VoucherSubscriptionAsyntask to run some code when get
     * responses.
     */

    public interface VoucherSubscriptionListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onVoucherSubscriptionPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param voucherSubscriptionOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                         Response Code from the server
         */

        void onVoucherSubscriptionPostExecuteCompleted(VoucherSubscriptionOutputModel voucherSubscriptionOutputModel, int status);
    }

    VoucherSubscriptionOutputModel voucherSubscriptionOutputModel = new VoucherSubscriptionOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param voucherSubscriptionInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                      For Example: to use this API we have to set following attributes:
     *                                      setAuthToken(),setUser_id() etc.
     * @param listener                      VoucherSubscriptionListener
     * @param context                       android.content.Context
     */

    public VoucherSubscriptionAsyntask(VoucherSubscriptionInputModel voucherSubscriptionInputModel, VoucherSubscriptionListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.voucherSubscriptionInputModel = voucherSubscriptionInputModel;
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
    protected Void doInBackground(VoucherSubscriptionInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getVoucherSubscriptionUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.voucherSubscriptionInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.voucherSubscriptionInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.voucherSubscriptionInputModel.getMovie_id());
            httppost.addHeader(HeaderConstants.STREAM_ID, this.voucherSubscriptionInputModel.getStream_id());
            httppost.addHeader(HeaderConstants.PURCHASE_TYPE, this.voucherSubscriptionInputModel.getPurchase_type());
            httppost.addHeader(HeaderConstants.VOUCHER_CODE, this.voucherSubscriptionInputModel.getVoucher_code());
            httppost.addHeader(HeaderConstants.IS_PREORDER, this.voucherSubscriptionInputModel.getIs_preorder());
            httppost.addHeader(HeaderConstants.SEASON, this.voucherSubscriptionInputModel.getSeason());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.voucherSubscriptionInputModel.getLanguage());

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

            }

            if ((myJson.has("msg")) && myJson.optString("msg").trim() != null && !myJson.optString("msg").trim().isEmpty() && !myJson.optString("msg").trim().equals("null") && !myJson.optString("msg").trim().matches("")) {
                voucherSubscriptionOutputModel.setMsg(myJson.optString("msg"));
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
        listener.onVoucherSubscriptionPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            listener.onVoucherSubscriptionPostExecuteCompleted(voucherSubscriptionOutputModel, code);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            listener.onVoucherSubscriptionPostExecuteCompleted(voucherSubscriptionOutputModel, code);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onVoucherSubscriptionPostExecuteCompleted(voucherSubscriptionOutputModel, code);
    }
}
