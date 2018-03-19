package com.home.vod;

import android.app.Activity;

import com.home.apisdk.apiModel.MenusOutputModel;
import com.home.vod.model.NavDrawerItem;
import com.home.vod.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by BISHAL on 20-10-2017.
 */

public class FooterMenuHandler {
    private Activity context;
    public FooterMenuHandler(Activity context){
        this.context=context;
    }
    public void addFooterMenu(MenusOutputModel menusOutputModel,ArrayList<NavDrawerItem> menuList ){

        for (MenusOutputModel.FooterMenu menuListOutput : menusOutputModel.getFooterMenuModel()) {
            LogUtil.showLog("Alok", "footermenuListOutputList ::" + menuListOutput.getPermalink());
            //   if (menuListOutput.getUrl() != null && !menuListOutput.getUrl().equalsIgnoreCase("")) {
            menuList.add(new NavDrawerItem(menuListOutput.getDisplay_name(), menuListOutput.getPermalink(), menuListOutput.isEnable(), menuListOutput.getLink_type(), menuListOutput.getUrl()));

        //}
        }
    }
}
