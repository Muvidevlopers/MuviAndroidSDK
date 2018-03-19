/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.APVModel;
import com.home.apisdk.apiModel.ContentDetailsInput;
import com.home.apisdk.apiModel.ContentDetailsOutput;
import com.home.apisdk.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.PPVModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This Class gives all the important content about movie/series such as story, poster, Release Date etc.
 * This Class tells the user all the necessary things that user is looking for like Video Duration, whether the content is free or paid, banner, rating, reviews etc.
 *
 * @author MUVI
 */
public class GetContentDetailsAsynTask extends AsyncTask<ContentDetailsInput, Void, Void> {

    private ContentDetailsInput contentDetailsInput;
    private String responseStr;
    private int status;
    private String message;
    private String PACKAGE_NAME;
    private ArrayList<String> season;
    private GetContentDetailsListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetContentDetailsAsynTask to run some code when get
     * responses.
     */

    public interface GetContentDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */


        void onGetContentDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param contentDetailsOutput A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param status               Response Code From The Server
         * @param message              On Success Message
         */

        void onGetContentDetailsPostExecuteCompleted(ContentDetailsOutput contentDetailsOutput, int status, String message);
    }


    ContentDetailsOutput contentDetailsOutput = new ContentDetailsOutput();

    /**
     * Constructor to initialise the private data members.
     *
     * @param contentDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                            For Example: to use this API we have to set following attributes:
     *                            setAuthToken(),setPermalink() etc.
     * @param listener            GetContentDetails Listener
     * @param context             android.content.Context
     */

    public GetContentDetailsAsynTask(ContentDetailsInput contentDetailsInput, GetContentDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        this.contentDetailsInput = contentDetailsInput;
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
    protected Void doInBackground(ContentDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getContentDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.contentDetailsInput.getAuthToken());
            httppost.addHeader(HeaderConstants.PERMALINK, this.contentDetailsInput.getPermalink());
            httppost.addHeader(HeaderConstants.USER_ID, this.contentDetailsInput.getUser_id());
            httppost.addHeader("country", this.contentDetailsInput.getCountry());
            httppost.addHeader("lang_code", this.contentDetailsInput.getLanguage());

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

            if (myJson.has("rating") && myJson.has("rating") != false && myJson.optString("rating").trim() != null && !myJson.optString("rating").trim().isEmpty() && !myJson.optString("rating").trim().equals("null") && !myJson.optString("rating").trim().equals("false")) {
                contentDetailsOutput.setRating(myJson.optString("rating"));

            } else {
                contentDetailsOutput.setRating("");
            }

            if (myJson.has("review") && myJson.has("review") != false && myJson.optString("review").trim() != null && !myJson.optString("review").trim().isEmpty() && !myJson.optString("review").trim().equals("null") && !myJson.optString("review").trim().equals("false")) {
                contentDetailsOutput.setReview(myJson.optString("review"));
            } else {
                contentDetailsOutput.setReview("");
            }

            if ((myJson.has("epDetails")) && myJson.optString("epDetails").trim() != null && !myJson.optString("epDetails").trim().isEmpty() && !myJson.optString("epDetails").trim().equals("null") && !myJson.optString("epDetails").trim().matches("")) {
                JSONObject epDetailsJson = myJson.getJSONObject("epDetails");
                Log.v("MUVI", "epDetailsJson====== " + epDetailsJson.optString("series_number").split(","));

                if ((epDetailsJson.has("series_number")) && epDetailsJson.optString("series_number").trim() != null && !epDetailsJson.optString("series_number").trim().isEmpty() && !epDetailsJson.optString("series_number").trim().equals("null") && !epDetailsJson.optString("series_number").trim().matches("") && !epDetailsJson.optString("series_number").trim().matches("0")) {
                    String s[] = epDetailsJson.optString("series_number").split(",");
                    Log.v("MUVI", "s====== " + s.length);

                    Arrays.sort(s);
                    contentDetailsOutput.setSeason(s);


                }

            }


            if (status > 0) {

                /** rating*///


                if (status == 200) {

                    JSONObject mainJson = myJson.getJSONObject("movie");
                    if ((mainJson.has("name")) && mainJson.optString("name").trim() != null && !mainJson.optString("name").trim().isEmpty() && !mainJson.optString("name").trim().equals("null") && !mainJson.optString("name").trim().matches("")) {
                        contentDetailsOutput.setName(mainJson.optString("name"));
                    } else {
                        contentDetailsOutput.setName("");

                    }
                    String movieTypeStr = "";
                    if ((mainJson.has("genre")) && mainJson.optString("genre").trim() != null && !mainJson.optString("genre").trim().isEmpty() && !mainJson.optString("genre").trim().equals("null") && !mainJson.optString("genre").trim().matches("")) {
                        movieTypeStr = mainJson.optString("genre");
                        movieTypeStr = movieTypeStr.replaceAll("\\[", "");
                        movieTypeStr = movieTypeStr.replaceAll("\\]", "");
                        movieTypeStr = movieTypeStr.replaceAll(",", " , ");
                        movieTypeStr = movieTypeStr.replaceAll("\"", "");
                        movieTypeStr = movieTypeStr.trim();
                        movieTypeStr = movieTypeStr.replaceAll(" ","");

                        if(movieTypeStr.contains(",")){
                            String data[] = movieTypeStr.split(",");
                            movieTypeStr = "";
                            for(int i=0;i<data.length;i++){
                                movieTypeStr = movieTypeStr+data[i]+", ";
                            }
                            movieTypeStr =movieTypeStr.trim();
                            if (movieTypeStr.charAt(movieTypeStr.length()-1)==','){
                                movieTypeStr = movieTypeStr.substring(0, movieTypeStr.length() - 1);
                                movieTypeStr =movieTypeStr.trim();
                            }
                        }
                        contentDetailsOutput.setGenre(movieTypeStr);

                    } else {
                        contentDetailsOutput.setGenre("");

                    }
                    if ((mainJson.has("is_episode")) && mainJson.optString("is_episode").trim() != null && !mainJson.optString("is_episode").trim().isEmpty() && !mainJson.optString("is_episode").trim().equals("null") && !mainJson.optString("is_episode").trim().matches("")) {
                        contentDetailsOutput.setIsEpisode(mainJson.optString("is_episode"));

                    } else {
                        contentDetailsOutput.setIsEpisode("0");

                    }
                    if ((mainJson.has("permalink")) && mainJson.optString("permalink").trim() != null && !mainJson.optString("permalink").trim().isEmpty() && !mainJson.optString("permalink").trim().equals("null") && !mainJson.optString("permalink").trim().matches("")) {
                        contentDetailsOutput.setPermalink(mainJson.optString("permalink"));

                    } else {
                        contentDetailsOutput.setPermalink("");

                    }
                    if ((mainJson.has("censor_rating")) && mainJson.optString("censor_rating").trim() != null && !mainJson.optString("censor_rating").trim().isEmpty() && !mainJson.optString("censor_rating").trim().equals("null") && !mainJson.optString("censor_rating").trim().matches("")) {
                        String censorRatingStr = mainJson.optString("censor_rating");
                        censorRatingStr = censorRatingStr.replaceAll("\\[", "");
                        censorRatingStr = censorRatingStr.replaceAll("\\]", "");
                        censorRatingStr = censorRatingStr.replaceAll(",", " ");
                        censorRatingStr = censorRatingStr.replaceAll("\"", "");
                        contentDetailsOutput.setCensorRating(censorRatingStr);


                    } else {
                        contentDetailsOutput.setCensorRating("");

                    }
                    if ((mainJson.has("story")) && mainJson.optString("story").trim() != null && !mainJson.optString("story").trim().isEmpty() && !mainJson.optString("story").trim().equals("null") && !mainJson.optString("story").trim().matches("")) {
                        contentDetailsOutput.setStory(mainJson.optString("story"));
                    } else {
                        contentDetailsOutput.setStory("");

                    }
                    if ((mainJson.has("trailerUrl")) && mainJson.optString("trailerUrl").trim() != null && !mainJson.optString("trailerUrl").trim().isEmpty() && !mainJson.optString("trailerUrl").trim().equals("null") && !mainJson.optString("trailerUrl").trim().matches("")) {
                        contentDetailsOutput.setTrailerUrl(mainJson.optString("trailerUrl"));
                    } else {
                        contentDetailsOutput.setTrailerUrl("");

                    }
                    try {
                        if ((mainJson.has("is_favorite")) && mainJson.optString("is_favorite").trim() != null && !mainJson.optString("is_favorite").trim().isEmpty() && !mainJson.optString("is_favorite").trim().equals("null") && !mainJson.optString("is_episode").trim().matches("")) {
                            contentDetailsOutput.setIs_favorite(Integer.parseInt(mainJson.optString("is_favorite")));

                        }else {
                            contentDetailsOutput.setIs_favorite(0);
                        }
                    }catch (Exception e){
                        contentDetailsOutput.setIs_favorite(0);
                    }


                    if ((mainJson.has("movie_stream_uniq_id")) && mainJson.optString("movie_stream_uniq_id").trim() != null && !mainJson.optString("movie_stream_uniq_id").trim().isEmpty() && !mainJson.optString("movie_stream_uniq_id").trim().equals("null") && !mainJson.optString("movie_stream_uniq_id").trim().matches("")) {
                        contentDetailsOutput.setMovieStreamUniqId(mainJson.optString("movie_stream_uniq_id"));

                    } else {
                        contentDetailsOutput.setMovieStreamUniqId("");

                    }
                    if ((mainJson.has("id")) && mainJson.optString("id").trim() != null && !mainJson.optString("id").trim().isEmpty() && !mainJson.optString("id").trim().equals("null") && !mainJson.optString("id").trim().matches("")) {
                        contentDetailsOutput.setId(mainJson.optString("id"));
                    }

                    if ((mainJson.has("muvi_uniq_id")) && mainJson.optString("muvi_uniq_id").trim() != null && !mainJson.optString("muvi_uniq_id").trim().isEmpty() && !mainJson.optString("muvi_uniq_id").trim().equals("null") && !mainJson.optString("muvi_uniq_id").trim().matches("")) {
                        contentDetailsOutput.setMuviUniqId(mainJson.optString("muvi_uniq_id"));
                    } else {
                        contentDetailsOutput.setMuviUniqId("");

                    }
                    if ((mainJson.has("video_duration")) && mainJson.optString("video_duration").trim() != null && !mainJson.optString("video_duration").trim().isEmpty() && !mainJson.optString("video_duration").trim().equals("null") && !mainJson.optString("video_duration").trim().matches("")) {
                        contentDetailsOutput.setVideoDuration(mainJson.optString("video_duration"));
                    } else {
                        contentDetailsOutput.setVideoDuration("");

                    }

                    if ((mainJson.has("movieUrl")) && mainJson.optString("movieUrl").trim() != null && !mainJson.optString("movieUrl").trim().isEmpty() && !mainJson.optString("movieUrl").trim().equals("null") && !mainJson.optString("movieUrl").trim().matches("")) {
                        contentDetailsOutput.setMovieUrl(mainJson.optString("movieUrl"));

                    } else {
                        contentDetailsOutput.setMovieUrl("");

                    }

                    if ((mainJson.has("banner")) && mainJson.optString("banner").trim() != null && !mainJson.optString("banner").trim().isEmpty() && !mainJson.optString("banner").trim().equals("null") && !mainJson.optString("banner").trim().matches("")) {
                        contentDetailsOutput.setBanner(mainJson.optString("banner"));
                    } else {
                        contentDetailsOutput.setBanner("");

                    }

                    if ((mainJson.has("poster")) && mainJson.optString("poster").trim() != null && !mainJson.optString("poster").trim().isEmpty() && !mainJson.optString("poster").trim().equals("null") && !mainJson.optString("poster").trim().matches("")) {
                        contentDetailsOutput.setPoster(mainJson.optString("poster"));
                    } else {
                        contentDetailsOutput.setPoster("");

                    }
                    if ((mainJson.has("isFreeContent")) && mainJson.optString("isFreeContent").trim() != null && !mainJson.optString("isFreeContent").trim().isEmpty() && !mainJson.optString("isFreeContent").trim().equals("null") && !mainJson.optString("isFreeContent").trim().matches("")) {
                        contentDetailsOutput.setIsFreeContent(mainJson.optString("isFreeContent"));
                    } else {
                        contentDetailsOutput.setIsFreeContent("");

                    }
                    if ((mainJson.has("release_date")) && mainJson.optString("release_date").trim() != null && !mainJson.optString("release_date").trim().isEmpty() && !mainJson.optString("release_date").trim().equals("null") && !mainJson.optString("release_date").trim().matches("")) {
                        contentDetailsOutput.setReleaseDate(mainJson.optString("release_date"));
                    } else {
                        contentDetailsOutput.setReleaseDate("");

                    }
                    try {
                        if ((mainJson.has("is_ppv")) && mainJson.optString("is_ppv").trim() != null && !mainJson.optString("is_ppv").trim().isEmpty() && !mainJson.optString("is_ppv").trim().equals("null") && !mainJson.optString("is_ppv").trim().matches("")) {
                            contentDetailsOutput.setIsPpv(Integer.parseInt(mainJson.optString("is_ppv")));
                        } else {
                            contentDetailsOutput.setIsPpv(0);

                        }
                    }catch (Exception e){
                        contentDetailsOutput.setIsPpv(0);
                    }

                    try {
                        if ((mainJson.has("content_types_id")) && mainJson.optString("content_types_id").trim() != null && !mainJson.optString("content_types_id").trim().isEmpty() && !mainJson.optString("content_types_id").trim().equals("null") && !mainJson.optString("content_types_id").trim().matches("")) {
                            contentDetailsOutput.setContentTypesId(Integer.parseInt(mainJson.optString("content_types_id")));
                        } else {
                            contentDetailsOutput.setContentTypesId(0);

                        }
                    }catch (Exception e){
                        contentDetailsOutput.setContentTypesId(0);
                    }
                    try {
                        if ((mainJson.has("is_converted")) && mainJson.optString("is_converted").trim() != null && !mainJson.optString("is_converted").trim().isEmpty() && !mainJson.optString("is_converted").trim().equals("null") && !mainJson.optString("is_converted").trim().matches("")) {
                            contentDetailsOutput.setIsConverted(Integer.parseInt(mainJson.optString("is_converted")));
                        } else {
                            contentDetailsOutput.setIsConverted(0);

                        }
                    }catch (Exception e){
                        contentDetailsOutput.setIsConverted(0);
                    }

                    try {
                        if ((mainJson.has("is_advance")) && mainJson.optString("is_advance").trim() != null && !mainJson.optString("is_advance").trim().isEmpty() && !mainJson.optString("is_advance").trim().equals("null") && !mainJson.optString("is_advance").trim().matches("")) {
                            contentDetailsOutput.setIsApv(Integer.parseInt(mainJson.optString("is_advance")));
                        } else {
                            contentDetailsOutput.setIsApv(0);

                        }
                    }catch (Exception e){
                        contentDetailsOutput.setIsApv(0);
                    }


                    if (mainJson.has("cast_detail") && mainJson.has("cast_detail") != false && mainJson.optString("cast_detail").trim() != null && !mainJson.optString("cast_detail").trim().isEmpty() && !mainJson.optString("cast_detail").trim().equals("null") && !mainJson.optString("cast_detail").trim().equals("false")) {
                        contentDetailsOutput.setCastStr(true);

                    } else {
                        contentDetailsOutput.setCastStr(false);

                    }

                    if (contentDetailsOutput.getIsPpv() == 1) {
                        JSONObject ppvJson = null;
                        if ((myJson.has("ppv_pricing"))) {

                            PPVModel ppvModel = new PPVModel();
                            ppvJson = myJson.getJSONObject("ppv_pricing");
                            if ((ppvJson.has("price_for_unsubscribed")) && ppvJson.optString("price_for_unsubscribed").trim() != null && !ppvJson.optString("price_for_unsubscribed").trim().isEmpty() && !ppvJson.optString("price_for_unsubscribed").trim().equals("null") && !ppvJson.optString("price_for_unsubscribed").trim().matches("")) {
                                ppvModel.setPPVPriceForUnsubscribedStr(ppvJson.optString("price_for_unsubscribed"));
                            } else {
                                ppvModel.setPPVPriceForUnsubscribedStr("0.0");

                            }
                            if ((ppvJson.has("price_for_subscribed")) && ppvJson.optString("price_for_subscribed").trim() != null && !ppvJson.optString("price_for_subscribed").trim().isEmpty() && !ppvJson.optString("price_for_subscribed").trim().equals("null") && !ppvJson.optString("price_for_subscribed").trim().matches("")) {
                                ppvModel.setPPVPriceForsubscribedStr(ppvJson.optString("price_for_subscribed"));
                            } else {
                                ppvModel.setPPVPriceForsubscribedStr("0.0");

                            }
                            contentDetailsOutput.setPpvDetails(ppvModel);
                        }

                    }
                    if (contentDetailsOutput.getIsApv() == 1) {
                        JSONObject advJson = null;
                        if ((myJson.has("adv_pricing"))) {
                            APVModel aPVModel = new APVModel();

                            advJson = myJson.getJSONObject("adv_pricing");
                            if ((advJson.has("price_for_unsubscribed")) && advJson.optString("price_for_unsubscribed").trim() != null && !advJson.optString("price_for_unsubscribed").trim().isEmpty() && !advJson.optString("price_for_unsubscribed").trim().equals("null") && !advJson.optString("price_for_unsubscribed").trim().matches("")) {
                                aPVModel.setAPVPriceForUnsubscribedStr(advJson.optString("price_for_unsubscribed"));

                            } else {
                                aPVModel.setAPVPriceForUnsubscribedStr("0.0");

                            }
                            if ((advJson.has("price_for_subscribed")) && advJson.optString("price_for_subscribed").trim() != null && !advJson.optString("price_for_subscribed").trim().isEmpty() && !advJson.optString("price_for_subscribed").trim().equals("null") && !advJson.optString("price_for_subscribed").trim().matches("")) {
                                aPVModel.setAPVPriceForsubscribedStr(advJson.optString("price_for_subscribed"));
                            } else {
                                aPVModel.setAPVPriceForsubscribedStr("0.0");

                            }
                            contentDetailsOutput.setApvDetails(aPVModel);

                        }

                    }

                    if (contentDetailsOutput.getIsPpv() == 1 || contentDetailsOutput.getIsApv() == 1) {

                        JSONObject currencyJson = null;
                        if (myJson.has("currency") && myJson.optString("currency") != null && !myJson.optString("currency").equals("null")) {
                            currencyJson = myJson.getJSONObject("currency");
                            CurrencyModel currencyModel = new CurrencyModel();

                            if (currencyJson.has("id") && currencyJson.optString("id").trim() != null && !currencyJson.optString("id").trim().isEmpty() && !currencyJson.optString("id").trim().equals("null") && !currencyJson.optString("id").trim().matches("")) {
                                currencyModel.setCurrencyId(currencyJson.optString("id"));
                            } else {
                                currencyModel.setCurrencyId("");

                            }
                            if (currencyJson.has("country_code") && currencyJson.optString("country_code").trim() != null && !currencyJson.optString("country_code").trim().isEmpty() && !currencyJson.optString("country_code").trim().equals("null") && !currencyJson.optString("country_code").trim().matches("")) {
                                currencyModel.setCurrencyCode(currencyJson.optString("country_code"));
                            } else {
                                currencyModel.setCurrencyCode("");
                            }
                            if (currencyJson.has("symbol") && currencyJson.optString("symbol").trim() != null && !currencyJson.optString("symbol").trim().isEmpty() && !currencyJson.optString("symbol").trim().equals("null") && !currencyJson.optString("symbol").trim().matches("")) {
                                currencyModel.setCurrencySymbol(currencyJson.optString("symbol"));
                            } else {
                                currencyModel.setCurrencySymbol("$.0");
                            }
                            contentDetailsOutput.setCurrencyDetails(currencyModel);


                        }

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
        listener.onGetContentDetailsPreExecuteStarted();

        status = 0;
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);
        }


    }

    /**
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) {
        listener.onGetContentDetailsPostExecuteCompleted(contentDetailsOutput, status, message);

    }


}
