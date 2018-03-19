package com.home.vod.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.home.apisdk.apiController.GetUserProfileAsynctask;
import com.home.apisdk.apiController.UpadteUserProfileAsynctask;
import com.home.apisdk.apiModel.Get_UserProfile_Input;
import com.home.apisdk.apiModel.Get_UserProfile_Output;
import com.home.apisdk.apiModel.Update_UserProfile_Input;
import com.home.apisdk.apiModel.Update_UserProfile_Output;
import com.home.vod.ProfileHandler;
import com.home.vod.R;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.CHANGE_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CHANGE_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_IS_RESTRICT_DEVICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MANAGE_DEVICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NEW_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PASSWORDS_DO_NOT_MATCH;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PROFILE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PROFILE_UPDATED;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_UPDATE_PROFILE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_UPDATE_PROFILE_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VALID_CONFIRM_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_DATA_FETCHING;
import static com.home.vod.preferences.LanguagePreference.MANAGE_DEVICE;
import static com.home.vod.preferences.LanguagePreference.NEW_PASSWORD;
import static com.home.vod.preferences.LanguagePreference.NO_CONTENT;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.PASSWORDS_DO_NOT_MATCH;
import static com.home.vod.preferences.LanguagePreference.PROFILE;
import static com.home.vod.preferences.LanguagePreference.PROFILE_UPDATED;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.UPDATE_PROFILE;
import static com.home.vod.preferences.LanguagePreference.UPDATE_PROFILE_ALERT;
import static com.home.vod.preferences.LanguagePreference.VALID_CONFIRM_PASSWORD;
import static com.home.vod.util.Constant.authTokenStr;

public class ProfileActivity extends AppCompatActivity implements
        UpadteUserProfileAsynctask.Update_UserProfileListener, GetUserProfileAsynctask.Get_UserProfileListener {
    SharedPreferences loginPref;

    ImageView bannerImageView;
    ProfileHandler profileHandler;
    EditText editConfirmPassword, editNewPassword;
    EditText emailAddressEditText;
    Button changePassword, update_profile, manage_devices;

    String Name, Password;
    boolean password_visibility = false;
    private RelativeLayout noInternetConnectionLayout;
    RelativeLayout noDataLayout;
    TextView noDataTextView;
    TextView noInternetTextView;
    String User_Id = "";
    String Email_Id = "";
    TextView name_of_user;
    ProgressBarHandler pDialog;
    LanguagePreference languagePreference;


    // load asynctask
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    // Added for country and language spinner
    Spinner country_spinner, language_spinner;
    ArrayAdapter<String> Language_arrayAdapter, Country_arrayAdapter;

    String Selected_Language, Selected_Country = "0", Selected_Language_Id = "", Selected_Country_Id = "";
    PreferenceManager preferenceManager;
    List<String> Country_List, Country_Code_List, Language_List, Language_Code_List;
    FeatureHandler featureHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        languagePreference = LanguagePreference.getLanguagePreference(ProfileActivity.this);
        featureHandler = FeatureHandler.getFeaturePreference(ProfileActivity.this);

        bannerImageView = (ImageView) findViewById(R.id.bannerImageView);
        editNewPassword = (EditText) findViewById(R.id.editNewPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        profileHandler=new ProfileHandler(this);
        // editProfileNameEditText = (EditText) findViewById(R.id.editProfileNameEditText);

        emailAddressEditText = (EditText) findViewById(R.id.emailAddressEditText);
        changePassword = (Button) findViewById(R.id.changePasswordButton);
        update_profile = (Button) findViewById(R.id.update_profile);
        manage_devices = (Button) findViewById(R.id.manage_devices);

        noInternetConnectionLayout = (RelativeLayout) findViewById(R.id.noInternet);
        noDataLayout = (RelativeLayout) findViewById(R.id.noData);
        noInternetTextView = (TextView) findViewById(R.id.noInternetTextView);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        noInternetTextView.setText(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
        noDataTextView.setText(languagePreference.getTextofLanguage(ERROR_IN_DATA_FETCHING, DEFAULT_ERROR_IN_DATA_FETCHING));

        noInternetConnectionLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);

        if (!featureHandler.getFeatureStatus(FeatureHandler.IS_RESTRICTIVE_DEVICE, FeatureHandler.DEFAULT_IS_RESTRICTIVE_DEVICE)) {
            manage_devices.setVisibility(View.GONE);
        }

        editConfirmPassword.setVisibility(View.GONE);
        editNewPassword.setVisibility(View.GONE);
        name_of_user = (TextView) findViewById(R.id.name_of_user);

        country_spinner = (Spinner) findViewById(R.id.countrySpinner);
        language_spinner = (Spinner) findViewById(R.id.languageSpinner);
        country_spinner.setVisibility(View.GONE);
        language_spinner.setVisibility(View.GONE);
        // FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), editProfileNameEditText);
        FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), editConfirmPassword);

        FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), editNewPassword);
        FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.regular_fonts), changePassword);
        FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.regular_fonts), update_profile);
        FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.regular_fonts), manage_devices);

        //  editProfileNameEditText.setHint(languagePreference.getTextofLanguage(NAME_HINT, DEFAULT_NAME_HINT));
        editConfirmPassword.setHint(languagePreference.getTextofLanguage(CONFIRM_PASSWORD, DEFAULT_CONFIRM_PASSWORD));
        editNewPassword.setHint(languagePreference.getTextofLanguage(NEW_PASSWORD, DEFAULT_NEW_PASSWORD));
        changePassword.setText(languagePreference.getTextofLanguage(CHANGE_PASSWORD, DEFAULT_CHANGE_PASSWORD));
        update_profile.setText(languagePreference.getTextofLanguage(UPDATE_PROFILE, DEFAULT_UPDATE_PROFILE));
        manage_devices.setText(languagePreference.getTextofLanguage(MANAGE_DEVICE, DEFAULT_MANAGE_DEVICE));


        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(PROFILE,DEFAULT_PROFILE));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


       /* userId = getIntent().getStringExtra("LOGID");
        emailId = getIntent().getStringExtra("EMAIL");
*/
        if (loginPref != null) {

        }

        manage_devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                if (NetworkStatus.getInstance().isConnected(ProfileActivity.this)) {
                    Intent intent = new Intent(ProfileActivity.this, ManageDevices.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                }

            }
        });

        // This is used for language and country spunner


        Country_List = Arrays.asList(getResources().getStringArray(R.array.country));
        Country_Code_List = Arrays.asList(getResources().getStringArray(R.array.countrycode));
        Language_List = Arrays.asList(getResources().getStringArray(R.array.languages));
        Language_Code_List = Arrays.asList(getResources().getStringArray(R.array.languagesCode));


        Language_arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.country_language_spinner, Language_List) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), (TextView) v);

