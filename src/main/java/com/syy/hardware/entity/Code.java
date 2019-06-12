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
@TableName("code")
@Data
@ApiModel(value = "二维码的表")
public class Code extends Model<Code> implements Serializable {

    @ApiModelProperty(value = "二维码 的ID")
    private String code_id;
    @ApiModelProperty(value = "二维码 的名称")
    private String code_name;
    @ApiModelProperty(value = "二维码 的图片地址")
    private String code_url;
    @ApiModelProperty(value = "二维码 的时间")
    private String code_date;
    @ApiModelProperty(value = "二维码 的所属分类")
    private String code_type;
    @ApiModelProperty(value = "二维码 的状态")
    private String code_states;
    @ApiModelProperty(value = "所属的项目 ID")
    private String code_itemsId;

    @ApiModelProperty(value = "属性为2的列表")
    @TableField(exist = false)
    private String attributeQVal;

    @Override
    protected Serializable pkVal() {
        return this.code_id;
    }

}
