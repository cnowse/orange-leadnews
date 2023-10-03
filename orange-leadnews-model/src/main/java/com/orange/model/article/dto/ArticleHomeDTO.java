package com.orange.model.article.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.orange.model.common.constant.ArticleConstant;

import lombok.Data;

@Data
public class ArticleHomeDTO {

    /** 最大时间 */
    private LocalDateTime maxBehotTime = LocalDateTime.now();
    /** 最小时间 */
    private LocalDateTime minBehotTime = LocalDateTime.now();
    /** 分页size */
    @NotNull(message = "分页值不能为空")
    @Max(value = 50, message = "分页最大值不能超过50")
    @Min(value = 1, message = "分页最小值不能低于1")
    private Integer size;
    /** 频道ID */
    private String tag = ArticleConstant.DEFAULT_TAG;

}