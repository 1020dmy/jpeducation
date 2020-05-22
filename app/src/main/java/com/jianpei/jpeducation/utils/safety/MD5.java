package com.jianpei.jpeducation.utils.safety;

import java.security.MessageDigest;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MD5 {
    private static final String[] hexDigits = new String[] {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        byte[] var5 = b;
        int var4 = b.length;
        for (int var3 = 0; var3 < var4; var3++) {
            byte aB = var5[var3];
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0)
            n = b + 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(origin.getBytes()));
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return resultString;
    }
}
