package com.home.vod.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.home.apisdk.apiController.DeleteInvoicePdfAsynTask;
import com.home.apisdk.apiController.GetInvoicePdfAsynTask;
import com.home.apisdk.apiController.PurchaseHistoryAsyntask;
import com.home.apisdk.apiController.TransactionDetailsAsynctask;
import com.home.apisdk.apiModel.DeleteInvoicePdfInputModel;
import com.home.apisdk.apiModel.DeleteInvoicePdfOutputModel;
import com.home.apisdk.apiModel.GetInvoicePdfInputModel;
import com.home.apisdk.apiModel.GetInvoicePdfOutputModel;
import com.home.apisdk.apiModel.PurchaseHistoryInputModel;
import com.home.apisdk.apiModel.PurchaseHistoryOutputModel;
import com.home.apisdk.apiModel.TransactionInputModel;
import com.home.apisdk.apiModel.TransactionOutputModel;
import com.home.vod.R;
import com.home.vod.network.NetworkStatus;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.ProgressBarHandler;
import com.home.vod.util.Util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.home.vod.preferences.LanguagePreference.AMOUNT;
import static com.home.vod.preferences.LanguagePreference.BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_AMOUNT;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_BUTTON_OK;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DOWNLOAD_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DOWNLOAD_COMPLETED;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_DOWNLOAD_INTERRUPTED;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_INVOICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_PDF;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_ORDER;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_PLAN_NAME;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANASCTION_DETAIL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_DATE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_ORDER_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_STATUS;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRY_AGAIN;
import static com.home.vod.preferences.LanguagePreference.DOWNLOAD_BUTTON_TITLE;
import static com.home.vod.preferences.LanguagePreference.DOWNLOAD_COMPLETED;
import static com.home.vod.preferences.LanguagePreference.DOWNLOAD_INTERRUPTED;
import static com.home.vod.preferences.LanguagePreference.INVOICE;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_CONNECTION;
import static com.home.vod.preferences.LanguagePreference.NO_INTERNET_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_PDF;
import static com.home.vod.preferences.LanguagePreference.ORDER;
import static com.home.vod.preferences.LanguagePreference.PLAN_NAME;
import static com.home.vod.preferences.LanguagePreference.SELECTED_LANGUAGE_CODE;
import static com.home.vod.preferences.LanguagePreference.TRANASCTION_DETAIL;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_DATE;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_ORDER_ID;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_STATUS;
import static com.home.vod.preferences.LanguagePreference.TRY_AGAIN;
import static com.home.vod.util.Constant.authTokenStr;


