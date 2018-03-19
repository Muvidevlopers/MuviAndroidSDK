package com.home.apisdk.apiModel;

import java.util.ArrayList;


/**
 * This Model Class Holds All The Output Attributes For ViewContentRatingAsynTask
 *
 * @author MUVI
 */

public class ViewContentRatingOutputModel {

    ArrayList<Rating> RatingArray = new ArrayList<>();
    int showrating;

    /**
     * This Method is use to Get the Show Rating
     *
     * @return showrating
     */
    public int getShowrating() {
        return showrating;
    }

    /**
     * This Method is use to Set the Show Rating
     *
     * @param showrating For Setting The Show Rating
     */
    public void setShowrating(int showrating) {
        this.showrating = showrating;
    }

    /**
     * This Method is use to Get the Rating Rating
     *
     * @return RatingArray
     */
    public ArrayList<Rating> getRatingArray() {
        return RatingArray;
    }

    /**
     * This Method is use to Set the Rating Rating
     *
     * @param ratingArray For Setting The Rating Rating
     */
    public void setRatingValue(ArrayList<Rating> ratingArray) {
        RatingArray = ratingArray;
    }


    public class Rating {

        String display_name;
        String rating;
        String review;
        String status;


        /**
         * This Method is use to Get the Display Name
         *
         * @return display_name
         */

        public String getDisplay_name() {
            return display_name;
        }

        /**
         * This Method is use to Set the Display Name
         *
         * @param display_name For Setting The Display Name
         */
        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        /**
         * This Method is use to Get the Rating
         *
         * @return rating
         */
        public String getRating() {
            return rating;
        }

        /**
         * This Method is use to Set the Rating
         *
         * @param rating For Setting The Rating
         */
        public void setRating(String rating) {
            this.rating = rating;
        }

        /**
         * This Method is use to Get the Review
         *
         * @return review
         */
        public String getReview() {
            return review;
        }

        /**
         * This Method is use to Set the Review
         *
         * @param review For Setting The Review
         */
        public void setReview(String review) {
            this.review = review;
        }

        /**
         * This Method is use to Get the Status
         *
         * @return status
         */
        public String getStatus() {
            return status;
        }

        /**
         * This Method is use to Set the Status
         *
         * @param status For Setting The Status
         */
        public void setStatus(String status) {
            this.status = status;
        }

    }


}
