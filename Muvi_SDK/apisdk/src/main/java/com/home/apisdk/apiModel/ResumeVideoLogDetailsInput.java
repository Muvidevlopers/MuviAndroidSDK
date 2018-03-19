package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For ResumeVideoLogDetailsAsync
 *
 * @author MUVI
 */
public class ResumeVideoLogDetailsInput {

    String authToken;
    String user_id = "";
    String ip_address;
    String movie_id;
    String episode_id;

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
     * This Method is use to Get the IP Address
     *
     * @return ip_address
     */
    public String getIp_address() {
        return ip_address;
    }

    /**
     * This Method is use to Set the IP Address
     *
     * @param ip_address For Setting The IP Address
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    /**
     * This Method is use to Get the Movie Id
     *
     * @return movie_id
     */
    public String getMovie_id() {
        return movie_id;
    }

    /**
     * This Method is use to Set the Movie Id
     *
     * @param movie_id For Setting The Movie Id
     */
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    /**
     * This Method is use to Get the Episode Id
     *
     * @return episode_id
     */
    public String getEpisode_id() {
        return episode_id;
    }

    /**
     * This Method is use to Set the Episode Id
     *
     * @param episode_id For Setting The Episode Id
     */
    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    /**
     * This Method  is use to Get the Played Lenght
     *
     * @return played_length
     */
    public String getPlayed_length() {
        return played_length;
    }

    /**
     * This Method  is use to Set the Played Lenght
     *
     * @param played_length For Setting The Played Lenght
     */

    public void setPlayed_length(String played_length) {
        this.played_length = played_length;
    }

    /**
     * This Method  is use to Get the Watch Status
     *
     * @return watch_status
     */

    public String getWatch_status() {
        return watch_status;
    }

    /**
     * This Method  is use to Set the Watch Status
     *
     * @param watch_status For Setting The Watch Status
     */

    public void setWatch_status(String watch_status) {
        this.watch_status = watch_status;
    }

    String played_length;
    String watch_status;
}
