package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.IItems;
import com.syy.hardware.entity.IItemsAttributeVal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IItemsAttributeValDao extends BaseMapper<IItemsAttributeVal> {

    void setItemsAttributeVal(List<IItemsAttributeVal> list);

    @Delete("delete from i_items_attribute_val where attribute_id =#{attribute_id}")
    Integer deleteByAttributeId(String attribute_id);
}
