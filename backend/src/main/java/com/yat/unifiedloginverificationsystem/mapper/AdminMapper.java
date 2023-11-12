package com.yat.unifiedloginverificationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yat.unifiedloginverificationsystem.entry.Admin;
import com.yat.unifiedloginverificationsystem.entry.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    List<User> getRegister();

}
