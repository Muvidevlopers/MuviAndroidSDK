package com.home.apisdk.apiModel;

import java.io.Serializable;

/**
 * This Model Class Holds All The Output Attributes For The Currency Details
 *
 * @author Abhishek
 */

public class CurrencyModel implements Serializable {

    String currencyId;
    String currencyCode;
    String currencySymbol="$";

    /**
     * This Method is use to Get the Currency ID
     *
     * @return currencyId
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * This Method is use to Set the Currency ID
     *
     * @param currencyId For Setting The Currency ID
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * This Method is use to Get the Currency Code
     * @return currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * This Method is use to Set the Currency Code
     * @param currencyCode For Setting The Currency Code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * This Method is use to Get the Currency Symbol
     * @return currencySymbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * This Method is use to Set the Currency Symbol
     * @param currencySymbol For Setting The Currency Symbol
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


}
