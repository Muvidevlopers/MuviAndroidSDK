/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.ViewFavouriteInputModel;
import com.home.apisdk.apiModel.ViewFavouriteOutputModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This Class shows all the videos which has been added in to favorite list so that user can play them anytime.
 *
 * @author MUVI
 */
public class ViewFavouriteAsynTask extends AsyncTask<ViewFavouriteInputModel, Void, Void> {

    private ViewFavouriteInputModel viewFavouriteInputModel;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private ViewFavouriteListener listener;
    private Context context;


    /**
     * Interface used to allow the caller of a ViewFavouriteAsynTask to run some code when get
     * responses.
     */

    public interface ViewFavouriteListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onViewFavouritePreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param viewFavouriteOutputModelArray A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                        Response Code from the server
         * @param totalItems                    For Getting The Total Items
         * @param message                       On Success Message
         */

        void onViewFavouritePostExecuteCompleted(ArrayList<ViewFavouriteOutputModel> viewFavouriteOutputModelArray, int status, int totalItems, String message);
    }

    ArrayList<ViewFavouriteOutputModel> viewFavouriteOutputModel = new ArrayList<ViewFavouriteOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param viewFavouriteInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                For Example: to use this API we have to set following attributes:
     *                                setAuthToken(),setUser_id() etc.
     * @param listener                ViewFavourite Listener
     * @param context                 android.content.Context
     */

    public ViewFavouriteAsynTask(ViewFavouriteInputModel viewFavouriteInputModel, ViewFavouriteListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.viewFavouriteInputModel = viewFavouriteInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "view favorite data");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(ViewFavouriteInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getViewFavorite());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.viewFavouriteInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.viewFavouriteInputModel.getUser_id());

            Log.v("SUBHA", "AUTH_TOKEN" + HeaderConstants.AUTH_TOKEN);
            Log.v("SUBHA", "USER_ID" + HeaderConstants.USER_ID);
            Log.v("SUBHA", "authtoken" + this.viewFavouriteInputModel.getAuthToken());
            Log.v("SUBHA", "user id" + this.viewFavouriteInputModel.getUser_id());
            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("SUBHA", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                totalItems = 0;
                message = "";

            } catch (IOException e) {
                status = 0;
                totalItems = 0;
                message = "";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("status"));
                totalItems = Integer.parseInt(myJson.optString("item_count"));
                message = myJson.optString("msg");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("movieList");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        ViewFavouriteOutputModel content = new ViewFavouriteOutputModel();

                        if ((jsonChildNode.has("movie_uniq_id")) && jsonChildNode.optString("movie_uniq_id").trim() != null && !jsonChildNode.optString("movie_uniq_id").trim().isEmpty() && !jsonChildNode.optString("movie_uniq_id").trim().equals("null") && !jsonChildNode.optString("movie_uniq_id").trim().matches("")) {
                            content.setMovieId(jsonChildNode.optString("movie_uniq_id"));
                        }

                        Log.v("SUBHA", "movieid " + jsonChildNode.optString("movie_uniq_id"));


                        if ((jsonChildNode.has("title")) && jsonChildNode.optString("title").trim() != null && !jsonChildNode.optString("title").trim().isEmpty() && !jsonChildNode.optString("title").trim().equals("null") && !jsonChildNode.optString("title").trim().matches("")) {
                            content.setTitle(jsonChildNode.optString("title"));
                        }
                        Log.v("SUBHA", "title " + jsonChildNode.optString("title"));
                        if ((jsonChildNode.has("poster")) && jsonChildNode.optString("poster").trim() != null && !jsonChildNode.optString("poster").trim().isEmpty() && !jsonChildNode.optString("poster").trim().equals("null") && !jsonChildNode.optString("poster").trim().matches("")) {
                            content.setPoster(jsonChildNode.optString("poster"));

                        }
                        Log.v("SUBHA", "poster " + jsonChildNode.optString("poster"));
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            content.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        Log.v("SUBHA", "permalink " + jsonChildNode.optString("permalink"));
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            content.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }
                        Log.v("SUBHA", "content_types_id " + jsonChildNode.optString("content_types_id"));
                        //videoTypeIdStr = "1";


                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            content.setIsEpisodeStr(jsonChildNode.optString("is_episode"));

                        }

                        Log.v("SUBHA", "is_episode " + jsonChildNode.optString("is_episode"));
                        viewFavouriteOutputModel.add(content);
                    } catch (Exception e) {
                        status = 0;
                        totalItems = 0;
                        message = "";
                    }
                }
            }

        } catch (Exception e) {
            status = 0;
            totalItems = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onViewFavouritePreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onViewFavouritePostExecuteCompleted(viewFavouriteOutputModel, status, totalItems, message);

    }

    //}
}
