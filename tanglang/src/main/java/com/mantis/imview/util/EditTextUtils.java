package com.mantis.imview.util;

import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Mr.huang on 2020-1-14
 */
public class EditTextUtils {
    private static final String TAG = "EditTextUtils";

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


    public static void deleteChar(EditText editText) {
        int index = editText.getSelectionStart();
        Editable editable = editText.getText();
        if (index > 0) {
            boolean isFace = false;
            if (editable.charAt(index - 1) == ']') {
                for (int i = index - 2; i >= 0; i--) {
                    if (editable.charAt(i) == '[') {
                        editable.delete(i, index);
                        isFace = true;
                        break;
                    }
                }
            }
            if (!isFace) {
                editable.delete(index - 1, index);
            }
        }
    }
}
