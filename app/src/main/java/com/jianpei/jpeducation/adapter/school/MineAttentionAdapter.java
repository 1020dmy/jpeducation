package com.jianpei.jpeducation.adapter.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.TopicBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineAttentionAdapter extends RecyclerView.Adapter<MineAttentionAdapter.MyHolder> {


    private List<AttentionBean> mAttentionBeans;

    private ArrayList<AttentionBean> selectAttentionBeans;

    private Context context;
    private MyCheckBoxClickListener myCheckBoxClickListener;


    public ArrayList<AttentionBean> getSelectAttentionBeans() {
        return selectAttentionBeans;
    }

    public void setMyCheckBoxClickListener(MyCheckBoxClickListener myCheckBoxClickListener) {
        this.myCheckBoxClickListener = myCheckBoxClickListener;
    }

    public MineAttentionAdapter(List<AttentionBean> attentionBeans, Context context) {
        this.mAttentionBeans = attentionBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_attention, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AttentionBean attentionBean = mAttentionBeans.get(position);
        Glide.with(context).load(attentionBean.getImg()).into(holder.civ_head);
        holder.tvName.setText(attentionBean.getUser_name());
        holder.compatCheckBox.setChecked(attentionBean.isSelect());

    }

    @Override
    public int getItemCount() {
        return mAttentionBeans != null ? mAttentionBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView civ_head;
        private TextView tvName;
        private AppCompatCheckBox compatCheckBox;
        private TextView tvLine;
        private RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tvName = itemView.findViewById(R.id.tv_name);
            compatCheckBox = itemView.findViewById(R.id.checkBox);
            tvLine = itemView.findViewById(R.id.tv_line);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (selectAttentionBeans == null)
                selectAttentionBeans = new ArrayList<>();
            AttentionBean attentionBean = mAttentionBeans.get(getLayoutPosition());
            if (!attentionBean.isSelect() && selectAttentionBeans.size() >= 10) {
                if (myCheckBoxClickListener != null)
                    myCheckBoxClickListener.onCheckClick("最多可@10个用户");
                return;
            }
            attentionBean.setSelect(!attentionBean.isSelect());
            notifyItemChanged(getLayoutPosition());
            if (attentionBean.isSelect()) {
                selectAttentionBeans.add(attentionBean);
            } else {
                selectAttentionBeans.remove(attentionBean);
            }

        }
    }

    public interface MyCheckBoxClickListener {
        void onCheckClick(String message);
    }
}
