package com.hisoka.filmreview.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.UserDTO;
import com.hisoka.filmreview.entity.FilmComment;
import com.hisoka.filmreview.mapper.FilmCommentMapper;
import com.hisoka.filmreview.service.FilmCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.QiNiu;
import com.hisoka.filmreview.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-05
 */
@Service
public class FilmCommentServiceImpl extends ServiceImpl<FilmCommentMapper, FilmComment> implements FilmCommentService {

    @Resource
    private FilmCommentMapper filmCommentMapper;

    @Override
    public Result getFilmCommentByUp(Long filmId, Integer page) throws UnsupportedEncodingException {
        //获取总评论数量
        QueryWrapper<FilmComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("film_id",filmId);
        Integer count = filmCommentMapper.selectCount(queryWrapper);
        //页数
        Integer pages = (int)Math.ceil(count/8.0);
        //偏移量
        int offset = (page-1)*8;
        List<FilmComment> filmComments = filmCommentMapper.getFilmCommentPageOrderByUp(filmId, offset);

        //设置头像
        // for(FilmComment f : filmComments){
        //     f.setIcon(QiNiu.getUrl("head",f.getIcon()));
        // }

        return Result.ok(filmComments,(long)pages);
    }

    @Override
    public Result getFilmCommentByPub(Long filmId, Integer page) throws UnsupportedEncodingException {
        //获取总评论数量
        QueryWrapper<FilmComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("film_id",filmId);
        Integer count = filmCommentMapper.selectCount(queryWrapper);
        //页数
        Integer pages = (int)Math.ceil(count/8.0);
        //偏移量
        int offset = (page-1)*8;
        List<FilmComment> filmComments = filmCommentMapper.getFilmCommentPageOrderByPub(filmId, offset);

        //设置头像
        // for(FilmComment f : filmComments){
        //     f.setIcon(QiNiu.getUrl("head",f.getIcon()));
        // }
        return Result.ok(filmComments,(long)pages);
    }

    //上传评论
    @Override
    public Result uploadFilmComment(FilmComment filmComment) {
        UserDTO user = UserHolder.getUser();
        //1.查询用户是否登录
        if(user == null){
            log.error("用户未登录！ThreadLocal没有当前用户！");
            return Result.fail("用户未登录！");
        }
        filmComment.setUserId(user.getId());
        filmComment.setPublishTime(LocalDateTime.now());
        filmComment.setUp(0);
        filmComment.setDown(0);
        //2.查询用户是否已经评分或者点评
        FilmComment one = filmCommentMapper.selectOne(new QueryWrapper<FilmComment>().eq("user_id", user.getId()).eq("film_id", filmComment.getFilmId()));
        //2.1若用户未发表过点评或评分，则上传filmComment
        if(one == null){
            filmComment.setScored(0);
            filmCommentMapper.insert(filmComment);
            return Result.ok();
        }
        //2.2若用户发表过评分但未发表评论，则填充用户的评论
        else if(one.getScored() == 1 && one.getCommented() == 0){
            filmComment.setScored(1);
            filmComment.setScore(one.getScore());
            filmCommentMapper.updateById(filmComment);
            return Result.ok();
        }
        //2.3若用户即发布过评分又发布过评论，则拒绝用户再次评论
        else{
            return Result.fail("不能重复发布评论！");
        }

    }

    @Override
    public Result uploadFilmScore(FilmComment filmComment) {
        UserDTO user = UserHolder.getUser();
        //1.查询用户是否登录
        if(user == null){
            log.error("用户未登录！ThreadLocal没有当前用户！");
            return Result.fail("用户未登录！");
        }
        filmComment.setUserId(user.getId());
        filmComment.setPublishTime(LocalDateTime.now());

        //2.查询用户是否已经评分或者点评
        FilmComment one = filmCommentMapper.selectOne(new QueryWrapper<FilmComment>().eq("user_id", user.getId()).eq("film_id", filmComment.getFilmId()));
        //2.1若用户未发表过点评或评分，则上传filmComment
        if(one == null){
            filmComment.setScored(0);
            filmCommentMapper.insert(filmComment);
            return Result.ok();
        }
        //2.2若用户发表过评论,但未发表评分
        else if(one.getScored() == 0 && one.getCommented() == 1){
            one.setScored(1);
            one.setScore(filmComment.getScore());
            filmCommentMapper.updateById(one);
            return Result.ok();
        }
        //2.3若用户即发布过评分又发布过评论，则拒绝用户再次评论
        else{
            return Result.fail("不能重复发布评分！");
        }
    }

    @Override
    public Result getTop5ByFilmId(Long filmId) {
        List<FilmComment> filmCommentList = filmCommentMapper.getTop5ByFilmId(filmId);
        return Result.ok(filmCommentList);
    }
}
