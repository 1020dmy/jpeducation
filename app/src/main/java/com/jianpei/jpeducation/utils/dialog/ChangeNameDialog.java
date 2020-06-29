package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ChangeNameDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private View contentView;

    private EditText etName;
    private ImageView ivCancel;
    private Button btnSave;

    public ChangeNameDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        contentView = getLayoutInflater().inflate(R.layout.pop_userinfo_changename, null);//获取自定义布局
        setView(contentView);
        etName = contentView.findViewById(R.id.et_name);
        ivCancel = contentView.findViewById(R.id.iv_cancle);
        btnSave = contentView.findViewById(R.id.btn_save);
        ivCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_cancle:
                etName.setText("");
                break;
            case R.id.btn_save:
                dismiss();
                break;
        }

    }
}
