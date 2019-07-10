package com.syy.hardware.entity;

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
@TableName("h_hardware_attribute_val")
@Data
@ApiModel(value = "项目属性及属性值的关联表")
public class HHardwareAttributeVal implements Serializable {

    @ApiModelProperty(value = "关系 的ID")
    private String hid;
    @ApiModelProperty(value = "项目 的ID")
    private String hardware_id;
    @ApiModelProperty(value = "属性 的ID")
    private String attribute_id;
    @ApiModelProperty(value = "属性值 的ID")
    private String attribute_val_id;
    @ApiModelProperty(value = "属性 的类型")
    private String attribute_val_type;
    @ApiModelProperty(value = "属性 的类型")
    private String classify_id;

}
