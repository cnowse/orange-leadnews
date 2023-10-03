package com.orange.model.user.vo;

import com.orange.model.user.pojo.ApUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {

    private String token;
    private ApUser user;

    public LoginVO(String token) {
        this.token = token;
    }

}
