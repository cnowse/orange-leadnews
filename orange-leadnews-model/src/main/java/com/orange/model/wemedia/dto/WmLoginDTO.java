package com.orange.model.wemedia.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WmLoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
