package com.yat.unifiedloginverificationsystem.service;

import com.yat.unifiedloginverificationsystem.dto.ApprovedUserInfoDTO;
import com.yat.unifiedloginverificationsystem.dto.ApplyInfoDTO;
import com.yat.unifiedloginverificationsystem.entry.User;

import java.util.List;

public interface AdminService {

    /**
     * 获取注册申请
     * @return
     */
    List<ApplyInfoDTO> findRegister();

    /**
     * 通过注册新用户
     *
     * @param uid
     * @return
     */
    String approve(Integer uid);

    /**
     * 获取所有已通过审核的用户
     *
     * @return list {@link User}
     */
    List<ApprovedUserInfoDTO> findAll();

}

