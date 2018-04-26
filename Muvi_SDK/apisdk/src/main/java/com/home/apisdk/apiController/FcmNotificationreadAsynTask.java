/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.FcmNotificationreadInputModel;
import com.home.apisdk.apiModel.FcmNotificationreadOutputModel;

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
 * This Class is use to delete the movies/series from the "Add Favorite" list section.
 *
 * @author MUVI
 */

public class FcmNotificationreadAsynTask extends AsyncTask<FcmNotificationreadInputModel, Void, Void> {

    private FcmNotificationreadInputModel fcmNotificationreadInputModel;
    private FcmNotificationreadListener listener;
    private Context context;
    private String PACKAGE_NAME;
    private String responseStr;
    private String responseMsg;
    JSONObject jsonObject;

    /**
     * Interface used to allow the caller of a DeleteFavAsync to run some code when get
     * responses.
     */


    public interface FcmNotificationreadListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onFcmNotificationreadPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param fcmNotificationreadOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param sucessMsg            On Success Message
         */

        void onFcmNotificationreadPostExecuteCompleted(FcmNotificationreadOutputModel fcmNotificationreadOutputModel, String message);
    }

    FcmNotificationreadOutputModel fcmNotificationreadOutputModel = new FcmNotificationreadOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param fcmNotificationreadInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setMovieUniqueId() etc.
     * @param listener            DeleteFav Listener
     * @param context             android.content.Context
     */

    public FcmNotificationreadAsynTask(FcmNotificationreadInputModel fcmNotificationreadInputModel, FcmNotificationreadListener listener, Context context) {
        this.fcmNotificationreadInputModel = fcmNotificationreadInputModel;
        this.listener = listener;
        this.context = context;
        PACKAGE_NAME = context.getPackageName();
    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */


    @Override
    protected Void doInBackground(FcmNotificationreadInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getReadallnotification());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.fcmNotificationreadInputModel.getAuthToken().trim());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.fcmNotificationreadInputModel.getDevice_id().trim());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {



            }


            if(responseStr!=null){



                try {
                    jsonObject = new JSONObject(responseStr);
                    //responseStr=jsonObject.getString("msg");
                    if (jsonObject.getString("status").equals("Success")){


                        responseMsg = jsonObject.getString("status");

                    }else if (jsonObject.getString("status").equals("Failure")){


                        //taoast(response);
                        responseMsg = jsonObject.getString("status");



                    }
                    fcmNotificationreadOutputModel.setMessage(jsonObject.getString("status"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{



            }


        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onFcmNotificationreadPostExecuteCompleted(fcmNotificationreadOutputModel, responseMsg);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onFcmNotificationreadPreExecuteStarted();
    }
}
