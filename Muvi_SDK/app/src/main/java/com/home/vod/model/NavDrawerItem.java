package com.home.vod.model;


public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String permalink;
    private boolean isEnabled;
    private String linkType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;

    public NavDrawerItem() {

    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public NavDrawerItem(String title, String permalink, boolean isEnabled, String linkType,String url) {
        this.title = title;
        this.permalink = permalink;
        this.isEnabled = isEnabled;
        this.linkType = linkType;
        this.url=url;

    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
