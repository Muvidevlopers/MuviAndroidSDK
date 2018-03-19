package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetLoadVideosAsync
 *
 * @author MUVI
 */

public class LoadVideoInput {

    String authToken;

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
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
     * This Method is use to Get the Section Id
     *
     * @return section_id
     */
    public String getSection_id() {
        return section_id;
    }

    /**
     * This Method is use to Set the Section Id
     *
     * @param section_id For Setting The Section Id
     */
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

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

    String section_id;
    String lang_code;
}
