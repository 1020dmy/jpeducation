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
public class GroupDataBean implements Parcelable {

    /**
     * title : 免费试学课
     * Subtitle : 热门套餐，轻松过关
     * data : [{"id":"5","cat_id":"107","title":"三年畅学班","sub_title":"2020年一注九门全科","desc":null,"tags":null,"status":"1","teacher_ids":"{\"9\":\"5\",\"10\":\"4\"}","img":null,"content":null,"start_time":"0","end_time":"1669820400","year_num":"3","deleted":"0","add_time":"1574059051","sort_num":"3","buy_num":"3","coupon_str":""},{"id":"6","cat_id":"107","title":" 学霸推荐班","sub_title":"2020年一注三科作图+建筑结构 两年制","desc":null,"tags":null,"status":"1","teacher_ids":"","img":null,"content":null,"start_time":"0","end_time":"1638284400","year_num":"2","deleted":"0","add_time":"1574059120","sort_num":"2","buy_num":"2","coupon_str":""},{"id":"3","cat_id":"107","title":"定制享学班","sub_title":"2020年一注九门全科","desc":null,"tags":null,"status":"1","teacher_ids":"","img":null,"content":null,"start_time":"0","end_time":"82800","year_num":"1","deleted":"0","add_time":"1574058868","sort_num":"0","buy_num":"38","coupon_str":""}]
     */

    private String title;
    private String Subtitle;
    private List<GroupInfoBean> data;

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

    public List<GroupInfoBean> getData() {
        return data;
    }

    public void setData(List<GroupInfoBean> data) {
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

    public GroupDataBean() {
    }

    protected GroupDataBean(Parcel in) {
        this.title = in.readString();
        this.Subtitle = in.readString();
        this.data = in.createTypedArrayList(GroupInfoBean.CREATOR);
    }

    public static final Creator<GroupDataBean> CREATOR = new Creator<GroupDataBean>() {
        @Override
        public GroupDataBean createFromParcel(Parcel source) {
            return new GroupDataBean(source);
        }

        @Override
        public GroupDataBean[] newArray(int size) {
            return new GroupDataBean[size];
        }
    };
}
