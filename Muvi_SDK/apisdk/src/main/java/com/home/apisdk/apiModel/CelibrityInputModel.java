package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetCelibrityAsynTask
 *
 * @author MUVI
 */

public class CelibrityInputModel {

    String authToken;
    String movie_id;

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

    String lang_code;

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
