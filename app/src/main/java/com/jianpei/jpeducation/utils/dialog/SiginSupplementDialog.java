package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SiginSupplementDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private View contentView;
    private TextView tvContent;
    private Button btnSubmit, btnCancel;
    private ImageView ivCancle;

    private MySubmitListener mySubmitListener;

    public void setMySubmitListener(MySubmitListener mySubmitListener) {
        this.mySubmitListener = mySubmitListener;
    }

    public SiginSupplementDialog(@NonNull Context context) {
        super(context);
        contentView = getLayoutInflater().inflate(R.layout.dialog_supplement_tip, null);//获取自定义布局
        setView(contentView);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tvContent = contentView.findViewById(R.id.tv_content);
        btnSubmit = contentView.findViewById(R.id.btn_submit);
        btnCancel = contentView.findViewById(R.id.btn_cancel);
        ivCancle = contentView.findViewById(R.id.iv_cancel);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        ivCancle.setOnClickListener(this);
    }

    public void setContent(String content) {
        tvContent.setText(content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                dismiss();
                if (mySubmitListener != null) {
                    mySubmitListener.onSubmitClick();
                }
                break;
            case R.id.btn_cancel:
            case R.id.iv_cancel:
                dismiss();
                break;
        }
    }

    public interface MySubmitListener {
        void onSubmitClick();
    }
}
