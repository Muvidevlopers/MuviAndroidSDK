package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For AddContentRatingAsynTask
 *
 * @author MUVI
 */

public class AddContentRatingInputModel {

    String authToken;
    String content_id;
    String lang_code;
    String user_id;
    String rating;
    String review;

    /**
     * This Method is use to get the Review
     *
     * @return review
     */

    public String getReview() {
        return review;
    }

    /**
     * This Method is use to set the Review
     *
     * @param review for setting the review
     */

    public void setReview(String review) {
        this.review = review;
    }

    /**
     * This Method is use to get the Content Id
     *
     * @return content_id
     */

    public String getContent_id() {
        return content_id;
    }

    /**
     * This Method is use to set the Content Id
     *
     * @param content_id for setting the Content Id
     */

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    /**
     * This Method is use to get the Language Code
     *
     * @return lang_code
     */

    public String getLang_code() {
        return lang_code;
    }

    /**
     * This Method is use to set the Language Code
     *
     * @param lang_code For setting the Language Code
     */

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    /**
     * This Method is use to get the User Id
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to set the User Id
     *
     * @param user_id For Setting the User Id
     */

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to get the Rating
     *
     * @return rating
     */

    public String getRating() {
        return rating;
    }

    /**
     * This Method is use to set the Rating
     *
     * @param rating For Setting The Rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * This Method is use to set the Auth Token
     * @param authToken For Setting The Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to get the Auth Token
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

}
