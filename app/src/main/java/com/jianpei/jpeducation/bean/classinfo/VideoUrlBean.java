package com.jianpei.jpeducation.bean.classinfo;

public class VideoUrlBean {
    /**
     * img : https://ke.jianpei.com.cn/54a949e56ef34246bf9575abb7ef426a/covers/c446b03db5df44a18ed67b0630a59340-00005.jpg
     * auth : eyJTZ3NjY2NTIzNDV9
     * vid : 54a949e56ef34246bf9575abb7ef426a
     * record_time : 0
     */

    private String img;
    private String auth;
    private String vid;
    private String record_time;
    private int  type;//0.播放，1下载


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }


//    /**
//     * img : 0005.jpg
//     * url : 74e-ld.m3u8
//     */
//
//    private String img;
//    private String url;
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }


}
