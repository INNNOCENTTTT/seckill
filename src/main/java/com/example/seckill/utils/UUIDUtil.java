package com.example.seckill.utils;

/**
 * @projectName: seckill
 * @package: com.example.seckill.utils
 * @className: UUIDUtil
 * @author: zhn
 * @description: 生成UUID
 * @date: 2023/5/21 20:31
 * @version: 1.0
 */
import java.util.UUID;
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}