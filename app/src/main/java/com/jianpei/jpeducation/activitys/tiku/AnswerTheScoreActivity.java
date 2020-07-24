package com.jianpei.jpeducation.activitys.tiku;


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
    private PaperEvaluationBean paperEvaluationBean;
    private String recordId, paperId;

    private GetQuestionBean mGetQuestionBean;
    private String questionId;
    private String paperName;

    private boolean isSubmit = false;


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

        mGetQuestionBean = paperEvaluationBean.getQuestion_info();
        tvPaperName.setText(paperName);
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);


    }

    @Override
    protected void initData() {

        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
//                tvTotal.setText("/" + getQuestionBean.getQuestion_total_num());
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
                startActivity(new Intent(AnswerTheScoreActivity.this, AnswerResultActivity.class)
                        .putExtra("paperEvaluationBean", paperEvaluationBean)
                        .putExtra("recordId", recordId)
                        .putExtra("paperId", paperId));
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

        setData();

    }

    protected void setData() {
        if (mGetQuestionBean == null)
            return;
        questionId = mGetQuestionBean.getId();

        //当前第几题
        tvCurrent.setText(mGetQuestionBean.getQuestion_index());
        //
        tvParsing.setText(Html.fromHtml(mGetQuestionBean.getExplain(), getImageGetter(), null));
        tvMineAnswer.setText(mGetQuestionBean.getMy_answer());

        //是否显示上一题
        if (TextUtils.isEmpty(mGetQuestionBean.getBefore_answer_id())) {
            ivPrevious.setVisibility(View.GONE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);

        }
        //是否显示下一题
        if (TextUtils.isEmpty(mGetQuestionBean.getNext_answer_id())) {
            ivNext.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);

        } else {
            ivNext.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
        }


        tvTopic.setText(Html.fromHtml(mGetQuestionBean.getQuestion_name(), getImageGetter(), null));

        //
        setFavorites(mGetQuestionBean.getIs_favorites());
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


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_previous, R.id.tv_favorites, R.id.iv_next, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_previous:
                showLoading("");
                answerModel.answerScore(etMineScore.getText().toString(), questionId, recordId, "2");
                break;
            case R.id.tv_favorites:
                showLoading("");
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.iv_next:
                showLoading("");
                answerModel.answerScore(etMineScore.getText().toString(), questionId, recordId, "1");
                break;
            case R.id.tv_submit:
            case R.id.tv_right:
                showLoading("");
                isSubmit = true;
                answerModel.answerScore(etMineScore.getText().toString(), questionId, recordId, "0");
                break;
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
