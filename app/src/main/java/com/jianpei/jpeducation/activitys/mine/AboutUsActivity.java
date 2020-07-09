package com.jianpei.jpeducation.activitys.mine;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.utils.pop.CustomerServicePopup;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.button)
    Button button;

    private CustomerServicePopup customerServicePopup;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        tvTitle.setText("关于我们");

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.button:
                if (customerServicePopup == null)
                    customerServicePopup = new CustomerServicePopup(this, "13121597264");
                customerServicePopup.showPop();
                break;
        }
    }
}
