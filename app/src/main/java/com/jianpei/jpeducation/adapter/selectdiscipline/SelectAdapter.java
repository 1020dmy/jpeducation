package com.jianpei.jpeducation.adapter.selectdiscipline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.utils.HorizontalItemDecoration;
import com.jianpei.jpeducation.utils.listener.MyItemOnClickListener;

import java.util.ArrayList;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MyHolder> {


    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    private ArrayList<DisciplinesBean> disciplinesBeans;
    private String selectId;


    public SelectAdapter(
            Context context, MyItemOnClickListener myItemOnClickListener, ArrayList<DisciplinesBean> disciplinesBeans, String selectId) {
        this.context = context;
        this.myItemOnClickListener = myItemOnClickListener;
        this.disciplinesBeans = disciplinesBeans;
        this.selectId = selectId;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_zhuaye, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tvTitle.setText(disciplinesBeans.get(position).getCatname());

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        SelectItemAdapter selectItemAdapter = new SelectItemAdapter(disciplinesBeans.get(position).getSublevel(), selectId,context);
        selectItemAdapter.setMyItemOnClickListener(myItemOnClickListener);
        holder.recyclerView.setAdapter(selectItemAdapter);


    }

    @Override
    public int getItemCount() {
        return disciplinesBeans == null ? 0 : disciplinesBeans.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private RecyclerView recyclerView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.addItemDecoration(new HorizontalItemDecoration(9, context));

        }
    }

}
