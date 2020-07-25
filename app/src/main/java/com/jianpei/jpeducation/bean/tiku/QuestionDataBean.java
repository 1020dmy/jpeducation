package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class QuestionDataBean implements Parcelable {

    private PageDataBean pageData;

    private List<QuestionBean> data;


    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<QuestionBean> getData() {
        return data;
    }

    public void setData(List<QuestionBean> data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.pageData, flags);
        dest.writeTypedList(this.data);
    }

    public QuestionDataBean() {
    }

    protected QuestionDataBean(Parcel in) {
        this.pageData = in.readParcelable(PageDataBean.class.getClassLoader());
        this.data = in.createTypedArrayList(QuestionBean.CREATOR);
    }

    public static final Parcelable.Creator<QuestionDataBean> CREATOR = new Parcelable.Creator<QuestionDataBean>() {
        @Override
        public QuestionDataBean createFromParcel(Parcel source) {
            return new QuestionDataBean(source);
        }

        @Override
        public QuestionDataBean[] newArray(int size) {
            return new QuestionDataBean[size];
        }
    };
}
