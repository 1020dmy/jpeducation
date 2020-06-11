package com.jianpei.jpeducation.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/14
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class PermissionBaseActivity extends BaseNoStatusActivity {


    private AlertDialog mPermissionDialog;
    private String[] mPermissions;
    private InterfacePermission mInterPer;


    public void setPermissions(String[] mPermissions, InterfacePermission mInterPer) {
        this.mPermissions = mPermissions;
        this.mInterPer = mInterPer;
        init_permission();

    }


    public void init_permission() {
        List<String> mPermissionList = new ArrayList<>();
        mPermissionList.clear();
        for (int i = 0; i < mPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, mPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(mPermissions[i]);
            }
        }

        if (mPermissionList.isEmpty()) {// 全部允许
            //可进行初始化操作,接口回调
            mInterPer.onAllow();
        } else {//存在未允许的权限
            String[] permissionsArr = mPermissionList.toArray(new String[mPermissionList.size()]);
            ActivityCompat.requestPermissions(this, permissionsArr, 101);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选"禁止后不再询问"
                        boolean showRequestPermission = ActivityCompat
                                .shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {//未勾选,循环请求权限
                            init_permission();
                            return;
                        } else { //已勾选,跳转到应用设置,手动打开权限
                            showPermissionDialog();
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    public void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setTitle("使用该应用须打开相应权限")
                    .setMessage("请在“应用设置”的“权限”中,允许相应权限")
                    .setPositiveButton("转自“设置”", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionDialog.cancel();
                            Uri packageURI = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivityForResult(intent, 100);//打开应用设置


                        }
                    }).create();
            mPermissionDialog.setCanceledOnTouchOutside(false);
        }
        mPermissionDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //从设置返回到应用时,再次判断
            init_permission();
        }
    }

    public interface InterfacePermission {
        void onAllow();
    }

}
