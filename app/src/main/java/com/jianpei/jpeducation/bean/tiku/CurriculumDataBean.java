package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/23
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CurriculumDataBean implements Parcelable {


    /**
     * id : 1
     * cat_id : 97
     * parent_id : 0
     * cur_name : 公路工程管理与实务
     */

    private String id;
    private String cat_id;
    private String parent_id;
    private String cur_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCur_name() {
        return cur_name;
    }

    public void setCur_name(String cur_name) {
        this.cur_name = cur_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cat_id);
        dest.writeString(this.parent_id);
        dest.writeString(this.cur_name);
    }

    public CurriculumDataBean() {
    }

    protected CurriculumDataBean(Parcel in) {
        this.id = in.readString();
        this.cat_id = in.readString();
        this.parent_id = in.readString();
        this.cur_name = in.readString();
    }

    public static final Parcelable.Creator<CurriculumDataBean> CREATOR = new Parcelable.Creator<CurriculumDataBean>() {
        @Override
        public CurriculumDataBean createFromParcel(Parcel source) {
            return new CurriculumDataBean(source);
        }

        @Override
        public CurriculumDataBean[] newArray(int size) {
            return new CurriculumDataBean[size];
        }
    };
}
