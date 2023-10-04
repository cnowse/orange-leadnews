package com.orange.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.wemedia.dto.WmLoginDTO;
import com.orange.model.wemedia.pojo.WmUser;
import com.orange.model.wemedia.vo.LoginVO;

public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体端登录
     * 
     * @param dto 登录参数
     * @return 登陆成功用户数据
     */
    LoginVO login(WmLoginDTO dto);

}