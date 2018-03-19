package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetUserProfileAsynctask
 *
 * @author MUVI
 */

public class Get_UserProfile_Output {

    String id;
    String display_name;
    String email;

    public String getCustom_last_name() {
        return custom_last_name;
    }

    public void setCustom_last_name(String custom_last_name) {
        this.custom_last_name = custom_last_name;
    }

    String custom_last_name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;
    String studio_id;
    String profile_image;
    String isSubscribed;
    String custom_languages;

    /**
     * This Method is use to Get the Custom Language
     *
     * @return custom_languages
     */
    public String getCustom_languages() {
        return custom_languages;
    }

    /**
     * This Method is use to Set the Custom Language
     *
     * @param custom_languages For Setting The Custom Language
     */
    public void setCustom_languages(String custom_languages) {
        this.custom_languages = custom_languages;
    }

    /**
     * This Method is use to Get the Custom Country
     *
     * @return custom_country
     */
    public String getCustom_country() {
        return custom_country;
    }

    /**
     * This Method is use to Set the Custom Country
     *
     * @param custom_country For Setting The Custom Country
     */
    public void setCustom_country(String custom_country) {
        this.custom_country = custom_country;
    }

    String custom_country;

    /**
     * This Method is use to Set the ID
     *
     * @param id For Setting The ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This Method is use to Get the ID
     *
     * @return id
     */
    public String getId() {
        return id;
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
     * This Method is use to Get the Display Name
     *
     * @return display_name
     */
    public String getDisplay_name() {
        return display_name;
    }

    /**
     * This Method is use to Set the Email
     *
     * @param email For Setting The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This Method is use to Get the Email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This Method is use to Set the Studio ID
     *
     * @param studio_id For Setting The Studio ID
     */
    public void setStudio_id(String studio_id) {
        this.studio_id = studio_id;
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
     * This Method is use to Set the Profile Image
     *
     * @param profile_image For Setting The Profile Image
     */
    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    /**
     * This Method is use to Get the Profile Image
     *
     * @return profile_image
     */
    public String getProfile_image() {
        return profile_image;
    }

    /**
     * This Method is use to Set the Is Subscribe Detail
     *
     * @param isSubscribed For Setting The Is Subscribe Detail
     */
    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    /**
     * This Method is use to Get the Is Subscribe Detail
     *
     * @return isSubscribed
     */
    public String getIsSubscribed() {
        return isSubscribed;
    }

}
