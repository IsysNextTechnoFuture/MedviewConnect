package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.R;

import java.util.HashMap;
import java.util.List;


public class CustomSpinnerAdapter extends ArrayAdapter<HashMap<String, String>> {

    Context mContext;
    List<HashMap<String, String>> list;
    int layoutResourceId;
    View row = null;
    int textColor;
    String spinClassName = "";

    public CustomSpinnerAdapter(Context mContext, int layoutResourceId,
                                List<HashMap<String, String>> list, int textColor, String spinClassName) {
        super(mContext, layoutResourceId, list);
        this.mContext = mContext;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.textColor = textColor;
        this.spinClassName = spinClassName;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {

        try {
            return (list.size());
        } catch (Exception e1) {
            e1.printStackTrace();

            return 0;
        } catch (Error e1) {
            e1.printStackTrace();

            return 0;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        try {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_textview, parent, false);
            TextView label = (TextView) row.findViewById(R.id.text);
//            if (appSession.getLanguage().equalsIgnoreCase("ar"))
//                label.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            else label.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            if (position == 0) {
                label.setTextColor(mContext.getResources().getColor(R.color.white));
                label.setTextSize(18);
            } else {
                label.setTextColor(textColor);
                label.setTextSize(18);
            }
            label.setText(list.get(position).get("value"));
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return row;
        }
    }

    public View getCustomDropDownView(int position, View convertView,
                                      ViewGroup parent) {
        try {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_textview, parent, false);
            TextView label = (TextView) row.findViewById(R.id.text);
            label.setText("  " + list.get(position).get("value") + "  ");
            if (position == 0) {
                label.setBackgroundColor(mContext.getResources().getColor(
                        R.color.transparent_black_drop_down));
                label.setTextColor(mContext.getResources().getColor(
                        R.color.white));
            } else {
                label.setBackgroundColor(mContext.getResources().getColor(
                        R.color.transparent_black_drop_down));
                label.setTextColor(mContext.getResources().getColor(
                        R.color.white));
            }
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return row;
        }
    }
}