package com.jianpei.jpeducation.bean.gold;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WithdrawalBean {


    /**
     * id : 3
     * user_id : 4139
     * user_name : gfgdgds
     * bank_card : 6222320000461096
     * money : 78.00
     * state : 1
     * create_time_str : 2020-07-18 15:57:09
     * play_time_str :
     */

    private String id;
    private String user_id;
    private String user_name;
    private String bank_card;
    private String money;
    private String state;
    private String create_time_str;
    private String play_time_str;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getPlay_time_str() {
        return play_time_str;
    }

    public void setPlay_time_str(String play_time_str) {
        this.play_time_str = play_time_str;
    }
}
