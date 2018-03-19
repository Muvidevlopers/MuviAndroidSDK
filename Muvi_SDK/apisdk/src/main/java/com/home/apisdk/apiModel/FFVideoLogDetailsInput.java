package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetFFVideoLogDetailsAsync
 *
 * @author MUVI
 */

public class FFVideoLogDetailsInput {
    String authToken;
    String user_id;
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
     * This Method is use to Get the Episode ID
     *
     * @return episode_id
     */
    public String getEpisode_id() {
        return episode_id;
    }

    /**
     * This Method is use to Set the Episode ID
     *
     * @param episode_id For Setting The Episode ID
     */
    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    /**
     * This Method is use to Get the Played Lenght
     *
     * @return played_length
     */
    public String getPlayed_length() {
        return played_length;
    }

    /**
     * This Method is use to Set the Played Lenght
     *
     * @param played_length For Setting The Played Lenght
     */
    public void setPlayed_length(String played_length) {
        this.played_length = played_length;
    }

    /**
     * This Method is use to Get the Watch Status
     *
     * @return watch_status
     */
    public String getWatch_status() {
        return watch_status;
    }

    /**
     * This Method is use to Set the Watch Status
     *
     * @param watch_status
     */
    public void setWatch_status(String watch_status) {
        this.watch_status = watch_status;
    }

    /**
     * This Method is use to Get the Device Type
     *
     * @return device_type
     */
    public String getDevice_type() {
        return device_type;
    }

    /**
     * This Method is use to Set the Device Type
     *
     * @param device_type For Setting The Device Type
     */
    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    /**
     * This Method is use to Get the Log Id
     *
     * @return log_id
     */
    public String getLog_id() {
        return log_id;
    }

    /**
     * This Method is use to Set the Log Id
     *
     * @param log_id For Setting The Log Id
     */
    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    String played_length;
    String watch_status;
    String device_type;
    String log_id;
}
