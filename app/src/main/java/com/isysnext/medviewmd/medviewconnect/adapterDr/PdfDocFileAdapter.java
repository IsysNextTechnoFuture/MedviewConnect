package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.PdfDocActivityDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import java.util.List;

/**
 * Created by Harsh on 06/05/18.
 */
public class PdfDocFileAdapter extends ArrayAdapter<PdfDocActivityDTO> implements AppConstants
{
    /*Declaration of variables*/
    private List<PdfDocActivityDTO> strFileList;
    private int resourceId;
    private LayoutInflater inflater;
    private Context context;
    private AppSession appSession;
    private Utilities utilities;

    /*Constructor*/
    public PdfDocFileAdapter(Context context, int resourceId,
                             List<PdfDocActivityDTO> strFileList) {
        super(context, resourceId, strFileList);
        this.strFileList = strFileList;
        this.context = context;
        this.resourceId = resourceId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appSession = new AppSession(context);
        utilities = Utilities.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            /*Inflate view*/
            convertView = inflater.inflate(resourceId, null);
            viewHolder = new ViewHolder();
            /*Initializing view*/
            viewHolder.tvFile = (TextView) convertView.findViewById(R.id.tv_file);
            viewHolder.ivFile = (ImageView) convertView.findViewById(R.id.iv_file);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*Setting values on view*/
        viewHolder.tvFile.setText(strFileList.get(position).getName());


        return convertView;
    }

    public class ViewHolder
    {
        TextView tvFile;
        ImageView ivFile;
    }

}

