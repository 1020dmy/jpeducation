package com.jianpei.jpeducation.bean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentBean {


    /**
     * create_time_str : 2020-04-23 10:55:07
     * content : 谢谢王老师的课程教育
     * img : http://localhost/Source/pc/images/shiimg/a15.png
     * user_name : Zxiqi
     */

    private String create_time_str;
    private String content;
    private String img;
    private String user_name;

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
