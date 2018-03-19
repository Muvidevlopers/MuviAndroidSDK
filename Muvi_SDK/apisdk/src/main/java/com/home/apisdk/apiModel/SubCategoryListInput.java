package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetSubCategoryListAsync
 *
 * @author Abhishek
 */


public class SubCategoryListInput {

    String authToken;

    /**
     * This Method is use to get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Category Id
     *
     * @return category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * This Method is use to Set the Category Id
     *
     * @param category_id For Setting THe Category Id
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    String category_id;
}
