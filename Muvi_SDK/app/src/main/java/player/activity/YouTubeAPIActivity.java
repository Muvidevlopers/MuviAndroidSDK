package player.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.home.vod.R;
import com.home.vod.util.LogUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import player.utils.SensorOrientationChangeNotifier;

//import com.example.muvi.playermodel.Player;

public class YouTubeAPIActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, SensorOrientationChangeNotifier.Listener , ActionBarOverlayLayout.ActionBarVisibilityCallback {

    Toolbar mActionBarToolbar;
    Player playerModel;
    String url;

    String ipAddressStr = "";
    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    YouTubePlayerFragment fragmentYoutube;
    AsynGetIpAddress asynGetIpAddress;
    AsyncVideoLogDetails asyncVideoLogDetails;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyDy9dfNlSYnHlUsM28ayyPH7a7dMIfFoYg-0";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String id_to_end;
    int end_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_api);
        fragmentYoutube = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);

        fragmentYoutube.initialize(YoutubeDeveloperKey, YouTubeAPIActivity.this);
        //playerModel=new Player();
        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");

      /*YoutubeVideoUrl=playerModel.getVideoUrl();
        String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

        public static String getVideoId(YoutubeVideoUrl) {
            if (YoutubeVideoUrl == null || YoutubeVideoUrl.trim().length() <= 0)
                return null;

            Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(YoutubeVideoUrl);

            if (matcher.find())
                return matcher.group(1);
            return null;
        }*/

        if (playerModel.getVideoUrl().matches("")){
            onBackPressed();
        }

        asynGetIpAddress = new AsynGetIpAddress();
        asynGetIpAddress.executeOnExecutor(threadPoolExecutor);

    }

    public boolean isValidWord(String w) {
        return w.matches("[?@%#~.$&^*]");

    }

    String replaceString(String string) {
        String tt = string.replaceAll("[?@%#~.$&^*]",",");
        String[] hh=tt.split(",");
       String id=hh[0];

        String[] tempStr=string.replaceAll("[?@%#~.$&^*]",",").split(",");
        return tempStr[0];
    }

    @Override
    protected void onUserLeaveHint()
    {
        if (asynGetIpAddress!=null){
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails!=null){
            asyncVideoLogDetails.cancel(true);
        }
        SensorOrientationChangeNotifier.getInstance(YouTubeAPIActivity.this).remove(this);
        finish();
        overridePendingTransition(0, 0);
        super.onUserLeaveHint();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (asynGetIpAddress!=null){
            asynGetIpAddress.cancel(true);
        }
        if (asyncVideoLogDetails!=null){
            asyncVideoLogDetails.cancel(true);
        }
        SensorOrientationChangeNotifier.getInstance(YouTubeAPIActivity.this).remove(this);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onWindowVisibilityChanged(int visibility) {

    }

    @Override
    public void showForSystem() {

    }

    @Override
    public void hideForSystem() {

    }

    @Override
    public void enableContentAnimations(boolean enable) {

    }

    @Override
    public void onContentScrollStarted() {

    }

    @Override
    public void onContentScrollStopped() {

    }


    private class AsynGetIpAddress extends AsyncTask<Void, Void, Void> {
        String responseStr;


        @Override
        protected Void doInBackground(Void... params) {

            try {

                // Execute HTTP Post Request
                try {
                    URL myurl = new URL(Player.loadIPUrl);
                    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
                    InputStream ins = con.getInputStream();
                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader in = new BufferedReader(isr);

                    String inputLine;

                    while ((inputLine = in.readLine()) != null)
                    {
                        LogUtil.showLog("BISHAL","response1=="+inputLine);
                        System.out.println(inputLine);
                        responseStr = inputLine;

                    }

                    in.close();


                } catch (org.apache.http.conn.ConnectTimeoutException e){
                    ipAddressStr = "";

                } catch (UnsupportedEncodingException e) {

                    ipAddressStr = "";

                }catch (IOException e) {
                    LogUtil.showLog("BISHAL","Exception="+e.toString());
                    ipAddressStr = "";

                }
                LogUtil.showLog("BISHAL","response=="+responseStr);
                if(responseStr!=null){
                    Object json = new JSONTokener(responseStr).nextValue();
                    if (json instanceof JSONObject){
                        ipAddressStr = ((JSONObject) json).getString("ip");
                        LogUtil.showLog("BISHAL","IpYoutube=="+ipAddressStr);

                    }

                }

            }
            catch (Exception e) {
                ipAddressStr = "";

            }

            return null;
        }


        protected void onPostExecute(Void result) {
            LogUtil.showLog("BISHAL","IpYoutube1=="+ipAddressStr);

            if(responseStr == null){
                ipAddressStr = "";
            }
            if (!ipAddressStr.matches("")) {

                asyncVideoLogDetails = new AsyncVideoLogDetails();
                asyncVideoLogDetails.executeOnExecutor(threadPoolExecutor);
            }else{
                return;
            }
            return;
        }

        protected void onPreExecute() {

        }
    }


    private class AsyncVideoLogDetails extends AsyncTask<Void, Void, Void> {
        //  ProgressDialog pDialog;
        String responseStr;
        String userIdStr ="";
        @Override
        protected Void doInBackground(Void... params) {

            String urlRouteList = playerModel.getRootUrl().trim() + "videoLogs";
            //YoutubeVideoUrl=playerModel.getVideoUrl();
            LogUtil.showLog("BISHAL","YoutubeVideoUrl=="+playerModel.getVideoUrl());
            try {
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlRouteList);
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("authToken", playerModel.getAuthTokenStr().trim());
              /*  SharedPreferences pref = getSharedPreferences(Util.LOGIN_PREF, 0);
                if (pref!=null){
                    userIdStr = pref.getString("PREFS_LOGGEDIN_ID_KEY", null);
                }else{
                    userIdStr="";

                }*/
                if (playerModel.getUserId() != null && playerModel.getUserId().trim().matches("")){
                    userIdStr = playerModel.getUserId();
                }

                httppost.addHeader("user_id", userIdStr.trim());
                httppost.addHeader("ip_address", ipAddressStr.trim());
                httppost.addHeader("movie_id", playerModel.getMovieUniqueId().trim());
                httppost.addHeader("episode_id", playerModel.getEpisode_id().trim());
                httppost.addHeader("played_length", "0");
                httppost.addHeader("watch_status", "start");
                httppost.addHeader("device_type", "2");
                httppost.addHeader("log_id", "0");

                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());

                    LogUtil.showLog("BISHAL","responseStr=="+responseStr);

                } catch (org.apache.http.conn.ConnectTimeoutException e){


                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
            catch (Exception e) {

            }

            return null;
        }


        protected void onPostExecute(Void result) {


            return;

        }

        @Override
        protected void onPreExecute() {

        }


    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeDeveloperKey, this);
        }
    }*/

  /*  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtubeplayerfragment);
    }
*/

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        YPlayer = player;
 /*
 * Now that this variable YPlayer is global you can access it
 * throughout the activity, and perform all the player actions like
 * play, pause and seeking to a position by code.
 */
       // YPlayer.loadVideo(videoUrlStr);

        if (!wasRestored) {
            String vId =
            getYoutubeId();//this method is for get only youtube id from the whole url
            //YPlayer.cueVideo(videoUrlStr);
            YPlayer.loadVideo(vId);
        }


    }

    private String getYoutubeId() {
     //===============this logic is for get id=================
       /* String url =playerModel.getVideoUrl();
        if(url.indexOf("v=") == -1) //there's no video id
            return "";
        id_to_end  = url.split("v=")[1]; //everything before the first 'v=' will be the first element, i.e. [0]
        int end_of_id = id_to_end.indexOf("&"); //if there are other parameters in the url, get only the id's value
        if(end_of_id != -1)
            id_to_end = id_to_end.substring(0, end_of_id);
        return id_to_end;*/


       //===============this is anotherway to get id========================
        String url =playerModel.getVideoUrl();
        String vId = null;

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            vId= matcher.group();
        }
        return vId;



    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorOrientationChangeNotifier.getInstance(YouTubeAPIActivity.this).addListener(this);

    }

    @Override
    public void onOrientationChange(int orientation) {

        if (orientation == 90){


            if (YPlayer!=null) {
                YPlayer.setFullscreen(true);
            }
            // Do some landscape stuff
        }
        if (orientation == 270){
            if (YPlayer!=null) {
                YPlayer.setFullscreen(true);
            }

            // Do some landscape stuff
        }  if (orientation == 180){
            if (YPlayer!=null) {
                YPlayer.setFullscreen(false);

            }

            // Do some landscape stuff
        } if (orientation == 0) {

            if (YPlayer!=null) {
                YPlayer.setFullscreen(false);
            }

        }
    }
}