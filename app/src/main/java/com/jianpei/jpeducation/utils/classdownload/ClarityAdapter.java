package com.jianpei.jpeducation.utils.classdownload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClarityAdapter extends RecyclerView.Adapter<ClarityAdapter.MyHolder> {

    private List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos;

    public ClarityAdapter(List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos) {
        this.aliyunDownloadMediaInfos = aliyunDownloadMediaInfos;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clarity, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvClarity.setText(aliyunDownloadMediaInfos.get(position).getQuality());

    }

    @Override
    public int getItemCount() {
        return aliyunDownloadMediaInfos != null ? aliyunDownloadMediaInfos.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvClarity;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvClarity = itemView.findViewById(R.id.tv_clarity);
        }
    }
}
