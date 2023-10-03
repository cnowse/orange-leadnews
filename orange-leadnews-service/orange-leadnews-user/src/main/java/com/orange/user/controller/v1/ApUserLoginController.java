package com.orange.user.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.model.user.dto.LoginDTO;
import com.orange.model.user.vo.LoginVO;
import com.orange.user.service.ApUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
@Api(value = "app端用户登录", tags = "app端用户登录")
public class ApUserLoginController {

    private final ApUserService apUserService;

    @ApiOperation("用户登录")
    @PostMapping("/login_auth")
    public LoginVO login(@RequestBody LoginDTO dto) {
        return apUserService.login(dto);
    }

}
