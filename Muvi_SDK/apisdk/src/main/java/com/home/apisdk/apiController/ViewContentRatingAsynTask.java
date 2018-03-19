/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.ViewContentRatingInputModel;
import com.home.apisdk.apiModel.ViewContentRatingOutputModel;

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
 * This Class shows all the rating of the contents to the users.
 *
 * @author MUVI
 */
public class ViewContentRatingAsynTask extends AsyncTask<ViewContentRatingInputModel, Void, Void> {

    private ViewContentRatingInputModel viewContentRatingInputModel;
    private String responseStr, movieUniqueId;
    private int status;
    private String message;
    private String permalink;
    private String PACKAGE_NAME;
    private ViewContentRatingListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a ViewContentRatingAsynTask to run some code when get
     * responses.
     */

    public interface ViewContentRatingListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onViewContentRatingPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param viewContentRatingOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                       Response Code from the server
         * @param message                      On Success Message
         */

        void onViewContentRatingPostExecuteCompleted(ViewContentRatingOutputModel viewContentRatingOutputModel, int status, String message);
    }

    ViewContentRatingOutputModel viewContentRatingOutputModel;
    ViewContentRatingOutputModel.Rating rating;
    ArrayList<ViewContentRatingOutputModel.Rating> ratingArrayList;

    /**
     * Constructor to initialise the private data members.
     *
     * @param viewContentRatingInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                    For Example: to use this API we have to set following attributes:
     *                                    setAuthToken(),setUser_id() etc.
     * @param listener                    ViewContentRating Listener
     * @param context                     android.content.Context
     */

    public ViewContentRatingAsynTask(ViewContentRatingInputModel viewContentRatingInputModel, ViewContentRatingListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.viewContentRatingInputModel = viewContentRatingInputModel;
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
    protected Void doInBackground(ViewContentRatingInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getViewContentRating());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.viewContentRatingInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.viewContentRatingInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.CONTENT_ID, this.viewContentRatingInputModel.getContent_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.viewContentRatingInputModel.getLang_code());


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

            Log.v("SUBHA", "response == " + responseStr);
            if (status > 0) {

                if (status == 200) {
                    viewContentRatingOutputModel = new ViewContentRatingOutputModel();

                    try {
                        if ((myJson.has("showrating")) && myJson.optString("showrating").trim() != null && !myJson.optString("showrating").trim().isEmpty() && !myJson.optString("showrating").trim().equals("null") && !myJson.optString("showrating").trim().matches("")) {
                            viewContentRatingOutputModel.setShowrating(Integer.parseInt(myJson.optString("showrating")));

                        }else {
                            viewContentRatingOutputModel.setShowrating(0);
                        }
                    }catch (Exception e){
                        viewContentRatingOutputModel.setShowrating(0);
                    }
                    JSONArray jsonMainNode = myJson.getJSONArray("rating");
                    int lengthJsonArr = jsonMainNode.length();
                    ratingArrayList = new ArrayList<>();
                    for (int i = 0; i < lengthJsonArr; i++) {
                        JSONObject jsonChildNode;
                        try {
                            jsonChildNode = jsonMainNode.getJSONObject(i);
                            rating = new ViewContentRatingOutputModel().new Rating();

                            if ((jsonChildNode.has("display_name")) && jsonChildNode.optString("display_name").trim() != null && !jsonChildNode.optString("display_name").trim().isEmpty() && !jsonChildNode.optString("display_name").trim().equals("null") && !jsonChildNode.optString("display_name").trim().matches("")) {
                                String display_name = jsonChildNode.optString("display_name");
                                rating.setDisplay_name(display_name);

                            }
                            if ((jsonChildNode.has("rating")) && jsonChildNode.optString("rating").trim() != null && !jsonChildNode.optString("rating").trim().isEmpty() && !jsonChildNode.optString("rating").trim().equals("null") && !jsonChildNode.optString("rating").trim().matches("")) {
                                rating.setRating(jsonChildNode.optString("rating"));

                            }
                            if ((jsonChildNode.has("review")) && jsonChildNode.optString("review").trim() != null && !jsonChildNode.optString("review").trim().isEmpty() && !jsonChildNode.optString("review").trim().equals("null") && !jsonChildNode.optString("review").trim().matches("")) {
                                rating.setReview(jsonChildNode.optString("review"));
                            }
                            if ((jsonChildNode.has("status")) && jsonChildNode.optString("status").trim() != null && !jsonChildNode.optString("status").trim().isEmpty() && !jsonChildNode.optString("status").trim().equals("null") && !jsonChildNode.optString("status").trim().matches("")) {
                                rating.setStatus(jsonChildNode.optString("status"));

                            }
                            if(rating.getStatus().equals("1")) {
                                ratingArrayList.add(rating);
                            }

                        } catch (Exception e) {
                            status = 0;
                            // totalItems = 0;
                            message = "";
                        }
                    }

                    viewContentRatingOutputModel.setRatingValue(ratingArrayList);
                } else {
                    responseStr = "0";
                    status = 0;
                    // totalItems = 0;
                    message = "";
                }
            }
        } catch (Exception e) {
            status = 0;
            // totalItems = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onViewContentRatingPreExecuteStarted();
        status = 0;
        if(!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context)))
        {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onViewContentRatingPostExecuteCompleted(viewContentRatingOutputModel, status, message);
            return;
        }
        if(SDKInitializer.getHashKey(context).equals(""))
        {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onViewContentRatingPostExecuteCompleted(viewContentRatingOutputModel, status,message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onViewContentRatingPostExecuteCompleted(viewContentRatingOutputModel, status, message);

    }

}
