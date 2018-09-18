package com.liupeiqing.spring.cloud.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liupeqing
 * @date 2018/9/17 16:34
 *
 *   （1）关系型数据库中的数据库（DataBase），等价于ES中的索引（Index）
 *   （2）一个数据库下面有N张表（Table），等价于1个索引Index下面有N多类型（Type），
 *   （3）一个数据库表（Table）下的数据由多行（ROW）多列（column，属性）组成，等价于1个Type由多个文档（Document）和多Field组成。
 *   （4）在一个关系型数据库里面，schema定义了表、每个表的字段，还有表和字段之间的关系。
 *        与之对应的，在ES中：Mapping定义索引下的Type的字段处理规则，即索引如何建立、索引类型、是否保存原始索引JSON文档、是否压缩原始JSON文档、是否需要分词处理、如何进行分词处理等。
 *   （5）在数据库中的增insert、删delete、改update、查search操作等价于ES中的增PUT/POST、删Delete、改_update、查GET.
 */
@Data
@Document(indexName = "article_index",type = "article",shards = 5,replicas = 1,indexStoreType = "fs",refreshInterval = "-1")
public class Article implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    /**标题*/
    // @Field(index= FieldIndex.not_analyzed,store=true,type=FieldType.String)
    private String title;
    /**摘要*/
    private String abstracts;
    /**内容*/
    private String content;

    //TODO此处 时间在转换的时候，出现问题，因为没有深看，此处先注释掉
    /**发表时间*/
//    @Field(format=DateFormat.date_time,index=FieldIndex.not_analyzed,store=true,type= FieldType.Object)
//    private Date postTime;

    /**点击率*/
    private Long clickCount;


    public Article() {
    }

    public Article(Long id, String title, String abstracts, String content, Date postTime, Long clickCount) {
        this.id = id;
        this.title = title;
        this.abstracts = abstracts;
        this.content = content;
       // this.postTime = postTime;
        this.clickCount = clickCount;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", content='" + content + '\'' +
              //  ", postTime=" + postTime +
                ", clickCount=" + clickCount +
                '}';
    }

}
