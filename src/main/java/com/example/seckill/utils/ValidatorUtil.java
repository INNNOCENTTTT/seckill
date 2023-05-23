package com.example.seckill.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName: seckill
 * @package: com.example.seckill.utils
 * @className: ValidatorUtil
 * @author: zhn
 * @description: 手机号码校验
 * @date: 2023/5/20 15:13
 * @version: 1.0
 */
public class ValidatorUtil {
    private static final Pattern mobile_patten = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_patten.matcher(mobile);
        return matcher.matches();
    }
}
