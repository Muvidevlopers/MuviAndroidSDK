package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetCardListForPPVAsynTask
 *
 * @author MUVI
 */

public class GetCardListForPPVInputModel {

    String authToken;
    String user_id;

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
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
