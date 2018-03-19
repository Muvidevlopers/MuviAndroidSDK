package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For VideoDetailsAsynctask
 *
 * @author MUVI
 */

public class Video_Log_Output_Model {

    String videoLogId;

    public String getLogTempId() {
        return LogTempId;
    }

    public void setLogTempId(String logTempId) {
        LogTempId = logTempId;
    }

    String LogTempId;

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


}
