package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.isysnext.medviewmd.medviewconnect.modelDr.ChatDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import java.util.ArrayList;

public class DrChatAdapter extends RecyclerView.Adapter<DrChatAdapter.MyViewHolder> {
    ArrayList<ChatDTO> arrayList;
    private static DrChatAdapter.ClickListener clickListener;
    Context Mcontext;

    @Override
    public DrChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_chat_list, parent, false);
        return new DrChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrChatAdapter.MyViewHolder holder, int position) {

        holder.tvSenderName.setText(arrayList.get(position).getSenderName());
        holder.tvDateTime.setText(arrayList.get(position).getDateTime());
        holder.tvMsg.setText(arrayList.get(position).getMessage());

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
        public TextView tvSenderName, tvDateTime,tvMsg;
        public ImageView patient_intake;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            tvSenderName = (TextView) view.findViewById(R.id.tvSenderName);
            tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
            tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
            tvMsg = (TextView) view.findViewById(R.id.tvMsg);
            patient_intake = (ImageView) view.findViewById(R.id.patient_intake);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(DrChatAdapter.ClickListener clickListener) {
        DrChatAdapter.clickListener = clickListener;
    }
    public DrChatAdapter(ArrayList<ChatDTO> arrayList, Context Mcontext) {
        this.Mcontext = Mcontext;
        this.arrayList = arrayList;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

    }
}
