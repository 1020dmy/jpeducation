package com.jianpei.jpeducation.adapter.tiku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.MyHolder> {


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tv_title, tv_time, tv_nums, tv_correct_nums, tv_again, tv_result, tv_jiexi;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.ty_type);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_correct_nums = itemView.findViewById(R.id.tv_correct_nums);
            tv_again = itemView.findViewById(R.id.tv_again);
            tv_result = itemView.findViewById(R.id.tv_result);
            tv_jiexi = itemView.findViewById(R.id.tv_jiexi);


        }
    }
}
