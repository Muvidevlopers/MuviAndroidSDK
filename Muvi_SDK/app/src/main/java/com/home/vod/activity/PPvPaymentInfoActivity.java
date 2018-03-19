package com.home.vod.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.ResultCallback;
import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.apiController.AuthUserPaymentInfoAsyntask;
import com.home.apisdk.apiController.GetCardListForPPVAsynTask;
import com.home.apisdk.apiController.GetMonetizationDetailsAsynctask;
import com.home.apisdk.apiController.GetPPVPaymentAsync;
import com.home.apisdk.apiController.GetVoucherPlanAsynTask;
import com.home.apisdk.apiController.ValidateCouponCodeAsynTask;
import com.home.apisdk.apiController.ValidateVoucherAsynTask;
import com.home.apisdk.apiController.VideoDetailsAsynctask;
import com.home.apisdk.apiController.VoucherSubscriptionAsyntask;
import com.home.apisdk.apiController.WithouPaymentSubscriptionRegDetailsAsync;
import com.home.apisdk.apiModel.AuthUserPaymentInfoInputModel;
import com.home.apisdk.apiModel.AuthUserPaymentInfoOutputModel;
import com.home.apisdk.apiModel.GetCardListForPPVInputModel;
import com.home.apisdk.apiModel.GetCardListForPPVOutputModel;
import com.home.apisdk.apiModel.GetMonetizationDetailsInputModel;
import com.home.apisdk.apiModel.GetMonetizationDetailsOutputModel;
import com.home.apisdk.apiModel.GetVideoDetailsInput;
import com.home.apisdk.apiModel.GetVoucherPlanInputModel;
import com.home.apisdk.apiModel.GetVoucherPlanOutputModel;
import com.home.apisdk.apiModel.ValidateVoucherInputModel;
import com.home.apisdk.apiModel.ValidateVoucherOutputModel;
import com.home.apisdk.apiModel.Video_Details_Output;
import com.home.apisdk.apiModel.RegisterUserPaymentInputModel;
import com.home.apisdk.apiModel.RegisterUserPaymentOutputModel;
import com.home.apisdk.apiModel.ValidateCouponCodeInputModel;
import com.home.apisdk.apiModel.ValidateCouponCodeOutputModel;
import com.home.apisdk.apiModel.VoucherSubscriptionInputModel;
import com.home.apisdk.apiModel.VoucherSubscriptionOutputModel;
import com.home.apisdk.apiModel.WithouPaymentSubscriptionRegDetailsInput;
import com.home.vod.R;
import com.home.vod.adapter.CardSpinnerAdapter;
import com.home.vod.expandedcontrols.ExpandedControlsActivity;
import com.home.vod.model.CardModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import player.activity.AdPlayerActivity;
import player.activity.ExoPlayerActivity;
import player.activity.Player;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import static com.home.apisdk.apiModel.CommonConstants.VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.BTN_NEXT;
import static com.home.vod.preferences.LanguagePreference.BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.BUTTON_PAY_NOW;
import static com.home.vod.preferences.LanguagePreference.CARD_WILL_CHARGE;
import static com.home.vod.preferences.LanguagePreference.CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.COUPON_CANCELLED;
import static com.home.vod.preferences.LanguagePreference.COUPON_CODE_HINT;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_CVV_HINT;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_DETAILS;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.CREDIT_CARD_NUMBER_HINT;
import static com.home.vod.preferences.LanguagePreference.CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.CVV_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ACTIVATE_SUBSCRIPTION_WATCH_VIDEO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BTN_NEXT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_APPLY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_PAY_NOW;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CARD_WILL_CHARGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CONTENT_NOT_AVAILABLE_IN_YOUR_COUNTRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_COUPON_CANCELLED;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_COUPON_CODE_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_CVV_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_DETAILS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_NAME_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CREDIT_CARD_NUMBER_HINT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CROSSED_MAXIMUM_LIMIT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_CVV_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DISCOUNT_ON_COUPON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_PAYMENT_VALIDATION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_IN_SUBSCRIPTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ERROR_TRANSACTION_PROCESS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FAILURE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_FREE_FOR_COUPON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_INVALID_COUPON;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PAYMENT_OPTIONS_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE_SUCCESS_ALERT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SAVE_THIS_CARD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SORRY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_USE_NEW_CARD;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VOUCHER_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_WATCH_NOW;
import static com.home.vod.preferences.LanguagePreference.DETAILS_NOT_FOUND_ALERT;
import static com.home.vod.preferences.LanguagePreference.DISCOUNT_ON_COUPON;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_PAYMENT_VALIDATION;
import static com.home.vod.preferences.LanguagePreference.ERROR_IN_SUBSCRIPTION;
import static com.home.vod.preferences.LanguagePreference.ERROR_TRANSACTION_PROCESS;
import static com.home.vod.preferences.LanguagePreference.FAILURE;
import static com.home.vod.preferences.LanguagePreference.FREE_FOR_COUPON;
import static com.home.vod.preferences.LanguagePreference.INVALID_COUPON;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.PAYMENT_OPTIONS_TITLE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_SUCCESS_ALERT;
import static com.home.vod.preferences.LanguagePreference.SAVE_THIS_CARD;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SORRY;
import static com.home.vod.preferences.LanguagePreference.USE_NEW_CARD;
import static com.home.vod.preferences.LanguagePreference.VOUCHER_BLANK_MESSAGE;
import static com.home.vod.preferences.LanguagePreference.WATCH_NOW;
import static com.home.vod.util.Constant.authTokenStr;

