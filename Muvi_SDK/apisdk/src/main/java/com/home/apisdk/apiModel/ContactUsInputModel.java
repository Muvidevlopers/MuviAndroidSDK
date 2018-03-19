package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For ContactUsAsynTask
 *
 * @author MUVI
 */

public class ContactUsInputModel {
    String authToken;
    String email;
    String name;
    String message;

    /**
     * This Method is use to Get the Language Code
     *
     * @return Lang_code
     */
    public String getLang_code() {
        return Lang_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param lang_code For Setting Language Code
     */
    public void setLang_code(String lang_code) {
        Lang_code = lang_code;
    }

    String Lang_code;

    /**
     * This Method is use to Get the Message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This Method is use to Set the Message
     *
     * @param message For Setting The Message
     */

    public void setMessage(String message) {
        this.message = message;
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
     * This Method is use to Get the Email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This Method is use to Set the Email
     *
     * @param email For Setting The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
