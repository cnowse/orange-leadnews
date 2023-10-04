package com.orange.wemedia.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.model.wemedia.pojo.WmUser;

@Mapper
public interface WmUserMapper extends BaseMapper<WmUser> {}