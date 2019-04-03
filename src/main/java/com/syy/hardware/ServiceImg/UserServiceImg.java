package com.syy.hardware.ServiceImg;

import com.syy.hardware.Service.UserService;
import com.syy.hardware.dao.UserMapper;
import com.syy.hardware.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ServiceImg
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 18:35
 * @Version 1.0
 **/
@Service
public class UserServiceImg implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUser(User user) {

        return null;
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public Integer insert(User user) {

        Integer insert = userMapper.insert(user);
        return insert;
    }
}
