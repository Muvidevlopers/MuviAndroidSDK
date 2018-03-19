package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Attributes For HomePageAsynTask
 *
 * @author MUVI
 */

public class HomePageBannerModel {

    private String original;
    private String mobile_view;

    /**
     * This Method is use to Get the Image Path
     *
     * @return image_path
     */

    public String getImage_path() {
        return image_path;
    }

    /**
     * This Method is use to Set the Image Path
     *
     * @param image_path For Setting The Image Path
     */

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    /**
     * This Method is use to Get the Banner URL
     *
     * @return banner_url
     */
    public String getBanner_url() {
        return banner_url;
    }

    /**
     * This Method is use to Set the Banner URL
     *
     * @param banner_url For Setting The Banner URL
     */

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    private String image_path;
    private String banner_url;

    /**
     * This Method is use to Get the Original
     *
     * @return original
     */

    public String getOriginal() {
        return original;
    }

    /**
     * This Method is use to Set the Original
     *
     * @param original For Setting The Original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

    /**
     * This Method is use to Get the Mobile View
     *
     * @return mobile_view
     */
    public String getMobile_view() {
        return mobile_view;
    }

    /**
     * This Method is use to Set the Mobile View
     *
     * @param mobile_view For Setting The Mobile View
     */
    public void setMobile_view(String mobile_view) {
        this.mobile_view = mobile_view;
    }


}
