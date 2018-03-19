package player.service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.home.vod.R;

import player.utils.Util;


public class PopUpService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        Log.v("BIBHU1","service called");

        // Show download Completion PopUp

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        dialogBuilder.setTitle("");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setMessage(intent.getStringExtra("msg"));
        dialogBuilder.setNegativeButton(Util.getTextofLanguage(PopUpService.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        final AlertDialog dialog = dialogBuilder.create();
        final Window dialogWindow = dialog.getWindow();
        final WindowManager.LayoutParams dialogWindowAttributes = dialogWindow.getAttributes();

// Set fixed width (280dp) and WRAP_CONTENT height
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogWindowAttributes);
        lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, getResources().getDisplayMetrics());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

// Set to TYPE_SYSTEM_ALERT so that the Service can display it
        dialogWindow.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialogWindowAttributes.windowAnimations = R.style.d;
        dialog.show();


        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
