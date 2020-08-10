package com.jianpei.jpeducation.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.shop.GroupBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MIneOrderInfoBean implements Parcelable {


    /**
     * id : 6761
     * number : 20200702141645588984
     * type : 1
     * gid : s96,s98
     * num : 1
     * mid : 4139
     * money : 10560.00
     * integral :
     * count_integral : 10560.00
     * discount_integral : 0.00
     * pro_id :
     * city_id :
     * area_id :
     * address :
     * name :
     * tel :
     * pay : 0
     * pay_type : 0
     * state : 1
     * addtime : 1593670605
     * kuaidi :
     * kuaidi_number :
     * optime :
     * zhekou :
     * pid : 0
     * is_down : 0
     * zk_id :
     * tagent_id : 0
     * updtime : 1593670621
     * add_type : 1
     * tagent_zhekou : 0.00
     * suit_id :
     * group_id : 38
     * goods_type : 1
     * timeout : 1593757005
     * huod_id : 0
     * regiment_id : 0
     * gather_id : 0
     * is_material : 0
     * transaction_id : 4200000580202007028498587563
     * pay_time : 1593670620
     * trade_type : APP
     * car_ids : 29
     * is_reg_succ : 1
     * add_time_str : 2020-07-02 14:16:45
     * class_name_str : 市政工程全科+建筑工程全科
     * material_des : 购买课程送相关课程资料
     */

    private String id;
    private String number;
    private String type;
    private String gid;
    private String num;
    private String mid;
    private String money;
    private String count_integral;
    private String discount_integral;

    private String pay;
    private String pay_type;
    private String state;
    private String addtime;
//    private String optime;
//    private String zhekou;
    private String pid;
    private String is_down;
//    private String zk_id;
    private String tagent_id;
    private String updtime;
    private String add_type;
    private String tagent_zhekou;
//    private String suit_id;
    private String group_id;
    private String goods_type;
    private String timeout;
    private String huod_id;
    private String regiment_id;
    private String gather_id;
    private String is_material;
    private String transaction_id;
    private String pay_time;
    private String trade_type;
    private String car_ids;
    private String is_reg_succ;
    private String add_time_str;
    private String class_name_str;
    private String material_des;

    private GroupInfoBean group_info;
    private List<GroupBean> group_list;

    private RegimentBean regiment_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

//    public String getIntegral() {
//        return integral;
//    }
//
//    public void setIntegral(String integral) {
//        this.integral = integral;
//    }

    public String getCount_integral() {
        return count_integral;
    }

    public void setCount_integral(String count_integral) {
        this.count_integral = count_integral;
    }

    public String getDiscount_integral() {
        return discount_integral;
    }

    public void setDiscount_integral(String discount_integral) {
        this.discount_integral = discount_integral;
    }

//    public String getPro_id() {
//        return pro_id;
//    }
//
//    public void setPro_id(String pro_id) {
//        this.pro_id = pro_id;
//    }

//    public String getCity_id() {
//        return city_id;
//    }
//
//    public void setCity_id(String city_id) {
//        this.city_id = city_id;
//    }
//
//    public String getArea_id() {
//        return area_id;
//    }
//
//    public void setArea_id(String area_id) {
//        this.area_id = area_id;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTel() {
//        return tel;
//    }
//
//    public void setTel(String tel) {
//        this.tel = tel;
//    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

//    public String getKuaidi() {
//        return kuaidi;
//    }
//
//    public void setKuaidi(String kuaidi) {
//        this.kuaidi = kuaidi;
//    }
//
//    public String getKuaidi_number() {
//        return kuaidi_number;
//    }
//
//    public void setKuaidi_number(String kuaidi_number) {
//        this.kuaidi_number = kuaidi_number;
//    }
//
//    public String getOptime() {
//        return optime;
//    }
//
//    public void setOptime(String optime) {
//        this.optime = optime;
//    }
//
//    public String getZhekou() {
//        return zhekou;
//    }
//
//    public void setZhekou(String zhekou) {
//        this.zhekou = zhekou;
//    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIs_down() {
        return is_down;
    }

    public void setIs_down(String is_down) {
        this.is_down = is_down;
    }

//    public String getZk_id() {
//        return zk_id;
//    }
//
//    public void setZk_id(String zk_id) {
//        this.zk_id = zk_id;
//    }

    public String getTagent_id() {
        return tagent_id;
    }

    public void setTagent_id(String tagent_id) {
        this.tagent_id = tagent_id;
    }

    public String getUpdtime() {
        return updtime;
    }

    public void setUpdtime(String updtime) {
        this.updtime = updtime;
    }

    public String getAdd_type() {
        return add_type;
    }

    public void setAdd_type(String add_type) {
        this.add_type = add_type;
    }

    public String getTagent_zhekou() {
        return tagent_zhekou;
    }

    public void setTagent_zhekou(String tagent_zhekou) {
        this.tagent_zhekou = tagent_zhekou;
    }

