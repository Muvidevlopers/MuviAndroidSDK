package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For AddToFavAsync
 *
 * @author MUVI
 */

public class AddToFavInputModel {

    String authToken, movie_uniq_id, isEpisodeStr, loggedInStr;

    /**
     * This Method is use to get the Auth Token
     *
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to set the Auth Token
     *
     * @param authToken For Setting the Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to get the Movie Unique Id
     *
     * @return movie_uniq_id
     */
    public String getMovie_uniq_id() {
        return movie_uniq_id;
    }

    /**
     * This Method is use to set the Movie Unique Id
     *
     * @param movie_uniq_id For Setting The Movie Unique Id
     */

    public void setMovie_uniq_id(String movie_uniq_id) {
        this.movie_uniq_id = movie_uniq_id;
    }

    /**
     * This Method is use to get the Episode ID
     * @return isEpisodeStr
     */

    public String getIsEpisodeStr() {
        return isEpisodeStr;
    }

    /**
     * This Method is use to set the Episode ID
     * @param isEpisodeStr For Setting The Episode ID
     */

    public void setIsEpisodeStr(String isEpisodeStr) {
        this.isEpisodeStr = isEpisodeStr;
    }

    /**
     * This Method is use to get the User ID
     * @return loggedInStr
     */
    public String getLoggedInStr() {
        return loggedInStr;
    }

    /**
     * This Method is use to set the User ID
     * @param loggedInStr For Setting The UserId
     */
    public void setLoggedInStr(String loggedInStr) {
        this.loggedInStr = loggedInStr;
    }
}
