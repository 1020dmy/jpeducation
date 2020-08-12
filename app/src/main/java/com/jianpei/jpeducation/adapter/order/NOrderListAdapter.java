package com.jianpei.jpeducation.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.order.OrderDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class NOrderListAdapter extends RecyclerView.Adapter {

    private List<OrderDataBean> orderDataBeans;
    private Context context;

    public NOrderListAdapter(List<OrderDataBean> orderDataBeans, Context context) {
        this.orderDataBeans = orderDataBeans;
        this.context = context;
    }

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        //普通订单，购物车订单，团购订单
        if ("1".equals(orderDataBeans.get(position).getGoods_type())) {//普通订单（普通，购物车）
            if (orderDataBeans.get(position).getGroup_list() != null && orderDataBeans.get(position).getGroup_list().size() > 0) {
                return 1;//购物车订单
            } else {
                return 0;//普通订单
            }

        } else if ("2".equals(orderDataBeans.get(position).getGoods_type())) {
            return 2;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderliset_normal, parent, false);
            holder = new NormalHolder(view);

        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderliset_shoppingcat, parent, false);
            holder = new ShoppingCatHolder(view);

        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderliset_groupbuy, parent, false);
            holder = new GroupBuyHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDataBean orderDataBean = orderDataBeans.get(position);
        if (holder instanceof NormalHolder) {//普通订单
            NormalHolder normalHolder = (NormalHolder) holder;
            normalHolder.tvOrderNum.setText(orderDataBean.getNumber());
            if ("0".equals(orderDataBean.getState())) {
                normalHolder.tvStatus.setText("待付款");
                //
                normalHolder.tv_comment.setVisibility(View.GONE);
                normalHolder.tv_student.setVisibility(View.GONE);
                //
                normalHolder.tv_cancel.setVisibility(View.VISIBLE);
                normalHolder.tv_pay.setVisibility(View.VISIBLE);
            } else if ("1".equals(orderDataBean.getState())) {
                normalHolder.tvStatus.setText("已付款");
                //
                normalHolder.tv_cancel.setVisibility(View.GONE);
                normalHolder.tv_pay.setVisibility(View.GONE);
                //
                normalHolder.tv_comment.setVisibility(View.VISIBLE);
                normalHolder.tv_student.setVisibility(View.VISIBLE);
            } else if ("2".equals(orderDataBean.getState())) {
                normalHolder.tvStatus.setText("取消订单");
                //
                normalHolder.tv_cancel.setVisibility(View.GONE);
                normalHolder.tv_comment.setVisibility(View.GONE);
                normalHolder.tv_student.setVisibility(View.GONE);
                //
                normalHolder.tv_pay.setVisibility(View.GONE);
            }
            //
            if (orderDataBean.getGroup_info() != null) {
                Glide.with(context).load(orderDataBean.getGroup_info().getImg()).into(normalHolder.ivHead);
                normalHolder.tv_title.setText(orderDataBean.getGroup_info().getTitle());
                normalHolder.tv_classinfo.setText(orderDataBean.getClass_name_str());
                normalHolder.tv_price.setText("￥" + orderDataBean.getMoney());
            }
            //
            normalHolder.tv_total_price.setText("￥" + orderDataBean.getMoney());
            normalHolder.tv_nums.setText("共1件");

        } else if (holder instanceof ShoppingCatHolder) {//购物车订单
            ShoppingCatHolder shoppingCatHolder = (ShoppingCatHolder) holder;
            shoppingCatHolder.tvOrderNum.setText(orderDataBean.getNumber());
            if ("0".equals(orderDataBean.getState())) {
                shoppingCatHolder.tvStatus.setText("待付款");
                //
                shoppingCatHolder.tv_comment.setVisibility(View.GONE);
                shoppingCatHolder.tv_student.setVisibility(View.GONE);
                //
                shoppingCatHolder.tv_cancel.setVisibility(View.VISIBLE);
                shoppingCatHolder.tv_pay.setVisibility(View.VISIBLE);
            } else if ("1".equals(orderDataBean.getState())) {
                shoppingCatHolder.tvStatus.setText("已付款");
                //
                shoppingCatHolder.tv_cancel.setVisibility(View.GONE);
                shoppingCatHolder.tv_pay.setVisibility(View.GONE);
                //
                shoppingCatHolder.tv_comment.setVisibility(View.VISIBLE);
                shoppingCatHolder.tv_student.setVisibility(View.VISIBLE);
            } else if ("2".equals(orderDataBean.getState())) {
                shoppingCatHolder.tvStatus.setText("取消订单");
                //
                shoppingCatHolder.tv_cancel.setVisibility(View.GONE);
                shoppingCatHolder.tv_comment.setVisibility(View.GONE);
                shoppingCatHolder.tv_student.setVisibility(View.GONE);
                //
                shoppingCatHolder.tv_pay.setVisibility(View.GONE);
            }
            shoppingCatHolder.tv_total_price.setText("￥" + orderDataBean.getMoney());

            shoppingCatHolder.tv_nums.setText("共" + orderDataBean.getGroup_list().size() + "件");
            shoppingCatHolder.recyclerView.setAdapter(new OrderClassAdapter(orderDataBean.getGroup_list(), context));

        } else if (holder instanceof GroupBuyHolder) {//团购订单
            GroupBuyHolder groupBuyHolder = (GroupBuyHolder) holder;
            groupBuyHolder.tvOrderNum.setText(orderDataBean.getNumber());

            if ("0".equals(orderDataBean.getState())) {
                groupBuyHolder.tvStatus.setText("待付款");
                //
                groupBuyHolder.tv_comment.setVisibility(View.GONE);
                groupBuyHolder.tv_student.setVisibility(View.GONE);
                //
                groupBuyHolder.tv_cancel.setVisibility(View.VISIBLE);
                groupBuyHolder.tv_pay.setVisibility(View.VISIBLE);
            } else if ("1".equals(orderDataBean.getState())) {
                groupBuyHolder.tvStatus.setText("已付款");

                if ("1".equals(orderDataBean.getIs_reg_succ())) {//是团购切还未拼团完成
                    groupBuyHolder.tv_share.setVisibility(View.VISIBLE);
                    groupBuyHolder.tv_cancel.setVisibility(View.GONE);
                    groupBuyHolder.tv_pay.setVisibility(View.GONE);
                    groupBuyHolder.tv_comment.setVisibility(View.GONE);
                    groupBuyHolder.tv_student.setVisibility(View.GONE);
                } else {
                    groupBuyHolder.tv_cancel.setVisibility(View.GONE);
                    groupBuyHolder.tv_pay.setVisibility(View.GONE);
                    //
                    groupBuyHolder.tv_comment.setVisibility(View.VISIBLE);
                    groupBuyHolder.tv_student.setVisibility(View.VISIBLE);
                }
                //

            } else if ("2".equals(orderDataBean.getState())) {
                groupBuyHolder.tvStatus.setText("取消订单");
                //
                groupBuyHolder.tv_cancel.setVisibility(View.GONE);
                groupBuyHolder.tv_comment.setVisibility(View.GONE);
                groupBuyHolder.tv_student.setVisibility(View.GONE);
                //
                groupBuyHolder.tv_pay.setVisibility(View.GONE);
            }
            //
            if (orderDataBean.getGroup_info() != null) {
                Glide.with(context).load(orderDataBean.getGroup_info().getImg()).into(groupBuyHolder.ivHead);
                groupBuyHolder.tv_title.setText(orderDataBean.getGroup_info().getTitle());
                groupBuyHolder.tv_classinfo.setText(orderDataBean.getClass_name_str());
                groupBuyHolder.tv_price.setText("￥" + orderDataBean.getMoney());
            }
            //
            groupBuyHolder.tv_total_price.setText("￥" + orderDataBean.getMoney());
            groupBuyHolder.tv_nums.setText("共1件");


        }
    }

    @Override
    public int getItemCount() {
        return orderDataBeans != null ? orderDataBeans.size() : 0;
    }

    /**
     * 普通订单
     */
    class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvOrderNum, tvStatus;
        private ImageView ivHead;
        private TextView tv_title, tv_classinfo, tv_price;
        private TextView tv_nums, tv_total_price;
        private TextView tv_cancel, tv_pay, tv_comment, tv_student;
        //
        private LinearLayout linearLayout;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivHead = itemView.findViewById(R.id.iv_head);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_classinfo = itemView.findViewById(R.id.tv_classinfo);
            tv_price = itemView.findViewById(R.id.tv_price);

            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            //按钮
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_student = itemView.findViewById(R.id.tv_student);
            //
            linearLayout = itemView.findViewById(R.id.linearLayout);

            tv_cancel.setOnClickListener(this);
            tv_pay.setOnClickListener(this);
            tv_comment.setOnClickListener(this);
            tv_student.setOnClickListener(this);
            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }

        }
    }

    /**
     * 购物车
     */
    class ShoppingCatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvOrderNum, tvStatus;
        private RecyclerView recyclerView;
        private TextView tv_nums, tv_total_price;
        private TextView tv_cancel, tv_pay, tv_comment, tv_student;
        private LinearLayout linearLayout;

        public ShoppingCatHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            recyclerView = itemView.findViewById(R.id.recyclerView);

            tv_nums = itemView.findViewById(R.id.tv_nums);
            //按钮
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_student = itemView.findViewById(R.id.tv_student);
            //
            linearLayout = itemView.findViewById(R.id.linearLayout);


            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return linearLayout.onTouchEvent(event);
                }
            });

            tv_cancel.setOnClickListener(this);
            tv_pay.setOnClickListener(this);
            tv_comment.setOnClickListener(this);
            tv_student.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }

    /**
     * 团购
     */
    class GroupBuyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvOrderNum, tvStatus;
        private ImageView ivHead;
        private TextView tv_title, tv_classinfo, tv_price;
        private TextView tv_nums, tv_total_price;
        private TextView tv_cancel, tv_pay, tv_comment, tv_student, tv_share;

        private LinearLayout linearLayout;


        public GroupBuyHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivHead = itemView.findViewById(R.id.iv_head);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_classinfo = itemView.findViewById(R.id.tv_classinfo);
            tv_price = itemView.findViewById(R.id.tv_price);

            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            //按钮
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_student = itemView.findViewById(R.id.tv_student);
            tv_share = itemView.findViewById(R.id.tv_share);
            //
            linearLayout = itemView.findViewById(R.id.linearLayout);

            //
            tv_cancel.setOnClickListener(this);
            tv_pay.setOnClickListener(this);
            tv_comment.setOnClickListener(this);
            tv_student.setOnClickListener(this);
            tv_share.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }
}
