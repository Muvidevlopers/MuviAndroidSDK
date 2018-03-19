package com.home.vod.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.home.vod.R;


/**
 * Created by User on 10-02-2017.
 */
public class ProgressBarHandler extends Dialog {

    public ProgressBarHandler(Context mContext) {
        super(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.progress_bar_layout, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        getWindow().setDimAmount(0f);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setDimAmount(0f);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    public ProgressBarHandler(Context context, int theme) {
        super(context, theme);
    }
  @Override
  public void show() {
      super.show();
  }


    @Override
    public boolean isShowing() {
        return super.isShowing();
    }


    public void hide() {
     dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    /*    private ProgressBar mProgressBar;
    private Context mContext;
    RelativeLayout bar;
    ViewGroup layout;
    boolean indeterminate = true;
    public ProgressBarHandler(Context context) {
        mContext = context;

        layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

    *//*    mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleInverse);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress_rawable));
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);*//*

        //RelativeLayout rl = new RelativeLayout(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bar = (RelativeLayout) inflater.inflate(R.layout.progress_bar_layout, null);
      //  rl.setGravity(Gravity.CENTER);
//        rl.addView(mProgressBar);


       // hide();
    }

    public void show() {
        layout.removeView(bar);
        layout.addView(bar);

       // mProgressBar.setVisibility(View.VISIBLE);
    }
    public boolean isShowing() {
        return layout.indexOfChild(bar) != -1;

        // mProgressBar.setVisibility(View.VISIBLE);
    }
    public void hide() {

        layout.removeView(bar);

        //mProgressBar.setVisibility(View.INVISIBLE);
    }*/


}
