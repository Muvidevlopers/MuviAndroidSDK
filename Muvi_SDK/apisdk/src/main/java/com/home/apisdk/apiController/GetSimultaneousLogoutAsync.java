/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.SimultaneousLogoutInput;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by MUVI on 7/4/2017.
 * Class to get Simultaneous Logout details.
 */

public class GetSimultaneousLogoutAsync extends AsyncTask<SimultaneousLogoutInput, Void, Void> {

    private SimultaneousLogoutInput simultaneousLogoutInput;
    private String PACKAGE_NAME;
    private String responseStr;
    private String message;
    private int code;
    private SimultaneousLogoutAsyncListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetSimultaneousLogoutAsync to run some code when get
     * responses.
     */

    public interface SimultaneousLogoutAsyncListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onSimultaneousLogoutPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param code Response Code From The Server
         */

        void onSimultaneousLogoutPostExecuteCompleted(int code);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param simultaneousLogoutInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                For Example: to use this API we have to set following attributes:
     *                                setAuthToken(),setDevice_type() etc.
     * @param listener                SimultaneousLogoutAsync Listener
     * @param context                 android.content.Context
     */


    public GetSimultaneousLogoutAsync(SimultaneousLogoutInput simultaneousLogoutInput, SimultaneousLogoutAsyncListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.simultaneousLogoutInput = simultaneousLogoutInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "LogoutAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(SimultaneousLogoutInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getLogoutAll());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.simultaneousLogoutInput.getAuthToken());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.simultaneousLogoutInput.getDevice_type());
            httppost.addHeader(HeaderConstants.EMAIL_ID, this.simultaneousLogoutInput.getEmail_id());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (ClientProtocolException e) {
                responseStr = "0";
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onSimultaneousLogoutPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onSimultaneousLogoutPostExecuteCompleted(code);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onSimultaneousLogoutPostExecuteCompleted(code);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onSimultaneousLogoutPostExecuteCompleted(code);
    }
}
