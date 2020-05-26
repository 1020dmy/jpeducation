package com.jianpei.jpeducation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.SharedPreferencesCompat;

import com.jianpei.jpeducation.base.MyApplication;

import java.util.Map;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:轻量级存储
 */
public class SpUtils {

    public static final String SP_userModel = "SP_userModel";

    public  static final String ISFirst="IS_first";

    public  static final String USERNAME="userName";

    public  static final String TOKEN="token";

    public  static final String ID="id";
    public  static final String USER_NAME="user_name";
    public  static final String PHONE="phone";




    /**
     * Put string value to SharedPreferences
     *
     * @param spname
     * @param key
     * @param value
     */
    public static void putString(String spname, String key, String value) {
        SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences(spname, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * @param key
     * @return
     */
    public static String getValue(String key) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 存入某个key对应的value值
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }


    /**
     * 得到某个key对应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(String key, Object defValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);

        }
        return null;
    }

    /**
     * 返回所有数据
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        edit.remove(key);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 清除所有内容
     */
    public static void clearUserInfo() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_userModel, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

}
