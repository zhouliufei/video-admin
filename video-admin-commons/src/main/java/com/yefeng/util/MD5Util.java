package com.yefeng.util;

import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String encodeByUserId(String userId) {
        try {
            MessageDigest ms = MessageDigest.getInstance("MD5");
            byte[] encodeDate = ms.digest(userId.getBytes("utf-8"));
            //使用BASE64Encoder的加密算法，吧字节数组转换为字符串
            String result = new BASE64Encoder().encode(encodeDate);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
