package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.jianpei.jpeducation.R;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentPopup extends PopupWindow {

    private Context mContext;
    private View contentView;


    public CommentPopup(Context context) {
        this.mContext = mContext;
        setHeight(ScreenUtils.getHeight(context) / 5 * 3);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_comment,
                null, false);
        setContentView(contentView);
    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

}
