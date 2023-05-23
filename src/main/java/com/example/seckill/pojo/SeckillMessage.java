package com.example.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @projectName: seckill
 * @package: com.example.seckill.pojo
 * @className: SeckillMessage
 * @author: zhn
 * @description: 秒杀信息
 * @date: 2023/5/28 18:19
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodsId;

}
