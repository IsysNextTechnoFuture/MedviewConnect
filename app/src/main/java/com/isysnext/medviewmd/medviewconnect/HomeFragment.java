package com.isysnext.medviewmd.medviewconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.isysnext.medviewmd.medviewconnect.adapterDr.AppointmentAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.AppointmentItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    static Context Mcontext;
    FragmentManager fragmentManager;
    static android.support.v4.app.FragmentManager fm;
    private ImageView back_appointment;
    View view;
    private RecyclerView appoint_list;
    private ArrayList<AppointmentItem> arrayList;
    private AppointmentItem item;
    ImageView appoint_patient;
    private AppointmentAdapter mAdapter;


    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new HomeFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment, container, false);

        fragmentManager = getChildFragmentManager();
        appoint_patient = (ImageView) view.findViewById(R.id.appoint_patient);
        appoint_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                Fragment fragment = null;
              //  fragment = AppointmentFragment.getInstance(getActivity(), fragmentManager);
              //  fragmentManager.beginTransaction().replace(R.id.container, fragment, "Message").addToBackStack(null).commit();
            }

        });
        return view;
    }
}
