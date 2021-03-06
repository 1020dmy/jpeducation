package com.jianpei.jpeducation.activitys.tiku.wrongandcollect;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.jianpei.jpeducation.adapter.tiku.CheckWrongOrCollectQuestionsAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.QuestionBean;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckWrongEnterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_answer)
    EditText etAnswer;
    @BindView(R.id.tv_parsing)
    TextView tvParsing;
    @BindView(R.id.ll_jd_jiexi)
    LinearLayout llJdJiexi;
    @BindView(R.id.tv_you_answer)
    TextView tvYouAnswer;
    @BindView(R.id.ll_jd_answer)
    LinearLayout llJdAnswer;
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
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    private String source;//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题
    private String questionId;//当前问题id
    private String class_id;//课程id
    private String cur_name;//课程name
    private String index_type = "0";//2上一题；1下一题；0原样返回
    private String typeAnswer;////1.单选，2多选，5简答
    private String paperId;//试卷id

    //
    private AnswerModel answerModel;
    private List<AnswerBean> answerBeans;
    private CheckWrongOrCollectQuestionsAdapter adapter;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_check_wrong_questions;
    }

    @Override
    protected void initView() {
        source = getIntent().getStringExtra("source");
        QuestionBean questionBean = getIntent().getParcelableExtra("questionBean");
        if (questionBean != null) {
            questionId = questionBean.getQuestion_id();
            class_id = questionBean.getClass_id();
            paperId = questionBean.getPaper_id();
        }
        cur_name = getIntent().getStringExtra("cur_name");
        tvTitle.setText("错题集");
        tvPaperName.setText(cur_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
    }

    @Override
    protected void initData() {
        answerBeans = new ArrayList<>();
        adapter = new CheckWrongOrCollectQuestionsAdapter(answerBeans, this);
        recyclerView.setAdapter(adapter);
        //获取问题（添加答题记录）
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                dismissLoading();
                setData(getQuestionBean);
            }
        });

        //问题收藏/取消收藏
        answerModel.getFavoriteLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                dismissLoading();
                setFavorites(getQuestionBean.getIs_favorites());
            }
        });
        //错误返回
        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        answerModel.getQuestion(source, index_type, questionId, "", "", "", class_id);


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
        paperId=getQuestionBean.getPaper_id();
        answerBeans.clear();
        adapter.notifyDataSetChanged();
        typeAnswer = getQuestionBean.getType();//1.单选，2多选，5简答

        //显示当前第几道题
        tvCurrent.setText(getQuestionBean.getQuestion_index());
        tvTotal.setText("/" + getQuestionBean.getQuestion_total_num());

        //问题
        tvTopic.setText(Html.fromHtml(getQuestionBean.getQuestion_name(), getImageGetter(), null));
        //答题区
        if ("1".equals(typeAnswer) || "2".equals(typeAnswer)) {//单选
            etAnswer.setVisibility(View.GONE);//隐藏简答
            recyclerView.setVisibility(View.VISIBLE);//显示选项
            answerBeans.addAll(getQuestionBean.getAnswer_list());
            adapter.setType(typeAnswer);
            adapter.notifyDataSetChanged();
        } else if ("5".equals(typeAnswer)) {//简答题
            recyclerView.setVisibility(View.GONE);
            etAnswer.setVisibility(View.VISIBLE);
            etAnswer.setText(getQuestionBean.getMy_answer());
        }

        //
        //答案区
        llJdJiexi.setVisibility(View.GONE);
        llJdAnswer.setVisibility(View.GONE);
        llJiexi.setVisibility(View.GONE);
        if ("1".equals(typeAnswer) || "2".equals(typeAnswer)) {
            tvJiexi.setText(Html.fromHtml(getQuestionBean.getExplain(), getImageGetter(), null));
            tvCorrect.setText(getQuestionBean.getSucc_answer());
            tvMineAnswer.setText(getQuestionBean.getMy_answer());
        } else if ("5".equals(typeAnswer)) {
            tvParsing.setText(Html.fromHtml(getQuestionBean.getExplain(), getImageGetter(), null));
            tvYouAnswer.setText(getQuestionBean.getMy_answer());
        }

        //底部按钮
        //是否显示上一题按钮
        tvAnswer.setVisibility(View.VISIBLE);
        tvFavorites.setVisibility(View.GONE);
        if (TextUtils.isEmpty(getQuestionBean.getBefore_answer_id())) {
            ivPrevious.setVisibility(View.INVISIBLE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);
        }
        //是否显示下一题或者交卷按钮
        if (TextUtils.isEmpty(getQuestionBean.getNext_answer_id())) {
            ivNext.setVisibility(View.INVISIBLE);
        } else {
            ivNext.setVisibility(View.VISIBLE);
        }
        //收藏状态
        setFavorites(getQuestionBean.getIs_favorites());
    }

    /**
     * 收藏状态
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


    @OnClick({R.id.iv_back, R.id.tv_share, R.id.iv_previous, R.id.tv_card, R.id.tv_answer, R.id.tv_favorites, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_share:
                if (mShareAction == null)
                    initShare();
                mShareAction.open();
                break;
            case R.id.iv_previous:
                index_type = "2";
                getQuestion();
                break;
            case R.id.tv_card:
                break;
            case R.id.tv_answer:
                lookAnswer();
                break;
            case R.id.tv_favorites:
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.iv_next:
                index_type = "1";
                getQuestion();
                break;
        }
    }

    /**
     * 获取题目（上一题/下一题）
     */
    protected void getQuestion() {
        if ("1".equals(typeAnswer) || "2".equals(typeAnswer)) {
            answerModel.getQuestion(source, index_type, questionId, "", "", adapter.getMineAnswerIds(), class_id);
        } else if ("5".equals(typeAnswer)) {
            answerModel.getQuestion(source, index_type, questionId, "", "", etAnswer.getText().toString(), class_id);

        }
    }

    /**
     * 查看答案
     */
    protected void lookAnswer() {
        //显示收藏
        tvAnswer.setVisibility(View.GONE);
        tvFavorites.setVisibility(View.VISIBLE);
        if ("1".equals(typeAnswer) || "2".equals(typeAnswer)) {//单选多选
            //答案判断
            tvMineAnswer.setText(adapter.getMineAnswer());
            if (TextUtils.isEmpty(adapter.getMineAnswer()))
                tvResult.setText("未作答");
            else if (adapter.getMineAnswer().equals(tvCorrect.getText().toString())) {
                tvResult.setText("回答正确");
            } else {
                tvResult.setText("回答错误");
            }


            llJiexi.setVisibility(View.VISIBLE);
            //
            llJdJiexi.setVisibility(View.GONE);
            llJdAnswer.setVisibility(View.GONE);

        } else if ("5".equals(typeAnswer)) {//简答
            llJdJiexi.setVisibility(View.VISIBLE);
            llJdAnswer.setVisibility(View.VISIBLE);
            //
            llJiexi.setVisibility(View.GONE);

        }

    }

    private Html.ImageGetter getImageGetter() {
        return new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                URLDrawable urlDrawable = new URLDrawable();
                try {
                    Glide.with(CheckWrongEnterActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
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
