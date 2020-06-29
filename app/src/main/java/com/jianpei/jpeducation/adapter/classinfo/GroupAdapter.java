package com.jianpei.jpeducation.adapter.classinfo;

import android.content.Context;
import android.os.CountDownTimer;
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

    private ToJoinGroupListener toJoinGroupListener;


    public void setToJoinGroupListener(ToJoinGroupListener toJoinGroupListener) {
        this.toJoinGroupListener = toJoinGroupListener;
    }

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
        holder.tvNums.setText(regimentBean.getRemaining_number() + "人");

        holder.countDownTimer = new CountDownTimer(regimentBean.getRemaining_time() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                holder.tvTimes.setText("还剩" + getTimeStr(millisUntilFinished));
            }

            @Override
            public void onFinish() {

            }
        }.start();


    }

    @Override
    public int getItemCount() {
        return regimentBeans != null ? regimentBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civHead;
        private TextView tvName;
        private TextView tvNums;
        private TextView tvTimes;
        private TextView tvJoin;
        private CountDownTimer countDownTimer;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civHead = itemView.findViewById(R.id.civ_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvTimes = itemView.findViewById(R.id.tv_time);
            tvJoin = itemView.findViewById(R.id.tv_join);

            tvJoin.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (toJoinGroupListener != null) {
                toJoinGroupListener.onJoinGroupClick(regimentBeans.get(getLayoutPosition()).getId());
            }

        }
    }

    private String getTimeStr(long l) {
        long day = l / (1000 * 24 * 60 * 60); //单位天
        long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
        long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
        long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

        String hourStr = String.valueOf(hour);
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        String minStr = String.valueOf(minute);
        if (minStr.length() == 1) {
            minStr = "0" + minStr;
        }
        String secondStr = String.valueOf(second);
        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        //如果day为0的时候天不显示
        if (day == 0) {
            return hourStr + ":" + minStr + ":" + secondStr;
        } else {
            return day + "天" + " " + hourStr + ":" + minStr + ":" + secondStr;
        }
    }


    public interface ToJoinGroupListener {
        void onJoinGroupClick(String groupId);
    }
}
