package com.jianpei.jpeducation.adapter.material;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.MaterialDataBean;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialTitleAdapter extends RecyclerView.Adapter<MaterialTitleAdapter.MyHolder> {

    private ArrayList<MaterialDataBean.MaterialTitle> materialTitles;

    private Context context;

    private MyItemClickListener myItemClickListener;

    private MyItemChildClickListener myItemChildClickListener;

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public void setMyItemChildClickListener(MyItemChildClickListener myItemChildClickListener) {
        this.myItemChildClickListener = myItemChildClickListener;
    }

    public MaterialTitleAdapter(ArrayList<MaterialDataBean.MaterialTitle> materialTitles, Context context) {
        this.materialTitles = materialTitles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_title_two, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        MaterialDataBean.MaterialTitle materialTitle = materialTitles.get(position);

        holder.tvTitle.setText(materialTitle.getTitle());

        if (materialTitle.getMaterialInfoBeans() != null) {
            MaterialInfoAdapter materialInfoAdapter = new MaterialInfoAdapter(materialTitle.getMaterialInfoBeans());
            materialInfoAdapter.setMyItemChildClickListener(myItemChildClickListener);
            holder.recyclerView.setAdapter(materialInfoAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return materialTitles != null ? materialTitles.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private LinearLayout llTitle;
        private ImageView imageView;
        private RecyclerView recyclerView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            llTitle = itemView.findViewById(R.id.ll_title);
            imageView = itemView.findViewById(R.id.imageView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            llTitle.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (materialTitles.get(getLayoutPosition()).isUnfold() == false) {
                recyclerView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.material_unfold);
                materialTitles.get(getLayoutPosition()).setUnfold(true);
            } else {
                recyclerView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.material_shrink);
                materialTitles.get(getLayoutPosition()).setUnfold(false);

            }
            if (myItemClickListener != null) {
                myItemClickListener.onClick(getLayoutPosition());
            }


        }
    }


}
