package com.jianpei.jpeducation.adapter.tiku;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;
import com.shuyu.textutillib.listener.ITextViewShow;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.MyHolder> {


    private List<TestPaperBean> testPaperBeans;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public RecordListAdapter(List<TestPaperBean> testPaperBeans) {
        this.testPaperBeans = testPaperBeans;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TestPaperBean testPaperBean = testPaperBeans.get(position);
        holder.tv_title.setText(testPaperBean.getPaper_name());
        if ("0".equals(testPaperBean.getUser_is_complete())) {
            holder.tv_again.setText("进入");
            holder.tv_result.setEnabled(false);
            holder.tv_jiexi.setEnabled(false);
            holder.tv_correct.setVisibility(View.GONE);
            holder.tv_correct_nums.setVisibility(View.GONE);
        } else if ("1".equals(testPaperBean.getUser_is_complete())) {
            holder.tv_again.setText("继续");
            holder.tv_result.setEnabled(false);
            holder.tv_jiexi.setEnabled(false);
            holder.tv_correct.setVisibility(View.GONE);
            holder.tv_correct_nums.setVisibility(View.GONE);
        } else {
            holder.tv_again.setText("重做");
            holder.tv_result.setEnabled(true);
            holder.tv_jiexi.setEnabled(true);
            holder.tv_correct.setVisibility(View.VISIBLE);
            holder.tv_correct_nums.setVisibility(View.VISIBLE);
        }
        holder.tv_time.setText(testPaperBean.getCreate_time_str());
        holder.tv_nums.setText(testPaperBean.getTotal_que_num() + "题");
        if (TextUtils.isEmpty(testPaperBean.getSuccess_num())) {
            holder.tv_correct_nums.setText("0题");
        } else {
            holder.tv_correct_nums.setText(testPaperBean.getSuccess_num() + "题");
        }
        //1历年真题，2模拟试题，3每日一练，4我的做题记录
        if ("1".equals(testPaperBean.getPaper_type())) {
            holder.tvType.setText("真");

        } else if ("2".equals(testPaperBean.getPaper_type())) {
            holder.tvType.setText("模");

        } else if ("3".equals(testPaperBean.getPaper_type())) {
            holder.tvType.setText("练");

        }

    }

    @Override
    public int getItemCount() {
        return testPaperBeans != null ? testPaperBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvType, tv_title, tv_time, tv_nums, tv_correct_nums, tv_again, tv_result, tv_jiexi;
        private TextView tv_correct;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_correct_nums = itemView.findViewById(R.id.tv_correct_nums);
            tv_again = itemView.findViewById(R.id.tv_again);
            tv_result = itemView.findViewById(R.id.tv_result);
            tv_jiexi = itemView.findViewById(R.id.tv_jiexi);

            tv_correct=itemView.findViewById(R.id.tv_correct);

            tv_again.setOnClickListener(this);
            tv_result.setOnClickListener(this);
            tv_jiexi.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
        }
    }
}
