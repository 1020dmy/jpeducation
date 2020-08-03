package com.jianpei.jpeducation.bean.elective;

import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupHomeBean {
    private List<BannerDataBean> bannerData;

//    private List<GroupDataBean> groupData;

    private List<GroupInfoBean> groupData;

    public List<BannerDataBean> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerDataBean> bannerData) {
        this.bannerData = bannerData;
    }

    public List<GroupInfoBean> getGroupData() {
        return groupData;
    }

    public void setGroupData(List<GroupInfoBean> groupData) {
        this.groupData = groupData;
    }
}