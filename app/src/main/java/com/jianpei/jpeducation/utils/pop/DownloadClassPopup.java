package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.telephony.RadioAccessSpecifier;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.nativeclass.MediaInfo;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.classdownload.AliyunDownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.ClarityAdapter;

import java.util.List;

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

    private ImageView ivCancel;
    private ImageView imageView;
    private TextView tv_title, tv_size;
    private RecyclerView recyclerView;

    private Button button;
    private List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos;

    public DownloadClassPopup(Context context, List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos) {
        this.aliyunDownloadMediaInfos = aliyunDownloadMediaInfos;
        this.mContext = context;
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.pop_download_class,
                null, false);
        setContentView(contentView);
        ivCancel = contentView.findViewById(R.id.iv_cancel);
        imageView = contentView.findViewById(R.id.imageView);
        tv_title = contentView.findViewById(R.id.tv_title);
        tv_size = contentView.findViewById(R.id.tv_size);
        button = contentView.findViewById(R.id.button);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setData();

    }

    protected void setData() {
        if (aliyunDownloadMediaInfos != null && aliyunDownloadMediaInfos.size() > 0) {
            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = aliyunDownloadMediaInfos.get(0);

            tv_title.setText(aliyunDownloadMediaInfo.getTitle());
            tv_size.setText(aliyunDownloadMediaInfo.getSizeStr());

            recyclerView.setAdapter(new ClarityAdapter(aliyunDownloadMediaInfos));

        }
    }


    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

}
