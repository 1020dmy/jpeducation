package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ImputedPriceBean implements Parcelable {


    /**
     * huod_id : 41
     * total_price : 3960
     * price : 3950
     */

    private String huod_id;
    private String total_price;
    private String price;

    private String is_material;

    public String getIs_material() {
        return is_material;
    }

    public void setIs_material(String is_material) {
        this.is_material = is_material;
    }

    public String getHuod_id() {
        return huod_id;
    }

    public void setHuod_id(String huod_id) {
        this.huod_id = huod_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.huod_id);
        dest.writeString(this.total_price);
        dest.writeString(this.price);
        dest.writeString(this.is_material);
    }

    public ImputedPriceBean() {
    }

    protected ImputedPriceBean(Parcel in) {
        this.huod_id = in.readString();
        this.total_price = in.readString();
        this.price = in.readString();
        this.is_material = in.readString();
    }

    public static final Creator<ImputedPriceBean> CREATOR = new Creator<ImputedPriceBean>() {
        @Override
        public ImputedPriceBean createFromParcel(Parcel source) {
            return new ImputedPriceBean(source);
        }

        @Override
        public ImputedPriceBean[] newArray(int size) {
            return new ImputedPriceBean[size];
        }
    };
}
