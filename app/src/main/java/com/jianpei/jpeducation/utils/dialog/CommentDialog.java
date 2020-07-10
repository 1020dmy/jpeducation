package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentDialog extends AlertDialog {

    private Context mContext;
    private View contentView;

    public CommentDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;


        contentView = getLayoutInflater().inflate(R.layout.dialog_comment, null);//获取自定义布局
        setView(contentView);
    }
}
