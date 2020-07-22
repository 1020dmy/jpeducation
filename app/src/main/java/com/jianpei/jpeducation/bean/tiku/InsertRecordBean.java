package com.jianpei.jpeducation.bean.tiku;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertRecordBean {

    private RecordInfoBean record_info;

    private GetQuestionBean answer_info;

    public RecordInfoBean getRecord_info() {
        return record_info;
    }

    public void setRecord_info(RecordInfoBean record_info) {
        this.record_info = record_info;
    }

    public GetQuestionBean getAnswer_info() {
        return answer_info;
    }

    public void setAnswer_info(GetQuestionBean answer_info) {
        this.answer_info = answer_info;
    }
}
