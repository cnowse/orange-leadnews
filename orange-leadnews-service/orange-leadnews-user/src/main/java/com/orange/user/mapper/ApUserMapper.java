package com.orange.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.model.user.pojo.ApUser;

@Mapper
public interface ApUserMapper extends BaseMapper<ApUser> {}
