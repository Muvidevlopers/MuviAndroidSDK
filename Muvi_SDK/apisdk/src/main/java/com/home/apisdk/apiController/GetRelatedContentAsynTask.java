/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.RelatedContentInput;
import com.home.apisdk.apiModel.RelatedContentOutput;

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
 * This Class gives all the important content about movie/series such as story, poster, Release Date etc.
 * This Class tells the user all the necessary things that user is looking for like Video Duration, whether the content is free or paid, banner, rating, reviews etc.
 *
 * @author MUVI
 */
public class GetRelatedContentAsynTask extends AsyncTask<RelatedContentInput, Void, Void> {

    private RelatedContentInput relatedContentInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetRelatedContentListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetRelatedContentAsynTask to run some code when get
     * responses.
     */

    public interface GetRelatedContentListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */


        void onGetRelatedContentPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param relatedContentOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status               Response Code From The Server
         * @param message              On Success Message
         */

        void onGetRelatedContentPostExecuteCompleted(RelatedContentOutput relatedContentOutput, int status, String message);
    }


    RelatedContentOutput relatedContentOutput = new RelatedContentOutput();

    /**
     * Constructor to initialise the private data members.
     *
     * @param relatedContentInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setContentId() etc.
     * @param listener            GetRelatedContentListener Listener
     * @param context             android.content.Context
     */

    public GetRelatedContentAsynTask(RelatedContentInput relatedContentInput, GetRelatedContentListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.relatedContentInput = relatedContentInput;
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
    protected Void doInBackground(RelatedContentInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getContentDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.relatedContentInput.getAuthToken());
            httppost.addHeader(HeaderConstants.CONTENT_ID, this.relatedContentInput.getContentId());
            httppost.addHeader(HeaderConstants.CONTENT_STREAM_ID, this.relatedContentInput.getContent_stream_id());
            httppost.addHeader(HeaderConstants.LANGUAGE_CODE,this.relatedContentInput.getLanguage());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("SUBHA", "responseStr====== " + responseStr);


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
                message = myJson.optString("msg");
            }




            if (status > 0) {

                /** rating*///


                if (status == 200) {

                    JSONObject mainJson = myJson.getJSONObject("contentData");
                    if ((mainJson.has("name")) && mainJson.optString("permalink").trim() != null && !mainJson.optString("name").trim().isEmpty() && !mainJson.optString("name").trim().equals("null") && !mainJson.optString("name").trim().matches("")) {
                        relatedContentOutput.setPermalink(mainJson.optString("name"));
                    } else {
                        relatedContentOutput.setPermalink("");

                    }


                }
            } else {

                responseStr = "0";
                status = 0;
                message = "Error";
            }
        } catch (final JSONException e1) {

            responseStr = "0";
            status = 0;
            message = "Error";
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
        listener.onGetRelatedContentPreExecuteStarted();

        status = 0;
       if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetRelatedContentPostExecuteCompleted(relatedContentOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetRelatedContentPostExecuteCompleted(relatedContentOutput, status, message);
        }


    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) {
        listener.onGetRelatedContentPostExecuteCompleted(relatedContentOutput, status, message);

    }


}
