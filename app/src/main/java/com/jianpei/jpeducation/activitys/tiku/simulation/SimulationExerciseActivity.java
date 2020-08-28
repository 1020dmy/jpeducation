package com.jianpei.jpeducation.activitys.tiku.simulation;


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
import com.jianpei.jpeducation.activitys.tiku.AnswerCardActivity;
import com.jianpei.jpeducation.activitys.tiku.result.SimulationExerciseResultActivity;
import com.jianpei.jpeducation.adapter.tiku.NOptionsAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.RecordInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.dialog.OutAnswerDialog;
import com.jianpei.jpeducation.utils.dialog.SubmitPaperDialog;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SimulationExerciseActivity extends BaseActivity implements OutAnswerDialog.OutAnswerListener {


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
    private String recordId;//答题记录
    private String paperId;//试卷id
    private String paperName;//试卷名称
    private int restartType;//0添加新试卷，2重做，1继续答题
    //
    private List<AnswerBean> answerBeans;
    private NOptionsAdapter nOptionsAdapter;
    //

    private String index_type;//2上一题；1下一题；0原样返回
    private String questionId;//当前问题
    private String source = "1";//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题
    private long answering_time;//答题时间
    private String class_id;

    private boolean isSubmit = false;//是否交卷
    private boolean isEndAnswer = false;//是否结束答题
    private String type;//题目类型1，单选，2多选，5简答

    //
    private OutAnswerDialog outAnswerDialog;
    private SubmitPaperDialog submitPaperDialog;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_simulation_exercise;
    }

    @Override
    protected void initView() {
        recordId = getIntent().getStringExtra("recordId");
        paperId = getIntent().getStringExtra("paperId");
        paperName = getIntent().getStringExtra("paperName");
        restartType = getIntent().getIntExtra("restartType", 0);

        tvPaperName.setText(paperName);

        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        answerBeans = new ArrayList<>();
        nOptionsAdapter = new NOptionsAdapter(answerBeans, this);
        recyclerView.setAdapter(nOptionsAdapter);
        //第一题请求的
        answerModel.getInsertRecordBeanLiveData().observe(this, new Observer<InsertRecordBean>() {
            @Override
            public void onChanged(InsertRecordBean insertRecordBean) {
                dismissLoading();
                if (insertRecordBean == null)
                    return;
                RecordInfoBean recordInfoBean = insertRecordBean.getRecord_info();
                if (recordInfoBean != null) {
                    tvTotal.setText("/" + recordInfoBean.getTotal_que_num());
                    recordId = recordInfoBean.getId();
                }
                setData(insertRecordBean.getAnswer_info());//设置数据
                startCountDownTimer(recordInfoBean.getAnswering_time());//开始计算时间
            }
        });
        //上一题/下一题
        answerModel.getQuestionBeanLiveData().observe(this, new Observer<GetQuestionBean>() {
            @Override
            public void onChanged(GetQuestionBean getQuestionBean) {
                if (isEndAnswer) {//是否结束答题
                    finish();
                    return;
                }
                if (isSubmit) {//去交卷
                    answerModel.paperEvaluation(recordId, "0");
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
                submitTestPaper(paperEvaluationBean);
            }
        });
        //错误返回
        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (isEndAnswer) {//是否结束答题
                    finish();
                    return;
                }
                shortToast(o);
            }
        });
        answerModel.getClosePaperLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                finish();
            }
        });
        showLoading("");
        answerModel.insertRecord(paperId, recordId, restartType + "");


    }

    /**
     * 设置数据
     *
     * @param getQuestionBean
     */
    protected void setData(GetQuestionBean getQuestionBean) {
        if (getQuestionBean == null)
            return;
        answerBeans.clear();
        nOptionsAdapter.notifyDataSetChanged();
        type = getQuestionBean.getType();//1.单选，2多选，5简答
        questionId = getQuestionBean.getId();//问题ID
        //显示当前第几道题
        tvCurrent.setText(getQuestionBean.getQuestion_index());
        //问题
        tvTopic.setText(Html.fromHtml(getQuestionBean.getQuestion_name(), getImageGetter(), null));
        //答题区
        etAnswer.setText("");
        if ("1".equals(type) || "2".equals(type)) {//单选
            etAnswer.setVisibility(View.GONE);//隐藏简答
            recyclerView.setVisibility(View.VISIBLE);//显示选项
            answerBeans.addAll(getQuestionBean.getAnswer_list());
            nOptionsAdapter.setType(type);
            nOptionsAdapter.notifyDataSetChanged();
        } else if ("5".equals(type)) {//简答题
            recyclerView.setVisibility(View.GONE);
            etAnswer.setVisibility(View.VISIBLE);
            etAnswer.setText(getQuestionBean.getMy_answer());
        }
        //答案区 模拟不能查看答案

        //底部按钮
        //是否显示上一题按钮

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
     * 交卷判断（1，有简答题跳转判分，2没有简答题跳转结果）
     *
     * @param paperEvaluationBean
     */
    protected void submitTestPaper(PaperEvaluationBean paperEvaluationBean) {
        if (paperEvaluationBean == null)
            return;
        if (paperEvaluationBean.getQuestion_info() != null) {//跳转简答题判分
            startActivity(new Intent(SimulationExerciseActivity.this, AnswerTheScoreActivity.class)
                    .putExtra("paperEvaluationBean", paperEvaluationBean)
                    .putExtra("recordId", recordId)
                    .putExtra("paperId", paperId)
                    .putExtra("paperName", paperName));

        } else {//跳转结果
            startActivity(new Intent(SimulationExerciseActivity.this, SimulationExerciseResultActivity.class)
                    .putExtra("paperEvaluationBean", paperEvaluationBean)
                    .putExtra("recordId", recordId)
                    .putExtra("paperId", paperId)
                    .putExtra("paperName", paperName));

        }
        finish();

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


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_previous, R.id.tv_card, R.id.tv_favorites, R.id.iv_next, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBack();
                break;
            case R.id.iv_previous://上一题
                index_type = "2";
                getQuestion();
                break;
            case R.id.tv_card://答题卡
                startActivityForResult(new Intent(this, AnswerCardActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("type", 1)
                        .putExtra("paperName", paperName), 111);
                break;
            case R.id.tv_favorites://收藏
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.iv_next://下一题
                index_type = "1";
                getQuestion();
                break;
            case R.id.tv_right://交卷
            case R.id.tv_submit:
                if (submitPaperDialog == null) {
                    submitPaperDialog = new SubmitPaperDialog(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submitPaperDialog.dismiss();
                            showLoading("");
                            isSubmit = true;
                            index_type = "0";
                            getQuestion();
                        }
                    });
                }
                submitPaperDialog.show();
                break;
        }
    }


    /**
     * 获取题目（上一题/下一题）
     */
    protected void getQuestion() {
        if ("1".equals(type) || "2".equals(type)) {
            answerModel.getQuestion(source, index_type, questionId, recordId, String.valueOf(answering_time), nOptionsAdapter.getMineAnswerIds(),class_id);
        } else if ("5".equals(type)) {
            answerModel.getQuestion(source, index_type, questionId, recordId, String.valueOf(answering_time), etAnswer.getText().toString(),class_id);

        }
    }


    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();

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
            answerModel.getQuestion(source, index_type, cardBean.getQuestion_id(), recordId, String.valueOf(answering_time), "",class_id);
        }
        super.onActivityResult(requestCode, resultCode, data);


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

    /**
     * 开始计算时间
     *
     * @param time
     */
    protected void startCountDownTimer(String time) {
        if (TextUtils.isEmpty(time)) {
            return;
        }
        long tims = Long.parseLong(time);
        if (countDownTimer == null) {
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
            };
        }
        countDownTimer.start();

    }

    /**
     * 下次继续
     */
    @Override
    public void onCarryOnClick() {
        outAnswerDialog.dismiss();
        isEndAnswer = true;
        index_type = "0";
        getQuestion();
    }

    /**
     * 结束考试
     */
    @Override
    public void onEndAnswerClick() {
        outAnswerDialog.dismiss();
        isEndAnswer = true;
        answerModel.closePaper(recordId, paperId, "0");
    }

    @Override
    public void onBack() {
        if (outAnswerDialog == null) {
            outAnswerDialog = new OutAnswerDialog(this);
            outAnswerDialog.setOutAnswerListener(this);
        }
        outAnswerDialog.show();
    }
}
