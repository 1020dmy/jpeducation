package com.jianpei.jpeducation.adapter.coupon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.CouponDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CouponAdapter extends RecyclerView.Adapter {


    private List<CouponDataBean.CouponData> couponDatas;

    public CouponAdapter(List<CouponDataBean.CouponData> couponDatas) {
        this.couponDatas = couponDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if ("1".equals(couponDatas.get(position).getCoupon_type())) {
            return 1;
        } else if ("2".equals(couponDatas.get(position).getCoupon_type())) {
            return 2;

        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_used_mj, parent, false);
            viewHolder = new MJHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_used_zk, parent, false);
            viewHolder = new ZKHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CouponDataBean.CouponData couponData = couponDatas.get(position);
        if (holder instanceof MJHolder) {
            MJHolder mjHolder = (MJHolder) holder;
            int price = Integer.valueOf(couponData.getDescribe()) * 100;
            mjHolder.tvPrice.setText(price + "");
            mjHolder.tvTitle.setText(couponData.getTitle());
            mjHolder.tvTime.setText("有效期至：" + couponData.getEnd_time_str());

            if ("1".equals(couponData.getType())) {//没使用
                mjHolder.tvStatus.setText("可使用");

            } else if ("0".equals(couponData.getType())) {//已经使用
                mjHolder.tvStatus.setText("已使用");
            }

        } else {
            ZKHolder zkHolder = (ZKHolder) holder;
            zkHolder.tvNum.setText(couponData.getDescribe());
//            zkHolder.tvNum.setText("8.5");

            zkHolder.tvTitle.setText(couponData.getTitle());
            zkHolder.tvTime.setText("有效期至：" + couponData.getEnd_time_str());
            if ("1".equals(couponData.getType())) {//没使用
                zkHolder.tvStatus.setText("可使用");
            } else if ("0".equals(couponData.getType())) {//已经使用
                zkHolder.tvStatus.setText("已使用");
            }
        }

    }

    @Override
    public int getItemCount() {
        return couponDatas != null ? couponDatas.size() : 0;
    }


    class MJHolder extends RecyclerView.ViewHolder {
        private TextView tvPrice, tvTitle, tvTime, tvStatus;
        private LinearLayout llStatus;

        public MJHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);

            llStatus = itemView.findViewById(R.id.ll_status);

        }
    }

    class ZKHolder extends RecyclerView.ViewHolder {
        private TextView tvNum, tvTitle, tvTime, tvStatus;
        private LinearLayout llStatus;

        public ZKHolder(@NonNull View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);
            llStatus = itemView.findViewById(R.id.ll_status);
        }
    }
}
