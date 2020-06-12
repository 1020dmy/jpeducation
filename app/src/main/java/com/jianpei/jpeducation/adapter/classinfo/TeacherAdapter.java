package com.jianpei.jpeducation.adapter.classinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.TeacherBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyHolder> {

    private List<TeacherBean> teachers;
    private Context context;

    public TeacherAdapter(List<TeacherBean> teachers, Context context) {
        this.teachers = teachers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infoclass_teacher, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TeacherBean teacherBean = teachers.get(position);

        Glide.with(context).load(teacherBean.getImg()).into(holder.imageView);
        holder.tvName.setText(teacherBean.getTitle());
        holder.tvTitle.setText(teacherBean.getZj_class());
        holder.tvInfo.setText(teacherBean.getInfo());

    }

    @Override
    public int getItemCount() {
        return teachers != null ? teachers.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvName, tvTitle, tvInfo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvInfo = itemView.findViewById(R.id.tv_info);


        }
    }
}
