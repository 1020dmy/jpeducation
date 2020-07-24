package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GetQuestionBean implements Parcelable {


    /**
     * id : 3685
     * question_name : 用氯丁乳胶水泥砂浆衬里的防腐蚀方法属于()
     * type : 1
     * answer_total_num : 0
     * answer_succ_num :
     * explain : <p>设备及管道覆盖层主要有：涂料涂层、金属涂层、衬里、管道防腐层几种形式。</p>
     * before_answer_id : 3684
     * current_answer_id : 3685
     * next_answer_id : 3686
     * succ_answer : A
     * my_answer :
     * question_score : 1
     */

    private String id;
    private String question_name;
    private String type;
    private String answer_total_num;
    private String answer_succ_num;
    private String explain;
    private String before_answer_id;
    private String current_answer_id;
    private String next_answer_id;
    private String succ_answer;
    private String my_answer;
    private String question_score;
    private String question_index;
    private String is_favorites;

    private int question_total_num;

    public int getQuestion_total_num() {
        return question_total_num;
    }

    public void setQuestion_total_num(int question_total_num) {
        this.question_total_num = question_total_num;
    }

    public String getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(String is_favorites) {
        this.is_favorites = is_favorites;
    }

    private List<AnswerBean> answer_list;

    public String getQuestion_index() {
        return question_index;
    }

    public void setQuestion_index(String question_index) {
        this.question_index = question_index;
    }

    public List<AnswerBean> getAnswer_list() {
        return answer_list;
    }

    public void setAnswer_list(List<AnswerBean> answer_list) {
        this.answer_list = answer_list;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer_total_num() {
        return answer_total_num;
    }

    public void setAnswer_total_num(String answer_total_num) {
        this.answer_total_num = answer_total_num;
    }

    public String getAnswer_succ_num() {
        return answer_succ_num;
    }

    public void setAnswer_succ_num(String answer_succ_num) {
        this.answer_succ_num = answer_succ_num;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getBefore_answer_id() {
        return before_answer_id;
    }

    public void setBefore_answer_id(String before_answer_id) {
        this.before_answer_id = before_answer_id;
    }

    public String getCurrent_answer_id() {
        return current_answer_id;
    }

    public void setCurrent_answer_id(String current_answer_id) {
        this.current_answer_id = current_answer_id;
    }

    public String getNext_answer_id() {
        return next_answer_id;
    }

    public void setNext_answer_id(String next_answer_id) {
        this.next_answer_id = next_answer_id;
    }

    public String getSucc_answer() {
        return succ_answer;
    }

    public void setSucc_answer(String succ_answer) {
        this.succ_answer = succ_answer;
    }

    public String getMy_answer() {
        return my_answer;
    }

    public void setMy_answer(String my_answer) {
        this.my_answer = my_answer;
    }

    public String getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(String question_score) {
        this.question_score = question_score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.question_name);
        dest.writeString(this.type);
        dest.writeString(this.answer_total_num);
        dest.writeString(this.answer_succ_num);
        dest.writeString(this.explain);
        dest.writeString(this.before_answer_id);
        dest.writeString(this.current_answer_id);
        dest.writeString(this.next_answer_id);
        dest.writeString(this.succ_answer);
        dest.writeString(this.my_answer);
        dest.writeString(this.question_score);
        dest.writeString(this.question_index);
        dest.writeString(this.is_favorites);
        dest.writeInt(this.question_total_num);
        dest.writeList(this.answer_list);
    }

    public GetQuestionBean() {
    }

    protected GetQuestionBean(Parcel in) {
        this.id = in.readString();
        this.question_name = in.readString();
        this.type = in.readString();
        this.answer_total_num = in.readString();
        this.answer_succ_num = in.readString();
        this.explain = in.readString();
        this.before_answer_id = in.readString();
        this.current_answer_id = in.readString();
        this.next_answer_id = in.readString();
        this.succ_answer = in.readString();
        this.my_answer = in.readString();
        this.question_score = in.readString();
        this.question_index = in.readString();
        this.is_favorites = in.readString();
        this.question_total_num = in.readInt();
        this.answer_list = new ArrayList<AnswerBean>();
        in.readList(this.answer_list, AnswerBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetQuestionBean> CREATOR = new Parcelable.Creator<GetQuestionBean>() {
        @Override
        public GetQuestionBean createFromParcel(Parcel source) {
            return new GetQuestionBean(source);
        }

        @Override
        public GetQuestionBean[] newArray(int size) {
            return new GetQuestionBean[size];
        }
    };
}
