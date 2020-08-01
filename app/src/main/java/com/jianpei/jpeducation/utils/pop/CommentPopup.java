package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.jianpei.jpeducation.R;

import org.w3c.dom.Text;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentPopup extends PopupWindow {

    private Context mContext;
    private View contentView;

    private EditText et_content;
    private TextView tv_num;
    private Button btn_comment;

    private MyOnClickListener myOnClickListener;

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public CommentPopup(Context context) {
        this.mContext = mContext;
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_comment,
                null, false);
        setContentView(contentView);

        et_content = contentView.findViewById(R.id.et_content);
        tv_num = contentView.findViewById(R.id.tv_num);
        btn_comment = contentView.findViewById(R.id.btn_comment);


        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_num.setText(s.length() + "");
            }
        });
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnClickListener != null) {
                    myOnClickListener.OnClick(et_content.getText().toString());
                }
            }
        });
    }

    public String getContent() {

        return et_content.getText().toString();

    }

    public interface MyOnClickListener {

        void OnClick(String message);
    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

}
