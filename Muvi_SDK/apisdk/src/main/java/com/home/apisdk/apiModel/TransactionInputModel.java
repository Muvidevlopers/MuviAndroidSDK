package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For TransactionDetailsAsynctask
 *
 * @author MUVI
 */
public class TransactionInputModel {
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    String language;
    String authToken;
    String user_id;
    String id;

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
     * This Method is use to Get the Id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * This Method is use to Set the Id
     *
     * @param id For Setting The Id
     */
    public void setId(String id) {
        this.id = id;
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
