package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserRole
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 19:40
 * @Version 1.0
 **/
@TableName("u_user_role")
@Data
public class UserRole   implements Serializable {

    private String uid;
    private String rid;


}
