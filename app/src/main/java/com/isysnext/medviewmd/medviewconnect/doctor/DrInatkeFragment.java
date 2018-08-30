package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.AppointmentItem;
import com.isysnext.medviewmd.medviewconnect.modelDr.PatientVisitDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.AppointmentAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrInatkeFragment extends Fragment implements View.OnClickListener {
    static Context Mcontext;
    static android.support.v4.app.FragmentManager fm;
    View view;
    TextView tv_Intake_Seemore;
    private Utilities utilities;
    private Bundle bundle;
    private ArrayList<PatientVisitDTO.Callqueue> arrayListPatientVisit;
    private ProgressDialog mProgressDialog;
    private ArrayList<AppointmentItem> arrayList;
    private AppSession appSession;
    private PatientVisitDTO patientVisitDTO;
    private Button close;
    private String position,waitnig_room_id;
    private EditText et_Purpose, et_Allergies, et_Current_medication, et_Prfred_pharmacy;
    private ImageView patient_Files_Img, btn_Cancer_Switch, btn_Heart_Diese, btn_Stroke, btn_High_Bp, btn_Cholestrol,
            btn_Asthma, btn_Depression, btn_Artritis, btn_Thyroid, btn_Pregnant, btn_Other,
            btn_Fevr, btn_Weight, btn_Dificulty, btn_Loss_Appetite, btn_Mood, btn_Fatigue;
    private TextView tv_Lp_Text;
    private AppointmentAdapter mAdapter;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrInatkeFragment();
        return frag;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dr_intake, container, false);
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(Mcontext);
        arrayListPatientVisit = new ArrayList<>();
        patientVisitDTO = new PatientVisitDTO();
        Mcontext = getActivity();
        initView();
        callDataFromServer();
        bundle=getArguments();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
//        position = (String.valueOf(bundle.getString("postion")));
        if (bundle!= null) {
            waitnig_room_id = bundle.getString("waiting_room_id");
        }
        return view;
    }

    public void initView() {
        //to initialize view
        close = view.findViewById(R.id.close);
        tv_Intake_Seemore = (TextView) view.findViewById(R.id.tv_see_more);
        et_Purpose = (EditText) view.findViewById(R.id.et_purpose);
        et_Allergies = (EditText) view.findViewById(R.id.et_allergies);
        et_Current_medication = (EditText) view.findViewById(R.id.et_current_medication);
        et_Prfred_pharmacy = (EditText) view.findViewById(R.id.et_prfred_pharmacy);
        patient_Files_Img =(ImageView) view.findViewById(R.id.patient_files_img);
        btn_Cancer_Switch = (ImageView)view.findViewById(R.id.btn_cancer_switch);
        btn_Heart_Diese = (ImageView)view.findViewById(R.id.btn_heart_diese);
        btn_Stroke =(ImageView) view.findViewById(R.id.btn_stroke);
        btn_High_Bp = (ImageView) view.findViewById(R.id.btn_high_bp);
        btn_Cholestrol =(ImageView) view.findViewById(R.id.btn_cholestrol);
        btn_Asthma =(ImageView) view.findViewById(R.id.btn_asthma);
        btn_Depression =(ImageView) view.findViewById(R.id.btn_depression);
        btn_Artritis =(ImageView) view.findViewById(R.id.btn_artritis);
        btn_Thyroid =(ImageView) view.findViewById(R.id.btn_thyroid);
        btn_Pregnant =(ImageView) view.findViewById(R.id.btn_pregnant);
        btn_Other =(ImageView) view.findViewById(R.id.btn_other);
        btn_Fevr =(ImageView) view.findViewById(R.id.btn_fevr);
        btn_Weight =(ImageView) view.findViewById(R.id.btn_weight);
        btn_Dificulty =(ImageView) view.findViewById(R.id.btn_dificulty);
        btn_Loss_Appetite =(ImageView) view.findViewById(R.id.btn_loss_appetite);
        btn_Mood =(ImageView) view.findViewById(R.id.btn_mood);
        btn_Fatigue =(ImageView) (ImageView) view.findViewById(R.id.btn_fatigue);
        tv_Lp_Text =(TextView) view.findViewById(R.id.tv_lp_text);

        //Click event
        close.setOnClickListener(this);
//        tv_Intake_Seemore.setOnClickListener(this);
        btn_Cancer_Switch.setOnClickListener(this);
        btn_Heart_Diese.setOnClickListener(this);
        btn_Stroke.setOnClickListener(this);
        btn_High_Bp.setOnClickListener(this);
        btn_Cholestrol.setOnClickListener(this);
        btn_Asthma.setOnClickListener(this);
        btn_Depression.setOnClickListener(this);
        btn_Artritis.setOnClickListener(this);
        btn_Thyroid.setOnClickListener(this);
        btn_Pregnant.setOnClickListener(this);
        btn_Other.setOnClickListener(this);
        btn_Fevr.setOnClickListener(this);
        btn_Weight.setOnClickListener(this);
        btn_Dificulty.setOnClickListener(this);
        btn_Loss_Appetite.setOnClickListener(this);
        btn_Mood.setOnClickListener(this);
        btn_Fatigue.setOnClickListener(this);
        tv_Lp_Text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_intake_seemore:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_cancer_switch:
                btn_Cancer_Switch.setImageResource(R.mipmap.btn_switch_on);
                break;
            case R.id.btn_heart_diese:
                btn_Heart_Diese.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_stroke:
                btn_Stroke.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_high_bp:
                btn_High_Bp.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_cholestrol:
                btn_Cholestrol.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_asthma:
                btn_Asthma.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_depression:
                btn_Depression.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_artritis:
                btn_Artritis.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_thyroid:
                btn_Thyroid.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_pregnant:
                btn_Pregnant.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_other:
                btn_Other.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_fevr:
                btn_Fevr.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_weight:
                btn_Weight.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_dificulty:
                btn_Dificulty.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_loss_appetite:
                btn_Loss_Appetite.setImageResource(R.mipmap.btn_switch_off);
                break;
            case R.id.btn_mood:

                if (btn_Mood.getResources().equals(R.mipmap.btn_switch_on))
                {
                    btn_Mood.setImageResource(R.mipmap.btn_switch_off);
                }
                if(btn_Mood.getResources().equals(R.mipmap.btn_switch_off))
                {
                    btn_Mood.setImageResource(R.mipmap.btn_switch_on);
                }
                break;
            case R.id.btn_fatigue:
                if (btn_Fatigue.getResources().equals(R.mipmap.btn_switch_on)) {
                    btn_Fatigue.setImageResource(R.mipmap.btn_switch_off);
                } else {
                    btn_Fatigue.setImageResource(R.mipmap.btn_switch_on);
                }
                break;
            case R.id.tv_lp_text:
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
        //   String url = AppConstants.BASE_URL+AppConstants.INTAKE_PATIENT+"/" + AppConstants.PARTNER_ID + "/" + String.valueOf(appSession.getUser().getUinfo().getProviderId()) + "/" + patientVisitDTO.getCallqueue().get(Integer.parseInt(position)).getWaitingroomId();
           final String url ="https://alphatelemed-api.noemaplatform.com/api/ios/patient/getIntakeFormdata/5/51/108";
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
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("Content-Type", "application/json; charset=UTF-8");

                    params.put("Content-Type","application/json");
                    params.put("Authorization", "Bearer " + appSession.getUser().getUinfo().getToken());
                    Log.i("paramsToken---------", "" + params);
                    return params;
                }
            };
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(Mcontext).add(stringRequest);
        }
    }


}
