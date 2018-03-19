/**
 * SDK initialization, platform and device information classes.
 */

package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.AddToFavInputModel;
import com.home.apisdk.apiModel.AddToFavOutputModel;

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
 * This Class helps user to add their favorite series/Movie to their favorite list section. Users
 * can watch their favorite series/movies without wasting much time in searching them.
 *
 * @author MUVI
 */

public class AddToFavAsync extends AsyncTask<AddToFavInputModel, Void, Void> {
    private AddToFavInputModel addToFavInputModel;
    private String PACKAGE_NAME;
    private String responseStr;
    private String sucessMsg;
    private int status=0;
    private AddToFavListener listener;
    private Context context;
    private String message="";

    /**
     * Interface used to allow the caller of a AddToFavAsync to run some code when get
     * responses.
     */

    public interface AddToFavListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onAddToFavPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param addToFavOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status              Response Code from the server
         * @param sucessMsg           On Success Message
         */

        void onAddToFavPostExecuteCompleted(AddToFavOutputModel addToFavOutputModel, int status, String sucessMsg);
    }

    AddToFavOutputModel AddToFavOutputModel = new AddToFavOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param addToFavInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                           For Example: to use this API we have to set following attributes:
     *                           setAuthToken(),setMovie_uniq_id() etc.
     * @param listener           AddToFavorite Listener
     * @param context            android.content.Context
     */

    public AddToFavAsync(AddToFavInputModel addToFavInputModel, AddToFavListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.addToFavInputModel = addToFavInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetUserProfileAsynctask");
    }

    /**
     * Background thread to execute.
     *
     * @return Null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(AddToFavInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getAddtoFavlist());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.addToFavInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.MOVIE_UNIQ_ID, this.addToFavInputModel.getMovie_uniq_id());
            httppost.addHeader(HeaderConstants.CONTENT_TYPE, this.addToFavInputModel.getIsEpisodeStr());
            httppost.addHeader(HeaderConstants.USER_ID, this.addToFavInputModel.getLoggedInStr());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject myJson = null;
        if (responseStr != null) {
            try {
                myJson = new JSONObject(responseStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            status = Integer.parseInt(myJson.optString("code"));
            sucessMsg = myJson.optString("msg");
//                statusmsg = myJson.optString("status");

        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onAddToFavPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onAddToFavPostExecuteCompleted(AddToFavOutputModel, status, sucessMsg);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onAddToFavPostExecuteCompleted(AddToFavOutputModel, status, sucessMsg);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onAddToFavPostExecuteCompleted(AddToFavOutputModel, status, sucessMsg);
    }
}
