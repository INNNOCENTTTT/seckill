package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Goods;
import com.example.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author zhn
 * @since 2023-05-22
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取GoodsVo（包含了商品和秒杀信息）列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 根据商品id获取对应GoodsVo
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
