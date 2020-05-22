package com.jianpei.jpeducation;

import com.jianpei.jpeducation.utils.safety.Configure;
import com.jianpei.jpeducation.utils.safety.MD5;

import org.junit.Test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);


        Map<String, Object> signMap = new HashMap<>();
        signMap.put("appId", "sdfdsfasdADSAFAF");
        signMap.put("nonce_str", "11111");
        signMap.put("param", "22222");
        signMap.put("timestamp", "333");
        String ss = createLinkStringByGet(signMap);
        System.out.println("ss:" + ss);
    }

    public String getSing(Map<String, Object> map) {
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
        result = result + "key=sunjingpun";
        System.out.println("result:" + result);

        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    public static String createLinkStringByGet(Map<String, Object> params) {
        String prestr = "";
        try {
            List<String> keys = new ArrayList<>(params.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = String.valueOf(params.get(key));
                value = URLEncoder.encode(value, "UTF-8");
                if (i == keys.size() - 1) {
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prestr;
    }
}