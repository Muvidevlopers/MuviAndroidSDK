package player.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;


import com.home.vod.R;

import java.util.ArrayList;

public class DownloadOptionAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList List_Of_FileSize ;
    private ArrayList ResolutionFormat ;

    int selected_option = 0;

    public DownloadOptionAdapter(Context mContext, ArrayList List_Of_FileSize , ArrayList ResolutionFormat) {
        this.mContext = mContext;
        this.List_Of_FileSize = List_Of_FileSize;
        this.ResolutionFormat = ResolutionFormat;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return List_Of_FileSize.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_download_resolution_list, null);
        } else {
            view = (View) convertView;
        }



        final RadioButton radioButton = (RadioButton)view.findViewById(R.id.selected_resolution_option);
//        radioButton.setText("hello");
        radioButton.setText("  "+ResolutionFormat.get(position)+" "+List_Of_FileSize.get(position));
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton.setChecked(true);
                selected_option = position;
                notifyDataSetChanged();

                Intent intent = new Intent("UrlPosition");
                // add data
                intent.putExtra("position", ""+selected_option);
                mContext.sendBroadcast(intent);
            }
        });

        if(selected_option==position)
        {
            radioButton.setChecked(true);
        }
        else
        {
            radioButton.setChecked(false);
        }


        return view;
    }
}