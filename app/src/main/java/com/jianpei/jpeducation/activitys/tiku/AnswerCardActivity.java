package com.jianpei.jpeducation.activitys.tiku;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.tiku.AnswerCardAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerCardActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AnswerCardAdapter answerCardAdapter;

    private List<CardBean> cardBeans;

    private AnswerModel answerModel;
    private String recordId;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_answer_card;
    }

    @Override
    protected void initView() {
        tvTitle.setText("答题卡");
        recordId = getIntent().getStringExtra("recordId");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);


    }

    @Override
    protected void initData() {
        cardBeans = new ArrayList<>();
        answerCardAdapter = new AnswerCardAdapter(cardBeans, this);
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
}
