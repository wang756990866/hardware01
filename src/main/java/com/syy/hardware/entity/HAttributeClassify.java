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
@TableName("h_attribute_classify")
@Data
@ApiModel(value = "二维码的表")
public class HAttributeClassify extends Model<HAttributeClassify> implements Serializable {

    @ApiModelProperty(value = " ID")
    private String classify_id;
    @ApiModelProperty(value = " 名称")
    private String classify_name;
    @ApiModelProperty(value = "代号")
    private String classify_code;

    @ApiModelProperty(value = "更改值")
    @TableField(exist = false)
    private String attributeQVal;

    @ApiModelProperty(value = "该分类属性的列表")
    @TableField(exist = false)
    private List<HAttribute> attributeList;

    @Override
    protected Serializable pkVal() {
        return this.classify_id;
    }

}
