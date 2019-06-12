package com.syy.hardware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.syy.hardware.entity.HAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HAttributeDao extends BaseMapper<HAttribute> {



}
