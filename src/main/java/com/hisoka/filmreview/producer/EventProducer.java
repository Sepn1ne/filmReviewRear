package com.hisoka.filmreview.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisoka.filmreview.entity.FilmOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/17 13:52
 */
@Slf4j
@Component
public class EventProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Resource
    private ObjectMapper objectMapper;

    public void sendMessage(String exchange, String routingKey, Object object){
        FilmOrder order = (FilmOrder)object;
        String json = "";

        try {
            json= objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            log.error("order转json出现error");
            e.printStackTrace();
        }

        //生成一个全局唯一的消息id,并将该消息放入一个列表保存，在消息丢失的时候可以重新发送
        String id = UUID.randomUUID().toString();


        // Map<String, FilmOrder> orderMap = rabbitCacheMap.getMap();

        //在发送该消息之前将该消息放入map
        // orderMap.put(id,order);

        //发送该消息
        CorrelationData data = new CorrelationData(id);
        rabbitTemplate.convertAndSend(exchange,routingKey,json,data);
        log.info("生成并发送消息:{},消息id为:{}",json,id);
    }

    //重载的方法，该方法用于MyCallBack.confirm()方法中ack == false的时候调用。
    // public void sendMessage(String exchange, String routingKey, Object object, String id){
    //     FilmOrder order = (FilmOrder)object;
    //     String json = "";
    //
    //     try {
    //         //objectMapper null point exception
    //         json= objectMapper.writeValueAsString(order);
    //     } catch (JsonProcessingException e) {
    //         log.error("order转json出现error");
    //         e.printStackTrace();
    //     }
    //
    //     //重载的方法，该方法的形参多了一个消息的id
    //     log.info("生成消息:{},消息id为:{}",json,id);
    //
    //     // Map<String,FilmOrder> orderMap = rabbitCacheMap.getMap();
    //     //
    //     // //在发送该消息之前将该消息放入map
    //     // orderMap.put(id,order);
    //
    //     //发送该消息
    //     CorrelationData data = new CorrelationData(id);
    //     rabbitTemplate.convertAndSend(exchange,routingKey,json,data);
    // }
}