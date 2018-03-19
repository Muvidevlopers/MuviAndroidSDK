package com.home.vod.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.home.vod.R;
import com.home.vod.model.ListItem;
import com.home.vod.preferences.PreferenceManager;
import com.home.vod.util.FontUtls;
import com.home.vod.util.Util;

import java.util.ArrayList;

/**
 * Created by Muvi on 9/6/2016.
 */
public class GenreFilterAdapter extends RecyclerView.Adapter<GenreFilterAdapter.MyViewHolder> {

    public static int prevPosition = 0;
    private Context context;

    private ArrayList<ListItem> moviesList;
    PreferenceManager preferenceManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView filterTextName;
        private ImageView selectButton;
        private RelativeLayout listviewLay1;

        public MyViewHolder(View view) {
            super(view);
            filterTextName = (TextView) view.findViewById(R.id.listTextView);
            selectButton = (ImageView) view.findViewById(R.id.listCheckBox);
            listviewLay1 = (RelativeLayout) view.findViewById(R.id.listviewLay1);

        }
    }


    public GenreFilterAdapter(ArrayList<ListItem> moviesList, Context context) {
        this.context = context;
        this.moviesList = moviesList;
        preferenceManager = PreferenceManager.getPreferenceManager(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_check, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ListItem movie = moviesList.get(position);



        String genreString = preferenceManager.getGenreArrayFromPref();
        final String[] genreTempArr = genreString.split(",");

        if (movie.isSelected() == false){


            if (position >= 1 && position <=(genreTempArr.length -5)) {
                holder.selectButton.setImageResource(R.drawable.tick_unselect25x25);
            }else{
                holder.selectButton.setImageResource(R.drawable.radio_deselect25x25);

            }
            //holder.snap.setVisibility(View.INVISIBLE);

        }else{
            if (position >= 1 && position <=(genreTempArr.length -5)) {
                holder.selectButton.setImageResource(R.drawable.tick_select25x25);
            }else{
                holder.selectButton.setImageResource(R.drawable.radio_select25x25);


            }
            //holder.snap.setVisibility(View.VISIBLE);

        }

        if (position == 0) {
            holder.selectButton.setVisibility(View.GONE);

            holder.listviewLay1.setClickable(false);
            holder.filterTextName.setTextColor(context.getResources().getColor(R.color.castCrewTitleTextColor));
            holder.filterTextName.setTextSize(18);
          //  holder.filterTextName.setTypeface(holder.filterTextName.getTypeface(), Typeface.BOLD);
           // holder.filterTextName.setGravity(Gravity.LEFT);
            holder.filterTextName.setGravity(Gravity.LEFT);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.filterTextName);

            /*Typeface filterTextNameTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
            holder.filterTextName.setTypeface(filterTextNameTypeface)*/;
        }
       else if (position == (genreTempArr.length - 5)){
            holder.selectButton.setVisibility(View.GONE);
            holder.listviewLay1.setClickable(false);
            holder.filterTextName.setTextColor(context.getResources().getColor(R.color.castCrewTitleTextColor));
            holder.filterTextName.setTextSize(18);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.filterTextName);

        /*    Typeface filterTextNameTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
            holder.filterTextName.setTypeface(filterTextNameTypeface);*/
          //  holder.filterTextName.setTypeface(holder.filterTextName.getTypeface(), Typeface.BOLD);
            holder.filterTextName.setGravity(Gravity.LEFT);

        }
        else{
            holder.listviewLay1.setClickable(true);
            holder.filterTextName.setTextColor(context.getResources().getColor(R.color.filter_text_color));
            holder.filterTextName.setTextSize(14);
          //  holder.filterTextName.setTypeface(holder.filterTextName.getTypeface(), Typeface.BOLD);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),holder.filterTextName);

          /*  Typeface filterTextNameTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
            holder.filterTextName.setTypeface(filterTextNameTypeface);*/
            holder.selectButton.setVisibility(View.VISIBLE);
        }

        holder.filterTextName.setText(movie.getTitle());



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
