package com.example.seckill.config;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: seckill
 * @package: com.example.seckill.config
 * @className: UserArgumentResolver
 * @author: zhn
 * @description: 自定义用户参数，在每次Controller的入参之前就进行校验，最后传到对应的Controller
 * @date: 2023/5/22 0:56
 * @version: 1.0
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;

    /**
     * 当supportsParameter返回true，才执行resolveArgument
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 获取参数类型
        Class<?> parameterType = methodParameter.getParameterType();
        // 判断是否为User类型
        return parameterType == User.class;
    }

    /**
     * 执行后会将结果返回到Controller，所以我们可以接收到一个入参User
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest req = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse resp= nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String ticket = CookieUtil.getCookieValue(req, "userTicket");
        if(StringUtils.isEmpty(ticket)) {
            return null;
        }
        //  最终返回的结果会直接传给对应的controller方法，所以controller可以接收到User对象作为参数
        return userService.getUserByCookie(ticket, req, resp);
    }
}
