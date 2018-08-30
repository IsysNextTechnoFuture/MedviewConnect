package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.SendMessageAttachFileDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Harsh on 07/05/18.
 */
public class SendMsgAttachFileAdapter extends ArrayAdapter<SendMessageAttachFileDTO> implements AppConstants {
    //Declaration of variables
    private List<SendMessageAttachFileDTO> sendMsgAttachmentList;
    private int resourceId;
    private LayoutInflater inflater;
    private Context context;
    private AppSession appSession;
    private Utilities utilities;
    private String strDate;
    //Constructor
    public SendMsgAttachFileAdapter(Context context, int resourceId,
                                    List<SendMessageAttachFileDTO> sendMsgAttachmentList)
    {
        super(context, resourceId, sendMsgAttachmentList);
        this.sendMsgAttachmentList = sendMsgAttachmentList;
        this.context = context;
        this.resourceId = resourceId;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appSession = new AppSession(context);
        utilities = Utilities.getInstance(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvAttachFile = (TextView) convertView.findViewById(R.id.tv_attach_file);
            viewHolder.ivAttachFile = (ImageView) convertView.findViewById(R.id.iv_attach_file);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(sendMsgAttachmentList.size()>1)
        {
            getCurrentDate();
            //Toast.makeText(context,strPathHolder, Toast.LENGTH_LONG).show();
            String strShowFilePath = "";
            strShowFilePath = appSession.getUser().getUinfo().getFirstname() + "_" +
                    sendMsgAttachmentList.get(position).getStrAttachmentFilePath()
                    + "_" + strDate;
        }
        viewHolder.tvAttachFile.setText(sendMsgAttachmentList.get(position).getStrAttachmentFilePath());
        return convertView;
    }

    //Class for holding view
    public class ViewHolder {
        TextView tvAttachFile;
        ImageView ivAttachFile;
    }

    //Method for getting current date
    private void getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        strDate = YYYYMMDDHHMMSS.format(c);
        Log.i("Current Date-----------", strDate);
    }

}
