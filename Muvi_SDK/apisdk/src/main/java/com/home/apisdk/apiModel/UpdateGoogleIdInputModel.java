package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For UpdateGoogleIdAsynTask
 *
 * @author MUVI
 */

public class UpdateGoogleIdInputModel {

    String authToken;
    String google_id;
    String device_id;
    String user_id;

    /**
     * This Method is use to Get the Device Id
     *
     * @return device_id
     */
    public String getDevice_id() {
        return device_id;
    }

    /**
     * This Method is use to Set the Device Id
     *
     * @param device_id For Setting The Device ID
     */
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    /**
     * This Method is use to Get the User Id
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the User Id
     *
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to Get the Google Id
     *
     * @return google_id
     */
    public String getGoogle_id() {
        return google_id;
    }

    /**
     * This Method is use to Set the Google Id
     *
     * @param google_id For Setting The Google ID
     */
    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
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
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

}
