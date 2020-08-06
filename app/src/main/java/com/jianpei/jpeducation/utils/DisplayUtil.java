package com.jianpei.jpeducation.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.jianpei.jpeducation.base.MyApplication;

import static android.content.Context.WINDOW_SERVICE;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DisplayUtil {

    public static int screenWidthPx; //屏幕宽 px
    public static int screenhightPx; //屏幕高 px
//    public static float density;//屏幕密度
//    public static int densityDPI;//屏幕密度


    public static void getWindowSize() {
        Resources resources = MyApplication.getInstance().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        screenWidthPx = displayMetrics.widthPixels;//当前屏幕宽
        screenhightPx = displayMetrics.heightPixels;//当前屏幕高
    }


    /**
     * dp转换成px
     */
    public static int dp2px(float dpValue) {
        float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue) {
        float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
