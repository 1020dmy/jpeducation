package com.jianpei.jpeducation.adapter.tiku;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.MyHolder> {

    private List<AnswerBean> answerBeans;

    private Context context;

    private String single;//单选或者多选

    private int lastPosition = -1;
    private String mineAnswer;//我的答案；

    private String answerId;

//    private List<String> mineAnswerIds;
//    private List<String> mineAnswers;

    public OptionsAdapter(List<AnswerBean> answerBeans, Context context) {
        this.answerBeans = answerBeans;
        this.context = context;
//        mineAnswerIds = new ArrayList<>();
//        mineAnswers = new ArrayList<>();
    }

    public void setSingle(String single) {

        this.single = single;
    }

    public String getMineAnswer() {
        if ("2".equals(single))
            getMineAnswers();
        return mineAnswer;
    }

    //    public void getMineAnswerIds() {
//        if (mineAnswerIds.size() != 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String ansewerId : mineAnswerIds) {
//                stringBuilder.append(ansewerId);
//                stringBuilder.append(",");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
//            answerId = stringBuilder.toString();
//            stringBuilder.replace(0, stringBuilder.length(), "");
//            stringBuilder.reverse();
//        }
//    }
    public void getMineAnswerIds() {

        StringBuilder stringBuilder = new StringBuilder();
        for (AnswerBean answerBean : answerBeans) {
            if ("1".equals(answerBean.getIs_selected())) {
                stringBuilder.append(answerBean.getId());
                stringBuilder.append(",");
            }
        }
        if (!TextUtils.isEmpty(stringBuilder.toString())) {
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            answerId = stringBuilder.toString();
        } else {
            answerId = "";
        }
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
    }

    //    public void getMineAnswers() {
//        if (mineAnswers.size() != 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String mineAnswer : mineAnswers) {
//                stringBuilder.append(mineAnswer);
//                stringBuilder.append("、");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
//            mineAnswer = stringBuilder.toString();
//            stringBuilder.replace(0, stringBuilder.length(), "");
//            stringBuilder.reverse();
//        }
//    }
    public void getMineAnswers() {
        StringBuilder stringBuilder = new StringBuilder();

        for (AnswerBean answerBean : answerBeans) {
            if ("1".equals(answerBean.getIs_selected())) {
                stringBuilder.append(answerBean.getOptions_index());
                stringBuilder.append("、");
            }
        }
        if (!TextUtils.isEmpty(stringBuilder.toString())) {
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            mineAnswer = stringBuilder.toString();
        } else {
            mineAnswer = "";
        }
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
    }


    public void setMineAnswer(String mineAnswer) {
//        mineAnswers.clear();
        this.mineAnswer = mineAnswer;
    }

    public String getAnswerId() {
        if ("2".equals(single)) {
            getMineAnswerIds();
        }

        return answerId;
    }

    public void setAnswerId(String answerId) {
//        answerBeans.clear();
        this.answerId = answerId;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AnswerBean answerBean = answerBeans.get(position);
        holder.tv_options.setText(answerBean.getOptions_index());
        Spanned spanned = Html.fromHtml(answerBean.getAnswers_info());
        holder.tv_description.setText(Html.fromHtml(spanned.toString()));

        if ("1".equals(answerBean.getIs_selected())) {//已经选中
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.cF7F7F7));
            holder.compatCheckBox.setChecked(true);
            lastPosition = position;
            mineAnswer = answerBean.getOptions_index();
            answerId = answerBean.getId();

        } else {//没有选中
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.compatCheckBox.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return answerBeans != null ? answerBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatCheckBox compatCheckBox;
        private TextView tv_options, tv_description;

        private RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            compatCheckBox = itemView.findViewById(R.id.checkBox);
            tv_options = itemView.findViewById(R.id.tv_options);
            tv_description = itemView.findViewById(R.id.tv_description);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AnswerBean answerBean = answerBeans.get(getLayoutPosition());
            if ("1".equals(single)) {//单选
                if (!answerBean.isSelect() == true) {//未选中
                    if (lastPosition != -1) {
                        answerBeans.get(lastPosition).setIs_selected("0");
                        notifyItemChanged(lastPosition);
                    }
                    answerBean.setIs_selected("1");
                    lastPosition = getLayoutPosition();
                    notifyItemChanged(lastPosition);
                }
            } else {//多选
                if ("0".equals(answerBean.getIs_selected())) {
                    answerBean.setIs_selected("1");
//                    mineAnswerIds.add(answerBean.getId());
//                    mineAnswers.add(answerBean.getOptions_index());
                } else {
                    answerBean.setIs_selected("0");
//                    mineAnswerIds.remove(answerBean.getId());
//                    mineAnswers.remove(answerBean.getOptions_index());

                }
                notifyItemChanged(getLayoutPosition());
            }
        }
    }
}
