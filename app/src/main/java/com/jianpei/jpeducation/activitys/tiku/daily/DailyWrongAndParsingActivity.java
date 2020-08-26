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
import com.jianpei.jpeducation.adapter.tiku.DailyWrongAndParsingAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CardBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.URLDrawable;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DailyWrongAndParsingActivity extends BaseActivity {


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
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    private String source;//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题
    private String recordId;//答题记录id
    private String paperId;//试卷id
    private String paperName;//试卷名称

    private String questionId;//当前问题id
    private String answering_time;//剩余时间（秒）
    private String index_type = "0";//2上一题；1下一题；0原样返回

    private String type;////1.单选，2多选，5简答


    private AnswerModel answerModel;
    private DailyWrongAndParsingAdapter dailyWrongAndParsingAdapter;
    private List<AnswerBean> answerBeans;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_daily_wrong_and_parsing;
    }

    @Override
    protected void initView() {
        paperId = getIntent().getStringExtra("paperId");
        recordId = getIntent().getStringExtra("recordId");
        source = getIntent().getStringExtra("source");
        paperName = getIntent().getStringExtra("paperName");
        tvPaperName.setText(paperName);
        if ("4".equals(source)) {
            tvTitle.setText("本卷错题");
        } else if ("5".equals(source)) {
            tvTitle.setText("本卷解析");
        }
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
    }

    @Override
    protected void initData() {

        answerBeans = new ArrayList<>();
        dailyWrongAndParsingAdapter = new DailyWrongAndParsingAdapter(answerBeans, this);
        recyclerView.setAdapter(dailyWrongAndParsingAdapter);
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

        answerModel.getQuestion(source, index_type, questionId, recordId, answering_time, "");
    }


    /**
     * 设置数据
     */
    private void setData(GetQuestionBean getQuestionBean) {
        if (getQuestionBean == null)
            return;
        //
        answerBeans.clear();
        dailyWrongAndParsingAdapter.notifyDataSetChanged();
        //
        questionId = getQuestionBean.getId();//问题ID
        type = getQuestionBean.getType();//1.单选，2多选，5简答

        //显示当前第几道题
        tvCurrent.setText(getQuestionBean.getQuestion_index());
        tvTotal.setText("/"+getQuestionBean.getQuestion_total_num());
        //问题
        tvTopic.setText(Html.fromHtml(getQuestionBean.getQuestion_name(), getImageGetter(), null));
        //答题区
        if ("1".equals(type) || "2".equals(type)) {//单选
            recyclerView.setVisibility(View.VISIBLE);//显示选项
            answerBeans.addAll(getQuestionBean.getAnswer_list());
            dailyWrongAndParsingAdapter.notifyDataSetChanged();
        } else if ("5".equals(type)) {//简答题

        }
        //答案区
        if ("1".equals(type) || "2".equals(type)) {//单选
            tvSelectJiexi.setText(Html.fromHtml(getQuestionBean.getExplain(), getImageGetter(), null));
            tvCorrect.setText(getQuestionBean.getSucc_answer());
            tvMineAnswer.setText(getQuestionBean.getMy_answer());
        } else if ("5".equals(type)) {//简答题

        }

        //底部按钮区
        //是否显示上一题按钮
        if (TextUtils.isEmpty(getQuestionBean.getBefore_answer_id())) {
            ivPrevious.setVisibility(View.GONE);
        } else {
            ivPrevious.setVisibility(View.VISIBLE);
        }
        //是否显示下一题按钮
        if (TextUtils.isEmpty(getQuestionBean.getNext_answer_id())) {
            ivNext.setVisibility(View.GONE);
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


    @OnClick({R.id.iv_back, R.id.tv_share, R.id.iv_previous, R.id.tv_card, R.id.tv_favorites, R.id.iv_next})
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
                answerModel.getQuestion(source, index_type, questionId, recordId, answering_time, "");
                break;
            case R.id.tv_card:
                startActivityForResult(new Intent(this, AnswerCardActivity.class)
                        .putExtra("recordId", recordId)
                        .putExtra("type", 0)
                        .putExtra("paperName", paperName), 111);
                break;
            case R.id.tv_favorites:
                answerModel.favorites(paperId, questionId);
                break;
            case R.id.iv_next:
                index_type = "1";
                answerModel.getQuestion(source, index_type, questionId, recordId, answering_time, "");
                break;
        }
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
        if (data != null && requestCode == 111) {
            CardBean cardBean = (CardBean) data.getParcelableExtra("cardBean");
            index_type = "0";
            answerModel.getQuestion(source, index_type, cardBean.getQuestion_id(), recordId, answering_time, "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Html.ImageGetter getImageGetter() {
        return new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                URLDrawable urlDrawable = new URLDrawable();
                try {
                    Glide.with(DailyWrongAndParsingActivity.this).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
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
