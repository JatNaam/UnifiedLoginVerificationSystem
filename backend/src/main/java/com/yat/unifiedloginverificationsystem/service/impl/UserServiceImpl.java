package com.yat.unifiedloginverificationsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yat.unifiedloginverificationsystem.dto.UserInfoDTO;
import com.yat.unifiedloginverificationsystem.entry.User;
import com.yat.unifiedloginverificationsystem.mapper.UserMapper;
import com.yat.unifiedloginverificationsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getTelephoneNumber()).or().eq("cn", user.getCn());
        User _user = userMapper.selectOne(queryWrapper);
        if (_user != null)
            return false;
        userMapper.insert(user);
        return true;
    }

    @Override
    public void modifyPsw(User user) {
        ldapTemplate.update(user);
    }

    @Override
    public String getSecretKey(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return userMapper.selectOne(queryWrapper).getSecretKey();
    }

    @Override
    public void bindTOTP(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = userMapper.selectOne(queryWrapper);
        user.setIsBindTOTP(1);
        userMapper.updateById(user);
    }

    @Override
    public int getAu(String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return userMapper.selectOne(queryWrapper).getIsBindTOTP();
    }
}
