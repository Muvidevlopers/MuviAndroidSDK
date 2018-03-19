package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * This Model Class Holds All The Output Attributes For GetCastDetailsAsynTask
 *
 * @author Abhishek
 */
public class GetCastDetailsOutputModel {


    String name = "";
    String summary = "";
    String castImage = "";
    ArrayList<CastDetails> castdetails = new ArrayList<>();

    /**
     * This Method is use to Get the Cast Details
     *
     * @return castdetails
     */

    public ArrayList<CastDetails> getCastdetails() {
        return castdetails;
    }

    /**
     * This Method is use to Set the Cast Details
     *
     * @param castdetails For Setting The Cast Details
     */

    public void setCastdetails(ArrayList<CastDetails> castdetails) {
        this.castdetails = castdetails;
    }

    /**
     * This Method is use to Get the Cast Image
     *
     * @return castImage
     */

    public String getCastImage() {
        return castImage;
    }

    /**
     * This Method is use to Set the Cast Image
     *
     * @param castImage For Setting The Cast Image
     */

    public void setCastImage(String castImage) {
        this.castImage = castImage;
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


    public class CastDetails {
        String genre = "";
        String name = "";
        String posterUrl = "";
        String permalink = "";
        String contentTypesId = "";
        int isConverted;
        int isAdvance;
        int isPPV;

        /**
         * This Method is use to Get the Episode Details
         *
         * @return isEpisode
         */

        public String getIsEpisode() {
            return isEpisode;
        }

        /**
         * This Method is use to Set the Episode Details
         *
         * @param isEpisode For Setting The Episode Details
         */

        public void setIsEpisode(String isEpisode) {
            this.isEpisode = isEpisode;
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

        /**
         * This Method is use to Get the Advance Details
         *
         * @return isAdvance
         */

        public int getIsAdvance() {
            return isAdvance;
        }

        /**
         * This Method is use to Set the Advance Details
         *
         * @param isAdvance For Setting The Advance Details
         */

        public void setIsAdvance(int isAdvance) {
            this.isAdvance = isAdvance;
        }

        /**
         * This Method is use to Get the PPV Details
         *
         * @return isPPV
         */

        public int getIsPPV() {
            return isPPV;
        }

        /**
         * This Method is use to Set the PPV Details
         *
         * @param isPPV For Setting The PPV Details
         */

        public void setIsPPV(int isPPV) {
            this.isPPV = isPPV;
        }

        String isEpisode;
    }


}
