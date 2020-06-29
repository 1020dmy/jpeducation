package com.jianpei.jpeducation.utils.dialog;

import android.content.Context;
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


    public DatePickerDialog(@NonNull Context context) {
        this(context, 0, Calendar.getInstance(), -1, -1, -1);
    }

//    public DatePickerDialog(@NonNull Context context, @StyleRes int themeResId) {
//        this(context, themeResId, Calendar.getInstance(), -1, -1, -1);
//    }
//
//    public DatePickerDialog(@NonNull Context context,
//                            int year, int month, int dayOfMonth) {
//        this(context, 0, year, month, dayOfMonth);
//    }

//    public DatePickerDialog(@NonNull Context context, @StyleRes int themeResId,
//                            int year, int monthOfYear, int dayOfMonth) {
//        this(context, themeResId, null, year, monthOfYear, dayOfMonth);
//    }

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
                dismiss();
            }
        });
        if (calendar != null) {
            year = calendar.get(Calendar.YEAR);
            monthOfYear = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        datePicker.init(year, monthOfYear, dayOfMonth, null);
        getWindow().setGravity(Gravity.BOTTOM);

    }


}
