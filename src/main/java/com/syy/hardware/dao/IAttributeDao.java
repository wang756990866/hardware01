package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.IAttribute;
import com.syy.hardware.entity.IAttributeVal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IAttributeDao extends BaseMapper<IAttribute> {

    @Delete("delete from i_attribute where attribute_id =#{attribute_id}")
    int deleteByAttributeId(String attribute_id);

    @Update("update i_attribute set attribute_name_val =#{attribute_name_val} , attribute_name =#{attribute_name}, attribute_type = #{attribute_type}")
    Integer updateOne(IAttribute data1);

}
