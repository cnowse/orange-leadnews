package com.orange.wemedia.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.CustomException;
import com.orange.model.common.constant.WemediaConstants;
import com.orange.model.wemedia.dto.WmNewsDTO;
import com.orange.model.wemedia.dto.WmNewsPageReqDTO;
import com.orange.model.wemedia.pojo.WmMaterial;
import com.orange.model.wemedia.pojo.WmNews;
import com.orange.model.wemedia.pojo.WmNewsMaterial;
import com.orange.utils.thread.WmThreadLocalUtil;
import com.orange.wemedia.convert.WmNewsConvert;
import com.orange.wemedia.mapper.WmMaterialMapper;
import com.orange.wemedia.mapper.WmNewsMapper;
import com.orange.wemedia.mapper.WmNewsMaterialMapper;
import com.orange.wemedia.service.WmNewsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    private final WmNewsConvert convert;
    private final WmMaterialMapper wmMaterialMapper;
    private final WmNewsMaterialMapper wmNewsMaterialMapper;

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

    @Override
    public void submitNews(WmNewsDTO dto) {
        WmNews wmNews = convert.fromWmNewsDTO(dto);
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            String imgStr = dto.getImages().stream().map(String::valueOf).collect(Collectors.joining(","));
            wmNews.setImages(imgStr);
        }
        if (dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)) {
            wmNews.setType(null);
        }
        this.saveOrUpdateWmNews(wmNews);

        if (dto.getStatus().equals(WmNews.Status.NORMAL.getCode())) {
            return;
        }

        // 获取到文章内容中的图片信息
        List<String> materials = this.extractUrlInfo(dto.getContent());
        // 不是草稿，保存文章内容图片与素材的关系
        this.saveRelativeInfoForContent(materials, wmNews.getId());
        // 不是草稿，保存文章封面图片与素材的关系，如果当前布局是自动，需要匹配封面图片
        this.saveRelativeInfoForCover(dto.getImages(), wmNews, materials);
    }

    /**
     * 保存或修改文章
     *
     * @param wmNews 文章
     * @author Jeong Geol
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(LocalDateTime.now());
        wmNews.setSubmitedTime(LocalDateTime.now());
        // 默认上架
        wmNews.setEnable((short)1);
        if (wmNews.getId() == null) {
            this.save(wmNews);
        } else {
            wmNewsMaterialMapper
                    .delete(Wrappers.lambdaUpdate(WmNewsMaterial.class).eq(WmNewsMaterial::getNewsId, wmNews.getId()));
        }
    }

    /**
     * 提取文章内容中的图片信息
     * 
     * @param content 文章内容
     * @return 文章中图片 url
     */
    private List<String> extractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();
        List<Map<String, String>> mapsList =
                JSON.parseObject(content, new TypeReference<List<Map<String, String>>>() {});
        mapsList.forEach(map -> {
            if (map.get("type").equals("image")) {
                String imgUrl = map.get("value");
                materials.add(imgUrl);
            }
        });
        return materials;
    }

    /**
     * 处理文章内容图片与素材的关系
     * 
     * @param materials 文章中图片信息
     * @param newsId 文章 id
     */
    private void saveRelativeInfoForContent(List<String> materials, Integer newsId) {
        saveRelativeInfo(materials, newsId, WemediaConstants.WM_CONTENT_REFERENCE);
    }

    /**
     * 第一个功能：如果当前封面类型为自动，则设置封面类型的数据 匹配规则： 1，如果内容图片大于等于1，小于3 单图 type 1 2，如果内容图片大于等于3
     * 多图 type 3 3，如果内容没有图片，无图 type 0
     *
     * 第二个功能：保存封面图片与素材的关系
     * 
     * @param images 图片 url
     * @param wmNews 文章实体
     * @param materials 文章中图片 url
     */
    private void saveRelativeInfoForCover(List<String> images, WmNews wmNews, List<String> materials) {
        // 封面类型手动设置
        if (wmNews.getType() != null) {
            this.saveRelativeInfo(images, wmNews.getId(), WemediaConstants.WM_COVER_REFERENCE);
            return;
        }
        // 如果当前封面类型为自动，则设置封面类型的数据
        if (materials.size() >= 3) {
            // 多图
            wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
            images = materials.stream().limit(3).collect(Collectors.toList());
        } else if (!materials.isEmpty()) {
            // 单图
            wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
            images = materials.stream().limit(1).collect(Collectors.toList());
        } else {
            // 无图
            wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
        }
        // 修改文章
        if (images != null && !images.isEmpty()) {
            String imgStr = images.stream().map(String::valueOf).collect(Collectors.joining(","));
            wmNews.setImages(imgStr);
        }
        this.updateById(wmNews);
    }

    /**
     * 保存文章图片与素材的关系到数据库中
     * 
     * @param materials 文章中图片 url
     * @param newsId 文章 id
     * @param type 引用类型。0：内容引用；1：主图引用
     */
    private void saveRelativeInfo(List<String> materials, Integer newsId, Short type) {
        if (materials != null && !materials.isEmpty()) {
            // 通过图片的url查询素材的id
            List<WmMaterial> dbMaterials = wmMaterialMapper
                    .selectList(Wrappers.lambdaQuery(WmMaterial.class).in(WmMaterial::getUrl, materials));
            // 判断素材是否有效
            if (dbMaterials == null || dbMaterials.isEmpty()) {
                // 手动抛出异常 第一个功能：能够提示调用者素材失效了，第二个功能，进行数据的回滚
                // throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }
            if (materials.size() != dbMaterials.size()) {
                // throw new CustomException(AppHttpCodeEnum.MATERIASL_REFERENCE_FAIL);
            }
            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());
            // 批量保存
            wmNewsMaterialMapper.saveRelations(idList, newsId, type);
        }
    }

}
