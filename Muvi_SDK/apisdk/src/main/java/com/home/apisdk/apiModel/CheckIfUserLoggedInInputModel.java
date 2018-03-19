package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For CheckIfUserLoggedInAsynTask
 *
 * @author MUVI
 */

public class CheckIfUserLoggedInInputModel {

    String authToken;
    String user_id;
    String device_id;
    String device_type;

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
     * @param user_id For Setting The User Id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

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
     * @param device_id For Setting The Device Id
     */
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
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
