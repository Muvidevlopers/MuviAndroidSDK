/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.DeleteInvoicePdfInputModel;
import com.home.apisdk.apiModel.DeleteInvoicePdfOutputModel;

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
 * This Class delete the Invoice Pdf of the user.
 *
 * @author MUVI
 */

public class DeleteInvoicePdfAsynTask extends AsyncTask<DeleteInvoicePdfInputModel, Void, Void> {

    private DeleteInvoicePdfInputModel deleteInvoicePdfInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private DeleteInvoicePdfOutputModel deleteInvoicePdfOutputModel;
    private DeleteInvoicePdfListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a DeleteInvoicePdfAsynTask to run some code when get
     * responses.
     */

    public interface DeleteInvoicePdfListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onDeleteInvoicePdfPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param deleteInvoicePdfOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code                        Response Code From The Server
         * @param message                     On Success Message
         * @param status                      For Current Status
         */

        void onDeleteInvoicePdfPostExecuteCompleted(DeleteInvoicePdfOutputModel deleteInvoicePdfOutputModel, int code, String message, String status);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param deleteInvoicePdfInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                   For Example: to use this API we have to set following attributes:
     *                                   setAuthToken(),setFilepath() etc.
     * @param listener                   DeleteInvoicePdf Listener
     * @param context                    android.content.Context
     */

    public DeleteInvoicePdfAsynTask(DeleteInvoicePdfInputModel deleteInvoicePdfInputModel, DeleteInvoicePdfListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.deleteInvoicePdfInputModel = deleteInvoicePdfInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "deleteinvoicepdf");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(DeleteInvoicePdfInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getDeleteInvoicePdfUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.deleteInvoicePdfInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.FILE_PATH, this.deleteInvoicePdfInputModel.getFilepath());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.deleteInvoicePdfInputModel.getLanguage_code());

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

                message = myJson.optString("status");
            }

            if (code == 200) {

                deleteInvoicePdfOutputModel = new DeleteInvoicePdfOutputModel();
                deleteInvoicePdfOutputModel.setMsg(myJson.optString("msg"));

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
        listener.onDeleteInvoicePdfPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onDeleteInvoicePdfPostExecuteCompleted(deleteInvoicePdfOutputModel, code, message, status);
    }
}