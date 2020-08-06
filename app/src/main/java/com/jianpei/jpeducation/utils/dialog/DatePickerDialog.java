package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;


import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.L;

import java.util.Calendar;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DatePickerDialog extends AlertDialog {
    private Context mContext;
    private View contentView;

    private DatePicker datePicker;
    private TextView tvSet;

    private MyOnItemClickListener myOnItemClickListener;

    //    private String birthday;
    private int year, monthOfYear, dayOfMonth;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public DatePickerDialog(@NonNull Context context) {
        this(context, 0, Calendar.getInstance(), -1, -1, -1);
    }

    private DatePickerDialog(@NonNull Context context, @StyleRes int themeResId, @Nullable Calendar calendar, int year,
                             int monthOfYear, int dayOfMonth) {
        super(context);
        this.mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_userinfo_changebirthday, null);
        setView(contentView);
        datePicker = contentView.findViewById(R.id.datePicker);
        tvSet = contentView.findViewById(R.id.tv_set);

        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnItemClickListener != null) {
                    myOnItemClickListener.onChangeBirthday(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());
                }
                dismiss();
            }
        });
//        if (calendar != null) {
//            year = calendar.get(Calendar.YEAR);
//            monthOfYear = calendar.get(Calendar.MONTH);
//            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        }


        getWindow().setGravity(Gravity.BOTTOM);

    }

    public void setBirthday(String birthday) {
        if (!TextUtils.isEmpty(birthday)) {
            String[] date = birthday.split("-");
            year = Integer.parseInt(date[0]);
            monthOfYear = Integer.parseInt(date[1])-1;
            dayOfMonth = Integer.parseInt(date[2]);
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            monthOfYear = Calendar.getInstance().get(Calendar.MONTH);
            dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        datePicker.init(year, monthOfYear, dayOfMonth, null);

    }

    @Override
    public void show() {
        super.show();
    }


    public interface MyOnItemClickListener {
        void onChangeBirthday(String date);
    }
}
