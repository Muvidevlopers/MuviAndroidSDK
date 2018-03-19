package com.home.vod.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.model.EpisodesListModel;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FontUtls;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;

public class EpisodesListViewMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String bannerImageUrl = "";
    private String showTitle = "";
    private String showStory = "";
    private String movieTrailerUrl = "";
    private String movieUniqueId;
    private int contentTypesId = 0;


    private Context context;
    private int layoutResourceId;
    private int isThirdParty = 0;
    private LanguagePreference languagePreference;




    private  OnItemClickListener listener;
    private ArrayList<EpisodesListModel> data = new ArrayList<EpisodesListModel>();



    public interface OnItemClickListener {
        void onItemClick(EpisodesListModel item);

    }


    public EpisodesListViewMoreAdapter(Context context, int layoutResourceId,
                                       ArrayList<EpisodesListModel> data, OnItemClickListener listener) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.listener = listener;
        languagePreference = LanguagePreference.getLanguagePreference(context);


    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView episodeTitleTextView;
        public TextView episodeNameTextView;
        public TextView episodeDateTextView;

        public ImageView episodeImageView;
        public ViewHolder(View view) {
            super(view);
            episodeTitleTextView = (TextView) view.findViewById(R.id.episodeTitleTextView);
            episodeNameTextView = (TextView) view.findViewById(R.id.episodeNameTextView);
            episodeNameTextView.setVisibility(View.GONE);
            episodeDateTextView = (TextView) view.findViewById(R.id.episodeDateTextView);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),episodeTitleTextView);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),episodeNameTextView);
            FontUtls.loadFont(context,context.getResources().getString(R.string.regular_fonts),episodeDateTextView);

        /*    Typeface castDescriptionTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.regular_fonts));
            episodeTitleTextView.setTypeface(castDescriptionTypeface);

            Typeface episodeNameTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
            episodeNameTextView.setTypeface(episodeNameTypeface);

            Typeface episodeDateTypeface = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.light_fonts));
            episodeDateTextView.setTypeface(episodeDateTypeface);*/

            episodeImageView = (ImageView) view.findViewById(R.id.episodeImageView);
            episodeImageView.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.episodeImageView,episodeImageView.getDrawable().getIntrinsicWidth(),episodeImageView.getDrawable().getIntrinsicHeight()));
        }

        public void bind(final EpisodesListModel item, final OnItemClickListener listener) {
            episodeTitleTextView.setText(item.getEpisodeTitle());
            episodeNameTextView.setText(item.getEpisodeNumber());
            //episodeDateTextView.setText(item.getEpisodeTelecastOn());
            episodeDateTextView.setText("");

                if(episodeDateTextView.getText().toString().matches("") || episodeDateTextView.getText().toString().matches(languagePreference
                        .getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))) {


                    episodeDateTextView.setVisibility(View.GONE);
                }
                String imageId = item.getEpisodeThumbnailImageView();



            if(imageId.matches("") || imageId.matches(languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))){
                episodeImageView.setImageResource(R.drawable.logo);

            }else {
                Picasso.with(context)
                        .load(item.getEpisodeThumbnailImageView()).error(R.drawable.logo).placeholder(R.drawable.logo)
                        .into(episodeImageView);
            }                itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder groupViewHolder = (ViewHolder) holder;
        // groupViewHolder.mImage.setText(labels.get(position - 1));
        groupViewHolder.bind(data.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        final BitmapFactory.Options opt =new BitmapFactory.Options();
        opt.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res, resId, opt);
        opt.inSampleSize = calculateInSampleSize(opt,reqWidth,reqHeight);
        opt.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res, resId, opt);
    }
    public static int calculateInSampleSize(BitmapFactory.Options opt, int reqWidth, int reqHeight){
        final int height = opt.outHeight;
        final int width = opt.outWidth;
        int sampleSize=1;
        if (height > reqHeight || width > reqWidth){
            final int halfWidth = width/2;
            final int halfHeight = height/2;
            while ((halfHeight/sampleSize) > reqHeight && (halfWidth/sampleSize) > reqWidth){
                sampleSize *=2;
            }

        }
        return sampleSize;
    }
}



