package com.jianpei.jpeducation.bean.userinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UserInfoBean implements Parcelable {


    private String id;
    private String user_name;
    private String img;
    private String sex;
    private int is_attention;//0未关注1一关注2自己

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }




    /**
     * id : 3821
     * user_name : 15801080542
     * phone : 15801080542
     * is_line : 1
     * password :
     * level_intergral : 0
     * intergral : 0.00
     * img : /Source/pc/images/shiimg/a15.png
     * bathday :
     * area :
     * city :
     * province :
     * industry :
     * sex : 0
     * info :
     * is_black : 1
     * is_ice : 1
     * time : 1
     * add_time : 1589160001
     * total_time : 0
     * article_id :
     * zlquan : 0
     * age : 21
     * email : wwe@rrr.com
     * cars :
     * collection :
     * yaoqingid : 0
     * token_unionid :
     * last_session_id :
     * lastime : 1589160001
     * tagent_id : 0
     * search : 0
     * referer : 0
     * product : TEST-PRODUCT
     * channel : TEST-CHANNEL
     * version : 1.0
     * model : TEST-IPHONE
     * os_version : 13.2.12
     * vendor : TEST-VENDOR
     * imei : TEST-IMEI
     * last_product : TEST-PRODUCT
     * last_channel : TEST-CHANNEL
     * last_version : 1.0
     * push_token : TEST-TOKEN-dfssfsd
     * edit_time : 1589162315
     * lastime_str : 2020-05-11
     * message_num : 2
     * unpaid_num : 0
     */

