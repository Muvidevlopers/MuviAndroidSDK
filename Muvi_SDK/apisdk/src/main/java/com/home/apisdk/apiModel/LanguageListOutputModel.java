package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetTranslateLanguageAsync
 *
 * @author MUVI
 */

public class LanguageListOutputModel {

    String languageCode;
    String languageName;

    /**
     * This Method is use to Get the Is Selected Details
     *
     * @return isSelected
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * This Method is use to Set the Is Selected Details
     *
     * @param selected For Setting The Is Selected Details
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    /**
     * This Method is use to Get the Language Code
     *
     * @return languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param languageCode For Setting The Language Code
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * This Method is use to Get the Language Name
     *
     * @return languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * This Method is use to Set the Language Name
     *
     * @param languageName For Setting The Language Name
     */

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
