package com.yat.unifiedloginverificationsystem.controller;

import com.yat.unifiedloginverificationsystem.commen.Code;
import com.yat.unifiedloginverificationsystem.commen.R;
import com.yat.unifiedloginverificationsystem.dto.ApprovedUserInfoDTO;
import com.yat.unifiedloginverificationsystem.dto.ApplyInfoDTO;
import com.yat.unifiedloginverificationsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员模块
 *
 * @author 86183
 * @createTime 2023/10/2 15:05
 * @description
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取注册申请
     *
     * @return
     */
    @GetMapping("getRegister")
    public R<List<ApplyInfoDTO>> getRegister() {
        List<ApplyInfoDTO> userList = adminService.findRegister();
        return new R<>(Code.SUCCESS, userList);
    }

    /**
     * 管理员通过用户的注册申请
     *
     * @param uid
     * @return
     */
    @PostMapping("approve")
    public R<String> approve(@RequestParam Integer uid) {
        return new R<>(Code.SUCCESS, adminService.approve(uid));
    }

    /**
     * 获取所有已通过审核的用户
     *
     * @return
     */
    @GetMapping("getAll")
    public R<List<ApprovedUserInfoDTO>> getAll() {
        List<ApprovedUserInfoDTO> userList = adminService.findAll();
        return new R<>(Code.SUCCESS, userList);
    }

}

