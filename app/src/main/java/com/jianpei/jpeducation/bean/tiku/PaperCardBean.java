package com.jianpei.jpeducation.bean.tiku;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperCardBean {
    private RecordInfoBean record_info;
    private List<CardBean> card_data;

    public RecordInfoBean getRecord_info() {
        return record_info;
    }

    public void setRecord_info(RecordInfoBean record_info) {
        this.record_info = record_info;
    }

    public List<CardBean> getCard_data() {
        return card_data;
    }

    public void setCard_data(List<CardBean> card_data) {
        this.card_data = card_data;
    }
}
