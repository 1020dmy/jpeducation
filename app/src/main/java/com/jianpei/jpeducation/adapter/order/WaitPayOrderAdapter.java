package com.jianpei.jpeducation.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by sjl on 2020/7/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WaitPayOrderAdapter extends RecyclerView.Adapter<WaitPayOrderAdapter.MyHolder> {

    private List<OrderDataBean> mOrderDataBeans;
    private Context mContext;

    private MyOrderListItemListener myOrderListItemListener;

    public void setMyOrderListItemListener(MyOrderListItemListener myOrderListItemListener) {
        this.myOrderListItemListener = myOrderListItemListener;
    }

    public WaitPayOrderAdapter(List<OrderDataBean> mOrderDataBeans, Context mContext) {
        this.mOrderDataBeans = mOrderDataBeans;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist_watipay, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        OrderDataBean orderDataBean = mOrderDataBeans.get(position);
        holder.tvOrderNum.setText(orderDataBean.getNumber());
        if ("0".equals(orderDataBean.getState())) {
            holder.tvStatus.setText("待付款");
        } else if ("1".equals(orderDataBean.getState())) {
            holder.tvStatus.setText("已付款");
        } else {
            holder.tvStatus.setText("付款失败");
        }
        holder.tv_price.setText("￥" + orderDataBean.getMoney());


        if (orderDataBean.getGroup_list() == null || orderDataBean.getGroup_list().size() == 0) {
            holder.tv_nums.setText("共1件");
            holder.recyclerView.setAdapter(new OrderClassAdapter(mContext, orderDataBean.getGroup_info(), orderDataBean.getClass_name_str(), orderDataBean.getMoney()));

        } else {
            holder.tv_nums.setText("共" + orderDataBean.getGroup_list().size() + "件");
            holder.recyclerView.setAdapter(new OrderClassAdapter(orderDataBean.getGroup_list(), mContext));
        }
    }

    @Override
    public int getItemCount() {
        return mOrderDataBeans != null ? mOrderDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvOrderNum, tvStatus;
        private RecyclerView recyclerView;
        private TextView tv_nums, tv_price;
        private TextView tv_cancel, tv_pay;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvStatus = itemView.findViewById(R.id.tv_status);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

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

}
