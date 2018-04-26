package com.home.vod.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.apisdk.apiModel.FcmNotificationlistsOutputModel;
import com.home.vod.R;

import java.util.ArrayList;


/**
 * Created by Android on 10/31/2017.
 */

public class NotificatonAdapter extends RecyclerView.Adapter<NotificatonAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<FcmNotificationlistsOutputModel> msg;
    Object[] objPlace;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public MyViewHolder(View view) {
            super(view);

            message = (TextView) view.findViewById(R.id.message);
        }
    }


    public NotificatonAdapter(Context mContext, ArrayList<FcmNotificationlistsOutputModel> msg) {
        this.mContext = mContext;
        this.msg = msg;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_adapter, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public  void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.message.setText(this.msg.get(position).getNotification());


    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

}