/*
                Typeface externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                ((TextView) v).setTypeface(externalFont);*/
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), (TextView) v);
/*
                Typeface externalFont1 = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                ((TextView) v).setTypeface(externalFont1);*/

                return v;
            }

        };

        language_spinner.setAdapter(Language_arrayAdapter);

        Country_arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.country_language_spinner, Country_List) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), (TextView) v);
/*
                Typeface externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                ((TextView) v).setTypeface(externalFont);*/
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                FontUtls.loadFont(ProfileActivity.this, getResources().getString(R.string.light_fonts), (TextView) v);

               /* Typeface externalFont1 = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light_fonts));
                ((TextView) v).setTypeface(externalFont1);*/

                return v;
            }

        };

        country_spinner.setAdapter(Country_arrayAdapter);

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Selected_Country_Id = Country_Code_List.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        language_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected_Language_Id = Language_Code_List.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

// =======End ===========================//

        if (NetworkStatus.getInstance().isConnected(ProfileActivity.this)) {

            Get_UserProfile_Input get_userProfile_input = new Get_UserProfile_Input();
            get_userProfile_input.setAuthToken(authTokenStr);
            get_userProfile_input.setUser_id(preferenceManager.getUseridFromPref());
            get_userProfile_input.setEmail(preferenceManager.getEmailIdFromPref());
            get_userProfile_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));

            GetUserProfileAsynctask asynLoadProfileDetails = new GetUserProfileAsynctask(get_userProfile_input, this, this);
            asynLoadProfileDetails.executeOnExecutor(threadPoolExecutor);
        } else {
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
        }

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    editConfirmPassword.setText("");
                    editNewPassword.setText("");
                    editNewPassword.requestFocus();
                    editConfirmPassword.setVisibility(View.VISIBLE);
                    editNewPassword.setVisibility(View.VISIBLE);
                    changePassword.setVisibility(View.GONE);
                } catch (Exception e) {
                }

            }
        });



        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profileHandler.updateProfileHandler();

            }
        });

    }

    public void ShowDialog(String Title, String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ProfileActivity.this, R.style.MyAlertDialogStyle);
        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(Title);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        dlgAlert.create().show();
    }

    public void UpdateProfile(String first_name, String last_name, String phone_No) {

        Update_UserProfile_Input update_userProfile_input = new Update_UserProfile_Input();
        update_userProfile_input.setAuthToken(authTokenStr);
        update_userProfile_input.setUser_id(preferenceManager.getUseridFromPref().trim());
        update_userProfile_input.setName(first_name);
        update_userProfile_input.setPhone_no(phone_No);
        update_userProfile_input.setCustom_last_name(last_name);
        String confirmPasswordStr = editNewPassword.getText().toString().trim();
        if (!confirmPasswordStr.trim().equalsIgnoreCase("") && !confirmPasswordStr.isEmpty() && !confirmPasswordStr.equalsIgnoreCase("null") && !confirmPasswordStr.equalsIgnoreCase(null) && !confirmPasswordStr.equals(null) && !confirmPasswordStr.matches("")) {
            update_userProfile_input.setPassword(confirmPasswordStr.trim());
        }
        update_userProfile_input.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        update_userProfile_input.setCustom_country(Selected_Country_Id);
        update_userProfile_input.setCustom_languages(Selected_Language_Id);
        UpadteUserProfileAsynctask asyncLoadVideos = new UpadteUserProfileAsynctask(update_userProfile_input, this, this);
        asyncLoadVideos.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onUpdateUserProfilePreExecuteStarted() {
        pDialog = new ProgressBarHandler(ProfileActivity.this);
        pDialog.show();
    }

    @Override
    public void onUpdateUserProfilePostExecuteCompleted(Update_UserProfile_Output update_userProfile_output, int code, String message) {

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {

        }

        if (code > 0) {
            if (code == 200) {


                changePassword.setVisibility(View.VISIBLE);
                editConfirmPassword.setText("");
                editNewPassword.setText("");
                editConfirmPassword.setVisibility(View.GONE);
                editNewPassword.setVisibility(View.GONE);

                String confirmPasswordStr = editNewPassword.getText().toString().trim();
                name_of_user.setText(profileHandler.first_nameStr);

                if (!confirmPasswordStr.trim().equalsIgnoreCase("") &&
                        !confirmPasswordStr.isEmpty() &&
                        !confirmPasswordStr.equalsIgnoreCase("null") &&
                        !confirmPasswordStr.equalsIgnoreCase(null) && !confirmPasswordStr.equals(null) &&
                        !confirmPasswordStr.matches("")) {
                    preferenceManager.setPwdToPref(confirmPasswordStr);
                }
                if (update_userProfile_output != null) {

                    String displayNameStr = update_userProfile_output.getName();
                    preferenceManager.setDispNameToPref(displayNameStr);
                    String displayPhoneNumber = update_userProfile_output.getPhone_no();
                    preferenceManager.setDispPhoneToPref(displayPhoneNumber);
                }
                Util.showToast(ProfileActivity.this, languagePreference.getTextofLanguage(PROFILE_UPDATED, DEFAULT_PROFILE_UPDATED));
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (name_of_user != null) {
                    name_of_user.clearFocus();
                    name_of_user.setCursorVisible(false);
                }

                if (editConfirmPassword != null) {
                    editConfirmPassword.clearFocus();

                }
                if (editNewPassword != null) {
                    editNewPassword.clearFocus();
                }
            } else {

            }
        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ProfileActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(UPDATE_PROFILE_ALERT, DEFAULT_UPDATE_PROFILE_ALERT));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            editConfirmPassword.setText("");
                            editNewPassword.setText("");
                        }
                    });
            dlgAlert.create().show();
        }


    }

