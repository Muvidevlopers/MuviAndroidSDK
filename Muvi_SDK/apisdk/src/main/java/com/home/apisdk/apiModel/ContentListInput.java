package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetContentListAsynTask
 *
 * @author Abhishek
 */

public class ContentListInput {
    String limit = "10", offset = "0";
    String orderby;
    String authToken;
    String permalink;
    String country;

    /**
     * This Method is use to Get the Language
     *
     * @return Language
     */
    public String getLanguage() {
        return Language;
    }

    /**
     * This Method is use to Set the Language
     *
     * @param language For Setting The Language
     */
    public void setLanguage(String language) {
        Language = language;
    }

    String Language;

    /*public ContentListInput(String authToken,String permalink,String limit, String offset, String orderby , String country) {
        this.limit = limit;
        this.offset = offset;
        this.orderby = orderby;
        this.authToken = authToken;
        this.permalink = permalink;
        this.country = country;
    }*/

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
     * This Method is use to Get the Offset
     *
     * @return offset
     */
    public String getOffset() {
        return offset;
    }

    /**
     * This Method is use to Set the Offset
     *
     * @param offset For Setting The Offset
     */
    public void setOffset(String offset) {
        this.offset = offset;
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
     * This Method is use to Get the Order BY
     *
     * @return orderby
     */
    public String getOrderby() {
        return orderby;
    }

    /**
     * This Method is use to Set the Order BY
     *
     * @param orderby For Setting The Order By
     */
    public void setOrderby(String orderby) {
        this.orderby = orderby;
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
