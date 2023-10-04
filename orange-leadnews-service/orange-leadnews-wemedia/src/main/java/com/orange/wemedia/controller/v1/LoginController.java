package com.orange.wemedia.controller.v1;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.model.wemedia.dto.WmLoginDTO;
import com.orange.model.wemedia.vo.LoginVO;
import com.orange.wemedia.service.WmUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final WmUserService wmUserService;

    @PostMapping("/in")
    public LoginVO login(@RequestBody @Validated WmLoginDTO dto) {
        return wmUserService.login(dto);
    }

}
