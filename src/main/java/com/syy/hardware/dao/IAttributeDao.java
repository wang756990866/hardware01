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
public interface IAttributeDao extends BaseMapper<IAttribute> {



}
