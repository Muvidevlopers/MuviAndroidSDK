/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.FeatureContentInputModel;
import com.home.apisdk.apiModel.FeatureContentOutputModel;

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
 * Created by User on 12-12-2016.
 * Class to get Feature Content details.
 */
public class GetFeatureContentAsynTask extends AsyncTask<FeatureContentInputModel, Void, Void> {

    private FeatureContentInputModel featureContentInputModel;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private GetFeatureContentListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetFeatureContentAsynTask to run some code when get
     * responses.
     */

    public interface GetFeatureContentListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetFeatureContentPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param featureContentOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                    Response Code From The Server
         * @param message                   On Success Message
         */

        void onGetFeatureContentPostExecuteCompleted(ArrayList<FeatureContentOutputModel> featureContentOutputModel, int status, String message);
    }


    ArrayList<FeatureContentOutputModel> featureContentOutputModel = new ArrayList<FeatureContentOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param featureContentInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                                 For Example: to use this API we have to set following attributes:
     *                                 setAuthToken(),setPermalink() etc.
     * @param listener                 GetFeatureContentListener
     * @param context                  android.content.Context
     */

    public GetFeatureContentAsynTask(FeatureContentInputModel featureContentInputModel, GetFeatureContentListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.featureContentInputModel = featureContentInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getFeatureContentAsynTask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(FeatureContentInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetFeatureContentUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.featureContentInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.SECTION_ID, this.featureContentInputModel.getSection_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.featureContentInputModel.getLang_code());

            Log.v("MuviSDK", "authToken = " + this.featureContentInputModel.getAuthToken());
            Log.v("MuviSDK", "section_id = " + this.featureContentInputModel.getSection_id());
            Log.v("MuviSDK  ", "lang_code = " + this.featureContentInputModel.getLang_code());
//            httppost.addHeader("limit",this.featureContentInputModel.getLimit());
//            httppost.addHeader("offset",this.featureContentInputModel.getOffset());
//            httppost.addHeader("orderby",this.featureContentInputModel.getOrderby());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("Muvi", "RES feature =" + responseStr);

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
                message = myJson.optString("status");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("section");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        FeatureContentOutputModel content = new FeatureContentOutputModel();

                        if ((jsonChildNode.has("genre")) && jsonChildNode.optString("genre").trim() != null && !jsonChildNode.optString("genre").trim().isEmpty() && !jsonChildNode.optString("genre").trim().equals("null") && !jsonChildNode.optString("genre").trim().matches("")) {
                            content.setGenre(jsonChildNode.optString("genre"));

                        }
                        if ((jsonChildNode.has("name")) && jsonChildNode.optString("name").trim() != null && !jsonChildNode.optString("name").trim().isEmpty() && !jsonChildNode.optString("name").trim().equals("null") && !jsonChildNode.optString("name").trim().matches("")) {
                            content.setName(jsonChildNode.optString("name"));
                        }
                        if ((jsonChildNode.has("poster_url")) && jsonChildNode.optString("poster_url").trim() != null && !jsonChildNode.optString("poster_url").trim().isEmpty() && !jsonChildNode.optString("poster_url").trim().equals("null") && !jsonChildNode.optString("poster_url").trim().matches("")) {
                            content.setPoster_url(jsonChildNode.optString("poster_url"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            content.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            content.setContent_types_id(jsonChildNode.optString("content_types_id"));

                        }
                        //videoTypeIdStr = "1";

                        try {
                            if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                                content.setIs_converted(Integer.parseInt(jsonChildNode.optString("is_converted")));
                            }
                            else {
                                content.setIs_converted(0);
                            }
                        }catch (Exception e){
                            content.setIs_converted(0);
                        }

                        try{
                            if ((jsonChildNode.has("is_advance")) && jsonChildNode.optString("is_advance").trim() != null && !jsonChildNode.optString("is_advance").trim().isEmpty() && !jsonChildNode.optString("is_advance").trim().equals("null") && !jsonChildNode.optString("is_advance").trim().matches("")) {
                                content.setIs_advance(Integer.parseInt(jsonChildNode.optString("is_advance")));

                            }else {
                                content.setIs_advance(0);
                            }
                        }catch (Exception e){
                            content.setIs_advance(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_ppv")) && jsonChildNode.optString("is_ppv").trim() != null && !jsonChildNode.optString("is_ppv").trim().isEmpty() && !jsonChildNode.optString("is_ppv").trim().equals("null") && !jsonChildNode.optString("is_ppv").trim().matches("")) {
                                content.setIs_ppv(Integer.parseInt(jsonChildNode.optString("is_ppv")));

                            }
                            else {
                                content.setIs_ppv(0);
                            }
                        }catch (Exception e){
                            content.setIs_ppv(0);
                        }

                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            content.setIs_episode(jsonChildNode.optString("is_episode"));

                        }
                        featureContentOutputModel.add(content);
                    } catch (Exception e) {
                        status = 0;
                        message = "";
                    }
                }
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
        listener.onGetFeatureContentPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetFeatureContentPostExecuteCompleted(featureContentOutputModel, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetFeatureContentPostExecuteCompleted(featureContentOutputModel, status, message);
        }


    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetFeatureContentPostExecuteCompleted(featureContentOutputModel, status, message);

    }

    //}
}
