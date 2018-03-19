package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetVideoLogsAsynTask
 *
 * @author MUVI
 */

public class VideoLogsInputModel {

    String authToken;
    String userId;
    String ipAddress;
    String muviUniqueId;
    String episodeStreamUniqueId;
    String playedLength;
    String watchStatus;
    String deviceType;
    String videoLogId;
    String is_streaming_restriction;
    String contentTypeId;

    /**
     * This Method is use to Get the Content Type Id
     *
     * @return contentTypeId
     */

    public String getContentTypeId() {
        return contentTypeId;
    }

    /**
     * This Method is use to Set the Content Type Id
     *
     * @param contentTypeId For Setting The Content Type Id
     */

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    /**
     * This Method is use to Get the Resume Time
     *
     * @return resumeTime
     */

    public String getResumeTime() {
        return resumeTime;
    }

    /**
     * This Method is use to Set the Resume Time
     *
     * @param resumeTime For Setting The Resume Time
     */

    public void setResumeTime(String resumeTime) {
        this.resumeTime = resumeTime;
    }

    /**
     * This Method is use to Get the Log Item Id
     *
     * @return logTemId
     */

    public String getLogTemId() {
        return logTemId;
    }

    /**
     * This Method is use to Set the Log Item Id
     *
     * @param logTemId For Setting The Log Item Id
     */

    public void setLogTemId(String logTemId) {
        this.logTemId = logTemId;
    }

    String resumeTime;
    String logTemId;

    /**
     * This Method is use to Get the Streaming Restriction Details
     *
     * @return is_streaming_restriction
     */

    public String getIs_streaming_restriction() {
        return is_streaming_restriction;
    }

    /**
     * This Method is use to Set the Streaming Restriction Details
     *
     * @param is_streaming_restriction For Setting The Streaming Restriction Details
     */

    public void setIs_streaming_restriction(String is_streaming_restriction) {
        this.is_streaming_restriction = is_streaming_restriction;
    }

    /**
     * This Method is use to Get the Restrict Stream Id
     *
     * @return restrict_stream_id
     */

    public String getRestrict_stream_id() {
        return restrict_stream_id;
    }

    /**
     * This Method is use to Set the Restrict Stream Id
     *
     * @param restrict_stream_id For Setting The Restrict Stream Id
     */

    public void setRestrict_stream_id(String restrict_stream_id) {
        this.restrict_stream_id = restrict_stream_id;
    }

    String restrict_stream_id;

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the User Id
     *
     * @return userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * This Method is use to Set the User Id
     *
     * @param userId For Setting The User Id
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This Method is use to Get the IP Address
     *
     * @return ipAddress
     */

    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * This Method is use to Set the IP Address
     *
     * @param ipAddress For Setting The IP Address
     */

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * This Method is use to Get the Muvi Unique ID
     *
     * @return muviUniqueId
     */

    public String getMuviUniqueId() {
        return muviUniqueId;
    }

    /**
     * This Method is use to Set the Muvi Unique ID
     *
     * @param muviUniqueId For Setting The Muvi Unique ID
     */

    public void setMuviUniqueId(String muviUniqueId) {
        this.muviUniqueId = muviUniqueId;
    }

    /**
     * This Method is use to Get the Episode Stream Unique ID
     *
     * @return episodeStreamUniqueId
     */

    public String getEpisodeStreamUniqueId() {
        return episodeStreamUniqueId;
    }

    /**
     * This Method is use to Set the Episode Stream Unique ID
     *
     * @param episodeStreamUniqueId For Setting The Episode Stream Unique ID
     */

    public void setEpisodeStreamUniqueId(String episodeStreamUniqueId) {
        this.episodeStreamUniqueId = episodeStreamUniqueId;
    }

    /**
     * This Method is use to Get the Played Length
     *
     * @return playedLength
     */

    public String getPlayedLength() {
        return playedLength;
    }

    /**
     * This Method is use to Set the Played Length
     *
     * @param playedLength For Setting The Played Length
     */

    public void setPlayedLength(String playedLength) {
        this.playedLength = playedLength;
    }

    /**
     * This Method is use to Get the Watch Status
     *
     * @return watchStatus
     */

    public String getWatchStatus() {
        return watchStatus;
    }

    /**
     * This Method is use to Set the Watch Status
     *
     * @param watchStatus For Setting The Watch Status
     */

    public void setWatchStatus(String watchStatus) {
        this.watchStatus = watchStatus;
    }

    /**
     * This Method is use to Get the Device Type
     *
     * @return deviceType
     */

    public String getDeviceType() {
        return deviceType;
    }

    /**
     * This Method is use to Set the Device Type
     *
     * @param deviceType For Setting The Device Type
     */

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * This Method is use to Get the Video Log Id
     *
     * @return videoLogId
     */

    public String getVideoLogId() {
        return videoLogId;
    }

    /**
     * This Method is use to Set the Video Log Id
     *
     * @param videoLogId For Setting The Video Log Id
     */

    public void setVideoLogId(String videoLogId) {
        this.videoLogId = videoLogId;
    }
}
