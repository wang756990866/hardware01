package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * @ClassName User
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 16:10
 * @Version 1.0
 *
@Builder*/
@TableName("u_user")
@Data
public class User extends Model<User>  implements Serializable {

    //用户id
    private  String id;
    //用户名称
    private  String nickname;
    //邮箱
    private  String email;
    //用户密码
    private  String pswd;

    //创建时间
    private Date create_time;

    //最后登录时间
    private Date last_login_time;

    //状态
    private Integer  status;

    //用户的角色集合
    @TableField(exist = false)
    private List<String>  RoleStrlist;

    //用户的权限集合
    @TableField(exist = false)
    private List<String> perminsStrlist;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
