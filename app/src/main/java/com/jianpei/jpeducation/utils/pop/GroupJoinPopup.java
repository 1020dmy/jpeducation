package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupJoinPopup extends PopupWindow {

    private Context mContext;
    private View contentView;
    private ImageButton imbCancel;
    private RecyclerView recyclerView;
    private TextView tvTitle, tvNums, tvTime;

    private Button btnSubmit;

    private RegimentBean mRegimentBean;

    private CountDownTimer countDownTimer;


    public GroupJoinPopup(Context mContext, RegimentBean regimentBeans, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mRegimentBean = regimentBeans;
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_join_group,
                null, false);
        setContentView(contentView);
        imbCancel = contentView.findViewById(R.id.imb_cancel);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        imbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvTitle = contentView.findViewById(R.id.tv_title);
        tvNums = contentView.findViewById(R.id.tv_nums);
        tvTime = contentView.findViewById(R.id.tv_time);
        btnSubmit = contentView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(onClickListener);


        setData();

    }

    @Override
    public void dismiss() {
        countDownTimer.cancel();
        super.dismiss();
    }

    private void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        countDownTimer = new CountDownTimer(mRegimentBean.getRemaining_time() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTime.setText(getTimeStr(millisUntilFinished));

            }

            @Override
            public void onFinish() {

            }
        }.start();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        tvTitle.setText("参与" + mRegimentBean.getUser_name() + "的拼单");
        tvNums.setText(mRegimentBean.getRemaining_number() + "");

    }

    public void showPop() {
        showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_head, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            if (position <= mRegimentBean.getRegiment_data().size() - 1) {
                RegimentBean data = mRegimentBean.getRegiment_data().get(position);
                if ("1".equals(data.getIs_source())) {
                    holder.tvLeader.setVisibility(View.VISIBLE);
                }
                Glide.with(mContext).load(data.getImg()).placeholder(R.drawable.icon_no_member).into(holder.circleImageView);
            }
        }


        @Override
        public int getItemCount() {
            return Integer.valueOf(mRegimentBean.getNum_people());
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private CircleImageView circleImageView;
            private TextView tvLeader;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                circleImageView = itemView.findViewById(R.id.civ_head);
                tvLeader = itemView.findViewById(R.id.tv_leader);
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
}
