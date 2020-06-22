package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentDataBean implements Parcelable {

    /**
     * title : 限时团购
     * Subtitle : 热门套餐，轻松过关
     * data : [{"id":"42","man":null,"jian":null,"condition":null,"cat_id":"97","ks_time":"1590551756","dq_time":"1599551756","describe":null,"content":null,"create_time":"0","update_time":"0","img":"https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590657929.jpg","type":"regiment","point_id":"0","price":"10212120.00","num_people":"5","title":"","residual_time":8839339}]
     */

    private String title;
    private String Subtitle;
    private List<RegimentInfoBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String Subtitle) {
        this.Subtitle = Subtitle;
    }

    public List<RegimentInfoBean> getData() {
        return data;
    }

    public void setData(List<RegimentInfoBean> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.Subtitle);
        dest.writeTypedList(this.data);
    }

    public RegimentDataBean() {
    }

    protected RegimentDataBean(Parcel in) {
        this.title = in.readString();
        this.Subtitle = in.readString();
        this.data = in.createTypedArrayList(RegimentInfoBean.CREATOR);
    }

    public static final Creator<RegimentDataBean> CREATOR = new Creator<RegimentDataBean>() {
        @Override
        public RegimentDataBean createFromParcel(Parcel source) {
            return new RegimentDataBean(source);
        }

        @Override
        public RegimentDataBean[] newArray(int size) {
            return new RegimentDataBean[size];
        }
    };
}
