package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.PatientVisitDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.SessionTokenDTO;
import com.isysnext.medviewmd.medviewconnect.adapterDr.AppointmentAdapter;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentFragment extends Fragment implements AppConstants, View.OnClickListener {
    static Context Mcontext;
    static android.support.v4.app.FragmentManager fm;
    private TextView tvBackAppointment;
    private View view;
    private AppSession appSession;
    private RecyclerView appointList;
    private Utilities utilities;
    private int Clickposition;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    private ProgressDialog mProgressDialog;
    private Bundle bundle;
    private LinearLayout linear_layout_top;
    private AppointmentAdapter adapter;
    private TextView tvError;
    private ArrayList<PatientVisitDTO.Callqueue> arrayListPatientVisit;
    private String sessionToken;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new AppointmentFragment();
        return frag;
    }
    public static SessionTokenDTO token = new SessionTokenDTO();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appointment_fragment, container, false);
        InitView();
        setValues();
        setData();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        return view;
    }
    //initialize view
    public void InitView() {
        tvBackAppointment = (TextView) view.findViewById(R.id.back_appointment);
        appointList = (RecyclerView) view.findViewById(R.id.appoint_list);
        linear_layout_top = (LinearLayout) view.findViewById(R.id.linear_layout_top);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        appointList.setLayoutManager(mLayoutManager);
        appointList.setItemAnimator(new DefaultItemAnimator());
        tvBackAppointment.setOnClickListener(this);
    }
    //set values
    private void setValues() {
        bundle = new Bundle();
        Mcontext = getActivity();
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(Mcontext);
        arrayListPatientVisit = new ArrayList<>();
        sessionToken = appSession.getUser().getUinfo().getToken();
        token.setToken(sessionToken);
        fragmentManager = getFragmentManager();
    }
    //Method for calling volley web service for patient visit list
    public void setData() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            Log.i("TOKAN---------",appSession.getUser().getUinfo().getToken());
            String url = BASE_URL+GET_VISIT;
            mProgressDialog = ProgressDialog.show(Mcontext, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            //Request a string response for getting class name of spinner from the provided URL
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
                                    utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    appointList.setVisibility(View.GONE);
                                    tvError.setVisibility(View.VISIBLE);
                                    tvError.setText(getResources().getString(R.string.no_provider_available));
                                } else {
                                    Gson gson = new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    if (object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        PatientVisitDTO patientVisitDTO = gson.fromJson(response, PatientVisitDTO.class);
                                        tvError.setVisibility(View.GONE);
                                        Log.e("Response---", ""+response);
                                        if (patientVisitDTO.getSuccess().equals(1)) {
                                            if (patientVisitDTO.getCallqueue() != null) {
                                                if (patientVisitDTO.getCallqueue().size() > 0) {
                                                    appointList.setVisibility(View.VISIBLE);
                                                    tvError.setVisibility(View.GONE);
                                                    linear_layout_top.setVisibility(View.GONE);
                                                    // providerList.clear();
                                                    arrayListPatientVisit.addAll(patientVisitDTO.getCallqueue());
                                                    setUpRecyclerView();
                                                }
                                            } else {
                                                tvError.setVisibility(View.VISIBLE);
                                                appointList.setVisibility(View.GONE);
                                                tvError.setText(mJSONObject.optString("msg"));
                                            }
                                        } else {
                                            linear_layout_top.setVisibility(View.VISIBLE);
                                            appointList.setVisibility(View.GONE);
                                            tvError.setVisibility(View.VISIBLE);
                                            // tvError.setText(getResources().getString(R.string.no_provider_available));
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
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("provider_id", "" + String.valueOf(appSession.getUser().getUinfo().getProviderId()));
                    map.put("speciality", "" + String.valueOf(appSession.getUser().getUinfo().getSpeciality()));
                    map.put("partner_id", "" + PARTNER_ID);
                    return map;
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
            Volley.newRequestQueue(Mcontext).add(stringRequest);
        }
    }
    //set list data
    private void setUpRecyclerView() {
        adapter = new AppointmentAdapter(arrayListPatientVisit, getActivity());
        appointList.setAdapter(adapter);
        adapter.setOnItemClickListener(new AppointmentAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Clickposition = position;

                fragment = DrInatkeFragment.getInstance(getActivity(), fragmentManager);
                //   bundle.putString("postion", String.valueOf(Clickposition));
                bundle.putString("waiting_room_id", String.valueOf(arrayListPatientVisit.get(position).getWaitingroomId()));
                Log.i("waitnigroom---------", "" + String.valueOf(arrayListPatientVisit.get(position).getWaitingroomId()));
                fragment.setArguments(bundle); //data being send to SecondFragment
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "intakeform").addToBackStack(null).commit();
            }
        });
        appointList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (view.getId()) {
            case R.id.back_appointment:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }
}


