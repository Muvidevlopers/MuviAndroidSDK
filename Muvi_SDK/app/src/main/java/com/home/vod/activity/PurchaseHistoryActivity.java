package com.home.vod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.home.apisdk.apiController.PurchaseHistoryAsyntask;
import com.home.apisdk.apiModel.PurchaseHistoryInputModel;
import com.home.apisdk.apiModel.PurchaseHistoryOutputModel;
import com.home.vod.R;
import com.home.vod.adapter.PurchaseHistoryAdapter;
import com.home.vod.model.PurchaseHistoryModel;
import com.home.vod.model.RecyclerItemClickListener;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRY_AGAIN;
import static com.home.vod.preferences.LanguagePreference.MY_FAVOURITE;
import static com.home.vod.preferences.LanguagePreference.NO;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.PURCHASE;
import static com.home.vod.preferences.LanguagePreference.PURCHASE_HISTORY;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.TRY_AGAIN;
import static com.home.vod.util.Constant.authTokenStr;

public class PurchaseHistoryActivity extends AppCompatActivity implements
        PurchaseHistoryAsyntask.PurchaseHistoryListener {
    Toolbar mActionBarToolbar;
    RecyclerView recyclerView;
    ArrayList<PurchaseHistoryModel> purchaseData = new ArrayList<PurchaseHistoryModel>();
    LinearLayout primary_layout;
    Button tryAgainButton;
    RelativeLayout noInternet;
    RelativeLayout noData;
    boolean isNetwork;
    ProgressBarHandler pDialog;
    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    LanguagePreference languagePreference;
    String Invoice, Id, PutrcahseDate, TranactionStatus, Ppvstatus, Amount;
    private String Currency_symbol = "";
    private String currency_code = "";
    String user_id = "";
    TextView purchaseHistoryTitleTextView, no_internet_text, noDataTextView;
    PreferenceManager preferenceManager;
    PurchaseHistoryModel purchaseHistoryModel;
    ArrayList<String> Id_Purchase_History;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        languagePreference = LanguagePreference.getLanguagePreference(PurchaseHistoryActivity.this);
        noInternet = (RelativeLayout) findViewById(R.id.noInternet);
        primary_layout = (LinearLayout) findViewById(R.id.primary_layout);
        noData = (RelativeLayout) findViewById(R.id.noData);
        noDataTextView = (TextView) findViewById(R.id.noDataTextView);
        tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
        no_internet_text = (TextView) findViewById(R.id.no_internet_text);
        recyclerView = (RecyclerView) findViewById(R.id.purchase_history_recyclerview);
       // purchaseHistoryTitleTextView = (TextView) findViewById(R.id.purchaseHistoryTitleTextView);

        no_internet_text.setText(languagePreference.getTextofLanguage(NO_INTERNET_NO_DATA, DEFAULT_NO_INTERNET_NO_DATA));
        tryAgainButton.setText(languagePreference.getTextofLanguage(TRY_AGAIN, DEFAULT_TRY_AGAIN));

        preferenceManager = PreferenceManager.getPreferenceManager(this);
        user_id = preferenceManager.getUseridFromPref();
       // FontUtls.loadFont(PurchaseHistoryActivity.this, getResources().getString(R.string.regular_fonts), purchaseHistoryTitleTextView);

      //  purchaseHistoryTitleTextView.setText(languagePreference.getTextofLanguage(PURCHASE_HISTORY, DEFAULT_PURCHASE_HISTORY));


        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(languagePreference.getTextofLanguage(PURCHASE_HISTORY,DEFAULT_PURCHASE_HISTORY));
        mActionBarToolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.getInstance().isConnected(PurchaseHistoryActivity.this))
                    GetPurchaseHistoryDetails();
                else {
                    noInternet.setVisibility(View.VISIBLE);
                    primary_layout.setVisibility(View.GONE);
                }
            }
        });

        GetPurchaseHistoryDetails();


     /*   for(int i = 0 ;i<20 ;i++)
        {
            PurchaseHistoryModel purchaseHistoryModel = new PurchaseHistoryModel
                    ("Invoie Data","Order "+i,"12-10-20","Success","$299","Active");
            purchaseData.add(purchaseHistoryModel);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        PurchaseHistoryAdapter purchaseHistoryAdapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this,purchaseData);
        recyclerView.setAdapter(purchaseHistoryAdapter);*/

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(PurchaseHistoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        final Intent detailsIntent = new Intent(PurchaseHistoryActivity.this, TransactionDetailsActivity.class);

                        detailsIntent.putExtra("id", Id_Purchase_History.get(position));
                        detailsIntent.putExtra("user_id", user_id);

                        LogUtil.showLog("MUVI", "ID = " + Id_Purchase_History.get(position));
                        LogUtil.showLog("MUVI", "user_id = " + user_id);

                        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(detailsIntent);
                    }
                })
        );

    }


    public void GetPurchaseHistoryDetails() {
        noInternet.setVisibility(View.GONE);
        primary_layout.setVisibility(View.VISIBLE);
        PurchaseHistoryInputModel purchaseHistoryInputModel = new PurchaseHistoryInputModel();
        purchaseHistoryInputModel.setUser_id(user_id);
        purchaseHistoryInputModel.setAuthToken(authTokenStr);
        purchaseHistoryInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        PurchaseHistoryAsyntask asynGetPurchaseDetail = new PurchaseHistoryAsyntask(purchaseHistoryInputModel, this, this);
        asynGetPurchaseDetail.executeOnExecutor(threadPoolExecutor);
    }

    @Override
    public void onPurchaseHistoryPreExecuteStarted() {
        pDialog = new ProgressBarHandler(PurchaseHistoryActivity.this);
        pDialog.show();
    }

    @Override
    public void onPurchaseHistoryPostExecuteCompleted(ArrayList<PurchaseHistoryOutputModel> purchaseHistoryOutputModel, int status) {
        try {
            if (pDialog.isShowing())
                pDialog.hide();
        } catch (IllegalArgumentException ex) {
        }
        Id_Purchase_History = new ArrayList<>();
        if (status > 0) {
            if (status == 200) {

                if (purchaseHistoryOutputModel != null && purchaseHistoryOutputModel.size() > 0) {
                    for (PurchaseHistoryOutputModel model : purchaseHistoryOutputModel
                            ) {

                        purchaseData.add(new PurchaseHistoryModel(model.getInvoice_id(), model.getId(), model.getTransaction_date(), model.getTransaction_status(), model.getAmount(), model.getStatusppv()));
                        Id_Purchase_History.add(model.getId());
                    }
                }

                if (purchaseData.size() > 0) {

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    PurchaseHistoryAdapter purchaseHistoryAdapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this, purchaseData);
                    recyclerView.setAdapter(purchaseHistoryAdapter);

                } else {
                    primary_layout.setVisibility(View.GONE);

                    noData.setVisibility(View.VISIBLE);
                    noDataTextView.setText(languagePreference.getTextofLanguage(NO_PURCHASE_HISTORY, DEFAULT_NO_PURCHASE_HISTORY));

                    Log.v("pratik", "noDataText==" + languagePreference.getTextofLanguage(NO_PURCHASE_HISTORY, DEFAULT_NO_PURCHASE_HISTORY));

                }
            }

        } else {
            primary_layout.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);

        }


    }

    //Asyntask for getDetails of the csat and crew members.

