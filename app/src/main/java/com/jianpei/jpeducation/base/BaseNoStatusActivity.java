package com.jianpei.jpeducation.base;


import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.jianpei.jpeducation.utils.StatusBarUtil;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseNoStatusActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this);
        super.onCreate(savedInstanceState);


    }

    public void setStatusColor(int color) {

        StatusBarUtil.setColor(this, color);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void setTitleViewPadding(View view) {
        int statusH = getStatusBarHeight();
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = statusH;// 控件的宽强制设成30
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

    }

    public void setTitleViewPadding2(View view) {
        int statusH = getStatusBarHeight();
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = statusH;// 控件的宽强制设成30
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

    }

    private int getStatusBarHeight() {
        return getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }

}
