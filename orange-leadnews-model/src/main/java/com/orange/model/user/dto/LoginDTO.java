package com.orange.model.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户登录数据")
public class LoginDTO {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
