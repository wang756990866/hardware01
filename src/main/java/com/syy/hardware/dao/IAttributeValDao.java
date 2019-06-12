package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.IAttribute;
import com.syy.hardware.entity.IAttributeVal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IAttributeValDao extends BaseMapper<IAttributeVal> {

    @Select("select * from i_attribute_val where attribute_id = #{attribute_id}")
    List<IAttributeVal> selectValById(String attribute_id);

    void setattributeVal(List<IAttributeVal> attributeValList);
}
