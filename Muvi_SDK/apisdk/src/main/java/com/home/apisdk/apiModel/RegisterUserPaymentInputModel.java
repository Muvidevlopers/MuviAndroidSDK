package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetPPVPaymentAsync
 *
 * @author MUVI
 */

public class RegisterUserPaymentInputModel {

    String authToken = "";
    String card_name = "";
    String exp_month = "";
    String card_number = "";
    String exp_year = "";
    String email = "";
    String user_id = "";
    String card_type = "";
    String card_last_fourdigit = "";
    String profile_id = "";
    String token = "";
    String cvv = "";
    String country = "";
    String season_id = "";
    String episode_id = "";
    String currency_id = "";
    String plan_id = "";
    String name = "";



    String transaction_status = "";
    String invoice_id = "";
    String order_number = "";
    String dollar_amount = "";
    String amount = "";
    String response_text = "";
    String isSuccess = "";
    String transaction_is_success = "";

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getDollar_amount() {
        return dollar_amount;
    }

    public void setDollar_amount(String dollar_amount) {
        this.dollar_amount = dollar_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResponse_text() {
        return response_text;
    }

    public void setResponse_text(String response_text) {
        this.response_text = response_text;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getTransaction_is_success() {
        return transaction_is_success;
    }

    public void setTransaction_is_success(String transaction_is_success) {
        this.transaction_is_success = transaction_is_success;
    }




    /**
     * This Method is use to Get the Movie Id
     *
     * @return movie_id
     */

    public String getMovie_id() {
        return movie_id;
    }

    /**
     * This Method is use to Set the Movie Id
     *
     * @param movie_id For Setting The Movie Id
     */

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    String movie_id;

    /**
     * This Method is use to Get the Is Advance Details
     *
     * @return is_advance
     */

    public String getIs_advance() {
        return is_advance;
    }

    /**
     * This Method is use to Set the Is Advance Details
     *
     * @param is_advance For Setting The Is Advance Details
     */

    public void setIs_advance(String is_advance) {
        this.is_advance = is_advance;
    }

    String is_advance;

    /**
     * This Method is use to Get the Saved Card Details
     *
     * @return is_save_this_card
     */
    public String getIs_save_this_card() {
        return is_save_this_card;
    }

    /**
     * This Method is use to Set the Saved Card Details
     *
     * @param is_save_this_card For Setting The Saved Card Details
     */

    public void setIs_save_this_card(String is_save_this_card) {
        this.is_save_this_card = is_save_this_card;
    }

    String is_save_this_card;

    /**
     * This Method is use to Get the Coupon Code
     *
     * @return CouponCode
     */

    public String getCouponCode() {
        return CouponCode;
    }

    /**
     * This Method is use to Set the Coupon Code
     *
     * @param couponCode For Setting Tha Coupon Code
     */

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    String CouponCode;

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
     * This Method is use to Get the Card Name
     *
     * @return card_name
     */
    public String getCard_name() {
        return card_name;
    }

    /**
     * This Method is use to Set the Card Name
     *
     * @param card_name For Setting The Card Name
     */

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    /**
     * This Method is use to Get the Expire Month
     *
     * @return exp_month
     */
    public String getExp_month() {
        return exp_month;
    }

    /**
     * This Method is use to Set the Expire Month
     *
     * @param exp_month For Setting The Expire Month
     */

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    /**
     * This Method is use to Get the Card Number
     *
     * @return card_number
     */
    public String getCard_number() {
        return card_number;
    }

    /**
     * This Method is use to Set the Card Number
     *
     * @param card_number For Setting The Card Number
     */

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    /**
     * This Method is use to Get the Expire Year
     *
     * @return exp_year
     */
    public String getExp_year() {
        return exp_year;
    }

    /**
     * This Method is use to Set the Expire Year
     *
     * @param exp_year For Setting The Expire Year
     */

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
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
     * This Method is use to Get the Card Last Four Digits
     *
     * @return card_last_fourdigit
     */
    public String getCard_last_fourdigit() {
        return card_last_fourdigit;
    }

    /**
     * This Method is use to Set the Card Last Four Digits
     *
     * @param card_last_fourdigit For Setting The Card Last Four Digits
     */

    public void setCard_last_fourdigit(String card_last_fourdigit) {
        this.card_last_fourdigit = card_last_fourdigit;
    }

    /**
     * This Method is use to Get the Profile Id
     *
     * @return profile_id
     */
    public String getProfile_id() {
        return profile_id;
    }

    /**
     * This Method is use to Set the Profile Id
     *
     * @param profile_id For Setting The Profile Id
     */

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
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

    /**
     * This Method is use to Get the Country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * This Method is use to Set the Country
     *
     * @param country For Setting The Country
     */

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This Method is use to Get the Season Id
     *
     * @return season_id
     */

    public String getSeason_id() {
        return season_id;
    }

    /**
     * This Method is use to Set the Season Id
     *
     * @param season_id For Setting The Season Id
     */

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    /**
     * This Method is use to Get the Episode Id
     *
     * @return episode_id
     */
    public String getEpisode_id() {
        return episode_id;
    }

    /**
     * This Method is use to Set the Episode Id
     *
     * @param episode_id For Setting The Episode Id
     */

    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    /**
     * This Method is use to Get the Currency Id
     *
     * @return currency_id
     */

    public String getCurrency_id() {
        return currency_id;
    }

    /**
     * This Method is use to Set the Currency Id
     *
     * @param currency_id For Setting The Currency Id
     */

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    /**
     * This Method is use to Get the Plan Id
     *
     * @return plan_id
     */
    public String getPlan_id() {
        return plan_id;
    }

    /**
     * This Method is use to Set the Plan Id
     *
     * @param plan_id For Setting The PLan ID
     */

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }


}