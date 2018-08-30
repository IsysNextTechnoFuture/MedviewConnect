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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isysnext.medviewmd.medviewconnect.DashboardActivity;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.adapterDr.SendMsgAttachFileAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.SendMessageAttachFileDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.SentMessageDTO;
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
import static android.app.Activity.RESULT_OK;

public class DrSentMsgDetail extends Fragment implements View.OnClickListener,AppConstants {
    static Context Mcontext;
    private Fragment fragment;
    private Bundle bundle;
    private int pos,toId;
    private String strAttachmentFilePath = "", strAttachmentFileType = "", strEncodedFilePath = "";
    private InputMethodManager mgr;
    private Intent intent;
    private Uri cameraUri = null;
    boolean bResult;
    ArrayList<SendMessageAttachFileDTO> sendMsgAttachmentList;
    private JSONArray sendMessageJsonArray;
    private Bitmap bitmap;
    private ListView lvAttachmentFile;
    private SendMsgAttachFileAdapter sendMsgAttachFileAdapter;
    private SendMessageAttachFileDTO sendMessageAttachFileDTO;
    private ProgressDialog mProgressDialog;
    private AppSession appSession;
    private SentMessageDTO sentMessageDTO;
    private String strSubjectName,strSubjectDescription;
    public  Dialog dialogSendMessage;
    private ImageView ivReply;
    private ArrayList<SentMessageDTO.Message> arrayListMyMessage;
    private ArrayList<SentMessageDTO> arrayListMyMessageHistory;
    private EditText etSubjectName,etSubjectDescription;
    private Utilities utilities;
    private TextView tvMsgName,tvDate,tvTime,tvSubject,tvMsgDetail,
            tvSubjectSender,tvNameText,tvFromName,tvDateSendr,tvTo,
            tvMsgDetailSender,tvSubjectText,tvMsgNameAttch;
    static FragmentManager fm;
    private View view;
    private LinearLayout layoutHistory;

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new DrSentMsgDetail();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dr_sent_msg_detail, container, false);
        ((DashboardActivity) getActivity()).visibleTryMenuOnAnyFragment();
        InitView();
        setValues();
        return view;
    }
