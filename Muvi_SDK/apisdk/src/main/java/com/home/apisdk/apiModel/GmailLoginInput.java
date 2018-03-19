package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For AsyncGmailReg
 *
 * @author MUVI
 */

public class GmailLoginInput {
    String name;
    String email;
    String password;
    String authToken;
    String profile_image;

    /**
     * This Method is use to Get the Gmail User Id
     *
     * @return gmail_userid
     */
    public String getGmail_userid() {
        return gmail_userid;
    }

    /**
     * This Method is use to Set the Gmail User Id
     *
     * @param gmail_userid For Setting The Gmail User Id
     */

    public void setGmail_userid(String gmail_userid) {
        this.gmail_userid = gmail_userid;
    }

    String gmail_userid;

    /**
     * This Method is use to Get the User Name
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * This Method is use to Set the User Name
     *
     * @param name For Setting The Name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This Method is use to Get the Gmail Email Id
     *
     * @return email
     */

    public String getEmail() {
        return email;
    }

    /**
     * This Method is use to Set the Gmail Email Id
     *
     * @param email For Setting The Gmail Email Id
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
     * @param password For Setting the Password
     */

    public void setPassword(String password) {
        this.password = password;
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
     * @param authToken For Setting the Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Profile Image
     *
     * @return profile_image
     */

    public String getProfile_image() {
        return profile_image;
    }

    /**
     * This Method is use to Set the Profile Image
     *
     * @param profile_image For Setting the Profile Image
     */

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

}
