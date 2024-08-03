package com.hisoka.filmreview.service;

import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.CinemaScreening;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
public interface CinemaScreeningService extends IService<CinemaScreening> {
    public Result getStockByDistrictId(Integer districtId, Long filmId, Date stickDate);
}
