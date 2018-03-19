package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Attributes For CastDetailsAsynTask
 *
 * @author MUVI
 */

public class CastAndCrewModel {

    public String celeb_image;
    public String celeb_name;
    public String celeb_id;
    public String permalink;
    public String cast_type;

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

    /**
     * This Method is use to Get the Celebrity Image
     *
     * @return celeb_image
     */
    public String getCeleb_image() {
        return celeb_image;
    }

    /**
     * This Method is use to Set the Celebrity Image
     *
     * @param celeb_image For Setting The Celebrity Image
     */
    public void setCeleb_image(String celeb_image) {
        this.celeb_image = celeb_image;
    }

    /**
     * This Method is use to Get the Celebrity Name
     *
     * @return celeb_name
     */
    public String getCeleb_name() {
        return celeb_name;
    }

    /**
     * This Method is use to Set the Celebrity Name
     *
     * @param celeb_name For Setting The Celebrity Name
     */
    public void setCeleb_name(String celeb_name) {
        this.celeb_name = celeb_name;
    }

    /**
     * This Method is use to Get the Celebrity Id
     *
     * @return celeb_id
     */
    public String getCeleb_id() {
        return celeb_id;
    }

    /**
     * This Method is use to Set the Celebrity Id
     *
     * @param celeb_id For Setting The Celebrity Id
     */
    public void setCeleb_id(String celeb_id) {
        this.celeb_id = celeb_id;
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

}
