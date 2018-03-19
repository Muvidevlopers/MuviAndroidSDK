package com.home.apisdk.apiModel;

/**
 * Created by MUVI on 1/20/2017.
 */

public class IsRegistrationEnabledOutputModel {

    int isMylibrary = 0;
    int is_login = 0;
    int signup_step = 0;
    int has_favourite = 0;
    int rating = 0;
    int isRestrictDevice = 0;

    /**
     * This Method is use to get the Restrict Device Details
     *
     * @return isRestrictDevice
     */

    public int getIsRestrictDevice() {
        return isRestrictDevice;
    }

    /**
     * This Method is use to set the Restrict Device
     *
     * @param isRestrictDevice For Setting The Retricted Device Details
     */

    public void setIsRestrictDevice(int isRestrictDevice) {
        this.isRestrictDevice = isRestrictDevice;
    }

    /**
     * This Method is use to get the Streaming Restriction Details
     *
     * @return is_streaming_restriction
     */

    public int getIs_streaming_restriction() {
        return is_streaming_restriction;
    }

    /**
     * This Method is use to set the Streaming Restriction Details
     *
     * @param is_streaming_restriction For Setting The Streaming Restriction
     */

    public void setIs_streaming_restriction(int is_streaming_restriction) {
        this.is_streaming_restriction = is_streaming_restriction;
    }

    /**
     * This Method is use to get the Chromecast Details
     *
     * @return chromecast
     */
    public int getChromecast() {
        return chromecast;
    }

    /**
     * This Method is use to set the Chromecast Details
     *
     * @param chromecast For Setting The Chromecast
     */
    public void setChromecast(int chromecast) {
        this.chromecast = chromecast;
    }

    /**
     * This Method is use to get the is_offline Details
     *
     * @return is_offline
     */
    public int getIs_offline() {
        return is_offline;
    }

    /**
     * This Method is use to set the is_offline Details
     *
     * @param is_offline For Setting The Is Offline Details
     */
    public void setIs_offline(int is_offline) {
        this.is_offline = is_offline;
    }

    int is_streaming_restriction = 0;
    int chromecast = 0;
    int is_offline = 0;


    /**
     * This Method is use to get the Rating Details
     *
     * @return rating
     */

    public int getRating() {
        return rating;
    }

    /**
     * This Method is use to set the Rating Details
     *
     * @param rating For Setting The Rating Details
     */

    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * This Method is use to get the My Library Details
     *
     * @return isMylibrary
     */

    public int getIsMylibrary() {
        return isMylibrary;
    }

    /**
     * This Method is use to set the My Library Details
     *
     * @param isMylibrary For Setting The My Library Details
     */

    public void setIsMylibrary(int isMylibrary) {
        this.isMylibrary = isMylibrary;
    }

    /**
     * This Method is use to get the Is Login Details
     *
     * @return is_login
     */

    public int getIs_login() {
        return is_login;
    }

    /**
     * This Method is use to set the Is Login Details
     *
     * @param is_login For Setting The Is Login Details
     */

    public void setIs_login(int is_login) {
        this.is_login = is_login;
    }

    /**
     * This Method is use to get the Sign Up Step Details
     *
     * @return signup_step
     */

    public int getSignup_step() {
        return signup_step;
    }

    /**
     * This Method is use to set the Sign Up Step Details
     *
     * @param signup_step For Setting The Sign Up Step Details
     */

    public void setSignup_step(int signup_step) {
        this.signup_step = signup_step;
    }

    /**
     * This Method is use to get the Has Favorite Details
     *
     * @return has_favourite
     */

    public int getHas_favourite() {
        return has_favourite;
    }

    /**
     * This Method is use to set the Has Favorite Details
     *
     * @param has_favourite For Setting The Has Favorite Details
     */

    public void setHas_favourite(int has_favourite) {
        this.has_favourite = has_favourite;
    }


}
