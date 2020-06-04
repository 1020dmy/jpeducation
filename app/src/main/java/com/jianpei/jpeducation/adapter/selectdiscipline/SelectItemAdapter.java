package com.jianpei.jpeducation.adapter.selectdiscipline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.utils.listener.MyItemOnClickListener;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.MyHolder> {

    private MyItemOnClickListener myItemOnClickListener;

    private ArrayList<DisciplinesBean.SublevelBeanX> sublevelBeanXES;

    private String selectId;
    private Context context;

    public SelectItemAdapter(ArrayList<DisciplinesBean.SublevelBeanX> sublevelBeanXES, String selectId,Context context) {
        this.sublevelBeanXES = sublevelBeanXES;
        this.selectId = selectId;
        this.context=context;
    }

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectadapter_second, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        if (selectId.equals(sublevelBeanXES.get(position).getId())) {
            holder.linearLayout.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
            holder.textView.setTextColor(context.getResources().getColor(R.color.cE73E2F));
        }

        holder.textView.setText(sublevelBeanXES.get(position).getCatname());

    }

    @Override
    public int getItemCount() {
        return sublevelBeanXES == null ? 0 : sublevelBeanXES.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private LinearLayout linearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_zyname);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onClick(getLayoutPosition(), sublevelBeanXES.get(getLayoutPosition()).getId(),sublevelBeanXES.get(getLayoutPosition()).getCatname());
            }
        }
    }
}
