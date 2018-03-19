package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Output Attributes For GetSubCategoryListAsync
 *
 * @author Abhishek
 */

public class SubCategoryOutput {

    /**
     * This Method is use to get the Sub Category Id
     *
     * @return subcategory_id
     */

    public String getSubcategory_id() {
        return subcategory_id;
    }

    /**
     * This Method is use to set the Sub Category Id
     *
     * @param subcategory_id For Setting the Sub Category Id
     */
    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    /**
     * This Method is use to get the Subcat Name
     *
     * @return subcat_name
     */

    public String getSubcat_name() {
        return subcat_name;
    }

    /**
     * This Method is use to set the Subcat Name
     *
     * @param subcat_name For Setting the Subcat Name
     */

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    /**
     * This Method is use to get the Permalink
     *
     * @return permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * This Method is use to set the Permalink
     *
     * @param permalink For Setting the Permalink
     */

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    String subcategory_id;
    String subcat_name;
    String permalink;
}
