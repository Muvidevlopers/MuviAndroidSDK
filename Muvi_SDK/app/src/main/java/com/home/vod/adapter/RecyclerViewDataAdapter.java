package com.home.vod.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.home.vod.ListItemAllignmentHandler;
import com.home.vod.R;
import com.home.vod.activity.ViewMoreActivity;
import com.home.vod.model.SectionDataModel;
import com.home.vod.model.SingleItemModel;
import com.home.vod.preferences.LanguagePreference;
import com.home.vod.util.Constant;
import com.home.vod.util.FontUtls;
import com.home.vod.util.LogUtil;
import com.home.vod.util.Util;

import java.util.ArrayList;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_ALL;
import static com.home.vod.preferences.LanguagePreference.DEFAULT_VIEW_MORE;
import static com.home.vod.preferences.LanguagePreference.VIEW_ALL;
import static com.home.vod.preferences.LanguagePreference.VIEW_MORE;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {
    ArrayList<SingleItemModel> singleSectionItems;
    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private boolean firstTime;
    private ArrayList<String> bannerUrls = new ArrayList<String>();
    String pemalink;
    // String image;
    int vertical = 0;
    boolean loaded = false;
    //int counter=0;
    LanguagePreference languagePreference;
    int banner[] = {R.drawable.banner1};

    ListItemAllignmentHandler listItemAllignmentHandler;

  /*  int banner[] = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
    int bannerL[] = {R.drawable.banner1_l,R.drawable.banner2_l,R.drawable.banner3_l};*/

    //Forgot Password Dialog


    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, ArrayList<String> bannerUrls, boolean firstTime, int vertical) {
        this.dataList = dataList;
        this.mContext = context;
        this.bannerUrls = bannerUrls;
        this.firstTime = firstTime;
        this.vertical = vertical;
        languagePreference = LanguagePreference.getLanguagePreference(context);

        listItemAllignmentHandler = new ListItemAllignmentHandler(mContext);

    }

    /* public void swapItems(){
         loaded = true;
     }*/
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        float density = mContext.getResources().getDisplayMetrics().density;
        LogUtil.showLog("SUBHA", "density === " + density);
        if (density >= 1.5 && density <= 3.0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_small, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;

        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder itemRowHolder, final int i) {

        Log.v("BIBHU12", "position of the item in adapter ==============" + i);
        Log.v("BIBHU12", "section adapter size of Util.image_orentiation.get(i) ======***========" + Util.image_orentiation.size());
        try {

            if (dataList.size() < 1) {
                itemRowHolder.section_title_layout.setVisibility(View.GONE);
                itemRowHolder.recycler_view_list.setVisibility(View.GONE);
                return;
            } else{
                itemRowHolder.section_title_layout.setVisibility(View.VISIBLE);
                itemRowHolder.recycler_view_list.setVisibility(View.VISIBLE);
            }




            final String sectionName = dataList.get(i).getHeaderTitle().trim();
            final String sectionId = dataList.get(i).getHeaderPermalink();

            singleSectionItems = dataList.get(i).getAllItemsInSection();
            pemalink = dataList.get(i).getHeaderPermalink();
        /*for (int j = 0; j > bannerUrls.size(); j++) {
            //image = bannerUrls.get(j);
        }*/
            FontUtls.loadFont(mContext, mContext.getResources().getString(R.string.regular_fonts), itemRowHolder.itemTitle);

            itemRowHolder.itemTitle.setText(sectionName.trim());
            listItemAllignmentHandler.setAllignment(itemRowHolder.itemTitle);
            SectionListDataAdapter itemListDataAdapter = null;



            if (Util.image_orentiation.get(i) == Constant.IMAGE_PORTAIT_CONST) {
                float density = mContext.getResources().getDisplayMetrics().density;
                if (density >= 3.5 && density <= 4.0) {
                    itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, R.layout.list_single_card);
                    Log.v("BIBHU12", "position of *********** ==============" + i);
                } else if (density <= 1.5) {
                    itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, R.layout.list_single_card_small);
                    Log.v("BIBHU12", "position of *********** ==============" + i);
                } else {
                    itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, R.layout.list_single_card_nexus);
                    Log.v("BIBHU12", "position of *********** ==============" + i);
                }

            } else {
                itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, R.layout.home_280_card);
                Log.v("BIBHU12", "position of *********** ==============" + i);

            }


            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

            itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent i = new Intent(context, ViewMoreActivity.class);
                    i.putExtra("SectionId", sectionId);
                    i.putExtra("sectionName", sectionName);
                    context.startActivity(i);
                }
            });

            if (i == 0) {
                itemRowHolder.mDemoSliderLayout.setVisibility(View.VISIBLE);
            } else {
                itemRowHolder.mDemoSliderLayout.setVisibility(View.GONE);

            }
            LogUtil.showLog("SUBHA", "hggf" + singleSectionItems.size());
            //  itemRowHolder.btnMore.setVisibility(View.VISIBLE);

            if (singleSectionItems.size() <= 0) {
                itemRowHolder.itemTitle.setVisibility(View.GONE);
                itemRowHolder.btnMore.setVisibility(View.GONE);
                // itemRowHolder.recycler_view_list.setVisibility(View.GONE);
            } else if (singleSectionItems.size() == 1) {
                itemRowHolder.btnMore.setVisibility(View.GONE);
                // itemRowHolder.recycler_view_list.setVisibility(View.GONE);

            } else {
                itemRowHolder.btnMore.setVisibility(View.VISIBLE);
                // itemRowHolder.recycler_view_list.setVisibility(View.VISIBLE);


            }
            // }
        } catch (Exception e) {
            e.printStackTrace();
//            itemRowHolder.section_title_layout.setVisibility(View.GONE);
//            itemRowHolder.recycler_view_list.setVisibility(View.GONE);
            Log.v("BIBHU12", "recycler view adapter Exception==============" + e.toString());


        }
        /*itemRowHolder.recycler_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
            }
        });*/

    }

    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
    public int getItemCount() {
        if (Util.image_orentiation.size()< 1&&bannerUrls.size()>=1)
            return 1;
        else
            return Util.image_orentiation.size();
//        return Util.image_orentiation.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Method to load dynamic urls to slider.
     *
     * @param mDemoSlider
     * @param view
     * @param onSliderClickListener
     */
    private void loadDynamicBanners(SliderLayout mDemoSlider, View view, BaseSliderView.OnSliderClickListener onSliderClickListener) {
        if (bannerUrls.size() >= 0) {
            for (int i = 0; i < bannerUrls.size(); i++) {

                DefaultSliderView textSliderView = new DefaultSliderView(view.getContext());

                textSliderView
                        .description("")
                        .image(bannerUrls.get(i))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(onSliderClickListener);
                mDemoSlider.addSlider(textSliderView);

            }
        }
    }


    /**
     * Method to load static urls to slider.
     *
     * @param mDemoSlider
     * @param view
     * @param onSliderClickListener
     */
    private void loadStaticBanners(SliderLayout mDemoSlider, View view, BaseSliderView.OnSliderClickListener onSliderClickListener) {
        if (((mContext.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_LARGE)
                || ((mContext.getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_MASK) == SCREENLAYOUT_SIZE_XLARGE)) {
            for (int j = 0; j < banner.length; j++) {
                DefaultSliderView textSliderView = new DefaultSliderView(view.getContext());
                textSliderView
                        .description("")
                        .image(banner[j])
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(onSliderClickListener);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", "");

                mDemoSlider.addSlider(textSliderView);
            }
        } else {
            for (int j = 0; j < banner.length; j++) {
                DefaultSliderView textSliderView = new DefaultSliderView(view.getContext());
                textSliderView
                        .description("")
                        .image(banner[j])
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(onSliderClickListener);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", "");

                mDemoSlider.addSlider(textSliderView);
            }
        }
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;
        private SliderLayout mDemoSlider;
        private RelativeLayout mDemoSliderLayout, section_title_layout;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.section_title_layout = (RelativeLayout) view.findViewById(R.id.section_title_layout);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.featureContent);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);
            FontUtls.loadFont(mContext, mContext.getResources().getString(R.string.regular_fonts), this.btnMore);

/*
            Typeface watchTrailerButtonTypeface = Typeface.createFromAsset(mContext.getAssets(),mContext.getResources().getString(R.string.regular_fonts));
            this.btnMore.setTypeface(watchTrailerButtonTypeface);*/
            this.btnMore.setText(languagePreference.getTextofLanguage(VIEW_MORE, DEFAULT_VIEW_MORE));
            mDemoSlider = (SliderLayout) view.findViewById(R.id.sliderLayout);
            mDemoSliderLayout = (RelativeLayout) view.findViewById(R.id.sliderRelativeLayout);

           /* if (!firstTime) {
                firstTime = true;*/
                //for dynamic banner
                loadDynamicBanners(mDemoSlider, view, this);

                // for static banner
                //loadStaticBanners(mDemoSlider,view,this);

            //}

            if (bannerUrls.size() > 1) {
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider.setDuration(10000);
                mDemoSlider.addOnPageChangeListener(this);
            } else {
                mDemoSlider.stopAutoCycle();
                mDemoSlider.getPagerIndicator().setVisibility(View.INVISIBLE);
            }

        }


        @Override
        public void onSliderClick(BaseSliderView slider) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

   /* @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }*/
}