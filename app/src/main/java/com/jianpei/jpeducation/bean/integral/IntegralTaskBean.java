package com.jianpei.jpeducation.bean.integral;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralTaskBean {


    /**
     * title : 签到打卡领积分
     * des : 签到数达到100天送30积分
     * icon : https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/integral/lALPGo_k8PhnXeXMnsyc_156_158.png
     * score : 1
     * operation : 1
     * is_finish : 0
     */

    private String title;
    private String des;
    private String icon;
    private String score;
    private String operation;
    private int is_finish;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }
}
