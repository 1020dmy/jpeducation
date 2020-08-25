package com.mantis.im_service.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantis.im_service.R;
import com.mantis.im_service.ui.base.BaseActivity;
import com.mantis.im_service.ui.callback.MantisMBViewCB;
import com.mantis.im_service.ui.dialog.ChatDialog;
import com.mantis.im_service.ui.presenter.MsgBoardPresenter;
import com.mantis.im_service.ui.views.MsgBoardItem;

public class MsgBoardActivity extends BaseActivity<MsgBoardPresenter> implements View.OnClickListener, MantisMBViewCB<MsgBoardPresenter> {

    private ImageView mMbIvBack;
    private MsgBoardItem mMbName;
    private MsgBoardItem mMbPhone;
    private long recordTime;
    /**
     * 请输入留言内容...
     */
    private EditText mMbContent;
    /**
     * 提交留言
     */
    private TextView mMbSubmitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_board);
        initView();
    }

    private void initView() {
        mMbIvBack = findViewById(R.id.mb_iv_back);
        mMbName = findViewById(R.id.mb_name);
        mMbPhone = findViewById(R.id.mb_phone);
        mMbContent = findViewById(R.id.mb_content);
        mMbSubmitBt = findViewById(R.id.mb_submit_bt);
        mMbIvBack.setOnClickListener(this);
        mMbSubmitBt.setOnClickListener(this);

        mMbName.isShowLabel(false).setHintContent("请输入姓名").setTitleName("姓     名");
        mMbPhone.isShowLabel(true).setHintContent("请输入手机号").setTitleName("手机号").isStrictNum(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.mb_submit_bt) {
            if (!TextUtils.isEmpty(mMbPhone.getContent()) && mMbPhone.getContent().length() == 11) {
                if ((System.currentTimeMillis() - recordTime) < 3000) {
                    showToast("请不要频繁留言哟！");
                    return;
                }
                recordTime = System.currentTimeMillis();
                showLoading();
                mPresenter.sendRequest(mMbName.getContent(), mMbPhone.getContent(), mMbContent.getText().toString());
            } else {
                showToast("请正确填写手机号!");
            }
        } else if (id == R.id.mb_iv_back) {
            finish();
        }
    }

    @Override
    public MsgBoardPresenter createPresenter() {
        return new MsgBoardPresenter();
    }

    @Override
    public void onSuccess() {
        closeLoading();
        runOnUiThread(()->{
            new ChatDialog(this)
                    .setSingle(true)
                    .setTitle("提示")
                    .setMessage("留言成功，咨询顾问人员会尽快与您联系!")
                    .setOnClickBottomListener(new ChatDialog.OnClickBottomListener() {
                        @Override
                        public void onConfirmClick() {
                            finish();
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    }).show();
        });
    }

    @Override
    public void onError(String errorStr) {
        closeLoading();
        showToast("留言失败,请重新尝试!");
    }

    private void showToast(String content) {
        runOnUiThread(() -> {
            Toast toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
            toast.setText(content);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }
}
