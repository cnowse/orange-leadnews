package com.orange.article.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.article.mapper.ApArticleMapper;
import com.orange.article.service.ApArticleService;
import com.orange.model.article.dto.ArticleHomeDTO;
import com.orange.model.article.pojo.ApArticle;

@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Override
    public List<ApArticle> listArticleByType(ArticleHomeDTO dto, Short type) {
        return baseMapper.listArticleByType(dto, type);
    }

}