package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.IItems;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface IItemsDao extends BaseMapper<IItems> {

    @Select("SELECT  iiav.items_id,it.items_name,it.items_branch,it.items_date,it.items_name,it.user_id,ia.attribute_name_val, iav.attribute_val,iiav.attribute_val_type,ia.attribute_name FROM i_items_attribute_val iiav left join  i_items it on iiav.items_id = it.items_id left join  i_attribute ia on iiav.attribute_id = ia.attribute_id  left join  i_attribute_val iav on iav.attribute_val_id = iiav.attribute_val_id where  iiav.items_id =#{itemsId}")
    List<Map<Object, Object>> getItemsById(String itemsId);

    @Select("SELECT  ia.attribute_id,iiav.items_id,it.items_name,it.items_branch,it.items_date,it.items_name,it.user_id,ia.attribute_name_val, iav.attribute_val,iiav.attribute_val_type,ia.attribute_name FROM i_items_attribute_val iiav left join  i_items it on iiav.items_id = it.items_id left join  i_attribute ia on iiav.attribute_id = ia.attribute_id  left join  i_attribute_val iav on iav.attribute_val_id = iiav.attribute_val_id where  it.items_branch =#{items_branch} and it.items_years=#{items_years}")
    List<Map<Object, Object>> getItemsAll(String items_branch,String items_years);

    //@Select("SELECT  ia.attribute_id,iiav.items_id,it.items_name,it.items_branch,it.items_date,it.items_name,it.user_id,ia.attribute_name_val, iav.attribute_val,iiav.attribute_val_type,ia.attribute_name FROM i_items_attribute_val iiav left join  i_items it on iiav.items_id = it.items_id left join  i_attribute ia on iiav.attribute_id = ia.attribute_id  left join  i_attribute_val iav on iav.attribute_val_id = iiav.attribute_val_id where  it.items_branch =#{items_branch} and it.items_years=#{items_years} and  it.items_branch =#{items_id} and  it.items_name like CONCAT('%',#{items_name},'%')")
    List<Map<Object, Object>> getItemsByIdAndName(HashMap hashMap);

    List<IItems> getItemsVal(IItems items);

    @Select("SELECT * from i_items where items_id = #{itemsId}")
    IItems getOneItemsById(String itemsId);

    @Delete("delete from i_items where items_id =#{items_id}")
    Integer deleteByItemsId(String items_id);

    @Update("update i_items set items_name=#{attribute_QVal} where items_id =#{items_id}")
    Integer updateNameById(String attribute_QVal,String items_id);
}
