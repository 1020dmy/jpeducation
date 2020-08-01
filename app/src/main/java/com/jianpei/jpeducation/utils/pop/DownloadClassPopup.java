package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.telephony.RadioAccessSpecifier;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.vodplayerview.utils.DensityUtil;
import com.aliyun.vodplayerview.utils.FixedToastUtils;
import com.aliyun.vodplayerview.utils.ImageLoader;
import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.classdownload.AliyunDownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.ClarityAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
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
//    private RecyclerView recyclerView;

    private Button button;
    private List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos;

    private RadioGroup rg_quality_list;
    private AliyunDownloadMediaInfo downLoadTag;

    private MyClickListener myClickListener;

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

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
        rg_quality_list = contentView.findViewById(R.id.rg_quality_list);

//        recyclerView = contentView.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (myClickListener != null) {
                    myClickListener.ClickListener(downLoadTag);
                }
            }
        });

//        setData();

        showAllDownloadItems();


    }

//    protected void setData() {
//        if (aliyunDownloadMediaInfos != null && aliyunDownloadMediaInfos.size() > 0) {
////            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = aliyunDownloadMediaInfos.get(0);
////
////            tv_title.setText(aliyunDownloadMediaInfo.getTitle());
////            tv_size.setText(aliyunDownloadMediaInfo.getSizeStr());
////
////            recyclerView.setAdapter(new ClarityAdapter(aliyunDownloadMediaInfos));
//
//        }
//    }

    private void showAllDownloadItems() {
        if (aliyunDownloadMediaInfos == null || aliyunDownloadMediaInfos.isEmpty()) {
            return;
        }

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT, 1f);
        layoutParams.setMargins(0, 0, DensityUtil.px2dip(mContext, 16), 0);
//        int infoSize = list.size();
        Iterator<AliyunDownloadMediaInfo> iterator = aliyunDownloadMediaInfos.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            AliyunDownloadMediaInfo info = iterator.next();
//            String encript = info.isEncripted() == 1 ? mContext.getString(com.aliyun.vodplayer.R.string.encrypted)
//                    : mContext.getString(R.string.encrypted_no);
            RadioButton item = (RadioButton) LayoutInflater.from(mContext).inflate(com.aliyun.vodplayer.R.layout.view_item_quality,
                    new FrameLayout(mContext), false);

            item.setLayoutParams(layoutParams);
            item.setText(info.getQuality());
            item.setTag(info);
            ////设置id，供自动化测试用
//            int id = com.aliyun.vodplayer.R.id.custom_id_min + i;
//            item.setId(R.id.custom_id_min);
            rg_quality_list.addView(item);
//            i++;
        }

        if (rg_quality_list.getChildCount() > 0) {
            int checkId = rg_quality_list.getChildAt(0).getId();
            rg_quality_list.check(checkId);
            downLoadTag = (AliyunDownloadMediaInfo) rg_quality_list.findViewById(checkId).getTag();
        }
        rg_quality_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbChecked = contentView.findViewById(checkedId);
                downLoadTag = (AliyunDownloadMediaInfo) rbChecked.getTag();
                tv_size.setText(formatSizeDecimal(downLoadTag.getSize()));
            }
        });
        Glide.with(mContext).load(aliyunDownloadMediaInfos.get(0).getCoverUrl()).into(imageView);
        tv_title.setText(aliyunDownloadMediaInfos.get(0).getTitle());
        tv_size.setText(formatSizeDecimal(aliyunDownloadMediaInfos.get(0).getSize()));

    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 视频大小格式化,这里由于要和IOS同步,所以先四舍五入保留两位小数,再四舍五入保留一位小数
     */
    private String formatSizeDecimal(long size) {
        float kb = (size / 1024 * 1.0f);
        BigDecimal bigDecimal = new BigDecimal(kb);

        if (kb < 1024) {
            return String.format("%.1f", bigDecimal.setScale(2, RoundingMode.HALF_UP)) + "KB";
        }

        float mb = (kb / 1024 * 1.0f);
        BigDecimal decimal = new BigDecimal(mb);
        return String.format("%.1f", decimal.setScale(2, RoundingMode.HALF_UP)) + "MB";
    }


    public interface MyClickListener {
        void ClickListener(AliyunDownloadMediaInfo downLoadTag);
    }

}
