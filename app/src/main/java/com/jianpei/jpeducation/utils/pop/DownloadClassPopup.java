package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/14
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadClassPopup extends PopupWindow {


    private Context mContext;
    private View contentView;

    public DownloadClassPopup(Context context, MediaInfo mediaInfo) {
        this.mContext = context;
        setHeight(ScreenUtils.getHeight(context) / 5 * 3);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.pop_download_class,
                null, false);
        setContentView(contentView);
    }

    protected void setData(MediaInfo mediaInfo){


    }
}
