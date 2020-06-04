package com.jianpei.jpeducation.adapter.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ZiliaoAdapter extends BaseQuickAdapter<MaterialInfoBean, ZiliaoAdapter.MyHolder> {
    public ZiliaoAdapter(@Nullable List<MaterialInfoBean> data) {
        super(R.layout.item_home_six, data);
    }

    @Override
    protected void convert(@NotNull MyHolder myHolder,MaterialInfoBean materialInfoBean) {

        myHolder.tvTitle.setText(materialInfoBean.getTitle());
        myHolder.tvNums.setText(materialInfoBean.getDownload() + "次下载");
    }


    //    private ArrayList<HomeDataBean.MaterialDataBean.DataBeanXX> dataBeanXXES;

//    public ZiliaoAdapter(ArrayList<HomeDataBean.MaterialDataBean.DataBeanXX> dataBeanXXES) {
//        this.dataBeanXXES = dataBeanXXES;
//    }
//
//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_six, parent, false);
//        return new MyHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        holder.tvTitle.setText(dataBeanXXES.get(position).getTitle());
//        holder.tvNums.setText(dataBeanXXES.get(position).getDownload() + "次下载");
//
//    }

//    @Override
//    public int getItemCount() {
//        return dataBeanXXES != null ? dataBeanXXES.size() : 0;
//    }

    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvNums, tvDown;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvDown = itemView.findViewById(R.id.tv_down);
        }
    }
}
