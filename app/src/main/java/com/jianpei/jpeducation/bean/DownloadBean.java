package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadBean implements Parcelable {


    /**
     * is_pay : 0时不需要积分购买；1时需要积分购买
     * downloadUrl : http://jianpeizhibo.oss-cn-beijing.aliyuncs.com/%E5%BB%BA%E7%AD%91%E5%B8%88%E7%B1%BB/%E4%B8%80%E7%BA%A7%E5%BB%BA%E7%AD%91%E5%B8%88/?OSSAccessKeyId=LTAI4FmLh5akpo9df3XJpX4P&Expires=1590655719&Signature=U8fHL%2F6cO%2BDo2tJr%2BLfoiZkxGuY%3D
     */

    private String is_pay;
    private String downloadUrl;
    private String intergral_price;
    private String user_intergral;

    public String getIntergral_price() {
        return intergral_price;
    }

    public void setIntergral_price(String intergral_price) {
        this.intergral_price = intergral_price;
    }

    public String getUser_intergral() {
        return user_intergral;
    }

    public void setUser_intergral(String user_intergral) {
        this.user_intergral = user_intergral;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public DownloadBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.is_pay);
        dest.writeString(this.downloadUrl);
        dest.writeString(this.intergral_price);
        dest.writeString(this.user_intergral);
    }

    protected DownloadBean(Parcel in) {
        this.is_pay = in.readString();
        this.downloadUrl = in.readString();
        this.intergral_price = in.readString();
        this.user_intergral = in.readString();
    }

    public static final Creator<DownloadBean> CREATOR = new Creator<DownloadBean>() {
        @Override
        public DownloadBean createFromParcel(Parcel source) {
            return new DownloadBean(source);
        }

        @Override
        public DownloadBean[] newArray(int size) {
            return new DownloadBean[size];
        }
    };
}
