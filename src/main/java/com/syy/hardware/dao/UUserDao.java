package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.User;
import com.syy.hardware.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UUserDao  extends BaseMapper<User> {
    User findByName(String name);

    List<UserRole>  selectByUid(String uid);
}
