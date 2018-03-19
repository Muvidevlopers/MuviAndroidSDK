package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For ContactUsAsynTask
 *
 * @author MUVI
 */
public class ContactUsOutputModel {

    String success_msg;
    String error_msg;

    /**
     * This Method is use to Get the Error Message
     *
     * @return error_msg
     */
    public String getError_msg() {
        return error_msg;
    }

    /**
     * This Method is use to Set the Error Message
     *
     * @param error_msg For Setting The Error Message
     */
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    /**
     * This Method is use to Get the Success Message
     *
     * @return success_msg
     */
    public String getSuccess_msg() {
        return success_msg;
    }

    /**
     * This Method is use to Set the Success Message
     *
     * @param success_msg For Setting The Success Message
     */
    public void setSuccess_msg(String success_msg) {
        this.success_msg = success_msg;
    }


}
