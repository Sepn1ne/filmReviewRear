package com.hisoka.filmreview;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.entity.Film;
import com.hisoka.filmreview.entity.FilmScore;
import com.hisoka.filmreview.mapper.FilmMapper;
import com.hisoka.filmreview.mapper.FilmScoreMapper;
import com.hisoka.filmreview.service.FilmScoreService;
import com.hisoka.filmreview.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/21 23:09
 */

@SpringBootTest
@Slf4j
public class FilmTest {

    @Resource
    private FilmScoreMapper filmScoreMapper;

    @Resource
    private FilmService filmService;

    @Resource
    private FilmMapper filmMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //计算电影的分数
    @Test
    public void computeFilmScore(){
        List<FilmScore> scores = filmScoreMapper.selectList(new QueryWrapper<FilmScore>());
        System.out.println(scores.size());
        for(FilmScore fs : scores){
            float score = (float) (fs.getNormalFive()*10 + fs.getNormalFour()*8 + fs.getNormalThree()*6 + fs.getNormalTwo()*4 + fs.getNormalOne()*2) / (fs.getNormalFive() + fs.getNormalFour() + fs.getNormalThree() + fs.getNormalTwo() + fs.getNormalOne());
            String s = String.format("%.1f", score);
            float v = Float.parseFloat(s);
            fs.setNormalScore(v);
            filmScoreMapper.updateById(fs);
        }
    }

    //将电影评分表中的评分同步到电影表中
    @Test
    public void sychnFilmScore(){
        filmService.synchFilmScore();
    }

    //将所有电影按score填充到zset中
    @Test
    public void insertFilmsToRedis(){
        List<Film> films = filmMapper.selectList(new QueryWrapper<Film>());
        for(Film f : films){
            stringRedisTemplate.opsForZSet().add("films:sort:score",f.getId().toString(),f.getScore());
        }
        Set<String> ss = stringRedisTemplate.opsForZSet().range("films:sort:score", 0, -1);
        for (String s : ss) {
            System.out.println(s);
        }
    }

}
