package com.orange.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.common.dtos.ResponseResult;
import com.orange.model.user.dtos.LoginDto;
import com.orange.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {

    /**
     * app端登录功能
     * 
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

}
