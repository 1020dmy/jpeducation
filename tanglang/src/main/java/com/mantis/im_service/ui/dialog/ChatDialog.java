package com.mantis.im_service.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantis.im_service.R;

public class ChatDialog extends Dialog {
    /**
     * 显示的标题
     */
    private TextView titleTv;

    /**
     * 显示的消息
     */
    private TextView messageTv;

    /**
     * 确认和取消按钮
     */
    private TextView cancelBn, confirmBn;

    public ChatDialog(Context context) {
        super(context, R.style.CustomDialog);
        OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setOnKeyListener(keylistener);
        setCancelable(false);
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String title;
    private String confirmText, cancelText;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        confirmBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onConfirmClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        cancelBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onCancelClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(confirmText)) {
            confirmBn.setText(confirmText);
        } else {
            confirmBn.setText("确定");
        }
        if (!TextUtils.isEmpty(cancelText)) {
            cancelBn.setText(cancelText);
        } else {
            cancelBn.setText("取消");
        }
        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle) {
            cancelBn.setVisibility(View.GONE);
        } else {
            cancelBn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        cancelBn = findViewById(R.id.negtive);
        confirmBn = findViewById(R.id.positive);
        titleTv = findViewById(R.id.title);
        messageTv = findViewById(R.id.message);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public ChatDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onConfirmClick();

        /**
         * 点击取消按钮事件
         */
        public void onCancelClick();
    }

    public String getMessage() {
        return message;
    }

    public ChatDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ChatDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public ChatDialog setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    public ChatDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    public ChatDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }
}
