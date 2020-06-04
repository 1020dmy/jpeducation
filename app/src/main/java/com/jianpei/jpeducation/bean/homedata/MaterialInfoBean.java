package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialInfoBean extends ProviderMultiEntity implements Parcelable {

    @Override
    public int getItemType() {
        return MTI;
    }

    /**
     * id : 2376
     * type_id : 4
     * cat_id : 107
     * class_id : 25
     * title : 建筑结构精讲班课件01.pdf
     * file_type : pdf
     * file_size : 8.991659M
     * oss_path : 建筑师类/一级建筑师/
     * year_of : null
     * is_free : 0
     * price : 0.00
     * give_by_class : 1
     * download : 0
     * created_at : 2020-04-28 09:16:49
     * expired_at : null
     * chapter_id : 0
     * sort : 0
     * total : 0
     * is_rec : 0
     */



    private String id;
    private String type_id;
    private String cat_id;
    private String class_id;
    private String title;
    private String file_type;
    private String file_size;
    private String oss_path;
    private String year_of;
    private String is_free;
    private String price;
    private String give_by_class;
    private String download;
    private String created_at;
    private String expired_at;
    private String chapter_id;
    private String sort;
    private String total;
    private String is_rec;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getOss_path() {
        return oss_path;
    }

    public void setOss_path(String oss_path) {
        this.oss_path = oss_path;
    }

    public String getYear_of() {
        return year_of;
    }

    public void setYear_of(String year_of) {
        this.year_of = year_of;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGive_by_class() {
        return give_by_class;
    }

    public void setGive_by_class(String give_by_class) {
        this.give_by_class = give_by_class;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIs_rec() {
        return is_rec;
    }

    public void setIs_rec(String is_rec) {
        this.is_rec = is_rec;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type_id);
        dest.writeString(this.cat_id);
        dest.writeString(this.class_id);
        dest.writeString(this.title);
        dest.writeString(this.file_type);
        dest.writeString(this.file_size);
        dest.writeString(this.oss_path);
        dest.writeString(this.year_of);
        dest.writeString(this.is_free);
        dest.writeString(this.price);
        dest.writeString(this.give_by_class);
        dest.writeString(this.download);
        dest.writeString(this.created_at);
        dest.writeString(this.expired_at);
        dest.writeString(this.chapter_id);
        dest.writeString(this.sort);
        dest.writeString(this.total);
        dest.writeString(this.is_rec);
    }

    public MaterialInfoBean() {
    }

    protected MaterialInfoBean(Parcel in) {
        this.id = in.readString();
        this.type_id = in.readString();
        this.cat_id = in.readString();
        this.class_id = in.readString();
        this.title = in.readString();
        this.file_type = in.readString();
        this.file_size = in.readString();
        this.oss_path = in.readString();
        this.year_of = in.readString();
        this.is_free = in.readString();
        this.price = in.readString();
        this.give_by_class = in.readString();
        this.download = in.readString();
        this.created_at = in.readString();
        this.expired_at = in.readString();
        this.chapter_id = in.readString();
        this.sort = in.readString();
        this.total = in.readString();
        this.is_rec = in.readString();
    }

    public static final Parcelable.Creator<MaterialInfoBean> CREATOR = new Parcelable.Creator<MaterialInfoBean>() {
        @Override
        public MaterialInfoBean createFromParcel(Parcel source) {
            return new MaterialInfoBean(source);
        }

        @Override
        public MaterialInfoBean[] newArray(int size) {
            return new MaterialInfoBean[size];
        }
    };
}
