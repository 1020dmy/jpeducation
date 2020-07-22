package com.jianpei.jpeducation.bean.tiku;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AnswerBean {


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

    private boolean isSelect;

    private String is_selected;

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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
}
