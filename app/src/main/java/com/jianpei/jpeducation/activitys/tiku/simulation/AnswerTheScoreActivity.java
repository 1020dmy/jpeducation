package com.jianpei.jpeducation.activitys.tiku.simulation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.tiku.result.SimulationExerciseResultActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerTheScoreActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_parsing)
    TextView tvParsing;
    @BindView(R.id.tv_mine_answer)
    TextView tvMineAnswer;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    @BindView(R.id.et_mine_score)
    EditText etMineScore;
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    //
    private AnswerModel answerModel;

    //
    private String recordId, paperId;//答题记录Id,试卷ID
    private PaperEvaluationBean paperEvaluationBean;

    private String questionId;//问题ID
    private String paperName;//试卷名称
    private boolean isSubmit = false;//是否交卷
    private String index_type;//2上一题；1下一题；0原样返回

    private int totalScore = 0;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_answer_the_score;
    }

    @Override
    protected void initView() {
        tvTitle.setText("解答判分");
        tvRight.setText("交卷");
        tvRight.setVisibility(View.VISIBLE);

        paperEvaluationBean = getIntent().getParcelableExtra("paperEvaluationBean");
        recordId = getIntent().getStringExtra("recordId");
        paperId = getIntent().getStringExtra("paperId");
        paperName = getIntent().getStringExtra("paperName");

        tvPaperName.setText(paperName);
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);


    }

    @Override
    protected void initData() {
        //获取问题
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                if (isSubmit) {//去交卷
                    answerModel.paperEvaluation(recordId, "1");
                    return;
                }
                dismissLoading();
                setData(getQuestionBean);

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
                startActivity(new Intent(AnswerTheScoreActivity.this, SimulationExerciseResultActivity.class)
                        .putExtra("paperEvaluationBean", paperEvaluationBean)
                        .putExtra("recordId", recordId)
                        .putExtra("paperId", paperId)
                        .putExtra("paperName", paperName));
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
        setData(paperEvaluationBean.getQuestion_info());

    }

    /**
     * 设置数据
     *
     * @param getQuestionBean
     */
    protected void setData(GetQuestionBean getQuestionBean) {
        if (getQuestionBean == null)
            return;
        questionId = getQuestionBean.getId();
        //总题数
        tvTotal.setText("/" + getQuestionBean.getQuestion_total_num());
        //当前第几题
        tvCurrent.setText(getQuestionBean.getQuestion_index());
        //问题
        tvTopic.setText(Html.fromHtml(getQuestionBean.getQuestion_name(), getImageGetter(), null));
        //答案解析
        tvParsing.setText(Html.fromHtml(getQuestionBean.getSucc_answer(), getImageGetter(), null));
        //我的答案
        tvMineAnswer.setText(getQuestionBean.getMy_answer());
        //分数
        if (TextUtils.isEmpty(getQuestionBean.getQuestion_score())) {
            totalScore = 0;
            tvTotalScore.setText(totalScore + "分");
        } else {
            totalScore = Integer.parseInt(getQuestionBean.getQuestion_score());
            tvTotalScore.setText(totalScore + "分");
        }
        //是否显示上一题
        if (TextUtils.isEmpty(getQuestionBean.getBefore_answer_id())) {
            ivPrevious.setVisibility(View.INVISIBLE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);
        }
        //是否显示下一题
        if (TextUtils.isEmpty(getQuestionBean.getNext_answer_id())) {
            ivNext.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);

        } else {
            ivNext.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
        }
        //收藏
        setFavorites(getQuestionBean.getIs_favorites());

    }

    /**
     * 收藏
     *
     * @param isFavorites
     */
    protected void setFavorites(String isFavorites) {
        if ("1".equals(isFavorites)) {//已经收藏
            tvFavorites.setTextColor(getResources().getColor(R.color.cE73B30));
            Drawable drawable = getResources().getDrawable(
                    R.drawable.answer_favorites);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvFavorites.setCompoundDrawables(null, drawable, null, null);
        } else if ("0".equals(isFavorites)) {//没有收藏
            tvFavorites.setTextColor(getResources().getColor(R.color.c0A0C14));
            Drawable drawable = getResources().getDrawable(
                    R.drawable.answer_unfavorites);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tvFavorites.setCompoundDrawables(null, drawable, null, null);
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_previous, R.id.tv_favorites, R.id.iv_next, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_previous://上一题
                index_type = "2";
                answerScore();
                break;
            case R.id.tv_favorites://收藏
                showLoading("");
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.iv_next://下一题
                index_type = "1";
                answerScore();
                break;
            case R.id.tv_submit://交卷
            case R.id.tv_right:
                showLoading("");
                isSubmit = true;
                index_type = "0";
                answerScore();
                break;
        }
    }


    /**
     * 获取题目（上一题/下一题）解答题平分
     */
    protected void answerScore() {
        int mineScore;
        if (TextUtils.isEmpty(etMineScore.getText().toString())) {
            mineScore = 0;
        } else {
            mineScore = Integer.parseInt(etMineScore.getText().toString());
        }
        if (mineScore <= totalScore) {
            answerModel.answerScore(mineScore + "", questionId, recordId, index_type);
        } else {
            shortToast("凭判分数不能大于总分");
        }


    }


    private Html.ImageGetter getImageGetter() {
        return new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                L.e("======Source:" + source);
                URLDrawable urlDrawable = new URLDrawable();
                try {
                    Glide.with(AnswerTheScoreActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
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
