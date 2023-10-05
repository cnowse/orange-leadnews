package com.orange.wemedia.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.model.wemedia.dto.WmNewsPageReqDTO;
import com.orange.model.wemedia.pojo.WmNews;
import com.orange.wemedia.service.WmNewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class WmNewsController {

    private final WmNewsService wmNewsService;

    @PostMapping("/list")
    public Page<WmNews> listNews(@RequestBody WmNewsPageReqDTO dto) {
        dto.checkParam();
        return wmNewsService.listByCondition(dto);
    }

}