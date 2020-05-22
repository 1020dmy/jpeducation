package com.jianpei.jpeducation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LauncherBean {

    /**
     * guideList : ["https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide1.jpg","https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide2.jpg","https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/guide3.jpg"]
     * startingInfo : https://jianpei-images.oss-cn-beijing.aliyuncs.com/images/public/launcher.jpg
     */

    private String startingInfo;
    private ArrayList<String> guideList;

    public String getStartingInfo() {
        return startingInfo;
    }

    public void setStartingInfo(String startingInfo) {
        this.startingInfo = startingInfo;
    }

    public ArrayList<String> getGuideList() {
        return guideList;
    }

    public void setGuideList(ArrayList<String> guideList) {
        this.guideList = guideList;
    }
}
