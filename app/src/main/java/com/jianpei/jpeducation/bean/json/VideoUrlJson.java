package com.jianpei.jpeducation.bean.json;

public class VideoUrlJson {

    private String type;

    private String video_id;

    private String buy_id;

    public VideoUrlJson(String type, String video_id, String buy_id) {
        this.type = type;
        this.video_id = video_id;
        this.buy_id = buy_id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(String buy_id) {
        this.buy_id = buy_id;
    }
}
