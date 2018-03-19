package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetTranslateLanguageAsync
 *
 * @author MUVI
 */

public class LanguageListInputModel {

    String authToken;

    /**
     * This Method is use to Get the Language Code
     *
     * @return langCode
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param langCode For Setting The Language Code
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    String langCode;

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
