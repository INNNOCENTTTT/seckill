package com.example.seckill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.seckill.vo.RespBean;
import com.example.seckill.pojo.User;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2023-05-19
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 用户信息（测试）
     * @param user
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }
}
