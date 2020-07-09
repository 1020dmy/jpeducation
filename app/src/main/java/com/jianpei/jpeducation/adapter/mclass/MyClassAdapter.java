package com.jianpei.jpeducation.adapter.mclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.MyClassBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.MyHolder> {

    private Context context;
    private List<MyClassBean> list;
    private int type;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public MyClassAdapter(Context context, List<MyClassBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mineclass, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyClassBean myClassBean = list.get(position);

        Glide.with(context).load(myClassBean.getImg()).into(holder.imageView);

        holder.tvTitle.setText(myClassBean.getTitle());
        holder.tvPeople.setText(myClassBean.getTotal_count() + "人正在学习");
        holder.tvStudent.setText("已学" + myClassBean.getStudy_count() + "/" + myClassBean.getVideo_count());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView tvTitle, tvPeople, tvStudent;
        private Button button;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPeople = itemView.findViewById(R.id.tv_people);
            tvStudent = itemView.findViewById(R.id.tv_student);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(this);
            if (type == 2) {
                button.setBackgroundResource(R.drawable.shape_myclass);
            }
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
        }
    }
}
