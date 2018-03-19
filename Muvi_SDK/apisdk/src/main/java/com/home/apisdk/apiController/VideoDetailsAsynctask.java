/**
 * SDK initialization, platform and device information classes.
 */


package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.Video_Details_Output;

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
 * This Class provides all the details related to the video such as video lenght, resolution etc.
 *
 * @author MUVI
 */

public class VideoDetailsAsynctask extends AsyncTask<GetVideoDetailsInput, Void, Void> {

    private GetVideoDetailsInput getVideoDetailsInput;
    private ArrayList<String> SubTitleName = new ArrayList<>();
    private ArrayList<String> SubTitlePath = new ArrayList<>();
    private ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    private ArrayList<String> ResolutionFormat = new ArrayList<>();
    private ArrayList<String> adchannel = new ArrayList<>();
    private ArrayList<String> adnetworkid = new ArrayList<>();
    private ArrayList<String> offline_url = new ArrayList<>();
    private ArrayList<String> offline_language = new ArrayList<>();
    private ArrayList<String> subtitle_code = new ArrayList<>();
    private ArrayList<String> ResolutionUrl = new ArrayList<>();
    private String PACKAGE_NAME;
    private String message;
    private String responseStr;
    private String status;
    private JSONArray SubtitleJosnArray = null;
    private JSONArray ResolutionJosnArray = null;
    private int code;
    private Video_Details_Output _video_details_output;
    private VideoDetailsListener listener;
    private Context context;
    int watermark_status;

    /**
     * Interface used to allow the caller of a VideoDetailsAsynctask to run some code when get
     * responses.
     */

    public interface VideoDetailsListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onVideoDetailsPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param _video_details_output A Model Class which contain responses. To get that responses we need to call the respective getter methods.
         * @param code                  Response Code from the server
         * @param status                For Getting The Status
         * @param message               On Success Message
         */

