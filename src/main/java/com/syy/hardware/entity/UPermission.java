package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UPermission
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 14:30
 * @Version 1.0
 **/
@TableName("u_permission")
@Data
public class UPermission  extends Model<UPermission> implements Serializable{

    private  String id;
    private  String url;
    private  String name;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
