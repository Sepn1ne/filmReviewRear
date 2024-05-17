package com.hisoka.filmreview.mapper;

import com.hisoka.filmreview.entity.FilmOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@Mapper
public interface FilmOrderMapper extends BaseMapper<FilmOrder> {
    public Integer getHasPurchase(@Param("userId") Long userId,@Param("filmId") Long filmId);
}
