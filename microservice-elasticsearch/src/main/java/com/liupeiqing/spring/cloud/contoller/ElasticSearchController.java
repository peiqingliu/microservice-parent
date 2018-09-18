package com.liupeiqing.spring.cloud.contoller;

import com.google.common.collect.Lists;
import com.liupeiqing.spring.cloud.domain.Article;
import com.liupeiqing.spring.cloud.repository.ArticleSearchRepository;
import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

/**
 * @author liupeqing
 * @date 2018/9/17 14:58
 *
 * spring data elsaticsearch提供了三种构建查询模块的方式：
 *  1. 基本的增删改查：继承spring data提供的接口就默认提供
 *  2. 接口中声明方法：无需实现类，spring data根据方法名，自动生成实现类，方法名必须符合一定的规则（这里还扩展出一种忽略方法名，根据注解的方式查询）,
 *  样例参考：ArticleSearchRepository
 *  3. 自定义repository：在实现类中注入elasticsearchTemplate，实现上面两种方式不易实现的查询（例如：聚合、分组、深度翻页等）
 *
 */
@Slf4j
@RestController
public class ElasticSearchController {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 保存测试数据
     */
    @GetMapping("/elasticsearch/save")
    public void save(){
        Article article=new Article(3L,"srpignMVC教程","srpignMVC","srpignMVC入门到放弃",new Date(),22L);
        Article article1=new Article(4L,"sprig教程","spring","spring入门到放弃",new Date(),20L);
        Article article2=new Article(5L,"srpigCloud教程","springCloud","springCloud入门到放弃",new Date(),20L);
        Article article3=new Article(6L,"java教程","java","java入门到放弃",new Date(),120L);
        Article article4=new Article(7L,"php教程","php","php入门到放弃",new Date(),160L);
        Article article8=new Article(8L,"mysql教程","mysql","mysql入门到放弃",new Date(),460L);
        Article article9=new Article(9L,"redis教程","redis","redis入门到放弃",new Date(),60L);
        Article article10=new Article(10L,"c教程","c","c教程入门到放弃",new Date(),600L);
        //bulk index 批量方式插入
        List<Article> sampleEntities = Arrays.asList(article10);
        articleSearchRepository.save(sampleEntities);

    }

    /**
     * 获取所有测试数据
     * @return
     */
    @GetMapping("/elasticsearch/findAll")
    public List<Article> findAll(){
        Iterable<Article> iterable = this.articleSearchRepository.findAll();

        List<Article> myList = Lists.newArrayList(iterable);
        return myList;
    }

    /**
     * 根据title精确查找
     * @param title
     * @return
     */
    @GetMapping("/elasticsearch/findByTitle")
    public List<Article> findByTitle(@RequestParam("title") String title){
        List<Article> list = this.articleSearchRepository.findByTitle(title);
        return list;
    }

    /**
     *  根据内容查找  进行分页
     * @return
     */
    @GetMapping("/elasticsearch/pageByContent")
    public List<Article> pageByContent(){
        List<Article> list;
        // list=articleSearchRepository.findByTitleAndClickCount("教程",20 );//and
        // list=articleSearchRepository.findByTitleOrClickCount("教程",20 );//or

        // 分页参数:分页从0开始，clickCount倒序
        Pageable pageable = new PageRequest(0, 5, Sort.Direction.DESC,"clickCount");
        Page<Article> pageageRsutl=articleSearchRepository.findByContent("入门",pageable );
        System.out.println("总页数"+pageageRsutl.getTotalPages());
        list= pageageRsutl.getContent();//结果
        return list;
    }

    /**
     *  termQuery("key", obj) 完全匹配
     *  matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * @return
     */
    @GetMapping("/elasticsearch/pageByMoreCondition")
    public List<Article> pageByMoreCondition(){
        List<Article> list = new ArrayList<Article>();
        // 创建搜索 DSL:多条件搜索
        /* 搜索模式: boolQuery */
        Pageable pageable = new PageRequest(0, 5);//分页
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(boolQuery().should(QueryBuilders.matchQuery("content","教程")),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(boolQuery().should(QueryBuilders.matchQuery("clickCount", 20)),
                        ScoreFunctionBuilders.weightFactorFunction(1000));
//                //设置权重分 求和模式
//                .scoreMode("sum")
//                //设置权重分最低分
//                .setMinScore(10);

        // 创建搜索 DSL 查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        log.info("查询语句searchQuery" + searchQuery);
        Page<Article> pageageRsutl = this.articleSearchRepository.search(searchQuery);
        list = pageageRsutl.getContent();
        return list;
    }

    /**
     * 聚合条件测试
     * @return
     *  weightFactorFunction是评分函数，官网的控制相关度中有详细讲解
     */
    @GetMapping("/elasticsearch/andCondtuon")
    public List<Article> andCondtuon(){

        List<Article> list = new ArrayList<Article>();
        // 创建搜索 DSL 查询:weightFactorFunction是评分函数，官网的控制相关度中有详细讲解价格，地理位置因素
        /* 搜索模式 */
        String SCORE_MODE_SUM = "sum"; // 权重分求和模式
        Float  MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        Pageable pageable = new PageRequest(0, 5);//分页
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(boolQuery().should(QueryBuilders.matchQuery("content","教程")),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(boolQuery().should(QueryBuilders.matchQuery("clickCount",20)),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                //分值模式设置为:求和,
                .scoreMode(SCORE_MODE_SUM)
                //设置权重分最低分
                .setMinScore(MIN_SCORE);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
        Page<Article> pageageRsutl = this.articleSearchRepository.search(searchQuery);
        list = pageageRsutl.getContent();
        return list;
    }

    /**
     * 自定义查询
     * elasticsearchTemplate自定义查询：提交时间倒叙
     * elasticsearchTemplate
     * @return
     */
    @GetMapping("/elasticsearch/etmSearch")
    public List<Article> etmSearch(){
        //查询关键字
        String word="c入门";
        // 分页设置,postTime倒序
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC,"clickCount");
        SearchQuery searchQuery;
        //0.使用queryStringQuery完成单字符串查询queryStringQuery(word, "title")
        //1.multiMatchQuery多个字段匹配 .operator(MatchQueryBuilder.Operator.AND)多项匹配使用and查询即完全匹配都存在才查出来
        //searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(word, "title", "content").operator(MatchQueryBuilder.Operator.AND)).withPageable(pageable).build();

        //2.多条件查询：title和content必须包含word=“XXX”且clickCount必须大于200的以postTime倒序分页结果
        word="教程";
        searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery().must(multiMatchQuery(word, "title", "content").operator(MatchQueryBuilder.Operator.AND))
                        .must(rangeQuery("clickCount").gt(200))).withPageable(pageable).build();

        List<Article> list= elasticsearchTemplate.queryForList(searchQuery, Article.class);
        return list;
    }

}
