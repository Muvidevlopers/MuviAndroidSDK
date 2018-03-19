package com.home.vod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.home.vod.activity.LoginActivity;
import com.home.vod.preferences.LanguagePreference;

import org.json.JSONException;
import org.json.JSONObject;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_GMAIL_SIGNIN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_LOGIN_FACEBOOK;
import static com.home.vod.preferences.LanguagePreference.DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.GMAIL_SIGNIN;
import static com.home.vod.preferences.LanguagePreference.LOGIN_FACEBOOK;

/**
 * Created by Android on 9/21/2017.
 */

public class LoginHandler {
    LoginActivity context;
    TextView gmailTest;
    private RelativeLayout googleSignView;
    private LinearLayout btnLogin;
    private Button loginButton;
    private Button registerButton;
    LoginButton loginWithFacebookButton;
    TextView fbLoginTextView;
    private LanguagePreference languagePreference;

    String fbUserId = "";
    String fbEmail = "";
    String fbName = "";

    public LoginHandler(LoginActivity context) {
        this.context=context;
        gmailTest=(TextView) context.findViewById(R.id.textView);
        googleSignView = (RelativeLayout) context.findViewById(R.id.sign_in_button);
        loginWithFacebookButton = (LoginButton) context.findViewById(R.id.loginWithFacebookButton);
        loginWithFacebookButton.setVisibility(View.GONE);
        loginWithFacebookButton.setReadPermissions("public_profile", "email", "user_friends");
        fbLoginTextView = (TextView) context.findViewById(R.id.fbLoginTextView);

        btnLogin = (LinearLayout) context.findViewById(R.id.btnLogin);
        btnLogin.setVisibility(View.VISIBLE);
        googleSignView.setVisibility(View.VISIBLE);
    }

    public void callSignin(LanguagePreference languagePreference){
        gmailTest.setText(languagePreference.getTextofLanguage(GMAIL_SIGNIN, DEFAULT_GMAIL_SIGNIN));
        googleSignView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.signIn();
            }
        });
    }
    public void callFblogin(final CallbackManager callbackManager,Button registerButton,LanguagePreference languagePreference){

        this.registerButton=registerButton;
        this.languagePreference=languagePreference;
        fbLoginTextView.setText(languagePreference.getTextofLanguage(LOGIN_FACEBOOK,DEFAULT_LOGIN_FACEBOOK));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithFacebookButton.performClick();

                loginWithFacebookButton.setPressed(true);

                loginWithFacebookButton.invalidate();

                loginWithFacebookButton.registerCallback(callbackManager, mCallBack);

                loginWithFacebookButton.setPressed(false);

                loginWithFacebookButton.invalidate();

            }
        });
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            //progressDialog.dismiss();

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {


                            JSONObject json = response.getJSONObject();
                            try {
                                if (json != null) {

                                    String fName = "";
                                    if ((json.has("id")) && json.getString("id").trim() != null && !json.getString("id").trim().isEmpty() && !json.getString("id").trim().equals("null") && !json.getString("id").trim().matches("")) {
                                        fbUserId = json.getString("id");
                                    }
                                    if ((json.has("first_name")) && json.getString("first_name").trim() != null && !json.getString("first_name").trim().isEmpty() && !json.getString("first_name").trim().equals("null") && !json.getString("first_name").trim().matches("")) {
                                        if ((json.has("last_name")) && json.getString("last_name").trim() != null && !json.getString("last_name").trim().isEmpty() && !json.getString("last_name").trim().equals("null") && !json.getString("last_name").trim().matches("")) {
                                            fbName = json.getString("first_name") + " " + json.getString("last_name");
                                        }
                                        fName =  json.getString("first_name");
                                    } else {

                                        if ((json.has("last_name")) && json.getString("last_name").trim() != null && !json.getString("last_name").trim().isEmpty() && !json.getString("last_name").trim().equals("null") && !json.getString("last_name").trim().matches("")) {
                                            fbName = json.getString("last_name");
                                            fName =  json.getString("last_name");
                                        }else{
                                            if ((json.has("name")) && json.getString("name").trim() != null && !json.getString("name").trim().isEmpty() && !json.getString("name").trim().equals("null") && !json.getString("name").trim().matches("")) {
                                                fbName = json.getString("name");
                                                fName = json.getString("name").replace(" ","").trim();
                                            }
                                        }

                                    }

                                    if ((json.has("email")) && json.getString("email").trim() != null && !json.getString("email").trim().isEmpty() && !json.getString("email").trim().equals("null") && !json.getString("email").trim().matches("")) {
                                        fbEmail = json.getString("email");
                                    } else {
                                        if (fbName != null && !fbName.matches("")) {
                                            fbEmail = fName + "@facebook.com";
                                        } else {
                                            fbName = fbUserId;
                                            fbEmail = fbUserId + "@facebook.com";
                                        }
                                    }


                                    registerButton.setVisibility(View.GONE);
                                    loginWithFacebookButton.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.GONE);
                                    ((LoginActivity)context).handleFbUserDetails(fbUserId,fbEmail,fbName);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
              }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

            registerButton.setVisibility(View.VISIBLE);
            loginWithFacebookButton.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            Toast.makeText(context, languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT), Toast.LENGTH_LONG).show();
            //progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {

            registerButton.setVisibility(View.VISIBLE);
            loginWithFacebookButton.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            Toast.makeText(context, languagePreference.getTextofLanguage(DETAILS_NOT_FOUND_ALERT, DEFAULT_DETAILS_NOT_FOUND_ALERT), Toast.LENGTH_LONG).show();

            //progressDialog.dismiss();
        }
    };

    public void sendBroadCast()
    {
        Intent Sintent = new Intent("LOGIN_SUCCESS");
        context.sendBroadcast(Sintent);
    }

}
