package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HAttribute;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HAttributeDao extends BaseMapper<HAttribute> {

    List<Map<Object, Object>> getHardwareAttribute();

    @Delete("delete from h_attribute where attribute_id = #{attribute_id}")
    int deleteByAttributeId(String attribute_id);

    @Select("select * from h_attribute where classify_id = #{classify_id}")
    List<HAttribute>   seletByClassifyId(String classify_id);

}
