package com.jianpei.jpeducation.activitys.tiku.daily;


import android.content.Intent;
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
import com.jianpei.jpeducation.activitys.tiku.AnswerCardActivity;
import com.jianpei.jpeducation.activitys.tiku.result.AnswerResultActivity;
import com.jianpei.jpeducation.adapter.tiku.NOptionsAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.RecordInfoBean;
import com.jianpei.jpeducation.utils.dialog.SubmitPaperDialog;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyAnswerActivity extends BaseActivity {


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
    @BindView(R.id.tv_select_jiexi)
    TextView tvSelectJiexi;
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;


    private String source = "1";//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题
    private String index_type;//2上一题；1下一题；0原样返回
    private String question_id;//当前问题id
    private String answering_time = "";//剩余时间（秒）
    private String type;//1.单选，2多选，5简答

    private String paperId;//试卷id
    private String recordId;//答题记录id
    private String restartType;//0添加新试卷，2重做，1继续答题
    private String paperName;//试卷名称
    private String class_id;

    private AnswerModel answerModel;
    private NOptionsAdapter nOptionsAdapter;
    private List<AnswerBean> answerBeans;

    private boolean isSubmit = false;//是否交卷

    private SubmitPaperDialog submitPaperDialog;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_daily_answer;
    }

    @Override
    protected void initView() {
        tvTitle.setText("每日一练");
        paperId = getIntent().getStringExtra("paperId");
        recordId = getIntent().getStringExtra("recordId");
        restartType = getIntent().getStringExtra("restartType");
        paperName = getIntent().getStringExtra("paperName");
        tvPaperName.setText(paperName);

        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

    }

    @Override
    protected void initData() {
        answerBeans = new ArrayList<>();
        nOptionsAdapter = new NOptionsAdapter(answerBeans, this);
        recyclerView.setAdapter(nOptionsAdapter);
        //首次请求结果
        answerModel.getInsertRecordBeanLiveData().observe(this, new Observer<InsertRecordBean>() {
            @Override
            public void onChanged(InsertRecordBean insertRecordBean) {
                dismissLoading();
                RecordInfoBean recordInfoBean = insertRecordBean.getRecord_info();
                if (recordInfoBean != null) {
                    recordId = recordInfoBean.getId();
                    tvTotal.setText("/" + recordInfoBean.getTotal_que_num());
                }
                setData(insertRecordBean.getAnswer_info());

            }
        });
        //获取问题（添加答题记录）
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                dismissLoading();
                if (isSubmit) {//交卷
                    answerModel.paperEvaluation(recordId, "1");
                    return;
                }
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
        //交卷结果
        answerModel.getPaperEvaluationBeanLiveData().observe(this, new Observer<PaperEvaluationBean>() {
            @Override
            public void onChanged(PaperEvaluationBean paperEvaluationBean) {
                dismissLoading();
                startActivity(new Intent(DailyAnswerActivity.this, AnswerResultActivity.class)
                        .putExtra("paperEvaluationBean", paperEvaluationBean)
                        .putExtra("recordId", recordId)
                        .putExtra("paperId", paperId));
                finish();
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

        showLoading("");
        answerModel.insertRecord(paperId, recordId, restartType);
    }

    /**
     * 设置数据
     */
    private void setData(GetQuestionBean getQuestionBean) {
        if (getQuestionBean == null)
            return;
        //
        answerBeans.clear();
        nOptionsAdapter.notifyDataSetChanged();
        //
        question_id = getQuestionBean.getId();//问题ID
        type = getQuestionBean.getType();//1.单选，2多选，5简答

        //显示当前第几道题
        tvCurrent.setText(getQuestionBean.getQuestion_index());
        //问题
        tvTopic.setText(Html.fromHtml(getQuestionBean.getQuestion_name(), getImageGetter(), null));
        //答题区
        if ("1".equals(type) || "2".equals(type)) {//单选
            etAnswer.setVisibility(View.GONE);//隐藏简答
            recyclerView.setVisibility(View.VISIBLE);//显示选项
            answerBeans.addAll(getQuestionBean.getAnswer_list());
            nOptionsAdapter.setType(type);
            nOptionsAdapter.notifyDataSetChanged();
        } else if ("5".equals(type)) {//简答题
            recyclerView.setVisibility(View.GONE);
            etAnswer.setVisibility(View.VISIBLE);
        }
        //答案区
        llJdJiexi.setVisibility(View.GONE);
        llJdAnswer.setVisibility(View.GONE);
        llJiexi.setVisibility(View.GONE);
        if ("1".equals(type) || "2".equals(type)) {
            tvSelectJiexi.setText(Html.fromHtml(getQuestionBean.getExplain(), getImageGetter(), null));
            tvCorrect.setText(getQuestionBean.getSucc_answer());
            tvMineAnswer.setText(getQuestionBean.getMy_answer());
        } else if ("5".equals(type)) {
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
            ivNext.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            ivNext.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
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


    @OnClick({R.id.iv_back, R.id.iv_previous, R.id.tv_card, R.id.tv_answer, R.id.tv_favorites, R.id.iv_next, R.id.tv_submit, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.iv_previous://上一题
                index_type = "2";
                getQuestion();
                break;
            case R.id.tv_card://答题卡
                startActivityForResult(new Intent(this, AnswerCardActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("type", 0)
                        .putExtra("paperName", paperName), 111);
                break;
            case R.id.tv_answer://查看答案
                lookAnswer();
                break;
            case R.id.tv_favorites://收藏
                answerModel.favorites(paperId, question_id);
                break;
            case R.id.iv_next://下一题
                index_type = "1";
                getQuestion();
                break;
            case R.id.tv_submit://交卷
                if (submitPaperDialog == null) {
                    submitPaperDialog = new SubmitPaperDialog(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submitPaperDialog.dismiss();
                            showLoading("");
                            index_type = "0";
                            isSubmit = true;
                            getQuestion();
                        }
                    });
                }
                submitPaperDialog.show();
                break;
            case R.id.tv_share:
                if (mShareAction==null)
                    initShare();
                mShareAction.open();
                break;
        }
    }

    /**
     * 获取题目（上一题/下一题）
     */
    protected void getQuestion() {
        if ("1".equals(type) || "2".equals(type)) {
            answerModel.getQuestion(source, index_type, question_id, recordId, answering_time, nOptionsAdapter.getMineAnswerIds(), class_id);
        } else if ("5".equals(type)) {
            answerModel.getQuestion(source, index_type, question_id, recordId, answering_time, etAnswer.getText().toString(), class_id);

        }
    }

    /**
     * 查看答案
     */
    protected void lookAnswer() {
        //显示收藏
        tvAnswer.setVisibility(View.GONE);
        tvFavorites.setVisibility(View.VISIBLE);
        if ("1".equals(type) || "2".equals(type)) {//单选多选
            //答案判断
            tvMineAnswer.setText(nOptionsAdapter.getMineAnswer());
            if (TextUtils.isEmpty(nOptionsAdapter.getMineAnswer()))
                tvResult.setText("未作答");
            else if (nOptionsAdapter.getMineAnswer().equals(tvCorrect.getText().toString())) {
                tvResult.setText("回答正确");
            } else {
                tvResult.setText("回答错误");
            }


            llJiexi.setVisibility(View.VISIBLE);
            //
            llJdJiexi.setVisibility(View.GONE);
            llJdAnswer.setVisibility(View.GONE);

        } else if ("5".equals(type)) {//简答
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
                    Glide.with(DailyAnswerActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
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

    /**
     * 答题卡返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && resultCode == 112) {
            CardBean cardBean = (CardBean) data.getParcelableExtra("cardBean");
            index_type = "0";
            answerModel.getQuestion(source, index_type, cardBean.getQuestion_id(), recordId, answering_time, "", class_id);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
