package player.activity;

/**
 * Created by MUVI on 9/6/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.home.vod.R;
import com.home.vod.util.LogUtil;
import com.spotxchange.v3.SpotX;
import com.spotxchange.v3.SpotXAd;
import com.spotxchange.v3.SpotXAdGroup;
import com.spotxchange.v3.view.InterstitialPresentationController;




public class AdPlayerActivity extends AppCompatActivity implements AdLoader.Callback {
    private static final String TAG = AdPlayerActivity.class.getSimpleName();

    private ProgressBar _progressBar;
    Player playerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_player);
        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");
        SpotX.initialize(this);
        SpotX.setConfigurationValue("skippableTime", "10");
        _progressBar = (ProgressBar) findViewById(R.id.progressBar);
        showLoadingIndicator(false);
        loadAd();

    }


    private void loadAd() {


        LogUtil.showLog("Abhishek", "CHANNEL" + playerModel.getChannel_id());

          if (!playerModel.getChannel_id().equalsIgnoreCase("")) {
            AdLoader loader = new AdLoader(playerModel.getChannel_id(), 1, 10 /*seconds*/, this);
            loader.execute();
        }
    }


    // MARK: AdLoader.Callback

    @Override
    public void adLoadingStarted() {
        showLoadingIndicator(true);
    }

    @Override
    public void adLoadingFinished(@Nullable SpotXAdGroup adGroup) {
        showLoadingIndicator(false);
        if (adGroup == null) {
            showNoAdsMessage();
        } else {
            showAd(adGroup);
        }
    }

    private void showLoadingIndicator(final boolean visible) {
        _progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showNoAdsMessage() {
    }

    private void showAd(final SpotXAdGroup group) {
        group.registerObserver(new AdObserver());
        InterstitialPresentationController.show(AdPlayerActivity.this, group);
    }


    private class AdObserver implements SpotXAdGroup.Observer {
        @Override
        public void onGroupStart() {
        }

        @Override
        public void onStart(SpotXAd ad) {
            //Toast.makeText(AdPlayerActivity.this, "Starting Ad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SpotXAd ad) {
            //  Toast.makeText(AdPlayerActivity.this, "Ad Complete", Toast.LENGTH_SHORT).show();
            //  startActivity(new Intent(AdPlayerActivity.this, Main2Activity.class));
            if (getIntent().getStringExtra("fromAd") != null) {
                Intent in = new Intent();
                setResult(RESULT_OK, in);
                finish();
            } else {
                Intent playVideoIntent = new Intent(AdPlayerActivity.this, ExoPlayerActivity.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                playVideoIntent.putExtra("SubTitleName", getIntent().getStringArrayListExtra("SubTitleName"));
//                playVideoIntent.putExtra("SubTitlePath", getIntent().getStringArrayListExtra("SubTitlePath"));
//                playVideoIntent.putExtra("ResolutionFormat", getIntent().getStringArrayListExtra("ResolutionFormat"));
//                playVideoIntent.putExtra("ResolutionUrl", getIntent().getStringArrayListExtra("ResolutionUrl"));
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
                finish();
            }
          /*  if (Util.dataModel.getPreRoll() == 1){
                Intent playVideoIntent = new Intent(AdPlayerActivity.this, ExoPlayerActivity.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                playVideoIntent.putExtra("SubTitleName", getIntent().getStringArrayListExtra("SubTitleName"));
                playVideoIntent.putExtra("SubTitlePath",getIntent().getStringArrayListExtra("SubTitlePath"));
                playVideoIntent.putExtra("ResolutionFormat",getIntent().getStringArrayListExtra("ResolutionFormat"));
                playVideoIntent.putExtra("ResolutionUrl",getIntent().getStringArrayListExtra("ResolutionUrl"));
                startActivity(playVideoIntent);
                finish();
            }
           else{
                finish();
            }
*/

        }

        @Override
        public void onSkip(SpotXAd ad) {
            // Toast.makeText(AdPlayerActivity.this, "Ad Skipped", Toast.LENGTH_SHORT).show();
           /* if (Util.dataModel.getPreRoll() == 1){
                Intent playVideoIntent = new Intent(AdPlayerActivity.this, ExoPlayerActivity.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                playVideoIntent.putExtra("SubTitleName", getIntent().getStringArrayListExtra("SubTitleName"));
                playVideoIntent.putExtra("SubTitlePath",getIntent().getStringArrayListExtra("SubTitlePath"));
                playVideoIntent.putExtra("ResolutionFormat",getIntent().getStringArrayListExtra("ResolutionFormat"));
                playVideoIntent.putExtra("ResolutionUrl",getIntent().getStringArrayListExtra("ResolutionUrl"));
                startActivity(playVideoIntent);
                finish();
            }
            else{
                finish();
            }*/
            if (getIntent().getStringExtra("fromAd") != null) {
                Intent in = new Intent();
                setResult(RESULT_OK, in);
                finish();
            } else {
                Intent playVideoIntent = new Intent(AdPlayerActivity.this, ExoPlayerActivity.class);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                playVideoIntent.putExtra("SubTitleName", getIntent().getStringArrayListExtra("SubTitleName"));
                playVideoIntent.putExtra("SubTitlePath", getIntent().getStringArrayListExtra("SubTitlePath"));
                playVideoIntent.putExtra("ResolutionFormat", getIntent().getStringArrayListExtra("ResolutionFormat"));
                playVideoIntent.putExtra("ResolutionUrl", getIntent().getStringArrayListExtra("ResolutionUrl"));
                playVideoIntent.putExtra("PlayerModel", playerModel);
                startActivity(playVideoIntent);
                finish();
            }

        }

        @Override
        public void onError(SpotXAd ad, Error error) {
            // Toast.makeText(AdPlayerActivity.this, "Ad Error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGroupComplete() {
            // Toast.makeText(AdPlayerActivity.this, "Group completed.", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onTimeUpdate(SpotXAd ad, int elapsed) {
            // do nothing
        }

        @Override
        public void onClick(SpotXAd ad) {
            //  Toast.makeText(AdPlayerActivity.this, "Ad Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    /*// MARK: SpotX Observer

    @Override
    public void onGroupStart() {
        Log.d(TAG, "Group Start");
    }

    @Override
    public void onStart(SpotXAd spotXAd) {
        Log.d(TAG, "Ad Start");
    }

    @Override
    public void onComplete(SpotXAd spotXAd) {
        LogUtil.showLog("SUBHA", "Ad Complete");
        // startActivity(new Intent(MainActivity.this,Main2Activity.class));

    }

    @Override
    public void onSkip(SpotXAd spotXAd) {
        Log.d(TAG, "Ad Skip");
    }

    @Override
    public void onError(SpotXAd spotXAd, Error error) {
        Log.d(TAG, "Ad Error: " + error.getLocalizedMessage());
    }

    @Override
    public void onGroupComplete() {
        Log.d(TAG, "Group Complete");
    }*/

}
