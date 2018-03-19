package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For LoginAsynTask
 *
 * @author MUVI
 */

public class Login_input {
    String authToken;
    String email;
    String password;
    String lang_code;
    String device_id;
    String google_id;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    String device_type;

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
     * @param google_id For Setting The Google Id
     */
    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
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
     * This Method is use to Get the Email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This Method is use to Set the Email
     *
     * @param email For Setting The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This Method is use to Get the Password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This Method is use to Set the Password
     *
     * @param password For Setting The Password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
