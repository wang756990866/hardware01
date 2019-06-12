package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.IItems;
import com.syy.hardware.entity.IItemsAttributeVal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IItemsAttributeValDao extends BaseMapper<IItemsAttributeVal> {

    void setItemsAttributeVal(List<IItemsAttributeVal> list);
}
