package com.isysnext.medviewmd.medviewconnect.doctor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.ChatDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.DrChatAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;

import java.util.ArrayList;

public class DrChatFragment  extends Fragment implements AppConstants,View.OnClickListener {
    static Context Mcontext;
    static android.support.v4.app.FragmentManager fm;
    private TextView back_appointment;
    private View view;
    private AppSession appSession;
    private DrChatAdapter adapter;
    private RecyclerView chatList;
    private ChatDTO chatDTO;
    private TextView tvChatClose;
    private ArrayList<ChatDTO> arrayList;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrChatFragment();
        return frag;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dr_chat_fragment, container, false);

        Mcontext = getActivity();
        initView();
        setData();

        return view;
    }

    public void initView()
    {
        chatList = (RecyclerView) view.findViewById(R.id.chat_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        chatList.setLayoutManager(mLayoutManager);
        chatList.setItemAnimator(new DefaultItemAnimator());
        tvChatClose = (TextView) view.findViewById(R.id.tv_chat_close);
        tvChatClose.setOnClickListener(this);
        arrayList = new ArrayList<>();

    }
    public void setValues()
    {


    }
    public void setData() {
        chatDTO = new ChatDTO("Gregory House", "12/05/2018", "Hey How r u");
        arrayList.add(chatDTO);
        chatDTO = new ChatDTO("Milinda", "10/05/2018", "Hey where r u");
        arrayList.add(chatDTO);
        chatDTO = new ChatDTO("Milan patel", "09/05/2018", "Hiii all");
        arrayList.add(chatDTO);
        chatDTO = new ChatDTO("Michael", "08/05/2018", "Hellloooo");
        arrayList.add(chatDTO);
        chatDTO = new ChatDTO("Mireya Valdez", "07/05/2018", "where r u");
        arrayList.add(chatDTO);
        adapter = new DrChatAdapter(arrayList,getActivity());
        chatList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (view.getId()) {
            case R.id.tv_chat_close:
               getActivity().getSupportFragmentManager().popBackStack();
                break;

            default:
                break;
        }
    }
}
