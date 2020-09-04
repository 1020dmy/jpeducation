package com.mantis.imview.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Mr.huang on 2020-1-14
 */
public class KeyBoardUtils {
    public static void closeKeyBoard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            View view = activity.getWindow().peekDecorView();
            if (imm != null && view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public static void showInput(Activity activity, EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }
}
