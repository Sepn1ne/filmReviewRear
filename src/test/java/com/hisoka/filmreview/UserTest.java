package com.hisoka.filmreview;

import cn.hutool.core.bean.BeanUtil;
import com.hisoka.filmreview.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/1 15:34
 */

@SpringBootTest
@Slf4j
public class UserTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void getUserByToken(){
        String key = "login:token:c8d30d74a9e94d3fb27bc4636ea1a0f6";
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
        UserDTO userDTO = BeanUtil.fillBeanWithMap(map, new UserDTO(), false);
        System.out.println(userDTO.toString());
    }
}
