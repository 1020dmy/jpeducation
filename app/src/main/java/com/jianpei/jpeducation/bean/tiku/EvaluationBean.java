package com.jianpei.jpeducation.bean.tiku;

import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class EvaluationBean {


    /**
     * unsolved_num : 31
     * fail_num : 10
     * success_num : 6
     * total_score : 63
     * paper_score : 1
     * choose_score : 63
     * answer_score : 63
     * paper_name : 一级建造师-机电工程实务-历年真题-2019
     * create_time_str : 2020-07-07 14:25:07
     * answering_time_str : 1分1秒
     * answer_time_str : 1分
     * score_assessment :
     */

    private int unsolved_num;
    private int fail_num;
    private int success_num;
    private int total_score;
    private String paper_score;
    private int choose_score;
    private int answer_score;
    private String paper_name;
    private String create_time_str;
    private String answering_time_str;
    private String answer_time_str;
    private String score_assessment;

    private GroupInfoBean recommend_info;


    public GroupInfoBean getRecommend_info() {
        return recommend_info;
    }

    public void setRecommend_info(GroupInfoBean recommend_info) {
        this.recommend_info = recommend_info;
    }

    public int getUnsolved_num() {
        return unsolved_num;
    }

    public void setUnsolved_num(int unsolved_num) {
        this.unsolved_num = unsolved_num;
    }

    public int getFail_num() {
        return fail_num;
    }

    public void setFail_num(int fail_num) {
        this.fail_num = fail_num;
    }

    public int getSuccess_num() {
        return success_num;
    }

    public void setSuccess_num(int success_num) {
        this.success_num = success_num;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public String getPaper_score() {
        return paper_score;
    }

    public void setPaper_score(String paper_score) {
        this.paper_score = paper_score;
    }

    public int getChoose_score() {
        return choose_score;
    }

    public void setChoose_score(int choose_score) {
        this.choose_score = choose_score;
    }

    public int getAnswer_score() {
        return answer_score;
    }

    public void setAnswer_score(int answer_score) {
        this.answer_score = answer_score;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getAnswering_time_str() {
        return answering_time_str;
    }

    public void setAnswering_time_str(String answering_time_str) {
        this.answering_time_str = answering_time_str;
    }

    public String getAnswer_time_str() {
        return answer_time_str;
    }

    public void setAnswer_time_str(String answer_time_str) {
        this.answer_time_str = answer_time_str;
    }

    public String getScore_assessment() {
        return score_assessment;
    }

    public void setScore_assessment(String score_assessment) {
        this.score_assessment = score_assessment;
    }
}
