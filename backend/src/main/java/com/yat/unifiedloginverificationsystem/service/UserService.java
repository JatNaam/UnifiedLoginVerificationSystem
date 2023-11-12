package com.yat.unifiedloginverificationsystem.service;

import com.yat.unifiedloginverificationsystem.entry.User;

public interface UserService {


    /**
     * 注册待审核的用户
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 修改用户密码
     *
     * @param user
     * @return
     */
    void modifyPsw(User user);

    /**
     * 获取用户TOTP协议的密钥
     * @param phone
     * @return
     */
    String getSecretKey(String phone);

    /**
     * 开启无密码登录
     * @param phone
     */
    void bindTOTP(String phone);

    /**
     * 获取用户是否开启无密码登录
     * @param phone
     * @return
     */
    int getAu(String phone);
}
