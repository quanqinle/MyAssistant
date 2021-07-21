package com.quanqinle.myassistant.entity.vo;

import lombok.Data;
import org.dozer.Mapping;

/**
 * 中国朝代 VO
 * @author quanql
 */
@Data
public class DynastyVO {

    /**
     * 朝代名
     */
    private String name;

    /**
     * 朝代年份
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
    @Mapping("ethnic")
    private String nation;

    /**
     * 建国君王
     */
    @Mapping("founder")
    private String stateFound;

    private String _id;
    private String pinyin;
    private String onlyId;
    private String updateAt;
    private String __v;

}
