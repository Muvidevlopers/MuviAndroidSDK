package com.home.vod.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.home.apisdk.apiController.AuthUserPaymentInfoAsyntask;
import com.home.apisdk.apiController.RegisterUserPaymentAsyntask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiModel.AuthUserPaymentInfoInputModel;
import com.home.apisdk.apiModel.AuthUserPaymentInfoOutputModel;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.RegisterUserPaymentInputModel;
import com.home.apisdk.apiModel.RegisterUserPaymentOutputModel;
import com.home.vod.R;
import com.home.vod.adapter.CardSpinnerAdapter;
import com.home.vod.model.CardModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.Player;
import player.activity.ThirdPartyPlayer;
import player.activity.YouTubeAPIActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.BUTTON_PAY_NOW;
import static com.home.vod.preferences.LanguagePreference.CARD_WILL_CHARGE;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_DETAILS;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_NUMBER_HINT;
import static com.home.vod.preferences.LanguagePreference.CVV_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_PAY_NOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CARD_WILL_CHARGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_DETAILS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_NUMBER_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CVV_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_SUBSCRIPTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_TRANSACTION_PROCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FAILURE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SUBSCRIPTION_COMPLETED;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_SUBSCRIPTION;
import static com.home.vod.preferences.LanguagePreference.ERROR_TRANSACTION_PROCESS;
import static com.home.vod.preferences.LanguagePreference.FAILURE;

import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.SUBSCRIPTION_COMPLETED;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;


