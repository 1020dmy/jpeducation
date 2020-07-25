package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class QuestionBean implements Parcelable {


    /**
     * id : 8
     * question_name : 你叫啥？
     * paper_id : 149
     * question_id : 3687
     * class_id : 52
     * fav_type : 1
     */

    private String id;
    private String question_name;
    private String paper_id;
    private String question_id;
    private String class_id;
    private String fav_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getFav_type() {
        return fav_type;
    }

    public void setFav_type(String fav_type) {
        this.fav_type = fav_type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.question_name);
        dest.writeString(this.paper_id);
        dest.writeString(this.question_id);
        dest.writeString(this.class_id);
        dest.writeString(this.fav_type);
    }

    public QuestionBean() {
    }

    protected QuestionBean(Parcel in) {
        this.id = in.readString();
        this.question_name = in.readString();
        this.paper_id = in.readString();
        this.question_id = in.readString();
        this.class_id = in.readString();
        this.fav_type = in.readString();
    }

    public static final Parcelable.Creator<QuestionBean> CREATOR = new Parcelable.Creator<QuestionBean>() {
        @Override
        public QuestionBean createFromParcel(Parcel source) {
            return new QuestionBean(source);
        }

        @Override
        public QuestionBean[] newArray(int size) {
            return new QuestionBean[size];
        }
    };
}
