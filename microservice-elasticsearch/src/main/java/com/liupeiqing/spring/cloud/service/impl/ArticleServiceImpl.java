package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.domain.Article;
import com.liupeiqing.spring.cloud.repository.ArticleSearchRepository;
import com.liupeiqing.spring.cloud.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/9/17 17:05
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Override
    public Long saveArticle(Article article) {
        Article article1 = this.articleSearchRepository.save(article);
        return article1.getId();
    }

    @Override
    public List<Article> searchArticle(Pageable pageable, String searchContent) {
        return null;
    }

    @Override
    public Article getOne(Long id) {
        return null;
    }
}
