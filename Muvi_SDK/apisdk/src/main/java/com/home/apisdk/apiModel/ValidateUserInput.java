package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetValidateUserAsynTask
 *
 * @author MUVI
 */
public class ValidateUserInput {
    String authToken, userId, muviUniqueId, episodeStreamUniqueId, purchaseType, seasonId, languageCode;

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
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This Method is use to Set the User Id
     *
     * @param userId For Setting The User Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This Method is use to Get the Movie Unique Id
     *
     * @return muviUniqueId
     */
    public String getMuviUniqueId() {
        return muviUniqueId;
    }

    /**
     * This Method is use to Set the Movie Unique Id
     *
     * @param muviUniqueId For Setting The Movie Unique Id
     */
    public void setMuviUniqueId(String muviUniqueId) {
        this.muviUniqueId = muviUniqueId;
    }

    /**
     * This Method is use to Get the Episode Stream Unique Id
     *
     * @return episodeStreamUniqueId
     */
    public String getEpisodeStreamUniqueId() {
        return episodeStreamUniqueId;
    }

    /**
     * This Method is use to Set the Episode Stream Unique Id
     *
     * @param episodeStreamUniqueId For Setting The Episode Stream Unique Id
     */
    public void setEpisodeStreamUniqueId(String episodeStreamUniqueId) {
        this.episodeStreamUniqueId = episodeStreamUniqueId;
    }

    /**
     * This Method is use to Get the Purchase Type
     *
     * @return purchaseType
     */
    public String getPurchaseType() {
        return purchaseType;
    }

    /**
     * This Method is use to Set the Purchase Type
     *
     * @param purchaseType For Setting The Purchase Type
     */
    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    /**
     * This Method is use to Get the Season Id
     *
     * @return seasonId
     */
    public String getSeasonId() {
        return seasonId;
    }

    /**
     * This Method is use to Set the Season Id
     *
     * @param seasonId For Setting The Season Id
     */
    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    /**
     * This Method is use to Get the Language Code
     *
     * @return languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param languageCode For Setting The Language Code
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
