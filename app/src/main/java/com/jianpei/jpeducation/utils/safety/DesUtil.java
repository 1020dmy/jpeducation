package com.jianpei.jpeducation.utils.safety;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DesUtil {
    private static final String KEY_ALGORITHM = "DES";

    private static String charSet = "utf-8";

    public static final String key = "izmFNROXQ98C3w3T8tTiDD/ril0TlAzJGuEY+WiagsN19YPz3ewZJPIsL/H4JBp2mUDwr5mfv+1y7mPNLAnnndSQbklhcpK/aZXJfO7x!xuLt2Z9/xRX7J6DcxlHa9LT$OfhloXHrlFeO|VGbX1O8Et4t6DvFdZOh2SIendLNsF";

    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    public static String encrypt(String content) {

        String result = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = factory.generateSecret(new DESKeySpec(key.getBytes()));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, secretKey, new SecureRandom());
            result = Base64.encodeToString(cipher.doFinal(content.getBytes(charSet)), Base64.DEFAULT);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decrypt(String content) {
        String result = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = factory.generateSecret(new DESKeySpec(key.getBytes()));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, secretKey, new SecureRandom());
            result = new String(cipher.doFinal(Base64.decode(content, Base64.DEFAULT)), charSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
