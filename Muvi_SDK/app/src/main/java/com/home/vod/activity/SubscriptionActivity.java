package com.home.vod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.home.apisdk.apiController.GetPlanListAsynctask;
import com.home.apisdk.apiModel.CurrencyModel;
import com.home.apisdk.apiModel.SubscriptionPlanInputModel;
import com.home.apisdk.apiModel.SubscriptionPlanOutputModel;
import com.home.vod.R;
import com.home.vod.adapter.PlanAdapter;
import com.home.vod.fragment.VideosListFragment;
import com.home.vod.model.PlanModel;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FeatureHandler;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.ACTIAVTE_PLAN_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ACTIAVTE_PLAN_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECT_PLAN;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SKIP_BUTTON_TITLE;

import static com.home.vod.preferences.LanguagePreference.NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DETAILS_AVAILABLE;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.SELECT_PLAN;
import static com.home.vod.preferences.LanguagePreference.SKIP_BUTTON_TITLE;
import static com.home.vod.util.Constant.authTokenStr;
import static com.home.vod.util.Util.DEFAULT_IS_ONE_STEP_REGISTRATION;

public class SubscriptionActivity extends AppCompatActivity implements GetPlanListAsynctask.GetStudioPlanListsListener {
    RecyclerView subcription;
    ArrayList<PlanModel> movieList=new ArrayList<PlanModel>();
    Button activation_plan,skipButton;
    LinearLayoutManager mLayoutManager;
    PlanAdapter mAdapter;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    String responseStr;
    String planIdStr;
    String planNamestr;
    String planStudioIdStr;
    String planPriceStr;
    String planRecurrenceStr;
    String planFrequencyStr;
    int planStatusStr = 0;
    String planLanguage_idStr;
    String currencyIdStr;
    String currencyCountryCodeStr;
    String currencySymbolStr;
    String currencyTrialPeriodStr;
    String currencyTrialRecurrenceStr;
    int keepAliveTime = 10;
    String planId;
    TextView subscriptionTitleTextView;
    LanguagePreference languagePreference;
    int selected_subscription_plan = 0 ;
    ProgressBarHandler progressBarHandler;
    FeatureHandler featureHandler;
    int prevPosition = 0;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        subcription= (RecyclerView) findViewById(R.id.recyclerViewSubscription);
        activation_plan= (Button) findViewById(R.id.activationplan);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        subscriptionTitleTextView = (TextView) findViewById(R.id.subscriptionTitleTextView);
        languagePreference = LanguagePreference.getLanguagePreference(SubscriptionActivity.this);
        featureHandler = FeatureHandler.getFeaturePreference(SubscriptionActivity.this);
        skipButton= (Button) findViewById(R.id.skipButton);

