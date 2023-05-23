package com.example.seckill.vo;

import com.example.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: seckill
 * @package: com.example.seckill.vo
 * @className: DetailVo
 * @author: zhn
 * @description: 详情返回对象
 * @date: 2023/5/23 23:04
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}
