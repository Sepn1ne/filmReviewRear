package com.hisoka.filmreview.dto;

import lombok.Data;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/14 15:39
 */

@Data
public class LoginByPwdForm {
    private String email;
    private String password;
}
