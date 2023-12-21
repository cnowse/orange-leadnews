package com.orange.wemedia.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.wemedia.dto.WmNewsDTO;
import com.orange.model.wemedia.dto.WmNewsPageReqDTO;
import com.orange.model.wemedia.pojo.WmNews;

public interface WmNewsService extends IService<WmNews> {

    Page<WmNews> listByCondition(WmNewsPageReqDTO dto);

    /**
     * 发布文章或保存草稿
     * 
     * @param dto 数据
     */
    void submitNews(WmNewsDTO dto);

}
