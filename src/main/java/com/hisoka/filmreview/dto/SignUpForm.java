package com.hisoka.filmreview.dto;

import lombok.Data;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/17 21:40
 */
@Data
public class SignUpForm {
    private String email;
    private String nickName;
    private Integer code;
    private String password;

}
