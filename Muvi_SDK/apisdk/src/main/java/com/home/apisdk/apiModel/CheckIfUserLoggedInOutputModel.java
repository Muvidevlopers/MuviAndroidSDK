package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For CheckIfUserLoggedInAsynTask
 *
 * @author MUVI
 */
public class CheckIfUserLoggedInOutputModel {


    int is_login = 0;


    /**
     * This Method is use to Get the Login Status
     *
     * @return is_login
     */
    public int getIs_login() {
        return is_login;
    }

    /**
     * This Method is use to Set the Login Status
     *
     * @param is_login For Setting The login Status
     */
    public void setIs_login(int is_login) {
        this.is_login = is_login;
    }


}
