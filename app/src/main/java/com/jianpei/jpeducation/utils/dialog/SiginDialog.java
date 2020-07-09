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
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SiginDialog extends AlertDialog {
    private Context mContext;
    private View contentView;
    private ImageView ivCancel, imageView;


    public SiginDialog(@NonNull Context context, int type) {
        super(context);
        contentView = getLayoutInflater().inflate(R.layout.dialog_sigin_result, null);//获取自定义布局
        setView(contentView);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ivCancel = contentView.findViewById(R.id.iv_cancel);
        imageView = contentView.findViewById(R.id.imageView);


        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setData(type);
    }

    public void setData(int type) {
        if (type == 4) {
            imageView.setImageResource(R.drawable.sigin_tip);
        } else {
            imageView.setImageResource(R.drawable.sigin_tip_bq);

        }
    }
}
