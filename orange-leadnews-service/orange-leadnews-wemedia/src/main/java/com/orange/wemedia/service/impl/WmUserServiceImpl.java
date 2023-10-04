package com.orange.wemedia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.CustomException;
import com.orange.model.common.enums.AppHttpCodeEnum;
import com.orange.model.wemedia.dto.WmLoginDTO;
import com.orange.model.wemedia.pojo.WmUser;
import com.orange.model.wemedia.vo.LoginVO;
import com.orange.utils.common.AppJwtUtil;
import com.orange.wemedia.mapper.WmUserMapper;
import com.orange.wemedia.service.WmUserService;

@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Override
    public LoginVO login(WmLoginDTO dto) {
        WmUser wmUser = this.getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, dto.getName()));
        if (wmUser == null) {
            // 用户不存在
            throw new CustomException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        String salt = wmUser.getSalt();
        String pswd = dto.getPassword();
        pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
        if (!pswd.equals(wmUser.getPassword())) {
            // 密码校验未通过
            throw new CustomException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        wmUser.setSalt("");
        wmUser.setPassword("");
        return new LoginVO(AppJwtUtil.getToken(wmUser.getId()), wmUser);
    }

}