package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetUserProfileAsynctask
 *
 * @author MUVI
 */

public class Get_UserProfile_Input {

    String authToken;
    String user_id;
    String email;

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

    String lang_code;

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

    /**
     * This Method is use to Set the User ID
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to Get the User ID
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the Email
     * @param email For Setting The EMail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This Method is use to Get the Email
     * @return email
     */
    public String getEmail() {
        return email;
    }

}
