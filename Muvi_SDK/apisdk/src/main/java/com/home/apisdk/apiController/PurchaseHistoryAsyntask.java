/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.PurchaseHistoryInputModel;
import com.home.apisdk.apiModel.PurchaseHistoryOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class shows all the purchase history to the user whenever they needed.
 *
 * @author MUVI
 */

public class PurchaseHistoryAsyntask extends AsyncTask<PurchaseHistoryInputModel, Void, Void> {

    private PurchaseHistoryInputModel purchaseHistoryInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private int code;
    private PurchaseHistoryListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a PurchaseHistoryAsyntask to run some code when get
     * responses.
     */

    public interface PurchaseHistoryListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onPurchaseHistoryPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param purchaseHistoryOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                     Response Code from the server
         */

        void onPurchaseHistoryPostExecuteCompleted(ArrayList<PurchaseHistoryOutputModel> purchaseHistoryOutputModel, int status);
    }

    ArrayList<PurchaseHistoryOutputModel> purchaseHistoryOutputModel = new ArrayList<PurchaseHistoryOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param purchaseHistoryInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                  For Example: to use this API we have to set following attributes:
     *                                  setAuthToken(),setUser_id() etc.
     * @param listener                  PurchaseHistory Listener
     * @param context                   android.content.Context
     */

    public PurchaseHistoryAsyntask(PurchaseHistoryInputModel purchaseHistoryInputModel, PurchaseHistoryListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.purchaseHistoryInputModel = purchaseHistoryInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getPlanListAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(PurchaseHistoryInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getPurchaseHistoryUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.purchaseHistoryInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.purchaseHistoryInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.purchaseHistoryInputModel.getLang_code());
            httppost.addHeader(HeaderConstants.ID, this.purchaseHistoryInputModel.getId());


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
                message = myJson.optString("status");

            }

            if (code == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("section");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        PurchaseHistoryOutputModel content = new PurchaseHistoryOutputModel();


                        if ((jsonChildNode.has("currency_code")) && jsonChildNode.optString("currency_code").trim() != null && !jsonChildNode.optString("currency_code").trim().isEmpty() && !jsonChildNode.optString("currency_code").trim().equals("null") && !jsonChildNode.optString("currency_code").trim().matches("")) {
                            content.setCurrency_code(jsonChildNode.optString("currency_code"));

                        }
                        if ((jsonChildNode.has("currency_symbol")) && jsonChildNode.optString("currency_symbol").trim() != null && !jsonChildNode.optString("currency_symbol").trim().isEmpty() && !jsonChildNode.optString("currency_symbol").trim().equals("null") && !jsonChildNode.optString("currency_symbol").trim().matches("")) {
                            content.setCurrency_symbol(jsonChildNode.optString("currency_symbol"));
                        }
                        if ((jsonChildNode.has("amount")) && jsonChildNode.optString("amount").trim() != null && !jsonChildNode.optString("amount").trim().isEmpty() && !jsonChildNode.optString("amount").trim().equals("null") && !jsonChildNode.optString("amount").trim().matches("")) {
                            content.setAmount(jsonChildNode.optString("amount"));
                            if(content.getCurrency_symbol().equals("") || content.getCurrency_symbol()==null || content.getCurrency_symbol().trim().equals(null))
                            {
                                content.setAmount(content.getCurrency_code()+""+content.getAmount());
                            }
                            else
                            {
                                content.setAmount(content.getCurrency_symbol()+""+content.getAmount());
                            }
                        }
                        else {

                            if(content.getCurrency_symbol().equals("") || content.getCurrency_symbol()==null || content.getCurrency_symbol().trim().equals(null))
                            {
                                content.setAmount(content.getCurrency_code()+"0");
                            }
                            else
                            {
                                content.setAmount(content.getCurrency_symbol()+"0");
                            }
                           // content.setAmount("");
                        }
                        if ((jsonChildNode.has("id")) && jsonChildNode.optString("id").trim() != null && !jsonChildNode.optString("id").trim().isEmpty() && !jsonChildNode.optString("id").trim().equals("null") && !jsonChildNode.optString("id").trim().matches("")) {
                            content.setId(jsonChildNode.optString("id"));
                        }
                        if ((jsonChildNode.has("invoice_id")) && jsonChildNode.optString("invoice_id").trim() != null && !jsonChildNode.optString("invoice_id").trim().isEmpty() && !jsonChildNode.optString("invoice_id").trim().equals("null") && !jsonChildNode.optString("invoice_id").trim().matches("")) {
                            content.setInvoice_id(jsonChildNode.optString("invoice_id"));
                        }
                        if ((jsonChildNode.has("statusppv")) && jsonChildNode.optString("statusppv").trim() != null && !jsonChildNode.optString("statusppv").trim().isEmpty() && !jsonChildNode.optString("statusppv").trim().equals("null") && !jsonChildNode.optString("statusppv").trim().matches("")) {
                            content.setStatusppv(jsonChildNode.optString("statusppv"));
                        }
                        if ((jsonChildNode.has("transaction_date")) && jsonChildNode.optString("transaction_date").trim() != null && !jsonChildNode.optString("transaction_date").trim().isEmpty() && !jsonChildNode.optString("transaction_date").trim().equals("null") && !jsonChildNode.optString("transaction_date").trim().matches("")) {
                            content.setTransaction_date(jsonChildNode.optString("transaction_date"));
                        }
                        if ((jsonChildNode.has("transaction_status")) && jsonChildNode.optString("transaction_status").trim() != null && !jsonChildNode.optString("transaction_status").trim().isEmpty() && !jsonChildNode.optString("transaction_status").trim().equals("null") && !jsonChildNode.optString("transaction_status").trim().matches("")) {
                            content.setTransaction_status(jsonChildNode.optString("transaction_status"));
                        }


                        purchaseHistoryOutputModel.add(content);
                    } catch (Exception e) {
                        code = 0;
                        message = "";
                    }
                }
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
        listener.onPurchaseHistoryPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onPurchaseHistoryPostExecuteCompleted(purchaseHistoryOutputModel, code);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onPurchaseHistoryPostExecuteCompleted(purchaseHistoryOutputModel, code);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onPurchaseHistoryPostExecuteCompleted(purchaseHistoryOutputModel, code);
    }
}