public class TransactionDetailsActivity extends AppCompatActivity implements
        DeleteInvoicePdfAsynTask.DeleteInvoicePdfListener,
        GetInvoicePdfAsynTask.GetInvoicePdfListener,TransactionDetailsAsynctask.TransactionListener{
    Toolbar mActionBarToolbar;
    TextView transactionTitleTextView;
    LinearLayout transactionDateLayout, transactionOrderLayout, transactionAmountLayout, transactionInvoiceLayout,
            transactionStatusLayout, transactionPLanNameLayout;
    TextView transactionDateTitleTextView, transactionOrderTitletextView, transactionAmountTitletextView, transactionInvoiceTitletextView,
            transactionStatusTitletextView, transactionPlanNameTitleTextView;
    TextView transactionDateTextView, transactionOrdertextView, transactionAmounttextView, transactionInvoicetextView,
            transactionStatustextView, transactionPlanNameTextView;
    Button transactionDownloadButton;

    int corePoolSize = 60;
    int maximumPoolSize = 80;
    int keepAliveTime = 10;
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
    Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    ProgressDialog progressDialog;
    ProgressDialog pDialog;
    String filename;
    int progress_bar_type = 0;
    int progressStatus = 0;
    String Download_Url;
    int downloadedSize = 0, totalsize;
    //float per = 0;

    String id, user_id;
    RelativeLayout noInternet;
    LinearLayout primary_layout;
    Button tryAgainButton;

    String TransactionDate, OredrId, Amount, Invoice, TransactionStatus, PlanName;

    String download_Url="";
    boolean deletion_success = false;
    AlertDialog msgAlert;
    private String Currency_symbol;
    private String currency_code;
    static File mediaStorageDir;
    TextView no_internet_text;
    ProgressBarHandler Ph;
    LanguagePreference languagePreference;
    DownloadFileFromURL downloadFileFromURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        languagePreference = LanguagePreference.getLanguagePreference(this);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mActionBarToolbar.setTitle("");


        noInternet = (RelativeLayout) findViewById(R.id.noInternet);
        primary_layout = (LinearLayout) findViewById(R.id.primary_layout);
        tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
        no_internet_text = (TextView) findViewById(R.id.no_internet_text);

        no_internet_text.setText(languagePreference.getTextofLanguage(NO_INTERNET_NO_DATA,DEFAULT_NO_INTERNET_NO_DATA));
        tryAgainButton.setText(languagePreference.getTextofLanguage(TRY_AGAIN,DEFAULT_TRY_AGAIN));


        transactionDateLayout = (LinearLayout) findViewById(R.id.transactionDateLayout);
        transactionOrderLayout = (LinearLayout) findViewById(R.id.transactionOrderLayout);
        transactionAmountLayout = (LinearLayout) findViewById(R.id.transactionAmountLayout);
        transactionInvoiceLayout = (LinearLayout) findViewById(R.id.transactionInvoiceLayout);
        transactionStatusLayout = (LinearLayout) findViewById(R.id.transactionStatusLayout);
        transactionPLanNameLayout = (LinearLayout) findViewById(R.id.transactionPLanNameLayout);

        transactionDateTitleTextView = (TextView) findViewById(R.id.transactionDateTitleTextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionDateTitleTextView);

        transactionDateTitleTextView.setText(languagePreference.getTextofLanguage(TRANSACTION_DATE, DEFAULT_TRANSACTION_DATE) + " :");

        transactionOrderTitletextView = (TextView) findViewById(R.id.transactionOrderTitletextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionOrderTitletextView);
        transactionOrderTitletextView.setText(languagePreference.getTextofLanguage(TRANSACTION_ORDER_ID, DEFAULT_TRANSACTION_ORDER_ID) + " :");

        transactionAmountTitletextView = (TextView) findViewById(R.id.transactionAmountTitletextView);

        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionAmountTitletextView);

        transactionAmountTitletextView.setText(languagePreference.getTextofLanguage(AMOUNT, DEFAULT_AMOUNT) + " :");


        transactionInvoiceTitletextView = (TextView) findViewById(R.id.transactionInvoiceTitletextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionInvoiceTitletextView);

        transactionInvoiceTitletextView.setText(languagePreference.getTextofLanguage(INVOICE, DEFAULT_INVOICE) + " :");

        transactionStatusTitletextView = (TextView) findViewById(R.id.transactionStatusTitletextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionStatusTitletextView);


        transactionStatusTitletextView.setText(languagePreference.getTextofLanguage(TRANSACTION_STATUS, DEFAULT_TRANSACTION_STATUS) + " :");

        transactionPlanNameTitleTextView = (TextView) findViewById(R.id.transactionPlanNameTitleTextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionPlanNameTitleTextView);

        transactionPlanNameTitleTextView.setText(languagePreference.getTextofLanguage(PLAN_NAME, DEFAULT_PLAN_NAME) + " :");

        transactionTitleTextView = (TextView) findViewById(R.id.transactionTitleTextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionTitleTextView);

        transactionTitleTextView.setText(languagePreference.getTextofLanguage(TRANASCTION_DETAIL, DEFAULT_TRANASCTION_DETAIL));

        transactionDownloadButton = (Button) findViewById(R.id.transactionDownloadButton);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.regular_fonts),transactionDownloadButton);

        transactionDownloadButton.setText(languagePreference.getTextofLanguage(DOWNLOAD_BUTTON_TITLE, DEFAULT_DOWNLOAD_BUTTON_TITLE));

        transactionDateTextView = (TextView) findViewById(R.id.transactionDateTextView);
        transactionOrdertextView = (TextView) findViewById(R.id.transactionOrdertextView);
        transactionAmounttextView = (TextView) findViewById(R.id.transactionAmounttextView);
        transactionInvoicetextView = (TextView) findViewById(R.id.transactionInvoicetextView);
        transactionStatustextView = (TextView) findViewById(R.id.transactionStatustextView);
        transactionPlanNameTextView = (TextView) findViewById(R.id.transactionPlanNameTextView);

        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionDateTextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionOrdertextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionAmounttextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionInvoicetextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionStatustextView);
        FontUtls.loadFont(TransactionDetailsActivity.this, getResources().getString(R.string.light_fonts),transactionPlanNameTextView);


        id = getIntent().getStringExtra("id");
        user_id = getIntent().getStringExtra("user_id");

        // Calling Api To get Transaction Details.

        if (NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this))
            GetTransactionDetails();
        else {
            noInternet.setVisibility(View.VISIBLE);
            primary_layout.setVisibility(View.GONE);
        }

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this))
                    GetTransactionDetails();
                else {
                    noInternet.setVisibility(View.VISIBLE);
                    primary_layout.setVisibility(View.GONE);
                }
            }
        });

        transactionDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(TransactionDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(TransactionDetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(TransactionDetailsActivity.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                                111);
                    } else {
                        ActivityCompat.requestPermissions(TransactionDetailsActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                111);
                    }
                } else {
                    //Call whatever you want
                    if (NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this)) {
                        getPdfDetail();
                    } else {
                        Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }

    public void DownloadTransactionDetails() {

        registerReceiver(InternetStatus, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        downloadFileFromURL=new DownloadFileFromURL();
        downloadFileFromURL.execute(Util.Dwonload_pdf_rootUrl + download_Url);

        LogUtil.showLog("MUVI", "Url=" + Util.Dwonload_pdf_rootUrl + download_Url);

    }

    private BroadcastReceiver InternetStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            //  Toast.makeText(getApplicationContext(),""+NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this),Toast.LENGTH_SHORT).show();
            if (!NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this)) {
                if (pDialog.isShowing() && pDialog != null) {

                    showDialog(languagePreference.getTextofLanguage(DOWNLOAD_INTERRUPTED, DEFAULT_DOWNLOAD_INTERRUPTED), 0);
                    pDialog.setProgress(0);
                    progressStatus = 0;
                    dismissDialog(progress_bar_type);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(InternetStatus);
        }catch (Exception e) {
        }
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(InternetStatus);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(InternetStatus);
    }

    @Override
    public void onDeleteInvoicePdfPreExecuteStarted() {
        Ph = new ProgressBarHandler(TransactionDetailsActivity.this);
        Ph.show();
    }

    @Override
    public void onDeleteInvoicePdfPostExecuteCompleted(DeleteInvoicePdfOutputModel deleteInvoicePdfOutputModel, int code, String message, String status) {
        try {
            if (Ph.isShowing())
                Ph.hide();
        } catch (IllegalArgumentException ex) {

            deletion_success = false;
        }
        if (status == null)
            deletion_success = false;

        if (deletion_success) {
            // Do whatever u want to do
            finish();
        }
    }



    @Override
    public void onTransactionPreExecuteStarted() {
        Ph = new ProgressBarHandler(TransactionDetailsActivity.this);
        Ph.show();
    }

    @Override
    public void onTransactionPostExecuteCompleted(TransactionOutputModel transactionOutputModel, int status, String message) {

        if (Ph.isShowing() && Ph!=null)
            Ph.hide();

        if (status == 200) {
            primary_layout.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);

            transactionDateTextView.setText(transactionOutputModel.getTransaction_date());
            transactionOrdertextView.setText(transactionOutputModel.getOrder_number());
            transactionAmounttextView.setText(transactionOutputModel.getAmount());
            transactionInvoicetextView.setText(transactionOutputModel.getInvoice_id());
            transactionStatustextView.setText(transactionOutputModel.getTransaction_status());
            transactionPlanNameTextView.setText(transactionOutputModel.getPlan_name());


          /*  GetInvoicePdfInputModel getInvoicePdfInputModel = new GetInvoicePdfInputModel();
            getInvoicePdfInputModel.setAuthToken(authTokenStr);
            getInvoicePdfInputModel.setUser_id(user_id);
            getInvoicePdfInputModel.setId(id);
            getInvoicePdfInputModel.setDevice_type("app");
            getInvoicePdfInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
            GetInvoicePdfAsynTask downloadDocumentDetails = new GetInvoicePdfAsynTask(getInvoicePdfInputModel, this, this);
            downloadDocumentDetails.executeOnExecutor(threadPoolExecutor);*/
        } else {
            primary_layout.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
    }
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
//                pDialog.cancel();
                showDialog(progress_bar_type);
            }catch (Exception e){showDialog(progress_bar_type);
            }
        }

        /**
         * Downloading file in background thread
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected String doInBackground(String... f_url) {
            int count;


            File file = null;
            try {
                pDialog.setProgress(0);
                URL url = new URL(f_url[0]);
                String dwnload_file_path = f_url[0];
                Download_Url = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1);
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);

                // connect
                urlConnection.connect();

                // set the path where we want to save the file
                File SDCardRoot =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                // create a new file, to save the downloaded file
                file = new File(SDCardRoot, Download_Url);

                FileOutputStream fileOutput = new FileOutputStream(file);

                // Stream used for reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                // this is the total size of the file which we are
                // downloading
                totalsize = urlConnection.getContentLength();
                //setText("Starting PDF download...");

                // create a buffer...
                byte[] buffer = new byte[1024 * 1024];
                int bufferLength = 0;
                float per = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    per = ((float) downloadedSize / totalsize) * 100;
                    pDialog.setProgress((int) per);

                }
                // close the output stream when complete //
                fileOutput.close();
                urlConnection.disconnect();
                //setText("Download Complete. Open PDF Application installed in the device.");
                runOnUiThread(new Runnable() {
                    public void run() {
                        pDialog.dismiss(); // if you want close it..

                        showDialog(languagePreference.getTextofLanguage(DOWNLOAD_COMPLETED,DEFAULT_DOWNLOAD_COMPLETED), 1);
                    }
                });
            }


           catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));

            if ((Integer.parseInt(progress[0])) == 100) {

                showDialog(languagePreference.getTextofLanguage(DOWNLOAD_COMPLETED, DEFAULT_DOWNLOAD_COMPLETED), 1);


                pDialog.setProgress(0);
                progressStatus = 0;
                dismissDialog(progress_bar_type);

                //Calling Api To Delete Pdf file from the Server.


            }

        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded

            try {
                downloadFileFromURL.cancel(true);
            }catch (Exception e){

            }
            LogUtil.showLog("MUVI", "Download Completed");

        }
    }


    //Asyntask to get Transaction Details.

//    private class Deletepdf extends AsyncTask<Void, Void, Void> {
//
//        String responseStr = "";
//        int status;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.DeleteInvoicePath.trim());//hv to cahnge
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr);
//                httppost.addHeader("filepath", download_Url);
//                httppost.addHeader("lang_code",Util.getTextofLanguage(TransactionDetailsActivity.this,Util.SELECTED_LANGUAGE_CODE,Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                    LogUtil.showLog("MUVI", "responseStr Delete Invoice Path=" + responseStr);
//                } catch (Exception e) {
//
//                }
//
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                }
//                if (status > 0) {
//                    if (status == 200) {
//
//                        deletion_success = true;
//
//                    } else {
//                        deletion_success = false;
//                    }
//                } else {
//                    deletion_success = false;
//
//                }
//            } catch (final JSONException e1) {
//                deletion_success = false;
//            } catch (Exception e) {
//                deletion_success = false;
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void result) {
//
//            try {
//                if (Ph.isShowing())
//                    Ph.hide();
//            } catch (IllegalArgumentException ex) {
//
//                deletion_success = false;
//            }
//            if (responseStr == null)
//                deletion_success = false;
//
//            if (deletion_success) {
//                // Do whatever u want to do
//                finish();
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            Ph = new ProgressBarHandler(TransactionDetailsActivity.this);
//            Ph.show();
//
//        }
//    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
                progressStatus = 0;
                return pDialog;
            default:
                return null;
        }
    }


    public void GetTransactionDetails() {
        noInternet.setVisibility(View.GONE);
        primary_layout.setVisibility(View.VISIBLE);
        TransactionInputModel transactionInputModel=new TransactionInputModel();
        transactionInputModel.setAuthToken(authTokenStr);
        transactionInputModel.setLanguage(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        transactionInputModel.setUser_id(user_id);
        transactionInputModel.setId(id);
        TransactionDetailsAsynctask asynGetTransactionDetails = new TransactionDetailsAsynctask(transactionInputModel,this,this);
        asynGetTransactionDetails.executeOnExecutor(threadPoolExecutor);
    }


    //Asyntask to get Transaction Details.

//    private class AsynGetTransactionDetails extends AsyncTask<Void, Void, Void> {
//
//        String responseStr = "";
//        int status;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.PurchaseHistoryDetails.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr);
//                httppost.addHeader("user_id", user_id);
//                httppost.addHeader("id", id);
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//
//                    LogUtil.showLog("MUVI", "responseStr transcation Details=" + responseStr);
//                } catch (Exception e) {
//
//                }
//
//                JSONObject myJson = null;
//                JSONObject myJson1 = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                    myJson1 = myJson.getJSONObject("section");
//
////                    TransactionDate,OredrId,Amount,Invoice,TransactionStatus,PlanName;
//                }
//                if (status > 0) {
//                    if (status == 200) {
//
//
//                        OredrId = myJson1.optString("order_number");
//                        if (OredrId.equals("") || OredrId == null || OredrId.equals("null"))
//                            OredrId = "";
//
//                        Invoice = myJson1.optString("invoice_id");
//                        if (Invoice.equals("") || Invoice == null || Invoice.equals("null"))
//                            Invoice = "";
//
//                        TransactionDate = myJson1.optString("transaction_date");
//                        if (TransactionDate.equals("") || TransactionDate == null || TransactionDate.equals("null"))
//                            TransactionDate = "";
//
//
//                        if (!TransactionDate.equals("")) {
//                            String date = TransactionDate.trim();
//                            SimpleDateFormat spf = new SimpleDateFormat("MMMM dd,yyyy hh:mm:ss aaa");
//                            Date newDate = spf.parse(date);
//                            spf = new SimpleDateFormat("MMMM dd,yyyy");
//                            date = spf.format(newDate);
//                            LogUtil.showLog("MUVI", "Transaction Date = " + date);
//                        }
//
//
//                        TransactionStatus = myJson1.optString("transaction_status");
//                        if (TransactionStatus.equals("") || TransactionStatus == null || TransactionStatus.equals("null"))
//                            TransactionStatus = "";
//
//                        PlanName = myJson1.optString("plan_name");
//                        if (PlanName.equals("") || PlanName == null || PlanName.equals("null"))
//                            PlanName = "";
//
//                        Currency_symbol = myJson1.optString("currency_symbol");
//                        if (Currency_symbol.equals("") || Currency_symbol == null || Currency_symbol.equals("null"))
//                            Currency_symbol = "";
//
//                        LogUtil.showLog("MUVI", "currency_symbol = " + Currency_symbol);
//
//                        currency_code = myJson1.optString("currency_code");
//                        if (currency_code.equals("") || currency_code == null || currency_code.equals("null"))
//                            currency_code = "";
//
//                        LogUtil.showLog("MUVI", "currency_code = " + currency_code);
//
//
//                        Amount = myJson1.optString("amount");
//                        if (Amount.equals("") || Amount == null || Amount.equals("null"))
//                            Amount = "";
//                        else {
//
//                            if (Currency_symbol.equals("") || Currency_symbol == null || Currency_symbol.trim().equals(null)) {
//                                Amount = currency_code + " " + Amount;
//                            } else {
//                                Amount = Currency_symbol + " " + Amount;
//                            }
//                        }
//
//                        LogUtil.showLog("MUVI", "amount" + Amount);
//
//
//                    } else {
//                        responseStr = "0";
//                    }
//                } else {
//                    responseStr = "0";
//
//                }
//            } catch (final JSONException e1) {
//                responseStr = "0";
//            } catch (Exception e) {
//                responseStr = "0";
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
//
//          /*  try{
//                if( progressDialog.isShowing())
//                    progressDialog.dismiss();
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
//            }*/
//            if (responseStr == null)
//                responseStr = "0";
//
//            if ((responseStr.trim().equals("0"))) {
//                primary_layout.setVisibility(View.GONE);
//                noInternet.setVisibility(View.VISIBLE);
//
//                if (Ph.isShowing())
//                    Ph.hide();
//
//            } else {
//                transactionDateTextView.setText(TransactionDate);
//                transactionOrdertextView.setText(OredrId);
//                transactionAmounttextView.setText(Amount);
//                transactionInvoicetextView.setText(Invoice);
//                transactionStatustextView.setText(TransactionStatus);
//                transactionPlanNameTextView.setText(PlanName);
//
//                DownloadDocumentDetails downloadDocumentDetails = new DownloadDocumentDetails();
//                downloadDocumentDetails.executeOnExecutor(threadPoolExecutor);
//
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            Ph = new ProgressBarHandler(TransactionDetailsActivity.this);
//            Ph.show();
//        }
//    }


    //Asyntask to get Transaction Details.
    @Override
    public void onGetInvoicePdfPreExecuteStarted() {

        Ph = new ProgressBarHandler(TransactionDetailsActivity.this);
        Ph.show();
    }

    @Override
    public void onGetInvoicePdfPostExecuteCompleted(GetInvoicePdfOutputModel getInvoicePdfOutputModel, int code, String message, String status) {

        if (Ph.isShowing())
            Ph.hide();

        if (!getInvoicePdfOutputModel.getSection().equals("")) {
            download_Url = getInvoicePdfOutputModel.getSection();
            DownloadTransactionDetails();
        } else
            Util.showToast(getApplicationContext(),languagePreference.getTextofLanguage(NO_PDF,DEFAULT_NO_PDF));

    }
