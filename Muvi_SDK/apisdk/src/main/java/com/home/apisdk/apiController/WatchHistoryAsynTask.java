/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.MyLibraryInputModel;
import com.home.apisdk.apiModel.MyLibraryOutputModel;
import com.home.apisdk.apiModel.WatchHistoryInputModel;
import com.home.apisdk.apiModel.WatchHistoryOutputModel;

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
 * This Class loads the MyLibrary details of the user.
 *
 * @author MUVI
 */
public class WatchHistoryAsynTask extends AsyncTask<MyLibraryInputModel, Void, Void> {

    private WatchHistoryInputModel watchHistoryInputModel;
    private String responseStr;
    private int status;
    private String totalItems;
    private String message;
    private String PACKAGE_NAME;
    private WatchHistoryListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a MyLibraryAsynTask to run some code when get
     * responses.
     */

    public interface WatchHistoryListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onWatchHistoryPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param watchHistoryOutputModels A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                    Response Code from the server
         * @param totalItems                For Getting The Total Item
         * @param message                   On Success Message
         */

        void onWatchHistoryPostExecuteCompleted(ArrayList<WatchHistoryOutputModel> watchHistoryOutputModels, int status, String totalItems, String message);
    }

    ArrayList<WatchHistoryOutputModel> watchHistoryOutputModels = new ArrayList<WatchHistoryOutputModel>();

    /**
     * Constructor to initialise the private data members.
     *
     * @param watchHistoryInputModel A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setUser_id() etc.
     * @param listener            MyLibrary Listener
     * @param context             android.content.Context
     */

    public WatchHistoryAsynTask(WatchHistoryInputModel watchHistoryInputModel, WatchHistoryListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.watchHistoryInputModel = watchHistoryInputModel;
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
    protected Void doInBackground(MyLibraryInputModel... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getWatchHistory());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.watchHistoryInputModel.getAuthToken());
            httppost.addHeader(HeaderConstants.USER_ID, this.watchHistoryInputModel.getUser_id());
            httppost.addHeader(HeaderConstants.LIMIT, this.watchHistoryInputModel.getLimit());
            httppost.addHeader(HeaderConstants.OFFSET, this.watchHistoryInputModel.getOffset());
            httppost.addHeader(HeaderConstants.COUNTRY, this.watchHistoryInputModel.getCountry());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.watchHistoryInputModel.getLang_code());


            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                status = 0;
                message = "";
                Log.v("MUVISDK", "ConnectTimeoutException" + e.toString());

            } catch (IOException e) {
                status = 0;
                message = "";
                Log.v("MUVISDK", "IOException" + e.toString());
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("status");
                totalItems = myJson.optString("item_count");
            }


            if (status == 200) {

                JSONArray jsonMainNode = myJson.getJSONArray("list");

                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        WatchHistoryOutputModel content = new WatchHistoryOutputModel();

                        String mylibrarygenre = "";
                        if ((jsonChildNode.has("genres")) && jsonChildNode.optString("genres").trim() != null && !jsonChildNode.optString("genres").trim().isEmpty() && !jsonChildNode.optString("genres").trim().equals("null") && !jsonChildNode.optString("genres").trim().matches("")) {
                            mylibrarygenre = jsonChildNode.optString("genres");
                            mylibrarygenre = mylibrarygenre.replaceAll("\\[", "");
                            mylibrarygenre = mylibrarygenre.replaceAll("\\]", "");
                            mylibrarygenre = mylibrarygenre.replaceAll(",", " , ");
                            mylibrarygenre = mylibrarygenre.replaceAll("\"", "");
                            content.setGenre(mylibrarygenre);

                        }
                        if ((jsonChildNode.has("name")) && jsonChildNode.optString("name").trim() != null && !jsonChildNode.optString("name").trim().isEmpty() && !jsonChildNode.optString("name").trim().equals("null") && !jsonChildNode.optString("name").trim().matches("")) {
                            content.setName(jsonChildNode.optString("name"));
                        }
                        if ((jsonChildNode.has("poster")) && jsonChildNode.optString("poster").trim() != null && !jsonChildNode.optString("poster").trim().isEmpty() && !jsonChildNode.optString("poster").trim().equals("null") && !jsonChildNode.optString("poster").trim().matches("")) {
                            content.setPosterUrl(jsonChildNode.optString("poster"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            content.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            content.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }


                        if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                            content.setIsConverted(Integer.parseInt(jsonChildNode.optString("is_converted")));

                        }
                        if ((jsonChildNode.has("season_number")) && jsonChildNode.optString("season_number").trim() != null && !jsonChildNode.optString("season_number").trim().isEmpty() && !jsonChildNode.optString("season_number").trim().equals("null") && !jsonChildNode.optString("season_number").trim().matches("")) {
                            content.setSeason_id(Integer.parseInt(jsonChildNode.optString("season_number")));

                        }
                        if ((jsonChildNode.has("isFreeContent")) && jsonChildNode.optString("isFreeContent").trim() != null && !jsonChildNode.optString("isFreeContent").trim().isEmpty() && !jsonChildNode.optString("isFreeContent").trim().equals("null") && !jsonChildNode.optString("isFreeContent").trim().matches("")) {
                            content.setIsfreeContent(Integer.parseInt(jsonChildNode.optString("isFreeContent")));

                        }
                        if ((jsonChildNode.has("movie_uniq_id")) && jsonChildNode.optString("movie_uniq_id").trim() != null && !jsonChildNode.optString("movie_uniq_id").trim().isEmpty() && !jsonChildNode.optString("movie_uniq_id").trim().equals("null") && !jsonChildNode.optString("movie_uniq_id").trim().matches("")) {
                            content.setMovieId(jsonChildNode.optString("movie_uniq_id"));

                        }
                        if ((jsonChildNode.has("movie_stream_uniq_id")) && jsonChildNode.optString("movie_stream_uniq_id").trim() != null && !jsonChildNode.optString("movie_stream_uniq_id").trim().isEmpty() && !jsonChildNode.optString("movie_stream_uniq_id").trim().equals("null") && !jsonChildNode.optString("movie_stream_uniq_id").trim().matches("")) {
                            content.setMovie_stream_uniq_id(jsonChildNode.optString("movie_stream_uniq_id"));

                        }
                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            content.setIs_episode(jsonChildNode.optString("is_episode"));

                        }
                        watchHistoryOutputModels.add(content);
                    } catch (Exception e) {
                        status = 0;
                        message = "";
                    }
                }
            }

        } catch (Exception e) {
            status = 0;
            message = "";
            Log.v("MUVISDK", "Exception" + e.toString());
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onWatchHistoryPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onWatchHistoryPostExecuteCompleted(watchHistoryOutputModels, status, totalItems, responseStr);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onWatchHistoryPostExecuteCompleted(watchHistoryOutputModels, status, totalItems, responseStr);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onWatchHistoryPostExecuteCompleted(watchHistoryOutputModels, status, totalItems, responseStr);

    }

    //}
}
