/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.RegisterUserPaymentInputModel;
import com.home.apisdk.apiModel.RegisterUserPaymentOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class gives the details of the Pay-per-view payment to the users.
 *
 * @author MUVI
 */

public class GetPPVPaymentAsync extends AsyncTask<RegisterUserPaymentInputModel, Void, Void> {

    private RegisterUserPaymentInputModel registerUserPaymentInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private int code;
    private GetPPVPaymentListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetPPVPaymentAsync to run some code when get
     * responses.
     */

    public interface GetPPVPaymentListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetPPVPaymentPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param registerUserPaymentOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                         Response Code From The Server
         */

        void onGetPPVPaymentPostExecuteCompleted(RegisterUserPaymentOutputModel registerUserPaymentOutputModel, int status, String response);
    }

    RegisterUserPaymentOutputModel registerUserPaymentOutputModel = new RegisterUserPaymentOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param registerUserPaymentInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                      For Example: to use this API we have to set following attributes:
     *                                      setAuthToken(),setUser_id() etc.
     * @param listener                      GetPPVPayment Listener
     * @param context                       android.content.Context
     */

    public GetPPVPaymentAsync(RegisterUserPaymentInputModel registerUserPaymentInputModel, GetPPVPaymentListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.registerUserPaymentInputModel = registerUserPaymentInputModel;
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
    protected Void doInBackground(RegisterUserPaymentInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getAddSubscriptionUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.registerUserPaymentInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.registerUserPaymentInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.CARD_LAST_FOUR_DIGIT, this.registerUserPaymentInputModel.getCard_last_fourdigit());
            httppost.addHeader(HeaderConstants.CARD_NAME, this.registerUserPaymentInputModel.getCard_name());
            httppost.addHeader(HeaderConstants.CARD_NUMBER, this.registerUserPaymentInputModel.getCard_number());
            httppost.addHeader(HeaderConstants.CARD_TYPE, this.registerUserPaymentInputModel.getCard_type());
            httppost.addHeader(HeaderConstants.COUNTRY, this.registerUserPaymentInputModel.getCountry());
            httppost.addHeader(HeaderConstants.CURRENCY_ID, this.registerUserPaymentInputModel.getCurrency_id());
            httppost.addHeader(HeaderConstants.CVV, this.registerUserPaymentInputModel.getCvv());
            httppost.addHeader(HeaderConstants.EMAIL, this.registerUserPaymentInputModel.getEmail());
            httppost.addHeader(HeaderConstants.EPISODE_ID, this.registerUserPaymentInputModel.getEpisode_id());
            httppost.addHeader(HeaderConstants.EXP_MONTH, this.registerUserPaymentInputModel.getExp_month());
            httppost.addHeader(HeaderConstants.EXP_YEAR, this.registerUserPaymentInputModel.getExp_year());
            httppost.addHeader(HeaderConstants.SEASON_ID, this.registerUserPaymentInputModel.getSeason_id());
            httppost.addHeader(HeaderConstants.PROFILE_ID, this.registerUserPaymentInputModel.getProfile_id());
            httppost.addHeader(HeaderConstants.TOKEN, this.registerUserPaymentInputModel.getToken());
            httppost.addHeader(HeaderConstants.COUPAN_CODE, this.registerUserPaymentInputModel.getCouponCode());
            httppost.addHeader(HeaderConstants.IS_SAVE_THIS_CARD, this.registerUserPaymentInputModel.getIs_save_this_card());
            httppost.addHeader(HeaderConstants.IS_ADVANCE, this.registerUserPaymentInputModel.getIs_advance());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.registerUserPaymentInputModel.getMovie_id());


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

//            if (code == 200) {
//                if ((myJson.has("msg")) && myJson.optString("msg").trim() != null && !myJson.optString("msg").trim().isEmpty() && !myJson.optString("msg").trim().equals("null") && !myJson.optString("msg").trim().matches("")) {
//                    registerUserPaymentOutputModel.setMsg(myJson.optString("msg"));
//                }
//
//            }

        } catch (Exception e) {
            code = 0;
            message = "";

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetPPVPaymentPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetPPVPaymentPostExecuteCompleted(registerUserPaymentOutputModel, code, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetPPVPaymentPostExecuteCompleted(registerUserPaymentOutputModel, code, responseStr);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onGetPPVPaymentPostExecuteCompleted(registerUserPaymentOutputModel, code, responseStr);
    }
}

