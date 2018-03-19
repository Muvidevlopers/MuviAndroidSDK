package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For VideoDetailsAsynctask
 *
 * @author MUVI
 */

public class GetVideoDetailsInput {
    String authToken;
    String content_uniq_id;
    String stream_uniq_id;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String language;

    /**
     * This Method is use to Get the User ID
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String user_id;
    String internetSpeed = "0";

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to Get the Internet Speed
     *
     * @return internetSpeed
     */
    public String getInternetSpeed() {
        return internetSpeed;
    }

    /**
     * This Method is use to Set the Internet Speed
     *
     * @param internetSpeed For Setting The Internet Speed
     */
    public void setInternetSpeed(String internetSpeed) {
        this.internetSpeed = internetSpeed;
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
     * This Method is use to Get the Content Unique Id
     *
     * @return content_uniq_id
     */
    public String getContent_uniq_id() {
        return content_uniq_id;
    }

    /**
     * This Method is use to Set the Content Unique Id
     *
     * @param content_uniq_id For Setting The Content Unique Id
     */
    public void setContent_uniq_id(String content_uniq_id) {
        this.content_uniq_id = content_uniq_id;
    }

    /**
     * This Method is use to Get the Stream Unique Id
     *
     * @return stream_uniq_id
     */
    public String getStream_uniq_id() {
        return stream_uniq_id;
    }

    /**
     * This Method is use to Set the Stream Unique Id
     *
     * @param stream_uniq_id For Setting The Stream Unique Id
     */
    public void setStream_uniq_id(String stream_uniq_id) {
        this.stream_uniq_id = stream_uniq_id;
    }
}
