package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For ForgotpassAsynTask
 *
 * @author MUVI
 */
public class Forgotpassword_input {
    String authToken;
    String email;
    String pakagename;

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
     * This Method is use to Get the Package Name
     *
     * @return pakagename
     */
    public String getPakagename() {
        return pakagename;
    }

    /**
     * This Method is use to Set the Package Name
     *
     * @param pakagename For Setting The Package Name
     */
    public void setPakagename(String pakagename) {
        this.pakagename = pakagename;
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
     * @param authToken For Setting The Auth Token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This Method is use to Set the Email
     *
     * @param email For Setting The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
