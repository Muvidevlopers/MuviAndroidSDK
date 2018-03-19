package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For AuthUserPaymentInfoAsyncTask
 *
 * @author MUVI
 */

public class AuthUserPaymentInfoOutputModel {

    String card_type = "";
    String card_last_fourdigit = "";
    String profile_id = "";
    String response_text = "";
    String token = "";

    String transaction_invoice_id = "";
    String transaction_order_number = "";
    String transaction_dollar_amount = "";
    String transaction_amount = "";
    String transaction_response_text = "";
    String transaction_is_success = "";
    String transaction_status = "";
    String isSuccess = "";


    public String getTransaction_invoice_id() {
        return transaction_invoice_id;
    }

    public void setTransaction_invoice_id(String transaction_invoice_id) {
        this.transaction_invoice_id = transaction_invoice_id;
    }

    public String getTransaction_order_number() {
        return transaction_order_number;
    }

    public void setTransaction_order_number(String transaction_order_number) {
        this.transaction_order_number = transaction_order_number;
    }

    public String getTransaction_dollar_amount() {
        return transaction_dollar_amount;
    }

    public void setTransaction_dollar_amount(String transaction_dollar_amount) {
        this.transaction_dollar_amount = transaction_dollar_amount;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_response_text() {
        return transaction_response_text;
    }

    public void setTransaction_response_text(String transaction_response_text) {
        this.transaction_response_text = transaction_response_text;
    }

    public String getTransaction_is_success() {
        return transaction_is_success;
    }

    public void setTransaction_is_success(String transaction_is_success) {
        this.transaction_is_success = transaction_is_success;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }



    /**
     * This Method is use to Get the Status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * This Method is use to Set the Status
     *
     * @param status For Setting The Status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This Method is use to Get the Card Type
     *
     * @return card_type
     */
    public String getCard_type() {
        return card_type;
    }

    /**
     * This Method is use to Set the Card Type
     *
     * @param card_type For Setting The Card Type
     */
    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    /**
     * This Method is use to Get the Card Last Four Digit
     *
     * @return card_last_fourdigit
     */
    public String getCard_last_fourdigit() {
        return card_last_fourdigit;
    }

    /**
     * This Method is use to Set the Card Last Four Digit
     *
     * @param card_last_fourdigit For Setting The Card Last Four Digit
     */
    public void setCard_last_fourdigit(String card_last_fourdigit) {
        this.card_last_fourdigit = card_last_fourdigit;
    }

    /**
     * This Method is use to Get the Profile ID
     *
     * @return profile_id
     */
    public String getProfile_id() {
        return profile_id;
    }

    /**
     * This Method is use to Get the Profile ID
     *
     * @param profile_id For Setting The Profile ID
     */
    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    /**
     * This Method is use to Get the Response Text
     *
     * @return response_text
     */
    public String getResponse_text() {
        return response_text;
    }

    /**
     * This Method is use to Set the Response Text
     *
     * @param response_text For Setting The Response Text
     */
    public void setResponse_text(String response_text) {
        this.response_text = response_text;
    }

    /**
     * This Method is use to Get the Token
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * This Method is use to Set the Token
     *
     * @param token For Setting The Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    String status;

}
