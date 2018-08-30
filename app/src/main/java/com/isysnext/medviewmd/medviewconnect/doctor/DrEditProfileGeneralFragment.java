package com.isysnext.medviewmd.medviewconnect.doctor;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.modelDr.UserDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.UserProfileDTO;
import com.isysnext.medviewmd.medviewconnect.adapterDr.CustomSpinnerAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.SpinDTO;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.APIClient;
import com.isysnext.medviewmd.medviewconnect.utils.APIInterface;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrEditProfileGeneralFragment extends Fragment implements AppConstants, View.OnClickListener {
    //Declaration of variables
    private View view;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private ImageView ivUser;
    private Spinner spinProvince;
    private EditText etEmail, etFirstName, etLastName, etMobileNumber;
    private TextView tvGallery, tvCamera, tvRemovePhoto, tvClose, tvPassword, tvSecurityQuestion;
    private InputMethodManager mgr;
    private AppCompatButton btnSave, btnClose;
    private ArrayList<SpinDTO> listSpinProvince;
    private List<HashMap<String, String>> listHMProvince;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private String strEmail = "", strProvinceId = "", strProvinceName = "",
            strFirstName = "", strLastName = "", strMobileNumber = "",
            cropPicturePath = "", picturePath = "", image = "",
            strUserChooseTask = "", strEncodedImage = "", strProvinceOfResidence = "";
    private Intent intent;
    private Uri cameraUri = null;
    boolean bResult;
    Animation slideUp;
    APIInterface apiInterface;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.dr_edit_profile, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return view;
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
        try {
            ivUser = (ImageView) view.findViewById(R.id.iv_user);
            ivUser.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinProvince = (Spinner) view.findViewById(R.id.spin_province);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etFirstName = (EditText) view.findViewById(R.id.et_first_name);
        etLastName = (EditText) view.findViewById(R.id.et_last_name);
        etMobileNumber = (EditText) view.findViewById(R.id.et_mobile_number);
        etMobileNumber = (EditText) view.findViewById(R.id.et_mobile_number);
        btnSave = (AppCompatButton) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        btnClose = (AppCompatButton) view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);
        tvGallery = (TextView) view.findViewById(R.id.tv_gallery);
        tvCamera = (TextView) view.findViewById(R.id.tv_camera);
        tvRemovePhoto = (TextView) view.findViewById(R.id.tv_remove_photo);
        tvPassword = (TextView) view.findViewById(R.id.tv_password);
        tvPassword.setOnClickListener(this);
        tvSecurityQuestion = (TextView) view.findViewById(R.id.tv_security_question);
        tvSecurityQuestion.setOnClickListener(this);
        tvClose = (TextView) view.findViewById(R.id.tv_close);
    }

    //Method for setting values
    private void initValues() {
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
        ((DashboardActivity) getActivity()).hideTryMenuOnAnyFragment();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        listSpinProvince = new ArrayList<>();
        setSessionDataOnView();
        setValueForProvinceSpinner();
        callVolleyLoadProfile();

    }

    //Method for setting session data on view
    private void setSessionDataOnView() {
        if (appSession.getUser().getUinfo() != null) {
            if (appSession.getUser().getUinfo().getAvatar() != null || !appSession.getUser().getUinfo().getAvatar().equals("")) {
                Glide.with(context)
                        .load(BASE_URL+appSession.getUser().getUinfo().getAvatar())
                        .placeholder(R.mipmap.user_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.user_image)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(ivUser);
            } else {
                Glide.with(context)
                        .load(R.mipmap.user_image)
                        .placeholder(R.mipmap.user_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.user_image)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(ivUser);
            }
            if(appSession.getUser().getUinfo().getState() != null || !appSession.getUser().getUinfo().getState().equals("")) {
                strProvinceOfResidence=appSession.getUser().getUinfo().getState();
            }
            if (appSession.getUser().getUinfo().getEmail() != null || !appSession.getUser().getUinfo().getEmail().equals("")) {
                etEmail.setText(appSession.getUser().getUinfo().getEmail());
            }
            if (appSession.getUser().getUinfo().getFirstname() != null || !appSession.getUser().getUinfo().getFirstname().equals("")) {
                etFirstName.setText(appSession.getUser().getUinfo().getFirstname());
            }
            if (appSession.getUser().getUinfo().getLastname() != null || !appSession.getUser().getUinfo().getLastname().equals("")) {
                etLastName.setText(appSession.getUser().getUinfo().getLastname());
            }
            if (appSession.getUser().getUinfo().getPhone() != null || !appSession.getUser().getUinfo().getPhone().equals("")) {
                etMobileNumber.setText(appSession.getUser().getUinfo().getPhone());
            }
        }
    }

    //Method for setting values of province spinner
    private void setValueForProvinceSpinner() {
        listHMProvince = new ArrayList<>();
        //setting for province spinner
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("name", context.getResources().getString(R.string.province_of_residence));
        hm.put("value", context.getResources().getString(R.string.province_of_residence));
        listHMProvince.add(hm);
        listSpinProvince.add(new SpinDTO("AB", "Alberta"));
        listSpinProvince.add(new SpinDTO("BC", "British Columbia"));
        listSpinProvince.add(new SpinDTO("MB", "Manitoba"));
        listSpinProvince.add(new SpinDTO("NB", "New Brunswick"));
        listSpinProvince.add(new SpinDTO("NL", "Newfoundland and Labrador"));
        listSpinProvince.add(new SpinDTO("NT", "Northwest Territories"));
        listSpinProvince.add(new SpinDTO("NS", "Nova Scotia"));
        listSpinProvince.add(new SpinDTO("NU", "Nunavut"));
        listSpinProvince.add(new SpinDTO("ON", "Ontario"));
        listSpinProvince.add(new SpinDTO("PE", "Prince Edward Island"));
        listSpinProvince.add(new SpinDTO("QC", "Quebec"));
        listSpinProvince.add(new SpinDTO("SK", "Saskatchewan"));
        listSpinProvince.add(new SpinDTO("YT", "Yukon"));
        listSpinProvince.add(new SpinDTO("OT", "Other / Non-Canada"));
        if (listSpinProvince != null) {
            for (SpinDTO provinceOfResidence : listSpinProvince) {
                hm = new HashMap<String, String>();
                hm.put("name", provinceOfResidence.getKey());
                hm.put("value", provinceOfResidence.getValue());
                listHMProvince.add(hm);
            }
        }
        customSpinnerAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_textview, listHMProvince,
                getResources().getColor(R.color.white), "");
        spinProvince.setAdapter(customSpinnerAdapter);

        spinProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strProvinceId = listHMProvince.get(position).get("name");
                strProvinceName = listHMProvince.get(position).get("value");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (appSession.getUser().getUinfo().getState() != null || !appSession.getUser().getUinfo().getState().equals("")) {
            if (listSpinProvince.size() > 0) {
                for (int i = 0; i < listSpinProvince.size(); i++)
                    if (listHMProvince.get(i).get("name").equals(strProvinceOfResidence)) {
                        strProvinceId = listHMProvince.get(i).get("name");
                        strProvinceName = listHMProvince.get(i).get("value");
                        spinProvince.setSelection(i);
                    }
            }
        }
    }


    //Method for calling volley web service of load profile
    private void callVolleyLoadProfile() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url =BASE_URL+LOAD_PROFILE+"/"+PARTNER_ID+"/"+appSession.getUser().getUinfo().getId();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("TOKAN---------", appSession.getUser().getUinfo().getToken());
                            Log.i("ResponseA---------", response);
                            try {
                                if (response == null) {
                                    utilities.dialogOK(context, context.getResources().getString(R.string.Whoops), context.getResources().getString(R.string.server_error), context.getString(R.string.ok), false);
                                }
                                else {
                                    Gson gson=new Gson();
                                    Object object = new JSONTokener(response).nextValue();
                                    UserProfileDTO userProfileDTO=gson.fromJson(response,UserProfileDTO.class);
                                    if(object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        if(userProfileDTO.getSuccess()==1)
                                        {
                                            UserDTO userDTO1 = appSession.getUser();
                                            if(userProfileDTO.getProfile().getSecurityQ()==null){
                                                userDTO1.getUinfo().setSecurityQ("");
                                                appSession.setUser(userDTO1);
                                            }
                                            else {
                                                Log.i("securityQ---------",userProfileDTO.getProfile().getSecurityQ());
                                                userDTO1.getUinfo().setSecurityQ(userProfileDTO.getProfile().getSecurityQ());
                                                appSession.setUser(userDTO1);
                                            }
                                            //utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                        else
                                        {
                                            //utilities.dialogOK(context, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user:
                dailogueImageChooser(context, getResources().getString(R.string.choose_photo));
                break;
            case R.id.btn_save:
                getEditTextEnteredValues();
                if (isValid()) {
//                    mgr.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
                    callVolleyEditProfile();
                }
                break;
            case R.id.btn_close:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_password:
                showFragment(new DrEditProfilePassword(), "DrEditProfilePassword");
                break;
            case R.id.tv_security_question:
                showFragment(new DrEditProfileSecurityFragment() , "DrEditProfileSecurity");
                break;
            default:
                break;
        }
    }

    //Method to validation
    public boolean isValid() {
        if (strProvinceName == null || strProvinceName.equals("")
                || strProvinceName.equals(getResources().getString(R.string.province_of_residence))) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_select_province_of_residence), getString(R.string.ok), false);
            spinProvince.requestFocus();
            return false;
        } else if (strEmail == null || strEmail.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.email_empty), getString(R.string.ok), false);
            etEmail.requestFocus();
            return false;
        }
        if (!utilities.checkEmail(strEmail)) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_email), getString(R.string.ok), false);
            etEmail.requestFocus();
            return false;
        } else if (strFirstName == null || strFirstName.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_first_name), getString(R.string.ok), false);
            etFirstName.requestFocus();
            return false;
        }
        if (!PERSON_NAME_PATTERN.matcher(strFirstName).matches()) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_firt_name), getString(R.string.ok), false);
            etFirstName.requestFocus();
            return false;
        } else if (strLastName == null || strLastName.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_last_name), getString(R.string.ok), false);
            etLastName.requestFocus();
            return false;
        }
        if (!PERSON_NAME_PATTERN.matcher(strLastName).matches()) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_last_name), getString(R.string.ok), false);
            etLastName.requestFocus();
            return false;
        } else if (strMobileNumber == null || strMobileNumber.equals("")) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_mobile_number),
                    getString(R.string.ok), false);
            etMobileNumber.requestFocus();
            return false;
        }
        if (strMobileNumber.length() < 6 || strMobileNumber.length() > 13) {
            utilities.dialogOK(context, getString(R.string.validation_title), getString(R.string.please_enter_valid_mobile_number),
                    getString(R.string.ok), false);
            etMobileNumber.requestFocus();
            return false;
        }

        return true;
    }

    //Method for getting values of edit text
    private void getEditTextEnteredValues() {
        strEmail = etEmail.getText().toString().trim();
        strFirstName = etFirstName.getText().toString().trim();
        strLastName = etLastName.getText().toString().trim();
        strMobileNumber = etMobileNumber.getText().toString().trim();
    }

    //Method for calling volley web service of edit profile
    private void callVolleyEditProfile() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(getActivity(), "", getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
            String url =BASE_URL+EDIT_PROFILE;
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
                                            UserDTO userDTO1 = appSession.getUser();
                                            userDTO1.getUinfo().setAvatar(image);
                                            userDTO1.getUinfo().setState(strProvinceId);
                                            userDTO1.getUinfo().setFirstname(strFirstName);
                                            userDTO1.getUinfo().setLastname(strLastName);
                                            userDTO1.getUinfo().setPhone(strMobileNumber);
                                            appSession.setUser(userDTO1);
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
                    if(image==null || image.equals("")) {
                        params.put("state", strProvinceId);
                        params.put("email", strEmail);
                        params.put("phone", strMobileNumber);
                        params.put("fname", strFirstName);
                        params.put("lname", strLastName);
                        Log.i("ServerParameter-------", "" + params);
                    }
                    else
                    {
                        encodingImagePath();
                        params.put("state", strProvinceId);
                        params.put("email", strEmail);
                        params.put("phone", strMobileNumber);
                        params.put("fname", strFirstName);
                        params.put("lname", strLastName);
                        params.put("cropped_image", strEncodedImage);
                        Log.i("ServerParameter-------", "" + params);
                    }
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

    //Method for encoding image path
    private void encodingImagePath() {
        try {
            Bitmap bm = BitmapFactory.decodeFile(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            strEncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //function for choose the image from gallery or camera
    public void dailogueImageChooser(Context context, String header) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dr_dialog_image_chooser);
        Window window = dialog.getWindow();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        TextView tvHeader = (TextView) dialog.findViewById(R.id.tv_header);
        TextView tvGallery = (TextView) dialog.findViewById(R.id.tv_gallery);
        TextView tvCamera = (TextView) dialog.findViewById(R.id.tv_camera);
        TextView tvRemovePhoto = (TextView) dialog.findViewById(R.id.tv_remove_photo);
        appSession = new AppSession(context);
        tvHeader.setText(header);
        bResult = Utilities.checkPermission(context);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bResult) {
                    strUserChooseTask = String.valueOf(CAMERA);
                    cameraIntent();
                }
                dialog.dismiss();
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bResult) {
                    strUserChooseTask = String.valueOf(GALLERY);
                    galleryIntent();
                }
                dialog.dismiss();
            }
        });
        tvRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUser.setImageResource(R.mipmap.user_image);
                image = "";
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    //Method for firing camera intent
    private void cameraIntent() {
        try {
            intent = new Intent();
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String fileName = "IMAGE_" + System.currentTimeMillis() + ".jpg";
            cameraUri = Uri.fromFile(utilities.getNewFile(IMAGE_DIRECTORY, fileName));
            //cameraUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + "com.isysnext.medviewmd.medviewconnect.patient",fileName);
            appSession.setImageUri(cameraUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method for firing gallery intent
    private void galleryIntent() {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, ""),
                GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utilities.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (strUserChooseTask.equals(String.valueOf(CAMERA)))
                        cameraIntent();
                    else if (strUserChooseTask.equals(String.valueOf(GALLERY)))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    //Method for setting image uri
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    //Method for getting image file
    private File getTempFile() {
        String imageName = "CROP_" + System.currentTimeMillis() + ".jpg";
        File tempFile = utilities.getNewFile(IMAGE_DIRECTORY_CROP, imageName);
        cropPicturePath = tempFile.getPath();
        appSession = new AppSession(context);
        appSession.setCropImagePath(tempFile.getPath());
        return tempFile;
    }

    /**
     * Default Helper method to carry out crop operation
     */
    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
            cropIntent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cropIntent, CROP);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,
                    getString(R.string.crop_action_support), Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,
                    getString(R.string.crop_action_support), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(getClass().getName(), "onActivityResult requestCode : " + requestCode + " requestCode : " + resultCode);
        if (requestCode == ACTIVITY_RESULT && resultCode == getActivity().RESULT_OK) {
            //  finish();
        }
        if (requestCode == CROP && resultCode == Activity.RESULT_OK) {
            try {
                if (cropPicturePath == null || cropPicturePath.equals("")
                        || !new File(cropPicturePath).isFile())
                    cropPicturePath = appSession.getCropImagePath();

                if (cropPicturePath == null || cropPicturePath.equals("")
                        || !new File(cropPicturePath).isFile())
                    cropPicturePath = picturePath;

                if (cropPicturePath == null || cropPicturePath.equals("")
                        || !new File(cropPicturePath).isFile())
                    cropPicturePath = appSession.getImagePath();

                Log.i(getClass().getName(),
                        "CROP cropPicturePath : "
                                + cropPicturePath);
                if (cropPicturePath != null && !cropPicturePath.equals("")
                        && new File(cropPicturePath).isFile()) {
                    if (bitmap != null)
                        bitmap.recycle();
                    bitmap = Utilities.decodeFile(new File(cropPicturePath),
                            640, 640);
                    cropPicturePath = Utilities.getFilePath(bitmap, context, cropPicturePath);
                    image = cropPicturePath;
                    Picasso.with(context).load(new File(image)).resize(100, 100).centerCrop().error(R.mipmap.user_image).placeholder(R.mipmap.user_image).into(ivUser);
                } else {
                    Toast.makeText(context,
                            getString(R.string.crop_action_error),
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("Exception ==>", " " + e);
                /*Toast.makeText(context,
                        getString(R.string.crop_action_error),
                        Toast.LENGTH_LONG).show();*/
            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == GALLERY) {
                try {
                    Uri uriImage = data.getData();
                    if (uriImage != null) {
                        picturePath = utilities.getAbsolutePath(uriImage);
                        if (picturePath == null || picturePath.equals(""))
                            picturePath = uriImage.getPath();
                        appSession.setImagePath(picturePath);
                        Log.i(getClass().getName(), "GALLERY picturePath : " + picturePath);
                        Cursor cursor = context
                                .getContentResolver()
                                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        new String[]{MediaStore.Images.Media._ID},
                                        MediaStore.Images.Media.DATA + "=? ",
                                        new String[]{picturePath}, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                            uriImage = Uri.parse("content://media/external/images/media/" + id);
                        }
                        performCrop(uriImage);
                    } else {
                        Toast.makeText(context,
                                getString(R.string.gallery_pick_error),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            getString(R.string.gallery_pick_error),
                            Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == CAMERA) {
                try {
                    if (cameraUri == null)
                        cameraUri = appSession.getImageUri();
                    if (cameraUri != null) {
                        picturePath = utilities.getAbsolutePath(cameraUri);
                        if (picturePath == null || picturePath.equals(""))
                            picturePath = cameraUri.getPath();
                        appSession.setImagePath(picturePath);
                        Log.i(getClass().getName(), "CAMERA picturePath : " + picturePath);
                        Cursor cursor = context
                                .getContentResolver()
                                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        new String[]{MediaStore.Images.Media._ID},
                                        MediaStore.Images.Media.DATA + "=? ",
                                        new String[]{picturePath}, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int id = cursor
                                    .getInt(cursor
                                            .getColumnIndex(MediaStore.MediaColumns._ID));
                            cameraUri = Uri
                                    .parse("content://media/external/images/media/"
                                            + id);
                        }
                        performCrop(cameraUri);
                    } else {
                        Toast.makeText(context,
                                getString(R.string.camera_capture_error),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            getString(R.string.camera_capture_error),
                            Toast.LENGTH_LONG).show();
                }
            }
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
