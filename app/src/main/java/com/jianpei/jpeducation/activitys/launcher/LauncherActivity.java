package com.jianpei.jpeducation.activitys.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.utils.SpUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        int isfirst = (int) SpUtils.get(SpUtils.ISFirst, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isfirst == 0) {
                    startActivity(new Intent(LauncherActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));

                }
                finish();
            }
        }, 2000);

    }
}
