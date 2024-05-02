package com.hisoka.filmreview.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.FilmScore;
import com.hisoka.filmreview.service.FilmScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-21
 */
@RestController
@RequestMapping("/filmScore")
public class FilmScoreController {

    @Resource
    private FilmScoreService filmScoreService;

    @GetMapping("/{filmId}")
    public Result getFilmScore(@PathVariable Long filmId){
        FilmScore filmScore = filmScoreService.getOne(new QueryWrapper<FilmScore>().eq("film_id", filmId));
        if(filmScore == null){
            return Result.fail("未找到该电影的普通点评");
        }
        return Result.ok(filmScore);
    }

}

