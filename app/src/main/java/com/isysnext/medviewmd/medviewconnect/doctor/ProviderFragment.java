package com.isysnext.medviewmd.medviewconnect.doctor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;


public class ProviderFragment extends Fragment implements View.OnClickListener, AppConstants {
    //Declaration of variables
    private View parentView;
    static Context context;
    static android.support.v4.app.FragmentManager fm;
    private Utilities utilities;
    private AppSession appSession;
    private TextView tvFavorite,tvProvider;
    private String strFavouriteClickCheck=OFF;
    static String sStrFragmentStatusCheck;

    public static Fragment getInstance(Context contxt, FragmentManager FM) {
        context = contxt;
        fm = FM;
        Fragment frag = new ProviderFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.provider_fragment, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(context);
        initView();
        initValues();
    }

    //Method for initializing view
    public void initView() {
        tvFavorite = (TextView) parentView.findViewById(R.id.tv_favorite_provider);
        tvFavorite.setOnClickListener(this);
        tvProvider = (TextView) parentView.findViewById(R.id.tv_provider);
        tvProvider.setOnClickListener(this);
    }

    //Method for initializing values
    private void initValues() {
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        if(sStrFragmentStatusCheck==null) {
            tvProvider.setTextColor(getResources().getColor(R.color.forgot_pasword));
            showFragment(new HomeFavoriteProviderList(), "HomeFavoriteProviderList");
            tvFavorite.setTextColor(getResources().getColor(R.color.forgot_pasword));
            tvProvider.setTextColor(getResources().getColor(R.color.lighter_gray));
            strFavouriteClickCheck = OFF;
        }
        else
        {
            if (sStrFragmentStatusCheck.equals(FAVOURITE)) {
                tvProvider.setTextColor(getResources().getColor(R.color.forgot_pasword));
                showFragment(new HomeFavoriteProviderList(), "HomeFavoriteProviderList");
                tvFavorite.setTextColor(getResources().getColor(R.color.forgot_pasword));
                tvProvider.setTextColor(getResources().getColor(R.color.lighter_gray));
                strFavouriteClickCheck = OFF;
            } else {
                tvProvider.setTextColor(getResources().getColor(R.color.lighter_gray));
                showFragment(new HomeProviderList(), "HomeProviderList");
                tvFavorite.setTextColor(getResources().getColor(R.color.lighter_gray));
                tvProvider.setTextColor(getResources().getColor(R.color.forgot_pasword));
                strFavouriteClickCheck = ON;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_favorite_provider:
                if(strFavouriteClickCheck.equals(ON)) {
                    tvFavorite.setTextColor(getResources().getColor(R.color.forgot_pasword));
                    tvProvider.setTextColor(getResources().getColor(R.color.lighter_gray));
                    showFragment(new HomeFavoriteProviderList(), "HomeFavoriteProviderList");
                    strFavouriteClickCheck=OFF;
                }
                break;
            case R.id.tv_provider:
                if(strFavouriteClickCheck.equals(OFF)) {
                    tvProvider.setTextColor(getResources().getColor(R.color.forgot_pasword));
                    tvFavorite.setTextColor(getResources().getColor(R.color.lighter_gray));
                    showFragment(new HomeProviderList(), "HomeProviderList");
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
                .replace(R.id.fl_home, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
