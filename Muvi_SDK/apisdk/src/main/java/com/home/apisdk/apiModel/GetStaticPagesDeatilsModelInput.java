package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Output Attributes For GetStaticPagesDetailsAsynTask
 *
 * @author MUVI
 */

public class GetStaticPagesDeatilsModelInput {

    String authToken;
    String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String permalink;

    /**
     * This Method is use to Get the Permalink
     *
     * @return permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * This Method is use to Set the Permalink
     *
     * @param permalink For Setting The Permalink
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
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
