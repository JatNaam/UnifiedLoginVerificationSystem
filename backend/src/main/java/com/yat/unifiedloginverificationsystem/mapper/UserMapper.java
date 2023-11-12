package com.yat.unifiedloginverificationsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yat.unifiedloginverificationsystem.entry.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
