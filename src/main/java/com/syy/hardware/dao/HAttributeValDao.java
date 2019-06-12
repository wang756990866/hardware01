package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HAttributeVal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HAttributeValDao extends BaseMapper<HAttributeVal> {

    @Select("select * from h_attribute_val where attribute_id = #{attribute_id}")
    List<HAttributeVal> selectValById(String attribute_id);

    void setattributeVal(List<HAttributeVal> attributeValList);

    @Update("update h_attribute_val set attribute_val=#{attribute_QVal} where attribute_val_id =#{attribute_val_id}")
    int updateOneById(String attribute_val_id,String attribute_QVal);
}
