package com.jianpei.jpeducation.activitys.tiku.daily;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.tiku.AnswerCardActivity;
import com.jianpei.jpeducation.activitys.tiku.AnswerTheScoreActivity;
import com.jianpei.jpeducation.activitys.tiku.result.AnswerResultActivity;
import com.jianpei.jpeducation.adapter.tiku.OptionsAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TodayAnswerActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_correct)
    TextView tvCorrect;
    @BindView(R.id.tv_mine_answer)
    TextView tvMineAnswer;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_jiexi)
    TextView tvJiexi;
    @BindView(R.id.ll_jiexi)
    LinearLayout llJiexi;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;

    @BindView(R.id.et_answer)
    TextView etAnswer;
    @BindView(R.id.tv_parsing)
    TextView tvParsing;
    @BindView(R.id.ll_jd_jiexi)
    LinearLayout llJdJiexi;
    @BindView(R.id.tv_you_answer)
    TextView tvYouAnswer;
    @BindView(R.id.ll_jd_answer)
    LinearLayout llJdAnswer;

    private OptionsAdapter optionsAdapter;
    private List<AnswerBean> answerBeans;

    private AnswerModel answerModel;

    private String recordId;
    private String questionId;


    private GetQuestionBean mGetQuestionBean;

    private String source;//1正常答题，2收藏，4本卷错题，3错题集
    private String paperId;//试卷id
    private String restartType;//0添加新试卷，2重做，1继续答题
    private String type;//题目类型1，单选，2多选，5简答


    @Override
    protected int setLayoutView() {
        return R.layout.activity_today_answer;
    }

    @Override
    protected void initView() {
        tvTitle.setText("每日一练");
        source = getIntent().getStringExtra("source");
        recordId = getIntent().getStringExtra("recordId");
        paperId = getIntent().getStringExtra("paperId");
        restartType = getIntent().getStringExtra("restartType");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

    }

    @Override
    protected void initData() {
        answerBeans = new ArrayList<>();
        optionsAdapter = new OptionsAdapter(answerBeans, this);
        optionsAdapter.setSource(source);
        recyclerView.setAdapter(optionsAdapter);
        //1-添加答题记录（答题）
        answerModel.getInsertRecordBeanLiveData().observe(this, new Observer<InsertRecordBean>() {
            @Override
            public void onChanged(InsertRecordBean insertRecordBean) {
                dismissLoading();
                recordId = insertRecordBean.getRecord_info().getId();
                tvTotal.setText("/" + insertRecordBean.getRecord_info().getTotal_que_num());
                //
                mGetQuestionBean = insertRecordBean.getAnswer_info();
                setData();

            }
        });
        //获取问题（添加答题记录）
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                tvTotal.setText("/" + getQuestionBean.getQuestion_total_num());
                if (isSubmit) {//去交卷
                    answerModel.paperEvaluation(recordId, "1");
                    return;
                }
                dismissLoading();
                mGetQuestionBean = getQuestionBean;
                setData();

            }
        });
        //1-问题收藏/取消收藏
        answerModel.getFavoriteLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean s) {
                dismissLoading();
                setFavorites(s.getIs_favorites());

            }
        });
        //交卷
        answerModel.getPaperEvaluationBeanLiveData().observe(this, new Observer<PaperEvaluationBean>() {
            @Override
            public void onChanged(PaperEvaluationBean paperEvaluationBean) {
                dismissLoading();
                if (paperEvaluationBean.getQuestion_info() != null) {
                    startActivity(new Intent(TodayAnswerActivity.this, AnswerTheScoreActivity.class)
                            .putExtra("paperEvaluationBean", paperEvaluationBean)
                            .putExtra("recordId", recordId)
                            .putExtra("paperId", paperId)
                            .putExtra("paperName", tvPaperName.getText().toString()));

                } else {
                    startActivity(new Intent(TodayAnswerActivity.this, AnswerResultActivity.class)
                            .putExtra("paperEvaluationBean", paperEvaluationBean)
                            .putExtra("recordId", recordId)
                            .putExtra("paperId", paperId));
                }
                finish();
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
        if ("1".equals(source)) {//正常答题
            answerModel.insertRecord(paperId, recordId, restartType);
        } else if ("4".equals(source)) {//本卷错题
            answerModel.getQuestion(source, "0", questionId, recordId, "", optionsAdapter.getAnswerId());
            tvAnswer.setVisibility(View.GONE);
            tvFavorites.setVisibility(View.VISIBLE);

        } else if ("5".equals(source)) {//全部解析
            answerModel.getQuestion(source, "0", questionId, recordId, "", optionsAdapter.getAnswerId());
            tvAnswer.setVisibility(View.GONE);
            tvFavorites.setVisibility(View.VISIBLE);
        }

    }


    protected void setData() {
        if (mGetQuestionBean == null)
            return;
        questionId = mGetQuestionBean.getId();
        answerBeans.clear();
        optionsAdapter.setAnswerId("");
        //我的答案
        optionsAdapter.setMineAnswer("");
        optionsAdapter.setLastPosition(-1);

        type = mGetQuestionBean.getType();

        //1.单选，2多选，5简答
        if (!"5".equals(mGetQuestionBean.getType())) {
            etAnswer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            optionsAdapter.setSingle(mGetQuestionBean.getType());
            answerBeans.addAll(mGetQuestionBean.getAnswer_list());
            optionsAdapter.notifyDataSetChanged();
            //解析
            tvJiexi.setText(Html.fromHtml(mGetQuestionBean.getExplain(), getImageGetter(), null));
            tvCorrect.setText(mGetQuestionBean.getSucc_answer());
            tvMineAnswer.setText(mGetQuestionBean.getMy_answer());
            if ("4".equals(source) || "5".equals(source)){
                llJiexi.setVisibility(View.VISIBLE);
                llJdJiexi.setVisibility(View.GONE);
                llJdAnswer.setVisibility(View.GONE);
            }

        } else {
            recyclerView.setVisibility(View.GONE);
            etAnswer.setVisibility(View.VISIBLE);
            //解析
            tvParsing.setText(Html.fromHtml(mGetQuestionBean.getExplain(), getImageGetter(), null));
            tvYouAnswer.setText(mGetQuestionBean.getMy_answer());
            if ("4".equals(source) || "5".equals(source)){
                llJiexi.setVisibility(View.GONE);
                llJdJiexi.setVisibility(View.VISIBLE);
                llJdAnswer.setVisibility(View.VISIBLE);
            }
        }
        //当前第几题
        tvCurrent.setText(mGetQuestionBean.getQuestion_index());
        //是否显示上一题
        if (TextUtils.isEmpty(mGetQuestionBean.getBefore_answer_id())) {
            ivPrevious.setVisibility(View.GONE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);

        }

        //是否显示下一题
        if (TextUtils.isEmpty(mGetQuestionBean.getNext_answer_id())) {
            ivNext.setVisibility(View.GONE);
            if ("1".equals(source)) {
                tvSubmit.setVisibility(View.VISIBLE);
            }
        } else {
            ivNext.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
        }


        tvTopic.setText(Html.fromHtml(mGetQuestionBean.getQuestion_name(), getImageGetter(), null));


        setFavorites(mGetQuestionBean.getIs_favorites());
    }

    protected void answerResult() {
        tvMineAnswer.setText(optionsAdapter.getMineAnswer());
        if (TextUtils.isEmpty(optionsAdapter.getMineAnswer())) {
            tvResult.setText("未作答");
        } else {
            if (optionsAdapter.getMineAnswer().equals(mGetQuestionBean.getSucc_answer())) {
                tvResult.setText("回答正确");
            } else {
                tvResult.setText("回答错误");

            }
        }
    }



    //收藏
    protected void setFavorites(String isFavorites) {
        if ("1".equals(isFavorites)) {
            tvFavorites.setTextColor(getResources().getColor(R.color.cE73B30));
            Drawable drawable = getResources().getDrawable(
                    R.drawable.answer_favorites);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvFavorites.setCompoundDrawables(null, drawable, null, null);
        } else {
            tvFavorites.setTextColor(getResources().getColor(R.color.c0A0C14));
            Drawable drawable = getResources().getDrawable(
                    R.drawable.answer_unfavorites);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvFavorites.setCompoundDrawables(null, drawable, null, null);
        }
    }


    private boolean isSubmit = false;

    @OnClick({R.id.iv_back, R.id.iv_previous, R.id.tv_card, R.id.tv_answer, R.id.iv_next, R.id.tv_share, R.id.tv_favorites, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_previous://上一题
                showLoading("");
                if ("1".equals(source)) {
                    llJdJiexi.setVisibility(View.GONE);
                    llJdAnswer.setVisibility(View.GONE);
                    llJiexi.setVisibility(View.GONE);
                    tvFavorites.setVisibility(View.GONE);
                    tvAnswer.setVisibility(View.VISIBLE);
                }
                answerModel.getQuestion(source, "2", questionId, recordId, "", optionsAdapter.getAnswerId());
                break;
            case R.id.tv_card://答题卡
                startActivityForResult(new Intent(this, AnswerCardActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("type", 0)
                        .putExtra("paperName", tvPaperName.getText().toString()), 111);
                break;
            case R.id.tv_answer://查看答案
                if ("5".equals(type)) {
                    llJiexi.setVisibility(View.GONE);
                    llJdJiexi.setVisibility(View.VISIBLE);
                    llJdAnswer.setVisibility(View.VISIBLE);
                } else {
                    answerResult();
                    llJiexi.setVisibility(View.VISIBLE);
                    llJdJiexi.setVisibility(View.GONE);
                    llJdAnswer.setVisibility(View.GONE);
                }
                tvAnswer.setVisibility(View.GONE);
                tvFavorites.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_next://下一题
                showLoading("");
                if ("1".equals(source)) {
                    llJdJiexi.setVisibility(View.GONE);
                    llJdAnswer.setVisibility(View.GONE);
                    llJiexi.setVisibility(View.GONE);
                    tvFavorites.setVisibility(View.GONE);
                    tvAnswer.setVisibility(View.VISIBLE);
                }
                answerModel.getQuestion(source, "1", questionId, recordId, "", optionsAdapter.getAnswerId());
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_favorites://收藏/取消收藏
                showLoading("");
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.tv_submit://交卷
                showLoading("");
                isSubmit = true;
                answerModel.getQuestion(source, "0", questionId, recordId, "", optionsAdapter.getAnswerId());
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (resultCode) {
            case 111:
                CardBean cardBean = (CardBean) data.getParcelableExtra("cardBean");
                answerModel.getQuestion(source, "0", cardBean.getQuestion_id(), cardBean.getRecord_id() + "", "", "");
                break;
        }

    }

    private Html.ImageGetter getImageGetter() {
        return new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                URLDrawable urlDrawable = new URLDrawable();
                try {
                    Glide.with(TodayAnswerActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            urlDrawable.bitmap = resource;
                            urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                            tvTopic.invalidate();
                            tvTopic.setText(tvTopic.getText());
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
