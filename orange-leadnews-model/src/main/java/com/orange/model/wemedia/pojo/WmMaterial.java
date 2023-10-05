package com.orange.model.wemedia.pojo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 素材表
 */
@Data
public class WmMaterial {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String url;

    private Short type;

    private Short isCollection;

    private LocalDateTime createdTime;

}
