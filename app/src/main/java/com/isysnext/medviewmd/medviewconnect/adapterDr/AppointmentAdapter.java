package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.PatientVisitDTO;
import com.isysnext.medviewmd.medviewconnect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
 ArrayList<PatientVisitDTO.Callqueue> arrayList;
    private static ClickListener clickListener;
    Context Mcontext;
    @Override
    public AppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_list_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(AppointmentAdapter.MyViewHolder holder, int position) {
        try {
            SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat sdfmt2= new SimpleDateFormat("dd-MM-yyyy");
            Date dDate = sdfmt1.parse(arrayList.get(position).getBirthday());
            String outDate = sdfmt2.format(dDate);
            holder.date.setText(""+outDate);
          //  System.out.println("Old Format :   " + originalFormat.format(date));

        } catch (ParseException ex) {
            // Handle Exception.
        }
        holder.name.setText(arrayList.get(position).getName());
        holder.state.setText(arrayList.get(position).getState());
        holder.time.setText(arrayList.get(position).getBirthday());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView name, date, time,state;
        public ImageView patientIntake;

        public MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            name = (TextView) view.findViewById(R.id.appoint_patient_name);
            date = (TextView) view.findViewById(R.id.appoint_date);
            time = (TextView) view.findViewById(R.id.appoint_time);
            state = (TextView) view.findViewById(R.id.appoint_state);
            patientIntake = (ImageView) view.findViewById(R.id.patient_intake);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
        public void setOnItemClickListener(ClickListener clickListener)
        {
            AppointmentAdapter.clickListener = clickListener;
        }


    public AppointmentAdapter(ArrayList<PatientVisitDTO.Callqueue> arrayList, Context Mcontext) {
        this.Mcontext = Mcontext;
        this.arrayList = arrayList;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
