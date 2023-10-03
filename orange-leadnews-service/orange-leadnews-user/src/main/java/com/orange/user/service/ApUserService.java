package com.orange.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.user.dto.LoginDTO;
import com.orange.model.user.pojo.ApUser;
import com.orange.model.user.vo.LoginVO;

public interface ApUserService extends IService<ApUser> {

    /**
     * app端登录功能
     * 
     * @param dto 登录数据
     */
    LoginVO login(LoginDTO dto);

}