//    private class AsynGetPurchaseDetails extends AsyncTask<Void, Void, Void> {
//        ProgressBarHandler pDialog;
//        String responseStr = "";
//        int status;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient=new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim()+Util.PurchaseHistory.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken",Util.authTokenStr);
//                httppost.addHeader("user_id",user_id);
//                httppost.addHeader("lang_code",languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                } catch (Exception e){
//
//                }
//
//                JSONObject myJson =null;
//                if(responseStr!=null){
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                }
//                if (status > 0) {
//                    if (status == 200) {
//                        Id_Purchase_History = new ArrayList<>();
//                        JSONArray jsonArray = myJson.getJSONArray("section");
//                        for(int i=0 ;i<jsonArray.length();i++)
//                        {
//                            Invoice = jsonArray.getJSONObject(i).optString("invoice_id");
//                            if(Invoice.equals("") || Invoice==null || Invoice.equals("null"))
//                                Invoice = "";
//                            Id = jsonArray.getJSONObject(i).optString("id");
//                            if(Id.equals("") || Id==null || Id.equals("null"))
//                                Id = "";
//
//                            Id_Purchase_History.add(Id);
//                            LogUtil.showLog("MUVI","ID =========================== "+Id);
//
//                            PutrcahseDate = jsonArray.getJSONObject(i).optString("transaction_date");
//                            if(PutrcahseDate.equals("") || PutrcahseDate==null || PutrcahseDate.equals("null"))
//                                PutrcahseDate = "";
//
//                            TranactionStatus = jsonArray.getJSONObject(i).optString("transaction_status");
//                            if(TranactionStatus.equals("") || TranactionStatus==null || TranactionStatus.equals("null"))
//                                TranactionStatus = "";
//
//                            Ppvstatus = jsonArray.getJSONObject(i).optString("statusppv");
//                            if(Ppvstatus.equals("") || Ppvstatus==null || Ppvstatus.equals("null"))
//                                Ppvstatus = "";
//
//                            Currency_symbol = (jsonArray.getJSONObject(i).optString("currency_symbol")).trim();
//                            if(Currency_symbol.equals("") || Currency_symbol==null || Currency_symbol.equals("null"))
//                                Currency_symbol = "";
//
//                            LogUtil.showLog("MUVI","currency_symbol = "+Currency_symbol);
//
//                            currency_code = jsonArray.getJSONObject(i).optString("currency_code");
//                            if(currency_code.equals("") || currency_code==null || currency_code.equals("null"))
//                                currency_code = "";
//
//                            LogUtil.showLog("MUVI","currency_code = "+currency_code);
//
//
//                            Amount = jsonArray.getJSONObject(i).optString("amount");
//                            if(Amount.equals("") || Amount==null || Amount.equals("null"))
//                                Amount = "";
//                            else{
//
//                                if(Currency_symbol.equals("") || Currency_symbol==null || Currency_symbol.trim().equals(null))
//                                {
//                                    Amount = currency_code+ " "+ Amount;
//                                }
//                                else
//                                {
//                                    Amount = Currency_symbol+ " "+ Amount;
//                                }
//                            }
//
//                            LogUtil.showLog("MUVI","amount"+ Amount);
//
//
//                            purchaseHistoryModel = new PurchaseHistoryModel(Invoice,Id,PutrcahseDate,TranactionStatus,Amount,Ppvstatus);
//                            purchaseData.add(purchaseHistoryModel);
//
//                        }
//
//
//
//                    }else{  responseStr = "0";}
//                }
//                else{
//                    responseStr = "0";
//
//                }
//            } catch (final JSONException e1) {
//                responseStr = "0";
//            }
//            catch (Exception e)
//            {
//                responseStr = "0";
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
//
//            try{
//                if(pDialog.isShowing())
//                    pDialog.hide();
//            }
//            catch(IllegalArgumentException ex)
//            {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        primary_layout.setVisibility(View.GONE);
//                        noInternet.setVisibility(View.VISIBLE);
//
//                    }
//
//                });
//                responseStr = "0";
//            }
//            if(responseStr == null)
//                responseStr = "0";
//
//            if((responseStr.trim().equals("0"))){
//                primary_layout.setVisibility(View.GONE);
//                noInternet.setVisibility(View.VISIBLE);
//            }else{
//                // Set the recycler adapter here.
//
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                recyclerView.setLayoutManager(layoutManager);
//                PurchaseHistoryAdapter purchaseHistoryAdapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this,purchaseData);
//                recyclerView.setAdapter(purchaseHistoryAdapter);
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            pDialog = new ProgressBarHandler(PurchaseHistoryActivity.this);
//            pDialog.show();
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }
}
