package com.example.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName: seckill
 * @package: com.example.seckill.rabbitmq
 * @className: MQSender
 * @author: zhn
 * @description: 消息发送者
 * @date: 2023/5/26 20:41
 * @version: 1.0
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀信息
     * @param message
     */
    public void sendSeckillMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.messgae", message);
    }
//    public void send(Object msg) {
//        log.info("发送消息" + msg);
//        rabbitTemplate.convertAndSend("fanoutExchange","", msg);
//    }
//
//    public void send01(Object msg) {
//        log.info("发送red消息" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
//    }
//
//    public void send02(Object msg) {
//        log.info("发送green消息" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
//    }
//
//    public void send03(Object msg) {
//        log.info("发送消息(QUEUE01接收)" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
//    }
//
//    public void send04(Object msg) {
//        log.info("发送消息(QUEUE01, QUEUE02接收)" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.abc", msg);
//    }
}
