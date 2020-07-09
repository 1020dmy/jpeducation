package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralBuyDialog extends AlertDialog {
    private Context mContext;
    private View contentView;
    private TextView tv_xjifen, tv_title, tv_nums, tv_jifen;
    private Button btn_download, btn_dismiss;


    public IntegralBuyDialog(@NonNull Context context, View.OnClickListener onClickListener, String name, String num, String price, String total) {
        super(context);
        this.mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_download, null, false);
        setView(contentView);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv_xjifen = contentView.findViewById(R.id.tv_xjifen);
        tv_title = contentView.findViewById(R.id.tv_title);
        tv_nums = contentView.findViewById(R.id.tv_nums);
        tv_jifen = contentView.findViewById(R.id.tv_jifen);
        btn_download = contentView.findViewById(R.id.btn_download);
        btn_dismiss = contentView.findViewById(R.id.btn_dismiss);
        setData(onClickListener,name, num, price, total);
    }

    public void setData(View.OnClickListener onClickListener, String name, String num, String price, String total) {
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_download.setOnClickListener(onClickListener);
        tv_xjifen.setText(price);
        tv_title.setText(name);
        tv_nums.setText("已有"+num+"人下载");
        tv_jifen.setText("您现在有"+total+"积分");


    }
}
