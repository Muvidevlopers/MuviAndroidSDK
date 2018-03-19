package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * This Model Class Holds All The Output Attributes For GetAppHomePageAsync
 *
 * @author Abhishek
 */

public class AppHomePageOutput {

    String banner_text;

    /**
     * This Method is use to get the Banner Text
     *
     * @return banner_text
     */

    public String getBanner_text() {
        return banner_text;
    }

    /**
     * This Method is use to Set the Banner Text
     *
     * @param banner_text For Setting The Banner Text
     */

    public void setBanner_text(String banner_text) {
        this.banner_text = banner_text;
    }

    /**
     * This Method is use to get the Is Feature Details
     *
     * @return is_featured
     */

    public String getIs_featured() {
        return is_featured;
    }

    /**
     * This Method is use to set the Is Feature Details
     *
     * @param is_featured For Setting The Feature Details
     */

    public void setIs_featured(String is_featured) {
        this.is_featured = is_featured;
    }

    String is_featured;

    /**
     * This Method is use to get the Home Page Section Model
     *
     * @return homePageSectionModel
     */

    public ArrayList<HomePageSectionModel> getHomePageSectionModel() {
        return homePageSectionModel;
    }

    /**
     * This Method is use to set the Home Page Section Model
     *
     * @param homePageSectionModel For Setting the Home Page Section Model
     */

    public void setHomePageSectionModel(ArrayList<HomePageSectionModel> homePageSectionModel) {
        this.homePageSectionModel = homePageSectionModel;
    }

    ArrayList<HomePageSectionModel> homePageSectionModel;

    /**
     * This Method is use to get the Home Page Banner Model
     *
     * @return homePageBannerModels
     */
    public ArrayList<HomePageBannerModel> getHomePageBannerModels() {
        return homePageBannerModels;
    }

    /**
     * This Method is use to set the Home Page Banner Model
     *
     * @param homePageBannerModels For Setting The Home Page Banner Model
     */

    public void setHomePageBannerModels(ArrayList<HomePageBannerModel> homePageBannerModels) {
        this.homePageBannerModels = homePageBannerModels;
    }

    ArrayList<HomePageBannerModel> homePageBannerModels;


}
