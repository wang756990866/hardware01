package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName URole
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 14:16
 * @Version 1.0
 **/
@TableName("h_hardware")
@Data
@ApiModel(value = "项目名称的表")
public class HHardware extends Model<HHardware> implements Serializable {
    @ApiModelProperty(value = "物品产品 的ID")
    private String hardware_id;
    @ApiModelProperty(value = "物品产品 的名称")
    private String hardware_name;
    @ApiModelProperty(value = "项目 的id")
    private String items_id;
    @ApiModelProperty(value = "项目 的添加时间")
    private String hardware_date;

    @Override
    protected Serializable pkVal() {
        return this.hardware_id;
    }

}
