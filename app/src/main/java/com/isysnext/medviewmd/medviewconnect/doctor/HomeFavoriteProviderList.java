package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.modelDr.HomeProviderListDTO;
import com.isysnext.medviewmd.medviewconnect.OnItemClickListener;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.FavoriteProviderListAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFavoriteProviderList extends Fragment  implements AppConstants
{
    //Declaration of variables
    private View parentView;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private RecyclerView rvProvider;
    private TextView tvError;
    private LinearLayoutManager mLinearLayoutManager;
    APIInterface apiInterface;
    private int iFirstVisibleItem, iVisibleItemCount,
            iTotalItemCount, iLastVisibleItem, pageNo = 1, pageSize = 10, iListPosition;
    private FavoriteProviderListAdapter adapter;
    private boolean bShouldLoadMore = false;
    static String sStrWebServiceCallCheck=ON;
    ArrayList<HomeProviderListDTO.Provider> providerList;
    ProgressDialog mProgressDialog;
    HomeProviderListDTO homeProviderListDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.dr_home_provider_list, container, false);
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
    public void initView()
    {
        rvProvider = (RecyclerView) parentView.findViewById(R.id.rv_provider_list);
        tvError = (TextView) parentView.findViewById(R.id.tv_error);
    }
    //Method for initializing values
    private void initValues() {
        providerList = new ArrayList<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if(sStrWebServiceCallCheck.equals(ON)) {
            callVolleyProviderList();
            sStrWebServiceCallCheck=OFF;
        }
        else
        {
            providerList.addAll(appSession.getProviderDetails().getProviders());
            prepareDtoDataForDetailScreen();
            if(providerList.size()>0)
            {
                rvProvider.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                setUpRecyclerView();
            }
            else
            {
                tvError.setVisibility(View.VISIBLE);
                rvProvider.setVisibility(View.GONE);
                tvError.setText(getResources().getString(R.string.favourite_providers_sre_not_navailable));
            }
        }
    }
    //Method for setting recycler view
    private void setUpRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(context);
        rvProvider.setLayoutManager(mLinearLayoutManager);
        adapter = new FavoriteProviderListAdapter(getActivity(),providerList,onItemClickCallback);
        rvProvider.setAdapter(adapter);
        rvProvider.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                iVisibleItemCount = recyclerView.getChildCount();
                iTotalItemCount = mLinearLayoutManager.getItemCount();
                iFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                iLastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                //Get next page record if we are on last item and we have more record on server
                if((iLastVisibleItem == iTotalItemCount - 1) && bShouldLoadMore)
                {
                    //callTask();
                }
            }
        });
    }
    //Adapter click on list item
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            switch (view.getId()) {
                case R.id.ll_provider_list_item:
                  //  Home.sStrFragmentStatusCheck=FAVOURITE;
                    Bundle bundle=new Bundle();
                    bundle.putString(POSITION,String.valueOf(position));
                    bundle.putString(FAVOURITE,FAVOURITE);
                    HomeProviderDetail fragment=new HomeProviderDetail();
                    fragment.setArguments(bundle);
                    showFragment(fragment, "HomeProviderDetail");
                    break;
                case R.id.iv_favorite:
                    callRetrofitRemoveToFavorite(position);
                    break;
                default:
                    break;
            }
        }
    };
    //Method for calling volley web service for provider listing
    private void callVolleyProviderList() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            final String url =BASE_URL+PROVIDER_LIST+"/"+PARTNER_ID +"/"+String.valueOf(appSession.getUser().getUinfo().getId());
            mProgressDialog = ProgressDialog.show(context, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("URL---------", url);
                            Log.i("TOKAN---------", appSession.getUser().getUinfo().getToken());
                            Log.i("ResponseA---------", response);
                            try {
                                if (mProgressDialog != null && mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response == null) {
                                    utilities.dialogOK(context, context.getResources().getString(R.string.Whoops), context.getResources().getString(R.string.server_error), context.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    rvProvider.setVisibility(View.GONE);
                                    tvError.setVisibility(View.VISIBLE);
                                    tvError.setText(getResources().getString(R.string.favourite_providers_sre_not_navailable));
                                } else {
                                    Gson gson = new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    if(object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        homeProviderListDTO = gson.fromJson(response, HomeProviderListDTO.class);
                                        tvError.setVisibility(View.GONE);
                                            if (homeProviderListDTO.getProviders() != null) {
                                                if(homeProviderListDTO.getProviders().size() > 0) {
                                                    rvProvider.setVisibility(View.VISIBLE);
                                                    tvError.setVisibility(View.GONE);
                                                    appSession.setProviderDetails(homeProviderListDTO);
                                                    providerList.addAll(homeProviderListDTO.getProviders());
                                                    prepareDtoDataForDetailScreen();
                                                    if(providerList.size()>0)
                                                    {
                                                        rvProvider.setVisibility(View.VISIBLE);
                                                        tvError.setVisibility(View.GONE);
                                                        setUpRecyclerView();
                                                    }
                                                    else
                                                    {
                                                        tvError.setVisibility(View.VISIBLE);
                                                        rvProvider.setVisibility(View.GONE);
                                                        tvError.setText(getResources().getString(R.string.favourite_providers_sre_not_navailable));
                                                    }
                                                }
                                                else {
                                                    tvError.setVisibility(View.VISIBLE);
                                                    rvProvider.setVisibility(View.GONE);
                                                    tvError.setText(getResources().getString(R.string.favourite_providers_sre_not_navailable));
                                                }
                                            } else {
                                                tvError.setVisibility(View.VISIBLE);
                                                rvProvider.setVisibility(View.GONE);
                                                tvError.setText(getResources().getString(R.string.favourite_providers_sre_not_navailable));
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization","Bearer "+appSession.getUser().getUinfo().getToken());
                    Log.i("paramsToken---------",""+params);
                    return params;
                }
            };
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(context).add(stringRequest);
        }
    }

    //Method for preparing dto data of details screen
    public void prepareDtoDataForDetailScreen()
    {
        int count=0;
        count=providerList.size()-1;
        while(count!=-1){
            if (providerList.get(count).getIsFavourite() != null || !providerList.get(count).getIsFavourite().equals("")) {
                if(providerList.get(count).getIsFavourite().equals("0")) {
                    providerList.remove(count);
                }
            }
            count--;
        }
    }

    //Method for calling retrofit web service for removing to favorite
    private void callRetrofitRemoveToFavorite(final int position) {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url =BASE_URL+REMOVE_TO_FAVORITE+String.valueOf(appSession.getUser().getUinfo().getId()) + "/" + providerList.get(position).getProviderId();
            mProgressDialog = ProgressDialog.show(context, null, null);
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
                                            providerList.remove(position);
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
            }){
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
                .replace(R.id.fl_home, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commit();
    }
}
