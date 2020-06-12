package com.jianpei.jpeducation.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

//    private int mItemOffset;
//
//    public ItemOffsetDecoration(int itemOffset) {
//        mItemOffset = itemOffset;
//    }
//
//    public ItemOffsetDecoration(Context context, int itemOffsetId) {
//        this(context.getResources().getDimensionPixelSize(itemOffsetId));
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                               RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
//    }

    private int space;

    public ItemOffsetDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }

}
