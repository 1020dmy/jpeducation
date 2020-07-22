package com.jianpei.jpeducation.bean.tiku;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CardBean {


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
}
