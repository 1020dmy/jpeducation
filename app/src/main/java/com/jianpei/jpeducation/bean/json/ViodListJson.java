package com.jianpei.jpeducation.bean.json;

public class ViodListJson {

    private String class_id;
    private String chapter_id;
    private String type;

    public ViodListJson(String class_id, String chapter_id, String type) {
        this.class_id = class_id;
        this.chapter_id = chapter_id;
        this.type = type;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
