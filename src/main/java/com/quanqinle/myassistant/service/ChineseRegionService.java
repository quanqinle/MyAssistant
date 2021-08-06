package com.quanqinle.myassistant.service;

import com.quanqinle.myassistant.entity.po.ChineseRegion;

import java.util.List;
import java.util.Optional;

/**
 * @author quanql
 * @version 2021/7/26
 */
public interface ChineseRegionService {

    /**
     * insert
     *
     * @param chineseRegion a ChineseRegion object
     * @return
     */
    ChineseRegion insert(ChineseRegion chineseRegion);

    /**
     * update
     *
     * @param chineseRegion a ChineseRegion object
     * @return
     */
    ChineseRegion update(ChineseRegion chineseRegion);

    /**
     * query by id
     *
     * @param id ChineseRegion id
     * @return
     */
    Optional<ChineseRegion> queryById(Long id);

    /**
     * delete by id
     *
     * @param id ChineseRegion id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * Insert all
     *
     * @param chineseRegions a ChineseRegion list
     * @return
     */
    List<ChineseRegion> insertAll(List<ChineseRegion> chineseRegions);

    /**
     * Query all provinces
     *
     * @return province list
     */
    List<ChineseRegion> queryProvince();

    /**
     * Find region list by code and order by revision desc
     *
     * @param code region code
     * @return region list
     */
    List<ChineseRegion> getByCode(String code);

    /**
     * Get all revision
     *
     * @return revision list
     */
    List<String> getRevisions();

    /**
     * Get all Code, even including the invalid code, with the latest revision
     *
     * @return region list
     */
    List<ChineseRegion> getAllCodeIncludingInvalidCodeWithLatestRevision();
}