package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeClassify;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HAttributeClassifyDao extends BaseMapper<HAttributeClassify> {

    @Select("select classify_code,count(*) as count from h_attribute_classify WHERE classify_code = #{stringPinYin} group by classify_code having count > 1;")
    ArrayList<Map> selectIntByCode(String stringPinYin);

    @Delete("delete from h_attribute_classify where classify_id =#{classify_id}")
    Integer deleteByClassify_id(String classify_id);
}
