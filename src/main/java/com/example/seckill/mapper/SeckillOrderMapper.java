package com.example.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckill.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 秒杀订单表 Mapper 接口
 * </p>
 *
 * @author zhn
 * @since 2023-05-22
 */
@Mapper
public interface SeckillOrderMapper extends BaseMapper<SeckillOrder> {

}
