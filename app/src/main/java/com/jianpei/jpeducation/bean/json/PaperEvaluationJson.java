package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperEvaluationJson {

    private String record_id;

    private String confirm_status;

    public PaperEvaluationJson(String record_id, String confirm_status) {
        this.record_id = record_id;
        this.confirm_status = confirm_status;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(String confirm_status) {
        this.confirm_status = confirm_status;
    }
}
