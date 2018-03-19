package player.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MUVI on 15-05-2017.
 */

public class Player implements Serializable{

    boolean isLiveStream = false;
    boolean isLandScape = false;

    boolean isThirdPartyPlayer=false;
    public static boolean player_description = true;
    public static boolean landscape = true;
    public static boolean hide_pause = false;
    public static boolean call_finish_at_onUserLeaveHint = true;
    public static String DefaultSubtitle = "Off";
    public static String VideoResolution = "Auto";
    public static final String loadIPUrl = "https://api.ipify.org/?format=json";


    // This code is added by Bibhuprasad Jena //

    public ArrayList<String> offline_url = new ArrayList<>();
    public ArrayList<String> offline_language = new ArrayList<>();
    public ArrayList<String> offline_language_code = new ArrayList<>();

    String downloadStatus = "0";

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setOfflineUrl(ArrayList<String> offline_url) {
        this.offline_url = offline_url;
    }

    public ArrayList<String> getOfflineUrl() {
        return offline_url;
    }

    public void setOfflineLanguage(ArrayList<String> offline_language) {
        this.offline_language = offline_language;
    }

    public ArrayList<String> getOfflineLanguage() {
        return offline_language;
    }


    public void setOffline_language_code(ArrayList<String> offline_language_code) {
        this.offline_language_code = offline_language_code;
    }

    public ArrayList<String> getOffline_language_code() {
        return offline_language_code;
    }

    //********************** END ***************************//

    public static boolean checkNetwork(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected != false;
    }

    public boolean isstreaming_restricted() {
        return isstreaming_restricted;
    }

    public void setIsstreaming_restricted(boolean isstreaming_restricted) {
        this.isstreaming_restricted = isstreaming_restricted;
    }

    private boolean isstreaming_restricted=false;


    public boolean isLandScape() {
        return isLandScape;
    }

    public void setLandScape(boolean landScape) {
        isLandScape = landScape;
    }

    public boolean isLiveStream() {
        return isLiveStream;
    }

    public void setLiveStream(boolean liveStream) {
        isLiveStream = liveStream;
    }


    boolean waterMark=true;


    int playPos = 0;

    String movieUniqueId,streamUniqueId;
    String videoUrl = "";
    String rootUrl="";

    String authTokenStr;
    String userId;
    String emailId;
    String movieId;
    String Episode_id;
    int isFreeContent;
    String videoResolution;
    String licenseUrl,mpdVideoUrl,isOffline="";
    int ContentTypesId = 1;

    public int getAdNetworkId() {
        return adNetworkId;
    }

    public void setAdNetworkId(int adNetworkId) {
        this.adNetworkId = adNetworkId;
    }

    int adNetworkId = 1;
    String channel_id;


    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getAdDetails() {
        return adDetails;
    }

    public void setAdDetails(String adDetails) {
        this.adDetails = adDetails;
    }

    String adDetails="";

    int midRoll = 0;

    public int getMidRoll() {
        return midRoll;
    }

    public void setMidRoll(int midRoll) {
        this.midRoll = midRoll;
    }

    public int getPostRoll() {
        return postRoll;
    }

    public void setPostRoll(int postRoll) {
        this.postRoll = postRoll;
    }

    public int getPreRoll() {
        return preRoll;
    }

    public void setPreRoll(int preRoll) {
        this.preRoll = preRoll;
    }

    int postRoll = 0;
    int preRoll = 0;

    public int getContentTypesId() {
        return ContentTypesId;
    }

    public void setContentTypesId(int contentTypesId) {
        ContentTypesId = contentTypesId;
    }

    public String getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(String isOffline) {
        this.isOffline = isOffline;
    }

    public String getMpdVideoUrl() {
        return mpdVideoUrl;
    }

