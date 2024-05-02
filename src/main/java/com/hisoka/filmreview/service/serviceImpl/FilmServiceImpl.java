package com.hisoka.filmreview.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.Film;
import com.hisoka.filmreview.mapper.FilmMapper;
import com.hisoka.filmreview.service.FilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.QiNiu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-19
 */
@Service
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements FilmService {

    @Resource
    private FilmMapper filmMapper;

    @Override
    public IPage<Film> pageById(Page<Film> page, QueryWrapper<Film> queryWrapper) {
        return filmMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Result getFilmDetailById(int id) throws UnsupportedEncodingException {
        Film film = filmMapper.selectById(id);
        if(film == null){
            return Result.fail("未找到该电影");
        }
        film.setCover(QiNiu.getUrl("cover",film.getCover()));
        return Result.ok(film);
    }
}
