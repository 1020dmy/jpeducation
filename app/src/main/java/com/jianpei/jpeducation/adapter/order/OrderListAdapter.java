package com.jianpei.jpeducation.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.order.OrderDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderListAdapter extends RecyclerView.Adapter {


    private List<OrderDataBean> orderDataBeans;

    private Context context;

    private MyOrderListItemListener myOrderListItemListener;

    public void setMyOrderListItemListener(MyOrderListItemListener myOrderListItemListener) {
        this.myOrderListItemListener = myOrderListItemListener;
    }


    public OrderListAdapter(List<OrderDataBean> orderDataBeans, Context context) {
        this.orderDataBeans = orderDataBeans;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if ("0".equals(orderDataBeans.get(position).getState())) {//未支付
            return 0;

        } else if ("1".equals(orderDataBeans.get(position).getState())) {//已经支付
            return 1;

        } else {//支付失败
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist_complete, parent, false);
            return new CompleteMyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist_watipay, parent, false);
            return new WaitPayMyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDataBean orderDataBean = orderDataBeans.get(position);

        if (holder instanceof WaitPayMyHolder) {
            WaitPayMyHolder waitPayMyHolder = (WaitPayMyHolder) holder;
            waitPayMyHolder.tvOrderNum.setText(orderDataBean.getNumber());
            if ("0".equals(orderDataBean.getState())) {
                waitPayMyHolder.tvStatus.setText("待付款");
            } else if ("1".equals(orderDataBean.getState())) {
                waitPayMyHolder.tvStatus.setText("已付款");
            } else if ("2".equals(orderDataBean.getState())) {
                waitPayMyHolder.tvStatus.setText("付款失败");
                waitPayMyHolder.tv_cancel.setVisibility(View.GONE);
            }
            waitPayMyHolder.tv_price.setText("￥" + orderDataBean.getMoney());


            if (orderDataBean.getGroup_list() == null || orderDataBean.getGroup_list().size() == 0) {
                waitPayMyHolder.tv_nums.setText("共1件");
                waitPayMyHolder.recyclerView.setAdapter(new OrderClassAdapter(context, orderDataBean.getGroup_info(), orderDataBean.getClass_name_str(), orderDataBean.getMoney()));

            } else {
                waitPayMyHolder.tv_nums.setText("共" + orderDataBean.getGroup_list().size() + "件");
                waitPayMyHolder.recyclerView.setAdapter(new OrderClassAdapter(orderDataBean.getGroup_list(), context));
            }

        } else {
            CompleteMyHolder completeMyHolder = (CompleteMyHolder) holder;

            completeMyHolder.tvOrderNum.setText(orderDataBean.getNumber());
            if ("0".equals(orderDataBean.getState())) {
                completeMyHolder.tvStatus.setText("待付款");
            } else if ("1".equals(orderDataBean.getState())) {
                completeMyHolder.tvStatus.setText("已付款");
                if ("2".equals(orderDataBean.getGoods_type()) && "1".equals(orderDataBean.getIs_reg_succ())) {//是团购切还未拼团完成
                    completeMyHolder.tv_student.setVisibility(View.GONE);
                    completeMyHolder.tv_comment.setVisibility(View.GONE);
                    completeMyHolder.tv_share.setVisibility(View.VISIBLE);
                }
            } else {
                completeMyHolder.tvStatus.setText("付款失败");

            }

            if (orderDataBean.getGroup_list() == null || orderDataBean.getGroup_list().size() == 0) {
                completeMyHolder.recyclerView.setAdapter(new OrderClassAdapter(context, orderDataBean.getGroup_info(), orderDataBean.getClass_name_str(), orderDataBean.getMoney()));
            } else {
                completeMyHolder.recyclerView.setAdapter(new OrderClassAdapter(orderDataBean.getGroup_list(), context));
            }

        }

    }

    @Override
    public int getItemCount() {
        return orderDataBeans != null ? orderDataBeans.size() : 0;
    }


    class WaitPayMyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvOrderNum, tvStatus;
        private RecyclerView recyclerView;
        private TextView tv_nums, tv_price;
        private TextView tv_cancel, tv_pay;

        public WaitPayMyHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            tv_cancel.setOnClickListener(this);
            tv_pay.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myOrderListItemListener != null) {
                myOrderListItemListener.onClick(v, getLayoutPosition());
            }

        }
    }

    class CompleteMyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvOrderNum, tvStatus;
        private RecyclerView recyclerView;
        private TextView tv_comment, tv_student, tv_share;
        private LinearLayout linearLayout;


        public CompleteMyHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tv_student = itemView.findViewById(R.id.tv_student);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_share = itemView.findViewById(R.id.tv_share);

            linearLayout = itemView.findViewById(R.id.linearLayout);


            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            tv_comment.setOnClickListener(this);
            tv_student.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            tv_share.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myOrderListItemListener != null) {
                myOrderListItemListener.onClick(v, getLayoutPosition());
            }
        }
    }
}
