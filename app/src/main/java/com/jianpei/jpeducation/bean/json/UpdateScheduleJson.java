package com.jianpei.jpeducation.bean.json;

import com.jianpei.jpeducation.utils.L;

public class UpdateScheduleJson {


    private String total_second;
    private String current_second;
    private String class_id;
    private String chapter_id;
    private String viod_id;
    private String buy_id;

    public UpdateScheduleJson(String total_second, String current_second, String class_id, String chapter_id, String viod_id, String buy_id) {

        this.total_second = total_second;
        this.current_second = current_second;
        this.class_id = class_id;
        this.chapter_id = chapter_id;
        this.viod_id = viod_id;
        this.buy_id = buy_id;
    }

    public String getTotal_second() {
        return total_second;
    }

    public void setTotal_second(String total_second) {
        this.total_second = total_second;
    }

    public String getCurrent_second() {
        return current_second;
    }

    public void setCurrent_second(String current_second) {
        this.current_second = current_second;
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

    public String getViod_id() {
        return viod_id;
    }

    public void setViod_id(String viod_id) {
        this.viod_id = viod_id;
    }

    public String getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(String buy_id) {
        this.buy_id = buy_id;
    }
}
