package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SubmitPaperDialog extends AlertDialog {
    private Context mContext;
    private View contentView;
    private Button btn_submit, btn_cancel;

    public SubmitPaperDialog(@NonNull Context context, View.OnClickListener clickListener) {
        super(context);
        this.mContext = context;
        contentView = getLayoutInflater().inflate(R.layout.dialog_submit_paper, null);//获取自定义布局
        setView(contentView);
        btn_submit = contentView.findViewById(R.id.btn_submit);
        btn_cancel = contentView.findViewById(R.id.btn_cancel);
        btn_submit.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