        void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int code, String status, String message);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param getVideoDetailsInput A Model Class which is use for background task, we need to set all the attributes through setter methods of input model class,
     *                             For Example: to use this API we have to set following attributes:
     *                             setAuthToken(),setContent_uniq_id() etc.
     * @param listener             VideoDetails Listener
     * @param context              android.content.Context
     */


    public VideoDetailsAsynctask(GetVideoDetailsInput getVideoDetailsInput, VideoDetailsListener listener, Context context) {
        this.listener = listener;
        this.context = context;


        this.getVideoDetailsInput = getVideoDetailsInput;
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);
        Log.v("MUVISDK", "VideoDetailsAsynctask");

    }

    /**
     * Background thread to execute.
     *
     * @return null
     * @throws org.apache.http.conn.ConnectTimeoutException,IOException,JSONException
     */

    @Override
    protected Void doInBackground(GetVideoDetailsInput... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIUrlConstant.getVideoDetailsUrl());
            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");

            httppost.addHeader(HeaderConstants.AUTH_TOKEN, this.getVideoDetailsInput.getAuthToken());
            httppost.addHeader(HeaderConstants.CONTENT_UNIQ_ID, this.getVideoDetailsInput.getContent_uniq_id());
            httppost.addHeader(HeaderConstants.STREAM_UNIQ_ID, this.getVideoDetailsInput.getStream_uniq_id());
            httppost.addHeader(HeaderConstants.INTERNET_SPEED, this.getVideoDetailsInput.getInternetSpeed());
            httppost.addHeader(HeaderConstants.USER_ID, this.getVideoDetailsInput.getUser_id());
            httppost.addHeader(HeaderConstants.LANG_CODE, this.getVideoDetailsInput.getLanguage());

            // Execute HTTP Post Request
            try {
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("MUVISDK", "RES" + responseStr);

            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                code = 0;
                message = "";
                status = "";

            } catch (IOException e) {
                code = 0;
                message = "";
                status = "";

            }
            JSONArray SubtitleJosnArray = null;
            JSONArray ResolutionJosnArray = null;
            JSONArray JasonAdNetwok = null;
            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                code = Integer.parseInt(myJson.optString("code"));
                message = myJson.optString("msg");
                SubtitleJosnArray = myJson.optJSONArray("subTitle");
                ResolutionJosnArray = myJson.optJSONArray("videoDetails");
                status = myJson.optString("status");
            }

            if (code == 200) {
                try {
                    _video_details_output = new Video_Details_Output();
                    _video_details_output.setVideoResolution(myJson.optString("videoResolution"));
                    _video_details_output.setVideoUrl(myJson.optString("videoUrl"));
                    _video_details_output.setEmed_url(myJson.optString("emed_url"));
                    if ((myJson.has("played_length")) && myJson.getString("played_length").trim() != null && !myJson.getString("played_length").trim().isEmpty() && !myJson.getString("played_length").trim().equals("null") && !myJson.getString("played_length").trim().matches("")) {
                        _video_details_output.setPlayed_length(myJson.optString("played_length"));
                    } else {
                        _video_details_output.setPlayed_length("0");
                    }
                    _video_details_output.setThirdparty_url(myJson.optString("thirdparty_url"));
                    _video_details_output.setStudio_approved_url(myJson.optString("studio_approved_url"));
                    _video_details_output.setLicenseUrl(myJson.optString("licenseUrl"));
                    if ((myJson.has("is_offline")) && myJson.getString("is_offline").trim() != null && !myJson.getString("is_offline").trim().isEmpty() && !myJson.getString("is_offline").trim().equals("null") && !myJson.getString("is_offline").trim().matches("")) {
                        _video_details_output.setIs_offline(myJson.optString("is_offline"));
                    } else {
                        _video_details_output.setIs_offline("");
                    }
                    _video_details_output.setStreaming_restriction(myJson.optString("streaming_restriction"));
                    _video_details_output.setEmbedTrailerUrl(myJson.optString("embedTrailerUrl"));
                    _video_details_output.setEmed_url(myJson.optString("emed_url"));
                    _video_details_output.setNo_streaming_device(myJson.optString("no_streaming_device"));
                    _video_details_output.setNo_of_views(myJson.optString("no_of_views"));
                    _video_details_output.setTrailerUrl(myJson.optString("trailerUrl"));
                    _video_details_output.setDownload_status(myJson.optString("download_status"));


                } catch (Exception e) {
                    code = 0;
                    message = "";
                    status = "";
                }
                if (SubtitleJosnArray != null) {
                    if (SubtitleJosnArray.length() > 0) {
                        for (int i = 0; i < SubtitleJosnArray.length(); i++) {


                            SubTitleName.add(SubtitleJosnArray.getJSONObject(i).optString("language").trim());
                            FakeSubTitlePath.add(SubtitleJosnArray.getJSONObject(i).optString("url").trim());
                            subtitle_code.add(SubtitleJosnArray.getJSONObject(i).optString("code").trim());
                            offline_url.add(SubtitleJosnArray.getJSONObject(i).optString("url").trim());
                            offline_language.add(SubtitleJosnArray.getJSONObject(i).optString("language").trim());




                        }

                        _video_details_output.setSubTitleName(SubTitleName);
                        _video_details_output.setFakeSubTitlePath(FakeSubTitlePath);
                        _video_details_output.setSubTitleLanguage(subtitle_code);
                        _video_details_output.setOfflineUrl(offline_url);
                        _video_details_output.setOfflineLanguage(offline_language);
                    }
                }

                /******Resolution****/

                if (ResolutionJosnArray != null) {
                    if (ResolutionJosnArray.length() > 0) {
                        for (int i = 0; i < ResolutionJosnArray.length(); i++) {
                            if ((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()).equals("BEST")) {
                                ResolutionFormat.add(ResolutionJosnArray.getJSONObject(i).optString("resolution").trim());
                            } else {
                                ResolutionFormat.add((ResolutionJosnArray.getJSONObject(i).optString("resolution").trim()) + "p");
                            }

                            ResolutionUrl.add(ResolutionJosnArray.getJSONObject(i).optString("url").trim());

                            Log.v("MUVISDK", "Resolution Format Name =" + ResolutionJosnArray.getJSONObject(i).optString("resolution").trim());
                            Log.v("MUVISDK", "Resolution url =" + ResolutionJosnArray.getJSONObject(i).optString("url").trim());
                        }

                        _video_details_output.setResolutionFormat(ResolutionFormat);
                        _video_details_output.setResolutionUrl(ResolutionUrl);
                    }

                }

                if (myJson.has("adsDetails")) {
                    JSONObject adJosnDetails = myJson.getJSONObject("adsDetails");
                    JSONArray adJosnArray = null;
                    if (adJosnDetails.has("adNetwork")) {
                        adJosnArray = adJosnDetails.optJSONArray("adNetwork");
                        if (adJosnArray != null) {
                            if (adJosnArray.length() > 0) {
                                for (int i = 0; i < adJosnArray.length(); i++) {
                                    if (adJosnArray.getJSONObject(i).has("channel_id"))
                                        _video_details_output.setChannel_id(adJosnArray.getJSONObject(i).optString("channel_id").trim());
                                    if (adJosnArray.getJSONObject(i).has("ad_network_id"))
                                        try {
                                            _video_details_output.setAdNetworkId(adJosnArray.getJSONObject(i).optInt("ad_network_id"));
                                        }catch (Exception e){
                                            _video_details_output.setAdNetworkId(0);
                                        }
                                }

                            }
                        }
                    }

                    if (adJosnDetails.has("adsTime")) {
                        JSONObject adTimeJosnDetails = adJosnDetails.getJSONObject("adsTime");
                        try {
                            _video_details_output.setMidRoll(Integer.parseInt(adTimeJosnDetails.optString("mid")));
                        }catch (Exception e){
                            _video_details_output.setMidRoll(0);
                        }
                        try {
                            _video_details_output.setPreRoll(Integer.parseInt(adTimeJosnDetails.optString("start")));
                        }catch (Exception e){
                            _video_details_output.setPreRoll(0);
                        }
                        try {
                            _video_details_output.setPostRoll(Integer.parseInt(adTimeJosnDetails.optString("end")));
                        }catch (Exception e){
                            _video_details_output.setPostRoll(0);
                        }

                        if (_video_details_output.getMidRoll() == 1) {
                            _video_details_output.setAdDetails(adTimeJosnDetails.optString("midroll_values"));
                        }
                    }
                }
                if (myJson.has("is_watermark")) {
                    JSONObject mainJson = myJson.getJSONObject("is_watermark");
                    _video_details_output.setWatermark_status(mainJson.optInt("status")==1?true:false);
                    if (_video_details_output.isWatermark_status()) {
                        _video_details_output.setWatermark_email(mainJson.optString("email").equals("1")?true:false);
                        _video_details_output.setWatermark_date(mainJson.optString("date").equals("1")?true:false);
                        _video_details_output.setWatermark_ip(mainJson.optString("ip").equals("1")?true:false);
                    }
                }
            }
        } catch (Exception e) {
            code = 0;
            message = "";
            status = "";
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onVideoDetailsPreExecuteStarted();
        code = 0;
        status = "";
        if (!PACKAGE_NAME.equals(SDKInitializer.getUser_Package_Name_At_Api(context))) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onVideoDetailsPostExecuteCompleted(_video_details_output, code, status, message);
            return;
        }
        if (SDKInitializer.getHashKey(context).equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onVideoDetailsPostExecuteCompleted(_video_details_output, code, status, message);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onVideoDetailsPostExecuteCompleted(_video_details_output, code, status, message);
    }
}
