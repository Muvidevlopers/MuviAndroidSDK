package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * This Model Class Holds All The Output Attributes For VideoDetailsAsynctask
 *
 * @author MUVI
 */

public class Video_Details_Output {

    String videoResolution;
    String videoUrl;
    String emed_url;

    public boolean isWatermark_status() {
        return watermark_status;
    }

    public void setWatermark_status(boolean watermark_status) {
        this.watermark_status = watermark_status;
    }

    ArrayList<String> SubTitleName = new ArrayList<>();
    String studio_approved_url, licenseUrl;
    String is_offline;
    String channel_id;
    int midRoll;

    boolean watermark_status = false;
    boolean watermark_email = false;

    public boolean isWatermark_email() {
        return watermark_email;
    }

    public void setWatermark_email(boolean watermark_email) {
        this.watermark_email = watermark_email;
    }

    public boolean isWatermark_ip() {
        return watermark_ip;
    }

    public void setWatermark_ip(boolean watermark_ip) {
        this.watermark_ip = watermark_ip;
    }

    public boolean isWatermark_date() {
        return watermark_date;
    }

    public void setWatermark_date(boolean watermark_date) {
        this.watermark_date = watermark_date;
    }

    boolean watermark_ip = false;
    boolean watermark_date = false;

    /**
     * This Method is use to Get the Download Status
     *
     * @return download_status
     */

    public String getDownload_status() {
        return download_status;
    }

    /**
     * This Method is use to Set the Download Status
     *
     * @param download_status For Setting The Download Status
     */

    public void setDownload_status(String download_status) {
        this.download_status = download_status;
    }

    String download_status;

    /**
     * This Method is use to Get the Mid Roll
     *
     * @return midRoll
     */

    public int getMidRoll() {
        return midRoll;
    }

    /**
     * This Method is use to Set the Mid Roll
     *
     * @param midRoll For Setting The Mid Roll
     */

    public void setMidRoll(int midRoll) {
        this.midRoll = midRoll;
    }

    /**
     * This Method is use to Get the Post Roll
     *
     * @return postRoll
     */

    public int getPostRoll() {
        return postRoll;
    }

    /**
     * This Method is use to Set the Post Roll
     *
     * @param postRoll For Setting The Post Roll
     */

    public void setPostRoll(int postRoll) {
        this.postRoll = postRoll;
    }

    /**
     * This Method is use to Get the Pre Roll
     *
     * @return preRoll
     */

    public int getPreRoll() {
        return preRoll;
    }

    /**
     * This Method is use to Set the Pre Roll
     *
     * @param preRoll For Setting The Pre Roll
     */

    public void setPreRoll(int preRoll) {
        this.preRoll = preRoll;
    }

    int postRoll;
    int preRoll;

    /**
     * This Method is use to Get the Ad Network Id
     *
     * @return adNetworkId
     */

    public int getAdNetworkId() {
        return adNetworkId;
    }

    /**
     * This Method is use to Set the Ad Network Id
     *
     * @param adNetworkId For Setting The Ad Network Id
     */

    public void setAdNetworkId(int adNetworkId) {
        this.adNetworkId = adNetworkId;
    }

    int adNetworkId = 1;
    String no_streaming_device;

    /**
     * This Method is use to Get the No Streaming Device
     *
     * @return no_streaming_device
     */

    public String getNo_streaming_device() {
        return no_streaming_device;
    }

    /**
     * This Method is use to Set the No Streaming Device
     *
     * @param no_streaming_device For Setting The No Streaming Device
     */

    public void setNo_streaming_device(String no_streaming_device) {
        this.no_streaming_device = no_streaming_device;
    }

    /**
     * This Method is use to Get the Number Of Views
     *
     * @return no_of_views
     */

    public String getNo_of_views() {
        return no_of_views;
    }

    /**
     * This Method is use to Set the Number Of Views
     *
     * @param no_of_views For Setting The Number Of Views
     */

    public void setNo_of_views(String no_of_views) {
        this.no_of_views = no_of_views;
    }

    /**
     * This Method is use to Get the Trailer Third Party URL
     *
     * @return trailerThirdpartyUrl
     */

    public String getTrailerThirdpartyUrl() {
        return trailerThirdpartyUrl;
    }

    /**
     * This Method is use to Set the Trailer Third Party URL
     *
     * @param trailerThirdpartyUrl For Setting The Trailer Third Party URL
     */

