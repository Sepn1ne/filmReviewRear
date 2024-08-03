package com.hisoka.filmreview.utils;

import org.springframework.util.DigestUtils;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/19 20:25
 */
public class MD5Util {
    public static String encryptToMD5(String str) {
        if(str == null){
            return null;
        }
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
