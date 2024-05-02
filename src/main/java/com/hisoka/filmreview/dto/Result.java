package com.hisoka.filmreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/14 15:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;
    //返回的状态码
    private Integer statusCode;
    private Long total;

    public static Result ok(){
        return new Result(true, null, null, 200 ,null);
    }
    public static Result ok(Object data){
        return new Result(true, null, data, 200 , null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, null, data, 200 , total);
    }
    public static Result fail(String errorMsg){
        return new Result(false, errorMsg, null, 500 , null);
    }
    public static Result fail(String errorMsg,Integer code){
        return new Result(false, errorMsg, null, code , null);
    }
}
