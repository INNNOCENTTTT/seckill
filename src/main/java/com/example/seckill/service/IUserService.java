package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.User;
//import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2023-05-19
 */
public interface IUserService extends IService<User> {
    /**
     * 登录接口
     * @param loginVo
     * @param req
     * @param resp
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 根据Cookie（userTicket）获取用户
     * @param userTicket
     * @param req
     * @param resp
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 更新密码 根据Cookie获取用户
     * @param userTicket
     * @param password
     * @param req
     * @param resp
     * @return
     */
    RespBean updatePassword(String userTicket, String password, HttpServletRequest req, HttpServletResponse resp);
}
