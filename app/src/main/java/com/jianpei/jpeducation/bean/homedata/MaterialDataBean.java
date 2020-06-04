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
public class MaterialDataBean implements Parcelable {

    /**
     * title : 资料下载
     * Subtitle : 热门套餐，轻松过关
     * data : [{"id":"2376","type_id":"4","cat_id":"107","class_id":"25","title":"建筑结构精讲班课件01.pdf","file_type":"pdf","file_size":"8.991659M","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:16:49","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"},{"id":"2370","type_id":"4","cat_id":"107","class_id":"26","title":"技术作图精讲班课件04.pdf","file_type":"pdf","file_size":"29.879296M","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:14:05","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"},{"id":"2371","type_id":"4","cat_id":"107","class_id":"26","title":"技术作图精讲班课件03.pdf","file_type":"pdf","file_size":"373.259717","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:15:21","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"}]
     */

    private String title;
    private String Subtitle;
    private List<MaterialInfoBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public List<MaterialInfoBean> getData() {
        return data;
    }

    public void setData(List<MaterialInfoBean> data) {
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

    public MaterialDataBean() {
    }

    protected MaterialDataBean(Parcel in) {
        this.title = in.readString();
        this.Subtitle = in.readString();
        this.data = in.createTypedArrayList(MaterialInfoBean.CREATOR);
    }

    public static final Parcelable.Creator<MaterialDataBean> CREATOR = new Parcelable.Creator<MaterialDataBean>() {
        @Override
        public MaterialDataBean createFromParcel(Parcel source) {
            return new MaterialDataBean(source);
        }

        @Override
        public MaterialDataBean[] newArray(int size) {
            return new MaterialDataBean[size];
        }
    };
}
