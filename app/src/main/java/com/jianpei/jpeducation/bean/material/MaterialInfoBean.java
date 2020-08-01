package com.jianpei.jpeducation.bean.material;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.aliyun.vodplayerview.utils.download.AliyunDownloadMediaInfo;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Entity(tableName = "material")
public class MaterialInfoBean extends BaseNode implements Parcelable {


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


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "type_id")
    private String type_id;
    @ColumnInfo(name = "cat_id")
    private String cat_id;
    @ColumnInfo(name = "class_id")
    private String class_id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "file_type")
    private String file_type;
    @ColumnInfo(name = "file_size")
    private String file_size;
    @ColumnInfo(name = "oss_path")
    private String oss_path;
    @ColumnInfo(name = "year_of")
    private String year_of;
    @ColumnInfo(name = "is_free")
    private String is_free;
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "give_by_class")
    private String give_by_class;
    @ColumnInfo(name = "download")
    private String download;
    @ColumnInfo(name = "created_at")
    private String created_at;
    @ColumnInfo(name = "expired_at")
    private String expired_at;
    @ColumnInfo(name = "chapter_id")
    private String chapter_id;
    @ColumnInfo(name = "sort")
    private String sort;
    @ColumnInfo(name = "total")
    private String total;
    @ColumnInfo(name = "is_rec")
    private String is_rec;
    @ColumnInfo(name = "status")
    private String status = "下载";//2下载完成，0为开始，1开始下载，3下载错误；
    @ColumnInfo(name = "path")
    private String path;
    @ColumnInfo(name = "progress")
    private int progress = 0;


    private String downloadUrl;
    //改变状态，进度
//    private int tvDown;
//    private int progressBar;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

//    public int getTvDown() {
//        return tvDown;
//    }
//
//    public void setTvDown(int tvDown) {
//        this.tvDown = tvDown;
//    }
//
//    public int getProgressBar() {
//        return progressBar;
//    }
//
//    public void setProgressBar(int progressBar) {
//        this.progressBar = progressBar;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

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


    public MaterialInfoBean() {
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
        dest.writeString(this.status);
        dest.writeString(this.path);
        dest.writeInt(this.progress);
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
        this.status = in.readString();
        this.path = in.readString();
        this.progress = in.readInt();
    }

    public static final Creator<MaterialInfoBean> CREATOR = new Creator<MaterialInfoBean>() {
        @Override
        public MaterialInfoBean createFromParcel(Parcel source) {
            return new MaterialInfoBean(source);
        }

        @Override
        public MaterialInfoBean[] newArray(int size) {
            return new MaterialInfoBean[size];
        }
    };

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MaterialInfoBean that = (MaterialInfoBean) o;
        return id == that.id;
    }



}
