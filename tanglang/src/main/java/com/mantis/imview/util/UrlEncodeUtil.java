package com.mantis.imview.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Mr.huang on 2020-1-16
 */
public class UrlEncodeUtil {
    /**
     * 将汉字转为url编码
     *
     * @param string
     * @return
     */
    public static final String utf8UrlEncode(String string) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if ((c <= '\u001f' && c != '\t') || c >= '\u007f') {
                String encode = URLEncoder.encode(Character.toString(c));
                result.append(encode);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 将url编码转为汉字
     *
     * @param string
     * @return
     */
    public static final String utf8UrlDecode(String string) {
        StringBuffer result = new StringBuffer();
        String lowerCase = string.toLowerCase();
        for (int i = 0; i < lowerCase.length(); i++) {
            char c = lowerCase.charAt(i);
            char c2 = 0;
            String e = null;
            if (i < lowerCase.length() - 1) {
                c2 = lowerCase.charAt(i + 1);
            }
            if (c2 != 0 && Character.toString(c2).equals("e")) {
                e = Character.toString(c2);
            } else {
                e = null;
            }
            if (Character.toString(c).contains("%") && e != null && lowerCase.length() > 8) {
                String substring = lowerCase.substring(i, i + 9);
                String decode = URLDecoder.decode(substring);
                result.append(decode);
            } else {
                result.append(Character.toString(c));
            }
        }
        return result.toString();
    }
}
