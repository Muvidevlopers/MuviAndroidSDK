package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetInvoicePdfAsynTask
 *
 * @author MUVI
 */

public class GetInvoicePdfInputModel {
    String authToken;
    String id;
    String user_id;
    String device_type;

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

    String lang_code;

    /**
     * This Method is use to Get the Device Type
     *
     * @return device_type
     */
    public String getDevice_type() {
        return device_type;
    }

    /**
     * This Method is use to Set the Device Type
     *
     * @param device_type For Setting The Device Type
     */
    public void setDevice_type(String device_type) {
        this.device_type = device_type;
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
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
     * This Method is use to Set the ID
     *
     * @param id For Setting The ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This Method is use to Get the User ID
     *
     * @return user_id
     */

    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the User ID
     *
     * @param user_id For Setting The User ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
