package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetValidateUserAsynTask
 *
 * @author MUVI
 */
public class ValidateUserOutput {
    String message;
    String isMemberSubscribed;

    /**
     * This Method is use to Get the User's Status
     *
     * @return validuser_str
     */
    public String getValiduser_str() {
        return validuser_str;
    }

    /**
     * This Method is use to Set the User's Status
     *
     * @param validuser_str For Setting The User's Status
     */
    public void setValiduser_str(String validuser_str) {
        this.validuser_str = validuser_str;
    }

    String validuser_str;

    /**
     * This Method is use to Get the Message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This Method is use to Set the Message
     *
     * @param message For Setting The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This Method is use to Get the Is Member Subscribe Detail
     *
     * @return isMemberSubscribed
     */
    public String getIsMemberSubscribed() {
        return isMemberSubscribed;
    }

    /**
     * This Method is use to Set the Is Member Subscribe Detail
     *
     * @param isMemberSubscribed For Setting The Is Member Subscribe Detail
     */
    public void setIsMemberSubscribed(String isMemberSubscribed) {
        this.isMemberSubscribed = isMemberSubscribed;
    }
}