//    private String id;
//    private String user_name;
//    private String phone;
//    private String is_line;
//    private String password;
//    private String level_intergral;
//    private String intergral;
//    private String img;
//    private String bathday;
//    private String area;
//    private String city;
//    private String province;
//    private String industry;
//    private String sex;
//    private String info;
//    private String is_black;
//    private String is_ice;
//    private String time;
//    private String add_time;
//    private String total_time;
//    private String article_id;
//    private String zlquan;
//    private String age;
//    private String email;
//    private String cars;
//    private String collection;
//    private String yaoqingid;
//    private String token_unionid;
//    private String last_session_id;
//    private String lastime;
//    private String tagent_id;
//    private String search;
//    private String referer;
//    private String product;
//    private String channel;
//    private String version;
//    private String model;
//    private String os_version;
//    private String vendor;
//    private String imei;
//    private String last_product;
//    private String last_channel;
//    private String last_version;
//    private String push_token;
//    private String edit_time;
//    private String lastime_str;
//    private String message_num;
//    private String unpaid_num;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getUser_name() {
//        return user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getIs_line() {
//        return is_line;
//    }
//
//    public void setIs_line(String is_line) {
//        this.is_line = is_line;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getLevel_intergral() {
//        return level_intergral;
//    }
//
//    public void setLevel_intergral(String level_intergral) {
//        this.level_intergral = level_intergral;
//    }
//
//    public String getIntergral() {
//        return intergral;
//    }
//
//    public void setIntergral(String intergral) {
//        this.intergral = intergral;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//
//    public String getBathday() {
//        return bathday;
//    }
//
//    public void setBathday(String bathday) {
//        this.bathday = bathday;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getIndustry() {
//        return industry;
//    }
//
//    public void setIndustry(String industry) {
//        this.industry = industry;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }
//
//    public String getIs_black() {
//        return is_black;
//    }
//
//    public void setIs_black(String is_black) {
//        this.is_black = is_black;
//    }
//
//    public String getIs_ice() {
//        return is_ice;
//    }
//
//    public void setIs_ice(String is_ice) {
//        this.is_ice = is_ice;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getAdd_time() {
//        return add_time;
//    }
//
//    public void setAdd_time(String add_time) {
//        this.add_time = add_time;
//    }
//
//    public String getTotal_time() {
//        return total_time;
//    }
//
//    public void setTotal_time(String total_time) {
//        this.total_time = total_time;
//    }
//
//    public String getArticle_id() {
//        return article_id;
//    }
//
//    public void setArticle_id(String article_id) {
//        this.article_id = article_id;
//    }
//
//    public String getZlquan() {
//        return zlquan;
//    }
//
//    public void setZlquan(String zlquan) {
//        this.zlquan = zlquan;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getCars() {
//        return cars;
//    }
//
//    public void setCars(String cars) {
//        this.cars = cars;
//    }
//
//    public String getCollection() {
//        return collection;
//    }
//
//    public void setCollection(String collection) {
//        this.collection = collection;
//    }
//
//    public String getYaoqingid() {
//        return yaoqingid;
//    }
//
//    public void setYaoqingid(String yaoqingid) {
//        this.yaoqingid = yaoqingid;
//    }
//
//    public String getToken_unionid() {
//        return token_unionid;
//    }
//
//    public void setToken_unionid(String token_unionid) {
//        this.token_unionid = token_unionid;
//    }
//
//    public String getLast_session_id() {
//        return last_session_id;
//    }
//
//    public void setLast_session_id(String last_session_id) {
//        this.last_session_id = last_session_id;
//    }
//
//    public String getLastime() {
//        return lastime;
//    }
//
//    public void setLastime(String lastime) {
//        this.lastime = lastime;
//    }
//
//    public String getTagent_id() {
//        return tagent_id;
//    }
//
//    public void setTagent_id(String tagent_id) {
//        this.tagent_id = tagent_id;
//    }
//
//    public String getSearch() {
//        return search;
//    }
//
//    public void setSearch(String search) {
//        this.search = search;
//    }
//
//    public String getReferer() {
//        return referer;
//    }
//
//    public void setReferer(String referer) {
//        this.referer = referer;
//    }
//
//    public String getProduct() {
//        return product;
//    }
//
//    public void setProduct(String product) {
//        this.product = product;
//    }
//
//    public String getChannel() {
//        return channel;
//    }
//
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public String getOs_version() {
//        return os_version;
//    }
//
//    public void setOs_version(String os_version) {
//        this.os_version = os_version;
//    }
//
//    public String getVendor() {
//        return vendor;
//    }
//
//    public void setVendor(String vendor) {
//        this.vendor = vendor;
//    }
//
//    public String getImei() {
//        return imei;
//    }
//
//    public void setImei(String imei) {
//        this.imei = imei;
//    }
//
//    public String getLast_product() {
//        return last_product;
//    }
//
//    public void setLast_product(String last_product) {
//        this.last_product = last_product;
//    }
//
//    public String getLast_channel() {
//        return last_channel;
//    }
//
//    public void setLast_channel(String last_channel) {
//        this.last_channel = last_channel;
//    }
//
//    public String getLast_version() {
//        return last_version;
//    }
//
//    public void setLast_version(String last_version) {
//        this.last_version = last_version;
//    }
//
//    public String getPush_token() {
//        return push_token;
//    }
//
//    public void setPush_token(String push_token) {
//        this.push_token = push_token;
//    }
//
//    public String getEdit_time() {
//        return edit_time;
//    }
//
//    public void setEdit_time(String edit_time) {
//        this.edit_time = edit_time;
//    }
//
//    public String getLastime_str() {
//        return lastime_str;
//    }
//
//    public void setLastime_str(String lastime_str) {
//        this.lastime_str = lastime_str;
//    }
//
//    public String getMessage_num() {
//        return message_num;
//    }
//
//    public void setMessage_num(String message_num) {
//        this.message_num = message_num;
//    }
//
//    public String getUnpaid_num() {
//        return unpaid_num;
//    }
//
//    public void setUnpaid_num(String unpaid_num) {
//        this.unpaid_num = unpaid_num;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_name);
        dest.writeString(this.img);
        dest.writeString(this.sex);
        dest.writeInt(this.is_attention);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.id = in.readString();
        this.user_name = in.readString();
        this.img = in.readString();
        this.sex = in.readString();
        this.is_attention = in.readInt();
    }

    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
