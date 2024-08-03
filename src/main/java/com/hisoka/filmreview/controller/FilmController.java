package com.hisoka.filmreview.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hisoka.filmreview.dto.AbstractFilm;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.Film;
import com.hisoka.filmreview.entity.FilmScore;
import com.hisoka.filmreview.mapper.FilmScoreMapper;
import com.hisoka.filmreview.service.FilmService;
import com.hisoka.filmreview.utils.QiNiu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-18
 */
@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController {

    @Resource
    private FilmService filmService;

    @Resource
    private FilmScoreMapper filmScoreMapper;

    @GetMapping("/page/{page}")
    public Result pageById(@PathVariable int page) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        IPage<Film> pages = filmService.pageByPage(new Page<>(page, 8), queryWrapper);
        List<AbstractFilm> list = new ArrayList<>();
        for(Film f : pages.getRecords()){
            log.info(f.toString());
            AbstractFilm abstractFilm = new AbstractFilm();
            abstractFilm.setFilmName(f.getFilmName());
            abstractFilm.setCover(QiNiu.getUrl("cover",f.getCover()));
            abstractFilm.setId(f.getId());
            list.add(abstractFilm);
        }
        Collections.shuffle(list);
        return Result.ok(list, pages.getPages());
    }
    @GetMapping("/top/{page}")
    public Result topByPage(@PathVariable int page) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        QueryWrapper<Film> queryWrapper = new QueryWrapper<>();
        IPage<Film> pages = filmService.pageByPage(new Page<>(page, 8), queryWrapper);
        List<Film> list = new ArrayList<>();
        for(Film f : pages.getRecords()){
            log.info(f.toString());
            f.setCover(QiNiu.getUrl("cover",f.getCover()));
            // f.setScore(filmScoreMapper.selectOne(new QueryWrapper<FilmScore>().eq("film_id",f.getId())).getNormalScore());
            list.add(f);
        }
        return Result.ok(list, pages.getPages());
    }

    @GetMapping("/{id}")
    public Result getFilmInfoById(@PathVariable int id) throws UnsupportedEncodingException, JsonProcessingException {
        return filmService.getFilmDetailById(id);
    }

    @GetMapping("/name/{filmName}")
    public Result getFilmInfoByName(@PathVariable String filmName) throws UnsupportedEncodingException {
        return filmService.getFilmDetailByName(filmName);
    }
}

