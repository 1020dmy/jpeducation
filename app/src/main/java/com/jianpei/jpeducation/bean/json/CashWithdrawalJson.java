package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CashWithdrawalJson {
    private String bank_card;
    private String bank_user_name;
    private String virtual_currency;

    public CashWithdrawalJson(String bank_card, String bank_user_name, String virtual_currency) {
        this.bank_card = bank_card;
        this.bank_user_name = bank_user_name;
        this.virtual_currency = virtual_currency;
    }


    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_user_name() {
        return bank_user_name;
    }

    public void setBank_user_name(String bank_user_name) {
        this.bank_user_name = bank_user_name;
    }

    public String getVirtual_currency() {
        return virtual_currency;
    }

    public void setVirtual_currency(String virtual_currency) {
        this.virtual_currency = virtual_currency;
    }
}
