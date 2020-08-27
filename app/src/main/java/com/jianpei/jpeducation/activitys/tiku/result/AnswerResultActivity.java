package com.jianpei.jpeducation.activitys.tiku.result;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.activitys.tiku.daily.DailyWrongAndParsingActivity;
import com.jianpei.jpeducation.activitys.tiku.daily.TodayAnswerActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.RecommendClassAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.tiku.EvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerResultActivity extends BaseActivity implements MyItemOnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_correct)
    TextView tvCorrect;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_notDone)
    TextView tvNotDone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_multipleChoice_score)
    TextView tvMultipleChoiceScore;
    @BindView(R.id.tv_answerQuestion_score)
    TextView tvAnswerQuestionScore;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_back_list)
    TextView tvBackList;
    @BindView(R.id.tv_look_error)
    TextView tvLookError;
    @BindView(R.id.tv_parsing)
    TextView tvParsing;


    private PaperEvaluationBean paperEvaluationBean;
    private String recordId;
    private String paperId;
    //
    private List<GroupInfoBean> groupDataBeans;
    private RecommendClassAdapter recommendClassAdapter;

    private String source;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_answer_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText("练习结果");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        paperEvaluationBean = getIntent().getParcelableExtra("paperEvaluationBean");
        recordId = getIntent().getStringExtra("recordId");
        paperId = getIntent().getStringExtra("paperId");


        setData();

    }


    protected void setData() {
        if (paperEvaluationBean == null) {
            shortToast("测试结果获取失败！");
            return;
        }
        EvaluationBean evaluationBean = paperEvaluationBean.getEvaluation();
        if (evaluationBean == null) {
            shortToast("测试结果获取失败！");
            return;
        }
        tvName.setText(evaluationBean.getPaper_name());
        tvScore.setText(evaluationBean.getTotal_score() + "");
        tvCorrect.setText(evaluationBean.getSuccess_num() + "");
        tvError.setText(evaluationBean.getFail_num() + "");
        tvNotDone.setText(evaluationBean.getUnsolved_num() + "");
        tvTime.setText(evaluationBean.getCreate_time_str());
        //
        tvMultipleChoiceScore.setText(evaluationBean.getChoose_score() + "分");
        tvAnswerQuestionScore.setText(evaluationBean.getAnswer_score() + "分");
        if (TextUtils.isEmpty(evaluationBean.getScore_assessment()))
            tvTip.setVisibility(View.GONE);
        else {
            tvTip.setText(evaluationBean.getScore_assessment());
        }
        //
        if (evaluationBean.getGroupData() != null && evaluationBean.getGroupData().size() > 0) {
            groupDataBeans = new ArrayList<>();
            groupDataBeans.addAll(evaluationBean.getGroupData());
            recommendClassAdapter = new RecommendClassAdapter(groupDataBeans, this);
            recommendClassAdapter.setMyItemOnClickListener(this);
            recyclerView.setAdapter(recommendClassAdapter);
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_back_list, R.id.tv_look_error, R.id.tv_parsing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back_list:
                finish();
                break;
            case R.id.tv_look_error:
                source = "4";
                toActivity();
                break;
            case R.id.tv_parsing:
                source = "5";
                toActivity();
                break;
        }
    }

    private void toActivity() {
        startActivity(new Intent(this, DailyWrongAndParsingActivity.class)
                .putExtra("source", source)
                .putExtra("recordId", recordId)
                .putExtra("paperId", paperId)
                .putExtra("paperName", tvName.getText().toString()));
    }

    @Override
    public void onItemClick(int position, View view) {
        startActivity(new Intent(this, ClassInfoActivity.class)
                .putExtra("groupId", groupDataBeans.get(position).getId())
                .putExtra("catId", groupDataBeans.get(position).getCat_id()));


    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
