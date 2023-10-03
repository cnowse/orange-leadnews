package com.orange.user.service.impl;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.CustomException;
import com.orange.model.common.enums.AppHttpCodeEnum;
import com.orange.model.user.dto.LoginDTO;
import com.orange.model.user.pojo.ApUser;
import com.orange.model.user.vo.LoginVO;
import com.orange.user.mapper.ApUserMapper;
import com.orange.user.service.ApUserService;
import com.orange.utils.common.AppJwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Override
    public LoginVO login(LoginDTO dto) {
        // 1.正常登录 用户名和密码
        if (StringUtils.isNotBlank(dto.getPhone()) && StringUtils.isNotBlank(dto.getPassword())) {
            // 1.1 根据手机号查询用户信息
            ApUser dbUser = this.getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbUser == null) {
                throw new CustomException(AppHttpCodeEnum.DATA_NOT_EXIST, "用户信息不存在");
            }

            // 1.2 比对密码
            String salt = dbUser.getSalt();
            String password = dto.getPassword();
            String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if (!pswd.equals(dbUser.getPassword())) {
                throw new CustomException(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            // 1.3 返回数据 jwt user
            String token = AppJwtUtil.getToken(dbUser.getId());
            dbUser.setSalt(StringUtils.EMPTY);
            dbUser.setPassword(StringUtils.EMPTY);
            return new LoginVO(token, dbUser);
        }
        // 2.游客登录
        return new LoginVO(AppJwtUtil.getToken(0));
    }

}
