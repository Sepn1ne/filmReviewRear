package com.hisoka.filmreview;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.entity.User;
import com.hisoka.filmreview.mapper.UserMapper;
import com.hisoka.filmreview.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/6/4 2:16
 */

@SpringBootTest
@Slf4j
public class InsertDataTest {

    @Autowired
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void insertUserToMysql(){
        for(int i=16;i<1001;i++){
            userMapper.insert(new User((long)i,i+".",i+"@qq.com","123123","https://api.multiavatar.com/sasfadddfd"+i+".svg"));
        }
    }

    @Test
    public void insertUserToRedis(){
        //批量获取数据库中1~1000的userId与nickname
        //查出userId从1~1000的数据
        List<User> list = userMapper.selectList(new QueryWrapper<User>().ge("id",1).lt("id",1001));

        //利用管道将数据存入到redis
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                list.forEach(user -> {
                    //UUID是hutool工具包的，可以去掉UUID中的-
                    UUID uuid = UUID.randomUUID();
                    byte[] key=("login:token:"+uuid.toString(true)).getBytes();
                    HashMap<byte[], byte[]> map = new HashMap<>();
                    map.put("nickName".getBytes(), user.getNickName().getBytes());
                    map.put("icon".getBytes(), user.getIcon().getBytes());
                    map.put("id".getBytes(), user.getId().toString().getBytes());
                    connection.hashCommands().hMSet(key,map);
                });
                return null;
            }
        });

    }
}
