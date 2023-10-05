package com.orange.wemedia.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.wemedia.pojo.WmMaterial;

public interface WmMaterialService extends IService<WmMaterial> {

    void uploadPicture(MultipartFile multipartFile);

}