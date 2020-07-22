package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertRecordJson {

    private String paper_id;
    private String record_id;
    private String restart_type;

    public InsertRecordJson(String paper_id, String record_id, String restart_type) {
        this.paper_id = paper_id;
        this.record_id = record_id;
        this.restart_type = restart_type;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getRestart_type() {
        return restart_type;
    }

    public void setRestart_type(String restart_type) {
        this.restart_type = restart_type;
    }
}
