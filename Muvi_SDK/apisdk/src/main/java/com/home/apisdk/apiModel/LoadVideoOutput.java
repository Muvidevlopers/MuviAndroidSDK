package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For FeaturedContentLoadVideoAsync
 *
 * @author MUVI
 */

public class LoadVideoOutput {

    String genre;
    String name;
    String poster_url;
    String permalink;
    String content_types_id;

    /**
     * This Method is use to Get the Genre List
     *
     * @return genre
     */

    public String getGenre() {
        return genre;
    }

    /**
     * This Method is use to Set the Genre List
     *
     * @param genre For Setting The Genre List
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
     * This Method is use to Get the Poster URL
     *
     * @return poster_url
     */

    public String getPoster_url() {
        return poster_url;
    }

    /**
     * This Method is use to Set the Poster URL
     *
     * @param poster_url For Setting The Poster URL
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
     * @param permalink For Setting The Permalink
     */

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    /**
     * This Method is use to Get the Content Type Id
     *
     * @return content_types_id
     */

    public String getContent_types_id() {
        return content_types_id;
    }

    /**
     * This Method is use to Set the Content Type Id
     *
     * @param content_types_id For Setting The Content Type Id
     */

    public void setContent_types_id(String content_types_id) {
        this.content_types_id = content_types_id;
    }

    /**
     * This Method is use to Get the Converted Details
     *
     * @return is_converted
     */
    public int getIs_converted() {
        return is_converted;
    }

    /**
     * This Method is use to Set the Converted Details
     *
     * @param is_converted For Setting The Converted Details
     */

    public void setIs_converted(int is_converted) {
        this.is_converted = is_converted;
    }

    /**
     * This Method is use to Get the Advance Details
     *
     * @return is_advance
     */

    public int getIs_advance() {
        return is_advance;
    }

    /**
     * This Method is use to Set the Advance Details
     *
     * @param is_advance For Setting The Advance Details
     */

    public void setIs_advance(int is_advance) {
        this.is_advance = is_advance;
    }

    /**
     * This Method is use to Get the PPV Details
     *
     * @return is_ppv
     */

    public int getIs_ppv() {
        return is_ppv;
    }

    /**
     * This Method is use to Set the PPV Details
     *
     * @param is_ppv
     */

    public void setIs_ppv(int is_ppv) {
        this.is_ppv = is_ppv;
    }

    /**
     * This Method is use to Get the Episode Details
     *
     * @return is_episode
     */

    public String getIs_episode() {
        return is_episode;
    }

    /**
     * This Method is use to Set the Episode Details
     *
     * @param is_episode For Setting The Episode Details
     */

    public void setIs_episode(String is_episode) {
        this.is_episode = is_episode;
    }

    int is_converted;
    int is_advance;
    int is_ppv;
    String is_episode;
}
