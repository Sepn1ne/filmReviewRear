package com.hisoka.filmreview.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.City;
import com.hisoka.filmreview.mapper.CityMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@RestController
@RequestMapping("/filmreview/city")
public class CityController {

    @Resource
    private CityMapper cityMapper;

    //查所有省份
    @GetMapping("/province")
    private Result getProvince(){
        List<City> cities = cityMapper.selectList(new QueryWrapper<City>().eq("type", 1));
        return Result.ok(cities);
    }

    //根据省份查市
    @GetMapping("/city/{provinceId}")
    private Result getCity(@PathVariable("provinceId") Integer provinceId){
        List<City> cities = cityMapper.selectList(new QueryWrapper<City>().eq("pid", provinceId));
        return Result.ok(cities);
    }

    //根据市查区县
    @GetMapping("/district/{districtId}")
    private Result getDistrict(@PathVariable("districtId") Integer districtId){
        List<City> cities = cityMapper.selectList(new QueryWrapper<City>().eq("pid", districtId));
        return Result.ok(cities);
    }


}

