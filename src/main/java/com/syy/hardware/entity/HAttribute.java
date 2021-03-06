package com.syy.hardware.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName URole
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/3 14:16
 * @Version 1.0
 **/
@TableName("h_attribute")
@Data
@ApiModel(value = "属性名称的表")
public class HAttribute extends Model<HAttribute> implements Serializable {

    @ApiModelProperty(value = "属性 的名称")
    private String attribute_name;
    @ApiModelProperty(value = "属性 的ID")
    private String attribute_id;
    @ApiModelProperty(value = "属性 的代号")
    private String attribute_code;
    @ApiModelProperty(value = "属性 的类型")
    private String attribute_type;
    @ApiModelProperty(value = "属性 的类型")
    private String classify_id;

    @ApiModelProperty(value = "属性为2的值")
    @TableField(exist = false)
    private String attributeQVal;

    @ApiModelProperty(value = "属性为2的列表")
    @TableField(exist = false)
    private List<HAttributeVal> attributeList;

    @ApiModelProperty(value = "属性为1的ID")
    @TableField(exist = false)
    private String attributeValId;
    @Override
    protected Serializable pkVal() {
        return this.attribute_id;
    }

}
