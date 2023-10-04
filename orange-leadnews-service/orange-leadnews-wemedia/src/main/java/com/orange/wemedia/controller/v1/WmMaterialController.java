package com.orange.wemedia.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @PostMapping("/upload_picture")
    public void uploadPicture(MultipartFile multipartFile) {

    }
}
