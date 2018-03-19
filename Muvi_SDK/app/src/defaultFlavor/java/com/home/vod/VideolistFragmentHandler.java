package com.home.vod;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.home.vod.util.FeatureHandler;

/**
 * Created by MUVI on 10/27/2017.
 */

public class VideolistFragmentHandler {
    private Activity context;
    FeatureHandler featureHandler;

    public VideolistFragmentHandler(Activity context){
        this.context=context;
        featureHandler = FeatureHandler.getFeaturePreference(context);

    }

    public void handleMenuFilter(Menu menu){

        MenuItem item;
        item= menu.findItem(R.id.action_filter);
        if(featureHandler.getFeatureStatus(FeatureHandler.IS_FILTER,FeatureHandler.DEFAULT_IS_FILTER)){
            item.setVisible(true);
        }else{
            item.setVisible(false);
        }

    }
}
