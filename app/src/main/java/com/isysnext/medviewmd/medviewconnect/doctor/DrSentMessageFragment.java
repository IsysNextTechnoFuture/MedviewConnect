package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.SentMessageAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.SentMessageDTO;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrSentMessageFragment extends Fragment implements AppConstants {
    static Context Mcontext;
    private RecyclerView sentMessageList;
    private SentMessageAdapter adapter;
    private Fragment fragment;
    private int positon,history,appointment;
    private Utilities utilities;
    private AppSession appSession;
    private Bundle bundle;
    private ArrayList<SentMessageDTO.Message> arrayListMyMessage;
    ProgressDialog mProgressDialog;
    static FragmentManager fm;
    private View view;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrSentMessageFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dr_sent_message, container, false);
        Mcontext = getActivity();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(Mcontext);
        arrayListMyMessage = new ArrayList<>();
        InitView();
        bundle=new Bundle();
        callMessageListFromServer();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        return view;
    }

    // to initialize view
    public void InitView() {
        sentMessageList = (RecyclerView) view.findViewById(R.id.sent_message_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        sentMessageList.setLayoutManager(mLayoutManager);
        DrMessageFragment.img_Back.setVisibility(View.GONE);
        DrMessageFragment.tv_Sent_Message.setVisibility(View.VISIBLE);
        DrMessageFragment.tv_My_Message.setText(R.string.my_message);
        DrMessageFragment.tv_My_Message.getResources().getColor(R.color.white);
        DrMessageFragment.tv_Close.setVisibility(View.VISIBLE);
    }
   //for call data from server
    private void callMessageListFromServer() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "",getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url =BASE_URL+MY_MESSAGE_LIST+"/"+String.valueOf(appSession.getUser().getUinfo().getId())+"/"+1;
            mProgressDialog = ProgressDialog.show(Mcontext, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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

                                } else {
                                    Gson gson = new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    if(object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        SentMessageDTO sentMessageDTO = gson.fromJson(response, SentMessageDTO.class);
                                        // tvError.setVisibility(View.GONE);
                                        Log.e("Response---", "" + response);
                                        if (sentMessageDTO.getSuccess().equals(1)) {
                                            if (sentMessageDTO.getMessages() != null) {
                                                if (sentMessageDTO.getMessages().size() > 0) {
                                                    /*appoint_list.setVisibility(View.VISIBLE);
                                                    tvError.setVisibility(View.GONE);
                                                    linear_layout_top.setVisibility(View.GONE);*/
                                                    // providerList.clear();
                                                    arrayListMyMessage.addAll(sentMessageDTO.getMessages());
                                                    setUpRecyclerView();
                                                }
                                            } else {
                                                /*tvError.setVisibility(View.VISIBLE);
                                                appoint_list.setVisibility(View.GONE);
                                                tvError.setText(mJSONObject.optString("msg"));*/
                                            }
                                        } else {
                                           /* linear_layout_top.setVisibility(View.VISIBLE);
                                            appoint_list.setVisibility(View.GONE);
                                            tvError.setVisibility(View.VISIBLE);*/
                                            // tvError.setText(getResources().getString(R.string.no_provider_available));
                                        }
                                    }
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + appSession.getUser().getUinfo().getToken());
                    Log.i("paramsToken---------", "" + params);
                    return params;
                }
            };
            //Add the request to the RequestQueue.
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 1, 1.0f));
            Volley.newRequestQueue(Mcontext).add(stringRequest);
        }
    }

    //set list adapter
    private void setUpRecyclerView() {
        adapter = new SentMessageAdapter(arrayListMyMessage,Mcontext);
        sentMessageList.setAdapter(adapter);

        adapter.setOnItemClickListener(new SentMessageAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DrMessageFragment.sStrFragmentStatusCheck=SENT_MESSAGE_CHECK;
                DrMessageFragment.img_Back.setVisibility(View.VISIBLE);
                DrMessageFragment.tv_My_Message.getResources().getColor(R.color.white);
                DrMessageFragment.tv_My_Message.setText(R.string.back_to_inbox);
                DrMessageFragment.tv_Sent_Message.setVisibility(View.GONE);

                //send data to other fragment
                fragment = DrSentMsgDetail.getInstance(getActivity(),getFragmentManager());
                positon = position;
                bundle.putString("TO_ID",String.valueOf(arrayListMyMessage.get(position).getTo()));
                bundle.putString("char",String.valueOf(""+arrayListMyMessage.get(position).getFromname().charAt(0)));
                bundle.putString("from",String.valueOf(""+arrayListMyMessage.get(position).getFromname()));
                bundle.putString("date",String.valueOf(""+arrayListMyMessage.get(position).getCreatedAt()));
                bundle.putString("subject",String.valueOf(arrayListMyMessage.get(position).getSubject()));
                bundle.putString("detail_msg",String.valueOf(arrayListMyMessage.get(position).getMessage()));

                if(arrayListMyMessage.get(position).getHistory().size()>0)
                {
                    history = 1;
                    bundle.putString("history",""+history);
                    bundle.putString("toname",""+arrayListMyMessage.get(position).getHistory().get(0).getToname());
                    bundle.putString("fromname",""+arrayListMyMessage.get(position).getHistory().get(0).getFromname());
                    bundle.putString("datetime",""+arrayListMyMessage.get(position).getHistory().get(0).getCreatedAt());
                    bundle.putString("msgsubject",""+arrayListMyMessage.get(position).getHistory().get(0).getSubject());
                    bundle.putString("message",""+arrayListMyMessage.get(position).getHistory().get(0).getMessage());
                }
                else
                {
                    history = 0;
                    bundle.putString("history",""+history);
                }
                if (arrayListMyMessage.get(position).getAttachment().size() > 0) {
                    appointment = 1;
                    bundle.putString("attachment", "" +appointment);
                } else {
                    appointment = 0;
                    bundle.putString("attachment", "" +appointment);
                }
                fragment.setArguments(bundle); //data being send to SecondFragment
                getFragmentManager().beginTransaction().replace(R.id.content_frame_message, fragment, "MsgDeatil").addToBackStack(null).commit();
            }
        });
    }
}
