package com.isysnext.medviewmd.medviewconnect;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.isysnext.medviewmd.medviewconnect.modelDr.UserDTO;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 06/04/18.
 */
public class SignIn extends Fragment implements View.OnClickListener,AppConstants
{
    //Declaration of variables
    private View parentView;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private EditText etEmail, etPassword;
    private TextView tvCreateAnAccount, tvForgotPassword;
    private String strEmail = "", strPassword = "";
    private InputMethodManager mgr;
    private AppCompatButton btnLogin;
    APIInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.sign_in, container, false);
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
        //initToolbar();
        initView();
        setValues();
    }

    //Method for initializing toolbar
  /*  private void initToolbar() {
        android.support.v7.app.ActionBar actionBar = ((DashboardActivity) context).getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(false);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.transparent));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayShowTitleEnabled(false);
    }*/

    //Method for initializing view
    private void initView() {
        etEmail = (EditText) parentView.findViewById(R.id.et_email);
        etPassword = (EditText) parentView.findViewById(R.id.et_password);
        tvCreateAnAccount = (TextView) parentView.findViewById(R.id.tv_create_an_account);
        tvCreateAnAccount.setOnClickListener(this);
        tvForgotPassword = (TextView) parentView.findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setOnClickListener(this);
        btnLogin = (AppCompatButton) parentView.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }
    //Method for setting values
    private void setValues() {
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        mgr.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgot_password:
               // showFragment(new ForgotPassword(),"ForgetPassword");
                break;
            case R.id.btn_login:
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();
                if(isValid()) {
                    mgr.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
                    callRetrofitSignIn();
                }
                break;
            case R.id.tv_create_an_account:
               // showFragment(new SignUp(), "SignUp");
                break;
            default:
                break;
        }
    }

    //Method to validation
    public boolean isValid() {
        if (strEmail == null || strEmail.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.email_empty), getString(R.string.ok), false);
            etEmail.requestFocus();
            return false;
        } else if (!utilities.checkEmail(strEmail)) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_email), getString(R.string.ok), false);
            etEmail.requestFocus();
            return false;
        }
        if (strPassword == null || strPassword.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_password), getString(R.string.ok), false);
            etPassword.requestFocus();
            return false;
        }else if(strPassword.length() < 8) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_password), getString(R.string.ok), false);
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    //Method for calling retrofit web service of sign in
    private void callRetrofitSignIn() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            /**
             POST sign in Url encoded.
             **/
            Call<UserDTO> call = apiInterface.signIn(strEmail,strPassword,PARTNER_ID);
            call.enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    try {
                        if (response.body() != null) {
                                UserDTO userDTO = response.body();
                                Log.e("Response---", "" + response.body());
                                if(userDTO.getSuccess()==1) {
                                    appSession.setUser(userDTO);
                                    appSession.setLogin(true);
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
                                }
                                else
                                {
                                    utilities.dialogOK(context, getResources().getString(R.string.Whoops),userDTO.getMessage(), getString(R.string.ok), false);
                                }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    call.cancel();
                }
            });
        }
    }

    //Method for opening fragment
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("SignIn")
                .commit();
    }
}
