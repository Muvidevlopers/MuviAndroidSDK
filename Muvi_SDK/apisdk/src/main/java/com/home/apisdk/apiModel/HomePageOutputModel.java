package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * This Model Class Holds All The Output Attributes For HomePageAsynTask
 *
 * @author MUVI
 */

public class HomePageOutputModel {

    ArrayList<HomePageBannerModel> homePageBannerModel;
    ArrayList<HomePageSectionModel> homePageSectionModel;

    HomePageTextModel homePageTextModel;

    /**
     * This Method is use to Get the Home Page Text Model
     *
     * @return homePageTextModel
     */

    public HomePageTextModel getHomePageTextModel() {
        return homePageTextModel;
    }

    /**
     * This Method is use to Set the Home Page Text Model
     *
     * @param homePageTextModel For Setting The Home Page Text Model
     */

    public void setHomePageTextModel(HomePageTextModel homePageTextModel) {
        this.homePageTextModel = homePageTextModel;
    }

    /**
     * This Method is use to Get the Home Page Banner Model
     *
     * @return homePageBannerModel
     */

    public ArrayList<HomePageBannerModel> getHomePageBannerModel() {
        return homePageBannerModel;
    }

    /**
     * This Method is use to Set the Home Page Banner Model
     *
     * @param homePageBannerModel For Setting Tha Home Page Banner Model
     */

    public void setHomePageBannerModel(ArrayList<HomePageBannerModel> homePageBannerModel) {
        this.homePageBannerModel = homePageBannerModel;
    }

    /**
     * This Method is use to Get the Home Page Section Model
     *
     * @return homePageSectionModel
     */

    public ArrayList<HomePageSectionModel> getHomePageSectionModel() {
        return homePageSectionModel;
    }

    /**
     * This Method is use to Set the Home Page Section Model
     *
     * @param homePageSectionModel For Setting The Home Page Section Model
     */

    public void setHomePageSectionModel(ArrayList<HomePageSectionModel> homePageSectionModel) {
        this.homePageSectionModel = homePageSectionModel;
    }


}
