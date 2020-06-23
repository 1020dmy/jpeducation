package com.jianpei.jpeducation.bean.classinfo;


import java.util.List;

public class RegimentInfoBean {


    /**
     * regiment_des : 21
     * regiment_data : [{"id":"1","start_time":"1591681306","end_time":"1591781306","create_time":"1591681306","user_id":"2414","huod_id":"42","is_source":"1","user_name":"15801080541","img":"http://localhost/Source/pc/images/shiimg/a15.png","num_people":"5","remaining_number":4,"remaining_time":99441}]
     */

    private String regiment_des;
    private List<RegimentBean> regiment_data;

    public String getRegiment_des() {
        return regiment_des;
    }

    public void setRegiment_des(String regiment_des) {
        this.regiment_des = regiment_des;
    }

    public List<RegimentBean> getRegiment_data() {
        return regiment_data;
    }

    public void setRegiment_data(List<RegimentBean> regiment_data) {
        this.regiment_data = regiment_data;
    }


}