//initialize values
    public void InitView() {
        tvSubjectText = (TextView) view.findViewById(R.id.tv_subject_text);
        tvMsgName = (TextView) view.findViewById(R.id.tv_msg_name);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvNameText = (TextView) view.findViewById(R.id.tv_name_text);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvSubject = (TextView) view.findViewById(R.id.tv_subject);
        tvMsgDetail = (TextView) view.findViewById(R.id.tv_msg_detail);
        tvSubjectSender = (TextView) view.findViewById(R.id.tv_subject_sender);
        ivReply = (ImageView) view.findViewById(R.id.reply);
        tvFromName = (TextView) view.findViewById(R.id.tv_from_name);
        tvDateSendr = (TextView) view.findViewById(R.id.tv_date_sendr);
        tvTo = (TextView) view.findViewById(R.id.tv_to);
        tvSubjectSender = (TextView) view.findViewById(R.id.tv_subject_sender);
        tvMsgDetailSender = (TextView) view.findViewById(R.id.tv_msg_detail_sender);
        layoutHistory = (LinearLayout) view.findViewById(R.id.layout_history);
        tvMsgNameAttch = (TextView) view.findViewById(R.id.tv_msg_name_attch);
        mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        DrMessageFragment.img_Back.setVisibility(View.VISIBLE);
        DrMessageFragment.tv_Sent_Message.setVisibility(View.GONE);
        DrMessageFragment.tv_My_Message.setText(R.string.back_to_inbox);
        DrMessageFragment.tv_My_Message.getResources().getColor(R.color.white);
        DrMessageFragment.tv_Close.setVisibility(View.GONE);

        if(bundle!=null)
        {
            //pos = Integer.parseInt(bundle.getString("position"));
            toId = Integer.parseInt(bundle.getString("TO_ID"));
            tvMsgName.setText(bundle.getString("from"));
            tvNameText.setText(bundle.getString("char"));
            tvDate.setText(bundle.getString("date"));
            tvSubjectText.setText(bundle.getString("subject"));

            if(Integer.parseInt(bundle.getString("history"))==1)
            {
                layoutHistory.setVisibility(View.VISIBLE);
                tvMsgDetail.setText(bundle.getString("detail_msg"));
                tvMsgNameAttch.setText(bundle.getString("from"));
                tvFromName.setText(bundle.getString("toname"));
                tvTo.setText(bundle.getString("fromname"));
                tvDateSendr.setText(bundle.getString("datetime"));
                tvMsgDetailSender.setText(bundle.getString("message"));
            }
            else
            {
                layoutHistory.setVisibility(View.GONE);
                //tv_Msg_Name_Attch.setText(bundle.getString("detail_msg"));
            }
            if(Integer.parseInt(bundle.getString("attachment"))==1)
            {
                tvMsgDetail.setVisibility(View.VISIBLE);
                tvMsgDetail.setText(bundle.getString("detail_msg"));
            }
            else
            {
                tvMsgNameAttch.setText(bundle.getString("detail_msg"));
                tvMsgDetail.setVisibility(View.GONE);
            }
        }
        DrMessageFragment.img_Back.setOnClickListener(this);
        ivReply.setOnClickListener(this);
    }
    // set values
    public void setValues()
    {
        Mcontext = getActivity();
        appSession = new AppSession(Mcontext);
        utilities = Utilities.getInstance(getActivity());
        bundle = getArguments();
        sentMessageDTO = new SentMessageDTO();
        arrayListMyMessage = new ArrayList<>();
        arrayListMyMessageHistory = new ArrayList<SentMessageDTO>();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.reply:
                dialogSendMessage(Mcontext);

            default:
                break;
        }
    }
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
            String url = BASE_URL+MY_MESSAGE_LIST;
            mProgressDialog = ProgressDialog.show(Mcontext, null, null);
            mProgressDialog.setContentView(R.layout.progress_loader);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(true);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("TOKAN---------",appSession.getUser().getUinfo().getToken());
                            Log.i("ResponseA---------", response);
                            try {
                                if (mProgressDialog != null && mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                if (response == null) {
                                    utilities.dialogOK(Mcontext, Mcontext.getResources().getString(R.string.Whoops), Mcontext.getResources().getString(R.string.server_error), Mcontext.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    utilities.dialogOK(Mcontext, getResources().getString(R.string.Whoops), getResources().getString(R.string.server_error), getString(R.string.ok), false);
                                } else {
                                    Object object = new JSONTokener(response).nextValue();
                                    if (object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        if(mJSONObject.optInt("success")==1)
                                        {
                                            etSubjectName.setText(null);
                                            etSubjectDescription.setText(null);
                                            utilities.dialogOK(Mcontext, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                        else
                                        {
                                            utilities.dialogOK(Mcontext, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
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
//                    Toast.makeText(Mcontext, ""+arrayListMyMessage.get(pos).getTo(), Toast.LENGTH_LONG).show();
                    params.put("from_id", String.valueOf(appSession.getUser().getUinfo().getId()));
                    params.put("subject", strSubjectName);
                    params.put("content", strSubjectDescription);
                    params.put("to_id", String.valueOf(toId));
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
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 1, 1.0f));
            //Add the request to the RequestQueue.
            Volley.newRequestQueue(Mcontext).add(stringRequest);
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
                    strAttachmentFilePath = Utilities.getFilePath(bitmap, Mcontext, strAttachmentFilePath);
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
                        sendMsgAttachFileAdapter=new SendMsgAttachFileAdapter(Mcontext,R.layout.dr_list_item_attachment_file,
                                sendMsgAttachmentList);
                        //Log.i("strAttachmentFileType--", strAttachmentFileType);
                    }
                }
                break;
        }
    }


    //Method to validation
    public boolean isValid() {
        if (strSubjectName == null || strSubjectName.equals("")) {
            utilities.dialogOK(Mcontext, getString(R.string.validation_title), getString(R.string.please_enter_subject_name), getString(R.string.ok), false);
            etSubjectName.requestFocus();
            return false;
        } else if (strSubjectDescription == null || strSubjectDescription.equals("")) {
            utilities.dialogOK(Mcontext, getString(R.string.validation_title), getString(R.string.please_enter_description), getString(R.string.ok), false);
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
