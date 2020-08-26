package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AnswerBean implements Parcelable {


    /**
     * id : 15102
     * question_id : 3685
     * is_succ : 2
     * answers_info : 介质处理<p><br></p>
     * options_index : A
     */

    private String id;
    private String question_id;
    private String is_succ;
    private String answers_info;
    private String options_index;

//    private boolean isSelect;

    private int is_selected;//0未选择，1选择

    public int getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(int is_selected) {
        this.is_selected = is_selected;
    }

//    public boolean isSelect() {
//        return isSelect;
//    }

//    public void setSelect(boolean select) {
//        isSelect = select;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getIs_succ() {
        return is_succ;
    }

    public void setIs_succ(String is_succ) {
        this.is_succ = is_succ;
    }

    public String getAnswers_info() {
        return answers_info;
    }

    public void setAnswers_info(String answers_info) {
        this.answers_info = answers_info;
    }

    public String getOptions_index() {
        return options_index;
    }

    public void setOptions_index(String options_index) {
        this.options_index = options_index;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.question_id);
        dest.writeString(this.is_succ);
        dest.writeString(this.answers_info);
        dest.writeString(this.options_index);
        dest.writeInt(this.is_selected);
    }

    public AnswerBean() {
    }

    protected AnswerBean(Parcel in) {
        this.id = in.readString();
        this.question_id = in.readString();
        this.is_succ = in.readString();
        this.answers_info = in.readString();
        this.options_index = in.readString();
        this.is_selected = in.readInt();
    }

    public static final Creator<AnswerBean> CREATOR = new Creator<AnswerBean>() {
        @Override
        public AnswerBean createFromParcel(Parcel source) {
            return new AnswerBean(source);
        }

        @Override
        public AnswerBean[] newArray(int size) {
            return new AnswerBean[size];
        }
    };
}
