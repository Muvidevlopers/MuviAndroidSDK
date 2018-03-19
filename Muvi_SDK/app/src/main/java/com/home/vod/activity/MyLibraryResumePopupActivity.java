 package com.home.vod.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.SensorOrientationChangeNotifier;
import com.home.vod.util.Util;

import static com.home.vod.preferences.LanguagePreference.CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.CONTINUE_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEAFULT_CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEAFULT_CONTINUE_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_RESUME_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.RESUME_MESSAGE;


 public class MyLibraryResumePopupActivity extends Activity implements SensorOrientationChangeNotifier.Listener {

     LanguagePreference languagePreference;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_resume_playing);
         languagePreference = LanguagePreference.getLanguagePreference(MyLibraryResumePopupActivity.this);
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
         LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainlayout);

         LinearLayout popupLayout = (LinearLayout) findViewById(R.id.popupLayout);
         Button yesButton = (Button) findViewById(R.id.yesButton);
         Button cancelButton = (Button) findViewById(R.id.cancelButton);
         TextView resumeTitleTextView = (TextView) findViewById(R.id.resumeTitleTextView);
         resumeTitleTextView.setText(languagePreference.getTextofLanguage(RESUME_MESSAGE, DEFAULT_RESUME_MESSAGE));
         yesButton.setText(languagePreference.getTextofLanguage(CONTINUE_BUTTON, DEAFULT_CONTINUE_BUTTON));
         cancelButton.setText(languagePreference.getTextofLanguage(CANCEL_BUTTON, DEAFULT_CANCEL_BUTTON));

         Animation topTobottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
         popupLayout.startAnimation(topTobottom);
         yesButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent playerIntent = new Intent();
                 playerIntent.putExtra("yes", "1002");
                 setResult(RESULT_OK, playerIntent);
                 finish();
             }
         });
         cancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent playerIntent = new Intent();
                 playerIntent.putExtra("yes", "1003");
                 setResult(RESULT_OK, playerIntent);
                 finish();
             }
         });
         mainLayout.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                /* Intent playerIntent = new Intent();
                 playerIntent.putExtra("yes", "1003");
                 setResult(RESULT_OK, playerIntent);
                 finish();*/
                 return false;
             }
         });
     }

     @Override
     protected void onResume() {
         super.onResume();
         SensorOrientationChangeNotifier.getInstance(MyLibraryResumePopupActivity.this).addListener(this);
     }

     @Override
     public void onOrientationChange(int orientation) {

         if (orientation == 90) {
             setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
         } else if (orientation == 270) {
             setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
         }
     }
 }
