
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
import com.home.vod.model.GridItem;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;

public class FilmographyAdapter extends RecyclerView.Adapter<FilmographyAdapter.MyViewHolder> {
    private Context context;
    private int layoutResourceId;
    LanguagePreference languagePreference;
    private ArrayList<GridItem> data = new ArrayList<GridItem>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView videoImageview;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.movieTitle);
            videoImageview = (ImageView) view.findViewById(R.id.movieImageView);
        }
    }

    public FilmographyAdapter(Context context, int layoutResourceId,
                              ArrayList<GridItem> data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        languagePreference = LanguagePreference.getLanguagePreference(context);
    }

 /*   public FilmographyAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GridItem item = data.get(position);
        holder.title.setText(item.getTitle());
        String imageId = item.getImage();
        holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));

        if(imageId.matches("") || imageId.matches(languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))){
            holder.videoImageview.setImageResource(R.drawable.logo);

        }else {

            Picasso.with(context)
                    .load(item.getImage()).error(R.drawable.no_image).placeholder(R.drawable.no_image)
                    .into(holder.videoImageview);


        }


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