package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For ValidateCouponCodeAsynTask
 *
 * @author MUVI
 */

public class ValidateCouponCodeOutputModel {

    String discount_type;
    String discount;

    /**
     * This Method is use to Get the discount
     *
     * @return discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * This Method is use to Set the discount
     *
     * @param discount For Setting The Discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * This Method is use to Get the discount Type
     *
     * @return discount_type
     */
    public String getDiscount_type() {
        return discount_type;
    }

    /**
     * This Method is use to Set the discount Type
     *
     * @param discount_type For Setting The Discount Type
     */
    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }


}
