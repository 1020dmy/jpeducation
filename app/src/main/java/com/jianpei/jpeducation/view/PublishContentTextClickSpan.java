package com.jianpei.jpeducation.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PublishContentTextClickSpan extends ClickableSpan {
    private Context mContext ;
    public PublishContentTextClickSpan(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(mContext, "点了可点击链接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
