package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For GetVoucherPlanAsynTask
 *
 * @author MUVI
 */


public class GetVoucherPlanInputModel {

    String authToken;
    String movie_id;
    String stream_id;
    String user_id;
    String season;

    /**
     * This Method is use to Get the Season
     *
     * @return season
     */
    public String getSeason() {
        return season;
    }

    /**
     * This Method is use to Set the Season
     *
     * @param season For Setting The Season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * This Method is use to Get the Stream ID
     *
     * @return stream_id
     */
    public String getStream_id() {
        return stream_id;
    }

    /**
     * This Method is use to Set the Stream ID
     *
     * @param stream_id For Setting The Stream ID
     */
    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    /**
     * This Method is use to Get the User ID
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the User ID
     *
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to Get the Movie ID
     *
     * @return movie_id
     */
    public String getMovie_id() {
        return movie_id;
    }

    /**
     * This Method is use to Set the Movie ID
     *
     * @param movie_id For Setting The Movie ID
     */
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
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
