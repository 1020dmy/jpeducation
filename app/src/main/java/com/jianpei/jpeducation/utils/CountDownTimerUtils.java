package com.jianpei.jpeducation.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;


    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "s后重新获取");  //设置倒计时时间
//        mTextView.setBackgroundResource(R.drawable.shape_yzm_checked);//
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);//重新获得点击
//        mTextView.setBackgroundResource(R.drawable.shape_yzm_default);  //还原背景色

    }

    public void onDestroy() {
        mTextView = null;
        cancel();
    }
}
