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
public class PaperEvaluationBean implements Parcelable {

    private GetQuestionBean question_info;
    private EvaluationBean evaluation;

//    private List<GroupInfoBean> groupData;


//    public List<GroupInfoBean> getGroupData() {
//        return groupData;
//    }
//
//    public void setGroupData(List<GroupInfoBean> groupData) {
//        this.groupData = groupData;
//    }

    public EvaluationBean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationBean evaluation) {
        this.evaluation = evaluation;
    }

    public GetQuestionBean getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(GetQuestionBean question_info) {
        this.question_info = question_info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.evaluation, flags);
        dest.writeParcelable(this.question_info, flags);
    }

    public PaperEvaluationBean() {
    }

    protected PaperEvaluationBean(Parcel in) {
        this.evaluation = in.readParcelable(EvaluationBean.class.getClassLoader());
        this.question_info = in.readParcelable(GetQuestionBean.class.getClassLoader());
    }

    public static final Creator<PaperEvaluationBean> CREATOR = new Creator<PaperEvaluationBean>() {
        @Override
        public PaperEvaluationBean createFromParcel(Parcel source) {
            return new PaperEvaluationBean(source);
        }

        @Override
        public PaperEvaluationBean[] newArray(int size) {
            return new PaperEvaluationBean[size];
        }
    };
}
