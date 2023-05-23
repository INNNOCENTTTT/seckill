package com.example.seckill.exception;

import com.example.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: seckill
 * @package: com.example.seckill.exception
 * @className: GlobalException
 * @author: zhn
 * @description: 全局异常
 * @date: 2023/5/21 19:47
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
