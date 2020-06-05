package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentInfoBean  implements Parcelable {



    /**
     * id : 42
     * man : null
     * jian : null
     * condition : null
     * cat_id : 97
     * ks_time : 1590551756
     * dq_time : 1599551756
     * describe : null
     * content : null
     * create_time : 0
     * update_time : 0
     * img : https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590657929.jpg
     * type : regiment
     * point_id : 0
     * price : 10212120.00
     * num_people : 5
     * title :
     * residual_time : 8839339
     */



    private String id;
    private String man;
    private String jian;
    private String condition;
    private String cat_id;
    private String ks_time;
    private String dq_time;
    private String describe;
    private String content;
    private String create_time;
    private String update_time;
    private String img;
    private String type;
    private String point_id;
    private String price;
    private String num_people;
    private String title;
    private int residual_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getJian() {
        return jian;
    }

    public void setJian(String jian) {
        this.jian = jian;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getKs_time() {
        return ks_time;
    }

    public void setKs_time(String ks_time) {
        this.ks_time = ks_time;
    }

    public String getDq_time() {
        return dq_time;
    }

    public void setDq_time(String dq_time) {
        this.dq_time = dq_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoint_id() {
        return point_id;
    }

    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum_people() {
        return num_people;
    }

    public void setNum_people(String num_people) {
        this.num_people = num_people;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResidual_time() {
        return residual_time;
    }

    public void setResidual_time(int residual_time) {
        this.residual_time = residual_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.man);
        dest.writeString(this.jian);
        dest.writeString(this.condition);
        dest.writeString(this.cat_id);
        dest.writeString(this.ks_time);
        dest.writeString(this.dq_time);
        dest.writeString(this.describe);
        dest.writeString(this.content);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeString(this.img);
        dest.writeString(this.type);
        dest.writeString(this.point_id);
        dest.writeString(this.price);
        dest.writeString(this.num_people);
        dest.writeString(this.title);
        dest.writeInt(this.residual_time);
    }

    public RegimentInfoBean() {
    }

    protected RegimentInfoBean(Parcel in) {
        this.id = in.readString();
        this.man = in.readString();
        this.jian = in.readString();
        this.condition = in.readString();
        this.cat_id = in.readString();
        this.ks_time = in.readString();
        this.dq_time = in.readString();
        this.describe = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.img = in.readString();
        this.type = in.readString();
        this.point_id = in.readString();
        this.price = in.readString();
        this.num_people = in.readString();
        this.title = in.readString();
        this.residual_time = in.readInt();
    }

    public static final Creator<RegimentInfoBean> CREATOR = new Creator<RegimentInfoBean>() {
        @Override
        public RegimentInfoBean createFromParcel(Parcel source) {
            return new RegimentInfoBean(source);
        }

        @Override
        public RegimentInfoBean[] newArray(int size) {
            return new RegimentInfoBean[size];
        }
    };
}
