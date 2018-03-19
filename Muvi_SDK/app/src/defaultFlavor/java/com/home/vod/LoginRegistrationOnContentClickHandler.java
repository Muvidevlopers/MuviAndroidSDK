package com.home.vod;

import android.app.Activity;
import android.content.Intent;

import com.home.vod.activity.LoginActivity;
import com.home.vod.activity.RegisterActivity;
import com.home.vod.activity.ShowWithEpisodesActivity;
import com.home.vod.util.Util;

/**
 * Created by BISHAL on 09-10-2017.
 */
// the purpose to create this handler is that,in other application from intent it goes to register activitybut in Matas flavour it goes to Loginactivity
public class LoginRegistrationOnContentClickHandler {
    Activity activity;
    public LoginRegistrationOnContentClickHandler(Activity activity){
            this.activity = activity;
    }

    public Intent handleClickOnContent(){
        return new Intent(activity, LoginActivity.class);


    }
}
