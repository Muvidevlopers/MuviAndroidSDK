package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For AuthUserPaymentInfoAsyncTask
 *
 * @author MUVI
 */

public class AuthUserPaymentInfoInputModel {

    String authToken="";
    String name_on_card="";
    String expiryMonth="";
    String expiryYear="";
    String cardNumber="";
    String cvv="";

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    String plan_id="";

    /**
     * This Method is use to get the Email
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

    /**
     * This Method is use to get the Auth Token
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
     * This Method is use to Get the Name On Card
     *
     * @return name_on_card
     */
    public String getName_on_card() {
        return name_on_card;
    }

    /**
     * This Method is use to Set the Name On Card
     *
     * @param name_on_card For Setting The Name On Card
     */

    public void setName_on_card(String name_on_card) {
        this.name_on_card = name_on_card;
    }

    /**
     * This Method is use to Get the Expiry Month
     *
     * @return expiryMonth
     */
    public String getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * This Method is use to Set the Expiry Month
     *
     * @param expiryMonth For Setting The Expiry Month
     */
    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * This Method is use to Get the Expiry Year
     *
     * @return expiryYear
     */
    public String getExpiryYear() {
        return expiryYear;
    }

    /**
     * This Method is use to Set the Expiry Year
     *
     * @param expiryYear For Setting The Expiry Year
     */
    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * This Method is use to Get the Card Number
     *
     * @return cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * This Method is use to Set the Card Number
     *
     * @param cardNumber For Setting The Card Number
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * This Method is use to Get the CVV
     *
     * @return cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * This Method is use to Set the CVV
     *
     * @param cvv For Setting The CVV
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    String email;


}
