package com.jianpei.jpeducation.bean.classinfo;

public class GroupCouponBean {


    /**
     * id : 41
     * end_time_str : 2020-07-31 06:23:38
     * title : 满减券
     * describe : ￥1
     * is_receive : 1
     */

    private String id;
    private String end_time_str;
    private String title;
    private String describe;
    private int is_receive;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd_time_str() {
        return end_time_str;
    }

    public void setEnd_time_str(String end_time_str) {
        this.end_time_str = end_time_str;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(int is_receive) {
        this.is_receive = is_receive;
    }
}
