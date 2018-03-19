package com.home.apisdk.apiModel;

/**
 * Created by MUVI on 11/8/2017.
 */

public class LoadFilterVideoOutput {

    String movieId;
    String permalink;
    String name;
    String story;
    String releaseDate;
    String contentTypesId;
    String posterUrl;
    String genre;
    String isEpisodeStr;
    int isConverted,isPPV,isAPV;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContentTypesId() {
        return contentTypesId;
    }

    public void setContentTypesId(String contentTypesId) {
        this.contentTypesId = contentTypesId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsEpisodeStr() {
        return isEpisodeStr;
    }

    public void setIsEpisodeStr(String isEpisodeStr) {
        this.isEpisodeStr = isEpisodeStr;
    }

    public int getIsConverted() {
        return isConverted;
    }

    public void setIsConverted(int isConverted) {
        this.isConverted = isConverted;
    }

    public int getIsPPV() {
        return isPPV;
    }

    public void setIsPPV(int isPPV) {
        this.isPPV = isPPV;
    }

    public int getIsAPV() {
        return isAPV;
    }

    public void setIsAPV(int isAPV) {
        this.isAPV = isAPV;
    }
}
