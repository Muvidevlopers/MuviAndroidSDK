package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For CheckFbUserDetailsAsyn
 *
 * @author MUVI
 */

public class CheckFbUserDetailsInput {

    String authToken;

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

    String fb_userid;
}
