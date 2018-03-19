/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.LoadVideoInput;
import com.home.apisdk.apiModel.LoadVideoOutput;

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
 * This Class Loads the video and play it.
 *
 * @author MUVI
 */

public class GetLoadVideosAsync extends AsyncTask<LoadVideoInput, Void, Void> {

    private LoadVideoInput loadVideoInput;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private int code;
    private LoadVideosAsyncListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetLoadVideosAsync to run some code when get
     * responses.
     */

    public interface LoadVideosAsyncListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onLoadVideosAsyncPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param loadVideoOutputs A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code             Response Code From The Server
         * @param status           For Getting the Status
         */

        void onLoadVideosAsyncPostExecuteCompleted(ArrayList<LoadVideoOutput> loadVideoOutputs, int code, String status);
    }


    ArrayList<LoadVideoOutput> loadVideoOutputs = new ArrayList<LoadVideoOutput>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param loadVideoInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                       For Example: to use this API we have to set following attributes:
     *                       setAuthToken(),setMovie_uniq_id() etc.
     * @param listener       LoadVideosAsync Listener
     * @param context        android.content.Context
     */

    public GetLoadVideosAsync(LoadVideoInput loadVideoInput, LoadVideosAsyncListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.loadVideoInput = loadVideoInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getLoadVideoAsync");
        Log.v("MUVISDK", "authToken = " + this.loadVideoInput.getAuthToken());
    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(LoadVideoInput... params) {
        Log.v("MUVISDK", "doInbkg....");
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetFeatureContentUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.loadVideoInput.getAuthToken());
            httppost.addHeader(HeaderConstants.SECTION_ID, this.loadVideoInput.getSection_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.loadVideoInput.getLang_code());

            Log.v("Abhi Auth", this.loadVideoInput.getAuthToken());
            Log.v("Abhi Session", this.loadVideoInput.getSection_id());
            Log.v("Abhi Lang", this.loadVideoInput.getLang_code());

            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                message = "";


            } catch (IOException e) {
                code = 0;
                message = "";

            }
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
            }
            if (code == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("section");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;

                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        LoadVideoOutput content = new LoadVideoOutput();

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

                        try {
                            if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                                content.setIs_converted(jsonChildNode.optInt("is_converted"));
                            }
                            else {
                                content.setIs_converted(0);
                            }
                        }catch (Exception e){
                            content.setIs_converted(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_advance")) && jsonChildNode.optString("is_advance").trim() != null && !jsonChildNode.optString("is_advance").trim().isEmpty() && !jsonChildNode.optString("is_advance").trim().equals("null") && !jsonChildNode.optString("is_advance").trim().matches("")) {
                                content.setIs_advance(jsonChildNode.optInt("is_advance"));
                            }
                            else {
                                content.setIs_advance(0);
                            }
                        }catch (Exception e){
                            content.setIs_advance(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_ppv")) && jsonChildNode.optString("is_ppv").trim() != null && !jsonChildNode.optString("is_ppv").trim().isEmpty() && !jsonChildNode.optString("is_ppv").trim().equals("null") && !jsonChildNode.optString("is_ppv").trim().matches("")) {
                                content.setIs_ppv(jsonChildNode.optInt("is_ppv"));
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
                        loadVideoOutputs.add(content);
                    } catch (Exception e) {
                        code = 0;
                        message = "";
                    }
                }
            }
        } catch (Exception e) {
            code = 0;
            message = "";

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onLoadVideosAsyncPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLoadVideosAsyncPostExecuteCompleted(loadVideoOutputs, code, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLoadVideosAsyncPostExecuteCompleted(loadVideoOutputs, code, responseStr);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onLoadVideosAsyncPostExecuteCompleted(loadVideoOutputs, code, responseStr);
    }
}