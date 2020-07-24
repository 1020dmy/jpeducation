package com.jianpei.jpeducation.activitys.tiku;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.tiku.OptionsAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SimulationExerciseActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_right)
    TextView tvRight;
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
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_paper_name)
    TextView tvPaperName;
    @BindView(R.id.et_answer)
    EditText etAnswer;

    private CountDownTimer countDownTimer;

    private AnswerModel answerModel;
    //
    private String recordId;
    private PaperInfoBean paperInfoBean;
    //
    private OptionsAdapter optionsAdapter;
    private List<AnswerBean> answerBeans;
    //
    private GetQuestionBean mGetQuestionBean;


    private String questionId;

    private String source = "1";

    private long answering_time;

    private boolean isSubmit = false;


    private String type;//题目类型1，单选，2多选，5简答
    private String mineAnswer;//我的回答

    @Override
    protected int setLayoutView() {
        return R.layout.activity_simulation_exercise;
    }

    @Override
    protected void initView() {
        paperInfoBean = getIntent().getParcelableExtra("paperInfoBean");
        recordId = getIntent().getStringExtra("recordId");
        tvPaperName.setText(paperInfoBean.getPaper_name());
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        answerBeans = new ArrayList<>();
        optionsAdapter = new OptionsAdapter(answerBeans, this);
        recyclerView.setAdapter(optionsAdapter);

        //第一题请求的
        answerModel.getInsertRecordBeanLiveData().observe(this, new Observer<InsertRecordBean>() {
            @Override
            public void onChanged(InsertRecordBean insertRecordBean) {
                dismissLoading();
                tvTotal.setText("/" + insertRecordBean.getRecord_info().getTotal_que_num());

                mGetQuestionBean = insertRecordBean.getAnswer_info();
                setData();
                if (countDownTimer == null) {
                    long tims = Long.parseLong(insertRecordBean.getRecord_info().getAnswering_time());
                    countDownTimer = new CountDownTimer(tims * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            answering_time = millisUntilFinished / 1000;
                            long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天
                            long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                            long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                            long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                            tvHour.setText(hour + "");
                            tvMinute.setText(minute + "");
                            tvSecond.setText(second + "");
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();
                }


            }
        });
        //上一题/下一题
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
//                tvTotal.setText("/" + getQuestionBean.getQuestion_total_num());
                if (isSubmit) {//去交卷
                    answerModel.paperEvaluation(recordId, "0");
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
                    startActivity(new Intent(SimulationExerciseActivity.this, AnswerTheScoreActivity.class)
                            .putExtra("paperEvaluationBean", paperEvaluationBean)
                            .putExtra("recordId", recordId)
                            .putExtra("paperId", paperInfoBean.getId())
                            .putExtra("paperName", tvPaperName.getText().toString()));

                } else {
                    startActivity(new Intent(SimulationExerciseActivity.this, AnswerResultActivity.class)
                            .putExtra("paperEvaluationBean", paperEvaluationBean)
                            .putExtra("recordId", recordId)
                            .putExtra("paperId", paperInfoBean.getId()));

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
        answerModel.insertRecord(paperInfoBean.getId(), recordId, paperInfoBean.getComplete_status() + "");


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
        //1.单选，2多选，5简答
        type = mGetQuestionBean.getType();
        if (!"5".equals(mGetQuestionBean.getType())) {
            etAnswer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            optionsAdapter.setSingle(mGetQuestionBean.getType());
            answerBeans.addAll(mGetQuestionBean.getAnswer_list());
            optionsAdapter.notifyDataSetChanged();
        } else {
            etAnswer.setText("");
            recyclerView.setVisibility(View.GONE);
            etAnswer.setVisibility(View.VISIBLE);

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
            tvSubmit.setVisibility(View.VISIBLE);

        } else {
            ivNext.setVisibility(View.VISIBLE);
            tvSubmit.setVisibility(View.GONE);
        }

        //问题
//        Spanned spanned = Html.fromHtml(mGetQuestionBean.getQuestion_name());
//        tvTopic.setText(type + Html.fromHtml(spanned.toString()));

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


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_previous, R.id.tv_card, R.id.tv_favorites, R.id.iv_next, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_previous:
                showLoading("");
                getMineAnswer();//获取我的答案
                answerModel.getQuestion(source, "2", questionId, recordId, String.valueOf(answering_time), mineAnswer);
                break;
            case R.id.tv_card:
                startActivityForResult(new Intent(this, AnswerCardActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("type", 1)
                        .putExtra("paperName", paperInfoBean.getPaper_name()), 111);
                break;
            case R.id.tv_favorites:
                showLoading("");
                answerModel.favorites(paperInfoBean.getId(), questionId);
                break;
            case R.id.iv_next:
                showLoading("");
                getMineAnswer();//获取我的答案
                answerModel.getQuestion(source, "1", questionId, recordId, String.valueOf(answering_time), mineAnswer);
                break;
            case R.id.tv_right://交卷
            case R.id.tv_submit:
                showLoading("");
                isSubmit = true;
                getMineAnswer();//获取我的答案
                answerModel.getQuestion(source, "0", questionId, recordId, "", mineAnswer);
                break;
        }
    }

    protected void getMineAnswer() {
        if ("5".equals(type)) {
            mineAnswer = etAnswer.getText().toString();
        } else {
            mineAnswer = optionsAdapter.getAnswerId();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
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
                answerModel.getQuestion("5", "0", cardBean.getQuestion_id(), cardBean.getRecord_id() + "", "", "");
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
                    Glide.with(SimulationExerciseActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
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
