package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For LogoutAsynctask
 *
 * @author MUVI
 */

public class LogoutInput {


    String authToken;
    String login_history_id;

    /**
     * This Method  is use to Get the Language Code
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
     * This Method is use to Get the Login History Id
     *
     * @return login_history_id
     */
    public String getLogin_history_id() {
        return login_history_id;
    }

    /**
     * This Method is use to Set the Login History Id
     *
     * @param login_history_id For Setting The Login History Id
     */
    public void setLogin_history_id(String login_history_id) {
        this.login_history_id = login_history_id;
    }
}
