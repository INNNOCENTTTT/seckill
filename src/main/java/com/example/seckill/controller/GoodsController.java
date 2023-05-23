package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.DetailVo;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: seckill
 * @package: com.example.seckill.controller
 * @className: GoodsController
 * @author: zhn
 * @description: 商品页面
 * @date: 2023/5/21 20:41
 * @version: 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    // 将页面缓存到redis
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转到商品列表页
     * @param model
     * @param user
     * @return
     * win 优化前QPS 1332   优化后 2442
     * linux 优化前QPS 400
     */
    @RequestMapping(value = "/toList", produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest req, HttpServletResponse resp) {
        // 从Redis中获取页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        model.addAttribute("user", user);
        // 如果为空，手动渲染模板文件，并存入Redis
        // webcontext最后传入的Map就是要在页面中渲染的数据，直接将Model转化为Map传入即可
        WebContext context = new WebContext(req, resp, req.getServletContext(), req.getLocale(), model.asMap());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);

        // 渲染成功
        if(!StringUtils.isEmpty(html)) {
            // 将页面存入Redis，设置一分钟过期
            valueOperations.set("goodsList", html,  60, TimeUnit.SECONDS);

        }
        return html;
    }

    /**
     * 向前端ajax请求返回数据，避免redis中缓存整个页面，把thymeleaf模板换成了静态页面
     * @param user
     * @param goodsId
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(User user, @PathVariable Long goodsId, HttpServletRequest req, HttpServletResponse resp) {
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        // 秒杀状态
        int secKillStatus = 0;
        // 秒杀倒计时
        int remainSeconds = 0;
        // 秒杀未开始
        if(nowDate.before(startDate)) {
            remainSeconds = (int)(startDate.getTime() - nowDate.getTime()) / 1000;
        }
        // 秒杀已结束
        else if(nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        }
        // 秒杀中
        else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }


    @RequestMapping(value = "/toDetail2/{goodsId}", produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String toDetail2(Model model, User user, @PathVariable Long goodsId, HttpServletRequest req, HttpServletResponse resp) {
        // 从Redis中获取页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        // 秒杀状态
        int secKillStatus = 0;
        // 秒杀倒计时
        int remainSeconds = 0;
        // 秒杀未开始
        if(nowDate.before(startDate)) {
            remainSeconds = (int)(startDate.getTime() - nowDate.getTime()) / 1000;
        }
        // 秒杀已结束
        else if(nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        }
        // 秒杀中
        else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);

        // 如果为空，手动渲染模板文件，并存入Redis
        // webcontext最后传入的Map就是要在页面中渲染的数据，直接将Model转化为Map传入即可
        WebContext context = new WebContext(req, resp, req.getServletContext(), req.getLocale(), model.asMap());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        // 渲染成功
        if(!StringUtils.isEmpty(html)) {
            // 将页面存入Redis，设置一分钟过期
            valueOperations.set("goodsDetail:" + goodsId, html,  60, TimeUnit.SECONDS);

        }
        return html;
    }
}
