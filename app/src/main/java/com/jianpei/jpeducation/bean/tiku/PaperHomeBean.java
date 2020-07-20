package com.jianpei.jpeducation.bean.tiku;

import com.jianpei.jpeducation.bean.homedata.BannerDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperHomeBean {

    private List<BannerDataBean> bannerData;

    private List<RecommendClassBean>  groupDaata;


    public List<BannerDataBean> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerDataBean> bannerData) {
        this.bannerData = bannerData;
    }

    public List<RecommendClassBean> getGroupDaata() {
        return groupDaata;
    }

    public void setGroupDaata(List<RecommendClassBean> groupDaata) {
        this.groupDaata = groupDaata;
    }
}
