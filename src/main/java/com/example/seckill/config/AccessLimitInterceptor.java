package com.example.seckill.config;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * @projectName: seckill
 * @package: com.example.seckill.config
 * @className: AccessLimitInterceptor
 * @author: zhn
 * @description: 拦截器
 * @date: 2023/5/29 22:48
 * @version: 1.0
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            User user = getUser(req, resp);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.count();
            boolean needLogin = accessLimit.needLogin();
            String key = req.getRequestURI();
            if(needLogin) {
                if(user == null) {
                    render(resp, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key += ":" + user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            if(count == null) {
                valueOperations.set(key, 1, second, TimeUnit.SECONDS);
            }
            else if(count < maxCount) {
                valueOperations.increment(key);
            }
            else {
                render(resp, RespBeanEnum.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    /**
     * 构建返回对象
     * @param resp
     * @param respBeanEnum
     */
    private void render(HttpServletResponse resp, RespBeanEnum respBeanEnum) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        RespBean respBean = RespBean.error(respBeanEnum);
        writer.write(new ObjectMapper().writeValueAsString(respBean));
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前登录用户
     * @param req
     * @param resp
     * @return
     */
    private User getUser(HttpServletRequest req, HttpServletResponse resp) {
        String ticket = CookieUtil.getCookieValue(req, "userTicket");
        if(StringUtils.isEmpty(ticket)) {
            return null;
        }
        //  最终返回的结果会直接传给对应的controller方法，所以controller可以接收到User对象作为参数
        return userService.getUserByCookie(ticket, req, resp);
    }
}
