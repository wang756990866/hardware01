package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CodeDao extends BaseMapper<Code> {

    @Select("select * from code where code_itemsId = #{items_id} and code_type = #{ClassName}")
    List<Code>  getCodeByClsaaAndItemsId(String items_id,String ClassName);

    @Select("select code_id from code where code_itemsId = #{items_id} and code_type = #{classify} and code_id LIKE CONCAT('%',#{queryString},'%') and code_states = #{code_states}")
    List<Code>  getCodeByName(String queryString,String items_id,String classify,String code_states);

    @Update("update code set code_states=#{code_states} where code_id =#{codeid}")
    int updateOneById(String codeid,String code_states);

    @Select("select * from code where code_id = #{codeid}")
    Code selectOneById(String codeid);
}
