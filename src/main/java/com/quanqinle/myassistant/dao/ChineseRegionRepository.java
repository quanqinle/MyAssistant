package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.ChineseRegion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author quanql
 * @version 2021/7/26
 */
@Repository
public interface ChineseRegionRepository extends JpaRepository<ChineseRegion, Long> {

    /**
     * Find region list by code and order by revision desc
     *
     * @param code address code
     * @return region list
     */
    List<ChineseRegion> findByCodeOrderByRevisionDesc(String code);

    /**
     * Find by revision and type
     *
     * @param revision -
     * @param type -
     * @return
     */
    List<ChineseRegion> findByRevisionAndType(String revision, int type);

    /**
     * Query revision list
     *
     * @return revision list
     */
    @Query("select DISTINCT cr.revision from ChineseRegion cr order by cr.revision desc")
    List<String> findRevisionsOrderByRevisionDesc();

    /**
     * Get code data by filtering the latest revision
     *
     * @return -
     */
    @Query(value="SELECT cr.* \n" +
            "FROM\n" +
            "\tchinese_region cr,\n" +
            "\t(SELECT MAX(c.revision) as max_revision, c.code\n" +
            "\tfrom chinese_region c\n" +
            "\tgroup by c.code) maxcr\n" +
            "WHERE\n" +
            "\tcr.code = maxcr.code\n" +
            "\tAND cr.revision = maxcr.max_revision",
            nativeQuery = true)
    List<ChineseRegion> getAllWithLatestRevision();
}