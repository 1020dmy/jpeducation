package com.jianpei.jpeducation.bean.shop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupBean implements Parcelable {


    /**
     * car_id : 14
     * title : 定制享学班
     * img : http://www.jianpei.com.cn/Source/pc/images/shiimg/a15.png
     * sub_title : 2020年一注九门全科
     * end_time : 82800
     * status : 1
     * price : 7800.00
     * class_name_str : 2020建筑材料与构造+2020建筑技术作图+全科套餐
     */

    private String car_id;
    private String title;
    private String img;
    private String sub_title;
    private String end_time;
    private String status;
    private String price;
    private String class_name_str;
    private String is_material;
    private String material_des;

    public String getIs_material() {
        return is_material;
    }

    public void setIs_material(String is_material) {
        this.is_material = is_material;
    }

    public String getMaterial_des() {
        return material_des;
    }

    public void setMaterial_des(String material_des) {
        this.material_des = material_des;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClass_name_str() {
        return class_name_str;
    }

    public void setClass_name_str(String class_name_str) {
        this.class_name_str = class_name_str;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.car_id);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeString(this.sub_title);
        dest.writeString(this.end_time);
        dest.writeString(this.status);
        dest.writeString(this.price);
        dest.writeString(this.class_name_str);
        dest.writeString(this.is_material);
        dest.writeString(this.material_des);
    }

    public GroupBean() {
    }

    protected GroupBean(Parcel in) {
        this.car_id = in.readString();
        this.title = in.readString();
        this.img = in.readString();
        this.sub_title = in.readString();
        this.end_time = in.readString();
        this.status = in.readString();
        this.price = in.readString();
        this.class_name_str = in.readString();
        this.is_material = in.readString();
        this.material_des = in.readString();
    }

    public static final Parcelable.Creator<GroupBean> CREATOR = new Parcelable.Creator<GroupBean>() {
        @Override
        public GroupBean createFromParcel(Parcel source) {
            return new GroupBean(source);
        }

        @Override
        public GroupBean[] newArray(int size) {
            return new GroupBean[size];
        }
    };
}
