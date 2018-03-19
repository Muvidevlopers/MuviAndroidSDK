/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.CelibrityInputModel;
import com.home.apisdk.apiModel.CelibrityOutputModel;

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
 * This Class gives detail of the celebrity who have acted on that particular series/movie which user is looking for.
 * This gives full cast detail like actor, actress, director etc.
 *
 * @author MUVI
 */

public class GetCelibrityAsyntask extends AsyncTask<CelibrityInputModel, Void, Void> {

    private CelibrityInputModel celibrityInputModel;
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String msg;
    private int code;
    private GetCelibrityListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetCelibrityAsyntask to run some code when get
     * responses.
     */

    public interface GetCelibrityListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetCelibrityPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param celibrityOutputModel A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code                 Response Code From The Server
         * @param msg                  On Success Message
         */

        void onGetCelibrityPostExecuteCompleted(ArrayList<CelibrityOutputModel> celibrityOutputModel, int code, String msg);
    }


    ArrayList<CelibrityOutputModel> celibrityOutputModel = new ArrayList<CelibrityOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param celibrityInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setLang_code() etc.
     * @param listener            GetCelibrity Listener
     * @param context             android.content.Context
     */

    public GetCelibrityAsyntask(CelibrityInputModel celibrityInputModel, GetCelibrityListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.celibrityInputModel = celibrityInputModel;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "getPlanListAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(CelibrityInputModel... params) {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetCelibrityUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.celibrityInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.MOVIE_ID, this.celibrityInputModel.getMovie_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.celibrityInputModel.getLang_code());

            Log.v("MUVISDK", "lang_code = " + this.celibrityInputModel.getLang_code());
            Log.v("MUVISDK", "authToken = " + this.celibrityInputModel.getAuthToken());
            Log.v("MUVISDK", "movie id = " + this.celibrityInputModel.getMovie_id());
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
                message = myJson.optString("status");
                msg = myJson.optString("msg");

            }

            if (code == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("celibrity");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        CelibrityOutputModel content = new CelibrityOutputModel();
                        String celebrityName = jsonChildNode.optString("name").trim();
                        String celebrityImage = jsonChildNode.optString("celebrity_image");
                        String celebrityPermalink = jsonChildNode.optString("permalink");
                        String celebritySummary = jsonChildNode.optString("summary");



                        String celebrityCastType = jsonChildNode.optString("cast_type").trim();

                        celebrityCastType = celebrityCastType.replaceAll("\\[", "");
                        celebrityCastType = celebrityCastType.replaceAll("\\]", "");
                        celebrityCastType = celebrityCastType.replaceAll(",", " , ");
                        celebrityCastType = celebrityCastType.replaceAll("\"", "");


                        if (celebrityImage.equals("") || celebrityImage == null) {
                            celebrityImage = "";
                        } else {
                            if (!celebrityImage.contains("http")) {
                                celebrityImage = "";
                            }
                        }

                        content.setName(celebrityName);
                        content.setCast_type(celebrityCastType);
                        content.setCelebrity_image(celebrityImage);
                        content.setSummary(celebritySummary);
                        content.setPermalink(celebrityPermalink);

                        celibrityOutputModel.add(content);
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
        listener.onGetCelibrityPreExecuteStarted();
        code = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            listener.onGetCelibrityPostExecuteCompleted(celibrityOutputModel, code, msg);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            listener.onGetCelibrityPostExecuteCompleted(celibrityOutputModel, code, msg);
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onGetCelibrityPostExecuteCompleted(celibrityOutputModel, code, msg);
    }
}
