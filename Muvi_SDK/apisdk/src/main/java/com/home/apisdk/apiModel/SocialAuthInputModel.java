package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For SocialAuthAsynTask
 *
 * @author MUVI
 */

public class SocialAuthInputModel {
    String authToken;
    String email;
    String password;
    String name;

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

    String fb_userid;
    String Language;
    String device_type;
    String device_id;

    /**
     * This Method  is use to Get the Language
     *
     * @return Language
     */

    public String getLanguage() {
        return Language;
    }

    /**
     * This Method is use to Set the Language
     *
     * @param language For Setting The Language
     */
    public void setLanguage(String language) {
        Language = language;
    }

    /**
     * This Method is use to Get the Facebook User Id
     *
     * @return fb_userid
     */
    public String getFb_userid() {
        return fb_userid;
    }

    /**
     * This Method is use to Set the Facebook User Id
     *
     * @param fb_userid For Setting The Facebook User Id
     */

    public void setFb_userid(String fb_userid) {
        this.fb_userid = fb_userid;
    }

    /**
     * This Method is use to Get the Name
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * This Method is use to Set the Name
     *
     * @param name For Setting The Name
     */

    public void setName(String name) {
        this.name = name;
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
