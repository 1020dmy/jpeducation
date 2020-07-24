package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/24
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AnswerScoreJson {
    private String score;
    private String questino_id;
    private String record_id;
    private String index_type;

    public AnswerScoreJson(String score, String questino_id, String record_id, String index_type) {
        this.score = score;
        this.questino_id = questino_id;
        this.record_id = record_id;
        this.index_type = index_type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getQuestino_id() {
        return questino_id;
    }

    public void setQuestino_id(String questino_id) {
        this.questino_id = questino_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getIndex_type() {
        return index_type;
    }

    public void setIndex_type(String index_type) {
        this.index_type = index_type;
    }
}
