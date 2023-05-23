package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author zhn
 * @since 2023-05-22
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {
    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return OrderId:成功  -1:秒杀失败  0:排队中
     */
    Long getResult(User user, Long goodsId);
}
