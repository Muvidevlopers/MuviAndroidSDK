package com.home.vod;


import android.app.Activity;
import com.home.vod.activity.MainActivity;

/**
 * Created by Android on 1/10/2018.
 */

public class ToolbarTitleHandler {
    Activity activity;


    public ToolbarTitleHandler(MainActivity activity){
        this.activity=activity;
        activity.getSupportActionBar().setTitle(R.string.app_name);

    }
}
