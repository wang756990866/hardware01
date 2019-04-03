package com.syy.hardware.Service;

import com.syy.hardware.entity.User;

import java.util.List;

public interface UserService {

    List<User>  getUser(User user);
    void  insertUser(User user);
    Integer insert(User user);


}
