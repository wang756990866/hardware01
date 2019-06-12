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
@TableName("i_attribute_val")
@Data
@ApiModel(value = "属性值的表")
public class IAttributeVal extends Model<IAttributeVal> implements Serializable {

    @ApiModelProperty(value = "属性值 的ID")
    private String attribute_val_id;
    @ApiModelProperty(value = "属性值 的内容")
    private String attribute_val;
    @ApiModelProperty(value = "属性 的ID")
    private String attribute_id;

    @ApiModelProperty(value = "属性为2的列表")
    @TableField(exist = false)
    private List<IAttributeVal> attributeList;

    @Override
    protected Serializable pkVal() {
        return this.attribute_val_id;
    }

}
