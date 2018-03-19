package com.home.vod.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.apisdk.apiController.ContactUsAsynTask;
import com.home.apisdk.apiModel.ContactUsInputModel;
import com.home.apisdk.apiModel.ContactUsOutputModel;
import com.home.vod.R;
import com.home.vod.activity.MainActivity;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.home.vod.preferences.LanguagePreference.BTN_SUBMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_SUBMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ENTER_YOUR_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FILL_FORM_BELOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.ENTER_REGISTER_FIELDS_DATA;
import static com.home.vod.preferences.LanguagePreference.ENTER_YOUR_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.FILL_FORM_BELOW;
import static com.home.vod.preferences.LanguagePreference.MESSAGE;
import static com.home.vod.preferences.LanguagePreference.NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.OOPS_INVALID_EMAIL;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.TEXT_EMIAL;
import static com.home.vod.util.Constant.authTokenStr;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements ContactUsAsynTask.ContactUsListener{
    ProgressBarHandler pDialog;
    Context context;
    String regEmailStr, regNameStr,regMessageStr;
    EditText editEmailStr, editNameStr,editMessageStr;
    TextView contactFormTitle;
    Button submit;
    String sucessMsg,statusmsg;
    String contEmail;
    boolean validate = true;
    LanguagePreference languagePreference;



    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* getActionBar().setTitle(getArguments().getString(""));
        setHasOptionsMenu(true);*/
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        context = getActivity();
        languagePreference = LanguagePreference.getLanguagePreference(context);

     //   TextView categoryTitle = (TextView) v.findViewById(R.id.categoryTitle);
        Typeface castDescriptionTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        /*categoryTitle.setTypeface(castDescriptionTypeface);
        categoryTitle.setText(getArguments().getString("title"));*/
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("title"));
        contactFormTitle = (TextView) v.findViewById(R.id.contactFormTitle);
        Typeface contactFormTitleTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        contactFormTitle.setTypeface(contactFormTitleTypeface);
        contactFormTitle.setText(languagePreference.getTextofLanguage(FILL_FORM_BELOW, DEFAULT_FILL_FORM_BELOW));

        //Log.v("SUBHA","ontact = "+ languagePreference.getTextofLanguage(FILL_FORM_BELOW, DEFAULT_FILL_FORM_BELOW));

        editEmailStr=(EditText) v.findViewById(R.id.contact_email) ;
        Typeface editEmailStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        editEmailStr.setTypeface(editEmailStrTypeface);
        editEmailStr.setHint(languagePreference.getTextofLanguage(TEXT_EMIAL, DEFAULT_TEXT_EMIAL));

        editNameStr=(EditText) v.findViewById(R.id.contact_name) ;
        Typeface editNameStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        editNameStr.setTypeface(editNameStrTypeface);
        editNameStr.setHint(languagePreference.getTextofLanguage(NAME_HINT, DEFAULT_NAME_HINT));
        editNameStr.requestFocus();
      /*  InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editNameStr, InputMethodManager.SHOW_FORCED);*/
        //showKeyboard();

        editMessageStr=(EditText) v.findViewById(R.id.contact_msg) ;
        Typeface editMessageStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        editMessageStr.setTypeface(editMessageStrTypeface);
        editMessageStr.setHint(languagePreference.getTextofLanguage(ENTER_YOUR_MESSAGE, DEFAULT_ENTER_YOUR_MESSAGE));

        submit = (Button) v.findViewById(R.id.submit_cont);
        Typeface submitTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.fonts));
        submit.setTypeface(submitTypeface);
        submit.setText(languagePreference.getTextofLanguage(BTN_SUBMIT, DEFAULT_BTN_SUBMIT));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Submitted successfully", Toast.LENGTH_SHORT).show();

                SubmmitClicked();

            }
        });

     /*   editNameStr.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        editMessageStr.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        editEmailStr.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
*/
        /*editNameStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    editNameStr.setError(null);
                }
                else{
                    editNameStr.setError("Required Field . ");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        /*editEmailStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                   if (Util.isValidMail((editEmailStr.getText().toString().trim()))){
                       editEmailStr.setError(null);
                   }else
                   {
                       editEmailStr.setError("Invalid Email.");
                   }
                }
                else{
                    editEmailStr.setError("Required Field.");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        /*editMessageStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    editMessageStr.setError(null);
                }
                else{
                    editMessageStr.setError("Required Field.");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/

        //  requestFocus(editNameStr);


      /*  v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        final Intent startIntent = new Intent(getActivity(), MainActivity.class);

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().startActivity(startIntent);

                                getActivity().finish();

                            }
                        });
                    }
                }
                return false;
            }
        });*/

        editMessageStr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                       /* final Intent startIntent = new Intent(getActivity(), MainActivity.class);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().startActivity(startIntent);
                                getActivity().finish();*/


                    }
                }
                return false;
            }
        });

        editNameStr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        final Intent startIntent = new Intent(getActivity(), MainActivity.class);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().startActivity(startIntent);
                                getActivity().finish();


                    }
                }
                return false;
            }
        });

        editEmailStr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        final Intent startIntent = new Intent(getActivity(), MainActivity.class);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        getActivity().startActivity(startIntent);
                        getActivity().finish();


                    }
                }
                return false;
            }
        });

        return v;
    }


    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public void SubmmitClicked() {

        regEmailStr = editEmailStr.getText().toString().trim();
        regNameStr = editNameStr.getText().toString().trim();
        regMessageStr = editMessageStr.getText().toString().trim();
        regMessageStr = regMessageStr.replaceAll("(\r\n|\n\r|\r|\n|<br />)", " ");

        boolean isNetwork = NetworkStatus.getInstance().isConnected(context);
        if (isNetwork) {
            if (!regNameStr.matches("") && (!regEmailStr.matches("")) && (!regMessageStr.matches(""))) {
                boolean isValidEmail = Util.isValidMail(regEmailStr);
                if (isValidEmail) {
                    if (validate){
                        ContactUsInputModel contactUsInputModel=new ContactUsInputModel();
                        contactUsInputModel.setAuthToken(authTokenStr);
                        contactUsInputModel.setEmail(String.valueOf(regEmailStr));
                        contactUsInputModel.setName(String.valueOf(regNameStr));
                        contactUsInputModel.setMessage(String.valueOf(regMessageStr));
                        contactUsInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,DEFAULT_SELECTED_LANGUAGE_CODE));
                        ContactUsAsynTask asynContactUs = new ContactUsAsynTask(contactUsInputModel, this,context);
                        asynContactUs.execute();

                    }else {
                        validate=true;
                        return ;
                    }

                } else {
                    Toast.makeText(context, languagePreference.getTextofLanguage(OOPS_INVALID_EMAIL, DEFAULT_OOPS_INVALID_EMAIL), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, languagePreference.getTextofLanguage(ENTER_REGISTER_FIELDS_DATA,DEFAULT_ENTER_REGISTER_FIELDS_DATA), Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(context, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION,DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
        }

       /* if (regNameStr.equals("")){
            editNameStr.setError("Required Field.");
            validate=false;
        }

        if (regEmailStr.equals("")){
            editEmailStr.setError("Required Field.");
            validate=false;
        }else {
            if (Util.isValidMail((editEmailStr.getText().toString().trim()))){
                editEmailStr.setError(null);
            }else
            {
                editEmailStr.setError("Invalid Email.");
                validate=false;
            }

        }

        if (regMessageStr.equals("")){
            editMessageStr.setError("Required Field.");
            validate=false;
        }*/




//
//        boolean isNetwork = Util.checkNetwork(getActivity());
//        if (isNetwork) {
//            if (!regEmailStr.equals("")) {
//                boolean isValidEmail = Util.isValidMail(regEmailStr);
//                boolean isValidname = Util.isValidMail(regNameStr);
//                boolean isValidMessage = Util.isValidMail(regEmailStr);
//                if (isValidEmail == true) {
//
////                    Toast.makeText(getActivity(), sucessMsg, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), Util.getTextofLanguage(getActivity(), Util.OOPS_INVALID_EMAIL, Util.DEFAULT_OOPS_INVALID_EMAIL), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(getActivity(), Util.getTextofLanguage(getActivity(), Util.EMPTY_FIELD, Util.DEFAULT_EMPTY_FIELD), Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(getActivity(), Util.getTextofLanguage(getActivity(), Util.NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//        }
//
//        return isNetwork;
    }



    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getActivity().getCurrentFocus();
        if (v != null)
            imm.showSoftInput(v, 0);
    }

   /* private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }*/

/*
    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    final Intent startIntent = new Intent(getActivity(), MainActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().startActivity(startIntent);
                    getActivity().finish();
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
*/

    @Override
    public void onContactUsPreExecuteStarted() {
        pDialog = new ProgressBarHandler(context);
        pDialog.show();

    }

    @Override
    public void onContactUsPostExecuteCompleted(ContactUsOutputModel contactUsOutputModel, int code, String message, String status) {

        Toast.makeText(getActivity(), contactUsOutputModel.getSuccess_msg(), Toast.LENGTH_SHORT).show();
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.hide();
                pDialog = null;
            }
        } catch (IllegalArgumentException ex) {

        }

        editMessageStr.setText("");
        editNameStr.setText("");
        editEmailStr.setText("");
        editMessageStr.setError(null);
        editNameStr.setError(null);
        editEmailStr.setError(null);

    }
}
