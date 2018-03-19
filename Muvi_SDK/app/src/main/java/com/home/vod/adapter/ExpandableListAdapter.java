package com.home.vod.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.home.apisdk.apiModel.MenusOutputModel;
import com.home.vod.R;
import com.home.vod.activity.MovieDetailsActivity;
import com.home.vod.util.FontUtls;
import com.home.vod.util.Util;

import java.util.ArrayList;
import java.util.HashMap;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> expandableListTitle;
    private HashMap<String, ArrayList<String>> expandableListDetail;
    private ArrayList<MenusOutputModel.MainMenu> mainMenuModelArrayList;
    // add by subha
    private ArrayList<MenusOutputModel.FooterMenu> footerMenuModelArrayList;


    public ExpandableListAdapter(Context context, ArrayList<String> expandableListTitle,
                                 HashMap<String, ArrayList<String>> expandableListDetail, ArrayList<MenusOutputModel.MainMenu> mainMenuModelArrayList, ArrayList<MenusOutputModel.FooterMenu> footerMenuModelArrayList) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.mainMenuModelArrayList = mainMenuModelArrayList;
        this.footerMenuModelArrayList = footerMenuModelArrayList;

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item1, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        FontUtls.loadFont(context, context.getResources().getString(R.string.regular_fonts), expandedListTextView);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView1, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
         final ImageView iconimage,iconimage1;
        int totalposition=listPosition;
//        Util.drawer_collapse_expand_imageview.clear();

        View convertView = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
           //convertView = layoutInflater.inflate(R.layout.nav_drawer_row, null);
        }
       TextView textViewLine = (TextView) convertView.findViewById(R.id.textViewLine);

        TextView listTitleTextView = (TextView) convertView .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.NORMAL);
        listTitleTextView.setText(Html.fromHtml(listTitle));
        iconimage=(ImageView) convertView.findViewById(R.id.iconimage);
        iconimage1=(ImageView) convertView.findViewById(R.id.iconimage1);
        //listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, isExpanded ? 0 : android.R.drawable.ic_menu_more, 0);


        if(expandableListDetail.get(this.expandableListTitle.get(listPosition)).size()>0){
            iconimage .setVisibility(View.VISIBLE);
            Log.v("SUBHA","iconimage visible Position ===== "+ listPosition);
        }

         //for expand less and expand the child content
        //***for this we have clear drawer_collapse in splashscreen and create a arraylist for imageview which is declare in Util
        //****in NavigationdrawerFragment we have two method ongroupcollapse and ongroupexpand ther we written logic( expnad and less)
        boolean add_to_array = true;


        for(int k = 0; k< Util.drawer_collapse_expand_imageview.size(); k++)
        {
            String Data[] = Util.drawer_collapse_expand_imageview.get(k).split(",");
            if(listPosition == Integer.parseInt(Data[0]))
            {
                add_to_array = false;
            }
        }
        if(add_to_array)
        {
            Util.drawer_collapse_expand_imageview.add(listPosition+","+Util.image_compressed);
        }


            String expand_collapse_image_info1[] = Util.drawer_collapse_expand_imageview.get(listPosition).split(",");
            Log.v("SUBHA1","inside adapter===Data=========="+expand_collapse_image_info1[0]+","+expand_collapse_image_info1[1]);


        String expand_collapse_image_info[] = Util.drawer_collapse_expand_imageview.get(listPosition).split(",");
        if(listPosition == Integer.parseInt(expand_collapse_image_info[0]) && Integer.parseInt(expand_collapse_image_info[1]) == 1)
        {
            iconimage.setImageResource(R.drawable.ic_remove_black_24dp);

            Log.v("SUBHA1","image expanded ="+expand_collapse_image_info[0]+","+expand_collapse_image_info[1]);
        }

        else if(listPosition == Integer.parseInt(expand_collapse_image_info[0]) && Integer.parseInt(expand_collapse_image_info[1]) == 0)
        {
            iconimage.setImageResource(R.drawable.ic_add_black_24dp);

            Log.v("SUBHA1","image collapsed ="+expand_collapse_image_info[0]+","+expand_collapse_image_info[1]);
        }


//*************underline where the mainmenu arraylist data finish*************

        if(footerMenuModelArrayList.size()>0)
        {
            if (listPosition==mainMenuModelArrayList.size()){
                textViewLine .setVisibility(View.VISIBLE);
            }
        }

        return convertView;

    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}