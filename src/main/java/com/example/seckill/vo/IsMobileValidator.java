package com.example.seckill.vo;

import com.example.seckill.utils.ValidatorUtil;
import com.example.seckill.validator.IsMobile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * @projectName: seckill
 * @package: com.example.seckill.vo
 * @className: IsMobileValidator
 * @author: zhn
 * @description: 手机号码校验规则，泛型的第一个参数是你实现的注解，第二个是类，和注解共同生效
 * @date: 2023/5/20 15:59
 * @version: 1.0
 */

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        // 是否必填
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果必填
        if(required) {
            // 校验
            return ValidatorUtil.isMobile(value);
        }
        // 非必填
        else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }
            else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
