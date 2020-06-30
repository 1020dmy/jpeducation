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
public class WxInfo implements Parcelable {


    /**
     * appid : wx760dbdd41ef089a5
     * partnerid : 1532340281
     * prepayid : wx301531064677895fb51f02c91610870200
     * package : Sign=WXPay
     * noncestr : g7nBOZ2bsW8jrh8f
     * timestamp : 1593502266
     * sign : 84654440CAB7308E2DAD9AB00C7250F0
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private int timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appid);
        dest.writeString(this.partnerid);
        dest.writeString(this.prepayid);
        dest.writeString(this.noncestr);
        dest.writeInt(this.timestamp);
        dest.writeString(this.sign);
    }

    public WxInfo() {
    }

    protected WxInfo(Parcel in) {
        this.appid = in.readString();
        this.partnerid = in.readString();
        this.prepayid = in.readString();
        this.noncestr = in.readString();
        this.timestamp = in.readInt();
        this.sign = in.readString();
    }

    public static final Creator<WxInfo> CREATOR = new Creator<WxInfo>() {
        @Override
        public WxInfo createFromParcel(Parcel source) {
            return new WxInfo(source);
        }

        @Override
        public WxInfo[] newArray(int size) {
            return new WxInfo[size];
        }
    };
}
