/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetVoucherPlanInputModel;
import com.home.apisdk.apiModel.GetVoucherPlanOutputModel;

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
 * This Class shows the Voucher plans to the user so that they can buy any voucher according to their need.
 *
 * @author MUVI
 */
public class GetVoucherPlanAsynTask extends AsyncTask<GetVoucherPlanInputModel, Void, Void> {

    private GetVoucherPlanInputModel getVoucherPlanInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetVoucherPlanListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetVoucherPlanAsynTask to run some code when get
     * responses.
     */

    public interface GetVoucherPlanListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetVoucherPlanPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getVoucherPlanOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                    Response Code From The Server
         * @param message                   On Success Message
         */

        void onGetVoucherPlanPostExecuteCompleted(GetVoucherPlanOutputModel getVoucherPlanOutputModel, int status, String message);
    }


    GetVoucherPlanOutputModel getVoucherPlanOutputModel = new GetVoucherPlanOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param getVoucherPlanInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                 For Example: to use this API we have to set following attributes:
     *                                 setAuthToken(),setMovie_id() etc.
     * @param listener                 GetVoucherPlan Listener
     * @param context                  android.content.Context
     */

    public GetVoucherPlanAsynTask(GetVoucherPlanInputModel getVoucherPlanInputModel, GetVoucherPlanListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getVoucherPlanInputModel = getVoucherPlanInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "get voucher plan");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(GetVoucherPlanInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetVoucherPlanUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getVoucherPlanInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.getVoucherPlanInputModel.getMovie_id());
            httppost.addHeader(HeaderConstants.STREAM_ID, this.getVoucherPlanInputModel.getStream_id());
            httppost.addHeader(HeaderConstants.SEASON, this.getVoucherPlanInputModel.getSeason());
            httppost.addHeader(HeaderConstants.USER_ID, this.getVoucherPlanInputModel.getUser_id());


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
                message = myJson.optString("status");
            }


            if (status == 200) {


                if(myJson.optString("is_show")!=null)
                {
                    if(!(myJson.optString("is_show").equals("")) && !(myJson.optString("is_show").equals("null"))
                            && (myJson.optString("is_show").trim().equals("1")))
                    {
                        getVoucherPlanOutputModel.setIs_show("1");
                    }
                    else
                    {
                        getVoucherPlanOutputModel.setIs_show("0");
                    }
                }
                else
                {
                    getVoucherPlanOutputModel.setIs_show("0");
                }

                // Checking for Season Purchase Type

                if(myJson.optString("is_season")!=null)
                {
                    if(!(myJson.optString("is_season").equals("")) && !(myJson.optString("is_season").equals("null"))
                            && (myJson.optString("is_season").trim().equals("1")))
                    {
                        getVoucherPlanOutputModel.setIs_season("1");
                    }
                    else
                    {
                        getVoucherPlanOutputModel.setIs_season("0");
                    }
                }
                else
                {
                    getVoucherPlanOutputModel.setIs_season("0");
                }

                // Checking for Episode Purchase Type

                if(myJson.optString("is_episode")!=null)
                {
                    if(!(myJson.optString("is_episode").equals("")) && !(myJson.optString("is_episode").equals("null"))
                            && (myJson.optString("is_episode").trim().equals("1")))
                    {
                        getVoucherPlanOutputModel.setIs_episode("1");
                    }
                    else
                    {
                        getVoucherPlanOutputModel.setIs_episode("0");
                    }
                }
                else
                {
                    getVoucherPlanOutputModel.setIs_episode("0");
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
        listener.onGetVoucherPlanPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetVoucherPlanPostExecuteCompleted(getVoucherPlanOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetVoucherPlanPostExecuteCompleted(getVoucherPlanOutputModel, status, message);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetVoucherPlanPostExecuteCompleted(getVoucherPlanOutputModel, status, message);

    }

    //}
}
