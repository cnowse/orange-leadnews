package com.orange.model.wemedia.vo;

import com.orange.model.wemedia.pojo.WmUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    private String token;
    private WmUser user;

}
