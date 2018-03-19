package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For ValidateCouponCodeAsynTask
 *
 * @author MUVI
 */

public class ValidateCouponCodeInputModel {

    String authToken;
    String couponCode;
    String user_id;
    String currencyId;

    /**
     * This Method is use to Get the Currency Id
     *
     * @return currencyId
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * This Method is use to Set the Currency Id
     *
     * @param currencyId For Setting The Currency Id
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * This Method is use to Get the Coupon Code
     *
     * @return couponCode
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * This Method is use to Set the Coupon Code
     *
     * @param couponCode For Setting The Coupon Code
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * This Method is use to Get the User Id
     *
     * @return user_id
     */

    public String getUser_id() {
        return user_id;
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

}
