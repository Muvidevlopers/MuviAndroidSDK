package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Attributes For HomePageAsynTask
 *
 * @author MUVI
 */

public class HomePageSectionModel {

    private String studio_id;
    private String section_id;
    private String title;
    private String language_id;


    /**
     * This Method is use to Get the Section Type
     *
     * @return section_type
     */

    public String getSection_type() {
        return section_type;
    }

    /**
     * This Method is use to Set the Section Type
     *
     * @param section_type For Setting The Section Type
     */

    public void setSection_type(String section_type) {
        this.section_type = section_type;
    }

    /**
     * This Method is use to Get the Total
     *
     * @return total
     */

    public String getTotal() {
        return total;
    }

    /**
     * This Method is use to Set the Total
     *
     * @param total For Setting The Total
     */

    public void setTotal(String total) {
        this.total = total;
    }

    String section_type;
    String total;


    /**
     * This Method is use to Get the Language ID
     *
     * @return language_id
     */
    public String getLanguage_id() {
        return language_id;
    }

    /**
     * This Method is use to Set the Language ID
     *
     * @param language_id For Setting The Language Id
     */
    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    /**
     * This Method is use to Get the Studio ID
     *
     * @return studio_id
     */
    public String getStudio_id() {
        return studio_id;
    }

    /**
     * This Method is use to Set the Studio ID
     *
     * @param studio_id For Setting The Studio Id
     */
    public void setStudio_id(String studio_id) {
        this.studio_id = studio_id;
    }

    /**
     * This Method is use to Get the Section ID
     *
     * @return section_id
     */
    public String getSection_id() {
        return section_id;
    }

    /**
     * This Method is use to Set the Section ID
     *
     * @param section_id For Setting The Section Id
     */
    public void setSection_id(String section_id) {
        this.section_id = section_id;
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


}
