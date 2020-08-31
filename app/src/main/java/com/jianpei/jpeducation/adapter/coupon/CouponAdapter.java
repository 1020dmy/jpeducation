package com.jianpei.jpeducation.adapter.coupon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private MyCouponReceiveListener myCouponReceiveListener;

    private Context context;


//    private int type;//展示的位置1：课程详情2：我的优惠券


    public void setMyCouponReceiveListener(MyCouponReceiveListener myCouponReceiveListener) {
        this.myCouponReceiveListener = myCouponReceiveListener;
    }

//    public CouponAdapter(List<CouponDataBean.CouponData> couponDatas) {
//        this.couponDatas = couponDatas;
//    }

    public CouponAdapter(List<CouponDataBean.CouponData> couponDatas, Context context) {
        this.couponDatas = couponDatas;
        this.context = context;
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
            mjHolder.tvPrice.setText(couponData.getDescribe());
            mjHolder.tvTitle.setText(couponData.getTitle());
            mjHolder.tvTime.setText("有效期至：" + couponData.getEnd_time_str());

            if ("1".equals(couponData.getType())) {//没使用
                mjHolder.tvStatus.setText("可使用");
                mjHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_used);
            } else if ("2".equals(couponData.getType())) {//已经使用
                mjHolder.tvStatus.setText("已使用");
                mjHolder.tvStatus.setBackgroundResource(R.drawable.shape_home_zhekou_guoqi);
                mjHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_unused);
            } else {//过期
                mjHolder.tvStatus.setText("已过期");
                mjHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_unused);
                mjHolder.tvStatus.setBackgroundResource(R.drawable.shape_home_zhekou_guoqi);

            }

        } else {
            ZKHolder zkHolder = (ZKHolder) holder;

            if (!TextUtils.isEmpty(couponData.getSon_describe())) {
                String aaa = couponData.getDescribe() + "." + couponData.getSon_describe();
                SpannableString spanString = new SpannableString(aaa);
                AbsoluteSizeSpan span = new AbsoluteSizeSpan(20, true);//这里设置要改变的字的大小
                spanString.setSpan(span, couponData.getDescribe().length(), aaa.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                zkHolder.tvNum.setText(spanString);

            } else {
                zkHolder.tvNum.setText(couponData.getDescribe());

            }

            zkHolder.tvTitle.setText(couponData.getTitle());
            zkHolder.tvTime.setText("有效期至：" + couponData.getEnd_time_str());
            if ("1".equals(couponData.getType())) {//没使用
                zkHolder.tvStatus.setText("可使用");
                zkHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_used);
            } else if ("2".equals(couponData.getType())) {//已经使用
                zkHolder.tvStatus.setText("已使用");
                zkHolder.tvStatus.setBackgroundResource(R.drawable.shape_home_zhekou_guoqi);
                zkHolder.tvNum.setTextColor(context.getResources().getColor(R.color.c989898));
                zkHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_unused);
                zkHolder.llStatus.setBackgroundResource(R.drawable.user_coupon_used_bg);
                zkHolder.iv_zhe.setImageResource(R.drawable.icon_zhe_guoqi);
            } else {//过期
                zkHolder.tvStatus.setText("已过期");
                zkHolder.linearLayout.setBackgroundResource(R.drawable.user_coupon_unused);
                zkHolder.tvStatus.setBackgroundResource(R.drawable.shape_home_zhekou_guoqi);
                zkHolder.tvNum.setTextColor(context.getResources().getColor(R.color.c989898));
                zkHolder.llStatus.setBackgroundResource(R.drawable.user_coupon_used_bg);
                zkHolder.iv_zhe.setImageResource(R.drawable.icon_zhe_guoqi);
            }
        }

    }

    @Override
    public int getItemCount() {
        return couponDatas != null ? couponDatas.size() : 0;
    }


    class MJHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvPrice, tvTitle, tvTime, tvStatus;
        private LinearLayout llStatus;
        //        private TextView tvSubmit;
        private LinearLayout linearLayout;

        public MJHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);

            llStatus = itemView.findViewById(R.id.ll_status);
//            tvSubmit = itemView.findViewById(R.id.tv_submit);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(this);
//            if (type == 1) {
//                tvSubmit.setVisibility(View.VISIBLE);
//                llStatus.setVisibility(View.GONE);
//            }

        }

        @Override
        public void onClick(View v) {
            if (myCouponReceiveListener != null) {
                myCouponReceiveListener.onClickCouponReceive(couponDatas.get(getLayoutPosition()).getId(), couponDatas.get(getLayoutPosition()).getTitle());
            }
        }
    }

    class ZKHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvNum, tvTitle, tvTime, tvStatus;
        private LinearLayout llStatus;
        //        private TextView tvSubmit;
        private LinearLayout linearLayout;
//        private TextView tvNumt;
        private ImageView iv_zhe;


        public ZKHolder(@NonNull View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvStatus = itemView.findViewById(R.id.tv_status);
            llStatus = itemView.findViewById(R.id.ll_status);
//            tvSubmit = itemView.findViewById(R.id.tv_submit);
            linearLayout = itemView.findViewById(R.id.linearLayout);

            iv_zhe=itemView.findViewById(R.id.iv_zhe);
            linearLayout.setOnClickListener(this);

//            tvNumt = itemView.findViewById(R.id.tv_numt);

//            if (type == 1) {
//                tvSubmit.setVisibility(View.VISIBLE);
//                llStatus.setVisibility(View.GONE);
//                tvSubmit.setOnClickListener(this);
//
//            }
        }

        @Override
        public void onClick(View v) {
            if (myCouponReceiveListener != null) {
                myCouponReceiveListener.onClickCouponReceive(couponDatas.get(getLayoutPosition()).getId(), couponDatas.get(getLayoutPosition()).getTitle());
            }
        }
    }


    public interface MyCouponReceiveListener {
        void onClickCouponReceive(String couponId, String title);
    }

}
