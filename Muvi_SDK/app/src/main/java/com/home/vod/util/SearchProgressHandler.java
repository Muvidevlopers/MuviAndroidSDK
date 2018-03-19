package com.home.vod.util;

/**
 * Created by MUVI on 07-Feb-18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.home.vod.R;

/**
 * Created by User on 10-02-2017.
 */
public class SearchProgressHandler {
    private ProgressBar mProgressBar;
    private Context mContext;
    RelativeLayout bar;
    ViewGroup layout;
    boolean indeterminate = true;
    public SearchProgressHandler(Context context) {
        mContext = context;

        layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

    /*    mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleInverse);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress_rawable));
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);*/

        //RelativeLayout rl = new RelativeLayout(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bar = (RelativeLayout) inflater.inflate(R.layout.progress_bar_layout, null);
        layout.addView(bar);
        bar.setVisibility(View.GONE);

        //  rl.setGravity(Gravity.CENTER);
//        rl.addView(mProgressBar);


        // hide();
    }

    public void show() {

        bar.setVisibility(View.VISIBLE);
        layout.bringToFront();
        bar.bringToFront();

        // mProgressBar.setVisibility(View.VISIBLE);
    }
    public boolean isShowing() {
        if(layout.indexOfChild(bar) == -1){
            return false;
        }else{
            return true;
        }
    }
    public void hide() {
//        layout.removeView(bar);
        bar.setVisibility(View.GONE);
    }
}
