package com.hisoka.filmreview.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.Film;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-19
 */
public interface FilmService extends IService<Film> {
    IPage<Film> pageByPage(Page<Film> page, QueryWrapper<Film> queryWrapper);
    Result getFilmDetailById(int id) throws UnsupportedEncodingException, JsonProcessingException;

    Result updateFilmDetail(Film film);

    Result getFilmDetailByName(String name) throws UnsupportedEncodingException;

    //将电影评分表中的评分同步到电影表中
    void synchFilmScore();
}
