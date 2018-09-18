package com.liupeiqing.spring.cloud.service;

import com.liupeiqing.spring.cloud.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/9/17 17:01
 */
public interface ArticleService {

    /**
     * 新增文章信息
     * @param article
     * @return
     */
    Long saveArticle(Article article);

    /**
     * 根据关键词搜索
     * @param pageable
     * @param searchContent
     * @return
     */
    List<Article> searchArticle(Pageable pageable, String searchContent);

    Article getOne(Long id);
}
