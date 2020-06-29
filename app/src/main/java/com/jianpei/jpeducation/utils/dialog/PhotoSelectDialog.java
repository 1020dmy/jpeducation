package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.SelectphotoUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PhotoSelectDialog extends AlertDialog implements View.OnClickListener {


    private Context mContext;
    private View contentView;
    private TextView takePhotoTV, choosePhotoTV, cancelTV;
    //
    private SelectphotoUtils selectphotoUtils;


    public PhotoSelectDialog(@NonNull Context context,SelectphotoUtils selectphotoUtils) {
        super(context);
        this.mContext = context;
        this.selectphotoUtils=selectphotoUtils;
        contentView = getLayoutInflater().inflate(R.layout.dialog_photo_camera, null);//获取自定义布局
        setView(contentView);
        takePhotoTV = contentView.findViewById(R.id.photograph);
        choosePhotoTV = contentView.findViewById(R.id.photo);
        cancelTV = contentView.findViewById(R.id.cancel);
        takePhotoTV.setOnClickListener(this);
        choosePhotoTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photograph:
                if (selectphotoUtils != null)
                    selectphotoUtils.openGallery();
                dismiss();
                break;
            case R.id.photo:
                if (selectphotoUtils != null)
                    selectphotoUtils.takePhone();
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        selectphotoUtils = null;
        super.dismiss();
    }
}
