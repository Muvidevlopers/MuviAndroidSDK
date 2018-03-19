package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetContentDetailsAsynTask
 *
 * @author MUVI
 */
public class RelatedContentInput {
    String authtoken;
    String contentId;
    String content_stream_id;

    /**
     * This Method is use to Get the Content Id
     *
     * @return content Id
     */

    public String getContentId() {
        return contentId;
    }

    /**
     * This Method is use to Get the Content Id
     *
     * @param contentId For Setting The Content Id
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * This Method is use to Get the Stream Id
     *
     * @return Stream Id
     */

    public String getContent_stream_id() {
        return content_stream_id;
    }

    /**
     * This Method is use to Get the Stream Id
     *
     * @param content_stream_id For Setting The Stream Id
     */

    public void setContent_stream_id(String content_stream_id) {
        this.content_stream_id = content_stream_id;
    }

    /**
     * This Method is use to Get the Language
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * This Method is use to Set the Language
     *
     * @param language For Setting The Language
     */

    public void setLanguage(String language) {
        this.language = language;
    }

    String language;


    /**
     * This Method is use to Get the Auth Token
     *
     * @return authtoken
     */
    public String getAuthToken() {
        return authtoken;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authtoken For Setting The Auth Token
     */
    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }
}
