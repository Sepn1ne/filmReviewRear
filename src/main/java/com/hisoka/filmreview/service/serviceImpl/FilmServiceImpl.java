package com.hisoka.filmreview.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.Film;
import com.hisoka.filmreview.mapper.FilmMapper;
import com.hisoka.filmreview.service.FilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.QiNiu;
import com.hisoka.filmreview.utils.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
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
@Slf4j
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film> implements FilmService {

    @Resource
    private FilmMapper filmMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public IPage<Film> pageByPage(Page<Film> page, QueryWrapper<Film> queryWrapper) {
        return filmMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Result getFilmDetailById(int id) throws UnsupportedEncodingException, JsonProcessingException {
        //1.先查询Redis中电影信息的缓存
        String filmKey = RedisConstants.CACHE_FILM_KEY + id;
        String filmJSON = stringRedisTemplate.opsForValue().get(filmKey);
        if (StrUtil.isNotBlank(filmJSON)) {
            log.info("缓存命中:{}",filmJSON);
            Film f = objectMapper.readValue(filmJSON, Film.class);
            // 2.存在，直接返回
            return Result.ok(f);
        }
        //3.不存在，则先查询数据库
        log.info("缓存未命中");
        Film film = filmMapper.selectById(id);
        if(film == null){
            return Result.fail("未找到该电影");
        }
        film.setCover(QiNiu.getUrl("cover",film.getCover()));

        String json="";
        try {
            json = objectMapper.writeValueAsString(film);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //4.将查询到的film写入Redis
        stringRedisTemplate.opsForValue().set(filmKey,json);

        return Result.ok(film);
    }

    @Override
    public Result updateFilmDetail(Film film) {
        //更新数据库
        int i = filmMapper.updateById(film);
        if(i > 0){
            log.info("更新成功！");
            String key = RedisConstants.CACHE_FILM_KEY + film.getId();
            //删除缓存
            Boolean deleted = stringRedisTemplate.delete(key);
            if(deleted){
                log.info("删除缓存成功");
            }else{
                log.info("删除缓存失败");
            }
            return Result.ok();
        }else{
            return Result.fail("更新失败");
        }
    }
//http://sdkcya4ll.hd-bkt.clouddn.com/film_review/cover/%E5%93%A5%E6%96%AF%E6%8B%89%E5%A4%A7%E6%88%98%E9%87%91%E5%88%9A2.png?e=1715964559&token=ICQuP4YAErcSNKJzhzPxDYs_RVXhl8S0JpUBU_Gt:qZIA6gDfQOy_4_ThFTGel79Ssz8=
    @Override
    public Result getFilmDetailByName(String name) {
        Film film = filmMapper.selectOne(new QueryWrapper<Film>().eq("film_name", name));
        if(film == null){
            return Result.fail("不存在该电影！");
        }
        return Result.ok(film.getId());
    }
}
