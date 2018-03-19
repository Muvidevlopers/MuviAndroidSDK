package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For MyLibraryAsynTask
 *
 * @author MUVI
 */

public class WatchHistoryInputModel {

    String authToken="";
    String user_id="";
    String limit="";

    /**
     * This Method is use to Get the Limit
     *
     * @return limit
     */
    public String getLimit() {
        return limit;
    }

    /**
     * This Method is use to Set the Limit
     *
     * @param limit For Setting The Limit
     */
    public void setLimit(String limit) {
        this.limit = limit;
    }

    /**
     * This Method is use to Get the Off Set
     *
     * @return offset
     */
    public String getOffset() {
        return offset;
    }

    /**
     * This Method is use to Set the Off Set
     *
     * @param offset For Setting The Off Set
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

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

    String offset;
    String country;
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
