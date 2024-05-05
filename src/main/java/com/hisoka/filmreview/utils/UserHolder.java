package com.hisoka.filmreview.utils;

import com.hisoka.filmreview.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/2 21:40
 */

@Slf4j
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        log.info("保存用户:" + user.getNickName());
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}