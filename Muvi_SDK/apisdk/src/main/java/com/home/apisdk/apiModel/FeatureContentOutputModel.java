package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetFeatureContentAsynTask
 *
 * @author MUVI
 */
public class FeatureContentOutputModel {
    String genre;
    String name;
    String poster_url;
    String permalink;
    String content_types_id;
    String is_episode;

    int is_converted;
    int is_ppv;
    int is_advance;

    /**
     * This Method is use to Get the Is Advance Details
     *
     * @return is_advance
     */
    public int getIs_advance() {
        return is_advance;
    }

    /**
     * This Method is use to Set the Is Advance Details
     *
     * @param is_advance For Setting The Is Advance Details
     */
    public void setIs_advance(int is_advance) {
        this.is_advance = is_advance;
    }

    /**
     * This Method is use to Get the Is Converted Details
     *
     * @return is_converted
     */
    public int getIs_converted() {
        return is_converted;
    }

    /**
     * This Method is use to Set the Is Converted Details
     *
     * @param is_converted For Setting The Is Converted Details
     */
    public void setIs_converted(int is_converted) {
        this.is_converted = is_converted;
    }

    /**
     * This Method is use to Get the Is PPV Details
     *
     * @return is_ppv
     */
    public int getIs_ppv() {
        return is_ppv;
    }

    /**
     * This Method is use to Set the Is PPV Details
     *
     * @param is_ppv For Setting The Is PPV Details
     */
    public void setIs_ppv(int is_ppv) {
        this.is_ppv = is_ppv;
    }

    /**
     * This Method is use to Get the Genre
     *
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * This Method is use to Set the Genre
     *
     * @param genre For Setting The Genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
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
     * This Method is use to Get the Poste URL
     *
     * @return poster_url
     */
    public String getPoster_url() {
        return poster_url;
    }

    /**
     * This Method is use to Set the Poste URL
     *
     * @param poster_url For Setting the Poste URL
     */
    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
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
     * This Method is use to Get the Content Type ID
     *
     * @return content_types_id
     */
    public String getContent_types_id() {
        return content_types_id;
    }

    /**
     * This Method is use to Set the Content Type ID
     *
     * @param content_types_id For Setting the Content Type ID
     */
    public void setContent_types_id(String content_types_id) {
        this.content_types_id = content_types_id;
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
     * @param is_episode For Setting the Is Episode Details
     */
    public void setIs_episode(String is_episode) {
        this.is_episode = is_episode;
    }


}
