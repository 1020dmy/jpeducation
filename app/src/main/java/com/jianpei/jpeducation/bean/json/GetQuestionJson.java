package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GetQuestionJson {
    private String source;
    private String index_type;
    private String question_id;
    //
    private String record_id;
    private String answering_time;
    //
    private String class_id;
    private String answer;

    //正常答题
    public GetQuestionJson(String source, String index_type, String question_id, String record_id, String answering_time, String answer) {
        this.source = source;
        this.index_type = index_type;
        this.question_id = question_id;
        this.record_id = record_id;
        this.answering_time = answering_time;
        this.answer = answer;
    }
    //收藏/错题
    public GetQuestionJson(String source, String index_type, String question_id, String class_id, String answer) {
        this.source = source;
        this.index_type = index_type;
        this.question_id = question_id;
        this.class_id = class_id;
        this.answer = answer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIndex_type() {
        return index_type;
    }

    public void setIndex_type(String index_type) {
        this.index_type = index_type;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getAnswering_time() {
        return answering_time;
    }

    public void setAnswering_time(String answering_time) {
        this.answering_time = answering_time;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
