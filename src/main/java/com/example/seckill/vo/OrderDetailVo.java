package com.example.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.seckill.pojo.Order;

/**
 * @projectName: seckill
 * @package: com.example.seckill.vo
 * @className: OrderDetailVo
 * @author: zhn
 * @description: 订单详情返回对象
 * @date: 2023/5/24 13:46
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
