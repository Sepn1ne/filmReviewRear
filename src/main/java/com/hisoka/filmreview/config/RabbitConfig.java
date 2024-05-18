package com.hisoka.filmreview.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/17 13:10
 */

@Slf4j
@Configuration
public class RabbitConfig {
    //交换机名字
    public static final String ORDER_EXCHANGE_NAME = "order_exchange";
    //队列名称
    public static final String ORDER_QUEUE_NAME = "order_queue";
    //Routing Key
    public static final String ORDER_ROUTING_KEY = "order_key";

    //备份交换机
    public static final String BACKUP_ORDER_EXCHANGE_NAME = "backup_order_exchange";
    //备份队列
    public static final String BACKUP_ORDER_QUEUE_NAME = "backup_order_queue";
    //预警队列
    public static final String WARNING_ORDER_QUEUE_NAME = "warning_order_queue";

    //创建直接交换机
    @Bean("orderExchange")
    public DirectExchange orderExchange(){
        return ExchangeBuilder
                .directExchange(ORDER_EXCHANGE_NAME)
                .durable(true)
                .withArgument("alternate-exchange",BACKUP_ORDER_EXCHANGE_NAME)
                .build();
    }

    //创建处理消息的队列
    @Bean("orderQueue")
    public Queue orderQueue(){
        return QueueBuilder.durable(ORDER_QUEUE_NAME).build();
    }

    //绑定交换机与队列
    @Bean
    public Binding voucherQueue2voucherExchange(Queue orderQueue, DirectExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
    }

    //声明备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_ORDER_EXCHANGE_NAME);
    }

    //备份队列
    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_ORDER_QUEUE_NAME).build();
    }

    //预警队列
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_ORDER_QUEUE_NAME).build();
    }

    //绑定备份队列与交换机
    @Bean("bindingBackupQueue2WarningQueue")
    public Binding bindingBackupQueue2WarningQueue(FanoutExchange backupExchange,Queue backupQueue){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    //绑定预警队列与交换机
    @Bean("bindingWarningQueue2WarningQueue")
    public Binding bindingWarningQueue2WarningQueue(FanoutExchange backupExchange,Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
