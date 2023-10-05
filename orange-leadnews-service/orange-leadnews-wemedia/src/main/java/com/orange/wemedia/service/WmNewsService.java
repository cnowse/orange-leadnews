package com.orange.wemedia.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.model.wemedia.dto.WmNewsPageReqDTO;
import com.orange.model.wemedia.pojo.WmNews;

public interface WmNewsService extends IService<WmNews> {

    Page<WmNews> listByCondition(WmNewsPageReqDTO dto);

}
