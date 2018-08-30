package com.isysnext.medviewmd.medviewconnect;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.doctor.Splash;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;

public class MainActivity extends AppCompatActivity {

    //Declaration of variables
    Context context;
    Toolbar toolbar;
    TextView mTitle;
    AppSession appSession;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        context=this;
        appSession = new AppSession(this);
        initView();
        //initToolBar();
        showFragment(new Splash(),"Splash");
    }

    //Method for initializing view
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_splash);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
        else
        {

        }
    }

    //Method for opening fragment
    private void showFragment(Fragment targetFragment, String className) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
