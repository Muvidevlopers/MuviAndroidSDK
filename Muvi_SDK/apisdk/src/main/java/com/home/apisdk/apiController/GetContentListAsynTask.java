/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.Utils;
import com.home.apisdk.apiModel.ContentListInput;
import com.home.apisdk.apiModel.ContentListOutput;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This Class is use to show the content list to the users.
 * Among the movies they can select their favorite movies/series and can check all the details about that particular movie/series.
 *
 * @author MUVI
 */
public class GetContentListAsynTask extends AsyncTask<ContentListInput, Void, Void> {

    private ContentListInput contentListInput;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private GetContentListListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetContentListAsynTask to run some code when get
     * responses.
     */

    public interface GetContentListListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */


        void onGetContentListPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param contentListOutputArray A Model Class which which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                 Response Code From the Server
         * @param totalItems             Total Item Present
         * @param message                On Success Message
         */

        void onGetContentListPostExecuteCompleted(ArrayList<ContentListOutput> contentListOutputArray, int status, int totalItems, String message);
    }


    ArrayList<ContentListOutput> contentListOutput = new ArrayList<ContentListOutput>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param contentListInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                         For Example: to use this API we have to set following attributes:
     *                         setAuthToken(),setOffset() etc.
     * @param listener         GetContentList Listener
     * @param context          android.content.Context
     */

    public GetContentListAsynTask(ContentListInput contentListInput, GetContentListListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.contentListInput = contentListInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException
     */

    @Override
    protected Void doInBackground(ContentListInput... params) {

        try {
           /* HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetContentListUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.contentListInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.contentListInput.getPermalink());
            httppost.addHeader(HeaderConstants.LIMIT, this.contentListInput.getLimit());
            httppost.addHeader(HeaderConstants.OFFSET, this.contentListInput.getOffset());
//            httppost.addHeader("orderby", this.contentListInput.getOrderby());
            httppost.addHeader(HeaderConstants.COUNTRY, this.contentListInput.getCountry());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.contentListInput.getLanguage());
            httppost.addHeader(HeaderConstants.ORDER_BY, this.contentListInput.getOrderby());*/

            URL url = new URL(APIUrlConstant.getGetContentListUrl());
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter(HeaderConstants.AUTH_TOKEN, this.contentListInput.getAuthToken())
                    .appendQueryParameter(HeaderConstants.PERMALINK, this.contentListInput.getPermalink())
                    .appendQueryParameter(HeaderConstants.LIMIT, this.contentListInput.getLimit())
                    .appendQueryParameter(HeaderConstants.OFFSET, this.contentListInput.getOffset())
                    .appendQueryParameter("orderby", this.contentListInput.getOrderby())
                    .appendQueryParameter(HeaderConstants.COUNTRY, this.contentListInput.getCountry())
                    .appendQueryParameter(HeaderConstants.LANG_CODE, this.contentListInput.getLanguage())
                    .appendQueryParameter(HeaderConstants.ORDER_BY, this.contentListInput.getOrderby());
            String query = (builder.build().getEncodedQuery()).replaceAll("%40","@");
            responseStr = Utils.handleHttpAndHttpsRequest(url,query,status,message);


            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("status"));
                try {
                    totalItems = Integer.parseInt(myJson.optString("item_count"));
                }catch (Exception e){
                    totalItems=0;
                }

                message = myJson.optString("msg");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("movieList");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        ContentListOutput content = new ContentListOutput();

                        if ((jsonChildNode.has("genre")) && jsonChildNode.optString("genre").trim() != null && !jsonChildNode.optString("genre").trim().isEmpty() && !jsonChildNode.optString("genre").trim().equals("null") && !jsonChildNode.optString("genre").trim().matches("")) {
                            content.setGenre(jsonChildNode.optString("genre"));

                        }
                        if ((jsonChildNode.has("name")) && jsonChildNode.optString("name").trim() != null && !jsonChildNode.optString("name").trim().isEmpty() && !jsonChildNode.optString("name").trim().equals("null") && !jsonChildNode.optString("name").trim().matches("")) {
                            content.setName(jsonChildNode.optString("name"));
                        }
                        if ((jsonChildNode.has("poster_url")) && jsonChildNode.optString("poster_url").trim() != null && !jsonChildNode.optString("poster_url").trim().isEmpty() && !jsonChildNode.optString("poster_url").trim().equals("null") && !jsonChildNode.optString("poster_url").trim().matches("")) {
                            content.setPosterUrl(jsonChildNode.optString("poster_url"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            content.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            content.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }
                        //videoTypeIdStr = "1";

                        try {
                            if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                                content.setIsConverted(Integer.parseInt(jsonChildNode.optString("is_converted")));

                            }else {
                                content.setIsConverted(0);
                            }
                        }catch (Exception e){
                            content.setIsConverted(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_advance")) && jsonChildNode.optString("is_advance").trim() != null && !jsonChildNode.optString("is_advance").trim().isEmpty() && !jsonChildNode.optString("is_advance").trim().equals("null") && !jsonChildNode.optString("is_advance").trim().matches("")) {
                                content.setIsAPV(Integer.parseInt(jsonChildNode.optString("is_advance")));

                            }else {
                                content.setIsAPV(0);
                            }
                        }catch (Exception e){
                            content.setIsAPV(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_ppv")) && jsonChildNode.optString("is_ppv").trim() != null && !jsonChildNode.optString("is_ppv").trim().isEmpty() && !jsonChildNode.optString("is_ppv").trim().equals("null") && !jsonChildNode.optString("is_ppv").trim().matches("")) {
                                content.setIsPPV(Integer.parseInt(jsonChildNode.optString("is_ppv")));

                            }
                            else {
                                content.setIsPPV(0);
                            }
                        }catch (Exception e){
                            content.setIsPPV(0);
                        }

                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            content.setIsEpisodeStr(jsonChildNode.optString("is_episode"));

                        }
                        contentListOutput.add(content);
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
        listener.onGetContentListPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetContentListPostExecuteCompleted(contentListOutput, status, totalItems, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetContentListPostExecuteCompleted(contentListOutput, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetContentListPostExecuteCompleted(contentListOutput, status, totalItems, message);

    }

    //}
}
