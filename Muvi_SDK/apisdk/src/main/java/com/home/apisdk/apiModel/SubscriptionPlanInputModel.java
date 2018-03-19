package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetPlanListAsynctask
 *
 * @author MUVI
 */

public class SubscriptionPlanInputModel {
    String authToken;

    /**
     * This Method  is use to Get the Language
     *
     * @return lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * This Method is use to Set the Language
     *
     * @param lang For Setting The Language
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    String lang;

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
}
