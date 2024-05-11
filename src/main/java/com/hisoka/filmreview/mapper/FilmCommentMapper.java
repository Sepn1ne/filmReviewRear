package com.hisoka.filmreview.mapper;

import com.hisoka.filmreview.entity.FilmComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-05
 */
@Mapper
public interface FilmCommentMapper extends BaseMapper<FilmComment> {
    List<FilmComment> getFilmCommentPageOrderByUp(@Param("filmId") Long filmId,@Param("offset") Integer offset);

    List<FilmComment> getFilmCommentPageOrderByPub(@Param("filmId") Long filmId,@Param("offset") Integer offset);

    List<FilmComment> getTop5ByFilmId(@Param("filmId") Long filmId);
}
