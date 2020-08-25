package com.jianpei.jpeducation.adapter.mclass;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
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


    public PlayListAdapter(MyItemOnClickListener myItemOnClickListener) {
        super();
        addNodeProvider(new PlayListChapterProvider(myItemOnClickListener));
        addNodeProvider(new PlayListJIeProvider(myItemOnClickListener));

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode baseNode = list.get(i);

        if (baseNode instanceof DirectoryBean) {
            return 0;
        } else if (baseNode instanceof ViodBean) {
            return 1;
        }
        return -1;
    }
}
