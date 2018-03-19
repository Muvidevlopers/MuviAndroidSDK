
package com.home.vod.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vod.R;
import com.home.vod.model.GridItem;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;

public class VideoFilterAdapter extends ArrayAdapter<GridItem> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<GridItem> data = new ArrayList<GridItem>();

    public VideoFilterAdapter(Context context, int layoutResourceId,
                              ArrayList<GridItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        Log.v("BISHAL123", "RES" + "videofilter adapter calleed");
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.movieTitle);
            holder.videoImageview = (ImageView) row.findViewById(R.id.movieImageView);

           /* int height = holder.videoImageview.getDrawable().getIntrinsicHeight();
            int width = holder.videoImageview.getDrawable().getIntrinsicWidth();

            holder.videoImageview.getLayoutParams().height = height;
            holder.videoImageview.getLayoutParams().width = width;*/

            if ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));

            }
            else if ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            else if ((context.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            else {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }
      try {

        GridItem item = data.get(position);
        holder.title.setText(item.getTitle().replaceAll("[^a-zA-Z0-9]", ""));
        String imageId = item.getImage();


          if(imageId == null || imageId.matches("") || imageId.matches(LanguagePreference.getLanguagePreference(context).getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))){
            holder.videoImageview.setImageResource(R.drawable.logo);

        }else {

            Picasso.with(context)
                    .load(item.getImage()).error(R.drawable.no_image).placeholder(R.drawable.logo)
                    .into(holder.videoImageview);


          /*  ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(R.drawable.no_thumbnail)
                    .showImageOnFail(R.drawable.no_thumbnail)
                    .showImageOnLoading(R.drawable.no_thumbnail).build();
            ImageAware imageAware = new ImageViewAware(holder.videoImageview, false);
            imageLoader.displayImage(imageId, imageAware,options);*/
        }

      }
      catch (IndexOutOfBoundsException e){
          e.printStackTrace();

      }

        return row;
    }

    static class ViewHolder {
        public TextView title;
        public ImageView videoImageview;

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