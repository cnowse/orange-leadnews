package com.orange.wemedia.controller.v1;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orange.model.wemedia.pojo.WmChannel;
import com.orange.wemedia.service.WmChannelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    private final WmChannelService wmChannelService;

    @GetMapping("/channels")
    public List<WmChannel> findAll() {
        return wmChannelService.list();
    }

}