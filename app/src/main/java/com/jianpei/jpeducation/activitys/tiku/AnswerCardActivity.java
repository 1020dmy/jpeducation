package com.jianpei.jpeducation.activitys.tiku;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.AnswerCardAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerCardActivity extends BaseActivity implements MyItemOnClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_correct)
    LinearLayout llCorrect;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.ll_complete)
    LinearLayout llComplete;
    @BindView(R.id.ll_uncomplete)
    LinearLayout llUncomplete;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;

    private AnswerCardAdapter answerCardAdapter;

    private List<CardBean> cardBeans;

    private AnswerModel answerModel;
    private String recordId;
    private String paperName;

    private int type;//1.模拟练习，0.每日一练

    @Override
    protected int setLayoutView() {
        return R.layout.activity_answer_card;
    }

    @Override
    protected void initView() {
        tvTitle.setText("答题卡");
        recordId = getIntent().getStringExtra("recordId");
        paperName = getIntent().getStringExtra("paperName");
        type = getIntent().getIntExtra("type", -1);

        tvPaperName.setText(paperName);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

        if (type == 0) {
            llComplete.setVisibility(View.GONE);
        } else if (type == 1) {
            llCorrect.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);

        }


    }

    @Override
    protected void initData() {
        cardBeans = new ArrayList<>();
        answerCardAdapter = new AnswerCardAdapter(cardBeans, this);
        answerCardAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(answerCardAdapter);

        answerModel.getPaperCardBeanLiveData().observe(this, new Observer<PaperCardBean>() {
            @Override
            public void onChanged(PaperCardBean paperCardBean) {
                dismissLoading();
                cardBeans.clear();
                cardBeans.addAll(paperCardBean.getCard_data());
                answerCardAdapter.notifyDataSetChanged();
            }
        });
        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        answerModel.paperCard(recordId);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(int position, View view) {

        setResult(111, getIntent().putExtra("cardBean", cardBeans.get(position)));
        finish();

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }

}
