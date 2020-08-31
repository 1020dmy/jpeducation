package com.jianpei.jpeducation.adapter.tiku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.URLDrawable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class NOptionsAdapter extends RecyclerView.Adapter<NOptionsAdapter.MyHolder> {

    private List<AnswerBean> answerBeans;
    private Context context;
    private String type;//1.单选，2多选，5简答

//    public List<AnswerBean> mineAnswers;

    public NOptionsAdapter(List<AnswerBean> answerBeans, Context context) {
        this.answerBeans = answerBeans;
        this.context = context;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMineAnswerIds() {
        String answerIds;
        StringBuilder stringBuilder = new StringBuilder();
        for (AnswerBean answerBean : answerBeans) {
            if (1 == answerBean.getIs_selected()) {
                stringBuilder.append(answerBean.getId());
                stringBuilder.append(",");
            }
        }
        if (!TextUtils.isEmpty(stringBuilder.toString()))
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        answerIds = stringBuilder.toString();
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
        stringBuilder = null;
        return answerIds;
    }

    public String getMineAnswer() {
        String answers;
        StringBuilder stringBuilder = new StringBuilder();
        for (AnswerBean answerBean : answerBeans) {
            if (1 == answerBean.getIs_selected()) {
                stringBuilder.append(answerBean.getOptions_index());
                stringBuilder.append("、");
            }
        }
        if (!TextUtils.isEmpty(stringBuilder.toString()))
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        answers = stringBuilder.toString();
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
        stringBuilder = null;
        return answers;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options_new, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AnswerBean answerBean = answerBeans.get(position);
        holder.tv_options.setText(answerBean.getOptions_index());
        holder.tv_description.setText(Html.fromHtml(answerBean.getAnswers_info(), getImageGetter(holder.tv_description), null));

        if (0 == answerBean.getIs_selected()) {//没有选中
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.compatCheckBox.setChecked(false);
        } else if (1 == answerBean.getIs_selected()) {//选中
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.cF7F7F7));
            holder.compatCheckBox.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return answerBeans != null ? answerBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatCheckBox compatCheckBox;
        private TextView tv_options, tv_description;

        private LinearLayout linearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            compatCheckBox = itemView.findViewById(R.id.checkBox);
            tv_options = itemView.findViewById(R.id.tv_options);
            tv_description = itemView.findViewById(R.id.tv_description);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AnswerBean answerBean = answerBeans.get(getLayoutPosition());
            if ("1".equals(type)) {//单选
                if (answerBean.getIs_selected() == 1)
                    return;
                for (AnswerBean answerBean1 : answerBeans) {
                    answerBean1.setIs_selected(0);
                }
                answerBean.setIs_selected(1);
                notifyDataSetChanged();
            } else if ("2".equals(type)) {//多选
                answerBean.setIs_selected(answerBean.getIs_selected() == 0 ? 1 : 0);
                notifyItemChanged(getLayoutPosition());
            }
        }
    }


    private Html.ImageGetter getImageGetter(TextView textView) {
        return new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                L.e("======Source:" + source);
                URLDrawable urlDrawable = new URLDrawable();
                try {
                    Glide.with(context).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            urlDrawable.bitmap = resource;
                            urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                            textView.invalidate();
                            textView.setText(textView.getText());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return urlDrawable;
            }
        };
    }
}
