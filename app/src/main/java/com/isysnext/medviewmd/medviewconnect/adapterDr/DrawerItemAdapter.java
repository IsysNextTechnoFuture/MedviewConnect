package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.DrawerListItem;
import com.isysnext.medviewmd.medviewconnect.R;
import java.util.ArrayList;

public class DrawerItemAdapter extends BaseAdapter {

    Context context;
    ArrayList<DrawerListItem> arrayList;
    public DrawerItemAdapter(Context context, ArrayList<DrawerListItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.drawr_list_item,null);
        ImageView drawr_list_icon = (ImageView) v.findViewById(R.id.drawr_list_icon);
        TextView tv_Unread_Msg = (TextView) v.findViewById(R.id.tv_unread_msg);
        drawr_list_icon.setImageResource(arrayList.get(position).getItem_img());
        String pos = String.valueOf(arrayList.get(position));
        if(pos.equals("0"))
        {
            if(DashboardActivity.count>0) {
                tv_Unread_Msg.setVisibility(View.VISIBLE);
                tv_Unread_Msg.setText(""+DashboardActivity.count);
            }
        }
        else
        {
            tv_Unread_Msg.setVisibility(View.GONE);
        }
        return v;
    }
}
