/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.Utils;
import com.home.apisdk.apiModel.LoadRegisteredDevicesInput;
import com.home.apisdk.apiModel.LoadRegisteredDevicesOutput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * This Class Loads the details of the registered device
 *
 * @author MUVI
 */

public class LoadRegisteredDevicesAsync extends AsyncTask<LoadRegisteredDevicesInput, Void, Void> {

    private LoadRegisteredDevicesInput loadRegisteredDevicesInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private LoadRegisteredDevicesListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a LoadRegisteredDevicesAsync to run some code when get
     * responses.
     */

    public interface LoadRegisteredDevicesListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onLoadRegisteredDevicesPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param loadRegisteredDevicesOutputs A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                       Response Code from the server
         * @param message                      On Success Message
         */

        void onLoadRegisteredDevicesPostExecuteCompleted(ArrayList<LoadRegisteredDevicesOutput> loadRegisteredDevicesOutputs, int status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param loadRegisteredDevicesInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                   For Example: to use this API we have to set following attributes:
     *                                   setAuthToken(),setUser_id() etc.
     * @param listener                   LoadRegisteredDevices Listener
     * @param context                    android.content.Context
     */

    public LoadRegisteredDevicesAsync(LoadRegisteredDevicesInput loadRegisteredDevicesInput, LoadRegisteredDevicesListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.loadRegisteredDevicesInput = loadRegisteredDevicesInput;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    ArrayList<LoadRegisteredDevicesOutput> loadRegisteredDevicesOutputArrayList = new ArrayList<LoadRegisteredDevicesOutput>();

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(LoadRegisteredDevicesInput... params) {

        try {


            URL url = new URL(APIUrlConstant.getManageDevices());

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.loadRegisteredDevicesInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.USER_ID, this.loadRegisteredDevicesInput.getUser_id())
                    .appendQueryParameter(HeaderConstants.DEVICE, this.loadRegisteredDevicesInput.getDevice())
                    .appendQueryParameter(HeaderConstants.LANG_CODE, this.loadRegisteredDevicesInput.getLang_code());
            String query = builder.build().getEncodedQuery();
            responseStr = Utils.handleHttpAndHttpsRequest(url, query, status, message);

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                if (myJson.has("msg")) {
                    message = myJson.optString("msg");
                }

                if (status == 200) {
                    myJson = new JSONObject(responseStr);
                    JSONArray jsonMainNode = myJson.getJSONArray("device_list");

                    int lengthJsonArr = jsonMainNode.length();
                    for (int i = 0; i < lengthJsonArr; i++) {
                        JSONObject jsonChildNode;
                        try {
                            jsonChildNode = jsonMainNode.getJSONObject(i);
                            LoadRegisteredDevicesOutput loadRegisteredDevicesOutput = new LoadRegisteredDevicesOutput();

                            if ((jsonChildNode.has("device")) && jsonChildNode.optString("device").trim() != null && !jsonChildNode.optString("device").trim().isEmpty() && !jsonChildNode.optString("device").trim().equals("null") && !jsonChildNode.optString("device").trim().matches("")) {
                                loadRegisteredDevicesOutput.setDevice(jsonChildNode.optString("device"));

                            }
                            if ((jsonChildNode.has("device_info")) && jsonChildNode.optString("device_info").trim() != null && !jsonChildNode.optString("device_info").trim().isEmpty() && !jsonChildNode.optString("device_info").trim().equals("null") && !jsonChildNode.optString("device_info").trim().matches("")) {
                                loadRegisteredDevicesOutput.setDevice_info(jsonChildNode.optString("device_info"));
                            }
                            if ((jsonChildNode.has("flag")) && jsonChildNode.optString("flag").trim() != null && !jsonChildNode.optString("flag").trim().isEmpty() && !jsonChildNode.optString("flag").trim().equals("null") && !jsonChildNode.optString("flag").trim().matches("")) {
                                loadRegisteredDevicesOutput.setFlag(jsonChildNode.optString("flag"));
                            }


                            loadRegisteredDevicesOutputArrayList.add(loadRegisteredDevicesOutput);
                        } catch (Exception e) {
                            status = 0;
                            message = "";
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.v("MUVISDK", "Exception" + e.toString());

            status = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onLoadRegisteredDevicesPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList, status, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList, status, responseStr);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList, status, message);
    }
}