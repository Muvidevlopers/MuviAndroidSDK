package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For IsRegistrationEnabledAsynTask
 *
 * @author MUVI
 */

public class IsRegistrationEnabledInputModel {

    String authToken;

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
