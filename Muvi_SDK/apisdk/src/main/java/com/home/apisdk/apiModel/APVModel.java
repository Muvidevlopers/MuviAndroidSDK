package com.home.apisdk.apiModel;

import java.io.Serializable;

/**
 * This Model Class Holds All The Output Attributes For GetEpisodeDetailsAsynTask
 *
 * @author MUVI
 */

public class APVModel implements Serializable {

    private String apvPriceForUnsubscribedStr;
    private String apvPriceForsubscribedStr;
    private String apvPlanId;
    private String apvShowUnsubscribedStr;
    private String apvShowSubscribedStr;
    private String apvSeasonUnsubscribedStr;
    private String apvSeasonSubscribedStr;
    private String apvEpisodeUnsubscribedStr;
    private String apvEpisodeSubscribedStr;
    private int isShow;
    private int isSeason;
    private int isEpisode;
    String validity_recurrence;
    String pricing_id;
    String validity_period;

    /**
     * This Method is use to get the Validate Recurrence
     *
     * @return validity_recurrence
     */
    public String getValidity_recurrence() {
        return validity_recurrence;
    }

    /**
     * This Method is use to set the Validate Recurrence
     *
     * @param validity_recurrence For Setting The Validate Recurrence
     */
    public void setValidity_recurrence(String validity_recurrence) {
        this.validity_recurrence = validity_recurrence;
    }

    /**
     * This Method is use to get the Pricing ID
     *
     * @return pricing_id
     */
    public String getPricing_id() {
        return pricing_id;
    }

    /**
     * This Method is use to set the Pricing ID
     *
     * @param pricing_id For Setting The Pricing ID
     */
    public void setPricing_id(String pricing_id) {
        this.pricing_id = pricing_id;
    }

    /**
     * This Method is use to get the Validity Period
     *
     * @return validity_period
     */
    public String getValidity_period() {
        return validity_period;
    }

    /**
     * This Method is use to set the Validity Period
     *
     * @param validity_period For Setting The Validity Period
     */
    public void setValidity_period(String validity_period) {
        this.validity_period = validity_period;
    }

    /**
     * This Method is Use to Get The Show Unsubscribe detail
     *
     * @return apvShowUnsubscribedStr
     */
    public String getApvShowUnsubscribedStr() {
        return apvShowUnsubscribedStr;
    }

    /**
     * This Method is Use to Set The Show Unsubscribe detail
     *
     * @param apvShowUnsubscribedStr For Setting The Show Unsubscribe details
     */
    public void setApvShowUnsubscribedStr(String apvShowUnsubscribedStr) {
        this.apvShowUnsubscribedStr = apvShowUnsubscribedStr;
    }

    /**
     * This Method is Use to get The Show subscribe detail
     *
     * @return apvShowSubscribedStr
     */
    public String getApvShowSubscribedStr() {
        return apvShowSubscribedStr;
    }

    /**
     * This Method is Use to Set The Show subscribe detail
     *
     * @param apvShowSubscribedStr For Setting The Show subscribe details
     */
    public void setApvShowSubscribedStr(String apvShowSubscribedStr) {
        this.apvShowSubscribedStr = apvShowSubscribedStr;
    }

    /**
     * This Method is Use to Get The Season Unsubscribe detail
     *
     * @return apvSeasonUnsubscribedStr
     */
    public String getApvSeasonUnsubscribedStr() {
        return apvSeasonUnsubscribedStr;
    }

    /**
     * This Method is Use to Set The Season Unsubscribe detail
     *
     * @param apvSeasonUnsubscribedStr For Setting The Season Unsubscribe details
     */
    public void setApvSeasonUnsubscribedStr(String apvSeasonUnsubscribedStr) {
        this.apvSeasonUnsubscribedStr = apvSeasonUnsubscribedStr;
    }

    /**
     * This Method is Use to Get The Season subscribe detail
     *
     * @return apvSeasonSubscribedStr
     */
    public String getApvSeasonSubscribedStr() {
        return apvSeasonSubscribedStr;
    }

