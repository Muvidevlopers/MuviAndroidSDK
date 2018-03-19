package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For PurchaseHistoryAsyntask
 *
 * @author MUVI
 */

public class PurchaseHistoryOutputModel {

    String invoice_id="";
    String id="";
    String transaction_date="";
    String transaction_status="";
    String statusppv="";
    String currency_symbol="";
    String currency_code="";
    String amount="";

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
     * This Method is use to Get the Invoice ID
     *
     * @return invoice_id
     */
    public String getInvoice_id() {
        return invoice_id;
    }

    /**
     * This Method is use to Set the Invoice ID
     *
     * @param invoice_id For Setting The Invoice ID
     */
    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     * This Method is use to Get the Id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * This Method is use to Set the Id
     *
     * @param id For Setting The ID
     */
    public void setId(String id) {
        this.id = id;
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
     * This Method is use to Get the Status PPV
     *
     * @return statusppv
     */
    public String getStatusppv() {
        return statusppv;
    }

    /**
     * This Method is use to Set the Status PPV
     *
     * @param statusppv For Setting The Status PPV
     */
    public void setStatusppv(String statusppv) {
        this.statusppv = statusppv;
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


}
