package com.isysnext.medviewmd.medviewconnect;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isysnext.medviewmd.medviewconnect.modelDr.SpinnerListItem;
import com.isysnext.medviewmd.medviewconnect.adapterDr.DrSpinnerAdapter;
import com.isysnext.medviewmd.medviewconnect.adapterDr.DrawerItemAdapter;
import com.isysnext.medviewmd.medviewconnect.modelDr.DrawerListItem;
import com.isysnext.medviewmd.medviewconnect.doctor.DrEditProfileGeneralFragment;
import com.isysnext.medviewmd.medviewconnect.doctor.DrPlanOfCareFragment;
import com.isysnext.medviewmd.medviewconnect.doctor.DrMessageFragment;
import com.isysnext.medviewmd.medviewconnect.doctor.ProviderFragment;
import com.isysnext.medviewmd.medviewconnect.doctor.TestFragment;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import com.mikhaellopez.circularimageview.CircularImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLContext;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DashboardActivity extends AppCompatActivity implements AppConstants{

    private Button tray_button,tray_button_list;
    private Spinner lpspin;
    private Dialog lp_Dialog;
    public static int count;
    private SlidingDrawer simpleSlidingDrawer;
    private FrameLayout content_frame;
    private CircularImageView profile_Image;
    private DrawerItemAdapter adapter;
    private DrawerListItem listItem;
    private ArrayList<SpinnerListItem> arrayListSpin;
    private ArrayList<DrawerListItem> arrayList;
    private ListView tray_list_item;
    private AppSession appSession;
    private FragmentManager fragmentManager;
    public  static LinearLayout bottom_layout_icon;
    private LinearLayout linear_Layout_Tray,layout_parent;
    private Fragment fragment = null;
    private ProgressDialog mProgressDialog;
    private Utilities utilities;
    private ImageView plan_of_care,lp,icon_intake,appoint_patient;
    private Animation left_to_right,right_to_left;
    private static final String SOCKET_URL = "https://www.tele-presence.medviewconnect.com/";
    private String accessToken = "", userId = "", userIdStr = "",socktId ="";
    public io.socket.client.Socket mSocket;
    private static final String LOG_SOCKET = "AppController";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_new);

        initView();
        setValues();
        setTrayListData();
        setProfileImage();
        left_to_right = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right);
        right_to_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.right_to_left);
        simpleSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {

            }
        });

        // implement setOnDrawerCloseListener event
        simpleSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                // change the handle button text
                //   linear_Layout_Tray.startAnimation(right_to_left);

            }
        });
//        callForUnreadMessage();
        //publicSocket();
        fragmentManager = getSupportFragmentManager();
     /*  fragment = AppointmentFragment.getInstance(DashboardActivity.this, fragmentManager);

       fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Appointment").addToBackStack(null).commit();

        appoint_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(DashboardActivity.this, "Clicked", Toast.LENGTH_LONG).show();
                  fragment = AppointmentFragment.getInstance(DashboardActivity.this, fragmentManager);
                  fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Appointment").addToBackStack(null).commit();
            }
        });*/
        plan_of_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = DrPlanOfCareFragment.getInstance(DashboardActivity.this, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "DrPlanOfCare").addToBackStack(null).commit();
            }
        });
        lp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLpdialog(DashboardActivity.this);
            }
        });
        icon_intake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = TestFragment.getInstance(DashboardActivity.this, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Test").addToBackStack(null).commit();
            }
        });
        tray_list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    fragment = DrMessageFragment.getInstance(DashboardActivity.this, fragmentManager);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Message").addToBackStack(null).commit();
                }
                if(position==1)
                {    fragment = ProviderFragment.getInstance(DashboardActivity.this, fragmentManager);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Provider").addToBackStack(null).commit();
                }
                if(position==2)
                {
                    fragment = new DrEditProfileGeneralFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "DrEditProfileGeneral").addToBackStack(null).commit();
                }
                if(position==3)
                {
                    appSession.setLogin(false);
                    appSession.setUser(null);
                    finish();
                }
            }
        });
    }

    // for initialize view
    public void initView ()
    {
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        tray_list_item = (ListView) findViewById(R.id.tray_list_menu);// initiate the ListView that is used for content of Sl.idingDrawer
        simpleSlidingDrawer = (SlidingDrawer) findViewById(R.id.simpleSlidingDrawer);; // initiate the SlidingDrawer
        final Button handleButton = (Button) findViewById(R.id.handle);
        linear_Layout_Tray = (LinearLayout) findViewById(R.id.linear_layout_tray);
        bottom_layout_icon = (LinearLayout) findViewById(R.id.bottom_layout_icon);
        layout_parent = (LinearLayout)findViewById(R.id.layout_parent);
        tray_button = (Button) findViewById(R.id.tray_button);
        profile_Image = (CircularImageView) findViewById(R.id.profile_image);
        // iv_try = (ImageView) findViewById(R.id.iv_try);
        plan_of_care = (ImageView) findViewById(R.id.plan_of_care);
        lp = (ImageView) findViewById(R.id.lp);
        icon_intake = (ImageView) findViewById(R.id.icon_intake);
        tray_list_item = (ListView) findViewById(R.id.tray_list_menu);
        appoint_patient = (ImageView) findViewById(R.id.appoint_patient);
    }

    // for set or initialize values
    public void setValues()
    {
        utilities = Utilities.getInstance(this);
        bottom_layout_icon.setVisibility(View.VISIBLE);
        appSession = new AppSession(this);
        userId = String.valueOf(appSession.getUser().getUinfo().getId());
    }
