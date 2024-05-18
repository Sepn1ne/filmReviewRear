package com.hisoka.filmreview.service;

import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.UserDTO;
import com.hisoka.filmreview.entity.FilmOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
public interface FilmOrderService extends IService<FilmOrder> {
    //创建订单
    Result createOrder(Long csId);
    //将订单插入到数据库
    Integer insertOrder2Database(FilmOrder order);
}
