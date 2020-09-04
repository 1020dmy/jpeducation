package com.mantis.imview.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.huang on 2020-1-12
 */
public class PermissionUtils {
    public static void requestPer(Activity context, List<String> mList, int reqCode) {
        if (mList.size() > 0) {
            String[] arr = new String[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                String str = mList.get(i);
                arr[i] = str;
            }
            ActivityCompat.requestPermissions(context, arr, reqCode);
        }
    }

    public static List<String> checkPermission(Context context, String[] array) {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String p = array[i];
            if (!TextUtils.isEmpty(p)) {
                if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {    //权限不通过
                    mList.add(p);
                }
            }
        }
        return mList;
    }

    public static boolean checkAllPer(Context context, String[] array) {
        boolean flag = true;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                String p = array[i];
                if (!TextUtils.isEmpty(p)) {
                    if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {    //权限不通过
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    public static boolean isSavePertmission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {    //权限不通过
            return false;
        }
        return true;
    }
}