//    public String getSuit_id() {
//        return suit_id;
//    }
//
//    public void setSuit_id(String suit_id) {
//        this.suit_id = suit_id;
//    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getHuod_id() {
        return huod_id;
    }

    public void setHuod_id(String huod_id) {
        this.huod_id = huod_id;
    }

    public String getRegiment_id() {
        return regiment_id;
    }

    public void setRegiment_id(String regiment_id) {
        this.regiment_id = regiment_id;
    }

    public String getGather_id() {
        return gather_id;
    }

    public void setGather_id(String gather_id) {
        this.gather_id = gather_id;
    }

    public String getIs_material() {
        return is_material;
    }

    public void setIs_material(String is_material) {
        this.is_material = is_material;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getCar_ids() {
        return car_ids;
    }

    public void setCar_ids(String car_ids) {
        this.car_ids = car_ids;
    }

    public String getIs_reg_succ() {
        return is_reg_succ;
    }

    public void setIs_reg_succ(String is_reg_succ) {
        this.is_reg_succ = is_reg_succ;
    }

    public String getAdd_time_str() {
        return add_time_str;
    }

    public void setAdd_time_str(String add_time_str) {
        this.add_time_str = add_time_str;
    }

    public String getClass_name_str() {
        return class_name_str;
    }

    public void setClass_name_str(String class_name_str) {
        this.class_name_str = class_name_str;
    }

    public String getMaterial_des() {
        return material_des;
    }

    public void setMaterial_des(String material_des) {
        this.material_des = material_des;
    }

    public GroupInfoBean getGroup_info() {
        return group_info;
    }

    public void setGroup_info(GroupInfoBean group_info) {
        this.group_info = group_info;
    }

    public List<GroupBean> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(List<GroupBean> group_list) {
        this.group_list = group_list;
    }

//    public RegimentBean getRegiment_info() {
//        return regiment_info;
//    }
//
//    public void setRegiment_info(RegimentBean regiment_info) {
//        this.regiment_info = regiment_info;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.number);
        dest.writeString(this.type);
        dest.writeString(this.gid);
        dest.writeString(this.num);
        dest.writeString(this.mid);
        dest.writeString(this.money);
//        dest.writeString(this.integral);
        dest.writeString(this.count_integral);
        dest.writeString(this.discount_integral);
//        dest.writeString(this.pro_id);
//        dest.writeString(this.city_id);
//        dest.writeString(this.area_id);
//        dest.writeString(this.address);
//        dest.writeString(this.name);
//        dest.writeString(this.tel);
        dest.writeString(this.pay);
        dest.writeString(this.pay_type);
        dest.writeString(this.state);
        dest.writeString(this.addtime);
//        dest.writeString(this.kuaidi);
//        dest.writeString(this.kuaidi_number);
//        dest.writeString(this.optime);
//        dest.writeString(this.zhekou);
        dest.writeString(this.pid);
        dest.writeString(this.is_down);
//        dest.writeString(this.zk_id);
        dest.writeString(this.tagent_id);
        dest.writeString(this.updtime);
        dest.writeString(this.add_type);
        dest.writeString(this.tagent_zhekou);
//        dest.writeString(this.suit_id);
        dest.writeString(this.group_id);
        dest.writeString(this.goods_type);
        dest.writeString(this.timeout);
        dest.writeString(this.huod_id);
        dest.writeString(this.regiment_id);
        dest.writeString(this.gather_id);
        dest.writeString(this.is_material);
        dest.writeString(this.transaction_id);
        dest.writeString(this.pay_time);
        dest.writeString(this.trade_type);
        dest.writeString(this.car_ids);
        dest.writeString(this.is_reg_succ);
        dest.writeString(this.add_time_str);
        dest.writeString(this.class_name_str);
        dest.writeString(this.material_des);
        dest.writeParcelable(this.group_info, flags);
        dest.writeTypedList(this.group_list);
        dest.writeParcelable(this.regiment_info, flags);
    }

    public MIneOrderInfoBean() {
    }

    protected MIneOrderInfoBean(Parcel in) {
        this.id = in.readString();
        this.number = in.readString();
        this.type = in.readString();
        this.gid = in.readString();
        this.num = in.readString();
        this.mid = in.readString();
        this.money = in.readString();
//        this.integral = in.readString();
        this.count_integral = in.readString();
        this.discount_integral = in.readString();
//        this.pro_id = in.readString();
//        this.city_id = in.readString();
//        this.area_id = in.readString();
//        this.address = in.readString();
//        this.name = in.readString();
//        this.tel = in.readString();
        this.pay = in.readString();
        this.pay_type = in.readString();
        this.state = in.readString();
        this.addtime = in.readString();
//        this.kuaidi = in.readString();
//        this.kuaidi_number = in.readString();
//        this.optime = in.readString();
//        this.zhekou = in.readString();
        this.pid = in.readString();
        this.is_down = in.readString();
//        this.zk_id = in.readString();
        this.tagent_id = in.readString();
        this.updtime = in.readString();
        this.add_type = in.readString();
        this.tagent_zhekou = in.readString();
//        this.suit_id = in.readString();
        this.group_id = in.readString();
        this.goods_type = in.readString();
        this.timeout = in.readString();
        this.huod_id = in.readString();
        this.regiment_id = in.readString();
        this.gather_id = in.readString();
        this.is_material = in.readString();
        this.transaction_id = in.readString();
        this.pay_time = in.readString();
        this.trade_type = in.readString();
        this.car_ids = in.readString();
        this.is_reg_succ = in.readString();
        this.add_time_str = in.readString();
        this.class_name_str = in.readString();
        this.material_des = in.readString();
        this.group_info = in.readParcelable(GroupInfoBean.class.getClassLoader());
        this.group_list = in.createTypedArrayList(GroupBean.CREATOR);
        this.regiment_info = in.readParcelable(RegimentBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MIneOrderInfoBean> CREATOR = new Parcelable.Creator<MIneOrderInfoBean>() {
        @Override
        public MIneOrderInfoBean createFromParcel(Parcel source) {
            return new MIneOrderInfoBean(source);
        }

        @Override
        public MIneOrderInfoBean[] newArray(int size) {
            return new MIneOrderInfoBean[size];
        }
    };
}
