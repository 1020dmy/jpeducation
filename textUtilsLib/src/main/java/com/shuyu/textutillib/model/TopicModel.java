package com.shuyu.textutillib.model;

import java.io.Serializable;

/**
 * 话题model
 * Created by guoshuyu on 2017/8/16.
 */

public class TopicModel implements Serializable {
    /**
     * 话题名字内部不能有#和空格
     */
    private String topicName;
    private String topicId;
    private String viewNums;

    public TopicModel() {

    }

    public TopicModel(String topicName, String topicId) {
        this.topicName = topicName;
        this.topicId = topicId;
    }

    public TopicModel(String topicName, String topicId, String viewNums) {
        this.topicName = topicName;
        this.topicId = topicId;
        this.viewNums = viewNums;
    }

    public String getViewNums() {
        return viewNums;
    }

    public void setViewNums(String viewNums) {
        this.viewNums = viewNums;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return this.topicName;
    }
}
