package com.hisoka.filmreview.controller;


import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.service.FilmOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@RestController
@RequestMapping("/filmreview/filmOrder")
public class FilmOrderController {

    @Resource
    private FilmOrderService filmOrderService;

    //localhost:8081/filmreview/filmOrder/create/1
    //根据csId创建订单
    @GetMapping("/create/{csId}")
    public Result createOrder(@PathVariable("csId") Long csId){
        return filmOrderService.createOrder(csId);
    }
}

