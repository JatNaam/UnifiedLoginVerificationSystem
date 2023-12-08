package com.yat.unifiedloginverificationsystem.controller;

import com.yat.unifiedloginverificationsystem.commen.Code;
import com.yat.unifiedloginverificationsystem.commen.R;
import com.yat.unifiedloginverificationsystem.dto.RegisterInfoDTO;
import com.yat.unifiedloginverificationsystem.dto.UserInfoDTO;
import com.yat.unifiedloginverificationsystem.entry.User;
import com.yat.unifiedloginverificationsystem.service.UserService;
import com.yat.unifiedloginverificationsystem.utils.GoogleAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块
 *
 * @author 86183
 * @createTime 2023/10/2 17:05
 * @description
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户申请注册，待管理员审核
     *
     * @param registerInfoDTO
     * @return
     */
    @PostMapping("register")
    public R<Null> register(@RequestBody RegisterInfoDTO registerInfoDTO) {
        User user = new User();
        user.setSn(registerInfoDTO.getFirst_name());
        user.setCn(registerInfoDTO.getFull_name());
        user.setTelephoneNumber(registerInfoDTO.getPhone());
        boolean r = userService.register(user);
        if (r)
            return new R<>(Code.SUCCESS, "已发起申请", null);
        else return new R<>(Code.FAILED, "手机号或姓名已被占用", null);
    }

    @PostMapping("modifyPsw")
    public R<Null> modifyPsw(@RequestBody UserInfoDTO userInfoDTO) {
        User user = new User();
        user.setTelephoneNumber(userInfoDTO.getPhone());
        user.setCn(userInfoDTO.getFull_name());
        user.setSn(userInfoDTO.getFirst_name());
        user.setUserPassword(userInfoDTO.getPassword());
        user.setOu("2");
        userService.modifyPsw(user);
        return new R<>(Code.SUCCESS, null);
    }

    /**
     * 生成二维码，APP直接扫描绑定，两种方式任选一种
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("qrCode")
    public R<String> getQrcode(@RequestParam("phone") String phone) throws Exception {
        String logo = "http://139.9.44.141/resource/szu.png";
        String path = "/resource/qrCode/qr_" + phone + ".png";
        String _path = "http://139.9.44.141/" + path;
//        String path = "D:\\CodeProject\\temp\\UnifiedLoginVerificationSystem\\src\\main\\resources\\static\\img\\qrCode_" + phone + ".png";
//        String _path = "http://localhost:8081/static/img/qrCode_" + phone + ".png";
        String secretKey = userService.getSecretKey(phone);
        // 生成二维码内容
        String qrCodeText = GoogleAuthenticator.getQrCodeText(secretKey, phone, "");
        // 生成二维码输出
        new SimpleQrcodeGenerator().setRemoteLogo(logo).generate(qrCodeText).toFile(path);
        return new R<>(Code.SUCCESS, _path);
    }

    /**
     * 开启无密码登录
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("bindTOTP")
    public R<Integer> bindTOTP(@RequestParam String phone, @RequestParam String code) {
        String secretKey = userService.getSecretKey(phone);
        boolean success = GoogleAuthenticator.checkCode(secretKey, Long.parseLong(code), System.currentTimeMillis());
        if (success) {
            userService.bindTOTP(phone);
            return new R<>(Code.SUCCESS, "绑定成功", 1);
        } else return new R<>(Code.FAILED, "验证码错误", -1);
    }

    /**
     * 获取用户是否开启无密码登录
     *
     * @param phone
     * @return
     */
    @GetMapping("getAu")
    public R<Integer> getAu(@RequestParam String phone) {
        return new R<>(Code.SUCCESS, userService.getAu(phone));
    }
}

