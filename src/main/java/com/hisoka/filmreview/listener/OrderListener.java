package com.hisoka.filmreview.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoka.filmreview.config.RabbitConfig;
import com.hisoka.filmreview.entity.FilmOrder;
import com.hisoka.filmreview.service.FilmOrderService;
import com.hisoka.filmreview.service.serviceImpl.FilmOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/17 13:45
 */
@Component
@Slf4j
public class OrderListener {

    //用于解析json
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private FilmOrderServiceImpl filmOrderService;

    //监听电影订单队列
    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE_NAME)
    public void receive(Message message){
        log.info("监听到消息id为{}的消息",message.getMessageProperties().getMessageId());
        //将message封装为order
        FilmOrder order = getFilmOrder(message);
        //对数据库处理接收到的订单消息
        Integer res = filmOrderService.insertOrder2Database(order);
        //判断是否成功存入数据库
        Long  userId = order.getUserId();

        if (res == 1){
            log.error("未在数据库中找到该电影的上映信息");
        }else if ( res == 2){
            log.error("库存不足！用户id为:{}的用户下单失败！",userId);
        }else if(res == 3){
            log.info("出现未知错误");
        }else{
            log.info("用户id为:{}的用户数据写入数据库成功！",userId);
        }
    }

    private FilmOrder getFilmOrder(Message message){
        String msg = new String(message.getBody());
        log.info("接收到消息{}",msg);
        FilmOrder order = null;
        //将字符串转为FilmOrder类型
        try {
            order = objectMapper.readValue(msg, FilmOrder.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return order;
    }
}
