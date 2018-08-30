package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.isysnext.medviewmd.medviewconnect.modelDr.SpinnerListItem;
import com.isysnext.medviewmd.medviewconnect.R;
import java.util.ArrayList;

public class DrSpinnerAdapter extends BaseAdapter {
    ArrayList<SpinnerListItem> arrayList;
    Context context;

    public DrSpinnerAdapter(ArrayList<SpinnerListItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        v = inflater.inflate(R.layout.spinner_list_item,null);
        TextView tv = (TextView) v.findViewById(R.id.tv_spin_item);

        tv.setText(arrayList.get(position).getText());

        return v;
    }
}
