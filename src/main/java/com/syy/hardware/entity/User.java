package com.syy.hardware.entity;

import lombok.Data;

/**
 * @ClassName User
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 16:10
 * @Version 1.0
 *
@Builder*/
@Data
public class User {
    //用户id
    public String userId;
    //用户名称
    public String userName;
    //d登录名
    public String loginVal;
    //用户性别
    public String password;
}
