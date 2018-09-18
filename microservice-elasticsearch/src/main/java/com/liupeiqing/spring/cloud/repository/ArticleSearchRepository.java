package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/9/17 16:47
 *
 * 关键字     方法命名
 * And          findByNameAndPwd
 * Or             findByNameOrSex
 * Is              findById
 * Between   findByIdBetween
 * Like           findByNameLike
 * NotLike     findByNameNotLike
 * OrderBy    findByIdOrderByXDesc
 * Not           findByNameNot
 */
@Repository
public interface ArticleSearchRepository extends ElasticsearchRepository<Article,Long> {

    /**
     * 根据标题查询
     * @param title
     * @return
     */
    public List<Article> findByTitle(String title);

    /**
     * AND 查询
     * @param title
     * @param clickcount
     * @return
     */
    List<Article> findByTitleAndClickCount(String title,Integer clickcount);

    /**
     * OR 查询
     * @param title
     * @param clickcount
     * @return
     */
    List<Article> findByTitleOrClickCount(String title,Integer clickcount);

    /**
     * 根据文章内容进行分页
     *
     *      * 等同于下面代码
     *      * @Query("{\"bool\" : {\"must\" : {\"term\" : {\"content\" : \"?0\"}}}}")
     *      * Page<Article> findByContent(String content, Pageable pageable);
     *
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByContent(String content, Pageable pageable);

    /**
     * NOT 语句查询
     *
     * @param content
     * @param page
     * @return
     */
    Page<Article> findByContentNot(String content, Pageable page);
    /**
     * LIKE 语句查询
     *
     * @param content
     * @param page
     * @return
     */
    Page<Article> findByContentLike(String content, Pageable page);


}
