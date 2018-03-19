package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For MyLibraryAsynTask
 *
 * @author MUVI
 */
public class WatchHistoryOutputModel {
    String movieId = "",
            permalink = "", name = "", story = "", releaseDate = "",
            contentTypesId = "", posterUrl = "", genre = "", is_episode = "", muvi_uniq_id = "", movie_stream_uniq_id = "";
    int isConverted;

    int season_id;
    int isfreeContent;

    /**
     * This Method is use to Get the Free Content Details
     *
     * @return isfreeContent
     */

    public int getIsfreeContent() {
        return isfreeContent;
    }

    /**
     * This Method is use to Set the Free Content Details
     *
     * @param isfreeContent For Setting The Free Content Details
     */

    public void setIsfreeContent(int isfreeContent) {
        this.isfreeContent = isfreeContent;
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

    /**
     * This Method is use to Get the Muvi Uniq Id
     *
     * @return muvi_uniq_id
     */

    public String getMuvi_uniq_id() {
        return muvi_uniq_id;
    }

    /**
     * This Method is use to Set the Muvi Unique Id
     *
     * @param muvi_uniq_id For Setting The Muvi Unique Id
     */

    public void setMuvi_uniq_id(String muvi_uniq_id) {
        this.muvi_uniq_id = muvi_uniq_id;
    }

    /**
     * This Method is use to Get the Movie Stream Unique Id
     *
     * @return movie_stream_uniq_id
     */
    public String getMovie_stream_uniq_id() {
        return movie_stream_uniq_id;
    }

    /**
     * This Method is use to Set the Movie Stream Unique Id
     *
     * @param movie_stream_uniq_id For Setting The Movie Stream Unique Id
     */

    public void setMovie_stream_uniq_id(String movie_stream_uniq_id) {
        this.movie_stream_uniq_id = movie_stream_uniq_id;
    }

    /**
     * This Method is use to Get the Season Id
     *
     * @return season_id
     */
    public int getSeason_id() {
        return season_id;
    }

    /**
     * This Method is use to Set the Season Id
     *
     * @param season_id For Setting The Season Id
     */

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
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
     * This Method is use to Get the Story
     *
     * @return story
     */
    public String getStory() {
        return story;
    }

    /**
     * This Method is use to Set the Story
     *
     * @param story For Setting The Story
     */

    public void setStory(String story) {
        this.story = story;
    }

    /**
     * This Method is use to Get the Release Date
     *
     * @return releaseDate
     */

    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * This Method is use to Set the Release Date
     *
     * @param releaseDate For Setting The Release Date
     */

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
     * This Method is use to Get the Poster URL
     *
     * @return posterUrl
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * This Method is use to Set the Poster URL
     *
     * @param posterUrl For Setting The Poster URL
     */

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

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
     * @param genre For Setting The Genre LIst
     */

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * This Method is use to Get the Converted Details
     *
     * @return isConverted
     */
    public int getIsConverted() {
        return isConverted;
    }

    /**
     * This Method is use to Set the Converted Details
     *
     * @param isConverted For Setting The Converted Details
     */

    public void setIsConverted(int isConverted) {
        this.isConverted = isConverted;
    }
}
