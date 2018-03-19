/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.CheckDeviceInput;
import com.home.apisdk.apiModel.CheckDeviceOutput;

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
 * This Class checks all the information about the device on which the application is running.
 * With the help of this Class we can get the device name, device type, device Id etc.
 *
 * @author MUVI
 */

public class CheckDeviceAsyncTask extends AsyncTask<Void, Void, Void> {

    private CheckDeviceInput checkDeviceInput;
    private String PACKAGE_NAME;
    private String message = "";
    private String responseStr;
    private int code;
    private JSONObject myJson = null;
    private CheckDeviceOutput checkDeviceOutput;
    private CheckDeviceListener listener;
    private Context context;


    /**
     * Interface used to allow the caller of a CheckDeviceAsyncTask to run some code when get
     * responses.
     */

    public interface CheckDeviceListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onCheckDevicePreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param checkDeviceOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code              Response Code From the Server
         * @param message           On Success Message
         */

        void onCheckDevicePostExecuteCompleted(CheckDeviceOutput checkDeviceOutput, int code, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param checkDeviceInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                         For Example: to use this API we have to set following attributes:
     *                         setAuthToken(),setUser_id() etc.
     * @param listener         CheckDevice Listener
     * @param context          android.content.Context
     */

    public CheckDeviceAsyncTask(CheckDeviceInput checkDeviceInput, CheckDeviceListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.checkDeviceInput = checkDeviceInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetUserProfileAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return  null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */
    @Override
    protected Void doInBackground(Void... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getCheckDevice());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.USER_ID, this.checkDeviceInput.getUser_id());
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.checkDeviceInput.getAuthToken());
            httppost.addHeader(HeaderConstants.DEVICE, this.checkDeviceInput.getDevice());
            httppost.addHeader(HeaderConstants.GOOGLE_ID, this.checkDeviceInput.getGoogle_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.checkDeviceInput.getDevice_type());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.checkDeviceInput.getLang_code());
            httppost.addHeader(HeaderConstants.DEVICE_INFO, this.checkDeviceInput.getDevice_info());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);
            } catch (ClientProtocolException e) {
                code = 0;
                e.printStackTrace();
            } catch (IOException e) {
                code = 0;
                e.printStackTrace();
            }

            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
            }

        } catch (JSONException e) {
            code = 0;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onCheckDevicePreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onCheckDevicePostExecuteCompleted(checkDeviceOutput, code, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onCheckDevicePostExecuteCompleted(checkDeviceOutput, code, message);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onCheckDevicePostExecuteCompleted(checkDeviceOutput, code, message);
    }
}