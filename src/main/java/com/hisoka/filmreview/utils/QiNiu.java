package com.hisoka.filmreview.utils;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Miaobu
 * @version 1.0
 * @description: 用于七牛云上传和下载电影封面的工具类
 * @date 2024/4/19 14:56
 */
@Slf4j
public class QiNiu {
    public static String getUrl(String filePos, String coverName) throws UnsupportedEncodingException {
        String fileName = "film_review/" + filePos + "/" + coverName;
        String domainOfBucket = "http://sdkcya4ll.hd-bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String accessKey = "ICQuP4YAErcSNKJzhzPxDYs_RVXhl8S0JpUBU_Gt";
        String secretKey = "GV6ivJ3g0bu0k-6WpF_J3a2kmcu10BAieqbMhVJl";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        log.info(finalUrl);
        return finalUrl;
    }
}
