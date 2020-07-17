package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PostNewsResultDialog extends AlertDialog {
    private View contentView;
    private ImageView ivDismiss;

    public PostNewsResultDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context);
        contentView = getLayoutInflater().inflate(R.layout.dialog_postnews_result, null);//获取自定义布局
        setView(contentView);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ivDismiss = contentView.findViewById(R.id.iv_dismiss);
        ivDismiss.setOnClickListener(onClickListener);
    }
}
