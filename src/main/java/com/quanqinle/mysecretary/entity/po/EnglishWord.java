package com.quanqinle.mysecretary.entity.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author quanql
 * @version 2021/7/9
 */
@Entity
@Data
@Table(name = "english_word")
public class EnglishWord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(value = "字词句")
    private String word;
    @ApiModelProperty(value = "类型 0未分类 1单词 2词组 3句型 4谚语成语 5单词对比 6词组对比 7句子 8语法")
    private Integer type;
    @ApiModelProperty(value = "音标")
    private String ipa;
    @ApiModelProperty(value = "含义")
    private String meaning;
    @ApiModelProperty(value = "示例。如果和 meaning 必须有一个为空，请让 example 为空")
    private String example;
    @ApiModelProperty(value = "分类。重要性，难度水平，来源集合 等")
    private String category;
    @ApiModelProperty(value = "其他")
    private String others;
    @ApiModelProperty(value = "0删除，1有效")
    private Integer state;

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
