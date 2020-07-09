package com.jianpei.jpeducation.adapter.mclass;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PlayListAdapter extends BaseNodeAdapter {

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode baseNode=list.get(i);

        if(baseNode instanceof MClassInfoBean.DirectorysBean){
            return 0;
        }else if(baseNode instanceof MClassInfoBean.DirectorysBean.ViodsBean){
            return 1;
        }
        return -1;
    }
}