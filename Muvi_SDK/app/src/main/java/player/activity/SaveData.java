package player.activity;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by MUVI on 08-05-2017.
 */

public class SaveData {
    Context context;
    String sharedPreferences_Name;
    SharedPreferences languageSharedPref;


    public SaveData(Context context, String sharedPreferences_Name) {
        this.context = context;
        this.sharedPreferences_Name = sharedPreferences_Name;
        CreateSharedPreff();
    }

    public void CreateSharedPreff() {
        languageSharedPref = context.getSharedPreferences(sharedPreferences_Name, 0); // 0 - for private mode
    }

    public void SetData(String Key, String Value) {
        SharedPreferences.Editor editor = languageSharedPref.edit();
        editor.putString(Key, Value);
        editor.commit();
    }

    public String getData(String Key, String defaultValue) {
        String str = languageSharedPref.getString(Key, defaultValue);
        return str;
    }

    public void ClearData(){
        SharedPreferences.Editor editor = languageSharedPref.edit();
        editor.clear();
        editor.commit();
    }

}