/*    public void openSocket() {
        if (self.srIoSocket == nil || self.srIoSocket.status != SocketIOClientStatusConnected) {
            NSURL * url;
            url = [[NSURL alloc]initWithString:
            WEBSOCKET_URL_NEW];
            self.srIoSocket = [[SocketIOClient alloc]initWithSocketURL:
            url config:@ {
                @ "log":@YES,@ "compress":@YES
            }];
        [self.srIoSocket on:@ "connect" callback:^(NSArray * data, SocketAckEmitter * ack){
                NSLog( @ "socket connected");
                NSLog( @ "socket session id %@", self.srIoSocket.sid);
            [self registerIOSocket];
            }];
        } else {
            if (!self.providerSocketRegistered) {
                if (self.user_id != nil && [self.user_type isEqualToString:
                CONSTANT_USERTYPE_PROVIDER]){
                [self registerIOSocket];
                }
            }
        }
    }*/

    // method for socket connection
    public void publicSocket() {
        if(!userId.equals("")) {
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, null, null);
                IO.Options opts = new IO.Options();
                IO.setDefaultSSLContext(sc);
                opts.forceNew = false;
                opts.secure = true;
                opts.reconnection = true;
                mSocket = IO.socket(SOCKET_URL, opts);
                mSocket.connect();
                mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            Log.d(LOG_SOCKET, "socket connected");
                            JSONObject json = new JSONObject();
                            socktId = json.getString("socket_id");

                            if(appSession.getUser().getUinfo().getId() == null ||(!appSession.getUser().getUinfo().getType().equals(CONSTANT_USERTYPE_PROVIDER)))
                            {
                                Log.d(LOG_SOCKET,"not registering provider socket as provider is nil");
                            }
                            if(socktId == null || socktId.equals(""))
                            {
                                Log.d(LOG_SOCKET,"not registering provider socket as sid is empty");
                            }
                             json.put("socket_id",socktId);
                            json.put("orderId", userId);
                            json.put("user_id", userId);
                            json.put("userType","provider");
                            json.put("user_id",appSession.getUser().getUinfo().getId());
                            json.put("provider_id",appSession.getUser().getUinfo().getProviderId());
                            mSocket.emit("start_tracking",json);
                            // onLocation.onConnected(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            Log.d(LOG_SOCKET, "socket disconnected");
                            // onLocation.onConnected(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).on(io.socket.client.Socket.EVENT_ERROR, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            Log.d(LOG_SOCKET, "socket error");
                            publicSocket();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }).on(io.socket.client.Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        try {
                            Log.d(LOG_SOCKET, "socket connect time out error");
                            publicSocket();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }).on("new_location", new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        try {
                            Log.d(LOG_SOCKET, "socket update location " + args.toString());
                             publicSocket();
                            JSONObject object = new JSONObject(args[0].toString());
                            Log.d(LOG_SOCKET, "socket update location " + object.toString());
                            // onLocation.onGetLocation(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
    }
       //method to set tray menu item
        public void setTrayListData ()
        {

            arrayList = new ArrayList<>();
            listItem = new DrawerListItem(R.mipmap.messages);
            arrayList.add(listItem);

            listItem = new DrawerListItem(R.mipmap.conference);
            arrayList.add(listItem);

            listItem = new DrawerListItem(R.mipmap.edit_profile);
            arrayList.add(listItem);

            listItem = new DrawerListItem(R.mipmap.signout);
            arrayList.add(listItem);

            adapter = new DrawerItemAdapter(this, arrayList);
            tray_list_item.setAdapter(adapter);
        }

        //method to set profile image of login user
        protected void setProfileImage()
        {
            if (appSession.getUser().getUinfo().getAvatar() != null || !appSession.getUser().getUinfo().getAvatar() .equals("")) {
                Glide.with(this)
                        .load(BASE_URL+"/"+appSession.getUser().getUinfo().getAvatar())
                        .placeholder(R.mipmap.user_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.user_image)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(profile_Image);;
                Log.e("Image--------",BASE_URL+appSession.getUser().getUinfo().getAvatar());
            } else {
                Glide.with(this)
                        .load(R.mipmap.user_image)
                        .placeholder(R.mipmap.user_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.user_image)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(profile_Image);
            }
        }

    //for call unread msg from server
    private void callForUnreadMessage() {
        if (!utilities.isNetworkAvailable())
            utilities.dialogOK(this, "",getResources().getString(R.string.network_error), getString(R.string.ok), false);
        else {
           final String url =BASE_URL+UNREAD_MESSAGE_COUNT+"/"+String.valueOf(appSession.getUser().getUinfo().getId());
            mProgressDialog = ProgressDialog.show(this, null, null);
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
                                    utilities.dialogOK(DashboardActivity.this, DashboardActivity.this.getResources().getString(R.string.Whoops), DashboardActivity.this.getResources().getString(R.string.server_error), DashboardActivity.this.getString(R.string.ok), false);
                                }
                                if (response.equals("")) {
                                    utilities.dialogOK(DashboardActivity.this, getResources().getString(R.string.Whoops), getResources().getString(R.string.server_error), getString(R.string.ok), false);
                                } else {
                                    Object object = new JSONTokener(response).nextValue();
                                    if (object instanceof JSONObject) {
                                        JSONObject mJSONObject = new JSONObject(response);
                                        if(mJSONObject.optInt("success")==1)
                                        {

                                            count = mJSONObject.optInt("count");
                                            Log.i("COUNT---------", String.valueOf(count));
                                         //   utilities.dialogOK(DashboardActivity.this, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                        else
                                        {
                                            utilities.dialogOK(DashboardActivity.this, getResources().getString(R.string.Whoops),mJSONObject.optString("message"), getString(R.string.ok), false);
                                        }
                                    }
                                }
                            } catch (Exception e) {
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
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }
    protected void showLpdialog(Context context)
    {
        // Create the dialog.
        lp_Dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        Window window = lp_Dialog.getWindow();
        lp_Dialog.setCanceledOnTouchOutside(true);
        lp_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lp_Dialog.setContentView(R.layout.send_msg_dialog);
        window.setType(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        lp_Dialog.setContentView(R.layout.dr_lp_alert_dilog);

        // Get dialog widgets references.
         lpspin = (Spinner) lp_Dialog.findViewById(R.id.spin_lp_lp);
        setSpinData();
        Button close = (Button)lp_Dialog.findViewById(R.id.lp_spin_close);

        // Set on click lister for accept button
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                // Close dialog.
                lp_Dialog.dismiss();

                // TODO: Call api to send email to web service using friendsEmail var.

            }
        });

        //now that the dialog is set up, it's time to show it
        lp_Dialog.show();
    }

    //method to set spinner data
    public void setSpinData()
    {
        arrayListSpin = new ArrayList<>();
        arrayListSpin.add(new SpinnerListItem("Alberta"));
        arrayListSpin.add(new SpinnerListItem("Banxis"));
        arrayListSpin.add(new SpinnerListItem("Califor"));
        arrayListSpin.add(new SpinnerListItem("Thrasher"));
        lpspin.setAdapter(new DrSpinnerAdapter(arrayListSpin,this));
    }
    //Method for hiding try menu on any fragment
    public void hideTryMenuOnAnyFragment() {
        simpleSlidingDrawer.setVisibility(View.GONE);
    }
    //Method for visible try menu on any fragment
    public void visibleTryMenuOnAnyFragment() {
        simpleSlidingDrawer.setVisibility(View.VISIBLE);
    }
    }



