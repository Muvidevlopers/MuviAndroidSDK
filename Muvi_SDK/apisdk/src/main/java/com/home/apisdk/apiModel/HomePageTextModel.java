package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Text Attributes For HomePageAsynTask
 *
 * @author MUVI
 */

public class HomePageTextModel {

    private String text = "";
    private String join_btn_txt = "";

    /**
     * This Method is use to Get the Join Button Text
     *
     * @return join_btn_txt
     */
    public String getJoin_btn_txt() {
        return join_btn_txt;
    }

    /**
     * This Method is use to Set the Join Button Text
     *
     * @param join_btn_txt For Setting The Join Button Text
     */
    public void setJoin_btn_txt(String join_btn_txt) {
        this.join_btn_txt = join_btn_txt;
    }

    /**
     * This Method is use to Get the Text
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * This Method is use to Set the Text
     *
     * @param text For Setting The Text
     */
    public void setText(String text) {
        this.text = text;
    }


}
