package com.orange.wemedia.convert;

import org.mapstruct.Mapper;

import com.orange.model.wemedia.dto.WmNewsDTO;
import com.orange.model.wemedia.pojo.WmNews;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WmNewsConvert {

    @Mapping(target = "images", ignore = true)
    WmNews fromWmNewsDTO(WmNewsDTO dto);

}