public class PaymentInfoActivity extends ActionBarActivity implements VideoDetailsAsynctask.VideoDetailsListener,
        AuthUserPaymentInfoAsyntask.AuthUserPaymentInfoListener,
        RegisterUserPaymentAsyntask.RegisterUserPaymentListener {
    CardModel[] cardSavedArray;

    String filename = "";
    static File mediaStorageDir;
    ArrayList<String> SubTitlePath = new ArrayList<>();

    ProgressDialog pDialog;
    String existing_card_id = "";
    String isCheckedToSavetheCard = "1";

    String videoResolution = "BEST";
    LanguagePreference languagePreference;
    PreferenceManager preferenceManager;
    ProgressBarHandler progressBarHandler;
    Toolbar mActionBarToolbar;
    boolean isCouponCodeAdded = false;
    String validCouponCode;

    final String TAG = getClass().getName();
    private int MY_SCAN_REQUEST_CODE = 100; // arbitrary int

    Spinner cardExpiryYearSpinner;
    Spinner cardExpiryMonthSpinner;
    Spinner creditCardSaveSpinner;
    Spinner spinnerCardTextView;
    private RelativeLayout creditCardLayout;
    private RelativeLayout withoutCreditCardLayout;
    private LinearLayout cardExpiryDetailsLayout;
    private CheckBox saveCardCheckbox;
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();

    private TextView withoutCreditCardChargedPriceTextView;
    private Button nextButton;
    //private Button paywithCreditCardButton;
    //private Button paywithPaypalButton;

    private String movieVideoUrlStr = null;

    private EditText nameOnCardEditText;
    private EditText cardNumberEditText;
    private EditText securityCodeEditText;
    private Button scanButton;
    private Button payNowButton;
    private ImageButton payByPaypalButton;
    private RadioGroup paymentOptionsRadioGroup;
    private RadioButton payWithCreditCardRadioButton;
    private RadioButton payByPalRadioButton;
    Player playerModel;

    private LinearLayout paymentOptionLinearLayout;

    private TextView paymentOptionsTitle;


    private Button applyButton;
    private EditText couponCodeEditText;
    // private TextView entireShowPrice;

    private TextView selectShowRadioButton;

    private TextView chargedPriceTextView;

    String cardLastFourDigitStr;
    String tokenStr;
    String cardTypeStr;
    String responseText;
    String statusStr;

    String movieStreamUniqueIdStr;
    String muviUniqueIdStr;
    String planPriceStr;
    String videoUrlStr;
    String currencyCountryCodeStr;
    String currencyIdStr;
    String currencySymbolStr;
    String videoPreview;
    String videoName = "No Name";
    private int contentTypesId = 0;
    private String movieThirdPartyUrl = null;

    int isPPV = 0;
    int isAPV = 0;
    int isConverted = 0;

    String profileIdStr;
    int expiryMonthStr = 0;
    int planIdForPaypal = 0;

    String movieNameStr = "";
    String videoduration = "";
    String movieTypeStr = "";
    String censorRatingStr = "";
    String movieDetailsStr = "";

    int expiryYearStr = 0;

    ArrayAdapter<Integer> cardExpiryYearSpinnerAdapter;
    ArrayAdapter<Integer> cardExpiryMonthSpinnerAdapter;
    CardSpinnerAdapter creditCardSaveSpinnerAdapter;
    List<Integer> yearArray = new ArrayList<Integer>(21);
    List<Integer> monthsIdArray = new ArrayList<Integer>(12);
    /*Asynctask on background thread*/
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    TextView creditCardDetailsTitleTextView;
    FeatureHandler featureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);
        //Set toolbar
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(PaymentInfoActivity.this);
                onBackPressed();
            }
        });
        languagePreference = LanguagePreference.getLanguagePreference(PaymentInfoActivity.this);
        featureHandler = FeatureHandler.getFeaturePreference(PaymentInfoActivity.this);
        videoPreview = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);
        creditCardDetailsTitleTextView = (TextView) findViewById(R.id.creditCardDetailsTitleTextView);

        playerModel = new Player();
        playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));


        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP, FeatureHandler.DEFAULT_SIGNUP_STEP))) {
            // mActionBarToolbar.setNavigationIcon(null);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        } else {
            //mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        }

        if (getIntent().getStringExtra("muviuniqueid") != null) {
            muviUniqueIdStr = getIntent().getStringExtra("muviuniqueid");
        } else {
            muviUniqueIdStr = "";
        }



        /*mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(PaymentInfoActivity.this);
                onBackPressed();
            }
        });*/
        if (pDialog == null) {
            pDialog = new ProgressDialog(PaymentInfoActivity.this, R.style.CustomDialogTheme);
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
            pDialog.setIndeterminate(false);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_rawable));

        }

        preferenceManager = PreferenceManager.getPreferenceManager(this);

        if (getIntent().getStringExtra("currencyId") != null) {
            currencyIdStr = getIntent().getStringExtra("currencyId");
        } else {
            currencyIdStr = "";
        }

        if (getIntent().getStringExtra("currencyCountryCode") != null) {
            currencyCountryCodeStr = getIntent().getStringExtra("currencyCountryCode");
        } else {
            currencyCountryCodeStr = "";
        }

        if (getIntent().getStringExtra("currencySymbol") != null) {
            currencySymbolStr = getIntent().getStringExtra("currencySymbol");
        } else {
            currencySymbolStr = "";
        }


        nameOnCardEditText = (EditText) findViewById(R.id.nameOnCardEditText);
        cardNumberEditText = (EditText) findViewById(R.id.cardNumberEditText);
        securityCodeEditText = (EditText) findViewById(R.id.securityCodeEditText);
        couponCodeEditText = (EditText) findViewById(R.id.couponCodeEditText);

        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.light_fonts), nameOnCardEditText);
        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.light_fonts), cardNumberEditText);
        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.light_fonts), securityCodeEditText);
        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.light_fonts), couponCodeEditText);


        chargedPriceTextView = (TextView) findViewById(R.id.chargeDetailsTextView);
        creditCardLayout = (RelativeLayout) findViewById(R.id.creditCardLayout);
        cardExpiryDetailsLayout = (LinearLayout) findViewById(R.id.cardExpiryDetailsLayout);
        saveCardCheckbox = (CheckBox) findViewById(R.id.saveCardCheckbox);
        withoutCreditCardLayout = (RelativeLayout) findViewById(R.id.withoutPaymentLayout);
        withoutCreditCardLayout.setVisibility(View.GONE);
        withoutCreditCardChargedPriceTextView = (TextView) findViewById(R.id.withoutPaymentChargeDetailsTextView);
        nextButton = (Button) findViewById(R.id.nextButton);
        //paywithCreditCardButton = (Button) findViewById(R.id.payWithCreditCardButton);
        //paywithPaypalButton = (Button) findViewById(R.id.payWithPaypalCardButton);

        //payByPalRadioButton = (RadioButton) findViewById(R.id.payByPalRadioButton);


        cardExpiryMonthSpinner = (Spinner) findViewById(R.id.cardExpiryMonthEditText);
        cardExpiryYearSpinner = (Spinner) findViewById(R.id.cardExpiryYearEditText);
        creditCardSaveSpinner = (Spinner) findViewById(R.id.creditCardSaveEditText);

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setVisibility(View.GONE);
        payNowButton = (Button) findViewById(R.id.payNowButton);
        applyButton = (Button) findViewById(R.id.addCouponButton);

        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), creditCardDetailsTitleTextView);

        creditCardDetailsTitleTextView.setText(languagePreference.getTextofLanguage(CREDIT_CARD_DETAILS, DEFAULT_CREDIT_CARD_DETAILS));
        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.light_fonts), chargedPriceTextView);
        FontUtls.loadFont(PaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), payNowButton);
        payNowButton.setText(languagePreference.getTextofLanguage(BUTTON_PAY_NOW, DEFAULT_BUTTON_PAY_NOW));

       /* if (planIdForPaypal == 0){
            payByPaypalButton.setVisibility(View.GONE);
        }else{
            payByPaypalButton.setVisibility(View.VISIBLE);
        }

        payByPaypalButton.setVisibility(View.GONE);
        creditCardLayout.setVisibility(View.GONE);*/

        /*paywithCreditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        selectShowRadioButton = (TextView) findViewById(R.id.showNameWithPrice);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        for (int i = 0; i < 21; i++) {
            yearArray.add(year + i);

        }
        for (int i = 1; i < 13; i++) {
            monthsIdArray.add(i);


        }


        cardExpiryMonthSpinnerAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_new, monthsIdArray);
        cardExpiryMonthSpinner.setAdapter(cardExpiryMonthSpinnerAdapter);


        int mn = c.get(Calendar.MONTH);
        if (Util.containsIgnoreCase(monthsIdArray, mn + 1)) {
            // true
            int mnIndex = monthsIdArray.indexOf(mn + 1);

            cardExpiryMonthSpinner.setSelection(mnIndex);
            expiryMonthStr = monthsIdArray.get(mnIndex);
        } else {
            cardExpiryMonthSpinner.setSelection(0);
            expiryMonthStr = monthsIdArray.get(0);

        }

       /* cardExpiryMonthSpinner.setSelection(0);
        expiryMonthStr = monthsIdArray.get(0);*/

        cardExpiryYearSpinnerAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_new, yearArray);
        cardExpiryYearSpinner.setAdapter(cardExpiryYearSpinnerAdapter);
        cardExpiryYearSpinner.setSelection(0);
        expiryYearStr = yearArray.get(0);

      /*  creditCardSaveSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                creditCardSaveSpinner.setSelection(position);
                if(position == 0)
                {
                    creditCardLayout.setVisibility(View.VISIBLE);
                }else
                {
                    creditCardLayout.setVisibility(View.INVISIBLE);
                }

            }

        });
*/

        saveCardCheckbox.setChecked(true);

        creditCardSaveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                creditCardSaveSpinner.setSelection(position);
                if (position == 0) {

                    nameOnCardEditText.setVisibility(View.VISIBLE);
                    cardNumberEditText.setVisibility(View.VISIBLE);
                    cardNumberEditText.setVisibility(View.VISIBLE);
                    cardExpiryDetailsLayout.setVisibility(View.VISIBLE);
                    saveCardCheckbox.setVisibility(View.VISIBLE);
                    isCheckedToSavetheCard = "1";
                    saveCardCheckbox.setChecked(true);
                } else {
                    //withoutCreditCardLayout.setVisibility(View.GONE);
                    nameOnCardEditText.setVisibility(View.GONE);
                    cardNumberEditText.setVisibility(View.GONE);
                    cardNumberEditText.setVisibility(View.GONE);
                    cardExpiryDetailsLayout.setVisibility(View.GONE);
                    saveCardCheckbox.setVisibility(View.GONE);
                    isCheckedToSavetheCard = "0";
                    saveCardCheckbox.setChecked(false);


                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                creditCardSaveSpinner.setSelection(0);

                if (creditCardSaveSpinner.getSelectedItemPosition() == 0) {

                    nameOnCardEditText.setVisibility(View.VISIBLE);
                    cardNumberEditText.setVisibility(View.VISIBLE);
                    cardNumberEditText.setVisibility(View.VISIBLE);
                    cardExpiryDetailsLayout.setVisibility(View.VISIBLE);
                    saveCardCheckbox.setVisibility(View.VISIBLE);
                    isCheckedToSavetheCard = "1";
                    saveCardCheckbox.setChecked(true);

                    //withoutCreditCardLayout.setVisibility(View.VISIBLE);
                    //creditCardLayout.setVisibility(View.GONE);
                    //chargedPriceTextView.setText(Util.getTextofLanguage(PPvPaymentInfoActivity.this,Util.CARD_WILL_CHARGE,Util.DEFAULT_CARD_WILL_CHARGE)+" " +currencySymbolStr+chargedPrice);
                } else {
                    //withoutCreditCardLayout.setVisibility(View.GONE);
                    nameOnCardEditText.setVisibility(View.GONE);
                    cardNumberEditText.setVisibility(View.GONE);
                    cardNumberEditText.setVisibility(View.GONE);
                    cardExpiryDetailsLayout.setVisibility(View.GONE);
                    saveCardCheckbox.setVisibility(View.GONE);
                    isCheckedToSavetheCard = "0";
                    saveCardCheckbox.setChecked(false);
                }

            }
        });


        cardExpiryMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cardExpiryMonthSpinner.setSelection(position);
                expiryMonthStr = monthsIdArray.get(position);

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                int mn = c.get(Calendar.MONTH);
                if (Util.containsIgnoreCase(monthsIdArray, mn + 1)) {
                    // true
                    int mnIndex = monthsIdArray.indexOf(mn + 1);

                    cardExpiryMonthSpinner.setSelection(mnIndex);
                    expiryMonthStr = monthsIdArray.get(mnIndex);
                } else {
                    cardExpiryMonthSpinner.setSelection(0);
                    expiryMonthStr = monthsIdArray.get(0);

                }
            }
        });
        cardExpiryYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cardExpiryYearSpinner.setSelection(position);
                expiryYearStr = yearArray.get(position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                cardExpiryYearSpinner.setSelection(0);
                expiryYearStr = yearArray.get(0);

            }
        });

        chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " : " + currencySymbolStr + getIntent().getStringExtra("price"));
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOnCardEditText.setText("");
                securityCodeEditText.setText("");
                cardNumberEditText.setText("");


            }
        });


        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String nameOnCardStr = nameOnCardEditText.getText().toString().trim();
                String cardNumberStr = cardNumberEditText.getText().toString().trim();
                String securityCodeStr = securityCodeEditText.getText().toString().trim();

                if (nameOnCardStr.matches("")) {
                    Toast.makeText(PaymentInfoActivity.this, languagePreference.getTextofLanguage(CREDIT_CARD_NAME_HINT, DEFAULT_CREDIT_CARD_NAME_HINT), Toast.LENGTH_LONG).show();

                } else if (cardNumberStr.matches("")) {
                    Toast.makeText(PaymentInfoActivity.this, languagePreference.getTextofLanguage(CREDIT_CARD_NUMBER_HINT, DEFAULT_CREDIT_CARD_NUMBER_HINT), Toast.LENGTH_LONG).show();

                } else if (securityCodeStr.matches("")) {
                    Toast.makeText(PaymentInfoActivity.this, languagePreference.getTextofLanguage(CVV_ALERT, DEFAULT_CVV_ALERT), Toast.LENGTH_LONG).show();


                } else if (expiryMonthStr <= 0) {
//                    Toast.makeText(PaymentInfoActivity.this, "Please enter expiry month", Toast.LENGTH_LONG).show();

                } else if (expiryYearStr <= 0) {
//                    Toast.makeText(PaymentInfoActivity.this, "Please enter expiry year", Toast.LENGTH_LONG).show();

                } else {

                    if (!NetworkStatus.getInstance().isConnected(PaymentInfoActivity.this)) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
                        dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                        dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                        dlgAlert.setCancelable(false);
                        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        dlgAlert.create().show();

                    } else {
                        String emailIdStr = preferenceManager.getEmailIdFromPref();

                        nameOnCardStr = nameOnCardEditText.getText().toString().trim();
                        cardNumberStr = cardNumberEditText.getText().toString().trim();
                        securityCodeStr = securityCodeEditText.getText().toString().trim();
                        AuthUserPaymentInfoInputModel authUserPaymentInfoInputModel = new AuthUserPaymentInfoInputModel();
                        authUserPaymentInfoInputModel.setAuthToken(authTokenStr);
                        authUserPaymentInfoInputModel.setName_on_card(nameOnCardStr);
                        authUserPaymentInfoInputModel.setExpiryMonth(String.valueOf(expiryMonthStr).trim());
                        authUserPaymentInfoInputModel.setExpiryYear(String.valueOf(expiryYearStr).trim());
                        authUserPaymentInfoInputModel.setCardNumber(cardNumberStr);
                        authUserPaymentInfoInputModel.setCvv(securityCodeStr);
                        authUserPaymentInfoInputModel.setEmail(emailIdStr);

                        // ******************************* Added Later ***********************************//

                        authUserPaymentInfoInputModel.setPlan_id(getIntent().getStringExtra("selected_plan_id").toString().trim());

                        // ******************************* Added Later ***********************************//

                        AuthUserPaymentInfoAsyntask asyncReg = new AuthUserPaymentInfoAsyntask(authUserPaymentInfoInputModel, PaymentInfoActivity.this, PaymentInfoActivity.this);
                        asyncReg.executeOnExecutor(threadPoolExecutor);


                    }

                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkStatus.getInstance().isConnected(PaymentInfoActivity.this)) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    dlgAlert.create().show();

                } else {

                   /* AsynWithouPaymentSubscriptionRegDetails asyncSubWithoutPayment = new AsynWithouPaymentSubscriptionRegDetails();
                    asyncSubWithoutPayment.executeOnExecutor(threadPoolExecutor);*/
                }
            }

        });

    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onVideoDetailsPreExecuteStarted() {
        pDialog = new ProgressDialog(PaymentInfoActivity.this);
        pDialog.show();
    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int statusCode, String stus, String message) {
        // _video_details_output.setThirdparty_url("https://www.youtube.com/watch?v=fqU2FzATTPY&spfreload=10");
        // _video_details_output.setThirdparty_url("https://player.vimeo.com/video/192417650?color=00ff00&badge=0");

     /*check if status code 200 then set the video url before this it check it is thirdparty url or normal if third party
        then set thirdpartyurl true here and assign the url to videourl*/


        if (statusCode == 200) {
            playerModel.setIsOffline(_video_details_output.getIs_offline());
            playerModel.setDownloadStatus(_video_details_output.getDownload_status());
            if (_video_details_output.getThirdparty_url() == null || _video_details_output.getThirdparty_url().matches("")) {
                if (_video_details_output.getVideoUrl() != null || !_video_details_output.getVideoUrl().matches("")) {
                    playerModel.setVideoUrl(_video_details_output.getVideoUrl());
                    LogUtil.showLog("BISHAL", "videourl===" + playerModel.getVideoUrl());
                    playerModel.setThirdPartyPlayer(false);
                } else {
                    //  Util.dataModel.setVideoUrl(translatedLanuage.getNoData());
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

                }
            } else {
                if (_video_details_output.getThirdparty_url() != null || !_video_details_output.getThirdparty_url().matches("")) {
                    playerModel.setVideoUrl(_video_details_output.getThirdparty_url());
                    playerModel.setThirdPartyPlayer(true);

                } else {
                    //  Util.dataModel.setVideoUrl(translatedLanuage.getNoData());
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

                }
            }

            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());

            playerModel.setVideoResolution(_video_details_output.getVideoResolution());
            if (_video_details_output.getPlayed_length() != null && !_video_details_output.getPlayed_length().equals(""))
                playerModel.setPlayPos((Util.isDouble(_video_details_output.getPlayed_length())));


            //dependency for datamodel
            Util.dataModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            Util.dataModel.setChannel_id(_video_details_output.getChannel_id());
            Util.dataModel.setPreRoll(_video_details_output.getPreRoll());
            Util.dataModel.setPostRoll(_video_details_output.getPostRoll());
            Util.dataModel.setMidRoll(_video_details_output.getMidRoll());
            Util.dataModel.setVideoUrl(_video_details_output.getVideoUrl());
            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());
            Util.dataModel.setThirdPartyUrl(_video_details_output.getThirdparty_url());
            Util.dataModel.setAdDetails(_video_details_output.getAdDetails());


            //player model set
            playerModel.setAdDetails(_video_details_output.getAdDetails());
            playerModel.setMidRoll(_video_details_output.getMidRoll());
            playerModel.setPostRoll(_video_details_output.getPostRoll());
            playerModel.setChannel_id(_video_details_output.getChannel_id());
            playerModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            playerModel.setPreRoll(_video_details_output.getPreRoll());
            playerModel.setSubTitleName(_video_details_output.getSubTitleName());
            playerModel.setSubTitlePath(_video_details_output.getSubTitlePath());
            playerModel.setResolutionFormat(_video_details_output.getResolutionFormat());
            playerModel.setResolutionUrl(_video_details_output.getResolutionUrl());
            playerModel.setFakeSubTitlePath(_video_details_output.getFakeSubTitlePath());
            playerModel.setVideoResolution(_video_details_output.getVideoResolution());
            FakeSubTitlePath = _video_details_output.getFakeSubTitlePath();


            if (playerModel.getVideoUrl() == null ||
                    playerModel.getVideoUrl().matches("")) {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                }
                Util.showNoDataAlert(PaymentInfoActivity.this);
              /*  AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(Util.getTextofLanguage(PaymentInfoActivity.this, Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(Util.getTextofLanguage(PaymentInfoActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
                dlgAlert.setPositiveButton(Util.getTextofLanguage(PaymentInfoActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(Util.getTextofLanguage(PaymentInfoActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();*/
            } else {
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.hide();
                        pDialog = null;
                    }
                } catch (IllegalArgumentException ex) {
                    playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                }


                // condition for checking if the response has third party url or not.
                if (_video_details_output.getThirdparty_url() == null ||
                        _video_details_output.getThirdparty_url().matches("")
                        ) {


                    playerModel.setThirdPartyPlayer(false);
                    final Intent playVideoIntent;

                    if (Util.dataModel.getAdNetworkId() == 3) {
                        LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                        playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                    } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                        if (Util.dataModel.getPlayPos() <= 0) {
                            playVideoIntent = new Intent(PaymentInfoActivity.this, AdPlayerActivity.class);
                        } else {
                            playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                        }
                    } else {
                        playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                    }
                    /***ad **/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (FakeSubTitlePath.size() > 0) {
                                // This Portion Will Be changed Later.

                                File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
                                if (dir.isDirectory()) {
                                    String[] children = dir.list();
                                    for (int i = 0; i < children.length; i++) {
                                        new File(dir, children[i]).delete();
                                    }
                                }

                                progressBarHandler = new ProgressBarHandler(PaymentInfoActivity.this);
                                progressBarHandler.show();
                                Download_SubTitle(FakeSubTitlePath.get(0).trim());
                            } else {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                                playVideoIntent.putExtra("PlayerModel", playerModel);
                                startActivity(playVideoIntent);
                                finish();
                            }

                        }
                    });
                } else {
                    final Intent playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);
                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                /*playVideoIntent.putExtra("SubTitleName", SubTitleName);
                                playVideoIntent.putExtra("SubTitlePath", SubTitlePath);
                                playVideoIntent.putExtra("ResolutionFormat", ResolutionFormat);
                                playVideoIntent.putExtra("ResolutionUrl", ResolutionUrl);*/
                    playVideoIntent.putExtra("PlayerModel", playerModel);
                    startActivity(playVideoIntent);
                    finish();

                    //below part  checked at exoplayer thats why no need of checking here

                   /* playerModel.setThirdPartyPlayer(true);
                    if (playerModel.getVideoUrl().contains("://www.youtube") ||
                            playerModel.getVideoUrl().contains("://www.youtu.be")) {
                        if (playerModel.getVideoUrl().contains("live_stream?channel")) {
                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel",playerModel);
                                    startActivity(playVideoIntent);

                                }
                            });
                        } else {

                            final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, YouTubeAPIActivity.class);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    playVideoIntent.putExtra("PlayerModel",playerModel);
                                    startActivity(playVideoIntent);


                                }
                            });

                        }
                    } else {
                        final Intent playVideoIntent = new Intent(MovieDetailsActivity.this, ThirdPartyPlayer.class);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                playVideoIntent.putExtra("PlayerModel",playerModel);
                                startActivity(playVideoIntent);

                            }
                        });
                    }*/
                }
            }

        } else {

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                    pDialog = null;
                }
            } catch (IllegalArgumentException ex) {
                playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                // movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
            }
            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            //movieThirdPartyUrl = getResources().getString(R.string.no_data_str);
          /*  AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(Util.getTextofLanguage(PaymentInfoActivity.this, Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
            dlgAlert.setTitle(Util.getTextofLanguage(PaymentInfoActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
            dlgAlert.setPositiveButton(Util.getTextofLanguage(PaymentInfoActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(Util.getTextofLanguage(PaymentInfoActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();*/
            Util.showNoDataAlert(PaymentInfoActivity.this);
        }


    }

    @Override
    public void onAuthUserPaymentInfoPreExecuteStarted() {
        progressBarHandler = new ProgressBarHandler(PaymentInfoActivity.this);
        progressBarHandler.show();
    }

    @Override
    public void onAuthUserPaymentInfoPostExecuteCompleted(AuthUserPaymentInfoOutputModel authUserPaymentInfoOutputModel, int status, String responseMessageStr) {

        try {
            if (progressBarHandler.isShowing())
                progressBarHandler.hide();
        } catch (Exception ex) {
        }

        if (status == 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
            dlgAlert.setMessage(responseMessageStr);
            dlgAlert.setTitle("Failure");
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        } else if (status == 1) {

            if (!NetworkStatus.getInstance().isConnected(PaymentInfoActivity.this)) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();

            } else {
                RegisterUserPaymentInputModel registerUserPaymentInputModel = new RegisterUserPaymentInputModel();
                registerUserPaymentInputModel.setAuthToken(authTokenStr);
                registerUserPaymentInputModel.setCard_name(nameOnCardEditText.getText().toString().trim());
                registerUserPaymentInputModel.setExp_month(String.valueOf(expiryMonthStr).trim());
                registerUserPaymentInputModel.setCard_number(cardNumberEditText.getText().toString().trim());
                registerUserPaymentInputModel.setExp_year(String.valueOf(expiryYearStr).trim());
                String userIdStr = preferenceManager.getUseridFromPref();
                String emailIdSubStr = preferenceManager.getEmailIdFromPref();
                registerUserPaymentInputModel.setEmail(emailIdSubStr.trim());
                registerUserPaymentInputModel.setUser_id(userIdStr.trim());
                if (isCouponCodeAdded == true) {
                    registerUserPaymentInputModel.setCouponCode(validCouponCode);
                } else {
                    registerUserPaymentInputModel.setCouponCode("");
                }
                registerUserPaymentInputModel.setCard_type(authUserPaymentInfoOutputModel.getCard_type());
                registerUserPaymentInputModel.setCard_last_fourdigit(authUserPaymentInfoOutputModel.getCard_last_fourdigit());
                registerUserPaymentInputModel.setProfile_id(authUserPaymentInfoOutputModel.getProfile_id());
                registerUserPaymentInputModel.setToken(authUserPaymentInfoOutputModel.getToken());
                registerUserPaymentInputModel.setCvv(securityCodeEditText.getText().toString().trim());
                registerUserPaymentInputModel.setCountry(preferenceManager.getCountryCodeFromPref());
                registerUserPaymentInputModel.setEpisode_id("0");
                registerUserPaymentInputModel.setSeason_id("0");
                registerUserPaymentInputModel.setCurrency_id(currencyIdStr.trim());
                registerUserPaymentInputModel.setPlan_id(getIntent().getStringExtra("selected_plan_id").toString().trim());
                registerUserPaymentInputModel.setName(preferenceManager.getDispNameFromPref());


                // ************************ Added Later ***********************************************//

                registerUserPaymentInputModel.setTransaction_status(authUserPaymentInfoOutputModel.getTransaction_status());
                registerUserPaymentInputModel.setInvoice_id(authUserPaymentInfoOutputModel.getTransaction_invoice_id());
                registerUserPaymentInputModel.setOrder_number(authUserPaymentInfoOutputModel.getTransaction_order_number());
                registerUserPaymentInputModel.setDollar_amount(authUserPaymentInfoOutputModel.getTransaction_dollar_amount());
                registerUserPaymentInputModel.setAmount(authUserPaymentInfoOutputModel.getTransaction_amount());
                registerUserPaymentInputModel.setResponse_text(authUserPaymentInfoOutputModel.getTransaction_response_text());
                registerUserPaymentInputModel.setIsSuccess(authUserPaymentInfoOutputModel.getIsSuccess());
                registerUserPaymentInputModel.setTransaction_is_success(authUserPaymentInfoOutputModel.getTransaction_is_success());

                // ************************ Added Later ***********************************************//


                RegisterUserPaymentAsyntask asyncSubsrInfo = new RegisterUserPaymentAsyntask(registerUserPaymentInputModel, this, this);
                asyncSubsrInfo.executeOnExecutor(threadPoolExecutor);
            }
        }
    }



    @Override
    public void onRegisterUserPaymentPreExecuteStarted() {
        progressBarHandler = new ProgressBarHandler(PaymentInfoActivity.this);
        progressBarHandler.show();
    }

    @Override
    public void onRegisterUserPaymentPostExecuteCompleted(RegisterUserPaymentOutputModel registerUserPaymentOutputModel, int status) {

        try {
            if (progressBarHandler.isShowing())
                progressBarHandler.hide();
        } catch (IllegalArgumentException ex) {

        }
        if (status == 0) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_TRANSACTION_PROCESS, DEFAULT_ERROR_TRANSACTION_PROCESS));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, DEFAULT_FAILURE));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            dlgAlert.create().show();
        } else if (status > 0) {

            if (status == 200) {
                Toast.makeText(PaymentInfoActivity.this, languagePreference.getTextofLanguage(SUBSCRIPTION_COMPLETED, DEFAULT_SUBSCRIPTION_COMPLETED), Toast.LENGTH_LONG).show();
                if (Util.check_for_subscription == 0) {
                    Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } else {
                    if (NetworkStatus.getInstance().isConnected(this)) {

                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
                        getVideoDetailsInput.setAuthToken(authTokenStr);
                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
                        getVideoDetailsInput.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                        VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, PaymentInfoActivity.this, PaymentInfoActivity.this);
                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);

                    } else {
                        Intent intent = new Intent(PaymentInfoActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                        Toast.makeText(PaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }
                }

            } else {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PaymentInfoActivity.this);
                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_TRANSACTION_PROCESS, DEFAULT_ERROR_TRANSACTION_PROCESS));
                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
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
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL().execute(Url);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {
            int count;


            try {
                URL url = new URL(f_url[0]);
                String str = f_url[0];
                filename = str.substring(str.lastIndexOf("/") + 1);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File root = Environment.getExternalStorageDirectory();
                mediaStorageDir = new File(root + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/", "");

                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                }

                SubTitlePath.add(mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".vtt");
                OutputStream output = new FileOutputStream(mediaStorageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".vtt");

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String file_url) {
            FakeSubTitlePath.remove(0);
            if (FakeSubTitlePath.size() > 0) {
                Download_SubTitle(FakeSubTitlePath.get(0).trim());
            } else {
                if (progressBarHandler != null && progressBarHandler.isShowing()) {
                    progressBarHandler.hide();
                }
                final Intent playVideoIntent;
                if (Util.dataModel.getAdNetworkId() == 3) {
                    LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                    playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                    if (Util.dataModel.getPlayPos() <= 0) {
                        playVideoIntent = new Intent(PaymentInfoActivity.this, AdPlayerActivity.class);
                    } else {
                        playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                    }
                } else {
                    playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);

                }
                playerModel.setSubTitlePath(SubTitlePath);
                //Intent playVideoIntent = new Intent(PaymentInfoActivity.this, ExoPlayerActivity.class);
                playVideoIntent.putExtra("PlayerModel", playerModel);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(playVideoIntent);
                finish();
            }
        }
    }


}
