package com.jianpei.jpeducation.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupInfoBean implements Parcelable {


    /**
     * id : 10
     * title : 品质基础班
     * sub_title :
     * status : 1
     * img :
     * deleted : 0
     * end_time : 1632927600
     */

    private String id;
    private String title;
    private String sub_title;
    private String status;
    private String img;
    private String deleted;
    private String end_time;
    private String cat_id;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.sub_title);
        dest.writeString(this.status);
        dest.writeString(this.img);
        dest.writeString(this.deleted);
        dest.writeString(this.end_time);
        dest.writeString(this.cat_id);

    }

    public GroupInfoBean() {
    }

    protected GroupInfoBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.sub_title = in.readString();
        this.status = in.readString();
        this.img = in.readString();
        this.deleted = in.readString();
        this.end_time = in.readString();
        this.cat_id = in.readString();

    }

    public static final Parcelable.Creator<GroupInfoBean> CREATOR = new Parcelable.Creator<GroupInfoBean>() {
        @Override
        public GroupInfoBean createFromParcel(Parcel source) {
            return new GroupInfoBean(source);
        }

        @Override
        public GroupInfoBean[] newArray(int size) {
            return new GroupInfoBean[size];
        }
    };



}
