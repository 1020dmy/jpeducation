package com.jianpei.jpeducation.adapter.mclass;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PlayListJIeProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_playerlist_t;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        MClassInfoBean.DirectorysBean.ViodsBean viodsBean = (MClassInfoBean.DirectorysBean.ViodsBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, viodsBean.getTitle());

    }
}