    public void setMpdVideoUrl(String mpdVideoUrl) {
        this.mpdVideoUrl = mpdVideoUrl;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public int getIsFreeContent() {
        return isFreeContent;
    }

    public void setIsFreeContent(int isFreeContent) {
        this.isFreeContent = isFreeContent;
    }

    public String getEpisode_id() {
        return Episode_id;
    }

    public void setEpisode_id(String episode_id) {
        Episode_id = episode_id;
    }


    ArrayList<String> SubTitleName = new ArrayList<>();

    public static boolean isPlayer_description() {
        return player_description;
    }

    public static void setPlayer_description(boolean player_description) {
        Player.player_description = player_description;
    }

    public ArrayList<String> getSubTitleName() {
        return SubTitleName;
    }

    public void setSubTitleName(ArrayList<String> subTitleName) {
        SubTitleName = subTitleName;
    }

    public ArrayList<String> getSubTitlePath() {
        return SubTitlePath;
    }

    public void setSubTitlePath(ArrayList<String> subTitlePath) {
        SubTitlePath = subTitlePath;
    }

    public ArrayList<String> getResolutionFormat() {
        return ResolutionFormat;
    }

    public void setResolutionFormat(ArrayList<String> resolutionFormat) {
        ResolutionFormat = resolutionFormat;
    }

    public ArrayList<String> getResolutionUrl() {
        return ResolutionUrl;
    }

    public void setResolutionUrl(ArrayList<String> resolutionUrl) {
        ResolutionUrl = resolutionUrl;
    }
    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();



    public ArrayList<String> getSubTitleLanguage() {
        return SubTitleLanguage;
    }

    public void setSubTitleLanguage(ArrayList<String> subTitleLanguage) {
        SubTitleLanguage = subTitleLanguage;
    }

    ArrayList<String> SubTitleLanguage = new ArrayList<>();


    public ArrayList<String> getFakeSubTitlePath() {
        return FakeSubTitlePath;
    }

    public void setFakeSubTitlePath(ArrayList<String> fakeSubTitlePath) {
        FakeSubTitlePath = fakeSubTitlePath;
    }

    private String videoTitle = "";
    private String videoGenre = "";
    /* private String videoDuration = "00:00:00";
     private String videoReleaseDate = "00/00/0000";*/
    private String videoDuration = "";
    private String videoReleaseDate = "";
    private String videoStory = "";
    private boolean castCrew = false;
    private String censorRating = "";
    private String posterImageId = "https://d2gx0xinochgze.cloudfront.net/public/no-image-a.png";

    public boolean isThirdPartyPlayer() {
        return isThirdPartyPlayer;
    }

    public void setThirdPartyPlayer(boolean thirdPartyPlayer) {
        isThirdPartyPlayer = thirdPartyPlayer;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }


    public String getAuthTokenStr() {
        return authTokenStr;
    }

    public void setAuthTokenStr(String authTokenStr) {
        this.authTokenStr = authTokenStr;
    }

    public boolean isWaterMark() {
        return waterMark;
    }

    public void setWaterMark(boolean waterMark) {
        this.waterMark = waterMark;
    }


    public int getPlayPos() {
        return playPos;
    }

    public void setPlayPos(int playPos) {
        this.playPos = playPos;
    }


    public String getMovieUniqueId() {
        return movieUniqueId;
    }

    public void setMovieUniqueId(String movieUniqueId) {
        this.movieUniqueId = movieUniqueId;
    }

    public String getStreamUniqueId() {
        return streamUniqueId;
    }

    public void setStreamUniqueId(String streamUniqueId) {
        this.streamUniqueId = streamUniqueId;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoGenre() {
        return videoGenre;
    }

    public void setVideoGenre(String videoGenre) {
        this.videoGenre = videoGenre;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoReleaseDate() {
        return videoReleaseDate;
    }

    public void setVideoReleaseDate(String videoReleaseDate) {
        this.videoReleaseDate = videoReleaseDate;
    }

    public String getVideoStory() {
        return videoStory;
    }

    public void setVideoStory(String videoStory) {
        this.videoStory = videoStory;
    }

    public boolean isCastCrew() {
        return castCrew;
    }

    public void setCastCrew(boolean castCrew) {
        this.castCrew = castCrew;
    }

    public String getCensorRating() {
        return censorRating;
    }

    public void setCensorRating(String censorRating) {
        this.censorRating = censorRating;
    }

    public String getPosterImageId() {
        return posterImageId;
    }

    public void setPosterImageId(String posterImageId) {
        this.posterImageId = posterImageId;
    }

  /*  public int getContentTypesId() {
        return ContentTypesId;
    }

    public void setContentTypesId(int contentTypesId) {
        ContentTypesId = contentTypesId;
    }
*/


    private String StoryColor;

    public String getStoryColor() {
        return StoryColor;
    }

    public void setStoryColor(String storyColor) {
        StoryColor = storyColor;
    }




}
