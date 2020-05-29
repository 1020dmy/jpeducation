package com.jianpei.jpeducation.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MyTextWatcher implements TextWatcher {

    protected ImageView imageView;

    public MyTextWatcher(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        imageView.setVisibility(TextUtils.isEmpty(s.toString()) ? View.INVISIBLE : View.VISIBLE);

    }
}
