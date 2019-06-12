package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName URole
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 14:16
 * @Version 1.0
 **/
@TableName("u_role")
@Data
public class URole extends Model<URole> implements Serializable {
    private String id;
    private String name;
    private String type;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
