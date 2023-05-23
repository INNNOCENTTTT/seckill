package com.example.seckill.vo;

import com.example.seckill.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @projectName: seckill
 * @package: com.example.seckill.vo
 * @className: LoginVo
 * @author: zhn
 * @description: 前端登录传输的数据
 * @date: 2023/5/20 14:52
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
