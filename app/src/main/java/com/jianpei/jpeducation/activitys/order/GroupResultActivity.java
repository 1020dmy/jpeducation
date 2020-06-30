package com.jianpei.jpeducation.activitys.order;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.classinfo.GroupInfoActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupResultActivity extends BaseActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_back)
    Button btnBack;
    private String state;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_group_result;
    }

    @Override
    protected void initView() {
        state = getIntent().getStringExtra("state");
        ivBack.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }

    @Override
    protected void initData() {
        if ("1".equals(state)) {
            tvTitle.setText("拼团成功");
            btnOne.setText("查看订单");
            tvResult.setText("拼团成功，我们会尽快为您发货");
            ivResult.setImageResource(R.drawable.group_result_success);
        } else {
            tvTitle.setText("拼团失败");
            btnOne.setText("查看订单");
            tvResult.setText("拼团失败，原路退还您的金额");
            ivResult.setImageResource(R.drawable.group_result_fail);
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_one, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_one:
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }


//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
//
//        @NonNull
//        @Override
//        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_head, parent, false);
//            return new MyHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//            if (position <= mRegimentBean.getRegiment_data().size() - 1) {
//                RegimentBean data = mRegimentBean.getRegiment_data().get(position);
//                if ("1".equals(data.getIs_source())) {
//                    holder.tvLeader.setVisibility(View.VISIBLE);
//                }
//                Glide.with(GroupResultActivity.this).load(data.getImg()).placeholder(R.drawable.icon_no_member).into(holder.circleImageView);
//            }
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return Integer.valueOf(mRegimentBean.getNum_people());
//        }
//
//        class MyHolder extends RecyclerView.ViewHolder {
//            private CircleImageView circleImageView;
//            private TextView tvLeader;
//
//            public MyHolder(@NonNull View itemView) {
//                super(itemView);
//                circleImageView = itemView.findViewById(R.id.civ_head);
//                tvLeader = itemView.findViewById(R.id.tv_leader);
//            }
//        }
//    }
}
