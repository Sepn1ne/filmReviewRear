package com.hisoka.filmreview.controller;


import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.mapper.CinemaScreeningMapper;
import com.hisoka.filmreview.service.CinemaScreeningService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */

// localhost:8081/filmreview/cinemaScreening/stock/{districtId}
@RestController
@RequestMapping("/filmreview/cinemaScreening")
public class CinemaScreeningController {

    @Resource
    private CinemaScreeningService cinemaScreeningService;

    //根据districtId查询当日余票
    @GetMapping("/stock/{districtId}/{filmId}/{stickDate}")
    public Result getStock(@PathVariable("districtId") Integer districtId,@PathVariable("filmId") Long filmId,@PathVariable("stickDate") Date stickDate){
        return cinemaScreeningService.getStockByDistrictId(districtId,filmId,stickDate);
    }
}

