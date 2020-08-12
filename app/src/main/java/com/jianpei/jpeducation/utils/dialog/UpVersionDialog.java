package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.utils.down.DownloadApkUtils;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UpVersionDialog extends AlertDialog {
    TextView tvContent;
    Button btnCancel;
    TextView line;
    Button btnUp;
    private Context mContext;
    private View contentView;

    private String url;


    public UpVersionDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        contentView = getLayoutInflater().inflate(R.layout.dialog_updata, null);//获取自定义布局
        setView(contentView);
        tvContent = contentView.findViewById(R.id.tv_content);
        btnCancel = contentView.findViewById(R.id.btn_cancel);
        line = contentView.findViewById(R.id.line);
        btnUp = contentView.findViewById(R.id.btn_up);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url))
                    DownloadApkUtils.startDownload(mContext, url, "建培教育");
                dismiss();
            }
        });

    }

    public void setData(VersionDetectBean versionDetectBean) {
        url = versionDetectBean.getFile_path();
        tvContent.setText(versionDetectBean.getHint());
        if ("1".equals(versionDetectBean.getIs_mandatory())) {
            btnCancel.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void show() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        super.show();
    }
}
