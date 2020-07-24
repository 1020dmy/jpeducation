package com.jianpei.jpeducation.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/24
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class URLDrawable extends BitmapDrawable {
    public Bitmap bitmap;

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }
}
