package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For GetEpisodeDeatailsAsynTask
 *
 * @author Abhishek
 */
public class Episode_Details_input {
    String permalink, authtoken, limit = "10", offset = "0";

    /**
     * This Method is use to Get the Series Number
     *
     * @return series_number
     */
    public String getSeries_number() {
        return series_number;
    }

    /**
     * This Method is use to Set the Series Number
     *
     * @param series_number For Setting The Serial Number
     */
    public void setSeries_number(String series_number) {
        this.series_number = series_number;
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

    String series_number;
    String lang_code;
    String country;

   /* public Episode_Details_input(String permalink, String authtoken, String limit, String offset) {
        this.permalink = permalink;
        this.authtoken = authtoken;
        this.limit = limit;
        this.offset = offset;
    }*/

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
     * @param permalink For Setting The Country
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authtoken
     */
    public String getAuthtoken() {
        return authtoken;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authtoken For Setting The Auth Token
     */
    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

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
     * This Method is use to Get the OffSet
     *
     * @return offset
     */
    public String getOffset() {
        return offset;
    }

    /**
     * This Method is use to Get the OffSet
     *
     * @param offset For Setting The OffSet
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }
}
