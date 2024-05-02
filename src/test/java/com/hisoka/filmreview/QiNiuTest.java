package com.hisoka.filmreview;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Miaobu
 * @version 1.0
 * @description: 测试七牛云下载电影封面url
 * @date 2024/4/19 14:20
 */

@SpringBootTest
@Slf4j
class QiNiuTest {

    @Test
    void getUrl() throws UnsupportedEncodingException {
        String fileName = "film_review/cover/茶馆.png";
        String domainOfBucket = "http://sc0k96wge.hd-bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String accessKey = "ICQuP4YAErcSNKJzhzPxDYs_RVXhl8S0JpUBU_Gt";
        String secretKey = "GV6ivJ3g0bu0k-6WpF_J3a2kmcu10BAieqbMhVJl";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        log.info(finalUrl);
    }
}
