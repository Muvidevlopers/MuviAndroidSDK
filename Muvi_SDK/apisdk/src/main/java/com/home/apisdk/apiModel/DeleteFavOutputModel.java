package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For DeleteFavAsync
 *
 * @author Abhishek
 */

public class DeleteFavOutputModel {
    String code, status, msg;

    /**
     * This Method is use to Get the Code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * This Method is use to Set the Code
     *
     * @param code For Setting The Code
     */
    public void setCode(String code) {
        this.code = code;
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
     * This Method is use to Get the Message
     *
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * This Method is use to Set the Message
     *
     * @param msg For Setting The Message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
