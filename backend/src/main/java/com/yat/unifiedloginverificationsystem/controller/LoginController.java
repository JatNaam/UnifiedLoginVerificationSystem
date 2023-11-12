package com.yat.unifiedloginverificationsystem.controller;

import com.yat.unifiedloginverificationsystem.commen.Code;
import com.yat.unifiedloginverificationsystem.commen.R;
import com.yat.unifiedloginverificationsystem.dto.LoginInfoDTO;
import com.yat.unifiedloginverificationsystem.dto.UserInfoDTO;
import com.yat.unifiedloginverificationsystem.entry.Admin;
import com.yat.unifiedloginverificationsystem.entry.User;
import com.yat.unifiedloginverificationsystem.service.LoginService;
import com.yat.unifiedloginverificationsystem.service.UserService;
import com.yat.unifiedloginverificationsystem.utils.GoogleAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录验证模块
 *
 * @author 86183
 * @createTime 2023/10/2 16:05
 * @description
 */
@RestController
@Slf4j
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    /**
     * 密码登录
     *
     * @param user
     * @return
     */
    @PostMapping("psw")
    public R<UserInfoDTO> loginByPassword(@RequestBody LoginInfoDTO user) {
        boolean success = false;
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        Object account = loginService.loginByPassword(user.getType(), user.getPhone());
        if (account == null)
            return new R<>(Code.FAILED, "账号不存在", null);
        else {
            // ascii转码
            StringBuilder asciiStr = new StringBuilder();
            for (int i = 0; i < user.getPassword().length(); i++)
                asciiStr.append((int) user.getPassword().charAt(i)).append(",");
            String entryText = asciiStr.substring(0, asciiStr.length() - 1);
            if (user.getType().equals("1")) {
                Admin admin = (Admin) account;
                if (admin.getUserPassword().equals(entryText)) {
                    success = true;
                    userInfoDTO.setFirst_name(admin.getSn());
                    userInfoDTO.setFull_name(admin.getCn());
                    userInfoDTO.setPassword(admin.getUserPassword());
                    userInfoDTO.setPhone(admin.getTelephoneNumber());
                    userInfoDTO.setToken(admin.getTelephoneNumber());
                }
            } else {
                User _user = (User) account;
                if (userService.getAu(_user.getTelephoneNumber()) == 1) {
                    return new R<>(Code.SUCCESS, "请进行二次验证", null);
                } else if (_user.getUserPassword().equals(entryText)) {
                    success = true;
                    userInfoDTO.setFirst_name(_user.getSn());
                    userInfoDTO.setFull_name(_user.getCn());
                    userInfoDTO.setPassword(_user.getUserPassword());
                    userInfoDTO.setPhone(_user.getTelephoneNumber());
                    userInfoDTO.setToken(_user.getTelephoneNumber());
                }
            }
        }
        if (success) {
            return new R<>(Code.SUCCESS, "登录成功", userInfoDTO);
        } else return new R<>(Code.FAILED, "密码错误", null);
    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("code")
    public R<UserInfoDTO> loginByCode(@RequestParam String phone, @RequestParam String code) {
        String secretKey = userService.getSecretKey(phone);
        boolean success = GoogleAuthenticator.checkCode(secretKey,
                Long.parseLong(code), System.currentTimeMillis());
        if (success) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            User user = loginService.loginByCode(phone);
            userInfoDTO.setFirst_name(user.getSn());
            userInfoDTO.setFull_name(user.getCn());
            userInfoDTO.setPassword(user.getUserPassword());
            userInfoDTO.setPhone(user.getTelephoneNumber());
            userInfoDTO.setToken(user.getTelephoneNumber());
            return new R<>(Code.SUCCESS, "登录成功", userInfoDTO);
        } else return new R<>(Code.FAILED, "验证码错误", null);
    }

}
