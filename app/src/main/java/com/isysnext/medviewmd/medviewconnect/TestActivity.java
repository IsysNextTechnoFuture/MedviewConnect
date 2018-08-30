package com.isysnext.medviewmd.medviewconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TestActivity extends AppCompatActivity {
    boolean visible;
    LinearLayout layout_one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Button tray_button = (Button) findViewById(R.id.tray_button);
         layout_one = (LinearLayout) findViewById(R.id.layout_one);

        tray_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout_one.getVisibility() == View.INVISIBLE) {

                    // layout_one.startAnimation(slideUp);

                    //   animate.setDuration(200);
                    //   animate.setFillAfter(true);
                    layout_one.setVisibility(View.VISIBLE);
                    layout_one.setAlpha(0.0f);
                    layout_one.animate()
                            .translationY(layout_one.getHeight())
                            .alpha(1.0f)
                            .setListener(null);
                }
                //*  visible = !visible;
                layout_one.setVisibility(visible ? View.VISIBLE : View.GONE);
            }});
    }
}

