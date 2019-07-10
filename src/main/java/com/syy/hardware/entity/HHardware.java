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
    @ApiModelProperty(value = "硬件最后修改时间 的添加时间")
    private String hardware_date;
    @ApiModelProperty(value = "硬件最后修改人 的添加时间")
    private String user_id;
    @ApiModelProperty(value = "二维码状态 的类型")
    private String code_type;
    @ApiModelProperty(value = "硬件 的类型")
    private String classify_id;

    @Override
    protected Serializable pkVal() {
        return this.hardware_id;
    }

}
