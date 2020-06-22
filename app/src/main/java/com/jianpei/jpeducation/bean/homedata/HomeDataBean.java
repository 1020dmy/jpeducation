package com.jianpei.jpeducation.bean.homedata;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HomeDataBean implements Parcelable {
    /**
     * customerServiceUrl : http://www.baidu.com
     * bannerData : [{"id":"79","cid":"6","title":"ttttttttt","url":"","img":"https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590549257.jpg","status":"1","start_time":"1590422400","end_time":"1684944000","create_time":"1590549408","update_time":"1590549408","sort":"1","simg":"","wx_url":"","app_jump_type":"xueyuan","app_point_id":"1","cat_id":"97"}]
     * huoDongData : [{"id":"41","man":"100","jian":"10","condition":null,"cat_id":"97","ks_time":"1590551756","dq_time":"1597551756","describe":"活动的描述","content":"这是活动的详情介绍","create_time":"1590551756","update_time":"1590551756","img":"https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590551858.jpg","type":"group","point_id":"37","price":"0.00","num_people":null,"title":null}]
     * regimentData : {"title":"限时团购","Subtitle":"热门套餐，轻松过关","data":[{"id":"42","man":null,"jian":null,"condition":null,"cat_id":"97","ks_time":"1590551756","dq_time":"1599551756","describe":null,"content":null,"create_time":"0","update_time":"0","img":"https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/1590657929.jpg","type":"regiment","point_id":"0","price":"10212120.00","num_people":"5","title":"","residual_time":8839339}]}
     * groupData : {"title":"免费试学课","Subtitle":"热门套餐，轻松过关","data":[{"id":"5","cat_id":"107","title":"三年畅学班","sub_title":"2020年一注九门全科","desc":null,"tags":null,"status":"1","teacher_ids":"{\"9\":\"5\",\"10\":\"4\"}","img":null,"content":null,"start_time":"0","end_time":"1669820400","year_num":"3","deleted":"0","add_time":"1574059051","sort_num":"3","buy_num":"3","coupon_str":""},{"id":"6","cat_id":"107","title":" 学霸推荐班","sub_title":"2020年一注三科作图+建筑结构 两年制","desc":null,"tags":null,"status":"1","teacher_ids":"","img":null,"content":null,"start_time":"0","end_time":"1638284400","year_num":"2","deleted":"0","add_time":"1574059120","sort_num":"2","buy_num":"2","coupon_str":""},{"id":"3","cat_id":"107","title":"定制享学班","sub_title":"2020年一注九门全科","desc":null,"tags":null,"status":"1","teacher_ids":"","img":null,"content":null,"start_time":"0","end_time":"82800","year_num":"1","deleted":"0","add_time":"1574058868","sort_num":"0","buy_num":"38","coupon_str":""}]}
     * MaterialData : {"title":"资料下载","Subtitle":"热门套餐，轻松过关","data":[{"id":"2376","type_id":"4","cat_id":"107","class_id":"25","title":"建筑结构精讲班课件01.pdf","file_type":"pdf","file_size":"8.991659M","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:16:49","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"},{"id":"2370","type_id":"4","cat_id":"107","class_id":"26","title":"技术作图精讲班课件04.pdf","file_type":"pdf","file_size":"29.879296M","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:14:05","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"},{"id":"2371","type_id":"4","cat_id":"107","class_id":"26","title":"技术作图精讲班课件03.pdf","file_type":"pdf","file_size":"373.259717","oss_path":"建筑师类/一级建筑师/","year_of":null,"is_free":"0","price":"0.00","give_by_class":"1","download":"0","created_at":"2020-04-28 09:15:21","expired_at":null,"chapter_id":"0","sort":"0","total":"0","is_rec":"0"}]}
     */
    private String customerServiceUrl;
    private RegimentDataBean regimentData;
    private GroupDataBean groupData;
    private MaterialDataBean MaterialData;
    private List<BannerDataBean> bannerData;
    private List<HuoDongDataBean> huoDongData;

    public String getCustomerServiceUrl() {
        return customerServiceUrl;
    }

    public void setCustomerServiceUrl(String customerServiceUrl) {
        this.customerServiceUrl = customerServiceUrl;
    }

    public RegimentDataBean getRegimentData() {
        return regimentData;
    }

    public void setRegimentData(RegimentDataBean regimentData) {
        this.regimentData = regimentData;
    }

    public GroupDataBean getGroupData() {
        return groupData;
    }

    public void setGroupData(GroupDataBean groupData) {
        this.groupData = groupData;
    }

    public MaterialDataBean getMaterialData() {
        return MaterialData;
    }

    public void setMaterialData(MaterialDataBean materialData) {
        MaterialData = materialData;
    }

    public List<BannerDataBean> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerDataBean> bannerData) {
        this.bannerData = bannerData;
    }

    public List<HuoDongDataBean> getHuoDongData() {
        return huoDongData;
    }

    public void setHuoDongData(List<HuoDongDataBean> huoDongData) {
        this.huoDongData = huoDongData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerServiceUrl);
        dest.writeParcelable(this.regimentData, flags);
        dest.writeParcelable(this.groupData, flags);
        dest.writeParcelable(this.MaterialData, flags);
        dest.writeTypedList(this.bannerData);
        dest.writeTypedList(this.huoDongData);
    }

    public HomeDataBean() {
    }

    protected HomeDataBean(Parcel in) {
        this.customerServiceUrl = in.readString();
        this.regimentData = in.readParcelable(RegimentDataBean.class.getClassLoader());
        this.groupData = in.readParcelable(GroupDataBean.class.getClassLoader());
        this.MaterialData = in.readParcelable(MaterialDataBean.class.getClassLoader());
        this.bannerData = in.createTypedArrayList(BannerDataBean.CREATOR);
        this.huoDongData = in.createTypedArrayList(HuoDongDataBean.CREATOR);
    }

    public static final Creator<HomeDataBean> CREATOR = new Creator<HomeDataBean>() {
        @Override
        public HomeDataBean createFromParcel(Parcel source) {
            return new HomeDataBean(source);
        }

        @Override
        public HomeDataBean[] newArray(int size) {
            return new HomeDataBean[size];
        }
    };
}
