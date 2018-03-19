package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetCardListForPPVAsynTask
 *
 * @author MUVI
 */

public class GetCardListForPPVOutputModel {

    String card_id;
    String card_last_fourdigit;

    /**
     * This Method is use to Get the Card Last 4 Digit
     *
     * @return card_last_fourdigit
     */
    public String getCard_last_fourdigit() {
        return card_last_fourdigit;
    }

    /**
     * This Method is use to Set the Card Last 4 Digit
     *
     * @param card_last_fourdigit For Setting The Card Last 4 Digit
     */
    public void setCard_last_fourdigit(String card_last_fourdigit) {
        this.card_last_fourdigit = card_last_fourdigit;
    }

    /**
     * This Method is use to Get the Card ID
     *
     * @return card_id
     */
    public String getCard_id() {
        return card_id;
    }

    /**
     * This Method is use to Set the Card ID
     *
     * @param card_id For Setting The Card ID
     */
    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }


}
