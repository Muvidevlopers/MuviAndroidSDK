package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For DeleteInvoicePdfAsynTask
 *
 * @author Abhishek
 */
public class DeleteInvoicePdfInputModel {
    String authToken;

    /**
     * This Method is use to Get the Language Code
     *
     * @return Language_code
     */
    public String getLanguage_code() {
        return Language_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param language_code For Setting The Language Code
     */
    public void setLanguage_code(String language_code) {
        Language_code = language_code;
    }

    String Language_code;

    /**
     * This Method is use to Get the File Path
     *
     * @return filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * This Method is use to Set the File Path
     *
     * @param filepath For Setting The File Path
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    String filepath;


}
