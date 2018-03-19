/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetInvoicePdfInputModel;
import com.home.apisdk.apiModel.GetInvoicePdfOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This Class generate Payment Invoice Pdf to help user to pay for the subscription.
 * This Class gives the details of the payment.
 *
 * @author MUVI
 */

public class GetInvoicePdfAsynTask extends AsyncTask<GetInvoicePdfInputModel, Void, Void> {

    private GetInvoicePdfInputModel getInvoicePdfInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private GetInvoicePdfOutputModel getInvoicePdfOutputModel;
    private GetInvoicePdfListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetInvoicePdfAsynTask to run some code when get
     * responses.
     */

    public interface GetInvoicePdfListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetInvoicePdfPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getInvoicePdfOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code                     Response Code From The Server
         * @param message                  On Success Message
         * @param status                   For Getting The Current Response
         */

        void onGetInvoicePdfPostExecuteCompleted(GetInvoicePdfOutputModel getInvoicePdfOutputModel, int code, String message, String status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param getInvoicePdfInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                For Example: to use this API we have to set following attributes:
     *                                setAuthToken(),setUser_id() etc.
     * @param listener                GetInvoicePdf Listener
     * @param context                 android.content.Context
     */

    public GetInvoicePdfAsynTask(GetInvoicePdfInputModel getInvoicePdfInputModel, GetInvoicePdfListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.getInvoicePdfInputModel = getInvoicePdfInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getinvoicepdf");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(GetInvoicePdfInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetInvoicePdfUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getInvoicePdfInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.ID, this.getInvoicePdfInputModel.getId());
            httppost.addHeader(HeaderConstants.USER_ID, this.getInvoicePdfInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.getInvoicePdfInputModel.getDevice_type());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.getInvoicePdfInputModel.getLang_code());
            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                message = "";
                status = "";

            } catch (IOException e) {
                code = 0;
                message = "";
                status = "";
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
                message = myJson.optString("status");
            }

            if (code == 200) {

                getInvoicePdfOutputModel = new GetInvoicePdfOutputModel();
                if ((myJson.has("section")) && myJson.optString("section").trim() != null && !myJson.optString("section").trim().isEmpty() && !myJson.optString("section").trim().equals("null") && !myJson.optString("section").trim().matches("")) {

                    getInvoicePdfOutputModel.setSection(myJson.optString("section"));

                }


            }
        } catch (Exception e) {
            code = 0;
            message = "";
            status = "";
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetInvoicePdfPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
        }


    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetInvoicePdfPostExecuteCompleted(getInvoicePdfOutputModel, code, message, status);
    }
}
