package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertGardenJson {

    private String title;
    private String content;
    private String images;
    private String remind_id;
    private String topic_id;


    public InsertGardenJson(String title, String content, String images, String remind_id, String topic_id) {
        this.title = title;
        this.content = content;
        this.images = images;
        this.remind_id = remind_id;
        this.topic_id = topic_id;
    }

    public InsertGardenJson(String content, String images, String remind_id, String topic_id) {
        this.content = content;
        this.images = images;
        this.remind_id = remind_id;
        this.topic_id = topic_id;
    }

    public InsertGardenJson(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(String remind_id) {
        this.remind_id = remind_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
