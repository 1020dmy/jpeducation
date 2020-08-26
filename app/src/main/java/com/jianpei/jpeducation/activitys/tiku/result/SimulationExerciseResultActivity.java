package com.jianpei.jpeducation.activitys.tiku.result;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimulationExerciseResultActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_use_time)
    TextView tvUseTime;
    @BindView(R.id.tv_total_fraction)
    TextView tvTotalFraction;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.tv_fraction)
    TextView tvFraction;
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
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.tv_answer)
    TextView tvAnswer;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_simulation_exercise_result;
    }

    @Override
    protected void initView() {
        tvTitle.setText("考试结果");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.info_black_share);


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.tv_back_list, R.id.tv_again, R.id.tv_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                break;
            case R.id.tv_back_list:
                break;
            case R.id.tv_again:
                break;
            case R.id.tv_answer:
                break;
        }
    }
}
