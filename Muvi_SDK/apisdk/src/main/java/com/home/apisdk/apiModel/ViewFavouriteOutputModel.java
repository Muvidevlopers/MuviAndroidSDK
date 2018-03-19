package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For ViewFavouriteAsynTask
 *
 * @author MUVI
 */

public class ViewFavouriteOutputModel {
    String movieId;
    String permalink;
    String title;
    String contentTypesId;
    String poster;
    String isEpisodeStr;

    /**
     * This Method is use to Get the Is Episode Details
     *
     * @return isEpisodeStr
     */
    public String getIsEpisodeStr() {
        return isEpisodeStr;
    }

    /**
     * This Method is use to Set the Is Episode Details
     *
     * @param isEpisodeStr For Setting The Is Episode Details
     */
    public void setIsEpisodeStr(String isEpisodeStr) {
        this.isEpisodeStr = isEpisodeStr;
    }

    /**
     * This Method is use to Get the Movie Id
     *
     * @return movieId
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * This Method is use to Set the Movie Id
     *
     * @param movieId For Setting The Movie Id
     */
    public void setMovieId(String movieId) {
        this.movieId = movieId;
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
     * This Method is use to Get the Title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This Method is use to Set the Title
     *
     * @param title For Setting The Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This Method is use to Get the Content Type Id
     *
     * @return contentTypesId
     */
    public String getContentTypesId() {
        return contentTypesId;
    }

    /**
     * This Method is use to Set the Content Type Id
     *
     * @param contentTypesId For Setting The Content Type Id
     */
    public void setContentTypesId(String contentTypesId) {
        this.contentTypesId = contentTypesId;
    }

    /**
     * This Method is use to Get the Poster
     *
     * @return poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * This Method is use to Set the Poster
     *
     * @param poster For Setting The Poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }


}
