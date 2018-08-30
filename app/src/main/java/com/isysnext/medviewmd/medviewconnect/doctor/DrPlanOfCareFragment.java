package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.PatientVisitDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.SpinnerListItem;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.DrSpinnerAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DrPlanOfCareFragment extends Fragment implements View.OnClickListener {
    //Declaration of variables
    static android.support.v4.app.FragmentManager fm;
    static Context mcontext;
    private Spinner spinPatient,spinCall;
    private View view;
    private Utilities utilities;
    static Context Mcontext;
    private ProgressDialog mProgressDialog;
    private AppSession appSession;
    private Button send;
    private EditText etSubjective,etObjective,etAssesment,etPlan,etPrescription;
    private TextView tvSeeMore;
    ArrayList<SpinnerListItem> arrayList;
    private SpinnerListItem item;
    public  static Fragment getInstance(Context context, FragmentManager manger)
    {
        mcontext=context;
        fm = manger;

        Fragment frag = new DrPlanOfCareFragment();
        return frag;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dr_plan_of_care,container,false);
        Mcontext = getActivity();
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(Mcontext);
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        initView();
        callDataFromServer();
        setData();
        return view;
    }
    //to initialize view
    public void initView()
    {
        spinPatient = (Spinner) view.findViewById(R.id.spin_patient);
        spinCall = (Spinner) view.findViewById(R.id.spin_call);
        etSubjective = (EditText) view.findViewById(R.id.et_subjective);
        etObjective = (EditText) view.findViewById(R.id.et_objective);
        etAssesment = (EditText) view.findViewById(R.id.et_assesment);
        etPlan = (EditText) view.findViewById(R.id.et_plan);
        etPrescription = (EditText) view.findViewById(R.id.et_prescription);
        send = (Button) view.findViewById(R.id.send);
        tvSeeMore = (TextView) view.findViewById(R.id.tv_see_more);
        send.setOnClickListener(this);
    }
    //set arrayList Data
    public void setData()
    {
        arrayList = new ArrayList<>();
        arrayList.add(new SpinnerListItem("One"));
        arrayList.add(new SpinnerListItem("Two"));
        arrayList.add(new SpinnerListItem("Three"));
        arrayList.add(new SpinnerListItem("Four"));

        spinPatient.setAdapter(new DrSpinnerAdapter(arrayList,mcontext));
        spinCall.setAdapter(new DrSpinnerAdapter(arrayList,mcontext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send:
                getFragmentManager().popBackStack();
            break;

            case R.id.tv_see_more:
                getFragmentManager().popBackStack();
                break;

            default:
                break;
        }
    }

    public void callDataFromServer() {
        //Method for calling volley web service for patient intake
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            Log.i("TOKAN---------", appSession.getUser().getUinfo().getToken());
            /**
             **/
            //   String url = AppConstants.BASE_URL+AppConstants.INTAKE_PATIENT+"/" + AppConstants.PARTNER_ID + "/" + String.valueOf(appSession.getUser().getUinfo().getProviderId()) + "/" + patientVisitDTO.getCallqueue().get(Integer.parseInt(position)).getWaitingroomId();
            final String url ="https://alphatelemed-api.noemaplatform.com/api/ios/patient/getIntakeFormdata/5/51/109";
            //  final String url = AppConstants.BASE_URL+AppConstants.INTAKE_PATIENT+"/"+AppConstants.PARTNER_ID +"/"+String.valueOf(appSession.getUser().getUinfo().getProviderId())+"/"+String.valueOf(waitnig_room_id);
            //  final String url = AppConstants.BASE_URL+AppConstants.INTAKE_PATIENT+"/"+"5"+"/"+"51"+"/"+"109";
            //  https://medviewmd-api.noemaplatform.com/api/ios/patient/getIntakeFormdata/5/51/108
            //   String url = "https://medviewmd-api.noemaplatform.com/api/ios/patient/getIntakeFormdata/"+AppConstants.PARTNER_ID+"/"+String.valueOf(appSession.getUser().getUinfo().getProviderId())+String.valueOf(patientVisitDTO.getCallqueue().get(Integer.parseInt(position)).getWaitingroomId());
            mProgressDialog = ProgressDialog.show(Mcontext, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            //Request a string response for getting class name of spinner from the provided URL
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("url---",url);
                            Log.i("ResponseA---------",response);
                            try {
                                if (mProgressDialog != null && mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response == null) {
                                    utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops),Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    //appoint_list.setVisibility(View.GONE);

                                } else {
                                    Gson gson = new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    if (object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        PatientVisitDTO patientVisitDTO = gson.fromJson(response, PatientVisitDTO.class);
                                        Log.e("Response---", "" + response);
                                        if (patientVisitDTO.getSuccess().equals(1)) {
                                            if (patientVisitDTO.getCallqueue() != null) {
                                                if (patientVisitDTO.getCallqueue().size() > 0) {

                                                    // providerList.clear();
                                                    //  arrayListPatientVisit.addAll(patientVisitDTO.getCallqueue());
                                                    // setUpRecyclerView();
                                                }
                                            } else {
                                                //  tvError.setVisibility(View.VISIBLE);
                                                //appoint_list.setVisibility(View.GONE);
                                                //tvError.setText(mJSONObject.optString("msg"));
                                            }
                                        } else {
                                            //appoint_list.setVisibility(View.GONE);
                                            //tvError.setVisibility(View.VISIBLE);
                                            //tvError.setText(getResources().getString(R.string.no_provider_available));
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
                    //mTextView.setText("That didn't work!");
                }
            }) {
                /* @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> map = new HashMap<String, String>();
                     map.put("partner_id",""+AppConstants.PARTNER_ID);
                     map.put("provider_id",""+String.valueOf(appSession.getUser().getUinfo().getProviderId()));
                     map.put("waitingroom_id",""+waitnig_room_id);
                     Log.i("waitning room---------", ""+waitnig_room_id);
                     return map;
                 }*/
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Content-Type","application/json");
                    params.put("Authorization", "Bearer " + appSession.getUser().getUinfo().getToken());
                    //params.put("Accept","application/json");
                   /* String auth = "Basic "
                           + Base64.encodeToString(appSession.getUser().getUinfo().getToken().getBytes(), Base64.NO_WRAP);
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", auth);*/
                    Log.i("paramsToken---------", "" + params);
                    return params;
                }
            };
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(Mcontext).add(stringRequest);
        }
    }
}
