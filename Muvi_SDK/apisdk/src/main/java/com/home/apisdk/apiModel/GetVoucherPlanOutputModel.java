package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetVoucherPlanAsynTask
 *
 * @author MUVI
 */

public class GetVoucherPlanOutputModel {

    String is_show="0";
    String is_episode="0";
    String is_season="0";

    /**
     * This Method is use to Get the Is Season Details
     *
     * @return is_season
     */
    public String getIs_season() {
        return is_season;
    }

    /**
     * This Method is use to Set the Is Season Details
     *
     * @param is_season For Setting The Is Season Details
     */
    public void setIs_season(String is_season) {
        this.is_season = is_season;
    }

    /**
     * This Method is use to Get the Is Show Details
     *
     * @return is_show
     */
    public String getIs_show() {
        return is_show;
    }

    /**
     * This Method is use to Set the Is Show Details
     *
     * @param is_show For Setting The Is Show Details
     */
    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    /**
     * This Method is use to Get the Is Episode Details
     *
     * @return is_episode
     */
    public String getIs_episode() {
        return is_episode;
    }

    /**
     * This Method is use to Set the Is Episode Details
     *
     * @param is_episode For Setting The Is Episode Details
     */
    public void setIs_episode(String is_episode) {
        this.is_episode = is_episode;
    }


}
