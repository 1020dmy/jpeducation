package com.jianpei.jpeducation.activitys.mine.gold;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.gold.CashWithdrawalBean;
import com.jianpei.jpeducation.viewmodel.GoldModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoldWithdrawActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.btn_exchange)
    Button btnExchange;

    private GoldModel goldModel;

    private String gold;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_gold_withdraw;
    }

    @Override
    protected void initView() {
        tvTitle.setText("金币兑换");
        gold = getIntent().getStringExtra("gold");

        goldModel = new ViewModelProvider(this).get(GoldModel.class);

    }

    @Override
    protected void initData() {

        goldModel.getCashWithdrawalBeanLiveData().observe(this, new Observer<CashWithdrawalBean>() {
            @Override
            public void onChanged(CashWithdrawalBean cashWithdrawalBean) {
                dismissLoading();
            }
        });

        goldModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

    }


    @OnClick({R.id.iv_back, R.id.btn_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_exchange:
                goldModel.cashWithdrawal(etCardNumber.getText().toString(), etName.getText().toString(), gold);
                break;
        }
    }
}
