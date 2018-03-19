package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetMenuListAsynctask
 *
 * @author MUVI
 */

public class MenuListOutput {

    String link_type, display_name, permalink, url;
    boolean isEnable;

    /**
     * This Method is use to Get the URL
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * This Method is use to Set the URL
     *
     * @param url For Setting the URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This Method is use to Get the Is Enable Detail
     *
     * @return isEnable
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * This Method is use to Set the Is Enable Detail
     *
     * @param enable For Setting the Is Enable Detail
     */
    public void setEnable(boolean enable) {
        isEnable = enable;
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
     * @param permalink For Setting the Permalink
     */
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     * This Method is use to Set the Link Type
     *
     * @param link_type For Setting the Link Type
     */
    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    /**
     * This Method is use to Get the Link Type
     *
     * @return link_type
     */
    public String getLink_type() {
        return link_type;
    }

    /**
     * This Method is use to Set the Display Name
     *
     * @param display_name For Setting the Display Name
     */
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    /**
     * This Method is use to Get the Display Name
     *
     * @return display_name
     */
    public String getDisplay_name() {
        return display_name;
    }
}
