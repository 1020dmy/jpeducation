package com.mantis.imview.ui.views;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mantis.imview.R;


public class MsgBoardItem extends LinearLayout {
    private EditText mbContent;
    private TextView mbLabel;
    private TextView mbTitleName;

    public MsgBoardItem(Context context) {
        this(context, null);
    }

    public MsgBoardItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MsgBoardItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.msg_board_item_layout, this);
        mbLabel = inflate.findViewById(R.id.mb_item_label_tv);
        mbTitleName = inflate.findViewById(R.id.mb_item_title_name_tv);
        mbContent = inflate.findViewById(R.id.mb_item_content_et);
    }

    public MsgBoardItem isStrictNum(boolean isStrict) {
        if (isStrict) {
            mbContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mbContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }
        return this;
    }

    public MsgBoardItem isShowLabel(boolean isShow) {
        if (isShow) {
            mbLabel.setText("*");
        } else {
            mbLabel.setText(" ");
        }
        return this;
    }

    public MsgBoardItem setTitleName(String name) {
        mbTitleName.setText(name);
        return this;
    }

    public MsgBoardItem setHintContent(String hintStr) {
        mbContent.setHint(hintStr);
        return this;
    }

    public String getContent() {
        return mbContent.getText().toString();
    }
}
