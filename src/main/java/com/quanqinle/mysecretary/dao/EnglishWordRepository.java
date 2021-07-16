package com.quanqinle.mysecretary.dao;

import com.quanqinle.mysecretary.entity.po.EnglishWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author quanql
 * @version 2021/7/9
 */
@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
    /**
     * 查找
     * @param type
     * @param pageable
     * @return
     */
    Page<EnglishWord> findByType(int type, Pageable pageable);

}