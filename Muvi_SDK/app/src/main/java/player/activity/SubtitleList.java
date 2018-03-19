package player.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.home.vod.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import player.adapter.SubtitleAdapter;
import player.utils.Util;

public class SubtitleList extends Activity{

    ListView listView;
    ArrayList<String> subtitle_list = new ArrayList<>();
    SubtitleAdapter subtitleAdapter;
    LinearLayout total_layout;

    ArrayList<String> SubTitleName = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtitle_list);
        listView = (ListView) findViewById(R.id.listView);
        total_layout = (LinearLayout) findViewById(R.id.total_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Util.call_finish_at_onUserLeaveHint = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try{

                            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                            int orientation = display.getRotation();

                            Log.v("PINTU", "CheckAvailabilityOfChromecast called orientation="+orientation);

                            if (orientation == 1|| orientation == 3) {
                                hideSystemUI();
                            }}catch (Exception e){}
                    }
                });
            }
        },0,1000);



        if (getIntent().getStringArrayListExtra("SubTitleName") != null) {
            SubTitleName = getIntent().getStringArrayListExtra("SubTitleName");
        } else {
            SubTitleName.clear();
        }

        if (getIntent().getStringArrayListExtra("SubTitlePath") != null) {
            SubTitlePath = getIntent().getStringArrayListExtra("SubTitlePath");
        } else {
            SubTitlePath.clear();
        }

        subtitle_list.add("Off");

        for(int i=0;i<SubTitleName.size();i++)
        {
            subtitle_list.add(SubTitleName.get(i));
        }

        subtitleAdapter = new SubtitleAdapter(SubtitleList.this,subtitle_list);
        listView.setAdapter(subtitleAdapter);

        Animation topTobottom = AnimationUtils.loadAnimation(this, R.anim.bottom_top);
        listView.startAnimation(topTobottom );


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    Util.DefaultSubtitle ="Off";
                }
                else
                {

                    Util.DefaultSubtitle =SubTitleName.get(position-1).trim();
                }


                Intent playerIntent = new Intent();
                playerIntent.putExtra("position", ""+position);
                playerIntent.putExtra("type", "subtitle");
                setResult(RESULT_OK, playerIntent);
                finish();
            }
        });

        total_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent playerIntent = new Intent();
                playerIntent.putExtra("type", "subtitle");
                playerIntent.putExtra("position", "nothing");
                setResult(RESULT_OK, playerIntent);
                finish();
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent playerIntent = new Intent();
        playerIntent.putExtra("type", "subtitle");
        playerIntent.putExtra("position", "nothing");
        setResult(RESULT_OK, playerIntent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        Toast.makeText(SubtitleList.this,"pause called",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(SubtitleList.this,"resume called",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try{
            timer.cancel();
        }catch (Exception e){}
    }

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        );
    }


   /* @Override
    public void onOrientationChange(int orientation) {


        if (orientation == 90) {

            Log.v("MUVI11","90");

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

            hideSystemUI();
        } else if (orientation == 270) {
            Log.v("MUVI11","270");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            hideSystemUI();
        } else if (orientation == 180) {
            Log.v("MUVI11","180");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            showSystemUI();

        } else if (orientation == 0) {
            Log.v("MUVI11","0");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            showSystemUI();
        }

    }*/
}
