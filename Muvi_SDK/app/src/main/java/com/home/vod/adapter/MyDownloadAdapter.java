package com.home.vod.adapter;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.activity.MyDownloads;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;

import com.home.vod.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import player.model.ContactModel1;
import player.utils.DBHelper;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.home.vod.preferences.LanguagePreference.CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.CONFIRM_DELETE_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CANCEL_BUTTON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONFIRM_DELETE_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DELETE_BTN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_WANT_TO_DELETE;
import static com.home.vod.preferences.LanguagePreference.DELETE_BTN;
import static com.home.vod.preferences.LanguagePreference.WANT_TO_DELETE;

/**
 * Created by Muvi on 1/16/2017.
 */
public class MyDownloadAdapter extends BaseAdapter {
    MyDownloads activity;
    ArrayList<ContactModel1> downloadModel;
    PreferenceManager preferenceManager;
    LanguagePreference languagePreference;
    List<String[]> allElements;
    //CSVReader readers = null;
    String[] nextLine;
    SharedPreferences pref;
    String emailIdStr = "";
    String genre_data = "";
    ContactModel1 audio;
    //MydownloadModel mydownloadModel;
    DBHelper dbHelper;
    public boolean downloading;
    DownloadManager downloadManager;
    //Downloadlistdb downloadlistdb;
    public MyDownloadAdapter(MyDownloads activity, int simple_dropdown_item_1line, ArrayList<ContactModel1> downloadModel) {

        LogUtil.showLog("MUVI","DOWNLOAD MODEL=="+downloadModel.get(0).toString());
        this.activity = activity;
        this.downloadModel = downloadModel;
        dbHelper = new DBHelper(activity);
        downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
        preferenceManager = PreferenceManager.getPreferenceManager(activity);
        languagePreference = LanguagePreference.getLanguagePreference(activity);
       // pref = activity.getSharedPreferences(Util.LOGIN_PREF, 0);
        if (preferenceManager != null) {
            emailIdStr = preferenceManager.getEmailIdFromPref();


        } else {
            emailIdStr = "";
        }
    }

    @Override
    public int getCount() {
        return downloadModel.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater inflater = activity.getLayoutInflater();
        v = inflater.inflate(R.layout.custom_offlist, null);
        TextView title = (TextView) v.findViewById(R.id.textView);
        TextView realise_date = (TextView) v.findViewById(R.id.textView2);
        TextView genre = (TextView) v.findViewById(R.id.textView3);
        TextView duration = (TextView) v.findViewById(R.id.textView4);
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        ImageView image1 = (ImageView) v.findViewById(R.id.imageView1);
//
        Picasso.with(activity)
                .load(downloadModel.get(position).getPoster())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(image);
        title.setText(downloadModel.get(position).getMUVIID());
        realise_date.setText("");

        try{
            String genre_story = downloadModel.get(position).getGenere().trim();
            if (genre_story.equals("@@@")) {
                genre_data = "";
            } else {
                String data[] = (downloadModel.get(position).getGenere().trim()).split("@@@");
                genre_data = data[0];
            }
        }catch (Exception e){
            genre_data = "";
        }



        genre.setText(genre_data);
        String dd = downloadModel.get(position).getDuration();
        LogUtil.showLog("SUBHA", dd);
        duration.setText(dd);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
                dlgAlert.setTitle("");
                dlgAlert.setMessage(languagePreference.getTextofLanguage(CONFIRM_DELETE_MESSAGE,DEFAULT_CONFIRM_DELETE_MESSAGE));

                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(DELETE_BTN,DEFAULT_DELETE_BTN), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(DELETE_BTN,DEFAULT_DELETE_BTN),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
                                downloadManager.remove(downloadModel.get(position).getDOWNLOADID());

                                String path1 = downloadModel.get(position).getPath().trim();
                                File file = new File(path1);
                                if (file != null && file.exists()) {
                                    file.delete();
                                }
                                SQLiteDatabase DB = activity.openOrCreateDatabase(DBHelper.DATABASE_NAME, activity.MODE_PRIVATE, null);
                                Cursor cursor = DB.rawQuery("SELECT LANGUAGE,PATH FROM "+DBHelper.TABLE_NAME_SUBTITLE_LUIMERE+" WHERE UID = '"+downloadModel.get(position).getUniqueId()+"'", null);
                                int count = cursor.getCount();

                                if(count>0)
                                {
                                    String Query = "DELETE FROM "+DBHelper.TABLE_NAME_SUBTITLE_LUIMERE+" WHERE UID  = '"+downloadModel.get(position).getUniqueId()+"'";
                                    DB.execSQL(Query);
                                }

                                Cursor cursor1 = DB.rawQuery("SELECT * FROM "+DBHelper.RESUME_WATCH+" WHERE UniqueId = '"+downloadModel.get(position).getUniqueId()+"'", null);
                                int count1 = cursor.getCount();

                                if(count1>0)
                                {
                                    String Query = "DELETE FROM "+DBHelper.RESUME_WATCH+" WHERE UniqueId  = '"+downloadModel.get(position).getUniqueId()+"'";
                                    DB.execSQL(Query);
                                    Log.v("BIBHU11","Resume watch record deleted");
                                }

                                audio = dbHelper.getContact(downloadModel.get(position).getUniqueId().trim());
                                downloadModel.remove(position);
                                notifyDataSetChanged();
                                activity.visible();

                                if (audio != null) {


                                    dbHelper.deleteRecord(audio);

                                }

                            }
                        });
                dlgAlert.setNegativeButton(languagePreference.getTextofLanguage(CANCEL_BUTTON,DEFAULT_CANCEL_BUTTON),null);
                dlgAlert.setCancelable(false);
                dlgAlert.setNegativeButton(languagePreference.getTextofLanguage(CANCEL_BUTTON,DEFAULT_CANCEL_BUTTON),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();



                            }
                        });

                dlgAlert.create().show();


            }


        });

        return v;
    }


}
