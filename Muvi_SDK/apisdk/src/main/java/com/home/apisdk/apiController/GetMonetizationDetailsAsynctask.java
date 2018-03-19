/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetMonetizationDetailsInputModel;
import com.home.apisdk.apiModel.GetMonetizationDetailsOutputModel;

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
 * This Class holds all the details of payment.
 * This gives monitization details and voucher details to the users.
 *
 * @author MUVI
 */
public class GetMonetizationDetailsAsynctask extends AsyncTask<GetMonetizationDetailsInputModel, Void, Void> {

    private GetMonetizationDetailsInputModel getMonetizationDetailsInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetMonetizationDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetMonetizationDetailsAsynctask to run some code when get
     * responses.
     */

    public interface GetMonetizationDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetMonetizationDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getMonetizationDetailsOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                            Response Code From The Server
         * @param message                           On Success Message
         */

        void onGetMonetizationDetailsPostExecuteCompleted(GetMonetizationDetailsOutputModel getMonetizationDetailsOutputModel, int status, String message);
    }


    GetMonetizationDetailsOutputModel getMonetizationDetailsOutputModel = new GetMonetizationDetailsOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param getMonetizationDetailsInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                         For Example: to use this API we have to set following attributes:
     *                                         setAuthToken(),setUser_id() etc.
     * @param listener                         GetMonetizationDetails Listener
     * @param context                          android.content.Context
     */

    public GetMonetizationDetailsAsynctask(GetMonetizationDetailsInputModel getMonetizationDetailsInputModel, GetMonetizationDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.getMonetizationDetailsInputModel = getMonetizationDetailsInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "transaction" + responseStr);


    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(GetMonetizationDetailsInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetMonetizationDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getMonetizationDetailsInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.getMonetizationDetailsInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.getMonetizationDetailsInputModel.getMovie_id());
            httppost.addHeader(HeaderConstants.PURCHASE_TYPE, this.getMonetizationDetailsInputModel.getPurchase_type());
            httppost.addHeader(HeaderConstants.STREAM_ID, this.getMonetizationDetailsInputModel.getStream_id());


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

            Log.v("MUVISDK", "response = " + responseStr);
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
            }

            if (status > 0) {

                if (status == 200) {

                    JSONObject mainJson = myJson.getJSONObject("monetization_plans");
                    if(!(mainJson.optString("voucher").equals("")) && !(mainJson.optString("voucher").equals("null"))
                            && (mainJson.optString("voucher").trim().equals("1")))
                    {
                        getMonetizationDetailsOutputModel.setVoucher(1);
                    }
                    else
                    {
                        getMonetizationDetailsOutputModel.setVoucher(0);
                    }


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
        listener.onGetMonetizationDetailsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetMonetizationDetailsPostExecuteCompleted(getMonetizationDetailsOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetMonetizationDetailsPostExecuteCompleted(getMonetizationDetailsOutputModel, status, message);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetMonetizationDetailsPostExecuteCompleted(getMonetizationDetailsOutputModel, status, message);

    }

    //}
}
