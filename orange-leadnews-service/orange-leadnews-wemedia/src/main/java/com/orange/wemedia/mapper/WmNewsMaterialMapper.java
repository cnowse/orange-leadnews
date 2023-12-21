package com.orange.wemedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.model.wemedia.pojo.WmNewsMaterial;

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    void saveRelations(@Param("materialIds") List<Integer> materialIds, @Param("newsId") Integer newsId,
            @Param("type") Short type);

}