package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.SentMessageDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class SentMessageAdapter extends RecyclerView.Adapter<SentMessageAdapter.MyViewHolder> {
    ArrayList<SentMessageDTO.Message> arrayList;
    private static ClickListener clickListener;
    Context Mcontext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_sent_msg_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvMsgName.setText(arrayList.get(position).getFromname());
        holder.tvMsgDate.setText(arrayList.get(position).getCreatedAt());
        holder.tvSubject.setText(arrayList.get(position).getSubject());
        holder.tvMyMessageContent.setText(arrayList.get(position).getMessage());
        holder.tvNameText.setText(""+arrayList.get(position).getFromname().charAt(0));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNameText,tvMsgName,tvMsgTime,tvMsgDate,tvSubject,tvMyMessageContent;
        public ImageView imgAttchment;
        public CircularImageView textImg;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            tvNameText = (TextView) view.findViewById(R.id.tv_name_text);
            tvMsgName = (TextView) view.findViewById(R.id.tv_msg_name);
            tvMsgTime = (TextView) view.findViewById(R.id.msg_time);
            tvMsgDate = (TextView) view.findViewById(R.id.msg_date);
            tvSubject = (TextView) view.findViewById(R.id.tv_subject);
            tvMyMessageContent = (TextView) view.findViewById(R.id.my_message_content);
            // img_Attchment = (ImageView) view.findViewById(R.id.img_attchment);
            // text_Img = (CircularImageView) view.findViewById(R.id.text_img);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SentMessageAdapter.clickListener = clickListener;
    }


    public SentMessageAdapter(ArrayList<SentMessageDTO.Message> arrayList, Context Mcontext) {
        this.Mcontext = Mcontext;
        this.arrayList = arrayList;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

    }
}
