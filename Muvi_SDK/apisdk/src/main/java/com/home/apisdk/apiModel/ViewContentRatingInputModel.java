package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For ViewContentRatingAsynTask
 *
 * @author MUVI
 */
public class ViewContentRatingInputModel {

    String authToken;
    String content_id;
    String user_id;
    String lang_code;

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
     * This Method is use to Get the Content Id
     *
     * @return content_id
     */
    public String getContent_id() {
        return content_id;
    }

    /**
     * This Method is use to Set the Content Id
     *
     * @param content_id For Setting The Content Id
     */
    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

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
