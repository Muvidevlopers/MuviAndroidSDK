/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.FcmNotificationlistsInputModel;
import com.home.apisdk.apiModel.FcmNotificationlistsOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class is use to show the content list to the users.
 * Among the movies they can select their favorite movies/series and can check all the details about that particular movie/series.
 *
 * @author MUVI
 */
public class FcmNotificationlistsAsynTask extends AsyncTask<FcmNotificationlistsInputModel, Void, Void> {

    private FcmNotificationlistsInputModel fcmNotificationlistsInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetFcmNotificationListListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetContentListAsynTask to run some code when get
     * responses.
     */

    public interface GetFcmNotificationListListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */


        void onGetFcmNotificationListPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param fcmNotificationlistsOutputArray A Model Class which which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                 Response Code From the Server
         * @param message                On Success Message
         */

        void onGetFcmNotificationListPostExecuteCompleted(ArrayList<FcmNotificationlistsOutputModel> fcmNotificationlistsOutputArray, int status, String message);
    }


    ArrayList<FcmNotificationlistsOutputModel> fcmNotificationlistsOutputArray = new ArrayList<FcmNotificationlistsOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param fcmNotificationlistsInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                         For Example: to use this API we have to set following attributes:
     *                         setAuthToken(),setOffset() etc.
     * @param listener         GetContentList Listener
     * @param context          android.content.Context
     */

    public FcmNotificationlistsAsynTask(FcmNotificationlistsInputModel fcmNotificationlistsInputModel, GetFcmNotificationListListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.fcmNotificationlistsInputModel = fcmNotificationlistsInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     */

    @Override
    protected Void doInBackground(FcmNotificationlistsInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getNotificationLists());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.fcmNotificationlistsInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.fcmNotificationlistsInputModel.getDevice_id());

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
                message = myJson.optString("msg");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("notifyList");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        FcmNotificationlistsOutputModel fcmNotificationlistsOutputModel = new FcmNotificationlistsOutputModel();

                        if ((jsonChildNode.has("message")) && jsonChildNode.optString("message").trim() != null && !jsonChildNode.optString("message").trim().isEmpty() && !jsonChildNode.optString("message").trim().equals("null") && !jsonChildNode.optString("message").trim().matches("")) {
                            fcmNotificationlistsOutputModel.setNotification(jsonChildNode.optString("message"));

                        }

                        fcmNotificationlistsOutputArray.add(fcmNotificationlistsOutputModel);
                    } catch (Exception e) {
                        status = 0;
                        message = "";
                    }
                }
            }else{
                status = 0;
                message = "";
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
        listener.onGetFcmNotificationListPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetFcmNotificationListPostExecuteCompleted(fcmNotificationlistsOutputArray, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetFcmNotificationListPostExecuteCompleted(fcmNotificationlistsOutputArray, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetFcmNotificationListPostExecuteCompleted(fcmNotificationlistsOutputArray, status, message);

    }

    //}
}
