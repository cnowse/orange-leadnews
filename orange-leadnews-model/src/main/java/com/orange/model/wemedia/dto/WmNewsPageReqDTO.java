package com.orange.model.wemedia.dto;

import java.time.LocalDateTime;

import com.orange.model.common.dto.PageRequestDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WmNewsPageReqDTO extends PageRequestDTO {

    /**
     * 状态
     */
    private Short status;

    /**
     * 开始时间
     */
    private LocalDateTime beginPubDate;

    /**
     * 结束时间
     */
    private LocalDateTime endPubDate;

    /**
     * 所属频道ID
     */
    private Integer channelId;

    /**
     * 关键字
     */
    private String keyword;

}