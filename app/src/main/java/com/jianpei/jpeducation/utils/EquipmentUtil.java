package com.jianpei.jpeducation.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.jianpei.jpeducation.base.MyApplication;

import java.util.Locale;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class EquipmentUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机设备名
     *
     * @return 手机设备名
     */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机主板名
     *
     * @return 主板名
     */
    public static String getDeviceBoand() {
        return Build.BOARD;
    }


    /**
     * 获取手机厂商名
     *
     * @return 手机厂商名
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI() {
//        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            if (ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                return "";
//            }
//            return tm.getDeviceId();
//        }
//        return "";

        //获取手机设备号
        TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        //8.0及以后版本获取
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return TelephonyMgr.getDeviceId();
        } else {//9.0到10.0获取
            return Settings.System.getString(
                    MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }


}
