package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetContentDetailsAsyn
 *
 * @author Abhishek
 */
public class ContentDetailsInput {
    String permalink;
    String authtoken;
    String country;

    /**
     * This Method is use to Get the Country
     *
     * @return country
     */

    public String getCountry() {
        return country;
    }

    /**
     * This Method is use to Set the Country
     *
     * @param country For Setting The Country
     */

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This Method is use to Get the Language
     *
     * @return language
     */

    public String getLanguage() {
        return language;
    }

    /**
     * This Method is use to Set the Language
     *
     * @param language For Setting The Language
     */

    public void setLanguage(String language) {
        this.language = language;
    }

    String language;

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

    String user_id="";

    public ContentDetailsInput() {

    }

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
     * This Method is use to Get the Auth Token
     *
     * @return authtoken
     */

    public String getAuthToken() {
        return authtoken;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authtoken For Setting The Auth Token
     */

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }
}
