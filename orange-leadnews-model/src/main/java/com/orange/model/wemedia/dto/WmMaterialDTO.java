package com.orange.model.wemedia.dto;

import com.orange.model.common.dto.PageRequestDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmMaterialDTO extends PageRequestDTO {

    /**
     * 0：收藏
     * 1：未收藏
     */
    private Short isCollection;

}
