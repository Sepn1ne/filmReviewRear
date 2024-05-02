package com.hisoka.filmreview.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.LoginByPwdForm;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.SignUpForm;
import com.hisoka.filmreview.entity.User;
import com.hisoka.filmreview.mapper.UserMapper;

import com.hisoka.filmreview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    //根据用户Id获取用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return userMapper.selectById(id);
    }

    //测试
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //邮箱密码登录
    @PostMapping("/login")
    public Result loginByPwd(@RequestBody LoginByPwdForm form) throws UnsupportedEncodingException {
        return userService.loginByPwd(form);
    }

    //获取验证码
    @GetMapping("/code/{email}")
    public Result getCode(@PathVariable("email") String email){
        //获取验证码
        System.out.println("获取验证码");
        return userService.getCode(email);
    }

    //提交注册
    @PostMapping("/signup")
    public Result signup(@RequestBody SignUpForm signupForm){

        return userService.signUp(signupForm);
    }

    //获取token
    @GetMapping("/token/{userId}")
    public Result getToken(@PathVariable Long userId){
        return userService.getToken(userId);
    }

    //判断token是否过期
    @PostMapping("/token")
    public Result judgeToken(@RequestParam String token){
        System.out.println("判断token是否过期");
        return userService.judgeToken(token);
    }

}

