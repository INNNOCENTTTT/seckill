package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.service.impl.UserServiceImpl;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @projectName: seckill
 * @package: com.example.seckill.controller
 * @className: LoginController
 * @author: zhn
 * @description: 登录接口
 * @date: 2023/5/20 14:23
 * @version: 1.0
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {


    @Autowired
    private IUserService userService;


    /**
     * 跳转登录页面
     *
     * @param
     * @return java.lang.String
     * @author LC
     * @operation add
     * @date 10:13 上午 2022/3/2
     **/
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(User user) {
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    // 会帮你自动封装为LoginVo对象
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest req, HttpServletResponse resp) {
        log.info("{}", loginVo);
        return userService.doLogin(loginVo, req, resp);
    }
}