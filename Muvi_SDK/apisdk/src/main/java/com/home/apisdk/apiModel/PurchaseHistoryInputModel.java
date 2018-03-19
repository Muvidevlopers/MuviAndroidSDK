package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For PurchaseHistoryAsyntask
 *
 * @author MUVI
 */


public class PurchaseHistoryInputModel {

    String authToken;
    String user_id;

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

    String id;

    /**
     * This Method is use to Get the Language Code
     *
     * @return lang_code
     */

    public String getLang_code() {
        return Lang_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param lang_code For Setting The Language Code
     */

    public void setLang_code(String lang_code) {
        Lang_code = lang_code;
    }

    String Lang_code;

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
