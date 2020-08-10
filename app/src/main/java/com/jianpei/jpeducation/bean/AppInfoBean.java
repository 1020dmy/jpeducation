package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.utils.AppUtils;
import com.jianpei.jpeducation.utils.EquipmentUtil;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AppInfoBean implements Parcelable {

    private String product;
    private String channel;
    private String version;
    private String model;
    private String device;
    private String packages;
    private String uid;
    private String imei;
    private String push_token;
    private String os_version;
    private String vendor;

    public static AppInfoBean appInfoBean = new AppInfoBean();


    private AppInfoBean() {
        product = "JP-A-MP";
        channel = "jianpei";
        version = AppUtils.getVersionName(MyApplication.getInstance());
        packages = AppUtils.getPackageName(MyApplication.getInstance());
        model = EquipmentUtil.getSystemModel();
        device = EquipmentUtil.getSystemDevice();
        imei = EquipmentUtil.getIMEI();
        os_version = EquipmentUtil.getSystemVersion();
        vendor = EquipmentUtil.getDeviceManufacturer();


    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product);
        dest.writeString(this.channel);
        dest.writeString(this.version);
        dest.writeString(this.model);
        dest.writeString(this.device);
        dest.writeString(this.uid);
        dest.writeString(this.imei);
        dest.writeString(this.push_token);
        dest.writeString(this.os_version);
        dest.writeString(this.vendor);
        dest.writeString(this.packages);

    }


    protected AppInfoBean(Parcel in) {
        this.product = in.readString();
        this.channel = in.readString();
        this.version = in.readString();
        this.model = in.readString();
        this.device = in.readString();
        this.uid = in.readString();
        this.imei = in.readString();
        this.push_token = in.readString();
        this.os_version = in.readString();
        this.vendor = in.readString();
        this.packages = in.readString();

    }

    public static final Creator<AppInfoBean> CREATOR = new Creator<AppInfoBean>() {
        @Override
        public AppInfoBean createFromParcel(Parcel source) {
            return new AppInfoBean(source);
        }

        @Override
        public AppInfoBean[] newArray(int size) {
            return new AppInfoBean[size];
        }
    };
}
