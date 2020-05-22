package com.jianpei.jpeducation.utils.safety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SafetyUtil {

    //后台分配给的secert与appid结合使用
    //参数转换为json串
    //通过des加密参数
    //生成32位随机数
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static String getSing(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> var3 = map.entrySet().iterator();
        while (var3.hasNext()) {
            Map.Entry<String, Object> entry = var3.next();
            if (entry.getValue() != "")
                list.add((String) entry.getKey() + "=" + entry.getValue() + "&");
        }
        int size = list.size();
        String[] arrayToSort = list.<String>toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
            sb.append(arrayToSort[i]);
        String result = sb.toString();
        result = result + "key=" + Configure.appSecert;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

//    public static String createLinkStringByGet(Map<String, Object> params) {
//        String prestr = "";
//        try {
//            List<String> keys = new ArrayList<>(params.keySet());
//            Collections.sort(keys);
//            for (int i = 0; i < keys.size(); i++) {
//                String key = keys.get(i);
//                String value = String.valueOf(params.get(key));
//                value = URLEncoder.encode(value, "UTF-8");
//                if (i == keys.size() - 1) {
//                    prestr = prestr + key + "=" + value;
//                } else {
//                    prestr = prestr + key + "=" + value + "&";
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return prestr;
//    }

}
