package com.hisoka.filmreview.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.FilmChainScore;
import com.hisoka.filmreview.entity.FilmScore;
import com.hisoka.filmreview.service.FilmChainScoreService;
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
@RequestMapping("/filmChainScore")
public class FilmChainScoreController {
    @Resource
    private FilmChainScoreService filmChainScoreService;

    @GetMapping("/{filmId}")
    public Result getFilmChainScore(@PathVariable Long filmId){
        FilmChainScore filmChainScore = filmChainScoreService.getOne(new QueryWrapper<FilmChainScore>().eq("film_id", filmId));
        if(filmChainScore == null){
            return Result.fail("未找到该电影的院线点评");
        }
        return Result.ok(filmChainScore);
    }

}
