package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
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

import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.vodplayerview.listener.QualityValue;
import com.aliyun.vodplayerview.utils.DensityUtil;
import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private Button button;

    private RadioGroup rg_quality_list;
    private TrackInfo downLoadTag;
    private ViodBean viodBean;

    private MyClickListener myClickListener;
    private Map<String, String> qualityList = new HashMap<>();


    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public DownloadClassPopup(Context context) {
        this.mContext = context;
        initQuality();
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
                    myClickListener.ClickListener(downLoadTag, viodBean);
                }
            }
        });

    }

    public void showAllDownloadItems(List<TrackInfo> trackInfos, ViodBean viodBean) {
        if (trackInfos == null) {
            return;
        }
        this.viodBean = viodBean;
        rg_quality_list.removeAllViews();

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT, 1f);
        layoutParams.setMargins(0, 0, DensityUtil.px2dip(mContext, 16), 0);
        for (TrackInfo trackInfo : trackInfos) {
            L.e("========清晰度：" + trackInfo.getVodDefinition());
            RadioButton item = (RadioButton) LayoutInflater.from(mContext).inflate(com.aliyun.vodplayer.R.layout.view_item_quality,
                    new FrameLayout(mContext), false);

            item.setLayoutParams(layoutParams);
            item.setText(qualityList.get(trackInfo.getVodDefinition()));
            item.setTag(trackInfo);
            ////设置id，供自动化测试用
            rg_quality_list.addView(item);

        }
        //        Iterator<TrackInfo> iterator = downloadMediaInfos.iterator();
//        while (iterator.hasNext()) {
//            ViodBean info = iterator.next();
//            RadioButton item = (RadioButton) LayoutInflater.from(mContext).inflate(com.aliyun.vodplayer.R.layout.view_item_quality,
//                    new FrameLayout(mContext), false);
//
//            item.setLayoutParams(layoutParams);
//            item.setText(qualityList.get(info.getQuality()));
//            item.setTag(info);
//            ////设置id，供自动化测试用
//            rg_quality_list.addView(item);
//        }

        if (rg_quality_list.getChildCount() > 0) {
            int checkId = rg_quality_list.getChildAt(0).getId();
            rg_quality_list.check(checkId);
            downLoadTag = (TrackInfo) rg_quality_list.findViewById(checkId).getTag();
        }
        rg_quality_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbChecked = contentView.findViewById(checkedId);
                downLoadTag = (TrackInfo) rbChecked.getTag();
                tv_size.setText(formatSizeDecimal(downLoadTag.getVodFileSize()));
            }
        });
        Glide.with(mContext).load(viodBean.getCoverUrl()).into(imageView);
        tv_title.setText(viodBean.getTitle());
        tv_size.setText(formatSizeDecimal(downLoadTag.getVodFileSize()));

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
        void ClickListener(TrackInfo trackInfo, ViodBean viodBean);
    }


    private void initQuality() {
        qualityList.put(QualityValue.QUALITY_FLUENT,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_fd_definition));
        qualityList.put(QualityValue.QUALITY_LOW,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_ld_definition));
        qualityList.put(QualityValue.QUALITY_STAND,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_sd_definition));
        qualityList.put(QualityValue.QUALITY_HIGH,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_hd_definition));
        qualityList.put(QualityValue.QUALITY_2K, mContext.getString(com.aliyun.vodplayer.R.string.alivc_k2_definition));
        qualityList.put(QualityValue.QUALITY_4K, mContext.getString(com.aliyun.vodplayer.R.string.alivc_k4_definition));
        qualityList.put(QualityValue.QUALITY_ORIGINAL,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_od_definition));
        qualityList.put(QualityValue.QUALITY_SQ,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_sq_definition));
        qualityList.put(QualityValue.QUALITY_HQ,
                mContext.getString(com.aliyun.vodplayer.R.string.alivc_hq_definition));
    }
}
