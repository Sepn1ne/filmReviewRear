package com.hisoka.filmreview.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hisoka.filmreview.entity.Film;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-19
 */
@Mapper
public interface FilmMapper extends BaseMapper<Film> {
    //通过ID的顺序进行分页查询
    //IPage<Film> selectFilmPageById(IPage<Film> page, Integer state);
}
