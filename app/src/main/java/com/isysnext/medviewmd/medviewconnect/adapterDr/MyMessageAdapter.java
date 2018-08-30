package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.isysnext.medviewmd.medviewconnect.modelDr.MyMessageDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import java.util.ArrayList;


public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.MyViewHolder> {
    ArrayList<MyMessageDTO.Message> arrayList;
    private static MyMessageAdapter.ClickListener clickListener;
    Context Mcontext;

    @Override
    public MyMessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dr_my_message_list_item, parent, false);
        return new MyMessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tvMsgName.setText(arrayList.get(position).getFromname());
        holder.tvMsgDate.setText(arrayList.get(position).getCreatedAt());
       // holder.msg_Time.setText(arrayList.get(position).getTime());
        holder.tvDepart.setText(arrayList.get(position).getSubject());
        holder.tvMyMessageContent.setText(arrayList.get(position).getMessage());
        holder.tvNameText.setText(""+arrayList.get(position).getFromname().charAt(0));
        if(arrayList.get(position).getRead()==1)
        {
            holder.tvMsgName.getResources().getColor(R.color.green);
            holder.tvMsgDate.getResources().getColor(R.color.green);
            holder.tvDepart.getResources().getColor(R.color.green);
            holder.tvMyMessageContent.getResources().getColor(R.color.green);
        }
        else
            {
                holder.tvMsgName.getResources().getColor(R.color.white);
                holder.tvMsgDate.getResources().getColor(R.color.white);
                holder.tvDepart.getResources().getColor(R.color.white);
                holder.tvMyMessageContent.getResources().getColor(R.color.white);
        }

        if (arrayList.get(position).getAttachment().size()==0)
        {
            holder.imgAttchment.setVisibility(View.GONE);
        }
        else
        {
            holder.imgAttchment.setVisibility(View.VISIBLE);
        }
       // holder.my_List_Img.setImageResource(arrayList.get(position).);

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
        public TextView tvNameText,tvMsgName,tvMsgTime,tvMsgDate,tvDepart,tvMyMessageContent;
        public ImageView imgAttchment;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            tvNameText = (TextView) view.findViewById(R.id.tv_name_text);
            tvMsgName = (TextView) view.findViewById(R.id.tv_msg_name);
            tvMsgTime = (TextView) view.findViewById(R.id.msg_time);
            tvMsgDate = (TextView) view.findViewById(R.id.msg_date);
            tvDepart = (TextView) view.findViewById(R.id.tv_depart);
            tvMyMessageContent = (TextView) view.findViewById(R.id.my_message_content);
            imgAttchment = (ImageView) view.findViewById(R.id.img_attchment);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyMessageAdapter.ClickListener clickListener) {
        MyMessageAdapter.clickListener = clickListener;
    }


    public MyMessageAdapter(ArrayList<MyMessageDTO.Message> arrayList, Context Mcontext) {
        this.Mcontext = Mcontext;
        this.arrayList = arrayList;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

    }

}