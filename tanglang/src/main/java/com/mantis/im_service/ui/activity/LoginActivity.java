package com.mantis.im_service.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.mantis.im_service.R;
import com.mantis.im_service.common.MantisCommon;
import com.mantis.im_service.util.PermissionUtils;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * probeid
     */
    private EditText mProbeid;
    /**
     * userid
     */
    private EditText mUserid;
    /**
     * 进入
     */
    private Button mLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        List<String> list = PermissionUtils.checkPermission(this, MantisCommon.perms);
        if (list.size() > 0) {
            PermissionUtils.requestPer(this, list, MantisCommon.REQ_CODE_PERMISSION);
        }
        initView();
    }

    private void initView() {
        mProbeid = findViewById(R.id.probeid);
        mUserid = findViewById(R.id.userid);
        mLogin = findViewById(R.id.login);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            startActivity(new Intent(this, ChatActivity.class));
        }
    }
}