    public void setTrailerThirdpartyUrl(String trailerThirdpartyUrl) {
        this.trailerThirdpartyUrl = trailerThirdpartyUrl;
    }

    /**
     * This Method is use to Get the Embedded Trailer URL
     *
     * @return embedTrailerUrl
     */

    public String getEmbedTrailerUrl() {
        return embedTrailerUrl;
    }

    /**
     * This Method is use to Set the Embedded Trailer URL
     *
     * @param embedTrailerUrl For Setting The Embedded URL
     */

    public void setEmbedTrailerUrl(String embedTrailerUrl) {
        this.embedTrailerUrl = embedTrailerUrl;
    }

    /**
     * This Method is use to Get the Trailer URL
     *
     * @return trailerUrl
     */

    public String getTrailerUrl() {
        return trailerUrl;
    }

    /**
     * This Method is use to Set the Trailer URL
     *
     * @param trailerUrl For Setting The Trailer URL
     */

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    String no_of_views;
    String trailerThirdpartyUrl;
    String embedTrailerUrl;
    String trailerUrl;

    /**
     * This Method is use to Get the Channel Id
     *
     * @return channel_id
     */

    public String getChannel_id() {
        return channel_id;
    }

    /**
     * This Method is use to Set the Channel Id
     *
     * @param channel_id For Setting The Channel Id
     */

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    /**
     * This Method is use to Get the Ad Details
     *
     * @return adDetails
     */

    public String getAdDetails() {
        return adDetails;
    }

    /**
     * This Method is use to Set the Ad Details
     *
     * @param adDetails For Setting The Ad Details
     */

    public void setAdDetails(String adDetails) {
        this.adDetails = adDetails;
    }

    /**
     * This Method is use to Get the Streaming Restriction
     *
     * @return streaming_restriction
     */

    public String getStreaming_restriction() {
        return streaming_restriction;
    }

    /**
     * This Method is use to Set the Streaming Restriction
     *
     * @param streaming_restriction For Setting The Streaming Restriction
     */

    public void setStreaming_restriction(String streaming_restriction) {
        this.streaming_restriction = streaming_restriction;
    }


    String adDetails = "";
    String streaming_restriction;

    /**
     * This Method is use to Get the Offline Details
     *
     * @return is_offline
     */

    public String getIs_offline() {
        return is_offline;
    }

    /**
     * This Method is use to Set the Offline Details
     *
     * @param is_offline For Setting The Offline Details
     */

    public void setIs_offline(String is_offline) {
        this.is_offline = is_offline;
    }

    /**
     * This Method is use to Get the Studio Approved URL
     *
     * @return studio_approved_url
     */

    public String getStudio_approved_url() {
        return studio_approved_url;
    }

    /**
     * This Method is use to Set the Studio Approved URL
     *
     * @param studio_approved_url For Setting The Studio Approved URL
     */

    public void setStudio_approved_url(String studio_approved_url) {
        this.studio_approved_url = studio_approved_url;
    }

    /**
     * This Method is use to Get the License URL
     *
     * @return licenseUrl
     */

    public String getLicenseUrl() {
        return licenseUrl;
    }

    /**
     * This Method is use to Set the License URL
     *
     * @param licenseUrl For Setting The License URL
     */

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    /**
     * This Method is use to Get the Subtitle Name
     *
     * @return SubTitleName
     */

    public ArrayList<String> getSubTitleName() {
        return SubTitleName;
    }

    /**
     * This Method is use to Set the Subtitle Name
     *
     * @param subTitleName For Setting The Subtitle Name
     */

    public void setSubTitleName(ArrayList<String> subTitleName) {
        SubTitleName = subTitleName;
    }

    /**
     * This Method is use to Get the Subtitle Path
     *
     * @return SubTitlePath
     */

    public ArrayList<String> getSubTitlePath() {
        return SubTitlePath;
    }

    /**
     * This Method is use to Set the Subtitle Path
     *
     * @param subTitlePath For Setting The Subtitle Path
     */

    public void setSubTitlePath(ArrayList<String> subTitlePath) {
        SubTitlePath = subTitlePath;
    }

    /**
     * This Method is use to Get the Fake Subtitle Path
     *
     * @return FakeSubTitlePath
     */

    public ArrayList<String> getFakeSubTitlePath() {
        return FakeSubTitlePath;
    }

    /**
     * This Method is use to Set the Fake Subtitle Path
     *
     * @param fakeSubTitlePath For Setting The Fake Subtitle Path
     */

