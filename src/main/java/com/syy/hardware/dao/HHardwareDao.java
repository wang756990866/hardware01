package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HAttribute;
import com.syy.hardware.entity.HAttributeVal;
import com.syy.hardware.entity.HHardware;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HHardwareDao extends BaseMapper<HHardware> {

    @Select("SELECT  iiav.items_id,it.items_name,it.items_branch,it.items_date,it.items_name,it.user_id,ia.attribute_name_val, iav.attribute_val,iiav.attribute_val_type,ia.attribute_name FROM i_items_attribute_val iiav left join  i_items it on iiav.items_id = it.items_id left join  i_attribute ia on iiav.attribute_id = ia.attribute_id  left join  i_attribute_val iav on iav.attribute_val_id = iiav.attribute_val_id where  iiav.items_id =#{itemsId}")
    List<Map<Object, Object>> getItemsById(String itemsId);

    @Select("SELECT  ia.attribute_id,iiav.items_id,it.items_name,it.items_branch,it.items_date,it.items_name,it.user_id,ia.attribute_name_val, iav.attribute_val,iiav.attribute_val_type,ia.attribute_name FROM i_items_attribute_val iiav left join  i_items it on iiav.items_id = it.items_id left join  i_attribute ia on iiav.attribute_id = ia.attribute_id  left join  i_attribute_val iav on iav.attribute_val_id = iiav.attribute_val_id where  it.items_branch =#{items_branch} and it.items_years=#{items_years}")
    List<Map<Object, Object>> getItemsAll(String items_branch, String items_years);

    List<Map<Object, Object>> getItemsByIdAndName(HashMap hashMap);

    List<HHardware> getItemsVal(HHardware items);

    List<Map<Object, Object>> getHardwaresByItemsId(String items_id);

    @Select("SELECT * FROM h_attribute_val Where attribute_id = #{classify}")
    List<HAttributeVal> getclassify(String classify);
    @Update("update h_hardware set hardware_name=#{hardwareName} where hardware_id=#{hardware_id}")
    int updateNameById(String hardwareName,String hardware_id);

    List<Map<Object, Object>> getHardwaresByIdAndName(String items_id,String hardware_id,String hardware_name);
}
