package com.orange.wemedia.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orange.wemedia.service.WmMaterialService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    private final WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    public void uploadPicture(MultipartFile multipartFile) {
        wmMaterialService.uploadPicture(multipartFile);
    }
}
