package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

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
public class OutAnswerDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private View contentView;
    private TextView tv_tip, tv_carryOn, tv_end, tv_cancel;

    private OutAnswerListener outAnswerListener;

    public void setOutAnswerListener(OutAnswerListener outAnswerListener) {
        this.outAnswerListener = outAnswerListener;
    }

    public OutAnswerDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        contentView = getLayoutInflater().inflate(R.layout.dialog_out_answer, null);//获取自定义布局
        setView(contentView);
        tv_tip = contentView.findViewById(R.id.tv_tip);
        tv_carryOn = contentView.findViewById(R.id.tv_carryOn);
        tv_end = contentView.findViewById(R.id.tv_end);
        tv_cancel = contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_carryOn.setOnClickListener(this);
        tv_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_carryOn:
                if (outAnswerListener != null)
                    outAnswerListener.onCarryOnClick();
                break;
            case R.id.tv_end:
                if (outAnswerListener != null)
                    outAnswerListener.onEndAnswerClick();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface OutAnswerListener {
        void onCarryOnClick();

        void onEndAnswerClick();

    }
}
