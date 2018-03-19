/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;

import com.home.apisdk.apiModel.DeleteFavInputModel;
import com.home.apisdk.apiModel.DeleteFavOutputModel;

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

public class DeleteFavAsync extends AsyncTask<DeleteFavInputModel, Void, Void> {

    private DeleteFavInputModel deleteFavInputModel;
    private String PACKAGE_NAME;
    private String responseStr;
    private String sucessMsg;
    private int status;
    private DeleteFavListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a DeleteFavAsync to run some code when get
     * responses.
     */


    public interface DeleteFavListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onDeleteFavPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param deleteFavOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status               Response Code From The Server
         * @param sucessMsg            On Success Message
         */

        void onDeleteFavPostExecuteCompleted(DeleteFavOutputModel deleteFavOutputModel, int status, String sucessMsg);
    }

    DeleteFavOutputModel deleteFavOutputModel = new DeleteFavOutputModel();

    /**
     * Constructor to initialise the private data members.
     *
     * @param deleteFavInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setMovieUniqueId() etc.
     * @param listener            DeleteFav Listener
     * @param context             android.content.Context
     */

    public DeleteFavAsync(DeleteFavInputModel deleteFavInputModel, DeleteFavListener listener, Context context) {
        this.deleteFavInputModel = deleteFavInputModel;
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
    protected Void doInBackground(DeleteFavInputModel... params) {
        // String urlRouteList = Util.rootUrl() + Util.DeleteFavList;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getDeleteFavList());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.deleteFavInputModel.getAuthTokenStr());
            httppost.addHeader(HeaderConstants.MOVIE_UNIQ_ID, this.deleteFavInputModel.getMovieUniqueId());
            httppost.addHeader(HeaderConstants.CONTENT_TYPE, this.deleteFavInputModel.getIsEpisode());
            httppost.addHeader(HeaderConstants.USER_ID, this.deleteFavInputModel.getLoggedInStr());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());


            } catch (org.apache.http.conn.ConnectTimeoutException e) {

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (responseStr != null) {
            JSONObject myJson = null;
            try {
                myJson = new JSONObject(responseStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            status = Integer.parseInt(myJson.optString("code"));
            sucessMsg = myJson.optString("msg");
//                statusmsg = myJson.optString("status");
            Log.v("MUVI", "response delete==" + myJson);


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onDeleteFavPostExecuteCompleted(deleteFavOutputModel, status, sucessMsg);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onDeleteFavPreExecuteStarted();
    }
}
