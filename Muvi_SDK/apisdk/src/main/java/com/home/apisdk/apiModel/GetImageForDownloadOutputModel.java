package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetImageForDownloadAsynTask
 *
 * @author MUVI
 */

public class GetImageForDownloadOutputModel {

    /**
     * This Method is use to Get the Image URL
     *
     * @return imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * This Method is use to Set the Image URL
     *
     * @param imageUrl For Setting The Image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;


}
