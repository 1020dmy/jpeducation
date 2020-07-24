package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CardBean implements Parcelable {


    /**
     * record_id : 94
     * question_id : 3678
     * is_complete : 2
     * answer_status : 0
     */

    private int record_id;
    private String question_id;
    private int is_complete;
    private int answer_status;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }

    public int getAnswer_status() {
        return answer_status;
    }

    public void setAnswer_status(int answer_status) {
        this.answer_status = answer_status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.record_id);
        dest.writeString(this.question_id);
        dest.writeInt(this.is_complete);
        dest.writeInt(this.answer_status);
    }

    public CardBean() {
    }

    protected CardBean(Parcel in) {
        this.record_id = in.readInt();
        this.question_id = in.readString();
        this.is_complete = in.readInt();
        this.answer_status = in.readInt();
    }

    public static final Parcelable.Creator<CardBean> CREATOR = new Parcelable.Creator<CardBean>() {
        @Override
        public CardBean createFromParcel(Parcel source) {
            return new CardBean(source);
        }

        @Override
        public CardBean[] newArray(int size) {
            return new CardBean[size];
        }
    };
}
