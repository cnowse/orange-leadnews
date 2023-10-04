package com.orange.article;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.orange.article.service.ApArticleService;
import com.orange.file.service.FileStorageService;
import com.orange.model.article.pojo.ApArticle;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.article.mapper.ApArticleContentMapper;
import com.orange.model.article.pojo.ApArticleContent;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    @Autowired
    private ApArticleContentMapper articleContentMapper;

    @Autowired
    private ApArticleService articleService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void name() throws Exception {
        ApArticleContent apArticleContent = articleContentMapper.selectOne(
                Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, 1383827787629252610L));
        Map<String, Object> map = new HashMap<>();
        map.put("content", JSONArray.parseArray(apArticleContent.getContent()));
        Template template = configuration.getTemplate("article.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        InputStream is = new ByteArrayInputStream(stringWriter.toString().getBytes());

        //3.把html文件上传到minio中
        String path = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleId() + ".html", is);

        //4.修改ap_article表，保存static_url字段
        ApArticle article = new ApArticle();
        article.setId(apArticleContent.getArticleId());
        article.setStaticUrl(path);
        articleService.updateById(article);
    }

}
