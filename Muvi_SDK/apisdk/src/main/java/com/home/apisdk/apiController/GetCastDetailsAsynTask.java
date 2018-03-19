/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetCastDetailsInput;
import com.home.apisdk.apiModel.GetCastDetailsOutputModel;

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
 * This Class shows the cast details of the video such as Name of the artists, summary of the movie, Image of the artist etc.
 *
 * @author MUVI
 */
public class GetCastDetailsAsynTask extends AsyncTask<GetCastDetailsInput, Void, Void> {

    private GetCastDetailsInput getCastDetailsInput;
    private String responseStr;
    private int status;
    private int totalItems;
    private String message;
    private String PACKAGE_NAME;
    private GetCastDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetCastDetailsAsynTask to run some code when get
     * responses.
     */

    public interface GetCastDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onGetCastDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param getCastDetailsOutputModelArray A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status                         Response Code From The Server
         * @param totalItems                     Total Item Present
         * @param message                        On Success Message
         */

        void onGetCastDetailsPostExecuteCompleted(GetCastDetailsOutputModel getCastDetailsOutputModelArray, int status, int totalItems, String message);
    }

    GetCastDetailsOutputModel getCastDetailsOutputModel;
    GetCastDetailsOutputModel.CastDetails castDetails;
    ArrayList<GetCastDetailsOutputModel.CastDetails> castDetailsArrayList;

    /**
     * Constructor to initialise the private data members.
     *
     * @param getCastDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setLimit() etc.
     * @param listener            GetCastDetailsListener
     * @param context             android.content.Context
     */

    public GetCastDetailsAsynTask(GetCastDetailsInput getCastDetailsInput, GetCastDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getCastDetailsInput = getCastDetailsInput;
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
    protected Void doInBackground(GetCastDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getGetCastDetails());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getCastDetailsInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.getCastDetailsInput.getPermalink());
            httppost.addHeader(HeaderConstants.LIMIT, this.getCastDetailsInput.getLimit());
            httppost.addHeader(HeaderConstants.OFFSET, this.getCastDetailsInput.getOffset());
            httppost.addHeader(HeaderConstants.COUNTRY, this.getCastDetailsInput.getCountry());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.getCastDetailsInput.getLanguage());

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
                    totalItems =0;
                }

                message = myJson.optString("msg");
            }


            if (status == 200) {
                getCastDetailsOutputModel = new GetCastDetailsOutputModel();
                if ((myJson.has("name")) && myJson.optString("name").trim() != null && !myJson.optString("name").trim().isEmpty() && !myJson.optString("name").trim().equals("null") && !myJson.optString("name").trim().matches("")) {
                    getCastDetailsOutputModel.setName(myJson.optString("name"));

                }
                if ((myJson.has("summary")) && myJson.optString("summary").trim() != null && !myJson.optString("summary").trim().isEmpty() && !myJson.optString("summary").trim().equals("null") && !myJson.optString("summary").trim().matches("")) {
                    getCastDetailsOutputModel.setSummary(myJson.optString("summary"));

                }
                if ((myJson.has("cast_image")) && myJson.optString("cast_image").trim() != null && !myJson.optString("cast_image").trim().isEmpty() && !myJson.optString("cast_image").trim().equals("null") && !myJson.optString("cast_image").trim().matches("")) {
                    getCastDetailsOutputModel.setCastImage(myJson.optString("cast_image"));
                }
                JSONArray jsonMainNode = myJson.getJSONArray("movieList");
                castDetailsArrayList = new ArrayList<>();
                int lengthJsonArr = jsonMainNode.length();
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject jsonChildNode;
                    try {
                        jsonChildNode = jsonMainNode.getJSONObject(i);
                        castDetails = new GetCastDetailsOutputModel().new CastDetails();


                        if ((jsonChildNode.has("genre")) && jsonChildNode.optString("genre").trim() != null && !jsonChildNode.optString("genre").trim().isEmpty() && !jsonChildNode.optString("genre").trim().equals("null") && !jsonChildNode.optString("genre").trim().matches("")) {
                            castDetails.setGenre(jsonChildNode.optString("genre"));
                        }


                        if ((jsonChildNode.has("name")) && jsonChildNode.optString("name").trim() != null && !jsonChildNode.optString("name").trim().isEmpty() && !jsonChildNode.optString("name").trim().equals("null") && !jsonChildNode.optString("name").trim().matches("")) {
                            castDetails.setName(jsonChildNode.optString("name"));
                        }
                        if ((jsonChildNode.has("poster_url")) && jsonChildNode.optString("poster_url").trim() != null && !jsonChildNode.optString("poster_url").trim().isEmpty() && !jsonChildNode.optString("poster_url").trim().equals("null") && !jsonChildNode.optString("poster_url").trim().matches("")) {
                            castDetails.setPosterUrl(jsonChildNode.optString("poster_url"));

                        }
                        if ((jsonChildNode.has("permalink")) && jsonChildNode.optString("permalink").trim() != null && !jsonChildNode.optString("permalink").trim().isEmpty() && !jsonChildNode.optString("permalink").trim().equals("null") && !jsonChildNode.optString("permalink").trim().matches("")) {
                            castDetails.setPermalink(jsonChildNode.optString("permalink"));
                        }
                        if ((jsonChildNode.has("content_types_id")) && jsonChildNode.optString("content_types_id").trim() != null && !jsonChildNode.optString("content_types_id").trim().isEmpty() && !jsonChildNode.optString("content_types_id").trim().equals("null") && !jsonChildNode.optString("content_types_id").trim().matches("")) {
                            castDetails.setContentTypesId(jsonChildNode.optString("content_types_id"));

                        }

                        try {
                            if ((jsonChildNode.has("is_converted")) && jsonChildNode.optString("is_converted").trim() != null && !jsonChildNode.optString("is_converted").trim().isEmpty() && !jsonChildNode.optString("is_converted").trim().equals("null") && !jsonChildNode.optString("is_converted").trim().matches("")) {
                                castDetails.setIsConverted(Integer.parseInt(jsonChildNode.optString("is_converted")));

                            }
                            else {
                                castDetails.setIsConverted(0);
                            }
                        }catch (Exception e){
                            castDetails.setIsConverted(0);
                        }
                        try {
                            if ((jsonChildNode.has("is_advance")) && jsonChildNode.optString("is_advance").trim() != null && !jsonChildNode.optString("is_advance").trim().isEmpty() && !jsonChildNode.optString("is_advance").trim().equals("null") && !jsonChildNode.optString("is_advance").trim().matches("")) {
                                castDetails.setIsAdvance(Integer.parseInt(jsonChildNode.optString("is_advance")));

                            }
                            else {
                                castDetails.setIsAdvance(0);
                            }
                        }catch (Exception e){
                            castDetails.setIsAdvance(0);
                        }

                        try {
                            if ((jsonChildNode.has("is_ppv")) && jsonChildNode.optString("is_ppv").trim() != null && !jsonChildNode.optString("is_ppv").trim().isEmpty() && !jsonChildNode.optString("is_ppv").trim().equals("null") && !jsonChildNode.optString("is_ppv").trim().matches("")) {
                                castDetails.setIsPPV(Integer.parseInt(jsonChildNode.optString("is_ppv")));

                            }
                            else {
                                castDetails.setIsPPV(0);
                            }
                        }catch (Exception e){
                            castDetails.setIsPPV(0);
                        }

                        if ((jsonChildNode.has("is_episode")) && jsonChildNode.optString("is_episode").trim() != null && !jsonChildNode.optString("is_episode").trim().isEmpty() && !jsonChildNode.optString("is_episode").trim().equals("null") && !jsonChildNode.optString("is_episode").trim().matches("")) {
                            castDetails.setIsEpisode(jsonChildNode.optString("is_episode"));

                        }
                        castDetailsArrayList.add(castDetails);
                    } catch (Exception e) {
                        /*status = 0;
                        totalItems = 0;
                        message = "";*/
                    }

                }

                getCastDetailsOutputModel.setCastdetails(castDetailsArrayList);


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
        listener.onGetCastDetailsPreExecuteStarted();
        responseStr = "0";
        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetCastDetailsPostExecuteCompleted(getCastDetailsOutputModel, status, totalItems, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetCastDetailsPostExecuteCompleted(getCastDetailsOutputModel, status, totalItems, message);
        }

    }


    @Override
    protected void onPostExecute(Void result) {
        listener.onGetCastDetailsPostExecuteCompleted(getCastDetailsOutputModel, status, totalItems, message);

    }

    //}
}
