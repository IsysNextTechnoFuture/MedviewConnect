package com.isysnext.medviewmd.medviewconnect.doctor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;

public class DrMessageFragment extends Fragment implements View.OnClickListener,AppConstants
{
    static Context Mcontext;
    static FragmentManager fm;
    public static TextView tv_My_Message,tv_Sent_Message,tv_Close;
    public static ImageView img_Back;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private View view;
    private String strFavouriteClickCheck = OFF;
    static String sStrFragmentStatusCheck;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrMessageFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dr_message_fragment, container, false);
        Mcontext = getActivity();
        fragmentManager = getFragmentManager();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        InitView();
        // retain this fragment
        setRetainInstance(true);
        return view;
    }

    //to initialize view
    public void InitView() {

        tv_My_Message = (TextView) view.findViewById(R.id.tv_my_message);
        tv_Sent_Message = (TextView) view.findViewById(R.id.tv_sent_message);
        tv_Close = (TextView) view.findViewById(R.id.tv_close);
        img_Back = (ImageView) view.findViewById(R.id.img_back);

        tv_My_Message.setText(R.string.my_message);
        tv_Close.setVisibility(View.VISIBLE);
        img_Back.setVisibility(View.GONE);
        tv_Sent_Message.setVisibility(View.VISIBLE);

        //on click event
        tv_Close.setOnClickListener(this);
        tv_My_Message.setOnClickListener(this);
        tv_Sent_Message.setOnClickListener(this);
        callFragment();
    }

    // to call My Message fragment
    public void callFragment()
    {
        if (sStrFragmentStatusCheck == null) {
            tv_My_Message.setTextColor(getResources().getColor(R.color.green));
            fragment = new DrMyMessageFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame_message, fragment, "MyMessage").addToBackStack(null).commit();
            ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
            tv_Close.setVisibility(View.VISIBLE);
            strFavouriteClickCheck = OFF;
        } else {
            if (sStrFragmentStatusCheck.equals(MY_MESSAGE_CHECK)) {
                tv_My_Message.setTextColor(getResources().getColor(R.color.green));
                fragment = new DrMyMessageFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame_message, fragment, "MyMessage").addToBackStack(null).commit();
                ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
                tv_Close.setVisibility(View.VISIBLE);
                strFavouriteClickCheck = OFF;
            } else {
                tv_My_Message.setTextColor(getResources().getColor(R.color.white));
                tv_Sent_Message.setTextColor(getResources().getColor(R.color.green));
                fragment = new DrSentMessageFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame_message, fragment, "SentMessage").addToBackStack(null).commit();
                tv_Close.setVisibility(View.VISIBLE);
                strFavouriteClickCheck = ON;
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
              getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_my_message:
                if (strFavouriteClickCheck.equals(ON)) {
                    tv_Sent_Message.setTextColor(getResources().getColor(R.color.white));
                    tv_My_Message.setTextColor(getResources().getColor(R.color.green));
                    fragment = DrMyMessageFragment.getInstance(getActivity(), fragmentManager);
                    fragmentManager.beginTransaction().replace(R.id.content_frame_message, fragment, "MyMessage").addToBackStack(null).commit();
                    strFavouriteClickCheck=OFF;
                }
                break;

            case R.id.tv_sent_message:
                if (strFavouriteClickCheck.equals(OFF)) {
                    tv_My_Message.setTextColor(getResources().getColor(R.color.white));
                    tv_Sent_Message.setTextColor(getResources().getColor(R.color.green));
                    fragment = DrSentMessageFragment.getInstance(getActivity(), fragmentManager);
                    fragmentManager.beginTransaction().replace(R.id.content_frame_message, fragment, "SentMessage").addToBackStack(null).commit();
                    strFavouriteClickCheck=ON;
                }
                break;

            default:
                break;
        }
    }

    //Method for opening fragment
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
