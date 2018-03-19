package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For the AboutUsAsync
 *
 * @author MUVI
 */

public class AboutUsInput {

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
     * This Method is use to get the permalink
     * @return Permalink
     */

    public String getPermalink() {
        return Permalink;
    }

    /**
     * This Method is use to set the permalink
     * @param permalink For Setting The Permalink
     */

    public void setPermalink(String permalink) {
        Permalink = permalink;
    }

    /**
     * This Method is use to get the language code
     * @return lang_code
     */

    public String getLang_code() {
        return lang_code;
    }

    /**
     * This Method is use To Set The Language Code
     * @param lang_code For Setting The Language Code
     */
    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    String Permalink;
    String lang_code;
}
