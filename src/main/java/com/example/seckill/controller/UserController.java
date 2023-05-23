//package com.example.seckill.controller;
//
//
//import com.example.seckill.rabbitmq.MQSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import com.example.seckill.vo.RespBean;
//import com.example.seckill.pojo.User;
//
///**
// * <p>
// * 用户表 前端控制器
// * </p>
// *
// * @author zhoubin
// * @since 2023-05-19
// */
//@Controller
//@RequestMapping("/user")
//public class UserController {
//    @Autowired
//    private MQSender mqSender;
//    /**
//     * 用户信息（测试）
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    @ResponseBody
//    public RespBean info(User user) {
//        return RespBean.success(user);
//    }
//
//    /**
//     * 测试发送rabbitmq消息
//     */
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq() {
//        mqSender.send("hello");
//    }
//
//    /**
//     * Fanout模式
//     */
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public void mq01() {
//        mqSender.send("hello");
//    }
//
//    /**
//     * Direct模式
//     */
//    @RequestMapping("/mq/direct01")
//    @ResponseBody
//    public void mq02() {
//        mqSender.send01("hello red");
//    }
//    /**
//     * Direct模式
//     */
//    @RequestMapping("/mq/direct02")
//    @ResponseBody
//    public void mq03() {
//        mqSender.send02("hello green");
//    }
//
//    /**
//     * Topic模式
//     */
//    @RequestMapping("/mq/topic01")
//    @ResponseBody
//    public void mq04() {
//        mqSender.send03("hello red");
//    }
//    /**
//     * Topic模式
//     */
//    @RequestMapping("/mq/topic02")
//    @ResponseBody
//    public void mq05() {
//        mqSender.send04("hello green");
//    }
//}
