package com.jianpei.jpeducation.bean.classinfo;

import java.util.List;

public class RegimentBean {

    /**
     * id : 1
     * start_time : 1591681306
     * end_time : 1591781306
     * create_time : 1591681306
     * user_id : 2414
     * huod_id : 42
     * is_source : 1
     * user_name : 15801080541
     * img : http://localhost/Source/pc/images/shiimg/a15.png
     * num_people : 5
     * remaining_number : 4
     * remaining_time : 99441
     */

    private String id;
    private String start_time;
    private String end_time;
    private String create_time;
    private String user_id;
    private String huod_id;
    private String is_source;
    private String user_name;
    private String img;
    private String num_people;
    private int remaining_number;
    private long remaining_time;


    private List<RegimentBean> regiment_data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHuod_id() {
        return huod_id;
    }

    public void setHuod_id(String huod_id) {
        this.huod_id = huod_id;
    }

    public String getIs_source() {
        return is_source;
    }

    public void setIs_source(String is_source) {
        this.is_source = is_source;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNum_people() {
        return num_people;
    }

    public void setNum_people(String num_people) {
        this.num_people = num_people;
    }

    public int getRemaining_number() {
        return remaining_number;
    }

    public void setRemaining_number(int remaining_number) {
        this.remaining_number = remaining_number;
    }

    public long getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(long remaining_time) {
        this.remaining_time = remaining_time;
    }


    public List<RegimentBean> getRegiment_data() {
        return regiment_data;
    }

    public void setRegiment_data(List<RegimentBean> regiment_data) {
        this.regiment_data = regiment_data;
    }
}
