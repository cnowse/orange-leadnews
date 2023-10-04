package com.orange.wemedia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.model.wemedia.pojo.WmMaterial;
import com.orange.wemedia.mapper.WmMaterialMapper;
import com.orange.wemedia.service.WmMaterialService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

}
