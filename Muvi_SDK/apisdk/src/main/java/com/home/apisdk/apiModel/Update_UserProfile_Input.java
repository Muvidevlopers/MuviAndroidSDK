package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For UpadteUserProfileAsynctask
 *
 * @author MUVI
 */

public class Update_UserProfile_Input {

    String authToken;
    String user_id;

    public String getCustom_last_name() {
        return custom_last_name;
    }

    public void setCustom_last_name(String custom_last_name) {
        this.custom_last_name = custom_last_name;
    }

    String custom_last_name;

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    String phone_no;
    String name;
    String password;
    String lang_code;

    /**
     * This Method is use to Get the Language Code
     *
     * @return lang_code
     */

    public String getLang_code() {
        return lang_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param lang_code For Setting The Language Code
     */

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
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

    String custom_country;
    String custom_languages;

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
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to Set the User Id
     *
     * @param user_id For Setting The User Id
     */

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to Get the User Id
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the Password
     *
     * @param password For Setting The Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This Method is use to Get the Password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

}
