package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetFFVideoBufferLogDetailsAsync
 *
 * @author MUVI
 */

public class VideoBufferLogsInputModel {

    String authToken;
    String userId;
    String ipAddress = "";
    String muviUniqueId;
    String episodeStreamUniqueId;
    String deviceType;
    String bufferLogId;

    /**
     * This Method is use to Get the Buffer Log Unique Id
     *
     * @return bufferLogUniqueId
     */

    public String getBufferLogUniqueId() {
        return bufferLogUniqueId;
    }

    /**
     * This Method is use to Set the Buffer Log Unique Id
     *
     * @param bufferLogUniqueId For Setting The Buffer Log Unique Id
     */
    public void setBufferLogUniqueId(String bufferLogUniqueId) {
        this.bufferLogUniqueId = bufferLogUniqueId;
    }

    String bufferLogUniqueId;
    String location;
    String bufferStartTime;
    String bufferEndTime;
    String videoResolution;

    /**
     * This Method is use to Get the Buffer Log Id
     *
     * @return bufferLogId
     */

    public String getBufferLogId() {
        return bufferLogId;
    }

    /**
     * This Method is use to Set the Buffer Log Id
     *
     * @param bufferLogId For Setting The Buffer Log Id
     */

    public void setBufferLogId(String bufferLogId) {
        this.bufferLogId = bufferLogId;
    }

    /**
     * This Method is use to Get the Location
     *
     * @return location
     */

    public String getLocation() {
        return location;
    }

    /**
     * This Method is use to Set the Location
     *
     * @param location For Setting The Location
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This Method is use to Get the Buffer Start Time
     *
     * @return bufferStartTime
     */

    public String getBufferStartTime() {
        return bufferStartTime;
    }

    /**
     * This Method is use to Set the Buffer Start Time
     *
     * @param bufferStartTime For Setting The Buffer Start Time
     */

    public void setBufferStartTime(String bufferStartTime) {
        this.bufferStartTime = bufferStartTime;
    }

    /**
     * This Method is use to Get the Buffer End Time
     *
     * @return bufferEndTime
     */
    public String getBufferEndTime() {
        return bufferEndTime;
    }

    /**
     * This Method is use to Set the Buffer End Time
     *
     * @param bufferEndTime For Setting The Buffer End Time
     */

    public void setBufferEndTime(String bufferEndTime) {
        this.bufferEndTime = bufferEndTime;
    }

    /**
     * This Method is use to Get the Video Resolution
     *
     * @return videoResolution
     */

    public String getVideoResolution() {
        return videoResolution;
    }

    /**
     * This Method is use to Set the Video Resolution
     *
     * @param videoResolution For Settig The Video Resolution
     */

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

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
     * This Method is use to Get the Muvi Unique Id
     *
     * @return muviUniqueId
     */

    public String getMuviUniqueId() {
        return muviUniqueId;
    }

    /**
     * This Method is use to Set the Muvi Unique Id
     *
     * @param muviUniqueId For Setting The Muvi Unique Id
     */

    public void setMuviUniqueId(String muviUniqueId) {
        this.muviUniqueId = muviUniqueId;
    }

    /**
     * This Method is use to Get the Episode Stream Unique Id
     *
     * @return episodeStreamUniqueId
     */

    public String getEpisodeStreamUniqueId() {
        return episodeStreamUniqueId;
    }

    /**
     * This Method is use to Set the Episode Stream Unique Id
     *
     * @param episodeStreamUniqueId For Setting The Episode Stream Unique Id
     */

    public void setEpisodeStreamUniqueId(String episodeStreamUniqueId) {
        this.episodeStreamUniqueId = episodeStreamUniqueId;
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


}
