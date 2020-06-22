package com.jianpei.jpeducation.bean.homedata;

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
public class BannerDataBean implements Parcelable {
    /**
     * id : 79
     * cid : 6
     * title : ttttttttt
     * url :
     * img : https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590549257.jpg
     * status : 1
     * start_time : 1590422400
     * end_time : 1684944000
     * create_time : 1590549408
     * update_time : 1590549408
     * sort : 1
     * simg :
     * wx_url :
     * app_jump_type : xueyuan
     * app_point_id : 1
     * cat_id : 97
     */

    private String id;
    private String cid;
    private String title;
    private String url;
    private String img;
    private String status;
    private String start_time;
    private String end_time;
    private String create_time;
    private String update_time;
    private String sort;
    private String simg;
    private String wx_url;
    private String app_jump_type;
    private String app_point_id;
    private String cat_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSimg() {
        return simg;
    }

    public void setSimg(String simg) {
        this.simg = simg;
    }

    public String getWx_url() {
        return wx_url;
    }

    public void setWx_url(String wx_url) {
        this.wx_url = wx_url;
    }

    public String getApp_jump_type() {
        return app_jump_type;
    }

    public void setApp_jump_type(String app_jump_type) {
        this.app_jump_type = app_jump_type;
    }

    public String getApp_point_id() {
        return app_point_id;
    }

    public void setApp_point_id(String app_point_id) {
        this.app_point_id = app_point_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cid);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.img);
        dest.writeString(this.status);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeString(this.sort);
        dest.writeString(this.simg);
        dest.writeString(this.wx_url);
        dest.writeString(this.app_jump_type);
        dest.writeString(this.app_point_id);
        dest.writeString(this.cat_id);
    }

    public BannerDataBean() {
    }

    protected BannerDataBean(Parcel in) {
        this.id = in.readString();
        this.cid = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.img = in.readString();
        this.status = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.sort = in.readString();
        this.simg = in.readString();
        this.wx_url = in.readString();
        this.app_jump_type = in.readString();
        this.app_point_id = in.readString();
        this.cat_id = in.readString();
    }

    public static final Creator<BannerDataBean> CREATOR = new Creator<BannerDataBean>() {
        @Override
        public BannerDataBean createFromParcel(Parcel source) {
            return new BannerDataBean(source);
        }

        @Override
        public BannerDataBean[] newArray(int size) {
            return new BannerDataBean[size];
        }
    };
}
