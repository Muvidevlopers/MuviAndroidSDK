package player.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.home.vod.R;

import java.util.ArrayList;

import player.adapter.ResolutionChangeAdapter;
import player.utils.Util;

public class ResolutionChangeActivity extends Activity {

    ListView listView;
    ArrayList<String> resolutionformst_list = new ArrayList<>();
    ResolutionChangeAdapter resolutionChangeAdapter;
    LinearLayout total_layout;

    ArrayList<String> ResolutionFormat = new ArrayList<>();
    ArrayList<String> ResolutionUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolution_change);

        listView = (ListView) findViewById(R.id.listView);
        total_layout = (LinearLayout) findViewById(R.id.total_layout);

        Util.call_finish_at_onUserLeaveHint = true;

        if (getIntent().getStringArrayListExtra("ResolutionFormat") != null) {
            ResolutionFormat = getIntent().getStringArrayListExtra("ResolutionFormat");
        } else {
            ResolutionFormat.clear();
        }

        if (getIntent().getStringArrayListExtra("ResolutionUrl") != null) {
            ResolutionUrl = getIntent().getStringArrayListExtra("ResolutionUrl");
        } else {
            ResolutionUrl.clear();
        }


        for(int i=0;i<ResolutionFormat.size();i++)
        {
            resolutionformst_list.add(ResolutionFormat.get(i));
        }

        resolutionChangeAdapter = new ResolutionChangeAdapter(ResolutionChangeActivity.this,resolutionformst_list);
        listView.setAdapter(resolutionChangeAdapter);

        Animation topTobottom = AnimationUtils.loadAnimation(this, R.anim.bottom_top);
        listView.startAnimation(topTobottom );


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Util.VideoResolution = ResolutionFormat.get(position).trim();

                Intent playerIntent = new Intent();
                playerIntent.putExtra("position", ""+position);
                playerIntent.putExtra("type", "resolution");
                setResult(RESULT_OK, playerIntent);
                finish();
            }
        });

        total_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent playerIntent = new Intent();
                playerIntent.putExtra("position", "nothing");
                playerIntent.putExtra("type", "resolution");
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
        playerIntent.putExtra("position", "nothing");
        playerIntent.putExtra("type", "resolution");
        setResult(RESULT_OK, playerIntent);
        finish();
    }
}
