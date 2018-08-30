package com.isysnext.medviewmd.medviewconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    Button send,cancel;
    EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    public void initView()
    {
        send = (Button) findViewById(R.id.send);
        cancel = (Button) findViewById(R.id.cancel);
        et_email = (EditText) findViewById(R.id.input_email);
    }

}
