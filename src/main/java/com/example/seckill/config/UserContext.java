package com.example.seckill.config;

import com.example.seckill.pojo.User;

/**
 * @projectName: seckill
 * @package: com.example.seckill.config
 * @className: UserContext
 * @author: zhn
 * @description: 多线程
 * @date: 2023/5/29 22:58
 * @version: 1.0
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
