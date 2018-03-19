package com.home.vod.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.home.vod.util.FontUtls;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import static com.home.vod.preferences.LanguagePreference.BTN_SUBMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_SUBMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FILL_FORM_BELOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TEXT_EMIAL;
import static com.home.vod.preferences.LanguagePreference.FILL_FORM_BELOW;
import static com.home.vod.preferences.LanguagePreference.MESSAGE;
import static com.home.vod.preferences.LanguagePreference.NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.TEXT_EMIAL;
import static com.home.vod.util.Constant.authTokenStr;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldContactUsFragment extends Fragment implements ContactUsAsynTask.ContactUsListener {
    Context context;
    String regEmailStr, regNameStr,regMessageStr;
    EditText editEmailStr, editNameStr,editMessageStr;
    TextView contactFormTitle;
    Button submit;
    String sucessMsg,statusmsg;
    String contEmail;
    ContactUsAsynTask asynContactUs;
    boolean validate = true;
    LanguagePreference languagePreference;
    ProgressBarHandler pDialog;



    public OldContactUsFragment() {
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

        v.setFocusableInTouchMode(true);
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
        });


        languagePreference = LanguagePreference.getLanguagePreference(context);

        /*TextView categoryTitle = (TextView) v.findViewById(R.id.categoryTitle);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),categoryTitle);*/
      /*  Typeface castDescriptionTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        categoryTitle.setTypeface(castDescriptionTypeface);*/
       // categoryTitle.setText(getArguments().getString("title"));

        contactFormTitle = (TextView) v.findViewById(R.id.contactFormTitle);
        FontUtls.loadFont(context,context.getResources().getString(R.string.light_fonts),contactFormTitle);

     /*   Typeface contactFormTitleTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
        contactFormTitle.setTypeface(contactFormTitleTypeface)*/;
        contactFormTitle.setText(languagePreference.getTextofLanguage(FILL_FORM_BELOW, DEFAULT_FILL_FORM_BELOW));

        editEmailStr=(EditText) v.findViewById(R.id.contact_email) ;
        FontUtls.loadFont(context,context.getResources().getString(R.string.light_fonts),editEmailStr);

       /* Typeface editEmailStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
        editEmailStr.setTypeface(editEmailStrTypeface);*/
        editEmailStr.setHint(languagePreference.getTextofLanguage( TEXT_EMIAL, DEFAULT_TEXT_EMIAL));

        editNameStr=(EditText) v.findViewById(R.id.contact_name) ;
        FontUtls.loadFont(context,context.getResources().getString(R.string.light_fonts),editNameStr);

        /*Typeface editNameStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
        editNameStr.setTypeface(editNameStrTypeface);*/
        editNameStr.setHint(languagePreference.getTextofLanguage( NAME_HINT, DEFAULT_NAME_HINT));

        editMessageStr=(EditText) v.findViewById(R.id.contact_msg) ;
        FontUtls.loadFont(context,context.getResources().getString(R.string.light_fonts),editMessageStr);

        /*Typeface editMessageStrTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
        editMessageStr.setTypeface(editMessageStrTypeface);*/
        editMessageStr.setHint(languagePreference.getTextofLanguage( MESSAGE, DEFAULT_MESSAGE));
        /*******enter key of keyboard *************/

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (source.charAt(i) == '\n') {
                        return " ";
                    }
                }
                return null;
            }
        };

        editMessageStr.setFilters(new InputFilter[]{filter});
        submit = (Button) v.findViewById(R.id.submit_cont);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),submit);
/*
        Typeface submitTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        submit.setTypeface(submitTypeface);*/
        submit.setText(languagePreference.getTextofLanguage( BTN_SUBMIT, DEFAULT_BTN_SUBMIT));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Submitted successfully", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                SubmmitClicked();

            }
        });

        editNameStr.addTextChangedListener(new TextWatcher() {
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
        });
        editEmailStr.addTextChangedListener(new TextWatcher() {
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
        });
        editMessageStr.addTextChangedListener(new TextWatcher() {
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







        return v;
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public void SubmmitClicked() {

        regEmailStr = editEmailStr.getText().toString().trim();
        regNameStr = editNameStr.getText().toString().trim();
        regMessageStr = editMessageStr.getText().toString().trim();


        if (regNameStr.equals("")){
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
        }

    if (validate){

        if (NetworkStatus.getInstance().isConnected(context)){
            ContactUsInputModel contactUsInputModel=new ContactUsInputModel();
            contactUsInputModel.setAuthToken(authTokenStr);
            contactUsInputModel.setEmail(String.valueOf(regEmailStr));
            contactUsInputModel.setName(String.valueOf(regNameStr));
            contactUsInputModel.setMessage(String.valueOf(regMessageStr));
            contactUsInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,DEFAULT_SELECTED_LANGUAGE_CODE));
            ContactUsAsynTask asynContactUs = new ContactUsAsynTask(contactUsInputModel, this,context);
            asynContactUs.execute();

        }else{
            Toast.makeText(getActivity(),languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
        }
    }else {
        validate=true;
        return ;
    }



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


//    private class AsynContactUs extends AsyncTask<String, Void, Void> {
////    ProgressBarHandler pDialog;
//    String contName;
//    JSONObject myJson = null;
//    int status;
//
//    String contMessage;
//    String responseStr;
//
////    @Override
////    protected void onPreExecute() {
////        pDialog = new ProgressBarHandler(getActivity().getBaseContext());
////        pDialog.show();
////        LogUtil.showLog("NIhar","onpreExecution");
////    }
//
//    @Override
//    protected Void doInBackground(String... params) {
//
//        String urlRouteList = Util.rootUrl().trim() + Util.OldOldContactUsFragment.trim();
//
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(urlRouteList);
//            httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//            httppost.addHeader("authToken", Util.authTokenStr.trim());
//            httppost.addHeader("name", String.valueOf(regNameStr));
//            httppost.addHeader("email", String.valueOf(regEmailStr));
//            httppost.addHeader("message", String.valueOf(regMessageStr));
//            httppost.addHeader("lang_code",languagePreference.getTextofLanguage(Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//            try {
//                HttpResponse response = httpclient.execute(httppost);
//                responseStr = EntityUtils.toString(response.getEntity());
//
//
//            } catch (org.apache.http.conn.ConnectTimeoutException e) {
////                getActivity().runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        if (pDialog != null && pDialog.isShowing()) {
////                            pDialog.hide();
////                            pDialog = null;
////                        }
////                        status = 0;
////
////                    }
////
////                });
//            }
//        } catch (IOException e) {
////            if (pDialog != null && pDialog.isShowing()) {
////                pDialog.hide();
////                pDialog = null;
////            }
////            status = 0;
//            e.printStackTrace();
//        }
//        if (responseStr != null) {
//            try {
//                myJson = new JSONObject(responseStr);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            status = Integer.parseInt(myJson.optString("code"));
//            sucessMsg = myJson.optString("success_msg");
//            statusmsg = myJson.optString("status");
//
//
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        Toast.makeText(getActivity(), sucessMsg, Toast.LENGTH_SHORT).show();
//
////        try {
////            if (pDialog != null && pDialog.isShowing()) {
////                pDialog.hide();
////                pDialog = null;
////            }
////        } catch (IllegalArgumentException ex) {
////            status = 0;
////
////        }
////        if (status == 0) {
////
////        }
//        editMessageStr.setText("");
//        editNameStr.setText("");
//        editEmailStr.setText("");
//        editMessageStr.setError(null);
//        editNameStr.setError(null);
//        editEmailStr.setError(null);
//
//    }
//}
}
