package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For TransactionDetailsAsynctask
 *
 * @author MUVI
 */
public class TransactionOutputModel {

    String order_number;
    String movie_id;
    String transaction_date;
    String transaction_status;
    String plan_name;
    String currency_symbol;
    String currency_code;
    String amount;


    /**
     * This Method is use to Get the Invoice Id
     *
     * @return invoice_id
     */

    public String getInvoice_id() {
        return invoice_id;
    }

    /**
     * This Method is use to Set the Invoice Id
     *
     * @param invoice_id For Setting The Invoice Id
     */

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    String invoice_id;

    /**
     * This Method is use to Get the Amount
     *
     * @return amount
     */

    public String getAmount() {
        return amount;
    }

    /**
     * This Method is use to Set the Amount
     *
     * @param amount For Setting The Amount
     */

    public void setAmount(String amount) {
        this.amount = amount;
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
     * @param movie_id For Setting The Movie ID
     */

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    /**
     * This Method is use to Get the Transaction Date
     *
     * @return transaction_date
     */

    public String getTransaction_date() {
        return transaction_date;
    }

    /**
     * This Method is use to Set the Transaction Date
     *
     * @param transaction_date For Setting The Transaction Date
     */

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    /**
     * This Method is use to Get the Transaction Status
     *
     * @return transaction_status
     */

    public String getTransaction_status() {
        return transaction_status;
    }

    /**
     * This Method is use to Set the Transaction Status
     *
     * @param transaction_status For Setting The Transaction Status
     */

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    /**
     * This Method is use to Get the Plan Name
     *
     * @return plan_name
     */

    public String getPlan_name() {
        return plan_name;
    }

    /**
     * This Method is use to Set the Plan Name
     *
     * @param plan_name For Setting The Plan Name
     */

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    /**
     * This Method is use to Get the Currency Symbol
     *
     * @return currency_symbol
     */

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    /**
     * This Method is use to Set the Currency Symbol
     *
     * @param currency_symbol For Setting The Currency Symbol
     */

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    /**
     * This Method is use to Get the Currency Code
     *
     * @return currency_code
     */

    public String getCurrency_code() {
        return currency_code;
    }

    /**
     * This Method is use to Set the Currency Code
     *
     * @param currency_code For Setting The Currency Code
     */

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     * This Method is use to Get the Order Number
     *
     * @return order_number
     */

    public String getOrder_number() {
        return order_number;
    }

    /**
     * This Method is use to Set the Order Number
     *
     * @param order_number For Setting The Order Number
     */

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }


}
