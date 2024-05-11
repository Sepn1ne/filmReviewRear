package com.hisoka.filmreview.service;

import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.FilmComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.geom.RectangularShape;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-05
 */
public interface FilmCommentService extends IService<FilmComment> {
    public Result getFilmCommentByUp(Long filmId, Integer page) throws UnsupportedEncodingException;

    public Result getFilmCommentByPub(Long filmId, Integer page) throws UnsupportedEncodingException;

    public Result uploadFilmComment(FilmComment filmComment);

    public Result uploadFilmScore(FilmComment filmComment);

    public Result getTop5ByFilmId( Long filmId);
}
