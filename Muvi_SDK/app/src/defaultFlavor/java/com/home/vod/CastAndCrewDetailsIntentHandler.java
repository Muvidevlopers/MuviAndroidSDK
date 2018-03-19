package com.home.vod;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by MUVI on 12/5/2017.
 */

public class CastAndCrewDetailsIntentHandler {

    Activity activity;

    public CastAndCrewDetailsIntentHandler(Activity activity){
        this.activity=activity;
    }

    public void castandcrewdetailsIntentShowORHide(Intent castCrewIntent){

        activity.startActivity(castCrewIntent);
    }


}