public class PPvPaymentInfoActivity extends ActionBarActivity implements
        VideoDetailsAsynctask.VideoDetailsListener,
        ValidateCouponCodeAsynTask.ValidateCouponCodeLIstener,
        AuthUserPaymentInfoAsyntask.AuthUserPaymentInfoListener,
        WithouPaymentSubscriptionRegDetailsAsync.WithouPaymentSubscriptionRegDetailsListener,
        GetPPVPaymentAsync.GetPPVPaymentListener,
        GetCardListForPPVAsynTask.GetCardListForPPVListener,
        GetMonetizationDetailsAsynctask.GetMonetizationDetailsListener,
        GetVoucherPlanAsynTask.GetVoucherPlanListener,
        ValidateVoucherAsynTask.ValidateVoucherListener,
        VoucherSubscriptionAsyntask.VoucherSubscriptionListener {


    String filename = "";
    static File mediaStorageDir;
    String VoucherCode = "";
    CardModel[] cardSavedArray;
    boolean watch_status = false;
    String loggedInIdStr;
   // ProgressDialog pDialog;
    String existing_card_id = "";
    String isCheckedToSavetheCard = "1";
    private boolean isCastConnected = false;

    /*chromecast-------------------------------------*/
    View view;


    public enum PlaybackLocation {
        LOCAL,
        REMOTE
    }

    /**
     * List of various states that we can be in
     */
    public enum PlaybackState {
        PLAYING, PAUSED, BUFFERING, IDLE
    }

    private MovieDetailsActivity.PlaybackLocation mLocation;
    private MovieDetailsActivity.PlaybackState mPlaybackState;
    private final float mAspectRatio = 72f / 128;
    private AQuery mAquery;
    private MediaInfo mSelectedMedia;


    private CastContext mCastContext;
    private SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private CastSession mCastSession;

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {

        @Override
        public void onSessionEnded(CastSession session, int error) {
            if (session == mCastSession) {
                mCastSession = null;
            }
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mCastSession = session;
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mCastSession = session;
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStarting(CastSession session) {
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionEnding(CastSession session) {
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
        }
    }


    MediaInfo mediaInfo;

    /*chromecast-------------------------------------*/

    PreferenceManager preferenceManager;

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

    private TextView withoutCreditCardChargedPriceTextView;
    private Button nextButton;
    Player playerModel;
    ArrayList<String> FakeSubTitlePath = new ArrayList<>();
    ArrayList<String> SubTitlePath = new ArrayList<>();
    //private Button paywithCreditCardButton;
    //private Button paywithPaypalButton;

    private EditText nameOnCardEditText;
    private EditText cardNumberEditText;
    private EditText securityCodeEditText;
    private Button scanButton;
    private Button payNowButton;
    private RadioButton voucherRadioButton;
    private LinearLayout voucherLinearLayout;

    private Button applyButton;
    private EditText couponCodeEditText;

    private TextView selectShowRadioButton;
    private TextView withoutPaymentTitleTextView;
    private RadioGroup paymentOptionsRadioGroup;
    private RadioButton payWithCreditCardRadioButton;
    private LinearLayout paymentOptionLinearLayout;
    private TextView paymentOptionsTitle;
    Button apply, watch_now;
    EditText voucher_code;
    TextView voucher_success;

    private TextView chargedPriceTextView;

    String cardLastFourDigitStr;
    String tokenStr = "";
    String cardTypeStr = "";
    String responseText;
    String statusStr;
    //ProgressDialog pDialog1;

    String movieStreamUniqueIdStr;
    String muviUniqueIdStr;
    String planPriceStr;
    String videoUrlStr;
    String currencyCountryCodeStr;
    String currencyIdStr;
    String currencySymbolStr;
    String videoPreview;
    String videoName = "No Name";
    int isPPV = 0;
    int isAPV = 0;
    int isConverted = 0;
    int contentTypesId = 0;
    int selectedPurchaseType = 0;

    String profileIdStr;
    int expiryMonthStr = 0;
    int planIdForPaypal = 0;
    ProgressBarHandler videoPDialog;

    int expiryYearStr = 0;
    float planPrice = 0.0f;
    float chargedPrice = 0.0f;
    float previousChargedPrice = 0.0f;
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

    TextView purchaseTextView, creditCardDetailsTitleTextView;
    LanguagePreference languagePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppv_payment_info);
        //  playerModel=new Player();
        playerModel = (Player) getIntent().getSerializableExtra("PlayerModel");
        languagePreference = LanguagePreference.getLanguagePreference(this);
        preferenceManager = PreferenceManager.getPreferenceManager(this);
        playerModel.setIsstreaming_restricted(Util.getStreamingRestriction(languagePreference));
        LogUtil.showLog("BKS", "ppvpayment Activity Season Id =" + getIntent().getSerializableExtra("PlayerModel"));
        LogUtil.showLog("MUVI", "ppvpatment Activity episode Id =" + Util.selected_episode_id);

        videoPreview = languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA);


        //Set toolbar
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(PPvPaymentInfoActivity.this);
                onBackPressed();
            }
        });

      /*  if (pDialog == null) {
            pDialog = new ProgressDialog(PPvPaymentInfoActivity.this, R.style.CustomDialogTheme);
            pDialog.setCancelable(false);
            pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
            pDialog.setIndeterminate(false);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_rawable));

        }*/


        purchaseTextView = (TextView) findViewById(R.id.purchaseTextView);


        if (getIntent().getStringExtra("muviuniqueid") != null) {
            muviUniqueIdStr = getIntent().getStringExtra("muviuniqueid");
        } else {
            muviUniqueIdStr = "";
        }


        if (getIntent().getStringExtra("episodeStreamId") != null) {
            movieStreamUniqueIdStr = getIntent().getStringExtra("episodeStreamId");
        } else {
            movieStreamUniqueIdStr = "";
        }

        if (getIntent().getStringExtra("videoPreview") != null) {
            videoPreview = getIntent().getStringExtra("videoPreview");
        }

        if (getIntent().getStringExtra("showName") != null) {
            videoName = getIntent().getStringExtra("showName");
        }
        if (getIntent().getStringExtra("planIdForPaypal") != null) {
            planIdForPaypal = Integer.parseInt(getIntent().getStringExtra("planIdForPaypal"));
        }
        if (getIntent().getIntExtra("isPPV", 0) != 0) {
            isPPV = getIntent().getIntExtra("isPPV", 0);
        }
        if (getIntent().getIntExtra("isAPV", 0) != 0) {
            isAPV = getIntent().getIntExtra("isPPV", 0);
        }
        if (getIntent().getIntExtra("isConverted", 0) != 0) {
            isConverted = getIntent().getIntExtra("isPPV", 0);
        }
        if (getIntent().getStringExtra("selectedPurchaseType") != null) {
            selectedPurchaseType = Integer.parseInt(getIntent().getStringExtra("selectedPurchaseType"));
        }
        if (getIntent().getStringExtra("contentTypesId") != null) {
            contentTypesId = Integer.parseInt(getIntent().getStringExtra("contentTypesId"));
        }

        if (preferenceManager.getIsSubscribedFromPref() != null) {
            String isSubscribedStr = "0";
            isSubscribedStr = preferenceManager.getIsSubscribedFromPref();
            if (isSubscribedStr.equalsIgnoreCase("1")) {
                if (getIntent().getStringExtra("planSubscribedPrice") != null) {
                    chargedPrice = Float.parseFloat(getIntent().getStringExtra("planSubscribedPrice"));
                    previousChargedPrice = Float.parseFloat(getIntent().getStringExtra("planSubscribedPrice"));
                    planPrice = Float.parseFloat(getIntent().getStringExtra("planSubscribedPrice"));
                } else {
                    chargedPrice = 0.0f;
                    previousChargedPrice = 0.0f;
                    planPrice = 0.0f;
                }
            } else {
                if (getIntent().getStringExtra("planUnSubscribedPrice") != null) {
                    chargedPrice = Float.parseFloat(getIntent().getStringExtra("planUnSubscribedPrice"));
                    previousChargedPrice = Float.parseFloat(getIntent().getStringExtra("planUnSubscribedPrice"));
                    planPrice = Float.parseFloat(getIntent().getStringExtra("planUnSubscribedPrice"));

                } else {
                    chargedPrice = 0.0f;
                    previousChargedPrice = 0.0f;
                    planPrice = 0.0f;

                }
            }


        } else {
            chargedPrice = 0.0f;
            previousChargedPrice = 0.0f;
            planPrice = 0.0f;

        }


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
        couponCodeEditText.addTextChangedListener(filterTextWatcher);
        voucherRadioButton = (RadioButton) findViewById(R.id.voucherRadioButton);


        chargedPriceTextView = (TextView) findViewById(R.id.chargeDetailsTextView);
        creditCardLayout = (RelativeLayout) findViewById(R.id.creditCardLayout);
        cardExpiryDetailsLayout = (LinearLayout) findViewById(R.id.cardExpiryDetailsLayout);
        saveCardCheckbox = (CheckBox) findViewById(R.id.saveCardCheckbox);
        withoutCreditCardLayout = (RelativeLayout) findViewById(R.id.withoutPaymentLayout);
        withoutCreditCardLayout.setVisibility(View.GONE);
        withoutCreditCardChargedPriceTextView = (TextView) findViewById(R.id.withoutPaymentChargeDetailsTextView);
        withoutPaymentTitleTextView = (TextView) findViewById(R.id.withoutPaymentTitleTextView);
        nextButton = (Button) findViewById(R.id.nextButton);
        //paywithCreditCardButton = (Button) findViewById(R.id.payWithCreditCardButton);
        //paywithPaypalButton = (Button) findViewById(R.id.payWithPaypalCardButton);
        voucherLinearLayout = (LinearLayout) findViewById(R.id.voucherLinearLayout);
        paymentOptionsRadioGroup = (RadioGroup) findViewById(R.id.paymentOptionsRadioGroup);
        payWithCreditCardRadioButton = (RadioButton) findViewById(R.id.payWithCreditCardRadioButton);
        paymentOptionsTitle = (TextView) findViewById(R.id.paymentOptionsTitle);
        paymentOptionLinearLayout = (LinearLayout) findViewById(R.id.paymentOptionLinearLayout);

        cardExpiryMonthSpinner = (Spinner) findViewById(R.id.cardExpiryMonthEditText);
        cardExpiryYearSpinner = (Spinner) findViewById(R.id.cardExpiryYearEditText);
        creditCardSaveSpinner = (Spinner) findViewById(R.id.creditCardSaveEditText);


        apply = (Button) findViewById(R.id.apply);
        watch_now = (Button) findViewById(R.id.watch_now);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setVisibility(View.GONE);
        payNowButton = (Button) findViewById(R.id.payNowButton);
        applyButton = (Button) findViewById(R.id.addCouponButton);
        selectShowRadioButton = (TextView) findViewById(R.id.showNameWithPrice);
        creditCardDetailsTitleTextView = (TextView) findViewById(R.id.creditCardDetailsTitleTextView);
        voucher_success = (TextView) findViewById(R.id.voucher_success);
        nameOnCardEditText = (EditText) findViewById(R.id.nameOnCardEditText);
        cardNumberEditText = (EditText) findViewById(R.id.cardNumberEditText);
        securityCodeEditText = (EditText) findViewById(R.id.securityCodeEditText);
        couponCodeEditText = (EditText) findViewById(R.id.couponCodeEditText);
        voucher_code = (EditText) findViewById(R.id.voucher_code);
        couponCodeEditText.addTextChangedListener(filterTextWatcher);

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.light_fonts), nameOnCardEditText);
        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.light_fonts), cardNumberEditText);
        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.light_fonts), securityCodeEditText);
        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.light_fonts), couponCodeEditText);

        nameOnCardEditText.setHint(languagePreference.getTextofLanguage(CREDIT_CARD_NAME_HINT, DEFAULT_CREDIT_CARD_NAME_HINT));
        cardNumberEditText.setHint(languagePreference.getTextofLanguage(CREDIT_CARD_NUMBER_HINT, DEFAULT_CREDIT_CARD_NUMBER_HINT));
        securityCodeEditText.setHint(languagePreference.getTextofLanguage(CREDIT_CARD_CVV_HINT, DEFAULT_CREDIT_CARD_CVV_HINT));
        couponCodeEditText.setHint(languagePreference.getTextofLanguage(COUPON_CODE_HINT, DEFAULT_COUPON_CODE_HINT));
        voucher_code.setHint(languagePreference.getTextofLanguage(VOUCHER_CODE, DEFAULT_VOUCHER_CODE));


        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), payWithCreditCardRadioButton);
        payWithCreditCardRadioButton.setText(languagePreference.getTextofLanguage(CREDIT_CARD_DETAILS, DEFAULT_CREDIT_CARD_DETAILS));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), voucherRadioButton);
        voucherRadioButton.setText(languagePreference.getTextofLanguage(VOUCHER_CODE, DEFAULT_VOUCHER_CODE));


        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), creditCardDetailsTitleTextView);
        creditCardDetailsTitleTextView.setText(languagePreference.getTextofLanguage(CREDIT_CARD_DETAILS, DEFAULT_CREDIT_CARD_DETAILS));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), paymentOptionsTitle);
        paymentOptionsTitle.setText(languagePreference.getTextofLanguage(PAYMENT_OPTIONS_TITLE, DEFAULT_PAYMENT_OPTIONS_TITLE));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), watch_now);
        watch_now.setText(languagePreference.getTextofLanguage(WATCH_NOW, DEFAULT_WATCH_NOW));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), apply);
        apply.setText(languagePreference.getTextofLanguage(BUTTON_APPLY, DEFAULT_BUTTON_APPLY));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), applyButton);
        applyButton.setText(languagePreference.getTextofLanguage(BUTTON_APPLY, DEFAULT_BUTTON_APPLY));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), purchaseTextView);
        purchaseTextView.setText(languagePreference.getTextofLanguage(PURCHASE, DEFAULT_PURCHASE));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), creditCardDetailsTitleTextView);
        creditCardDetailsTitleTextView.setText(languagePreference.getTextofLanguage(CREDIT_CARD_DETAILS, DEFAULT_CREDIT_CARD_DETAILS));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), chargedPriceTextView);
        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), selectShowRadioButton);

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), payNowButton);
        payNowButton.setText(languagePreference.getTextofLanguage(BUTTON_PAY_NOW, DEFAULT_BUTTON_PAY_NOW));

        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), saveCardCheckbox);

        nextButton.setText(languagePreference.getTextofLanguage(BTN_NEXT, DEFAULT_BTN_NEXT));
        FontUtls.loadFont(PPvPaymentInfoActivity.this, getResources().getString(R.string.regular_fonts), nextButton);
        saveCardCheckbox.setText(languagePreference.getTextofLanguage("  " + SAVE_THIS_CARD, "  " + DEFAULT_SAVE_THIS_CARD));

        GetMonetizationDetailsInputModel getMonetizationDetailsInputModel = new GetMonetizationDetailsInputModel();
        loggedInIdStr = preferenceManager.getUseridFromPref();
        getMonetizationDetailsInputModel.setAuthToken(authTokenStr);
        getMonetizationDetailsInputModel.setStream_id(Util.dataModel.getStreamUniqueId());
        getMonetizationDetailsInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        getMonetizationDetailsInputModel.setUser_id(loggedInIdStr);
        if (Util.dataModel.getContentTypesId() == 3) {
            getMonetizationDetailsInputModel.setPurchase_type("episode");
        } else {
            getMonetizationDetailsInputModel.setPurchase_type("show");
        }

        final GetMonetizationDetailsAsynctask asynGetMoniTization = new GetMonetizationDetailsAsynctask(getMonetizationDetailsInputModel, this, this);
        asynGetMoniTization.executeOnExecutor(threadPoolExecutor);

        voucherRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditCardLayout.setVisibility(View.GONE);
                paymentOptionLinearLayout.setVisibility(View.VISIBLE);
                voucherLinearLayout.setVisibility(View.VISIBLE);
            }

        });

        payWithCreditCardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditCardLayout.setVisibility(View.VISIBLE);
                paymentOptionLinearLayout.setVisibility(View.VISIBLE);
                voucherLinearLayout.setVisibility(View.GONE);
            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VoucherCode = voucher_code.getText().toString().trim();
                if (!VoucherCode.equals("")) {

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    ValidateVoucher_And_VoucherSubscription();

                } else {
                    Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(VOUCHER_BLANK_MESSAGE, DEFAULT_VOUCHER_BLANK_MESSAGE), Toast.LENGTH_SHORT).show();

                }
            }
        });

        watch_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (watch_status) {

                    // Calling Voucher Subscription Api
                    VoucherSubscriptionInputModel voucherSubscriptionInputModel = new VoucherSubscriptionInputModel();
                    voucherSubscriptionInputModel.setAuthToken(authTokenStr);
                    voucherSubscriptionInputModel.setUser_id(preferenceManager.getUseridFromPref());
                    voucherSubscriptionInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
                    voucherSubscriptionInputModel.setStream_id(Util.dataModel.getStreamUniqueId().trim());
                    voucherSubscriptionInputModel.setVoucher_code(VoucherCode);
                    voucherSubscriptionInputModel.setIs_preorder("" + Util.dataModel.getIsAPV());
                    voucherSubscriptionInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                    if (Util.dataModel.getContentTypesId() == 3) {
                        voucherSubscriptionInputModel.setSeason(Util.dataModel.getSeason_id());
                        if (selectedPurchaseType == 1)
                            voucherSubscriptionInputModel.setPurchase_type("show");
                        if (selectedPurchaseType == 2)
                            voucherSubscriptionInputModel.setPurchase_type("season");
                        if (selectedPurchaseType == 3)
                            voucherSubscriptionInputModel.setPurchase_type("episode");
                    } else
                        voucherSubscriptionInputModel.setPurchase_type("show");

                    VoucherSubscriptionAsyntask asynVoucherSubscription = new VoucherSubscriptionAsyntask(voucherSubscriptionInputModel, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
                    asynVoucherSubscription.executeOnExecutor(threadPoolExecutor);


                }
            }
        });

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

        creditCardSaveSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
                    //chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE,Util.DEFAULT_CARD_WILL_CHARGE)+" " +currencySymbolStr+chargedPrice);
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


        cardExpiryMonthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
        cardExpiryYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {


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

        String[] strValues = String.valueOf(planPrice).split("\\.");
        String[] strValues_charged = String.valueOf(chargedPrice).split("\\.");


        if (strValues[1].length() == 1) {

            selectShowRadioButton.setText(videoName + " : " + currencySymbolStr + planPrice + "0");

        }
        else {
            selectShowRadioButton.setText(videoName + " : " + currencySymbolStr + planPrice);

        }

        if (strValues_charged[1].length() == 1) {

            chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + chargedPrice + "0");

        }
        else {
            chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + chargedPrice);

        }

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOnCardEditText.setText("");
                securityCodeEditText.setText("");
                cardNumberEditText.setText("");

               /* cardExpiryMonthSpinner.setSelection(0);
                expiryMonthStr = monthsIdArray.get(0);
                cardExpiryYearSpinner.setSelection(0);
                expiryYearStr = yearArray.get(0);*/

                onScanPress(v);
            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(couponCodeEditText.getWindowToken(), 0);
                String couponCodeStr = couponCodeEditText.getText().toString().trim();

                if (couponCodeStr.matches("")) {
                    Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(COUPON_CODE_HINT, DEFAULT_COUPON_CODE_HINT), Toast.LENGTH_LONG).show();

                } else {

                    if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
                        ValidateCouponCodeInputModel validateCouponCodeInputModel = new ValidateCouponCodeInputModel();
                        validateCouponCodeInputModel.setAuthToken(authTokenStr);
                        validateCouponCodeInputModel.setCouponCode(couponCodeStr);
                        validateCouponCodeInputModel.setUser_id(preferenceManager.getUseridFromPref());
                        validateCouponCodeInputModel.setCurrencyId(currencyIdStr.trim());
                        ValidateCouponCodeAsynTask asyncReg = new ValidateCouponCodeAsynTask(validateCouponCodeInputModel, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
                        asyncReg.executeOnExecutor(threadPoolExecutor);
                    }

                }
            }
        });

       /* payByPaypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynPayByPaypalDetails asynPayByPaypalDetails = new AsynPayByPaypalDetails();
                asynPayByPaypalDetails.executeOnExecutor(threadPoolExecutor);

            }
        });*/
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                if (creditCardSaveSpinner != null && cardSavedArray != null && cardSavedArray.length > 0 && creditCardSaveSpinner.getSelectedItemPosition() > 0) {


                    if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                        Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    } else {
                        if (creditCardSaveSpinner != null && cardSavedArray != null && cardSavedArray.length > 0 && creditCardSaveSpinner.getSelectedItemPosition() > 0) {
                            existing_card_id = cardSavedArray[creditCardSaveSpinner.getSelectedItemPosition()].getCardId();

                        } else {
                            existing_card_id = "";
                        }
                        WithouPaymentSubscriptionRegDetailsInput withouPaymentSubscriptionRegDetailsInput = new WithouPaymentSubscriptionRegDetailsInput();
                        String userIdStr = preferenceManager.getUseridFromPref();
                        String emailIdSubStr = preferenceManager.getEmailIdFromPref();
                        withouPaymentSubscriptionRegDetailsInput.setAuthToken(authTokenStr);
                        if (isAPV == 1) {
                            withouPaymentSubscriptionRegDetailsInput.setIs_advance("1");
                        }
                        withouPaymentSubscriptionRegDetailsInput.setCard_name("");
                        withouPaymentSubscriptionRegDetailsInput.setExp_month("");
                        withouPaymentSubscriptionRegDetailsInput.setCard_number("");
                        withouPaymentSubscriptionRegDetailsInput.setExp_year("");
                        withouPaymentSubscriptionRegDetailsInput.setEmail(emailIdSubStr.trim());
                        withouPaymentSubscriptionRegDetailsInput.setMovie_id(muviUniqueIdStr.trim());
                        withouPaymentSubscriptionRegDetailsInput.setUser_id(userIdStr.trim());
                        if (isCouponCodeAdded == true) {
                            withouPaymentSubscriptionRegDetailsInput.setCoupon_code(validCouponCode);
                        } else {
                            withouPaymentSubscriptionRegDetailsInput.setCoupon_code("");
                        }
                        withouPaymentSubscriptionRegDetailsInput.setCard_last_fourdigit("");
                        withouPaymentSubscriptionRegDetailsInput.setCard_type("");
                        withouPaymentSubscriptionRegDetailsInput.setProfile_id("");
                        withouPaymentSubscriptionRegDetailsInput.setToken("");
                        withouPaymentSubscriptionRegDetailsInput.setCvv("");
                        withouPaymentSubscriptionRegDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                        withouPaymentSubscriptionRegDetailsInput.setSeason_id(Util.selected_season_id);
                        withouPaymentSubscriptionRegDetailsInput.setEpisode_id(Util.selected_episode_id);
                        withouPaymentSubscriptionRegDetailsInput.setCurrency_id(currencyIdStr.trim());
                        withouPaymentSubscriptionRegDetailsInput.setIs_save_this_card(isCheckedToSavetheCard.trim());
                        if (existing_card_id != null && !existing_card_id.matches("") && !existing_card_id.equalsIgnoreCase("")) {
                            withouPaymentSubscriptionRegDetailsInput.setExisting_card_id(existing_card_id);
                        } else {
                            withouPaymentSubscriptionRegDetailsInput.setExisting_card_id("");
                        }
                        WithouPaymentSubscriptionRegDetailsAsync asynWithouPaymentSubscriptionRegDetails = new WithouPaymentSubscriptionRegDetailsAsync(withouPaymentSubscriptionRegDetailsInput, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
                        asynWithouPaymentSubscriptionRegDetails.executeOnExecutor(threadPoolExecutor);
                    }

                } else {
                    String nameOnCardStr = nameOnCardEditText.getText().toString().trim();
                    String cardNumberStr = cardNumberEditText.getText().toString().trim();
                    String securityCodeStr = securityCodeEditText.getText().toString().trim();


                    if (nameOnCardStr.matches("")) {
                        Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(CREDIT_CARD_NAME_HINT, DEFAULT_CREDIT_CARD_NAME_HINT), Toast.LENGTH_LONG).show();

                    } else if (cardNumberStr.matches("")) {
                        Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(CREDIT_CARD_NUMBER_HINT, DEFAULT_CREDIT_CARD_NUMBER_HINT), Toast.LENGTH_LONG).show();

                    } else if (securityCodeStr.matches("")) {
                        Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(CVV_ALERT, DEFAULT_CVV_ALERT), Toast.LENGTH_LONG).show();


                    } else if (expiryMonthStr <= 0) {
//                        Toast.makeText(PPvPaymentInfoActivity.this, "Please enter expiry month", Toast.LENGTH_LONG).show();

                    } else if (expiryYearStr <= 0) {
//                        Toast.makeText(PPvPaymentInfoActivity.this, "Please enter expiry year", Toast.LENGTH_LONG).show();

                    } else {
                        if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
                            AuthUserPaymentInfoInputModel authUserPaymentInfoInputModel = new AuthUserPaymentInfoInputModel();
                            authUserPaymentInfoInputModel.setAuthToken(authTokenStr);
                            authUserPaymentInfoInputModel.setEmail(preferenceManager.getEmailIdFromPref());
                            authUserPaymentInfoInputModel.setExpiryMonth(String.valueOf(expiryMonthStr).trim());
                            authUserPaymentInfoInputModel.setExpiryYear(String.valueOf(expiryYearStr).trim());
                            authUserPaymentInfoInputModel.setCardNumber(cardNumberEditText.getText().toString().trim());
                            authUserPaymentInfoInputModel.setCvv(securityCodeEditText.getText().toString().trim());
                            authUserPaymentInfoInputModel.setName_on_card(nameOnCardEditText.getText().toString().trim());
                            AuthUserPaymentInfoAsyntask asyncReg = new AuthUserPaymentInfoAsyntask(authUserPaymentInfoInputModel, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
                            asyncReg.executeOnExecutor(threadPoolExecutor);


                        }

                    }
                }

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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

                    WithouPaymentSubscriptionRegDetailsInput withouPaymentSubscriptionRegDetailsInput = new WithouPaymentSubscriptionRegDetailsInput();
                    String userIdStr = preferenceManager.getUseridFromPref();
                    String emailIdSubStr = preferenceManager.getEmailIdFromPref();
                    withouPaymentSubscriptionRegDetailsInput.setAuthToken(authTokenStr);
                    if (isAPV == 1) {
                        withouPaymentSubscriptionRegDetailsInput.setIs_advance("1");
                    }
                    withouPaymentSubscriptionRegDetailsInput.setCard_name("");
                    withouPaymentSubscriptionRegDetailsInput.setExp_month("");
                    withouPaymentSubscriptionRegDetailsInput.setCard_number("");
                    withouPaymentSubscriptionRegDetailsInput.setExp_year("");
                    withouPaymentSubscriptionRegDetailsInput.setEmail(emailIdSubStr.trim());
                    withouPaymentSubscriptionRegDetailsInput.setMovie_id(muviUniqueIdStr.trim());
                    withouPaymentSubscriptionRegDetailsInput.setUser_id(userIdStr.trim());
                    if (isCouponCodeAdded == true) {
                        withouPaymentSubscriptionRegDetailsInput.setCoupon_code(validCouponCode);
                    } else {
                        withouPaymentSubscriptionRegDetailsInput.setCoupon_code("");
                    }
                    withouPaymentSubscriptionRegDetailsInput.setCard_last_fourdigit("");
                    withouPaymentSubscriptionRegDetailsInput.setCard_type("");
                    withouPaymentSubscriptionRegDetailsInput.setProfile_id("");
                    withouPaymentSubscriptionRegDetailsInput.setToken("");
                    withouPaymentSubscriptionRegDetailsInput.setCvv("");
                    withouPaymentSubscriptionRegDetailsInput.setCountry(preferenceManager.getCountryCodeFromPref());
                    withouPaymentSubscriptionRegDetailsInput.setSeason_id(Util.selected_season_id);
                    withouPaymentSubscriptionRegDetailsInput.setEpisode_id(Util.selected_episode_id);
                    withouPaymentSubscriptionRegDetailsInput.setCurrency_id(currencyIdStr.trim());
                    withouPaymentSubscriptionRegDetailsInput.setIs_save_this_card(isCheckedToSavetheCard.trim());
                    if (existing_card_id != null && !existing_card_id.matches("") && !existing_card_id.equalsIgnoreCase("")) {
                        withouPaymentSubscriptionRegDetailsInput.setExisting_card_id(existing_card_id);
                    } else {
                        withouPaymentSubscriptionRegDetailsInput.setExisting_card_id("");
                    }
                    WithouPaymentSubscriptionRegDetailsAsync asynWithouPaymentSubscriptionRegDetails = new WithouPaymentSubscriptionRegDetailsAsync(withouPaymentSubscriptionRegDetailsInput, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
                    asynWithouPaymentSubscriptionRegDetails.executeOnExecutor(threadPoolExecutor);
                }
            }

        });


        if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
            creditCardSaveSpinner.setVisibility(View.GONE);
            Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();

        } else {
            GetCardListForPPVInputModel getCardListForPPVInputModel = new GetCardListForPPVInputModel();
            String userIdStr = preferenceManager.getUseridFromPref();
            getCardListForPPVInputModel.setUser_id(userIdStr.trim());
            getCardListForPPVInputModel.setAuthToken(authTokenStr);
            GetCardListForPPVAsynTask asynLoadCardList = new GetCardListForPPVAsynTask(getCardListForPPVInputModel, this, this);
            asynLoadCardList.executeOnExecutor(threadPoolExecutor);
        }
        saveCardCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            if (isChecked == true) {
                                                                isCheckedToSavetheCard = "1";
                                                            } else {
                                                                isCheckedToSavetheCard = "0";

                                                            }

                                                        }
                                                    }
        );

          /*chromecast-------------------------------------*/

        mAquery = new AQuery(this);

        // setupControlsCallbacks();
        setupCastListener();
        mCastContext = CastContext.getSharedInstance(this);
        mCastContext.registerLifecycleCallbacksBeforeIceCreamSandwich(this, savedInstanceState);
        mCastSession = mCastContext.getSessionManager().getCurrentCastSession();

        boolean shouldStartPlayback = false;
        int startPosition = 0;

         /*   MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, movieName.getText().toString());
            movieMetadata.putString(MediaMetadata.KEY_TITLE,  movieName.getText().toString());
            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
            movieMetadata.addImage(new WebImage(Uri.parse(posterImageId.trim())));
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put("description", movieName.getText().toString());
            } catch (JSONException e) {
                Log.e(TAG, "Failed to add description to the json object", e);
            }

            mediaInfo = new MediaInfo.Builder(castVideoUrl.trim())
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType("videos/mp4")
                    .setMetadata(movieMetadata)
                    .setStreamDuration(15 * 1000)
                    .setCustomData(jsonObj)
                    .build();
            mSelectedMedia = mediaInfo;*/

        // see what we need to play and where
           /* Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mSelectedMedia = getIntent().getParcelableExtra("media");
                //setupActionBar();
                boolean shouldStartPlayback = bundle.getBoolean("shouldStart");
                int startPosition = bundle.getInt("startPosition", 0);
                // mVideoView.setVideoURI(Uri.parse(mSelectedMedia.getContentId()));
               // Log.d(TAG, "Setting url of the VideoView to: " + mSelectedMedia.getContentId());
                if (shouldStartPlayback) {
                    // this will be the case only if we are coming from the
                    // CastControllerActivity by disconnecting from a device
                    mPlaybackState = PlaybackState.PLAYING;
                    updatePlaybackLocation(PlaybackLocation.LOCAL);
                    updatePlayButton(mPlaybackState);
                    if (startPosition > 0) {
                        // mVideoView.seekTo(startPosition);
                    }
                    // mVideoView.start();
                    //startControllersTimer();
                } else {
                    // we should load the video but pause it
                    // and show the album art.
                    if (mCastSession != null && mCastSession.isConnected()) {
                        updatePlaybackLocation(PlaybackLocation.REMOTE);
                    } else {
                        updatePlaybackLocation(PlaybackLocation.LOCAL);
                    }
                    mPlaybackState = PlaybackState.IDLE;
                    updatePlayButton(mPlaybackState);
                }
            }*/


        if (shouldStartPlayback) {
            // this will be the case only if we are coming from the
            // CastControllerActivity by disconnecting from a device
            mPlaybackState = MovieDetailsActivity.PlaybackState.PLAYING;
            updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation.LOCAL);
            updatePlayButton(mPlaybackState);
            if (startPosition > 0) {
                // mVideoView.seekTo(startPosition);
            }
            // mVideoView.start();
            //startControllersTimer();
        } else {
            // we should load the video but pause it
            // and show the album art.
            if (mCastSession != null && mCastSession.isConnected()) {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation.REMOTE);
            } else {
                //watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));

                updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation.LOCAL);
            }
            mPlaybackState = MovieDetailsActivity.PlaybackState.IDLE;
            updatePlayButton(mPlaybackState);
        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO THE CALCULATIONS HERE AND SHOW THE RESULT AS PER YOUR CALCULATIONS
            if (isCouponCodeAdded == true) {
                if (s.length() <= 0) {
                    withoutCreditCardLayout.setVisibility(View.GONE);
                    creditCardLayout.setVisibility(View.VISIBLE);
                    paymentOptionLinearLayout.setVisibility(View.VISIBLE);
                    chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + previousChargedPrice);
                    isCouponCodeAdded = false;
                    validCouponCode = "";
                    nameOnCardEditText.setText("");
                    cardNumberEditText.setText("");
                    securityCodeEditText.setText("");
                    cardExpiryMonthSpinner.setSelection(0);
                    cardExpiryYearSpinner.setSelection(0);
                    expiryMonthStr = monthsIdArray.get(0);
                    expiryYearStr = yearArray.get(0);

                    Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(COUPON_CANCELLED, DEFAULT_COUPON_CANCELLED), Toast.LENGTH_LONG).show();

                }
            }

        }


        @Override
        public void afterTextChanged(Editable s) {

        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }


    };

    @Override
    public void onVideoDetailsPreExecuteStarted() {

        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();


    }

    @Override
    public void onVideoDetailsPostExecuteCompleted(Video_Details_Output _video_details_output, int statusCode, String stus, String message) {
        // _video_details_output.setThirdparty_url("https://www.youtube.com/watch?v=fqU2FzATTPY&spfreload=10");
        // _video_details_output.setThirdparty_url("https://player.vimeo.com/video/192417650?color=00ff00&badge=0");

     /*check if status code 200 then set the video url before this it check it is thirdparty url or normal if third party
        then set thirdpartyurl true here and assign the url to videourl*/
        try {
            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();
            }
        } catch (IllegalArgumentException ex) {
        }

        if (statusCode == 200) {
            playerModel.setIsOffline(_video_details_output.getIs_offline());
            playerModel.setDownloadStatus(_video_details_output.getDownload_status());
            if (_video_details_output.getThirdparty_url() == null || _video_details_output.getThirdparty_url().matches("")) {


                /**@bishal
                 * for nondrm player below condition added
                 * if studio_approved_url is there in api then set the videourl from this other wise goto 2nd one
                 */
                LogUtil.showLog("BKS", "studipapprovedurlbefore if entery====" + _video_details_output.getStudio_approved_url());
                if (_video_details_output.getStudio_approved_url() != null &&
                        !_video_details_output.getStudio_approved_url().isEmpty() &&
                        !_video_details_output.getStudio_approved_url().equals("null") &&
                        !_video_details_output.getStudio_approved_url().matches("") && playerModel != null) {
                    LogUtil.showLog("BKS", "if called means  studioapproved");

                    playerModel.setVideoUrl(_video_details_output.getStudio_approved_url());
                    LogUtil.showLog("BKS", "studipapprovedurl====" + playerModel.getVideoUrl());


                    if (_video_details_output.getLicenseUrl().trim() != null && !_video_details_output.getLicenseUrl().trim().isEmpty() && !_video_details_output.getLicenseUrl().trim().equals("null") && !_video_details_output.getLicenseUrl().trim().matches("")) {
                        playerModel.setLicenseUrl(_video_details_output.getLicenseUrl());
                    }
                    if (_video_details_output.getVideoUrl().trim() != null && !_video_details_output.getVideoUrl().isEmpty() && !_video_details_output.getVideoUrl().equals("null") && !_video_details_output.getVideoUrl().trim().matches("")) {
                        playerModel.setMpdVideoUrl(_video_details_output.getVideoUrl());

                    } else {
                        playerModel.setMpdVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
                    }
                } else {
                    if (_video_details_output.getVideoUrl() != null || !_video_details_output.getVideoUrl().matches("")) {
                        playerModel.setVideoUrl(_video_details_output.getVideoUrl());
                        LogUtil.showLog("BISHAL", "videourl===" + playerModel.getVideoUrl());
                        playerModel.setThirdPartyPlayer(false);
                    } else {
                        //  Util.dataModel.setVideoUrl(translatedLanuage.getNoData());
                        playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));

                    }
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
            Util.dataModel.setVideoUrl(playerModel.getVideoUrl());
            Util.dataModel.setVideoResolution(_video_details_output.getVideoResolution());
            Util.dataModel.setThirdPartyUrl(_video_details_output.getThirdparty_url());
            Util.dataModel.setAdNetworkId(_video_details_output.getAdNetworkId());
            Util.dataModel.setChannel_id(_video_details_output.getChannel_id());
            Util.dataModel.setPreRoll(_video_details_output.getPreRoll());
            Util.dataModel.setPostRoll(_video_details_output.getPostRoll());
            Util.dataModel.setMidRoll(_video_details_output.getMidRoll());
            Util.dataModel.setAdDetails(_video_details_output.getAdDetails());
            Util.dataModel.setPlayPos(Integer.parseInt(_video_details_output.getPlayed_length()));

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
                Util.showNoDataAlert(PPvPaymentInfoActivity.this);

                /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
                dlgAlert.setMessage(Util.getTextofLanguage(MovieDetailsActivity.this, Util.NO_VIDEO_AVAILABLE, Util.DEFAULT_NO_VIDEO_AVAILABLE));
                dlgAlert.setTitle(Util.getTextofLanguage(MovieDetailsActivity.this, Util.SORRY, Util.DEFAULT_SORRY));
                dlgAlert.setPositiveButton(Util.getTextofLanguage(MovieDetailsActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
                dlgAlert.setCancelable(false);
                dlgAlert.setPositiveButton(Util.getTextofLanguage(MovieDetailsActivity.this, Util.BUTTON_OK, Util.DEFAULT_BUTTON_OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();*/
            } else {

                // condition for checking if the response has third party url or not.
                if (_video_details_output.getThirdparty_url() == null ||
                        _video_details_output.getThirdparty_url().matches("")
                        ) {


                    playerModel.setThirdPartyPlayer(false);

                    final Intent playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);

                    if (FakeSubTitlePath.size() > 0) {
                        // This Portion Will Be changed Later.

                        File dir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName().trim() + "/SubTitleList/");
                        if (dir.isDirectory()) {
                            String[] children = dir.list();
                            for (int i = 0; i < children.length; i++) {
                                new File(dir, children[i]).delete();
                            }
                        }

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


                } else {
                    final Intent playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);
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

        } else {

            playerModel.setVideoUrl(languagePreference.getTextofLanguage(NO_DATA, DEFAULT_NO_DATA));
            Util.showNoDataAlert(PPvPaymentInfoActivity.this);

        }


    }



    @Override
    public void onGetCardListForPPVPreExecuteStarted() {

        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onGetCardListForPPVPostExecuteCompleted(ArrayList<GetCardListForPPVOutputModel> getCardListForPPVOutputModelArray,
                                                        int status, int totalItems, String message) {

        try {
            if (videoPDialog.isShowing())
                videoPDialog.hide();
        } catch (IllegalArgumentException ex) {

            creditCardSaveSpinner.setVisibility(View.GONE);

        }
        if(status==200)
        {
            ArrayList<CardModel> savedCards = new ArrayList<CardModel>();
            for (GetCardListForPPVOutputModel model:getCardListForPPVOutputModelArray) {
                savedCards.add(new CardModel(model.getCard_id(), model.getCard_last_fourdigit()));
            }
            if (savedCards.size() <= 0) {

                creditCardSaveSpinner.setVisibility(View.GONE);

            } else {

                savedCards.add(0, new CardModel("0", languagePreference.getTextofLanguage(USE_NEW_CARD, DEFAULT_USE_NEW_CARD)));
                cardSavedArray = savedCards.toArray(new CardModel[savedCards.size()]);
                creditCardSaveSpinnerAdapter = new CardSpinnerAdapter(PPvPaymentInfoActivity.this, cardSavedArray);
                //cardExpiryYearSpinnerAdapter = new CardSpinnerAdapter<Integer>(this, R.layout.spinner_new, yearArray);

                creditCardSaveSpinner.setAdapter(creditCardSaveSpinnerAdapter);
                creditCardSaveSpinner.setSelection(0);
            }

        }
        else{
            creditCardSaveSpinner.setVisibility(View.GONE);
        }


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
    public void onValidateCouponCodePreExecuteStarted() {
        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onValidateCouponCodePostExecuteCompleted(ValidateCouponCodeOutputModel validateCouponCodeOutputModel, int status, String message) {

        try {
            if (videoPDialog.isShowing())
                videoPDialog.hide();
        } catch (IllegalArgumentException ex) {
            isCouponCodeAdded = false;
            validCouponCode = "";
        }

        if (status >= 0) {
            if (status == 432) {

                if (validateCouponCodeOutputModel.getDiscount_type().equalsIgnoreCase("%")) {

                    chargedPrice = planPrice - planPrice * (Float.parseFloat(validateCouponCodeOutputModel.getDiscount().trim()) / 100);

                    if (chargedPrice < 0.0f) {
                        chargedPrice = 0.0f;
                    }
                } else {

                    chargedPrice = planPrice - Float.parseFloat(validateCouponCodeOutputModel.getDiscount().trim());

                    if (chargedPrice < 0.0f) {
                        chargedPrice = 0.0f;
                    }
                }


                String[] strValues_chargedPriceTextView = String.valueOf(chargedPrice).split("\\.");
                if (strValues_chargedPriceTextView[1].length() == 1) {
                    chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + chargedPrice + "0");

                }
                else {
                    chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + chargedPrice );

                }
                creditCardLayout.setVisibility(View.VISIBLE);
                paymentOptionLinearLayout.setVisibility(View.VISIBLE);

                isCouponCodeAdded = true;
                validCouponCode = couponCodeEditText.getText().toString().trim();
                Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(DISCOUNT_ON_COUPON, DEFAULT_DISCOUNT_ON_COUPON), Toast.LENGTH_LONG).show();
                if (chargedPrice <= 0.0f && isCouponCodeAdded == true) {
                    creditCardLayout.setVisibility(View.GONE);
                    paymentOptionLinearLayout.setVisibility(View.GONE);
                    //paywithCreditCardButton.setVisibility(View.GONE);
                    withoutCreditCardLayout.setVisibility(View.VISIBLE);

                    withoutPaymentTitleTextView.setText(languagePreference.getTextofLanguage(FREE_FOR_COUPON,DEFAULT_FREE_FOR_COUPON));

                    if (strValues_chargedPriceTextView[1].length() == 1) {
                        withoutCreditCardChargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " : " + currencySymbolStr + chargedPrice+"0");

                    }
                    else  {
                        withoutCreditCardChargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " : " + currencySymbolStr + chargedPrice);

                    }                }


            } else {
                isCouponCodeAdded = false;
                validCouponCode = "";
                chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + planPrice);
                //selectShowRadioButton.setText("Entire Show: " + currencySymbolStr + planPrice);
                isCouponCodeAdded = false;
                validCouponCode = "";
                couponCodeEditText.setText("");

                if (message.trim() != null && !message.trim().isEmpty() && !message.trim().equals("null") && !message.trim().matches("")) {
                    Toast.makeText(PPvPaymentInfoActivity.this, message, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(INVALID_COUPON, DEFAULT_INVALID_COUPON), Toast.LENGTH_LONG).show();

                }
            }
        } else {
            isCouponCodeAdded = false;
            validCouponCode = "";
            chargedPriceTextView.setText(languagePreference.getTextofLanguage(CARD_WILL_CHARGE, DEFAULT_CARD_WILL_CHARGE) + " " + currencySymbolStr + planPrice);
            //selectShowRadioButton.setText("Entire Show: " + currencySymbolStr + planPrice);
            isCouponCodeAdded = false;
            validCouponCode = "";
            couponCodeEditText.setText("");

            if (message.trim() != null && !message.trim().isEmpty() && !message.trim().equals("null") && !message.trim().matches("")) {
                Toast.makeText(PPvPaymentInfoActivity.this, message, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(INVALID_COUPON, DEFAULT_INVALID_COUPON), Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onGetMonetizationDetailsPreExecuteStarted() {

    }

    @Override
    public void onGetMonetizationDetailsPostExecuteCompleted(GetMonetizationDetailsOutputModel getMonetizationDetailsOutputModel, int status, String message) {


        if (status == 200) {
            if (getMonetizationDetailsOutputModel.getVoucher() == 1) {

                if (contentTypesId == 3) {
                    GetVoucherPlan();
                } else {
                    creditCardLayout.setVisibility(View.VISIBLE);
                    voucherRadioButton.setChecked(false);
                    paymentOptionLinearLayout.setVisibility(View.VISIBLE);
                    payWithCreditCardRadioButton.setChecked(true);
                    voucherLinearLayout.setVisibility(View.GONE);
                }
                Log.v("SUBHA", "monetizations voucher 1");

            } else {
                Log.v("SUBHA", "monetizations voucher 0");
                creditCardLayout.setVisibility(View.VISIBLE);
                paymentOptionLinearLayout.setVisibility(View.GONE);
                voucherLinearLayout.setVisibility(View.GONE);
            }

        } else {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this, R.style.MyAlertDialogStyle);
            dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, DEFAULT_NO_DETAILS_AVAILABLE));
            dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, DEFAULT_SORRY));
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            dlgAlert.create().show();
        }
    }


    public void GetVoucherPlan() {

        GetVoucherPlanInputModel getVoucherPlanInputModel = new GetVoucherPlanInputModel();
        loggedInIdStr = preferenceManager.getUseridFromPref();
        getVoucherPlanInputModel.setAuthToken(authTokenStr);
        getVoucherPlanInputModel.setUser_id(loggedInIdStr.trim());
        getVoucherPlanInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        getVoucherPlanInputModel.setSeason(Util.dataModel.getSeason_id());
        getVoucherPlanInputModel.setStream_id(Util.dataModel.getStreamUniqueId());
        GetVoucherPlanAsynTask getVoucherPlan = new GetVoucherPlanAsynTask(getVoucherPlanInputModel, this, this);
        getVoucherPlan.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onGetVoucherPlanPreExecuteStarted() {
        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onGetVoucherPlanPostExecuteCompleted(GetVoucherPlanOutputModel getVoucherPlanOutputModel, int status, String message) {

        try {
            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();
            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }
        if (status == 200) {

            if (selectedPurchaseType == Integer.parseInt(getVoucherPlanOutputModel.getIs_show()) || selectedPurchaseType == Integer.parseInt(getVoucherPlanOutputModel.getIs_season()) || selectedPurchaseType == Integer.parseInt(getVoucherPlanOutputModel.getIs_episode()))

            {
                creditCardLayout.setVisibility(View.VISIBLE);
                voucherRadioButton.setChecked(false);
                paymentOptionLinearLayout.setVisibility(View.VISIBLE);
                payWithCreditCardRadioButton.setChecked(true);
                voucherLinearLayout.setVisibility(View.GONE);
            } else {
                creditCardLayout.setVisibility(View.VISIBLE);
                voucherRadioButton.setChecked(false);
                paymentOptionLinearLayout.setVisibility(View.GONE);
                payWithCreditCardRadioButton.setChecked(true);
                voucherLinearLayout.setVisibility(View.GONE);
            }

        } else {
            creditCardLayout.setVisibility(View.VISIBLE);
            voucherRadioButton.setChecked(false);
            paymentOptionLinearLayout.setVisibility(View.GONE);
            payWithCreditCardRadioButton.setChecked(true);
            voucherLinearLayout.setVisibility(View.GONE);
        }
    }

    public void ValidateVoucher_And_VoucherSubscription() {

        // Calling Validate Voucher Api
        ValidateVoucherInputModel validateVoucherInputModel = new ValidateVoucherInputModel();
        loggedInIdStr = preferenceManager.getUseridFromPref();
        validateVoucherInputModel.setAuthToken(authTokenStr);
        validateVoucherInputModel.setUser_id(loggedInIdStr.trim());
        validateVoucherInputModel.setVoucher_code(VoucherCode);
        if (Util.dataModel.getContentTypesId() == 3) {
            validateVoucherInputModel.setSeason(Util.dataModel.getSeason_id());
            validateVoucherInputModel.setPurchase_type("episode");
        } else {
            validateVoucherInputModel.setPurchase_type("show");
        }
        validateVoucherInputModel.setMovie_id(Util.dataModel.getMovieUniqueId().trim());
        validateVoucherInputModel.setStream_id(Util.dataModel.getStreamUniqueId().trim());
        validateVoucherInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,DEFAULT_SELECTED_LANGUAGE_CODE));

        validateVoucherInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        ValidateVoucherAsynTask asynValidateVoucher = new ValidateVoucherAsynTask(validateVoucherInputModel, this, this);
        asynValidateVoucher.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onValidateVoucherPreExecuteStarted() {
        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onValidateVoucherPostExecuteCompleted(ValidateVoucherOutputModel validateVoucherOutputModel, int status, String message) {
        try {
            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();
            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }

        if (status == 200) {


            voucher_success.setVisibility(View.VISIBLE);
            watch_now.setBackgroundResource(R.drawable.button_radious);
            watch_now.setTextColor(Color.parseColor("#ffffff"));
            watch_status = true;

            apply.setEnabled(false);
            apply.setBackgroundResource(R.drawable.voucher_inactive_button);
            apply.setTextColor(Color.parseColor("#7f7f7f"));

        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onVoucherSubscriptionPreExecuteStarted() {
        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onVoucherSubscriptionPostExecuteCompleted(VoucherSubscriptionOutputModel voucherSubscriptionOutputModel, int status) {
        try {
            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();
            }
        } catch (IllegalArgumentException ex) {
            status = 0;
        }

        if (status == 200) {
            setResultAtFinishActivity();

        } else {
            Toast.makeText(getApplicationContext(), voucherSubscriptionOutputModel.getMsg(), Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onAuthUserPaymentInfoPreExecuteStarted() {

        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onAuthUserPaymentInfoPostExecuteCompleted(AuthUserPaymentInfoOutputModel authUserPaymentInfoOutputModel, int status, String message) {


        if (status == 0) {
            try {
                if (videoPDialog.isShowing())
                    videoPDialog.hide();
            } catch (IllegalArgumentException ex) {
                status = 0;
            }
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);

            if (message.equals(null) || message.equals("") || message == null) {
                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_TRANSACTION_PROCESS,DEFAULT_ERROR_TRANSACTION_PROCESS));
            } else {
                dlgAlert.setMessage(message);
            }

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
        } else if (status == 1) {

            if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                try {
                    if (videoPDialog.isShowing())
                        videoPDialog.hide();
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
                registerUserPaymentInputModel.setEmail(emailIdSubStr);
                registerUserPaymentInputModel.setMovie_id(muviUniqueIdStr.trim());
                registerUserPaymentInputModel.setUser_id(userIdStr);
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
                registerUserPaymentInputModel.setSeason_id(Util.selected_season_id);
                registerUserPaymentInputModel.setEpisode_id(Util.selected_episode_id);
                if (isAPV == 1) {
                    registerUserPaymentInputModel.setIs_advance("1");
                }
                registerUserPaymentInputModel.setCurrency_id(currencyIdStr.trim());
                registerUserPaymentInputModel.setIs_save_this_card(isCheckedToSavetheCard.trim());
                GetPPVPaymentAsync asyncSubsrInfo = new GetPPVPaymentAsync(registerUserPaymentInputModel, this, this);
                asyncSubsrInfo.executeOnExecutor(threadPoolExecutor);
            }
        }
    }

//    private class AsynPaymentInfoDetails extends AsyncTask<Void, Void, Void> {
//        //ProgressDialog pDialog;
//        int status;
//        String responseStr;
//        String responseMessageStr;
//        String emailIdStr = preferenceManager.getEmailIdFromPref();
//
//        String nameOnCardStr = nameOnCardEditText.getText().toString().trim();
//        String cardNumberStr = cardNumberEditText.getText().toString().trim();
//        String securityCodeStr = securityCodeEditText.getText().toString().trim();
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            String urlRouteList = Util.rootUrl().trim() + Util.authenticatedCardValidationUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("nameOnCard", nameOnCardStr);
//                httppost.addHeader("expiryMonth", String.valueOf(expiryMonthStr).trim());
//                httppost.addHeader("expiryYear", String.valueOf(expiryYearStr).trim());
//                httppost.addHeader("cardNumber", cardNumberStr);
//                httppost.addHeader("cvv", securityCodeStr);
//                httppost.addHeader("email", emailIdStr);
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
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
//                            if (pDialog.isShowing())
//                                pDialog.dismiss();
//                            status = 0;
//                            Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    status = 0;
//
//                    e.printStackTrace();
//                }
//                JSONObject myJson = null;
//
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("isSuccess"));
//                }
//                if (status == 1) {
//                    JSONObject mainJson = null;
//
//                    if (myJson.has("card")) {
//                        mainJson = myJson.getJSONObject("card");
//                        if (mainJson.has("status") && mainJson.getString("status").trim() != null && !mainJson.getString("status").trim().isEmpty() && !mainJson.getString("status").trim().equals("null") && !mainJson.getString("status").trim().matches("")) {
//                            statusStr = mainJson.getString("status");
//                        } else {
//                            statusStr = "";
//
//                        }
//
//                        if (mainJson.has("token") && mainJson.getString("token").trim() != null && !mainJson.getString("token").trim().isEmpty() && !mainJson.getString("token").trim().equals("null") && !mainJson.getString("token").trim().matches("")) {
//                            tokenStr = mainJson.getString("token");
//                        } else {
//                            tokenStr = "";
//
//                        }
//
//                        if (mainJson.has("response_text") && mainJson.getString("response_text").trim() != null && !mainJson.getString("response_text").trim().isEmpty() && !mainJson.getString("response_text").trim().equals("null") && !mainJson.getString("response_text").trim().matches("")) {
//                            responseText = mainJson.getString("response_text");
//                        } else {
//                            responseText = "";
//
//                        }
//
//                        if (mainJson.has("profile_id") && mainJson.getString("profile_id").trim() != null && !mainJson.getString("profile_id").trim().isEmpty() && !mainJson.getString("profile_id").trim().equals("null") && !mainJson.getString("profile_id").trim().matches("")) {
//                            profileIdStr = mainJson.getString("profile_id");
//                        } else {
//                            profileIdStr = "";
//
//                        }
//
//                        if (mainJson.has("card_last_fourdigit") && mainJson.getString("card_last_fourdigit").trim() != null && !mainJson.getString("card_last_fourdigit").trim().isEmpty() && !mainJson.getString("card_last_fourdigit").trim().equals("null") && !mainJson.getString("card_last_fourdigit").trim().matches("")) {
//                            cardLastFourDigitStr = mainJson.getString("card_last_fourdigit");
//                        } else {
//                            cardLastFourDigitStr = "";
//
//                        }
//
//                        if (mainJson.has("card_type") && mainJson.getString("card_type").trim() != null && !mainJson.getString("card_type").trim().isEmpty() && !mainJson.getString("card_type").trim().equals("null") && !mainJson.getString("card_type").trim().matches("")) {
//                            cardTypeStr = mainJson.getString("card_type");
//                        } else {
//                            cardTypeStr = "";
//
//                        }
//                    }
//
//
//                }
//                if (status == 0) {
//                    if (myJson.has("Message")) {
//                        responseMessageStr = myJson.optString("Message");
//                    }
//                    if (((responseMessageStr.equalsIgnoreCase("null")) || (responseMessageStr.length() <= 0))) {
//                        responseMessageStr = languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE, Util.DEFAULT_NO_DETAILS_AVAILABLE);
//
//                    }
//                }
//
//            } catch (Exception e) {
//                if (pDialog.isShowing())
//                    pDialog.dismiss();
//                status = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//           /* try {
//                if (pDialog.isShowing())
//                    pDialog.dismiss();
//            } catch (IllegalArgumentException ex) {
//                status = 0;
//            }*/
//            if (responseStr == null) {
//                try {
//                    if (videoPDialog.isShowing())
//                        videoPDialog.hide();
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_PAYMENT_VALIDATION, Util.DEFAULT_ERROR_IN_PAYMENT_VALIDATION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status == 0) {
//                try {
//                    if (videoPDialog.isShowing())
//                        videoPDialog.hide();
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(responseMessageStr);
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, Util.DEFAULT_FAILURE));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            } else if (status == 1) {
//                boolean isNetwork = Util.checkNetwork(PPvPaymentInfoActivity.this);
//                if (isNetwork == false) {
//                    try {
//                        if (videoPDialog.isShowing())
//                            videoPDialog.hide();
//                    } catch (IllegalArgumentException ex) {
//                        status = 0;
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                    dlgAlert.create().show();
//
//                } else {
//                    AsyncPPVPayment asyncSubsrInfo = new AsyncPPVPayment();
//                    asyncSubsrInfo.executeOnExecutor(threadPoolExecutor);
//                }
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
//            videoPDialog.show();
//           /* pDialog = new ProgressDialog(PPvPaymentInfoActivity.this);
//            pDialog.setMessage(getResources().getString(R.string.loading_str));
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();*/
//        }
//
//
//    }


    @Override
    public void onGetWithouPaymentSubscriptionRegDetailsPreExecuteStarted() {
        videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
        videoPDialog.show();
    }

    @Override
    public void onGetWithouPaymentSubscriptionRegDetailsPostExecuteCompleted(int status, String Response) {


        if (status == 0) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
        }
        if (status > 0) {

            if (status == 200) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
                            if (isAPV == 1) {
                                Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(PURCHASE_SUCCESS_ALERT, DEFAULT_PURCHASE_SUCCESS_ALERT), Toast.LENGTH_LONG).show();
                                finish();
                                overridePendingTransition(0, 0);
                            } else {
                                setResultAtFinishActivity();
                            }
                               /* final Intent playVideoIntent = new Intent(PPvPaymentInfoActivity.this, PlayVideoActivity.class);
                                playVideoIntent.putExtra("activity", "generic");
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        playVideoIntent.putExtra("url", videoUrlStr.trim());
                                        startActivity(playVideoIntent);
                                        finish();
                                    }
                                });*/
                        }
                    }
                });


            } else {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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

//    private class AsynWithouPaymentSubscriptionRegDetails extends AsyncTask<Void, Void, Void> {
//        ProgressDialog pDialog;
//        int status;
//        String responseStr;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String userIdStr = preferenceManager.getUseridFromPref();
//            String emailIdSubStr = preferenceManager.getEmailIdFromPref();
//
//         /*   runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(saveCardCheckbox.isChecked()){
//                        isCheckedToSavetheCard = "1";
//                    }else{
//                        isCheckedToSavetheCard = "0";
//
//                    }
//
//                }
//
//            });*/
//
//
//            String urlRouteList = Util.rootUrl().trim() + Util.addSubscriptionUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                final HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr.trim());
//                if (isAPV == 1) {
//                    httppost.addHeader("is_advance", "1");
//                }
//
//                httppost.addHeader("card_name", "");
//                httppost.addHeader("exp_month", "");
//                httppost.addHeader("card_number", "");
//                httppost.addHeader("exp_year", "");
//                httppost.addHeader("email", emailIdSubStr.trim());
//                httppost.addHeader("movie_id", muviUniqueIdStr.trim());
//                //httppost.addHeader("movie_id","5a07372fd347136975e3dd4c9897cf23");
//                httppost.addHeader("user_id", userIdStr.trim());
//                if (isCouponCodeAdded == true) {
//                    httppost.addHeader("coupon_code", validCouponCode);
//                } else {
//                    httppost.addHeader("coupon_code", "");
//                }
//                httppost.addHeader("card_type", "");
//                httppost.addHeader("card_last_fourdigit", "");
//                httppost.addHeader("profile_id", "");
//                httppost.addHeader("token", "");
//                httppost.addHeader("cvv", "");
//                // httppost.addHeader("country","US");
//
//                httppost.addHeader("country", preferenceManager.getCountryCodeFromPref());
//                //*********************************//
//
////                httppost.addHeader("season_id", "0");
////                httppost.addHeader("episode_id", "0");
//                httppost.addHeader("season_id", Util.selected_season_id);
//                httppost.addHeader("episode_id", Util.selected_episode_id);
//
//
//                LogUtil.showLog("MUVI", "season_id=====================" + Util.selected_season_id);
//                LogUtil.showLog("MUVI", "episode_id=====================" + Util.selected_episode_id);
//
//                httppost.addHeader("currency_id", currencyIdStr.trim());
//
//                httppost.addHeader("is_save_this_card", isCheckedToSavetheCard.trim());
//                if (existing_card_id != null && !existing_card_id.matches("") && !existing_card_id.equalsIgnoreCase("")) {
//                    httppost.addHeader("existing_card_id", existing_card_id);
//                } else {
//                    httppost.addHeader("existing_card_id", "");
//                }
//
//              /*  runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (creditCardSaveSpinner!=null && cardSavedArray!=null && cardSavedArray.length > 0 && creditCardSaveSpinner.getSelectedItemPosition() > 0){
//                            String  existing_card_id = cardSavedArray[creditCardSaveSpinner.getSelectedItemPosition()].getCardId();
//                            httppost.addHeader("existing_card_id", existing_card_id);
//
//                        }
//
//                    }
//
//                });
//
//*/
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
//
//                            if (pDialog.isShowing())
//                                pDialog.dismiss();
//                            status = 0;
//                            Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//                    try {
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                    } catch (IllegalArgumentException ex) {
//                        status = 0;
//
//                        e.printStackTrace();
//                    }
//                }
//                if (responseStr != null) {
//                    JSONObject myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//
//                }
//
//            } catch (Exception e) {
//                try {
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//            try {
//                if (videoPDialog.isShowing())
//                    videoPDialog.hide();
//            } catch (IllegalArgumentException ex) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (responseStr == null) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status == 0) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(FAILURE, Util.DEFAULT_FAILURE));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status > 0) {
//
//                if (status == 200) {
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            boolean isNetwork = Util.checkNetwork(PPvPaymentInfoActivity.this);
//                            if (isNetwork == false) {
//                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION));
//                                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                                dlgAlert.setCancelable(false);
//                                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//
//                                            }
//                                        });
//                                dlgAlert.create().show();
//
//                            } else {
//                                if (isAPV == 1) {
//                                    Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(PURCHASE_SUCCESS_ALERT, Util.DEFAULT_PURCHASE_SUCCESS_ALERT), Toast.LENGTH_LONG).show();
//                                    finish();
//                                    overridePendingTransition(0, 0);
//                                } else {
//                                    if (isCastConnected == true) {
//                                        onBackPressed();
//
//                                    } else {
//                                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                        getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
//                                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
//                                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
//
//                                        VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
//                                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                                    }
//                                }
//                               /* final Intent playVideoIntent = new Intent(PPvPaymentInfoActivity.this, PlayVideoActivity.class);
//                                playVideoIntent.putExtra("activity", "generic");
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        playVideoIntent.putExtra("url", videoUrlStr.trim());
//                                        startActivity(playVideoIntent);
//                                        finish();
//                                    }
//                                });*/
//                            }
//                        }
//                    });
//
//
//                } else {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                    dlgAlert.create().show();
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
//            videoPDialog.show();
//
//        }
//
//
//    }

    @Override
    public void onGetPPVPaymentPreExecuteStarted() {

    }

    @Override
    public void onGetPPVPaymentPostExecuteCompleted(RegisterUserPaymentOutputModel registerUserPaymentOutputModel, int status, String response) {


        if (status == 0) {
            try {
                if (videoPDialog.isShowing())
                    videoPDialog.hide();
            } catch (IllegalArgumentException ex) {
                status = 0;
            }
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
        if (status > 0) {

            if (status == 200) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!NetworkStatus.getInstance().isConnected(PPvPaymentInfoActivity.this)) {
                            try {
                                if (videoPDialog.isShowing())
                                    videoPDialog.hide();
                            } catch (IllegalArgumentException ex) {
                            }
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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
                            try {
                                if (videoPDialog.isShowing())
                                    videoPDialog.hide();
                            } catch (IllegalArgumentException ex) {
                            }
                            if (isAPV == 1) {
                                Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(PURCHASE_SUCCESS_ALERT, DEFAULT_PURCHASE_SUCCESS_ALERT), Toast.LENGTH_LONG).show();
                                onBackPressed();
                            } else {
                                setResultAtFinishActivity();
                            }
                        }

                    }
                });


            } else {
                try {
                    if (videoPDialog.isShowing())
                        videoPDialog.hide();
                } catch (IllegalArgumentException ex) {
                    status = 0;
                }
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
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

//    private class AsyncPPVPayment extends AsyncTask<Void, Void, Void> {
//        // ProgressDialog pDialog;
//        int status;
//        String responseStr;
//        String nameOnCardStr = nameOnCardEditText.getText().toString().trim();
//        String cardNumberStr = cardNumberEditText.getText().toString().trim();
//        String securityCardStr = securityCodeEditText.getText().toString().trim();
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            String userIdStr = preferenceManager.getUseridFromPref();
//            String emailIdSubStr = preferenceManager.getEmailIdFromPref();
//           /* runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if(saveCardCheckbox.isChecked()){
//                        isCheckedToSavetheCard = "1";
//                        Toast.makeText(PPvPaymentInfoActivity.this,"Data saved",Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        isCheckedToSavetheCard = "0";
//                        Toast.makeText(PPvPaymentInfoActivity.this,"Data Not saved",Toast.LENGTH_SHORT).show();
//
//
//                    }
//
//                }
//
//            });*/
//            String urlRouteList = rootUrl().trim() + addSubscriptionUrl.trim();
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(urlRouteList);
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", authTokenStr.trim());
//                httppost.addHeader("card_name", nameOnCardStr);
//                httppost.addHeader("exp_month", String.valueOf(expiryMonthStr).trim());
//                httppost.addHeader("card_number", cardNumberStr);
//                httppost.addHeader("exp_year", String.valueOf(expiryYearStr).trim());
//                httppost.addHeader("email", emailIdSubStr.trim());
//                httppost.addHeader("movie_id", muviUniqueIdStr.trim());
//                httppost.addHeader("user_id", userIdStr.trim());
//
//
//                if (isCouponCodeAdded == true) {
//
//                    httppost.addHeader("coupon_code", validCouponCode);
//                } else {
//
//                    httppost.addHeader("coupon_code", "");
//                }
//
//                httppost.addHeader("card_type", cardTypeStr.trim());
//                httppost.addHeader("card_last_fourdigit", cardLastFourDigitStr.trim());
//                httppost.addHeader("profile_id", profileIdStr.trim());
//                httppost.addHeader("token", tokenStr.trim());
//                httppost.addHeader("cvv", securityCardStr);
//                // httppost.addHeader("country",currencyCountryCodeStr.trim());
//
//                httppost.addHeader("country", preferenceManager.getCountryCodeFromPref());
//                //*********************************// ((Global) getApplicationContext()).getCountryCode()
////                httppost.addHeader("season_id", "0");
////                httppost.addHeader("episode_id", "0");
//                httppost.addHeader("season_id", selected_season_id);
//                httppost.addHeader("episode_id", Util.selected_episode_id);
//
//
//                if (isAPV == 1) {
//                    httppost.addHeader("is_advance", "1");
//                }
//                httppost.addHeader("currency_id", currencyIdStr.trim());
//
//                httppost.addHeader("is_save_this_card", isCheckedToSavetheCard.trim());
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//
//                } catch (org.apache.http.conn.ConnectTimeoutException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            status = 0;
//                            if (pDialog.isShowing())
//                                pDialog.dismiss();
//                            Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    });
//
//                } catch (IOException e) {
//
//                    status = 0;
//                    if (pDialog.isShowing())
//                        pDialog.dismiss();
//                    e.printStackTrace();
//                }
//                if (responseStr != null) {
//                    JSONObject myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//
//                }
//
//            } catch (Exception e) {
//
//                if (pDialog.isShowing())
//                    pDialog.dismiss();
//                status = 0;
//
//            }
//
//            return null;
//        }
//
//
//        protected void onPostExecute(Void result) {
//           /* try {
//                if (pDialog.isShowing())
//                    pDialog.dismiss();
//            } catch (IllegalArgumentException ex) {
//               status = 0;
//            }*/
//            if (responseStr == null) {
//                try {
//                    if (videoPDialog.isShowing())
//                        videoPDialog.hide();
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status == 0) {
//                try {
//                    if (videoPDialog.isShowing())
//                        videoPDialog.hide();
//                } catch (IllegalArgumentException ex) {
//                    status = 0;
//                }
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                dlgAlert.setCancelable(false);
//                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//                dlgAlert.create().show();
//            }
//            if (status > 0) {
//
//                if (status == 200) {
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            boolean isNetwork = Util.checkNetwork(PPvPaymentInfoActivity.this);
//                            if (isNetwork == false) {
//                                try {
//                                    if (videoPDialog.isShowing())
//                                        videoPDialog.hide();
//                                } catch (IllegalArgumentException ex) {
//                                    status = 0;
//                                }
//                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                                dlgAlert.setMessage(languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, Util.DEFAULT_NO_INTERNET_CONNECTION));
//                                dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                                dlgAlert.setCancelable(false);
//                                dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//
//                                            }
//                                        });
//                                dlgAlert.create().show();
//
//                            } else {
//                                try {
//                                    if (videoPDialog.isShowing())
//                                        videoPDialog.hide();
//                                } catch (IllegalArgumentException ex) {
//                                    status = 0;
//                                }
//                                if (isAPV == 1) {
//                                    Toast.makeText(PPvPaymentInfoActivity.this, languagePreference.getTextofLanguage(PURCHASE_SUCCESS_ALERT, Util.DEFAULT_PURCHASE_SUCCESS_ALERT), Toast.LENGTH_LONG).show();
//                                    onBackPressed();
//                                } else {
//                                    if (isCastConnected == true) {
//                                        onBackPressed();
//
//                                    } else {
//                                        GetVideoDetailsInput getVideoDetailsInput = new GetVideoDetailsInput();
//                                        getVideoDetailsInput.setAuthToken(Util.authTokenStr);
//                                        getVideoDetailsInput.setInternetSpeed(MainActivity.internetSpeed.trim());
//                                        getVideoDetailsInput.setStream_uniq_id(Util.dataModel.getStreamUniqueId().trim());
//                                        getVideoDetailsInput.setContent_uniq_id(Util.dataModel.getMovieUniqueId().trim());
//
//                                        VideoDetailsAsynctask asynLoadVideoUrls = new VideoDetailsAsynctask(getVideoDetailsInput, PPvPaymentInfoActivity.this, PPvPaymentInfoActivity.this);
//                                        asynLoadVideoUrls.executeOnExecutor(threadPoolExecutor);
//                                    }
//                                }
//                            }
//
//                        }
//                    });
//
//
//                } else {
//                    try {
//                        if (videoPDialog.isShowing())
//                            videoPDialog.hide();
//                    } catch (IllegalArgumentException ex) {
//                        status = 0;
//                    }
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(PPvPaymentInfoActivity.this);
//                    dlgAlert.setMessage(languagePreference.getTextofLanguage(ERROR_IN_SUBSCRIPTION, Util.DEFAULT_ERROR_IN_SUBSCRIPTION));
//                    dlgAlert.setTitle(languagePreference.getTextofLanguage(SORRY, Util.DEFAULT_SORRY));
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK), null);
//                    dlgAlert.setCancelable(false);
//                    dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, Util.DEFAULT_BUTTON_OK),
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                    dlgAlert.create().show();
//                }
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
//            videoPDialog.show();
//           /* pDialog = new ProgressDialog(PPvPaymentInfoActivity.this);
//            pDialog.setMessage(getResources().getString(R.string.loading_str));
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();*/
//        }
//
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        // **************chromecast*********************//
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(this).getSessionManager()
                    .getCurrentCastSession();
        }


        mCastContext.getSessionManager().addSessionManagerListener(
                mSessionManagerListener, CastSession.class);

        //**************chromecast*********************//
        if (CardIOActivity.canReadCardWithCamera()) {
            scanButton.setText("Scan");
            scanButton.setEnabled(true);
        } /*else {
            scanButton.setText("Scan");
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                scanButton.setBackgroundDrawable( getResources().getDrawable(R.drawable.button_background_gray) );
            } else {
                scanButton.setBackground( getResources().getDrawable(R.drawable.button_background_gray));
            }
           // scanButton.setBackground(R.drawable.button_background_gray);
            scanButton.setEnabled(false);
        }*/
    }

    public void onScanPress(View v) {
        // This method is set up as an onClick handler in the layout xml
        // e.g. android:onClick="onScanPress"

        Intent scanIntent = new Intent(this, CardIOActivity.class);
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true); // default: false

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true);

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        // matches the theme of your application
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            cardNumberEditText.setText(scanResult.cardNumber);

            if (scanResult.isExpiryValid()) {

                if (monthsIdArray.contains(scanResult.expiryMonth)) {

                    // true
                    int index = monthsIdArray.indexOf(scanResult.expiryMonth);
                    expiryMonthStr = monthsIdArray.get(index);
                    cardExpiryMonthSpinner.setSelection(index);
                }
                if (yearArray.contains(scanResult.expiryYear)) {
                    // true
                    int index = yearArray.indexOf(scanResult.expiryYear);
                    expiryYearStr = yearArray.get(index);
                    cardExpiryYearSpinner.setSelection(index);
                }

            }


            if (scanResult.cvv != null) {
                // Never log or display a CVV
                securityCodeEditText.setText(scanResult.cvv);
            }

        } else {
            Toast.makeText(PPvPaymentInfoActivity.this, "Please enter credit card details manually", Toast.LENGTH_LONG).show();
        }

    }

    public void Download_SubTitle(String Url) {
        new DownloadFileFromURL().execute(Url);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            videoPDialog = new ProgressBarHandler(PPvPaymentInfoActivity.this);
            videoPDialog.show();

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

            if (videoPDialog != null && videoPDialog.isShowing()) {
                videoPDialog.hide();
            }

            FakeSubTitlePath.remove(0);
            if (FakeSubTitlePath.size() > 0) {
                Download_SubTitle(FakeSubTitlePath.get(0).trim());
            } else {

                final Intent playVideoIntent;
                if (Util.dataModel.getAdNetworkId() == 3) {
                    LogUtil.showLog("responseStr", "playVideoIntent" + Util.dataModel.getAdNetworkId());

                    playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);

                } else if (Util.dataModel.getAdNetworkId() == 1 && Util.dataModel.getPreRoll() == 1) {
                    if (Util.dataModel.getPlayPos() <= 0) {
                        playVideoIntent = new Intent(PPvPaymentInfoActivity.this, AdPlayerActivity.class);
                    } else {
                        playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);

                    }
                } else {
                    playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);

                }
                /***ad **/
                playerModel.setSubTitlePath(SubTitlePath);
                //Intent playVideoIntent = new Intent(PPvPaymentInfoActivity.this, ExoPlayerActivity.class);
                playVideoIntent.putExtra("PlayerModel", playerModel);
                playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(playVideoIntent);
                finish();
            }
        }
    }

    private void setupCastListener() {
        mSessionManagerListener = new SessionManagerListener<CastSession>() {

            @Override
            public void onSessionEnded(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionResumed(CastSession session, boolean wasSuspended) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionResumeFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarted(CastSession session, String sessionId) {
                onApplicationConnected(session);
            }

            @Override
            public void onSessionStartFailed(CastSession session, int error) {
                onApplicationDisconnected();
            }

            @Override
            public void onSessionStarting(CastSession session) {
            }

            @Override
            public void onSessionEnding(CastSession session) {
            }

            @Override
            public void onSessionResuming(CastSession session, String sessionId) {
            }

            @Override
            public void onSessionSuspended(CastSession session, int reason) {
            }

            private void onApplicationConnected(CastSession castSession) {
                mCastSession = castSession;
                mLocation = MovieDetailsActivity.PlaybackLocation.REMOTE;
                if (null != mSelectedMedia) {

                    if (mPlaybackState == MovieDetailsActivity.PlaybackState.PLAYING) {
                       /* mVideoView.pause();
                        loadRemoteMedia(mSeekbar.getProgress(), true);*/
                        return;
                    } else {
                        mPlaybackState = MovieDetailsActivity.PlaybackState.IDLE;
                        updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation.REMOTE);
                    }
                }
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }

            private void onApplicationDisconnected() {
/*
                    mPlayCircle.setVisibility(View.GONE);
*/

                updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation.LOCAL);
                mPlaybackState = MovieDetailsActivity.PlaybackState.IDLE;
                mLocation = MovieDetailsActivity.PlaybackLocation.LOCAL;
                updatePlayButton(mPlaybackState);
                invalidateOptionsMenu();
            }
        };
    }

    private void updatePlayButton(MovieDetailsActivity.PlaybackState state) {
           /* boolean isConnected = (mCastSession != null)
                    && (mCastSession.isConnected() || mCastSession.isConnecting());*/
        //mControllers.setVisibility(isConnected ? View.GONE : View.VISIBLE);

        switch (state) {
            case PLAYING:

                //mLoading.setVisibility(View.INVISIBLE);
                // mPlayPause.setVisibility(View.VISIBLE);
                //mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_av_pause_dark));

                break;
            case IDLE:
                if (mLocation == MovieDetailsActivity.PlaybackLocation.LOCAL) {
                   /* if (isAPV == 1) {
                        watchMovieButton.setText(getResources().getString(R.string.advance_purchase_str));
                    }else {
                        watchMovieButton.setText(getResources().getString(R.string.movie_details_watch_video_button_title));
                    }*/

                } else {
                   /* if (isAPV == 1) {
                        watchMovieButton.setText(getResources().getString(R.string.advance_purchase_str));
                    }else {
                        watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                    }*/
                }
                //mCon
                // trollers.setVisibility(View.GONE);
                // mCoverArt.setVisibility(View.VISIBLE);
                // mVideoView.setVisibility(View.INVISIBLE);
                break;
            case PAUSED:
                //mLoading.setVisibility(View.INVISIBLE);
              /*  mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_av_play_dark));*/

                break;
            case BUFFERING:
                //mPlayPause.setVisibility(View.INVISIBLE);
                //mLoading.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void updatePlaybackLocation(MovieDetailsActivity.PlaybackLocation location) {
        mLocation = location;
        if (location == MovieDetailsActivity.PlaybackLocation.LOCAL) {
            if (mPlaybackState == MovieDetailsActivity.PlaybackState.PLAYING
                    || mPlaybackState == MovieDetailsActivity.PlaybackState.BUFFERING) {
                //setCoverArtStatus(null);
                //startControllersTimer();
            } else {
                //stopControllersTimer();

                //setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            }
        } else {
            //stopControllersTimer();
            // setCoverArtStatus(MediaUtils.getImageUrl(mSelectedMedia, 0));
            //updateControllersVisibility(false);
        }
    }

    private void togglePlayback() {
        //stopControllersTimer();
        switch (mPlaybackState) {
            case PAUSED:
                switch (mLocation) {
                    case LOCAL:



                      /* mVideoView.start();
                        Log.d(TAG, "Playing locally...");
                        mPlaybackState = PlaybackState.PLAYING;
                        startControllersTimer();
                        restartTrickplayTimer();
                        updatePlaybackLocation(PlaybackLocation.LOCAL);*/
                        break;

                    case REMOTE:

                        loadRemoteMedia(0, true);

                        break;
                    default:
                        break;
                }
                break;

            case PLAYING:
                mPlaybackState = MovieDetailsActivity.PlaybackState.PAUSED;

                //  mVideoView.pause();
                break;

            case IDLE:
                switch (mLocation) {
                    case LOCAL:
                        //watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));

                        // mPlayCircle.setVisibility(View.GONE);
                       /* mVideoView.setVideoURI(Uri.parse(mSelectedMedia.getContentId()));
                        mVideoView.seekTo(0);
                        mVideoView.start();
                        mPlaybackState = PlaybackState.PLAYING;
                        restartTrickplayTimer();
                        updatePlaybackLocation(PlaybackLocation.LOCAL);*/
                        break;
                    case REMOTE:
                        // mPlayCircle.setVisibility(View.VISIBLE);
                        if (mCastSession != null && mCastSession.isConnected()) {
                            // watchMovieButton.setText(getResources().getString(R.string.movie_details_cast_now_button_title));
                            loadRemoteMedia(0, true);


                            // Utils.showQueuePopup(this, mPlayCircle, mSelectedMedia);
                        } else {
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        updatePlayButton(mPlaybackState);
    }

    private void loadRemoteMedia(int position, boolean autoPlay) {

        if (mCastSession == null) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if (remoteMediaClient == null) {
            return;
        }
        remoteMediaClient.addListener(new RemoteMediaClient.Listener() {

            @Override
            public void onStatusUpdated() {

                Intent intent = new Intent(PPvPaymentInfoActivity.this, ExpandedControlsActivity.class);
                startActivity(intent);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                remoteMediaClient.removeListener(this);
                finish();

            }

            @Override
            public void onMetadataUpdated() {
            }

            @Override
            public void onQueueStatusUpdated() {
            }

            @Override
            public void onPreloadStatusUpdated() {
            }

            @Override
            public void onSendingRemoteMediaRequest() {
            }
        });
        remoteMediaClient.setActiveMediaTracks(new long[1]).setResultCallback(new ResultCallback<RemoteMediaClient.MediaChannelResult>() {
            @Override
            public void onResult(@NonNull RemoteMediaClient.MediaChannelResult mediaChannelResult) {
                if (!mediaChannelResult.getStatus().isSuccess()) {
                    Log.v("SUBHA", "Failed with status code:" +
                            mediaChannelResult.getStatus().getStatusCode());
                }
            }
        });
        remoteMediaClient.load(mSelectedMedia, autoPlay, position);
    }


    /*@Override
    public void onBackPressed()
    {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (getIntent().getStringExtra("activity").trim()!=null && getIntent().getStringExtra("activity").trim().equalsIgnoreCase("generic")){

            runOnUiThread(new Runnable() {
                public void run() {
                    Intent newIn = new Intent(PPvPaymentInfoActivity.this, MainActivity.class);
                    newIn.putExtra("activity","generic");
                    newIn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(newIn);
                    finish();
                    overridePendingTransition(0, 0);
                }
            });



        }
        super.onBackPressed();

    }*/
    public void setResultAtFinishActivity(){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
