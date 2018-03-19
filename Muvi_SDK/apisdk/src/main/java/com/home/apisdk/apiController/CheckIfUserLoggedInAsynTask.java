/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.CheckIfUserLoggedInInputModel;
import com.home.apisdk.apiModel.CheckIfUserLoggedInOutputModel;

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
 * This Class checks the login status of an user.
 *
 * @author MUVI
 */
public class CheckIfUserLoggedInAsynTask extends AsyncTask<CheckIfUserLoggedInInputModel, Void, Void> {

    private CheckIfUserLoggedInInputModel checkIfUserLoggedInInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private CheckIfUserLogggedInListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a CheckIfUserLoggedInAsynTask to run some code when get
     * responses.
     */

    public interface CheckIfUserLogggedInListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onCheckIfUserLogggedInPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param checkIfUserLoggedInOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                         Response Code From The Server
         * @param message                        On Success Message
         */

        void onCheckIfUserLogggedInPostExecuteCompleted(CheckIfUserLoggedInOutputModel checkIfUserLoggedInOutputModel, int status, String message);
    }


    CheckIfUserLoggedInOutputModel checkIfUserLoggedInOutputModel = new CheckIfUserLoggedInOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param checkIfUserLoggedInInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                      For Example: to use this API we have to set following attributes:
     *                                      setAuthToken(),setUser_id() etc.
     * @param listener                      CheckIfUserLogggedIn Listener
     * @param context                       android.content.Context
     */

    public CheckIfUserLoggedInAsynTask(CheckIfUserLoggedInInputModel checkIfUserLoggedInInputModel, CheckIfUserLogggedInListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.checkIfUserLoggedInInputModel = checkIfUserLoggedInInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");


    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(CheckIfUserLoggedInInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getCheckIfUserLoggedIn());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.checkIfUserLoggedInInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.checkIfUserLoggedInInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.DEVICE_ID, this.checkIfUserLoggedInInputModel.getDevice_id());
            httppost.addHeader(HeaderConstants.DEVICE_TYPE, this.checkIfUserLoggedInInputModel.getDevice_type());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

                status = 0;
                message = "Error";


            } catch (IOException e) {
                status = 0;
                message = "Error";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("status");
            }


            if (status == 200) {

                try {
                    if ((myJson.has("is_login")) && myJson.optString("is_login").trim() != null && !myJson.optString("is_login").trim().isEmpty() && !myJson.optString("is_login").trim().equals("null") && !myJson.optString("is_login").trim().matches("")) {
                        checkIfUserLoggedInOutputModel.setIs_login(Integer.parseInt(myJson.optString("is_login")));
                    }
                    else {
                        checkIfUserLoggedInOutputModel.setIs_login(0);
                    }
                }catch (Exception e){
                    checkIfUserLoggedInOutputModel.setIs_login(0);
                }



            } else {

                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (Exception e) {

            responseStr = "0";
            status = 0;
            message = "Error";
        }
        return null;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onCheckIfUserLogggedInPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onCheckIfUserLogggedInPostExecuteCompleted(checkIfUserLoggedInOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onCheckIfUserLogggedInPostExecuteCompleted(checkIfUserLoggedInOutputModel, status, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onCheckIfUserLogggedInPostExecuteCompleted(checkIfUserLoggedInOutputModel, status, message);

    }

    //}
}
