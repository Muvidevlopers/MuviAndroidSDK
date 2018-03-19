package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.ContentListOutput;
import com.home.apisdk.apiModel.LoadFilterVideoInput;
import com.home.apisdk.apiModel.LoadFilterVideoOutput;

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
 * Created by MUVI on 11/8/2017.
 */

public class LoadFilterVideoAsync extends AsyncTask<LoadFilterVideoInput,Void,Void>{
    private LoadFilterVideoInput loadFilterVideoInput;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private LoadFilterVideoAsync.LoadFilterVideoListner listener;
    private Context context;
    public static ArrayList<String> genreArray;


    public interface LoadFilterVideoListner{

        void onLoadFilterVideoPreExecuteStarted();
        void onLoadFilterVideoPostExecuteCompleted(ArrayList<LoadFilterVideoOutput> loadFilterVideoOutputArrayList, int status, int totalItems, String message);

    }

    ArrayList<LoadFilterVideoOutput> loadFilterVideoOutput = new ArrayList<LoadFilterVideoOutput>();


    public LoadFilterVideoAsync(LoadFilterVideoInput loadFilterVideoInput, LoadFilterVideoAsync.LoadFilterVideoListner listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.loadFilterVideoInput = loadFilterVideoInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "GetContentListAsynTask");

    }
    @Override
    protected Void doInBackground(LoadFilterVideoInput... params) {
        String urlRouteList = APIUrlConstant.getGetContentListUrl();
        if (loadFilterVideoInput.getGenreArray() != null && loadFilterVideoInput.getGenreArray().size() > 0) {
            String[] mStringArray = new String[loadFilterVideoInput.getGenreArray().size()];
            mStringArray = loadFilterVideoInput.getGenreArray().toArray(mStringArray);
            for (int i = 0; i < mStringArray.length; i++) {
                if (mStringArray.length <= 1) {
                    urlRouteList = (urlRouteList + "?genre[]=" + mStringArray[i].trim()).replace(" ", "%20");

                } else {
                    if (i == 0) {
                        urlRouteList = (urlRouteList + "?genre[]=" + mStringArray[i].trim()).replace(" ", "%20");
                    } else {
                        urlRouteList = (urlRouteList + "&genre[]=" + mStringArray[i].trim()).replace(" ", "%20");

                    }

                }
            }
        }


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlRouteList);
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.loadFilterVideoInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.loadFilterVideoInput.getPermalink());
            httppost.addHeader(HeaderConstants.LIMIT, this.loadFilterVideoInput.getLimit());
            httppost.addHeader(HeaderConstants.OFFSET, this.loadFilterVideoInput.getOffset());
//            httppost.addHeader("orderby", this.contentListInput.getOrderby());
            httppost.addHeader(HeaderConstants.COUNTRY, this.loadFilterVideoInput.getCountry());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.loadFilterVideoInput.getLanguage());
            httppost.addHeader(HeaderConstants.ORDER_BY, this.loadFilterVideoInput.getOrderby());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

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
                        LoadFilterVideoOutput filterVideo = new LoadFilterVideoOutput();

                        if ((jsonChildNode.has("genre")) && jsonChildNode.optString("genre").trim() != null && !jsonChildNode.optString("genre").trim().isEmpty() && !jsonChildNode.optString("genre").trim().equals("null") && !jsonChildNode.optString("genre").trim().matches("")) {
                            filterVideo.setGenre(jsonChildNode.optString("genre"));

                        }
                        if ((jsonChildNode.has("name")) && jsonChildNode.optString("name").trim() != null && !jsonChildNode.optString("name").trim().isEmpty() && !jsonChildNode.optString("name").trim().equals("null") && !jsonChildNode.optString("name").trim().matches("")) {
                            filterVideo.setName(jsonChildNode.optString("name"));
                        }
                        if ((jsonChildNode.has("poster_url")) && jsonChildNode.optString("poster_url").trim() != null && !jsonChildNode.optString("poster_url").trim().isEmpty() && !jsonChildNode.optString("poster_url").trim().equals("null") && !jsonChildNode.optString("poster_url").trim().matches("")) {
                            filterVideo.setPosterUrl(jsonChildNode.optString("poster_url"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            filterVideo.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            filterVideo.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }
                        //videoTypeIdStr = "1";

                        try {
                            if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                                filterVideo.setIsConverted(Integer.parseInt(jsonChildNode.optString("is_converted")));

                            }else {
                                filterVideo.setIsConverted(0);
                            }
                        }catch (Exception e){
                            filterVideo.setIsConverted(0);
                        }
                        try {
                            if ((jsonChildNode.has("is_advance")) && jsonChildNode.optString("is_advance").trim() != null && !jsonChildNode.optString("is_advance").trim().isEmpty() && !jsonChildNode.optString("is_advance").trim().equals("null") && !jsonChildNode.optString("is_advance").trim().matches("")) {
                                filterVideo.setIsAPV(Integer.parseInt(jsonChildNode.optString("is_advance")));

                            }else {
                                filterVideo.setIsAPV(0);
                            }
                        }catch (Exception e){
                            filterVideo.setIsAPV(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_ppv")) && jsonChildNode.optString("is_ppv").trim() != null && !jsonChildNode.optString("is_ppv").trim().isEmpty() && !jsonChildNode.optString("is_ppv").trim().equals("null") && !jsonChildNode.optString("is_ppv").trim().matches("")) {
                                filterVideo.setIsPPV(Integer.parseInt(jsonChildNode.optString("is_ppv")));

                            }else {
                                filterVideo.setIsPPV(0);
                            }
                        }catch (Exception e){
                            filterVideo.setIsPPV(0);
                        }

                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            filterVideo.setIsEpisodeStr(jsonChildNode.optString("is_episode"));

                        }
                        loadFilterVideoOutput.add(filterVideo);
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
        listener.onLoadFilterVideoPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLoadFilterVideoPostExecuteCompleted(loadFilterVideoOutput, status, totalItems, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLoadFilterVideoPostExecuteCompleted(loadFilterVideoOutput, status, totalItems, message);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onLoadFilterVideoPostExecuteCompleted(loadFilterVideoOutput, status, totalItems, message);
    }
}
