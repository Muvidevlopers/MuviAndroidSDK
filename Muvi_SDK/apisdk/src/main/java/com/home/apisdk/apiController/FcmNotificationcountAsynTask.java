/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.FcmNotificationcountInputModel;
import com.home.apisdk.apiModel.FcmNotificationcountOutputModel;


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

public class FcmNotificationcountAsynTask extends AsyncTask<FcmNotificationcountInputModel, Void, Void> {

    private FcmNotificationcountInputModel fcmNotificationcountInputModel;
    private String PACKAGE_NAME;
    private String responseStr;
    private String sucessMsg;
    private int status;
    private int notificationcont;
    private FcmNotificationcountListener listener;
    private Context context;
    SharedPreferences notificationCount;

    /**
     * Interface used to allow the caller of a DeleteFavAsync to run some code when get
     * responses.
     */


    public interface FcmNotificationcountListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onFcmNotificationcountPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param fcmNotificationcountOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param notificationcont               Response Code From The Server
         * @param sucessMsg            On Success Message
         */

        void onFcmNotificationcountPostExecuteCompleted(FcmNotificationcountOutputModel fcmNotificationcountOutputModel, int notificationcont, String sucessMsg);
    }

    FcmNotificationcountOutputModel fcmNotificationcountOutputModel = new FcmNotificationcountOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param fcmNotificationcountInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setMovieUniqueId() etc.
     * @param listener            DeleteFav Listener
     * @param context             android.content.Context
     */

    public FcmNotificationcountAsynTask(FcmNotificationcountInputModel fcmNotificationcountInputModel, FcmNotificationcountListener listener, Context context) {
        this.fcmNotificationcountInputModel = fcmNotificationcountInputModel;
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
    protected Void doInBackground(FcmNotificationcountInputModel... params) {
        // String urlRouteList = Util.rootUrl().trim() + Util.DeleteFavList.trim();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getNotificationcount());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.fcmNotificationcountInputModel.getAuthToken().trim());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.fcmNotificationcountInputModel.getDevice_id().trim());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if(responseStr!=null){


            JSONObject myJson = null;
//            notificationCount = context.getSharedPreferences(Config.NOTI_COUNT, 0);

            try {
                 myJson = new JSONObject(responseStr);
                //responseStr=jsonObject.getString("msg");
                sucessMsg = myJson.getString("status");
                if (myJson.getString("status").equals("Success")){
                    notificationcont=myJson.getInt("count");
                    fcmNotificationcountOutputModel.setCount(notificationcont);
                    fcmNotificationcountOutputModel.setStatus(sucessMsg);

                   /* SharedPreferences.Editor noti=notificationCount.edit();
                    noti.putString("COUNTER", String.valueOf(notificationcont));
                    noti.commit();
*/

                }else if (myJson.getString("status").equals("Failure")){

                    fcmNotificationcountOutputModel.setStatus(sucessMsg);

                    //toast(response);



                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else{



        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onFcmNotificationcountPostExecuteCompleted(fcmNotificationcountOutputModel, notificationcont, sucessMsg);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onFcmNotificationcountPreExecuteStarted();
    }
}
