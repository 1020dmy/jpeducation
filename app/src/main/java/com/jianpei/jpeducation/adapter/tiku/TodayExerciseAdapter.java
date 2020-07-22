package com.jianpei.jpeducation.adapter.tiku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TodayExerciseAdapter extends RecyclerView.Adapter<TodayExerciseAdapter.MyHolder> {

    private List<TestPaperBean> testPaperBeans;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public TodayExerciseAdapter(List<TestPaperBean> testPaperBeans) {
        this.testPaperBeans = testPaperBeans;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_exercise, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TestPaperBean testPaperBean = testPaperBeans.get(position);
        holder.tv_title.setText(testPaperBean.getPaper_name());
        if ("0".equals(testPaperBean.getUser_is_complete())) {
            holder.tv_status.setText("进入");
            holder.tv_status.setBackgroundResource(R.drawable.shape_testpaper_one);
        } else if ("1".equals(testPaperBean.getUser_is_complete())) {
            holder.tv_status.setText("继续");
            holder.tv_status.setBackgroundResource(R.drawable.shape_testpaper_two);
        } else {
            holder.tv_status.setText("重做");
            holder.tv_status.setBackgroundResource(R.drawable.shape_testpaper_three);
        }

    }

    @Override
    public int getItemCount() {
        return testPaperBeans != null ? testPaperBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_title, tv_status, tv_line;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_line = itemView.findViewById(R.id.tv_line);

            tv_status.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
        }
    }
}
