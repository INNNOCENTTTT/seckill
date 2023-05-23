package com.example.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckill.pojo.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 秒杀商品表 Mapper 接口
 * </p>
 *
 * @author zhn
 * @since 2023-05-22
 */
@Mapper
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

}
