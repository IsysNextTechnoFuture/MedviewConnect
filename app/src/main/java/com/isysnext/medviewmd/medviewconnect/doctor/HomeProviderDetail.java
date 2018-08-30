package com.isysnext.medviewmd.medviewconnect.doctor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.HomeProviderListDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.SendMessageAttachFileDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.SendMsgAttachFileAdapter;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class HomeProviderDetail extends Fragment implements View.OnClickListener, AppConstants {
    //Declaration of variables
    private View parentView;
    static Context context;
    static android.support.v4.app.FragmentManager fm;
    private Utilities utilities;
    private AppSession appSession;
    private RecyclerView rvProvider;
    private TextView tvBackToProviders, tvProviderName, tvSpeciality, tvStatus,
            tvProviderDescription, tvCostPerVisit, tvLanguages, tvYearOfExperience,
            tvBoardCertification, tvEducation, tvProfessionalOrganizations,
            tvStatesOfPractice, tvSendMessage, tvStartWelcomeVideo, tvAttachFile, tvLatestRating;
    private CircleImageView civProvider;
    private RatingBar rbProvider;
    private ImageView ivStatus, ivAttachFile;
    private AppCompatButton btnStartVisit;
    private EditText etSubjectName, etSubjectDescription;
    APIInterface apiInterface;
    private String strSubjectName = "", strSubjectDescription = "", strUserChooseTask = "",
            cropPicturePath = "", picturePath = "", image = "", strPosition = "", strVideoUrl = "",
            strDate = "", strAttachmentFilePath = "", strAttachmentFileType = "", strEncodedFilePath = "";
    private InputMethodManager mgr;
    private Intent intent;
    private Uri cameraUri = null;
    boolean bResult;
    ProgressDialog mProgressDialog;
    Bundle bundle;
    ArrayList<HomeProviderListDTO.Provider> providerList;
    ArrayList<SendMessageAttachFileDTO> sendMsgAttachmentList;
    private JSONArray sendMessageJsonArray;
    Bitmap bitmap;
    ListView lvAttachmentFile;
    SendMsgAttachFileAdapter sendMsgAttachFileAdapter;
    SendMessageAttachFileDTO sendMessageAttachFileDTO;

    public static Fragment getInstance(Context contxt, FragmentManager FM) {
        context = contxt;
        fm = FM;
        Fragment frag = new HomeProviderDetail();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            parentView = inflater.inflate(R.layout.dr_home_provider_detail, container, false);
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
    public void initView() {
        tvBackToProviders = (TextView) parentView.findViewById(R.id.tv_back_to_providers);
        tvBackToProviders.setOnClickListener(this);
        civProvider = (CircleImageView) parentView.findViewById(R.id.civ_provider);
        tvProviderName = (TextView) parentView.findViewById(R.id.tv_provider_name);
        tvSpeciality = (TextView) parentView.findViewById(R.id.tv_speciality);
        tvStatus = (TextView) parentView.findViewById(R.id.tv_status);
        ivStatus = (ImageView) parentView.findViewById(R.id.iv_status);
        rbProvider = (RatingBar) parentView.findViewById(R.id.rb_provider);
        tvProviderDescription = (TextView) parentView.findViewById(R.id.tv_provider_description);
        tvCostPerVisit = (TextView) parentView.findViewById(R.id.tv_cost_per_visit);
        tvLanguages = (TextView) parentView.findViewById(R.id.tv_languages);
        tvYearOfExperience = (TextView) parentView.findViewById(R.id.tv_year_of_experience);
        tvBoardCertification = (TextView) parentView.findViewById(R.id.tv_board_certification);
        tvEducation = (TextView) parentView.findViewById(R.id.tv_education);
        tvProfessionalOrganizations = (TextView) parentView.findViewById(R.id.tv_professional_organizations);
        tvStatesOfPractice = (TextView) parentView.findViewById(R.id.tv_states_of_practice);
        tvLatestRating = (TextView) parentView.findViewById(R.id.tv_latest_rating);
        tvLatestRating.setOnClickListener(this);
       // tvSendMessage = (TextView) parentView.findViewById(R.id.tv_send_message);
      //  tvSendMessage.setOnClickListener(this);
      //  tvStartWelcomeVideo = (TextView) parentView.findViewById(R.id.tv_start_welcome_video);
      //  tvStartWelcomeVideo.setOnClickListener(this);
        btnStartVisit = (AppCompatButton) parentView.findViewById(R.id.btn_start_visit);
        btnStartVisit.setOnClickListener(this);
    }

    //Method for initializing values
    private void initValues() {
        providerList = new ArrayList<>();
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(tvSendMessage.getWindowToken(), 0);
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(POSITION)) {
                strPosition = getArguments().getString(POSITION);
            }
        }
        setServerDataOnView();
    }

    //Method for setting server data on view
    private void setServerDataOnView() {
        if (appSession.getProviderDetails() != null) {
            if (appSession.getProviderDetails().getProviders() != null) {
                if (ProviderFragment.sStrFragmentStatusCheck.equals(FAVOURITE)) {
                    providerList.addAll(appSession.getProviderDetails().getProviders());
                    int count = 0;
                    count = providerList.size() - 1;
                    while (count != 0) {
                        if (providerList.get(count).getIsFavourite() != null || !providerList.get(count).getIsFavourite().equals("")) {
                            if (providerList.get(count).getIsFavourite().equals("0")) {
                                providerList.remove(count);
                            }
                        }
                        count--;
                    }

                    if (providerList.get(Integer.parseInt(strPosition)).getAvatar() != null ||
                            !providerList.get(Integer.parseInt(strPosition)).getAvatar().equals("")) {
                        Glide.with(context)
                                .load(BASE_URL + providerList.get(Integer.parseInt(strPosition)).getAvatar())
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(civProvider);
                    } else {
                        Glide.with(context)
                                .load(R.mipmap.user_image)
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(civProvider);
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getName() != null ||
                            !providerList.get(Integer.parseInt(strPosition)).getName().equals("")) {
                        tvProviderName.setText(providerList.get(Integer.parseInt(strPosition)).getName());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getSpecialty() != null ||
                            !providerList.get(Integer.parseInt(strPosition)).getSpecialty().equals("")) {
                        tvSpeciality.setText(providerList.get(Integer.parseInt(strPosition)).getSpecialty());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getCalculatedRating() != null ||
                            !providerList.get(Integer.parseInt(strPosition)).getCalculatedRating().equals("")) {
                        rbProvider.setRating(providerList.get(Integer.parseInt(strPosition)).getCalculatedRating());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getTextprofile() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getTextprofile().equals("")) {
                        tvProviderDescription.setText(providerList.get(Integer.parseInt(strPosition)).getTextprofile());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getCost() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getCost().equals("")) {
                        tvCostPerVisit.setText(providerList.get(Integer.parseInt(strPosition)).getCost());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getLanguage() != null ||
                            !providerList.get(Integer.parseInt(strPosition)).getLanguage().equals("")) {
                        tvLanguages.setText(providerList.get(Integer.parseInt(strPosition)).getLanguage());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getExperience() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getExperience().equals("")) {
                        tvYearOfExperience.setText(providerList.get(Integer.parseInt(strPosition)).getExperience());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getBoard() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getBoard().equals("")) {
                        tvBoardCertification.setText(providerList.get(Integer.parseInt(strPosition)).getBoard());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getEducation() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getEducation().equals("")) {
                        tvEducation.setText(providerList.get(Integer.parseInt(strPosition)).getEducation());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getResidency() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getResidency().equals("")) {
                        tvStatesOfPractice.setText(providerList.get(Integer.parseInt(strPosition)).getResidency());
                    }
                    if (providerList.get(Integer.parseInt(strPosition)).getVideoprofile() != null
                            || !providerList.get(Integer.parseInt(strPosition)).getVideoprofile().equals("")) {
                        strVideoUrl = providerList.get(Integer.parseInt(strPosition)).getVideoprofile();
                    }

                } else {
                    providerList.addAll(appSession.getProviderDetails().getProviders());
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getAvatar() != null ||
                            !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getAvatar().equals("")) {
                        Glide.with(context)
                                .load(BASE_URL + appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getAvatar())
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(civProvider);
                    } else {
                        Glide.with(context)
                                .load(R.mipmap.user_image)
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(civProvider);
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getName() != null ||
                            !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getName().equals("")) {
                        tvProviderName.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getName());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getSpecialty() != null ||
                            !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getSpecialty().equals("")) {
                        tvSpeciality.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getSpecialty());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCalculatedRating() != null ||
                            !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCalculatedRating().equals("")) {
                        rbProvider.setRating(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCalculatedRating());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getTextprofile() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getTextprofile().equals("")) {
                        tvProviderDescription.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getTextprofile());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCost() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCost().equals("")) {
                        tvCostPerVisit.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getCost());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getLanguage() != null ||
                            !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getLanguage().equals("")) {
                        tvLanguages.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getLanguage());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getExperience() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getExperience().equals("")) {
                        tvYearOfExperience.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getExperience());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getBoard() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getBoard().equals("")) {
                        tvBoardCertification.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getBoard());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getEducation() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getEducation().equals("")) {
                        tvEducation.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getEducation());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getResidency() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getResidency().equals("")) {
                        tvStatesOfPractice.setText(appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getResidency());
                    }
                    if (appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getVideoprofile() != null
                            || !appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getVideoprofile().equals("")) {
                        strVideoUrl = appSession.getProviderDetails().getProviders().get(Integer.parseInt(strPosition)).getVideoprofile();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_to_providers:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_latest_rating:
                if (!utilities.isNetworkAvailable())
                    utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
                else {
                    String url = BASE_URL + LATEST_RATING + "/" + PARTNER_ID + "/" + providerList.get(Integer.parseInt(strPosition)).getProviderId();
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
                                        }
                                        if (response.equals("")) {
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops), getResources().getString(R.string.server_error), getString(R.string.ok), false);
                                        } else {
                                            Object object = new JSONTokener(response).nextValue();
                                            if (object instanceof JSONObject) {
                                                JSONObject mJSONObject = new JSONObject(response);
//                                        if(mJSONObject.optInt("success")==1)
//                                        {
//                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
//                                            providerList.remove(position);
//                                        }
//                                        else
//                                        {
//                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
//                                        }
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
                break;
        /*    case R.id.tv_send_message:
                dialogSendMessage(context);
                break;
            case R.id.tv_start_welcome_video:

                break;*/
            case R.id.btn_start_visit:
             //   showFragment(new PurposeOfVisit(),"PurposeOfVisit");
                break;
            default:
                break;
        }
    }

    Dialog dialogSendMessage;

    //Method for dialogue send message
    public void dialogSendMessage(final Context context) {
        dialogSendMessage = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = dialogSendMessage.getWindow();
        dialogSendMessage.setCanceledOnTouchOutside(true);
        dialogSendMessage.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSendMessage.setContentView(R.layout.dr_dialog_send_message);
        window.setType(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etSubjectName = (EditText) window.findViewById(R.id.et_subject_name);
        etSubjectDescription = (EditText) window.findViewById(R.id.et_subject_description);
//        ivAttachFile = (ImageView) window.findViewById(R.id.iv_attach_file);
//        tvAttachFile = (TextView) window.findViewById(R.id.tv_attach_file);
        lvAttachmentFile= (ListView) window.findViewById(R.id.lv_attachment_file);
        AppCompatButton btnSubmit = (AppCompatButton) window.findViewById(R.id.btn_submit);
        AppCompatButton btnClose = (AppCompatButton) window.findViewById(R.id.btn_close);
        sendMsgAttachmentList = new ArrayList<>();
        sendMessageAttachFileDTO=new SendMessageAttachFileDTO(getResources().
                getString(R.string.Attach_file),"","");
        sendMsgAttachmentList.add(sendMessageAttachFileDTO);
        sendMsgAttachFileAdapter=new SendMsgAttachFileAdapter(context,R.layout.dr_list_item_attachment_file,
                sendMsgAttachmentList);
        lvAttachmentFile.setAdapter(sendMsgAttachFileAdapter);
        lvAttachmentFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                startActivityForResult(intent, 7);
                Intent intent = new Intent(getActivity(), PdfDocViewActivity.class);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                strSubjectName = etSubjectName.getText().toString().trim();
                strSubjectDescription = etSubjectDescription.getText().toString().trim();
                if (isValid()) {
                    mgr.hideSoftInputFromWindow(etSubjectName.getWindowToken(), 0);
                    callVolleySendMessage();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogSendMessage.dismiss();
            }
        });
        if (dialogSendMessage != null && !dialogSendMessage.isShowing())
            dialogSendMessage.show();
    }

    //Method for getting json array
    private String getJsonArrayOfSendMessageAttachFile() {
        JSONObject jsonObject = new JSONObject();
        sendMessageJsonArray = new JSONArray();
        if (sendMsgAttachmentList.size() > 1) {
            JSONObject jsonSize = null;
            try {
                for (int i = 0; i < sendMsgAttachmentList.size(); i++) {
                    jsonSize = new JSONObject();
                    jsonSize.put("fileData", "data:" + sendMsgAttachmentList.get(i).getStrAttachmentFilePath() + ";"
                            + "base64," + sendMsgAttachmentList.get(i).getStrEncodedFilePath());
                    jsonSize.put("name", appSession.getUser().getUinfo().getFirstname() + "_"
                            + "attach_file" + "_" + i);
                    sendMessageJsonArray.put(jsonSize);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("json", "" + String.valueOf(sendMessageJsonArray));
        return String.valueOf(sendMessageJsonArray);
    }

    //Method for encoding attachment file path
    private void encodingAttachmentFilePath() throws IOException {
//        File file = new File(strAttachmentFilePath);
//        byte[] bytes = loadFile(file);
//        strEncodedFilePath = Base64.encodeToString(bytes, Base64.DEFAULT);
        try {
            Bitmap bm = BitmapFactory.decodeFile(strAttachmentFilePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            strEncodedFilePath = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("strEncodedFilePath----", strEncodedFilePath);
    }

    //Method for getting byte array
    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    //Method for calling volley web service of send message
    private void callVolleySendMessage() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url = BASE_URL + MY_MESSAGE;
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
                                        if(mJSONObject.optInt("success")==1)
                                        {
                                            etSubjectName.setText("");
                                            etSubjectDescription.setText("");
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                        else
                                        {
                                            utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
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
                    String jsonArray = "";
                    if (sendMsgAttachmentList.size() > 1) {
                        jsonArray = getJsonArrayOfSendMessageAttachFile();
                        if (jsonArray != null || !jsonArray.equals("")) {
                            params.put("attachment", jsonArray);
                        }
                    }
                    params.put("from_id", String.valueOf(appSession.getUser().getUinfo().getId()));
                    params.put("subject", strSubjectName);
                    params.put("content", strSubjectDescription);
                    //params.put("to_id", providerList.get(Integer.parseInt(strPosition)).getId());
                    Log.i("Parameters---------", "" + params);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
//                    String[] strArray;
//                    strAttachmentFilePath = data.getData().getPath();
//                    File f=getFile(strAttachmentFilePath);
//                    strAttachmentFilePath=f.getPath();
//                    //strAttachmentFilePath =getActivity().getFilesDir()+strAttachmentFilePath;
//                    Log.i("strAttachmentFilePath--", strAttachmentFilePath);
//                    Uri returnUri = data.getData();
//                    strAttachmentFileType = context.getContentResolver().getType(returnUri);
//                    try {
//                        encodingAttachmentFilePath();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if(strAttachmentFilePath == null || strAttachmentFilePath.equals("")) {
//                    } else {
//                        sendMsgAttachmentList.add(strAttachmentFilePath);
//                        //Log.i("strAttachmentFileType--", strAttachmentFileType);
//                    }
//                    Uri returnUri = data.getData();
//                    strAttachmentFileType = context.getContentResolver().getType(returnUri);
                    strAttachmentFilePath = data.getStringExtra(FILE_PATH);
                    strAttachmentFileType = data.getStringExtra(MINE_TYPE);
                    if (bitmap != null)
                        bitmap.recycle();
                    bitmap = Utilities.decodeFile(new File(strAttachmentFilePath),
                            640, 640);
                    strAttachmentFilePath = Utilities.getFilePath(bitmap, context, strAttachmentFilePath);
                    Log.i("strAttachmentFilePath--", strAttachmentFilePath);
                    Log.i("strAttachmentFileType--", strAttachmentFileType);
                    try {
                        encodingAttachmentFilePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(strAttachmentFilePath == null || strAttachmentFilePath.equals("")) {
                    } else {
                        sendMessageAttachFileDTO=new SendMessageAttachFileDTO(strAttachmentFilePath,strAttachmentFileType,
                                strEncodedFilePath);
                        sendMsgAttachmentList.add(sendMessageAttachFileDTO);
                        sendMsgAttachFileAdapter=new SendMsgAttachFileAdapter(context,R.layout.dr_list_item_attachment_file,
                                sendMsgAttachmentList);
                        //Log.i("strAttachmentFileType--", strAttachmentFileType);
                    }
                }
                break;
        }
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(getActivity().getFilesDir(), filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    //Method to validation
    public boolean isValid() {
        if (strSubjectName == null || strSubjectName.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_subject_name), getString(R.string.ok), false);
            etSubjectName.requestFocus();
            return false;
        }
        if (!PERSON_NAME_PATTERN.matcher(strSubjectName).matches()) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_subject_name), getString(R.string.ok), false);
            etSubjectName.requestFocus();
            return false;
        } else if (strSubjectDescription == null || strSubjectDescription.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_description), getString(R.string.ok), false);
            etSubjectDescription.requestFocus();
            return false;
        }
        return true;
    }

    //Method for opening fragment
    private void showFragment(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commit();
    }
}
