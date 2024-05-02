package com.hisoka.filmreview;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.entity.FilmScore;
import com.hisoka.filmreview.mapper.FilmScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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
}
