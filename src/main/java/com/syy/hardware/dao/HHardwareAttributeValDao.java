package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HHardwareAttributeVal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HHardwareAttributeValDao extends BaseMapper<HHardwareAttributeVal> {

    void setItemsAttributeVal(List<HHardwareAttributeVal> list);

    @Update("update h_hardware_attribute_val set attribute_val_id=#{attribute_QVal} where hardware_id=#{hardware_id} and attribute_id =#{attribute_id}")
    int updateAttributeValId(String attribute_QVal,String hardware_id,String attribute_id);

    @Select("select attribute_val_id from h_hardware_attribute_val where hardware_id = #{hardware_id} and attribute_id =#{attribute_id}")
    HHardwareAttributeVal getHhavByHidAndAid(String hardware_id ,String attribute_id);

    @Delete("delete from h_hardware_attribute_val where attribute_id = #{attribute_id}")
    int deleteByHardwareId(String attribute_id);
}
