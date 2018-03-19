package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For DeleteFavAsync
 *
 * @author Abhishek
 */

public class DeleteFavInputModel {
    String authTokenStr, movieUniqueId, isEpisode, loggedInStr;

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authTokenStr
     */
    public String getAuthTokenStr() {
        return authTokenStr;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authTokenStr For Setting The Auth Token
     */
    public void setAuthTokenStr(String authTokenStr) {
        this.authTokenStr = authTokenStr;
    }

    /**
     * This Method is use to Get the Movie Unique ID
     *
     * @return movieUniqueId
     */
    public String getMovieUniqueId() {
        return movieUniqueId;
    }

    /**
     * This Method is use to Set the Movie Unique ID
     *
     * @param movieUniqueId For Setting The Movie Unique ID
     */
    public void setMovieUniqueId(String movieUniqueId) {
        this.movieUniqueId = movieUniqueId;
    }

    /**
     * This Method is use to Get the Is Episode Details
     *
     * @return isEpisode
     */
    public String getIsEpisode() {
        return isEpisode;
    }

    /**
     * This Method is use to Set the Is Episode Details
     *
     * @param isEpisode For Setting The Episode Details
     */
    public void setIsEpisode(String isEpisode) {
        this.isEpisode = isEpisode;
    }

    /**
     * This Method is use to Get the Login Status
     *
     * @return loggedInStr
     */
    public String getLoggedInStr() {
        return loggedInStr;
    }

    /**
     * This Method is use to Set the Login Status
     *
     * @param loggedInStr For Setting The Login Status
     */
    public void setLoggedInStr(String loggedInStr) {
        this.loggedInStr = loggedInStr;
    }
}
