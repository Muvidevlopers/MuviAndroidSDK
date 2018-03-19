package com.home.vod;

import android.app.Activity;
import android.content.Intent;

import com.home.vod.activity.ProgrammeActivity;
import com.home.vod.activity.ShowWithEpisodesActivity;

/**
 * Created by Abhishek on 10/6/2017.
 */

public class Episode_Programme_Handler {

    Activity activity;

    public Episode_Programme_Handler(Activity activity){
        this.activity=activity;
    }

    public void handleIntent(String key,String permalink){
        Intent detailsIntent = new Intent(activity, ShowWithEpisodesActivity.class);
        detailsIntent.putExtra(key,permalink);
        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(detailsIntent);

    }

}
