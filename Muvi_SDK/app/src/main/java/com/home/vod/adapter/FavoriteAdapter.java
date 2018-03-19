
package com.home.vod.adapter;

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
import com.home.vod.activity.FavoriteActivity;
import com.home.vod.model.GridItem;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_NORMAL;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_SMALL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_NO_DATA;
import static com.home.vod.preferences.LanguagePreference.NO_DATA;

public class FavoriteAdapter extends ArrayAdapter<GridItem> {
    private int layoutResourceId;
    boolean close = false;
    private ArrayList<GridItem> data = new ArrayList<GridItem>();
    private FavoriteActivity mActivity;
    LanguagePreference languagePreference;

    public FavoriteAdapter(FavoriteActivity mActivity, int layoutResourceId, ArrayList<GridItem> data) {
        super(mActivity, layoutResourceId, data);
        this.mActivity = mActivity;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        languagePreference = LanguagePreference.getLanguagePreference(mActivity);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (mActivity).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.movieTitle);
            holder.videoImageview = (ImageView) row.findViewById(R.id.movieImageView);
            holder.closeAlbumArt = (ImageView) row.findViewById(R.id.close_album_art);






            FontUtls.loadFont(mActivity,mActivity.getResources().getString(R.string.regular_fonts),holder.title);
/*
            Typeface castDescriptionTypeface = Typeface.createFromAsset(mActivity.getAssets(),mActivity.getResources().getString(R.string.regular_fonts));
            holder.title.setTypeface(castDescriptionTypeface);*/
           /* int height = holder.videoImageview.getDrawable().getIntrinsicHeight();
            int width = holder.videoImageview.getDrawable().getIntrinsicWidth();

            holder.videoImageview.getLayoutParams().height = height;
            holder.videoImageview.getLayoutParams().width = width;*/

            if ((mActivity.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(mActivity.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));

            }
            else if ((mActivity.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_NORMAL) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(mActivity.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            else if ((mActivity.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_SMALL) {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(mActivity.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            else {
                holder.videoImageview.setImageBitmap(decodeSampledBitmapFromResource(mActivity.getResources(), R.id.movieImageView,holder.videoImageview.getDrawable().getIntrinsicWidth(),holder.videoImageview.getDrawable().getIntrinsicHeight()));


            }
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        final GridItem item = data.get(position);
        holder.title.setText(item.getTitle());
        String imageId = item.getImage();
        LogUtil.showLog("Nihar_feb",""+imageId);



        holder.closeAlbumArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close=true;
               if (data.get(position).isClicked()){

                   LogUtil.showLog("ANU","movieUniqueId  ========"+item.getMovieUniqueId());

                   mActivity.removeFavorite(item,position);

               }

            }
        });




        if (data.get(position).isSelected()){
            holder.closeAlbumArt.setVisibility(View.VISIBLE);
//            feb_bt.setImageResource(R.drawable.favorite);
        }else {
            holder.closeAlbumArt.setVisibility(View.GONE);
//            feb_bt.setImageResource(R.drawable.favorite_unselected);

        }

        if(imageId.matches("") || imageId.matches(languagePreference.getTextofLanguage(NO_DATA,DEFAULT_NO_DATA))){
            holder.videoImageview.setImageResource(R.drawable.logo);


        }else {
            Picasso.with(mActivity)
                    .load(imageId)
                    .into(holder.videoImageview);

//            Picasso.with(mActivity)
//                    .load(imageId).error(R.drawable.logo).placeholder(R.drawable.logo)
//                    .into(holder.videoImageview);


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



        return row;
    }

    static class ViewHolder {
        public TextView title;
        public ImageView videoImageview,closeAlbumArt;


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