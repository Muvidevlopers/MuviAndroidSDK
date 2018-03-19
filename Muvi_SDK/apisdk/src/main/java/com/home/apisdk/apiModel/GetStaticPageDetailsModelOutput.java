package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetStaticPagesDetailsAsynTask
 *
 * @author MUVI
 */

public class GetStaticPageDetailsModelOutput {

    private String content;
    private String title;

    /**
     * This Method is use to Get the Title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This Method is use to Set the Title
     *
     * @param title For Setting The Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This Method is use to Get the Content
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * This Method is use to Set the Content
     *
     * @param content For Setting The Content
     */
    public void setContent(String content) {
        this.content = content;
    }


}
