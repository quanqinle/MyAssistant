package com.quanqinle.myassistant.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 中国朝代 PO
 * @author quanql
 */
@Data
@Entity
@Table(name = "Chinese_dynasty")
public class Dynasty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 朝代名
     */
    private String name;

    /**
     * 起始年份
     */
    private String yearStart;

    /**
     * 终止年份
     */
    private String yearEnd;

    /**
     * 延续时间
     */
    private String term;

    /**
     * 朝代起止年份
     */
    private String years;

    /**
     * 建国君王
     */
    private String founder;

    /**
     * 国都
     */
    private String capital;

    /**
     * 当今的位置
     */
    private String nowLocation;

    /**
     * 父级朝代名
     */
    private String parentName;

    /**
     * 民族
     */
    private String ethnic;

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
