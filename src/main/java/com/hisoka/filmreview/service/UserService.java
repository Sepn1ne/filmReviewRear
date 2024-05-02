package com.hisoka.filmreview.service;

import com.hisoka.filmreview.dto.LoginByPwdForm;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.SignUpForm;
import com.hisoka.filmreview.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-13
 */
public interface UserService extends IService<User> {

    //生成验证码并且发验证码至用户邮箱
    public Result getCode(String email);

    //用户通过邮箱验证码注册
    public Result signUp(SignUpForm form);

    //用户登录
    public Result loginByPwd(LoginByPwdForm form) throws UnsupportedEncodingException;

    //获取token
    public Result getToken(Long userId);

    //判断token是否过期
    public Result judgeToken(String token);
}
