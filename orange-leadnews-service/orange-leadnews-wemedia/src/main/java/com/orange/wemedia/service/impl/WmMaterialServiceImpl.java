package com.orange.wemedia.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.CustomException;
import com.orange.file.service.FileStorageService;
import com.orange.model.common.enums.AppHttpCodeEnum;
import com.orange.model.wemedia.dto.WmMaterialDTO;
import com.orange.model.wemedia.pojo.WmMaterial;
import com.orange.utils.thread.WmThreadLocalUtil;
import com.orange.wemedia.mapper.WmMaterialMapper;
import com.orange.wemedia.service.WmMaterialService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    private final FileStorageService fileStorageService;

    @Override
    public void uploadPicture(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getSize() == 0) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String originalFilename = multipartFile.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new CustomException(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        try {
            fileId = fileStorageService.uploadImgFile("", fileName + fileNameSuffix, multipartFile.getInputStream());
            log.info("上传文件成功 fileId={}", fileId);
        } catch (IOException e) {
            log.info("上传文件失败 fileId={}, msg={}", fileId, e.getMessage());
        }

        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(fileId);
        wmMaterial.setIsCollection((short)0);
        wmMaterial.setType((short)0);
        wmMaterial.setCreatedTime(LocalDateTime.now());
        this.save(wmMaterial);
    }

    @Override
    public Page<WmMaterial> listMaterialByCondition(WmMaterialDTO dto) {
        return this.lambdaQuery().eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId())
                .eq(dto.getIsCollection() != null, WmMaterial::getIsCollection, dto.getIsCollection())
                .orderByDesc(WmMaterial::getCreatedTime).page(new Page<>(dto.getPage(), dto.getSize()));

    }

}
