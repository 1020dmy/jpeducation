package com.jianpei.jpeducation.adapter.classinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyHolder> {


    private List<RegimentBean> regimentBeans;
    private Context context;


    public GroupAdapter(List<RegimentBean> regimentBeans, Context context) {
        this.regimentBeans = regimentBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        RegimentBean regimentBean = regimentBeans.get(position);

        Glide.with(context).load(regimentBean.getImg()).into(holder.civHead);
        holder.tvName.setText(regimentBean.getUser_name());
        holder.tvNums.setText("还差" + regimentBean.getRemaining_number() + "人拼成");
        holder.tvTimes.setText("还剩" + regimentBean.getRemaining_time());


    }

    @Override
    public int getItemCount() {
        return regimentBeans != null ? regimentBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private CircleImageView civHead;
        private TextView tvName;
        private TextView tvNums;
        private TextView tvTimes;
        private TextView tvJoin;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civHead = itemView.findViewById(R.id.civ_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvTimes = itemView.findViewById(R.id.tv_time);
            tvJoin = itemView.findViewById(R.id.tv_join);

        }
    }
}
