package com.jianpei.jpeducation.bean.tiku;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperInfoBean {


    /**
     * id : 188
     * paper_name : 一级建造师-市政工程实务-模拟试题一
     * paper_type : 2
     * status : 0
     * answer_time : 60
     * cat_id : 97
     * class_id : 288
     * chapter_id : 0
     * true_topic_year : 2020
     * answer_number : 1
     * paper_type_str : 模拟题
     * single_des : 单选题型介绍（共20题，共20分）
     * single_son_des : 下列每小题的四个选项中，只有一项是符合题意的正确答案，多选或不选不得分
     * multiple_des : 多选题型介绍（共11题，共22分）
     * multiple_son_des : 下列每小题的备选答案中，有两个或两个以上符合题意的正确答案，至少有一个错项，少选、多选、错选均不得分
     * reply_des : 解答题型介绍（共5题，共15分）
     * reply_son_des : 根据题目分数自己评分上传
     * complete_status : 0
     */

    private String id;
    private String paper_name;
    private String paper_type;
    private String status;
    private String answer_time;
    private String cat_id;
    private String class_id;
    private String chapter_id;
    private String true_topic_year;
    private String answer_number;
    private String paper_type_str;
    private String single_des;
    private String single_son_des;
    private String multiple_des;
    private String multiple_son_des;
    private String reply_des;
    private String reply_son_des;
    private int complete_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer_time() {
        return answer_time;
    }

    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
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

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getTrue_topic_year() {
        return true_topic_year;
    }

    public void setTrue_topic_year(String true_topic_year) {
        this.true_topic_year = true_topic_year;
    }

    public String getAnswer_number() {
        return answer_number;
    }

    public void setAnswer_number(String answer_number) {
        this.answer_number = answer_number;
    }

    public String getPaper_type_str() {
        return paper_type_str;
    }

    public void setPaper_type_str(String paper_type_str) {
        this.paper_type_str = paper_type_str;
    }

    public String getSingle_des() {
        return single_des;
    }

    public void setSingle_des(String single_des) {
        this.single_des = single_des;
    }

    public String getSingle_son_des() {
        return single_son_des;
    }

    public void setSingle_son_des(String single_son_des) {
        this.single_son_des = single_son_des;
    }

    public String getMultiple_des() {
        return multiple_des;
    }

    public void setMultiple_des(String multiple_des) {
        this.multiple_des = multiple_des;
    }

    public String getMultiple_son_des() {
        return multiple_son_des;
    }

    public void setMultiple_son_des(String multiple_son_des) {
        this.multiple_son_des = multiple_son_des;
    }

    public String getReply_des() {
        return reply_des;
    }

    public void setReply_des(String reply_des) {
        this.reply_des = reply_des;
    }

    public String getReply_son_des() {
        return reply_son_des;
    }

    public void setReply_son_des(String reply_son_des) {
        this.reply_son_des = reply_son_des;
    }

    public int getComplete_status() {
        return complete_status;
    }

    public void setComplete_status(int complete_status) {
        this.complete_status = complete_status;
    }
}
