package com.orange.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.orange.model.article.dto.ArticleHomeDTO;
import com.orange.model.article.pojo.ApArticle;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     * 根据 type 加载文章列表
     *
     * @param dto 参数
     * @param type 1：加载更多；2：加载最新
     * @return java.util.List<article.pojo.ApArticle>
     * @author Jeong Geol
     */
    List<ApArticle> listArticleByType(ArticleHomeDTO dto, Short type);

}
