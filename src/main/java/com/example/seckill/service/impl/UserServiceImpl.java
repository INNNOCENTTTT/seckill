package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.UUIDUtil;
import com.example.seckill.utils.ValidatorUtil;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
//import com.example.seckill.vo.LoginVo;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2023-05-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录方法
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest req, HttpServletResponse resp) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

//        // 参数校验  已用注解实现
//        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        // 判断手机号
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);

        if (null==user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //校验密码
        if (!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //Cookie相关操作
        //生成Cookie  并将用户对象存入redis
        String ticket = UUIDUtil.uuid();
        // 将用户信息存到redis
        redisTemplate.opsForValue().set("user:" + ticket, user);
//        req.getSession().setAttribute(ticket, user);

        CookieUtil.setCookie(req, resp, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    /**
     * 根据Cookie获取用户
     * @param userTicket
     * @param req
     * @param resp
     * @return
     */
    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest req, HttpServletResponse resp) {

        if(StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User)redisTemplate.opsForValue().get("user:" + userTicket);

        if(user != null) {
            CookieUtil.setCookie(req, resp, "userTicket", userTicket);
        }
        return user;
    }

    /**
     * 更新密码(根据Cookie获取用户)
     * @param userTicket
     * @param password
     * @param req
     * @param resp
     * @return
     */
    @Override
    public RespBean updatePassword(String userTicket, String password, HttpServletRequest req, HttpServletResponse resp) {
        User user = getUserByCookie(userTicket, req, resp);
        if(user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSalt()));
        int i = userMapper.updateById(user);
        if(1 == i) {
            // 删除Redis中的缓存
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }

}
