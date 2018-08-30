package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.SpinnerListItem;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.DrSpinnerAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    static android.support.v4.app.FragmentManager fm;
    static Context mcontext;
    Spinner spin_patient,spin_call;
    View view;
    DrSpinnerAdapter adapter;
    private Utilities utilities;
    private ProgressDialog mProgressDialog;
    private AppSession appSession;
    Button send;
    EditText et_Subjective,et_Objective,et_Assesment,et_Plan,et_Prescription;
    TextView tv_See_More;
    ArrayList<SpinnerListItem> arrayList;
    SpinnerListItem item;

    public  static Fragment getInstance(Context context, FragmentManager manger)
    {
        mcontext=context;
        fm = manger;

        Fragment frag = new TestFragment();
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.test_frag,container,false);
        mcontext = getActivity();
        return view;
    }
}
