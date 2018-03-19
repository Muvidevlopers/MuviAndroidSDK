package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For CheckDeviceAsyncTask
 *
 * @author MUVI
 */

public class CheckDeviceInput {
    String user_id;
    String authToken;
    String google_id;
    String device;
    String device_type;
    String lang_code;

    /**
     * This Method is use to Get the User ID
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the User ID
     *
     * @param user_id For Setting The User Id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
     * This Method is use to Get the Google ID
     *
     * @return google_id
     */
    public String getGoogle_id() {
        return google_id;
    }

    /**
     * This Method is use to Set the Google ID
     *
     * @param google_id For Setting The Google ID
     */
    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    /**
     * This Method is use to Get the Device
     *
     * @return device
     */
    public String getDevice() {
        return device;
    }

    /**
     * This Method is use to Set the Device
     *
     * @param device For Setting The Device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * This Method is use to Get the Device Type
     *
     * @return device_type
     */
    public String getDevice_type() {
        return device_type;
    }

    /**
     * This Method is use to Set the Device Type
     *
     * @param device_type For Setting The Device Type
     */
    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    /**
     * This Method is use to Get the Language Code
     *
     * @return lang_code
     */
    public String getLang_code() {
        return lang_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param lang_code For Setting The Language Code
     */
    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    /**
     * This Method is use to Get the Device Information
     *
     * @return device_info
     */
    public String getDevice_info() {
        return device_info;
    }

    /**
     * This Method is use to Set the Device Information
     *
     * @param device_info For Setting The Device Information
     */
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    String device_info;
}