        if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP,FeatureHandler.DEFAULT_SIGNUP_STEP))) {
            toolbar.setNavigationIcon(null);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        }
        else
        {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        }




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FontUtls.loadFont(SubscriptionActivity.this, getResources().getString(R.string.regular_fonts),subscriptionTitleTextView);

        subscriptionTitleTextView.setText(languagePreference.getTextofLanguage(SELECT_PLAN,DEFAULT_SELECT_PLAN)+" :");
        FontUtls.loadFont(SubscriptionActivity.this, getResources().getString(R.string.regular_fonts),activation_plan);
        activation_plan.setText(languagePreference.getTextofLanguage(ACTIAVTE_PLAN_TITLE,DEFAULT_ACTIAVTE_PLAN_TITLE));
        FontUtls.loadFont(SubscriptionActivity.this, getResources().getString(R.string.regular_fonts),skipButton);

        skipButton.setText(languagePreference.getTextofLanguage(SKIP_BUTTON_TITLE,DEFAULT_SKIP_BUTTON_TITLE));


        mLayoutManager = new LinearLayoutManager(SubscriptionActivity.this, LinearLayoutManager.VERTICAL, false);
        if(NetworkStatus.getInstance().isConnected(this)) {
            SubscriptionPlanInputModel planListInput=new SubscriptionPlanInputModel();
            planListInput.setAuthToken(authTokenStr);
            planListInput.setLang(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            GetPlanListAsynctask asynLoadPlanDetails = new GetPlanListAsynctask(planListInput,this,this);
            asynLoadPlanDetails.executeOnExecutor(threadPoolExecutor);
        }



        activation_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtil.showLog("MUVI","Chek for content click ="+Util.check_for_subscription);

                Intent intentpayment=new Intent(SubscriptionActivity.this,PaymentInfoActivity.class);
                intentpayment.putExtra("currencyId",movieList.get(selected_subscription_plan).getPlanCurrencyIdStr());
                intentpayment.putExtra("currencyCountryCode",movieList.get(selected_subscription_plan).getCurrencyCountryCodeStr());
                intentpayment.putExtra("currencySymbol",movieList.get(selected_subscription_plan).getPlanCurrencySymbolstr());
                intentpayment.putExtra("price",movieList.get(selected_subscription_plan).getPurchaseValueStr());
                intentpayment.putExtra("selected_plan_id",movieList.get(selected_subscription_plan).getPlanIdStr());

                startActivity(intentpayment);
                if (!featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP,FeatureHandler.DEFAULT_SIGNUP_STEP)){
                    finish();
                }
               // finish();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onGetPlanListPreExecuteStarted() {

        progressBarHandler = new ProgressBarHandler(SubscriptionActivity.this);
        progressBarHandler.show();
    }

    @Override
    public void onGetPlanListPostExecuteCompleted(ArrayList<SubscriptionPlanOutputModel> planListOutput, int status) {

        try{
            if(progressBarHandler.isShowing())
                progressBarHandler.hide();
        }
        catch(IllegalArgumentException ex)
        {
        }

        if (status>0){

            if (status==200){


                for (int i=0;i<planListOutput.size();i++) {
                    SubscriptionPlanOutputModel outPutModel = planListOutput.get(i);
                    CurrencyModel currency=outPutModel.getCurrencyDetails();
                    if (outPutModel.getId()!=null) {
                        planIdStr = outPutModel.getId();
                    } else {
                        planIdStr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }

                    if (outPutModel.getName()!=null) {
                        planNamestr = outPutModel.getName();
                    } else {
                        planNamestr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);


                    }

                    if (outPutModel.getRecurrence()!=null) {
                        planRecurrenceStr = outPutModel.getRecurrence();
                    } else {
                        planRecurrenceStr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }

                    if (outPutModel.getFrequency()!=null) {
                        planFrequencyStr = outPutModel.getFrequency();
                    } else {
                        planFrequencyStr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }

                    if (outPutModel.getStudio_id()!=null) {
                        planStudioIdStr = outPutModel.getStudio_id();
                    } else {
                        planStudioIdStr =languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }

                    if (outPutModel.getStatus()!=null) {
                        planStatusStr = Integer.parseInt(outPutModel.getStatus());
                    } else {
                        planStatusStr = 0;

                    }

                    if (outPutModel.getLanguage_id()!=null) {
                        planLanguage_idStr = outPutModel.getLanguage_id();
                    } else {
                        planLanguage_idStr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }
                    if (outPutModel.getPrice()!=null) {
                        planPriceStr = outPutModel.getPrice();
                    } else {
                        planPriceStr = languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA);

                    }
                    if (outPutModel.getTrial_period()!=null) {
                        currencyTrialPeriodStr = outPutModel.getTrial_period();
                    } else {
                        currencyTrialPeriodStr = "";
                    }

                    if (outPutModel.getTrial_recurrence()!=null) {
                        currencyTrialRecurrenceStr = outPutModel.getTrial_recurrence();
                    } else {
                        currencyTrialRecurrenceStr = "";
                    }

                    if (currency!=null) {
                        if (currency.getCurrencyId()!=null) {
                            currencyIdStr = currency.getCurrencyId();
                        } else {
                            currencyIdStr = "";

                        }
                        if (currency.getCurrencyCode()!=null){
                            currencyCountryCodeStr = currency.getCurrencyCode();
                        } else {
                            currencyCountryCodeStr = "";
                        }
                        if (currency.getCurrencySymbol()!=null){
                            currencySymbolStr = currency.getCurrencySymbol();
                        } else {
                            currencySymbolStr = "";
                        }

                    }
                    if (planStatusStr == 1) {
                        if (i == 0) {
                            planId = planIdStr;
                            movieList.add(new PlanModel(planNamestr, planPriceStr, planRecurrenceStr, planFrequencyStr, true, planStudioIdStr, planStatusStr, planLanguage_idStr, planIdStr, currencyIdStr, currencySymbolStr, currencyTrialPeriodStr, currencyTrialRecurrenceStr,currencyCountryCodeStr));

                        } else {
                            movieList.add(new PlanModel(planNamestr, planPriceStr, planRecurrenceStr, planFrequencyStr, false, planStudioIdStr, planStatusStr, planLanguage_idStr, planIdStr, currencyIdStr, currencySymbolStr,currencyTrialPeriodStr,currencyTrialRecurrenceStr,currencyCountryCodeStr));
                            LogUtil.showLog("MUVI","movieList"+movieList.size());
                        }
                    }
                }

                subcription.setVisibility(View.VISIBLE);
                subcription.setLayoutManager(mLayoutManager);
                subcription.setItemAnimator(new DefaultItemAnimator());

                mAdapter = new PlanAdapter(SubscriptionActivity.this,movieList);
                subcription.setAdapter(mAdapter);

                if ((featureHandler.getFeatureStatus(FeatureHandler.SIGNUP_STEP,FeatureHandler.DEFAULT_SIGNUP_STEP))) {
                    skipButton.setVisibility(View.VISIBLE);

                }
                else
                {
                    skipButton.setVisibility(View.GONE);
                }


                activation_plan.setVisibility(View.VISIBLE);
                subcription.addOnItemTouchListener(new VideosListFragment.RecyclerTouchListener(SubscriptionActivity.this, subcription, new VideosListFragment.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        selected_subscription_plan = position;
                        //Toast.makeText(getApplicationContext(),""+selected_subscription_plan,Toast.LENGTH_LONG).show();

                        if (position > 0) {
                            movieList.get(prevPosition).setSelected(false);
                            prevPosition = position;

                        } else if (position == 0 && prevPosition > position) {
                            movieList.get(prevPosition).setSelected(false);
                            prevPosition = position;

                        }
                        planId = movieList.get(position).getPlanIdStr();
                        movieList.get(position).setSelected(true);
                        mAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
            }
        }else{
            Util.showToast(SubscriptionActivity.this,languagePreference.getTextofLanguage(NO_DETAILS_AVAILABLE,DEFAULT_NO_DETAILS_AVAILABLE));
            onBackPressed();

        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }
}
