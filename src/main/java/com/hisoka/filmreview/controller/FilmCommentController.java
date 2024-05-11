package com.hisoka.filmreview.controller;


import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.FilmComment;
import com.hisoka.filmreview.service.FilmCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-05
 */
@Slf4j
@RestController
@RequestMapping("/filmComment")
public class FilmCommentController {
    @Resource
    private FilmCommentService filmCommentService;

    //通过电影id获取电影评论，排序按照 up 排序
    @GetMapping("/up/{filmId}/{page}")
    public Result getFilmCommentByUp(@PathVariable("filmId") Long filmId,@PathVariable("page") Integer page) throws UnsupportedEncodingException {
        return filmCommentService.getFilmCommentByUp(filmId,page);
    }

    //通过电影id获取电影评论，排序按照 pubTime 排序
    @GetMapping("/pub/{filmId}/{page}")
    public Result getFilmCommentByPublishTime(@PathVariable("filmId") Long filmId,@PathVariable("page") Integer page) throws UnsupportedEncodingException {
        return filmCommentService.getFilmCommentByPub(filmId,page);
    }

    //上传评论
    @PostMapping
    public Result uploadFilmComment(@RequestBody FilmComment filmComment){
       log.info("收到评论"+filmComment.toString());
        return filmCommentService.uploadFilmComment(filmComment);
    }

    //上传评分
    @PostMapping("/score")
    public Result uploadFilmScore(@RequestBody FilmComment filmComment){
        log.info("收到评分"+filmComment.toString());
        return filmCommentService.uploadFilmScore(filmComment);
    }

    //获取电影的热门评论top5
    @GetMapping("/top5/{filmId}")
    public Result getTop5ByFilmId(@PathVariable Long filmId){
        return filmCommentService.getTop5ByFilmId(filmId);
    }

}

