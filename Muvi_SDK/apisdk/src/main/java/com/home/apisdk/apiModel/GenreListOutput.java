package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetGenreListAsynctask
 *
 * @author MUVI
 */

public class GenreListOutput {

    String Genre_name;

    /**
     * This Method is use to Set the Genre Name
     *
     * @param Genre_name For Setting The Genre Name
     */
    public void setGenre_name(String Genre_name) {
        this.Genre_name = Genre_name;
    }

    /**
     * This Method is use to Get the Genre Name
     *
     * @return Genre_name
     */
    public String getGenre_name() {
        return Genre_name;
    }
}
