package com.jianpei.jpeducation.bean.school;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class EvaluationDataBean implements Parcelable {


    /**
     * id : 2
     * thread_id : 1
     * post_id : 0
     * user_id : 2415
     * user_id_at : 0
     * content : 多福多寿犯得上
     * like_num : 22
     * created_at :
     * created_at_str :
     * is_del : 0
     * user_name : 米乐
     * user_img :
     * is_post : 0
     * is_praise : 0
     * evaluation_count : 1
     */

    private String id;
    private String thread_id;
    private String post_id;
    private String user_id;
    private String user_id_at;
    private String content;
    private String like_num;
    private String created_at;
    private String created_at_str;
    private String is_del;
    private String user_name;
    private String user_img;
    private String is_post;
    private String is_praise;
    private String evaluation_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id_at() {
        return user_id_at;
    }

    public void setUser_id_at(String user_id_at) {
        this.user_id_at = user_id_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at_str() {
        return created_at_str;
    }

    public void setCreated_at_str(String created_at_str) {
        this.created_at_str = created_at_str;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getIs_post() {
        return is_post;
    }

    public void setIs_post(String is_post) {
        this.is_post = is_post;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getEvaluation_count() {
        return evaluation_count;
    }

    public void setEvaluation_count(String evaluation_count) {
        this.evaluation_count = evaluation_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.thread_id);
        dest.writeString(this.post_id);
        dest.writeString(this.user_id);
        dest.writeString(this.user_id_at);
        dest.writeString(this.content);
        dest.writeString(this.like_num);
        dest.writeString(this.created_at);
        dest.writeString(this.created_at_str);
        dest.writeString(this.is_del);
        dest.writeString(this.user_name);
        dest.writeString(this.user_img);
        dest.writeString(this.is_post);
        dest.writeString(this.is_praise);
        dest.writeString(this.evaluation_count);
    }

    public EvaluationDataBean() {
    }

    protected EvaluationDataBean(Parcel in) {
        this.id = in.readString();
        this.thread_id = in.readString();
        this.post_id = in.readString();
        this.user_id = in.readString();
        this.user_id_at = in.readString();
        this.content = in.readString();
        this.like_num = in.readString();
        this.created_at = in.readString();
        this.created_at_str = in.readString();
        this.is_del = in.readString();
        this.user_name = in.readString();
        this.user_img = in.readString();
        this.is_post = in.readString();
        this.is_praise = in.readString();
        this.evaluation_count = in.readString();
    }

    public static final Parcelable.Creator<EvaluationDataBean> CREATOR = new Parcelable.Creator<EvaluationDataBean>() {
        @Override
        public EvaluationDataBean createFromParcel(Parcel source) {
            return new EvaluationDataBean(source);
        }

        @Override
        public EvaluationDataBean[] newArray(int size) {
            return new EvaluationDataBean[size];
        }
    };
}
