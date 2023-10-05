package com.orange.wemedia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.model.wemedia.dto.WmNewsPageReqDTO;
import com.orange.model.wemedia.pojo.WmNews;
import com.orange.utils.thread.WmThreadLocalUtil;
import com.orange.wemedia.mapper.WmNewsMapper;
import com.orange.wemedia.service.WmNewsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Override
    public Page<WmNews> listByCondition(WmNewsPageReqDTO dto) {
        return this.lambdaQuery().like(StringUtils.hasText(dto.getKeyword()), WmNews::getTitle, dto.getKeyword())
                .eq(dto.getStatus() != null, WmNews::getStatus, dto.getStatus())
                .eq(dto.getChannelId() != null, WmNews::getChannelId, dto.getChannelId())
                .eq(WmNews::getUserId, WmThreadLocalUtil.getUser().getId())
                .between(dto.getBeginPubDate() != null && dto.getEndPubDate() != null,
                        WmNews::getPublishTime, dto.getBeginPubDate(), dto.getEndPubDate())
                .orderByDesc(WmNews::getCreatedTime).page(new Page<>(dto.getPage(), dto.getSize()));
    }

}
