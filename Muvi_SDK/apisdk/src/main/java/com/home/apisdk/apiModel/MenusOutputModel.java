package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * Created by MUVI on 1/20/2017.
 */

public class MenusOutputModel {

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<MainMenu> getMainMenuModel() {
        return mainMenuModel;
    }

    public void setMainMenuModel(ArrayList<MainMenu> mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    public ArrayList<UserMenu> getUserMenuModel() {
        return userMenuModel;
    }

    public void setUserMenuModel(ArrayList<UserMenu> userMenuModel) {
        this.userMenuModel = userMenuModel;
    }

    public ArrayList<FooterMenu> getFooterMenuModel() {
        return footerMenuModel;
    }

    public void setFooterMenuModel(ArrayList<FooterMenu> footerMenuModel) {
        this.footerMenuModel = footerMenuModel;
    }

    String msg;
    ArrayList<MainMenu> mainMenuModel = new ArrayList<>();
    ArrayList<UserMenu> userMenuModel = new ArrayList<>();
    ArrayList<FooterMenu> footerMenuModel = new ArrayList<>();


    public class MainMenu {

        String title;
        String permalink;
        String id;
        String parent_id;
        String link_type;
        String value;
        String id_seq;

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        boolean isEnable;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getIsSubcategoryPresent() {
            return isSubcategoryPresent;
        }

        public void setIsSubcategoryPresent(String isSubcategoryPresent) {
            this.isSubcategoryPresent = isSubcategoryPresent;
        }

        String category_id;
        String isSubcategoryPresent;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getId_seq() {
            return id_seq;
        }

        public void setId_seq(String id_seq) {
            this.id_seq = id_seq;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getLanguage_parent_id() {
            return language_parent_id;
        }

        public void setLanguage_parent_id(String language_parent_id) {
            this.language_parent_id = language_parent_id;
        }

        String language_id;
        String language_parent_id;

        public ArrayList<MainMenuChild> getMainMenuChildModel() {
            return mainMenuChildModel;
        }

        public void setMainMenuChildModel(ArrayList<MainMenuChild> mainMenuChildModel) {
            this.mainMenuChildModel = mainMenuChildModel;
        }

        ArrayList<MainMenuChild> mainMenuChildModel = new ArrayList<>();


        public class MainMenuChild {

            String title;
            String permalink;
            String id;
            String parent_id;
            String link_type;
            String value;
            String id_seq;
            String language_id;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getId_seq() {
                return id_seq;
            }

            public void setId_seq(String id_seq) {
                this.id_seq = id_seq;
            }

            public String getLanguage_id() {
                return language_id;
            }

            public void setLanguage_id(String language_id) {
                this.language_id = language_id;
            }

            public String getLanguage_parent_id() {
                return language_parent_id;
            }

            public void setLanguage_parent_id(String language_parent_id) {
                this.language_parent_id = language_parent_id;
            }

            String language_parent_id;

            public String getLink_type() {
                return link_type;
            }

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPermalink() {
                return permalink;
            }

            public void setPermalink(String permalink) {
                this.permalink = permalink;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }


        }

        public String getLink_type() {
            return link_type;
        }

        public void setLink_type(String link_type) {
            this.link_type = link_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }


    }


    public class UserMenu {

        String title;
        String permalink;
        String parent_id;

        public ArrayList<UserMenuChild> getUserMenuChildModel() {
            return userMenuChildModel;
        }

        public void setUserMenuChildModel(ArrayList<UserMenuChild> userMenuChildModel) {
            this.userMenuChildModel = userMenuChildModel;
        }

        ArrayList<UserMenuChild> userMenuChildModel = new ArrayList<>();


        public class UserMenuChild {


            String title;
            String permalink;
            String id;
            String parent_id;
            String link_type;

            public String getLink_type() {
                return link_type;
            }

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPermalink() {
                return permalink;
            }

            public void setPermalink(String permalink) {
                this.permalink = permalink;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }


        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }


    public class FooterMenu {

        String domain;
        String link_type;
        String id;
        String display_name;
        String permalink;
        String url;

        public String getIsSubcategoryPresent() {
            return isSubcategoryPresent;
        }

        public void setIsSubcategoryPresent(String isSubcategoryPresent) {
            this.isSubcategoryPresent = isSubcategoryPresent;
        }

        String isSubcategoryPresent;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        String category_id;


        public String getLanguage_parent_id() {
            return language_parent_id;
        }

        public void setLanguage_parent_id(String language_parent_id) {
            this.language_parent_id = language_parent_id;
        }

        String language_parent_id;
        String language_id;
        String id_seq;
        String parent_id;

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }




        public String getId_seq() {
            return id_seq;
        }

        public void setId_seq(String id_seq) {
            this.id_seq = id_seq;
        }




        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }




        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        boolean isEnable;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getLink_type() {
            return link_type;
        }

        public void setLink_type(String link_type) {
            this.link_type = link_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }


    }


}