package com.yat.unifiedloginverificationsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yat.unifiedloginverificationsystem.dto.ApprovedUserInfoDTO;
import com.yat.unifiedloginverificationsystem.dto.ApplyInfoDTO;
import com.yat.unifiedloginverificationsystem.entry.User;
import com.yat.unifiedloginverificationsystem.mapper.UserMapper;
import com.yat.unifiedloginverificationsystem.service.AdminService;
import com.yat.unifiedloginverificationsystem.utils.GoogleAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<ApplyInfoDTO> findRegister() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_approved", 0);
        List<ApplyInfoDTO> userList = userMapper.selectList(queryWrapper).stream().map(item -> {
            ApplyInfoDTO applyInfoDTO = ApplyInfoDTO.builder()
                    .uid(item.getUid())
                    .full_name(item.getCn())
                    .phone(item.getTelephoneNumber())
                    .applyTime(item.getApplyTime()).build();
            return applyInfoDTO;
        }).sorted(Comparator.comparing(ApplyInfoDTO::getApplyTime)).collect(Collectors.toList());
        return  userList;
    }

    @Override
    public String approve(Integer uid) {
        User user = userMapper.selectById(uid);
        user.setOu("2");
        user.setUserPassword("123456");
        ldapTemplate.create(user);
        user.setIsApproved(1);
        // 生成 Google 密钥
        String secretKey = GoogleAuthenticator.getSecretKey();
        user.setSecretKey(secretKey);
        userMapper.updateById(user);
        return user.getCn();
    }

    @Override
    public List<ApprovedUserInfoDTO> findAll() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_approved", 1);
        List<ApprovedUserInfoDTO> userList = userMapper.selectList(queryWrapper).stream().map(item -> {
            ApprovedUserInfoDTO approvedUserInfoDTO = ApprovedUserInfoDTO.builder()
                    .uid(item.getUid())
                    .full_name(item.getCn())
                    .phone(item.getTelephoneNumber())
                    .isBindTOTP(item.getIsBindTOTP()).build();
            return approvedUserInfoDTO;
        }).collect(Collectors.toList());
        return userList;
    }
}

