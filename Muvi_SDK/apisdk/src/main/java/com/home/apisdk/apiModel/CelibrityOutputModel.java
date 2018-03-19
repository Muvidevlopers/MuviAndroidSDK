package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetCelibrityAsynTask
 *
 * @author MUVI
 */

public class CelibrityOutputModel {

    String name;
    String cast_type;
    String celebrity_image;

    /**
     * This Method is use to Get the Summary
     *
     * @return summary
     */

    public String getSummary() {
        return summary;
    }

    /**
     * This Method is use to Set the Summary
     *
     * @param summary For Setting The Summary
     */

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * This Method is use to Get the Permalink
     *
     * @return permalink
     */

    public String getPermalink() {
        return permalink;
    }

    /**
     * This Method is use to Set the Permalink
     *
     * @param permalink For Setting The Permalink
     */

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    String summary;
    String permalink;

    /**
     * This Method is use to Get the Celebrity Image
     *
     * @return celebrity_image
     */

    public String getCelebrity_image() {
        return celebrity_image;
    }

    /**
     * This Method is use to Set the Celebrity Image
     *
     * @param celebrity_image For Setting The Celebrity Image
     */
    public void setCelebrity_image(String celebrity_image) {
        this.celebrity_image = celebrity_image;
    }

    /**
     * This Method is use to Get the Name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This Method is use to Set the Name
     *
     * @param name For Setting The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This Method is use to Get the Cast Type
     *
     * @return cast_type
     */
    public String getCast_type() {
        return cast_type;
    }

    /**
     * This Method is use to Set the Cast Type
     *
     * @param cast_type For Setting The Cast Type
     */
    public void setCast_type(String cast_type) {
        this.cast_type = cast_type;
    }


}
