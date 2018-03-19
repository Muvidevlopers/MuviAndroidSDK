package com.home.vod;

import android.app.Activity;

import com.home.vod.activity.Episode_list_Activity;
import com.home.vod.activity.LoginActivity;
import com.home.vod.activity.MovieDetailsActivity;
import com.home.vod.activity.RegisterActivity;
import com.home.vod.activity.ShowWithEpisodesActivity;

/**
 * Created by BISHAL on 16-11-2017.
 */

public class CheckVoucherOrPpvPaymentHandler {
    Activity activity;
    public CheckVoucherOrPpvPaymentHandler(Activity activity){
        this.activity=activity;
    }
    public void handleVoucherPaymentOrPpvPayment(){
        try {
            if (activity instanceof MovieDetailsActivity)
                ((MovieDetailsActivity) activity).callValidateUserAPI();
            if (activity instanceof ShowWithEpisodesActivity)
                ((ShowWithEpisodesActivity) activity).callValidateUserAPI();
            if (activity instanceof Episode_list_Activity)
                ((Episode_list_Activity) activity).callValidateUserAPI();
           /* if (activity instanceof RegisterActivity)
                ((RegisterActivity) activity).getMonitizationDetailsApi();
            if (activity instanceof LoginActivity)
                ((LoginActivity) activity).getMonitizationDetailsApi();*/

        } catch (ClassCastException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
