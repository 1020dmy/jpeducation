package com.jianpei.jpeducation.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/30
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderPaymentBean implements Parcelable {

    private WxInfo wx_info;

    private String ali_info;

    public String getAli_info() {
        return ali_info;
    }

    public void setAli_info(String ali_info) {
        this.ali_info = ali_info;
    }

    public WxInfo getWx_info() {
        return wx_info;
    }

    public void setWx_info(WxInfo wx_info) {
        this.wx_info = wx_info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.wx_info, flags);
        dest.writeString(this.ali_info);
    }

    public OrderPaymentBean() {
    }

    protected OrderPaymentBean(Parcel in) {
        this.wx_info = in.readParcelable(WxInfo.class.getClassLoader());
        this.ali_info = in.readString();
    }

    public static final Creator<OrderPaymentBean> CREATOR = new Creator<OrderPaymentBean>() {
        @Override
        public OrderPaymentBean createFromParcel(Parcel source) {
            return new OrderPaymentBean(source);
        }

        @Override
        public OrderPaymentBean[] newArray(int size) {
            return new OrderPaymentBean[size];
        }
    };
}
