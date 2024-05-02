package com.hisoka.filmreview.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public IPage<Film> pageById(Page<Film> page, QueryWrapper<Film> queryWrapper);
    public Result getFilmDetailById(int id) throws UnsupportedEncodingException;
}
