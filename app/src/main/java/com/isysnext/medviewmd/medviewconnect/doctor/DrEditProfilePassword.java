package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.HashMap;
import java.util.Map;

public class DrEditProfilePassword extends Fragment implements AppConstants, View.OnClickListener {
    //Declaration of variables
    private View parentView;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private TextView tvProfile,tvSecurityQuestion;
    private EditText etCurrentPassword,etNewPassword, etReTypePassword;
    private InputMethodManager mgr;
    private AppCompatButton btnChange, btnClose;
    private String strCurrentPassword = "", strNewPassword = "", strReTypePassword = "";
    APIInterface apiInterface;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.dr_edit_profile_password, container, false);
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
    private void initView() {
        tvProfile = (TextView) parentView.findViewById(R.id.tv_profile);
        tvProfile.setOnClickListener(this);
        tvSecurityQuestion = (TextView) parentView.findViewById(R.id.tv_security_question);
        tvSecurityQuestion.setOnClickListener(this);
        etCurrentPassword = (EditText) parentView.findViewById(R.id.et_current_password);
        etNewPassword = (EditText) parentView.findViewById(R.id.et_new_password);
        etReTypePassword = (EditText) parentView.findViewById(R.id.et_retype_password);
        btnChange = (AppCompatButton) parentView.findViewById(R.id.btn_change);
        btnChange.setOnClickListener(this);
        btnClose = (AppCompatButton) parentView.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
    }
    //Method for setting values
    private void initValues() {
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(etCurrentPassword.getWindowToken(), 0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_profile:
                showFragment(new DrEditProfileGeneralFragment(),"DrEditProfileGeneral");
                break;
            case R.id.tv_security_question:
                showFragment(new DrEditProfileSecurityFragment(),"DrEditProfileSecurity");
                break;
            case R.id.btn_change:
                getEditTextEnteredValues();
                if (isValid()) {
                    mgr.hideSoftInputFromWindow(etCurrentPassword.getWindowToken(), 0);
                    callVolleyPassword();
                }
                break;
            case R.id.btn_close:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }
    //Method to validation
    public boolean isValid() {
        if (strCurrentPassword == null || strCurrentPassword.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_password), getString(R.string.ok), false);
            etCurrentPassword.requestFocus();
            return false;
        }
        if (strCurrentPassword.length() < 8) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_current_password), getString(R.string.ok), false);
            etCurrentPassword.requestFocus();
            return false;
        }
        else if (strNewPassword == null || strNewPassword.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_new_password), getString(R.string.ok), false);
            etNewPassword.requestFocus();
            return false;
        }
        if (strNewPassword.length() < 8) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_new_password), getString(R.string.ok), false);
            etNewPassword.requestFocus();
            return false;
        }else if (strReTypePassword == null || strReTypePassword.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_re_type_password), getString(R.string.ok), false);
            etReTypePassword.requestFocus();
            return false;
        } if (!strReTypePassword.equals(strNewPassword)) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.new_password_and_retype_password_does_not_match), getString(R.string.ok), false);
            etReTypePassword.requestFocus();
            return false;
        }
        return true;
    }
    //Method for getting values of edit text
    private void getEditTextEnteredValues() {
        strCurrentPassword = etCurrentPassword.getText().toString().trim();
        strNewPassword = etNewPassword.getText().toString().trim();
        strReTypePassword = etReTypePassword.getText().toString().trim();
    }
    //Method for calling volley web service of edit profile password
    private void callVolleyPassword() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url =BASE_URL+EDIT_PROFILE_PASSWORD;
            mProgressDialog = ProgressDialog.show(context, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("TOKAN---------", appSession.getUser().getUinfo().getToken());
                            Log.i("ResponseA---------", response);
                            try {
                                if (mProgressDialog != null && mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response == null) {
                                    utilities.dialogOK(context, context.getResources().getString(R.string.Whoops), context.getResources().getString(R.string.server_error), context.getString(R.string.ok), false);
                                } if (response.equals("")) {
                                    utilities.dialogOK(context, getResources().getString(R.string.Whoops),getResources().getString(R.string.server_error), getString(R.string.ok), false);
                                }
                                else {
                                    Object object = new JSONTokener(response).nextValue();
                                    if(object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        if(mJSONObject.optInt("success")==1)
                                        {
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                        else
                                        {
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                    }
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("current_password",strCurrentPassword);
                    params.put("new_password",strNewPassword);
                    Log.i("ServerParameter-------", "" + params);
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + appSession.getUser().getUinfo().getToken());
                    Log.i("paramsToken---------", "" + params);
                    return params;
                }
            };
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(context).add(stringRequest);
        }
    }
    //Method for opening fragment
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}