//
//    private class AsynUpdateProfile extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//
//        int statusCode;
//        String loggedInIdStr;
//        String confirmPasswordStr = editNewPassword.getText().toString().trim();
//        String nameStr = editProfileNameEditText.getText().toString().trim();
//
//        String responseStr;
//        JSONObject myJson = null;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            if (loginPref != null) {
//                loggedInIdStr = loginPref.getString("PREFS_LOGGEDIN_ID_KEY", null);
//            }
//
//            String urlRouteList = Util.rootUrl().trim() + Util.updateProfileUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("user_id", User_Id.trim());
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                httppost.addHeader("name", nameStr.trim());
//                if (!confirmPasswordStr.trim().equalsIgnoreCase("") && !confirmPasswordStr.isEmpty() && !confirmPasswordStr.equalsIgnoreCase("null") && !confirmPasswordStr.equalsIgnoreCase(null) && !confirmPasswordStr.equals(null) && !confirmPasswordStr.matches("")) {
//                    httppost.addHeader("password", confirmPasswordStr.trim());
//                }
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//                httppost.addHeader("custom_country", Selected_Country_Id);
//                httppost.addHeader("custom_languages", Selected_Language_Id);
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            statusCode = 0;
//                            editOldPassword.setText("");
//                            editNewPassword.setText("");
//                            Toast.makeText(ProfileActivity.this, languagePreference.getTextofLanguage(SLOW_INTERNET_CONNECTION, Util.DEFAULT_SLOW_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    statusCode = 0;
//
//                    e.printStackTrace();
//                }
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    statusCode = Integer.parseInt(myJson.optString("code"));
//
//                }
//
//            } catch (Exception e) {
//                statusCode = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//
//            if (responseStr == null) {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//                    statusCode = 0;
//
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ProfileActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(UPDATE_PROFILE_ALERT, Util.DEFAULT_UPDATE_PROFILE_ALERT));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                editOldPassword.setText("");
//                                editNewPassword.setText("");
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//
//            if (statusCode > 0) {
//
//                if (statusCode == 200) {
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        SharedPreferences.Editor editor = loginPref.edit();
//                        if (myJson.has("name")) {
//                            String displayNameStr = myJson.optString("name");
//                            editor.putString("PREFS_LOGIN_DISPLAY_NAME_KEY", displayNameStr);
//                        }
//                        if (!confirmPasswordStr.trim().equalsIgnoreCase("") && !confirmPasswordStr.isEmpty() && !confirmPasswordStr.equalsIgnoreCase("null") && !confirmPasswordStr.equalsIgnoreCase(null) && !confirmPasswordStr.equals(null) && !confirmPasswordStr.matches("")) {
//                            editor.putString("PREFS_LOGGEDIN_PASSWORD_KEY", confirmPasswordStr.trim());
//
//                        }
//                        editor.commit();
//                    }
//                    SharedPreferences.Editor editor = loginPref.edit();
//                    if (myJson.has("name")) {
//                        String displayNameStr = myJson.optString("display_name");
//                        editor.putString("name", displayNameStr);
//                    }
//                    if (!confirmPasswordStr.trim().equalsIgnoreCase("") && !confirmPasswordStr.isEmpty() && !confirmPasswordStr.equalsIgnoreCase("null") && !confirmPasswordStr.equalsIgnoreCase(null) && !confirmPasswordStr.equals(null) && !confirmPasswordStr.matches("")) {
//                        editor.putString("PREFS_LOGGEDIN_PASSWORD_KEY", confirmPasswordStr.trim());
//
//                    }
//
//                    editor.commit();
//                    name_of_user.setText(editProfileNameEditText.getText().toString().trim());
//
//
//                    Toast.makeText(ProfileActivity.this, languagePreference.getTextofLanguage(PROFILE_UPDATED, Util.DEFAULT_PROFILE_UPDATED), Toast.LENGTH_SHORT).show();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//                    if (name_of_user != null) {
//                        name_of_user.clearFocus();
//                        name_of_user.setCursorVisible(false);
//                    }
//                    if (editOldPassword != null) {
//                        editOldPassword.clearFocus();
//
//                    }
//                    if (editNewPassword != null) {
//                        editNewPassword.clearFocus();
//                    }
//                   /* if (fullNameEditText != null) fullNameEditText.clearFocus();
//                    if (passwordEditText != null) passwordEditText.clearFocus();
//                    if (confirmPasswordEditText != null) confirmPasswordEditText.clearFocus();*/
//
//                } else {
//
//                    try {
//                        if (pDialog != null && pDialog.isShowing()) {
//                            pDialog.hide();
//                            pDialog = null;
//                        }
//                    } catch (IllegalArgumentException ex) {
//                        statusCode = 0;
//
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ProfileActivity.this, R.style.MyAlertDialogStyle);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(UPDATE_PROFILE_ALERT, Util.DEFAULT_UPDATE_PROFILE_ALERT));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    editOldPassword.setText("");
//                                    editNewPassword.setText("");
//
//
//                                }
//                            });
//                    dlgAlert.create().show();
//                }
//            } else {
//                try {
//                    if (pDialog != null && pDialog.isShowing()) {
//                        pDialog.hide();
//                        pDialog = null;
//                    }
//                } catch (IllegalArgumentException ex) {
//
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ProfileActivity.this, R.style.MyAlertDialogStyle);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(UPDATE_PROFILE_ALERT, Util.DEFAULT_UPDATE_PROFILE_ALERT));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                editOldPassword.setText("");
//                                editNewPassword.setText("");
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog = new ProgressBarHandler(ProfileActivity.this);
//            pDialog.show();
//        }
//
//
//    }
    //Getting Profile Details from The Api

    @Override
    public void onGet_UserProfilePreExecuteStarted() {

        pDialog = new ProgressBarHandler(ProfileActivity.this);
        pDialog.show();
    }

    @Override
    public void onGet_UserProfilePostExecuteCompleted(Get_UserProfile_Output get_userProfile_output, int code, String message, String status) {

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {

        }

        if (code == 200) {

            if (get_userProfile_output != null) {


                if (Selected_Country_Id.equals("0")) {
                    country_spinner.setSelection(224);
                    Selected_Country_Id = Country_Code_List.get(224);
                    LogUtil.showLog("Muvi", "country not  matched =" + Selected_Country + "==" + Selected_Country_Id);
                } else {
                    for (int i = 0; i < Country_Code_List.size(); i++) {
                        if (Selected_Country_Id.trim().equals(Country_Code_List.get(i))) {
                            country_spinner.setSelection(i);
                            Selected_Country_Id = Country_Code_List.get(i);

                            LogUtil.showLog("Muvi", "country  matched =" + Selected_Country_Id + "==" + Selected_Country_Id);
                        }
                    }
                }
                Country_arrayAdapter.notifyDataSetChanged();


                for (int i = 0; i < Language_Code_List.size(); i++) {
                    if (Selected_Language_Id.trim().equals(Language_Code_List.get(i))) {
                        language_spinner.setSelection(i);
                        Selected_Language_Id = Language_Code_List.get(i);

                        LogUtil.showLog("Muvi", "Selected_Language_Id =" + Selected_Language_Id);
                    }
                }
                Language_arrayAdapter.notifyDataSetChanged();


                profileHandler.setNameTxt(get_userProfile_output.getDisplay_name(), get_userProfile_output.getCustom_last_name(), get_userProfile_output.getPhone());
                name_of_user.setText(get_userProfile_output.getDisplay_name());
                emailAddressEditText.setText(get_userProfile_output.getEmail());
                if (get_userProfile_output.getProfile_image().matches(NO_DATA)) {
                    bannerImageView.setAlpha(0.8f);
                    bannerImageView.setImageResource(R.drawable.logo);
                } else {
                    Picasso.with(ProfileActivity.this)
                            .load(get_userProfile_output.getProfile_image())
                            .placeholder(R.drawable.logo).error(R.drawable.logo).noFade().resize(200, 200).into(bannerImageView, new Callback() {

                        @Override
                        public void onSuccess() {

                            Bitmap bitmapFromPalette = ((BitmapDrawable) bannerImageView.getDrawable()).getBitmap();
                            Palette palette = Palette.generate(bitmapFromPalette);
                        }

                        @Override
                        public void onError() {
                            // reset your views to default colors, etc.
                            bannerImageView.setAlpha(0.8f);
                            bannerImageView.setImageResource(R.drawable.no_image);
                        }

                    });
                    if (get_userProfile_output.getProfile_image() != null && get_userProfile_output.getProfile_image().length() > 0) {
                        int pos = get_userProfile_output.getProfile_image().lastIndexOf("/");
                        String x = get_userProfile_output.getProfile_image().substring(pos + 1, get_userProfile_output.getProfile_image().length());

                        if (x.equalsIgnoreCase("no-user.png")) {
                            bannerImageView.setImageResource(R.drawable.no_image);
                            bannerImageView.setAlpha(0.8f);
                            //imagebg.setBackgroundColor(Color.parseColor("#969393"));

                        } else {
                            Picasso.with(ProfileActivity.this)
                                    .load(get_userProfile_output.getProfile_image())
                                    .placeholder(R.drawable.logo).error(R.drawable.logo).noFade().resize(200, 200).into(bannerImageView, new Callback() {

                                @Override
                                public void onSuccess() {
                                    bannerImageView.setAlpha(0.3f);

                                }

                                @Override
                                public void onError() {
                                    bannerImageView.setImageResource(R.drawable.no_image);
                                    bannerImageView.setAlpha(0.8f);
                                    //imagebg.setBackgroundColor(Color.parseColor("#969393"));
                                }

                            });
                        }
                    }
                }
            }
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
            noInternetConnectionLayout.setVisibility(View.GONE);
        }
    }



    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }

    public void removeFocusFromViews() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        removeFocusFromViews();

    }

    @Override
    public void onPause() {
        super.onPause();

        removeFocusFromViews();

    }


    public boolean passwordMatchValidation() {
        return editConfirmPassword.getText().toString().matches(editNewPassword.getText().toString().trim());
    }
}
