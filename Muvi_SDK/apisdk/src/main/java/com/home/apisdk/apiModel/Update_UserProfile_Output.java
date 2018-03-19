package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For UpadteUserProfileAsynctask
 *
 * @author MUVI
 */
public class Update_UserProfile_Output {

    String name;

    public String getCustom_last_name() {
        return custom_last_name;
    }

    public void setCustom_last_name(String custom_last_name) {
        this.custom_last_name = custom_last_name;
    }

    String custom_last_name;
    String email;
    String nick_name;
    String profile_image;

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    String phone_no;

    /**
     * This Method is use to Set the Name
     *
     * @param name For Setting The Name
     */
    public void setName(String name) {
        this.name = name;
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
     * This Method is use to Set the Nick Name
     *
     * @param nick_name For Setting The Nick Name
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    /**
     * This Method is use to Get the Nick Name
     *
     * @return nick_name
     */
    public String getNick_name() {
        return nick_name;
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

}
