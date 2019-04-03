package com.syy.hardware.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList();
    Integer insert(User user);
}
