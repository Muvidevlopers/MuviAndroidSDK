package com.home.apisdk.apiModel;

import java.io.Serializable;

/**
 * This Model Class Holds All The PPV Attributes For GetEpisodeDeatailsAsynTask
 *
 * @author MUVI
 */

public class PPVModel implements Serializable {
    private String ppvPriceForUnsubscribedStr;
    private String ppvPriceForsubscribedStr;
    private String ppvPlanId;
    private String ppvShowUnsubscribedStr;
    private String ppvShowSubscribedStr;
    private String ppvSeasonUnsubscribedStr;
    private String ppvSeasonSubscribedStr;
    private String ppvEpisodeUnsubscribedStr;
    private String ppvEpisodeSubscribedStr;
    private int isShow;
    private int isSeason;
    private int isEpisode;
    String validity_recurrence;
    String pricing_id;
    String validity_period;

    /**
     * This Method is use to Set the Pricing Id
     *
     * @param pricing_id For Setting The Pricing Id
     */

    public void setPricing_id(String pricing_id) {
        this.pricing_id = pricing_id;
    }

    /**
     * This Method is use to Get the Validity Recurrence
     *
     * @return validity_recurrence
     */
    public String getValidity_recurrence() {
        return validity_recurrence;
    }

    /**
     * This Method is use to Set the Validity Recurrence
     *
     * @param validity_recurrence For Setting The Validity Recurrence
     */

    public void setValidity_recurrence(String validity_recurrence) {
        this.validity_recurrence = validity_recurrence;
    }

    /**
     * This Method is use to Get the Validity Period
     *
     * @return validity_period
     */

    public String getValidity_period() {
        return validity_period;
    }

    /**
     * This Method is use to Set the Validity Period
     *
     * @param validity_period For Setting The Validity Peroid
     */

    public void setValidity_period(String validity_period) {
        this.validity_period = validity_period;
    }

    /**
     * This Method is use to Get the PPV Show Unsubscribed Details
     *
     * @return ppvShowUnsubscribedStr
     */

    public String getPpvShowUnsubscribedStr() {
        return ppvShowUnsubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Show Unsubscribed Details
     *
     * @param ppvShowUnsubscribedStr For Setting The PPV Show Unsubscribed Details
     */

    public void setPpvShowUnsubscribedStr(String ppvShowUnsubscribedStr) {
        this.ppvShowUnsubscribedStr = ppvShowUnsubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Show Subscribed Details
     *
     * @return ppvShowSubscribedStr
     */

    public String getPpvShowSubscribedStr() {
        return ppvShowSubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Show Subscribed Details
     *
     * @param ppvShowSubscribedStr For Setting The PPV Show Subscribed Details
     */

    public void setPpvShowSubscribedStr(String ppvShowSubscribedStr) {
        this.ppvShowSubscribedStr = ppvShowSubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Season Unsubscribed Details
     *
     * @return ppvSeasonUnsubscribedStr
     */
    public String getPpvSeasonUnsubscribedStr() {
        return ppvSeasonUnsubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Season Unsubscribed Details
     *
     * @param ppvSeasonUnsubscribedStr For Setting The PPV Season Unubscribed Details
     */

    public void setPpvSeasonUnsubscribedStr(String ppvSeasonUnsubscribedStr) {
        this.ppvSeasonUnsubscribedStr = ppvSeasonUnsubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Season Subscribed Details
     *
     * @return ppvSeasonSubscribedStr
     */

    public String getPpvSeasonSubscribedStr() {
        return ppvSeasonSubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Season Subscribed Details
     *
     * @param ppvSeasonSubscribedStr For Setting The PPV Season Subscribed Details
     */

    public void setPpvSeasonSubscribedStr(String ppvSeasonSubscribedStr) {
        this.ppvSeasonSubscribedStr = ppvSeasonSubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Episode Unsubscribed Details
     *
     * @return ppvEpisodeUnsubscribedStr
     */

    public String getPpvEpisodeUnsubscribedStr() {
        return ppvEpisodeUnsubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Episode Unsubscribed Details
     *
     * @param ppvEpisodeUnsubscribedStr For Setting The PPV Episode Unsubscribed Details
     */

    public void setPpvEpisodeUnsubscribedStr(String ppvEpisodeUnsubscribedStr) {
        this.ppvEpisodeUnsubscribedStr = ppvEpisodeUnsubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Episode Subscribed Details
     *
     * @return ppvEpisodeSubscribedStr
     */
    public String getPpvEpisodeSubscribedStr() {
        return ppvEpisodeSubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Episode Subscribed Details
     *
     * @param ppvEpisodeSubscribedStr For Setting The PPV Episode Subscribed Details
     */
    public void setPpvEpisodeSubscribedStr(String ppvEpisodeSubscribedStr) {
        this.ppvEpisodeSubscribedStr = ppvEpisodeSubscribedStr;
    }

    /**
     * This Method is use to Get the Show Details
     *
     * @return isShow
     */
    public int getIsShow() {
        return isShow;
    }

    /**
     * This Method is use to Set the Show Details
     *
     * @param isShow For Setting The Show Details
     */

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    /**
     * This Method is use to Get the Season Details
     *
     * @return isSeason
     */
    public int getIsSeason() {
        return isSeason;
    }

    /**
     * This Method is use to Set the Season Details
     *
     * @param isSeason For Setting The Season Details
     */

    public void setIsSeason(int isSeason) {
        this.isSeason = isSeason;
    }

    /**
     * This Method is use to Get the Episode Details
     *
     * @return isEpisode
     */

    public int getIsEpisode() {
        return isEpisode;
    }

    /**
     * This Method is use to Set the Episode Details
     *
     * @param isEpisode For Setting The Episode Details
     */

    public void setIsEpisode(int isEpisode) {
        this.isEpisode = isEpisode;
    }

    /**
     * This Method is use to Get the PPV Plan ID
     *
     * @return ppvPlanId
     */
    public String getPpvPlanId() {
        return ppvPlanId;
    }

    /**
     * This Method is use to Set the PPV Plan ID
     *
     * @param ppvPlanId For Setting The PPV PLan ID
     */

    public void setPpvPlanId(String ppvPlanId) {
        this.ppvPlanId = ppvPlanId;
    }

    /**
     * This Method is use to Get the PPV Price For Unsubscribe
     *
     * @return ppvPriceForUnsubscribedStr
     */

    public String getPPVPriceForUnsubscribedStr() {
        return ppvPriceForUnsubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Price For Unsubscribe
     *
     * @param ppvPriceForUnsubscribedStr For Setting The PPV Price For Unsubscribe
     */

    public void setPPVPriceForUnsubscribedStr(String ppvPriceForUnsubscribedStr) {
        this.ppvPriceForUnsubscribedStr = ppvPriceForUnsubscribedStr;
    }

    /**
     * This Method is use to Get the PPV Price For Subscribe
     *
     * @return ppvPriceForsubscribedStr
     */
    public String getPPVPriceForsubscribedStr() {
        return ppvPriceForsubscribedStr;
    }

    /**
     * This Method is use to Set the PPV Price For Subscribe
     *
     * @param ppvPriceForsubscribedStr For Setting The PPV Price For Subscribe
     */

    public void setPPVPriceForsubscribedStr(String ppvPriceForsubscribedStr) {
        this.ppvPriceForsubscribedStr = ppvPriceForsubscribedStr;
    }
}
