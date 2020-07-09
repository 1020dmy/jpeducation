package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CustomerServicePopup extends PopupWindow {


    private Context mContext;
    private View contentView;
    private TextView tvCall, tvCancel;
    private String phoneNum;

    public CustomerServicePopup(Context context, String phone) {
        this.mContext = context;
        this.phoneNum=phone;

        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_call_customerservice, null, false);
        setContentView(contentView);

        tvCall = contentView.findViewById(R.id.tv_call);
        tvCancel = contentView.findViewById(R.id.tv_cancel);
        tvCall.setText("呼叫  " + phone);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });


    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

}
