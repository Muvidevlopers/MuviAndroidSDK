package com.home.vod.adapter;

/**
 * Created by MUVI on 10/6/2017.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.model.SeasonModel;

import java.util.ArrayList;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SeasonModel> data = new ArrayList<SeasonModel>();
    private int layoutResourceId;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.seasonTitle);
            thumbnail = (ImageView) view.findViewById(R.id.seasonImageView);
        }
    }

    public SeasonAdapter(Context context, int layoutResourceId,
                         ArrayList<SeasonModel> data) {
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.season_card_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SeasonModel album = data.get(position);
        holder.title.setText(album.getSeasonName());
        holder.thumbnail.setImageResource(album.getSeasonImage());




    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}