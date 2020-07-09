package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegrlPayJson implements Parcelable {

    private int type;//1看课，2做题，3下资料,4签到，5补签
    private String integrl;
    private String repair_time;

    public IntegrlPayJson(int type, String integrl, String repair_time) {
        this.type = type;
        this.integrl = integrl;
        this.repair_time = repair_time;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntegrl() {
        return integrl;
    }

    public void setIntegrl(String integrl) {
        this.integrl = integrl;
    }

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.integrl);
        dest.writeString(this.repair_time);

    }

    public IntegrlPayJson() {
    }

    protected IntegrlPayJson(Parcel in) {
        this.type = in.readInt();
        this.integrl = in.readString();
        this.repair_time=in.readString();

    }

    public static final Creator<IntegrlPayJson> CREATOR = new Creator<IntegrlPayJson>() {
        @Override
        public IntegrlPayJson createFromParcel(Parcel source) {
            return new IntegrlPayJson(source);
        }

        @Override
        public IntegrlPayJson[] newArray(int size) {
            return new IntegrlPayJson[size];
        }
    };
}