    public void setFakeSubTitlePath(ArrayList<String> fakeSubTitlePath) {
        FakeSubTitlePath = fakeSubTitlePath;
    }

    /**
     * This Method is use to Get the Resolution Format
     *
     * @return ResolutionFormat
     */

    public ArrayList<String> getResolutionFormat() {
        return ResolutionFormat;
    }

    /**
     * This Method is use to Set the Resolution Format
     *
     * @param resolutionFormat For Setting The Resolution Format
     */

    public void setResolutionFormat(ArrayList<String> resolutionFormat) {
        ResolutionFormat = resolutionFormat;
    }

    /**
     * This Method is use to Get the Resolution URL
     *
     * @return ResolutionUrl
     */

    public ArrayList<String> getResolutionUrl() {
        return ResolutionUrl;
    }

    /**
     * This Method is use to Set the Resolution URL
     *
     * @param resolutionUrl For Setting The Resolution URL
     */

    public void setResolutionUrl(ArrayList<String> resolutionUrl) {
        ResolutionUrl = resolutionUrl;
    }

    ArrayList<String> SubTitlePath = new ArrayList<>();
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();
    ArrayList<String> offlineUrl = new ArrayList<>();
    ArrayList<String> offlineLanguage = new ArrayList<>();
    ArrayList<String> SubTitleLanguage = new ArrayList<>();

    /**
     * This Method is use to Get the Offline URL
     *
     * @return offlineUrl
     */

    public ArrayList<String> getOfflineUrl() {
        return offlineUrl;
    }

    /**
     * This Method is use to Set the Offline URL
     *
     * @param offlineUrl For Setting The Offline URL
     */

    public void setOfflineUrl(ArrayList<String> offlineUrl) {
        this.offlineUrl = offlineUrl;
    }

    /**
     * This Method is use to Get the Offline Language
     *
     * @return offlineLanguage
     */

    public ArrayList<String> getOfflineLanguage() {
        return offlineLanguage;
    }

    /**
     * This Method is use to Set the Offline Language
     *
     * @param offlineLanguage For Setting The Offline Language
     */

    public void setOfflineLanguage(ArrayList<String> offlineLanguage) {
        this.offlineLanguage = offlineLanguage;
    }

    /**
     * This Method is use to Get the Subtitle Language
     *
     * @return SubTitleLanguage
     */

    public ArrayList<String> getSubTitleLanguage() {
        return SubTitleLanguage;
    }

    /**
     * This Method is use to Set the Subtitle Language
     *
     * @param subTitleLanguage For Setting The Subtitle Language
     */

    public void setSubTitleLanguage(ArrayList<String> subTitleLanguage) {
        SubTitleLanguage = subTitleLanguage;
    }

    /**
     * This Method is use to Get the Third Party URL
     *
     * @return thirdparty_url
     */

    public String getThirdparty_url() {
        return thirdparty_url;
    }

    /**
     * This Method is use to Set the Third Party URL
     *
     * @param thirdparty_url For Setting The Third Party URL
     */

    public void setThirdparty_url(String thirdparty_url) {
        this.thirdparty_url = thirdparty_url;
    }

    /**
     * This Method is use to Get the Played Length
     *
     * @return played_length
     */

    public String getPlayed_length() {
        return played_length;
    }

    /**
     * This Method is use to Set the Played Length
     *
     * @param played_length For Setting The Played Length
     */

    public void setPlayed_length(String played_length) {
        this.played_length = played_length;
    }

    String thirdparty_url;
    String played_length;

    /**
     * This Method is use to Set the Video Resolution
     *
     * @param videoResolution For Setting The Video Resolution
     */

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    /**
     * This Method is use to Set the Video Resolution
     *
     * @return videoResolution
     */

    public String getVideoResolution() {
        return videoResolution;
    }

    /**
     * This Method is use to Set the Video URL
     *
     * @param videoUrl For Setting The Video URL
     */

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * This Method is use to Get the Video URL
     *
     * @return videoUrl
     */

    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * This Method is use to Set the Embedded URL
     *
     * @param emed_url For Setting The Embedded URL
     */

    public void setEmed_url(String emed_url) {
        this.emed_url = emed_url;
    }

    /**
     * This Method is use to Get the Embedded URL
     *
     * @return emed_url
     */

    public String getEmed_url() {
        return emed_url;
    }
}
