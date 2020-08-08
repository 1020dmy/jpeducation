package com.jianpei.jpeducation.view.contentedittext;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PublishContentTextSpan extends MetricAffectingSpan {

    private String showText;
    private long userId;

    public PublishContentTextSpan(String showText) {
        this.showText = showText;
    }


    public String getShowText() {
        return showText;
    }

    @Override
    public void updateMeasureState(TextPaint p) {

    }

    @Override
    public void updateDrawState(TextPaint tp) {

    }

}
