package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For the AboutUsAsync
 *
 * @author Abhishek
 */

public class DownloadContentInput {

    String authToken;

    /**
     * This Method is use to get the Auth Token
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to set the Auth token
     * @param authToken For Setting The Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to get the vLink
     * @return Vlink
     */

    public String getvLink() {
        return vLink;
    }

    /**
     * This Method is use to set the permalink
     * @param vLink For Setting The vLink
     */

    public void setvLink(String vLink) {
        vLink = vLink;
    }


    String vLink;
}
