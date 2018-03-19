package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetFFVideoBufferLogDetailsAsync
 *
 * @author MUVI
 */

public class VideoBufferLogsOutputModel {

    String bufferLogId;
    String bufferLogUniqueId;
    String bufferLocation;

    /**
     * This Method is use to Get the Buffer Location
     *
     * @return bufferLocation
     */
    public String getBufferLocation() {
        return bufferLocation;
    }

    /**
     * This Method is use to Set the Buffer Location
     *
     * @param bufferLocation For Setting The Buffer Location
     */
    public void setBufferLocation(String bufferLocation) {
        this.bufferLocation = bufferLocation;
    }

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
}
