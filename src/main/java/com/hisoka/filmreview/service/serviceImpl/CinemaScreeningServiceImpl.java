package com.hisoka.filmreview.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.CinemaScreeningDto;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.entity.CinemaScreening;
import com.hisoka.filmreview.mapper.CinemaScreeningMapper;
import com.hisoka.filmreview.service.CinemaScreeningService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@Service
public class CinemaScreeningServiceImpl extends ServiceImpl<CinemaScreeningMapper, CinemaScreening> implements CinemaScreeningService {

    @Resource
    private CinemaScreeningMapper cinemaScreeningMapper;

    @Override
    public Result getStockByDistrictId(Integer districtId,Long filmId,Date stickDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stickDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        //将持续时间渲染
        List<CinemaScreeningDto> lists = cinemaScreeningMapper.getStockByDistrictId(districtId,filmId,startTime,endTime);
        SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat simpleDateFormatEnd = new SimpleDateFormat("HH:mm");
        for(CinemaScreeningDto c : lists){
            String s = simpleDateFormatStart.format(c.getStartTime());
            String e = simpleDateFormatEnd.format(c.getEndTime());
            c.setKeepTimes(s + "~" + e);
        }
        return Result.ok(lists);
    }
}
