package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.MonitizationDetailsInput;
import com.home.apisdk.apiModel.MonitizationDetailsOutput;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by BISHAL on 16-11-2017.
 */

public class GetMonitizationDetailsAsync extends AsyncTask<MonitizationDetailsInput,Void,Void> {
    private GetMonitizationDetailsListner listener;
    private Context context;
    private MonitizationDetailsInput monitizationDetailsInput;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
   // public MonitizationDetailsOutput monitizationDetailsOutput;

    public interface GetMonitizationDetailsListner{
        void onGetMonitizationDetailsPreExecuteStarted();
        void onGetMonitizationDetailsPostExecuteStarted(MonitizationDetailsOutput monitizationDetailsOutput, int code, String status, String message);

    }

    MonitizationDetailsOutput monitizationDetailsOutput=new MonitizationDetailsOutput();

    public GetMonitizationDetailsAsync(MonitizationDetailsInput monitizationDetailsInput, GetMonitizationDetailsListner listener, Context context){
        this.listener=listener;
        this.context = context;
        this.monitizationDetailsInput=monitizationDetailsInput;
        PACKAGE_NAME = context.getPackageName();
    }

    @Override
    protected Void doInBackground(MonitizationDetailsInput... params) {

        try {
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetMonetizationDetailsUrl());
            Log.v("MUVI","Url=="+APIUrlConstant.getGetMonetizationDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader("authToken", monitizationDetailsInput.getAuthToken());
            httppost.addHeader("user_id", monitizationDetailsInput.getUser_id());
            httppost.addHeader("movie_id",monitizationDetailsInput.getMovie_id());
            httppost.addHeader("stream_id", monitizationDetailsInput.getStream_id());
            httppost.addHeader("purchase_type", monitizationDetailsInput.getPurchase_type());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());

            } catch (final org.apache.http.conn.ConnectTimeoutException e){
                code = 0;
                message = "";
                status = "";
            } catch (IOException e) {
                code = 0;
                message = "";
                status = "";
                e.printStackTrace();
            }
            JSONObject myJson = null;
            if(responseStr!=null){
                myJson = new JSONObject(responseStr);
                status = myJson.optString("status");
                code = Integer.parseInt(myJson.optString("code"));
                message=myJson.optString("msg");
                /*monitizationDetailsOutput.setMovie_id(myJson.optString("movie_id"));
                monitizationDetailsOutput.setStream_id(myJson.optString("stream_id"));
                monitizationDetailsOutput.setContent_types_id(myJson.optString("content_types_id"));*/
            }
            if (code==200){
                JSONObject jsonObject= myJson.optJSONObject("monetization_plans");
                if(!(jsonObject.optString("voucher").equals("")) && !(jsonObject.optString("voucher").equals("null"))
                        && (jsonObject.optString("voucher").trim().equals("1")))
                {
                    monitizationDetailsOutput.setVoucher("1");
                }
                else
                {
                    monitizationDetailsOutput.setVoucher("0");
                }
                if ((jsonObject.has("ppv")) && jsonObject.optString("ppv").trim() != null && !jsonObject.optString("ppv").trim().isEmpty() && !jsonObject.optString("ppv").trim().equals("null") && !jsonObject.optString("ppv").trim().matches("")) {
                   monitizationDetailsOutput.setPpv(jsonObject.optString("ppv"));
                }
                else {
                    monitizationDetailsOutput.setPpv(jsonObject.optString(""));
                }
                if ((jsonObject.has("pre_order")) && jsonObject.optString("pre_order").trim() != null && !jsonObject.optString("pre_order").trim().isEmpty() && !jsonObject.optString("pre_order").trim().equals("null") && !jsonObject.optString("pre_order").trim().matches("")) {
                    monitizationDetailsOutput.setPre_order(jsonObject.optString("pre_order"));
                }
                else {
                    monitizationDetailsOutput.setPre_order(jsonObject.optString(""));
                }
                if ((jsonObject.has("subscription_bundles")) && jsonObject.optString("subscription_bundles").trim() != null && !jsonObject.optString("subscription_bundles").trim().isEmpty() && !jsonObject.optString("subscription_bundles").trim().equals("null") && !jsonObject.optString("subscription_bundles").trim().matches("")) {
                    monitizationDetailsOutput.setSubscription_bundles(jsonObject.optString("subscription_bundles"));
                }
                else {
                    monitizationDetailsOutput.setSubscription_bundles(jsonObject.optString(""));
                }
                if ((jsonObject.has("ppv_bundle")) && jsonObject.optString("ppv_bundle").trim() != null && !jsonObject.optString("ppv_bundle").trim().isEmpty() && !jsonObject.optString("ppv_bundle").trim().equals("null") && !jsonObject.optString("ppv_bundle").trim().matches("")) {
                    monitizationDetailsOutput.setPpv_bundle(jsonObject.optString("ppv_bundle"));
                }
                else {
                    monitizationDetailsOutput.setPpv_bundle(jsonObject.optString(""));
                }
            }
        }
        catch (Exception e) {
            code = 0;
            message = "";
            status = "";
            Log.v("MUVI","exception=="+e);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onGetMonitizationDetailsPreExecuteStarted();
        responseStr = "0";
        code=0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetMonitizationDetailsPostExecuteStarted(monitizationDetailsOutput, code, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetMonitizationDetailsPostExecuteStarted(monitizationDetailsOutput, code, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onGetMonitizationDetailsPostExecuteStarted(monitizationDetailsOutput, code, status, message);

    }
}
