package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For SearchDataAsynTask
 *
 * @author MUVI
 */

public class Search_Data_input {
    String limit = "10";
    String authToken;
    String offset = "0";
    String q;

    /**
     * This Method  is use to Get the Language Code
     *
     * @return language_code
     */

    public String getLanguage_code() {
        return language_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param language_code For Setting The Language Code
     */

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    String language_code;

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

    String country;

    /**
     * This Method  is use to Get the Limit
     *
     * @return limit
     */
    public String getLimit() {
        return limit;
    }

    /**
     * This Method  is use to Set the Limit
     *
     * @param limit For Setting The Limit
     */

    public void setLimit(String limit) {
        this.limit = limit;
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
     * This Method is use to Get the OffSet
     *
     * @return offset
     */

    public String getOffset() {
        return offset;
    }

    /**
     * This Method is use to Set the OffSet
     *
     * @param offset For Setting The Offset
     */

    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * This Method is use to Get the Q
     *
     * @return q
     */

    public String getQ() {
        return q;
    }

    /**
     * This Method is use to Set the Q
     *
     * @param q For Setting The Q
     */

    public void setQ(String q) {
        this.q = q;
    }
}
