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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isysnext.medviewmd.medviewconnect.modelDr.SpinDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.UserDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.CustomSpinnerAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrEditProfileSecurityFragment extends Fragment implements AppConstants, View.OnClickListener {
    //Declaration of variables
    private View parentView;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private ImageView ivUser;
    private TextView tvPassword, tvProfile;
    private Spinner spinCurrentSecurityQuestion, spinNewSecurityQuestion;
    private EditText etCurrentSecurityAnswer, etNewSecurityAnswer;
    private List<HashMap<String, String>> listHMSquirityQuestion;
    private ArrayList<SpinDTO> listSquirityQuestion;
    private InputMethodManager mgr;
    private AppCompatButton btnChange, btnClose;
    private String strCurrentSecurityQuestionId = "", strCurrentSecurityQuestionName = "",
            strNewSecurityQuestionId = "", strNewSecurityQuestionName = "", strCurrentSecurityAnswer = "",
            strNewSecurityAnswer = "";
    APIInterface apiInterface;
    private CustomSpinnerAdapter customSpinnerAdapter;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.dr_edit_profile_security_question, container, false);
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
        tvPassword = (TextView) parentView.findViewById(R.id.tv_password);
        tvPassword.setOnClickListener(this);
        tvProfile = (TextView) parentView.findViewById(R.id.tv_profile);
        tvProfile.setOnClickListener(this);
        spinCurrentSecurityQuestion = (Spinner) parentView.findViewById(R.id.spin_current_squirity_que);
        spinNewSecurityQuestion = (Spinner) parentView.findViewById(R.id.spin_new_squirity_que);
        etCurrentSecurityAnswer = (EditText) parentView.findViewById(R.id.et_current_squirity_ans);
        etNewSecurityAnswer = (EditText) parentView.findViewById(R.id.et_new_squirity_ans);
        btnChange = (AppCompatButton) parentView.findViewById(R.id.btn_change);
        btnChange.setOnClickListener(this);
        btnClose = (AppCompatButton) parentView.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
    }
    //Method for setting values
    private void initValues() {
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(etNewSecurityAnswer.getWindowToken(), 0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        listSquirityQuestion = new ArrayList<>();
        setValueForSecurityQuestionsSpinner(CURRENT_SECURITY_QUESTION);
        setValueForSecurityQuestionsSpinner(NEW_SECURITY_QUESTION);
    }
    //Method for setting values of security questions spinner
    private void setValueForSecurityQuestionsSpinner(String checkSpin) {
        listHMSquirityQuestion = new ArrayList<>();
        //setting for province spinner
        HashMap<String, String> hm = new HashMap<String, String>();
        if (checkSpin.equals(CURRENT_SECURITY_QUESTION)) {
            hm.put("name", context.getResources().getString(R.string.current_security_question));
            hm.put("value", context.getResources().getString(R.string.current_security_question));
        } else {
            hm.put("name", context.getResources().getString(R.string.new_security_question));
            hm.put("value", context.getResources().getString(R.string.new_security_question));
        }
        listHMSquirityQuestion.add(hm);
        listSquirityQuestion.add(new SpinDTO("Your mother's maiden name", "Your mother's maiden name"));
        listSquirityQuestion.add(new SpinDTO("Your first pet's name", "Your first pet's name"));
        listSquirityQuestion.add(new SpinDTO("The name of your elementary school", "The name of your elementary school"));
        listSquirityQuestion.add(new SpinDTO("Your elementary school mascot", "Your elementary school mascot"));
        listSquirityQuestion.add(new SpinDTO("Your best friend's nickname", "Your best friend's nickname"));
        listSquirityQuestion.add(new SpinDTO("Your favorite sports team", "Your favorite sports team"));
        listSquirityQuestion.add(new SpinDTO("Your favorite writer", "Your favorite writer"));
        listSquirityQuestion.add(new SpinDTO("Your favorite actor", "Your favorite actor"));
        listSquirityQuestion.add(new SpinDTO("Your favorite singer", "Your favorite singer"));
        listSquirityQuestion.add(new SpinDTO("Your favorite song", "Your favorite song"));
        listSquirityQuestion.add(new SpinDTO("The name of the street you grew up on", "The name of the street you grew up on"));
        listSquirityQuestion.add(new SpinDTO("Make and model of your first car", "Make and model of your first car"));
        listSquirityQuestion.add(new SpinDTO("The city where you first met your spouse", "The city where you first met your spouse"));
        if (listSquirityQuestion != null) {
            for (SpinDTO squirityQuestion : listSquirityQuestion) {
                hm = new HashMap<String, String>();
                hm.put("name", squirityQuestion.getKey());
                hm.put("value", squirityQuestion.getValue());
                listHMSquirityQuestion.add(hm);
            }
        }
        customSpinnerAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_textview, listHMSquirityQuestion,
                getResources().getColor(R.color.white), "");
        if (checkSpin.equals(CURRENT_SECURITY_QUESTION)) {
            spinCurrentSecurityQuestion.setAdapter(customSpinnerAdapter);

            spinCurrentSecurityQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    strCurrentSecurityQuestionId = listHMSquirityQuestion.get(position).get("name");
                    strCurrentSecurityQuestionName = listHMSquirityQuestion.get(position).get("value");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (appSession.getUser().getUinfo() != null) {
                if (appSession.getUser().getUinfo().getSecurityQ() != null || !appSession.getUser().getUinfo().getSecurityQ().equals("")) {
                    if (listSquirityQuestion.size() > 0) {
                        for (int i = 0; i < listSquirityQuestion.size(); i++)
                            if (listHMSquirityQuestion.get(i).get("name").equals(appSession.getUser().getUinfo().getSecurityQ())) {
                                strCurrentSecurityQuestionId = listHMSquirityQuestion.get(i).get("name");
                                strCurrentSecurityQuestionName = listHMSquirityQuestion.get(i).get("value");
                                spinCurrentSecurityQuestion.setSelection(i);
                            }
                    }
                }
            }
        } else {
            spinNewSecurityQuestion.setAdapter(customSpinnerAdapter);

            spinNewSecurityQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    strNewSecurityQuestionId = listHMSquirityQuestion.get(position).get("name");
                    strNewSecurityQuestionName = listHMSquirityQuestion.get(position).get("value");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_password:
                showFragment(new DrEditProfilePassword(), "EditProfilePassword");
                break;
            case R.id.tv_profile:
                showFragment(new DrEditProfileGeneralFragment(), "EditProfile");
                break;
            case R.id.btn_change:
                getEditTextEnteredValues();
                if (isValid()) {
                    mgr.hideSoftInputFromWindow(etCurrentSecurityAnswer.getWindowToken(), 0);
                    callRetrofitEditSecurityQuestion();
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
        if (strCurrentSecurityQuestionName == null || strCurrentSecurityQuestionName.equals("")
                || strCurrentSecurityQuestionName.equals(getResources().getString(R.string.current_security_question))) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_select_current_squirity_question), getString(R.string.ok), false);
            spinCurrentSecurityQuestion.requestFocus();
            return false;
        } else if (strCurrentSecurityAnswer == null || strCurrentSecurityAnswer.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_current_squirity_answer), getString(R.string.ok), false);
            etCurrentSecurityAnswer.requestFocus();
            return false;
        } else if (strNewSecurityQuestionName == null || strNewSecurityQuestionName.equals("")
                || strNewSecurityQuestionName.equals(getResources().getString(R.string.new_security_question))) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_select_new_squirity_question), getString(R.string.ok), false);
            spinNewSecurityQuestion.requestFocus();
            return false;
        } else if (strNewSecurityAnswer == null || strNewSecurityAnswer.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_new_squirity_answer), getString(R.string.ok), false);
            etNewSecurityAnswer.requestFocus();
            return false;
        }
        return true;
    }

    //Method for getting values of edit text
    private void getEditTextEnteredValues() {
        strCurrentSecurityAnswer = etCurrentSecurityAnswer.getText().toString().trim();
        strNewSecurityAnswer = etNewSecurityAnswer.getText().toString().trim();
    }

    //Method for calling retrofit web service of edit security question
    private void callRetrofitEditSecurityQuestion() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url = BASE_URL+EDIT_PROFILE_SECURITY_QUESTION;
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
                                }
                                if (response.equals("")) {
                                    utilities.dialogOK(context, getResources().getString(R.string.Whoops), getResources().getString(R.string.server_error), getString(R.string.ok), false);
                                } else {
                                    Object object = new JSONTokener(response).nextValue();
                                    if (object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        if (mJSONObject.optInt("success") == 1) {
                                            UserDTO userDTO1 = appSession.getUser();
                                            userDTO1.getUinfo().setSecurityQ(strCurrentSecurityQuestionId);
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops), mJSONObject.optString("message"), getString(R.string.ok), false);
                                        } else {
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops), mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                    }
                                }
                            } catch (Exception e) {
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USER_ID,String.valueOf(appSession.getUser().getUinfo().getId()));
                    params.put("current_security_question", strCurrentSecurityQuestionId);
                    params.put("current_security_answer", strCurrentSecurityAnswer);
                    params.put("new_security_question", strNewSecurityQuestionId);
                    params.put("new_security_answer", strNewSecurityAnswer);
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
