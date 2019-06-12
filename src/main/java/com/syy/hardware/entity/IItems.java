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
@TableName("i_items")
@Data
@ApiModel(value = "项目名称的表")
public class IItems extends Model<IItems> implements Serializable {
    @ApiModelProperty(value = "项目 的ID")
    private String items_id;
    @ApiModelProperty(value = "项目 的名称")
    private String items_name;
    @ApiModelProperty(value = "项目所属技术部")
    private String items_branch;
    @ApiModelProperty(value = "项目 的创建的时间")
    private String items_date;
    @ApiModelProperty(value = "项目 的创建者")
    private String user_id;
    @ApiModelProperty(value = "项目 的所在时间")
    private String items_years;

    @Override
    protected Serializable pkVal() {
        return this.items_id;
    }

}
