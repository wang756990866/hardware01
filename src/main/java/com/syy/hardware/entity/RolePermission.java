package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RolePermission
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 19:47
 * @Version 1.0
 **/
@TableName("u_role_permission")
@Data
public class RolePermission  implements Serializable {

    private  String rid;
    private  String pid;


}
