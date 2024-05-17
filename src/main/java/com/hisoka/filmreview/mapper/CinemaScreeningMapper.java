package com.hisoka.filmreview.mapper;

import com.hisoka.filmreview.dto.CinemaScreeningDto;
import com.hisoka.filmreview.entity.CinemaScreening;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hisoka.filmreview.entity.FilmComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@Mapper
public interface CinemaScreeningMapper extends BaseMapper<CinemaScreening> {
    List<CinemaScreeningDto> getStockByDistrictId(@Param("districtId") Integer districtId,@Param("filmId") Long filmId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}