    /**
     * This Method is Use to Set The Season subscribe detail
     *
     * @param apvSeasonSubscribedStr For Setting The Season subscribe details
     */
    public void setApvSeasonSubscribedStr(String apvSeasonSubscribedStr) {
        this.apvSeasonSubscribedStr = apvSeasonSubscribedStr;
    }

    /**
     * This Method is Use to Get The Episode Unsubscribe detail
     *
     * @return apvEpisodeUnsubscribedStr
     */
    public String getApvEpisodeUnsubscribedStr() {
        return apvEpisodeUnsubscribedStr;
    }

    /**
     * This Method is Use to Set The Episode Unsubscribe detail
     *
     * @param apvEpisodeUnsubscribedStr For Setting The Episode Unsubscribe details
     */
    public void setApvEpisodeUnsubscribedStr(String apvEpisodeUnsubscribedStr) {
        this.apvEpisodeUnsubscribedStr = apvEpisodeUnsubscribedStr;
    }

    /**
     * This Method is Use to Get The Episode Subscribe detail
     *
     * @return apvEpisodeSubscribedStr
     */
    public String getApvEpisodeSubscribedStr() {
        return apvEpisodeSubscribedStr;
    }

    /**
     * This Method is Use to Set The Episode Subscribe detail
     *
     * @param apvEpisodeSubscribedStr For Setting The Episode Subscribe details
     */
    public void setApvEpisodeSubscribedStr(String apvEpisodeSubscribedStr) {
        this.apvEpisodeSubscribedStr = apvEpisodeSubscribedStr;
    }

    /**
     * This Method is Use to Get The Show detail
     *
     * @return isShow
     */
    public int getIsShow() {
        return isShow;
    }

    /**
     * This Method is Use to Set The Show detail
     *
     * @param isShow For Setting The Show Details
     */
    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    /**
     * This Method is Use to Get The Season detail
     *
     * @return isSeason
     */
    public int getIsSeason() {
        return isSeason;
    }

    /**
     * This Method is Use to Set The Season detail
     *
     * @param isSeason For Setting The Season Details
     */
    public void setIsSeason(int isSeason) {
        this.isSeason = isSeason;
    }

    /**
     * This Method is Use to Get The Episode detail
     *
     * @return isEpisode
     */
    public int getIsEpisode() {
        return isEpisode;
    }

    /**
     * This Method is Use to Set The Episode detail
     *
     * @param isEpisode For Setting The Episode Details
     */
    public void setIsEpisode(int isEpisode) {
        this.isEpisode = isEpisode;
    }

    /**
     * This Method is Use to Get The Plan ID
     *
     * @return apvPlanId
     */
    public String getApvPlanId() {
        return apvPlanId;
    }

    /**
     * This Method is Use to Set The Plan ID
     *
     * @param apvPlanId For Setting The Plan ID
     */
    public void setApvPlanId(String apvPlanId) {
        this.apvPlanId = apvPlanId;
    }

    /**
     * This Method is Use to Get The Price For Unsubscribe
     *
     * @return apvPriceForUnsubscribedStr
     */
    public String getAPVPriceForUnsubscribedStr() {
        return apvPriceForUnsubscribedStr;
    }

    /**
     * This Method is Use to Set The Price For Unsubscribe
     *
     * @param apvPriceForUnsubscribedStr For Setting The Price For Unsubscribe
     */
    public void setAPVPriceForUnsubscribedStr(String apvPriceForUnsubscribedStr) {
        this.apvPriceForUnsubscribedStr = apvPriceForUnsubscribedStr;
    }

    /**
     * This Method is Use to Get The Price For Subscribe
     *
     * @return apvPriceForsubscribedStr
     */
    public String getAPVPriceForsubscribedStr() {
        return apvPriceForsubscribedStr;
    }

    /**
     * This Method is Use to Set The Price For Subscribe
     *
     * @param apvPriceForsubscribedStr For Setting The Price For Subscribe
     */
    public void setAPVPriceForsubscribedStr(String apvPriceForsubscribedStr) {
        this.apvPriceForsubscribedStr = apvPriceForsubscribedStr;
    }
}
