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
import com.isysnext.medviewmd.medviewconnect.adapterDr.MyMessageAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.MyMessageDTO;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrMyMessageFragment extends Fragment implements AppConstants {
    static Context Mcontext;
    private RecyclerView myMessageList;
    private MyMessageAdapter adapter;
    private Fragment fragment;
    private  Bundle bundle;
    private Utilities utilities;
    private AppSession appSession;
    private int postition,history,appointment;
    private ArrayList<MyMessageDTO.Message> arrayListMyMessage;
    private ArrayList<MyMessageDTO.Message> arrayListHistoryMessage;
    static FragmentManager fm;
    private View view;
    private ProgressDialog mProgressDialog;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrMyMessageFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dr_my_message_fragment, container, false);
        Mcontext = getActivity();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        utilities = Utilities.getInstance(getActivity());
        appSession = new AppSession(Mcontext);
        InitView();
        arrayListMyMessage = new ArrayList<>();
        arrayListHistoryMessage = new ArrayList<>();
        bundle=new Bundle();
        callMessageListFromServer();
        return view;
    }

    //to initialize view
    public void InitView() {
        myMessageList = (RecyclerView) view.findViewById(R.id.my_message_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        myMessageList.setLayoutManager(mLayoutManager);
        DrMessageFragment.img_Back.setVisibility(View.GONE);
        DrMessageFragment.tv_Sent_Message.setVisibility(View.VISIBLE);
        DrMessageFragment.tv_My_Message.setText(R.string.my_message);
        DrMessageFragment.tv_My_Message.getResources().getColor(R.color.white);
        DrMessageFragment.tv_My_Message.setTextColor(getResources().getColor(R.color.sky_blue));
    }

    //for call data from server
    private void callMessageListFromServer() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "",getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
           final String url =BASE_URL+MY_MESSAGE_LIST+"/"+String.valueOf(appSession.getUser().getUinfo().getId())+"/"+0;
           // final String url =BASE_URL+MY_MESSAGE_LIST+"/"+222+"/"+0;
            mProgressDialog = ProgressDialog.show(Mcontext, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("URL---------",url);
                            Log.i("TOKAN---------", appSession.getUser().getUinfo().getToken());
                            Log.i("ResponseA---------", response);
                            try {
                                if (mProgressDialog != null && mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response == null) {
                                    utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                } else {
                                    Gson gson = new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    if(object instanceof JSONObject) {
                                        MyMessageDTO myMessageDTO = gson.fromJson(response, MyMessageDTO.class);
                                        Log.e("Response---", "" + response);
                                        if (myMessageDTO.getSuccess().equals(1)) {
                                            if (myMessageDTO.getMessages() != null) {
                                                if (myMessageDTO.getMessages().size() > 0) {
                                                    arrayListMyMessage.addAll(myMessageDTO.getMessages());
                                                    setUpRecyclerView();
                                                }
                                            }
                                            else {
                                                utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                            }
                                        }
                                        else {
                                            utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
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
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 1, 1.0f));
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(Mcontext).add(stringRequest);
        }
    }

    //set adapter on list
    private void setUpRecyclerView() {
        adapter = new MyMessageAdapter(arrayListMyMessage,Mcontext);
        myMessageList.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyMessageAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DrMessageFragment.sStrFragmentStatusCheck=MY_MESSAGE_CHECK;
                postition = position;
                DrMessageFragment.img_Back.setVisibility(View.VISIBLE);
                DrMessageFragment.tv_My_Message.getResources().getColor(R.color.white);
                DrMessageFragment.tv_My_Message.setText(R.string.back_to_inbox);
                DrMessageFragment.tv_Sent_Message.setVisibility(View.GONE);
                fragment = DrMsgDetailFragment.getInstance(getActivity(),getFragmentManager());
                bundle.putString("position",""+arrayListMyMessage.get(position).getTo());
                bundle.putString("char", String.valueOf("" + arrayListMyMessage.get(position).getFromname().charAt(0)));
                bundle.putString("from", String.valueOf("" + arrayListMyMessage.get(position).getFromname()));
                bundle.putString("date", String.valueOf("" + arrayListMyMessage.get(position).getCreatedAt()));
                bundle.putString("subject", String.valueOf(arrayListMyMessage.get(position).getSubject()));
                bundle.putString("detail_msg", String.valueOf(arrayListMyMessage.get(position).getMessage()));

                if (arrayListMyMessage.get(position).getHistory().size() > 0) {
                    history = 1;
                    bundle.putString("history", "" + history);
                    bundle.putString("toname", "" + arrayListMyMessage.get(position).getHistory().get(0).getToname());
                    bundle.putString("fromname", "" + arrayListMyMessage.get(position).getHistory().get(0).getFromname());
                    bundle.putString("datetime", "" + arrayListMyMessage.get(position).getHistory().get(0).getCreatedAt());
                    bundle.putString("msgsubject", "" + arrayListMyMessage.get(position).getHistory().get(0).getSubject());
                    bundle.putString("message", "" + arrayListMyMessage.get(position).getHistory().get(0).getMessage());
                } else {
                    history = 0;
                    bundle.putString("history", "" + history);
                }
                if (arrayListMyMessage.get(position).getAttachment().size() > 0) {
                    appointment = 1;
                    bundle.putString("attachment", "" +appointment);
                } else {
                    appointment = 0;
                    bundle.putString("attachment", "" +appointment);
                }
                fragment.setArguments(bundle); //data being send to Detail FRagment
                getFragmentManager().beginTransaction().replace(R.id.content_frame_message, fragment, "MsgDeatil").addToBackStack(null).commit();
            }
        });
    }
}
