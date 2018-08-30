package com.isysnext.medviewmd.medviewconnect.doctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.SignIn;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;


/**
 * Created by Harsh on 05/04/18.
 */
public class Splash extends Fragment implements AppConstants
{
    //Declaration of variables
    Context context;
    public static final int SPLASH_DELAY = 3; // in second
    private RelativeLayout rlMain;
    Utilities utilities;
    AppSession appSession;
    Handler handler;
    Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        appSession = new AppSession(getActivity());
        utilities = Utilities.getInstance(getContext());
        intiView(view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        handleThread();
    }
    //Method for initializing view
    private void intiView(View view) {
        // rlMain = (LinearLayout) view.findViewById(R.id.rl_main);
        //rlMain.setBackgroundColor(Color.TRANSPARENT);
    }

    //Method for handling thread of splash
    private void handleThread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(appSession.isLogin()) {
                    if(appSession.getUser().getUinfo().getProviderId()==0) {
                        Intent intent = new Intent(context, DashboardActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else
                    {
                        Intent intent = new Intent(context, DashboardActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    try {
                        showFragment(new SignIn(), "SignIn");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, SPLASH_DELAY * 1000);
    }
    //Interface for checking success on base task
    public interface OnTaskResult {
        void onSuccess();
    }
    //Method for showing fragment
    private void showFragment(Fragment targetFragment, String className) {
        try{
            if (getActivity()!=null){
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (ft!=null){
                    ft.replace(R.id.container, targetFragment, className);
                    ft.commitAllowingStateLoss();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
