package com.yat.unifiedloginverificationsystem.service;

import com.yat.unifiedloginverificationsystem.entry.User;

public interface LoginService {

    /**
     * 密码登录
     *
     * @param ou 账号类型
     * @param telephoneNumber 手机号
     * @return {@link Object}
     */
    Object loginByPassword(String ou, String telephoneNumber);

    /**
     * 验证码登录
     *
     * @param telephoneNumber 手机号
     * @return {@link Object}
     */
    User loginByCode(String telephoneNumber);
}
