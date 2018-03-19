package com.home.vod.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.home.vod.R;
import com.home.vod.model.PurchaseHistoryModel;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FontUtls;
import com.home.vod.util.Util;

import java.util.ArrayList;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_INVOICE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_DETAIL_PURCHASE_DATE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_ORDER_ID;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_TRANSACTION_TITLE;
import static com.home.vod.preferences.LanguagePreference.INVOICE;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_DETAIL_PURCHASE_DATE;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_ORDER_ID;
import static com.home.vod.preferences.LanguagePreference.TRANSACTION_TITLE;


public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder>{
    Context context;
    ArrayList<PurchaseHistoryModel> purchaseData;
    LanguagePreference languagePreference;


    public PurchaseHistoryAdapter(Context context, ArrayList<PurchaseHistoryModel> purchaseData) {
        this.context = context;
        this.purchaseData = purchaseData;
        languagePreference =  LanguagePreference.getLanguagePreference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_purchase_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int POSITION = position;

        FontUtls.loadFont(context, context.getResources().getString(R.string.regular_fonts), holder.transactionTitleTextView);
/*
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        holder.transactionTitleTextView.setTypeface(typeface);*/
        holder.transactionTitleTextView.setText(languagePreference.getTextofLanguage(TRANSACTION_TITLE, DEFAULT_TRANSACTION_TITLE));
        FontUtls.loadFont(context, context.getResources().getString(R.string.regular_fonts), holder.transactionInvoiceTitleTextView);
/*
        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        holder.transactionInvoiceTitleTextView.setTypeface(typeface1);*/
        holder.transactionInvoiceTitleTextView.setText(languagePreference.getTextofLanguage(INVOICE, DEFAULT_INVOICE) + " :");
        FontUtls.loadFont(context, context.getResources().getString(R.string.regular_fonts), holder.transactionOrderTitleTextView);

       /* Typeface typeface2 = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        holder.transactionOrderTitleTextView.setTypeface(typeface2);*/
        holder.transactionOrderTitleTextView.setText(languagePreference.getTextofLanguage(TRANSACTION_ORDER_ID, DEFAULT_TRANSACTION_ORDER_ID) + " :");
        FontUtls.loadFont(context, context.getResources().getString(R.string.regular_fonts), holder.transactionPurchaseDateTitleTextView);

       /* Typeface typeface3 = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
        holder.transactionPurchaseDateTitleTextView.setTypeface(typeface3);*/
        holder.transactionPurchaseDateTitleTextView.setText(languagePreference.getTextofLanguage(TRANSACTION_DETAIL_PURCHASE_DATE, DEFAULT_TRANSACTION_DETAIL_PURCHASE_DATE) + " :");

        if(purchaseData.get(position).getTransctionActiveInactive()!=null){
            if ((purchaseData.get(position).getTransctionActiveInactive().contains("Active")) || (purchaseData.get(position).getTransctionActiveInactive().contains("active"))) {
                holder.activeAlertTextView.setTextColor(Color.parseColor("#197b30"));
                holder.activeAlertTextView.setText(purchaseData.get(position).getTransctionActiveInactive());
            } else {
                if (purchaseData.get(position).getTransctionActiveInactive().contains("N/A")) {
                    holder.activeAlertTextView.setText("Expired");
                } else {
                    holder.activeAlertTextView.setText(purchaseData.get(position).getTransctionActiveInactive());
                }

                holder.activeAlertTextView.setTextColor(Color.parseColor("#737373"));
            }
        }

        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.transactionInvoicetextView);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.transactionOrderTextView);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.transactionPurchaseDateTextView);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.showPriceTextView);
        FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.successTextView);

      /*  holder.transactionInvoicetextView.setTypeface(typeface3);
        holder.transactionOrderTextView.setTypeface(typeface3);
        holder.transactionPurchaseDateTextView.setTypeface(typeface3);
        holder.showPriceTextView.setTypeface(typeface3);
        holder.successTextView.setTypeface(typeface3);*/

        holder.transactionInvoicetextView.setText(purchaseData.get(position).getInvoice());
        holder.transactionOrderTextView.setText(purchaseData.get(position).getOrderId());
        holder.transactionPurchaseDateTextView.setText(purchaseData.get(position).getPurchaseDate());
        holder.showPriceTextView.setText(purchaseData.get(position).getAmount());
        holder.successTextView.setText(purchaseData.get(position).getTransactionStatus());

    }

    @Override
    public int getItemCount() {
        return purchaseData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView activeAlertTextView,transactionInvoicetextView,transactionOrderTextView,transactionPurchaseDateTextView,
                showPriceTextView,successTextView;

        public TextView transactionTitleTextView,transactionInvoiceTitleTextView,transactionOrderTitleTextView,transactionPurchaseDateTitleTextView;
        public ViewHolder(View v){

            super(v);
            transactionTitleTextView = (TextView)v. findViewById(R.id.transactionTitleTextView);
            transactionInvoiceTitleTextView = (TextView)v. findViewById(R.id.transactionInvoiceTitleTextView);
            transactionOrderTitleTextView = (TextView)v. findViewById(R.id.transactionOrderTitleTextView);
            transactionPurchaseDateTitleTextView = (TextView)v. findViewById(R.id.transactionPurchaseDateTitleTextView);


            activeAlertTextView = (TextView)v. findViewById(R.id.activeAlertTextView);
            transactionInvoicetextView = (TextView)v. findViewById(R.id.transactionInvoice);
            transactionOrderTextView = (TextView)v. findViewById(R.id.transactionOrderTextView);
            transactionPurchaseDateTextView = (TextView)v. findViewById(R.id.transactionPurchaseDateTextView);
            showPriceTextView = (TextView)v. findViewById(R.id.showPriceTextView);
            successTextView = (TextView)v. findViewById(R.id.successTextView);
        }
    }

}