//    private class DownloadDocumentDetails extends AsyncTask<Void, Void, Void> {
//
//        String responseStr = "";
//        int status;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(Util.rootUrl().trim() + Util.GetInvoicePDF.trim());
//                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
//                httppost.addHeader("authToken", Util.authTokenStr);
//                httppost.addHeader("user_id", user_id);
//                httppost.addHeader("id", id);
//                httppost.addHeader("device_type", "app");
//                httppost.addHeader("lang_code", languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, Util.DEFAULT_SELECTED_LANGUAGE_CODE));
//
//
//                // Execute HTTP Post Request
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    responseStr = EntityUtils.toString(response.getEntity());
//                    LogUtil.showLog("MUVI", "responseStr getpdf Details=" + responseStr);
//                } catch (Exception e) {
//
//                }
//
//                JSONObject myJson = null;
//                if (responseStr != null) {
//                    myJson = new JSONObject(responseStr);
//                    status = Integer.parseInt(myJson.optString("code"));
//                }
//                if (status > 0) {
//                    if (status == 200) {
//
//
//                        download_Url = myJson.optString("section");
//                        if (download_Url.equals("") || download_Url == null || download_Url.equals("null")) {
//
//                            download_Url = "";
//                        }
//                    } else {
//                        responseStr = "0";
//                    }
//                } else {
//                    responseStr = "0";
//
//                }
//            } catch (final JSONException e1) {
//                responseStr = "0";
//            } catch (Exception e) {
//                responseStr = "0";
//            }
//            return null;
//
//        }
//
//        protected void onPostExecute(Void result) {
//
//            try {
//                if (Ph.isShowing())
//                    Ph.hide();
//            } catch (IllegalArgumentException ex) {
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
//            if (responseStr == null)
//                responseStr = "0";
//
//            if ((responseStr.trim().equals("0"))) {
//                primary_layout.setVisibility(View.GONE);
//                noInternet.setVisibility(View.VISIBLE);
//            } else {
//
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            /*progressDialog = new ProgressDialog(TransactionDetailsActivity.this,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
//            progressDialog.setIndeterminate(false);
//            progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_rawable));
//            progressDialog.show()*/
//            ;
//        }
//    }

    public void showDialog(String msg, final int deletevalue) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(TransactionDetailsActivity.this);
        dlgAlert.setMessage(msg);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK), null);
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton(languagePreference.getTextofLanguage(BUTTON_OK, DEFAULT_BUTTON_OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (deletevalue == 1) {
                            /*Delete download url from server */
                            DeleteInvoicePdfInputModel deleteInvoicePdfInputModel = new DeleteInvoicePdfInputModel();
                            deleteInvoicePdfInputModel.setAuthToken(authTokenStr);
                            deleteInvoicePdfInputModel.setFilepath(download_Url);
                            deleteInvoicePdfInputModel.setLanguage_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
                            DeleteInvoicePdfAsynTask deletepdf = new DeleteInvoicePdfAsynTask(deleteInvoicePdfInputModel, TransactionDetailsActivity.this, TransactionDetailsActivity.this);
                            deletepdf.executeOnExecutor(threadPoolExecutor);
                        }
                    }
                });
        dlgAlert.create();
        msgAlert = dlgAlert.show();
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111: {

                if (grantResults.length > 0) {
                    if ((grantResults.length > 0) && (grantResults[0]) == PackageManager.PERMISSION_GRANTED) {
                        //Call whatever you want
                        if (NetworkStatus.getInstance().isConnected(TransactionDetailsActivity.this)) {
                            getPdfDetail();
                        } else {
                            Toast.makeText(getApplicationContext(), languagePreference.getTextofLanguage(NO_INTERNET_CONNECTION, DEFAULT_NO_INTERNET_CONNECTION), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }

                return;
            }
        }
    }

    public void getPdfDetail(){
        GetInvoicePdfInputModel getInvoicePdfInputModel = new GetInvoicePdfInputModel();
        getInvoicePdfInputModel.setAuthToken(authTokenStr);
        getInvoicePdfInputModel.setUser_id(user_id);
        getInvoicePdfInputModel.setId(id);
        getInvoicePdfInputModel.setDevice_type("app");
        getInvoicePdfInputModel.setLang_code(languagePreference.getTextofLanguage(SELECTED_LANGUAGE_CODE, DEFAULT_SELECTED_LANGUAGE_CODE));
        GetInvoicePdfAsynTask downloadDocumentDetails = new GetInvoicePdfAsynTask(getInvoicePdfInputModel, TransactionDetailsActivity.this, TransactionDetailsActivity.this);
        downloadDocumentDetails.executeOnExecutor(threadPoolExecutor);
    }
}
